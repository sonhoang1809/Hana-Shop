/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.controllers;

import com.sample.hanashop.daos.CategoryDAO;
import com.sample.hanashop.daos.FoodDAO;
import com.sample.hanashop.daos.FoodInBillDAO;
import com.sample.hanashop.dtos.BillDTO;
import com.sample.hanashop.dtos.CategoryDTO;
import com.sample.hanashop.dtos.FoodDTO;
import com.sample.hanashop.dtos.FoodsInBillDTO;
import com.sample.hanashop.dtos.UserDTO;
import com.sample.hanashop.errors.LoginError;
import com.sample.hanashop.mails.SendMailSSL;
import com.sample.hanashop.supports.Support;
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
public class LoadShoppingCartController extends HttpServlet {

    private static final Logger log = Logger.getLogger(LoadShoppingCartController.class.getName());

    private final String SUCCESS = "shopping-cart.jsp";
    private final String ERROR = "SearchController";
    private final String NOTLOGINYET = "SearchController";

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
            UserDTO userDTO = (UserDTO) session.getAttribute("USERDTO");
            if (userDTO != null) {
                BillDTO billDTO = (BillDTO) session.getAttribute("BILLDTO");
                if (billDTO != null) {
                    List<FoodsInBillDTO> listFood = (List<FoodsInBillDTO>) session.getAttribute("LISTFOODINBILL");
                    if (listFood != null) {
                        boolean resultUpdateFoodInBill;
                        for (FoodsInBillDTO x : listFood) {
                            FoodDTO foodDTO = FoodDAO.getFoodDTO(x.getIdFood());
                            x.setMaxQuantity(foodDTO.getQuantity());
                            if (x.getQuantity() > foodDTO.getQuantity()) {
                                x.setStatusFoodID(0);
                            } else {
                                x.setStatusFoodID(1);
                            }
                            if (foodDTO.getStatusCode() == 2 || foodDTO.getStatusCode() == 0) {
                                x.setStatusFoodID(0);
                            }
                            if (x.getPrice() != foodDTO.getPrice()) {
                                x.setPrice(foodDTO.getPrice());
                                float totalNew = foodDTO.getPrice() * x.getQuantity() * 100 / 100;
                                x.setTotal(totalNew);
                            }
                            x.setNameFood(foodDTO.getNameFood());
                            x.setImgFood(foodDTO.getImgFood());
                            x.setShortDescription(Support.getShortDescription(foodDTO.getDescription()));
                            CategoryDTO cateDTO = CategoryDAO.getCategoryByCategoryID(foodDTO.getCategoryID());
                            x.setCategoryName(cateDTO.getCategoryName());
                            resultUpdateFoodInBill = FoodInBillDAO.updateFoodInBill(x);
                        }
                    }
                    int numFood = FoodInBillDAO.getNumProductInBill(billDTO.getIdBill());
                    session.setAttribute("NUMFOOD", numFood);
                    session.setAttribute("LISTFOODINBILL", listFood);
                }
                url = SUCCESS;
            } else {
                url = NOTLOGINYET;
                LoginError loginError = new LoginError();
                loginError.setErrorNotLoginYet("Please Login First !!");
                request.setAttribute("LOGINERROR", loginError);
            }
        } catch (Exception ex) {
            log.error("Error at LoadShoppingCartController: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at LoadShoppingCartController: " + ex.toString(), "Error!!");
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
