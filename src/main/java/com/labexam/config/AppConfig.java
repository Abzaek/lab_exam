package com.labexam.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.labexam.application.Application;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;

@Configuration
public class AppConfig implements WebApplicationInitializer {

	@Override
	public void onStartup(@NonNull ServletContext servletContext) throws ServletException {
		// Create the Spring application context
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		// Register the main application configuration class
		applicationContext.register(Application.class);
		// Associate the application context with the servlet context
		applicationContext.setServletContext(servletContext);
		// Add a listener to manage the lifecycle of the root application context
		servletContext.addListener(new ContextLoaderListener(applicationContext));

		// Register and configure the DispatcherServlet
		ServletRegistration.Dynamic dispatcherRegistration = servletContext.addServlet(
				"dispatcher", 
				new com.labexam.books.task8.DispatcherServlet());
		dispatcherRegistration.setLoadOnStartup(1);
		dispatcherRegistration.addMapping("/");
	}
}
