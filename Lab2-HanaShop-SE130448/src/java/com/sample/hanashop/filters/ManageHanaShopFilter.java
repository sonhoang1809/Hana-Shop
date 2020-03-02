/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.filters;

import com.sample.hanashop.daos.BillDAO;
import com.sample.hanashop.daos.CategoryDAO;
import com.sample.hanashop.daos.FoodDAO;
import com.sample.hanashop.daos.FoodInBillDAO;
import com.sample.hanashop.daos.KindDAO;
import com.sample.hanashop.daos.UserDAO;
import com.sample.hanashop.dtos.BillDTO;
import com.sample.hanashop.dtos.CategoryDTO;
import com.sample.hanashop.dtos.KindDTO;
import com.sample.hanashop.mails.SendMailSSL;
import com.sample.hanashop.supports.Support;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 *
 * @author sonho
 */
public class ManageHanaShopFilter implements Filter {

    private static final Logger log = Logger.getLogger(ManageHanaShopFilter.class.getName());
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public ManageHanaShopFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("ManageHanaShopFilter:DoBeforeProcessing");
        }

    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("ManageHanaShopFilter:DoAfterProcessing");
        }

    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            //HttpSession session = req.getSession();
            //UserDTO userDTO = (UserDTO) session.getAttribute("USERDTO");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String dateStr = request.getParameter("date");
            //dung de ve bieu do so bill moi ngay
            {
                List<Integer> listTime = new ArrayList<>();
                List<String> listDate = new ArrayList<>();
                if (dateStr == null || dateStr.equals("")) {
                    String currDay = dateFormat.format(date);
                    String tommorrow = Support.getTomorrowDay(currDay);
                    for (int i = 0; i < 7; i++) {
                        if (i != 0) {
                            tommorrow = currDay;
                            currDay = Support.getYesterday(currDay);
                        }
                        listTime.add(0, BillDAO.getNumberOfBillFromDateToDate(currDay, tommorrow));
                        listDate.add(0, Support.convertDatetoDateAndMonth(currDay));
                    }
                } else {
                    String currDay = Support.convertDate(dateStr);
                    String tomorrow = Support.getTomorrowDay(currDay);
                    for (int i = 0; i < 7; i++) {
                        if (i != 0) {
                            currDay = tomorrow;
                            tomorrow = Support.getTomorrowDay(tomorrow);
                        }
                        listTime.add(BillDAO.getNumberOfBillFromDateToDate(currDay, tomorrow));
                        listDate.add(Support.convertDatetoDateAndMonth(currDay));
                    }
                }
                req.setAttribute("ListTimeFromDateToDate", listTime);
                req.setAttribute("ListDate", listDate);
            }
            //Number of food of each category sold performance
            List<String> listBillOrdered = BillDAO.getAllBillIdIsOrdered();
            List<CategoryDTO> listCategory = CategoryDAO.getAllListCategory();
            if (listCategory != null && listBillOrdered != null) {
                for (CategoryDTO x : listCategory) {
                    int quantitySold = 0;
                    List<String> listFoodIdOfCategory = FoodDAO.getListIdFoodHasCategory(x.getCategoryID());
                    if (listFoodIdOfCategory != null) {
                        quantitySold = FoodInBillDAO.getQuantityOfFoodOfCategoryInListBill(listBillOrdered, listFoodIdOfCategory);
                    }
                    x.setNumFoodSold(quantitySold);
                }
            }
            req.setAttribute("ListCategoryPer", listCategory);
            //Kind food in storage
            {
                List<KindDTO> listKind = KindDAO.getAllKindFood();
                for (KindDTO x : listKind) {
                    List<CategoryDTO> listCate = CategoryDAO.getListCategoryByKindID(x.getKindID());
                    x.setTotalNumFood(FoodDAO.getQuantityOfFoodHasCategory(listCate));
                }
                req.setAttribute("listKindStorage", listKind);
            }
            //Revenue each 7 days
            {
                List<Float> listMoney = new ArrayList<>();
                List<String> listDate = new ArrayList<>();
                if (dateStr == null || dateStr.equals("")) {
                    String currDay = dateFormat.format(date);
                    String tommorrow = Support.getTomorrowDay(currDay);
                    for (int i = 0; i < 7; i++) {
                        if (i != 0) {
                            tommorrow = currDay;
                            currDay = Support.getYesterday(currDay);
                        }
                        listMoney.add(0, BillDAO.getTotalMoneyFromDateToDate(currDay, tommorrow));
                        listDate.add(0, Support.convertDatetoDateAndMonth(currDay));
                    }
                } else {
                    String currDay = Support.convertDate(dateStr);
                    String tommorrow = Support.getTomorrowDay(currDay);
                    for (int i = 0; i < 7; i++) {
                        if (i != 0) {
                            currDay = tommorrow;
                            tommorrow = Support.getTomorrowDay(tommorrow);
                        }
                        listMoney.add(BillDAO.getTotalMoneyFromDateToDate(currDay, tommorrow));
                        listDate.add(Support.convertDatetoDateAndMonth(currDay));
                    }
                }
                req.setAttribute("ListMoneyByDay", listMoney);
                req.setAttribute("ListDateMoney", listDate);
            }
            {//get order list
                if (dateStr == null || dateStr.equals("")) {
                    //neu null thi hien so bill cua ngay hom nay
                    String currDay = dateFormat.format(date);
                    String tomorrowDay = Support.getTomorrowDay(currDay);
                    //  String the7DayBefore = Support.get7DayBefore(currDay);
                    List<BillDTO> listOrder = BillDAO.getListBillFromDateToDate(currDay, tomorrowDay);
                    if (listOrder != null) {
                        for (BillDTO x : listOrder) {
                            x.setUserName(UserDAO.getUserNameById(x.getUserID()));
                        }
                    }
                    req.setAttribute("LISTORDERBILL", listOrder);
                } else {
                    String currDay = Support.convertDate(dateStr);
                    String tomorrowDay = Support.getTomorrowDay(currDay);
                    List<BillDTO> listOrder = BillDAO.getListBillFromDateToDate(currDay, tomorrowDay);
                    if (listOrder != null) {
                        for (BillDTO x : listOrder) {
                            x.setUserName(UserDAO.getUserNameById(x.getUserID()));
                        }
                    }
                    req.setAttribute("LISTORDERBILL", listOrder);
                }
            }
        } catch (Exception ex) {
            log.error("Error at ManageHanaShopFilter: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at ManageHanaShopFilter: " + ex.toString(), "Error!!");
        } finally {
            chain.doFilter(request, response);
        }

    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("ManageHanaShopFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("ManageHanaShopFilter()");
        }
        StringBuffer sb = new StringBuffer("ManageHanaShopFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
