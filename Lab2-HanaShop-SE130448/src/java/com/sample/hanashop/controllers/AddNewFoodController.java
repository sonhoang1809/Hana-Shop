/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.controllers;

import com.sample.hanashop.daos.CategoryDAO;
import com.sample.hanashop.daos.FoodDAO;
import com.sample.hanashop.daos.KindDAO;
import com.sample.hanashop.dtos.CategoryDTO;
import com.sample.hanashop.dtos.FoodDTO;
import com.sample.hanashop.mails.SendMailSSL;
import com.sample.hanashop.supports.Support;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

/**
 *
 * @author sonho
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class AddNewFoodController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final String SUCCESS = "add-food.jsp";
    private final String ERROR = "add-food.jsp";
    private final String SAVE_DIRECTORY = "assets/images/Food";
    private static final Logger log = Logger.getLogger(AddNewFoodController.class.getName());

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
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            int numFood = FoodDAO.getNumberOfAllFood() + 1;
            String idFood = "f" + numFood;
            String nameFood = request.getParameter("nameFood");
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price");
            String quantityStr = request.getParameter("quantity");
            float price = Float.parseFloat(priceStr);
            int quantity = Integer.parseInt(quantityStr);
            String categoryID = request.getParameter("categoryID");
            CategoryDTO categoryDTO = CategoryDAO.getCategoryByCategoryID(categoryID);
            String kind = KindDAO.getKindFoodByKindID(categoryDTO.getKindID());
            Date date = new Date();
            SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
            String createDate = df.format(date);
            String imgFood = null;
            String appPath = request.getServletContext().getRealPath("");
            appPath = appPath.replace('\\', '/');
            String pathInBuild;
            if (appPath.endsWith("/")) {
                pathInBuild = appPath + SAVE_DIRECTORY + "/" + kind + "/" + categoryID;
            } else {
                pathInBuild = appPath + "/" + SAVE_DIRECTORY + "/" + kind + "/" + categoryID;
            }
            int indexOfBuild = appPath.indexOf("build");
            appPath = appPath.substring(0, indexOfBuild) + appPath.substring(indexOfBuild + 6);
            String fullSavePath = null;
            if (appPath.endsWith("/")) {
                fullSavePath = appPath + SAVE_DIRECTORY + "/" + kind + "/" + categoryID;
            } else {
                fullSavePath = appPath + "/" + SAVE_DIRECTORY + "/" + kind + "/" + categoryID;
            }
            File fileSaveDir = new File(fullSavePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }
            // String pathInBuild = request.getServletContext().getRealPath("/") + SAVE_DIRECTORY + "/" + kind + "/" + categoryID;
            File fileSaveInBuild = new File(pathInBuild);
            if (!fileSaveInBuild.exists()) {
                fileSaveInBuild.mkdir();
            }
            Part part = request.getPart("imgFood");
            String fileName = Support.extractFileName(part);
            if (fileName != null && fileName.length() > 0) {
                String filePath = fullSavePath + File.separator + fileName;
                String filePathInBuild = pathInBuild + File.separator + fileName;
                imgFood = SAVE_DIRECTORY + "/" + kind + "/" + categoryID + "/" + fileName;
                try {
                    part.write(filePath);
                    part.write(filePathInBuild);
                } catch (IOException ex) {
                    log.error("Add new food image fail!!");
                    ex.printStackTrace();
                }
            }
            //xong xu ly file
            FoodDTO foodDTO = new FoodDTO(idFood, imgFood, nameFood, description, quantity, price, categoryID, createDate, 1);
            boolean resultInsert = FoodDAO.insertAFood(foodDTO);
            if (resultInsert) {
                request.setAttribute("MESSAGE", "Add new food success!!");
            } else {
                request.setAttribute("MESSAGE", "Add new food fail !!");
            }
            url = SUCCESS;
        } catch (Exception ex) {
            log.error("Error at AddNewFoodController: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at AddNewFoodController: " + ex.toString(), "Error!!");
            //request.setAttribute("MESSAGE", request.setAttribute("MESSAGE", "Add new food fail !! error: " + ex.toString()););
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
