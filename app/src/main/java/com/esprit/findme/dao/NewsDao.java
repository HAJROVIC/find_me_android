package com.esprit.findme.dao;

import com.esprit.findme.models.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Med-Amine on 03/12/2016.
 */

public class NewsDao {

    public void getAllNews(List<News> listnews, String jsonResponse) {
        try {
            JSONObject jo = new JSONObject(jsonResponse);
            JSONArray array = jo.getJSONArray("news");
            for (int i = 0; i < array.length(); i++) {
                JSONObject j = array.getJSONObject(i);
                News news = new News(j);
                listnews.add(news);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
