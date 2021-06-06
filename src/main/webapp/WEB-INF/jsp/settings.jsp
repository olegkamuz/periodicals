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
    <%--    <table id="main-container">--%>
    <%--        <tr>--%>
    <%--            <td class="content center">--%>
    <form id="settings_form" action="update-settings" method="post">
        <h3 style="color: #6e614c !important"><fmt:message key="settings_jsp.label.localization"/></h3>
        <fieldset class="settings_lang">
            <legend style="color: #6e614c !important">
                <fmt:message key="settings_jsp.label.choose"/>
            </legend>


            <select class="lang_select"name="localeToSet">
                <c:choose>
                    <c:when test="${not empty defaultLocale}">
                        <c:if test="${defaultLocale eq ru}">
                            <option value="${defaultLocale}">
                                <fmt:message key="settings_jsp.label.language.russian"/>
                            </option>
                        </c:if>
                        <c:if test="${defaultLocale eq en}">
                            <option value="${defaultLocale}">
                                <fmt:message key="settings_jsp.label.language.english"/>
                            </option>
                        </c:if>

<%--                        <option value="${defaultLocale}">--%>
<%--                            <fmt:message key="settings_jsp.label.default"/>--%>
<%--                        </option>--%>
                        <%--                        <option value="<fmt:message key="settings_jsp.label.default"/>">--%>
                        <%--                        </option>--%>
                    </c:when>
                    <c:otherwise>
                        <option value=""/>
                    </c:otherwise>
                </c:choose>

                <c:forEach var="localeName" items="${locales}">
                    <c:if test="${localeName eq ru}">
                        <option value="${localeName}">
                            <fmt:message key="settings_jsp.label.language.russian"/>
                        </option>
                    </c:if>
                    <c:if test="${localeName eq en}">
                        <option value="${localeName}">
                            <fmt:message key="settings_jsp.label.language.english"/>
                        </option>
                    </c:if>
                </c:forEach>
            </select>
        </fieldset>


        <fieldset>
            <legend style="color: #6e614c !important">
                <fmt:message key="settings_jsp.label.first_name"/>
            </legend>
            <input name="firstName" value="<c:out value="${user.firstName}"/>"/><br/>
        </fieldset>
        <br/>
        <fieldset>
            <legend style="color: #6e614c !important">
                <fmt:message key="settings_jsp.label.last_name"/>
            </legend>
            <input name="lastName" value="<c:out value="${user.lastName}"/>"/>

        </fieldset>
        <br/>

        <input type="submit" value='<fmt:message key="settings_jsp.button.update"/>'><br/>
    </form>
    <%--            </td>--%>
    <%--        </tr>--%>
    <%--    </table>--%>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>