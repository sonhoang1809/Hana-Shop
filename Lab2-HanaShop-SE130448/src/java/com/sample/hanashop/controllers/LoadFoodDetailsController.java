/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.controllers;

import com.sample.hanashop.daos.CategoryDAO;
import com.sample.hanashop.daos.FoodDAO;
import com.sample.hanashop.daos.KindDAO;
import com.sample.hanashop.daos.StatusFoodDAO;
import com.sample.hanashop.dtos.CategoryDTO;
import com.sample.hanashop.dtos.FoodDTO;
import com.sample.hanashop.dtos.UserDTO;
import com.sample.hanashop.mails.SendMailSSL;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author sonho
 */

public class LoadFoodDetailsController extends HttpServlet {

    private final String SUCCESS = "food-details.jsp";
    private static final Logger log = Logger.getLogger(LoadFoodDetailsController.class.getName());

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
            HttpSession session = request.getSession();
            UserDTO userDTO = (UserDTO) session.getAttribute("USERDTO");
            String idFood = request.getParameter("idFood");
            FoodDTO foodDTO = null;
            if (userDTO == null) {
                foodDTO = FoodDAO.getFoodDTO(idFood);
            } else if (userDTO != null) {
                if (userDTO.getRoleID().equalsIgnoreCase("US")) {
                    foodDTO = FoodDAO.getFoodDTO(idFood);
                } else if (userDTO.getRoleID().equalsIgnoreCase("AD")) {
                    foodDTO = FoodDAO.getFullDetailsFoodDTO(idFood);
                }
            }
            CategoryDTO cateDTO = CategoryDAO.getCategoryByCategoryID(foodDTO.getCategoryID());
            foodDTO.setCategoryName(cateDTO.getCategoryName());
            foodDTO.setKind(KindDAO.getKindFoodByKindID(cateDTO.getKindID()));
            foodDTO.setStatus(StatusFoodDAO.getFoodDTO(foodDTO.getStatusCode()).getStatus());

//            //check contains at shopping cart
//            List<FoodsInBillDTO> listFoodInBill = (List<FoodsInBillDTO>) session.getAttribute("LISTFOODINBILL");
//            if (listFoodInBill != null) {
//                for (FoodsInBillDTO x : listFoodInBill) {
//                    if (x.getIdFood().equalsIgnoreCase(idFood)) {
//                        int maxQuantityAtPresent = foodDTO.getQuantity() - x.getQuantity();
//                        foodDTO.setMaxQuantity(maxQuantityAtPresent);
//                        x.setMaxQuantity(maxQuantityAtPresent);
//                        break;
//                    } else {
//                        foodDTO.setMaxQuantity(foodDTO.getQuantity());
//                    }
//                }
//            } else {
//                foodDTO.setMaxQuantity(foodDTO.getQuantity());
//            }
            request.setAttribute("FOODDTO", foodDTO);
//            //load food reccommendation

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

            session.setAttribute("POSITION", "LoadFoodDetailsController");
            session.setAttribute("idFood", idFood);
        } catch (Exception ex) {
            log.error("Error at LoadFoodDetailsController: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at LoadFoodDetailsController: " + ex.toString(), "Error!!");
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
