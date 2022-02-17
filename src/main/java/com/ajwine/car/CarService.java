package com.ajwine.car;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private CarDAO carDAO;

    public CarService(@Qualifier("postgres") CarDAO carDAO) {
        this.carDAO = carDAO;
    }

    public void registerNewCar(Car car) {
        // business logic. check if reg number is valid and not take
        if (car.getPrice() <= 0) {
            throw new IllegalStateException("Car price cannot be 0 or less");
        }
        int result = carDAO.insertCar(car);

        if (result != 1) {
            throw new IllegalStateException("Could not save car...");
        }
    }

    public List<Car> getAllCars() {
        return carDAO.selectAllCars();
    }

    public Car getCarById(Integer id) {
        Car selected  = carDAO.selectCarById(id);
        if (selected == null) {
            throw new CarNotFoundException("Not found...");
        } else return selected;
    }

    public void deleteCarById(Integer id) {
        if (carDAO.selectCarById(id) == null) {
            throw new CarNotFoundException("Not found...");
        }

        int result = carDAO.deleteCar(id);

        if (result != 1) {
            throw new IllegalStateException("Could not delete car...");
        }
    }

    public void updateCarById(Integer id, Car update) {
        if (carDAO.selectCarById(id) == null) {
            throw new CarNotFoundException("Not found...");
        }

        if (update.getRegNumber() != null) {
            if (update.getRegNumber().matches("[^A-Z0-9 ]+")) {
                throw new IllegalStateException("Registration is invalid...");
            }
        }

        int result = carDAO.updateCar(id, update);

        if (result != 1) {
            throw new IllegalStateException("Could not update car...");
        }
    }
}
