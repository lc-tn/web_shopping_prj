/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.shopping.UserCart;
import sample.user.UserDAO;
import sample.user.UserDTO;

/**
 *
 * @author Lc_Tn
 */
@WebServlet(name = "RemoveController", urlPatterns = {"/RemoveController"})
public class RemoveController extends HttpServlet {

    private final static String SUCCESS = "viewCart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = SUCCESS;
        try {
            HttpSession session = request.getSession();
            UserDAO dao = new UserDAO();
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");

            String userID = user.getUserID();
            String productId = request.getParameter("productID");
            double total = 0;
            String status = "Not ordered yet";

            List<UserCart> userCarts = dao.viewCart(status, userID);
            int orderID = userCarts.get(0).getOrderID();

            dao.removeCart(orderID, productId, status);

            userCarts = dao.viewCart(status, userID);
            for (int i = 0; i < userCarts.size(); i++) {
                total += userCarts.get(i).getProductPrice() * userCarts.get(i).getCartQuantity();
            }

            boolean check = false;
            if (total == 0) {
                check = dao.removeOrder(orderID, status);
            } else {
                check = dao.editCart(0, status, productId, total, orderID);
            }

            if (check) {
                if (userCarts != null) {
                    session.setAttribute("TOTAL_ORDER_PRICE", userCarts.get(0).getTotal());
                }
                session.setAttribute("CART", userCarts);
                url = SUCCESS;
            }
        } catch (Exception e) {
            log("Error at RemoveController: " + e.toString());
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
