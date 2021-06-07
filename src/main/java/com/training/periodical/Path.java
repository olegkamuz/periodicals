package com.training.periodical;

import java.io.Serializable;

/**
 * Path holder (jsp pages, forward, redirects).
 * 
 */
public final class Path implements Serializable {
    private static final long serialVersionUID = 8957289300674157212L;

	// pages
	public static final String PAGE__LOGIN = "/WEB-INF/jsp/login.jsp";
    public static final String PAGE__REGISTRATION = "/WEB-INF/jsp/registration.jsp";
    public static final String PAGE__USER_CABINET = "/WEB-INF/jsp/client/user-cabinet.jsp";
    public static final String PAGE__ADMIN_CABINET = "/WEB-INF/jsp/admin/admin-cabinet.jsp";

	public static final String PAGE__ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
	public static final String PAGE__SETTINGS = "/WEB-INF/jsp/view-settings.jsp";
    public static final String PAGE_INDEX = "/index.jsp";

    // forward
    public static final String FORWARD__INDEX = "/index";

    // redirects
    public static final String REDIRECT__LOGIN = "redirect:/login";
    public static final String REDIRECT__INDEX = "redirect:/index";
    public static final String REDIRECT__USER_CABINET = "redirect:/user-cabinet";
    public static final String REDIRECT__ADMIN_CABINET = "redirect:/admin-cabinet";

}