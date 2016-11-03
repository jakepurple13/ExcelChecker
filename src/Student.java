import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
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

		//String day = getDay(timeIn.getDay());

		time.add(new Times(day, timeIn, timeOut));
	}
	
	public int checkTime(Date timed) {
		
		Times t = new Times("Fri", LocalTime.MIN, LocalTime.MAX);
		
		for(int i=0;i<time.size();i++) {
			String day = getDay(timed.getDay());
			
			if(time.get(i).dayOfWeek.equals(day)) {
				t = time.get(i);
				break;
			}
		}
			
		Date date = timed;
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss a");
		String startCheck = formatter.format(date);
		
		DateTimeFormatter parseFormat = new DateTimeFormatterBuilder()
		.appendPattern("hh:mm:ss a").toFormatter();

		LocalTime started = LocalTime.parse(startCheck, parseFormat);
		
		System.out.println(started);
		
		return t.timeIn.getMinute()-started.getMinute();
		
		
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
		
		for(Times t : time) {
			info += "\t" + t;
		}
		
		return info;
	}
	

}
