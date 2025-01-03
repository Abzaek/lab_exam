package com.labexam.books.task8;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * DispatcherServlet handles incoming HTTP requests and forwards them to appropriate resources.
 */
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles GET requests by delegating to handleRequest method.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	/**
	 * Handles POST requests by delegating to handleRequest method.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleRequest(request, response);
	}

	/**
	 * Processes the request and forwards it to the appropriate resource based on the request path.
	 *
	 * @param request  The HttpServletRequest object.
	 * @param response The HttpServletResponse object.
	 * @throws ServletException If a servlet-specific error occurs.
	 * @throws IOException      If an I/O error occurs.
	 */
	private void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve the full request URI
		String requestUri = request.getRequestURI();
		// Retrieve the context path of the application
		String applicationContextPath = request.getContextPath();
		// Determine the relative path by removing the context path from the request URI
		String relativePath = requestUri.substring(applicationContextPath.length());

		// Forward the request based on the relative path
		if (relativePath.startsWith("/bookList")) {
			request.getRequestDispatcher("/bookList").forward(request, response);
		} else if (relativePath.equals("/") || relativePath.startsWith("/index.html")) {
			request.getRequestDispatcher("/index.html").forward(request, response);
		} else if (relativePath.startsWith("/search.html")) {
			request.getRequestDispatcher("/search.html").forward(request, response);
		} else if (relativePath.startsWith("/addBook.html")) {
			request.getRequestDispatcher("/addBook.html").forward(request, response);
		} else if (relativePath.startsWith("/editScreen")) {
			request.getRequestDispatcher("/editScreen").forward(request, response);
		} else if (relativePath.startsWith("/editurl")) {
			request.getRequestDispatcher("/editurl").forward(request, response);
		} else {
			// If the URL does not match any known patterns, respond with a 404 error
			response.getWriter().println("Invalid URL");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}
}