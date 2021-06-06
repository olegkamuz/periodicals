<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Settings" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<div>
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
</div>
<div class="login_form">
    <table id="main-container">
        <tr>
            <td class="content center">
                <p style="color: #6e614c !important"><fmt:message key="settings_jsp.label.localization"/></p>
                <form id="settings_form" action="update-settings" method="post">
                    <fieldset>
                        <legend style="color: #6e614c !important">
                            <fmt:message key="settings_jsp.label.first_name"/>
                        </legend>

                        <select name="localeToSet">
                            <c:choose>
                                <c:when test="${not empty defaultLocale}">
                                    <option value="">${defaultLocale}
                                        <fmt:message key="settings_jsp.label.default"/>
                                    </option>
                                </c:when>
                                <c:otherwise>
                                    <option value=""/>
                                </c:otherwise>
                            </c:choose>

                            <c:forEach var="localeName" items="${locales}">
                                <option value="${localeName}">${localeName}</option>
                            </c:forEach>
                        </select>
                    </fieldset>
                    <fieldset>
                        <legend style="color: #6e614c !important">
                            <fmt:message key="settings_jsp.label.first_name"/>
                        </legend>
                        <input name="login"/><br/>
                    </fieldset>
                    <br/>
                    <fieldset>
                        <legend style="color: #6e614c !important">
                            <fmt:message key="settings_jsp.label.last_name"/>
                        </legend>
                        <input type="password" name="password"/>
                    </fieldset>
                    <br/>

                    <input type="submit" value='<fmt:message key="settings_jsp.button.update"/>'><br/>
                </form>
            </td>
        </tr>
    </table>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>