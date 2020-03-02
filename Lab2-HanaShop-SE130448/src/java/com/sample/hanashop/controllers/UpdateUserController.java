/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.controllers;

import com.sample.hanashop.daos.UserDAO;
import com.sample.hanashop.dtos.UserDTO;
import com.sample.hanashop.mails.SendMailSSL;
import com.sample.hanashop.supports.Support;
import java.io.File;
import java.io.IOException;
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
public class UpdateUserController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final String SUCCESS = "LoadUserDetailsController";
    private final String ERROR = "LoadUserDetailsController";
    private static final Logger log = Logger.getLogger(UpdateUserController.class.getName());
    private final String SAVE_DIRECTORY = "assets/images/User-Avatar";

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
            String userID = request.getParameter("userID");
            String name = request.getParameter("name");
            String description = request.getParameter("description").trim();
            String roleID = request.getParameter("roleID");
            if (roleID == null || roleID.equals("")) {
                roleID = request.getParameter("oldRoleID");
            }
            String avatarNew = null;
            Part part = request.getPart("avatar");
            String fileName = Support.extractFileName(part);
            if (fileName != null && fileName.length() > 0) {
                String appPath = request.getServletContext().getRealPath("");
                appPath = appPath.replace('\\', '/');
                int indexOfBuild = appPath.indexOf("build");
                String pathInBuild;
                if (appPath.endsWith("/")) {
                    pathInBuild = appPath + SAVE_DIRECTORY + "/" + userID;
                } else {
                    pathInBuild = appPath + "/" + SAVE_DIRECTORY + "/" + userID;
                }
                File fileSaveInBuild = new File(pathInBuild);
                if (!fileSaveInBuild.exists()) {
                    fileSaveInBuild.mkdir();
                }
                appPath = appPath.substring(0, indexOfBuild) + appPath.substring(indexOfBuild + 6);
                String fullSavePath = null;
                if (appPath.endsWith("/")) {
                    fullSavePath = appPath + SAVE_DIRECTORY + "/" + userID;
                } else {
                    fullSavePath = appPath + "/" + SAVE_DIRECTORY + "/" + userID;
                }
                File fileSaveDir = new File(fullSavePath);
                if (!fileSaveDir.exists()) {
                    fileSaveDir.mkdir();
                }
                String filePath = fullSavePath + File.separator + fileName;
                String filePathInBuild = pathInBuild + File.separator + fileName;
                avatarNew = SAVE_DIRECTORY + "/" + userID + "/" + fileName;
                try {
                    part.write(filePath);
                    part.write(filePathInBuild);
                } catch (IOException ex) {
                    log.error("Error at UpdateUserController: " + ex.toString());
                }
            } else {
                avatarNew = request.getParameter("oldAvatar");
            }
            UserDTO userUpdate = new UserDTO(userID, avatarNew, name, description);
            userUpdate.setRoleID(roleID);
            boolean resultUpdate = UserDAO.updateUser(userUpdate);
            if (resultUpdate) {
                request.setAttribute("MESSAGEUPDATE", "Update Success!!");
            } else {
                request.setAttribute("MESSAGEUPDATE", "Update fail!!");
            }
            url = SUCCESS;
        } catch (Exception ex) {
            log.error("Error at UpdateUserController: " + ex.toString());
            SendMailSSL.sendToAdmin("Error at UpdateUserController: " + ex.toString(), "Error!!");
            request.setAttribute("MESSAGEUPDATE", "Update User fail !! ");
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
