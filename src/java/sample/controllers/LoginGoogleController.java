/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static sample.loginGG.GoogleUtils.getToken;
import static sample.loginGG.GoogleUtils.getUserInfo;
import sample.loginGG.UserGoogleDTO;
import sample.user.UserDAO;

/**
 *
 * @author Lc_Tn
 */
//@WebServlet(name = "LoginGoogleHandler", urlPatterns = {"/LoginGoogleHandler"})
public class LoginGoogleController extends HttpServlet {

    private static final String ERROR = "login.jsp";
    private static final String LOGIN = "LoginController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = ERROR;
        try {
            UserDAO dao = new UserDAO();
            String code = request.getParameter("code");
            HttpSession session = request.getSession();
            
            if (code!=null){
                String accessToken = getToken(code);
                UserGoogleDTO user = getUserInfo(accessToken);
                session.setAttribute("GG_ID", user.getId());
                session.setAttribute("GG_NAME", user.getName());
                session.setAttribute("EMAIL", user.getEmail());
                
                String userID = user.getId();
                String password = "";
                String mail = user.getEmail();
                String name = user.getName();
                String roleID = "US";
                
                boolean check = dao.checkExist(userID, password, mail);
                if (!check){
                    dao.signUp(userID, "", mail, name, roleID);
                    url = LOGIN;
                }else{
                    url = LOGIN;
                }                
            }
        } catch (Exception e) {
            log("Error at LoginGoogleController: " + e.toString());
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
