package com.esprit.findme.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.esprit.findme.R;
import com.esprit.findme.dao.UserDao;
import com.esprit.findme.main.MainActivity;
import com.esprit.findme.utils.SessionManager;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by TIBH on 14/11/2016.
 */

public class LoginFragment extends Fragment {
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private LoginButton loginfacebook;
    private ProgressDialog pDialog;
    private SessionManager session;
    private UserDao userDao;
    private CallbackManager callbackManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        View view =inflater.inflate(R.layout.fragment_login,container,false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        inputEmail = (EditText) view.findViewById(R.id.email);
        inputPassword = (EditText) view.findViewById(R.id.password);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) view.findViewById(R.id.btnLinkToRegisterScreen);
        loginfacebook =(LoginButton) view.findViewById(R.id.login_button);
        userDao=new UserDao(getActivity());

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        // SQLite database handler
        //db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getActivity().getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(getActivity(), MainActivity.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        }

        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));
        loginfacebook.setReadPermissions(Arrays.asList("public_profile","email"));
        loginfacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Bundle parameters = new Bundle();
                parameters.putString("fields", "name,email");
                //,birthday,hometown,number,picture.type(large)
                GraphRequest request =GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject me, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error
                                } else {
                                        String email = me.optString("email");
                                    inputEmail.setText(email);
                                    }

                                    // send email and id to your web server
                                    // userDao.registerUser(me.optString("name"),me.optString("email"),password,me.optString("city")
                                          //  ,number,me.optString("birthday"),photo);



                            }
                        });
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    userDao.checkLogin(email, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container,new RegisterFragment()).addToBackStack(null).commit();
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
