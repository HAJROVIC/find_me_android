package com.esprit.findme.dao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.esprit.findme.utils.AppConfig;
import com.esprit.findme.utils.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Med-Amine on 20/11/2016.
 */

public class InvitationDao {
    private ProgressDialog pDialog;
    private Activity activity;

    public InvitationDao(Activity activity) {
        this.activity = activity;
    }

    public void addMember(final String circle_code, final int user_id) {
// Tag used to cancel the request
        String tag_string_req = "req_register";
        pDialog = new ProgressDialog(activity);
        pDialog.setCancelable(false);
        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_JOIN_CIRCLE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        JSONObject invitation = jObj.getJSONObject("invitation");
                        String circle_code = invitation.getString("circle_code");
                        int user_id = invitation.getInt("user_id");
                        Toast.makeText(activity.getApplicationContext(), "Congrats you have been added to the circle !", Toast.LENGTH_LONG).show();
                    } else {

                        // Error occurred in registration. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(activity.getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity.getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("circle_code", circle_code);
                params.put("user_id", String.valueOf(user_id));

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
