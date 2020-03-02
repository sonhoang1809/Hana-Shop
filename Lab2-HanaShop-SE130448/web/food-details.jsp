<%-- 
    Document   : food-details
    Created on : Feb 13, 2020, 2:18:59 PM
    Author     : sonho
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Food Details Page</title>
        <!-- Favicon -->
        <link rel="shortcut icon" href="assets/images/logo/favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="style/style-food-details.css"/>
        <link rel="stylesheet" href="style/pop-up-message-out-of-stock.css"/>
        <link rel="stylesheet" href="style/pop-up-confirm.css"/>
        <link rel="stylesheet" href="assets/css/nice-select.css" type="text/css">
        <link rel="stylesheet" href="style/templatemo-style.css">
        <link rel="stylesheet" href="style/upload-pic-food.css" type="text/css">
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
        <!-- Food Details Admin Starts -->
        <c:if test="${sessionScope.USERDTO.roleID == 'AD'}">
            <nav class="navbar navbar-expand-xl">
                <div class="container h-100">
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav mx-auto h-100">
                            <li class="nav-item">
                                <a class="nav-link" href="manage-hanashop.jsp">
                                    <i class="fas fa-tachometer-alt"></i>
                                    <h5>Dashboard</h5>
                                </a>
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
            <div class="container tm-mt-big tm-mb-big">
                <div class="row">
                    <div class="col-xl-9 col-lg-10 col-md-12 col-sm-12 mx-auto">
                        <div class="tm-bg-primary-dark tm-block tm-block-h-auto">
                            <div class="row">
                                <div class="col-12">
                                    <h2 class="tm-block-title d-inline-block">Food Details</h2>
                                </div>
                            </div>
                            <form action="UpdateFoodController"  enctype="multipart/form-data" method="POST">
                                <div class="row tm-edit-product-row">
                                    <div class="col-xl-6 col-lg-6 col-md-12">
                                        <div class="tm-edit-product-form">
                                            <div class="form-group mb-3">
                                                <label for="name">Food Name</label>
                                                <input id="name" name="nameFood" type="text" class="form-control validate" required
                                                       value="${requestScope.FOODDTO.nameFood}" />
                                            </div>
                                            <div class="form-group mb-3" style="margin-top: 10px;">
                                                <label for="description">Description</label>
                                                <textarea name="description" class="form-control validate" rows="3" required>${requestScope.FOODDTO.description}
                                                </textarea>
                                            </div>
                                            <div class="form-group mb-3">
                                                <label for="category">Category</label>
                                                <select class="custom-select tm-select-accounts" id="category" name="categoryID" required>
                                                    <c:forEach items="${requestScope.LISTCATEGORIES}" var="x">
                                                        <option value="${x.categoryID}"
                                                                <c:if test="${requestScope.FOODDTO.categoryID == x.categoryID}">
                                                                    selected
                                                                </c:if>
                                                                >${x.categoryName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group mb-3" style="margin-top: 10px;">
                                                <label for="Status" style="margin-top: 30px;">Status</label>
                                                <select class="custom-select tm-select-accounts" id="category" name="statusCode" required>
                                                    <c:forEach items="${requestScope.LISTSTATUSFOOD}" var="x">
                                                        <option value="${x.statusCode}"
                                                                <c:if test="${requestScope.FOODDTO.quantity > 0 }">
                                                                    <c:if test="${x.statusCode == 0}">
                                                                        disabled 
                                                                    </c:if>
                                                                </c:if>
                                                                <c:if test="${requestScope.FOODDTO.statusCode == x.statusCode}">
                                                                    selected
                                                                </c:if>
                                                                >${x.status}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="row" >
                                                <div class="form-group mb-3 col-xs-12 col-sm-6" style="margin-top: 30px;">
                                                    <label for="Price Unit ($)">Price Unit ($)</label>
                                                    <input id="stock" name="price" type="number" min="1" step="0.1" class="form-control validate"
                                                           required  value="${requestScope.FOODDTO.price}"/>
                                                </div>
                                                <div class="form-group mb-3 col-xs-12 col-sm-6" style="margin-top: 30px;">
                                                    <label for="Quantity">Quantity In Stock </label>
                                                    <input id="stock" name="quantity" type="number" min="0" step="1" class="form-control validate" required 
                                                           value="${requestScope.FOODDTO.quantity}"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-xl-6 col-lg-6 col-md-12 mx-auto mb-4">
                                        <div class="uploadWrapper">
                                            <div id="imageUploadForm" class="imageUploadForm">
                                                <span class="helpText" id="helpText">Upload an image</span>
                                                <img style="height: 350px; width: 320px;" src="${requestScope.FOODDTO.imgFood}">
                                                <input type="hidden" name="oldImage" value="${requestScope.FOODDTO.imgFood}">
                                                <input type="file" id="fileUpdate" 
                                                       class="uploadButton" name="imgFood" 
                                                       accept="image/*" >
                                                <div id="uploadedImg" class="uploadedImg" >
                                                    <span class="unveil" ></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row" style="margin-top: 10px;">
                                            <div class="form-group mb-3 col-xs-12 col-sm-6" >
                                                <label for="Create Date">Create Date</label>
                                                <input id="stock" name="createDate" type="text" class="form-control validate" disabled 
                                                       required  value="${requestScope.FOODDTO.createDate}"/>
                                            </div>
                                            <div class="form-group mb-3 col-xs-12 col-sm-6">
                                                <label for="Update Date">Update Date</label>
                                                <input id="stock" name="updateDate" type="text" class="form-control validate" disabled 
                                                       required  value="${requestScope.FOODDTO.updateDate}"/>
                                            </div>
                                            <div class="form-group mb-3 col-xs-12 col-sm-6">
                                                <label for="Update User">Update User</label>
                                                <input id="stock" type="text" class="form-control validate" disabled 
                                                       value="${sessionScope.USERDTO.name}"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="cd-popup" role="alert">
                                            <div class="cd-popup-container">
                                                <p>Are you sure you want to update this food ?</p>
                                                <ul class="cd-buttons">
                                                    <li><a href="#0" onclick="$(this).closest('form').submit();">Yes</a></li>
                                                    <li><a href="#0" class="cd-no" >No</a></li>
                                                </ul>
                                                <a href="#0" class="cd-popup-close img-replace">Close</a>
                                            </div> <!-- cd-popup-container -->
                                        </div> <!-- cd-popup -->
                                        <input type="hidden" name="idFood" value="${requestScope.FOODDTO.idFood}">
                                        <input type="hidden" name="kindID" value="${param.kindID}">
                                        <input type="hidden" name="updateUser" value="${sessionScope.USERDTO.name}">
                                        <input type="hidden" name="categoryID" value="${param.categoryID}">
                                        <input type="hidden" name="priceLower" value="${param.priceLower}">
                                        <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                        <input type="hidden" name="searchName" value="${param.searchName}">
                                        <input type="hidden" name="quantity" id="${requestScope.FOODDTO.idFood}" value="1">
                                        <button type="submit" style="font-size: 20px;" id="update-food" class="genric-btn success-border btn btn-block text-uppercase">Update Food</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </c:if>
        <!-- Food Details Admin End -->
        <!-- Food Details Use Starts -->
        <c:if test="${sessionScope.USERDTO.roleID == 'US' || empty sessionScope.USERDTO}">
            <section class="welcome-area section-padding2">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-md-6 align-self-center">
                            <div class="welcome-img">
                                <img src="${requestScope.FOODDTO.imgFood}" class="img-fluid" alt=""
                                     style=" padding-left: 120px; width: 620px; height: 500px;">
                            </div>
                        </div>
                        <div class="col-md-6 align-self-center">
                            <div class="welcome-text mt-5 mt-md-0">
                                <h3><span class="style-change">${requestScope.FOODDTO.nameFood} </span></h3>
                                <h5>Kind: ${requestScope.FOODDTO.kind}</h5>
                                <h5>Category: ${requestScope.FOODDTO.categoryName}</h5>
                                <h5>Create Date: ${requestScope.FOODDTO.createDate}</h5>
                                <h5>Status: ${requestScope.FOODDTO.status}</h5>
                                <p class="pt-3">${requestScope.FOODDTO.description}</p>
                                <h5>Price: 
                                    <span class="price">${requestScope.FOODDTO.price}$</span>
                                </h5>
                                <form name="form-add" action="AddAFoodToCartController" method="POST">
                                    <h5>Quantity:</h5>
                                    <input type="hidden" class="maxQuantity" id="maxQuantity" value="${requestScope.FOODDTO.maxQuantity}">
                                    <span class="qt-minus">-</span>
                                    <span class="qt" style="font-size: 20px;" id="${requestScope.FOODDTO.idFood}">1</span>
                                    <span class="qt-plus">+</span>
                                    <div class="cd-popup" role="alert">
                                        <div class="cd-popup-container">
                                            <p>Are you sure you want to add this food to your cart?</p>
                                            <ul class="cd-buttons">
                                                <li><a href="#0" onclick="$(this).closest('form').submit();">Yes</a></li>
                                                <li><a href="#0" class="cd-no" >No</a></li>
                                            </ul>
                                            <a href="#0" class="cd-popup-close img-replace">Close</a>
                                        </div> <!-- cd-popup-container -->
                                    </div> <!-- cd-popup -->
                                    <br><br><br>
                                    <input type="hidden" name="idFood" value="${requestScope.FOODDTO.idFood}">
                                    <input type="hidden" name="kindID" value="${param.kindID}">
                                    <input type="hidden" name="categoryID" value="${param.categoryID}">
                                    <input type="hidden" name="priceLower" value="${param.priceLower}">
                                    <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                    <input type="hidden" name="searchName" value="${param.searchName}">
                                    <input type="hidden" name="quantity" id="${requestScope.FOODDTO.idFood}" value="1">
                                    <input type="submit" id="add-to-cart" class="template-btn mt-3" onclick="checkLoginYet();"value="Add to cart">
                                </form>
                                <BR>
                                <br>
                                <form action="SearchController" method="POST">
                                    <input type="hidden" name="kindID" value="${param.kindID}">
                                    <input type="hidden" name="categoryID" value="${param.categoryID}">
                                    <input type="hidden" name="priceLower" value="${param.priceLower}">
                                    <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                    <input type="hidden" name="searchName" value="${param.searchName}">
                                    <a href="#0" onclick="$(this).closest('form').submit();">Continue Shopping</a>
                                </form>
                            </div>
                        </div>

                    </div>
                </div>
            </section>
        </c:if>
        <!-- Food Details User End -->

        <c:if test="${sessionScope.USERDTO.roleID == 'US' || empty sessionScope.USERDTO}">
            <!-- Recommend Starts -->
            <div class="deshes-area section-padding">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="section-top2 text-center">
                                <h3>You <span>May</span> Also Like</h3>
                            </div>
                        </div>
                    </div>


                    <div class="row">
                        <div class="col-lg-5 col-md-6 align-self-center">
                            <h1>01.</h1>
                            <div class="deshes-text">
                                <h3><span>${requestScope.FOODREC1.nameFood}</span></h3>
                                <p class="pt-3">${requestScope.FOODREC1.description}</p>
                                <span class="style-change">$${requestScope.FOODREC1.price}</span>
                                <div style="display: inline-block; font-size: 17px;">
                                    <form style="float: left;" action="LoadFoodDetailsController" method="POST">
                                        <input type="hidden" name="idFood" value="${requestScope.FOODREC1.idFood}">
                                        <input type="hidden" name="kindID" value="${param.kindID}">
                                        <input type="hidden" name="categoryID" value="${param.categoryID}">
                                        <input type="hidden" name="priceLower" value="${param.priceLower}">
                                        <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                        <input type="hidden" name="searchName" value="${param.searchName}">
                                        <input type="submit" class="genric-btn primary" name="action" value="View Details">
                                    </form>
                                    <c:if test="${sessionScope.USERDTO.roleID == 'US' || empty sessionScope.USERDTO}">
                                        <form action="AddAFoodToCartController" style="float: right;margin-left: 10px;" method="POST">
                                            <div class="cd-popup" role="alert">
                                                <div class="cd-popup-container">
                                                    <p>Are you sure you want to add this food to your cart?</p>
                                                    <ul class="cd-buttons">
                                                        <li><a href="#0" onclick="$(this).closest('form').submit();">Yes</a></li>
                                                        <li><a href="#0" class="cd-no" >No</a></li>
                                                    </ul>
                                                    <a href="#0" class="cd-popup-close img-replace">Close</a>
                                                </div> <!-- cd-popup-container -->
                                            </div> <!-- cd-popup -->
                                            <input type="hidden" name="idFood" value="${requestScope.FOODREC1.idFood}">
                                            <input type="hidden" name="kindID" value="${param.kindID}">
                                            <input type="hidden" name="categoryID" value="${param.categoryID}">
                                            <input type="hidden" name="priceLower" value="${param.priceLower}">
                                            <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                            <input type="hidden" name="searchName" value="${param.searchName}">
                                            <input type="submit" id="add-to-cart" class="genric-btn danger" onclick="checkLoginYet();" name="action" value="Add to cart">
                                        </form>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-5 offset-lg-2 col-md-6 align-self-center mt-4 mt-md-0">
                            <img src="${requestScope.FOODREC1.imgFood}" alt="" class="img-fluid">
                        </div>
                    </div>
                    <div class="row mt-5">
                        <div class="col-lg-5 col-md-6 align-self-center order-2 order-md-1 mt-4 mt-md-0">
                            <img src="${requestScope.FOODREC2.imgFood}" alt="" class="img-fluid">
                        </div>
                        <div class="col-lg-5 offset-lg-2 col-md-6 align-self-center order-1 order-md-2">
                            <h1>02.</h1>
                            <div class="deshes-text">
                                <h3><span>${requestScope.FOODREC2.nameFood}</span><br></h3>
                                <p class="pt-3">${requestScope.FOODREC2.description}</p>
                                <span class="style-change">$${requestScope.FOODREC2.price}</span>
                                <div style="display: inline-block; font-size: 17px;">
                                    <form style="float: left;" action="LoadFoodDetailsController" method="POST">
                                        <input type="hidden" name="idFood" value="${requestScope.FOODREC2.idFood}">
                                        <input type="hidden" name="kindID" value="${param.kindID}">
                                        <input type="hidden" name="categoryID" value="${param.categoryID}">
                                        <input type="hidden" name="priceLower" value="${param.priceLower}">
                                        <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                        <input type="hidden" name="searchName" value="${param.searchName}">
                                        <input type="submit" class="genric-btn primary" name="action" value="View Details">
                                    </form>
                                    <c:if test="${sessionScope.USERDTO.roleID == 'US' || empty sessionScope.USERDTO}">
                                        <form action="AddAFoodToCartController" style="float: right;margin-left: 10px;" method="POST">
                                            <div class="cd-popup" role="alert">
                                                <div class="cd-popup-container">
                                                    <p>Are you sure you want to add this food to your cart?</p>
                                                    <ul class="cd-buttons">
                                                        <li><a href="#0" onclick="$(this).closest('form').submit();">Yes</a></li>
                                                        <li><a href="#0" class="cd-no" >No</a></li>
                                                    </ul>
                                                    <a href="#0" class="cd-popup-close img-replace">Close</a>
                                                </div> <!-- cd-popup-container -->
                                            </div> <!-- cd-popup -->
                                            <input type="hidden" name="idFood" value="${requestScope.FOODREC2.idFood}">
                                            <input type="hidden" name="kindID" value="${param.kindID}">
                                            <input type="hidden" name="categoryID" value="${param.categoryID}">
                                            <input type="hidden" name="priceLower" value="${param.priceLower}">
                                            <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                            <input type="hidden" name="searchName" value="${param.searchName}">
                                            <input type="submit" id="add-to-cart" class="genric-btn danger" onclick="checkLoginYet();" name="action" value="Add to cart">
                                        </form>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Recommend End -->
        </c:if>
        <!-- Products that are often purchased together -->
        <section class="testimonial-area section-padding4">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="section-top2 text-center">
                            <h3>Often <span>purchased</span> with</h3>
                            <p><i>All for customers - customers for all.</i></p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-12">
                        <div class="testimonial-slider owl-carousel">
                            <c:forEach items="${requestScope.LISTFOODPURCHASEDWITH}" var="x">
                                <div class="single-slide d-sm-flex">
                                    <div class="single-food">
                                        <div class="food-img">
                                            <img src="${x.imgFood}" class="img-fluid" alt="">
                                        </div>
                                        <div class="food-content">
                                            <h5>${x.nameFood}</h5>
                                            Kind: ${x.kind} <br>
                                            Category: ${x.categoryName} <br>
                                            <p>${x.shortDescription}.</p>
                                            <div style="display: inline-block; font-size: 17px;">
                                                <form style="float: left;" action="LoadFoodDetailsController" method="POST">
                                                    <input type="hidden" name="idFood" value="${x.idFood}">
                                                    <input type="hidden" name="kindID" value="${param.kindID}">
                                                    <input type="hidden" name="categoryID" value="${param.categoryID}">
                                                    <input type="hidden" name="priceLower" value="${param.priceLower}">
                                                    <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                                    <input type="hidden" name="searchName" value="${param.searchName}">
                                                    <input type="submit" class="genric-btn primary" name="action" value="View Details">
                                                </form>
                                                <c:if test="${sessionScope.USERDTO.roleID == 'US' || empty sessionScope.USERDTO}">
                                                    <form action="AddAFoodToCartController" style="float: right;margin-left: 10px;" method="POST">
                                                        <div class="cd-popup" role="alert">
                                                            <div class="cd-popup-container">
                                                                <p>Are you sure you want to add this food to your cart?</p>
                                                                <ul class="cd-buttons">
                                                                    <li><a href="#0" onclick="$(this).closest('form').submit();">Yes</a></li>
                                                                    <li><a href="#0" class="cd-no" >No</a></li>
                                                                </ul>
                                                                <a href="#0" class="cd-popup-close img-replace">Close</a>
                                                            </div> <!-- cd-popup-container -->
                                                        </div> <!-- cd-popup -->
                                                        <input type="hidden" name="idFood" value="${x.idFood}">
                                                        <input type="hidden" name="kindID" value="${param.kindID}">
                                                        <input type="hidden" name="categoryID" value="${param.categoryID}">
                                                        <input type="hidden" name="priceLower" value="${param.priceLower}">
                                                        <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                                        <input type="hidden" name="searchName" value="${param.searchName}">
                                                        <input type="submit" id="add-to-cart" class="genric-btn danger" onclick="checkLoginYet();" name="action" value="Add to cart">
                                                    </form>
                                                </c:if>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </c:forEach>

                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Products that are often purchased together Area End -->

        <!--Your recent view starts-->
        <section class="update-area section-padding">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="section-top2 text-center">
                            <h3>Your <span>recent</span> view</h3>
                            <p><i>All for customers - customers for all.</i></p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <c:forEach items="${requestScope.LISTFOODRECENTVIEW}" var="x">
                        <div class="col-md-4">
                            <div class="single-food">
                                <div class="food-img">
                                    <img src="${x.imgFood}" class="img-fluid" alt="">
                                </div>
                                <div class="food-content">
                                    <h5>${x.nameFood}</h5>
                                    Kind: ${x.kind} <br>
                                    Category: ${x.categoryName} <br>
                                    <p>${x.shortDescription}.</p>
                                    <div style="display: inline-block; font-size: 17px;">
                                        <form style="float: left;" action="LoadFoodDetailsController" method="POST">
                                            <input type="hidden" name="idFood" value="${x.idFood}">
                                            <input type="hidden" name="kindID" value="${param.kindID}">
                                            <input type="hidden" name="categoryID" value="${param.categoryID}">
                                            <input type="hidden" name="priceLower" value="${param.priceLower}">
                                            <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                            <input type="hidden" name="searchName" value="${param.searchName}">
                                            <input type="submit" class="genric-btn primary" name="action" value="View Details">
                                        </form>
                                        <c:if test="${sessionScope.USERDTO.roleID == 'US' || empty sessionScope.USERDTO}">
                                            <form action="AddAFoodToCartController" style="float: right;margin-left: 10px;" method="POST">
                                                <div class="cd-popup" role="alert">
                                                    <div class="cd-popup-container">
                                                        <p>Are you sure you want to add this food to your cart?</p>
                                                        <ul class="cd-buttons">
                                                            <li><a href="#0" onclick="$(this).closest('form').submit();">Yes</a></li>
                                                            <li><a href="#0" class="cd-no" >No</a></li>
                                                        </ul>
                                                        <a href="#0" class="cd-popup-close img-replace">Close</a>
                                                    </div> <!-- cd-popup-container -->
                                                </div> <!-- cd-popup -->
                                                <input type="hidden" name="idFood" value="${x.idFood}">
                                                <input type="hidden" name="kindID" value="${param.kindID}">
                                                <input type="hidden" name="categoryID" value="${param.categoryID}">
                                                <input type="hidden" name="priceLower" value="${param.priceLower}">
                                                <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                                <input type="hidden" name="searchName" value="${param.searchName}">
                                                <input type="submit" id="add-to-cart" class="genric-btn danger" onclick="checkLoginYet();" name="action" value="Add to cart">
                                            </form>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                </div>
            </div>
        </section>
        <!-- Your recent view End -->


        <div class="message-popup" role="alert">
            <div class="message-popup-container">
                <c:if test="${not empty requestScope.QuantityError}">
                    <p class="message-display">${requestScope.QuantityError}</p>
                </c:if>
                <c:if test="${not empty requestScope.MESSAGEUPDATE}">
                    <p class="message-display">${requestScope.MESSAGEUPDATE}</p>
                </c:if>
                <a href="#0" class="message-buttons" style="color: #000; font-size: 25px;">OK</a>
                <a href="#0" class="message-popup-close img-replace">Close</a>
            </div> <!-- message-popup-container -->
        </div> <!-- message-popup -->

        <%@include file="footer.html" %>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="js/update-food.js"></script>
        <script src="js/food-details.js"></script>
        <script src="js/pop-up-confirm.js"></script>
        <script src="js/pop-up-message-out-of-stock.js"></script>


        <script>
            <c:if test="${not empty requestScope.MESSAGEUPDATE}">
                                                    $(".message-popup").addClass("is-visible");
            </c:if>
            <c:if test="${not empty requestScope.QuantityError}">
                                                    $(".message-popup").addClass("is-visible");
            </c:if>
                                                    function checkLoginYet() {
            <c:if test="${empty sessionScope.USERDTO}">
                                                        displayLogin();
                                                        event.preventDefault();
            </c:if>
                                                    }

        </script>
        <c:if test="${not empty sessionScope.USERDTO}">
            <c:if test="${sessionScope.USERDTO.roleID == 'US'}">
                <script>
                    jQuery(document).ready(function ($) {
                        $('#add-to-cart*').click(function () {
                            event.preventDefault();
                            $(this).parent().children(".cd-popup").addClass("is-visible");
                        });
                    });
                </script>
            </c:if>
            <c:if test="${sessionScope.USERDTO.roleID == 'AD'}">
                <script>
                    jQuery(document).ready(function ($) {
                        $('#update-food*').click(function () {
                            event.preventDefault();
                            $(this).parent().children(".cd-popup").addClass("is-visible");
                        });
                    });
                </script>
            </c:if>
        </c:if>
    </body>
</html>
