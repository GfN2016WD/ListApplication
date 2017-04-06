package android.java.gfn.de.listapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.text.Normalizer;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Created by TN77 on 05.04.2017.
 */
 // public class DateFragment extends Fragment {
public class DateFragment extends AppCompatDialogFragment {
    // Fragments can be added to existing Layout
    // Fragment, if a fragment is replaced with a new instance
    // then the old instance is "deleted"
    // Fragments are governed by FragmentManager
    private int fieldId;

    public DateFragment() {
        super();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();

        // Listener for Calendar
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            int count = 0;
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                final FormActivity context = (FormActivity) getActivity();

                // create a Time picker
                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        if (!timePicker.isShown()) {
                            GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute);
                            context.setDate(fieldId, gregorianCalendar.getTime());
                        }
                    }
                };
                // true => 24h
                if (count == 0) {
                    new TimePickerDialog(getActivity(),
                            listener, calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE), true).show();
                    count++;
                }
/*                Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth); */
                // get Activity, since Fragment is not instanceof Activity
//                GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month, dayOfMonth );
            }
        };

        return new DatePickerDialog(getActivity(), listener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            fieldId = bundle.getInt("fieldID");
        }
    }
}
