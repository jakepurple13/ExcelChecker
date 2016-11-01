import java.util.Date;


public class Employee {
	
	WantedTimed wt;
	Date realTimeIn;
	Date realTimeOut;
	String name;
	
	public Employee() {
		
	}
	
	public Employee(Date realTimeIn, Date realTimeOut, String name) {
		this.realTimeIn = realTimeIn;
		this.realTimeOut = realTimeOut;
		this.name = name;
	}
	
	public Employee(Date realTimeIn, Date realTimeOut, String name, WantedTimed wt) {
		this.realTimeIn = realTimeIn;
		this.realTimeOut = realTimeOut;
		this.name = name;
		this.wt = wt;
	}

	public Date getRealTimeIn() {
		return realTimeIn;
	}

	public void setRealTimeIn(Date realTimeIn) {
		this.realTimeIn = realTimeIn;
	}

	public Date getRealTimeOut() {
		return realTimeOut;
	}

	public void setRealTimeOut(Date realTimeOut) {
		this.realTimeOut = realTimeOut;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setWantedTimed(String day, String timeIn, String timeOut) {
		wt = new WantedTimed(day, timeIn, timeOut);
	}
	
	public void setWanted(WantedTimed wt) {
		this.wt = wt;
	}
	
	public WantedTimed getWanted() {
		return wt;
	}
	
	
	
	@Override
	public String toString() {
		return name + "\t" + realTimeIn + "\t" + realTimeOut + "\n" + wt;
	}
	
	
	

}
