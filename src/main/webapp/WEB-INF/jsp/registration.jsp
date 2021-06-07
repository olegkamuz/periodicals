<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Registration"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<div>
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
</div>
<div class="login_form">
    <table id="main-container">

        <tr>
            <td class="content center">
                <form id="registration_form" action="registration-save" method="post">
                    <fieldset>
                        <legend style="color: #6e614c !important">
                            <fmt:message key="login_jsp.label.login"/>
                        </legend>
                        <input name="login"/>
                    </fieldset>
                    <fieldset>
                        <legend style="color: #6e614c !important">
                            <fmt:message key="login_jsp.label.password"/>
                        </legend>
                        <input type="password" name="password"/>
                    </fieldset>
                    <fieldset>
                        <legend style="color: #6e614c !important">
                            <fmt:message key="settings_jsp.label.first_name"/>
                        </legend>
                        <input name="first-name"/>
                    </fieldset>
                    <fieldset>
                        <legend style="color: #6e614c !important">
                            <fmt:message key="settings_jsp.label.last_name"/>
                        </legend>
                        <input name="last-name"/>
                    </fieldset>

                    <div class="error">
                        <c:if test="${not empty error_reg}">
                            <fmt:message key="registration_jsp.error.error_reg"/>
                        </c:if>
                    </div>
                    <input type="submit" value='<fmt:message key="login_jsp.label.registration"/>'>
                </form>
            </td>
        </tr>
    </table>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>