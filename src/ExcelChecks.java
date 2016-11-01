import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelChecks {

	final static int name = 0;
	final static int timeIn = 5;
	final static int timeOut = 6;

	public static void main(String[] args) throws EncryptedDocumentException,
			InvalidFormatException, IOException, ParseException {
		// TODO Auto-generated method stub

		Workbook wb = WorkbookFactory.create(new File("EmployeeTimeDetail_PayPeriodEnd10-15-16.xlsx"));

		Sheet sheet = wb.getSheet("Spans");

		Workbook wb1 = WorkbookFactory.create(new File("schedules_on_kronos_201670.xls"));

		Sheet sheet1 = wb1.getSheet("schedule matches google drive");

		ArrayList<Employee> ale = new ArrayList<Employee>();

		for (int rows = 5; rows < sheet.getLastRowNum(); rows++) {

			Date in = null;
			Date out = null;
			String named = "";

			Row r = sheet.getRow(rows);

			named = r.getCell(name).getStringCellValue();

			String a = r.getCell(timeIn).toString();
			String c = r.getCell(timeOut).toString();

			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");

			in = sdf.parse(a);
			out = sdf.parse(c);

			// System.out.println(named + "\t" + a + "\t" + c + "\t" + in);
			
			
			
			
			Row row = sheet1.getRow(rows-4);

			String sheetOneName = row.getCell(0).toString();

			/*if (named.equals("")) {
				break;
			}*/

			System.out.println(sheetOneName + "\t" + row.getRowNum());
			
			String day = row.getCell(1).toString();
			String startTime = row.getCell(2).toString();
			String endTime = row.getCell(3).toString();

			// System.err.println(named + "\t" + day + "\t" + startTime + "\t" +
			// endTime);

			WantedTimed wt = new WantedTimed(day, startTime, endTime);
			// ale.get(i-1).setWantedTimed(day, startTime, endTime);
			
			System.out.println(wt);

			Employee e = new Employee(in, out, named, wt);

			System.out.println(e);

			ale.add(e);
		}

		System.err.println("------------");

		/*for (int i = 1; i < sheet1.getLastRowNum(); i++) {

			
		}*/

		for (int i = 0; i < ale.size(); i++) {
			System.out.println(ale.get(i));
		}

	}

}
