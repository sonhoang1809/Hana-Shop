/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.daos;

import com.sample.hanashop.dtos.KindDTO;
import com.sample.hanashop.mails.SendMailSSL;
import com.sample.hanashop.utils.DBUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author sonho
 */
public class KindDAO extends DAO {

    public static String getKindFoodByKindID(String kindID) throws SQLException {
        String result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT KindID, KindFood "
                        + " FROM tblKinds"
                        + " WHERE KindID = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, kindID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    result = rs.getString("KindFood");
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at getKindFoodByKindID(String kindID): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getKindFoodByKindID(String kindID): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<KindDTO> getAllKindFood() throws SQLException {
        List<KindDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT KindID, KindFood "
                        + " FROM tblKinds";
                ps = cnn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new KindDTO(rs.getString("KindID"), rs.getString("KindFood")));
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at getAllKindFood(): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getAllKindFood(): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }
}
