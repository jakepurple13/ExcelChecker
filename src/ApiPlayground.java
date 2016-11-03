import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ApiPlayground {

	final static int NAME = 0;
	final static int TIMEIN = 5;
	final static int TIMEOUT = 6;

	final static int DAY = 1;
	final static int STARTTIME = 2;
	final static int ENDTIME = 3;
	
	static HashMap<String, Student> hmss;
	static ArrayList<Exception> badThings;
	static ArrayList<String> timing;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws EncryptedDocumentException,
			InvalidFormatException, IOException, ParseException {
		// TODO Auto-generated method stub
		
		hmss = new HashMap<String, Student>();
		badThings = new ArrayList<Exception>();
		timing = new ArrayList<String>();

		// READS SCHEDULE WORKBOOK
		suspectedTimeReadIn();

		/*System.out.println(hmss.values());
		System.out.println(hmss.keySet());
		 */
		for (Student s : hmss.values()) {
			System.err.println(s);
		}

		System.out.println("------------");
		
		// -----------------------------------
		
		// READS WORKBOOK THAT HAS EXACT TIME DETAIL
		actualReadIn();

		System.err.println("------------");

		// ------------------------------

		// CREATES WORKBOOK AND ADDS DATA TO IT
		writeOut();
		

	}
	
	public static void suspectedTimeReadIn() throws EncryptedDocumentException, InvalidFormatException, IOException {
		Workbook wb1 = WorkbookFactory.create(new File("schedules_on_kronos_201670.xls"));

		Sheet sheet1 = wb1.getSheet("schedule matches google drive");

		for (int i = 1; i < sheet1.getLastRowNum(); i++) {

			Row r = sheet1.getRow(i); //the row we are on
			String day; //the day
			String named; //the name of the employee
			String startTime; //the time the employee is going to start working
			String endTime; //the time the employee is going to end working
			DateTimeFormatter parseFormat; //will parse a date into a LocalTime format
			LocalTime start; //the start time in LocalTime format
			LocalTime end; //the end time in LocalTime format
			Cell two; //the second column which should be the start time
			Cell three; //the third column which should be the end time
			
			named = r.getCell(NAME).toString();

			if (named.equals("")) {
				break;
			}

			day = r.getCell(DAY).toString();

			two = r.getCell(STARTTIME);
			three = r.getCell(ENDTIME);

			if (DateUtil.isCellDateFormatted(two)) {
				startTime = new DataFormatter().formatCellValue(two);
			} else {
				startTime = String.valueOf((int) (two.getNumericCellValue()));
			}

			if (DateUtil.isCellDateFormatted(three)) {
				endTime = new DataFormatter().formatCellValue(three);
			} else {
				endTime = String.valueOf((int) (three.getNumericCellValue()));
			}
			
			parseFormat = new DateTimeFormatterBuilder()
					.appendPattern("h:mm:ss a").toFormatter();

			start = LocalTime.parse(startTime, parseFormat);
			end = LocalTime.parse(endTime, parseFormat);

			if (hmss.containsKey(named)) {
				hmss.get(named).addTime(day, start, end);
			} else {
				Student s = new Student(named);
				hmss.put(named, s);
				hmss.get(named).addTime(day, start, end);
			}
		}
	}
	
	public static void actualReadIn() throws EncryptedDocumentException, InvalidFormatException, IOException, ParseException {
		Workbook wb = WorkbookFactory.create(new File(
				"EmployeeTimeDetail_PayPeriodEnd10-15-16.xlsx"));
		
		Sheet sheet = wb.getSheet("Spans");

		for (int rows = 5; rows < sheet.getLastRowNum(); rows++) {

			Date in = null;
			Date out = null;
			String named;

			Row r = sheet.getRow(rows);
			
			Cell a = r.getCell(TIMEIN);
			Cell c = r.getCell(TIMEOUT);
			
			named = r.getCell(NAME).toString().trim(); 
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");

			in = sdf.parse(a.toString());
			out = sdf.parse(c.toString());
			
			System.err.println(named + "\t" + in + "\t" + out);
			
			if(hmss.containsKey(named)) {
				Student s = hmss.get(named);

				System.out.println(s.toString());
				
				System.out.println(s.checkTime(in) + "\t" + s.checkTime(out));
				timing.add(s.checkTime(in) + "\t" + s.checkTime(out));
				
			} else {
				badThings.add(new MissingPersonException(named));
			}
			
			
			
		}
	}
	
	public static void writeOut() throws IOException {
		// Workbook flagged = new HSSFWorkbook();
				Workbook flagged = new XSSFWorkbook();
				CreationHelper createHelper = flagged.getCreationHelper();
				Sheet flagShip = flagged.createSheet("People who are bad");
				
				ArrayList<Student> als = new ArrayList<Student>(hmss.values());
				
				for (int i = 0; i < als.size(); i++) {
					
					// Create a row and put some cells in it. Rows are 0 based.
					Row row = flagShip.createRow(i);
					// Create a cell and put a value in it.
					Cell cell = row.createCell(0);
					cell.setCellValue(als.get(i).toString());
					
					Cell cell1 = row.createCell(1);
					cell1.setCellValue(timing.get(i));

					//String in = ale.get(i).getWanted().getTimeIn();
					//String out = ale.get(i).getWanted().getTimeOut();

					/*DateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
					Date inTime = (Date) formatter.parse(in);
					Date outTime = (Date) formatter.parse(out);

					int inPlace = ale.get(i).realTimeIn.compareTo(inTime);
					int outPlace = ale.get(i).realTimeOut.compareTo(outTime);

					Cell cell1 = row.createCell(1);
					cell1.setCellValue(inPlace);

					Cell cell2 = row.createCell(2);
					cell2.setCellValue(outPlace);*/

					
					  // Or do it on one line. row.createCell(1).setCellValue(1.2);
					 
				}
				
				Sheet badShip = flagged.createSheet("Exceptions");
				
				for (int i = 0; i < badThings.size(); i++) {
					
					// Create a row and put some cells in it. Rows are 0 based.
					Row row = badShip.createRow(i);
					// Create a cell and put a value in it.
					Cell cell = row.createCell(0);
					cell.setCellValue(badThings.get(i).getMessage());
					
				}

				// Write the output to a file
				FileOutputStream fileOut = new FileOutputStream("captainslog.xlsx");
				flagged.write(fileOut);
				fileOut.close();
				flagged.close();
	}
	

}
