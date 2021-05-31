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
                            <td><fmt:message key="admin-cabinet_jsp.label.id"/></td>
                            <td><fmt:message key="admin-cabinet_jsp.label.login"/></td>
                            <td><fmt:message key="admin-cabinet_jsp.label.first_name"/></td>
                            <td><fmt:message key="admin-cabinet_jsp.label.last_name"/></td>
                            <td><fmt:message key="admin-cabinet_jsp.label.blocked"/></td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="user" items="${userList}">
                            <tr>
                                <td>${user.id}</td>
                                <td>${user.login}</td>
                                <td>${user.firstName}</td>
                                <td>${user.lastName}</td>
                                <td>${user.blocked}</td>
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