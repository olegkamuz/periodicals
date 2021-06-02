package com.training.periodical.model.dao.query;

public class Query {
    public static String getFindAllFromTable(String table){
        return "SELECT * FROM " + table;
    }
    public static String getFindAllFromTable(String table, int limit, int offset){
        return "SELECT * FROM " + table + "LIMIT=" + limit + " OFFSET=" + offset;
    }
}
