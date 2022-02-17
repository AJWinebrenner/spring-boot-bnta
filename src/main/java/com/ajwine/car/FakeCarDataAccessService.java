package com.ajwine.car;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository("fake")
public class FakeCarDataAccessService implements CarDAO {

    private List<Car> db;

    public FakeCarDataAccessService() {
        this.db = new ArrayList<>();
    }

    @Override
    public Car selectCarById(Integer id) {
        for (Car car : db) {
            if (Objects.equals(id, car.getId())) {
                return car;
            }
        }
        return null;
    }

    @Override
    public List<Car> selectAllCars() {
        return db;
    }

    @Override
    public int insertCar(Car car) {
        db.add(car);
        return 1;
    }

    @Override
    public int deleteCar(Integer id) {
        Car car = selectCarById(id);
        if (car != null) {
            db.remove(car);
            return 1;
        }
        return 0;
    }

    @Override
    public int updateCar(Integer id, Car update) {
        Car car = selectCarById(id);
        if (car != null) {
            db.set(db.indexOf(car), update);
            return 1;
        }
        return 0;
    }
}
