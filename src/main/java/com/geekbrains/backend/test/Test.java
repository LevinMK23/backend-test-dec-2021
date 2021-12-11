package com.geekbrains.backend.test;

public class Test {
    public static void main(String[] args) {
        User user = User.builder()
                .setName("Ivan")
                .setSurname("Ivanov")
                .setPhone("9798798")
                .build();
    }
}
