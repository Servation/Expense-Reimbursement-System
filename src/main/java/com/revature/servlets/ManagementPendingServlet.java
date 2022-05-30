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

public class ManagementPendingServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
                    out.println(allPending(user.getUser_id()));
                } else {
                    throw new Exception();
                }
            } else {
                throw new Exception();
            }
            out.close();
        } catch (Exception e) {
            request.getRequestDispatcher("logout").include(request, response);
        }
    }

    private String allPending(int id) {
        // TODO: 5/29/2022  
        List<Reimbursement> reimbursements = DatabaseHandler.getDbHandler().listPending();
        StringBuilder output = new StringBuilder("<div class='container'>");
        for (Reimbursement reimbursement : reimbursements) {
            if (reimbursement.getUser_ID() != id) {
                output.append("<div>").append("<div class='card w-75 mx-auto mb-3'>" +
                        "<h5 class='card-header'>Reimbursement # " + reimbursement.getReimbursement_ID() + " | " + reimbursement.getTitle() + "</h5>" +
                        "<div class='card-body'>" +
                        "<h6 class='card-title'><strong>User# " + reimbursement.getUser_ID() + "</strong></h6>" +
                        "<p class='card-text'><strong>Description</strong> " + reimbursement.getDetail() + "</p>" +
                        "<p class='card-text'><strong>Amount</strong> " + NumberFormat.getCurrencyInstance().format(reimbursement.getAmount()) + "</p>" +
                        "<p class='card-text'><small class='text-muted'><strong>Date</strong> " + reimbursement.getDate() + "</small></p>" +
                        "<button href='#' class='btn btn-success float-right ml-2'>Approve</button>" +
                        "<button href='#' class='btn btn-danger float-right ml-2'>Deny</button>" +
                        "</div>").append("</div>");
            }
        }
        return output + "</div>";
    }
}
