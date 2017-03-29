package android.java.gfn.de.listapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TN77 on 23.03.2017.
 */

public class DBManager extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private final static String DB_NAME = "eventlist";
    private final static String DB_TABLE_NAME = "events";

    public DBManager(Context activity, String dbName) {
        super(activity, dbName, null, 8);
        db = getWritableDatabase();
        Log.d("DBManager",db.getPath());
        Log.d("DBManager",String.valueOf(db.isOpen()));
        Log.d("DBManager",db.toString());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("DBManager","onCreate() started");
        try {
            String sqlStatement = "CREATE TABLE "+ DB_TABLE_NAME + " " +
                                  "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                  "created DATETIME NOT NULL, description VARCHAR(256), " +
                                  "eventstart DATETIME NOT NULL, eventend DATETIME NOT NULL, " +
                                  "locationLong FLOAT, locationLat FLOAT, status TINYINT(1) NOT NULL)";
            sqLiteDatabase.execSQL(sqlStatement);
        } catch (SQLException sqlEx) {
            Log.e("ListApplication",sqlEx.getMessage());
        }
        Log.d("DBManager","onCreate() end");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d("DBManager","onUpgrade() started");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NAME);
        onCreate(sqLiteDatabase);
        Log.d("DBManager","onUpgrade() end");
    }

    public long insertValues(Event event) {
        return db.insert(DB_TABLE_NAME, null, event.toContentValues());
    }

    public long updateValues(Event event) {
        return db.update(DB_TABLE_NAME, event.toContentValues(), "id"+event.getId(), null);
    }

    public Event findEntry(long id) {
        String cols[] = new String[]{"id","created", "description", "eventstart", "eventend",
                        "locationLong", "locationLat", "status"};
        Cursor cursor = db.query(DB_TABLE_NAME, cols, "id"+id, null, null, null, null);
        cursor.moveToFirst();
        Event event = Event.getEventByCursor(cursor);
        cursor.close();
        return event;
    }

    public List<Event> findEntries() {
        String cols[] = new String[]{"id","created", "description", "eventstart", "eventend",
                "locationLong", "locationLat", "status"};
        Cursor cursor = db.query(DB_TABLE_NAME, cols, null, null, null, null, null);
        List<Event> list = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(Event.getEventByCursor(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public static String getDbTable(){
        return DB_NAME;
    }

    public DBManager open() {
        db = getWritableDatabase();
        return this;
    }

    public void open(DBManager db) {
        if (db != null){
            db.close();
        };
    }
}
