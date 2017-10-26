/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globalbilisim.exporter.impl;

/**
 *
 * @author Baran Buyuk <baranbuyuk@globalbilisim.com>
 */
public class Test {

    @ExcelColumnName(value = "Name")
    private String name;
    @ExcelColumnName(value = "Surname")
    private String surname;
    @ExcelColumnName(value = "Yaş")
    private int age;

    public Test(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
