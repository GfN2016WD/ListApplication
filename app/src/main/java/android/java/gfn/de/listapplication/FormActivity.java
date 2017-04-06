package android.java.gfn.de.listapplication;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class FormActivity extends AppCompatActivity implements LocationListener {

    private EditText txtDescription;
    private EditText txtStart;
    private EditText txtEnd;
    private EditText txtLong;
    private EditText txtLat;
    private CheckBox chkStatus;
    private TextView txtStatus;
    private Button saveBtn;
    private Button cancelBtn;
    private EventManager eventManager = EventManager.getInstance();
    private Event currentEvent = eventManager.getCurrentEvent();
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        saveBtn = (Button)findViewById(R.id.btnSave);
        cancelBtn = (Button)findViewById(R.id.btnCancel);
        txtDescription = (EditText)findViewById(R.id.txtDescription);
        txtStart = (EditText)findViewById(R.id.txtStart);
        txtEnd = (EditText)findViewById(R.id.txtEnd);
        txtStatus = (TextView)findViewById(R.id.txtStatus);
        chkStatus = (CheckBox)findViewById(R.id.chkStatus);
        txtLong = (EditText)findViewById(R.id.location_long);
        txtLat = (EditText)findViewById(R.id.location_lat);


        setFormByEvent();

/*        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(); */

        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Event event = null;
                if (currentEvent == null) {
                    event = new Event();
                    event.setCreated(new Date());
                } else {
                    event = currentEvent;
                }
                event.setDescription(txtDescription.getText().toString());
                event.setStart(BasicHelper.parseDate(txtStart.getText().toString()));
                event.setEnd(BasicHelper.parseDate(txtEnd.getText().toString()));
                // TODO auf DatePicker umstellen
                event.setLocationLat(30);
                event.setLocationLong(60);
                // TODO Status dynamisch setzen
                if (chkStatus.isChecked()) {
                    event.setStatus(1);
                } else {
                    event.setStatus(0);
                }
                Intent intent = new Intent();
//                intent.putExtra("event", event);
                eventManager.setCurrentEvent(event);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        txtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFragment fragment = new DateFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("fieldID", R.id.txtStart);
                // add bundle to fragment
                fragment.setArguments(bundle);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(fragment,"Date Dialog");
                transaction.commit();
            }
        });

        txtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFragment fragment = new DateFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("fieldID", R.id.txtEnd);
                fragment.setArguments(bundle);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(fragment,"Date Dialog");
                transaction.commit();
            }
        });

        /* Event event = new Event();
        event.setCreated(new Date());
        event.setDescription("Das ist was ganz feines!");
        event.setStart(new Date());
        event.setEnd(new Date());
        event.setLocationLong(53.5511);
        event.setLocationLat(9.9937);
        event.setStatus(1);*/
    }

    public void onCheckChangeStatus(View view) {
        // Is the view now checked?
        if (chkStatus.isChecked()) {
            txtStatus.setText(getResources().getString(R.string.status_active));
        } else {
            txtStatus.setText(getResources().getString(R.string.status_inactive));
        }
    }

    public void setDate(int fieldId, Date date) {
        ((TextView)findViewById(fieldId)).setText(BasicHelper.formatdate(date));
    }

    private void setFormByEvent() {
        if (currentEvent != null) {
            txtDescription.setText(currentEvent.getDescription());
            txtStart.setText(BasicHelper.formatdate(currentEvent.getStart()));
            txtEnd.setText(BasicHelper.formatdate(currentEvent.getEnd()));
            // TODO: set status and checkbox for status!
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "GPS", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "GPS disabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            currentLocation = location;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}
