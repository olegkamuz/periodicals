<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="User Cabinet" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<div>
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
</div>
<div class="main-container user">
    <div class="wrapper wrapper-cabinet">
        <section class="jumbotron text-center">
            <div class="container">
                <p class="lead text-muted"><fmt:message key="client-cabinet_jsp.p.hello"/> ${user.login}</p>
                <p class="lead text-muted"><fmt:message key="client-cabinet_jsp.p.your_balance"/> ${user.balance}</p>
                <form id="login_form" action="client-cabinet" method="post">
                    <input type="hidden" name="user-id" value="${user.id}"/>
                    <fieldset>
                        <legend>
                            <fmt:message key="client-cabinet_jsp.label.replenish"/>
                        </legend>
                        <input name="replenish"/>
                        <input type="submit" value='<fmt:message key="client-cabinet_jsp.label.replenish"/>'>
                    </fieldset>
                </form>
                <p class="lead text-muted"><fmt:message key="client-cabinet_jsp.p.your_subscription"/></p>
            </div>
        </section>
        <c:choose>
            <c:when test="${fn:length(subscriptionList) == 0}">
                <div class="get_back_wrapper">
                    <div class="get_back">
                        <div>
                            <h3>
                                <fmt:message key="client-cabinet_jsp.div.no_subscription"/>
                            </h3>
                        </div>
                        <div>
                            <h3>
                                <a class="home" href="index
<c:if test='${not empty currentPage}'>?page=${currentPage}</c:if>
">
                                    <fmt:message key="client-cabinet_jsp.a.back_with_pre_params"/>
                                </a>
                            </h3>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <c:if test="${not empty notEnough}">
                    <div class="client_cabinet_error_wrapper">
                        <div class="error">
                            <fmt:message key="client-cabinet_jsp.div.not_enough_money_for_subscription"/>
                        </div>
                    </div>
                </c:if>
                <div class="container">
                    <div class="row">
                        <c:forEach var="item" items="${subscriptionList}">
                            <div class="col-xl-2 col-custom col-custom-cabinet">
                                <div class="mag_image">
                                    <img
                                            src="<c:url value="/static/images/${item.magazineImage}"/>"
                                            width="100%"/>
                                </div>
                                <p>${item.magazineName}</p>
                                <p>Price: ${item.magazinePrice}</p>
                                <p>Theme: ${item.themeName}</p>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
