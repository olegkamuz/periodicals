package ua.kharkov.knure.dkolesnikov.st4example;

/**
 * Path holder (jsp pages, controller commands).
 * 
 * @author D.Kolesnikov
 * 
 */
public final class Path {

	// pages
	public static final String PAGE__LOGIN = "/login.jsp";
	public static final String PAGE__ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
	public static final String PAGE__LIST_MENU = "/WEB-INF/jsp/client/list_menu.jsp";
	public static final String PAGE__LIST_ORDERS = "/WEB-INF/jsp/admin/list_orders.jsp";
	public static final String PAGE__SETTINGS = "/WEB-INF/jsp/settings.jsp";
    public static final String PAGE__LIST_BY_CATEGORY_MENU = "/WEB-INF/jsp/client/list_by_category_menu.jsp";

    // redirects
    public static final String REDIRECT__LIST_BY_CATEGORY_MENU = "redirect:/client/index.jsp";
    public static final String REDIRECT__LIST_BY_ONE_CATEGORY_MENU = "redirect:/client/one-category-magazines.jsp";

	// commands
	public static final String COMMAND__LIST_ORDERS = "/controller?command=listOrders";
	public static final String COMMAND__LIST_MENU = "/controller?command=listMenu";
    public static final String COMMAND__LIST_BY_CATEGORY_MENU = "/controller?command=listByCategoryMenu";

}