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

@WebServlet(name = "DeleteBookServlet", urlPatterns = { "/deleteBook" })
public class DeleteBookServlet extends HttpServlet {

    private DBConnectionManager dbManager;

    @Override
    public void init() throws ServletException {
        super.init();
        dbManager = new DBConnectionManager();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Retrieve book ID from the request parameter
        String idStr = request.getParameter("id");

        if (idStr == null || idStr.isEmpty()) {
            out.println("<html><body>");
            out.println("<h3>Error: Book ID is required to delete a book.</h3>");
            out.println("<p><a href=\"index.html\">Go Back</a></p>");
            out.println("</body></html>");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            out.println("<html><body>");
            out.println("<h3>Error: Invalid book ID format.</h3>");
            out.println("<p><a href=\"index.html\">Go Back</a></p>");
            out.println("</body></html>");
            return;
        }

        try {
            // Open database connection
            dbManager.openConnection();
            Connection conn = dbManager.getConnection();

            // Delete query to remove the book by ID
            String sql = "DELETE FROM Books WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                out.println("<html><body>");
                out.println("<h3>Book with ID " + id + " deleted successfully.</h3>");
                out.println("<p><a href=\"viewBooks\">View All Books</a></p>");
                out.println("<p><a href=\"index.html\">Go Back</a></p>");
                out.println("</body></html>");
            } else {
                out.println("<html><body>");
                out.println("<h3>Error: No book found with ID " + id + ".</h3>");
                out.println("<p><a href=\"index.html\">Go Back</a></p>");
                out.println("</body></html>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h3>Error deleting book: " + e.getMessage() + "</h3>");
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