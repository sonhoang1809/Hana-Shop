<%-- 
    Document   : bill-details
    Created on : Feb 27, 2020, 2:25:47 PM
    Author     : sonho
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bill Details</title>
        <!-- Favicon -->
        <link rel="shortcut icon" href="assets/images/logo/favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="style/style-shopping-cart2.css"/>

    </head>
    <body>
        <header id="site-header">
            <div class="container">
                <h1>Bill ID: <span>[</span><em>${requestScope.BILLDETAILS.idBill}</em><span >]</span></h1>
                <c:if test="${sessionScope.USERDTO.roleID == 'US'}">
                    <form action="SearchBillByUserController" method="POST">
                        <input type="hidden" name="date" value="${param.date}">
                        <input type="hidden" name="nameFood" value="${param.nameFood}">
                        <a href="#0" onclick="$(this).closest('form').submit();">Back to History Shopping</a>
                    </form>
                </c:if>
                <c:if test="${sessionScope.USERDTO.roleID == 'AD'}">
                    <form action="manage-hanashop.jsp" method="POST">
                        <input type="hidden" name="date" value="${param.date}">
                        <a href="#0" onclick="$(this).closest('form').submit();">Back to Manage Hana Shop</a>
                    </form>
                    <br>
                    <br>
                    <form action="SearchBillByUserController" method="POST">
                        <input type="hidden" name="date" value="${param.date}">
                        <input type="hidden" name="nameFood" value="${param.nameFood}">
                        <input type="hidden" name="userID" value="${param.userID}">
                        <a href="#0" onclick="$(this).closest('form').submit();">View User's History Shopping</a>
                    </form>
                </c:if>
            </div>
        </header>
        <div class="container">
            <section id="cart"> 
                <c:if test="${empty requestScope.LISTFOODDETAILSINBILL}">
                    <h1>No products!</h1>
                </c:if>
                <c:forEach items="${requestScope.LISTFOODDETAILSINBILL}" var="x">
                    <article class="product">
                        <header>
                            <form action="LoadFoodDetailsController" method="POST">
                                <input type="hidden" name="kindID" value="${param.kindID}">
                                <input type="hidden" name="categoryID" value="${param.categoryID}">
                                <input type="hidden" name="priceLower" value="${param.priceLower}">
                                <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                <input type="hidden" name="searchName" value="${param.searchName}">
                                <input type="hidden" name="idFood" value="${x.idFood}">
                                <input type="hidden" name="userID" value="${param.userID}">
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
                            <span class="qt" id="${x.idFood}">Quantity:${x.quantity}</span>
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
                    <c:if test="${not empty requestScope.BILLDETAILS}">
                        <h2 class="subtotal">Subtotal: <span>${requestScope.BILLDETAILS.subTotal}</span>$</h2>
                        <h2 class="tax">Taxes (10%): <span>${requestScope.BILLDETAILS.tax}</span>$</h2>
                        <h2>Last time change: ${requestScope.BILLDETAILS.date}</h2>
                    </c:if>
                </div>
                <div class="right">
                    <c:if test="${not empty requestScope.BILLDETAILS}">
                        <h1 class="total">Total: <span>${requestScope.BILLDETAILS.total}</span>$</h1>
                    </c:if>
                </div>
            </div>
        </footer>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    </body>
</html>
