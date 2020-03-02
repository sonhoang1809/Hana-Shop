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
import java.util.Random;
import java.util.TreeSet;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author sonho
 */
public class LoadFoodDetailsFilter implements Filter {

    private static final boolean debug = true;
    private static final Logger log = Logger.getLogger(LoadFoodDetailsFilter.class.getName());
    private final String SEARCH = "SearchController";
    private FilterConfig filterConfig = null;

    public LoadFoodDetailsFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("LoadFoodDetailsFilter:DoBeforeProcessing");
        }

    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("LoadFoodDetailsFilter:DoAfterProcessing");
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
            HttpServletResponse res = (HttpServletResponse) response;
            HttpSession session = req.getSession();
            UserDTO userDTO = (UserDTO) session.getAttribute("USERDTO");
            String idFood = req.getParameter("idFood");
            FoodDTO fDTO = FoodDAO.getFoodDTO(idFood);
            if (fDTO.getQuantity() == 0 || fDTO.getStatusCode() == 0 || fDTO.getStatusCode() == 2) {
                if (userDTO != null) {
                    //xu ly khi food bi delete luc load details food voi user
                    if (userDTO.getRoleID().equalsIgnoreCase("US")) {
                        if (fDTO.getStatusCode() == 0 || fDTO.getQuantity() == 0) {
                            req.setAttribute("ErrorFood", fDTO.getIdFood() + "-" + fDTO.getNameFood() + " is out of stock!!");
                        } else if (fDTO.getStatusCode() == 2) {
                            req.setAttribute("ErrorFood", fDTO.getIdFood() + "-" + fDTO.getNameFood() + " is stop selling!!");
                        }
                        req.getRequestDispatcher(SEARCH).forward(req, res);
                        return;
                    }
                } //xu ly khi food bi delete luc load details food voi guest
                else {
                    if (fDTO.getStatusCode() == 0 || fDTO.getQuantity() == 0) {
                        req.setAttribute("ErrorFood", fDTO.getIdFood() + "-" + fDTO.getNameFood() + " is out of stock!!");
                    } else if (fDTO.getStatusCode() == 2) {
                        req.setAttribute("ErrorFood", fDTO.getIdFood() + "-" + fDTO.getNameFood() + " is stop selling!!");
                    }
                    req.getRequestDispatcher(SEARCH).forward(req, res);
                    return;
                }
            }
            //add to recent view of user
            if (userDTO != null) {
                List<String> listIdFoodsRecentView = (List<String>) session.getAttribute("LISTIDFOODSRECENTVIEW");
                if (listIdFoodsRecentView != null) {
                    if (FoodDAO.checkFoodIsSelling(idFood)) {
                        Support.addIdFoodToListIdFoodsRecent(idFood, listIdFoodsRecentView);
                    }
                } else {
                    String idFoodsRecentView = UserDAO.getFoodsRecentViewOfUser(userDTO.getUserID());
                    if (idFoodsRecentView == null) {
                        listIdFoodsRecentView = new ArrayList<>();
                    } else {
                        listIdFoodsRecentView = Support.convertToListIdFoodsRecent(idFoodsRecentView);
                    }
                    if (FoodDAO.checkFoodIsSelling(idFood)) {
                        listIdFoodsRecentView = Support.addIdFoodToListIdFoodsRecent(idFood, listIdFoodsRecentView);
                    }
                }
                UserDAO.setFoodRecentViewOfUser(userDTO.getUserID(),
                        Support.convertToStringIdFoodsRecent(listIdFoodsRecentView));
                session.setAttribute("LISTIDFOODSRECENTVIEW", listIdFoodsRecentView);
            }//add to recent view of guest
            else {
                List<String> listIdFoodsRecentView = (List<String>) session.getAttribute("LISTIDFOODSRECENTVIEW");
                if (listIdFoodsRecentView == null) {
                    listIdFoodsRecentView = new ArrayList<>();
                }
                Support.addIdFoodToListIdFoodsRecent(idFood, listIdFoodsRecentView);
                session.setAttribute("LISTIDFOODSRECENTVIEW", listIdFoodsRecentView);
            }
            //load the most purchased food with, k phan biet admin, customer, guest
            List<String> listBillIsOrdered = BillDAO.getAllBillIdIsOrdered();
            if (listBillIsOrdered != null) {
                List<String> listBillIsOrderedHasIdFood = FoodInBillDAO.getListIdBillIsOrderedHasIdFood(idFood, listBillIsOrdered);
                if (listBillIsOrderedHasIdFood != null) {
                    List<String> listIdFoodIsOrderedInIdBill = FoodInBillDAO.getListIdFoodIsOrderedInIdBillExcepIdFood(idFood,
                            listBillIsOrderedHasIdFood);
                    if (listIdFoodIsOrderedInIdBill != null) {
                        List<FoodDTO> listFoodMostPurchasedWith = new ArrayList<>();
                        if (listIdFoodIsOrderedInIdBill.size() > 5) {
                            TreeSet<FoodDTO> listFoodWithNumOrder = new TreeSet<>();
                            for (String x : listIdFoodIsOrderedInIdBill) {
                                if (FoodDAO.checkFoodIsSelling(x)) {
                                    int timeOrder = FoodInBillDAO.getTimeAppearInListBill(listBillIsOrderedHasIdFood, x);
                                    FoodDTO dto = new FoodDTO(x, timeOrder);
                                    listFoodWithNumOrder.add(dto);
                                }
                            }
                            for (int i = 0; i < 5; i++) {
                                String id = listFoodWithNumOrder.pollFirst().getIdFood();
                                if (FoodDAO.checkFoodIsSelling(id)) {
                                    FoodDTO foodDTO = FoodDAO.getFoodDTO(id);
                                    if (!listFoodMostPurchasedWith.contains(foodDTO)) {
                                        foodDTO.setShortDescription(Support.getShortDescription(foodDTO.getDescription()));
                                        CategoryDTO cateDTO = CategoryDAO.getCategoryByCategoryID(foodDTO.getCategoryID());
                                        foodDTO.setCategoryName(cateDTO.getCategoryName());
                                        foodDTO.setKind(KindDAO.getKindFoodByKindID(cateDTO.getKindID()));
                                        listFoodMostPurchasedWith.add(foodDTO);
                                    }
                                }
                            }
                        } else {
                            for (String x : listIdFoodIsOrderedInIdBill) {
                                if (FoodDAO.checkFoodIsSelling(x)) {
                                    FoodDTO foodDTO = FoodDAO.getFoodDTO(x);
                                    if (!listFoodMostPurchasedWith.contains(foodDTO)) {
                                        foodDTO.setShortDescription(Support.getShortDescription(foodDTO.getDescription()));
                                        CategoryDTO cateDTO = CategoryDAO.getCategoryByCategoryID(foodDTO.getCategoryID());
                                        foodDTO.setCategoryName(cateDTO.getCategoryName());
                                        foodDTO.setKind(KindDAO.getKindFoodByKindID(cateDTO.getKindID()));
                                        listFoodMostPurchasedWith.add(foodDTO);
                                    }
                                }
                            }
                        }
                        req.setAttribute("LISTFOODPURCHASEDWITH", listFoodMostPurchasedWith);
                    }
                }
            }
            if (userDTO == null) {
                //load food relative
                {
                    FoodDTO foodDTO = FoodDAO.getFoodDTO(idFood);
                    CategoryDTO cateDTO = CategoryDAO.getCategoryByCategoryID(foodDTO.getCategoryID());

                    List<CategoryDTO> listCategory = CategoryDAO.getListCategoryByKindID(cateDTO.getKindID());
                    String strPriceLower = req.getParameter("priceLower");
                    if (strPriceLower == null || strPriceLower.equalsIgnoreCase("")) {
                        strPriceLower = "100";
                    }
                    String strPriceHigher = req.getParameter("priceHigher");
                    if (strPriceHigher == null || strPriceHigher.equalsIgnoreCase("")) {
                        strPriceHigher = "1";
                    }
                    float priceLower = Float.parseFloat(strPriceLower);
                    float priceHigher = Float.parseFloat(strPriceHigher);
                    if (listCategory != null) {
                        List<FoodDTO> listRecomment = FoodDAO.getListFoodRecommend(priceLower, priceHigher, 1, listCategory, idFood);
                        Random r = new Random();
                        int num1 = r.nextInt(listRecomment.size());
                        int num2 = -1;
                        do {
                            num2 = r.nextInt(listRecomment.size());
                        } while (num2 == num1);
                        req.setAttribute("FOODREC1", listRecomment.get(num1));
                        req.setAttribute("FOODREC2", listRecomment.get(num2));
                    }
                }
            } else if (userDTO != null && userDTO.getRoleID().equalsIgnoreCase("US")) {
                //load food relative
                {
                    FoodDTO foodDTO = FoodDAO.getFoodDTO(idFood);
                    CategoryDTO cateDTO = CategoryDAO.getCategoryByCategoryID(foodDTO.getCategoryID());

                    List<CategoryDTO> listCategory = CategoryDAO.getListCategoryByKindID(cateDTO.getKindID());
                    String strPriceLower = req.getParameter("priceLower");
                    if (strPriceLower == null || strPriceLower.equalsIgnoreCase("")) {
                        strPriceLower = "100";
                    }
                    String strPriceHigher = req.getParameter("priceHigher");
                    if (strPriceHigher == null || strPriceHigher.equalsIgnoreCase("")) {
                        strPriceHigher = "1";
                    }
                    float priceLower = Float.parseFloat(strPriceLower);
                    float priceHigher = Float.parseFloat(strPriceHigher);
                    if (listCategory != null) {
                        List<FoodDTO> listRecomment = FoodDAO.getListFoodRecommend(priceLower, priceHigher, 1, listCategory, idFood);
                        Random r = new Random();
                        int num1 = r.nextInt(listRecomment.size());
                        int num2 = -1;
                        do {
                            num2 = r.nextInt(listRecomment.size());
                        } while (num2 == num1);
                        req.setAttribute("FOODREC1", listRecomment.get(num1));
                        req.setAttribute("FOODREC2", listRecomment.get(num2));
                    }
                }
            }
            //list food recent view of user
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
                            if (foodDTO.getStatusCode() == 1) {
                                foodDTO.setShortDescription(Support.getShortDescription(foodDTO.getDescription()));
                                CategoryDTO cateDTO = CategoryDAO.getCategoryByCategoryID(foodDTO.getCategoryID());
                                foodDTO.setCategoryName(cateDTO.getCategoryName());
                                foodDTO.setKind(KindDAO.getKindFoodByKindID(cateDTO.getKindID()));
                                listFoodDTORecentView.add(foodDTO);
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
                            if (foodDTO.getStatusCode() == 1) {
                                foodDTO.setShortDescription(Support.getShortDescription(foodDTO.getDescription()));
                                CategoryDTO cateDTO = CategoryDAO.getCategoryByCategoryID(foodDTO.getCategoryID());
                                foodDTO.setCategoryName(cateDTO.getCategoryName());
                                foodDTO.setKind(KindDAO.getKindFoodByKindID(cateDTO.getKindID()));
                                listFoodDTORecentView.add(foodDTO);
                            }
                        }
                    } else {
                        for (int i = listIdFoodsRecentView.size() - 1; i >= 0; i--) {
                            FoodDTO foodDTO = FoodDAO.getFoodDTO(listIdFoodsRecentView.get(i));
                            if (foodDTO.getStatusCode() == 1) {
                                foodDTO.setShortDescription(Support.getShortDescription(foodDTO.getDescription()));
                                CategoryDTO cateDTO = CategoryDAO.getCategoryByCategoryID(foodDTO.getCategoryID());
                                foodDTO.setCategoryName(cateDTO.getCategoryName());
                                foodDTO.setKind(KindDAO.getKindFoodByKindID(cateDTO.getKindID()));
                                listFoodDTORecentView.add(foodDTO);
                            }
                        }
                    }
                    req.setAttribute("LISTFOODRECENTVIEW", listFoodDTORecentView);
                }
            }
            //load category list and status if admin
            if (userDTO != null) {
                if (userDTO.getRoleID().equalsIgnoreCase("AD")) {
                    List<CategoryDTO> listCatetory = CategoryDAO.getAllListCategory();
                    List<StatusFoodDTO> listStatusFood = StatusFoodDAO.getAllListStatus();
                    req.setAttribute("LISTCATEGORIES", listCatetory);
                    req.setAttribute("LISTSTATUSFOOD", listStatusFood);
                }
            }
        } catch (Exception ex) {
            log.error("Error at LoadFoodDetailsFilter: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at LoadFoodDetailsFilter: " + ex.toString(), "Error!!");
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
                log("LoadFoodDetailsFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("LoadFoodDetailsFilter()");
        }
        StringBuffer sb = new StringBuffer("LoadFoodDetailsFilter(");
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
