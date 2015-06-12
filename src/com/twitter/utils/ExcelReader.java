package com.twitter.utils;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import java.io.*;

/**
 * Created
 * by:   Admin
 * date: 11.06.2015
 * time: 18:10
 */
public class ExcelReader {

    public static String[][] getTableArray(String xlFilePath, String sheetName, String tableName) {
        String[][] tabArray = null;
        try {
            Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
            Sheet sheet = workbook.getSheet(sheetName);
            Cell tableStart = sheet.findCell(tableName);
            Cell tableEnd = sheet.findCell(tableName, tableStart.getColumn() + 1, tableStart.getRow() + 1, 100, 64000, false);

            /*System.out.println("startRow=" + tableStart.getRow() + ", endRow=" + tableEnd.getRow() + ", " +
                    "startCol=" + tableStart.getColumn() + ", endCol=" + tableEnd.getColumn());*/

            tabArray = new String[tableEnd.getRow() - tableStart.getRow() - 1][tableEnd.getColumn() - tableStart.getColumn() - 1];

            for (int i = tableStart.getRow() + 1, ci = 0; i < tableEnd.getRow(); i++, ci++) {
                for (int j = tableStart.getColumn() + 1, cj = 0; j < tableEnd.getColumn(); j++, cj++) {
                    tabArray[ci][cj] = sheet.getCell(j, i).getContents();
                }
            }
        } catch (Exception e) {
/*            System.out.println(e);
            System.out.println("**********************************");
            System.out.println(e.fillInStackTrace());
            System.out.println("**********************************");
            System.out.println(e.getStackTrace());
            System.out.println("**********************************");
            System.out.println("error in getTableArray()");*/
        }

        return (tabArray);
    }

}
