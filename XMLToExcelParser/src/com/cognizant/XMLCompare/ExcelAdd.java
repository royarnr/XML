package com.cognizant.XMLCompare;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelAdd {
	
	private String filename;
	private List<String> inputdata1;
	private List<String> inputdata2;
	private XSSFWorkbook workbook;
	
	public ExcelAdd(String excelname, List<String> datafirstXML, List<String> datasecondXML)
	{
		this.filename = excelname;
		this.inputdata1 = datafirstXML;
		this.inputdata2 = datasecondXML;
		try
		{
		this.workbook = new XSSFWorkbook();
		}
		catch(Exception e)
		{
			System.out.println("Unable to initialize workbook");
		}
	}
	
	public Sheet openSheet(String sheet)
	{
		Sheet worksheet = null;
		try{
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				if (workbook.getSheetAt(i).getSheetName().equals(sheet))
				{
					worksheet = workbook.getSheetAt(i);
					break;
				}
			}
			
			if(worksheet==null)
			{
			worksheet = workbook.createSheet(sheet);
			}
		}
		catch (Exception e)
		{
			System.out.println("Unable to access/create data sheet");
		}
		
		return worksheet;
		
	}
	
	public void addHeaderfirstXML(String sheetname)
	{
		Sheet sheet = openSheet(sheetname);
		Row row = null;
		
		row = sheet.createRow(0);
		String columnnames = inputdata1.get(0);
		String[] column = columnnames.split("~");
		for (int i = 0; i < column.length; i++) {
			Cell cell = row.createCell(i);
			setCellstyle(workbook, cell);
			cell.setCellType(CellType.STRING);
			cell.setCellValue(column[i]);
			
		}
		
	}
	
	public void addHeadersecondXML(String sheetname)
	{
		Sheet sheet = openSheet(sheetname);
		Row row = null;
		
		row = sheet.createRow(0);
		String columnnames = inputdata2.get(0);
		String[] column = columnnames.split("~");
		for (int i = 0; i < column.length; i++) {
			Cell cell = row.createCell(i);
			setCellstyle(workbook, cell);
			cell.setCellType(CellType.STRING);
			cell.setCellValue(column[i]);
			
		}
		
	}
	
	private int getColumnnum(String sheetname, String columnname)
	{
		Sheet sheet = openSheet(sheetname);
		Row row = null;
		int colNum = 0;
		
		if (sheet.getRow(0)!=null)
		{
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) 
			{
				if (row.getCell(i).getStringCellValue().equals(columnname))
				{
					colNum = i;
					break;
				}
				
			}
		}
		else
		{
			colNum = 0;
			System.out.println("Header row not yet created");
		}
		
		return colNum;
		
	}
	
	
	
	public void inserttoRowfirstXML(int rownum, String sheetname)
	{
			String[] keyvaluepairs = inputdata1.get(rownum).split(",");
			for (int j = 0; j < keyvaluepairs.length; j++) 
			{
				
				String[] values = keyvaluepairs[j].split("-");
				String key = values[0].trim();
				String value = "";
				if (values.length>1)
				{
					value = values[1].trim();
				}
				Sheet sheet = openSheet(sheetname);
				int colnum = getColumnnum(sheetname, key);
				Row row = null;
				if (sheet.getRow(rownum)==null)
				{
				row = sheet.createRow(rownum);
				}
				else
				{
					row = sheet.getRow(rownum);
				}
				Cell cell = row.createCell(colnum);
				setCellstyle(workbook, cell);
				cell.setCellType(CellType.STRING);
				cell.setCellValue(value);
				
			}
		
	}
	
	public void inserttoRowsecondXML(int rownum, String sheetname)
	{
			String[] keyvaluepairs = inputdata2.get(rownum).split(",");
			for (int j = 0; j < keyvaluepairs.length; j++) 
			{
				
				String[] values = keyvaluepairs[j].split("-");
				String key = values[0].trim();
				String value = "";
				if (values.length>1)
				{
					value = values[1].trim();
				}
				Sheet sheet = openSheet(sheetname);
				int colnum = getColumnnum(sheetname, key);
				Row row = null;
				if (sheet.getRow(rownum)==null)
				{
				row = sheet.createRow(rownum);
				}
				else
				{
					row = sheet.getRow(rownum);
				}
				Cell cell = row.createCell(colnum);
				setCellstyle(workbook, cell);
				cell.setCellType(CellType.STRING);
				cell.setCellValue(value);
				
			}
		
	}
	
	@SuppressWarnings("deprecation")
	private Cell getCell(Sheet sheet, int i, int j)
	{
		Cell celltoreturn = null;
		
		try {
			Row row = sheet.getRow(i);
			if (row==null)
			{
				row = sheet.createRow(i);
			}
			
			Cell cell = row.getCell(j, Row.RETURN_BLANK_AS_NULL);
			if (cell!=null)
			{
				celltoreturn = cell;
			}
			else
			{
				celltoreturn = row.createCell(j);
				celltoreturn.setCellValue("");
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return celltoreturn;
	}
	
	@SuppressWarnings("deprecation")
	private String getCellvalue(Cell cell)
	{
		String value = "";
		cell.setCellType(CellType.STRING);
		switch(cell.getCellType()){
	    case 0:
	        value = Double.toString(cell.getNumericCellValue());
	        break;
	    case 1:
	        value = cell.getStringCellValue();
	        break;
	    default:
	        value =  "";
	        break;
	    }
		
		
		return value;
	}
	
	@SuppressWarnings("deprecation")
	private void highlightcell(Workbook wb,Cell cell)
	{
		CellStyle style = wb.createCellStyle();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cell.setCellStyle(style);
		Font font = wb.createFont();
		font.setBold(true);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);
		style.setWrapText(true);
	}
	
	private void setCellstyle(Workbook wb,Cell cell)
	{
		CellStyle style = wb.createCellStyle();
		cell.setCellStyle(style);
		Font font = wb.createFont();
//		font.setBold(true);
		font.setColor(HSSFColor.BLACK.index);
		style.setFont(font);
		style.setWrapText(true);
	}
	
	public boolean compareSheet(String sheetname1, String sheetname2)
	{
		boolean flag = true;
		Sheet worksheet1 = null;
		Sheet worksheet2 = null;
		Sheet worksheet3 = null;
		try{
			worksheet1 = workbook.getSheet(sheetname1);
			worksheet2 = workbook.getSheet(sheetname2);
			if (workbook.getSheet("CompareXML")==null)
			{
			worksheet3 = workbook.createSheet("CompareXML");
			}
			else
			{
				worksheet3 = workbook.getSheet("CompareXML");
			}
		}
		catch (Exception e)
		{
			System.out.println("Unable to access/create data sheet");
		}
		
		int rows1 = worksheet1.getLastRowNum();
		int rows2 = worksheet2.getLastRowNum();
		
		for (int i = 0; i <= (worksheet1.getLastRowNum()>worksheet2.getLastRowNum()?worksheet1.getLastRowNum():worksheet2.getLastRowNum()); i++) 
		{
			
			Row row1 = worksheet1.getRow(i);
			if (row1==null)
			{
				row1 = worksheet1.createRow(i);
			}
			Row row2 = worksheet2.getRow(i);
			if (row2==null)
			{
				row2 = worksheet2.createRow(i);
			}
			worksheet3.createRow(i);
			
			int sheet1cell1 = row1.getLastCellNum();
			int sheet1cell2 = row2.getLastCellNum();
			
			for (int j = 0; j < (row1.getLastCellNum()>row2.getLastCellNum()?row1.getLastCellNum():row2.getLastCellNum()); j++) 
			{
				
				Cell cell = worksheet3.getRow(i).createCell(j);
				cell.setCellType(CellType.STRING);
				
				Cell cell1 = getCell(worksheet1, i, j);
				Cell cell2 = getCell(worksheet2, i, j);
				String cell1value = getCellvalue(cell1);
				String cell2value = getCellvalue(cell2);
				if(cell1value.equals(cell2value))
				{
					cell.setCellType(Cell.CELL_TYPE_STRING);
					setCellstyle(workbook, cell);
					cell.setCellValue(cell1value);
//					System.out.println(cell.getStringCellValue());
				}
				else
				{
					highlightcell(workbook, cell);
					cell.setCellValue(cell1value+"||"+cell2value);
//					System.out.println(getCellvalue(cell1)+"||"+getCellvalue(cell2));
					flag = false;
					
				}
			}
			
			
			
		}
		
		
		return flag;
	}
	
	public void writeReport()
	{
		try(FileOutputStream outputstream = new FileOutputStream(new File(filename)))
		{
			workbook.write(outputstream);
		}
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		
	}
}
