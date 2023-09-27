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
import sample.shopping.Product;
import sample.user.UserDAO;

/**
 *
 * @author Lc_Tn
 */
public class ShoppingController extends HttpServlet {
    
    private final static String ADMIN = "admin.jsp";
    private final static String USER = "shopping.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = null;
        try { 
            String auth =  request.getParameter("auth");
            UserDAO dao = new UserDAO();
            
            String search = request.getParameter("search");
            if (search == null){
                search ="";
            }
            int totalProduct = dao.getTotalPage(search);
            int totalPage = totalProduct/3;
            if (totalProduct %3 !=0){
                totalPage++;
            }
            HttpSession session = request.getSession();
            session.setAttribute("TOTAL_PAGE", totalPage);
            session.setAttribute("SEARCH", search);
            List<Product> products = null;
            String page= request.getParameter("page");
            
            if (page == null){
                page = "0";
                products = dao.viewAll(0, search);
            }else{
                products = dao.viewAll((Integer.parseInt(page)-1)*3, search);
            }
            session.setAttribute("PAGE", page);

            if (products!=null){
                request.setAttribute("LIST_PRODUCTS", products);
            }
            
            if ("admin".equals(auth)){
                url = ADMIN;
            }else{
                url = USER;
            }
        }catch (Exception e) {
            log("Error at ShoppingController: " + e.toString());
        }finally{
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
