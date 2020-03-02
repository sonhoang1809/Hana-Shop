<%-- 
    Document   : manage-account
    Created on : Mar 1, 2020, 10:42:37 AM
    Author     : sonho
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Manage Account Hana Shop</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700">
        <!-- Favicon -->
        <link rel="shortcut icon" href="assets/images/logo/favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="assets/css/animate-3.7.0.css">
        <link rel="stylesheet" href="assets/css/font-awesome-4.7.0.min.css">
        <link rel="stylesheet" href="assets/css/bootstrap-4.1.3.min.css">
        <link rel="stylesheet" href="assets/css/owl-carousel.min.css">
        <link rel="stylesheet" href="assets/css/jquery.datetimepicker.min.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <link rel="stylesheet" href="style/pop-up-message-out-of-stock.css"/>
        <link rel="stylesheet" href="style/style-avt-user.css">
        <link rel="stylesheet" href="assets/css/nice-select.css" type="text/css">
        <link rel="stylesheet" href="style/templatemo-style.css">
    </head>
    <body>
        <%@include file="header.jsp" %>
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
        <div class="" id="home" style="m">
            <nav class="navbar navbar-expand-xl">
                <div class="container h-100">
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav mx-auto h-100">
                            <li class="nav-item">
                                <a class="nav-link" href="manage-hanashop.jsp">
                                    <i class="fas fa-tachometer-alt"></i>
                                    <h5>Dashboard</h5>
                                    <span class="sr-only">(current)</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <form action="manage-food.jsp" method="POST">
                                    <input type="hidden" name="kindIDAd" value="${param.kindIDAd}">
                                    <input type="hidden" name="categoryIDAd" value="${param.categoryIDAd}">
                                    <a class="nav-link" href="#0" onclick="$(this).closest('form').submit();">
                                        <i class="fas fa-shopping-cart"></i>
                                        <h5>Foods</h5>
                                    </a>
                                </form>
                            </li>
                            <li class="nav-item">
                               <form class="nav-link active" action="manage-account.jsp" method="POST">
                                    <input type="hidden" name="kindIDAd" value="${param.kindIDAd}">
                                    <input type="hidden" name="categoryIDAd" value="${param.categoryIDAd}">
                                    <a  href="#0" onclick="$(this).closest('form').submit();">
                                        <i class="far fa-user"></i>
                                        <h5>Accounts</h5>
                                    </a>
                                </form>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <div class="container">                            
                <div class="container mt-5">
                    <div class="row tm-content-row">
                        <div class="col-12 tm-block-col">
                            <div class="tm-bg-primary-dark tm-block tm-block-h-auto">
                                <h4 class="tm-block-title">List of Accounts</h4>
                                <form action="manage-account.jsp" method="POST">
                                    <select class="custom-select" name="roleID" onchange="$(this).closest('form').submit();">
                                        <option value="">Select account</option>
                                        <c:forEach items="${requestScope.ListRole}" var="x">
                                            <option value="${x.roleID}"
                                                    <c:if test="${not empty param.roleID}">
                                                        <c:if test="${param.roleID == x.roleID}">
                                                            selected
                                                        </c:if>
                                                    </c:if>    
                                                    >${x.roleDetail}</option>
                                        </c:forEach>
                                    </select>
                                </form>
                                <div style="height: 50px;"></div>
                            </div>
                        </div>
                    </div>
                    <!-- row -->
                    <div class="col-12 tm-block-col">
                        <div class="tm-bg-primary-dark tm-block tm-block-taller tm-block-scroll">
                            <h4 class="tm-block-title">User Account List</h4>
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th scope="col">STT</th>
                                        <th scope="col">User ID.</th>
                                        <th scope="col">Email</th>
                                        <th scope="col">Avatar</th>
                                        <th scope="col">User Name</th>
                                        <th scope="col">Role</th>
                                        <th scope="col">Date Create</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${requestScope.listUser}" var="x" varStatus="Count">
                                        <tr style="cursor: pointer;" onclick="document.getElementById('${x.userID}').submit();">
                                            <th scope="row"><b>${Count.count}</b></th>
                                            <th scope="row"><b>${x.userID}</b></th>
                                            <td>
                                                ${x.email}
                                            </td>
                                            <td>
                                                <img style="width: 40px; height: 40px;" src="${x.avatar}" alt="">
                                            </td>
                                            <td><b>${x.name}</b></td>
                                            <td>
                                                <b>
                                                    <c:if test="${x.roleID == 'AD'}">
                                                        Administrator
                                                    </c:if>
                                                    <c:if test="${x.roleID == 'US'}">
                                                        User
                                                    </c:if>
                                                </b>
                                            </td>
                                            <td>${x.dateCreate}</td>
                                            <td style="display: none;">
                                                <form action="LoadUserDetailsController" method="POST" id="${x.userID}">
                                                    <input type="hidden" name="date" value="${param.date}">
                                                    <input type="hidden" name="userID" value="${x.userID}">
                                                    <input type="submit" class="genric-btn warning" 
                                                           name="action" value="View Details">
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="footer.html" %>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="js/jquery-3.3.1.min.js"></script>
        <!-- https://jquery.com/download/ -->
        <script src="js/moment.min.js"></script>
        <!-- https://momentjs.com/ -->
        <script src="js/Chart.min.js"></script>
        <!-- http://www.chartjs.org/docs/latest/ -->
        <script src="js/bootstrap.min.js"></script>
        <!-- https://getbootstrap.com/ -->
        <script src="js/tooplate-scripts.js"></script>
        <script src="assets/js/vendor/jquery.datetimepicker.full.min.js"></script>
        <script src="assets/js/vendor/jquery.nice-select.min.js"></script>
    </body>
</html>
