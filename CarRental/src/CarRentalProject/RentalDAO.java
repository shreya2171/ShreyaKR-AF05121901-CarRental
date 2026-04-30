package CarRentalProject;

import java.sql.*;

public class RentalDAO {

    public void addCar(String brand, String model, double price) throws Exception {
        Connection con = DBConnection.getConnection();
        String sql = "INSERT INTO cars(brand, model, price_per_day, available) VALUES (?, ?, ?, true)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, brand);
        ps.setString(2, model);
        ps.setDouble(3, price);
        ps.executeUpdate();
        System.out.println("Car Added!");
        con.close();
    }

    public void viewCars() throws Exception {
        Connection con = DBConnection.getConnection();
        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM cars");

        while (rs.next()) {
            System.out.println(rs.getInt(1)+" | "+rs.getString(2)+" | "+
                               rs.getString(3)+" | "+rs.getDouble(4)+" | "+
                               rs.getBoolean(5));
        }
        con.close();
    }

    public int addCustomer(String name, String phone) throws Exception {
        Connection con = DBConnection.getConnection();
        String sql = "INSERT INTO customers(name, phone) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, name);
        ps.setString(2, phone);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        rs.next();
        int id = rs.getInt(1);
        con.close();
        return id;
    }

    public void rentCar(int carId, int customerId, int days) throws Exception {
        Connection con = DBConnection.getConnection();

        PreparedStatement ps1 = con.prepareStatement("SELECT price_per_day, available FROM cars WHERE car_id=?");
        ps1.setInt(1, carId);
        ResultSet rs = ps1.executeQuery();

        if (rs.next() && rs.getBoolean("available")) {
            double price = rs.getDouble("price_per_day");
            double total = price * days;

            PreparedStatement ps2 = con.prepareStatement(
                "INSERT INTO rentals(car_id, customer_id, days, total_amount) VALUES (?, ?, ?, ?)");
            ps2.setInt(1, carId);
            ps2.setInt(2, customerId);
            ps2.setInt(3, days);
            ps2.setDouble(4, total);
            ps2.executeUpdate();

            PreparedStatement ps3 = con.prepareStatement(
                "UPDATE cars SET available=false WHERE car_id=?");
            ps3.setInt(1, carId);
            ps3.executeUpdate();

            System.out.println("Car Rented! Total = " + total);
        } else {
            System.out.println("Car not available!");
        }
        con.close();
    }

    public void returnCar(int carId) throws Exception {
        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
            "UPDATE cars SET available=true WHERE car_id=?");
        ps.setInt(1, carId);
        ps.executeUpdate();

        System.out.println("Car Returned!");
        con.close();
    }
}