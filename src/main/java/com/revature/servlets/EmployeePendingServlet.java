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

public class EmployeePendingServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        try {
            PrintWriter out = response.getWriter();
            HttpSession session = request.getSession(false);
            if (session != null) {
                String username = session.getAttribute("username").toString();
                String password = session.getAttribute("password").toString();
                User user = DatabaseHandler.getDbHandler().getUser(username, password);
                if (user.getType().equals("Employee")) {
                    request.getRequestDispatcher("employee-home.html").include(request, response);
                    request.getRequestDispatcher("employee-reimbursement-controls.component.html").include(request, response);
                    out.println(employeePending(user.getUser_id()));
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

    private String employeePending(int id){
        // TODO: 5/29/2022  
        List<Reimbursement> reimbursements = DatabaseHandler.getDbHandler().listPending();
        StringBuilder output = new StringBuilder("<div class='container'>");
        for (Reimbursement reimbursement : reimbursements) {
            if (reimbursement.getUser_ID() == id){
                output.append("<div>").append(reimbursement).append("</div>");
            }
        }
        return output + "</div>";
    }
}
