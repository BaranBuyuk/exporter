/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globalbilisim.exporter.model;

import java.util.List;

/**
 *
 * @author Baran Buyuk <baranbuyuk@globalbilisim.com>
 */
public class Model {

    private final Class clzz;
    private final List<?> data;

    /**
     *
     * @param object - for find excel columns
     * @param data - list of clzz
     */
    private Model(Class clzz, List data) {
        this.clzz = clzz;
        this.data = data;

    }

    /**
     *
     * @param clzz
     * @param data
     * @param columnNameEnum
     * @return
     */
    public static Model of(Class clzz, List data) {
        return new Model(clzz, data);
    }

    public Class getClzz() {
        return clzz;
    }

    public List getData() {
        return data;
    }

}
