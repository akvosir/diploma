package com.example.yuliia.diploma.models;

public  class User {
    private String personName;
    private String personEmail;
    private String personId;
    private String password;
    private String personUri;

    public String getPersonUri() {
        return personUri;
    }

    public void setPersonUri(String personUri) {
        this.personUri = personUri;
    }

    public User(){}

    public User(String personEmail, String personName) {
        this.personName = personName;
        this.personEmail = personEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
