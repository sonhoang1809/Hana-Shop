/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sample.hanashop.daos;

import com.sample.hanashop.dtos.FoodsInBillDTO;
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
public class FoodInBillDAO extends DAO {

    public static int getQuantityOfFoodOfCategoryInListBill(List<String> listBillOrdered,
            List<String> listIdFoodOfCategory) throws SQLException {
        int result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT SUM(Quantity) AS 'quantityTotal' "
                        + " FROM tblFoodsInBill "
                        + " WHERE " + Support.getSQLListBill(listBillOrdered.size())
                        + " AND " + Support.getSQLListFood(listIdFoodOfCategory.size());
                ps = cnn.prepareStatement(sql);
                for (int i = 1; i <= listBillOrdered.size(); i++) {
                    ps.setString(i, listBillOrdered.get(i - 1));
                }
                for (int i = (listBillOrdered.size() + 1); i < (listBillOrdered.size() + 1 + listIdFoodOfCategory.size()); i++) {
                    ps.setString(i, listIdFoodOfCategory.get((i - (listBillOrdered.size() + 1))));
                }
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("quantityTotal");
                }
            }
        } catch (Exception ex) {
            log.error("Error at getQuantityOfFoodInListBill(List<String> listBillOrdered, List<String> listIdFoodOfCategory): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getQuantityOfFoodInListBill(List<String> listBillOrdered, List<String> listIdFoodOfCategory): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static int getQuantityOfFoodInListBill(List<String> listBillOrdered, String idFood) throws SQLException {
        int result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT SUM(Quantity) AS 'quantityTotal' "
                        + " FROM tblFoodsInBill "
                        + " WHERE " + Support.getSQLListBill(listBillOrdered.size())
                        + " AND IdFood = ?";
                ps = cnn.prepareStatement(sql);
                for (int i = 1; i <= listBillOrdered.size(); i++) {
                    ps.setString(i, listBillOrdered.get(i - 1));
                }
                ps.setString(listBillOrdered.size() + 1, idFood);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("quantityTotal");
                }
            }
        } catch (Exception ex) {
            log.error("Error at getQuantityOfFoodInListBill(List<String> listBillOrdered, String idFood): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getQuantityOfFoodInListBill(List<String> listBillOrdered, String idFood): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<String> getListIdFoodIsOrderedInIdBill(String idFood, List<String> listBillIsOrdered) throws SQLException {
        List<String> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT DISTINCT IdFood"
                        + " FROM tblFoodsInBill "
                        + " WHERE " + Support.getSQLListBill(listBillIsOrdered.size())
                        + " AND NOT IdFood = ?";
                ps = cnn.prepareStatement(sql);
                for (int i = 1; i <= listBillIsOrdered.size(); i++) {
                    ps.setString(i, listBillIsOrdered.get(i - 1));
                }
                ps.setString(listBillIsOrdered.size() + 1, idFood);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    String id = rs.getString("IdFood");
                    result.add(id);
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

    public static List<String> getListIdFoodIsOrderedInIdBillExcepIdFood(String idFood, List<String> listBillIsOrdered) throws SQLException {
        List<String> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT DISTINCT IdFood"
                        + " FROM tblFoodsInBill "
                        + " WHERE " + Support.getSQLListBill(listBillIsOrdered.size())
                        + " AND NOT IdFood = ?";
                ps = cnn.prepareStatement(sql);
                for (int i = 1; i <= listBillIsOrdered.size(); i++) {
                    ps.setString(i, listBillIsOrdered.get(i - 1));
                }
                ps.setString(listBillIsOrdered.size() + 1, idFood);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    String id = rs.getString("IdFood");
                    result.add(id);
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

    public static List<String> getListIdBillIsOrderedHasIdFood(String idFood, List<String> listBillIsOrdered) throws SQLException {
        List<String> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT DISTINCT IdBill"
                        + " FROM tblFoodsInBill "
                        + " WHERE " + Support.getSQLListBill(listBillIsOrdered.size())
                        + " AND idFood = ? ";
                ps = cnn.prepareStatement(sql);
                for (int i = 1; i <= listBillIsOrdered.size(); i++) {
                    ps.setString(i, listBillIsOrdered.get(i - 1));
                }
                ps.setString(listBillIsOrdered.size() + 1, idFood);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    String id = rs.getString("IdBill");
                    result.add(id);
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

    public static int getTimeAppearInListBill(List<String> listBillOrdered, String idFood) throws SQLException {
        int result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT COUNT(IdFood) AS 'rowcount' FROM tblFoodsInBill "
                        + " WHERE " + Support.getSQLListBill(listBillOrdered.size())
                        + " AND IdFood = ?";
                ps = cnn.prepareStatement(sql);
                for (int i = 1; i <= listBillOrdered.size(); i++) {
                    ps.setString(i, listBillOrdered.get(i - 1));
                }
                ps.setString((listBillOrdered.size() + 1), idFood);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = rs.getInt("rowcount");
                }
            }
        } catch (Exception ex) {
            log.error("Error at getTimeAppearInListBill(List<String> listBillOrdered, String idFood): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getTimeAppearInListBill(List<String> listBillOrdered, String idFood): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<String> getListIdBillInOrderContainNameFood(List<String> listBillOrdered, String nameFood) throws SQLException {
        List<String> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT DISTINCT IdBill"
                        + " FROM tblFoodsInBill"
                        + " WHERE " + Support.getSQLListBill(listBillOrdered.size())
                        + " AND NameFood LIKE ? ";
                ps = cnn.prepareStatement(sql);
                for (int i = 1; i <= listBillOrdered.size(); i++) {
                    ps.setString(i, listBillOrdered.get(i - 1));
                }
                ps.setString(listBillOrdered.size() + 1, "%" + nameFood + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(rs.getString("IdBill"));
                }
            }
        } catch (Exception ex) {
            log.error("Error at getListIdBillInOrderContainNameFood(List<String> listBillOrdered, String nameFood): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListIdBillInOrderContainNameFood(List<String> listBillOrdered, String nameFood): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<String> getListIdFoodInOrderedBillContainName(List<String> listBillOrdered, String nameFood) throws SQLException {
        List<String> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT DISTINCT IdFood FROM tblFoodsInBill"
                        + " WHERE " + Support.getSQLListBill(listBillOrdered.size())
                        + " AND NameFood LIKE ?";
                ps = cnn.prepareStatement(sql);
                for (int i = 1; i <= listBillOrdered.size(); i++) {
                    ps.setString(i, listBillOrdered.get(i - 1));
                }
                ps.setString(listBillOrdered.size() + 1, "%" + nameFood + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    String idFood = rs.getString("IdFood");
                    result.add(idFood);
                }
            }
        } catch (Exception ex) {
            log.error("Error at getListIdFoodInOrderedBillContainName(List<String> listBillOrdered, String nameFood): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListIdFoodInOrderedBillContainName(List<String> listBillOrdered, String nameFood) " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<String> listIdFoodInOrderedBill(List<String> listBillOrdered) throws SQLException {
        List<String> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT DISTINCT IdFood FROM tblFoodsInBill"
                        + " WHERE " + Support.getSQLListBill(listBillOrdered.size());
                ps = cnn.prepareStatement(sql);
                for (int i = 1; i <= listBillOrdered.size(); i++) {
                    ps.setString(i, listBillOrdered.get(i - 1));
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    String idFood = rs.getString("IdFood");
                    result.add(idFood);
                }
            }
        } catch (Exception ex) {
            log.error("Error at listFoodInOrderedBill(List<BillDTO> listBillOrdered): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at listFoodInOrderedBill(List<BillDTO> listBillOrdered): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static boolean removeAFoodInBill(String idBill, String idFood) throws SQLException {
        boolean result = false;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "DELETE FROM tblFoodsInBill WHERE IdBill = ? AND IdFood = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, idBill);
                ps.setString(2, idFood);
                ps.executeUpdate();
                result = true;
            }
        } catch (Exception ex) {
            log.error("Error at removeAFoodInBill(String idBill): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at removeAFoodInBill(String idBill): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static float getTotalInBill(String idBill) throws SQLException {
        float result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT Total FROM tblFoodsInBill WHERE IdBill = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, idBill);
                rs = ps.executeQuery();
                while (rs.next()) {
                    result = result + rs.getFloat("Total");
                }
            }
        } catch (Exception ex) {
        } finally {
            closeConnection();
        }
        return result;
    }

    public static boolean updateFoodInBill(FoodsInBillDTO dto) throws SQLException {
        boolean result = false;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "UPDATE tblFoodsInBill SET NameFood = ?, Quantity = ? , StatusFoodID = ?, Price = ?, Total = ? "
                        + " WHERE IdBill = ? AND IdFood = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, dto.getNameFood());
                ps.setInt(2, dto.getQuantity());
                ps.setInt(3, dto.getStatusFoodID());
                ps.setFloat(4, dto.getPrice());
                ps.setFloat(5, dto.getTotal());
                ps.setString(6, dto.getIdBill());
                ps.setString(7, dto.getIdFood());
                ps.executeUpdate();
                result = true;
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at updateFoodInBill(FoodsInBillDTO dto): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at updateFoodInBill(FoodsInBillDTO dto): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static boolean checkContainFood(String idBill, String idFood) throws SQLException {
        boolean result = false;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdFood FROM tblFoodsInBill WHERE IdBill = ? AND IdFood = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, idBill);
                ps.setString(2, idFood);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        } catch (Exception ex) {
            log.error("Error at checkContainFood(String idBill, String idFood): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at checkContainFood(String idBill, String idFood): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static FoodsInBillDTO getFoodInBill(String idBill, String idFood) throws SQLException {
        FoodsInBillDTO result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT Quantity, StatusFoodID, Price, Total  FROM tblFoodsInBill"
                        + " WHERE IdBill = ? AND IdFood = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, idBill);
                ps.setString(2, idFood);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = new FoodsInBillDTO(idBill, idFood, rs.getInt("Quantity"), rs.getInt("StatusFoodID"),
                            rs.getFloat("Price"), rs.getFloat("Total"));
                }
            }
        } catch (Exception ex) {
            log.error("Error at getFoodInBill(String idBill, String idFood): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getFoodInBill(String idBill, String idFood): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<FoodsInBillDTO> getListFoodInBill(String idBill) throws SQLException {
        List<FoodsInBillDTO> result = null;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT IdFood, NameFood, Quantity, StatusFoodID, Price, Total"
                        + " FROM tblFoodsInBill"
                        + " WHERE IdBill = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, idBill);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (result == null) {
                        result = new ArrayList<>();
                    }
                    result.add(new FoodsInBillDTO(idBill, rs.getString("IdFood"), rs.getInt("Quantity"), rs.getInt("StatusFoodID"),
                            rs.getFloat("Price"), rs.getFloat("Total"), rs.getString("NameFood")));
                }

            }
        } catch (Exception ex) {
            log.error("Error at getListFoodInBill(String idBill): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getListFoodInBill(String idBill): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static int getNumProductInBill(String idBill) throws SQLException {
        int result = 0;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "SELECT Quantity FROM tblFoodsInBill WHERE IdBill = ?";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, idBill);
                rs = ps.executeQuery();
                while (rs.next()) {
                    result = result + rs.getInt("Quantity");
                }
            }
        } catch (Exception ex) {
            log.error("Error at getNumProductInBill(String idBill): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at getNumProductInBill(String idBill): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }

    public static boolean insertProductToBill(FoodsInBillDTO dto) throws SQLException {
        boolean result = false;
        try {
            cnn = DBUtils.getConnection();
            if (cnn != null) {
                String sql = "INSERT INTO tblFoodsInBill(IdBill, IdFood, NameFood, Quantity, StatusFoodID, Price, Total) "
                        + " VALUES(?,?,?,?,?,?,?) ";
                ps = cnn.prepareStatement(sql);
                ps.setString(1, dto.getIdBill());
                ps.setString(2, dto.getIdFood());
                ps.setString(3, dto.getNameFood());
                ps.setInt(4, dto.getQuantity());
                ps.setInt(5, dto.getStatusFoodID());
                ps.setFloat(6, dto.getPrice());
                ps.setFloat(7, dto.getTotal());
                ps.executeUpdate();
                result = true;
            }
        } catch (SQLException | NamingException ex) {
            log.error("Error at insertProductToBill(FoodsInBillDTO dto): " + ex.toString());
            SendMailSSL.sendToAdmin("Error at insertProductToBill(FoodsInBillDTO dto): " + ex.toString(), "Error!!");
        } finally {
            closeConnection();
        }
        return result;
    }
}
