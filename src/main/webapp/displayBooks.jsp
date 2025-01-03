<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>All Books</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Author</th>
        <th>Price</th>
    </tr>
    <%
        List<DisplayBooksServlet.Book> books = (List<DisplayBooksServlet.Book>) request.getAttribute("books");
        if (books != null) {
            for (DisplayBooksServlet.Book book : books) {
    %>
    <tr>
        <td><%= book.getId() %></td>
        <td><%= book.getTitle() %></td>
        <td><%= book.getAuthor() %></td>
        <td><%= book.getPrice() %></td>
    </tr>
    <%      }
    } else { %>
    <tr>
        <td colspan="4">No books found</td>
    </tr>
    <% } %>
</table>
<p><a href="index.html">Go Back</a></p>
</body>
</html>