package com.web.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class FrontCommand {

    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public void init(ServletContext context, HttpServletRequest request, HttpServletResponse response) {
        this.context = context;
        this.request = request;
        this.response = response;
    }

    public void process() throws ServletException, IOException {
        switch (request.getMethod()) {
            case "GET" : processGET(); break;
            case "POST" : processPOST(); break;
        }
    }

    public abstract void processGET() throws ServletException, IOException;

    public abstract void processPOST() throws ServletException, IOException;

    public void forward(String target) throws ServletException, IOException {
        target = String.format("/WEB-INF/jsp/%s.jsp", target);
        RequestDispatcher dispatcher = context.getRequestDispatcher(target);
        dispatcher.forward(request, response);
    }

}
