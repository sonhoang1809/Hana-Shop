/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.controllers;

import com.sample.hanashop.daos.CategoryDAO;
import com.sample.hanashop.daos.FoodDAO;
import com.sample.hanashop.daos.KindDAO;
import com.sample.hanashop.dtos.CategoryDTO;
import com.sample.hanashop.dtos.FoodDTO;
import com.sample.hanashop.dtos.UserDTO;
import com.sample.hanashop.mails.SendMailSSL;
import com.sample.hanashop.supports.Support;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sonho
 */
public class SearchController extends HttpServlet {

    private final String SUCCESS = "index.jsp";
    private static final Logger log = Logger.getLogger(SearchController.class.getName());

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = SUCCESS;
        try {
            String kindID = request.getParameter("kindID");
            String categoryID = request.getParameter("categoryID");
            String strPriceLower = request.getParameter("priceLower");
            if (strPriceLower == null || strPriceLower.equalsIgnoreCase("")) {
                strPriceLower = "100";
            }
            String strPriceHigher = request.getParameter("priceHigher");
            if (strPriceHigher == null || strPriceHigher.equalsIgnoreCase("")) {
                strPriceHigher = "1";
            }
            float priceLower = Float.parseFloat(strPriceLower);
            float priceHigher = Float.parseFloat(strPriceHigher);
            int numOfFood = 0;
            int numOfPage = 0;
            int statusCode = -1;
            HttpSession session = request.getSession();
            UserDTO userDTO = (UserDTO) session.getAttribute("USERDTO");
            if (userDTO != null) {
                if (userDTO.getRoleID().equalsIgnoreCase("AD")) {
                    String sttCode = request.getParameter("statusCode");
                    if (sttCode != null && !sttCode.equalsIgnoreCase("")) {
                        try {
                            statusCode = Integer.parseInt(sttCode);
                        } catch (NumberFormatException ex) {
                            statusCode = 1;
                        }
                    }
                }
            }
            if (statusCode == -1) {
                statusCode = 1;
            }
            List<CategoryDTO> listCategories = null;
            if (userDTO == null || userDTO.getRoleID().equalsIgnoreCase("US")) {
                if (kindID != null) {
                    listCategories = CategoryDAO.getListCategoryByKindID(kindID);
                    request.setAttribute("LISTCATEGORIES", listCategories);
                }
            }
            String searchName = request.getParameter("searchName");
            List<FoodDTO> listFood = null;
            int pageShow = 0;
            String strPageShow = request.getParameter("pageShow");
            if (strPageShow == null) {
                strPageShow = "1";
            }
            pageShow = Integer.parseInt(strPageShow);
            //000
            if ((kindID == null || kindID.equals("")) && (categoryID == null || categoryID.equals("")) && (searchName == null || searchName.equals(""))) {
                numOfFood = FoodDAO.getNumberFood(priceLower, priceHigher, statusCode);
                if (numOfFood > 0) {
                    numOfPage = Support.calculateNumPage(numOfFood);
                }
                listFood = FoodDAO.getListFood(priceLower, priceHigher, statusCode, pageShow);
            }//001
            else if ((kindID == null || kindID.equals("")) && (categoryID == null || categoryID.equals("")) && (searchName != null || !searchName.equals(""))) {
                numOfFood = FoodDAO.getNumberFood(priceLower, priceHigher, statusCode, searchName);
                if (numOfFood > 0) {
                    numOfPage = Support.calculateNumPage(numOfFood);
                }
                listFood = FoodDAO.getListFood(priceLower, priceHigher, statusCode, pageShow, searchName);
            }//010
            else if ((kindID == null || kindID.equals("")) && (categoryID != null || !categoryID.equals("")) && (searchName == null || searchName.equals(""))) {
                if (userDTO == null || userDTO.getRoleID().equalsIgnoreCase("US")) {
                    categoryID = null;
                    //000
                    numOfFood = FoodDAO.getNumberFood(priceLower, priceHigher, statusCode);
                    if (numOfFood > 0) {
                        numOfPage = Support.calculateNumPage(numOfFood);
                    }
                    listFood = FoodDAO.getListFood(priceLower, priceHigher, statusCode, pageShow);
                } else if (userDTO.getRoleID().equalsIgnoreCase("AD")) {
                    numOfFood = FoodDAO.getNumberFood(priceLower, priceHigher, categoryID, statusCode);
                    if (numOfFood > 0) {
                        numOfPage = Support.calculateNumPage(numOfFood);
                    }
                    listFood = FoodDAO.getListFood(priceLower, priceHigher, categoryID, statusCode, pageShow);
                }
            }//011
            else if ((kindID == null || kindID.equals("")) && (categoryID != null || !categoryID.equals("")) && (searchName != null || !searchName.equals(""))) {
                if (userDTO == null || userDTO.getRoleID().equalsIgnoreCase("US")) {
                    categoryID = null;
                    //001
                    numOfFood = FoodDAO.getNumberFood(priceLower, priceHigher, statusCode, searchName);
                    if (numOfFood > 0) {
                        numOfPage = Support.calculateNumPage(numOfFood);
                    }
                    listFood = FoodDAO.getListFood(priceLower, priceHigher, statusCode, pageShow, searchName);
                } else if (userDTO.getRoleID().equalsIgnoreCase("AD")) {
                    numOfFood = FoodDAO.getNumberFood(priceLower, priceHigher, statusCode, categoryID, searchName);
                    if (numOfFood > 0) {
                        numOfPage = Support.calculateNumPage(numOfFood);
                    }
                    listFood = FoodDAO.getListFood(priceLower, priceHigher, statusCode, pageShow, categoryID, searchName);
                }
            } //100
            else if ((kindID != null || !kindID.equals("")) && (categoryID == null || categoryID.equals("")) && (searchName == null || searchName.equals(""))) {
                if (listCategories != null) {
                    numOfFood = FoodDAO.getNumberFood(priceLower, priceHigher, statusCode, listCategories);
                }
                if (numOfFood > 0) {
                    numOfPage = Support.calculateNumPage(numOfFood);
                }
                listFood = FoodDAO.getListFood(priceLower, priceHigher, statusCode, pageShow, listCategories);
            }//101
            else if ((kindID != null || !kindID.equals("")) && (categoryID == null || categoryID.equals("")) && (searchName != null || !searchName.equals(""))) {
                if (listCategories != null) {
                    numOfFood = FoodDAO.getNumberFood(priceLower, priceHigher, statusCode, listCategories, searchName);
                }
                if (numOfFood > 0) {
                    numOfPage = Support.calculateNumPage(numOfFood);
                }
                if (listCategories != null) {
                    listFood = FoodDAO.getListFood(priceLower, priceHigher, statusCode, pageShow, listCategories, searchName);
                }
            }//110
            else if ((kindID != null || !kindID.equals("")) && (categoryID != null || !categoryID.equals("")) && (searchName == null || searchName.equals(""))) {
                CategoryDTO ctDTO = CategoryDAO.getCategoryByCategoryID(categoryID);
                if (ctDTO.getKindID().equalsIgnoreCase(kindID)) {
                    numOfFood = FoodDAO.getNumberFood(priceLower, priceHigher, categoryID, statusCode);
                    if (numOfFood > 0) {
                        numOfPage = Support.calculateNumPage(numOfFood);
                    }
                    listFood = FoodDAO.getListFood(priceLower, priceHigher, categoryID, statusCode, pageShow);
                } else {//100
                    if (listCategories != null) {
                        numOfFood = FoodDAO.getNumberFood(priceLower, priceHigher, statusCode, listCategories);
                    }
                    if (numOfFood > 0) {
                        numOfPage = Support.calculateNumPage(numOfFood);
                    }
                    listFood = FoodDAO.getListFood(priceLower, priceHigher, statusCode, pageShow, listCategories);
                }
            }//111
            else if ((kindID != null || !kindID.equals("")) && (categoryID != null || !categoryID.equals("")) && (searchName != null || !searchName.equals(""))) {
                CategoryDTO ctDTO = CategoryDAO.getCategoryByCategoryID(categoryID);
                if (ctDTO.getKindID().equalsIgnoreCase(kindID)) {
                    numOfFood = FoodDAO.getNumberFood(priceLower, priceHigher, statusCode, categoryID, searchName);
                    if (numOfFood > 0) {
                        numOfPage = Support.calculateNumPage(numOfFood);
                    }
                    listFood = FoodDAO.getListFood(priceLower, priceHigher, statusCode, pageShow, categoryID, searchName);
                } else {//101
                    if (listCategories != null) {
                        numOfFood = FoodDAO.getNumberFood(priceLower, priceHigher, statusCode, listCategories, searchName);
                    }
                    if (numOfFood > 0) {
                        numOfPage = Support.calculateNumPage(numOfFood);
                    }
                    if (listCategories != null) {
                        listFood = FoodDAO.getListFood(priceLower, priceHigher, statusCode, pageShow, listCategories, searchName);
                    }
                }
            }
            if (listFood == null) {
                request.setAttribute("SEARCHNULL", "Result not found!!!");
            } else {
                for (FoodDTO x : listFood) {
                    CategoryDTO category = CategoryDAO.getCategoryByCategoryID(x.getCategoryID());
                    String kind = KindDAO.getKindFoodByKindID(category.getKindID());
                    x.setCategoryName(category.getCategoryName());
                    x.setKind(kind);
                    x.setShortDescription(Support.getShortDescription(x.getDescription()));
                }
            }

            request.setAttribute("NUMBEROFPAGE", numOfPage);
            request.setAttribute("PAGESHOW", pageShow);
            request.setAttribute("LISTFOOD", listFood);
            session.setAttribute("POSITION", "SearchController");
        } catch (Exception ex) {
            log.error("Error at SearchController: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at SearchController: " + ex.toString(), "Error!!");
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
