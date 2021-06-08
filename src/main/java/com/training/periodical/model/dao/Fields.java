package com.training.periodical.model.dao;

/**
 * Holder for fields names of DB tables and beans.
 * 
 *
 */
public final class Fields {
	
	// entities
	public static final String ENTITY__ID = "id";
	public static final String USER__ID = "user_id";
    public static final String MAGAZINE__ID = "magazine_id";

    public static final String THEME_NAME = "name";

    public static final String BEAN_THEME_NAME = "theme_name";

	public static final String USER__LOGIN = "login";
	public static final String USER__PASSWORD = "password";
    public static final String USER__BALANCE = "balance";
	public static final String USER__FIRST_NAME = "first_name";
	public static final String USER__LAST_NAME = "last_name";
	public static final String USER__LOCALE = "locale";
	public static final String USER__ROLE_ID = "role_id";
    public static final String USER__BLOCKED = "blocked";

    public static final String MAGAZINE__PRICE = "price";
    public static final String MAGAZINE__NAME = "name";
    public static final String MAGAZINE__IMAGE = "image";
    public static final String MAGAZINE__THEME_ID = "theme_id";
}