package com.revature.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class ManagementToolsServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session != null) {
            String username = session.getAttribute("username").toString();
            if (username.equals("admin")) {
                request.getRequestDispatcher("manager-home.html").include(request, response);
                request.getRequestDispatcher("management-tools.component.html").include(request, response);
            }
        } else {
            request.getRequestDispatcher("logout").include(request, response);
        }
        out.close();
    }
}