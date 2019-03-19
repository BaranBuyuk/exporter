/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globalbilisim.exporter.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Baran Buyuk <baranbuyuk@globalbilisim.com>
 */
public class Util {

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isNullOrEmpty(String string) {
        return isNull(string) || string.isEmpty();
    }

    public static boolean isNullOrEmpty(Collection collection) {
        return isNull(collection) || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(Map map) {
        return isNull(map) || map.isEmpty();
    }

    public static boolean isObjEqual(Object o1, Object o2) {
        return !isNull(o1) && !isNull(o2) && o1.equals(o2);
    }

    public static void autoSizeColumns(Workbook workbook) {
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getPhysicalNumberOfRows() > 0) {
                Row row = sheet.getRow(0);
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    sheet.autoSizeColumn(columnIndex, true);
                }
            }
        }
    }

    public static List<String> findFieldValues(List list, String fieldName, boolean forceAccessible) {
        List<String> result = new ArrayList<>();
        try {
            for (Object o : list) {
                Field field = o.getClass().getDeclaredField(fieldName);
                if (field != null) {
                    field.setAccessible(forceAccessible);
                    result.add(field.get(o).toString());
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchFieldException e) {
            System.out.println(e.getMessage());
        }
        return result;

    }

}
