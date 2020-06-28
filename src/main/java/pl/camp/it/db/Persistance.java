package pl.camp.it.db;

import pl.camp.it.gui.GUI;
import pl.camp.it.model.Bus;
import pl.camp.it.model.Car;
import pl.camp.it.model.Vehicle;

import java.io.*;
import java.util.List;

public class Persistance {

    private static final String fileName = "/home/piotr/IdeaProjects/carRentDb.txt";

    public static void saveData() {

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            boolean flag = false;
            for (Vehicle vehicle :VehicleRepository.getRepository().getVehicles()) {
                StringBuilder sb = new StringBuilder();

                if (vehicle instanceof Car) {
                    Car c = (Car) vehicle;
                    sb.append("Car;")
                            .append(c.getId())
                            .append(";")
                            .append(c.getBrand())
                            .append(";")
                            .append(c.getModel())
                            .append(";")
                            .append(c.getVin())
                            .append(";")
                            .append(c.isRent());

                    if (flag) {
                        bufferedWriter.newLine();
                    }
                    flag = true;
                    bufferedWriter.append(sb.toString());

                } else {
                    Bus b = (Bus) vehicle;
                    sb.append("Bus;")
                            .append(b.getId())
                            .append(";")
                            .append(b.getBrand())
                            .append(";")
                            .append(b.getModel())
                            .append(";")
                            .append(b.getVin())
                            .append(";")
                            .append(b.isRent())
                            .append(";")
                            .append(b.getPersonsAmount())
                            .append(";")
                            .append(b.getWheelsCount());

                    if (flag) {
                        bufferedWriter.newLine();
                    }
                    flag = true;
                    bufferedWriter.append(sb.toString());
                }

            }

            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadData(List<Vehicle> list) {

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tab = line.split(";");

                if (tab[0].equals("Car")) {
                    Car car = new Car(Integer.parseInt(tab[1]),
                            tab[2],
                            tab[3],
                            tab[4],
                            Boolean.parseBoolean(tab[5]));

                    list.add(car);

                } else {
                    Bus bus = new Bus(Integer.parseInt(tab[1]),
                            tab[2],
                            tab[3],
                            tab[4],
                            Boolean.parseBoolean(tab[5]),
                            Integer.parseInt(tab[6]),
                            Integer.parseInt(tab[7]));

                    list.add(bus);

                }
            }

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
