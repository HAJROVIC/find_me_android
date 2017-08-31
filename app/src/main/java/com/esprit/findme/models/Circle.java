package com.esprit.findme.models;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Med-Amine on 14/11/2016.
 */

public class Circle {

    private int id;
    private String title;
    private String description;
    private Date created_at;
    private Date updated_at;
    private String code;
    private int creator;

    public Circle() {
    }

    public Circle(int id, String title, String description, Date created_at, Date updated_at, String code, int creator) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.code = code;
        this.creator = creator;
    }

    public Circle(JSONObject j) {
        id = j.optInt("id");
        title = j.optString("title");
        description = j.optString("description");
        created_at = new Date(j.optLong("created_at"));
        updated_at = new Date(j.optLong("updated_at"));
        code = j.optString("code");
        creator = j.optInt("creator");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }
}
