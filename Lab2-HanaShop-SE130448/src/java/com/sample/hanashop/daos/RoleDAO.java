/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.daos;

import com.sample.hanashop.dtos.RoleDTO;
import com.sample.hanashop.mails.SendMailSSL;
import com.sample.hanashop.utils.DBUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sonho
 */
public class RoleDAO extends DAO {

    public static List<RoleDTO> getAllRole() throws SQLException {
        List<RoleDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT RoleID, RoleDetail "
                        + " FROM tblRoles";
                ps = cnn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new RoleDTO(rs.getString("RoleID"), rs.getString("RoleDetail")));
                }
            }
        } catch (Exception ex) {
            log.error("Error at getAllRole(): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getAllRole(): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }
}
