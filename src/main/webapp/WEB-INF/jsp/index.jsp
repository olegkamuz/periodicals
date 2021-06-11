<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Menu" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<c:if test="${not empty fieldToSort}">
    <c:set var="sort" value="${fieldToSort}"/>
</c:if>
<c:if test="${not empty fieldToFilter}">
    <c:set var="filter" value="${fieldToFilter}"/>
</c:if>
<div>
    <%@ include file="/WEB-INF/jspf/header.jspf" %>
</div>
<div class="main-container">
        <div class="wrapper">
<%-- GREETINGS --%>
            <section class="jumbotron text-center">
                <div class="container">
                    <h1 class="jumbotron-heading"><fmt:message key="index_jsp.h1.periodicals_subscription"/></h1>
                    <p class="lead text-muted"><fmt:message key="index_jsp.p.inspiring_text"/></p>
                    <c:if test="${empty user}">
                        <h3><fmt:message key="index_jsp.h3.login_to_subscribe"/></h3>
                    </c:if>
                    <c:if test="${not empty user}">
                        <h3><fmt:message key="index_jsp.h3.replenish_to_subscribe"/></h3>
                    </c:if>
                </div>
            </section>

            <div class="container container-custom">
<%-- MAGAZINES BY PAGES --%>
                <div class="row row-custom">
                    <div class="subscription_submit">
                        <form class="form-custom" id="make_order" action="create-subscription">
                        <input class="btn btn-primary my-2" type="submit"
                               value='<fmt:message key="index_jsp.button.buy_subscription"/>'/>
                        <c:url var="reset_url" value="">
                            <c:forEach items="${param}" var="entry">
                                <c:if test="${
                                         entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                         entry.key != 'localeToSet' &&
                                         entry.key != 'login' &&
                                         entry.key != 'password'
                                         }">
                                    <c:param name="${entry.key}" value="${entry.value}"/>
                                </c:if>
                            </c:forEach>
                            <c:param name="reset_checked" value="all"/>
                        </c:url>
                        </form>
                        <a href="<c:out value='${reset_url}'/>">
                            <span class="btn btn-secondary my-2">
                                <fmt:message key="index_jsp.button.reset_checked"/>
                            </span>
                        </a>
                    </div>

                    <div class="sorting">
<%-- FILTERING --%>

                        <div class="dropdown show dropdown-filtering">
                            <c:url var="url_filter" value="">
                                <c:forEach items="${param}" var="entry">
                                    <c:if test="${
                                         entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                         entry.key != 'localeToSet' &&
                                         entry.key != 'login' &&
                                         entry.key != 'localeToSet' &&
                                         entry.key != 'password'
                                         }">
                                        <c:param name="${entry.key}" value="${entry.value}"/>
                                    </c:if>
                                </c:forEach>
                            </c:url>
                            <a class="btn btn-secondary dropdown-toggle" href="${url_filter}" role="button"
                               id="dropdownMenuLinkFilter"
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
                                        <c:if test="${entry.key != 'filter' &&
                                                            entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                         entry.key != 'page' &&
                                         entry.key != 'localeToSet' &&
                                         entry.key != 'login' &&
                                         entry.key != 'localeToSet' &&
                                         entry.key != 'password'
                                         }">
                                            <c:param name="${entry.key}" value="${entry.value}"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:param name="filter" value="all"/>
                                    <c:param name="page" value="1"/>
                                </c:url>
                                <a class="dropdown-item"
                                   href="<c:out value='${url}'/>">
                                    <fmt:message key="index_jsp.label.filter.all_wo_filter"/>
                                </a>

                                <c:url var="url" value="">
                                    <c:forEach items="${param}" var="entry">
                                        <c:if test="${entry.key != 'filter' &&
                                                            entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                         entry.key != 'page' &&
                                         entry.key != 'login' &&
                                         entry.key != 'localeToSet' &&
                                         entry.key != 'password'
                                         }">
                                            <c:param name="${entry.key}" value="${entry.value}"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:param name="filter" value="interior"/>
                                    <c:param name="page" value="1"/>
                                </c:url>
                                <a class="dropdown-item"
                                   href="<c:out value='${url}'/>">
                                    <fmt:message key="index_jsp.label.filter.interior"/>
                                </a>

                                <c:url var="url" value="">
                                    <c:forEach items="${param}" var="entry">
                                        <c:if test="${entry.key != 'filter' &&
                                                            entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                         entry.key != 'page' &&
                                         entry.key != 'login' &&
                                         entry.key != 'localeToSet' &&
                                         entry.key != 'password'
                                         }">
                                            <c:param name="${entry.key}" value="${entry.value}"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:param name="filter" value="sport"/>
                                    <c:param name="page" value="1"/>
                                </c:url>
                                <a class=" dropdown-item"
                                   href="<c:out value='${url}'/>">
                                    <fmt:message key="index_jsp.label.filter.sport"/>
                                </a>

                                <c:url var="url" value="">
                                    <c:forEach items="${param}" var="entry">
                                        <c:if test="${entry.key != 'filter' &&
                                                            entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                         entry.key != 'page' &&
                                         entry.key != 'login' &&
                                         entry.key != 'localeToSet' &&
                                         entry.key != 'password'
                                         }">
                                            <c:param name="${entry.key}" value="${entry.value}"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:param name="filter" value="it_world"/>
                                    <c:param name="page" value="1"/>
                                </c:url>
                                <a class="dropdown-item"
                                   href="<c:out value='${url}'/>">
                                    <fmt:message key="index_jsp.label.filter.it_world"/>
                                </a>


                                <c:url var="url" value="">
                                    <c:forEach items="${param}" var="entry">
                                        <c:if test="${entry.key != 'filter' &&
                                                            entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                         entry.key != 'page' &&
                                         entry.key != 'login' &&
                                         entry.key != 'localeToSet' &&
                                         entry.key != 'password'
                                         }">
                                            <c:param name="${entry.key}" value="${entry.value}"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:param name="filter" value="music"/>
                                    <c:param name="page" value="1"/>
                                </c:url>
                                <a class="dropdown-item"
                                   href="<c:out value='${url}'/>">
                                    <fmt:message key="index_jsp.label.filter.music"/>
                                </a>
                            </div>
                        </div>
<%-- SEARCH --%>
                        <div class="search">
                            <form id="search_form" action="index" method="post">

                                <div class="input-group mb-3">
                                    <div class="input-group-prepend">
                                        <button class="btn btn-outline-secondary" type="submit"><fmt:message key="index_jsp.input.search"/></button>
                                    </div>
                                    <input value="<c:if test='${not empty fieldToSearch}'>${fieldToSearch}</c:if>" name="search" type="text" class="form-control" placeholder="" aria-label="" aria-describedby="basic-addon1">
                                </div>
                            </form>
                        </div>
<%-- SORTING --%>
                      <div class="dropdown show dropdown-custom">

        <c:url var="url_sort" value="">
            <c:forEach items="${param}" var="entry">
                <c:if test="${
                                         entry.key != 'magazineId' &&
                                         entry.key != 'reset_checked' &&
                                         entry.key != 'login' &&
                                         entry.key != 'localeToSet' &&
                                         entry.key != 'password'
                                         }">
                    <c:param name="${entry.key}" value="${entry.value}"/>
                </c:if>
            </c:forEach>
        </c:url>
        <a class="btn btn-secondary dropdown-toggle" href="${url_sort}" role="button"
           id="dropdownMenuLink"
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
                    <c:if test="${entry.key != 'sort' &&
                                         entry.key != 'page' &&
                                         entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                         entry.key != 'login' &&
                                         entry.key != 'localeToSet' &&
                                         entry.key != 'password'
                                         }">
                        <c:param name="${entry.key}" value="${entry.value}"/>
                    </c:if>
                </c:forEach>
                <c:param name="sort" value="all"/>
                <c:param name="page" value="1"/>
            </c:url>
            <a class="dropdown-item"
               href="<c:out value='${url}'/>">
                <fmt:message key="index_jsp.label.sort.all_wo_sorting"/>
            </a>

            <c:url var="url" value="">
                <c:forEach items="${param}" var="entry">
                    <c:if test="${entry.key != 'sort' &&
                                         entry.key != 'page' &&
                                         entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                         entry.key != 'login' &&
                                         entry.key != 'localeToSet' &&
                                         entry.key != 'password'
                                         }">
                        <c:param name="${entry.key}" value="${entry.value}"/>
                    </c:if>
                </c:forEach>
                <c:param name="sort" value="name_asc"/>
                <c:param name="page" value="1"/>
            </c:url>
            <a class="dropdown-item"
               href="<c:out value='${url}'/>">
                <fmt:message key="index_jsp.label.sort.name_asc"/>
            </a>


            <c:url var="url" value="">
                <c:forEach items="${param}" var="entry">
                    <c:if test="${entry.key != 'sort' &&
                                         entry.key != 'page' &&
                                         entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                         entry.key != 'login' &&
                                         entry.key != 'localeToSet' &&
                                         entry.key != 'password'
                                         }">
                        <c:param name="${entry.key}" value="${entry.value}"/>
                    </c:if>
                </c:forEach>
                <c:param name="sort" value="name_desc"/>
                <c:param name="page" value="1"/>
            </c:url>
            <a class="dropdown-item"
               href="<c:out value='${url}'/>">
                <fmt:message key="index_jsp.label.sort.name_desc"/>
            </a>


            <c:url var="url" value="">
                <c:forEach items="${param}" var="entry">
                    <c:if test="${entry.key != 'sort' &&
                                         entry.key != 'page' &&
                                         entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                         entry.key != 'login' &&
                                         entry.key != 'localeToSet' &&
                                         entry.key != 'password'
                                         }">
                        <c:param name="${entry.key}" value="${entry.value}"/>
                    </c:if>
                </c:forEach>
                <c:param name="sort" value="price_asc"/>
                <c:param name="page" value="1"/>
            </c:url>
            <a class="dropdown-item"
               href="<c:out value='${url}'/>">
                <fmt:message key="index_jsp.label.sort.price_asc"/>
            </a>


            <c:url var="url" value="">
                <c:forEach items="${param}" var="entry">
                    <c:if test="${entry.key != 'sort' &&
                                         entry.key != 'page' &&
                                         entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                         entry.key != 'login' &&
                                         entry.key != 'localeToSet' &&
                                         entry.key != 'password'
                                         }">
                        <c:param name="${entry.key}" value="${entry.value}"/>
                    </c:if>
                </c:forEach>
                <c:param name="sort" value="price_desc"/>
                <c:param name="page" value="1"/>
            </c:url>
            <a class="dropdown-item"
               href="<c:out value='${url}'/>">
                <fmt:message key="index_jsp.label.sort.price_desc"/>
            </a>
        </div>
    </div>
                    </div>
<%-- PAGE --%>

                    <div class="mags">
                        <c:choose>
                        <c:when test="${fn:length(magazinesPage) == 0}">
                            <div class="no_magazines">
                                <h3>
                                    <fmt:message key="index_jsp.label.no_magazines"/>
                                </h3>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="magazine" items="${magazinesPage}">
                                <c:url var="check_url" value="">
                                    <c:forEach items="${paramValues}" var="entry">
                                        <c:if test="${
                                        entry.key != 'login' &&
                                         entry.key != 'localeToSet' &&
                                         entry.key != 'password' &&
                                         entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked'
                                         }">
                                            <c:forEach items="${entry.value}" var="value">
                                                <c:param name="${entry.key}" value="${value}"/>
                                            </c:forEach>
                                        </c:if>
                                    </c:forEach>
                                    <c:param name="magazineId" value="${magazine.id}"/>
                                </c:url>
                                <div class="col-xl-3 col-custom col-custom-index<c:forEach var="item" items="${magazineId}">
                            <c:if test="${item eq magazine.id}">col-active</c:if></c:forEach>">
                                    <a href="${check_url}">
                                        <div class="mag_image">
                                            <img
                                                    src="<c:url value="/static/images/${magazine.image}"/>"
                                                    width="100%"/>
                                        </div>
                                        <div class="name">
                                            <p>${magazine.name}</p>
                                        </div>
                                        <div class="price">
                                            <p>${magazine.price}</p>
                                        </div>
                                    </a>
                                </div>
                            </c:forEach>
<%-- PAGINATION--%>
                            </div>
                            <div class="page_bar">
                                <c:if test="${firstPage != null}">
                                    <a style="padding: 0 3px"
                                            <c:url var="first_url" value="">
                                                <c:forEach items="${param}" var="entry">
                                                    <c:if test="${entry.key != 'page' &&
                                                            entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                                             entry.key != 'password' &&
                                                              entry.key != 'login' &&
                                         entry.key != 'localeToSet'
                                                              }">
                                                        <c:param name="${entry.key}" value="${entry.value}"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:param name="page" value="${firstPage}"/>
                                                <c:param name="sort" value="${sort}"/>
                                                <c:param name="filter" value="${filter}"/>
                                            </c:url>


                                       <c:if test="${currentPage == firstPage}">class="disabled"</c:if>
                                       <c:if test="${currentPage != firstPage}">href="<c:out value='${first_url}'/>"
                                    </c:if>
                                    ><<</a>
                                </c:if>
                                <c:if test="${previousPage != null}">
                                    <c:url var="prev_url" value="">
                                        <c:forEach items="${param}" var="entry">
                                            <c:if test="${entry.key != 'page' &&
                                                            entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                                             entry.key != 'password' &&
                                                              entry.key != 'login' &&
                                         entry.key != 'localeToSet'
                                                              }">
                                                <c:param name="${entry.key}" value="${entry.value}"/>
                                            </c:if>
                                        </c:forEach>
                                        <c:param name="page" value="${previousPage}"/>
                                        <c:param name="sort" value="${sort}"/>
                                        <c:param name="filter" value="${filter}"/>
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
                                                    <c:url var="first_url" value="">
                                                        <c:forEach items="${param}" var="entry">
                                                            <c:if test="${entry.key != 'page' &&
                                                            entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                                             entry.key != 'password' &&
                                                              entry.key != 'login' &&
                                         entry.key != 'localeToSet'
                                                              }">
                                                                <c:param name="${entry.key}"
                                                                         value="${entry.value}"/>
                                                            </c:if>
                                                        </c:forEach>
                                                        <c:param name="page" value="1"/>
                                                        <c:param name="sort" value="${sort}"/>
                                                        <c:param name="filter" value="${filter}"/>
                                                    </c:url>
                                               href="<c:out value='${first_url}'/>">1</a>
                                            <span>...</span>
                                        </c:if>

                                        <c:forEach var="page" items="${carriage}">
                                            <c:url var="url" value="">
                                                <c:forEach items="${param}" var="entry">
                                                    <c:if test="${entry.key != 'page' &&
                                                            entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                                             entry.key != 'password' &&
                                                              entry.key != 'login'
                                                              }">
                                                        <c:param name="${entry.key}" value="${entry.value}"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:param name="page" value="${page}"/>
                                                <c:param name="sort" value="${sort}"/>
                                                <c:param name="filter" value="${filter}"/>
                                            </c:url>
                                            <a style="padding: 0 3px"
                                               <c:if test="${currentPage == page}">class="active"</c:if>
                                               href="<c:out value='${url}'/>">${page}</a>
                                        </c:forEach>

                                        <c:if test="${pageLast}">
                                            <span>...</span>
                                            <c:url var="url" value="">
                                                <c:forEach items="${param}" var="entry">
                                                    <c:if test="${entry.key != 'page' &&
                                                            entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                                             entry.key != 'password' &&
                                         entry.key != 'localeToSet' &&
                                                              entry.key != 'login'
                                                    }">
                                                        <c:param name="${entry.key}" value="${entry.value}"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:param name="page" value="${lastPage}"/>
                                                <c:param name="sort" value="${sort}"/>
                                                <c:param name="filter" value="${filter}"/>
                                            </c:url>
                                            <a style="padding: 0 3px"
                                               href="<c:out value='${url}'/>">${lastPage}</a>
                                        </c:if>

                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="page" items="${baseList}">
                                            <c:url var="url" value="">
                                                <c:forEach items="${param}" var="entry">
                                                    <c:if test="${entry.key != 'page' &&
                                                            entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                                             entry.key != 'password' &&
                                         entry.key != 'localeToSet' &&
                                                              entry.key != 'login'
                                                              }">
                                                        <c:param name="${entry.key}" value="${entry.value}"/>
                                                    </c:if>
                                                </c:forEach>
                                                <c:param name="page" value="${page}"/>
                                                <c:param name="sort" value="${sort}"/>
                                                <c:param name="filter" value="${filter}"/>
                                            </c:url>
                                            <a style="padding: 0 3px"
                                               <c:if test="${currentPage == page}">class="active"</c:if>
                                               href="<c:out value='${url}'/>">${page}</a>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>

                                <c:if test="${nextPage != null}">
                                    <c:url var="next_url" value="">
                                        <c:forEach items="${param}" var="entry">
                                            <c:if test="${entry.key != 'page' &&
                                                            entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                                             entry.key != 'password' &&
                                         entry.key != 'localeToSet' &&
                                                              entry.key != 'login'
                                                              }">
                                                <c:param name="${entry.key}" value="${entry.value}"/>
                                            </c:if>
                                        </c:forEach>
                                        <c:param name="page" value="${nextPage}"/>
                                        <c:param name="sort" value="${sort}"/>
                                        <c:param name="filter" value="${filter}"/>
                                    </c:url>
                                    <a style="padding: 0 3px"
                                       <c:if test="${currentPage == lastPage}">class="disabled"</c:if>
                                       <c:if test="${currentPage != lastPage}">href="<c:out value='${next_url}'/>"
                                    </c:if>
                                    >></a>
                                </c:if>
                                <c:if test="${lastPage != null}">
                                    <c:url var="last_url" value="">
                                        <c:forEach items="${param}" var="entry">
                                            <c:if test="${entry.key != 'page' &&
                                                            entry.key != 'magazineId' &&
                                                            entry.key != 'reset_checked' &&
                                                             entry.key != 'password' &&
                                         entry.key != 'localeToSet' &&
                                                              entry.key != 'login'
                                                              }">
                                                <c:param name="${entry.key}" value="${entry.value}"/>
                                            </c:if>
                                        </c:forEach>
                                        <c:param name="page" value="${lastPage}"/>
                                        <c:param name="sort" value="${sort}"/>
                                        <c:param name="filter" value="${filter}"/>
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

<%-- MAGAZINE BY THEMES  --%>


            </div>

            <div class="container container_by_themes">
                <c:forEach var="theme" items="${magazinesByThemes}">
                    <div class="row">
                        <div class="theme-name"><h3>
                            <c:if test="${theme.key.name eq 'Sport'}"><fmt:message key="index_jsp.h3.sport"/></c:if>
                            <c:if test="${theme.key.name eq 'IT world'}"><fmt:message key="index_jsp.h3.it"/></c:if>
                            <c:if test="${theme.key.name eq 'Music'}"><fmt:message key="index_jsp.h3.music"/></c:if>
                            <c:if test="${theme.key.name eq 'Interior'}"><fmt:message key="index_jsp.h3.interior"/></c:if>
                        </h3></div>
                        <c:forEach var="magazine" items="${theme.value}" end="2">
                            <div class="col">
                                <div class="mag_image">
                                    <img src="<c:url value="/static/images/${magazine.image}"/>" width="100%"/>
                                </div>
                                <div class="name">
                                    <p>${magazine.name}</p>
                                </div>
                                <div class="price">
                                    <p>${magazine.price}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>
            </div>
        </div>
    <%@ include file="/WEB-INF/jspf/footer.jspf" %>
</body>
