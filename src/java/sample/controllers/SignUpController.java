/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.user.UserDAO;
import sample.user.UserError;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Lc_Tn
 */
@WebServlet(name = "SignUpController", urlPatterns = {"/SignUpController"})
public class SignUpController extends HttpServlet {

    private static final String ERROR = "signUp.jsp";
    private static final String SUCCESS = "verifyMail.jsp";

    private static final String SENDER_MAIL = "lamtanloc3@gmail.com";
    private static final String PASSWORD = "yyscybbbsoshxbqa";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String userID = request.getParameter("userID");
            String password = request.getParameter("password");
            String mail = request.getParameter("mail");
            String fullName = request.getParameter("fullName");
            String roleID = request.getParameter("roleID");
            String confirmPassword = request.getParameter("confirmPassword");

            HttpSession session = request.getSession();

            session.setAttribute("fullName", request.getParameter("fullName"));
            session.setAttribute("userID", request.getParameter("userID"));
            session.setAttribute("roleID", request.getParameter("roleID"));
            session.setAttribute("mail", request.getParameter("mail"));
            session.setAttribute("password", request.getParameter("password"));
            session.setAttribute("confirmPassword", request.getParameter("confirmPassword"));

            UserDAO dao = new UserDAO();

            boolean check = true;

            UserError userError = new UserError();

            if (dao.exist(userID)) {
                userError.setIdError("This ID already existed!");
                check = false;
            }

            if (!password.equals(confirmPassword)) {
                userError.setConfirmError("The confirm password does not match the password!");
                check = false;
            }

            if (check) {
                String recipient = request.getParameter("mail");
                String subject = "Ma xac thuc tai khoan";
                String messageText = String.valueOf((int) ((Math.random() * 899999) + 100000));
                session.setAttribute("TEXT", messageText);
                
                Properties props = new Properties();

                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");

                Session sessionMail = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SENDER_MAIL, PASSWORD);
                    }
                });

                try {
                    MimeMessage message = new MimeMessage(sessionMail);
                    message.setFrom(new InternetAddress(SENDER_MAIL));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                    message.setSubject(subject);
                    message.setText(messageText);

                    Transport.send(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                url = SUCCESS;
            }

        } catch (Exception e) {
            log("Error at SignUpController: " + e.toString());
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
