package com.labexam.books.task6;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;

import com.labexam.task1and3.DBConnectionManager;

@WebServlet("/deleteurl")
public class DeleteBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Autowired
    private DBConnectionManager dbConnectionManager;
    
    private static final String TABLE_NAME = "Books";
    private static final String DELETE_QUERY = "DELETE FROM " + TABLE_NAME + " WHERE id=?";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Set response content type to HTML
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        
        // Retrieve the book ID from request parameters
        int bookId = Integer.parseInt(request.getParameter("id"));

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }

        // Attempt to delete the book record from the database
        try (Connection connection = dbConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
             
            // Set the ID parameter in the DELETE query
            preparedStatement.setInt(1, bookId);
            
            // Execute the DELETE operation
            int rowsAffected = preparedStatement.executeUpdate();
            
            // Provide feedback based on the operation result
            if (rowsAffected == 1) {
                writer.println("<h2>Record has been deleted successfully.</h2>");
            } else {
                writer.println("<h2>Record could not be deleted.</h2>");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            writer.println("<h1>Error: " + sqlException.getMessage() + "</h1>");
        } catch (Exception exception) {
            exception.printStackTrace();
            writer.println("<h1>Error: " + exception.getMessage() + "</h1>");
        }
        
        // Provide navigation links
        writer.println("<a href='index.html'>Home</a><br>");
        writer.println("<a href='bookList'>Task List</a>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Delegate POST requests to doGet method
        doGet(request, response);
    }
}