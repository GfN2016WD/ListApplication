package android.java.gfn.de.listapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//public class MainActivity extends ListActivity { // Activity with ListView layout

    private DBManager db = null;
/*    private String[] data = {
            "Peter Parker",
            "Bruce Wayne",
            "Clark Kent",
            "Peter Pan"
    };*/
    private Event[] data;
    // integrate ListView into AppCompatActivity, no need fpr ListActivity
    private ListView eventList;
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MainActivity", "onCreate() start");
        db = new DBManager(this, DBManager.getDbTable());
        Event event = new Event();
        event.setCreated(new Date());
        event.setDescription("Test test test");
        event.setStart(new Date());
        event.setEnd(new Date());
        event.setLocationLat(30);
        event.setLocationLong(60);
/*        db.insertValues(event);
        db.insertValues(event);
        db.insertValues(event);
        db.insertValues(event); */
//        getListView().setChoiceMode(List  View.CHOICE_MODE_MULTIPLE);
        data = db.open().findEntries().toArray(new Event[0]);
        db.close();
        eventList = (ListView)findViewById(R.id.eventList);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, data);
        EventAdapter adapter = new EventAdapter(MainActivity.this, R.layout.eventlist_row, data);
        View header = getLayoutInflater().inflate(R.layout.eventlist_header, null); // eventlist_header should be eventlist_header
        eventList.addHeaderView(header);
        eventList.setAdapter(adapter);

        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "Hallo Welt", Toast.LENGTH_SHORT).show();
            }
        });
//        View row = getLayoutInflater().inflate(R.layout.eventlist_row, null); // eventlist_header should be eventlist_header
//        View event =  getLayoutInflater().inflate(R.layout.eventlist_entry_linear, null); // eventlist_header should be eventlist_header
        // XML Datei zum Anzeigen von Event-Details
//        eventList.addHeaderView(row);
//        eventList.addHeaderView(header);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() start");
//        EventAdapter adapter = new EventAdapter(MainActivity.this, R.layout.eventlist_row, data);
//        Log.d("MainActivity","onResume()");
        data = db.open().findEntries().toArray(new Event[0]);
        db.close();
        EventAdapter adapter = new EventAdapter(MainActivity.this, R.layout.eventlist_row, data);
        eventList.setAdapter(adapter);
        Log.d(TAG, "onResume() end");
    }

    // onCreate und onListItemClick für ListActivity
/*    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("MainActivity", "onCreate() start");
        db = new DBManager(this, DBManager.getDbTable());
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_checked, data);
        setListAdapter(adapter);
/*        List<Event> list = db.findEntries();
        for (Event e : list) {
            Log.i("MainActivity", e.getId() + ": " + e.getDescription());
        }
        Log.i("MainActivity", "onCreate() end");
    }*/

/*    protected void onListItemClick(ListView l, View v, int position, long id) {
        CheckedTextView ctv = (CheckedTextView) v;
        if (ctv.isChecked()) {
            Toast.makeText(this, "CheckedView: Bla bla bla bla!" + ctv.getText(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "I am not checked!" + ctv.getText(), Toast.LENGTH_SHORT).show();
        }
        Log.d("MainActivity", data[position]);
    };*/

/*    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        CheckedTextView ctv = (CheckedTextView) v;
        if (ctv.isChecked()) {
            Toast.makeText(this, "CheckedView: Bla bla bla bla!" + ctv.getText(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "I am not checked!" + ctv.getText(), Toast.LENGTH_SHORT).show();
        }
        Log.d("MainActivity", data[position]);
    };*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.newEvent:
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivityForResult(intent, 1);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult() start");
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Event event = (Event) data.getExtras().getSerializable("event");
                Log.d(TAG,event.getDescription());
                db.open().insertValues(event);
                // TODO: - add new events ListView
                db.close();
            }
        }
        Log.d(TAG, "onActivityResult() end");
    }
}
