/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.user.UserDAO;
import sample.user.UserDTO;

/**
 *
 * @author Lc_Tn
 */
@WebServlet(name = "AddController", urlPatterns = {"/AddController"})
public class AddController extends HttpServlet {

    private static final String ERROR = "login.jsp";
    private static final String SUCCESS = "ShoppingController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            UserDAO dao = new UserDAO();
            UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");

            String productID = request.getParameter("productID");
            String productName = request.getParameter("productName");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            String userID = user.getUserID();
            String status = "Not ordered yet";

            double total = dao.getTotalPrice(userID, status);
            total += price;

            int orderID = dao.checkOrderID(userID, status);
            if (orderID == 0) {
                orderID = dao.getOrderID() + 1;
                dao.order(orderID, userID, total, status);
            } else {
                dao.updateOrder(orderID, total);
            }

            int orderDetailID = dao.getOrderDetailID() + 1;

            int[] orderDetail = dao.getOrderDetail(status, productID);

            if (orderDetail[0] != 0) {
                int currentQuantity = orderDetail[0];
                currentQuantity += quantity;
                dao.updateQuantity(currentQuantity, orderDetail[1]);
            } else {
                dao.orderDetail(orderDetailID, productID, orderID, price, quantity);
            }
                String page = (String) session.getAttribute("PAGE");
                request.setAttribute("PAGE", page);

                request.setAttribute("MESSAGE", "Add "+productName+ " successfully.");
            if (user != null) {
                url = SUCCESS;
            } else {
                url = ERROR;
            }
        } catch (Exception e) {
            log("Error at AddController: " + e.toString());
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
