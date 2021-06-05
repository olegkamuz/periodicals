<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Menu" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<div>
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
</div>
<div class="main-container">
    <form class="form-custom" id="make_order" action="create-subscription">
        <div class="wrapper">
            <section class="jumbotron text-center">
                <div class="container">
                    <h1 class="jumbotron-heading">Periodical subscription</h1>
                    <p class="lead text-muted">Here you can find some interesting stuff to read, so don't hesitate
                        subscribe to few magazines and you'll like it.Spend some time with new knowledge sweet as cake,
                        healthy as apple.</p>
                    <input class="btn btn-primary my-2" type="submit"
                           value='<fmt:message key="list_menu_jsp.button.make_an_order"/>'/>
                </div>
            </section>
            <div class="container">
                <c:forEach var="theme" items="${magazinesByOneTheme}">
                    <input type="hidden" name="theme" value="${theme.key}"/>
                    <div class="row">
                        <div class="theme-name"><h3>${theme.key}</h3></div>
                        <c:forEach var="magazine" items="${theme.value}">
                            <div class="col">
                                <link rel="stylesheet" href="../static/css/album.css"/>
                                <img src="<c:url value="/static/images/${magazine.image}"/>" width="100%"/>
                                <p>${magazine.name}</p>
                                <p>${magazine.price}</p>
                                <div class="form-check form-switch">
                                    <input class="form-check-input" type="checkbox" id="magazineId${magazine.id}"
                                           name="magazineId"
                                           value="${magazine.id}"
                                            <c:forEach var="item" items="${checked}">
                                                <c:if test="${item eq magazine.id}">
                                                    checked
                                                </c:if>
                                            </c:forEach>
                                    />
                                    <label class="form-check-label" for="magazineId${magazine.id}">Add this magazine to
                                        subscription</label>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>
            </div>
        </div>
    </form>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
