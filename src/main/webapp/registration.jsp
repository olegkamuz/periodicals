<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Login" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
	
<body>
	<table id="main-container">

		<tr >
			<td class="content center">
				<form id="registration_form" action="registration-save" method="post">
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
					</fieldset>
                    <fieldset >
                        <legend>
                            <fmt:message key="settings_jsp.label.first_name"/>
                        </legend>
                        <input name="first-name"/><br/>
                    </fieldset><br/>
                    <fieldset>
                        <legend>
                            <fmt:message key="settings_jsp.label.last_name"/>
                        </legend>
                        <input name="last-name"/>
                    </fieldset>
                    <br/>
					
					<input type="submit" value='<fmt:message key="login_jsp.label.registration"/>'>
				</form>
			</td>
		</tr>
	</table>
    <%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>