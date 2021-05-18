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
    <form class="form-custom" id="make_order" action="controller">
        <div class="wrapper">
            <section class="jumbotron text-center">
                <div class="container">
                    <h1 class="jumbotron-heading">Periodical subscription</h1>
                    <p class="lead text-muted">Here you can find some interesting stuff to read, so don't hesitate
                        subscribe to few magazines and you'll like it.Spend some time with new knowledge sweet as cake,
                        healthy as apple.</p>
                    <input type="hidden" name="command" value="makeOrder"/>
                    <input class="btn btn-primary my-2" type="submit"
                           value='<fmt:message key="list_menu_jsp.button.make_an_order"/>'/>
                </div>
            </section>
            <div class="container">
                <c:forEach var="category" items="${menuItemsByOneCategory}">
                    <div class="row">
                        <div class="theme-name"><h3>${category.key}</h3></div>
                        <c:forEach var="menuItem" items="${category.value}" end="2">
                            <div class="col">
                                <link rel="stylesheet" href="../static/css/album.css"/>
                                <img src="../static/images/music_1.jpg" width="100%"/>
                                <p>${menuItem.name}</p>
                                <p>${menuItem.price}</p>
                                <div class="form-check form-switch">
                                    <input class="form-check-input" type="checkbox" id="itemId${menuItem.id}"
                                           name="itemId"
                                           value="${menuItem.id}"/>
                                    <label class="form-check-label" for="itemId${menuItem.id}">Add this magazine to
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
