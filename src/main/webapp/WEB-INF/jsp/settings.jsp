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
    <form id="settings_form" action="update-settings" method="post">
        <h3 style="color: #6e614c !important"><fmt:message key="settings_jsp.label.localization"/></h3>
        <fieldset class="settings_lang">
            <legend style="color: #6e614c !important">
                <fmt:message key="settings_jsp.label.choose"/>
            </legend>

            <select class="lang_select" name="localeToSetUser">
                <c:forEach var="localeName" items="${locales}">
                    <c:if test="${localeName eq ru}">
                        <option style="text-align: center" value="${localeName}"
                                <c:if test="${not empty defaultLocale}">
                                <c:if test="${localeName eq defaultLocale}">selected</c:if>
                        </c:if>>
                            <fmt:message key="settings_jsp.label.language.russian"/>
                        </option>
                    </c:if>
                    <c:if test="${localeName eq en}">
                        <option style="text-align: center" value="${localeName}"
                                <c:if test="${not empty defaultLocale}">
                                <c:if test="${localeName eq defaultLocale}">selected</c:if>
                        </c:if>>
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
            <input style="text-align: center" name="firstName" value="<c:out value="${user.firstName}"/>"/>
        </fieldset>
        <fieldset>
            <legend style="color: #6e614c !important">
                <fmt:message key="settings_jsp.label.last_name"/>
            </legend>
            <input style="text-align: center" name="lastName" value="<c:out value="${user.lastName}"/>"/>

        </fieldset>

            <c:if test="${not empty error_not_valid_symbols_first_name}">
        <div class="error">
                <fmt:message key="settings_jsp.error.error_not_valid_symbols_first_name"/>
        </div>
            </c:if>
            <c:if test="${not empty error_not_valid_symbols_last_name}">
        <div class="error">
                <fmt:message key="settings_jsp.error.error_not_valid_symbols_last_name"/>
        </div>
            </c:if>
        <c:if test="${not empty error_not_valid_symbols_locale_user_format}">
            <div class="error">
                <fmt:message key="settings_jsp.error.error_not_valid_symbols_language"/>
            </div>
        </c:if>
        <div class="settings_sub_button"
             <c:if test="${not empty error_not_valid_symbols_first_name or not empty error_not_valid_symbols_last_name or not empty error_not_valid_symbols_locale_user_format}">style="padding:0px"</c:if>
        >
            <input type="submit" value='<fmt:message key="settings_jsp.button.update"/>'>
        </div>
    </form>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>