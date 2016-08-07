package utilities;

import java.text.*;
import java.util.Date;

public class DateUtils {
	
	public static String getCurrentDate() {
		Date date = new Date();
		DateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
		return dateformat.format(date);
	}
	
	public static String getCurrentTime() {
		Date date = new Date();
		DateFormat dateformat = new SimpleDateFormat("HH-mm-ss");
		return dateformat.format(date);
	}

}
