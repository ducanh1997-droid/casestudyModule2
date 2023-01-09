package service.impl;
import model.Driver;
import service.ICrudManager;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DriverManager implements ICrudManager<Driver> {
    private final ArrayList<Driver> drivers;

    public DriverManager() {
        drivers = new ArrayList<>();
        drivers.addAll(read("/Users/ducanh/Desktop/casestudyModule2/data/driver.txt"));
        int maxId = 0;
        for(Driver driver:drivers){
            if(maxId<driver.getId()){
                maxId = driver.getId();
                driver.setINDEX(maxId+1);
            }
        }
    }
    public ArrayList<Driver> getDrivers() {
        return drivers;
    }
    @Override
    public Driver create(Scanner scanner){
        boolean flag = false;
        String address;
        String fullName;
        String dateOfBirth;
        int age;
        do{
            System.out.println("Nhập họ tên:");
            fullName = scanner.nextLine();
            String regexFullName = "^[a-zA-Z\\\\s]*$";
            boolean checkFullName = Pattern.matches(regexFullName,fullName);

            if(!checkFullName){
                System.out.println("Họ tên không sử dụng số và kí tự đặc biệt, mời nhập lại!");
            }else{
                flag = true;
            }
        }while(!flag);
        flag = false;
        do{
            System.out.println("Nhập tuổi:");
            age = Integer.parseInt(scanner.nextLine());
            if(age<18 || age>45){
                System.out.println("nhập tuổi từ 18-45, mời nhập lại!");
            }else{
                flag = true;
            }
        }while(!flag);
        flag = false;
        do{
            System.out.println("Nhập tên địa chỉ:");
            address = scanner.nextLine();
            String regexAddress = "^[a-zA-Z0-9\\\\s]*$";
            boolean checkAddress = Pattern.matches(regexAddress,address);
            if(!checkAddress){
                System.out.println("địa chỉ không dùng kí tự đặc biệt, mời nhập lại!");
            }else{
                flag = true;
            }
        }while(!flag);
        flag= false;
        do{
            System.out.println("Nhập ngày sinh(dd/mm/yyyy):");
            dateOfBirth = scanner.nextLine();
            String regexDateOfBirth = "^([0-9]{2})/([0-9]{2})/([0-9]{4})$";
            boolean checkDateOfBirth = Pattern.matches(regexDateOfBirth,dateOfBirth);
            if(!checkDateOfBirth){
                System.out.println("ngày sinh không hợp lệ, mời nhập lại!");
            }else{
                flag = true;
            }
        }while(!flag);
        Driver driver = new Driver(age,fullName,address,"none-active",dateOfBirth, null,0);
        drivers.add(driver);
        write("/Users/ducanh/Desktop/casestudyModule2/data/driver.txt",drivers,false);
        System.out.println("Thêm tài xế thành công!");
        return driver;
    }
    public void updateDriver(){
        write("/Users/ducanh/Desktop/casestudyModule2/data/driver.txt",drivers,false);
    }
    public void displayAll(){
        title();
        if(drivers.isEmpty()){
            System.out.println("Không có tài xế nào trên hệ thống!");
        }else{
            for(Driver driver: drivers){
                driver.display();
            }
        }
    }
    @Override
    public void deleteById(Scanner scanner){
        boolean flag = false;
        do{
            try{
                System.out.println("Nhập id tài xế muốn xoá:");
                int id = Integer.parseInt(scanner.nextLine());
                for(Driver driver:drivers){
                    if(id == driver.getId()){
                        drivers.remove(id-1);
                        System.out.println("Xoá thành công tài xế!");
                        updateDriver();
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    System.out.println("id không tồn tại");
                    flag = true;
                }
            }catch (NumberFormatException e){
                System.out.println("Nhập id bằng số, mời nhập lại!");
            }
        }while(!flag);
    }
    public void editInfoDriver(Scanner scanner){
        boolean flag = false;
        String address;
        String fullName;
        String dateOfBirth;
        int age;
        System.out.println("Nhập id tài xế muốn sửa thông tin:");
        int id = Integer.parseInt(scanner.nextLine());
        for(Driver driver:drivers){
            if(id == driver.getId()){
                int choice;
                do {
                    System.out.println("Nhập lựa chọn của bạn: ");
                    System.out.println("1.Sửa họ tên");
                    System.out.println("2.Sửa tuổi");
                    System.out.println("3.Sửa địa chỉ");
                    System.out.println("4.Sửa ngày sinh");
                    System.out.println("0.Exit");
                    choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            do{
                                System.out.println("Nhập họ tên:");
                                fullName = scanner.nextLine();
                                String regexFullName = "^[a-zA-Z\\s]*$";
                                boolean checkFullName = Pattern.matches(regexFullName,fullName);

                                if(!checkFullName){
                                    System.out.println("Họ tên không sử dụng số và kí tự đặc biệt, mời nhập lại!");
                                }else{
                                    flag = true;
                                }
                            }while(!flag);
                            driver.setFullName(fullName);
                            System.out.println("Cập nhật họ tên thành công!");
                            updateDriver();
                            flag = false;
                            break;
                        case 2:
                            do{
                                System.out.println("Nhập tuổi:");
                                age = Integer.parseInt(scanner.nextLine());
                                if(age<18 || age>45){
                                    System.out.println("nhập tuổi từ 18-45, mời nhập lại!");
                                }else{
                                    flag = true;
                                }
                            }while(!flag);
                            driver.setAge(age);
                            System.out.println("Cập nhật tuổi thành công!");
                            updateDriver();
                            flag = false;
                            break;
                        case 3:
                            do{
                                System.out.println("Nhập địa chỉ:");
                                address = scanner.nextLine();
                                String regexAddress = "^[a-zA-Z0-9\\\\s]*$";
                                boolean checkAddress = Pattern.matches(regexAddress,address);
                                if(!checkAddress){
                                    System.out.println("địa chỉ không dùng kí tự đặc biệt, mời nhập lại!");
                                }else{
                                    flag = true;
                                }
                            }while(!flag);
                            driver.setAddress(address);
                            System.out.println("Cập nhật địa chỉ thành công!");
                            updateDriver();
                            flag = false;
                            break;
                        case 4:
                            do{
                                System.out.println("Nhập ngày sinh(dd/mm/yyyy):");
                                dateOfBirth = scanner.nextLine();
                                String regexDateOfBirth = "^([0-9]{2})/([0-9]{2})/([0-9]{4})$";
                                boolean checkDateOfBirth = Pattern.matches(regexDateOfBirth,dateOfBirth);
                                if(!checkDateOfBirth){
                                    System.out.println("ngày sinh không hợp lệ, mời nhập lại!");
                                }else{
                                    flag = true;
                                }
                            }while(!flag);
                            driver.setDateOfBirth(dateOfBirth);
                            System.out.println("Cập nhật ngày sinh thành công!");
                            updateDriver();
                            flag = false;
                            break;
                    }
                } while (choice != 0);
            }
        }
    }
    public void title(){
        System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%s",
                "ID","TUỔI","HỌ VÀ TÊN","ĐỊA CHỈ","TRANG THÁI","NGÀY SINH","NGÀY BẮT ĐẦU","TIỀN BẢO HIỂM"+"\n" );
    }
    public static void write(String path, ArrayList<Driver> drivers, boolean append) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path,append);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(drivers);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static  ArrayList<Driver> read(String path) {
        ArrayList<Driver> drivers = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            if (fileInputStream.available() != 0) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                Object object = objectInputStream.readObject();
                drivers = (ArrayList<Driver>) object;
                objectInputStream.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return drivers;
    }
}
