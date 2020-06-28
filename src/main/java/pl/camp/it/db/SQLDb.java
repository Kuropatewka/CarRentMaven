package pl.camp.it.db;

import pl.camp.it.model.Bus;
import pl.camp.it.model.Car;
import pl.camp.it.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLDb {
    public static final Connection connection = connect();

    private static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/car-rent?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&characterEncoding=UTF8", "root", "");
            return con;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("Brak połączenia z bazą");
        return null;
    }

    public static void saveVehicle(Vehicle vehicle) {
        try {
            Statement statement = connection.createStatement();

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tvehicle (brand, model, vin, rent, personsAmount, wheelsCount)")
                    .append(" values ('")
                    .append(vehicle.getBrand())
                    .append("', '")
                    .append(vehicle.getModel())
                    .append("', '")
                    .append(vehicle.getVin())
                    .append("', ")
                    .append(vehicle.isRent());

            //TODO sprawdzić czy działa automatyczne dodawanie nulli, nie dodaje
            if (vehicle instanceof Bus) {
                Bus temp = (Bus) vehicle;
                sql.append(", ")
                        .append(temp.getPersonsAmount())
                        .append(", ")
                        .append(temp.getWheelsCount());
            } else {
                sql.append(", ")
                        .append("NULL")
                        .append(", ")
                        .append("NULL");
            }
            sql.append(")");

            statement.executeUpdate(sql.toString());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static List<Vehicle> getAllvehicles() {
        List<Vehicle> resultList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet wyniki = statement.executeQuery( "SELECT * FROM tvehicle");

            while(wyniki.next()) {
                int id = wyniki.getInt("id");
                String brand = wyniki.getString("brand");
                String model = wyniki.getString("model");
                String vin = wyniki.getString("vin");
                boolean rent = wyniki.getBoolean("rent");

                //TODO sprawdzić co sie stanie jak w bazie będzie null
                Integer personsAmoount = wyniki.getInt("personsAmount");
                Integer wheelsCount = wyniki.getInt("wheelsCount");

                if (wyniki.wasNull()) {
                    resultList.add(new Car(id, brand, model, vin, rent));
                } else {
                    resultList.add(new Bus(id, brand, model, vin,
                            rent, personsAmoount, wheelsCount));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultList;
    }

    public static void updateVehicleRent(Vehicle vehicle) {
        try {
            Statement statement = connection.createStatement();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE tvehicle SET rent = ")
                    .append(vehicle.isRent())
                    .append(" WHERE id = ")
                    .append(vehicle.getId());

            statement.executeUpdate(sql.toString());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}