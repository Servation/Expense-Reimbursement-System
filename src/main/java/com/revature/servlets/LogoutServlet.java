package com.revature.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LogoutServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        request.getRequestDispatcher("/index.html").include(request, response);
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("username");
            session.removeAttribute("reimbursement");
            out.println("<div class=\"container\">You are logged out successfully</div>");
        } else {
            out.println("<div class=\"container\">You are not logged in</div>");
        }
        out.close();

    }
}
