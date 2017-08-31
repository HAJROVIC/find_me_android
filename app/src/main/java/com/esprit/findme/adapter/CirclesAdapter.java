package com.esprit.findme.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.esprit.findme.R;
import com.esprit.findme.models.Circle;

import java.util.List;

/**
 * Created by Med-Amine on 14/11/2016.
 */

public class CirclesAdapter extends ArrayAdapter<Circle> {

    Context context;
    int layoutResourceId;
    List<Circle> circles = null;



    public CirclesAdapter(Context context, int layoutResourceId, List<Circle> circles) {
        super(context, layoutResourceId, circles);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.circles = circles;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
            CircleHolder holder = new CircleHolder();
        if (view == null) {
            //charger tout le contenu d'un fichier xml
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layoutResourceId, parent, false);

            holder.circleTitle = (TextView) view.findViewById(R.id.circle_title);
            holder.circleDescription = (TextView) view.findViewById(R.id.circle_description);

            view.setTag(holder);
        } else {
            holder = (CircleHolder) view.getTag();
        }
        Circle circle = circles.get(position);
        holder.circleTitle.setText(circle.getTitle());
        holder.circleDescription.setText(circle.getDescription());



        return view;
    }

    class CircleHolder {
        TextView circleTitle;
        TextView circleDescription;

    }
}
