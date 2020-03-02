<%-- 
    Document   : manage-hanashop
    Created on : Feb 28, 2020, 11:54:42 PM
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
        <title>Manage Hana Shop Page</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,700">
        <!-- Favicon -->
        <link rel="shortcut icon" href="assets/images/logo/favicon.png" type="image/x-icon">
        <link rel="stylesheet" href="style/font-awesome.min.css">
        <link rel="stylesheet" href="style/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/nice-select.css" type="text/css">
        <link rel="stylesheet" href="assets/css/jquery.datetimepicker.min.css">

        <link rel="stylesheet" href="style/templatemo-style.css">

    </head>

    <body id="reportsPage">
        <%@include file="header.jsp" %>
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
        <div class="" id="home" style="m">
            <nav class="navbar navbar-expand-xl">
                <div class="container h-100">
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav mx-auto h-100">
                            <li class="nav-item">
                               <form class="nav-link active" action="manage-hanashop.jsp" method="POST">
                                    <input type="hidden" name="date" value="${param.date}">
                                    <a href="manage-hanashop.jsp">
                                        <i class="fas fa-tachometer-alt"></i>
                                        <h5>Dashboard</h5>
                                    </a>
                                </form>
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
                                <form action="manage-account.jsp" method="POST">
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
            <div class="container">
                <div class="row">
                    <div class="col">
                        <h4 class="mt-5 mb-5"> <b>Welcome back, Admin ${sessionScope.USERDTO.name} !!</b></h4>
                    </div>
                </div>
                <div class="row tm-content-row">
                    <div class="col-12 tm-block-col">
                        <div class="tm-bg-primary-dark tm-block tm-block-h-auto">
                            <h4 class="tm-block-title">View Information 7 days from</h4>
                            <div class="col-lg-8 offset-lg-2">
                                <form action="manage-hanashop.jsp" method="POST">
                                    <div class="input-group mb-3">
                                        <div class="input-group-prepend">
                                            <span class="input-group-text"><i class="fa fa-calendar"></i></span>
                                        </div>
                                        <input style="width: 90%;" type="text" autocomplete="off" placeholder="From" name="date" 
                                               id="datepicker" value="${param.date}">
                                    </div>
                                    <div class="table-btn text-center">
                                        <a href="#0" class="template-btn template-btn2 mt-4" onclick="$(this).closest('form').submit();">
                                            View Information
                                        </a>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- row -->
                <div class="row tm-content-row">
                    <div class="col-sm-12 col-md-12 col-lg-6 col-xl-6 tm-block-col">
                        <div class="tm-bg-primary-dark tm-block">
                            <h4 class="tm-block-title">Ordered bills each 7 days</h4>
                            <canvas id="lineChart"></canvas>
                        </div>
                    </div>
                    <div class="col-sm-12 col-md-12 col-lg-6 col-xl-6 tm-block-col">
                        <div class="tm-bg-primary-dark tm-block">
                            <h4 class="tm-block-title">Revenue each 7 days</h4>
                            <canvas id="lineChart2"></canvas>
                        </div>
                    </div>
                    <div class="col-sm-12 col-md-12 col-lg-6 col-xl-6 tm-block-col">
                        <div class="tm-bg-primary-dark tm-block tm-block-taller">
                            <h4 class="tm-block-title">Kind Food In Storage</h4>
                            <div id="pieChartContainer">
                                <canvas id="pieChart" class="chartjs-render-monitor" width="200" height="200"></canvas>
                            </div>                        
                        </div>
                    </div>
                    <div class="col-sm-12 col-md-12 col-lg-6 col-xl-6 tm-block-col">
                        <div class="tm-bg-primary-dark tm-block">
                            <h4 class="tm-block-title">Number food of each category sold</h4>
                            <canvas id="barChart"></canvas> 
                        </div>
                    </div>
                    <div class="col-12 tm-block-col">
                        <div class="tm-bg-primary-dark tm-block tm-block-taller tm-block-scroll">
                            <h2 class="tm-block-title">Orders List</h2>
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th scope="col">STT</th>
                                        <th scope="col">BILL NO.</th>
                                        <th scope="col">STATUS</th>
                                        <th scope="col">USER NAME</th>
                                        <th scope="col">SUBTOTAL</th>
                                        <th scope="col">TAX</th>
                                        <th scope="col">TOTAL</th>
                                        <th scope="col">ORDER DATE</th>

                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${requestScope.LISTORDERBILL}" var="x" varStatus="Count">
                                        <tr style="cursor: pointer;" onclick="document.getElementById('${x.idBill}').submit();">
                                            <th scope="row"><b>${Count.count}</b></th>
                                            <th scope="row"><b>${x.idBill}</b></th>
                                            <td>
                                                <c:if test="${x.statusBillCode==1}">
                                                    <div class="tm-status-circle moving">
                                                    </div> Shipping
                                                </c:if>
                                                <c:if test="${x.statusBillCode==0}">
                                                    <div class="tm-status-circle pending">
                                                        <!--    <div class="tm-status-circle cancelled"></div> -->
                                                    </div> Not Order Yet !!
                                                </c:if>
                                            </td>
                                            <td><b>${x.userName}</b></td>
                                            <td><b>${x.subTotal}$</b></td>
                                            <td><b>${x.tax}$</b></td>
                                            <td>${x.total}$</td>
                                            <td>${x.date}</td>
                                            <td style="display: none;">
                                                <form style="display: none;" action="LoadShoppingHistoryController" method="POST" id="${x.idBill}">
                                                    <input type="hidden" name="date" value="${param.date}">
                                                    <input type="hidden" name="idBill" value="${x.idBill}">
                                                    <input type="hidden" name="userID" value="${x.userID}">
                                                    <input type="submit" class="genric-btn warning" 
                                                           name="action" value="View Details">
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="footer.html" %>

        <script src="js/jquery-3.3.1.min.js"></script>
        <!-- https://jquery.com/download/ -->
        <script src="js/moment.min.js"></script>
        <!-- https://momentjs.com/ -->
        <script src="js/Chart.min.js"></script>
        <!-- http://www.chartjs.org/docs/latest/ -->
        <script src="js/bootstrap.min.js"></script>
        <!-- https://getbootstrap.com/ -->
        <script src="js/tooplate-scripts.js"></script>
        <script>

                                            Chart.defaults.global.defaultFontColor = 'white';
                                            let ctxLine,
                                                    ctxBar,
                                                    ctxPie,
                                                    optionsLine,
                                                    optionsBar,
                                                    optionsPie,
                                                    configLine,
                                                    configBar,
                                                    configPie,
                                                    lineChart,
                                                    lineChart2;
                                            barChart, pieChart;

                                            // DOM is ready
                                            $(function () {
                                                var colors = ['#f6e58d', '#dff9fb', '#be2edd',
                                                    '#22a6b3', '#eb4d4b', '#e58e26', '#fff200',
                                                    '#7efff5', '#32ff7e', '#18dcff', '#38ada9',
                                                    '#9980FA', '#82ccdd', '#f8c291', '#f9ca24'];
                                                var categoryName = [];
                                                var quantitySold = [];
            <c:if test="${not empty requestScope.ListCategoryPer}">
                <c:forEach items="${requestScope.ListCategoryPer}" var="x" >
                                                categoryName.push("${x.categoryName}");
                                                quantitySold.push(${x.numFoodSold});
                </c:forEach>
            </c:if>
                                                var totalFoods = [];
                                                var kinds = [];
            <c:forEach items="${requestScope.listKindStorage}" var="x">
                                                totalFoods.push(${x.totalNumFood});
                                                kinds.push('${x.kindFood}');
            </c:forEach>
                                                var date = [];
                                                var time = [];
            <c:forEach items="${requestScope.ListTimeFromDateToDate}" var="x">
                                                time.push(${x});
            </c:forEach>
            <c:forEach items="${requestScope.ListDate}" var="x">
                                                date.push('${x}');
            </c:forEach>
                                                var dateMoney = [];
                                                var money = [];
            <c:forEach items="${requestScope.ListMoneyByDay}" var="x">
                                                money.push(${x});
            </c:forEach>
            <c:forEach items="${requestScope.ListDateMoney}" var="x">
                                                dateMoney.push('${x}');
            </c:forEach>

                                                drawLineChart(date, time); // Line Chart
                                                drawLineChart2(dateMoney, money);
                                                drawBarChart(categoryName, quantitySold, colors); // Bar Chart
                                                drawPieChart(totalFoods, kinds, colors); // Pie Chart

                                                $(window).resize(function () {
                                                    updateLineChart();
                                                    updateBarChart();
                                                });
                                            });

        </script>
        <script>
            $("#datepicker").bind({
                keydown: function (e) {
                    return false;
                }
            });
        </script>
        <script src="assets/js/vendor/jquery.datetimepicker.full.min.js"></script>
        <script src="assets/js/vendor/jquery.nice-select.min.js"></script>
    </body>
</html>
