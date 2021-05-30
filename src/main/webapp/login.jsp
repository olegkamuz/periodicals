<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
	
<body>
	<table id="main-container">

		<tr >
			<td class="content center">
				<form id="login_form" action="login-check" method="post">
					<fieldset >
						<legend>
							<fmt:message key="login_jsp.label.login"/>
						</legend>
						<input name="login"/><br/>
					</fieldset><br/>
					<fieldset>
						<legend>
							<fmt:message key="login_jsp.label.password"/>
						</legend>
						<input type="password" name="password"/>
					</fieldset><br/>
					
					<input type="submit" value='<fmt:message key="login_jsp.button.login"/>'>								
				</form>

                <link rel="stylesheet" href="<c:url value="/static/css/main.css" />">

                <a href="/registration">
                    <fmt:message key="login_jsp.label.registration"/>
                </a>
            </td>
		</tr>
	</table>
    <%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>