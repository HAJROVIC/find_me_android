package com.esprit.findme.models;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Med-Amine on 29/11/2016.
 */

public class News implements Serializable {
    private int id;
    private String user_name;
    private String photo;
    private String content;
    private String url;
    private String created_at;

    public News() 
    {
    }

    public News(int id, String user_name, String photo,String description, String url, String created_at) {
        this.id = id;
        this.user_name = user_name;
        this.photo = photo;
        this.content = description;
        this.url = url;
        this.created_at = created_at;
    }

    public News(JSONObject j) {
        id = j.optInt("id");
        user_name = j.optString("user_name");
        photo = j.optString("photo");
        url = j.optString("url");
        content = j.optString("content");
        created_at = j.optString("when");

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDescription() {
        return content;
    }

    public void setDescription(String description) {
        this.content = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
