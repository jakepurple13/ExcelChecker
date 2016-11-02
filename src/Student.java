import java.time.LocalTime;
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
