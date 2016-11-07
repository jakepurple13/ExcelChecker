import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;

public class Student {

	ArrayList<Times> time;
	String name;

	public Student(String name) {

		this.name = name;
		time = new ArrayList<Times>();

	}

	public void addTime(String day, LocalTime timeIn, LocalTime timeOut) {

		// String day = getDay(timeIn.getDay());

		time.add(new Times(day, timeIn, timeOut));
	}

	public double checkTime(Date timed) {

		Times t = new Times("Fri", LocalTime.MIN, LocalTime.MAX);

		for (int i = 0; i < time.size(); i++) {
			String day = getDay(timed.getDay());

			if (time.get(i).dayOfWeek.equals(day)) {
				t = time.get(i);
				break;
			}
		}

		Date date = timed;
		DateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
		String startCheck = formatter.format(date);

		DateTimeFormatter parseFormat = new DateTimeFormatterBuilder()
				.appendPattern("hh:mm:ss a").toFormatter();

		LocalTime started = LocalTime.parse(startCheck, parseFormat);

		//System.out.println(started);
		
		double num = 60 - Math.abs(t.timeIn.getMinute() - started.getMinute());
		
		//return t.timeIn.getMinute() - started.getMinute();
		return num;

	}

	public boolean areTheyGood(Date timed) {

		double num = checkTime(timed);

		num = 60 - Math.abs(num);

		return num <= 10;
	}

	public ArrayList<Times> getTime() {
		return time;
	}

	private String getDay(int day) {

		switch (day) {
		case 0:

			return "Sun";
		case 1:

			return "Mon";
		case 2:

			return "Tue";
		case 3:

			return "Wed";
		case 4:

			return "Thu";
		case 5:

			return "Fri";
		case 6:

			return "Sat";

		default:
			return "";
		}

	}

	@Override
	public String toString() {

		String info = name;

		for (Times t : time) {
			info += "\t" + t;
		}

		return info;
	}

}
