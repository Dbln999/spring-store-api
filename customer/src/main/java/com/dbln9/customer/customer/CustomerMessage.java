package com.dbln9.customer.customer;

public record CustomerMessage(String email, String firstname, String lastname, String verificationCode, String baseUrl) {
}
