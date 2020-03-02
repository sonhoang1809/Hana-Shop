/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.daos;

import com.sample.hanashop.dtos.BillDTO;
import com.sample.hanashop.mails.SendMailSSL;
import com.sample.hanashop.supports.Support;
import com.sample.hanashop.utils.DBUtils;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author sonho
 */
public class BillDAO extends DAO {

    public static List<BillDTO> getListBillFromDateToDate(String dateFrom, String dateTo) throws NamingException, SQLException {
        List<BillDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdBill, UserID, SubTotal, Tax, Total, Date, StatusBillCode"
                        + " FROM tblBills "
                        + " WHERE Date >= ? AND Date < ? ";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, dateFrom);
                ps.setString(2, dateTo);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new BillDTO(rs.getString("IdBill"), rs.getString("UserID"),
                            rs.getFloat("SubTotal"), rs.getFloat("Tax"), rs.getFloat("Total"),
                            rs.getString("Date"), rs.getInt("StatusBillCode")));
                }
            }
        } catch (SQLException ex) {
            log.error("Error at getListBillFromDateToDate(String dateFrom, String dateTo): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListBillFromDateToDate(String dateFrom, String dateTo): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public static float getTotalMoneyFromDateToDate(String dateFrom, String dateTo) throws NamingException, SQLException {
        float result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT SUM(Total) AS 'totalSum' "
                        + " FROM tblBills"
                        + " WHERE Date >= ? AND Date < ? AND StatusBillCode = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, dateFrom);
                ps.setString(2, dateTo);
                ps.setInt(3, 1);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = rs.getFloat("totalSum");
                }
            }
        } catch (SQLException ex) {
            log.error("Error at getTotalMoneyFromDateToDate(String dateFrom, String dateTo): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getTotalMoneyFromDateToDate(String dateFrom, String dateTo): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static int getNumberOfBillFromDateToDate(String dateFrom, String dateTo) throws NamingException, SQLException {
        int result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT COUNT(IdBill) AS 'rowcount' "
                        + " FROM tblBills"
                        + " WHERE Date >= ? AND Date < ? AND StatusBillCode = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, dateFrom);
                ps.setString(2, dateTo);
                ps.setInt(3, 1);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("rowcount");
                }
            }
        } catch (SQLException ex) {
            log.error("Error at getNumberOfBillByUserID(String userID, String date): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getNumberOfBillByUserID(String userID, String date): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<String> getListIdBillOrderedByUserID(String userID) throws SQLException {
        List<String> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdBill "
                        + " FROM tblBills "
                        + " WHERE UserID = ? AND StatusBillCode = ? ";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, userID);
                ps.setInt(2, 1);

                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(rs.getString("IdBill"));
                }
            }
        } catch (Exception ex) {
            log.error("Error at getListBillByUserID(String userID): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListBillByUserID(String userID): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<String> getAllBillIdIsOrdered() throws SQLException {
        List<String> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdBill"
                        + " FROM tblBills "
                        + " WHERE StatusBillCode = ? ";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, 1);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(rs.getString("IdBill"));
                }
            }
        } catch (Exception ex) {
            log.error("Error at getAllBillIdIsOrdered(): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getAllBillIdIsOrdered(): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<BillDTO> getListAllBillIsOrdered() throws SQLException {
        List<BillDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdBill, UserID, SubTotal, Tax, Total, Date, StatusBillCode"
                        + " FROM tblBills "
                        + " WHERE StatusBillCode = ? ";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, 1);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new BillDTO(rs.getString("IdBill"), rs.getString("UserID"),
                            rs.getFloat("SubTotal"), rs.getFloat("Tax"), rs.getFloat("Total"),
                            rs.getString("Date"), rs.getInt("StatusBillCode")));
                }
            }
        } catch (Exception ex) {
            log.error("Error at getListAllBillIsOrdered(): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListAllBillIsOrdered(): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<BillDTO> getListAllBill() throws SQLException {
        List<BillDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdBill, UserID, SubTotal, Tax, Total, Date, StatusBillCode FROM tblBills";
                ps = cnn.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new BillDTO(rs.getString("IdBill"), rs.getString("UserID"),
                            rs.getFloat("SubTotal"), rs.getFloat("Tax"), rs.getFloat("Total"),
                            rs.getString("Date"), rs.getInt("StatusBillCode")));
                }
            }
        } catch (Exception ex) {
            log.error("Error at getListAllBill(): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListAllBill(): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static int getNumberOfBillByUserID(String userID, String date) throws NamingException, SQLException {
        int result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdBill, Date "
                        + " FROM tblBills"
                        + " WHERE UserID = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, userID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String dateCompare = rs.getString("Date");
                    if (dateCompare.contains(date)) {
                        result++;
                    }
                }
            }
        } catch (SQLException ex) {
            log.error("Error at getNumberOfBillByUserID(String userID, String date): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getNumberOfBillByUserID(String userID, String date): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<String> getListIdBillByUserIDHasDate(String userID, String date) throws SQLException {
        List<String> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdBill, Date"
                        + " FROM tblBills "
                        + " WHERE UserID = ? "
                        + " ORDER BY Date DESC";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, userID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    String dateCompare = rs.getString("Date");
                    if (dateCompare.contains(date)) {
                        result.add(rs.getString("IdBill"));
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Error at getListBillByUserID(String userID): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListBillByUserID(String userID): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<BillDTO> getListBillByUserID(String userID, String date) throws SQLException {
        List<BillDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdBill, SubTotal, Tax, Total, Date, StatusBillCode"
                        + " FROM tblBills "
                        + " WHERE UserID = ? "
                        + " ORDER BY Date DESC";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, userID);

                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    String dateCompare = rs.getString("Date");
                    if (dateCompare.contains(date)) {
                        result.add(new BillDTO(rs.getString("IdBill"), userID, rs.getFloat("SubTotal"),
                                rs.getFloat("Tax"), rs.getFloat("Total"),
                                rs.getString("Date"), rs.getInt("StatusBillCode")));
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Error at getListBillByUserID(String userID): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListBillByUserID(String userID): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static int getNumberOfBillByUserID(String userID) throws NamingException, SQLException {
        int result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT COUNT(IdBill) AS 'rowcount'"
                        + " FROM tblBills"
                        + " WHERE UserID = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, userID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("rowcount");
                }
            }
        } catch (SQLException ex) {
            log.error("Error at getNumberFood(float priceLower, float priceHigher, int statusCode): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getNumberFood(float priceLower, float priceHigher, int statusCode): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<BillDTO> getListBillByUserID(String userID) throws SQLException {
        List<BillDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdBill, SubTotal, Tax, Total, Date, StatusBillCode"
                        + " FROM tblBills "
                        + " WHERE UserID = ?"
                        + " ORDER BY Date DESC";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, userID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new BillDTO(rs.getString("IdBill"), userID, rs.getFloat("SubTotal"),
                            rs.getFloat("Tax"), rs.getFloat("Total"),
                            rs.getString("Date"), rs.getInt("StatusBillCode")));
                }
            }
        } catch (Exception ex) {
            log.error("Error at getListBillByUserID(String userID): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListBillByUserID(String userID): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<BillDTO> getListBillUserContainNameFoodAndDate(String userID, List<String> listIdBill,
            String date) throws SQLException {
        List<BillDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdBill, SubTotal, Tax, Total, Date, StatusBillCode"
                        + " FROM tblBills "
                        + " WHERE UserID = ? AND " + Support.getSQLListBill(listIdBill.size())
                        + " ORDER BY Date DESC ";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, userID);
                for (int i = 2; i < listIdBill.size() + 2; i++) {
                    ps.setString(i, listIdBill.get(i - 2));
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    String dateCompare = rs.getString("Date");
                    if (dateCompare.contains(date)) {
                        if (result == null) {
                            result = new ArrayList<>();
                        }
                        result.add(new BillDTO(rs.getString("IdBill"), userID, rs.getFloat("SubTotal"),
                                rs.getFloat("Tax"), rs.getFloat("Total"),
                                rs.getString("Date"), rs.getInt("StatusBillCode")));
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Error at getListBillUserContainNameFoodAndDate(String userID, List<String> listIdBill, String date): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListBillUserContainNameFoodAndDate(String userID, List<String> listIdBill, String date): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<BillDTO> getListBillUserContainNameFood(String userID, List<String> listIdBill) throws SQLException {
        List<BillDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdBill, SubTotal, Tax, Total, Date, StatusBillCode"
                        + " FROM tblBills "
                        + " WHERE UserID = ? AND " + Support.getSQLListBill(listIdBill.size())
                        + " ORDER BY Date DESC ";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, userID);
                for (int i = 2; i < listIdBill.size() + 2; i++) {
                    ps.setString(i, listIdBill.get(i - 2));
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new BillDTO(rs.getString("IdBill"), userID, rs.getFloat("SubTotal"),
                            rs.getFloat("Tax"), rs.getFloat("Total"),
                            rs.getString("Date"), rs.getInt("StatusBillCode")));
                }
            }
        } catch (Exception ex) {
            log.error("Error at getListBillByUserID(String userID): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListBillByUserID(String userID): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<String> getListIdBillByUserID(String userID) throws SQLException {
        List<String> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdBill"
                        + " FROM tblBills "
                        + " WHERE UserID = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, userID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    String idBill = rs.getString("IdBill");
                    result.add(idBill);
                }
            }
        } catch (Exception ex) {
            log.error("Error at getListIdBillByUserID(String userID): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListIdBillByUserID(String userID): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static boolean checkUserHasBill(String userID) throws SQLException {
        boolean result = false;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdBill FROM tblBills WHERE UserID = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, userID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        } catch (Exception ex) {
            log.error("Error at checkUserHasBill(String userID): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at checkUserHasBill(String userID): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static String getLastBillIsNotPaid(String userID) throws SQLException {
        String result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdBill FROM tblBills WHERE UserID = ? AND StatusBillCode = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, userID);
                ps.setInt(2, 0);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = rs.getString("IdBill");
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at getLastBillIsNotPaid(String userID): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getLastBillIsNotPaid(String userID): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static BillDTO createNewBillForUser(String userID, int billNum) throws SQLException {
        BillDTO result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "INSERT INTO tblBills(IdBill, UserID, SubTotal, Tax, Total, Date, StatusBillCode) "
                        + " VALUES(?,?,?,?,?,?,?)";
                ps = cnn.prepareStatement(sql);
                String idBill = billNum + "-" + userID;
                ps.setString(1, idBill);
                ps.setString(2, userID);
                float total = 0;
                ps.setFloat(3, 0);
                ps.setFloat(4, 0);
                ps.setFloat(5, 0);
                Date date = new Date();
                SimpleDateFormat df
                        = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                ps.setString(6, df.format(date));
                int statusBillCode = 0;
                ps.setInt(7, statusBillCode);
                ps.executeUpdate();
                result = new BillDTO(idBill, userID, 0, 0, total, df.format(date), statusBillCode);
            }
        } catch (Exception ex) {
            log.error("Error at createNewBillForUser(String userID, int billNum): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at createNewBillForUser(String userID, int billNum): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static float getTotalOfBill(String billID) throws SQLException {
        float result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT Total FROM tblBills WHERE IdBill = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, billID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = rs.getFloat("Total");
                }
            }
        } catch (SQLException | NamingException ex) {

        } finally {
            closeConnection();
        }
        return result;
    }

    public static boolean updateBillTotal(String idBill, float subTotalBill, float tax, float totalBill) throws SQLException {
        boolean result = false;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "UPDATE tblBills SET SubTotal = ?, Tax = ?, Total = ?, Date = ? "
                        + " WHERE IdBill = ?";
                ps = cnn.prepareStatement(sql);
                ps.setFloat(1, subTotalBill);
                ps.setFloat(2, tax);
                ps.setFloat(3, totalBill);
                Date date = new Date();
                SimpleDateFormat df
                        = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                ps.setString(4, df.format(date));
                ps.setString(5, idBill);
                ps.executeUpdate();
                result = true;
            }
        } catch (Exception ex) {
            log.error("Error at  updateBillTotal(String idBill, float subTotalBill, float tax, float totalBill): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at  updateBillTotal(String idBill, float subTotalBill, float tax, float totalBill): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static boolean updateBillDetail(BillDTO dto) throws SQLException {
        boolean result = false;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "UPDATE tblBills SET Total = ?, Date = ?, StatusBillCode = ?"
                        + " WHERE IdBill = ?";
                ps = cnn.prepareStatement(sql);
                ps.setFloat(1, dto.getTotal());
                Date date = new Date();
                SimpleDateFormat df
                        = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                ps.setString(2, df.format(date));
                ps.setInt(3, dto.getStatusBillCode());
                ps.setString(4, dto.getIdBill());
                ps.executeUpdate();
                result = true;
            }
        } catch (Exception ex) {

        } finally {
            closeConnection();
        }
        return result;
    }

    public static BillDTO getBillDetailByBillID(String idBill) throws SQLException {
        BillDTO result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT UserID, SubTotal, Tax, Total, Date, StatusBillCode"
                        + " FROM tblBills "
                        + " WHERE IdBill = ? ";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, idBill);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = new BillDTO(idBill, rs.getString("UserID"),
                            rs.getFloat("SubTotal"), rs.getFloat("Tax"), rs.getFloat("Total"),
                            rs.getString("Date"), rs.getInt("StatusBillCode"));
                }
            }
        } catch (Exception ex) {
            log.error("Error at getBillDetailByBillID(String idBill) : " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getBillDetailByBillID(String idBill) : " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }
}
