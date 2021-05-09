<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Index" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
	
<body>
   <%
    RequestDispatcher rd = request.getRequestDispatcher("/controller?command=login_session");
    rd.forward(request, response);
   %>
</body>
</html>