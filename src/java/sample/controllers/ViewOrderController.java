/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.shopping.Order;
import sample.shopping.UserCart;
import sample.user.UserDAO;
import sample.user.UserDTO;

/**
 *
 * @author Lc_Tn
 */
public class ViewOrderController extends HttpServlet {

    private static final String ERROR = "viewCart.jsp";
    private static final String USER = "viewCart.jsp";
    private static final String ADMIN = "admin.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            UserDAO dao = new UserDAO();
            String auth = request.getParameter("auth");
            String search = "";
            String status = "Not ordered yet";
            String statusOrder = "Ordered";
            String statusCancel = "Cancel";
            List<Order> order = new ArrayList<>();
            List<UserCart> orderDetail = new ArrayList<>();

            if ("admin".equals(auth)) {
                List<UserDTO> users = dao.search(search);
                for (int i = 0; i < users.size(); i++) {
                    String userID = users.get(i).getUserID();
                    if (users.get(i).getRoleID().equals("US")) {
                        order.addAll(dao.viewOrder(userID, status));
                        orderDetail.addAll(dao.viewCart(statusOrder, userID));
                        orderDetail.addAll(dao.viewCart(statusCancel, userID));
                    }

                }
                if (order.size() != 0) {
                    request.setAttribute("ORDER", order);
                    session.setAttribute("ORDER_DETAIL", orderDetail);
                } else {
                    session.setAttribute("MESSAGE", "Your purchasing history is empty.");
                }

                url = ADMIN;
            } else {
                String userID = loginUser.getUserID();
                orderDetail = dao.viewCart(statusOrder, userID);
                orderDetail.addAll(dao.viewCart(statusCancel, userID));
                order = dao.viewOrder(userID, status);
                url = USER;
                if (order.size() != 0) {
                    session.setAttribute("ORDER", order);
                    session.setAttribute("ORDER_DETAIL", orderDetail);

                } else {
                    session.setAttribute("MESSAGE", "Your purchasing history is empty.");
                }
            }

        } catch (Exception e) {
            log("Error at ViewOrderController: " + e.toString());
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
