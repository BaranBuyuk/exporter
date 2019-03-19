/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globalbilisim.exporter.test;

import com.globalbilisim.exporter.enums.ExcelType;
import com.globalbilisim.exporter.impl.ExportToExcel;
import com.globalbilisim.exporter.model.Model;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Baran Buyuk <baranbuyuk@globalbilisim.com>
 */
public class ExcelExportTest {

    private static List<TestModel> list = null;

    @BeforeClass
    public static void init() {
        //create new list
        list = new ArrayList<>();
        list.add(new TestModel(3355, "Baran", "BÜYÜK", "baran@gmail.com"));
        list.add(new TestModel(3354, "John", "Doe", "johndoe@mail.com"));
        list.add(new TestModel(3353, "Akira", "Kurusowa", "akira@hotmail.com"));
        list.add(new TestModel(3352, "David", "Fincher", "fincher@hotmail.com"));

    }

    @Test
    public void createExcel() {
        ExportToExcel exporter = new ExportToExcel.Builder("C:/Baran/export/", "Movies", ExcelType.XLS)
                .withModel(Model.of(TestModel.class, list)).build();
        Assert.assertNotNull(exporter.export());
    }

}
