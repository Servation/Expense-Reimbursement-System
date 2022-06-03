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
import java.util.List;
import java.util.stream.Collectors;

public class AddEmployeeFormServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                    request.getRequestDispatcher("add-employee.component.html").include(request, response);
                    out.println("<script>\n\tdocument.getElementById('management-tools').classList.toggle('active')\n</script>");
                    out.println("<script>\n\tdocument.getElementById('add-employee-link').classList.toggle('active')\n</script>");
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

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
                    request.getRequestDispatcher("add-employee.component.html").include(request, response);
                    out.println("<script>\n\tdocument.getElementById('management-tools').classList.toggle('active')\n</script>");
                    String addUsername = request.getParameter("username");
                    List<String> users = DatabaseHandler.getDbHandler().listUser().stream().map(User::getUsername).collect(Collectors.toList());
                    if (!users.contains(addUsername)) {
                        String firstName = request.getParameter("first");
                        String lastName = request.getParameter("last");
                        String email = request.getParameter("email");
                        String addPassword = request.getParameter("password");
                        String[] checkbox = request.getParameterValues("manager");
                        String type = checkbox != null ? "Manager" : "Employee";
                        DatabaseHandler.getDbHandler().addingUser(firstName, lastName, email, type, addUsername,
                                addPassword);
                        out.println("<div class=' container text-success'>User added</div>");
                        User newUser = DatabaseHandler.getDbHandler().getUser(addUsername, addPassword);
                        JavaMail.sendUserLogin(newUser);
                    } else {
                        out.println("<div class=' container text-danger'>A User with that username has already been created</div>");
                    }
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
}
