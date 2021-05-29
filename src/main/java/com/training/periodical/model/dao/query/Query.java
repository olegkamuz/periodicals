package com.training.periodical.model.dao.query;

public class Query {
    public static String getFindAllFromTable(String table){
        return "SELECT * FROM " + table;
    }
}
