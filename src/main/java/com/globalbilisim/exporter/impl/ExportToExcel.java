/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globalbilisim.exporter.impl;

import com.globalbilisim.exporter.enums.ExcelType;
import com.globalbilisim.exporter.model.AnnotationModel;
import com.globalbilisim.exporter.model.Model;
import com.globalbilisim.exporter.util.Util;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Baran Buyuk <baranbuyuk@globalbilisim.com>
 */
public class ExportToExcel implements IExporter {

    private final File file;
    private final Workbook wb;

    private final Map<AnnotationModel, List<String>> allData;

    @SuppressWarnings("unchecked")
    private ExportToExcel(Builder builder) {
        file = builder.file;
        wb = builder.wb;
        this.allData = builder.allData;
    }

    @Override
    public boolean export() {
        Sheet sheet = null;
        Row firstRow = null;
        int rowId = 0;
        int cellId = 0;
        try {
            sheet = sheet = wb.getSheetAt(0);
            firstRow = sheet.createRow(rowId);
            rowId++;
            //create first row for headers
            for (Map.Entry<AnnotationModel, List<String>> entry : allData.entrySet()) {
                //create first columns
                AnnotationModel key = entry.getKey();
                Cell cell = firstRow.createCell(cellId);
                cell.setCellValue(key.getExcelColumnName());

                //loop in values
                List<String> values = entry.getValue();
                for (String value : values) {
                    //if not first row crate new row.
                    Row nextRow = sheet.getRow(rowId);
                    if (nextRow == null) {
                        nextRow = sheet.createRow(rowId);
                    }
                    nextRow.createCell(cellId).setCellValue(value);
                    rowId++;
                }
                rowId = 1;
                cellId++;

            }

            wb.write(new FileOutputStream(file));
            wb.close();
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    /**
     * Builder class
     */
    public static class Builder {

        /**
         * Mandatory fields
         */
        private final File file;
        private final ExcelType type;
        private final Map<String, List> data = new LinkedHashMap<>();
        /**
         * Optional fields
         */
        private Workbook wb;
        private String schemaName;
        private Model model;
        private Map<AnnotationModel, List<String>> allData;

        /**
         * new File(path + fileName + "." + schemaName);
         *
         * @param path
         * @param fileName
         * @param type
         */
        public Builder(String path, String fileName, ExcelType type) {
            if (Util.isNullOrEmpty(fileName)) {
                throw new IllegalArgumentException("Filename cannot be null or empty.");
            } else if (Util.isNullOrEmpty(path)) {
                throw new IllegalArgumentException("Path cannot be null.");
            } else if (Util.isNull(type)) {
                throw new IllegalArgumentException("Excel type cannot be null.");
            }
            this.type = type;
            String eType = Util.isObjEqual(type, ExcelType.XLS) ? "xls" : "xlsx";
            path = path.endsWith("/") ? path : path + "/";
            //check folder
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdir();
            }
            //create file in 
            this.file = new File(path + fileName + "." + eType);

        }

        /**
         * Default -> "Default Schema Name"
         *
         * @param schemaName
         * @return
         */
        public Builder withSchemaName(String schemaName) {
            this.schemaName = schemaName;
            return this;
        }

        /**
         * @param model
         * @return
         */
        public Builder withModel(Model model) {
            this.model = model;
            return this;
        }

        /**
         * Create new ExcelExport instance
         *
         * @return
         */
        public ExportToExcel build() {
            createWorkbook();
            this.allData = findFields(model);
            return new ExportToExcel(this);
        }

        private void createWorkbook() {
            //create new instance
            if (this.type == ExcelType.XLSX) {
                wb = new XSSFWorkbook();
            } else {
                wb = new HSSFWorkbook();
            }
            this.schemaName = Util.isNullOrEmpty(schemaName) ? "Default Schema Name" : schemaName;
            //set sheetname
            wb.createSheet(schemaName);
        }

        private Map<AnnotationModel, List<String>> findFields(Model model) {
            if (model == null || model.getClzz() == null || model.getData() == null) {
                throw new IllegalArgumentException("Please set Model. You can use to Model.of(clazz,data) method.");
            }
            Map<AnnotationModel, List<String>> dataMap = new LinkedHashMap<>();
            Field[] fields = model.getClzz().getDeclaredFields();
            for (Field field : fields) {
                ExcelColumnName column = field.getDeclaredAnnotation(ExcelColumnName.class);
                String value;
                switch (column.showType()) {
                    case LOWERCASE:
                        value = Util.isNullOrEmpty(column.value()) ? field.getName().toLowerCase() : column.value().toLowerCase();
                        break;
                    case UPPERCASE:
                        value = Util.isNullOrEmpty(column.value()) ? field.getName().toUpperCase() : column.value().toUpperCase();
                        break;
                    default:
                        value = Util.isNullOrEmpty(column.value()) ? field.getName() : column.value();
                        break;
                }
                dataMap.put(new AnnotationModel(field.getName(), value), new ArrayList<>());
            }

            if (!model.getData().isEmpty()) {
                dataMap.forEach((key, value) -> {
                    dataMap.replace(key, Util.findFieldValues(model.getData(), key.getOrjinalFieldName(), true));
                });

            }
            return dataMap;
        }
    }
}
