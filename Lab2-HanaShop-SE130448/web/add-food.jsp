<%-- 
    Document   : add-food
    Created on : Feb 27, 2020, 10:24:41 PM
    Author     : sonho
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Food Page</title>
        <!-- Favicon -->
        <link rel="shortcut icon" href="assets/images/logo/favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700"/>
        <!-- https://fonts.google.com/specimen/Roboto -->
        <link rel="stylesheet" href="style/fontawesome.min.css" />
        <!-- http://api.jqueryui.com/datepicker/ -->
        <!--    <link rel="stylesheet" href="css/bootstrap.min.css" /> -->

        <link rel="stylesheet" href="style/bootstrap.min.css" />
        <link rel="stylesheet" href="style/style-add-food.css">
        <link rel="stylesheet" href="assets/css/nice-select.css" type="text/css">
        <link rel="stylesheet" href="style/upload-pic-food.css" type="text/css">
        <link rel="stylesheet" href="style/pop-up-message-out-of-stock.css"/>
        <link rel="stylesheet" href="assets/css/style.css">
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
        <!--Add food area Start-->
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
                                <form class="nav-link active" action="manage-food.jsp" method="POST">
                                    <input type="hidden" name="kindIDAd" value="${param.kindIDAd}">
                                    <input type="hidden" name="categoryIDAd" value="${param.categoryIDAd}">
                                    <a href="#0" onclick="$(this).closest('form').submit();">
                                        <i class="fas fa-shopping-cart"></i>
                                        <h5>Foods</h5>
                                    </a>
                                </form>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="accounts.html">
                                    <i class="far fa-user"></i>
                                    <h5>Accounts</h5>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <div class="container tm-mt-big tm-mb-big">
                <div class="row">
                    <div class="col-xl-9 col-lg-10 col-md-12 col-sm-12 mx-auto">
                        <div class="tm-bg-primary-dark tm-block tm-block-h-auto">
                            <div class="row">
                                <div class="col-12">
                                    <h2 class="tm-block-title d-inline-block">Add Product</h2>
                                </div>
                            </div>
                            <form action="AddNewFoodController" enctype="multipart/form-data" method="POST">
                                <div class="row tm-edit-product-row">
                                    <div class="col-xl-6 col-lg-6 col-md-12">
                                        <div class="tm-edit-product-form">
                                            <div class="form-group mb-3">
                                                <label for="name">Food Name</label>
                                                <input id="name" name="nameFood" type="text" class="form-control validate" required/>
                                            </div>
                                            <div class="form-group mb-3">
                                                <label for="description">Description</label>
                                                <textarea name="description" class="form-control validate" rows="3" required></textarea>
                                            </div>
                                            <div class="form-group mb-3">
                                                <label for="category">Category</label>
                                                <select class="custom-select tm-select-accounts" id="category" name="categoryID" required>
                                                    <c:forEach items="${requestScope.LISTCATEGORY}" var="x">
                                                        <option value="${x.categoryID}">${x.categoryName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="row">
                                                <div class="form-group mb-3 col-xs-12 col-sm-6">
                                                    <label for="stock">Price Unit ($)</label>
                                                    <input id="stock" name="price" type="number" min="1" step="0.1" class="form-control validate" required />
                                                </div>
                                                <div class="form-group mb-3 col-xs-12 col-sm-6">
                                                    <label for="stock">Quantity In Stock </label>
                                                    <input id="stock" name="quantity" type="number" min="1" step="1" class="form-control validate" required />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-6 col-lg-6 col-md-12 mx-auto mb-4">
                                        <div class="uploadWrapper">
                                            <div id="imageUploadForm" class="imageUploadForm">
                                                <span class="helpText" id="helpText">Upload an image</span>
                                                <input type="file" id="file" class="uploadButton" required="" name="imgFood" accept="image/*" >
                                                <div id="uploadedImg" class="uploadedImg">
                                                    <span class="unveil"></span>
                                                </div>
                                                <span class="pickFile">
                                                    <a href="#" class="pickFileButton">Pick file</a>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <input type="hidden" name="kindIDAd" value="${param.kindIDAd}">
                                        <input type="hidden" name="categoryIDAd" value="${param.categoryIDAd}">
                                        <button type="submit" style="font-size: 20px;" class="genric-btn success-border btn btn-block text-uppercase">Add Product Now</button>
                                    </div>

                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--Add food area End-->
        <div class="message-popup" role="alert">
            <div class="message-popup-container">
                <c:if test="${not empty requestScope.MESSAGE}">
                    <p class="message-display">${requestScope.MESSAGE}</p>
                </c:if>
                <a href="#0" class="message-buttons" style="font-size: 20px; color: #000;">OK</a>
                <a href="#0" class="message-popup-close img-replace">Close</a>
            </div> <!-- message-popup-container -->
        </div> <!-- message-popup -->
        <c:if test="${not empty requestScope.MESSAGE}">
            <script>
                $(".message-popup").addClass("is-visible");
            </script>
        </c:if>
        <%@include file="footer.html" %>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="js/add-food.js"></script>
        <script src="js/pop-up-message-out-of-stock.js"></script>
        <script src="assets/js/vendor/bootstrap-4.1.3.min.js"></script>

        <script src="assets/js/vendor/owl-carousel.min.js"></script>

        <script src="assets/js/main.js"></script>

    </body>
</html>
