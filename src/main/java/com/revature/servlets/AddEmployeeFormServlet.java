package com.revature.servlets;

import com.revature.database.DatabaseHandler;
import com.revature.database.User;
import com.revature.javamail.JavaMail;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AddEmployeeFormServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {

            PrintWriter out = response.getWriter();
            HttpSession session = request.getSession(false);
            if (session != null) {
                String username = session.getAttribute("username").toString();
                String password = session.getAttribute("password").toString();
                User user = DatabaseHandler.getDbHandler().getUser(username, password);
                if (user.getType().equals("Manager")) {
                    request.getRequestDispatcher("manager-home.html").include(request, response);
                    request.getRequestDispatcher("management-tools.component.html").include(request, response);
                    request.getRequestDispatcher("add-employee.component.html").include(request, response);
                } else {
                    throw new Exception();
                }
            } else {
                throw new Exception();
            }
            out.close();
        } catch (Exception e){
            request.getRequestDispatcher("logout").include(request, response);
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        request.getRequestDispatcher("manager-home.html").include(request, response);
        request.getRequestDispatcher("management-tools.component.html").include(request, response);
        request.getRequestDispatcher("add-employee.component.html").include(request, response);
        String firstName = request.getParameter("first");
        String lastName = request.getParameter("last");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String[] checkbox = request.getParameterValues("manager");
        String type = checkbox != null ? "Manager": "Employee";
        try{
            DatabaseHandler.getDbHandler().addingUser(firstName,lastName,email,type,username,password);
        }catch(Exception e){
            out.println("<div class=\"container text-center text-danger\">User already exist please use another name or email!</div>");
        }
        User user = DatabaseHandler.getDbHandler().getUser(username,password);
        JavaMail.sendUserLogin(user);
        out.close();
    }
}
