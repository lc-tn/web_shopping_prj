/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.shopping.Product;
import sample.shopping.UserCart;
import sample.user.UserDAO;
import sample.user.UserError;

/**
 *
 * @author Lc_Tn
 */
public class CheckoutController extends HttpServlet {

    private static final String ERROR = "viewCart.jsp";
    private static final String SUCCESS = "viewCart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;

        try {
            HttpSession session = request.getSession();
            UserDAO dao = new UserDAO();
            UserError userError = new UserError();

            List<UserCart> userCart = (List) session.getAttribute("CART");

            String phone = request.getParameter("phone");
            String address = request.getParameter("address") + ", Dis " + request.getParameter("district") + ", " + request.getParameter("city") + " City";
            String currentStatus = "Not ordered yet";
            String newStatus = "Ordered";

//            List<UserCart> dataCart = dao.viewCart(currentStatus, userID);
            int orderID = userCart.get(0).getOrderID();
            String quantityError = null;
            boolean checkQuantity = true;
            for (int i = 0; i < userCart.size(); i++) {
                String productID = userCart.get(i).getProductId();
                String productImage = userCart.get(i).getProductImage();
                String productName = userCart.get(i).getProductName();
                double price = userCart.get(i).getProductPrice();
                int cartQuantity = userCart.get(i).getCartQuantity();
                int productQuantity = dao.getProductQuantity(productID);
                if (productQuantity < cartQuantity) {
                    checkQuantity = false;
                    userError.setQuantityError(productName + " does not have enough quantities! Only " + productQuantity + " left.");
                    session.setAttribute("USER_ERROR", userError);
                } else {
                    if (phone.length() >= 10 && phone.length() <= 12) {
                        productQuantity -= cartQuantity;
                        Product product = new Product(productID, productName, productImage, price, productQuantity);
                        dao.updateProduct(product);
                    } else {
                        checkQuantity = false;
                        userError.setPhoneError("The phone number must have the length between 10 and 12");
                        request.setAttribute("USER_ERROR", userError);
                    }
                }
            }
            if (checkQuantity) {
                boolean check = dao.checkout(newStatus, phone, address, orderID, currentStatus);
                if (check) {
                    userCart = null;
                    session.setAttribute("CART", userCart);
                    request.setAttribute("MESSAGE", "Checkout successfully!");
                    url = SUCCESS;
                }
            }
        } catch (Exception e) {
            log("Error at CheckoutController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
