import java.time.LocalTime;
import java.util.Date;

public class Times {

	String dayOfWeek;
	LocalTime timeIn;
	LocalTime timeOut;

	public Times(String day, LocalTime timeIn, LocalTime timeOut) {
		
		dayOfWeek = day;
		this.timeIn = timeIn;
		this.timeOut = timeOut;
		
	}
	
	
	/*public boolean equals() {
		
	}*/
	
	@Override
	public String toString() {
		return dayOfWeek + " Start: " + timeIn + " End: " + timeOut; 
	}
	

}
