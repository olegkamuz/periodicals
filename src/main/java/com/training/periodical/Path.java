package com.training.periodical;

import java.io.Serializable;

/**
 * Path holder (jsp pages, controller commands).
 * 
 */
public final class Path implements Serializable {
    private static final long serialVersionUID = 8957289300674157212L;

	// pages
	public static final String PAGE__LOGIN = "/login.jsp";
    public static final String PAGE__REGISTRATION = "/registration.jsp";
    public static final String PAGE__USER_CABINET = "/WEB-INF/jsp/client/user-cabinet.jsp";
    public static final String PAGE__ADMIN_CABINET = "/WEB-INF/jsp/admin/admin-cabinet.jsp";

	public static final String PAGE__ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
	public static final String PAGE__LIST_MENU = "/WEB-INF/jsp/client/list_menu.jsp";
	public static final String PAGE__LIST_ORDERS = "/WEB-INF/jsp/admin/list_orders.jsp";
	public static final String PAGE__SETTINGS = "/WEB-INF/jsp/view-settings.jsp";
    public static final String PAGE__LIST_MAGAZINES_BY_THEMES = "/client/list_by_category_menu.jsp";
    public static final String PAGE_INDEX = "/index.jsp";

    // forward
    public static final String FORWARD__INDEX = "/index";
    public static final String FORWARD__USER_CABINET = "/client/user-cabinet";

    // redirects
    public static final String REDIRECT__LIST_MAGAZINES_BY_ONE_THEME = "redirect:/one-theme-magazines";
    public static final String REDIRECT__LOGIN = "redirect:/login";
    public static final String REDIRECT__INDEX = "redirect:/index";
    public static final String REDIRECT__REGISTRATION = "redirect:/registration";
    public static final String REDIRECT__USER_CABINET = "redirect:/user-cabinet";
    public static final String REDIRECT__ADMIN_CABINET = "redirect:/admin-cabinet";

	// commands
	public static final String COMMAND__LIST_ORDERS = "/controller?command=listOrders";
	public static final String COMMAND__LIST_MENU = "/controller?command=listMenu";
    public static final String COMMAND__LIST_BY_CATEGORY_MENU = "/controller?command=listByCategoryMenu";

}