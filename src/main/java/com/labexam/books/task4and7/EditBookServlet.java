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
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;

import com.labexam.task1and3.DBConnectionManager;

@WebServlet("/editurl")
public class EditBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private DBConnectionManager manager;

	// Table name for books
	private static final String TABLE_NAME = "Books";

	// SQL query to update book details
	private static final String UPDATE_QUERY = "UPDATE " + TABLE_NAME + " SET title=?, author=?, price=? WHERE id=?";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// Set response content type to HTML
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();

		// Retrieve and parse book ID from request parameters
		int id = Integer.parseInt(req.getParameter("id"));

		// Retrieve book details from request parameters
		String title = req.getParameter("title");
		String author = req.getParameter("author");
		String price = req.getParameter("price");

		try {
			// Load MySQL JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}

		// Try-with-resources to manage database connection and statement
		try (Connection con = manager.openConnection();
			 PreparedStatement ps = con.prepareStatement(UPDATE_QUERY)) {
			 
			// Set parameters for the prepared statement
			ps.setString(1, title);
			ps.setString(2, author);
			ps.setString(3, price);
			ps.setInt(4, id);

			// Execute update and get affected row count
			int count = ps.executeUpdate();

			// Provide feedback based on update result
			if (count == 1) {
				pw.println("<h2>Book is Edited Successfully</h2>");
			} else {
				pw.println("<h2>Book is not Edited Successfully</h2>");
			}
		} catch (SQLException se) {
			// Handle SQL exceptions
			se.printStackTrace();
			pw.println("<h1>" + se.getMessage() + "</h1>");
		} catch (Exception e) {
			// Handle general exceptions
			e.printStackTrace();
			pw.println("<h1>" + e.getMessage() + "</h1>");
		}

		// Provide navigation links
		pw.println("<a href='index.html'>Home</a>");
		pw.println("<br>");
		pw.println("<a href='bookList'>Book List</a>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// Delegate POST requests to doGet method
		doGet(req, res);
	}
}
