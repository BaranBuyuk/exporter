/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globalbilisim.exporter.test;

import com.globalbilisim.exporter.impl.ExcelColumnName;

/**
 *
 * @author Baran Buyuk <baranbuyuk@globalbilisim.com>
 */
public class TestModel {

    @ExcelColumnName(value = "")
    private final Integer customerId;
    @ExcelColumnName(value = "name", showType = ExcelColumnName.ShowType.LOWERCASE)
    private final String name;
    @ExcelColumnName(value = "Surname", showType = ExcelColumnName.ShowType.UPPERCASE)
    private final String surname;
    @ExcelColumnName(value = "Email")
    private final String email;

    public TestModel(Integer customerId, String name, String surname, String email) {
        this.customerId = customerId;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

}
