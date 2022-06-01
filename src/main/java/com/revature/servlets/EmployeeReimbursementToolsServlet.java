package com.revature.servlets;

import com.revature.database.DatabaseHandler;
import com.revature.database.User;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class EmployeeReimbursementToolsServlet extends HttpServlet {
    //get absolute
    String absolute = "D:\\Google Drive\\Ravature\\project-1\\src\\main\\webapp\\images\\";
    String relative = "images\\";
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        try {
            if (session != null && !session.isNew()) {
                String username = session.getAttribute("username").toString();
                String password = session.getAttribute("password").toString();
                User user = DatabaseHandler.getDbHandler().getUser(username, password);
                if (user.getType().equals("Employee")) {
                    request.getRequestDispatcher("employee-home.html").include(request, response);
                    request.getRequestDispatcher("employee-reimbursement-controls.component.html").include(request,
                            response);
                    request.getRequestDispatcher("employee-reimbursement-form.component.html").include(request,
                            response);
                } else {
                    throw new NoLoginException();
                }
            } else {
                throw new NoLoginException();
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
        if (session != null) {
            request.getRequestDispatcher("employee-home.html").include(request, response);
            request.getRequestDispatcher("employee-reimbursement-controls.component.html").include(request,
                    response);
            request.getRequestDispatcher("employee-reimbursement-form.component.html").include(request,
                    response);
            String username = session.getAttribute("username").toString();
            String password = session.getAttribute("password").toString();
            User user = DatabaseHandler.getDbHandler().getUser(username, password);
            if (user.getType().equals("Employee")) {
                int userID = user.getUser_id();
                String title = request.getParameter("title");
                double amount = Double.parseDouble(request.getParameter("amount"));
                String date = request.getParameter("date");
                String details = request.getParameter("detail");
                Part filePart = request.getPart("picture");
                String picture = filePart.getSubmittedFileName();
                if(!picture.isEmpty()){
                    String absolutePicture = absolute + picture;
                    String relativePicture = relative + picture;
                    filePart.write(absolutePicture);
                    DatabaseHandler.getDbHandler().addingReimbursement(userID,title,amount,details,date, relativePicture);
                    out.println("<div class=' container text-success'>Reimbursement request added</div>");
                }
                else{
                    DatabaseHandler.getDbHandler().addingReimbursement(userID,title,amount,details,date, "");
                    out.println("<div class=' container text-success'>Reimbursement request added</div>");
                }
            }
        } else {
            request.getRequestDispatcher("logout").include(request, response);
        }
        out.close();
    }
}
