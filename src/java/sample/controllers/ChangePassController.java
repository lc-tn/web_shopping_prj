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
import sample.user.UserError;

/**
 *
 * @author Lc_Tn
 */
@WebServlet(name = "ChangePassController", urlPatterns = {"/ChangePassController"})
public class ChangePassController extends HttpServlet {

    private static final String ERROR = "changepassword.jsp";
    private static final String SUCCESS = "changepassword.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String mail = request.getParameter("ID");
            String userID = request.getParameter("ID");
            String password = request.getParameter("password");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            UserDAO dao = new UserDAO();
            UserError userError = new UserError();

            if (!newPassword.equals(confirmPassword)) {
                userError.setConfirmError("The confirm password does not match the password!");
            } else {
                boolean checkValid = dao.checkExist(userID, password, mail);

                if (checkValid) {
                    boolean changePass = dao.changePass(userID, newPassword);
                    if (changePass) {
                        url = SUCCESS;
                        request.setAttribute("MESSAGE", "Change password successfully!");
                    }
                } else {
                    userError.setIdError("UserID or password does not valid!");
                }

            }

            request.setAttribute("USER_ERROR", userError);

        } catch (Exception e) {
            log("Error at ChangePassCOntroller: " + e.toString());
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
