<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Admin Cabinet" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="main-container main-container-admin">
    <div class="wrapper wrapper-cabinet admin">
        <c:choose>
            <c:when test="${fn:length(userList) == 0}">no users registered</c:when>
            <c:otherwise>
                <table class="table table-bordered table_custom">
                    <thead>
                    <tr>
                        <th style="text-align: center; color: rgb(217 205 185)" colspan="5">
                            <h3>
                                <fmt:message key="admin-cabinet_jsp.label.user.block"/>
                            </h3>
                        </th>
                    </tr>
                    <tr>
                        <th scope="col"><fmt:message key="admin-cabinet_jsp.label.user.login"/></th>
                        <th scope="col"><fmt:message key="admin-cabinet_jsp.label.user.first_name"/></th>
                        <th scope="col"><fmt:message key="admin-cabinet_jsp.label.user.last_name"/></th>
                        <th scope="col"><fmt:message key="admin-cabinet_jsp.label.user.blocked"/></th>
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
                                <td><input type="submit"
                                           value='<fmt:message key="admin-cabinet_jsp.button.user.change_blocked"/>'/>
                                </td>
                            </form>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
        <div class="clients_list">
        </div>


        <table class="table table-bordered table_custom">
            <thead>
            <tr>
                <th style="text-align: center; color: rgb(217 205 185)" colspan="5">
                    <h3>
                        <fmt:message key="admin-cabinet_jsp.label.magazine.add"/>
                    </h3>
                </th>
            </tr>
            <tr>
                <th scope="col"><fmt:message key="admin-cabinet_jsp.label.magazine.name"/></th>
                <th scope="col"><fmt:message key="admin-cabinet_jsp.label.magazine.price"/></th>
                <th scope="col"><fmt:message key="admin-cabinet_jsp.label.magazine.image"/></th>
                <th scope="col"><fmt:message key="admin-cabinet_jsp.label.magazine.themeId"/></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <form action="admin-cabinet" method="post">
                    <td><input name="magazine_name_add_value"/></td>
                    <td><input name="magazine_price_add_value"/></td>
                    <td><input name="magazine_image_add_value"/></td>
                    <td><input name="magazine_theme_id_add_value"/></td>
                    <input type="hidden" name="magazine_theme_id_change_value"
                           value="${magazine.themeId}"/>
                    <td><input type="submit" name="add_magazine"
                               value='<fmt:message key="admin-cabinet_jsp.button.magazine.add"/>'/>
                    </td>
                </form>
            </tr>
            </tbody>
        </table>


        <c:choose>
            <c:when test="${fn:length(magazineList) == 0}">no magazines in the base</c:when>
            <c:otherwise>
                <table class="table table-bordered table_custom">
                    <thead>
                    <tr>
                        <th style="text-align: center; color: rgb(217 205 185)" colspan="6">
                            <h3>
                                <fmt:message key="admin-cabinet_jsp.label.magazine.edit"/>
                            </h3>
                        </th>
                    </tr>
                    <tr>
                        <th scope="col"><fmt:message key="admin-cabinet_jsp.label.magazine.name"/></th>
                        <th scope="col"><fmt:message key="admin-cabinet_jsp.label.magazine.price"/></th>
                        <th scope="col"><fmt:message key="admin-cabinet_jsp.label.magazine.image"/></th>
                        <th scope="col"><fmt:message key="admin-cabinet_jsp.label.magazine.themeId"/></th>
                        <th scope="col"><fmt:message key="admin-cabinet_jsp.button.magazine.save"/></th>
                        <th scope="col"><fmt:message key="admin-cabinet_jsp.button.magazine.delete"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="magazine" items="${magazineList}">
                        <tr>
                            <form action="admin-cabinet" method="post">
                                <input type="hidden" name="magazine_id" value="${magazine.id}"/>
                                <td>
                                    <input name="magazine_name_change_value" value="${magazine.name}"/>
                                </td>
                                <td>
                                    <input name="magazine_price_change_value" value="${magazine.price}"/>
                                </td>
                                <td>
                                    <input name="magazine_image_change_value" value="${magazine.image}"/>
                                </td>
                                <td>
                                    <input name="magazine_theme_id_change_value" value="${magazine.themeId}"/>
                                </td>
                                <td><input type="submit" name="update_magazine"
                                           value='<fmt:message key="admin-cabinet_jsp.button.magazine.save"/>'/>
                                </td>
                                <td><input type="submit" name="delete_magazine"
                                           value='<fmt:message key="admin-cabinet_jsp.button.magazine.delete"/>'/>
                                </td>
                            </form>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>