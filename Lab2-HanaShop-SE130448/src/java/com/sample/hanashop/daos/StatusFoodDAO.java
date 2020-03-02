/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.daos;

import com.sample.hanashop.dtos.StatusFoodDTO;
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
public class StatusFoodDAO extends DAO {

    public static List<StatusFoodDTO> getAllListStatus() throws SQLException {
        List<StatusFoodDTO> result = null;
        try {
            cnn = getConnection();
            if (cnn != null) {
                String sql = "SELECT StatusCode, Status "
                        + " FROM tblStatusFood";
                ps = cnn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new StatusFoodDTO(rs.getInt("StatusCode"), rs.getString("Status")));
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at getAllListStatus(): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getAllListStatus(): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static StatusFoodDTO getFoodDTO(int statusCode) throws SQLException {
        StatusFoodDTO result = null;
        try {
            cnn = getConnection();
            if (cnn != null) {
                String sql = "SELECT Status"
                        + " FROM tblStatusFood"
                        + " WHERE StatusCode = ?";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, statusCode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = new StatusFoodDTO(statusCode, rs.getString("Status"));
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at getFoodDTO(int statusCode): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getFoodDTO(int statusCode): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }
}
