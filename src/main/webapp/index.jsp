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

                <div class="sorting">
                    <c:set var="urlp" value="${fn:replace(
                            pageContext.request.requestURL,
                             pageContext.request.requestURI,
                            pageContext.request.contextPath)}/index?page=1"/>
                    <c:if test="${param['page'] != null}"/>
                    <div class="dropdown show dropdown-filtering">
                        <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLinkFilter"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <c:set var="interior" value="interior"/>
                            <c:set var="sport" value="sport"/>
                            <c:set var="it_world" value="it_world"/>
                            <c:set var="music" value="music"/>
                            <c:set var="all" value="all"/>
                            <c:choose>
                                <c:when test="${fieldToFilter eq interior}">
                                    <fmt:message key="index_jsp.label.filter.interior"/>
                                </c:when>
                                <c:when test="${fieldToFilter eq sport}">
                                    <fmt:message key="index_jsp.label.filter.sport"/>
                                </c:when>
                                <c:when test="${fieldToFilter eq it_world}">
                                    <fmt:message key="index_jsp.label.filter.it_world"/>
                                </c:when>
                                <c:when test="${fieldToFilter eq music}">
                                    <fmt:message key="index_jsp.label.filter.music"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="index_jsp.label.filter.all_wo_filter"/>
                                </c:otherwise>
                            </c:choose>
                        </a>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuLinkFilter">
                            <c:url var="url" value="">
                                <c:forEach items="${param}" var="entry">
                                    <c:if test="${entry.key != 'filter' && entry.key != 'page'}">
                                        <c:param name="${entry.key}" value="${entry.value}"/>
                                    </c:if>
                                </c:forEach>
                                <c:param name="filter" value="all"/>
                                <c:param name="page" value="1"/>
                            </c:url>
                            <a class="dropdown-item"
                               href="<c:out value='${url}'/>" >
                                <fmt:message key="index_jsp.label.filter.all_wo_filter"/>
                            </a>

                            <c:url var="url" value="">
                                <c:forEach items="${param}" var="entry">
                                    <c:if test="${entry.key != 'filter' && entry.key != 'page'}">
                                        <c:param name="${entry.key}" value="${entry.value}"/>
                                    </c:if>
                                </c:forEach>
                                <c:param name="filter" value="interior"/>
                                <c:param name="page" value="1"/>
                            </c:url>
                            <a class="dropdown-item"
                               href="<c:out value='${url}'/>" >
                                <fmt:message key="index_jsp.label.filter.interior"/>
                            </a>

                            <c:url var="url" value="">
                                <c:forEach items="${param}" var="entry">
                                    <c:if test="${entry.key != 'filter' && entry.key != 'page'}">
                                        <c:param name="${entry.key}" value="${entry.value}"/>
                                    </c:if>
                                </c:forEach>
                                <c:param name="filter" value="sport"/>
                                <c:param name="page" value="1"/>
                            </c:url>
                            <a class=" dropdown-item"
                               href="<c:out value='${url}'/>" >
                            <fmt:message key="index_jsp.label.filter.sport"/>
                            </a>

                            <c:url var="url" value="">
                                <c:forEach items="${param}" var="entry">
                                    <c:if test="${entry.key != 'filter' && entry.key != 'page'}">
                                        <c:param name="${entry.key}" value="${entry.value}"/>
                                    </c:if>
                                </c:forEach>
                                <c:param name="filter" value="it_world"/>
                                <c:param name="page" value="1"/>
                            </c:url>
                            <a class="dropdown-item"
                               href="<c:out value='${url}'/>" >
                                <fmt:message key="index_jsp.label.filter.it_world"/>
                            </a>


                            <c:url var="url" value="">
                                <c:forEach items="${param}" var="entry">
                                    <c:if test="${entry.key != 'filter' && entry.key != 'page'}">
                                        <c:param name="${entry.key}" value="${entry.value}"/>
                                    </c:if>
                                </c:forEach>
                                <c:param name="filter" value="music"/>
                                <c:param name="page" value="1"/>
                            </c:url>
                            <a class="dropdown-item"
                               href="<c:out value='${url}'/>" >
                                <fmt:message key="index_jsp.label.filter.music"/>
                            </a>
                        </div>
                    </div>

                    <div class="dropdown show dropdown-custom">
                        <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <c:set var="name_asc" value="name_asc"/>
                            <c:set var="name_desc" value="name_desc"/>
                            <c:set var="price_asc" value="price_asc"/>
                            <c:set var="price_desc" value="price_desc"/>
                            <c:set var="all" value="all"/>
                            <c:choose>
                                <c:when test="${fieldToSort eq name_asc}">
                                    <fmt:message key="index_jsp.label.sort.name_asc"/>
                                </c:when>
                                <c:when test="${fieldToSort eq name_desc}">
                                    <fmt:message key="index_jsp.label.sort.name_desc"/>
                                </c:when>
                                <c:when test="${fieldToSort eq price_asc}">
                                    <fmt:message key="index_jsp.label.sort.price_asc"/>
                                </c:when>
                                <c:when test="${fieldToSort eq price_desc}">
                                    <fmt:message key="index_jsp.label.sort.price_desc"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="index_jsp.label.sort.all_wo_sorting"/>
                                </c:otherwise>
                            </c:choose>
                        </a>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">

                                <c:url var="url" value="">
                                    <c:forEach items="${param}" var="entry">
                                        <c:if test="${entry.key != 'sort' && entry.key != 'page'}">
                                            <c:param name="${entry.key}" value="${entry.value}"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:param name="sort" value="all"/>
                                    <c:param name="page" value="1"/>
                                </c:url>
                            <a class="dropdown-item"
                               href="<c:out value='${url}'/>" >
                                <fmt:message key="index_jsp.label.sort.all_wo_sorting"/>
                            </a>

                            <c:url var="url" value="">
                                <c:forEach items="${param}" var="entry">
                                    <c:if test="${entry.key != 'sort' && entry.key != 'page'}">
                                        <c:param name="${entry.key}" value="${entry.value}"/>
                                    </c:if>
                                </c:forEach>
                                <c:param name="sort" value="name_asc"/>
                                <c:param name="page" value="1"/>
                            </c:url>
                            <a class="dropdown-item"
                               href="<c:out value='${url}'/>" >
                                <fmt:message key="index_jsp.label.sort.name_asc"/>
                            </a>


                            <c:url var="url" value="">
                                <c:forEach items="${param}" var="entry">
                                    <c:if test="${entry.key != 'sort' && entry.key != 'page'}">
                                        <c:param name="${entry.key}" value="${entry.value}"/>
                                    </c:if>
                                </c:forEach>
                                <c:param name="sort" value="name_desc"/>
                                <c:param name="page" value="1"/>
                            </c:url>
                            <a class="dropdown-item"
                               href="<c:out value='${url}'/>" >
                                <fmt:message key="index_jsp.label.sort.name_desc"/>
                            </a>


                            <c:url var="url" value="">
                                <c:forEach items="${param}" var="entry">
                                    <c:if test="${entry.key != 'sort' && entry.key != 'page'}">
                                        <c:param name="${entry.key}" value="${entry.value}"/>
                                    </c:if>
                                </c:forEach>
                                <c:param name="sort" value="price_asc"/>
                                <c:param name="page" value="1"/>
                            </c:url>
                            <a class="dropdown-item"
                               href="<c:out value='${url}'/>" >
                                <fmt:message key="index_jsp.label.sort.price_asc"/>
                            </a>


                            <c:url var="url" value="">
                                <c:forEach items="${param}" var="entry">
                                    <c:if test="${entry.key != 'sort' && entry.key != 'page'}">
                                        <c:param name="${entry.key}" value="${entry.value}"/>
                                    </c:if>
                                </c:forEach>
                                <c:param name="sort" value="price_desc"/>
                                <c:param name="page" value="1"/>
                            </c:url>
                            <a class="dropdown-item"
                               href="<c:out value='${url}'/>" >
                                <fmt:message key="index_jsp.label.sort.price_desc"/>
                            </a>

                        </div>
                    </div>
                </div>

                <c:choose>
                    <c:when test="${fn:length(magazinesPage) == 0}">No magazines in filtered theme</c:when>
                    <c:otherwise>
                        <c:forEach var="magazine" items="${magazinesPage}">
                            <div class="col">
                                <img src="<c:url value="/static/images/${magazine.image}"/>" width="100%"/>
                                <p>${magazine.name}</p>
                                <p>${magazine.price}</p>
                            </div>
                        </c:forEach>
                        <div class="page_bar">
                            <c:if test="${firstPage != null}">
                                <%--                                <c:url var="first_url" value="/index${nextUrl}&page=${firstPage}"/>--%>
                                <a style="padding: 0 3px"
                                        <c:url var="first_url" value="">
                                            <c:forEach items="${param}" var="entry">
                                                <c:if test="${entry.key != 'page'}">
                                                    <c:param name="${entry.key}" value="${entry.value}"/>
                                                </c:if>
                                            </c:forEach>
                                            <c:param name="page" value="${firstPage}"/>
                                        </c:url>
                                   <c:if test="${currentPage == firstPage}">class="disabled"</c:if>
                                   <c:if test="${currentPage != firstPage}">href="<c:out value='${firstUrl}'/>"
                                </c:if>
                                ><<</a>
                            </c:if>
                            <c:if test="${previousPage != null}">
                                <%--                                <c:url var="prev_url" value="/index?page=${previousPage}"/>--%>
                                <c:url var="prev_url" value="">
                                    <c:forEach items="${param}" var="entry">
                                        <c:if test="${entry.key != 'page'}">
                                            <c:param name="${entry.key}" value="${entry.value}"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:param name="page" value="${previousPage}"/>
                                </c:url>
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
                                                <c:url var="url" value="">
                                                    <c:forEach items="${param}" var="entry">
                                                        <c:if test="${entry.key != 'page'}">
                                                            <c:param name="${entry.key}" value="${entry.value}"/>
                                                        </c:if>
                                                    </c:forEach>
                                                    <c:param name="page" value="1"/>
                                                </c:url>
                                           href="<c:out value='${url}'/>">1</a>
                                        <span>...</span>
                                    </c:if>

                                    <c:forEach var="page" items="${carriage}">
                                        <%--                                        <c:url var="url" value="/index?page=${page}"/>--%>
                                        <c:url var="url" value="">
                                            <c:forEach items="${param}" var="entry">
                                                <c:if test="${entry.key != 'page'}">
                                                    <c:param name="${entry.key}" value="${entry.value}"/>
                                                </c:if>
                                            </c:forEach>
                                            <c:param name="page" value="${page}"/>
                                        </c:url>
                                        <a style="padding: 0 3px"
                                           <c:if test="${currentPage == page}">class="active"</c:if>
                                           href="<c:out value='${url}'/>">${page}</a>
                                    </c:forEach>

                                    <c:if test="${pageLast}">
                                        <span>...</span>
                                        <%--                                        <c:url var="url" value="/index?page=${lastPage}"/>--%>
                                        <c:url var="url" value="">
                                            <c:forEach items="${param}" var="entry">
                                                <c:if test="${entry.key != 'page'}">
                                                    <c:param name="${entry.key}" value="${entry.value}"/>
                                                </c:if>
                                            </c:forEach>
                                            <c:param name="page" value="${lastPage}"/>
                                        </c:url>
                                        <a style="padding: 0 3px"
                                           href="<c:out value='${url}'/>">${lastPage}</a>
                                    </c:if>

                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="page" items="${baseList}">
                                        <%--                                        <c:url var="url" value="/index?page=${page}"/>--%>
                                        <c:url var="url" value="">
                                            <c:forEach items="${param}" var="entry">
                                                <c:if test="${entry.key != 'page'}">
                                                    <c:param name="${entry.key}" value="${entry.value}"/>
                                                </c:if>
                                            </c:forEach>
                                            <c:param name="page" value="${page}"/>
                                        </c:url>
                                        <a style="padding: 0 3px"
                                           <c:if test="${currentPage == page}">class="active"</c:if>
                                           href="<c:out value='${url}'/>">${page}</a>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>

                            <c:if test="${nextPage != null}">
                                <%--                                <c:url var="next_url" value="/index?page=${nextPage}"/>--%>
                                <c:url var="next_url" value="">
                                    <c:forEach items="${param}" var="entry">
                                        <c:if test="${entry.key != 'page'}">
                                            <c:param name="${entry.key}" value="${entry.value}"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:param name="page" value="${nextPage}"/>
                                </c:url>
                                <a style="padding: 0 3px"
                                   <c:if test="${currentPage == lastPage}">class="disabled"</c:if>
                                   <c:if test="${currentPage != lastPage}">href="<c:out value='${next_url}'/>"
                                </c:if>
                                >></a>
                            </c:if>
                            <c:if test="${lastPage != null}">
                                <%--                                <c:url var="last_url" value="/index?page=${lastPage}"/>--%>
                                <c:url var="last_url" value="">
                                    <c:forEach items="${param}" var="entry">
                                        <c:if test="${entry.key != 'page'}">
                                            <c:param name="${entry.key}" value="${entry.value}"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:param name="page" value="${lastPage}"/>
                                </c:url>
                                <a style="padding: 0 3px"
                                   <c:if test="${currentPage == lastPage}">class="disabled"</c:if>
                                   <c:if test="${currentPage != lastPage}">href="<c:out value='${last_url}'/>"
                                </c:if>
                                >>></a>
                            </c:if>
                        </div>
                    </c:otherwise>
                </c:choose>
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
