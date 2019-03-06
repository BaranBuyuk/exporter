/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globalbilisim.exporter.impl;

import java.lang.annotation.*;

/**
 * @author Baran Buyuk <baranbuyuk@globalbilisim.com>
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumnName {

    String value();

    ShowType showType() default ShowType.NONE;

    public enum ShowType {
        UPPERCASE, LOWERCASE, NONE;
    }
}
