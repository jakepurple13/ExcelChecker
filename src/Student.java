import java.util.ArrayList;
import java.util.Date;

public class Student {

	ArrayList<Times> time;
	String name;

	public Student(String name) {

		this.name = name;

	}

	public void addTime(Date timeIn, Date timeOut) {

		String day = getDay(timeIn.getDay());

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

}
