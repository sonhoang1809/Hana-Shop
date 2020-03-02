<%-- 
    Document   : shopping-cart
    Created on : Feb 10, 2020, 10:30:38 PM
    Author     : sonho
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping Cart</title>
        <!-- Favicon -->
        <link rel="shortcut icon" href="assets/images/logo/favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="style/style-shopping-cart2.css"/>
        <link rel="stylesheet" href="style/pop-up-confirm.css"/>
        <link rel="stylesheet" href="style/pop-up-message-out-of-stock.css"/>
    </head>
    <body>
        <c:if test="${empty sessionScope.USERDTO}">
            <c:redirect url="SearchController">
            </c:redirect>
        </c:if>
        <c:if test="${not empty sessionScope.USERDTO}">
            <c:if test="${sessionScope.USERDTO.roleID == 'AD'}">
                <c:redirect url="SearchController">
                </c:redirect>
            </c:if>
        </c:if>
        <header id="site-header">
            <div class="container">
                <h1>Shopping cart                   <span>[</span><em>${sessionScope.USERDTO.name}</em><span >]</span></h1>
                <form action="SearchController" method="POST">
                    <input type="hidden" name="kindID" value="${param.kindID}">
                    <input type="hidden" name="categoryID" value="${param.categoryID}">
                    <input type="hidden" name="priceLower" value="${param.priceLower}">
                    <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                    <input type="hidden" name="searchName" value="${param.searchName}">
                    <a href="#" onclick="$(this).closest('form').submit();">Continue Shopping</a>
                </form>
            </div>
        </header>
        <div class="container">
            <section id="cart"> 
                <c:if test="${empty sessionScope.LISTFOODINBILL}">
                    <h1>No products!</h1>
                </c:if>
                <c:forEach items="${sessionScope.LISTFOODINBILL}" var="x">
                    <article class="product">
                        <header>
                            <form action="LoadFoodDetailsController" method="POST">
                                <input type="hidden" name="kindID" value="${param.kindID}">
                                <input type="hidden" name="categoryID" value="${param.categoryID}">
                                <input type="hidden" name="priceLower" value="${param.priceLower}">
                                <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                <input type="hidden" name="searchName" value="${param.searchName}">
                                <input type="hidden" name="idFood" value="${x.idFood}">
                                <a class="view-details-food" onclick="$(this).closest('form').submit();">
                                    <img src="${x.imgFood}" alt="">
                                    <h3>View details</h3>
                                </a>
                            </form>
                        </header>
                        <div class="content">
                            <h2>${x.nameFood}</h2>
                            ${x.shortDescription}
                        </div>
                        <footer class="content">
                            <input type="hidden" class="maxQuantity" id="maxQuantity" value="${x.maxQuantity}">
                            <span class="qt-minus">-</span>

                            <span class="qt" id="${x.idFood}">${x.quantity}</span>

                            <span class="qt-plus">+</span>
                            <span class="qt-remove">x</span>
                            <c:if test="${x.statusFoodID==0}">
                                <span class="qt-status">Out of Stock</span>
                            </c:if>
                            <h5 class="full-price">
                                ${x.total}$ 
                            </h5>

                            <h5 class="price">
                                ${x.price}$ 
                            </h5>

                            <div class="cd-popup" role="alert">
                                <div class="cd-popup-container">
                                    <p>Are you sure you want to delete this element?</p>
                                    <form action="DeleteAFoodInCartController" method="POST">
                                        <ul class="cd-buttons">
                                            <input type="hidden" name="kindID" value="${param.kindID}">
                                            <input type="hidden" name="categoryID" value="${param.categoryID}">
                                            <input type="hidden" name="priceLower" value="${param.priceLower}">
                                            <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                            <input type="hidden" name="searchName" value="${param.searchName}">
                                            <input type="hidden" name="idFoodDelete" value="${x.idFood}">
                                            <input type="hidden" name="idBill" value="${sessionScope.BILLDTO.idBill}">
                                            <li><a href="#0" onclick="$(this).closest('form').submit();">Yes</a></li>
                                            <li><a href="#0" class="cd-no" >No</a></li>
                                        </ul>
                                    </form>
                                    <a href="#0" class="cd-popup-close img-replace">Close</a>
                                </div> <!-- cd-popup-container -->
                            </div> <!-- cd-popup -->
                        </footer>
                    </article>
                </c:forEach>
            </section>
        </div>
        <footer id="site-footer">
            <div class="container clearfix">
                <div class="left">
                    <c:if test="${not empty sessionScope.BILLDTO}">
                        <h2 class="subtotal">Subtotal: <span>${sessionScope.BILLDTO.subTotal}</span>$</h2>
                        <h2 class="tax">Taxes (10%): <span>${sessionScope.BILLDTO.tax}</span>$</h2>
                        <h2>Last time change: ${sessionScope.BILLDTO.date}</h2>
                    </c:if>
                    <c:if test="${empty sessionScope.BILLDTO}">
                        <h2 class="subtotal">Subtotal: <span>0</span>$</h2>
                        <h2 class="tax">Taxes (10%): <span>0</span>$</h2>
                        <h2>Last time change: </h2>
                    </c:if>
                </div>
                <div class="right">
                    <form name="form-update-quantity" action="UpdateCartController" method="POST">
                        <input type="hidden" name="idBill" value="${sessionScope.BILLDTO.idBill}">
                        <input type="hidden" name="kindID" value="${param.kindID}">
                        <input type="hidden" name="categoryID" value="${param.categoryID}">
                        <input type="hidden" name="priceLower" value="${param.priceLower}">
                        <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                        <input type="hidden" name="searchName" value="${param.searchName}">
                        <c:forEach items="${sessionScope.LISTFOODINBILL}" var="x">
                            <input type="hidden" id="${x.idFood}" name="idFood-quantity" value="${x.idFood}-${x.quantity}">
                        </c:forEach>
                        <c:if test="${not empty sessionScope.BILLDTO}">
                            <c:if test="${sessionScope.NUMFOOD > 0}">
                                <a class="btn" onclick="$(this).closest('form').submit();">Update Cart</a>
                            </c:if>
                        </c:if>
                    </form>
                    <c:if test="${not empty sessionScope.BILLDTO}">
                        <h1 class="total">Total: <span>${sessionScope.BILLDTO.total}</span>$</h1>
                    </c:if>
                    <c:if test="${empty sessionScope.BILLDTO}">
                        <h1 class="total">Total: <span>0</span>$</h1>
                    </c:if>
                    <c:if test="${not empty sessionScope.BILLDTO}">
                        <c:if test="${sessionScope.NUMFOOD > 0}">
                            <form action="payment.jsp" method="POST">
                                <input type="hidden" name="kindID" value="${param.kindID}">
                                <input type="hidden" name="categoryID" value="${param.categoryID}">
                                <input type="hidden" name="priceLower" value="${param.priceLower}">
                                <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                <input type="hidden" name="searchName" value="${param.searchName}">
                                <input type="hidden" name="idBill" value="${sessionScope.BILLDTO.idBill}">
                                <a class="btn" onclick="$(this).closest('form').submit();">Checkout</a>
                            </form>
                        </c:if>
                    </c:if>

                </div>
            </div>
        </footer>
        <div class="message-popup" role="alert">
            <div class="message-popup-container">
                <c:if test="${not empty requestScope.ERRORNULLLISTFOODINBILL}">
                    <p class="message-display">${requestScope.ERRORNULLLISTFOODINBILL}</p>
                </c:if>
                <c:if test="${not empty requestScope.DeleteMessage}">
                    <p class="message-display">${requestScope.DeleteMessage}</p>
                </c:if>
                <c:if test="${not empty requestScope.ErrorPaypal}">
                    <p class="message-display">${requestScope.ErrorPaypal}</p>
                </c:if>
                <c:if test="${not empty requestScope.MessageRefund}">
                    <p class="message-display">${requestScope.MessageRefund}</p>
                </c:if>
                <c:if test="${not empty requestScope.MessageAdminChangePrice}">
                    <p class="message-display">${requestScope.MessageAdminChangePrice}</p>
                </c:if>
                <c:if test="${not empty requestScope.MessageUpdate}">
                    <p class="message-display">${requestScope.MessageUpdate}</p>
                </c:if>
                <c:if test="${not empty requestScope.ErrorFood}">
                    <p class="message-display">${requestScope.ErrorFood}</p>
                </c:if>
                <c:if test="${not empty requestScope.QuantityError}">
                    <p class="message-display">${requestScope.QuantityError}</p>
                </c:if>
                <a href="#0" class="message-buttons" style="color: #000; font-size: 20px;">OK</a>
                <a href="#0" class="message-popup-close img-replace">Close</a>
            </div> <!-- message-popup-container -->
        </div> <!-- message-popup -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

        <c:if test="${not empty requestScope.ERRORNULLLISTFOODINBILL}">
            <script>
                                    $(".message-popup").addClass("is-visible");
            </script>
        </c:if>
        <c:if test="${not empty requestScope.DeleteMessage}">
            <script>
                $(".message-popup").addClass("is-visible");
            </script>
        </c:if>
        <c:if test="${not empty requestScope.ErrorPaypal}">
            <script>
                $(".message-popup").addClass("is-visible");
            </script>
        </c:if>
        <c:if test="${not empty requestScope.MessageUpdate}">
            <script>
                $(".message-popup").addClass("is-visible");
            </script>
        </c:if>
        <c:if test="${not empty requestScope.MessageRefund}">
            <script>
                $(".message-popup").addClass("is-visible");
            </script>
        </c:if>
        <c:if test="${not empty requestScope.MessageAdminChangePrice}">
            <script>
                $(".message-popup").addClass("is-visible");
            </script>
        </c:if>

        <c:if test="${not empty requestScope.QuantityError}">
            <script>
                $(".message-popup").addClass("is-visible");
            </script>
        </c:if>
        <c:if test="${not empty requestScope.ErrorFood}">
            <script>
                $(".message-popup").addClass("is-visible");
            </script>
        </c:if>

        <script src="js/shopping-cart.js"></script>
        <script src="js/pop-up-delete-food-in-cart.js"></script>
        <script src="js/pop-up-message-out-of-stock.js"></script>
    </body>
</html>
