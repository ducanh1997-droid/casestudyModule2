package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class Driver implements Serializable {
    public static final long serialVersionUID = -8144781053803779229L;
    private int id;
    private static int INDEX = 1;
    private String fullName;
    private int age;
    private String address;
    private String active;
    private String dateOfBirth;

    private LocalDate dateOfStart;

    private long insuranceMoney;

    public Driver(int age,String fullName, String address, String active, String dateOfBirth, LocalDate dateOfStart, long insuranceMoney) {
        this.id = INDEX;
        this.age = age;
        this.fullName = fullName;
        this.address = address;
        this.active = active;
        this.dateOfBirth = dateOfBirth;
        this.dateOfStart = dateOfStart;
        this.insuranceMoney = insuranceMoney;
        INDEX++;
    }

    public long getInsuranceMoney() {
        return insuranceMoney;
    }

    public void setInsuranceMoney(long insuranceMoney) {
        this.insuranceMoney = insuranceMoney;
    }

    public LocalDate getDateOfStart() {
        return dateOfStart;
    }

    public void setDateOfStart(LocalDate dateOfStart) {
        this.dateOfStart = dateOfStart;
    }

    public int getINDEX() {
        return INDEX;
    }

    public void setINDEX(int INDEX) {
        Driver.INDEX = INDEX;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void calculateInsuranceMoney(){
        LocalDate dateNow = LocalDate.now();
        if(this.dateOfStart!= null){
            int amount1 = Period.between(this.dateOfStart, dateNow).getMonths();
            int amount2 = Period.between(this.dateOfStart, dateNow).getYears();
            int totalMonth = amount1+amount2*12;
            this.insuranceMoney = totalMonth * 3000000L;
        }
    }

    public void display(){
        calculateInsuranceMoney();
        System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%-15s%s",
                id, age,fullName, address, active, dateOfBirth,dateOfStart==null?" ":dateOfStart,insuranceMoney+"đ" + "\n");
    }
}
