/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.controllers;

import com.sample.hanashop.daos.BillDAO;
import com.sample.hanashop.daos.FoodDAO;
import com.sample.hanashop.daos.FoodInBillDAO;
import com.sample.hanashop.daos.UserDAO;
import com.sample.hanashop.dtos.BillDTO;
import com.sample.hanashop.dtos.FoodDTO;
import com.sample.hanashop.dtos.FoodsInBillDTO;
import com.sample.hanashop.dtos.UserDTO;
import com.sample.hanashop.mails.SendMailSSL;
import java.io.IOException;
import java.util.List;
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
public class FinishOrderController extends HttpServlet {

    private final String SUCCESS = "SearchController";
    private final String ERROR = "LoadShoppingCartController";
    private static final Logger log = Logger.getLogger(FinishOrderController.class.getName());

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
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            BillDTO billDTO = (BillDTO) session.getAttribute("BILLDTO");
            UserDTO userDTO = UserDAO.getFullUserDetail(billDTO.getUserID());
            List<FoodsInBillDTO> listFoodInBill = FoodInBillDAO.getListFoodInBill(billDTO.getIdBill());
            boolean resultUpdateQuantity;
            //update tblFoods
            for (FoodsInBillDTO x : listFoodInBill) {
                FoodDTO foodDTO = FoodDAO.getFoodDTO(x.getIdFood());
                int newQuantity = foodDTO.getQuantity() - x.getQuantity();
                foodDTO.setQuantity(newQuantity);
                if (newQuantity == 0) {
                    foodDTO.setStatusCode(0);
                }
                resultUpdateQuantity = FoodDAO.updateFood(foodDTO);
            }
            //update bill
            billDTO.setStatusBillCode(1);
            boolean updateBill = BillDAO.updateBillDetail(billDTO);
            String idBill = billDTO.getIdBill();
            String content = null;
            if (request.getAttribute("captureId") != null) {
                content = "Dear " + userDTO.getName() + ", \n"
                        + "Thank for your shopping !\n"
                        + "We have received $" + billDTO.getTotal() + " that you submitted at " + billDTO.getDate()
                        + ". The payment has been authorized and approved.\n"
                        + "Your order is on the way shipping to you.\n"
                        + "Have a good day.\n"
                        + "From Hana Shop.";
            } else {
                content = "Dear " + userDTO.getName() + ", \n"
                        + "Your bill total is: " + billDTO.getTotal()
                        + "Your bill is ordered success!!\n"
                        + "Thank for your shopping.";
            }
            SendMailSSL.sendMessageToUser("ORDER SUCCESS !!", content, userDTO.getEmail());
            session.removeAttribute("BILLDTO");
            session.removeAttribute("NUMFOOD");
            session.removeAttribute("LISTFOODINBILL");
            request.setAttribute("MESSAGEPAYMENT", "Your bill(bill ID:" + idBill + ") is ordered!! "
                    + "On the way shipping to you !!");
            request.setAttribute("idBill", idBill);
            url = SUCCESS;
        } catch (Exception ex) {
            log.error("Error at FinishOrderController: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at FinishOrderController: " + ex.toString(), "Error!!");
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
