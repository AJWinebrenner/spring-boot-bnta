package com.ajwine.car;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("postgres")
public class CarDataAccessService implements CarDAO {

    private JdbcTemplate jdbcTemplate;

    public CarDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Car selectCarById(Integer id) {

        String sql = "SELECT id, regNumber, brand, price FROM cars WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
            new Car(
                rs.getInt("id"),
                rs.getString("regNumber"),
                Brand.valueOf(rs.getString("brand")),
                rs.getDouble("price")
            ),
            id
        );
    }

    @Override
    public List<Car> selectAllCars() {

        String sql = "SELECT id, regNumber, brand, price FROM cars";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Car(
                    rs.getInt("id"),
                    rs.getString("regNumber"),
                    Brand.valueOf(rs.getString("brand")),
                    rs.getDouble("price")
            )
        );
    }

    @Override
    public int insertCar(Car car) {

        String sql = "INSERT INTO cars(regNumber, brand, price) VALUES(?, ?, ?)";

        return jdbcTemplate.update(sql,
                car.getRegNumber(),
                car.getBrand().name(),
                car.getPrice()
        );
    }

    @Override
    public int deleteCar(Integer id) {

        String sql = "DELETE FROM cars WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int updateCar(Integer id, Car update) {
        String sql = "UPDATE cars SET (regNumber, brand, price)=(?, ?, ?) WHERE id = ?";

        Car car = selectCarById(id);

        String newRegNumber = update.getRegNumber();
        if (newRegNumber == null) newRegNumber = car.getRegNumber();
        Brand newBrand = update.getBrand();
        if (newBrand == null) newBrand = car.getBrand();
        Double newPrice =  update.getPrice();
        if (newPrice == null) newPrice = car.getPrice();

        return jdbcTemplate.update(sql,
                newRegNumber,
                newBrand.name(),
                newPrice,
                id
        );
    }
}
