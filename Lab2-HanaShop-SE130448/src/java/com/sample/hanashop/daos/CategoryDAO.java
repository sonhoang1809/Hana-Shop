/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.daos;

import com.sample.hanashop.dtos.CategoryDTO;
import com.sample.hanashop.mails.SendMailSSL;
import static com.sample.hanashop.utils.DBUtils.getConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author sonho
 */
public class CategoryDAO extends DAO {

    public static List<CategoryDTO> getAllListCategory() throws SQLException {
        List<CategoryDTO> result = null;
        try {
            cnn = getConnection();
            if (cnn != null) {
                String sql = "SELECT CategoryID, CategoryName "
                        + " FROM tblCategories";
                ps = cnn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new CategoryDTO(rs.getString("CategoryID"), rs.getString("CategoryName")));
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at getAllListCategory(): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getAllListCategory(): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static CategoryDTO getCategoryByCategoryID(String categoryID) throws SQLException {
        CategoryDTO result = null;
        try {
            cnn = getConnection();
            if (cnn != null) {
                String sql = "SELECT CategoryID, CategoryName, KindID "
                        + " FROM tblCategories"
                        + " WHERE CategoryID = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, categoryID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    result = new CategoryDTO(rs.getString("CategoryID"), rs.getString("CategoryName"), rs.getString("KindID"));
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at getCategoryByCategoryID(String categoryID): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getCategoryByCategoryID(String categoryID): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<CategoryDTO> getListCategoryByKindID(String kindID) throws SQLException {
        List<CategoryDTO> result = null;
        try {
            cnn = getConnection();
            if (cnn != null) {
                String sql = "SELECT CategoryID, CategoryName "
                        + " FROM tblCategories"
                        + " WHERE KindID = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, kindID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new CategoryDTO(rs.getString("CategoryID"), rs.getString("CategoryName")));
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at getListCategoryByKindID(String kindID): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListCategoryByKindID(String kindID): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }
}
