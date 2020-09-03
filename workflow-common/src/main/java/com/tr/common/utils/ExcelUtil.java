package com.tr.common.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    /**
     * 判断Excel的版本,获取Workbook
     * @param in
     * @param file
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(InputStream in, File file) throws IOException {
        Workbook wb = null;
        if(file.getName().endsWith(EXCEL_XLS)){  //Excel 2003
            wb = new HSSFWorkbook(in);
        }else if(file.getName().endsWith(EXCEL_XLSX)){  // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    /**
     * 判断文件是否是excel
     * @throws Exception
     */
    public static void checkExcelVaild(File file) throws Exception{
        if(!file.exists()){
            throw new Exception("文件不存在");
        }
        if(!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))){
            throw new Exception("文件不是Excel");
        }
    }

    /**
     * 读取Excel测试，兼容 Excel 2003/2007/2010
     * @throws Exception
     */
    public static List<String[]> getData(String filePath) throws Exception {
        List<String[]> result = new ArrayList<>();
        try {
            // 同时支持Excel 2003、2007
            File excelFile = new File(filePath); // 创建文件对象
            FileInputStream in = new FileInputStream(excelFile); // 文件流
            checkExcelVaild(excelFile);
            Workbook workbook = getWorkbok(in,excelFile);
            /**
             * 设置当前excel中sheet的下标：0开始
             */
            Sheet sheet = workbook.getSheetAt(0);   // 遍历第一个Sheet
            int rowNum = sheet.getLastRowNum();
            System.out.println("总行数："+rowNum);
            int count = 0;
            for (Row row : sheet) {
                try {
                    // 跳过第一和第二行的目录
                    if(count < 1 ) {
                        count++;
                        continue;
                    }
                    //如果当前行没有数据，跳出循环
                    if(row.getCell(0).toString().equals("")){
                        return result;
                    }
                    //获取总列数(空格的不计算)
                    int columnTotalNum = row.getPhysicalNumberOfCells();
                    System.out.println("总列数：" + columnTotalNum);
                    System.out.println("最大列数：" + row.getLastCellNum());
                    String[] rowStr = new String[row.getLastCellNum()];
                    int end = row.getLastCellNum();
                    for (int i = 0; i < end; i++) {
                        Cell cell = row.getCell(i);
                        if(cell == null) {
                            rowStr[i] = null;
                            continue;
                        }
                        Object obj = getValue(cell);
                        rowStr[i]= obj.toString();
                    }
                    result.add(rowStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Object getValue(Cell cell) {
        Object obj = null;
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case ERROR:
                obj = cell.getErrorCellValue();
                break;
            case NUMERIC:
                DecimalFormat df = new DecimalFormat("0");
                obj = df.format(cell.getNumericCellValue());
                break;
            case STRING:
                obj = cell.getStringCellValue();
                break;
            case BLANK:
                obj = "";
                break;
            default:
                break;
        }
        return obj;
    }

    public static void main(String[] args) throws Exception{
        getData("jike");
    }
}
