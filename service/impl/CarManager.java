package service.impl;

import model.Car;
import model.Driver;
import service.ICrudManager;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CarManager implements ICrudManager<Car> {

    private final ArrayList<Car> cars;

    private final DriverManager driverManager;
    public CarManager(DriverManager driverManager) {
        cars = new ArrayList<>();
        cars.addAll(read("/Users/ducanh/Desktop/casestudyModule2/data/car.txt"));
        this.driverManager = driverManager;
        int maxId = 0;
        for(Car car:cars){
            if(maxId<car.getId()){
                maxId = car.getId();
                Car.setINDEX(maxId+1);
            }
        }
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public Car create(Scanner scanner){
        boolean flag = false;
        String name;
        String licensePlate;
        int yearManufacture = 0;
        String automaker;
        Driver driver1 = null;
        Driver driver2 = null;
        do{
            System.out.println("Nhập tên xe:");
            name = scanner.nextLine();
            String regexName = "^[a-zA-Z0-9\\s]*$";
            boolean checkName = Pattern.matches(regexName,name);

            if(!checkName){
                System.out.println("Tên không sử dụng kí tự đặc biệt, mời nhập lại!");
            }else{
                flag = true;
            }
        }while(!flag);
        flag = false;
        do{
            try{
                System.out.println("Nhập năm sản xuất(VD:2020):");
                yearManufacture = Integer.parseInt(scanner.nextLine());
                if(yearManufacture<1900 || yearManufacture>2022){
                    System.out.println("nhập năm sản xuất (1900-2022), mời nhập lại!");
                }else{
                    flag = true;
                }
            }catch(NumberFormatException e){
                System.out.println("năm sản xuất là định dạng số,mời nhập lại");
            }
        }while(!flag);
        flag = false;
        do{
            System.out.println("Nhập biển số xe(VD:30H1-12345):");
            licensePlate = scanner.nextLine();
            String regexLicensePlate = "^[1-9][0-9][A-Z][1-9]-[0-9]{5}$";
            boolean checkLicensePlate = Pattern.matches(regexLicensePlate,licensePlate);

            if(!checkLicensePlate){
                System.out.println("Biển số xe không đúng định dạng, mời nhập lại!");
            }else{
                flag = true;
            }
        }while(!flag);
        flag = false;
        do{
            System.out.println("Nhập hãng xe:");
            automaker = scanner.nextLine();
            String regexAutomaker = "^[a-zA-Z]+$";
            boolean checkAutomaker = Pattern.matches(regexAutomaker,automaker);

            if(!checkAutomaker){
                System.out.println("Hãng xe không đúng định dạng, mời nhập lại!");
            }else{
                flag = true;
            }
        }while(!flag);

        int choice;
        do {
            System.out.println("Nhập lựa chọn của bạn");
            System.out.println("1.Thêm tài xế 1");
            System.out.println("2.Thêm tài xế 2");
            System.out.println("0.Exit");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    if(driver1 == null){
                        boolean check = false;
                        driverManager.displayAll();
                        System.out.println("Nhập id tài xế muốn thêm");
                        int id = Integer.parseInt(scanner.nextLine());
                        for(Driver driver :driverManager.getDrivers()){
                            if(id == driver.getId()){
                                check = true;
                                if(driver.getActive().equals("none-active")){
                                    driver1 = driver;
                                    driver.setActive("active");
                                    //LocalDate from = LocalDate.parse("2020-05-15");
                                    LocalDate from = LocalDate.now();;
                                    driver.setDateOfStart(from);
                                    driverManager.updateDriver();
                                    System.out.println("Thêm tài xế 1 thành công");
                                }else{
                                    System.out.println("Tài xế đang hoạt động trên xe khác");
                                }
                            }
                        }
                        if(!check){
                            System.out.println("id tài xế không tồn tại");
                        }
                    }else{
                        System.out.println("Xe này đã có tài xế 1");
                    }
                    break;
                case 2:
                    if(driver2 == null){
                        boolean check = false;
                        driverManager.displayAll();
                        System.out.println("Nhập id tài xế muốn thêm");
                        int id = Integer.parseInt(scanner.nextLine());
                        for(Driver driver :driverManager.getDrivers()){
                            if(id == driver.getId()){
                                check = true;
                                if(driver.getActive().equals("none-active")){
                                    driver2 = driver;
                                    driver.setActive("active");
                                    LocalDate from = LocalDate.now();;
                                    driver.setDateOfStart(from);
                                    driverManager.updateDriver();
                                    System.out.println("Thêm tài xế 2 thành công");
                                }else{
                                    System.out.println("Tài xế đang hoạt động trên xe khác");
                                }
                            }
                        }
                        if(!check){
                            System.out.println("id tài xế không tồn tại");
                        }
                    }else{
                        System.out.println("Xe này đã có tài xế 2");
                    }
                    break;
            }
        } while (choice != 0);
        Car car = new Car(name,yearManufacture,licensePlate,driver1,driver2,automaker);
        cars.add(car);
        write("/Users/ducanh/Desktop/casestudyModule2/data/car.txt",cars,false);
        return car;
    };

    @Override
    public void deleteById(Scanner scanner){
        int id;
        boolean check = false;
        do{
            try{
                System.out.println("nhập id xe muốn xoá:");
                id = Integer.parseInt(scanner.nextLine());
                for (Iterator<Car> it = cars.iterator(); it.hasNext(); ) {
                    Car car = it.next();
                    if(id == car.getId()){
                        for(Driver driver: driverManager.getDrivers()){
                            if(car.getDriver1().getId() == driver.getId()){
                                driver.setActive("none-active");
                            }
                            if(car.getDriver2().getId() == driver.getId()){
                                driver.setActive("none-active");
                            }
                        }
                        it.remove();
                        System.out.println("Xoá xe thành công!");
                        updateCar();
                        check = true;
                    }
                }
                if(!check){
                    System.out.println("id không tồn tại, mời nhập lại");
                }
            }catch(NumberFormatException e){
                System.out.println("Bạn phải nhập id dạng số,mời nhập lại!");
            }
        }while(!check);
    }

    public void updateDriverWithCar(Scanner scanner){
        int id;
        boolean check = false;
        do{
            try{
                System.out.println("Nhập id xe muốn thay đổi tài xế:");
                id = Integer.parseInt(scanner.nextLine());
                for(Car car:cars){
                    if(id == car.getId()){
                        int choice;
                        do {
                            System.out.println("Nhập lựa chọn của bạn: ");
                            System.out.println("1.Cập nhật tài xế 1");
                            System.out.println("2.Cập nhật tài xế 2");
                            System.out.println("0.Exit");
                            choice = Integer.parseInt(scanner.nextLine());
                            switch (choice) {
                                case 1:
                                    boolean flag = false;
                                    driverManager.displayAll();
                                    System.out.println("Nhập id tài xế 1 muốn thay đổi(nhập 0 để xoá tài xế 1)");
                                    int idDriver = Integer.parseInt(scanner.nextLine());
                                    if(idDriver == 0){
                                        for(Driver driver: driverManager.getDrivers()){
                                            if(car.getDriver1().getId() == driver.getId()){
                                                driver.setActive("none-active");
                                            }
                                        }
                                        car.setDriver1(null);
                                        updateCar();
                                        driverManager.updateDriver();
                                    }else{
                                        for(Driver driver :driverManager.getDrivers()){
                                            if(idDriver == driver.getId()){
                                                flag = true;
                                                if(driver.getActive().equals("none-active")){
                                                    for(Driver driver1: driverManager.getDrivers()){
                                                        if(car.getDriver1().getId() == driver1.getId()){
                                                            driver1.setActive("none-active");
                                                        }
                                                    }
                                                    car.setDriver1(driver);
                                                    driver.setActive("active");
                                                    LocalDate from = LocalDate.now();;
                                                    driver.setDateOfStart(from);
                                                    driverManager.updateDriver();
                                                    updateCar();
                                                    System.out.println("Thay đổi tài xế 1 thành công");
                                                }else{
                                                    System.out.println("Tài xế đang hoạt động trên xe khác");
                                                }
                                            }
                                        }
                                        if(!flag){
                                            System.out.println("id tài xế không tồn tại");
                                        }
                                    }
                                    break;
                                case 2:
                                    flag = false;
                                    driverManager.displayAll();
                                    System.out.println("Nhập id tài xế 2 muốn thay đổi(nhập 0 để xoá tài xế 2)");
                                    idDriver = Integer.parseInt(scanner.nextLine());
                                    if(idDriver == 0){
                                        for(Driver driver: driverManager.getDrivers()){
                                            if(car.getDriver2().getId() == driver.getId()){
                                                driver.setActive("none-active");
                                            }
                                        }
                                        car.setDriver2(null);
                                        driverManager.updateDriver();
                                        updateCar();
                                    }else{
                                        for(Driver driver :driverManager.getDrivers()){
                                            if(idDriver == driver.getId()){
                                                flag = true;
                                                if(driver.getActive().equals("none-active")){
                                                    for(Driver driver2: driverManager.getDrivers()){
                                                        if(car.getDriver2().getId() == driver2.getId()){
                                                            driver2.setActive("none-active");
                                                        }
                                                    }
                                                    car.setDriver2(driver);
                                                    driver.setActive("active");
                                                    LocalDate from = LocalDate.now();;
                                                    driver.setDateOfStart(from);
                                                    driverManager.updateDriver();
                                                    updateCar();
                                                    System.out.println("Thay đổi tài xế 2 thành công");
                                                }else{
                                                    System.out.println("Tài xế đang hoạt động trên xe khác");
                                                }
                                            }
                                        }
                                        if(!flag){
                                            System.out.println("id tài xế không tồn tại");
                                        }
                                    }
                                    break;
                            }
                        } while (choice != 0);
                        check = true;
                    }
                }
                if(!check){
                    System.out.println("id không tồn tại, mời nhập lại");
                }
            }catch(NumberFormatException e){
                System.out.println("Bạn phải nhập id dạng số,mời nhập lại!");
            }
        }while(!check);
    }

    public void findCarByName(Scanner scanner){
        System.out.println("Nhập tên xe muốn tìm:");
        String name = scanner.nextLine();
        title();
        try{
            for(Car car: cars){
                if(car.getName().toLowerCase().contains(name.toLowerCase()))
                    car.display();
            }
        }catch(NullPointerException e){
            System.out.println("Không có xe nào trên hệ thống!");
        }
    }
    public void updateCar(){
        write("/Users/ducanh/Desktop/casestudyModule2/data/car.txt",cars,false);
    }
    public void displayAll(){
        boolean flag = false;
        title();
        for(Car car: cars){
                car.display();
                flag = true;
        }
        if(!flag){
            System.out.println("Không có xe nào trên hệ thống!");
        }
    }

    public void title(){
        System.out.printf("%-15s%-15s%-15s%-15s%s%s%-15s%s",
                "ID" ,"TÊN XE","NĂM SẢN XUẤT","BIỂN SỐ XE","TÀI XẾ","","","HÃNG XE"+ "\n");
    }



    public static void write(String path, ArrayList<Car> cars,boolean append) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path,append);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(cars);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static  ArrayList<Car> read(String path) {
        ArrayList<Car> cars = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            if (fileInputStream.available() != 0) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                Object object = objectInputStream.readObject();
                cars = (ArrayList<Car>) object;
                objectInputStream.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cars;
    }
}
