package org.xdams.utility;

public class DateUtil {

	public static String getDataSystem(String formatDate) throws Exception {
		String dataSystem = null;
		try {
			if (formatDate == null) {
				formatDate = "dd-MM-yyyy";
			}
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(formatDate);
			java.util.Date date = new java.util.Date();
			dataSystem = format.format(date);
		} catch (Exception e) {
			throw new Exception("Formato data non valido!");
		}
		return dataSystem;
	}

	public static String getTimeSystem(String formatTime) throws Exception {
		String timeSystem = null;
		try {
			if (formatTime == null) {
				formatTime = "HH:mm:ss";
			}
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(formatTime);
			java.util.Date date = new java.util.Date();
			timeSystem = format.format(date);
		} catch (Exception e) {
			throw new Exception("Formato ora non valido");
		}
		return timeSystem;
	}
}
