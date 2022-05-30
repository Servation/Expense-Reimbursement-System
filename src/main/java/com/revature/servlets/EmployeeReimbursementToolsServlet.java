package com.revature.servlets;

import com.revature.database.DatabaseHandler;
import com.revature.database.User;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class EmployeeReimbursementToolsServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session != null) {
            String username = session.getAttribute("username").toString();
            String password = session.getAttribute("password").toString();
            User user = DatabaseHandler.getDbHandler().getUser(username, password);
            if (user.getType().equals("Employee")) {
                request.getRequestDispatcher("employee-home.html").include(request, response);
                request.getRequestDispatcher("employee-reimbursement-controls.component.html").include(request, response);
                request.getRequestDispatcher("employee-reimbursement-form.component.html").include(request,response);
                if (session.getAttribute("reimbursement").toString().equals("true")) {
                    out.println("<div class='text-success'>Reimbursement recorded</div>");
                } else if (session.getAttribute("reimbursement").toString().equals("false")) {
                    out.println("<div class='container text-success'>Could not record reimbursement</div>");
                }
            }
        } else {
            request.getRequestDispatcher("logout").include(request, response);
        }
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session != null) {
            String username = session.getAttribute("username").toString();
            String password = session.getAttribute("password").toString();
            User user = DatabaseHandler.getDbHandler().getUser(username, password);
            if (user.getType().equals("Employee")) {
                String title = request.getParameter("title");
                double amount = Double.parseDouble(request.getParameter("amount"));
                String date = request.getParameter("date");
                String details = request.getParameter("detail");
                System.out.println(title);
                System.out.println(amount);
                System.out.println(date);
                System.out.println(details);
                session.setAttribute("reimbursement", true);
                doGet(request, response);
            }
        } else {
            request.getRequestDispatcher("logout").include(request, response);
        }
        out.close();
    }
}
