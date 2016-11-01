import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

	final static int name = 0;
	final static int timeIn = 5;
	final static int timeOut = 6;

	public static void main(String[] args) throws EncryptedDocumentException,
			InvalidFormatException, IOException, ParseException {
		// TODO Auto-generated method stub

		// READS WORKBOOK THAT HAS EXACT TIME DETAIL

		Workbook wb = WorkbookFactory.create(new File(
				"EmployeeTimeDetail_PayPeriodEnd10-15-16.xlsx"));
		// InputStream inp = new
		// FileInputStream("EmployeeTimeDetail_PayPeriodEnd10-15-16.xlsx");
		// InputStream inp = new FileInputStream("workbook.xlsx");

		// Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheet("Spans");

		// System.out.println(sheet.getSheetName());

		ArrayList<Employee> ale = new ArrayList<Employee>();

		for (int rows = 5; rows < sheet.getLastRowNum(); rows++) {

			Date in = null;
			Date out = null;
			String named = "";

			Row r = sheet.getRow(rows);

			named = r.getCell(name).getStringCellValue();
			// in = r.getCell(timeIn).getDateCellValue();
			// out = r.getCell(timeOut).getDateCellValue();

			String a = r.getCell(timeIn).toString();
			String c = r.getCell(timeOut).toString();

			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm");

			in = sdf.parse(a);
			out = sdf.parse(c);

			// System.out.println(named + "\t" + a + "\t" + c + "\t" + in);

			Employee e = new Employee(in, out, named);

			// System.out.println(e);

			ale.add(e);
		}

		System.err.println("------------");

		// -----------------------------------q

		// READS SCHEDULE WORKBOOK

		Workbook wb1 = WorkbookFactory.create(new File(
				"schedules_on_kronos_201670.xls"));

		Sheet sheet1 = wb1.getSheet("schedule matches google drive");

		for (int i = 1; i < sheet1.getLastRowNum(); i++) {

			Row r = sheet1.getRow(i);

			String named = r.getCell(0).toString();

			if (named.equals("")) {
				break;
			}

			String day = r.getCell(1).toString();
			String startTime;
			String endTime;

			// System.err.println(named + "\t" + day + "\t" + startTime + "\t" +
			// endTime);

			String q;
			Cell two = r.getCell(2);
			Cell three = r.getCell(3);

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

			WantedTimed wt = new WantedTimed(day, startTime, endTime);
			// System.out.println(wt);
			// ale.get(i-1).setWantedTimed(day, startTime, endTime);
			ale.get(i - 1).setWanted(wt);
		}

		for (int i = 0; i < ale.size(); i++) {
			System.out.println(ale.get(i));
		}

		// ------------------------------

		// CREATES WORKBOOK AND ADDS DATA TO IT

		// Workbook flagged = new HSSFWorkbook();
		Workbook flagged = new XSSFWorkbook();
		CreationHelper createHelper = flagged.getCreationHelper();
		Sheet flagShip = flagged.createSheet("People who are bad");

		for (int i = 0; i < ale.size(); i++) {

			// Create a row and put some cells in it. Rows are 0 based.
			Row row = flagShip.createRow(i);
			// Create a cell and put a value in it.
			Cell cell = row.createCell(0);
			cell.setCellValue(ale.get(i).getName());

			String in = ale.get(i).getWanted().getTimeIn();
			String out = ale.get(i).getWanted().getTimeOut();

			DateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
			Date inTime = (Date) formatter.parse(in);
			Date outTime = (Date) formatter.parse(out);

			int inPlace = ale.get(i).realTimeIn.compareTo(inTime);
			int outPlace = ale.get(i).realTimeOut.compareTo(outTime);

			Cell cell1 = row.createCell(1);
			cell1.setCellValue(inPlace);

			Cell cell2 = row.createCell(2);
			cell2.setCellValue(outPlace);

			/*
			 * // Or do it on one line. row.createCell(1).setCellValue(1.2);
			 */
		}

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("captainslog.xlsx");
		flagged.write(fileOut);
		fileOut.close();
		flagged.close();

	}

}
