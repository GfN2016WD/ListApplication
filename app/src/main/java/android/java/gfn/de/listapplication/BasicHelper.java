package android.java.gfn.de.listapplication;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by TN77 on 29.03.2017.
 */

public class BasicHelper {

    public static final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:MM");

    public static String formatdate(Date date) {
        return formatter.format(date);
    }

    public static Date parseDate(String dateString) {
        Date parsedDate = null;
        try {
            parsedDate = formatter.parse(dateString);
        } catch (ParseException pEx) {
            pEx.getMessage();
            parsedDate = new Date();
        }
        return parsedDate;
    }
}
