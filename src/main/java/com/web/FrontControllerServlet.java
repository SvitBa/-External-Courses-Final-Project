package com.web;

import com.web.controller.FrontCommand;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/")
public class FrontControllerServlet extends HttpServlet {
    final static Logger logger = Logger.getLogger(FrontControllerServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FrontCommand command = getCommand(request);
        command.init(getServletContext(), request, response);
        command.process();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FrontCommand command = getCommand(request);
        command.init(getServletContext(), request, response);
        command.process();
    }

    private FrontCommand getCommand(HttpServletRequest request) {
        try {
            logger.info("Request for resource: " + request.getRequestURL());

            String classTemplate = "com.web.controller%sCommand";

            String commandName = request.getServletPath();
            commandName = commandName.substring(1, 2).toUpperCase() + commandName.substring(2);

            String classPath = String.format(classTemplate, commandName);

            Class<?> type = Class.forName(classPath);
            return type.asSubclass(FrontCommand.class).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
