<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Menu" scope="page"/>
<%@ include file="/WEB-INF/jspf/headOLEG.jspf" %>

<body>
<div>
    <%@ include file="/WEB-INF/jspf/headerOLEG.jspf" %>
</div>
<div class="main-container">
        <div class="wrapper">
            <section class="jumbotron text-center">
                <div class="container">
                    <h1 class="jumbotron-heading">Periodical subscription</h1>
                    <p class="lead text-muted">Here you can find some interesting stuff to read, so don't hesitate
                        subscribe to few magazines and you'll like it.Spend some time with new knowledge sweet as cake,
                        healthy as apple.</p>
                    <h3>Go to category where you can order subscription</h3>
                </div>
            </section>
            <div class="container">
                <c:forEach var="category" items="${menuItemsByCategory}">
                    <c:url value="/one-category-magazines" var="url">
                        <c:param  name="category" value="${category.key.name}" />
                    </c:url>
                    <a href="<c:out value='${url}'/>">
                        <div class="row">
                            <div class="theme-name"><h3>${category.key.name}</h3></div>
                            <c:forEach var="menuItem" items="${category.value}" end="2">
                                <div class="col">
                                    <img src="<c:url value="/static/images/music_1.jpg"/>" width="100%"/>
                                    <p>${menuItem.name}</p>
                                    <p>${menuItem.price}</p>
                                </div>
                            </c:forEach>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
