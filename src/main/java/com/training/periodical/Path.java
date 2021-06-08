package com.training.periodical;

import java.io.Serializable;

/**
 * Path holder (jsp pages, redirects).
 * 
 */
public final class Path implements Serializable {
    private static final long serialVersionUID = 8957289300674157212L;

	// pages
	public static final String PAGE__LOGIN = "/WEB-INF/jsp/login.jsp";
    public static final String PAGE__REGISTRATION = "/WEB-INF/jsp/registration.jsp";
    public static final String PAGE__CLIENT_CABINET = "/WEB-INF/jsp/client/client-cabinet.jsp";
    public static final String PAGE__ADMIN_CABINET = "/WEB-INF/jsp/admin/admin-cabinet.jsp";
	public static final String PAGE__ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
	public static final String PAGE__SETTINGS = "/WEB-INF/jsp/view-settings.jsp";
    public static final String PAGE_INDEX = "/WEB-INF/jsp/index.jsp";


    // redirects
    public static final String REDIRECT__LOGIN = "redirect:/login";
    public static final String REDIRECT__INDEX = "redirect:/index";
    public static final String REDIRECT__USER_CABINET = "redirect:/client-cabinet";
    public static final String REDIRECT__ADMIN_CABINET = "redirect:/admin-cabinet";

}