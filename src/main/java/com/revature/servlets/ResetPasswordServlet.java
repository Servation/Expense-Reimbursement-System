package com.revature.servlets;

import com.revature.database.DatabaseHandler;
import com.revature.database.User;
import com.revature.javamail.JavaMail;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResetPasswordServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        User user = DatabaseHandler.getDbHandler().getUserByEmail(username, email);
        if (user != null) {
            String newPassword = "321";
            DatabaseHandler.getDbHandler().updatePassword(user.getUser_id(), newPassword);
            JavaMail.resetPassword(user);
        }
        response.setContentType("text/html");
        request.getRequestDispatcher("/login.html").include(request, response);

    }
}
