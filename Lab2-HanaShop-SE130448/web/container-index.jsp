<%-- 
    Document   : container-index
    Created on : Jan 31, 2020, 11:19:56 PM
    Author     : sonho
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Container index</title>
        <script src="https://use.fontawesome.com/releases/v5.11.2/js/all.js" data-auto-replace-svg="nest"></script>

        <link rel="stylesheet" href="assets/css/animate-3.7.0.css">
        <link rel="stylesheet" href="assets/css/font-awesome-4.7.0.min.css">

        <link rel="stylesheet" href="assets/css/jquery.datetimepicker.min.css">
        <link rel="stylesheet" href="assets/css/nice-select.css" type="text/css">
        <link rel="stylesheet" href="style/pop-up-confirm.css"/>
        <link rel="stylesheet" href="assets/css/style.css"/>
        <link rel="stylesheet" href="style/pop-up-message-out-of-stock.css"/>

    </head>
    <body>


        <!-- Food Area starts -->
        <section class="food-area section-padding">
            <div class="container">
                <div class="row">
                    <div class="col-md-5">
                        <div class="section-top">
                            <h3><span class="style-change">We serve</span> <br>delicious food</h3>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <c:if test="${empty requestScope.LISTFOOD && empty requestScope.SEARCHNULL}">
                        <c:redirect url="SearchController">
                            <c:param name="kindID" value="${param.kindID}"/>
                            <c:param name="categoryID" value="${param.categoryID}"/>
                            <c:param name="priceLower" value="${param.priceLower}"/>
                            <c:param name="priceHigher" value="${param.priceHigher}"/>
                            <c:param name="searchName" value="${param.searchName}"/>
                        </c:redirect>
                    </c:if>
                    ${requestScope.SEARCHNULL}
                    <c:forEach items="${requestScope.LISTFOOD}" var="x">
                        <div class="col-md-4 col-sm-6">
                            <div class="single-food">
                                <div class="food-img" style="w">
                                    <img src="${x.imgFood}" class="img-fluid" alt="">
                                </div>
                                <div class="food-content" >
                                    <div class="d-flex justify-content-between">
                                        <h5>${x.nameFood}</h5>
                                        <span class="style-change">$${x.price}</span>
                                    </div>
                                    Kind: ${x.kind} <br>
                                    Category: ${x.categoryName} <br>
                                    <c:if test="${sessionScope.USERDTO.roleID == 'AD'}">
                                        Quantity: ${x.quantity} 
                                    </c:if>

                                    <p class="pt-3">${x.shortDescription}</p>

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

        <!-- Food Area End -->
        <%@include  file="paging.jsp" %>

        <!-- Reservation Area Starts -->
        <div class="reservation-area section-padding text-center">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <h2>Natural ingredients and testy food</h2>
                        <h4 class="mt-4">some trendy and popular courses offerd</h4>

                    </div>
                </div>
            </div>
        </div>
        <!-- Reservation Area End -->

        <!-- The most purchased products Area start-->
        <div class="deshes-area section-padding">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="section-top2 text-center">
                            <h3>The most <span>purchased</span> products</h3>
                            <p><i>All for customers - customers for all.</i></p>
                        </div>
                    </div>
                </div>
                <c:if test="${not empty requestScope.THEMOSTPURCHASEDFOOD0.imgFood}">
                    <div class="row">
                        <div class="col-lg-5 col-md-6 align-self-center">
                            <h1>01.</h1>
                            <div class="deshes-text">
                                <h3><span>${requestScope.THEMOSTPURCHASEDFOOD0.nameFood}</span></h3>
                                <p class="pt-3">${requestScope.THEMOSTPURCHASEDFOOD0.shortDescription}.</p>
                                <span class="style-change">$ ${requestScope.THEMOSTPURCHASEDFOOD0.price}</span>

                                <div style="display: inline-block; font-size: 17px;">
                                    <form style="float: left;" action="LoadFoodDetailsController" method="POST">
                                        <input type="hidden" name="idFood" value="${requestScope.THEMOSTPURCHASEDFOOD0.idFood}">
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
                                            <input type="hidden" name="idFood" value="${requestScope.THEMOSTPURCHASEDFOOD0.idFood}">
                                            <input type="hidden" name="kindID" value="${param.kindID}">
                                            <input type="hidden" name="categoryID" value="${param.categoryID}">
                                            <input type="hidden" name="priceLower" value="${param.priceLower}">
                                            <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                            <input type="hidden" name="searchName" value="${param.searchName}">
                                            <input type="submit" id="add-to-cart" class="genric-btn danger" onclick="checkLoginYet();"
                                                   name="action" value="Add to cart">
                                        </form>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-5 offset-lg-2 col-md-6 align-self-center mt-4 mt-md-0">
                            <img src="${requestScope.THEMOSTPURCHASEDFOOD0.imgFood}" alt="" class="img-fluid">
                        </div>
                    </div>
                </c:if>
                <c:if test="${not empty requestScope.THEMOSTPURCHASEDFOOD1}">
                    <div class="row mt-5">
                        <div class="col-lg-5 col-md-6 align-self-center order-2 order-md-1 mt-4 mt-md-0">
                            <img src="${requestScope.THEMOSTPURCHASEDFOOD1.imgFood}" alt="" class="img-fluid">
                        </div>
                        <div class="col-lg-5 offset-lg-2 col-md-6 align-self-center order-1 order-md-2">
                            <h1>02.</h1>
                            <div class="deshes-text">
                                <h3><span>${requestScope.THEMOSTPURCHASEDFOOD1.nameFood}</span></h3>
                                <p class="pt-3">${requestScope.THEMOSTPURCHASEDFOOD1.shortDescription}.</p>
                                <span class="style-change">$ ${requestScope.THEMOSTPURCHASEDFOOD1.price}</span>
                                <div style="display: inline-block; font-size: 17px;">
                                    <form style="float: left;" action="LoadFoodDetailsController" method="POST">
                                        <input type="hidden" name="idFood" value="${requestScope.THEMOSTPURCHASEDFOOD1.idFood}">
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
                                            <input type="hidden" name="idFood" value="${requestScope.THEMOSTPURCHASEDFOOD1.idFood}">
                                            <input type="hidden" name="kindID" value="${param.kindID}">
                                            <input type="hidden" name="categoryID" value="${param.categoryID}">
                                            <input type="hidden" name="priceLower" value="${param.priceLower}">
                                            <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                            <input type="hidden" name="searchName" value="${param.searchName}">
                                            <input type="submit" id="add-to-cart" class="genric-btn danger" onclick="checkLoginYet();"
                                                   name="action" value="Add to cart">
                                        </form>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
        <!-- The most purchased products Area End -->

        <!-- List food user's preference Start -->
        <c:if test="${not empty sessionScope.USERDTO}">
            <c:if test="${sessionScope.USERDTO.roleID == 'US'}">
                <section class="testimonial-area section-padding4">
                    <div class="container">
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="section-top2 text-center">
                                    <h3>Your's <span>Preference</span> Food</h3>
                                    <p><i>All for customers - customers for all.</i></p>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="testimonial-slider owl-carousel">
                                    <c:forEach items="${requestScope.LISTFOODUSERPREFERENCE}" var="x">
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
            </c:if>
        </c:if>
        <!-- List food user's preference End -->

        <!-- Out new product Area Starts -->
        <section class="update-area section-padding">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="section-top2 text-center">
                            <h3>Our <span>new</span> products</h3>
                            <p><i>All for customers - customers for all.</i></p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <c:forEach items="${requestScope.LISTFOODNEW}" var="x">
                        <div class="col-md-4">
                            <div class="single-food">
                                <div class="food-img">
                                    <img src="${x.imgFood}" class="img-fluid" alt="">
                                </div>
                                <div class="food-content">
                                    <div class="post-admin d-lg-flex mb-3">
                                        <span><i class="fa fa-calendar-o mr-2"></i>${x.createDate}</span>
                                    </div>

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
        <!-- Out new product Area End -->

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
                <c:if test="${not empty requestScope.MessageWelcome}">
                    <p class="message-display">${requestScope.MessageWelcome}</p>
                </c:if>
                <c:if test="${not empty requestScope.ERRORCODECONFIRM}">
                    <p class="message-display">${requestScope.ERRORCODECONFIRM}</p>
                </c:if>
                <c:if test="${not empty requestScope.LOGINERROR}">
                    <p class="message-display">
                        ${requestScope.LOGINERROR.errorExistEmail} <br>
                        ${requestScope.LOGINERROR.errorExistUserID} <br>
                        ${requestScope.LOGINERROR.errorPassword} <br>
                        ${requestScope.LOGINERROR.errorNotLoginYet}
                    </p>
                </c:if>
                <c:if test="${not empty requestScope.SIGNUPERROR}">
                    <p class="message-display">
                        ${requestScope.SIGNUPERROR.errorUserID}<br>
                        ${requestScope.SIGNUPERROR.errorEmail}<br>
                        ${requestScope.SIGNUPERROR.errorName}<br>
                        ${requestScope.SIGNUPERROR.errorPassword}<br>
                        ${requestScope.SIGNUPERROR.errorConfirm}<br>
                        ${requestScope.SIGNUPERROR.errorConfirmPassword}
                    </p>
                </c:if>
                <c:if test="${not empty requestScope.ErrorFood}">
                    <p class="message-display">${requestScope.ErrorFood}</p>
                </c:if>
                <c:if test="${not empty requestScope.SEARCHNULL}">
                    <p class="message-display">${requestScope.SEARCHNULL}</p>
                </c:if>
                <c:if test="${not empty requestScope.QuantityError}">
                    <p class="message-display">${requestScope.QuantityError}</p>
                </c:if>
                <c:if test="${not empty requestScope.MESSAGEPAYMENT}">
                    <p class="message-display" style="font-size: 20px; color: #000;">${requestScope.MESSAGEPAYMENT}</p>
                    <form action="LoadShoppingHistoryController" method="POST">
                        <input type="hidden" name="idBill" value="${requestScope.idBill}">
                        <a href="#0" onclick="$(this).closest('form').submit();" 
                           style="font-size: 20px; color: #000;">Check Your Bill</a>
                    </form>
                    <br>
                </c:if>
                <a href="#0" class="message-buttons" style="font-size: 20px; color: #000;">OK</a>
                <a href="#0" class="message-popup-close img-replace">Close</a>
            </div> <!-- message-popup-container -->
        </div> <!-- message-popup -->
        <div id="p-up-confirm-email" class="cd-popup" role="alert">
            <div class="cd-popup-container">
                <p>We sent to you a code to confirm this email. Please check your email and confirm the code !</p>
                <form action="VerifyEmailController" method="POST">
                    <input name="codeConfirm" type="number" class="form-control validate" required=""/>
                    <ul class="cd-buttons">
                        <input type="hidden" name="userID" value="${param.userID}"/>
                        <input type="hidden" name="email" value="${param.email}"/>
                        <input type="hidden" name="userName" value="${param.userName}"/>
                        <input type="hidden" name="password" value="${param.password}"/>
                        <input type="hidden" name="confirm" value="${param.confirm}"/>
                        <a href="#0" class="message-buttons" onclick="$(this).closest('form').submit();"
                           style="font-size: 20px; color: #000;">Confirm</a>
                    </ul>
                </form>
                <form action="SendCodeUserController" method="POST">
                    <ul class="cd-buttons">
                        <input type="hidden" name="userID" value="${param.userID}"/>
                        <input type="hidden" name="email" value="${param.email}"/>
                        <input type="hidden" name="userName" value="${param.userName}"/>
                        <input type="hidden" name="password" value="${param.password}"/>
                        <input type="hidden" name="confirm" value="${param.confirm}"/>
                        <li><a href="#0" style=" color: #000;" onclick="$(this).closest('form').submit();">Send new code</a></li>
                        <li><a href="#0" class="cd-no" >Back</a></li>
                    </ul>
                </form>
                <a href="#0" class="cd-popup-close img-replace">Close</a>
            </div>
        </div>

        <script>
            <c:if test="${not empty requestScope.MessageWelcome}">
            $(".message-popup").addClass("is-visible");
            </c:if>
            <c:if test="${not empty requestScope.ERRORCODECONFIRM}">
            displaySignup();
            $(".message-popup").addClass("is-visible");
            $("#p-up-confirm-email").addClass("is-visible");
            </c:if>
            <c:if test="${not empty requestScope.LOGINERROR}">
            $(".message-popup").addClass("is-visible");
            </c:if>
            <c:if test="${not empty requestScope.SIGNUPERROR}">
            $(".message-popup").addClass("is-visible");
            </c:if>
            <c:if test="${not empty requestScope.MessageConfirmEmail}">
            displaySignup();
            $("#p-up-confirm-email").addClass("is-visible");
            </c:if>
            function checkLoginYet() {
            <c:if test="${empty sessionScope.USERDTO}">
                event.preventDefault();
                displayLogin();
            </c:if>
            }
            <c:if test="${not empty requestScope.ErrorFood}">
            $(".message-popup").addClass("is-visible");
            </c:if>
            <c:if test="${not empty requestScope.SEARCHNULL}">
            $(".message-popup").addClass("is-visible");
            </c:if>
            <c:if test="${not empty requestScope.QuantityError}">
            $(".message-popup").addClass("is-visible");
            </c:if>
            <c:if test="${not empty requestScope.MESSAGEPAYMENT}">
            $(".message-popup").addClass("is-visible");
            </c:if>
        </script>
        <c:if test="${not empty requestScope.SIGNUPERROR}">
            <script>
                displaySignup();
            </script>
        </c:if>
        <c:if test="${not empty requestScope.LOGINERROR}">
            <script>
                displayLogin();
            </script>
        </c:if>
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
        </c:if>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="js/page.js"></script>
        <script src="js/pop-up-message-out-of-stock.js"></script>
        <script src="js/pop-up-confirm.js"></script>
        <script src="assets/js/vendor/jquery-2.2.4.min.js"></script>
        <script src="assets/js/vendor/bootstrap-4.1.3.min.js"></script>
        <script src="assets/js/vendor/wow.min.js"></script>
        <script src="assets/js/vendor/owl-carousel.min.js"></script>
        <script src="assets/js/vendor/jquery.datetimepicker.full.min.js"></script>

        <script src="assets/js/main.js"></script>
    </body>
</html>
