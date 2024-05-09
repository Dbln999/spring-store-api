package com.dbln9.customer.customer;

import com.dbln9.customer.customer.enums.Country;

public record CustomerRegistrationRequest(
        String email,
        String password,
        String firstname,
        String lastname,
        String phone,
        String address,
        String city,
        Integer postcode,
        Country country) {
}
