<%-- 
    Document   : shopping-cart-hover
    Created on : Feb 10, 2020, 11:35:38 PM
    Author     : sonho
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping cart hover</title>
        <link rel="stylesheet" href="style/style-shopping-cart-hover.css"/>
    </head>
    <body>
        <div>
            <header id="site-header">
                <div class="container">
                    <h3>Your shopping cart</h3>
                </div>
            </header>

            <div class="container">
                <section id="cart">
                    <c:if test="${empty sessionScope.LISTFOODINBILL}">
                        <h5>No products!</h5>
                    </c:if>

                    <c:forEach items="${sessionScope.LISTFOODINBILL}" var="x">

                        <article class="product">
                            <header>
                                <img src="${x.imgFood}" alt="">
                            </header>
                            <div class="content">
                                <h5>${x.nameFood}</h5>
                                ${x.shortDescription}
                            </div>
                            <footer class="content">
                                <span class="qt">Kind: ${x.kind}</span>
                                <span class="qt">Category: ${x.categoryName}</span>
                                <span class="qt">Quantity: ${x.quantity}</span>

                                <c:if test="${x.statusFoodID==0}">
                                    <span class="qt" style="background-color: red;">Out of Stock</span>
                                </c:if>

                                <h5 class="full-price">
                                    ${x.total}$
                                </h5>
                                <h5 class="price">
                                    ${x.price}$
                                </h5>

                            </footer>
                        </article>

                    </c:forEach>
                </section>
            </div>
            <footer id="site-footer">
                <div class="container clearfix">
                    <div class="left">
                        <c:if test="${not empty sessionScope.BILLDTO}">
                            <h5 class="subtotal">Subtotal: <span>${sessionScope.BILLDTO.subTotal}</span>$</h5>
                            <h5 class="tax">Taxes (10%): <span>${sessionScope.BILLDTO.tax}</span>$</h5>
                            <h5>Last time change: ${sessionScope.BILLDTO.date}</h5>
                        </c:if>
                        <c:if test="${empty sessionScope.BILLDTO}">
                            <h5 class="subtotal">Subtotal: <span>0</span>$</h5>
                            <h5 class="tax">Taxes (10%): <span>0</span>$</h5>
                            <h5>Last time change: </h5>
                        </c:if>
                    </div>
                    <div class="right">
                        <c:if test="${not empty sessionScope.BILLDTO}">
                            <h3 class="total">Total: <span>${sessionScope.BILLDTO.total}</span>$</h3>
                        </c:if>
                        <c:if test="${empty sessionScope.BILLDTO}">
                            <h3 class="total">Total: <span>0</span>$</h3>
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
                                    <a class="btn" onclick="$(this).closest('form').submit();" style="color: #fff;">Checkout</a>
                                </form>
                            </c:if>
                        </c:if>
                    </div>
                </div>
            </footer>

        </div>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

        <c:if test="${empty sessionScope.USERDTO}">
            <script src="js/shopping-cart.js"></script>
        </c:if>
        <c:if test="${not empty sessionScope.USERDTO}">
            <c:if test="${sessionScope.USERDTO.roleID =='AD'}">
                <script src="js/shopping-cart.js"></script>
            </c:if>
        </c:if>

    </body>
</html>
