<%-- 
    Document   : payment
    Created on : Feb 17, 2020, 10:41:49 AM
    Author     : sonho
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Payment Page</title>
        <!-- Favicon -->
        <link rel="shortcut icon" href="assets/images/logo/favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="style/style-payment.css">
        <link rel="stylesheet" href="style/pop-up-message-out-of-stock.css"/>
    </head>
    <body>
        <div class="body-text"><h2>ENTER RECEIVED USER INFORMATION</h2></div>
        <form action="LoadShoppingCartController" method="POST">
            <input type="hidden" name="idBill" value="${param.idBill}">
            <input type="hidden" name="kindID" value="${param.kindID}">
            <input type="hidden" name="categoryID" value="${param.categoryID}">
            <input type="hidden" name="priceLower" value="${param.priceLower}">
            <input type="hidden" name="priceHigher" value="${param.priceHigher}">
            <input type="hidden" name="searchName" value="${param.searchName}">
            <a href="#" onclick="$(this).closest('form').submit();">< Back to Check Shopping Cart</a>
        </form>
        <br>
        <form action="method-payment.jsp" method="POST">
            <div class="form-container">
                <div class="personal-information">
                    <h1>Payment Information</h1>
                </div> <!-- end of personal-information -->
                <input id="column-left" type="text" name="firstname" required="" placeholder="First Name" value="${param.firstname}"/>
                <input id="column-right" type="text" name="lastname" required="" placeholder="Surname" value="${param.lastname}"/>
                <div class="card-wrapper"></div>
                <input id="input-field" type="text" name="address" required="required" autocomplete="on" maxlength="45" placeholder="Address" value="${param.address}"/>
                <input id="column-left" type="text" name="street" required="required" autocomplete="on" maxlength="20" placeholder="Street" value="${param.street}"/>
                <input id="column-right" type="text" name="city" required="required" autocomplete="on" placeholder="City" value="${param.city}"/>
                <input id="input-field" type="email" name="email" required="required" autocomplete="on" maxlength="40" placeholder="Email" value="${sessionScope.USERDTO.email}"/>
                <input type="hidden" name="idBill" value="${param.idBill}">
                <input type="hidden" name="kindID" value="${param.kindID}">
                <input type="hidden" name="categoryID" value="${param.categoryID}">
                <input type="hidden" name="priceLower" value="${param.priceLower}">
                <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                <input type="hidden" name="searchName" value="${param.searchName}">
                <input id="input-button" type="submit" value="Continue to Method Payment"/>
            </div>
        </form>
        <div class="message-popup" role="alert">
            <div class="message-popup-container">
                <c:if test="${not empty requestScope.ErrorAddress}">
                    <p class="message-display">${requestScope.ErrorAddress}</p>
                </c:if>
                <a href="#0" class="message-buttons" style="color: #000; font-size: 20px;">OK</a>
                <a href="#0" class="message-popup-close img-replace">Close</a>
            </div> <!-- message-popup-container -->
        </div> <!-- message-popup -->

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script>
            <c:if test="${not empty requestScope.ErrorAddress}">
                $(".message-popup").addClass("is-visible");
            </c:if>
        </script>
        <script src="js/pop-up-message-out-of-stock.js"></script>
        <script>
                $('form').card({
                    container: '.card-wrapper',
                    width: 280,

                    formSelectors: {
                        nameInput: 'input[name="first-name"], input[name="last-name"]'
                    }
                });
        </script>

    </body>
</html>
