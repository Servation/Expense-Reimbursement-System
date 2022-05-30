package com.revature.servlets;

import com.revature.database.DatabaseHandler;
import com.revature.database.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class PendingRequestServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session != null) {
            String username = session.getAttribute("username").toString();
            String password = session.getAttribute("password").toString();
            User user = DatabaseHandler.getDbHandler().getUser(username, password);
            if (user.getType().equals("Manager")) {
                request.getRequestDispatcher("manager-home.html").include(request, response);
                request.getRequestDispatcher("management-tools.component.html").include(request, response);
                out.println("TODO");
            }
        } else {
            request.getRequestDispatcher("logout").include(request, response);
        }
        out.close();
    }
}
