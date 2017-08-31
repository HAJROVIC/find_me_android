package com.esprit.findme.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.findme.activity.JoinCircleActivity;
import com.esprit.findme.activity.MapsActivity;
import com.esprit.findme.activity.PostActivity;
import com.esprit.findme.activity.ProfileActivity;
import com.esprit.findme.dao.CircleDAO;
import com.esprit.findme.fragment.ChatFragment;
import com.esprit.findme.fragment.HomeFragment;
import com.esprit.findme.R;
import com.esprit.findme.fragment.FriendsFragment;
import com.esprit.findme.models.Circle;
import com.esprit.findme.utils.AppConfig;
import com.esprit.findme.utils.SessionManager;
import com.esprit.findme.utils.ViewPagerAdapter;
import com.esprit.findme.activity.AddCircleActivity;
import com.esprit.findme.activity.LoginActivity;

import java.util.ArrayList;


/**
 * Created by TIBH on 07/11/2016.
 */

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    FloatingActionButton fab_btn;
    private SessionManager session;
    private ArrayList<Circle> circles;
    private CircleDAO circleDao;
    private Spinner spinner;
    String[] circlesArray;
    ArrayAdapter<String> adapter;
    private int[] tabIcons = {
            R.drawable.ic_menu_home,
            R.drawable.ic_menu_friends,
            R.drawable.ic_menu_chat
    };
    private String[] tabTitle = {
            " Home",
            "Friends",
            "Chat"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(tabTitle[0]);
        setSupportActionBar(toolbar);
        circles = new ArrayList<>();
        circleDao = new CircleDAO();
        spinner = (Spinner) findViewById(R.id.spinner);
        circlesArray = new String[]{};
        session = new SessionManager(getApplicationContext());

        //fab menu settings
        fab_btn = (FloatingActionButton) findViewById(R.id.fab_post);


        //initialisation
        AllFloatingButtonActions(0);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(0xFFFFFFFF);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

                AllFloatingButtonActions(position);
                toolbar.setTitle(tabTitle[position]);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //circlesArray = circles.toArray(circlesArray);

        Volley.newRequestQueue(this).add(new StringRequest(Request.Method.GET, AppConfig.URL_GET_CIRCLES + "?user_id=" + session.getUserId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            circleDao.getAllCircle(circles, response);
                            circlesArray = new String[circles.size()];
                            for (int k = 0; k < circles.size(); k++) {
                                circlesArray[k] = circles.get(k).getTitle();
                            }
                            adapter = new ArrayAdapter<String>
                                    (MainActivity.this, android.R.layout.simple_spinner_item, circlesArray);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            spinner.setAdapter(adapter);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                                    ((TextView) adapterView.getChildAt(0)).setTextSize(18);


                                    session.setCircleCode(circles.get(i).getCode());
                                    session.setCircleId(circles.get(i).getId());


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error setting note : " + error.getMessage());
                if (error instanceof TimeoutError) {
                    System.out.println("erreur");
                }
            }
        }));

        // session manager

        if (!session.isLoggedIn()) {
            logoutUser();
        }

    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawermenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fab_profile:
                Intent intent2 = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent2);
                break;
            case R.id.fab_invite:
                String title = getResources().getString(R.string.chooser_title);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, title + session.getCircleCode());
                sendIntent.setType("text/plain");

                // Verify that the intent will resolve to an activity
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(sendIntent);
                }
                break;
            case R.id.fab_join:
                Intent intent0 = new Intent(MainActivity.this, JoinCircleActivity.class);
                startActivity(intent0);
                break;
            case R.id.fab_circle:
                Intent intent1 = new Intent(MainActivity.this, AddCircleActivity.class);
                startActivity(intent1);
                break;
            case R.id.fab_logout:
                logoutUser();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        session.getEditor().clear();
        session.getEditor().commit();
        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void AllFloatingButtonActions(int position) {
        switch (position) {
            case 0:
                fab_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_post, null));
                fab_btn.show();

                fab_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, PostActivity.class);
                        startActivity(intent);
                    }
                });

                break;

            case 1:

                fab_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_map, null));
                fab_btn.show();

                fab_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(intent);
                    }
                });
                break;
            default:
                fab_btn.hide();
        }
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);

    }

    private void setupViewPager(ViewPager viewPager) {

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new HomeFragment());
        viewPagerAdapter.addFragments(new FriendsFragment());
        viewPagerAdapter.addFragments(new ChatFragment());
        viewPager.setAdapter(viewPagerAdapter);
    }


}