package model;

import java.io.Serializable;

public class Car implements Serializable {

    public static final long serialVersionUID = -559452458317470694L;
    private int id;
    private static int INDEX = 1;
    private String name;
    private int yearManufacture;
    private String licensePlate;
    private Driver driver1;
    private Driver driver2;
    private String automaker;

    public Car(String name, int yearManufacture, String licensePlate, Driver driver1, Driver driver2, String automaker) {
        this.id = INDEX;
        this.name = name;
        this.yearManufacture = yearManufacture;
        this.licensePlate = licensePlate;
        this.driver1 = driver1;
        this.driver2 = driver2;
        this.automaker = automaker;
        INDEX++;
    }

    public static int getINDEX() {
        return INDEX;
    }

    public static void setINDEX(int INDEX) {
        Car.INDEX = INDEX;
    }

    public Driver getDriver2() {
        return driver2;
    }

    public void setDriver2(Driver driver2) {
        this.driver2 = driver2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearManufacture() {
        return yearManufacture;
    }

    public void setYearManufacture(int yearManufacture) {
        this.yearManufacture = yearManufacture;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Driver getDriver1() {
        return driver1;
    }

    public void setDriver1(Driver driver1) {
        this.driver1 = driver1;
    }

    public String getAutomaker() {
        return automaker;
    }

    public void setAutomaker(String automaker) {
        this.automaker = automaker;
    }

    public void display(){
        if(driver1 == null && driver2 != null){
            System.out.printf("%-15s%-15s%-15s%-15s%s%s%-21s%s",
                    id, name,yearManufacture, licensePlate, "","",driver2.getFullName(),automaker + "\n");
        }else if(driver2 == null && driver1!=null){
            System.out.printf("%-15s%-15s%-15s%-15s%s%s%-16s%s",
                    id, name,yearManufacture, licensePlate, driver1.getFullName(),"","",automaker + "\n");
        }else if(driver1 == null && driver2 == null){
            System.out.printf("%-15s%-15s%-15s%-15s%s%s%-19s%s",
                    id, name,yearManufacture, licensePlate," "," "," ",automaker + "\n");
        }else{
            System.out.printf("%-15s%-15s%-15s%-15s%s%s%-15s%s",
                    id, name,yearManufacture, licensePlate, driver1.getFullName(),",",driver2.getFullName(),automaker + "\n");
        }
    }
}
