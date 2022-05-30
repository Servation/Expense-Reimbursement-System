package com.revature.servlets;

import com.revature.database.DatabaseHandler;
import com.revature.database.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        DatabaseHandler dbHandler = DatabaseHandler.getDbHandler();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = dbHandler.getUser(username, password);

        if (user == null) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.html");
            requestDispatcher.include(req, resp);
            out.println("<div class=\"container text-center text-danger\">Username or Password is incorrect</div>");
        } else if (user.getType().equals("Manager")) {
            req.getRequestDispatcher("manager-home.html").include(req, resp);
            out.print("<div class='text-center'>Welcome " + username + "!</div>");
            HttpSession session = req.getSession();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("password", user.getPassword());

        } else if (user.getType().equals("Employee")) {
            req.getRequestDispatcher("employee-home.html").include(req, resp);
            out.print("<div class='text-center'>Welcome " + username + "!</div>");
            HttpSession session = req.getSession();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("password", user.getPassword());

        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.html");
            requestDispatcher.include(req, resp);
            out.println("<div class=\"container text-center text-danger\">Username or Password is incorrect</div>");
        }
        out.close();
    }
}
