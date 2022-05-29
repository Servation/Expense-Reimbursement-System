package com.revature.servlets;

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
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        try {

            if (session != null) {
                String username = session.getAttribute("username").toString();
                if (username.equals("admin")) {
                    request.getRequestDispatcher("manager-home.html").include(request, response);
                    request.getRequestDispatcher("management-tools.component.html").include(request, response);
                    request.getRequestDispatcher("add-employee.component.html").include(request, response);
                } else {
                    throw new Exception();
                }
            }
        } catch (Exception e){
            request.getRequestDispatcher("logout").include(request, response);
        }
        out.close();
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        request.getRequestDispatcher("manager-home.html").include(request, response);
        request.getRequestDispatcher("management-tools.component.html").include(request, response);
        request.getRequestDispatcher("add-employee.component.html").include(request, response);
        out.write("<div class=\"container text-center text-success\">Employee added</div>");
        out.close();
    }
}
