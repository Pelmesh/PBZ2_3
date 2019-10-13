package sample;

import java.time.LocalDate;

public class Data {

    int id;
    String conclusion;
    String autonumber;
    String engine;
    String color;
    String password;
    String model;
    LocalDate date;
    String certificate;
    String address;
    int years;
    String employee;
    String rank;
    String sex;
    String FIO;
    String position;
    int count;


    public Data(int id, String autonumber, String engine, String color, String model, String password,
                String certificate, String FIO, String address, int years, String sex, LocalDate date, String employee,
                String position, String rank, String conclusion) {
        this.id=id;
        this.autonumber = autonumber;
        this.engine = engine;
        this.color = color;
        this.password = password;
        this.model = model;
        this.certificate = certificate;
        this.address = address;
        this.years = years;
        this.sex = sex;
        this.FIO = FIO;
        this.conclusion = conclusion;
        this.date = date;
        this.employee = employee;
        this.position = position;
        this.rank = rank;
    }

    public Data(int id, String autonumber, String engine, String color, String model, String password, String certificate, String FIO, String address, int years, String sex) {
        this.id = id;
        this.autonumber = autonumber;
        this.engine = engine;
        this.color = color;
        this.password = password;
        this.model = model;
        this.certificate = certificate;
        this.address = address;
        this.years = years;
        this.sex = sex;
        this.FIO = FIO;

    }

    public Data(int id, String FIO, String position, String rank) {
        this.FIO = FIO;
        this.id = id;
        this.position=position;
        this.rank=rank;
    }

    public Data(int id, LocalDate date, String conclusion) {
        this.id=id;
        this.date=date;
        this.conclusion=conclusion;
    }

    public Data(int id, LocalDate date) {
        this.id=id;
        this.date=date;
    }

    public Data(String employee, String rank, String autonumber) {
        this.employee=employee;
        this.rank=rank;
        this.autonumber=autonumber;
    }

    public Data(LocalDate date, String conclusion) {
        this.date=date;
        this.conclusion=conclusion;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAutonumber() {
        return autonumber;
    }

    public void setAutonumber(String autonumber) {
        this.autonumber = autonumber;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}