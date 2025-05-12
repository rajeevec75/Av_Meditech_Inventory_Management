<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="POS - Bootstrap Admin Template">
        <meta name="keywords"
              content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
        <meta name="author" content="Dreamguys - Bootstrap Admin Template">
        <meta name="robots" content="noindex, nofollow">
        <title>Av Meditech Inventory Management</title>

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/av-meditech-fevicon.png">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">

        <!-- Datetimepicker CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css">

        <!-- animation CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/animate.css">

        <!-- Feathericon CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/feather.css">

        <!-- Select2 CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/select2/css/select2.min.css">

        <!-- Bootstrap Tagsinput CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/bootstrap-tagsinput/bootstrap-tagsinput.css">

        <!-- Datatable CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dataTables.bootstrap5.min.css">

        <!-- Fontawesome CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome/css/all.min.css">

        <!-- Main CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

        <style>
            /* Ensure the design only shows on mobile screens */
            /* Mobile view */
            @media (max-width: 768px) {
                .sub-btn{
                    width:100%
                }
            }

        </style>

    </head>

    <body>
        <div id="global-loader">
            <div class="whirly-loader"> </div>
        </div>
        <!-- Main Wrapper -->
        <div class="main-wrapper">

            <!-- Header and Sidebar -->
            <jsp:include page="header.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>





                <div class="page-wrapper">
                    <div class="content">
                        <div class="row">
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger" role="alert">
                                ${errorMessage}
                            </div>
                        </c:if> 
                        <c:if test="${not empty successMessage}">
                            <div class="alert alert-success" role="alert">
                                ${successMessage}
                            </div>
                        </c:if>

                        <form id="purchaseForm">
                            <div class="row">
                                <div class="col-lg-4 col-sm-6 col-12">
                                    <div class="mb-3 add-product">
                                        <label class="form-label">Start Date</label>
                                        <input type="date" class="form-control" id="startDate" name="startDate" value="${fromDate}">
                                    </div>
                                </div>
                                <div class="col-lg-4 col-sm-6 col-12">
                                    <div class="mb-3 add-product">
                                        <label class="form-label">End Date</label>
                                        <input type="date" class="form-control" id="endDate" name="endDate" value="${toDate}">
                                    </div>
                                </div>
                                <div class="col-lg-4 col-sm-6 col-12" style="margin-top: 28px">
                                    <div class="mb-3 add-product">
                                        <button type="submit" class="btn btn-submit sub-btn">Submit</button>
                                    </div>
                                </div>
                            </div>
                        </form>

                        <!--                        <div class="col-xl-3 col-sm-6 col-12 d-flex">
                                                    <div class="dash-widget dash2 w-100">
                                                        <div class="dash-widgetimg">
                                                            <span><img src="${pageContext.request.contextPath}/image/icons/dash3.svg" alt="img"></span>
                                                        </div>
                                                        <div class="dash-widgetcontent">
                                                            <h5>
                                                                <span>Rs </span><span id="total-sale-amount" class="counters">${dashboardData.totalSaleAmount}</span>
                                                            </h5>
                                                            <h6>Total Sale Amount</h6>
                                                        </div>
                                                    </div>
                                                </div>-->
                        <!--                        <div class="col-xl-3 col-sm-6 col-12 d-flex">
                                                    <div class="dash-widget dash3 w-100">
                                                        <div class="dash-widgetimg">
                                                            <span><img src="${pageContext.request.contextPath}/image/icons/dash4.svg" alt="img"></span>
                                                        </div>
                                                        <div class="dash-widgetcontent">
                                                            <h5><span>Rs </span><span class="counters" id="total-purchase-amount">${dashboardData.totalPurchaseAmount}</span></h5>
                                                            <h6>Total Expense Amount</h6> 
                                                        </div>
                                                    </div>
                                                </div>-->

                        <div class="row">        
                            <div class="col-xl-3 col-sm-6 col-12 d-flex">
                                <div class="dash-count">
                                    <div class="dash-counts">
                                        <h4 id="total-customers">${dashboardData.totalCustomerCount}</h4>
                                        <h5>Customers</h5>
                                    </div>
                                    <div class="dash-imgs">
                                        <i data-feather="user"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-3 col-sm-6 col-12 d-flex">
                                <div class="dash-count das1">
                                    <div class="dash-counts">
                                        <h4 id="total-suppliers">${dashboardData.totalSupplierCount}</h4>
                                        <h5>Suppliers</h5>
                                    </div>
                                    <div class="dash-imgs">
                                        <i data-feather="user-check"></i>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-3 col-sm-6 col-12 d-flex">
                                <div class="dash-count das2">
                                    <div class="dash-counts">
                                        <h4 id="total-purchase">${dashboardData.totalPurchaseCount}</h4>
                                        <h5>Purchase Invoice</h5>
                                    </div>
                                    <div class="dash-imgs">
                                        <img src="${pageContext.request.contextPath}/image/icons/file-text-icon-01.svg" class="img-fluid" alt="icon">
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-3 col-sm-6 col-12 d-flex">
                                <div class="dash-count das3">
                                    <div class="dash-counts">
                                        <h4 id="total-sale">${dashboardData.totalSaleCount}</h4>
                                        <h5>Sales Invoice</h5>
                                    </div>
                                    <div class="dash-imgs">
                                        <i data-feather="file"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Button trigger modal -->

                    <div class="row">
                        <div class="col-xl-7 col-sm-12 col-12 d-flex">
                            <div class="card flex-fill">
                                <div class="card-header d-flex justify-content-between align-items-center">
                                    <h5 class="card-title mb-0">Purchase & Sales</h5>
                                    <div class="graph-sets">
                                        <ul class="mb-0">
                                            <li>
                                                <span>Sales</span>
                                            </li>
                                            <li>
                                                <span>Purchase</span>
                                            </li>
                                        </ul>

                                    </div>
                                </div>
                                <div class="card-body">
                                    <div id="sales_charts1"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>



        </div>
        <!-- /Main Wrapper -->

        <!-- jQuery -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>

        <!-- Feather Icon JS -->
        <script src="${pageContext.request.contextPath}/js/feather.min.js"></script>

        <!-- Slimscroll JS -->
        <script src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>

        <!-- Bootstrap Core JS -->
        <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>

        <!-- Chart JS -->
        <script src="${pageContext.request.contextPath}/plugins/apexchart/apexcharts.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/apexchart/chart-data.js"></script>

        <!-- Sweetalert 2 -->
        <script src="${pageContext.request.contextPath}/plugins/sweetalert/sweetalert2.all.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/sweetalert/sweetalerts.min.js"></script>

        <!-- Custom JS -->
        <script src="${pageContext.request.contextPath}/js/theme-script.js"></script>	
        <script src="${pageContext.request.contextPath}/js/script.js"></script>






        <script>
            // Function to format date as YYYY-MM-DD
            function formatDate(date) {
                let d = new Date(date),
                        month = '' + (d.getMonth() + 1),
                        day = '' + d.getDate(),
                        year = d.getFullYear();

                if (month.length < 2)
                    month = '0' + month;
                if (day.length < 2)
                    day = '0' + day;

                return [year, month, day].join('-');
            }

            // Set the "to-date" input to the current date
            document.getElementById('to-date').value = formatDate(new Date());
        </script>

        <script>
            $(document).ready(function () {

                // Prevent the form from submitting normally and handle it via AJAX
                $('#purchaseForm').submit(function (event) {
                    event.preventDefault();
                    var startDate = $('#startDate').val();
                    var endDate = $('#endDate').val();
                    $.ajax({
                        url: '${pageContext.request.contextPath}/startDateAndEndDate/json',
                        method: 'POST',
                        data: {
                            startDate: startDate,
                            endDate: endDate
                        },
                        success: function (response) {
                            console.log('Response:', response);
                            $('#total-sale-amount').text(response.totalSaleAmount);
                            $('#total-customers').text(response.totalCustomerCount);
                            $('#total-suppliers').text(response.totalSupplierCount);
                            $('#total-purchase').text(response.totalPurchaseCount);
                            $('#total-sale').text(response.totalSaleCount);
                            $('#total-purchase-amount').text(response.totalPurchaseAmount);
                        },
                        error: function (xhr, status, error) {
                            console.error('Error:', error);
                            $('#total-purchase-amount').text('Error calculating total purchase amount for date range');
                        }
                    });
                });
            });

            $(document).ready(function () {
                if ($('#sales_charts1').length > 0) {
                    var options = {
                        series: [{
                                name: 'Sales',
                                data: [130, 210, 300, 290, 150, 50, 210, 280, 105],
                            }, {
                                name: 'Purchase',
                                data: [-150, -90, -50, -180, -50, -70, -100, -90, -105]
                            }],
                        colors: ['#28C76F', '#EA5455'],
                        chart: {
                            type: 'bar',
                            height: 320,
                            stacked: true,

                            zoom: {
                                enabled: true
                            }
                        },
                        responsive: [{
                                breakpoint: 280,
                                options: {
                                    legend: {
                                        position: 'bottom',
                                        offsetY: 0
                                    }
                                }
                            }],
                        plotOptions: {
                            bar: {
                                horizontal: false,
                                borderRadius: 4,
                                borderRadiusApplication: "end", // "around" / "end" 
                                borderRadiusWhenStacked: "all", // "all"/"last"
                                columnWidth: '20%',
                            },
                        },
                        dataLabels: {
                            enabled: false
                        },
                        // stroke: {
                        //     width: 5,
                        //     colors: ['#fff']
                        //   },
                        yaxis: {
                            min: -200,
                            max: 300,
                            tickAmount: 5,
                        },
                        xaxis: {
                            categories: [' Jan ', 'Feb', 'Mar', 'Apr',
                                'May', 'Jun', 'Jul', 'Aug', 'Sep'
                            ],
                        },
                        legend: {show: false},
                        fill: {
                            opacity: 1
                        }
                    };

                    var chart = new ApexCharts(document.querySelector("#sales_charts1"), options);
                    chart.render();
                }
            });

        </script>
    </body>
</html>