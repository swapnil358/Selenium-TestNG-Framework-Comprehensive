package com.framework.models;

public class TestData {
    private String firstName;
    private String lastName;
    private String postalCode;

    public TestData() {
    }

    public TestData(String firstName, String lastName, String postalCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
