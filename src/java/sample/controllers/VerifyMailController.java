/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
@WebServlet(name = "MailController", urlPatterns = {"/MailController"})
public class VerifyMailController extends HttpServlet {

    private static final String ERROR = "verifyMail.jsp";
    private static final String SUCCESS = "login.jsp";
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        response.setContentType("text/html;charset=UTF-8");
            String url = ERROR;

        try {
            HttpSession session = request.getSession();
            UserDAO dao = new UserDAO();
            
            String code = request.getParameter("code");
            
            String userID = (String) session.getAttribute("userID");
            String password = (String) session.getAttribute("password");
            String mail = (String) session.getAttribute("mail");
            String fullName = (String) session.getAttribute("fullName");
            String roleID = (String) session.getAttribute("roleID");
            
            String messageText = (String) session.getAttribute("TEXT");
            if (messageText.equals(code)){
                dao.signUp(userID, password, mail, fullName, roleID);
                request.setAttribute("MESSAGE", "SignUp successfull! Please login.");
                url = SUCCESS;
            }else{
                UserError userError = new UserError();
                userError.setMail("The verified code does not match!");
                request.setAttribute("USER_ERROR", userError);
            }
            

        } catch (Exception e) {
            log("Error at VerifyMailController: " + e.toString());
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
