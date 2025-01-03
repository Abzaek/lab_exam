package com.todo.todo_test.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.todo.todo_test.db.DBConnectionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BookRegistrationServlet extends HttpServlet {

    private final DBConnectionManager dbManager;

    // Constructor for dependency injection
    public BookRegistrationServlet(DBConnectionManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve book details from the POST request
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String priceStr = request.getParameter("price");

        double price = 0.0;
        if (priceStr != null && !priceStr.isEmpty()) {
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                response.setContentType("text/html");
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h3>Invalid price format.</h3>");
                response.getWriter().println("<p><a href=\"index.html\">Go Back</a></p>");
                response.getWriter().println("</body></html>");
                return;
            }
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Open connection to the database
            dbManager.openConnection();
            Connection conn = dbManager.getConnection();

            // SQL query to insert book details into the Books table
            String sql = "INSERT INTO Books (title, author, price) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setDouble(3, price);

            // Execute the query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                // Respond with a success message
                out.println("<html><body>");
                out.println("<h3>Book registered successfully!</h3>");
                out.println("<p><a href=\"index.html\">Go Back</a></p>");
                out.println("</body></html>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Respond with an error message if the database operation fails
            out.println("<html><body>");
            out.println("<h3>Error registering book: " + e.getMessage() + "</h3>");
            out.println("<p><a href=\"index.html\">Go Back</a></p>");
            out.println("</body></html>");
        } finally {
            try {
                dbManager.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}