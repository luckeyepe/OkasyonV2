package com.example.morkince.okasyonv2.models;

public class User {

    String user_first_name = null;
    String user_last_name = null;
    String user_gender = null;
    String user_birth_date = null;
    String user_contact_no = null;
    String user_email = null;
    String address = null;
    String user_role = null;


    public User(){}

    public User(String user_first_name, String user_last_name, String user_gender, String user_birth_date, String user_contact_no, String user_email, String address, String user_role) {
        this.user_first_name = user_first_name;
        this.user_last_name = user_last_name;
        this.user_gender = user_gender;
        this.user_birth_date = user_birth_date;
        this.user_contact_no = user_contact_no;
        this.user_email = user_email;
        this.address = address;
        this.user_role = user_role;
    }

    public String getUser_first_name() {
        return user_first_name;
    }

    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    public String getUser_last_name() {
        return user_last_name;
    }

    public void setUser_last_name(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_birth_date() {
        return user_birth_date;
    }

    public void setUser_birth_date(String user_birth_date) {
        this.user_birth_date = user_birth_date;
    }

    public String getUser_contact_no() {
        return user_contact_no;
    }

    public void setUser_contact_no(String user_contact_no) {
        this.user_contact_no = user_contact_no;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }
}
