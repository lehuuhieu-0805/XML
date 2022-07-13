/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author lehuuhieu
 */
public class DispatchController extends HttpServlet {

    private final String LOGIN_PAGE = "index.html";
    private final String INVALID_PAGE = "invalid.html";
//    private final String LOGIN_CONTROLLER = "LoginController";
//    private final String LOGIN_CONTROLLER = "LoginSAXController";
    private final String LOGIN_CONTROLLER = "LoginStAXController";
    private final String SEARCH_CONTROLLER = "SearchController";
    private final String DELETE_CONTROLLER = "DeleteController";
    private final String UPDATE_CONTROLLER = "UpdateController";
    private final String CREATE_ACCOUNT_CONTROLLER = "CreateAccountController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_PAGE;
        try {
            String button = request.getParameter("btAction");
            if (button == null) {
                url = INVALID_PAGE;
            } else if (button.equals("Login")) {
                url = LOGIN_CONTROLLER;
            } else if (button.equals("Search")) {
                url = SEARCH_CONTROLLER;
            } else if (button.equals("Delete")) {
                url = DELETE_CONTROLLER;
            } else if (button.equals("Update")) {
                url = UPDATE_CONTROLLER;
            } else if(button.equals("Create New Account")){
                url = CREATE_ACCOUNT_CONTROLLER;
            }

        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
