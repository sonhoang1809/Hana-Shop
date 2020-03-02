/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.controllers;

import com.sample.hanashop.daos.BillDAO;
import com.sample.hanashop.daos.CategoryDAO;
import com.sample.hanashop.daos.FoodDAO;
import com.sample.hanashop.daos.FoodInBillDAO;
import com.sample.hanashop.daos.KindDAO;
import com.sample.hanashop.daos.UserDAO;
import com.sample.hanashop.dtos.BillDTO;
import com.sample.hanashop.dtos.CategoryDTO;
import com.sample.hanashop.dtos.FoodDTO;
import com.sample.hanashop.dtos.FoodsInBillDTO;
import com.sample.hanashop.dtos.UserDTO;
import com.sample.hanashop.errors.LoginError;
import com.sample.hanashop.mails.SendMailSSL;
import com.sample.hanashop.supports.Support;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
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
public class LoginController extends HttpServlet {

    private final String SUCCESS = "SearchController";
    private final String ERROR = "SearchController";
    private static final Logger log = Logger.getLogger(LoginController.class.getName());

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
            String userID = request.getParameter("userID");
            String password = Support.encryptedPassword(request.getParameter("password"));
            UserDTO userDTO = UserDAO.checkLogin(userID, password);
            LoginError err = new LoginError();
            if (userDTO != null) {
                HttpSession session = request.getSession();
                session.setAttribute("USERDTO", userDTO);
                String lastPosition = (String) session.getAttribute("POSITION");
                if (lastPosition != null) {
                    url = lastPosition;
                } else {
                    url = SUCCESS;
                }
                String idBillLastBillNotPayYet = BillDAO.getLastBillIsNotPaid(userID);
                if (idBillLastBillNotPayYet != null) {
                    List<FoodsInBillDTO> listFood = FoodInBillDAO.getListFoodInBill(idBillLastBillNotPayYet);
                    if (listFood != null) {
                        boolean resultUpdateFoodInBill;
                        for (FoodsInBillDTO x : listFood) {
                            FoodDTO foodDTO = FoodDAO.getFoodDTO(x.getIdFood());
                            if (x.getQuantity() > foodDTO.getQuantity() || foodDTO.getStatusCode() == 0 || foodDTO.getStatusCode() == 2) {
                                x.setStatusFoodID(0);
                            }
                            CategoryDTO category = CategoryDAO.getCategoryByCategoryID(foodDTO.getCategoryID());
                            String kind = KindDAO.getKindFoodByKindID(category.getKindID());
                            x.setCategoryName(category.getCategoryName());
                            x.setKind(kind);
                            x.setNameFood(foodDTO.getNameFood());
                            x.setImgFood(foodDTO.getImgFood());
                            x.setShortDescription(Support.getShortDescription(foodDTO.getDescription()));
                            resultUpdateFoodInBill = FoodInBillDAO.updateFoodInBill(x);
                        }

                        BillDTO billDTO = BillDAO.getBillDetailByBillID(idBillLastBillNotPayYet);
                        float subTotal = FoodInBillDAO.getTotalInBill(idBillLastBillNotPayYet);
                        float tax = subTotal * 10 / 100;
                        float total = subTotal + tax;
                        billDTO.setSubTotal(subTotal);
                        billDTO.setTax(tax);
                        billDTO.setTotal(total);
                        session.setAttribute("BILLDTO", billDTO);
                        session.setAttribute("NUMFOOD", FoodInBillDAO.getNumProductInBill(idBillLastBillNotPayYet));
                        session.setAttribute("LISTFOODINBILL", listFood);
                        request.setAttribute("MessageWelcome", "Welcome " + userDTO.getName() + " back to Hana Shop !! Let continue shopping !!");
                    }
                } else {
                    request.setAttribute("MessageWelcome", "Welcome " + userDTO.getName() + " to Hana Shop !!");
                }
            } else {
                if (!UserDAO.checkExistUserID(userID)) {
                    err.setErrorExistUserID("This UserID is not exists!!");
                } else {
                    err.setErrorPassword("Error Password!!");
                }
                request.setAttribute("LOGINERROR", err);
            }

        } catch (NoSuchAlgorithmException | SQLException | NamingException ex) {
            log.error("Error at LoginController: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at LoginController: " + ex.toString(), "Error!!");
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
