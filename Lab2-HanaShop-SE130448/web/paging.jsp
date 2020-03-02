<%-- 
    Document   : paging
    Created on : Feb 8, 2020, 10:44:26 PM
    Author     : sonho
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Paging</title>
        <link rel="stylesheet" href="style/style-paging.css"/>
    </head>
    <body>
        <c:if test="${requestScope.NUMBEROFPAGE>1}">
            <div class="wrapper">
                <nav>
                    <ul class="pager">
                        <c:if test="${requestScope.PAGESHOW > 1}">
                            <li class="pager__item pager__item--prev">
                                <form action="SearchController" method="POST">
                                    <input type="hidden" name="pageShow" value="${requestScope.PAGESHOW-1}">
                                    <input type="hidden" name="priceLower" value="${param.priceLower}">
                                    <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                    <input type="hidden" name="kindID" value="${param.kindID}">
                                    <input type="hidden" name="categoryID" value="${param.categoryID}">
                                    <input type="hidden" name="searchName" value="${param.searchName}">
                                    <a class="pager__link" onclick="$(this).closest('form').submit();" href="#0">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="8" height="12" viewbox="0 0 8 12">
                                        <g fill="none" fill-rule="evenodd">
                                        <path fill="#33313C" d="M7.41 1.41L6 0 0 6l6 6 1.41-1.41L2.83 6z"></path>
                                        </g>
                                        </svg>
                                    </a>
                                </form>
                            </li>
                        </c:if>

                        <c:forEach begin="1" end="${requestScope.NUMBEROFPAGE}" var="x">
                            <c:if test="${x == requestScope.PAGESHOW}">
                                <li class="pager__item active" onclick="activePage(this.document)">
                                    <form action="SearchController" method="POST">
                                        <input type="hidden" name="pageShow" value="${x}">
                                        <input type="hidden" name="priceLower" value="${param.priceLower}">
                                        <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                        <input type="hidden" name="kindID" value="${param.kindID}">
                                        <input type="hidden" name="categoryID" value="${param.categoryID}">
                                        <input type="hidden" name="searchName" value="${param.searchName}">
                                        <a class="pager__link" onclick="$(this).closest('form').submit();" href="#0">
                                            ${x}
                                        </a>
                                    </form>
                                </li>
                            </c:if>
                            <c:if test="${x != requestScope.PAGESHOW}">
                                <li class="pager__item" onclick="activePage(this.document)">
                                    <form action="SearchController" method="POST">
                                        <input type="hidden" name="pageShow" value="${x}">
                                        <input type="hidden" name="priceLower" value="${param.priceLower}">
                                        <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                        <input type="hidden" name="kindID" value="${param.kindID}">
                                        <input type="hidden" name="categoryID" value="${param.categoryID}">
                                        <input type="hidden" name="searchName" value="${param.searchName}">
                                        <a class="pager__link" onclick="$(this).closest('form').submit();" href="#0">
                                            ${x}
                                        </a>
                                    </form>
                                </li>
                            </c:if>
                        </c:forEach>

                        <c:if test="${requestScope.PAGESHOW < requestScope.NUMBEROFPAGE}">
                            <li class="pager__item pager__item--next">
                                <form action="SearchController" method="POST">
                                    <input type="hidden" name="pageShow" value="${requestScope.PAGESHOW+1}">
                                    <input type="hidden" name="priceLower" value="${param.priceLower}">
                                    <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                    <input type="hidden" name="kindID" value="${param.kindID}">
                                    <input type="hidden" name="categoryID" value="${param.categoryID}">
                                    <input type="hidden" name="searchName" value="${param.searchName}">
                                    <a class="pager__link" onclick="$(this).closest('form').submit();" href="#0">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="8" height="12" viewbox="0 0 8 12">
                                        <g fill="none" fill-rule="evenodd">
                                        <path fill="#33313C" d="M7.41 1.41L6 0 0 6l6 6 1.41-1.41L2.83 6z"></path>
                                        </g>
                                        </svg>
                                    </a>
                                </form>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </c:if>
    </body>
</html>
