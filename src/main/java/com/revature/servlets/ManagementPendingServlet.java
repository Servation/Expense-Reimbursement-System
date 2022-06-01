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

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        int reimbursementID = Integer.parseInt(request.getParameter("id"));
        String status = request.getParameter("status");
        System.out.println(reimbursementID);
        System.out.println(status);
        if (status.equals("Deny")) {
            DatabaseHandler.getDbHandler().approveReimbursement(reimbursementID, "Denied");
        } else {
            DatabaseHandler.getDbHandler().approveReimbursement(reimbursementID, "Approved");
        }

    }

    private String allPending(int id) {
        List<Reimbursement> reimbursements = DatabaseHandler.getDbHandler().listPending();
        StringBuilder output = new StringBuilder("<div class='container'>");
        for (Reimbursement reimbursement : reimbursements) {
            if (reimbursement.getUser_ID() != id) {
                output.append("<div>").append("<div class='card w-75 mx-auto mb-3' id='node"+reimbursement.getReimbursement_ID()+"'>\n" +
                        "<h5 class='card-header'>Reimbursement # " + reimbursement.getReimbursement_ID() + " | " + reimbursement.getTitle() + "</h5><div class='card-body'>\n" +
                        "<h6 class='card-title'><strong>User# " + reimbursement.getUser_ID() + "</strong></h6>\n" +
                        "<p class='card-text'><strong>Description</strong> " + reimbursement.getDetail() + "</p>\n" +
                        "<p class='card-text'><strong>Amount</strong> " + NumberFormat.getCurrencyInstance().format(reimbursement.getAmount()) + "</p>\n" +
                        (reimbursement.getImage().isEmpty() ? "":("<img src='" + reimbursement.getImage() + "' class=\"card-img-bottom\" height=" + 600 + " width=" + 200 + ">")) +
                        "<p class='card-text'><small class='text-muted'><strong>Date</strong> " + reimbursement.getDate() + "</small></p>\n" +
                        "<button href='#' class='btn btn-success float-right ml-2' id='approvedID"+reimbursement.getReimbursement_ID()+"'>Approve</button>\n" +
                        "<button href='#' class='btn btn-danger float-right ml-2' id='deniedID"+reimbursement.getReimbursement_ID()+"'>Deny</button></div>\n").append("</div>\n");
                output.append("<script>" + " const nodeEl"+reimbursement.getReimbursement_ID()+" = document.getElementById('node"+reimbursement.getReimbursement_ID()+"') \n" +
                        "const btnApprove"+reimbursement.getReimbursement_ID()+" = document.getElementById('approvedID"+reimbursement.getReimbursement_ID()+"') \n" +
                        "const btnDeny"+reimbursement.getReimbursement_ID()+" = document.getElementById('deniedID"+reimbursement.getReimbursement_ID()+"') \n" +
                        "btnApprove"+reimbursement.getReimbursement_ID()+".addEventListener('click', ()=>{\n" +
                        "let data"+reimbursement.getReimbursement_ID()+" = new FormData();\n" +
                        "data"+reimbursement.getReimbursement_ID()+".append('status', 'Approve')\n" +
                        "data"+reimbursement.getReimbursement_ID()+".append('id', "+reimbursement.getReimbursement_ID()+")\n" +
                        "fetch('/project-1/manage-pending',{body: data"+reimbursement.getReimbursement_ID()+
                        ", method: 'POST'}).then(nodeEl"+reimbursement.getReimbursement_ID()+".remove())\n" +
                        "})\n" +
                        "btnDeny"+reimbursement.getReimbursement_ID()+".addEventListener('click', ()=>{\n" +
                        "let dataDeny"+reimbursement.getReimbursement_ID()+" = new FormData();\n" +
                        "dataDeny"+reimbursement.getReimbursement_ID()+".append('status', 'Deny')\n" +
                        "dataDeny"+reimbursement.getReimbursement_ID()+".append('id', "+reimbursement.getReimbursement_ID()+")\n" +
                        "fetch('/project-1/manage-pending',{body: dataDeny"+reimbursement.getReimbursement_ID()+
                        ", method: 'POST'}).then(nodeEl"+reimbursement.getReimbursement_ID()+".remove())\n" +
                        "})\n" +
                        "</script>");
            }
        }
        return output + "</div>";
    }
}
