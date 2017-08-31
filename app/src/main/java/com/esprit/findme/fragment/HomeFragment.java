package com.esprit.findme.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.esprit.findme.R;
import com.esprit.findme.adapter.NewsAdapter;
import com.esprit.findme.dao.NewsDao;
import com.esprit.findme.models.News;
import com.esprit.findme.utils.AppConfig;
import com.esprit.findme.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<News> newsList;
    NewsDao newsDao;
    SessionManager session;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        session = new SessionManager(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        newsList = new ArrayList<>();
        newsDao = new NewsDao();
        adapter = new NewsAdapter(getActivity(), newsList);
       /* LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);*/
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        getNews();

        return view;
    }

    private void refresh() {

        mSwipeRefreshLayout.setRefreshing(true);
        getNews();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void getNews() {
        newsList.clear();

        Volley.newRequestQueue(getActivity()).add(new StringRequest(Request.Method.GET, AppConfig.URL_GET_NEWS + "?circle_id=" + session.getCircleId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null) {
                            newsDao.getAllNews(newsList, response);
                            adapter.notifyDataSetChanged();
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
    }


}
