package com.todo.todo_test.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.todo.todo_test.db.DBConnectionManager;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "DisplayBooksServlet", urlPatterns = { "/viewBooks" })
public class DisplayBooksServlet extends HttpServlet {

    private DBConnectionManager dbManager;

    @Override
    public void init() throws ServletException {
        super.init();
        dbManager = new DBConnectionManager();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Display books in HTML table directly
        out.println("<html><body>");
        out.println("<h2>All Books</h2>");
        out.println("<table border='1'>");
        out.println("<tr><th>ID</th><th>Title</th><th>Author</th><th>Price</th></tr>");

        List<Book> books = new ArrayList<>();

        try {
            dbManager.openConnection();
            Connection conn = dbManager.getConnection();

            // Query to fetch books
            String sql = "SELECT id, title, author, price FROM Books";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Iterate through the result set and populate HTML table
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                double price = rs.getDouble("price");

                // Add data to the list of books
                books.add(new Book(id, title, author, price));

                // Build the table row
                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + title + "</td>");
                out.println("<td>" + author + "</td>");
                out.println("<td>" + price + "</td>");
                out.println("</tr>");
            }
            rs.close();
            stmt.close();

            // Generate table close and back link
            out.println("</table>");
            out.println("<p><a href=\"index.html\">Go Back</a></p>");
            out.println("</body></html>");

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error retrieving books: " + e.getMessage() + "</p>");
            out.println("</body></html>");
        } finally {
            try {
                dbManager.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Optional: Forward to a JSP page with the data (if desired)
        request.setAttribute("books", books);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/displayBooks.jsp");
        dispatcher.forward(request, response);
    }

    // Inner class for Book object (optional but useful for JSP forwarding)
    private static class Book {
        private int id;
        private String title;
        private String author;
        private double price;

        public Book(int id, String title, String author, double price) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.price = price;
        }

        // Getters
        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public double getPrice() {
            return price;
        }
    }
}