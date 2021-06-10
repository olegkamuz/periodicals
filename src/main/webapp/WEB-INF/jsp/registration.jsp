<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Registration"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<div>
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
</div>
<form id="registration_form" action="registration-save" method="post">
    <div class="login_form">
        <div>
            <fieldset>
                <legend style="color: #6e614c !important">
                    <fmt:message key="login_jsp.label.login"/>
                </legend>
                <input name="login"/>
            </fieldset>
        </div>
        <div>
            <fieldset>
                <legend style="color: #6e614c !important">
                    <fmt:message key="login_jsp.label.password"/>
                </legend>
                <input type="password" name="password"/>
            </fieldset>
        </div>
        <div>
            <fieldset>
                <legend style="color: #6e614c !important">
                    <fmt:message key="settings_jsp.label.first_name"/>
                </legend>
                <input name="firstName"/>
            </fieldset>
        </div>
        <div>
            <fieldset>
                <legend style="color: #6e614c !important">
                    <fmt:message key="settings_jsp.label.last_name"/>
                </legend>
                <input name="lastName"/>
            </fieldset>
        </div>

        <c:if test="${not empty error_reg}">
            <div class="error">
                <fmt:message key="registration_jsp.error.error_reg"/>
            </div>
        </c:if>
        <c:if test="${not empty error_symbols}">
            <div class="error">
                <fmt:message key="registration_jsp.error.error_symbols"/>
            </div>
        </c:if>
        <c:if test="${not empty error_taken_login}">
            <div class="error">
                <fmt:message key="registration_jsp.error.error_taken_login"/>
            </div>
        </c:if>
        <input type="submit" value='<fmt:message key="login_jsp.label.registration"/>'>
    </div>
</form>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>