package com.labexam.books.task4and7;

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

@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	private DBConnectionManager connectionManager;

	private static final String TABLE_NAME = "Books";
	private static final String SELECT_QUERY = "SELECT title, author, price FROM " + TABLE_NAME + " WHERE id=?";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set response content type to HTML
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Retrieve the book ID from request parameters
		int bookId = Integer.parseInt(request.getParameter("id"));

		try {
			// Load MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException classNotFoundException) {
			classNotFoundException.printStackTrace();
		}

		// Initialize database connection and prepare statement
		try (Connection connection = connectionManager.openConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
			 
			preparedStatement.setInt(1, bookId);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				// Generate HTML form with book details
				out.println("<form action='editurl?id=" + bookId + "' method='post'>");
				out.println("<table align='center'>");

				// Title field
				out.println("<tr>");
				out.println("<td>Title</td>");
				out.println("<td><input type='text' name='title' value='" + resultSet.getString("title") + "'></td>");
				out.println("</tr>");

				// Author field
				out.println("<tr>");
				out.println("<td>Author</td>");
				out.println("<td><input type='text' name='author' value='" + resultSet.getString("author") + "'></td>");
				out.println("</tr>");

				// Price field
				out.println("<tr>");
				out.println("<td>Price</td>");
				out.println("<td><input type='text' name='price' value='" + resultSet.getFloat("price") + "'></td>");
				out.println("</tr>");

				// Submit and Reset buttons
				out.println("<tr>");
				out.println("<td><input type='submit' value='Edit'></td>");
				out.println("<td><input type='reset' value='Cancel'></td>");
				out.println("</tr>");

				out.println("</table>");
				out.println("</form>");
			} else {
				out.println("<h1>No Book Found with ID: " + bookId + "</h1>");
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			out.println("<h1>Error: " + sqlException.getMessage() + "</h1>");
		} catch (Exception exception) {
			exception.printStackTrace();
			out.println("<h1>Error: " + exception.getMessage() + "</h1>");
		}

		// Link to Home page
		out.println("<a href='index.html'>Home</a>");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Delegate POST request to doGet method
		doGet(request, response);
	}
}
