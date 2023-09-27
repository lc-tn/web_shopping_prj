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

/**
 *
 * @author Lc_Tn
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private static final String SHOPPING_PAGE = "shopping.jsp";
    private static final String LOGIN ="Login";
    private static final String LOGIN_CONTROLLER = "LoginController";
    
    private static final String SEARCH = "Search";
    private static final String SEARCH_CONTROLLER = "SearchController";
    
    private static final String UPDATE_USER = "UpdateUser";
    private static final String UPDATE_USER_CONTROLLER = "UpdateUserController";
    
    private static final String UPDATE_PRODUCT = "UpdateProduct";
    private static final String UPDATE_PRODUCT_CONTROLLER = "UpdateProductController";
    
    private static final String ADD = "Add";
    private static final String ADD_CONTROLLER = "AddController";
    
    private static final String VIEW = "View";
    private static final String VIEW_PAGE = "viewCart.jsp";
    
    private static final String VIEW_ORDER = "ViewOrder";
    private static final String VIEW_ORDER_CONTROLLER = "ViewOrderController";
    
    private static final String EDIT = "Edit";
    private static final String EDIT_CONTROLLER = "EditController";
    
    private static final String DELETE_USER = "Delete";
    private static final String DELETE_USER_CONTROLLER = "DeleteUserController";
    
    private static final String DELETE_PRODUCT = "DeleteProduct";
    private static final String DELETE_PRODUCT_CONTROLLER = "DeleteProductController";
    
    private static final String REMOVE = "Remove";
    private static final String REMOVE_CONTROLLER = "RemoveController";
    
    private static final String SIGNUP = "SignUp";
    private static final String SIGNUP_CONTROLLER = "SignUpController";
    
    private static final String LOGOUT = "Logout";
    private static final String LOGOUT_CONTROLLER = "LogoutController";
    
    private static final String CHANGE_PASS = "ChangePass";
    private static final String CHANGE_PASS_CONTROLLER = "ChangePassController";
    
    private static final String SHOPPING = "Shopping";
    private static final String PRODUCT = "Product";
    private static final String SHOPPING_CONTROLLER = "ShoppingController";
    
    private static final String VIEW_CART = "ViewCart";
    private static final String VIEW_CART_CONTROLLER = "ViewCartController";
    
    private static final String UPLOAD = "Upload";
    private static final String UPLOAD_CONTROLLER = "UploadController";
    
    private static final String VERIFY = "VerifyMail";
    private static final String VERIFY_MAIL_CONTROLLER = "VerifyMailController";
    
    private static final String CHECKOUT = "Checkout";
    private static final String CHECKOUT_CONTROLLER = "CheckoutController";
    
    private static final String EDIT_STATUS = "EditStatus";
    private static final String EDIT_STATUS_CONTROLLER = "EditStatusController";
    
    private static final String TOP1 = "Top1";
    private static final String TOP1_CONTROLLER = "Top1Controller";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = SHOPPING_CONTROLLER;
        
        try {
            String action = request.getParameter("action");
            if (action == null){
                url = SHOPPING_CONTROLLER;
            }else if(LOGIN.equals(action)){
                url = LOGIN_CONTROLLER;
            }else if(SEARCH.equals(action)){
                url = SEARCH_CONTROLLER;
            }else if(UPDATE_USER.equals(action)){
                url = UPDATE_USER_CONTROLLER;
            }else if(UPDATE_PRODUCT.equals(action)){
                url = UPDATE_PRODUCT_CONTROLLER;
            }else if(ADD.equals(action)){
                url = ADD_CONTROLLER;
            }else if(VIEW.equals(action)){
                url = VIEW_PAGE;
            }else if(EDIT.equals(action)){
                url = EDIT_CONTROLLER;
            }else if(DELETE_USER.equals(action)){
                url = DELETE_USER_CONTROLLER;
            }else if(DELETE_PRODUCT.equals(action)){
                url = DELETE_PRODUCT_CONTROLLER;
            }else if(REMOVE.equals(action)){
                url = REMOVE_CONTROLLER;
            }else if(SIGNUP.equals(action)){
                url = SIGNUP_CONTROLLER;
            }else if(LOGOUT.equals(action)){
                url = LOGOUT_CONTROLLER;
            }else if(CHANGE_PASS.equals(action)){
                url = CHANGE_PASS_CONTROLLER;
            }else if(SHOPPING.equals(action) || PRODUCT.equals(action)){
                url = SHOPPING_CONTROLLER;
            }else if(UPLOAD.equals(action)){
                url = UPLOAD_CONTROLLER;
            }else if(VERIFY.equals(action)){
                url = VERIFY_MAIL_CONTROLLER;
            }else if(VIEW_CART.equals(action)){
                url = VIEW_CART_CONTROLLER;
            }else if(CHECKOUT.equals(action)){
                url = CHECKOUT_CONTROLLER;
            }else if(VIEW_ORDER.equals(action)){
                url = VIEW_ORDER_CONTROLLER;
            }else if(EDIT_STATUS.equals(action)){
                url = EDIT_STATUS_CONTROLLER;
            }else if(TOP1.equals(action)){
                url = TOP1_CONTROLLER;
            }
        } catch (Exception e) {
            log("Error at MainController: " + e.toString());
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
