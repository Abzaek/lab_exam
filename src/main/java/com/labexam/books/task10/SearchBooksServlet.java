package com.labexam.books.task10;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;

import com.labexam.task1and3.DBConnectionManager;

@WebServlet("/searchList")
public class SearchBooksServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    @Autowired
    private DBConnectionManager dbConnectionManager;
    
    private static final String TABLE_NAME = "Books";
    private static final String SELECT_QUERY = "SELECT ID, title, author, price FROM " + TABLE_NAME;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Set response content type to HTML
        response.setContentType("text/html");
        PrintWriter printWriter = response.getWriter();

        // Load MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }

        // Retrieve search term from request parameters
        String searchTerm = request.getParameter("searchTerm");

        // Execute database query and generate HTML table with results
        try (Connection connection = dbConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
             
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // Start HTML table
            printWriter.println("<table border='1' align='center'>");
            printWriter.println("<tr>");
            printWriter.println("<th>Book ID</th>");
            printWriter.println("<th>Book Title</th>");
            printWriter.println("<th>Book Author</th>");
            printWriter.println("<th>Book Price</th>");
            printWriter.println("<th>Edit</th>");
            printWriter.println("<th>Delete</th>");
            printWriter.println("</tr>");
            
            // Iterate through result set and populate table rows
            while (resultSet.next()) {
                if (resultSet.getString("title").contains(searchTerm)) {
                    int bookId = resultSet.getInt("ID");
                    String bookTitle = resultSet.getString("title");
                    String bookAuthor = resultSet.getString("author");
                    float bookPrice = resultSet.getFloat("price");
                    
                    printWriter.println("<tr>");
                    printWriter.println("<td>" + bookId + "</td>");
                    printWriter.println("<td>" + bookTitle + "</td>");
                    printWriter.println("<td>" + bookAuthor + "</td>");
                    printWriter.println("<td>" + bookPrice + "</td>");
                    printWriter.println("<td><a href='editScreen?id=" + bookId + "'>Edit</a></td>");
                    printWriter.println("<td><a href='deleteurl?id=" + bookId + "'>Delete</a></td>");
                    printWriter.println("</tr>");
                }
            }
            // End HTML table
            printWriter.println("</table>");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            printWriter.println("<h1>" + sqlException.getMessage() + "</h1>");
        } catch (Exception exception) {
            exception.printStackTrace();
            printWriter.println("<h1>" + exception.getMessage() + "</h1>");
        }
        
        // Link to home page
        printWriter.println("<a href='index.html'>Home</a>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}