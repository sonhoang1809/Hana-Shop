/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;


/**
 *
 * @author sonho
 */
public class DAO {
    
    public static Connection cnn;
    public static PreparedStatement ps;
    public static ResultSet rs;

    public static final Logger log = Logger.getLogger(DAO.class.getName());
    
    public static void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (cnn != null) {
            cnn.close();
        }
    }
}
