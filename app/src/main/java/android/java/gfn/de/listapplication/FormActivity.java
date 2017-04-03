package android.java.gfn.de.listapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

public class FormActivity extends AppCompatActivity {

    private EditText txtDescription;
    private EditText txtStart;
    private EditText txtEnd;
    private CheckBox chkStatus;
    private TextView txtStatus;
    private Button saveBtn;
    private Button cancelBtn;
    Event event;

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
                event = new Event();
                event.setCreated(new Date());
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
                intent.putExtra("event", event);
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
        boolean checked = chkStatus.isChecked();
        if (checked) {
            txtStatus.setText(getResources().getString(R.string.status_active));
        } else {
            txtStatus.setText(getResources().getString(R.string.status_inactive));
        }
    }
}
