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
import com.sample.hanashop.dtos.BillDTO;
import com.sample.hanashop.dtos.CategoryDTO;
import com.sample.hanashop.dtos.FoodDTO;
import com.sample.hanashop.dtos.FoodsInBillDTO;
import com.sample.hanashop.dtos.UserDTO;
import com.sample.hanashop.errors.LoginError;
import com.sample.hanashop.mails.SendMailSSL;
import com.sample.hanashop.supports.Support;
import java.io.IOException;
import java.util.ArrayList;
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
public class AddAFoodToCartController extends HttpServlet {

    private final String SUCCESS = "SearchController";
    private final String ERROR = "SearchController";
    private static final Logger log = Logger.getLogger(AddAFoodToCartController.class.getName());

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
            String idFood = request.getParameter("idFood");
            if (userDTO != null) {
                String userID = userDTO.getUserID();
                FoodDTO foodDTO = FoodDAO.getFoodDTO(idFood);
                String strQuantity = request.getParameter("quantity");
                if (strQuantity == null || strQuantity.equals("")) {
                    strQuantity = "1";
                }
                int quantity = Integer.parseInt(strQuantity);
                if (foodDTO.getStatusCode() == 0) {
                    request.setAttribute("QuantityError", foodDTO.getIdFood() + "-" + foodDTO.getNameFood() + " is out of stock!! ");
                    url = (String) session.getAttribute("POSITION");
                } else if (foodDTO.getStatusCode() == 2) {
                    request.setAttribute("QuantityError", foodDTO.getIdFood() + "-" + foodDTO.getNameFood() + " has been stopped selling!!");
                    url = (String) session.getAttribute("POSITION");
                } else if (quantity > foodDTO.getQuantity()) {
                    request.setAttribute("QuantityError", foodDTO.getIdFood() + "-" + foodDTO.getNameFood() + " is out of stock!!  Only " + foodDTO.getQuantity() + " left!!");
                    url = (String) session.getAttribute("POSITION");
                } else {
                    String idBillLastBillNotPayYet = BillDAO.getLastBillIsNotPaid(userID);
                    if (idBillLastBillNotPayYet == null) {
                        int numBill = BillDAO.getNumberOfBillByUserID(userID);
                        numBill++;
                        BillDTO billDTO = BillDAO.createNewBillForUser(userID, numBill);
                        String idBill = billDTO.getIdBill();
                        int statusFoodID = 1;
                        float totalAFood = foodDTO.getPrice() * quantity;
                        FoodsInBillDTO foodInBillDTO = new FoodsInBillDTO(idBill, idFood, quantity, statusFoodID, foodDTO.getPrice(), totalAFood);
                        foodInBillDTO.setNameFood(foodDTO.getNameFood());
                        boolean resultInsertProduct = FoodInBillDAO.insertProductToBill(foodInBillDTO);
                        float subTotalBill = billDTO.getSubTotal() + foodInBillDTO.getTotal();
                        float taxBill = subTotalBill * 10 / 100;
                        billDTO.setTotal(subTotalBill + taxBill);
                        billDTO.setTax(taxBill);
                        boolean resultUpdateBill = BillDAO.updateBillTotal(idBill, subTotalBill, taxBill, billDTO.getTotal());
                        List<FoodsInBillDTO> listFoodInBill = new ArrayList<>();
                        foodInBillDTO.setImgFood(foodDTO.getImgFood());
                        foodInBillDTO.setNameFood(foodDTO.getNameFood());
                        foodInBillDTO.setShortDescription(Support.getShortDescription(foodDTO.getDescription()));
                        CategoryDTO category = CategoryDAO.getCategoryByCategoryID(foodDTO.getCategoryID());
                        String kind = KindDAO.getKindFoodByKindID(category.getKindID());
                        foodInBillDTO.setCategoryName(category.getCategoryName());
                        foodInBillDTO.setKind(kind);
                        listFoodInBill.add(foodInBillDTO);
                        session.setAttribute("LISTFOODINBILL", listFoodInBill);
                        session.setAttribute("BILLDTO", billDTO);
                        session.setAttribute("NUMFOOD", FoodInBillDAO.getNumProductInBill(idBill));
                        url = SUCCESS;
                    } else {
                        BillDTO billDTO = BillDAO.getBillDetailByBillID(idBillLastBillNotPayYet);
                        String idBill = billDTO.getIdBill();
                        //neu food da co trong bill
                        if (FoodInBillDAO.checkContainFood(idBill, idFood)) {
                            FoodsInBillDTO foodInBillDTO = FoodInBillDAO.getFoodInBill(idBill, idFood);
                            int quantity2 = quantity + foodInBillDTO.getQuantity();
                            if (quantity2 > foodDTO.getQuantity()) {
                                request.setAttribute("QuantityError", foodDTO.getIdFood() + "-" + foodDTO.getNameFood() + " is out of stock!! Only " + foodDTO.getQuantity() + " left!!");
                                url = (String) session.getAttribute("POSITION");
                            } else {
                                int statusFoodID = 1;
                                if (foodDTO.getStatusCode() == 0 || foodDTO.getStatusCode() == 2 || foodDTO.getQuantity() < quantity2) {
                                    statusFoodID = 0;
                                }
                                float totalAFood = foodInBillDTO.getPrice() * quantity2;
                                foodInBillDTO.setQuantity(quantity2);
                                foodInBillDTO.setStatusFoodID(statusFoodID);
                                foodInBillDTO.setTotal(totalAFood);
                                FoodInBillDAO.updateFoodInBill(foodInBillDTO);
                                float subTotalBill = billDTO.getSubTotal() + foodInBillDTO.getPrice() * quantity;
                                float taxBill = subTotalBill * 10 / 100;
                                billDTO.setSubTotal(subTotalBill);
                                billDTO.setTax(taxBill);
                                billDTO.setTotal(subTotalBill + taxBill);
                                boolean resultUpdateBill = BillDAO.updateBillTotal(idBill, subTotalBill, taxBill, billDTO.getTotal());
                                List<FoodsInBillDTO> listFoodInBill = FoodInBillDAO.getListFoodInBill(idBill);
                                for (FoodsInBillDTO x : listFoodInBill) {
                                    FoodDTO foodDTO2 = FoodDAO.getFoodDTO(x.getIdFood());
                                    CategoryDTO category = CategoryDAO.getCategoryByCategoryID(foodDTO2.getCategoryID());
                                    String kind = KindDAO.getKindFoodByKindID(category.getKindID());
                                    x.setCategoryName(category.getCategoryName());
                                    x.setKind(kind);
                                    x.setNameFood(foodDTO2.getNameFood());
                                    x.setImgFood(foodDTO2.getImgFood());
                                    x.setShortDescription(Support.getShortDescription(foodDTO2.getDescription()));
                                }
                                session.setAttribute("LISTFOODINBILL", listFoodInBill);
                                session.setAttribute("BILLDTO", billDTO);
                                session.setAttribute("NUMFOOD", FoodInBillDAO.getNumProductInBill(idBill));
                            }
                        }//neu food chua co trong bill
                        else {
                            int statusFoodID = 1;
                            if (foodDTO.getStatusCode() == 0 || foodDTO.getStatusCode() == 2 || foodDTO.getQuantity() < quantity) {
                                statusFoodID = 0;
                            }
                            float total = foodDTO.getPrice() * quantity;
                            FoodsInBillDTO foodInBillDTO = new FoodsInBillDTO(idBill, idFood, quantity, statusFoodID, foodDTO.getPrice(), total);
                            foodInBillDTO.setNameFood(foodDTO.getNameFood());
                            boolean resultInsertProduct = FoodInBillDAO.insertProductToBill(foodInBillDTO);
                            float subTotalBill = billDTO.getSubTotal() + foodInBillDTO.getTotal();
                            float taxBill = subTotalBill * 10 / 100;
                            billDTO.setSubTotal(subTotalBill);
                            billDTO.setTax(taxBill);
                            billDTO.setTotal(subTotalBill + taxBill);
                            boolean resultUpdateBill = BillDAO.updateBillTotal(idBill, subTotalBill, taxBill, billDTO.getTotal());
                            List<FoodsInBillDTO> listFoodInBill = FoodInBillDAO.getListFoodInBill(idBill);
                            for (FoodsInBillDTO x : listFoodInBill) {
                                FoodDTO foodDTO2 = FoodDAO.getFoodDTO(x.getIdFood());
                                CategoryDTO category = CategoryDAO.getCategoryByCategoryID(foodDTO2.getCategoryID());
                                String kind = KindDAO.getKindFoodByKindID(category.getKindID());
                                x.setCategoryName(category.getCategoryName());
                                x.setKind(kind);
                                x.setNameFood(foodDTO2.getNameFood());
                                x.setImgFood(foodDTO2.getImgFood());
                                x.setShortDescription(Support.getShortDescription(foodDTO2.getDescription()));
                            }
                            session.setAttribute("LISTFOODINBILL", listFoodInBill);
                            session.setAttribute("BILLDTO", billDTO);
                            session.setAttribute("NUMFOOD", FoodInBillDAO.getNumProductInBill(idBill));
                        }
                    }
                }
            } else {
                LoginError err = new LoginError();
                err.setErrorNotLoginYet("Please Login First!!");
                request.setAttribute("LOGINERROR", err);
            }
        } catch (Exception ex) {
            log.error("Error at AddAFoodToCartController: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at AddAFoodToCartController: " + ex.toString(), "Error!!");
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
