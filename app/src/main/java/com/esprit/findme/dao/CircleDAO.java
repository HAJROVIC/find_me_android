package com.esprit.findme.dao;

import com.esprit.findme.models.Circle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Med-Amine on 14/11/2016.
 */

public class CircleDAO {
    public void getAllCircle(List<Circle> circles, String jsonResponse) {
        try {
            JSONObject jo = new JSONObject(jsonResponse);
            JSONArray array = jo.getJSONArray("circles");
            for (int i = 0; i < array.length(); i++) {
                JSONObject j = array.getJSONObject(i);
                Circle circle = new Circle(j);
                circles.add(circle);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void getCircle(List<Circle> circles, String jsonResponse) {

        try {
            JSONObject jo = new JSONObject(jsonResponse);
            JSONArray array = jo.getJSONArray("circles");
            for (int i = 0; i < array.length(); i++) {
                JSONObject j = array.getJSONObject(i);
                Circle circle = new Circle(j);
                circles.add(circle);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
