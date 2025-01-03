package com.todo.todo_test.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "DispatcherServlet", urlPatterns = { "/" })
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Delegate routing to a common handler
        routeRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Delegate routing to a common handler
        routeRequest(request, response);
    }

    private void routeRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/registerBook":
                request.getRequestDispatcher("/bookRegistrationServlet").forward(request, response);
                break;

            case "/viewBooks":
                request.getRequestDispatcher("/displayBooksServlet").forward(request, response);
                break;

            case "/searchBooks":
                request.getRequestDispatcher("/searchBooksServlet").forward(request, response);
                break;

            default:
                response.setContentType("text/html");
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h3>Error: No handler found for " + path + "</h3>");
                response.getWriter().println("<p><a href=\"index.html\">Go Back</a></p>");
                response.getWriter().println("</body></html>");
                break;
        }
    }
}