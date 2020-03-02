/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.daos;

import com.sample.hanashop.dtos.UserDTO;
import com.sample.hanashop.mails.SendMailSSL;
import com.sample.hanashop.utils.DBUtils;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author sonho
 */
public class UserDAO extends DAO {

    public static String getFoodsRecentViewOfUser(String userID) throws SQLException {
        String result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String url = "SELECT IdFoodRecentlyView FROM tblUsers WHERE UserID = ? ";
                ps = cnn.prepareStatement(url);
                ps.setString(1, userID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = rs.getString("IdFoodRecentlyView");
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at getFoodRecentViewOfUser(String userID): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getFoodRecentViewOfUser(String userID): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static boolean setFoodRecentViewOfUser(String userID, String idFoods) throws SQLException {
        boolean result = false;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String url = "UPDATE tblUsers SET IdFoodRecentlyView = ? WHERE UserID = ? ";
                ps = cnn.prepareStatement(url);
                ps.setString(1, idFoods);
                ps.setString(2, userID);
                ps.executeUpdate();
                result = true;
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at setFoodRecentViewOfUser(String userID, String idFoods): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at setFoodRecentViewOfUser(String userID, String idFoods): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static UserDTO checkLoginByGoogle(String email, String pass) throws SQLException {
        UserDTO result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String url = "SELECT UserID, Avatar, Name, RoleID FROM tblUsers WHERE Email = ? AND Password = ?";
                ps = cnn.prepareStatement(url);
                ps.setString(1, email);
                ps.setString(2, pass);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = new UserDTO(rs.getString("UserID"), email, rs.getString("Avatar"), rs.getString("Name"),
                            rs.getString("RoleID"));
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at checkLogin(String userID, String pass): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at checkLogin(String userID, String pass): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static UserDTO checkLogin(String userID, String pass) throws SQLException {
        UserDTO result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String url = "SELECT Email, Avatar, Name, RoleID FROM tblUsers WHERE UserID = ? AND Password = ?";
                ps = cnn.prepareStatement(url);
                ps.setString(1, userID);
                ps.setString(2, pass);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = new UserDTO(userID, rs.getString("Email"), rs.getString("Avatar"), rs.getString("Name"),
                            rs.getString("RoleID"));
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at checkLogin(String userID, String pass): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at checkLogin(String userID, String pass): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<UserDTO> getListUserDetailByRole(String roleID) throws SQLException {
        List<UserDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT UserID, Email, Avatar, Name, RoleID, DateCreate, Description"
                        + " FROM tblUsers "
                        + " WHERE RoleID = ? ";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, roleID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new UserDTO(rs.getString("UserID"), rs.getString("Email"), rs.getString("Avatar"),
                            rs.getString("Name"), rs.getString("RoleID"),
                            rs.getString("DateCreate"), rs.getString("Description")));
                }
            }
        } catch (Exception ex) {
            log.error("Error at getListUserDetailByRole(String roleID): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListUserDetailByRole(String roleID): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static UserDTO getFullUserDetail(String userID) throws SQLException {
        UserDTO result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String url = "SELECT  Email, Avatar, Name, RoleID, DateCreate, Description, IdFoodRecentlyView"
                        + " FROM tblUsers "
                        + " WHERE UserID = ? ";
                ps = cnn.prepareStatement(url);
                ps.setString(1, userID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = new UserDTO(userID, rs.getString("Email"), rs.getString("Avatar"), rs.getString("Name"),
                            rs.getString("RoleID"), rs.getString("DateCreate"), rs.getString("Description"), rs.getString("IdFoodRecentlyView"));
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at checkLogin(String userID, String pass): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at checkLogin(String userID, String pass): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static String getUserNameById(String userID) throws NamingException, SQLException {
        String result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String url = "SELECT Name "
                        + " FROM tblUsers"
                        + " WHERE UserID = ?";
                ps = cnn.prepareStatement(url);
                ps.setString(1, userID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = rs.getString("Name");
                }
            }
        } catch (SQLException ex) {
            log.error("Error at getUserNameById(String userID): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getUserNameById(String userID): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static UserDTO getUserByEmail(String email) throws NamingException, SQLException {
        UserDTO result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String url = "SELECT UserID, Avatar, Name, RoleID FROM tblUsers WHERE Email = ?";
                ps = cnn.prepareStatement(url);
                ps.setString(1, email);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = new UserDTO(rs.getString("UserID"), email, rs.getString("Avatar"), rs.getString("Name"),
                            rs.getString("RoleID"));
                }
            }
        } catch (SQLException ex) {
            log.error("Error at getUserByEmail(String email): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getUserByEmail(String email): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static boolean updateUser(UserDTO userDTO) throws SQLException {
        boolean result = false;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String url = "UPDATE tblUsers SET Avatar = ?, Name = ?,"
                        + " RoleID = ?, Description = ? "
                        + " WHERE UserID = ? ";
                ps = cnn.prepareStatement(url);
                ps.setString(1, userDTO.getAvatar());
                ps.setString(2, userDTO.getName());
                ps.setString(3, userDTO.getRoleID());
                ps.setString(4, userDTO.getDescription());
                ps.setString(5, userDTO.getUserID());
                ps.executeUpdate();
                result = true;
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at updateUser(UserDTO userDTO): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at updateUser(UserDTO userDTO): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static boolean insertAUser(UserDTO dto) throws SQLException {
        boolean result = false;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String url = "INSERT INTO tblUsers (UserID, Email, Avatar, Name, Password, RoleID, DateCreate)"
                        + " VALUES (?,?,?,?,?,?,?)";
                ps = cnn.prepareStatement(url);
                ps.setString(1, dto.getUserID());
                ps.setString(2, dto.getEmail());
                ps.setString(3, dto.getAvatar());
                ps.setString(4, dto.getName());
                ps.setString(5, dto.getPassword());
                ps.setString(6, dto.getRoleID());
                Date date = new Date();
                SimpleDateFormat df
                        = new java.text.SimpleDateFormat("yyyy-MM-dd");
                ps.setString(7, df.format(date));
                ps.executeUpdate();
                result = true;
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at insertAUser(UserDTO dto): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at insertAUser(UserDTO dto): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static boolean checkExistUserID(String userID) throws NamingException, SQLException {
        boolean result = false;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null && userID != null) {
                String sql = "SELECT Name FROM tblUsers WHERE UserID = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, userID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        } catch (SQLException ex) {
            log.error("Error at checkExistUserID(String userID): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at checkExistUserID(String userID): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static boolean checkExistEmail(String email) throws NamingException, SQLException {
        boolean result = false;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null && email != null) {
                String sql = "SELECT Name FROM tblUsers WHERE Email = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, email);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        } catch (SQLException ex) {
            log.error("Error at checkExistEmail(String email): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at checkExistEmail(String email): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }
}
