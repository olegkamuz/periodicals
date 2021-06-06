package com.training.periodical.model.dao.query;

import java.io.Serializable;

public class Query implements Serializable {
    private static final long serialVersionUID = 7100834364683912258L;

    public static String findAllFromTable(String table){
        return "SELECT * FROM " + table;
    }
}
