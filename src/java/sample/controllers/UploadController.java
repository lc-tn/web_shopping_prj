/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import sample.shopping.Product;
import sample.user.UserDAO;
import sample.user.UserError;

/**
 *
 * @author Lc_Tn
 */
@MultipartConfig
@WebServlet(name = "UploadController", urlPatterns = {"/UploadController"})
public class UploadController extends HttpServlet {

    private static final String ERROR = "admin.jsp";
    private static final String SUCCESS = "admin.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            UserDAO dao = new UserDAO();
            HttpSession session = request.getSession();
            UserError error = new UserError();

            String productID = request.getParameter("productID");
            
            if (productID != null) {
                String productName = request.getParameter("productName");

                String imageName = request.getParameter("imageName");
                double price = Double.parseDouble(request.getParameter("price"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                if (!dao.checkProductID(productID)) {
                    Part part = request.getPart("photo");
                    String realPath = request.getServletContext().getRealPath("/images");
                    if (imageName == null) {
                        imageName = new File(part.getSubmittedFileName()).getName();
                    }

                    if (!Files.exists(Paths.get(realPath))) {
                        Files.createDirectory(Paths.get(realPath));
                    }

                    String path = realPath + "/" + imageName + ".jpg";

                    part.write(path);
                    
                    String image = "images/" + imageName + ".jpg";
                    Product product = new Product(image, productID, productName, price, quantity);
                    boolean check = dao.upload(product);
                    if (check) {
                        url = SUCCESS;
                    }
                } else {
                    error.setIdError("This productID already existed!");
                    session.setAttribute("USER_ERROR", error);
                }
            } else {
                request.setAttribute("UPLOAD", 1);
            }
        } catch (Exception e) {
            log("Error at UploadController: " + e.toString());
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
