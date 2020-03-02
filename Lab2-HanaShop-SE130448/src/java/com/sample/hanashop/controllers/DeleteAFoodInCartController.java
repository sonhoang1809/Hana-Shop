/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.controllers;

import com.sample.hanashop.daos.BillDAO;
import com.sample.hanashop.daos.FoodInBillDAO;
import com.sample.hanashop.dtos.BillDTO;
import com.sample.hanashop.dtos.FoodsInBillDTO;
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
public class DeleteAFoodInCartController extends HttpServlet {

    private static final Logger log = Logger.getLogger(DeleteAFoodInCartController.class.getName());
    private final String SUCCESS = "LoadShoppingCartController";
    private final String ERROR = "LoadShoppingCartController";

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
            String idFoodDelete = request.getParameter("idFoodDelete");
            String idBill = request.getParameter("idBill");
            HttpSession session = request.getSession();
            BillDTO billDTO = BillDAO.getBillDetailByBillID(idBill);
            boolean resultRemove = FoodInBillDAO.removeAFoodInBill(billDTO.getIdBill(), idFoodDelete);
            float subTotal = FoodInBillDAO.getTotalInBill(billDTO.getIdBill());
            float tax = subTotal * 10 / 100;
            float total = subTotal + tax;
            billDTO.setSubTotal(subTotal);
            billDTO.setTax(tax);
            billDTO.setTotal(total);
            boolean updateBill = BillDAO.updateBillTotal(idBill, subTotal, tax, total);
            List<FoodsInBillDTO> listFoodInBill = FoodInBillDAO.getListFoodInBill(idBill);
            session.setAttribute("LISTFOODINBILL", listFoodInBill);
            session.setAttribute("BILLDTO", billDTO);
            request.setAttribute("DeleteMessage", "Delete food in your cart success!!");
            url = SUCCESS;
        } catch (Exception ex) {
            log.error("Error at DeleteAFoodInCartController: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at DeleteAFoodInCartController: " + ex.toString(), "Error!!");
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
