<%-- 
    Document   : index
    Created on : Jan 31, 2020, 8:20:52 PM
    Author     : sonho
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!-- Page Title -->
        <title>Hana Shop</title>
        <!-- Favicon -->
        <link rel="shortcut icon" href="assets/images/logo/favicon.png" type="image/x-icon">

    </head>
    <body>
        <%@include file="header.jsp" %>
        <c:if test="${empty sessionScope.USERDTO || sessionScope.USERDTO.roleID == 'US'}">
            <!-- Banner Area Starts -->
            <section class="banner-area text-center">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12">
                            <h6>the most interesting food in the world</h6>
                            <h1>Discover the <span class="prime-color">flavors</span><br>  
                                <span class="style-change">of <span class="prime-color">Hana</span> Shop</span></h1>
                        </div>
                    </div>
                </div>
            </section>
        </c:if>
        <c:if test="${sessionScope.USERDTO.roleID == 'AD'}">
            <!-- Banner Area Starts -->
            <section class="banner-area banner-area2 blog-page text-center">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12">
                            <h1><i>Manage Hana Shop</i></h1>
                        </div>
                    </div>
                </div>
            </section>
            <!-- Banner Area End -->
        </c:if>
        <!-- Banner Area End -->
        <%@include file="search-components.jsp" %>

        <%@include file="container-index.jsp" %>

        <%@include file="footer.html" %>


    </body>
</html>
