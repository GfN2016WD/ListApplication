package android.java.gfn.de.listapplication;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by TN77 on 29.03.2017.
 */

public class EventAdapter extends ArrayAdapter<Event> {
    private Context context;
    private int resource;
    private List<Event> eventList;

    // ArrayAdapter(Context context, int resource, T[] objects)
    public EventAdapter(Context context, int resource, List<Event> eventList) {
        super(context, resource, eventList);
        this.context = context;
        this.resource = resource;
        this.eventList = eventList;
    }

    @NonNull
    @Override
//  View getView(int position, View convertView, ViewGroup parent)
    public View getView(int position, View convertView, ViewGroup parent) {
        EventHolder holder = null;

        if(convertView == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(resource,parent,false);
            holder = new EventHolder();
            holder.txtDescription = (TextView)convertView.findViewById(R.id.txtDescription);
            holder.txtStart = (TextView)convertView.findViewById(R.id.txtStart);
            holder.txtEnd = (TextView)convertView.findViewById(R.id.txtEnd);
            holder.imgStatus = (ImageView)convertView.findViewById(R.id.imgStatus);
            convertView.setTag(holder);
        } else {
            holder = (EventHolder) convertView.getTag();
            if (holder == null) {
                Log.d("EventAdapter","holder is null!");
            }
        }
//        return super.getView(position, convertView, parent);
        Event event = eventList.get(position);
        holder.txtDescription.setText(event.getDescription());
        holder.txtStart.setText(event.getStart().toString());
        holder.txtEnd.setText(event.getEnd().toString());
        holder.imgStatus.setImageResource(R.drawable.ic_add_circle_black_24dp);
        return convertView;
    }

    static class EventHolder {
        TextView txtDescription;
        TextView txtStart;
        TextView txtEnd;
        ImageView imgStatus;
    }
}
