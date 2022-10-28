package com.web;

import com.data.repository.CarModelRepository;
import com.data.DBException;
import com.data.entity.CarModel;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/CarModelServlet")
public class CarModelServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //read command
        String theCommand = request.getParameter("command");

        if (theCommand == null) {
            theCommand = "LIST";
        }

        // route appropriate method
        switch (theCommand) {
            case "LIST":
                listModel(request, response);
                break;
            case "ADD":
                addModel(request, response);
                break;
            case "ADDCAR":
                loadCarModelToAddCar(request, response);
                break;
            case "LOAD":
                loadCarModel(request, response);
                break;
            case "UPDATE":
                updateCarModel(request, response);
                break;
            case "DELETE":
                deleteCarModel(request, response);
                break;
            default:
                listModel(request, response);
        }

    }

    private void deleteCarModel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String carModelId = request.getParameter("carModelId");

        CarModelRepository.deleteCarModelById(Integer.parseInt(carModelId));

        listModel(request, response);
    }

    private void updateCarModel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String carModelId = request.getParameter("carModelId");
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String qualityClass = request.getParameter("qualityClass");

        CarModel newCarModel = new CarModel(Integer.parseInt(carModelId), brand, model, qualityClass);

        CarModelRepository.updateCarModel(newCarModel);

        listModel(request, response);

    }

    private void loadCarModel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String carModelId = request.getParameter("carModelId");

        CarModel carModelById = CarModelRepository.getCarModelId(Integer.parseInt(carModelId));

        request.setAttribute("CAR_MODEL_ID", carModelById);

        RequestDispatcher dispatcher = request.getRequestDispatcher("update_car_model.jsp");
        dispatcher.forward(request, response);

    }

    private void loadCarModelToAddCar (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String carModelId = request.getParameter("carModelId");

        CarModel carModelById = CarModelRepository.getCarModelId(Integer.parseInt(carModelId));

        request.setAttribute("CAR_MODEL_ID", carModelById);

        RequestDispatcher dispatcher = request.getRequestDispatcher("add_car.jsp");
        dispatcher.forward(request, response);

    }

    private void addModel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String qualityClass = request.getParameter("qualityClass");

        CarModel newCarModel = new CarModel(brand, model, qualityClass);

        CarModelRepository.createCarModel(newCarModel);
        listModel(request, response);

    }

    public void listModel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CarModel> carModelList = null;
        try {
            carModelList = CarModelRepository.getAllCarModel();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("model_list", carModelList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("view_car_model.jsp");
        dispatcher.forward(request, response);


    }
}
