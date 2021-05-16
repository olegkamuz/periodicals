<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>
<html>
<head>
    <c:set var="title" value="Menu" scope="page"/>
    <%@ include file="/WEB-INF/jspf/headOLEG.jspf" %>
</head>
<body>
<h1>Hello, world!</h1>
<div class="container">
    <div class="row">
        <div class="col">
            <img src="static/images/periodical_index.jpeg" width="15%"/>
            <div class="form-check form-switch">
                <input class="form-check-input" type="checkbox" id="itemId${menuItem.id}" name="itemId" value="${menuItem.id}"/>
                <label class="form-check-label" for="itemId${menuItem.id}"
                >Default switch checkbox input</label
                >
            </div>
        </div>
        <div class="col">
            <img src="static/images/periodical_index.jpeg" width="15%"/>
        </div>
        <div class="col">
            <img src="static/images/periodical_index.jpeg" width="15%"/>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
</html>

