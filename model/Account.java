package model;

import java.io.Serializable;

public class Account implements Serializable {
    public static final long serialVersionUID = 6290167036522015874L;
    private static int INDEX = 0;

    private String username;
    private String password;

    private final int id;

    private String fullname;
    int age;

    private String address;

    private String role;


    public Account(String username, String password, String fullname, int age, String address,String role) {
        this.username = username;
        this.password = password;
        this.id = INDEX;
        this.fullname = fullname;
        this.age = age;
        this.address = address;
        this.role = role;

        INDEX++;
    }


    public int getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return fullname;
    }

    public void setName(String fullname) {
        this.fullname = fullname;
    }

    public static int getINDEX() {
        return INDEX;
    }

    public static void setINDEX(int INDEX) {
        Account.INDEX = INDEX;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void display(){
        System.out.printf("%-15s%-15s%-15s%-15s%-15s%s",
                id, username, password, fullname, age, address + "\n");
    }
}
