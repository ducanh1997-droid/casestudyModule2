package service.impl;

import model.Account;
import service.ICrudManager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AccountManager implements ICrudManager<Account> {
    private final ArrayList<Account> accounts;

    private Account currentLogin;
    public AccountManager() {
        accounts = new ArrayList<>();
        accounts.addAll(read("/Users/ducanh/Desktop/casestudyModule2/data/account.txt"));
        if(accounts.isEmpty()){
            Account accountAdmin = new Account("Admin","123456",null,0,null,"admin");
            accounts.add(accountAdmin);
        }
        int maxId = 0;
        for(Account account:accounts){
            if(maxId<account.getId()){
                maxId = account.getId();
                Account.setINDEX(maxId+1);
            }
        }
    }



    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    @Override
    public Account create(Scanner scanner){
        boolean flag = false;
        boolean checkUser= true;
        String username;
        String password;
        String fullName;
        int age;
        String address;
        do{
            checkUser = true;
            System.out.println("Nhập tên đăng nhập:");
            username = scanner.nextLine();
            for(Account account: accounts){
                if(username.equals(account.getUsername())){
                    checkUser = false;
                    break;
                }
            }
            if(checkUser){
                String regexUsername = "^[a-zA-Z0-9._-]{6,}$";
                boolean checkUsername = Pattern.matches(regexUsername,username);

                if(!checkUsername){
                    System.out.println("Tên đăng nhập phải có ít nhất 6 kí tự, mời nhập lại!");
                }else{
                    flag = true;
                }
            }else{
                System.out.println("Tên đăng nhập đã tồn tại,mời nhâp lại");
            }
        }while(!flag);
        flag = false;
        do{
            System.out.println("Nhập mật khẩu:");
            password = scanner.nextLine();
            String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
            boolean checkPassword = Pattern.matches(regexPassword,password);

            if(!checkPassword){
                System.out.println("Mật khẩu phải có ít nhất 1 chữ hoa,1 chữ thường,1 số, có ít nhất 8 kí tự, mời nhập lại!");
            }else{
                flag = true;
            }
        }while(!flag);
        flag = false;
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
        Account account = new Account(username,password,fullName,age,address,"user");
        accounts.add(account);
        write("/Users/ducanh/Desktop/casestudyModule2/data/account.txt",accounts,false);
        System.out.println("Đăng ký thành công!");
        return account;
    }

    public void updateAccount(){
        write("/Users/ducanh/Desktop/casestudyModule2/data/account.txt",accounts,false);
    }
    @Override
    public void deleteById(Scanner scanner){
        boolean check = false;
        do{
            try{
                System.out.println("Nhập id user muốn xoá:");
                int id = Integer.parseInt(scanner.nextLine());
                for(Account account:accounts){
                    if(account.getId() == id && account.getRole().equals("user")){
                        accounts.remove(id);
                        updateAccount();
                        System.out.println("Xoá user thành công!");
                        check = true;
                        break;
                    }
                }
                if(!check){
                    System.out.println("id user không tồn tại, mời nhập lại");
                }
            }catch(NumberFormatException e){
                System.out.println("Nhập id là số, mời nhập lại");
            }
        }while(!check);
    }
    public boolean login(ArrayList<Account> accounts,Scanner scanner){
        System.out.println("Tên đăng nhập:");
        String username = scanner.nextLine();
        System.out.println("Mật khẩu");
        String password = scanner.nextLine();
        boolean flag = false;
        for(Account account: accounts){
            if(account.getUsername().equals(username) && account.getPassword().equals(password)){
                this.currentLogin = account;
                flag = true;
                break;
            }
        }
        if(flag){
            System.out.println("Đăng nhập thành công!");
            return true;
        }else{
            System.out.println("Tên đăng nhập hoặc mật khẩu không đúng!");
            return false;
        }
    }
    public Account currentLogin(){

        return this.currentLogin;
    }
    public void logout(){
        currentLogin = null;
        System.out.println("Đăng xuất thành công!");
    }

    public void upAdminById(ArrayList<Account> accounts,Scanner scanner){
        boolean flag = false;
        do{
            try{
                System.out.println("Nhập id user muốn phân quyền admin");
                int id = Integer.parseInt(scanner.nextLine());
                for(Account account:accounts){
                    if(account.getId() == id && account.getRole().equals("user")){
                        account.setRole("admin");
                        updateAccount();
                        System.out.println("Phân quyền admin thành công!");
                        flag = true;
                        break;
                    }
                }
                if(!flag){
                    System.out.println("Id không tồn tại!");
                }
            }catch(NumberFormatException e){
                System.out.println("Nhập id là số,mời nhập lại!");
            }
        }while(!flag);
    }

    public void changePassword(Scanner scanner){
        String password;
        boolean flag = false;
        do{
            System.out.println("Nhập mật khẩu mới:");
            password = scanner.nextLine();
            String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
            boolean checkPassword = Pattern.matches(regexPassword,password);

            if(!checkPassword){
                System.out.println("Mật khẩu phải có ít nhất 1 chữ hoa,1 chữ thường,1 số, có ít nhất 8 kí tự, mời nhập lại!");
            }else{
                updateAccount();
                System.out.println("Đổi mật khẩu thành công!");
                flag = true;
            }
        }while(!flag);
        currentLogin.setPassword(password);
    }

    public void changeInfoUser(Scanner scanner){
        boolean flag = false;
        String fullName,address;
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
            System.out.println("Nhập địa chỉ:");
            address = scanner.nextLine();
            String regexAddress = "^[a-zA-Z0-9\\\\s]*$";
            boolean checkAddress = Pattern.matches(regexAddress,address);
            if(age<18 || age>45){
                System.out.println("địa chỉ không dùng kí tự đặc biệt, mời nhập lại!");
            }else{
                flag = true;
            }
        }while(!flag);
        currentLogin.setFullname(fullName);
        currentLogin.setAge(age);
        currentLogin.setAddress(address);
        System.out.println("Cập nhật thông tin cá nhân thành công!");
        updateAccount();
    }
    public void displayAll(){
        boolean flag = false;
        title();
            for(Account account: accounts){
                if(account.getRole().equals("user")){
                    account.display();
                    flag = true;
                }
            }
            if(!flag){
                System.out.println("Không có user nào trên hệ thống!");
            }
    }
    public void title(){
        System.out.printf("%-15s%-15s%-15s%-15s%-15s%s",
                "ID" ,"TÊN ĐĂNG NHẬP","MẬT KHẨU","HỌ TÊN","TUỔI","ĐỊA CHỈ"+ "\n");
    }

    public static void write(String path, ArrayList<Account> accounts,boolean append) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path,append);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(accounts);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  ArrayList<Account> read(String path) {
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            if (fileInputStream.available() != 0) {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                Object object = objectInputStream.readObject();
                accounts = (ArrayList<Account>) object;
                objectInputStream.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return accounts;
    }

}
