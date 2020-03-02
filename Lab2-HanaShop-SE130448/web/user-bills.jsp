<%-- 
    Document   : user-details
    Created on : Feb 26, 2020, 10:12:39 PM
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
        <title>User Bills Page</title>
        <!-- Favicon -->
        <link rel="shortcut icon" href="assets/images/logo/favicon.png" type="image/x-icon">
        <!-- CSS Files -->
        <link rel="stylesheet" href="assets/css/animate-3.7.0.css">
        <link rel="stylesheet" href="assets/css/font-awesome-4.7.0.min.css">
        <link rel="stylesheet" href="assets/css/bootstrap-4.1.3.min.css">
        <link rel="stylesheet" href="assets/css/owl-carousel.min.css">
        <link rel="stylesheet" href="assets/css/jquery.datetimepicker.min.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <link rel="stylesheet" href="style/pop-up-message-out-of-stock.css"/>
        <link rel="stylesheet" href="style/style-avt-user.css">
    </head>
    <body>
        <%@include file="header.jsp" %>
        <!-- Banner Area Starts -->
        <section class="banner-area banner-area2 blog-page text-center">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <h1><i>User Details</i></h1>
                    </div>
                </div>
            </div>
        </section>
        <!-- Banner Area End -->
        <!-- Search bill Area Starts -->

        <section class="table-area section-padding">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="section-top2 text-center">
                            <c:if test="${sessionScope.USERDTO.roleID == 'US'}">
                                <h3>Search <span>your</span> bill</h3>
                            </c:if>
                            <c:if test="${sessionScope.USERDTO.roleID == 'AD'}">
                                <h3>Search <span>user</span> bill</h3>
                            </c:if>
                            <p><i>All for customers - customers for all.</i></p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-8 offset-lg-2">
                        <form action="SearchBillByUserController" method="POST">
                            <input type="hidden" name="userID" value="${param.userID}">
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text"><i class="fa fa-calendar"></i></span>
                                </div>
                                <input type="text" autocomplete="off" name="date" onchange="check();" id="datepicker" value="${param.date}">
                            </div>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text"><i class="far fa-clock"></i></span>
                                </div>
                                <input type="text" autocomplete="off" id="datepicker2" onchange="check2();" disabled=""  name="time" value="${param.time}">
                            </div>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text"><i class="fas fa-file-invoice"></i></span>
                                </div>
                                <input type="text" autocomplete="off" name="nameFood" id="nameFood" placeholder="Name food" value="${param.nameFood}">
                            </div>
                            <div class="table-btn text-center">
                                <a href="#0" class="template-btn template-btn2 mt-4" onclick="check2(); $(this).closest('form').submit();">
                                    Search Bill
                                </a>
                            </div>
                            <div class="table-btn text-center">
                                <a href="#0" class="template-btn template-btn2 mt-4" onclick="reset()">
                                    Reset Search
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <!-- Search bill Area End -->
        <!--================History shopping Area =================-->
        <section class="blog_area section-padding">
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 posts-list">
                        <div class="single-post row">
                            <div class="col-lg-12">
                                <div class="feature-img">
                                    <img class="img-fluid" src="assets/images/blog-details/feature-img1.jpg" alt="">
                                </div>
                            </div>
                            ${requestScope.SEARCHNULL}
                            <c:forEach items="${requestScope.LISTBILLUSER}" var="x">
                                <div class="col-lg-3  col-md-3">
                                    <div class="blog_info text-right">
                                        <ul class="blog_meta list">
                                            <li><a>${requestScope.FULLDETAIUSERDTO.name}<i class="far fa-user"></i></a></li>
                                            <li><a>${x.date}<i class="fa fa-calendar"></i></a></li>
                                                        <c:if test="${x.statusBillCode == 1}">
                                                <li>
                                                    <a>PAID<i class="fas fa-toggle-on"></i></a>
                                                </li>
                                            </c:if>
                                            <c:if test="${x.statusBillCode == 0}">
                                                <li><a>UNPAID<i class="fas fa-toggle-off"></i></a></li>
                                                    </c:if>
                                        </ul>
                                    </div>
                                </div>
                                <div class="col-lg-9 col-md-9 blog_details">
                                    <h5>Bill ID: ${x.idBill}</h5>
                                    <li><a>SubTotal: ${x.subTotal}</a></li>
                                    <li><a>Tax: ${x.tax}</a></li>
                                    <li><a>Total: ${x.total}</a></li>
                                    <form action="LoadShoppingHistoryController" method="POST">
                                        <input type="hidden" name="date" value="${param.date}">
                                        <input type="hidden" name="nameFood" value="${param.nameFood}">
                                        <input type="hidden" name="idBill" value="${x.idBill}">
                                        <input type="hidden" name="userID" value="${param.userID}">
                                        <a href="#0" class="template-btn" onclick="$(this).closest('form').submit();">
                                            Show Details
                                        </a>
                                    </form>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <!-- Details User Area Start -->
                    <div class="col-lg-4">
                        <div class="blog_right_sidebar">
                            <aside class="single_sidebar_widget author_widget">
                                <div class="avatar-wrapper">
                                    <img class="profile-pic" src="${requestScope.FULLDETAIUSERDTO.avatar}"/>
                                    <c:if test="${sessionScope.USERDTO.roleID == 'US'}">
                                        <div class="upload-button">
                                            <i class="fa fa-arrow-circle-up" aria-hidden="true"></i>
                                        </div>
                                        <form>
                                            <input class="file-upload" type="file" accept="image/*" />
                                        </form>
                                    </c:if>
                                </div>
                                <h4 style="color: #000;">${requestScope.FULLDETAIUSERDTO.name}</h4>
                                <c:if test="${requestScope.FULLDETAIUSERDTO.roleID == 'AD'}">
                                    <p><i class="fas fa-address-card"></i>: Admin</p>
                                </c:if>
                                <c:if test="${requestScope.FULLDETAIUSERDTO.roleID == 'US'}">
                                    <p><i class="fas fa-address-card"></i>: Customer</p>
                                </c:if>
                                <p>${requestScope.FULLDETAIUSERDTO.description}</p>
                                <c:if test="${requestScope.FULLDETAIUSERDTO.roleID == 'US'}">
                                    <p>Date join: ${requestScope.FULLDETAIUSERDTO.dateCreate}</p>
                                </c:if>
                                <div class="br"></div>
                            </aside>
                            <aside class="single_sidebar_widget popular_post_widget">

                                <h4 class="widget_title">Recent View</h4>
                                <c:forEach items="${requestScope.LISTFOODRECENTVIEW}" var="x">
                                    <div class="media post_item">
                                        <img src="${x.imgFood}" style="width: 80px; height: 80px;" alt="post">
                                        <div class="media-body">
                                            <form action="LoadFoodDetailsController" method="POST">
                                                <input type="hidden" name="idFood" value="${x.idFood}">
                                                <input type="hidden" name="kindID" value="${param.kindID}">
                                                <input type="hidden" name="categoryID" value="${param.categoryID}">
                                                <input type="hidden" name="priceLower" value="${param.priceLower}">
                                                <input type="hidden" name="priceHigher" value="${param.priceHigher}">
                                                <input type="hidden" name="searchName" value="${param.searchName}">
                                                <a href="#0" onclick="$(this).closest('form').submit();">
                                                    <h5>${x.nameFood}</h5>
                                                </a>
                                            </form>
                                        </div>
                                    </div>
                                </c:forEach>
                                <div class="br"></div>
                            </aside>
                        </div>
                    </div>
                    <!-- Details User Area End -->
                </div>
            </div>
        </section>
        <!--================History shopping Area End =================-->

        <!--footer start-->
        <%@include file="footer.html" %>
        <!--footer end-->
        <div class="message-popup" role="alert">
            <div class="message-popup-container">

                <c:if test="${not empty requestScope.SEARCHNULL}">
                    <p class="message-display">${requestScope.SEARCHNULL}</p>
                </c:if>

                <a href="#0" class="message-buttons" style="font-size: 20px; color: #000;">OK</a>
                <a href="#0" class="message-popup-close img-replace">Close</a>
            </div> <!-- message-popup-container -->
        </div> <!-- message-popup -->
        <c:if test="${not empty requestScope.SEARCHNULL}">
            <script>
                $(".message-popup").addClass("is-visible");
            </script>
        </c:if>
        <script>
            function check2() {
                var text = $("#datepicker2").val();
                console.log(text);
                if (text !== null) {
                    $("#datepicker").attr("required");
                }
            }
            function check() {
                var text = $("#datepicker").val();
                console.log(text);
                if (text !== null) {
                    $("#datepicker2").removeAttr("disabled");
                }
            }
            function reset() {
                $("#datepicker").val("");
                $("#datepicker2").val("");
                $("#nameFood").val("");
            }
        </script>
        <script>
            $("#datepicker").bind({
                keydown: function (e) {
                    return false;
                }
            });
            $("#datepicker2").bind({
                keydown: function (e) {
                    return false;
                }
            });
        </script>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="js/avt-user.js"></script>
        <script src="js/pop-up-message-out-of-stock.js"></script>
        <script src="assets/js/vendor/jquery-2.2.4.min.js"></script>
        <script src="assets/js/vendor/bootstrap-4.1.3.min.js"></script>
        <script src="assets/js/vendor/wow.min.js"></script>
        <script src="assets/js/vendor/owl-carousel.min.js"></script>
        <script src="assets/js/vendor/jquery.datetimepicker.full.min.js"></script>
        <script src="assets/js/vendor/jquery.nice-select.min.js"></script>
        <script src="assets/js/main.js"></script>

    </body>
</html>
