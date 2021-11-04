package com.stegano.myjavaapplication.dto;

public class UserDTO {
    // 여기 변수를 public으로 쓰면 생성자,getter 다 없애고 UserDTO.name 으로 찾아갈 수 있음
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
