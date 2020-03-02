<%-- 
    Document   : manage-product
    Created on : Feb 29, 2020, 10:20:37 PM
    Author     : sonho
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Manage Food Hana Shop</title>
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
    <body id="reportsPage">
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
                                <form action="manage-hanashop.jsp" method="POST">
                                    <input type="hidden" name="date" value="${param.date}">
                                    <a class="nav-link" href="manage-hanashop.jsp">
                                        <i class="fas fa-tachometer-alt"></i>
                                        <h5>Dashboard</h5>
                                    </a>
                                </form>
                            </li>
                            <li class="nav-item">
                                <form class="nav-link active" action="manage-food.jsp" method="POST">
                                    <input type="hidden" name="kindIDAd" value="${param.kindIDAd}">
                                    <input type="hidden" name="categoryIDAd" value="${param.categoryIDAd}">
                                    <a href="#0" onclick="$(this).closest('form').submit();">
                                        <i class="fas fa-shopping-cart"></i>
                                        <h5>Foods</h5>
                                        <span class="sr-only">(current)</span>
                                    </a>
                                </form>
                            </li>
                            <li class="nav-item">
                                <form  action="manage-account.jsp" method="POST">
                                    <input type="hidden" name="kindIDAd" value="${param.kindIDAd}">
                                    <input type="hidden" name="categoryIDAd" value="${param.categoryIDAd}">
                                    <a class="nav-link" href="#0" onclick="$(this).closest('form').submit();">
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
                                <h4 class="tm-block-title">List Kind</h4>
                                <div class="col-lg-8 offset-lg-2">
                                    <form action="manage-food.jsp" method="POST">
                                        <select class="custom-select" name="kindIDAd" onchange="this.form.submit();">
                                            <c:forEach items="${requestScope.ListKind}" var="x">
                                                <option value="${x.kindID}"
                                                        <c:if test="${not empty param.kindIDAd}">
                                                            <c:if test="${param.kindIDAd == x.kindID}">
                                                                selected
                                                            </c:if>
                                                        </c:if>             
                                                        >${x.kindFood}
                                                </option>
                                            </c:forEach> 
                                        </select>
                                    </form>
                                    <div style="height: 50px;"></div>
                                </div>
                            </div>

                        </div>
                    </div>
                    <div class="row tm-content-row">
                        <div class="col-sm-12 col-md-12 col-lg-8 col-xl-8 tm-block-col">
                            <div class="tm-bg-primary-dark tm-block tm-block-products">
                                <h4 class="tm-block-title">Product List</h4>
                                <div class="tm-product-table-container">
                                    <table class="table table-hover tm-table-small tm-product-table">
                                        <thead>
                                            <tr>
                                                <th scope="col">STT</th>
                                                <th scope="col">Food Name</th>
                                                <th scope="col">Status</th>
                                                <th scope="col">Price</th>
                                                <th scope="col">Quantity</th>
                                                <th scope="col">Create date</th>
                                                <th scope="col">Update date</th>
                                                <th scope="col">Update User</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${requestScope.listFood}" var="x" varStatus="Count">
                                                <tr style="cursor: pointer;" onclick="document.getElementById('${x.idFood}').submit();">
                                                    <td>${Count.count}</td>
                                                    <td>${x.nameFood}</td>
                                                    <td>
                                                        <c:if test="${x.statusCode==0}">
                                                            <div class="tm-status-circle pending">
                                                            </div> Out of stock !!
                                                        </c:if>
                                                        <c:if test="${x.statusCode==1}">
                                                            <div class="tm-status-circle moving">
                                                            </div> Stocking
                                                        </c:if>
                                                        <c:if test="${x.statusCode==2}">
                                                            <div class="tm-status-circle cancelled">
                                                            </div> Stop selling !!
                                                        </c:if>
                                                    </td>
                                                    <td>${x.price}$</td>
                                                    <td>${x.quantity}</td>
                                                    <td>${x.createDate}</td>
                                                    <td>${x.updateDate}</td>
                                                    <td>${x.updateUser}</td>
                                                    <td style="display: none;">
                                                        <form style="display: none;" action="LoadFoodDetailsController" method="POST" id="${x.idFood}">
                                                            <input type="hidden" name="idFood" value="${x.idFood}">
                                                            <input type="hidden" name="kindIDAd" value="${param.kindIDAd}">
                                                            <input type="hidden" name="categoryIDAd" value="${param.categoryIDAd}">
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!-- table container -->
                                <div class="col-12">
                                    <form  action="add-food.jsp" method="POST">
                                        <input type="hidden" name="kindIDAd" value="${param.kindIDAd}">
                                        <input type="hidden" name="categoryIDAd" value="${param.categoryIDAd}">
                                        <button type="submit" style="font-size: 20px;" class="genric-btn warning btn btn-block text-uppercase">Add Product Now</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 col-md-12 col-lg-4 col-xl-4 tm-block-col">
                            <div class="tm-bg-primary-dark tm-block tm-block-product-categories">
                                <h4 class="tm-block-title">Product Categories</h4>
                                <div class="tm-product-table-container">
                                    <table class="table table-hover tm-table-small tm-product-table">
                                        <thead>
                                            <tr>
                                                <th scope="col">STT</th>
                                                <th scope="col">CATEGORY NAME</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${requestScope.listCate}" var="x" varStatus="Count">
                                                <tr style="cursor: pointer;" onclick="document.getElementById('${x.categoryID}').submit();">
                                                    <td>${Count.count}</td>
                                                    <td>
                                                        ${x.categoryName}
                                                    </td>
                                                    <td style="display: none;">
                                                        <form style="display: none;" action="manage-food.jsp" method="POST" id="${x.categoryID}">
                                                            <input type="hidden" name="categoryIDAd" value="${x.categoryID}">
                                                            <input type="hidden" name="kindIDAd" value="${param.kindIDAd}">
                                                        </form>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!-- table container -->
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="footer.html" %>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="assets/js/vendor/jquery.datetimepicker.full.min.js"></script>
        <script src="assets/js/vendor/jquery.nice-select.min.js"></script>
        <script src="js/jquery-3.3.1.min.js"></script>
        <script src="js/moment.min.js"></script>
        <script src="js/Chart.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/tooplate-scripts.js"></script>
    </body>
</html>
