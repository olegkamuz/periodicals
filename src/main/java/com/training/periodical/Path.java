package com.training.periodical;

/**
 * Path holder (jsp pages, controller commands).
 * 
 */
public final class Path {

	// pages
	public static final String PAGE__LOGIN = "/login.jsp";
    public static final String PAGE__REGISTRATION = "/registration.jsp";
	public static final String PAGE__ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
	public static final String PAGE__LIST_MENU = "/WEB-INF/jsp/client/list_menu.jsp";
	public static final String PAGE__LIST_ORDERS = "/WEB-INF/jsp/admin/list_orders.jsp";
	public static final String PAGE__SETTINGS = "/WEB-INF/jsp/settings.jsp";
    public static final String PAGE__LIST_MAGAZINES_BY_THEMES = "/client/list_by_category_menu.jsp";
    public static final String PAGE_INDEX = "/index.jsp";
//    public static final String PAGE_INDEX = "/client/index.jsp";
    public static final String PAGE__LIST_MAGAZINES_BY_ONE_THEME = "/client/one-category-magazines.jsp";

    // forward
    public static final String FORWARD__LIST_MAGAZINES_BY_ONE_THEME = "/one-category-magazines";
    public static final String FORWARD__INDEX = "/index";

    // redirects
    public static final String REDIRECT__LIST_MAGAZINES_BY_ONE_THEME = "redirect:/one-category-magazines";
    public static final String REDIRECT__LOGIN = "redirect:/login";
    public static final String REDIRECT__INDEX = "redirect:/index";
    public static final String REDIRECT__REGISTRATION = "redirect:/registration";

	// commands
	public static final String COMMAND__LIST_ORDERS = "/controller?command=listOrders";
	public static final String COMMAND__LIST_MENU = "/controller?command=listMenu";
    public static final String COMMAND__LIST_BY_CATEGORY_MENU = "/controller?command=listByCategoryMenu";

}