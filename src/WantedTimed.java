public class WantedTimed {
	String day;
	String timeIn;
	String timeOut;

	public WantedTimed(String day, String timeIn, String timeOut) {
		this.day = day;
		this.timeIn = timeIn;
		this.timeOut = timeOut;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	@Override
	public String toString() {
		return day + "\t" + timeIn + "\t" + timeOut;
	}
}
