package com.labexam.books.task5;

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

@WebServlet("/bookList")
public class DisplayBooksServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private DBConnectionManager dbConnectionManager;
    private String tableName = "Books";
    private final String selectQuery = "SELECT ID, title, author, price FROM " + tableName;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter printWriter = response.getWriter();
        response.setContentType("text/html");

        // Load MySQL JDBC Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }

        // Execute SQL query and generate HTML table
        try (Connection connection = dbConnectionManager.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            
            ResultSet resultSet = preparedStatement.executeQuery();
            printWriter.println("<table border='1' align='center'>");
            printWriter.println("<tr>");
            printWriter.println("<th>Book ID</th>");
            printWriter.println("<th>Book Title</th>");
            printWriter.println("<th>Book Author</th>");
            printWriter.println("<th>Book Price</th>");
            printWriter.println("<th>Edit</th>");
            printWriter.println("<th>Delete</th>");
            printWriter.println("</tr>");

            // Iterate through the result set and populate the table
            while (resultSet.next()) {
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
}
