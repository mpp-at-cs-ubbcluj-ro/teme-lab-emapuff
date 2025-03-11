package ro.mpp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository{

    private JdbcUtils dbUtils;



    private static final Logger logger = LogManager.getLogger(CarsDBRepository.class);

    public CarsDBRepository(Properties props) {
        logger.info("Initializing CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
        logger.info("Fetching all cars from database from a manufactor...");
        List<Car> cars=new ArrayList<>();
        String query = "SELECT manufacturer, year, model FROM Masini where manufacturer =?";

        try (Connection con = dbUtils.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, manufacturerN);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    String manufacturer = resultSet.getString("manufacturer");
                    int year = resultSet.getInt("year");
                    String model = resultSet.getString("model");

                    Car car = new Car(manufacturer, model, year);
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            logger.error("Error fetching cars from database", e);
        }
        return cars;
    }

    @Override
    public List<Car> findBetweenYears(int min, int max) {
        logger.info("Fetching all cars manufactured between {} and {}", min, max);
        List<Car> cars = new ArrayList<>();

        String query = "SELECT manufacturer, year, model FROM Masini WHERE year BETWEEN ? AND ?";

        try (Connection con = dbUtils.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, min);
            stmt.setInt(2, max);

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    String manufacturer = resultSet.getString("manufacturer");
                    int year = resultSet.getInt("year");
                    String model = resultSet.getString("model");

                    Car car = new Car(manufacturer, model, year);
                    cars.add(car);
                }
            }
        } catch (SQLException e) {
            logger.error("Error fetching cars from database", e);
        }

        return cars;
    }

    @Override
    public void add(Car elem) {
        logger.info("Adding a new car to the database: {}", elem);

        String query = "INSERT INTO Masini (manufacturer, year, model) VALUES (?, ?, ?)";

        try (Connection con = dbUtils.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, elem.getManufacturer());
            stmt.setInt(2, elem.getYear());
            stmt.setString(3, elem.getModel());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                logger.info("A new car was inserted successfully!");
            }
        } catch (SQLException e) {
            logger.error("Error adding a new car to the database", e);
        }
    }

    @Override
    public void update(Integer integer, Car elem) {
        logger.info("Updating car with ID {}: {}", integer, elem);

        String query = "UPDATE Masini SET manufacturer = ?, year = ?, model = ? WHERE id = ?";

        try (Connection con = dbUtils.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, elem.getManufacturer());
            stmt.setInt(2, elem.getYear());
            stmt.setString(3, elem.getModel());
            stmt.setInt(4, integer);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                logger.info("Car with ID {} was updated successfully!", integer);
            }
        } catch (SQLException e) {
            logger.error("Error updating car in the database", e);
        }
    }

    @Override
    public Iterable<Car> findAll() {
        logger.info("Fetching all cars from database...");
        List<Car> cars = new ArrayList<>();

        String query = "SELECT manufacturer, year, model FROM Masini";

        try (Connection con = dbUtils.getConnection();
             PreparedStatement stmt = con.prepareStatement(query);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                String manufacturer = resultSet.getString("manufacturer");
                int year = resultSet.getInt("year");
                String model = resultSet.getString("model");

                Car car = new Car(manufacturer, model, year);
                cars.add(car);
            }
        } catch (SQLException e) {
            logger.error("Error fetching cars from database", e);
        }

        return cars;

    }
}
