/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.controllers;

import com.sample.hanashop.mails.SendMailSSL;
import com.sample.hanashop.paypals.authorizeintent.CreateOrder;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import com.paypal.http.HttpResponse;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.sample.hanashop.dtos.BillDTO;
import com.sample.hanashop.dtos.FoodsInBillDTO;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sonho
 */
public class CreatePayPalOrderController extends HttpServlet {

    private final String ERROR = "LoadShoppingCartController";
//    private final String SUCCESS = "PayByPaypalController";
    private static final Logger log = Logger.getLogger(CreatePayPalOrderController.class.getName());

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
            List<FoodsInBillDTO> listFoodInBill = (List<FoodsInBillDTO>) session.getAttribute("LISTFOODINBILL");
            String firstName = request.getParameter("firstname");
            String lastName = request.getParameter("lastname");
            String fullName = lastName + " " + firstName;
            String address = request.getParameter("address");
            String street = request.getParameter("street");
            String city = request.getParameter("city");
            String subTotal = billDTO.getSubTotal() + "";
            String tax = billDTO.getTax() + "";
            String total = billDTO.getTotal() + "";
            // Creating an order
            HttpResponse<Order> orderResponse = new CreateOrder().createOrderWithFullPayLoad(true,
                    subTotal, tax, total, listFoodInBill, fullName, address, street, city);
            String orderId = "";
            if (orderResponse.statusCode() == 201) {
                orderId = orderResponse.result().id();
                if (orderResponse.result().links() != null) {
                    for (LinkDescription link : orderResponse.result().links()) {
                        if (link.rel().equalsIgnoreCase("approve")) {
                            url = link.href();
                            break;
                        }
                    }
                } else {
                    url = "https://www.sandbox.paypal.com/checkoutnow?token=" + orderId;
                }
                session.setAttribute("orderId", orderId);
            } else {
                request.setAttribute("ErrorPayPal", "Error to Pay with PayPal");
            }
        } catch (Exception ex) {
            log.error("Error at CreatePayPalOrderController: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at CreatePayPalOrderController: " + ex.toString(), "Error!!");
            request.setAttribute("ErrorPaypal", "Error to create order to Paypal!! Please try again after !!");
        } finally {
            response.sendRedirect(url);
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
