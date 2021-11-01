package com.stegano.myjavaapplication.dto;

public class UserDTO {
    String name, city;
    int age;

    UserDTO(String name, int age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }
}
