package com.esprit.findme.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.esprit.findme.main.MainActivity;
import com.esprit.findme.utils.AppConfig;
import com.esprit.findme.utils.AppController;
import com.esprit.findme.R;
import com.esprit.findme.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddCircleActivity extends AppCompatActivity {
    private Button btnRegister,updateBtn, backBtn;
    private EditText inputTitle;
    private EditText inputCode;
    private EditText inputDescription;
    private int circle_id = 0;
    SessionManager session;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_circle);
        toolbar = (Toolbar) findViewById(R.id.secondary_toolbar);
        setSupportActionBar(toolbar);
        inputTitle = (EditText) findViewById(R.id.title);
        inputCode = (EditText) findViewById(R.id.code);
        inputDescription = (EditText) findViewById(R.id.description);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        updateBtn = (Button) findViewById(R.id.nextBtn);
        backBtn = (Button) findViewById(R.id.returnBtn);
        // Session manager
        session = new SessionManager(getApplicationContext());
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(
                        AddCircleActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String title = inputTitle.getText().toString().trim();
                String code = inputCode.getText().toString().trim();
                String description = inputDescription.getText().toString().trim();
                if (!title.isEmpty() && !code.isEmpty()) {
                    registerCircle(title, description, code, session.getUserId());

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String title = inputTitle.getText().toString().trim();
                String code = inputCode.getText().toString().trim();
                String description = inputDescription.getText().toString().trim();
                if (!title.isEmpty() && !code.isEmpty()) {
                    registerCircle(title, description, code, session.getUserId());

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    }

    private void registerCircle(final String title, final String description,
                                final String code, final int creator) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_AddCircle, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject circles = jObj.getJSONObject("circle");
                    circle_id = circles.getInt("id");
                    session.setCircleId(circle_id);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        Toast.makeText(getApplicationContext(), "Circle created successfully !", Toast.LENGTH_LONG).show();
                        // Launch Mainactivity
                        Intent intent = new Intent(
                                AddCircleActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("title", title);
                params.put("description", description);
                params.put("code", code);
                params.put("creator", String.valueOf(creator));
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
