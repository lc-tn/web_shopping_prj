/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
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
public class UpdateUserController extends HttpServlet {

    private static final String ERROR = "admin.jsp";
    private static final String SUCCESS = "admin.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String search = "";
            String userID = request.getParameter("userID");
            String fullName = request.getParameter("fullName");
            String mail = request.getParameter("mail");
            String roleID = request.getParameter("roleID");
            UserDAO dao = new UserDAO();
            UserDTO user = new UserDTO(userID, fullName, mail, roleID, "");

            HttpSession session = request.getSession();
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");

            if (loginUser.getUserID().equals(userID)) {
                if (!loginUser.getRoleID().equals(roleID)) {
                    user.setRoleID(loginUser.getRoleID());
                    session.setAttribute("LOGIN_USER", user);
                    request.setAttribute("ERROR", "Can not update your role!");
                } else {
                    session.setAttribute("LOGIN_USER", user);
                }
            }
            boolean checkUpdate = dao.update(user);
            if (checkUpdate) {
                List<UserDTO> listUsers = dao.search(search);
                if (listUsers != null) {
                    request.setAttribute("LIST_USERS", listUsers);
                    url = SUCCESS;
                }
            }

            }catch (Exception e) {
            log("Error at DeleteController: " + e.toString());
        }finally {
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
        protected void doGet
        (HttpServletRequest request, HttpServletResponse response)
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
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
