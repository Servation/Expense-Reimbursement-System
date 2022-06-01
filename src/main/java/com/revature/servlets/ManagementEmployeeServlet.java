package com.revature.servlets;

import com.revature.database.DatabaseHandler;
import com.revature.database.Reimbursement;
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
import java.text.NumberFormat;
import java.util.List;

@WebServlet
@MultipartConfig
public class ManagementEmployeeServlet extends HttpServlet {
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
                    int employeeID = Integer.parseInt(request.getParameter("id"));
                    out.println(employeeRequest(employeeID));
                } else {
                    throw new  NoLoginException();
                }
            } else {
                throw new  NoLoginException();
            }
        } catch (Exception e) {
            request.getRequestDispatcher("logout").include(request, response);
        }

    }
    private String employeeRequest(int id){
        List<Reimbursement> reimbursements = DatabaseHandler.getDbHandler().listUserReimbursement(id);
        StringBuilder output = new StringBuilder("<div class='container'>");
        for (Reimbursement reimbursement : reimbursements) {
                output.append("<div>").append("<div class='card w-75 mx-auto mb-3'>" +
                        "<h5 class='card-header'>Reimbursement # "+ reimbursement.getReimbursement_ID()+" | "+ reimbursement.getTitle()+"</h5>" +
                        "<div class='card-body'>" +
                        "<p class='card-text'><strong>Description</strong> "+reimbursement.getDetail()+"</p>" +
                        "<p class='card-text'><strong>Amount</strong> "+ NumberFormat.getCurrencyInstance().format(reimbursement.getAmount())+"</p>" +
                        "<p class='card-text'>" + (reimbursement.getImage().isEmpty() ? "":("<img src='" + reimbursement.getImage() + "' class=\"card-img-bottom\" height=" + 600 + " width=" + 200 + ">")) +
                        "<small class='text-muted'><strong>Date</strong> "+reimbursement.getDate()+"</small></p></div></div>").append("</div>");

        }
        return output + "</div>";
    }
}
