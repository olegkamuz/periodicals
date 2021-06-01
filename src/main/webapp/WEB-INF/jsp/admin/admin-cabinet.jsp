<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="List orders" scope="page"/>
<%@ include file="/WEB-INF/jspf/headOLEG.jspf" %>

<body style="background-image: none">
<%@ include file="/WEB-INF/jspf/headerOLEG.jspf" %>
<table id="main-container">
    <tr>
        <td class="content">
            <c:choose>
                <c:when test="${fn:length(userList) == 0}">no users registered</c:when>
                <c:otherwise>
                    <table id="list_user_table">
                        <thead>
                        <tr>
                            <td><fmt:message key="admin-cabinet_jsp.label.user.login"/></td>
                            <td><fmt:message key="admin-cabinet_jsp.label.user.first_name"/></td>
                            <td><fmt:message key="admin-cabinet_jsp.label.user.last_name"/></td>
                            <td><fmt:message key="admin-cabinet_jsp.label.user.blocked"/></td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="user" items="${userList}">
                            <tr>
                                <td>${user.login}</td>
                                <td>${user.firstName}</td>
                                <td>${user.lastName}</td>
                                <td>${user.blocked}</td>
                                <form action="admin-cabinet" method="post">
                                    <input type="hidden" name="user_id" value="${user.id}"/>
                                    <input type="hidden" name="change_block" value="${user.blocked}"/>
                                    <td><input type="submit" value='<fmt:message key="admin-cabinet_jsp.button.user.change_blocked"/>'/></td>
                                </form>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>
<table id="main-container">
    <tr>
        <td class="content">
            <c:choose>
                <c:when test="${fn:length(magazineList) == 0}">no magazines in the base</c:when>
                <c:otherwise>
                    <table id="list_user_table">
                        <thead>
                        <tr>
                            <td><fmt:message key="admin-cabinet_jsp.label.magazine.name"/></td>
                            <td><fmt:message key="admin-cabinet_jsp.label.magazine.price"/></td>
                            <td><fmt:message key="admin-cabinet_jsp.label.magazine.image"/></td>
                            <td><fmt:message key="admin-cabinet_jsp.button.magazine.change"/></td>
                            <td><fmt:message key="admin-cabinet_jsp.button.magazine.delete"/></td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="magazine" items="${magazineList}">
                            <tr>
                                <form action="admin-cabinet" method="post">
                                    <input type="hidden" name="magazine_id" value="${magazine.id}"/>
                                    <td><input name="magazine_name_change_value" value="${magazine.name}"/></td>
                                    <td><input name="magazine_price_change_value" value="${magazine.price}"/></td>
                                    <td><input name="magazine_image_change_value" value="${magazine.image}"/></td>
                                    <input type="hidden" name="magazine_theme_id_change_value" value="${magazine.themeId}"/>
                                    <td><input type="submit" name="update_magazine" value='<fmt:message key="admin-cabinet_jsp.button.magazine.change"/>'/></td>
                                    <td><input type="submit" name="delete_magazine" value='<fmt:message key="admin-cabinet_jsp.button.magazine.delete"/>'/></td>
                                </form>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>