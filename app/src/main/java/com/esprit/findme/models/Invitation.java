package com.esprit.findme.models;

import java.util.Date;

/**
 * Created by Med-Amine on 20/11/2016.
 */

public class Invitation {
    private Circle circle;
    private User user;
    private Date created_at;
    private Date updated_at;

    public Invitation() {
    }

    public Invitation(Circle circle, User user, Date created_at, Date updated_at) {
        this.circle = circle;
        this.user = user;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                ", circle=" + circle +
                ", user=" + user +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
