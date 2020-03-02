<%-- 
    Document   : method-payment
    Created on : Feb 17, 2020, 11:21:22 AM
    Author     : sonho
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1"> <!-- Ensures optimal rendering on mobile devices. -->
        <meta http-equiv="X-UA-Compatible" content="IE=edge" /> <!-- Optimal Internet Explorer compatibility -->
        <title>Method payment Page</title>
        <link rel="stylesheet" href="style/style-payment.css">
        <link rel="stylesheet" href="style/pop-up-message-out-of-stock.css"/>
    </head>
    <body>
        <div class="body-text"><h2>CHOOSE A METHOD PAYMENT</h2></div>
        <form action="payment.jsp" method="POST">
            <input type="hidden" name="idBill" value="${param.idBill}">
            <input type="hidden" name="kindID" value="${param.kindID}">
            <input type="hidden" name="categoryID" value="${param.categoryID}">
            <input type="hidden" name="priceLower" value="${param.priceLower}">
            <input type="hidden" name="priceHigher" value="${param.priceHigher}">
            <input type="hidden" name="searchName" value="${param.searchName}">

            <input id="column-left" type="hidden" name="firstname"  value="${param.firstname}"/>
            <input id="column-left" type="hidden" name="lastname" value="${param.lastname}"/>
            <input id="column-left" type="hidden" name="address"  value="${param.address}"/>
            <input id="column-left" type="hidden" name="street" value="${param.street}"/>
            <input id="column-left" type="hidden" name="city" value="${param.city}"/>
            <input id="input-field" type="hidden" name="email" value="${sessionScope.USERDTO.email}"/>
            <a href="#0" onclick="$(this).closest('form').submit();">< Back to Payment Information</a>
        </form>
        <br>

        <div class="form-container" id="here">
            <div class="personal-information">
                <h1>Payment Information</h1>
            </div> <!-- end of personal-information -->
            <br>
            <form action="PayController" method="POST">
                <input type="hidden" name="methodPay" value="COD">
                <input type="hidden" name="idBill" value="${param.idBill}">
                <input  type="hidden" name="firstname"  value="${param.firstname}"/>
                <input  type="hidden" name="lastname"  value="${param.lastname}"/>
                <input  type="hidden" name="address"  value="${param.address}"/>
                <input type="hidden" name="street" value="${param.street}"/>
                <input  type="hidden" name="city" value="${param.city}"/>
                <input id="column-left" type="hidden" name="address"  value="${param.streetaddress}"/>
                <input id="column-left" type="hidden" name="street" value="${param.city}"/>
                <input id="column-left" type="hidden" name="city" value="${param.zipcode}"/>
                <input id="input-button" type="submit" value="Cash On Delivery"/>
            </form>
            <br>
            <div class="card-wrapper"></div>
            <form id="form-paypal" action="PayController" method="POST">
                <input type="hidden" name="methodPay" value="Paypal">
                <input  type="hidden" name="firstname"  value="${param.firstname}"/>
                <input  type="hidden" name="lastname"  value="${param.lastname}"/>
                <input  type="hidden" name="address"  value="${param.address}"/>
                <input type="hidden" name="street" value="${param.street}"/>
                <input  type="hidden" name="city" value="${param.city}"/>
                <input type="hidden" name="idBill" value="${param.idBill}">

                <input id="input-button" type="submit" value="Pay now with Paypal"/>
                <!--                <div id="paypal-button-container"></div>-->
            </form>
        </div>

        <div class="message-popup" role="alert">
            <div class="message-popup-container">
                <p class="message-display">${requestScope.QuantityError}</p>
                <p class="message-display">${requestScope.ErrorFood}</p>
                <p class="message-display">${requestScope.ErrorPayPal}</p>
                <a href="#0" class="message-buttons" style="color: #000; font-size: 20px;">OK</a>
                <a href="#0" class="message-popup-close img-replace">Close</a>
            </div> <!-- message-popup-container -->
        </div> <!-- message-popup -->
    <c:if test="${not empty requestScope.ErrorPayPal}">
        <script>
            $(".message-popup").addClass("is-visible");
        </script>
    </c:if>
    <!--    <script
            src="https://www.paypal.com/sdk/js?client-id=Aa7AndcgAFRz297Bs1DHn0KW-w8eaIqen3hxRGwDZL3hmGHfdsMtL8GipWlpfu7svoBA8AgaqObgHlM8&intent=order&disable-funding=credit,card"> // Required. Replace SB_CLIENT_ID with your sandbox client ID.
        </script>
        <script>
            const formData = new FormData();
            const params = document.getElementById('form-paypal').querySelector('input[type="hidden"]');
            let data = {
                firstname: '',
                lastname: '',
                address: '',
                street: '',
                city: ''
            };
            formData.append('firstname', '');
            formData.append('lastName', '');
            paypal.Buttons({
                createOrder: function () {
                    var SETEC_URL = 'http://localhost:8084/Lab2-HanaShop-MavenDemo2/CreatePayPalOrderController';
                    // This function sets up the details of the transaction, including the amount and line item details.
                    return fetch(SETEC_URL, {
                        method: 'post',
                        headers: {
                            'content-type': 'application/json'
                        },
                        body: JSON.stringify({
                           firstname: '',
                lastname: '',
                address: '',
                street: '',
                city: ''
                        })
                    }).then(function (res) {
                        return res.json();
                    }).then(function (data) {
                        return data.orderID; // Use the same key name for order ID on the client and server
                    }
                    );
                },
                onApprove: function (data, actions) {
                    // This function captures the funds from the transaction.
                    return actions.order.capture().then(function (details) {
                        // This function shows a transaction success message to your buyer.
                        alert('Transaction completed by ' + details.payer.name.given_name);
                    });
                }
            }).render('#paypal-button-container');
        </script>-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

</body>
</html>
