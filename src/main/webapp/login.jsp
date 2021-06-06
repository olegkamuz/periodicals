<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<c:set var="title" value="Login"/>
<%--<%@ include file="/WEB-INF/jspf/headLoginRegistration.jspf" %>--%>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div>
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
</div>
<div class="login_form">
    <table id="main-container">
        <tr>
            <td class="content center">
                <form id="login_form" action="login-check" method="post">
                    <fieldset>
                        <legend style="color: #6e614c !important">
                            <fmt:message key="login_jsp.label.login"/>
                        </legend>
                        <input name="login"/><br/>
                    </fieldset>
                    <br/>
                    <fieldset>
                        <legend style="color: #6e614c !important">
                            <fmt:message key="login_jsp.label.password"/>
                        </legend>
                        <input type="password" name="password"/>
                    </fieldset>
                    <br/>

                    <input type="submit" value='<fmt:message key="login_jsp.button.login"/>'>
                </form>

                <a href="/registration" style="color: #6e614c !important">
                    <fmt:message key="login_jsp.label.registration"/>
                </a>
            </td>
        </tr>
    </table>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>