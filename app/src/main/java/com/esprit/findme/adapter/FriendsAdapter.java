package com.esprit.findme.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esprit.findme.R;
import com.esprit.findme.activity.MapsActivity;
import com.esprit.findme.activity.TransitDirectionActivity;
import com.esprit.findme.main.MainActivity;
import com.esprit.findme.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by TIBH on 27/11/2016.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyViewHolder> {
    private Context mContext;
    private List<User> friendList;
    String userPosition;
    Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public FriendsAdapter(Context mContext, List<User> friendList,Activity activity) {
        this.mContext = mContext;
        this.friendList = friendList;
        this.activity =activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friends_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        User user = friendList.get(position);
        holder.title.setText(user.getName());
        Picasso.with(mContext).load(user.getPhoto()).into(holder.thumbnail);


        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
                userPosition=friendList.get(position).getPosition();
            }
        });
    }

    /**
     * Showing popup menu when tapping on img3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, userPosition, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, TransitDirectionActivity.class);
                    Bundle b = new Bundle();
                    b.putString("position",userPosition);
                    activity.startActivity(intent);
                    activity.finish();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Send message", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }
}
