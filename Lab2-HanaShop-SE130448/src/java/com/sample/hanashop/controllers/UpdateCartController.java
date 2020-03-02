/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.controllers;

import com.sample.hanashop.daos.BillDAO;
import com.sample.hanashop.daos.FoodDAO;
import com.sample.hanashop.daos.FoodInBillDAO;
import com.sample.hanashop.dtos.BillDTO;
import com.sample.hanashop.dtos.FoodDTO;
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
public class UpdateCartController extends HttpServlet {

    private final String SUCCESS = "LoadShoppingCartController";
    private final String ERROR = "LoadShoppingCartController";

    private static final Logger log = Logger.getLogger(UpdateCartController.class.getName());

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
            String[] idFood_Quantitys = request.getParameterValues("idFood-quantity");
            String idBill = request.getParameter("idBill");
            HttpSession session = request.getSession();
            boolean quantityErr = true;
            for (String x : idFood_Quantitys) {
                String idFood = x.split("-")[0];
                String strQuantity = x.split("-")[1];
                int quantityFoodInBill = Integer.parseInt(strQuantity);
                FoodDTO foodDTO = FoodDAO.getFoodDTO(idFood);
                if (foodDTO.getStatusCode() == 2) {
                    quantityErr = false;
                    request.setAttribute("QuantityError", foodDTO.getIdFood() + "-" + foodDTO.getNameFood() + " is stop selling! cannot update!!");
                }
                if (quantityErr) {
                    //int currQuantityOfFood = foodDTO.getQuantity();
                    boolean updateFood;
                    float totalAFood = quantityFoodInBill * foodDTO.getPrice();
                    FoodsInBillDTO foodInBill = FoodInBillDAO.getFoodInBill(idBill, idFood);
                    foodInBill.setQuantity(quantityFoodInBill);
                    foodInBill.setTotal(totalAFood);
                    updateFood = FoodInBillDAO.updateFoodInBill(foodInBill);
                }
            }
            List<FoodsInBillDTO> listFoodInBill = FoodInBillDAO.getListFoodInBill(idBill);
            BillDTO billDTO = BillDAO.getBillDetailByBillID(idBill);
            float subTotal = FoodInBillDAO.getTotalInBill(idBill);
            float tax = subTotal * 10 / 100;
            float total = subTotal + tax;
            billDTO.setSubTotal(subTotal);
            billDTO.setTax(tax);
            billDTO.setTotal(total);
            boolean updateBill = BillDAO.updateBillTotal(idBill, subTotal, tax, total);
            session.setAttribute("LISTFOODINBILL", listFoodInBill);
            session.setAttribute("BILLDTO", billDTO);
            if (updateBill) {
                request.setAttribute("MessageUpdate", "Update your bill successfull!!");
                url = SUCCESS;
            }
        } catch (Exception ex) {
            log.error("Error at UpdateCartController: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at UpdateCartController: " + ex.toString(), "Error!!");
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
