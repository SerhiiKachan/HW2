package com.epam.models;

public class Author {
    String name;
    String countryName;
    int age;

    public Author(String name, String countryName, int age) {
        this.name = name;
        this.countryName = countryName;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

