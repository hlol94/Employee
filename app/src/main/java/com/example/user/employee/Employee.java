package com.example.user.employee;

import java.io.Serializable;

/**
 * Created by user on 28/04/2018.
 */

public class Employee implements Serializable{

    int empID;
    String empName;
    String empGender;
    String empCountry;
    float empSalary;
    String DOB;
    byte[] empImg;

    public Employee() {

    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpGender() {
        return empGender;
    }

    public void setEmpGender(String empGender) {
        this.empGender = empGender;
    }

    public String getEmpCountry() {
        return empCountry;
    }

    public void setEmpCountry(String empCountry) {
        this.empCountry = empCountry;
    }

    public float getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(float empSalary) {
        this.empSalary = empSalary;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public byte[] getEmpImg() {
        return empImg;
    }

    public void setEmpImg(byte[] empImg) {
        this.empImg = empImg;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empName='" + empName + '\'' +
                ", empGender='" + empGender + '\'' +
                ", empCountry='" + empCountry + '\'' +
                ", empSalary=" + empSalary +
                ", DOB='" + DOB + '\'' +
                '}';
    }
}
