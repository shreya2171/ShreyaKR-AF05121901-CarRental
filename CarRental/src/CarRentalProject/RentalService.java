package CarRentalProject;

import java.util.Scanner;

public class RentalService {
    RentalDAO dao = new RentalDAO();
    Scanner sc = new Scanner(System.in);

    public void menu() throws Exception {
        while (true) {
            System.out.println("\n1.Add Car\n2.View Cars\n3.Rent Car\n4.Return Car\n5.Exit");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("Brand: ");
                    String b = sc.next();
                    System.out.print("Model: ");
                    String m = sc.next();
                    System.out.print("Price/day: ");
                    double p = sc.nextDouble();
                    dao.addCar(b, m, p);
                    break;

                case 2:
                    dao.viewCars();
                    break;

                case 3:
                    System.out.print("Customer Name: ");
                    String name = sc.next();
                    System.out.print("Phone: ");
                    String ph = sc.next();
                    int custId = dao.addCustomer(name, ph);

                    System.out.print("Car ID: ");
                    int carId = sc.nextInt();
                    System.out.print("Days: ");
                    int days = sc.nextInt();

                    dao.rentCar(carId, custId, days);
                    break;

                case 4:
                    System.out.print("Car ID: ");
                    int id = sc.nextInt();
                    dao.returnCar(id);
                    break;

                case 5:
                    System.exit(0);
            }
        }
    }
}