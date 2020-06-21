package pl.camp.it.gui;

import pl.camp.it.db.VehicleRepository;
import pl.camp.it.model.Vehicle;

import java.util.Scanner;

public class GUI {

    private static final VehicleRepository cr = new VehicleRepository();  // final- nie chcemy zeby ktos zmienial nam baze danych, ona jest ostateczna
    private static final Scanner scanner = new Scanner(System.in);

    public static void showMainMenu() {
        System.out.println("1. Dostępne samochody");
        System.out.println("2. Wypożycz samochód");
        System.out.println("3. Exit");

        String choose = scanner.nextLine();

        switch (choose) {
            case "1":
                showCar();  // this odwoluje sie do obiektu samego siebie, referencja do samego siebie
                break;
            case "2":
                rentCar();
                break;
            case "3":
                System.exit(0);
            default:
                System.out.println("Nieprawidłowy wybór");
                showMainMenu();
                break;
        }
    }
   private static void showCar() {
        for (Vehicle tempVehicle : cr.getVehicles()) {
            if (tempVehicle != null && !tempVehicle.isRent()) {   // temporaryCar.rent == false
                System.out.println(tempVehicle);
                }
        }
        showMainMenu();
    }
   private static void rentCar() {
        System.out.println("Wpisz id samoochodu: ");
        String carId = scanner.nextLine();
        for(Vehicle tempVehicle : cr.getVehicles()) {
            try {
                if (tempVehicle != null && tempVehicle.getId() == Integer.parseInt(carId)) {
                    if (!tempVehicle.isRent()) {
                        tempVehicle.setRent(true);
                        System.out.println("Udało się");
                    } else {
                        System.out.println("Auto niedostępne");
                    }
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Wprowadzono błędne id");
                rentCar();
            }
        }
        showMainMenu();
    }
}