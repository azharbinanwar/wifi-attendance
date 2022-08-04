package dev.azsoft.wifiattendance.models;

import com.google.gson.GsonBuilder;

public class Employee {
    String uid;
    String name;
    String phoneNumber;
    String email;
    String designation;
    String password;
    long timestamp;
    String profileImage;
    Boolean isActive;

    public Employee() {
    }

    public Employee(String uid, String name,
                    String phoneNumber, String email,
                    String designation, String password,
                    long timestampt, String profileImage,
                    Boolean isActive) {
        this.uid = uid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.designation = designation;
        this.password = password;
        this.timestamp = timestampt;
        this.profileImage = profileImage;
        this.isActive = isActive;
    }

    public Employee(String uid, String name,
                    String phoneNumber, String email,
                    String designation,
                    long timestampt, String profileImage,
                    Boolean isActive) {
        this.uid = uid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.designation = designation;
        this.timestamp = timestampt;
        this.profileImage = profileImage;
        this.isActive = isActive;
    }

    public String getUid() {
        return uid;
    }

    public Employee setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getName() {
        return name;
    }

    public Employee setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Employee setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Employee setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getDesignation() {
        return designation;
    }

    public Employee setDesignation(String designation) {
        this.designation = designation;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Employee setPassword(String password) {
        this.password = password;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Employee setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public Employee setProfileImage(String profileImage) {
        this.profileImage = profileImage;
        return this;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Employee setActive(Boolean active) {
        isActive = active;
        return this;
    }
    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this, Employee.class);
    }

    static public Employee fromString(String value) {
        return new GsonBuilder().create().fromJson(value, Employee.class);
    }

}
