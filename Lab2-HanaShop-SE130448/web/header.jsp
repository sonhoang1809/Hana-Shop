<%-- 
    Document   : header2
    Created on : Jan 31, 2020, 10:10:58 PM
    Author     : sonho
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Header</title>
        <!-- CSS Files -->
        <link rel="stylesheet" href="assets/css/animate-3.7.0.css">
        <link rel="stylesheet" href="assets/css/font-awesome-4.7.0.min.css">
        <link rel="stylesheet" href="assets/css/bootstrap-4.1.3.min.css">
        <link rel="stylesheet" href="assets/css/owl-carousel.min.css">
        <link rel="stylesheet" href="assets/css/jquery.datetimepicker.min.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <script src="https://use.fontawesome.com/releases/v5.11.2/js/all.js" data-auto-replace-svg="nest"></script>
        <link rel="stylesheet" href="style/style-login.css" type="text/css">
        <link rel="stylesheet" href="style/style-signup.css" type="text/css">

        <!--facebook api-->
        <!-- <div id="fb-root"></div> 
        <script async defer crossorigin="anonymous" src="https://connect.facebook.net/en_US/sdk.js#xfbml=1&version=v5.0&appId=120134942650484&autoLogAppEvents=1"></script>
        -->
        <!--google api-->
        <script src="https://apis.google.com/js/platform.js" async defer></script>
        <meta name="google-signin-scope" content="profile email">
        <meta name="google-signin-client_id"
              content="91083904661-ah89apkechpos9lnu058ldgi6hava11b.apps.googleusercontent.com">
    </head>
    <body>
        <!-- Preloader Starts -->
        <div class="preloader">
            <div class="spinner"></div>
        </div>
        <!-- Preloader End -->

        <!-- Header Area Starts -->
        <header class="header-area">
            <div class="container">
                <div class="row">
                    <div class="col-lg-2">
                        <div class="logo-area">
                            <form action="SearchController" method="POST">
                                <input type="hidden" id="kindID" name="kindID" value="${param.kindID}">
                                <input type="hidden" id="categoryID" name="categoryID" value="${param.categoryID}">
                                <input type="hidden" id="priceLower" name="priceLower" value="${param.priceLower}">
                                <input type="hidden" id="priceHigher" name="priceHigher" value="${param.priceHigher}">
                                <input type="hidden" id="searchName" name="searchName" value="${param.searchName}">
                                <a href="#0" onclick="$(this).closest('form').submit();"><img src="assets/images/logo/logo.png" alt="logo"></a>
                            </form>
                        </div>
                    </div>
                    <div class="col-lg-10">
                        <div class="custom-navbar">
                            <span></span>
                            <span></span>
                            <span></span>
                        </div>  
                        <div class="main-menu">
                            <ul>
                                <li class="active">
                                    <form style="float: left;" action="SearchController" method="POST">
                                        <input type="hidden" name="kindID" value="${param.kindID}">
                                        <input type="hidden" name="categoryID" value="${param.categoryID}">
                                        <input type="hidden" name="priceLower" value="${param.priceLower}">
                                        <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                        <input type="hidden" name="searchName" value="${param.searchName}">
                                        <a href="#0" onclick="$(this).closest('form').submit();">home</a>
                                    </form>
                                </li>
                                <c:if test="${empty sessionScope.USERDTO}">
                                    <li>
                                        <a class="login-dp" onclick="displayLogin()" href="#0">Login</a>
                                        <ul class="sub-menu">
                                            <li>
                                                <a onclick="displaySignup()" href="#0">Sign up</a>
                                            </li>
                                        </ul>
                                    </li>
                                </c:if>
                                <c:if test="${not empty sessionScope.USERDTO}">
                                    <c:if test="${sessionScope.USERDTO.roleID=='AD'}">
                                        <li>
                                            <a href="#0">Manage</a>
                                            <ul class="sub-menu">
                                                <li>
                                                    <form action="add-food.jsp" method="POST">
                                                        <input type="hidden" name="kindID" value="${param.kindID}">
                                                        <input type="hidden" name="categoryID" value="${param.categoryID}">
                                                        <input type="hidden" name="priceLower" value="${param.priceLower}">
                                                        <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                                        <input type="hidden" name="searchName" value="${param.searchName}">
                                                        <a href="#0" onclick="$(this).closest('form').submit();">Add A New Food</a>
                                                    </form>
                                                </li>
                                                <li>
                                                    <form action="manage-hanashop.jsp" method="POST">
                                                        <input type="hidden" name="kindID" value="${param.kindID}">
                                                        <input type="hidden" name="categoryID" value="${param.categoryID}">
                                                        <input type="hidden" name="priceLower" value="${param.priceLower}">
                                                        <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                                        <input type="hidden" name="searchName" value="${param.searchName}">
                                                        <a href="#0" onclick="$(this).closest('form').submit();">Manage Hana Shop</a>
                                                    </form>
                                                </li>
                                            </ul>
                                        </li>
                                        <li> 
                                            <a href="#0">Welcome admin ${sessionScope.USERDTO.name} !</a>
                                            <ul class="sub-menu">
                                                <li>
                                                    <form action="LoadUserDetailsController" method="POST">
                                                        <input type="hidden" name="kindID" value="${param.kindID}">
                                                        <input type="hidden" name="categoryID" value="${param.categoryID}">
                                                        <input type="hidden" name="priceLower" value="${param.priceLower}">
                                                        <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                                        <input type="hidden" name="searchName" value="${param.searchName}">
                                                        <input type="hidden" name="userID" value="${sessionScope.USERDTO.userID}">
                                                        <a href="#0" onclick="$(this).closest('form').submit();">View Your Details</a>
                                                    </form>
                                                </li>
                                                <li>
                                                    <form action="LogoutController" method="POST">
                                                        <input type="hidden" name="kindID" value="${param.kindID}">
                                                        <input type="hidden" name="categoryID" value="${param.categoryID}">
                                                        <input type="hidden" name="priceLower" value="${param.priceLower}">
                                                        <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                                        <input type="hidden" name="searchName" value="${param.searchName}">
                                                        <a href="#0" onclick="logOut(); $(this).closest('form').submit();">Log out</a>
                                                    </form>
                                                </li>
                                            </ul>
                                        </li>
                                    </c:if>
                                    <c:if test="${sessionScope.USERDTO.roleID == 'US'}">
                                        <li>
                                            <a href="#0">Welcome ${sessionScope.USERDTO.name} !!</a>
                                            <ul class="sub-menu">
                                                <li>
                                                    <form action="LoadShoppingCartController" method="POST">
                                                        <input type="hidden" name="kindID" value="${param.kindID}">
                                                        <input type="hidden" name="categoryID" value="${param.categoryID}">
                                                        <input type="hidden" name="priceLower" value="${param.priceLower}">
                                                        <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                                        <input type="hidden" name="searchName" value="${param.searchName}">
                                                        <a href="#0" onclick="$(this).closest('form').submit();">Check Your Cart</a>
                                                    </form>
                                                </li>
                                                <li>
                                                    <form action="SearchBillByUserController" method="POST">
                                                        <input type="hidden" name="kindID" value="${param.kindID}">
                                                        <input type="hidden" name="categoryID" value="${param.categoryID}">
                                                        <input type="hidden" name="priceLower" value="${param.priceLower}">
                                                        <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                                        <input type="hidden" name="searchName" value="${param.searchName}">
                                                        <a href="#0" onclick="$(this).closest('form').submit();">Shopping History</a>
                                                    </form>
                                                </li>
                                                <li>
                                                    <form action="LoadUserDetailsController" method="POST">
                                                        <input type="hidden" name="kindID" value="${param.kindID}">
                                                        <input type="hidden" name="categoryID" value="${param.categoryID}">
                                                        <input type="hidden" name="priceLower" value="${param.priceLower}">
                                                        <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                                        <input type="hidden" name="searchName" value="${param.searchName}">
                                                        <input type="hidden" name="userID" value="${sessionScope.USERDTO.userID}">
                                                        <a href="#0" onclick="$(this).closest('form').submit();">View Your Details</a>
                                                    </form>
                                                </li>
                                                <li>
                                                    <form action="LogoutController" method="POST">
                                                        <input type="hidden" name="kindID" value="${param.kindID}">
                                                        <input type="hidden" name="categoryID" value="${param.categoryID}">
                                                        <input type="hidden" name="priceLower" value="${param.priceLower}">
                                                        <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                                        <input type="hidden" name="searchName" value="${param.searchName}">
                                                        <a href="#0" onclick="logOut(); $(this).closest('form').submit();">Log out</a>
                                                    </form>
                                                </li>
                                            </ul>
                                        </li>
                                        <li>
                                            <form style="float: right;" action="LoadShoppingCartController" method="POST">
                                                <input type="hidden" name="kindID" value="${param.kindID}">
                                                <input type="hidden" name="categoryID" value="${param.categoryID}">
                                                <input type="hidden" name="priceLower" value="${param.priceLower}">
                                                <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                                <input type="hidden" name="searchName" value="${param.searchName}">
                                                <a href="#0" onclick="$(this).closest('form').submit();"><i class="fas fa-shopping-cart"></i>${sessionScope.NUMFOOD}</a>
                                            </form>
                                            <ul class="sp-cart">
                                                <%@include file="shopping-cart-hover.jsp" %>
                                            </ul>
                                        </li>
                                    </c:if>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </header>
        <!-- Header Area End -->

        <!--login form-->

        <div id="login-form" class="login-form" style="display: none;">
            <button class="close-form" onclick="closeLogin()" >x</button> <br>
            <form action="LoginController" method="POST">
                <h1>Login</h1>
                <div class="txtb">
                    <input type="text" name="userID" required="" onkeypress="return AvoidSpace(event)" value="${param.userID}">
                    <span data-placeholder="User ID"></span>
                </div>
                <div class="txtb">
                    <input type="password" name="password" required="" value="${param.password}">
                    <span data-placeholder="Password"></span>
                </div>
                <input type="hidden" name="searchTitle" value="${param.searchTitle}"/>
                <input type="submit" class="genric-btn warning-border radius" name="action" value="Login" style="width: 100%; font-size: 20px;"/>
                <div class="middle">
                    <p>
                        Or Login With
                    </p>

                    <div class="fb-login-button" data-width="280px" data-size="large" data-button-type="login_with" data-auto-logout-link="false" data-use-continue-as="false"></div>

                    <div style="height: 10px;">

                    </div>
                    <div class="g-signin2" data-width="280%" data-height="50%" data-longtitle="true" data-onsuccess="onSignIn" ></div>
                </div>
                <div class="bottom-text">
                    Don't have account ?
                    <a onclick="fromLoginToSignup()" href="#">Sign up</a>
                    <br>
                    <br>
                </div>
            </form>
        </div>
        <!--End Login form-->

        <!--Signup form-->
        <div id="signup-form" class="signup-form" style="display: none;">
            <button class="close-form" onclick="closeSignup()">x</button> <br>
            <form action="SendCodeUserController" method="POST">
                <h1>Sign Up</h1>
                <div class="txtb">
                    <input type="text" name="userID" required="" pattern="[A-Za-z]{3,}" title="Input UserID that can contain three letters or more (no numbers or special characters)" onkeydown="return AvoidSpace(event)" value="${param.userID}">
                    <span data-placeholder="User ID"></span>
                </div>
                <div class="txtb">
                    <input type="email" name="email" required="" value="${param.email}">
                    <span data-placeholder="Email"></span>
                </div>
                <div class="txtb">
                    <input type="text" name="userName" required="" value="${param.userName}">
                    <span data-placeholder="User Name"></span>
                </div>
                <div class="txtb">

                    <input type="password" name="password" required="" value="${param.password}">
                    <span data-placeholder="Password"></span>
                </div>
                <div class="txtb">
                    <input type="password" name="confirm" required="" value="${param.confirm}">
                    <span data-placeholder="Confirm Password"></span>
                </div>
                <input type="submit" class="genric-btn warning-border radius" name="action" value="Sign up" style="width: 100%; font-size: 20px;">
            </form>
            <div class="bottom-text">
                Already have account ?
                <a onclick="fromSignupToLogin()" href="#">Login</a>
                <br>
                <br>
            </div>
        </div>
        <!--End Signup form-->
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

        <!-- Javascript -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="js/login-signup.js"></script>
        <script src="js/pop-up.js"></script>
        <script src="assets/js/vendor/jquery-2.2.4.min.js"></script>
        <script src="assets/js/vendor/bootstrap-4.1.3.min.js"></script>
        <script src="assets/js/vendor/wow.min.js"></script>
        <script src="assets/js/vendor/owl-carousel.min.js"></script>
        <script src="assets/js/vendor/jquery.datetimepicker.full.min.js"></script>
        <script src="assets/js/vendor/jquery.nice-select.min.js"></script>
        <script src="assets/js/main.js"></script>
    </body>
</html>
