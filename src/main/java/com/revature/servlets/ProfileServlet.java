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

public class ProfileServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                String username = session.getAttribute("username").toString();
                String password = session.getAttribute("password").toString();
                User user = DatabaseHandler.getDbHandler().getUser(username, password);
                if (user.getType().equals("Employee")) {
                    request.getRequestDispatcher("employee-home.html").include(request, response);
                    out.println(getProfile(user));
                } else if (user.getType().equals("Manager")) {
                    request.getRequestDispatcher("manager-home.html").include(request, response);
                    out.println(getProfile(user));
                } else {
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            request.getRequestDispatcher("logout").include(request, response);
        }
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                String username = session.getAttribute("username").toString();
                String password = session.getAttribute("password").toString();
                User user = DatabaseHandler.getDbHandler().getUser(username, password);
                String newUsername = request.getParameter("username");
                String newPassword = request.getParameter("password");
                String newEmail = request.getParameter("email");
                if (!user.getUsername().equals(newUsername) || newUsername.isEmpty()) {
                    DatabaseHandler.getDbHandler().updateUsername(user.getUser_id(), newUsername);
                    session.setAttribute("username", newUsername);
                }
                if (!user.getPassword().equals(newPassword) || newPassword.isEmpty()) {
                    DatabaseHandler.getDbHandler().updatePassword(user.getUser_id(), newPassword);
                    session.setAttribute("password", newPassword);
                }
                if (!user.getEmail().equals(newEmail) || newEmail.isEmpty()) {
                    DatabaseHandler.getDbHandler().updateEmail(user.getUser_id(), newEmail);
                }
                doGet(request, response);
            }
        } catch (Exception e) {
            request.getRequestDispatcher("logout").include(request, response);
        }
        out.close();
    }

    public String getProfile(User user) {

        String firstName = user.getFirst_Name();
        String lastName = user.getLast_Name();
        String userName = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        String out = "";

        out = "<div class=\"container mt-2\">\n" +
                "   <form action=\"profile\" method=\"post\">\n" +
                "    <div class=\"form-row\">\n" +
                "     <div class=\"col-sm mb-2\">\n" +
                "      <label for=\"firstName\">First name</label>\n" +
                "      <input type=\"text\" class=\"form-control\" id=\"firstName\" name=\"first\" value=\"" + firstName + "\" " +
                "required disabled />\n" +
                "     </div>\n" +
                "     <div class=\"col-sm mb-2\">\n" +
                "      <label for=\"lastName\">Last name</label>\n" +
                "      <input type=\"text\" class=\"form-control\" id=\"lastName\" name=\"last\" value=\"" + lastName + "\" " +
                "required disabled />\n" +
                "     </div>\n" +
                "    </div>\n" +
                "    <div class=\"form-row\">\n" +
                "     <div class=\"col-sm mb-2\">\n" +
                "      <label for=\"Username\">Username</label>\n" +
                "      <input type=\"text\" class=\"form-control\" id=\"Username\" name=\"username\" " +
                "value=\"" + userName + "\" required disabled />\n" +
                "     </div>\n" +
                "     <div class=\"col-sm mb-2\">\n" +
                "      <label for=\"Password\">Password</label>\n" +
                "      <input type=\"text\" class=\"form-control\" id=\"Password\" name=\"password\" " +
                "value=\"" + password + "\" required disabled />\n" +
                "     </div>\n\n" +
                "       <div class=\"col-sm mb-2\">\n" +
                "       <label for=\"lastName\">Email</label>\n" +
                "       <input type=\"text\" class=\"form-control\" id=\"email\" name=\"email\" value=\"" + email +
                "\" " +
                "       required disabled />\n" +
                "       </div>" +
                "    </div>\n" +
                "    <button class=\"btn btn-primary\" type=\"button\" id=\"editButton\">Edit</button>\n" +
                "    <button class=\"btn btn-secondary\" type=\"submit\" id=\"submitButton\" disabled>Save</button>\n" +
                "   </form>\n" +
                "   <script>\n" +
                "    const editButton = document.getElementById(\"editButton\");\n" +
                "    editButton.addEventListener(\"click\", () => {\n" +
                "     const password = document.getElementById(\"Password\");\n" +
                "     const inputs = document.getElementsByTagName(\"input\");\n" +
                "     const submitButton = document.getElementById(\"submitButton\");\n" +
                "\n" +
                "     if (password.disabled) {\n" +
                "      for (let el of inputs) {\n" +
                "       if (!(el.id == \"firstName\" || el.id == \"lastName\")){\n" +
                "          el.disabled = false;\n" +
                "         }" +
                "      }\n" +
                "      submitButton.disabled = false;\n" +
                "     } else {\n" +
                "      for (let el of inputs) {\n" +
                "       el.disabled = true;\n" +
                "      }\n" +
                "      submitButton.disabled = true;\n" +
                "     }\n" +
                "     submitButton.classList.toggle(\"btn-secondary\");\n" +
                "     submitButton.classList.toggle(\"btn-success\");\n" +
                "     editButton.classList.toggle(\"btn-primary\");\n" +
                "     editButton.classList.toggle(\"btn-danger\");\n" +
                "    });\n" +
                "   </script>\n" +
                "  </div>";
        return out;
    }

}
