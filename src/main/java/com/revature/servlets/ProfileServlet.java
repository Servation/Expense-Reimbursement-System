package com.revature.servlets;

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
        PrintWriter out = response.getWriter();HttpSession session = request.getSession(false);
        try {
            if (session != null) {
                String username = (String) session.getAttribute("username");
                if (username.equals("user")) {
                    request.getRequestDispatcher("employee-home.html").include(request, response);
                    // TODO: 5/28/2022 replace object
                    out.println(getProfile(new Object()));
                } else if (username.equals("admin")) {
                    request.getRequestDispatcher("manager-home.html").include(request, response);
                    // TODO: 5/28/2022 replace object
                    out.println(getProfile(new Object()));
                } else {
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            request.getRequestDispatcher("logout").include(request, response);
        }
        out.close();
    }

    public String getProfile(Object o){

        String firstName = "John";
        String lastName = "Doe";
        String userName = "JDoe1";
        String password = "1234";
        String email = "jD@gmail.com";
        String out = "";

        out= "<div class=\"container mt-2\">\n" +
                "   <form action=\"profile\" method=\"post\">\n" +
                "    <div class=\"form-row\">\n" +
                "     <div class=\"col-sm mb-2\">\n" +
                "      <label for=\"firstName\">First name</label>\n" +
                "      <input type=\"text\" class=\"form-control\" id=\"firstName\" name=\"first\" value=\""+firstName+"\" " +
                "required disabled />\n" +
                "     </div>\n" +
                "     <div class=\"col-sm mb-2\">\n" +
                "      <label for=\"lastName\">Last name</label>\n" +
                "      <input type=\"text\" class=\"form-control\" id=\"lastName\" name=\"last\" value=\""+lastName+"\" " +
                "required disabled />\n" +
                "     </div>\n" +
                "    </div>\n" +
                "    <div class=\"form-row\">\n" +
                "     <div class=\"col-sm mb-2\">\n" +
                "      <label for=\"Username\">Username</label>\n" +
                "      <input type=\"text\" class=\"form-control\" id=\"Username\" name=\"username\" " +
                "value=\""+ userName +"\" required disabled />\n" +
                "     </div>\n" +
                "     <div class=\"col-sm mb-2\">\n" +
                "      <label for=\"Password\">Password</label>\n" +
                "      <input type=\"text\" class=\"form-control\" id=\"Password\" name=\"password\" " +
                "value=\""+ password +"\" required disabled />\n" +
                "     </div>\n\n" +
                "       <div class=\"col-sm mb-2\">\n" +
                "       <label for=\"lastName\">Email</label>\n" +
                "       <input type=\"text\" class=\"form-control\" id=\"email\" name=\"email\" value=\""+ email+"\" " +
                "       required disabled />\n" +
                "       </div>" +
                "    </div>\n" +
                "    <button class=\"btn btn-primary\" type=\"button\" id=\"editButton\">Edit</button>\n" +
                "    <button class=\"btn btn-secondary\" type=\"submit\" id=\"submitButton\" disabled>Save</button>\n" +
                "   </form>\n" +
                "   <script>\n" +
                "    const editButton = document.getElementById(\"editButton\");\n" +
                "    editButton.addEventListener(\"click\", () => {\n" +
                "     const firstName = document.getElementById(\"firstName\");\n" +
                "     const inputs = document.getElementsByTagName(\"input\");\n" +
                "     const submitButton = document.getElementById(\"submitButton\");\n" +
                "\n" +
                "     if (firstName.disabled) {\n" +
                "      for (let el of inputs) {\n" +
                "       el.disabled = false;\n" +
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
