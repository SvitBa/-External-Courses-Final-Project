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
    public void init() throws ServletException {
        super.init();
    }

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
        // step1: read car model id from data
        String carModelId = request.getParameter("carModelId");

        // step2:delete car model id from data
        CarModelRepository.deleteCarModelById(Integer.parseInt(carModelId));

        // step3: send back to the list page
        listModel(request, response);
    }

    private void updateCarModel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: read car model data from form
        String carModelId = request.getParameter("carModelId");
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String qualityClass = request.getParameter("qualityClass");

        // step2: create new car model object
        CarModel newCarModel = new CarModel(Integer.parseInt(carModelId), brand, model, qualityClass);

        // step3: update db
        CarModelRepository.updateCarModel(newCarModel);

        // step4: send back to the list page
        listModel(request, response);

    }

    private void loadCarModel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: read car model id from data
        String carModelId = request.getParameter("carModelId");

        // step2: get car model from db
        CarModel carModelById = CarModelRepository.getCarModelId(Integer.parseInt(carModelId));

        // step3: place car model in request attribute
        request.setAttribute("CAR_MODEL_ID", carModelById);

        // step4: send back jsp update page
        RequestDispatcher dispatcher = request.getRequestDispatcher("update_car_model.jsp");
        dispatcher.forward(request, response);

    }

    private void loadCarModelToAddCar (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: read car model id from data
        String carModelId = request.getParameter("carModelId");

        // step2: get car model from db
        CarModel carModelById = CarModelRepository.getCarModelId(Integer.parseInt(carModelId));

        // step3: place car model in request attribute
        request.setAttribute("CAR_MODEL_ID", carModelById);

        // step4: send back jsp update page
        RequestDispatcher dispatcher = request.getRequestDispatcher("add_car.jsp");
        dispatcher.forward(request, response);

    }

    private void addModel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step1: read car model info from data
        String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String qualityClass = request.getParameter("qualityClass");

        // step2: create new car model object
        CarModel newCarModel = new CarModel(brand, model, qualityClass);

        // step3: add car model to db
        CarModelRepository.createCarModel(newCarModel);

        // step4: send back to the list page
        listModel(request, response);

    }

    public void listModel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // step 1: get car model from helper class from db
        List<CarModel> carModelList = null;
        try {
            carModelList = CarModelRepository.getAllCarModel();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }

        // step 2: add car_model to the request object
        request.setAttribute("model_list", carModelList);

        // step 3: get request dispatcher = send to the jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("view_car_model.jsp");

        // step 4: forward call jsp
        dispatcher.forward(request, response);


    }
}
