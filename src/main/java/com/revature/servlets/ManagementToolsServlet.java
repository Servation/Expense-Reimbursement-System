package com.revature.servlets;

import com.revature.database.DatabaseHandler;
import com.revature.database.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ManagementToolsServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        try {
            PrintWriter out = response.getWriter();
            HttpSession session = request.getSession(false);
            if (session != null && !session.isNew()) {
                String username = session.getAttribute("username").toString();
                String password = session.getAttribute("password").toString();
                User user = DatabaseHandler.getDbHandler().getUser(username, password);
                if (user.getType().equals("Manager")) {
                    request.getRequestDispatcher("manager-home.html").include(request, response);
                    request.getRequestDispatcher("management-tools.component.html").include(request, response);
                    out.println(allEmployees(username));
                } else {
                    throw new NoLoginException();
                }
            } else {
                throw new NoLoginException();
            }
            out.close();
        } catch (Exception e) {
            request.getRequestDispatcher("logout").include(request, response);
        }
    }

    private String allEmployees(String username) {
        List<User> users = DatabaseHandler.getDbHandler().listUser();
        StringBuilder output = new StringBuilder("<div class='container'>");
        for (User user : users) {
            if (!user.getUsername().equals(username)) {
                output.append("<div>").append("<div class='card w-75 mx-auto mb-3'>\n" +
                        "<h5 class='card-header'>" + user.getFirst_Name() + " " + user.getLast_Name() + "#" + user.getUser_id() + "</h5>\n" +
                        "<div class='card-body'>" +
                        "<p class='card-text'>Username: " + user.getUsername() + "</p>" +
                        "<p class='card-text'>" + user.getEmail() + "</p>" +
                        "<form action='manage-employee' method='get'>\n" +
                        "<input type='hidden' name='id' value='" + user.getUser_id() + "'>\n" +
                        "<input type='submit' value='Reimbursements' class='btn btn-primary float-right' />\n" +
                        "</form></div></div>").append("</button>");


            }
        }
        return output + "</div>";
    }
}
