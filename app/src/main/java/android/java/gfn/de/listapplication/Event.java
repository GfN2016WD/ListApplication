package android.java.gfn.de.listapplication;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Date;

/**
 * Created by TN77 on 23.03.2017.
 */

public class Event implements Serializable{

    private static final long serialVersionUID = 1L;

    public Event() {

    }

    public Event(Date start, Date end, String description, int status) {
        this.start = start;
        this.end = end;
        this.description = description;
        this.status = status;
    }

    private long id;
    private Date created;
    private Date start;
    private Date end;
    private String description;
    private double locationLong;
    private double locationLat;
    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(double locationLong) {
        this.locationLong = locationLong;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        if (id > 0) {
            values.put("id", id);
        }
        values.put("created", created.getTime());
        values.put("eventstart", start.getTime());
        values.put("eventend", end.getTime());
        values.put("description", description);
        values.put("locationLong",locationLong);
        values.put("locationLat",locationLat);
        values.put("status",status);
        return values;
    }

    public void setbyCursor(Cursor cursor) {
        setId(cursor.getLong(cursor.getColumnIndex("id")));
        setCreated(new Date(cursor.getLong(cursor.getColumnIndex("created"))));
        setStart(new Date(cursor.getLong(cursor.getColumnIndex("eventstart"))));
        setEnd(new Date(cursor.getLong(cursor.getColumnIndex("eventend"))));
        setDescription(cursor.getString(cursor.getColumnIndex("description")));
        setLocationLong(cursor.getDouble(cursor.getColumnIndex("locationLong")));
        setLocationLat(cursor.getDouble(cursor.getColumnIndex("locationLat")));
        setStatus(cursor.getInt(cursor.getColumnIndex("status")));
    }

    public static Event getEventByCursor(Cursor cursor) {
        Event event = new Event();
        event.setbyCursor(cursor);
        return event;
    }
}
