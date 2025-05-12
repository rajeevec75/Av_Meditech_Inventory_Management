<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.net.URLEncoder" %>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="POS - Bootstrap Admin Template">
        <meta name="keywords" content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
        <meta name="author" content="Dreamguys - Bootstrap Admin Template">
        <meta name="robots" content="noindex, nofollow">
        <title>Av Meditech Inventory Management</title>

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/av-meditech-fevicon.png">


        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">

        <!-- animation CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/animate.css">

        <!-- Select2 CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/select2/css/select2.min.css">

        <!-- Datetimepicker CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-datetimepicker.min.css">

        <!-- Datatable CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dataTables.bootstrap5.min.css">

        <!-- Fontawesome CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome/css/all.min.css">

        <!-- Main CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

        <style>
            /* Ensure the design only shows on mobile screens */
            /* Ensure the design only shows on mobile screens */
            @media (max-width: 768px) {
                .card-container {
                    display: flex;
                    flex-wrap: wrap;
                    justify-content: space-around;
                    background-color: white;
                    padding: 10px;
                }
                .card{
                    border: 1px solid blue;
                }

                .card-section {
                    background-color: white;
                    border: 1px solid #007bff; /* Use your logo's blue color or grey */
                    border-radius: 5px;
                    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                    width: 100%;
                    margin: 10px 0;
                    padding: 15px;
                    box-sizing: border-box;
                    position: relative;
                }

                .card-section h2 {
                    font-size: 18px;
                }

                .card-section p {
                    font-size: 17px;
                    margin: 5px 0;
                }

                .action-buttons {
                    display: flex;
                    justify-content: center;
                    margin-top: 10px;
                    position: absolute;
                    top: 52px;
                    right: 0;
                }

                .edit-btn, .delete-btn {
                    text-decoration: none;
                    color: white;
                    padding: 10px;
                    border-radius: 3px;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    width: 40px;
                    height: 40px;
                }
                .sub-btn{
                    width:100%
                }
                .edit-btn {
                    background-color: #fff;
                    color: blue;
                    border: 1px solid #b991f5;
                    border-radius: 8px;
                }

                .delete-btn {
                    background-color: #fff;
                    color: red;
                    border: 1px solid #b991f5;
                    border-radius: 8px;
                }

                .edit-btn i, .delete-btn i {
                    font-size: 18px;
                }

                .action-buttons a {
                    margin: 0 5px;
                }
            }
            /* Default to desktop view */
            .table-responsive {
                display: block;
            }

            .card-container {
                display: none;
            }

            /* Mobile view */
            @media (max-width: 768px) {
                .table-responsive {
                    display: none;
                }

                .card-container {
                    display: block;
                }
                .card-view-section{
                    display: none;
                }
                .mob-view{
                    display: none;
                }
            }


        </style>

    </head>
    <body>

        <div id="global-loader" >
            <div class="whirly-loader"> </div>
        </div>

        <!-- Main Wrapper -->
        <div class="main-wrapper">
            <jsp:include page="header.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>
            </div>



            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="add-item d-flex">
                            <div class="page-title">
                                <h4>Quick Search With Serial Number</h4>
                                <h6>Manage your Quick Search With Serial Number</h6>
                            </div>
                        </div>
                        <ul class="table-top-head">

                            <li>
                                <a href="${pageContext.request.contextPath}/quickSearchExportToExcel?searchValue=${URLEncoder.encode(searchValue, 'UTF-8')}" data-bs-toggle="tooltip" data-bs-placement="top" title="Export to Excel">
                                <img src="${pageContext.request.contextPath}/image/icons/excel.svg" alt="Export to Excel">
                            </a>
                        </li>



                        <li>
                            <a data-bs-toggle="tooltip" data-bs-placement="top" title="Collapse" id="collapse-header"><i data-feather="chevron-up" class="feather-chevron-up"></i></a>
                        </li>
                    </ul>

                </div>
                <!-- /product list -->
                <div class="card table-list-card">
                    <div class="card-body">
                        <div class="table-top">
                            <form action="quickSearchWithSerialNumber" method="post">
                                <div class="row">

                                    <div class="mb-3 add-product">
                                        <label class="form-label">Search Value</label>
                                        <input type="text" class="form-control" name="searchValue" value="${searchValue}" autocomplete="off">
                                    </div>



                                    <div class="col-lg-4 col-sm-6 col-12">
                                        <div class="mb-3 add-product">
                                            <button type="submit" class="btn btn-submit sub-btn">Submit</button>
                                        </div>
                                    </div>
                                </div>
                            </form>


                        </div>

                        <div class="table-responsive">
                            <table class="table  datanew1">

                                <thead>
                                    <tr>
                                        <th>Voucher Number</th>
                                        <th>Voucher Type</th>
                                        <th>Transaction Date</th>
                                        <th>Serial/Batch Number</th>
                                        <th>Bar Code</th>
                                        <th>Expiry Date</th>
                                        <th>Particulars</th>
                                        <th>Mobile Number</th>
                                        <th>Item Description</th>
                                        <th>Product Type</th>
                                        <th>Brand</th>
                                        <!--                                        <th>Net Amount</th>-->

                                    </tr>
                                </thead>
                                <c:forEach var="transaction" items="${quickSearchWithSerialNumberList}">
                                    <tr>
                                        <td>${transaction.id}</td>
                                        <td>${transaction.voucherType}</td>
                                        <td>${transaction.voucherDate}</td>
                                        <td>${transaction.itemSerialNumber}</td>
                                        <td>${transaction.itemBarcode}</td>
                                        <td>${transaction.itemSerialExpiryDate}</td>
                                        <td>${transaction.companyName}</td>
                                        <td>${transaction.mobileNumber}</td>
                                        <td>${transaction.productName}</td>
                                        <td>${transaction.brandName}</td>
                                        <td>${transaction.productTypeName}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>

                    </div>
                </div>
            </div>
            <!-- /product list -->
            <!-- Cards visible only on mobile screens -->
            <div class="row mt-4 d-block d-lg-none m-2">
                <div class="col-12">
                    <div class="card-deck">
                        <!-- Iterate through quickSearchWithSerialNumberList -->
                        <c:forEach var="transaction" items="${quickSearchWithSerialNumberList}">
                            <div class="card mb-3" >
                                <div class="card-body">
                                    <h5 class="card-title">Voucher Type: ${transaction.voucherType}</h5>
                                    <p class="card-text">Voucher Date: ${transaction.voucherDate}</p>
                                    <p class="card-text">Voucher Number: ${transaction.id}</p>
                                    <p class="card-text">Particulars: ${transaction.particulars}</p>
                                    <p class="card-text">Mobile Number: ${transaction.mobileNumber}</p>
                                    <p class="card-text">Net Amount: ${transaction.amount}</p>
                                </div>
                            </div>
                        </c:forEach>
                        <!-- Display message if quickSearchWithSerialNumberList is empty -->
                        <c:if test="${empty quickSearchWithSerialNumberList}">
                            <div class="card mb-3">
                                <div class="card-body">
                                    <p class="card-text text-center">No quick search results found</p>
                                </div>
                            </div>
                        </c:if>


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

    <!-- Datatable JS -->
    <script src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/dataTables.bootstrap5.min.js"></script>

    <!-- Bootstrap Core JS -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>

    <!-- Select2 JS -->
    <script src="${pageContext.request.contextPath}/plugins/select2/js/select2.min.js"></script>

    <!-- Datetimepicker JS -->
    <script src="${pageContext.request.contextPath}/js/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker.min.js"></script>

    <!-- Sweetalert 2 -->
    <script src="${pageContext.request.contextPath}/plugins/sweetalert/sweetalert2.all.min.js"></script>
    <script src="${pageContext.request.contextPath}/plugins/sweetalert/sweetalerts.min.js"></script>

    <!-- Custom JS -->
    <script src="${pageContext.request.contextPath}/js/theme-script.js"></script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>

    <script>
        $(document).ready(function () {
            // Suppress DataTables warning alerts
            $.fn.dataTable.ext.errMode = 'none';

            // Check if the table is already initialized
            if ($.fn.DataTable.isDataTable('.datanew1')) {
                // Destroy the existing instance
                $('.datanew1').DataTable().clear().destroy();
            }

            // Initialize DataTable
            $('.datanew1').DataTable({
                "paging": false, // Disable pagination within DataTables
                "searching": false, // Enable searching (default is true)
                "ordering": true, // Enable ordering (default is true)
                "info": false, // Hide information display
                "responsive": true  // Enable responsive mode
                        // Additional options and configurations can be added here
            });
        });

    </script>

</body>
</html>