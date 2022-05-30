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
import java.util.List;

public class ManagementPendingReimbursement extends HttpServlet {
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
        } catch (Exception e){
            request.getRequestDispatcher("logout").include(request, response);
        }
    }

    private String allPending(int id){
        List<Reimbursement> reimbursements = DatabaseHandler.getDbHandler().listPending();
        StringBuilder output = new StringBuilder("<div class='container'>");
        for (Reimbursement reimbursement : reimbursements) {
            if (reimbursement.getUser_ID() != id){
                output.append("<div>").append(reimbursement).append("</div>");
            }
        }
        return output + "</div>";
    }
}
