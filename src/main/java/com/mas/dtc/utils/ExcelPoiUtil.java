package com.mas.dtc.utils;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;


/**
 * Cell获取对应类型的值
 * @author Mathsys
 *
 */
public class ExcelPoiUtil {

	/**
	 * 日志
	 */
	private static final Logger LOG = Logger.getLogger(ExcelPoiUtil.class);
	
	/**
	 * 获取单元格的值
	 * @param cell 单元对象
	 * @return 单元格的值 
	 */
	public static String getCellValueString(Cell cell){
		
		String value = null;
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_NUMERIC: // 数字
			double temp = cell.getNumericCellValue();
			long l_temp = Math.round(temp);
			if (l_temp == temp)
				value = "" + l_temp;
			else
				value = "" + temp;
			break;
		case XSSFCell.CELL_TYPE_STRING:
			// 字符串类型除去前后空格
			value = cell.getStringCellValue().trim();
			if (value.length() == 0)
				value = null;
			break;
		// System.out.print(cell.getBooleanCellValue()
		// + " ");
		// break;
		case XSSFCell.CELL_TYPE_FORMULA: // 公式
			// System.out.print(cell.getCellFormula() + "----" +
			// cell.getRawValue()+ " ");
			value = cell.getStringCellValue().trim();
			if (value.length() == 0)
				value = null;
			// logger.info(cell.getSheet().getSheetName() +" "+
			// cell.getReference()+":" + value);
			break;
		case XSSFCell.CELL_TYPE_BLANK: // 空值
			break;
		default:
			value = null;
			LOG.error("未知类型  at CELL  sheet:"+ cell.getSheet().getSheetName() + cell.getColumnIndex() + cell.getRowIndex() );
			break;
		}
		return value;
	}
	
	
	
}
