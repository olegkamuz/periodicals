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
            <div class="row">
                <div class="theme-name"><h3>All</h3></div>
                <c:forEach var="magazine" items="${magazinesList}">
                    <div class="col">
                        <img src="<c:url value="/static/images/${magazine.image}"/>" width="100%"/>
                        <p>${magazine.name}</p>
                        <p>${magazine.price}</p>
                    </div>
                </c:forEach>

                <div class="page_bar">
                    <c:if test="${firstPage != null}">
                        <c:url var="first_url" value="/index?page=${firstPage}"/>
                        <a style="padding: 0 3px"
                           <c:if test="${currentPage == firstPage}">class="disabled"</c:if>
                           <c:if test="${currentPage != firstPage}">href="<c:out value='${first_url}'/>"</c:if>
                        ><<</a>
                    </c:if>
                    <c:if test="${previousPage != null}">
                        <c:url var="prev_url" value="/index?page=${previousPage}"/>
                        <a style="padding: 0 3px"
                           <c:if test="${currentPage == firstPage}">class="disabled"</c:if>
                           <c:if test="${currentPage != firstPage}">href="<c:out value='${prev_url}'/>"
                        </c:if>
                        ><</a>
                    </c:if>

                    <c:choose>
                        <c:when test="${areDots}">

                            <c:if test="${pageFirst}">
                                <c:url var="url" value="/index?page=1"/>
                                <a style="padding: 0 3px"
                                   href="<c:out value='${url}'/>">1</a>
                                <span>...</span>
                            </c:if>

                            <c:forEach var="page" items="${carriage}">
                                <c:url var="url" value="/index?page=${page}"/>
                                <a style="padding: 0 3px"
                                   <c:if test="${currentPage == page}">class="active"</c:if>
                                   href="<c:out value='${url}'/>">${page}</a>
                            </c:forEach>

                            <c:if test="${pageLast}">
                                <span>...</span>
                                <c:url var="url" value="/index?page=${lastPage}"/>
                                <a style="padding: 0 3px"
                                   href="<c:out value='${url}'/>">${lastPage}</a>
                            </c:if>

                        </c:when>
                        <c:otherwise>
                            <c:forEach var="page" items="${firstFourPages}">
                                <c:url var="url" value="/index?page=${page}"/>
                                <a style="padding: 0 3px"
                                   <c:if test="${currentPage == page}">class="active"</c:if>
                                   href="<c:out value='${url}'/>">${page}</a>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>

                    <c:if test="${nextPage != null}">
                        <c:url var="next_url" value="/index?page=${nextPage}"/>
                        <a style="padding: 0 3px"
                           <c:if test="${currentPage == lastPage}">class="disabled"</c:if>
                           <c:if test="${currentPage != lastPage}">href="<c:out value='${next_url}'/>"
                        </c:if>
                        >></a>
                    </c:if>
                    <c:if test="${lastPage != null}">
                        <c:url var="last_url" value="/index?page=${lastPage}"/>
                        <a style="padding: 0 3px"
                           <c:if test="${currentPage == lastPage}">class="disabled"</c:if>
                           <c:if test="${currentPage != lastPage}">href="<c:out value='${last_url}'/>"
                        </c:if>
                        >>></a>
                    </c:if>
                </div>
            </div>
        </div>


        <div class="container">
            <c:forEach var="theme" items="${magazinesByThemes}">
                <c:url value="/one-theme-magazines" var="url">
                    <c:param name="theme" value="${theme.key.name}"/>
                </c:url>
                <a href="<c:out value='${url}'/>">
                    <div class="row">
                        <div class="theme-name"><h3>${theme.key.name}</h3></div>
                        <c:forEach var="magazine" items="${theme.value}" end="2">
                            <div class="col">
                                <img src="<c:url value="/static/images/${magazine.image}"/>" width="100%"/>
                                <p>${magazine.name}</p>
                                <p>${magazine.price}</p>
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
