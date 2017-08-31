package com.esprit.findme.activity;

import android.app.Activity;
import android.os.Bundle;

import com.esprit.findme.R;
import com.esprit.findme.fragment.LoginFragment;


/**
 * Created by TIBH on 07/11/2016.
 */

public class LoginActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getFragmentManager().beginTransaction().add(R.id.container,new LoginFragment()).commit();

    }


}
