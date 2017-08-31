package com.esprit.findme.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esprit.findme.R;
import com.esprit.findme.dao.InvitationDao;
import com.esprit.findme.main.MainActivity;
import com.esprit.findme.utils.SessionManager;

public class JoinCircleActivity extends AppCompatActivity {
    EditText code;
    SessionManager session;
    Button joinBtn;
    InvitationDao invitDao;
     private Toolbar toolbar;
    private Button updateBtn, backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_circle);
        code = (EditText) findViewById(R.id.et_join_code);
        joinBtn = (Button) findViewById(R.id.join_btn);
        toolbar = (Toolbar) findViewById(R.id.secondary_toolbar);
        setSupportActionBar(toolbar);

        updateBtn = (Button) findViewById(R.id.nextBtn);
        backBtn = (Button) findViewById(R.id.returnBtn);
        // Session manager
        session = new SessionManager(getApplicationContext());
        invitDao = new InvitationDao(JoinCircleActivity.this);
        updateBtn.setVisibility(View.INVISIBLE);
        updateBtn.setClickable(false);
        backBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(
                        JoinCircleActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }
        });
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code_circle = code.getText().toString().trim();
                if (!code_circle.isEmpty()) {
                  invitDao.addMember( code_circle, session.getUserId());
                    Toast.makeText(getApplicationContext(),
                            "added successfully!", Toast.LENGTH_LONG)
                            .show();
                    // Launch Mainactivity
                    Intent intent = new Intent(
                            JoinCircleActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

    }
}
