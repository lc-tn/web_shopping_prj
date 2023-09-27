/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.user;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sample.shopping.Order;
import sample.shopping.Product;
import sample.shopping.UserCart;
import sample.utils.DBUtils;

/**
 *
 * @author Lc_Tn
 */
public class UserDAO {

    private static final String LOGIN = "SELECT roleID, mail, fullName FROM tblUsers WHERE (userID = ? and password = ?) OR (userID = ? and mail = ?)";
    private static final String SIGNUP = "INSERT INTO tblUsers (userID, fullName, mail, password, roleID, status) VALUES (?,?, ?, ?, ?, null)";
    private static final String SEARCH = "SELECT userID, mail, fullName, roleID FROM tblUsers WHERE fullName like ? ";
    private static final String DELETE_USER = "DELETE tblUsers WHERE userID = ?";
    private static final String DELETE_PRODUCT = "DELETE tblProducts WHERE productID = ?";
    private static final String UPDATE = "UPDATE tblUsers SET fullName = ?, roleID = ? WHERE userID = ?";
    private static final String EXIST = "SELECT userID, mail FROM tblUsers WHERE userID = ?";
    private static final String CHANGE_PASS = "UPDATE tblUsers SET password=? WHERE userID=?";
    private static final String CHECK_EXIST = "SELECT * FROM tblUsers WHERE (userID = ? AND password =?) OR (userID = ? AND mail =?)";
    
    //PAGING FUNCTION
    private static final String VIEW_ALL = "SELECT * FROM tblProducts WHERE name like ? ORDER BY productID OFFSET ? ROWS FETCH NEXT 3 ROWS ONLY ";
    
    private static final String TOTAL_PRODUCT = "SELECT * FROM tblProducts WHERE name like ?";
    private static final String ORDER = "INSERT INTO tblOrders (orderID, userID, date, total, status) VALUES (?,?, ?, ?, ?)";
    private static final String UPDATE_ORDER = "UPDATE tblOrders SET total=? WHERE orderID=?";
    private static final String UPDATE_PRODUCT = "UPDATE tblProducts SET productID=?, name=?, image=?, price=?, quantity=? WHERE productID=?";
    private static final String ORDER_DETAIL = "INSERT INTO tblOrderDetail (orderDetailID, productID, orderID, price, quantity) VALUES (?,?, ?, ?, ?)";
    private static final String VIEW_CART = "SELECT p.productID, p.image, p.name, p.price, od.quantity, o.total, od.orderID FROM tblProducts p join tblOrderDetail od on p.productID = od.productID join tblOrders o on od.orderID = o.orderID and o.status like ? and userID like ?";
    private static final String GET_ORDER_ID = "SELECT orderID FROM tblOrders";
    private static final String GET_ORDER_DETAIL_ID = "SELECT orderDetailID FROM tblOrderDetail";
    private static final String GET_ORDER_DETAIL = "SELECT quantity, orderDetailID FROM tblOrderDetail od join tblOrders o on od.orderID = o.orderID AND o.status like ? AND od.productID like ?";
    private static final String UPDATE_QUANTITY = "UPDATE tblOrderDetail SET quantity=? WHERE orderDetailID like ?";
    private static final String CHECK_ORDER_ID = "SELECT orderID FROM tblOrders WHERE status like ? AND userID like ?";
    private static final String GET_TOTAL_PRICE = "SELECT total FROM tblOrders WHERE userID like ? and status like ?";
    private static final String EDIT_QUANTITY_CART = "UPDATE tblOrderDetail SET tblOrderDetail.quantity = ? FROM tblOrderDetail od INNER JOIN tblOrders o on od.orderID=o.orderID AND o.status like ? AND od.productID like ?";
    private static final String EDIT_TOTAL_CART = "UPDATE tblOrders SET tblOrders.total = ? FROM tblOrderDetail od JOIN tblOrders o on od.orderID=o.orderID AND o.status like ? AND od.orderID like ?";
    private static final String REMOVE_CART = "DELETE od FROM tblOrderDetail od join tblOrders o on od.orderID = o.orderID WHERE od.productID like ? AND o.orderID like ? AND o.status like ?";
    private static final String REMOVE_ORDER = "DELETE FROM tblOrders WHERE orderID like ? AND status like ?";
    private static final String GET_PRODUCT_QUANTITY = "SELECT quantity FROM tblProducts WHERE productID like ? ";
    private static final String CHECKOUT = "UPDATE tblOrders SET status=?, phone=?, address=? WHERE orderID=? AND status like ?";
    private static final String GET_ORDER = "SELECT * FROM tblOrders WHERE userID=? and status != ?";
    private static final String EDIT_STATUS = "UPDATE tblOrders SET status=? WHERE orderID=?";
    private static final String UPLOAD = "INSERT INTO tblProducts (productID, name, image, price, quantity) VALUES (?,?, ?, ?, ?)";
    private static final String CHECK_PRODUCTID = "SELECT productID FROM tblProducts WHERE productID like ?";
    
    
    private static final String TOP1 = "SELECT TOP (1) userID, fullName FROM tblUsers";
    
    public UserDTO checkLogin(String userID, String password, String mail) throws SQLException {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOGIN);
                ptm.setString(1, userID);
                ptm.setString(2, password);
                ptm.setString(3, userID);
                ptm.setString(4, mail);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String roleID = rs.getString("roleID");
                    String fullName = rs.getString("fullName");
                    mail = rs.getString("mail");
                    user = new UserDTO(userID, fullName, mail, roleID, "");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            };
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return user;
    }

    public List<UserDTO> search(String search) throws SQLException {
        List<UserDTO> listUsers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH);
                ptm.setString(1, "%" + search + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String userID = rs.getString("userID");
                    String fullName = rs.getString("fullName");
                    String mail = rs.getString("mail");
                    String roleID = rs.getString("roleID");
                    UserDTO user = new UserDTO(userID, fullName, mail, roleID, "***");
                    listUsers.add(user);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            };
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listUsers;
    }
    
    public UserDTO top1() throws SQLException {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(TOP1);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String userID = rs.getString("userID");
                    String fullName = rs.getString("fullName");
                    user = new UserDTO(userID, fullName, "", "", "***");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            };
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return user;
    }
    
    public boolean delete (String userID) throws SQLException{
        boolean checkDelete = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null){
                ptm = conn.prepareStatement(DELETE_USER);
                ptm.setString(1, userID);
                checkDelete = ptm.executeUpdate()>0?true:false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (ptm!=null) ptm.close();
            if (conn!=null) conn.close();
        }
        return checkDelete;
    }
    
    public boolean deleteProduct (String productID) throws SQLException{
        boolean checkDelete = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null){
                ptm = conn.prepareStatement(DELETE_PRODUCT);
                ptm.setString(1, productID);
                checkDelete = ptm.executeUpdate()>0?true:false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (ptm!=null) ptm.close();
            if (conn!=null) conn.close();
        }
        return checkDelete;
    }
    
    public boolean update (UserDTO user) throws SQLException{
        boolean checkUpdate = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null){
                ptm = conn.prepareStatement(UPDATE);
                ptm.setString(1, user.getFullName());
                ptm.setString(2, user.getRoleID());
                ptm.setString(3, user.getUserID());
                checkUpdate = ptm.executeUpdate()>0?true:false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (ptm!=null) ptm.close();
            if (conn!=null) conn.close();
        }
        return checkUpdate;
    }

    public boolean exist(String userID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(EXIST);
                ptm.setString(1, userID);
                rs = ptm.executeQuery();

                if (rs.next()) {
                    check = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            };
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean signUp(String userID, String password, String mail, String fullName, String roleID) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SIGNUP);
                ptm.setString(1, userID);
                ptm.setString(2, fullName);
                ptm.setString(3, mail);
                ptm.setString(4, password);
                ptm.setString(5, roleID);
                rs = ptm.executeQuery();
                check = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean checkExist(String userID, String password, String mail) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_EXIST);
                ptm.setString(1, userID);
                ptm.setString(2, password);
                ptm.setString(3, userID);
                ptm.setString(4, mail);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    check = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            };
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean changePass(String userID, String newPassword) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHANGE_PASS);
                ptm.setString(1, newPassword);
                ptm.setString(2, userID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    check = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public List<Product> viewAll(int page, String search) throws SQLException {
        List<Product> products = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(VIEW_ALL);
                ptm.setString(1, "%"+search+"%");
                ptm.setInt(2, page);
                
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String image = rs.getString("image");
                    String id = rs.getString("productID");
                    String name = rs.getString("name");
                    double price = Double.parseDouble(rs.getString("price"));
                    int quantity = Integer.parseInt(rs.getString("quantity"));
                    Product product = new Product(image, id, name, price, quantity);
                    products.add(product);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return products;
    }

    public int getTotalPage(String search) throws SQLException {
        int total = 0;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(TOTAL_PRODUCT);
                ptm.setString(1, "%"+search+"%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    total++;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return total;
    }

    public boolean order(int orderID, String userID, double total, String status) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        ResultSet rs = null;
        long millis = System.currentTimeMillis();
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(ORDER);
                ptm.setInt(1, orderID);
                ptm.setString(2, userID);
                ptm.setDate(3, new java.sql.Date(millis));
                ptm.setDouble(4, total);
                ptm.setString(5, status);
                rs = ptm.executeQuery();
                check = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean updateOrder(int orderID, double total) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_ORDER);
                ptm.setDouble(1, total);
                ptm.setInt(2, orderID);
                rs = ptm.executeQuery();
                check = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }
    
    public boolean updateProduct(Product product) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_PRODUCT);
                ptm.setString(1, product.getId());
                ptm.setString(2, product.getName());
                ptm.setString(3, product.getImage());
                ptm.setDouble(4, product.getPrice());
                ptm.setInt(5, product.getQuantity());
                ptm.setString(6, product.getId());
                ptm.executeUpdate();
                check = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }


    public boolean orderDetail(int orderDetailID, String productID, int orderID, double price, int quantity) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(ORDER_DETAIL);
                ptm.setInt(1, orderDetailID);
                ptm.setString(2, productID);
                ptm.setInt(3, orderID);
                ptm.setDouble(4, price);
                ptm.setInt(5, quantity);
                rs = ptm.executeQuery();
                check = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public int getOrderID() throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        int orderID = 0;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ORDER_ID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    orderID = Integer.parseInt(rs.getString("orderID"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return orderID;
    }

    public int getOrderDetailID() throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        int orderDetailID = 0;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ORDER_DETAIL_ID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    orderDetailID = Integer.parseInt(rs.getString("orderDetailID"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return orderDetailID;
    }

    public int[] getOrderDetail(String status, String productID) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        int[] info = new int[2];
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ORDER_DETAIL);
                ptm.setString(1, status);
                ptm.setString(2, productID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    info[0] = rs.getInt("quantity");
                    info[1] = rs.getInt("orderDetailID");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return info;
    }

    public boolean updateQuantity(int quantity, int orderDetailID) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_QUANTITY);
                ptm.setInt(1, quantity);
                ptm.setInt(2, orderDetailID);
                rs = ptm.executeQuery();
                check = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public double getTotalPrice(String userID, String status) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        double total = 0;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_TOTAL_PRICE);
                ptm.setString(1, userID);
                ptm.setString(2, status);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    total = Double.parseDouble(rs.getString("total"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return total;
    }

    public int checkOrderID(String userID, String status) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        int orderID = 0;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_ORDER_ID);
                ptm.setString(1, status);
                ptm.setString(2, userID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    orderID = Integer.parseInt(rs.getString("orderID"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return orderID;
    }

    public List<UserCart> viewCart(String status, String userID) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        List<UserCart> cart = new ArrayList<>();
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(VIEW_CART);
                ptm.setString(1, status);
                ptm.setString(2, userID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String image = rs.getString("image");
                    String productID = rs.getString("productID");
                    String productName = rs.getString("name");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    double total = rs.getDouble("total");
                    int orderID = rs.getInt("orderID");
                    UserCart userCart = new UserCart(image, productID, productName, price, quantity, total, orderID);
                    cart.add(userCart);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
            return cart;
        }
    }

    public boolean editCart(int quantity, String status, String productID, double total, int orderID) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm1 = null;
        PreparedStatement ptm2 = null;
        boolean check = false;
        int check1 = 0;
        int check2 = 0;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                if (quantity != 0) {
                    ptm1 = conn.prepareStatement(EDIT_QUANTITY_CART);
                    ptm1.setInt(1, quantity);
                    ptm1.setString(2, status);
                    ptm1.setString(3, productID);
                    check1 = ptm1.executeUpdate();
                }

//                if (total != 0) {
                    ptm2 = conn.prepareStatement(EDIT_TOTAL_CART);
                    ptm2.setDouble(1, total);
                    ptm2.setString(2, status);
                    ptm2.setInt(3, orderID);
                    check2 = ptm2.executeUpdate();
//                }

                check = true;

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm1 != null) {
                ptm1.close();
            }
            if (ptm2 != null) {
                ptm2.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public void removeCart(int orderID, String productID, String status) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(REMOVE_CART);
                ptm.setString(1, productID);
                ptm.setInt(2, orderID);
                ptm.setString(3, status);
                ptm.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public boolean removeOrder(int orderID, String status) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(REMOVE_ORDER);
                ptm.setInt(1, orderID);
                ptm.setString(2, status);
                ptm.executeUpdate();
                check = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean checkout(String newStatus, String phone, String address, int orderID, String currentStatus) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        boolean check = false;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECKOUT);
                ptm.setString(1, newStatus);
                ptm.setString(2, phone);
                ptm.setString(3, address);      
                ptm.setInt(4, orderID);
                ptm.setString(5, currentStatus);
                ptm.executeUpdate();
                check = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }
    
    public int getProductQuantity(String productID) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        int productQuantity = 0;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_PRODUCT_QUANTITY);
                ptm.setString(1, productID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    productQuantity = Integer.parseInt(rs.getString("quantity"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return productQuantity;
    }
    
    public List<Order> viewOrder(String userID, String status) throws SQLException {
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        List<Order> orders = new ArrayList<>();
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ORDER);
                ptm.setString(1, userID);
                ptm.setString(2, status);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    int orderID = rs.getInt("orderID");
                    userID = rs.getString("userID");
                    String date = rs.getString("date");
                    double total = rs.getDouble("total");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    status = rs.getString("status");
                    Order order = new Order(orderID, userID, date, total, phone, address, status);
                    orders.add(order);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
            return orders;
        }
    }
    
    public boolean editStatus (String status, int orderID) throws SQLException{
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null){
                ptm = conn.prepareStatement(EDIT_STATUS);
                ptm.setString(1, status);
                ptm.setInt(2, orderID);
                check = ptm.executeUpdate()>0?true:false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (ptm!=null) ptm.close();
            if (conn!=null) conn.close();
        }
        return check;
    }
    
    public boolean upload (Product product) throws SQLException{
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null){
                ptm = conn.prepareStatement(UPLOAD);
                ptm.setString(1, product.getId());
                ptm.setString(2, product.getName());
                ptm.setString(3, product.getImage());
                ptm.setDouble(4, product.getPrice());
                ptm.setInt(5, product.getQuantity());
                check = ptm.executeUpdate()>0?true:false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (ptm!=null) ptm.close();
            if (conn!=null) conn.close();
        }
        return check;
    }
    
    public boolean checkProductID (String productID) throws SQLException{
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        
        try {
            conn = DBUtils.getConnection();
            if (conn != null){
                ptm = conn.prepareStatement(CHECK_PRODUCTID);
                ptm.setString(1, productID);
                check = ptm.executeUpdate()>0?true:false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (ptm!=null) ptm.close();
            if (conn!=null) conn.close();
        }
        return check;
    }
}
