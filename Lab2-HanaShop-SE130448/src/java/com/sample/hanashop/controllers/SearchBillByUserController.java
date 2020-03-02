/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.controllers;

import com.sample.hanashop.daos.BillDAO;
import com.sample.hanashop.daos.FoodInBillDAO;
import com.sample.hanashop.dtos.BillDTO;
import com.sample.hanashop.dtos.UserDTO;
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
public class SearchBillByUserController extends HttpServlet {

    private final String ERROR = "SearchController";
    private final String SUCCESS = "user-bills.jsp";
    private static final Logger log = Logger.getLogger(SearchBillByUserController.class.getName());

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
            String userID = null;
            if (userDTO.getRoleID().equalsIgnoreCase("US")) {
                userID = userDTO.getUserID();
            } else if (userDTO.getRoleID().equalsIgnoreCase("AD")) {
                userID = request.getParameter("userID");
            }
            String date = request.getParameter("date");
            if (date != null && !date.equals("")) {
                date = Support.convertDate(date);
                String time = request.getParameter("time");
                if (time != null && !time.equals("")) {
                    time = Support.convertTime(time);
                    date = date + " " + time;
                }
            }
            String nameFood = request.getParameter("nameFood");
            List<BillDTO> listBillByUserID = null;
            //00
            if ((date == null || date.equals("")) && (nameFood == null || nameFood.equals(""))) {
                listBillByUserID = BillDAO.getListBillByUserID(userID);
                if (listBillByUserID == null || listBillByUserID.isEmpty()) {
                    request.setAttribute("SEARCHNULL", "You have no bills !!!");
                }
            } //01
            else if ((date == null || date.equals("")) && (nameFood != null || !nameFood.equals(""))) {
                //lay cac bill cua user
                List<String> listBillIdOfUser = BillDAO.getListIdBillByUserID(userID);
                if (listBillIdOfUser != null) {
                    //roi dem no qua tblFoodsInBill de so cai name voi cai list IdBill do de lay ra listBillId tuong ung
                    List<String> listIdBillInOrderContainNameFood = FoodInBillDAO.getListIdBillInOrderContainNameFood(listBillIdOfUser, nameFood);
                    //lay list bill details
                    if (listIdBillInOrderContainNameFood != null) {
                        listBillByUserID = BillDAO.getListBillUserContainNameFood(userID, listIdBillInOrderContainNameFood);
                    }
                }
                if (listBillByUserID == null || listBillByUserID.isEmpty()) {
                    request.setAttribute("SEARCHNULL", "Result not Found !!!");
                }
            }//10
            else if ((date != null || !date.equals("")) && (nameFood == null || nameFood.equals(""))) {
                listBillByUserID = BillDAO.getListBillByUserID(userID, date);
                if (listBillByUserID == null || listBillByUserID.isEmpty()) {
                    request.setAttribute("SEARCHNULL", "Result not Found !!!");
                }
            }//11
            else if ((date != null || !date.equals("")) && (nameFood != null || !nameFood.equals(""))) {
                //lay cac bill cua user co date do
                List<String> listBillIdOfUserHasDate = BillDAO.getListIdBillByUserIDHasDate(userID, date);
                if (listBillIdOfUserHasDate != null) {
                    //roi dem no qua tblFoodsInBill de so cai name voi cai list IdBill do de lay ra listBillId tuong ung
                    List<String> listIdBillInOrderContainNameFood = FoodInBillDAO.getListIdBillInOrderContainNameFood(listBillIdOfUserHasDate, nameFood);
                    //lay list bill details
                    if (listIdBillInOrderContainNameFood != null) {
                        listBillByUserID = BillDAO.getListBillUserContainNameFoodAndDate(userID, listIdBillInOrderContainNameFood, date);
                    }
                }
                if (listBillByUserID == null || listBillByUserID.isEmpty()) {
                    request.setAttribute("SEARCHNULL", "Result not Found !!!");
                }
            }
            request.setAttribute("LISTBILLUSER", listBillByUserID);
            url = SUCCESS;
        } catch (Exception ex) {
            log.error("Error at SearchBillByUserController: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at SearchBillByUserController: " + ex.toString(), "Error!!");
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
