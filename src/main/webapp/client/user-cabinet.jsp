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
                <p class="lead text-muted">Hello, ${userLogin}</p>
                <p class="lead text-muted">your balance is: ${userBalance}</p>
                <p class="lead text-muted">here is your subscriptions:</p>
            </div>
        </section>
        <div class="container">
            <div class="row">
                <c:forEach var="item" items="${subscriptionList}">
                    <div class="col">
                        <img src="<c:url value="/static/images/${item.magazineImage}"/>" width="100%"/>
                        <p>${item.magazineName}</p>
                        <p>Price: ${item.magazinePrice}</p>
                        <p>Theme: ${item.themeName}</p>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
