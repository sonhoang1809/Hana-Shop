/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.controllers;

import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;
import com.paypal.payments.Capture;
import com.sample.hanashop.mails.SendMailSSL;
import com.sample.hanashop.paypals.authorizeintent.AuthorizeOrder;
import com.sample.hanashop.paypals.authorizeintent.CaptureOrder;
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
public class PayByPaypalController extends HttpServlet {

    private final String SUCCESS = "FinishOrderController";
    private final String ERROR = "LoadShoppingCartController";
    private static final Logger log = Logger.getLogger(PayByPaypalController.class.getName());

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
            String orderId = (String) session.getAttribute("orderId");
            HttpResponse<Order> orderResponse = new AuthorizeOrder().authorizeOrder(orderId, true);
            String authId = "";
            if (orderResponse.statusCode() == 201) {
                authId = orderResponse.result().purchaseUnits().get(0).payments().authorizations().get(0).id();
                if (orderResponse.statusCode() == 201) {
                    HttpResponse<Capture> captureOrderResponse = new CaptureOrder().captureOrder(authId, true);
                    if (captureOrderResponse.statusCode() == 201) {
                        url = SUCCESS;
                        
                    } else {
                        request.setAttribute("ErrorPaypal", "Error to order by Paypal!! Please try again later!!");
                    }
                    request.setAttribute("captureId", captureOrderResponse.result().id());
                }
            }
        } catch (Exception ex) {
            log.error("Error at PayByPaypalController: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at PayByPaypalController: " + ex.toString(), "Error!!");
            request.setAttribute("ErrorPaypal", "Error to order by Paypal!! Please try again later!!");
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
