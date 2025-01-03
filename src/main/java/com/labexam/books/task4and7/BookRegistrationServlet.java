package com.labexam.books.task4and7;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.labexam.task1and3.DBConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation for registering books.
 */
@Aspect
@WebServlet("/register")
public class BookRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private DBConnectionManager manager;
    private static final String TABLE_NAME = "Books";

    // SQL query for inserting a new book record
    private static final String QUERY = "INSERT INTO " + TABLE_NAME + " (title, author, price) VALUES (?, ?, ?)";

    /**
     * Constructor with dependency injection for DBConnectionManager.
     *
     * @param manager the database connection manager
     */
    @Autowired
    public BookRegistrationServlet(DBConnectionManager manager) {
        super();
        this.manager = manager;
    }

    /**
     * Handles POST requests to register a new book.
     *
     * @param req the HttpServletRequest object
     * @param res the HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Set response content type to HTML
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();

        // Retrieve parameters from the request
        String title = req.getParameter("title");
        String author = req.getParameter("author");
        float price = Float.parseFloat(req.getParameter("price"));

        // Load MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        // Insert the new book into the database
        try (Connection con = manager.openConnection();
             PreparedStatement ps = con.prepareStatement(QUERY)) {
             
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setFloat(3, price);

            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h2>Book is registered successfully</h2>");
            } else {
                pw.println("<h2>Book registration failed</h2>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        }

        // Provide navigation links
        pw.println("<a href='index.html'>Home</a><br>");
        pw.println("<a href='bookList'>Book List</a>");
    }
}
