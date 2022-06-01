package com.revature.servlets;

import com.revature.database.DatabaseHandler;
import com.revature.database.Reimbursement;
import com.revature.database.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.List;

public class EmployeeResolvedServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            PrintWriter out = response.getWriter();
            HttpSession session = request.getSession(false);
            if (session != null && !session.isNew()) {
                String username = session.getAttribute("username").toString();
                String password = session.getAttribute("password").toString();
                User user = DatabaseHandler.getDbHandler().getUser(username, password);
                if (user.getType().equals("Employee")) {
                    request.getRequestDispatcher("employee-home.html").include(request, response);
                    request.getRequestDispatcher("employee-reimbursement-controls.component.html").include(request, response);
                    out.println(employeeResolved(user.getUser_id()));
                } else {
                    throw new  NoLoginException();
                }
            } else {
                throw new  NoLoginException();
            }
            out.close();
        } catch (Exception e){
            request.getRequestDispatcher("logout").include(request, response);
        }
    }

    private String employeeResolved(int id){
        List<Reimbursement> reimbursements = DatabaseHandler.getDbHandler().listUserReimbursement(id);
        StringBuilder output = new StringBuilder("<div class='container'>");
        for (Reimbursement reimbursement : reimbursements) {
            if (!reimbursement.getStatus().equals("Pending")){
                output.append("<div>").append("<div class='card w-75 mx-auto mb-3'>\n" +
                        "<h5 class='card-header'>Reimbursement # "+reimbursement.getReimbursement_ID()+" | "+reimbursement.getTitle()+"</h5>\n" +
                        "<div class='card-body'>\n" +
                        "<p class='card-text'><strong>Description</strong> "+reimbursement.getDetail()+"</p>\n" +
                        "<p class='card-text'><strong>Amount</strong> "+ NumberFormat.getCurrencyInstance().format(reimbursement.getAmount())+"</p>\n" +
                        "<p class='card-text'><strong>Status</strong> "+reimbursement.getStatus()+"</p>\n" +
                        "<p class='card-text'>\n" +
                        (reimbursement.getImage().isEmpty() ? "":("<img src='" + reimbursement.getImage() + "' class=\"card-img-bottom\" height=" + 600 + " width=" + 200 + ">\n")) +
                        "<small class='text-muted'><strong>Date</strong> "+reimbursement.getDate()+"</small>\n" +
                        "</p></div></div>").append("</div>\n");
            }
        }
        return output + "</div>";
    }
}
