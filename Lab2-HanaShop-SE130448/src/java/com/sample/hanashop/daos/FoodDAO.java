/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.daos;

import com.sample.hanashop.dtos.CategoryDTO;
import com.sample.hanashop.dtos.FoodDTO;
import com.sample.hanashop.mails.SendMailSSL;
import com.sample.hanashop.supports.Support;
import com.sample.hanashop.utils.DBUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author sonho
 */
public class FoodDAO extends DAO {

    public static List<FoodDTO> getListFoodHasCategory(String categoryID) throws NamingException, SQLException {
        List<FoodDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdFood, ImgFood, NameFood, Description, Quantity, Price, CategoryID, CreateDate, "
                        + " UpdateDate, UpdateUser, StatusCode"
                        + " FROM tblFoods"
                        + " WHERE CategoryID = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, categoryID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new FoodDTO(rs.getString("IdFood"), rs.getString("ImgFood"),
                            rs.getString("NameFood"), rs.getString("Description"),
                            rs.getInt("Quantity"), rs.getFloat("Price"),
                            rs.getString("CategoryID"), rs.getString("CreateDate"),
                            rs.getString("UpdateDate"), rs.getString("UpdateUser"), rs.getInt("StatusCode")));
                }
            }
        } catch (SQLException ex) {
            log.error("Error at getListFoodHasCategory(String categoryID): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListFoodHasCategory(String categoryID): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<String> getListIdFoodHasCategory(String categoryID) throws NamingException, SQLException {
        List<String> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdFood FROM tblFoods"
                        + " WHERE CategoryID = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, categoryID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(rs.getString("IdFood"));
                }
            }
        } catch (SQLException ex) {
            log.error("Error at getNumberFoodHasCategory(List<CategoryDTO> listCategory): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getNumberFoodHasCategory(List<CategoryDTO> listCategory): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<FoodDTO> getListFood(List<CategoryDTO> listCategory) throws NamingException, SQLException {
        List<FoodDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdFood, ImgFood, NameFood, Description, Quantity, Price, CategoryID, CreateDate, "
                        + " UpdateDate, UpdateUser, StatusCode"
                        + " FROM tblFoods "
                        + " WHERE " + Support.getSQLlistCategory(listCategory);
                ps = cnn.prepareStatement(sql);
                for (int i = 1; i <= listCategory.size(); i++) {
                    ps.setString(i, listCategory.get(i - 1).getCategoryID());
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new FoodDTO(rs.getString("IdFood"), rs.getString("ImgFood"),
                            rs.getString("NameFood"), rs.getString("Description"),
                            rs.getInt("Quantity"), rs.getFloat("Price"),
                            rs.getString("CategoryID"), rs.getString("CreateDate"),
                            rs.getString("UpdateDate"), rs.getString("UpdateUser"), rs.getInt("StatusCode")));
                }
            }
        } catch (SQLException ex) {
            log.error("Error at getNumberFoodHasCategory(List<CategoryDTO> listCategory): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getNumberFoodHasCategory(List<CategoryDTO> listCategory): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static int getQuantityOfFoodHasCategory(List<CategoryDTO> listCategory) throws NamingException, SQLException {
        int result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT SUM(Quantity) AS 'quantityTotal' FROM tblFoods"
                        + " WHERE " + Support.getSQLlistCategory(listCategory);
                ps = cnn.prepareStatement(sql);
                for (int i = 1; i <= listCategory.size(); i++) {
                    ps.setString(i, listCategory.get(i - 1).getCategoryID());
                }
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("quantityTotal");
                }
            }
        } catch (SQLException ex) {
            log.error("Error at getNumberFoodHasCategory(List<CategoryDTO> listCategory): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getNumberFoodHasCategory(List<CategoryDTO> listCategory): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static int getNumberFoodHasCategory(List<CategoryDTO> listCategory) throws NamingException, SQLException {
        int result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT COUNT(IdFood) AS 'rowcount' FROM tblFoods"
                        + " WHERE " + Support.getSQLlistCategory(listCategory);
                ps = cnn.prepareStatement(sql);
                for (int i = 1; i <= listCategory.size(); i++) {
                    ps.setString(i, listCategory.get(i - 1).getCategoryID());
                }
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("rowcount");
                }
            }
        } catch (SQLException ex) {
            log.error("Error at getNumberFoodHasCategory(List<CategoryDTO> listCategory): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getNumberFoodHasCategory(List<CategoryDTO> listCategory): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<FoodDTO> getListFoodNew() throws SQLException {
        List<FoodDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdFood, ImgFood, NameFood, Description, Quantity, Price, CategoryID, CreateDate, StatusCode"
                        + " FROM tblFoods"
                        + " WHERE StatusCode = ? AND Quantity > 0 "
                        + " ORDER BY CreateDate DESC"
                        + " OFFSET 0 ROWS FETCH NEXT 3 ROWS ONLY";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, 1);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new FoodDTO(rs.getString("IdFood"), rs.getString("ImgFood"),
                            rs.getString("NameFood"), rs.getString("Description"),
                            rs.getInt("Quantity"), rs.getFloat("Price"),
                            rs.getString("CategoryID"), rs.getString("CreateDate"), rs.getInt("StatusCode")));
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at getListFoodNew(): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListFoodNew(): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static boolean updateFood(FoodDTO foodDTO) throws SQLException {
        boolean result = false;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "UPDATE tblFoods SET ImgFood = ?, NameFood = ?, Description = ? "
                        + " , Quantity = ?, Price = ?, CategoryID = ?, StatusCode = ? "
                        + " WHERE IdFood = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, foodDTO.getImgFood());
                ps.setString(2, foodDTO.getNameFood());
                ps.setString(3, foodDTO.getDescription());
                ps.setInt(4, foodDTO.getQuantity());
                ps.setFloat(5, foodDTO.getPrice());
                ps.setString(6, foodDTO.getCategoryID());
                ps.setInt(7, foodDTO.getStatusCode());
                ps.setString(8, foodDTO.getIdFood());
                ps.executeUpdate();
                result = true;
            }
        } catch (Exception ex) {
            log.error("Error at updateFood(FoodDTO foodDTO): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at updateFood(FoodDTO foodDTO): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static boolean updateFoodStatusByAdmin(String idFood, int statusCode) throws SQLException {
        boolean result = false;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "UPDATE tblFoods SET StatusCode = ?"
                        + " WHERE IdFood = ?";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, statusCode);
                ps.setString(2, idFood);
                ps.executeUpdate();
                result = true;
            }
        } catch (Exception ex) {
            log.error("Error at updateFoodStatusByAdmin(FoodDTO foodDTO): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at updateFoodStatusByAdmin(FoodDTO foodDTO): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static boolean updateFoodByAdmin(FoodDTO foodDTO) throws SQLException {
        boolean result = false;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = " UPDATE tblFoods SET ImgFood = ?, NameFood = ?, Description = ? ,"
                        + " Quantity = ?, Price = ?, CategoryID = ?, UpdateDate = ?, UpdateUser = ?, StatusCode = ?"
                        + " WHERE IdFood = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, foodDTO.getImgFood());
                ps.setString(2, foodDTO.getNameFood());
                ps.setString(3, foodDTO.getDescription());
                ps.setInt(4, foodDTO.getQuantity());
                ps.setFloat(5, foodDTO.getPrice());
                ps.setString(6, foodDTO.getCategoryID());
                ps.setString(7, foodDTO.getUpdateDate());
                ps.setString(8, foodDTO.getUpdateUser());
                ps.setInt(9, foodDTO.getStatusCode());
                ps.setString(10, foodDTO.getIdFood());
                ps.executeUpdate();
                result = true;
            }
        } catch (Exception ex) {
            log.error("Error at updateFoodByAdmin(FoodDTO foodDTO): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at updateFoodByAdmin(FoodDTO foodDTO): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static boolean checkFoodIsSelling(String idFood) throws SQLException {
        boolean result = false;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdFood "
                        + " FROM tblFoods "
                        + " WHERE IdFood = ? AND StatusCode = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, idFood);
                ps.setInt(2, 1);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        } catch (Exception ex) {
            log.error("Error at checkFoodIsSelling(String idFood): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at checkFoodIsSelling(String idFood): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static String checkValidFoodInStock(String idFood, int quantityInBill) throws SQLException {
        String idFoodFalse = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT Quantity, StatusCode "
                        + " FROM tblFoods "
                        + " WHERE IdFood = ? ";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, idFood);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int quantity = rs.getInt("Quantity");
                    int statusCode = rs.getInt("StatusCode");
                    if (quantity < quantityInBill || statusCode == 0) {
                        idFoodFalse = idFood;
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Error at checkValidQuantity(String idFood, int quantityInBill): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at checkValidQuantity(String idFood, int quantityInBill): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return idFoodFalse;
    }

    public static List<FoodDTO> getListFoodRecommend(float priceLower, float priceHigher, int statusCode, List<CategoryDTO> listCategory, String idFood) throws SQLException {
        List<FoodDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdFood, ImgFood, NameFood, Description, Quantity, Price, CategoryID, CreateDate, StatusCode"
                        + " FROM tblFoods"
                        + " WHERE StatusCode = ? AND Quantity > 0 AND Price BETWEEN ? AND ? AND NOT IdFood = ? AND " + Support.getSQLlistCategory(listCategory)
                        + " ORDER BY CreateDate";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, statusCode);
                ps.setFloat(2, priceHigher);
                ps.setFloat(3, priceLower);
                ps.setString(4, idFood);
                for (int i = 5; i < listCategory.size() + 5; i++) {
                    ps.setString(i, listCategory.get(i - 5).getCategoryID());
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new FoodDTO(rs.getString("IdFood"), rs.getString("ImgFood"),
                            rs.getString("NameFood"), rs.getString("Description"),
                            rs.getInt("Quantity"), rs.getFloat("Price"),
                            rs.getString("CategoryID"), rs.getString("CreateDate"), statusCode));
                }
            }
        } catch (Exception ex) {
            log.error("Error at getListFoodRecommend(float priceLower, float priceHigher, int statusCode, List<CategoryDTO> listCategory): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListFoodRecommend(float priceLower, float priceHigher, int statusCode, List<CategoryDTO> listCategory): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static FoodDTO getFullDetailsFoodDTO(String idFood) throws SQLException {
        FoodDTO result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT ImgFood, NameFood, Description, Quantity, Price, CategoryID, CreateDate, UpdateDate, UpdateUser, StatusCode"
                        + " FROM tblFoods"
                        + " WHERE IdFood = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, idFood);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = new FoodDTO(idFood, rs.getString("ImgFood"),
                            rs.getString("NameFood"), rs.getString("Description"),
                            rs.getInt("Quantity"), rs.getFloat("Price"),
                            rs.getString("CategoryID"), rs.getString("CreateDate"),
                            rs.getString("UpdateDate"), rs.getString("UpdateUser"),
                            rs.getInt("StatusCode"));
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at getListFood(float priceLower, float priceHigher, int statusCode): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListFood(float priceLower, float priceHigher, int statusCode): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static FoodDTO getFoodDTO(String idFood) throws SQLException {
        FoodDTO result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT ImgFood, NameFood, Description, Quantity, Price, CategoryID, CreateDate, StatusCode"
                        + " FROM tblFoods"
                        + " WHERE IdFood = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, idFood);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = new FoodDTO(idFood, rs.getString("ImgFood"),
                            rs.getString("NameFood"), rs.getString("Description"),
                            rs.getInt("Quantity"), rs.getFloat("Price"),
                            rs.getString("CategoryID"), rs.getString("CreateDate"), rs.getInt("StatusCode"));
                }
            }
        } catch (Exception ex) {
            log.error("Error at getListFood(float priceLower, float priceHigher, int statusCode): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListFood(float priceLower, float priceHigher, int statusCode): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<FoodDTO> getListFood(float priceLower, float priceHigher, int statusCode, int pageShow) throws SQLException {
        List<FoodDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdFood, ImgFood, NameFood, Description, Quantity, Price, CategoryID, CreateDate, StatusCode"
                        + " FROM tblFoods"
                        + " WHERE StatusCode = ? AND Price BETWEEN ? AND ? "
                        + " ORDER BY CreateDate DESC"
                        + " OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, statusCode);
                ps.setFloat(2, priceHigher);
                ps.setFloat(3, priceLower);
                int get = (pageShow - 1) * 20;
                ps.setInt(4, get);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new FoodDTO(rs.getString("IdFood"), rs.getString("ImgFood"),
                            rs.getString("NameFood"), rs.getString("Description"),
                            rs.getInt("Quantity"), rs.getFloat("Price"),
                            rs.getString("CategoryID"), rs.getString("CreateDate"), statusCode));
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at getListFood(float priceLower, float priceHigher, int statusCode): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListFood(float priceLower, float priceHigher, int statusCode): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static int getNumberFood(float priceLower, float priceHigher, int statusCode) throws NamingException, SQLException {
        int result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT Count(IdFood) AS 'rowcount' FROM tblFoods"
                        + " WHERE StatusCode = ?"
                        + " AND Price BETWEEN ? AND ?";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, statusCode);
                ps.setFloat(2, priceHigher);
                ps.setFloat(3, priceLower);
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

    public static List<FoodDTO> getListFood(float priceLower, float priceHigher, int statusCode, int pageShow, String searchName) throws SQLException {
        List<FoodDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdFood, ImgFood, NameFood, Description, Quantity, Price, CategoryID, CreateDate, StatusCode"
                        + " FROM tblFoods"
                        + " WHERE StatusCode = ? AND Price BETWEEN ? AND ? AND NameFood LIKE ?"
                        + " ORDER BY CreateDate DESC"
                        + " OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, statusCode);
                ps.setFloat(2, priceHigher);
                ps.setFloat(3, priceLower);
                ps.setString(4, "%" + searchName + "%");
                int get = (pageShow - 1) * 20;
                ps.setInt(5, get);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new FoodDTO(rs.getString("IdFood"), rs.getString("ImgFood"),
                            rs.getString("NameFood"), rs.getString("Description"),
                            rs.getInt("Quantity"), rs.getFloat("Price"),
                            rs.getString("CategoryID"), rs.getString("CreateDate"), statusCode));
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at getListFood(float priceLower, float priceHigher, int statusCode): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListFood(float priceLower, float priceHigher, int statusCode): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static int getNumberFood(float priceLower, float priceHigher, int statusCode, String searchName) throws NamingException, SQLException {
        int result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdFood FROM tblFoods"
                        + " WHERE StatusCode = ?"
                        + " AND Price BETWEEN ? AND ?"
                        + " AND NameFood LIKE ?";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, statusCode);
                ps.setFloat(2, priceHigher);
                ps.setFloat(3, priceLower);
                ps.setString(4, "%" + searchName + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    result = result + 1;
                }
            }
        } catch (SQLException ex) {
            log.error("Error at getNumberArticle(int statusArticleID, String searchTitle): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getNumberArticle(int statusArticleID, String searchTitle): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<FoodDTO> getListFood(float priceLower, float priceHigher, int statusCode, int pageShow, String categoryID, String searchName) throws SQLException {
        List<FoodDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdFood, ImgFood, NameFood, Description, Quantity, Price, CategoryID, CreateDate, StatusCode"
                        + " FROM tblFoods"
                        + " WHERE StatusCode = ? AND Price BETWEEN ? AND ? AND NameFood LIKE ? AND CategoryID = ?"
                        + " ORDER BY CreateDate DESC"
                        + " OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, statusCode);
                ps.setFloat(2, priceHigher);
                ps.setFloat(3, priceLower);
                ps.setString(4, "%" + searchName + "%");
                ps.setString(5, categoryID);
                int get = (pageShow - 1) * 20;
                ps.setInt(6, get);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new FoodDTO(rs.getString("IdFood"), rs.getString("ImgFood"),
                            rs.getString("NameFood"), rs.getString("Description"),
                            rs.getInt("Quantity"), rs.getFloat("Price"),
                            rs.getString("CategoryID"), rs.getString("CreateDate"), statusCode));
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at getListFood(float priceLower, float priceHigher, int statusCode): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListFood(float priceLower, float priceHigher, int statusCode): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static int getNumberFood(float priceLower, float priceHigher, int statusCode, String categoryID, String searchName) throws NamingException, SQLException {
        int result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdFood FROM tblFoods"
                        + " WHERE StatusCode = ?"
                        + " AND Price BETWEEN ? AND ?"
                        + " AND NameFood LIKE ? AND CategoryID = ?";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, statusCode);
                ps.setFloat(2, priceHigher);
                ps.setFloat(3, priceLower);
                ps.setString(4, "%" + searchName + "%");
                ps.setString(5, categoryID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    result = result + 1;
                }
            }
        } catch (SQLException ex) {
            log.error("Error at getNumberArticle(int statusArticleID, String searchTitle): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getNumberArticle(int statusArticleID, String searchTitle): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<FoodDTO> getListFood(float priceLower, float priceHigher, int statusCode, int pageShow, List<CategoryDTO> listCategory) throws SQLException {
        List<FoodDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {

                String sql = "SELECT IdFood, ImgFood, NameFood, Description, Quantity, Price, CategoryID, CreateDate, StatusCode"
                        + " FROM tblFoods"
                        + " WHERE StatusCode = ? AND Price BETWEEN ? AND ? AND " + Support.getSQLlistCategory(listCategory)
                        + " ORDER BY CreateDate DESC"
                        + " OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, statusCode);
                ps.setFloat(2, priceHigher);
                ps.setFloat(3, priceLower);
                for (int i = 4; i < (listCategory.size() + 4); i++) {
                    ps.setString(i, listCategory.get(i - 4).getCategoryID());
                }
                int get = (pageShow - 1) * 20;
                ps.setInt(4 + listCategory.size(), get);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new FoodDTO(rs.getString("IdFood"), rs.getString("ImgFood"),
                            rs.getString("NameFood"), rs.getString("Description"),
                            rs.getInt("Quantity"), rs.getFloat("Price"),
                            rs.getString("CategoryID"), rs.getString("CreateDate"), statusCode));
                }
            }
        } catch (Exception ex) {
            log.error("Error at getListFood(float priceLower, float priceHigher, int statusCode, int pageShow, List<CategoryDTO> listCategory): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListFood(float priceLower, float priceHigher, int statusCode, int pageShow, List<CategoryDTO> listCategory): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static int getNumberFood(float priceLower, float priceHigher, int statusCode, List<CategoryDTO> listCategory) throws NamingException, SQLException {
        int result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdFood FROM tblFoods"
                        + " WHERE StatusCode = ?"
                        + " AND Price BETWEEN ? AND ?"
                        + " AND " + Support.getSQLlistCategory(listCategory);
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, statusCode);
                ps.setFloat(2, priceHigher);
                ps.setFloat(3, priceLower);
                for (int i = 4; i < listCategory.size() + 4; i++) {
                    ps.setString(i, listCategory.get(i - 4).getCategoryID());
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    result = result + 1;
                }
            }
        } catch (SQLException ex) {
            log.error("Error at getNumberFood(float priceLower, float priceHigher, int statusCode, List<CategoryDTO> listCategory): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getNumberFood(float priceLower, float priceHigher, int statusCode, List<CategoryDTO> listCategory): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<FoodDTO> getListFood(float priceLower, float priceHigher, String categoryID, int statusCode, int pageShow) throws SQLException {
        List<FoodDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {

                String sql = "SELECT IdFood, ImgFood, NameFood, Description, Quantity, Price, CategoryID, CreateDate, StatusCode"
                        + " FROM tblFoods"
                        + " WHERE StatusCode = ? AND Price BETWEEN ? AND ? AND CategoryID = ? "
                        + " ORDER BY CreateDate DESC"
                        + " OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, statusCode);
                ps.setFloat(2, priceHigher);
                ps.setFloat(3, priceLower);
                ps.setString(4, categoryID);
                int get = (pageShow - 1) * 20;
                ps.setInt(5, get);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new FoodDTO(rs.getString("IdFood"), rs.getString("ImgFood"),
                            rs.getString("NameFood"), rs.getString("Description"),
                            rs.getInt("Quantity"), rs.getFloat("Price"),
                            rs.getString("CategoryID"), rs.getString("CreateDate"), statusCode));
                }
            }
        } catch (Exception ex) {
            log.error("Error at getListFood(float priceLower, float priceHigher, int statusCode, int pageShow, List<CategoryDTO> listCategory): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListFood(float priceLower, float priceHigher, int statusCode, int pageShow, List<CategoryDTO> listCategory): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static int getNumberFood(float priceLower, float priceHigher, String categoryID, int statusCode) throws SQLException {
        int result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdFood FROM tblFoods"
                        + " WHERE StatusCode = ?"
                        + " AND Price BETWEEN ? AND ?"
                        + " AND CategoryID = ? ";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, statusCode);
                ps.setFloat(2, priceHigher);
                ps.setFloat(3, priceLower);
                ps.setString(4, categoryID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    result = result + 1;
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at getNumberFood(float priceLower, float priceHigher, int statusCode, List<CategoryDTO> listCategory): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getNumberFood(float priceLower, float priceHigher, int statusCode, List<CategoryDTO> listCategory): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<FoodDTO> getListFood(float priceLower, float priceHigher, int statusCode, int pageShow, List<CategoryDTO> listCategory, String searchName) throws SQLException {
        List<FoodDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {

                String sql = "SELECT IdFood, ImgFood, NameFood, Description, Quantity, Price, CategoryID, CreateDate, StatusCode"
                        + " FROM tblFoods"
                        + " WHERE StatusCode = ? AND Price BETWEEN ? AND ? AND NameFood LIKE ? AND " + Support.getSQLlistCategory(listCategory)
                        + " ORDER BY CreateDate DESC"
                        + " OFFSET ? ROWS FETCH NEXT 20 ROWS ONLY";
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, statusCode);
                ps.setFloat(2, priceHigher);
                ps.setFloat(3, priceLower);
                ps.setString(4, "%" + searchName + "%");
                for (int i = 5; i < listCategory.size() + 5; i++) {
                    ps.setString(i, listCategory.get(i - 5).getCategoryID());
                }
                int get = (pageShow - 1) * 20;
                ps.setInt(5 + listCategory.size(), get);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new FoodDTO(rs.getString("IdFood"), rs.getString("ImgFood"),
                            rs.getString("NameFood"), rs.getString("Description"),
                            rs.getInt("Quantity"), rs.getFloat("Price"),
                            rs.getString("CategoryID"), rs.getString("CreateDate"), statusCode));
                }
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at getListFood(float priceLower, float priceHigher, int statusCode, int pageShow, List<CategoryDTO> listCategory): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListFood(float priceLower, float priceHigher, int statusCode, int pageShow, List<CategoryDTO> listCategory): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static int getNumberFood(float priceLower, float priceHigher, int statusCode, List<CategoryDTO> listCategory, String searchName) throws NamingException, SQLException {
        int result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdFood FROM tblFoods"
                        + " WHERE StatusCode = ?"
                        + " AND Price BETWEEN ? AND ? AND NameFood LIKE ?"
                        + " AND " + Support.getSQLlistCategory(listCategory);
                ps = cnn.prepareStatement(sql);
                ps.setInt(1, statusCode);
                ps.setFloat(2, priceHigher);
                ps.setFloat(3, priceLower);
                ps.setString(4, "%" + searchName + "%");
                for (int i = 5; i < listCategory.size() + 5; i++) {
                    ps.setString(i, listCategory.get(i - 5).getCategoryID());
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    result = result + 1;
                }
            }
        } catch (SQLException ex) {
            log.error("Error at getNumberFood(float priceLower, float priceHigher, int statusCode, List<CategoryDTO> listCategory): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getNumberFood(float priceLower, float priceHigher, int statusCode, List<CategoryDTO> listCategory): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static int getNumberOfAllFood() throws NamingException, SQLException {
        int result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT COUNT(IdFood) AS 'rowcount'"
                        + " FROM tblFoods";
                ps = cnn.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("rowcount");
                }
            }
        } catch (SQLException ex) {
            log.error("Error at getNumberOfFood(): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getNumberOfFood(): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static boolean insertAFood(FoodDTO foodDTO) throws NamingException, SQLException {
        boolean result = false;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "INSERT INTO tblFoods(IdFood,ImgFood,NameFood,Description,Quantity,Price,CategoryID,CreateDate,StatusCode) "
                        + " VALUES (?,?,?,?,?,?,?,?,?) ";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, foodDTO.getIdFood());
                ps.setString(2, foodDTO.getImgFood());
                ps.setString(3, foodDTO.getNameFood());
                ps.setString(4, foodDTO.getDescription());
                ps.setInt(5, foodDTO.getQuantity());
                ps.setFloat(6, foodDTO.getPrice());
                ps.setString(7, foodDTO.getCategoryID());
                ps.setString(8, foodDTO.getCreateDate());
                ps.setInt(9, 1);
                ps.executeUpdate();
                result = true;
            }
        } catch (SQLException ex) {
            log.error("Error at getNumberOfFood(): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getNumberOfFood(): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

}
