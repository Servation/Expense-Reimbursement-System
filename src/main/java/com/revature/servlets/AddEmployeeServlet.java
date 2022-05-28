package com.revature.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddEmployeeServlet extends HttpServlet {
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
