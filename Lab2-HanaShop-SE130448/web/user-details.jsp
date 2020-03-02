<%-- 
    Document   : user-details
    Created on : Mar 1, 2020, 10:35:59 AM
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
        <title>User Details Page</title>
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
        <link rel="stylesheet" href="style/upload-pic-food.css" type="text/css">
        <link rel="stylesheet" href="assets/css/nice-select.css" type="text/css">
        <link rel="stylesheet" href="style/templatemo-style.css">
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
        <div class="" id="home" style="m">
            <c:if test="${sessionScope.USERDTO.roleID == 'AD'}">
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
                                    <form action="manage-food.jsp" method="POST">
                                        <input type="hidden" name="kindIDAd" value="${param.kindIDAd}">
                                        <input type="hidden" name="categoryIDAd" value="${param.categoryIDAd}">
                                        <a class="nav-link" href="#0" onclick="$(this).closest('form').submit();">
                                            <i class="fas fa-shopping-cart"></i>
                                            <h5>Foods</h5>
                                        </a>
                                    </form>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link active" href="manage-account.jsp">
                                        <i class="far fa-user"></i>
                                        <h5>Accounts</h5>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </nav>
            </c:if>
            <br>
            <br>
            <br>
            <form class="container" action="UpdateUserController" enctype="multipart/form-data" method="POST" >
                <input type="hidden" name="oldAvatar" value="${requestScope.UserDetails.avatar}">
                <input type="hidden" name="userID" value="${requestScope.UserDetails.userID}">
                <div class="row tm-content-row">
                    <div class="tm-block-col tm-col-avatar">
                        <div class="tm-bg-primary-dark tm-block tm-block-avatar">
                            <h4 class="tm-block-title">Avatar</h4>
                            <div class="uploadWrapper">
                                <div id="imageUploadForm" class="imageUploadForm">
                                    <span class="helpText" id="helpText">Upload an image</span>
                                    <img style="height: 350px; width: 320px;" src="${requestScope.UserDetails.avatar}">
                                    <input type="file" id="fileUpdate" 
                                           class="uploadButton" name="avatar" 
                                           accept="image/*" >
                                    <div id="uploadedImg" class="uploadedImg" >
                                        <span class="unveil" ></span>
                                    </div>
                                </div>
                            </div>
                            <div style="height: 80px;"></div>
                        </div>
                    </div>
                    <div class="tm-block-col tm-col-account-settings">
                        <div class="tm-bg-primary-dark tm-block tm-block-settings">
                            <h4 class="tm-block-title">Account Information</h4>
                            <div class="tm-signup-form row">
                                <div class="form-group col-lg-6">
                                    <label for="name">Account Name</label>
                                    <input id="name" name="name" type="text"
                                           class="form-control validate" value="${requestScope.UserDetails.name}" />
                                </div>
                                <div class="form-group col-lg-6">
                                    <label for="email">Account Email</label>
                                    <input id="email" name="email" type="email" disabled
                                           class="form-control validate" value="${requestScope.UserDetails.email}" />
                                </div>
                                <div class="form-group col-lg-6">
                                    <label for="Date Create">Date Create</label>
                                    <input
                                        id="phone"
                                        name="dateCreate"
                                        type="text"
                                        disabled
                                        class="form-control validate" value="${requestScope.UserDetails.dateCreate}"
                                        />
                                </div>
                                <div class="form-group col-lg-6">
                                    <div class="form-group mb-3" >
                                        <label for="description">Description</label>
                                        <textarea name="description" class="form-control validate" rows="3" required>${requestScope.UserDetails.description}
                                        </textarea>
                                    </div>
                                </div>
                                <div class="form-group col-lg-6">
                                    <label for="Role">Role</label>
                                    <c:if test="${sessionScope.USERDTO.roleID == 'US'}">
                                        <input id="phone" type="text" disabled  class="form-control validate"
                                               value="Customer"/>
                                    </c:if>
                                    <c:if test="${sessionScope.USERDTO.roleID == 'AD' && requestScope.UserDetails.roleID == 'AD' }">
                                        <input id="phone" type="text" disabled  class="form-control validate"
                                               value="Administrator"/>
                                    </c:if>
                                    <input type="hidden" name="oldRoleID" value="${requestScope.UserDetails.roleID}"/>
                                    <c:if test="${sessionScope.USERDTO.roleID == 'AD' && requestScope.UserDetails.roleID != 'AD'}">
                                        <select class="custom-select tm-select-accounts" id="category" name="roleID" required>
                                            <c:forEach items="${requestScope.ListRole}" var="x">
                                                <option value="${x.roleID}"
                                                        <c:if test="${requestScope.UserDetails.roleID == x.roleID}">
                                                            selected
                                                        </c:if>
                                                        >${x.roleDetail}</option>
                                            </c:forEach>
                                        </select>
                                    </c:if>
                                </div>
                                <div class="form-group col-lg-6">
                                    <label class="tm-hide-sm">&nbsp;</label>
                                    <button type="submit" class="btn btn-primary btn-block text-uppercase" >
                                        Update Profile
                                    </button>
                                </div>
                            </div>
                            <c:if test="${requestScope.UserDetails.roleID == 'US'}">
                                <div class="col-12">
                                    <a onclick="$('#go-history-shopping').submit();" class="btn btn-primary btn-block text-uppercase" >
                                        View History Shopping
                                    </a>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </form>
            <form style="display: none;" id="go-history-shopping" action="SearchBillByUserController" method="POST">
                <input type="hidden" name="userID" value="${requestScope.UserDetails.userID}">
            </form>
        </div>
        <div class="message-popup" role="alert">
            <div class="message-popup-container">
                <c:if test="${not empty requestScope.MESSAGEUPDATE}">
                    <p class="message-display">${requestScope.MESSAGEUPDATE}</p>
                </c:if>
                <a href="#0" class="message-buttons" style="color: #000; font-size: 25px;">OK</a>
                <a href="#0" class="message-popup-close img-replace">Close</a>
            </div> <!-- message-popup-container -->
        </div> <!-- message-popup -->
        <%@include file="footer.html" %>
        <script>
            <c:if test="${not empty requestScope.MESSAGEUPDATE}">
            $(".message-popup").addClass("is-visible");
            </c:if>
        </script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="js/avt-user.js"></script>

        <script src="js/pop-up-confirm.js"></script>
        <script src="js/pop-up-message-out-of-stock.js"></script>
        <script src="js/jquery-3.3.1.min.js"></script>
        <!-- https://jquery.com/download/ -->
        <script src="js/moment.min.js"></script>
        <!-- https://momentjs.com/ -->
        <script src="js/Chart.min.js"></script>
        <!-- http://www.chartjs.org/docs/latest/ -->
        <script src="js/bootstrap.min.js"></script>
        <!-- https://getbootstrap.com/ -->
        <script src="js/tooplate-scripts.js"></script>
        <script src="assets/js/vendor/jquery.datetimepicker.full.min.js"></script>
        <script src="assets/js/vendor/jquery.nice-select.min.js"></script>
    </body>
</html>
