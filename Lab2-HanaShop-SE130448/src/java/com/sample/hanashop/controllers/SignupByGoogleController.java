/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.controllers;

import com.sample.hanashop.daos.UserDAO;
import com.sample.hanashop.dtos.UserDTO;
import com.sample.hanashop.errors.SignupError;
import com.sample.hanashop.mails.SendMailSSL;
import com.sample.hanashop.supports.Support;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author sonho
 */
public class SignupByGoogleController extends HttpServlet {

    private final String SUCCESS = "LoginByGoogleController";
    private final String ERROR = "SearchController";
    private static final Logger log = Logger.getLogger(SignupByGoogleController.class.getName());

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
            String email = request.getParameter("email");
            String userName = request.getParameter("name");
            String userID = request.getParameter("ID");
            String password = Support.encryptedPassword(userID);
            SignupError suError = new SignupError();
            boolean valid = true;
            if (UserDAO.checkExistUserID(userID)) {
                suError.setErrorUserID("This userID is already exist!!");
                valid = false;
            }
            if (UserDAO.checkExistEmail(email)) {
                suError.setErrorEmail("This email is already exist!!");
                valid = false;
            }
            if (valid) {
                String avatar = request.getParameter("avatar");
                String roleID = "US";
                UserDTO dto = new UserDTO(userID, email, avatar, userName, password, roleID);
                UserDAO.insertAUser(dto);
                url = SUCCESS;
            } else {
                request.setAttribute("SIGNUPERROR", suError);
            }
        } catch (Exception ex) {
            log.error("Error at SignupByGoogleController: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at SignupByGoogleController: " + ex.toString(), "Error!!");
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
