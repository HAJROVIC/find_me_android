package com.esprit.findme.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by TIBH on 14/11/2016.
 */

public class User {
    private int id;
    private String name;
    private String email;
    private int number;
    private String password;
    private String photo;
    private String position;

    public User()
    {

    }

    public User(String name, String email,  int number, String password, String photo) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.password = password;
        this.photo = photo;
    }

    public User(JSONObject j) {
        this.id=j.optInt("id");
        this.name = j.optString("name");
        this.photo = j.optString("photo");
        this.position = j.optString("position");

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", number=" + number +
                ", password='" + password + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (number != user.number) return false;
        if (!name.equals(user.name)) return false;
        if (!email.equals(user.email)) return false;
        if (!password.equals(user.password)) return false;
        if (!photo.equals(user.photo)) return false;
        return true ;

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + number;
        result = 31 * result + password.hashCode();
        result = 31 * result + photo.hashCode();
        return result;
    }


}
