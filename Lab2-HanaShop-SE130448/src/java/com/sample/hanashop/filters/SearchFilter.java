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
import com.sample.hanashop.daos.StatusFoodDAO;
import com.sample.hanashop.daos.UserDAO;
import com.sample.hanashop.dtos.CategoryDTO;
import com.sample.hanashop.dtos.FoodDTO;
import com.sample.hanashop.dtos.KindDTO;
import com.sample.hanashop.dtos.StatusFoodDTO;
import com.sample.hanashop.dtos.UserDTO;
import com.sample.hanashop.mails.SendMailSSL;
import com.sample.hanashop.supports.Support;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author sonho
 */
public class SearchFilter implements Filter {

    private static final boolean debug = true;
    private static final Logger log = Logger.getLogger(SearchFilter.class.getName());

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public SearchFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("SearchFilter:DoBeforeProcessing");
        }

    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("SearchFilter:DoAfterProcessing");
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
            //HttpServletResponse res = (HttpServletResponse) response;
            HttpSession session = req.getSession();
            UserDTO userDTO = (UserDTO) session.getAttribute("USERDTO");
//get list kind if guest and user
            if (userDTO == null) {
                //list kind
                List<KindDTO> listKind = KindDAO.getAllKindFood();
                req.setAttribute("LISTKIND", listKind);
            } else {
                if (userDTO.getRoleID().equalsIgnoreCase("US")) {
                    //list kind
                    List<KindDTO> listKind = KindDAO.getAllKindFood();
                    req.setAttribute("LISTKIND", listKind);
                }
            }
            //get list status food and list category if admin
            if (userDTO != null) {
                if (userDTO.getRoleID().equalsIgnoreCase("AD")) {
                    List<StatusFoodDTO> listStatus = StatusFoodDAO.getAllListStatus();
                    req.setAttribute("LISTSTATUSFOOD", listStatus);
                    List<CategoryDTO> listCategory = CategoryDAO.getAllListCategory();
                    req.setAttribute("LISTCATEGORIES", listCategory);
                }
            }
            {    //The most purchased products
                List<String> listBillIsOrdered = BillDAO.getAllBillIdIsOrdered();
                if (listBillIsOrdered != null) {
                    List<String> listIdFoodInOrderedBill = FoodInBillDAO.listIdFoodInOrderedBill(listBillIsOrdered);
                    if (listIdFoodInOrderedBill != null) {
                        List<FoodDTO> listOrderedFood = new ArrayList<>();
                        for (String x : listIdFoodInOrderedBill) {
                            if (FoodDAO.checkFoodIsSelling(x)) {
                                int orderTime = FoodInBillDAO.getTimeAppearInListBill(listBillIsOrdered, x);
                                FoodDTO foodDTO = new FoodDTO(x, orderTime);
                                listOrderedFood.add(foodDTO);
                            }
                        }
                        //sort
                        Support.sortListFoodOrderTime(listOrderedFood);

                        FoodDTO foodDTO0 = FoodDAO.getFoodDTO(listOrderedFood.get(0).getIdFood());

                        foodDTO0.setShortDescription(Support.getShortDescription(foodDTO0.getDescription()));
                        req.setAttribute("THEMOSTPURCHASEDFOOD0", foodDTO0);
                        if (listOrderedFood.size() >= 2) {
                            FoodDTO foodDTO1 = FoodDAO.getFoodDTO(listOrderedFood.get(1).getIdFood());

                            foodDTO1.setShortDescription(Support.getShortDescription(foodDTO0.getDescription()));
                            req.setAttribute("THEMOSTPURCHASEDFOOD1", foodDTO1);
                        }
                    }
                }
            }
            //list food new
            {
                List<FoodDTO> listFoodNew = FoodDAO.getListFoodNew();
                if (listFoodNew != null) {
                    for (FoodDTO x : listFoodNew) {
                        x.setShortDescription(Support.getShortDescription(x.getDescription()));
                        CategoryDTO cateDTO = CategoryDAO.getCategoryByCategoryID(x.getCategoryID());
                        x.setCategoryName(cateDTO.getCategoryName());
                        x.setKind(KindDAO.getKindFoodByKindID(cateDTO.getKindID()));
                    }
                }
                req.setAttribute("LISTFOODNEW", listFoodNew);
            }
            //list food recent view of user

            {
                if (userDTO != null) {
                    List<String> listIdFoodsRecentView = (List<String>) session.getAttribute("LISTIDFOODSRECENTVIEW");
                    if (listIdFoodsRecentView != null) {
                        UserDAO.setFoodRecentViewOfUser(userDTO.getUserID(),
                                Support.convertToStringIdFoodsRecent(listIdFoodsRecentView));
                    } else {
                        String idFoodsRecentView = UserDAO.getFoodsRecentViewOfUser(userDTO.getUserID());
                        if (idFoodsRecentView != null) {
                            listIdFoodsRecentView = Support.convertToListIdFoodsRecent(idFoodsRecentView);
                        }
                    }
                    if (listIdFoodsRecentView != null && listIdFoodsRecentView.size() >= 1) {
                        List<FoodDTO> listFoodDTORecentView = new ArrayList<>();
                        if (listIdFoodsRecentView.size() > 3) {
                            for (int i = listIdFoodsRecentView.size() - 1; i >= listIdFoodsRecentView.size() - 3; i--) {
                                FoodDTO foodDTO = FoodDAO.getFoodDTO(listIdFoodsRecentView.get(i));
                                if (foodDTO != null) {
                                    if (foodDTO.getStatusCode() == 1) {
                                        foodDTO.setShortDescription(Support.getShortDescription(foodDTO.getDescription()));
                                        CategoryDTO cateDTO = CategoryDAO.getCategoryByCategoryID(foodDTO.getCategoryID());
                                        foodDTO.setCategoryName(cateDTO.getCategoryName());
                                        foodDTO.setKind(KindDAO.getKindFoodByKindID(cateDTO.getKindID()));
                                        listFoodDTORecentView.add(foodDTO);
                                    }
                                }
                            }
                        } else {
                            for (int i = listIdFoodsRecentView.size() - 1; i >= 0; i--) {

                                FoodDTO foodDTO = FoodDAO.getFoodDTO(listIdFoodsRecentView.get(i));
                                if (foodDTO != null) {
                                    if (foodDTO.getStatusCode() == 1) {
                                        foodDTO.setShortDescription(Support.getShortDescription(foodDTO.getDescription()));
                                        CategoryDTO cateDTO = CategoryDAO.getCategoryByCategoryID(foodDTO.getCategoryID());
                                        foodDTO.setCategoryName(cateDTO.getCategoryName());
                                        foodDTO.setKind(KindDAO.getKindFoodByKindID(cateDTO.getKindID()));
                                        listFoodDTORecentView.add(foodDTO);
                                    }
                                }
                            }
                        }
                        req.setAttribute("LISTFOODRECENTVIEW", listFoodDTORecentView);
                    }
                }//list food recent view of guest
                else {
                    List<String> listIdFoodsRecentView = (List<String>) session.getAttribute("LISTIDFOODSRECENTVIEW");
                    if (listIdFoodsRecentView != null && listIdFoodsRecentView.size() >= 1) {
                        List<FoodDTO> listFoodDTORecentView = new ArrayList<>();
                        if (listIdFoodsRecentView.size() > 3) {
                            for (int i = listIdFoodsRecentView.size() - 1; i >= listIdFoodsRecentView.size() - 3; i--) {
                                FoodDTO foodDTO = FoodDAO.getFoodDTO(listIdFoodsRecentView.get(i));
                                if (foodDTO != null) {
                                    if (foodDTO.getStatusCode() == 1) {
                                        foodDTO.setShortDescription(Support.getShortDescription(foodDTO.getDescription()));
                                        CategoryDTO cateDTO = CategoryDAO.getCategoryByCategoryID(foodDTO.getCategoryID());
                                        foodDTO.setCategoryName(cateDTO.getCategoryName());
                                        foodDTO.setKind(KindDAO.getKindFoodByKindID(cateDTO.getKindID()));
                                        listFoodDTORecentView.add(foodDTO);
                                    }
                                }
                            }
                        } else {
                            for (int i = listIdFoodsRecentView.size() - 1; i >= 0; i--) {
                                FoodDTO foodDTO = FoodDAO.getFoodDTO(listIdFoodsRecentView.get(i));
                                if (foodDTO != null) {
                                    if (foodDTO.getStatusCode() == 1) {
                                        foodDTO.setShortDescription(Support.getShortDescription(foodDTO.getDescription()));
                                        CategoryDTO cateDTO = CategoryDAO.getCategoryByCategoryID(foodDTO.getCategoryID());
                                        foodDTO.setCategoryName(cateDTO.getCategoryName());
                                        foodDTO.setKind(KindDAO.getKindFoodByKindID(cateDTO.getKindID()));
                                        listFoodDTORecentView.add(foodDTO);
                                    }
                                }
                            }
                        }
                        req.setAttribute("LISTFOODRECENTVIEW", listFoodDTORecentView);
                    }
                }
            }
            // List food customers' preferences
            if (userDTO != null) {
                if (userDTO.getRoleID().equalsIgnoreCase("US")) {
                    String userID = userDTO.getUserID();
                    List<String> listIdBillOrderedByUserID = BillDAO.getListIdBillOrderedByUserID(userID);
                    if (listIdBillOrderedByUserID != null) {
                        List<String> listIdFoodInOrderedBill = FoodInBillDAO.listIdFoodInOrderedBill(listIdBillOrderedByUserID);
                        if (listIdFoodInOrderedBill != null) {
                            List<FoodDTO> listFoodUserPreference = new ArrayList<>();
                            if (listIdFoodInOrderedBill.size() > 5) {
                                TreeSet<FoodDTO> listFoodWithNumOrder = new TreeSet<>();
                                for (String x : listIdFoodInOrderedBill) {
                                    if (FoodDAO.checkFoodIsSelling(x)) {
                                        int timeOrder = FoodInBillDAO.getTimeAppearInListBill(listIdBillOrderedByUserID, x);
                                        FoodDTO dto = new FoodDTO(x, timeOrder);
                                        listFoodWithNumOrder.add(dto);
                                    }
                                }
                                for (int i = 0; i < 5; i++) {
                                    String id = listFoodWithNumOrder.pollFirst().getIdFood();
                                    if (FoodDAO.checkFoodIsSelling(id)) {
                                        FoodDTO foodDTO = FoodDAO.getFoodDTO(id);
                                        foodDTO.setShortDescription(Support.getShortDescription(foodDTO.getDescription()));
                                        CategoryDTO cateDTO = CategoryDAO.getCategoryByCategoryID(foodDTO.getCategoryID());
                                        foodDTO.setCategoryName(cateDTO.getCategoryName());
                                        foodDTO.setKind(KindDAO.getKindFoodByKindID(cateDTO.getKindID()));
                                        listFoodUserPreference.add(foodDTO);
                                    }
                                }
                            } else {
                                for (String x : listIdFoodInOrderedBill) {
                                    if (FoodDAO.checkFoodIsSelling(x)) {
                                        FoodDTO foodDTO = FoodDAO.getFoodDTO(x);
                                        foodDTO.setShortDescription(Support.getShortDescription(foodDTO.getDescription()));
                                        CategoryDTO cateDTO = CategoryDAO.getCategoryByCategoryID(foodDTO.getCategoryID());
                                        foodDTO.setCategoryName(cateDTO.getCategoryName());
                                        foodDTO.setKind(KindDAO.getKindFoodByKindID(cateDTO.getKindID()));
                                        listFoodUserPreference.add(foodDTO);
                                    }
                                }
                            }
                            request.setAttribute("LISTFOODUSERPREFERENCE", listFoodUserPreference);
                        }
                    }
                }
            }

        } catch (Exception ex) {
//            ex.printStackTrace();
            log.error("Error at SearchFilter: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at SearchFilter: " + ex.toString(), "Error!!");
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
                log("SearchFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("SearchFilter()");
        }
        StringBuffer sb = new StringBuffer("SearchFilter(");
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
