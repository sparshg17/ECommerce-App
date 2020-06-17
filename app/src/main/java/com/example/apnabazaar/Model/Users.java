package com.example.apnabazaar.Model;

public class Users {
    private String Email, Mobile, Password, fName, lName;

    public Users() {

    }

    public Users(String email, String mobile, String password, String fName, String lName) {
        Email = email;
        Mobile = mobile;
        Password = password;
        this.fName = fName;
        this.lName = lName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }
}