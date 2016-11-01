import java.util.Date;
import java.util.HashMap;


public class Person {
	
	String name;
	HashMap<Date, WorkTime> working;
	
	public Person(String name) {
		this.name = name;
		working = new HashMap<Date, WorkTime>();
	}
	
	
	public void setUp() {
		
	}
	
	
	

}
