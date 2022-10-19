package com.servlet.example;

import com.db.CarImpl;
import com.entity.Car;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/CarServlet")
public class CarServlet extends HttpServlet {


    @Override
    public void init() throws ServletException {
        super.init();
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     doPost(request, response);
    }

    @Override
    public void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //read command
        String theCommand = request.getParameter("command");

        if (theCommand == null) {
            theCommand = "LIST";
        }

        // route appropriate method
        switch (theCommand) {
            case "ADD":
                addCar(request, response);
                break;
        }

    }

    private void addCar (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: read car model info from data
        String modelId = request.getParameter("carModelId");
        BigDecimal rentPricePerDay = new BigDecimal(request.getParameter("price"));

        // step2: create new car object
        Car newCar = new Car(rentPricePerDay, modelId);

        // step3: add car model to db
        CarImpl.createCar(newCar);

        // step4: send back to the list page
        RequestDispatcher dispatcher = request.getRequestDispatcher("CarModelServlet");
        dispatcher.forward(request, response);

    }



}
