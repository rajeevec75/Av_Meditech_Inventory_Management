<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.net.URLEncoder" %>
<%-- 
    Document   : stockBalanceReportSerialNumberOrExpiryDateWise
    Created on : May 6, 2024, 4:15:00 PM
    Author     : Rajeev kumar
--%>

<!DOCTYPE html>
<html lang="en">
    <head>
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

            .product-item {
                padding: 8px;
                cursor: pointer;
            }

            .product-item.active {
                background-color: #f1f1f1;
            }
            #productList {
                max-height: 200px;
                overflow-y: auto;
            }
            .product-item {
                padding: 8px;
                cursor: pointer;
            }
            .customer-item {
                padding: 8px;
                cursor: pointer;
            }

            .customer-item.active {
                background-color: #f1f1f1;
            }
            .product-item:hover {
                background-color: #ddd;
            }
            #customerList {
                max-height: 200px;
                overflow-y: auto;
            }
            .customer-item {
                padding: 8px;
                cursor: pointer;
            }
            .customer-item:hover {
                background-color: #ddd;
            }
            @media (max-width: 768px) {
                .card-container {
                    display: flex;
                    flex-wrap: wrap;
                    gap: 20px;
                }
                .sub-btn{
                    width:100%
                }
                .card {
                    background: white;
                    border: 1px solid #ccc;
                    border-radius: 10px;
                    padding: 20px;
                    width: 100%;
                    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                }

                .card-body p {
                    margin: 0 0 10px;
                    font-size: 20px
                }

                .text-center {
                    text-align: center;
                }

                .badge-bgsuccess {
                    background-color: green;
                    color: white;
                    padding: 5px 10px;
                    border-radius: 5px;
                }

                .action-set {
                    cursor: pointer;
                    display: inline-block;
                }

                .dropdown-menu {
                    display: none;
                    position: absolute;
                    background-color: white;
                    border: 1px solid #ccc;
                    border-radius: 5px;
                    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                }

                .action-set:hover .dropdown-menu {
                    display: block;
                }
            }
            @media (min-width: 768px) {
                .card-container {
                    display: none;
                }

                .table-container {
                    display: block;
                }

            }
            .autocomplete-list {
                position: absolute;
                background: #fff;
                max-height: 150px;
                overflow-y: auto;
                width: 100%;
                z-index: 1000;
            }
            .autocomplete-list div {
                padding: 8px;
                cursor: pointer;
            }
            .autocomplete-list div:hover, .autocomplete-list .selected {
                background: #f0f0f0;
            }
        </style>
    </head>
    <body>

        <div id="global-loader" >
            <div class="whirly-loader"> </div>
        </div>

        <!-- Main Wrapper -->
        <div class="main-wrapper">
            <!-- Header and Sidebar -->
            <jsp:include page="header.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>

                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header">
                            <div class="add-item d-flex">
                                <div class="page-title mob-view">
                                    <h4>Stock Report Product Wise</h4>
                                    <h6>Manage your Stock Report Product Wise</h6>
                                </div>							
                            </div>
                            <ul class="table-top-head mob-view">

                                <li>
                                    <a href="${pageContext.request.contextPath}/stockReportProductWiseExportToExcel?productName=${URLEncoder.encode(productName, 'UTF-8')}&productType=${URLEncoder.encode(productType, 'UTF-8')}&brand=${URLEncoder.encode(brand, 'UTF-8')}" 
                                   data-bs-toggle="tooltip" data-bs-placement="top" title="Export to Excel">
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
                                <div class="row">
                                    <form id="stockReportProductWise" name="stockReportProductWise" onkeypress="return disableEnterSubmit(event);">
                                        <div class="row">
                                            <div class="col-lg-3 col-md-6 col-sm-12">
                                                <div class="mb-4 add-product">
                                                    <label for="productName" class="form-label">Product Name</label>
                                                    <input type="text" class="form-control" id="productName" name="productName"  
                                                           autocomplete="off" placeholder="Enter Product Name" value="${productName}">
                                                    <div id="productList" class="product-list" style="display: none; border: 1px solid #ccc; position: absolute; background-color: white; z-index: 1000;">
                                                        <!-- Suggestions will be dynamically added here -->
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="col-lg-4 col-sm-6 col-12">
                                                <div class="mb-4 add-product" style="position: relative;">
                                                    <label class="form-label">Product Type</label>
                                                    <input type="text" id="categoryInput" class="form-control" placeholder="Enter Product Type" autocomplete="off" required>
                                                    <input type="hidden" id="categoryIdInput" name="brand">
                                                    <div id="category-autocomplete-list" class="autocomplete-list"></div>
                                                </div>
                                            </div>

                                            <div class="col-lg-4 col-sm-6 col-12">
                                                <div class="mb-4 add-product" style="position: relative;">
                                                    <label class="form-label">Brand</label>
                                                    <input type="text" id="productTypeInput" class="form-control" placeholder="Enter Brand" autocomplete="off" required>
                                                    <input type="hidden" id="productTypeIdInput" name="productType">
                                                    <div id="productType-autocomplete-list" class="autocomplete-list"></div>
                                                </div>
                                            </div>
                                            <div class="col-12">
                                                <div class="mb-3 add-product">
                                                    <button type="button" onclick="getStockReportProductWise()" class="btn btn-submit sub-btn">Submit</button>
                                                </div>
                                            </div>
                                        </div>


                                    </form>
                                </div>
                            </div>

                            <div class="table-responsive">
                                <table class="table datanew1">
                                    <thead>
                                        <tr>
                                            <th hidden="">Id</th>
                                            <th>Stock Number</th>
                                            <th>Description</th>
                                            <th>Brand Name</th>
                                            <th>Product Type Name</th>
                                            <th>Quantity</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${stockReportProductWiseList}" var="userResponse">
                                            <tr>
                                                <td hidden="">${userResponse.id}</td>
                                                <td>${userResponse.sku}</td>
                                                <td>${userResponse.productName}</td>
                                                <td>${userResponse.productTypeName}</td>
                                                <td>${userResponse.categoryName}</td>
                                                <td>${userResponse.quantity}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                    <c:if test="${totalQuantity != 0}">
                                        <tbody>
                                            <tr>

                                                <td> </td>
                                                <td></td>
                                                <td></td>
                                                <td>Total</td>
                                                <td>${totalQuantity}</td>

                                            </tr>
                                        </tbody>
                                    </c:if>
                                </table>

                                <div class="container mt-2 mb-4">
                                    <div class="d-flex justify-content-end">
                                        <div class="page-btn">
                                            <c:if test="${currentPage > 1}">
                                                <a href="${pageContext.request.contextPath}/stockReportProductWise?pageNumber=${currentPage - 1}&pageSize=${pageSize}" class="btn btn-secondary">Previous</a>
                                            </c:if>
                                        </div>&nbsp;&nbsp;
                                        <div class="page-btn">
                                            <c:if test="${currentPage < noOfPages}">
                                                <a href="${pageContext.request.contextPath}/stockReportProductWise?pageNumber=${currentPage + 1}&pageSize=${pageSize}" class="btn btn-secondary">Next</a>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>










                            </div>
                        </div>
                        <!-- /product list -->
                        <!--add stock mobile view-->
                        <div class="card-container">
                            <c:if test="${not empty stockReportProductWiseList}">
                                <c:forEach items="${stockReportProductWiseList}" var="userResponse">
                                    <div class="card-section">
                                        <h2>Stock Number: ${userResponse.sku}</h2>
                                        <h2>Product: ${userResponse.productTypeName}</h2>
                                        <h2>Brand: ${userResponse.categoryName}</h2>
                                        <h2>Quantity: ${userResponse.quantity}</h2>
                                    </div>
                                </c:forEach>
                            </c:if>
                            <!-- Additional HTML or content can go here -->
                        </div>


                    </div>
                </div>
            </div>
            <!-- /Main Wrapper -->

            <!-- Add Adjustment -->
            <div class="modal fade" id="add-units">
                <div class="modal-dialog modal-dialog-centered stock-adjust-modal">
                    <div class="modal-content">
                        <div class="page-wrapper-new p-0">
                            <div class="content">
                                <div class="modal-header border-0 custom-modal-header">
                                    <div class="page-title">
                                        <h4>Add Adjustment</h4>
                                    </div>
                                    <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body custom-modal-body">
                                    <form action="stock-adjustment.html">
                                        <div class="input-blocks search-form">
                                            <label>Product</label>
                                            <input type="text" class="form-control">
                                            <i data-feather="search" class="feather-search"></i>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-6">
                                                <div class="input-blocks">
                                                    <label>Warehouse</label>
                                                    <select class="select">
                                                        <option>Choose</option>
                                                        <option>Lobar Handy</option>
                                                        <option>Quaint Warehouse</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-lg-6">
                                                <div class="input-blocks">
                                                    <label>Reference Number</label>
                                                    <input type="text" class="form-control">
                                                </div>
                                            </div>
                                            <div class="col-lg-12">
                                                <div class="modal-body-table">
                                                    <div class="table-responsive">
                                                        <table class="table  datanew">
                                                            <thead>
                                                                <tr>
                                                                    <th>Product</th>
                                                                    <th>SKU</th>
                                                                    <th>Category</th>
                                                                    <th>Qty</th>
                                                                    <th>Type</th>
                                                                    <th class="no-sort">Action</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <tr>
                                                                    <td>
                                                                        <div class="productimgname">
                                                                            <a href="javascript:void(0);" class="product-img stock-img">
                                                                                <img src="${pageContext.request.contextPath}/image/products/stock-img-02.png" alt="product">
                                                                            </a>
                                                                            <a href="javascript:void(0);">Nike Jordan</a>
                                                                        </div>												
                                                                    </td>
                                                                    <td>PT002</td>
                                                                    <td>Nike</td>
                                                                    <td>
                                                                        <div class="product-quantity">
                                                                            <span class="quantity-btn"><i data-feather="minus-circle" class="feather-search"></i></span>
                                                                            <input type="text" class="quntity-input" value="2">
                                                                            <span class="quantity-btn">+<i data-feather="plus-circle" class="plus-circle"></i></span>
                                                                        </div>
                                                                    </td>
                                                                    <td>
                                                                        <select class="select">
                                                                            <option>Addition</option>
                                                                            <option>Addition</option>
                                                                            <option>Addition</option>
                                                                        </select>
                                                                    </td>
                                                                    <td class="action-table-data">
                                                                        <div class="edit-delete-action">
                                                                            <a class="me-2 p-2" href="#" data-bs-toggle="modal" data-bs-target="#edit-units">
                                                                                <i data-feather="edit" class="feather-edit"></i>
                                                                            </a>
                                                                            <a class="confirm-text p-2" href="javascript:void(0);">
                                                                                <i data-feather="trash-2" class="feather-trash-2"></i>
                                                                            </a>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="col-lg-12">
                                                <div class="input-blocks">
                                                    <label>Responsible Person</label>
                                                    <select class="select">
                                                        <option>Choose</option>
                                                        <option>Steven</option>
                                                        <option>Gravely</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-lg-12">
                                            <div class="input-blocks summer-description-box">
                                                <label>Notes</label>
                                                <textarea class="form-control"></textarea>
                                            </div>
                                        </div>
                                        <div class="modal-footer-btn">
                                            <button type="button" class="btn btn-cancel me-2" data-bs-dismiss="modal">Cancel</button>
                                            <button type="submit" class="btn btn-submit">Create Adjustment</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /Add Adjustment -->

            <!-- Edit Adjustment -->
            <div class="modal fade" id="edit-units">
                <div class="modal-dialog modal-dialog-centered stock-adjust-modal">
                    <div class="modal-content">
                        <div class="page-wrapper-new p-0">
                            <div class="content">
                                <div class="modal-header border-0 custom-modal-header">
                                    <div class="page-title">
                                        <h4>Edit Adjustment</h4>
                                    </div>
                                    <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body custom-modal-body">
                                    <form action="stock-adjustment.html">
                                        <div class="input-blocks search-form">
                                            <label>Product</label>
                                            <input type="text" class="form-control" value="Nike Jordan">
                                            <i data-feather="search" class="feather-search"></i>
                                        </div>
                                        <div class="row">
                                            <div class="col-lg-6">
                                                <div class="input-blocks">
                                                    <label>Warehouse</label>
                                                    <select class="select">
                                                        <option>Lobar Handy</option>
                                                        <option>Quaint Warehouse</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-lg-6">
                                                <div class="input-blocks">
                                                    <label>Reference Number</label>
                                                    <input type="text" value="PT002">
                                                </div>
                                            </div>
                                            <div class="col-lg-12">
                                                <div class="modal-body-table">
                                                    <div class="table-responsive">
                                                        <table class="table  datanew">
                                                            <thead>
                                                                <tr>
                                                                    <th>Product</th>
                                                                    <th>SKU</th>
                                                                    <th>Category</th>
                                                                    <th>Qty</th>
                                                                    <th>Type</th>
                                                                    <th class="no-sort">Action</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <tr>
                                                                    <td>
                                                                        <div class="productimgname">
                                                                            <a href="javascript:void(0);" class="product-img stock-img">
                                                                                <img src="${pageContext.request.contextPath}/image/products/stock-img-02.png" alt="product">
                                                                            </a>
                                                                            <a href="javascript:void(0);">Nike Jordan</a>
                                                                        </div>												
                                                                    </td>
                                                                    <td>PT002</td>
                                                                    <td>Nike</td>
                                                                    <td>
                                                                        <div class="product-quantity">
                                                                            <span class="quantity-btn"><i data-feather="minus-circle" class="feather-search"></i></span>
                                                                            <input type="text" class="quntity-input" value="2">
                                                                            <span class="quantity-btn">+<i data-feather="plus-circle" class="plus-circle"></i></span>
                                                                        </div>
                                                                    </td>
                                                                    <td>
                                                                        <select class="select">
                                                                            <option>Addition</option>
                                                                            <option>Addition</option>
                                                                            <option>Addition</option>
                                                                        </select>
                                                                    </td>
                                                                    <td class="action-table-data">
                                                                        <div class="edit-delete-action">
                                                                            <a class="me-2 p-2" href="#" data-bs-toggle="modal" data-bs-target="#edit-units">
                                                                                <i data-feather="edit" class="feather-edit"></i>
                                                                            </a>
                                                                            <a class="confirm-text p-2" href="javascript:void(0);">
                                                                                <i data-feather="trash-2" class="feather-trash-2"></i>
                                                                            </a>
                                                                        </div>

                                                                    </td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="col-lg-12">
                                                <div class="input-blocks">
                                                    <label>Responsible Person</label>
                                                    <select class="select">
                                                        <option>Steven</option>
                                                        <option>Gravely</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-lg-12">
                                            <div class="input-blocks summer-description-box">
                                                <label>Notes</label>
                                                <textarea class="form-control">The Jordan brand is owned by Nike (owned by the Knight family), as, at the time, the company was building its strategy to work with athletes to launch shows that could inspire consumers.Although Jordan preferred Converse and Adidas, they simply could not match the offer Nike made. </textarea>
                                            </div>
                                        </div>
                                        <div class="modal-footer-btn">
                                            <button type="button" class="btn btn-cancel me-2" data-bs-dismiss="modal">Cancel</button>
                                            <button type="submit" class="btn btn-submit">Save Changes</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /Edit Adjustment -->

            <!-- View Notes -->
            <div class="modal fade" id="view-notes">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="page-wrapper-new p-0">
                            <div class="content">
                                <div class="modal-header border-0 custom-modal-header">
                                    <div class="page-title">
                                        <h4>Notes</h4>
                                    </div>
                                    <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body custom-modal-body">
                                    <p>The Jordan brand is owned by Nike (owned by the Knight family), as, at the time, the company was building its strategy to work with athletes to launch shows that could inspire consumers.Although Jordan preferred Converse and Adidas, they simply could not match the offer Nike made. Jordan also signed with Nike because he loved the way they wanted to market him with the banned colored shoes. Nike promised to cover the fine Jordan would receive from the NBA.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


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

            <!-- Datetimepicker JS -->
            <script src="${pageContext.request.contextPath}/js/moment.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker.min.js"></script>

            <!-- Select2 JS -->
            <script src="${pageContext.request.contextPath}/plugins/select2/js/select2.min.js"></script>

            <!-- Sweetalert 2 -->
            <script src="${pageContext.request.contextPath}/plugins/sweetalert/sweetalert2.all.min.js"></script>
            <script src="${pageContext.request.contextPath}/plugins/sweetalert/sweetalerts.min.js"></script>

            <!-- Custom JS --><script src="${pageContext.request.contextPath}/js/theme-script.js"></script>	
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


            <script>
                var typingTimer; // Timer identifier
                var doneTypingInterval = 300; // Delay in milliseconds (adjust as needed)
                var minInputLength = 1; // Minimum input length to trigger Ajax call
                var productFocus = -1; // Index for the currently focused product in the suggestion list

                document.getElementById('productName').addEventListener('keyup', function (event) {
                    clearTimeout(typingTimer);
                    if (event.keyCode === 40 || event.keyCode === 38 || event.keyCode === 13) {
                        handleProductKeyEvents(event);
                    } else {
                        var inputValue = this.value.trim();
                        if (inputValue.length >= minInputLength) {
                            typingTimer = setTimeout(function () {
                                fetchProductList(inputValue);
                            }, doneTypingInterval);
                        } else {
                            document.getElementById("productList").style.display = "none";
                        }
                    }
                });

                function fetchProductList(productName) {
                    var productListDiv = document.getElementById("productList");

                    // Perform Ajax request to get the product list
                    $.ajax({
                        type: "GET",
                        url: "${pageContext.request.contextPath}/productList1/json",
                        data: {
                            productName: productName.toLowerCase()
                        },
                        success: function (response) {
                            console.log("Response received:", response);
                            var suggestionsHtml = "";
                            response.forEach(function (product) {
                                if (product.productName.toLowerCase().includes(productName.toLowerCase()) || product.productSku.toLowerCase().includes(productName.toLowerCase())) {
                                    suggestionsHtml += '<div class="suggestion-item product-item" onclick="selectProduct(\'' + product.productName + '\')">' +
                                            product.productName + '</div>';
                                }

                            });
                            productListDiv.innerHTML = suggestionsHtml;
                            productListDiv.style.display = suggestionsHtml ? "block" : "none";
                            productFocus = -1; // Reset the focus index
                        },
                        error: function (xhr, status, error) {
                            console.error("Error: " + error);
                            productListDiv.innerHTML = "Error occurred while fetching data.";
                            productListDiv.style.display = "block";
                        }
                    });
                }

                function selectProduct(productName) {
                    document.getElementById("productName").value = productName;
                    document.getElementById("productList").style.display = "none";
                    productFocus = -1;
                }

                function handleProductKeyEvents(event) {
                    let x = document.getElementById("productList");
                    if (!x)
                        return false;

                    let items = Array.from(x.getElementsByClassName("product-item")).filter(item => item.style.display !== 'none');
                    if (event.keyCode == 40) { // Down key
                        productFocus++;
                        addProductActive(items);
                        scrollProductIntoView(items);
                    } else if (event.keyCode == 38) { // Up key
                        productFocus--;
                        addProductActive(items);
                        scrollProductIntoView(items);
                    } else if (event.keyCode == 13) { // Enter key
                        event.preventDefault();
                        if (productFocus > -1) {
                            if (items)
                                items[productFocus].click();
                        }
                    }
                }

                function addProductActive(items) {
                    if (!items)
                        return false;
                    removeProductActive(items);
                    if (productFocus >= items.length)
                        productFocus = 0;
                    if (productFocus < 0)
                        productFocus = items.length - 1;
                    items[productFocus].classList.add("active");
                }

                function removeProductActive(items) {
                    for (let i = 0; i < items.length; i++) {
                        items[i].classList.remove("active");
                    }
                }

                function scrollProductIntoView(items) {
                    if (productFocus >= 0 && productFocus < items.length) {
                        items[productFocus].scrollIntoView({behavior: "smooth", block: "nearest"});
                    }
                }

                document.addEventListener('click', function (event) {
                    var isClickInside = document.getElementById('productName').contains(event.target) ||
                            document.getElementById('productList').contains(event.target);
                    if (!isClickInside) {
                        document.getElementById('productList').style.display = 'none';
                    }
                });
            </script>

            <script>
                function getStockReportProductWise() {

                    document.stockReportProductWise.action = "stockReportProductWise";
                    document.stockReportProductWise.submit();
                }
                // Disable form submission on Enter key
                function disableEnterSubmit(event) {
                    if (event.key === "Enter") {
                        event.preventDefault();
                        return false;
                    }
                }
            </script>

            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    const productTypeInput = document.getElementById("productTypeInput");
                    const productTypeIdInput = document.getElementById("productTypeIdInput");
                    const autocompleteList = document.getElementById("productType-autocomplete-list");
                    let selectedIndex = -1;

                    // Get the selected product type from JSP
                    const selectedProductType = "${productType}";
                    console.log("Selected Product Type:", selectedProductType);

                    // Convert JSP productTypeList into JavaScript array
                    const productTypeList = [
                <c:forEach items="${productTypeList}" var="productTypes">
                        {name: "${productTypes.productTypeName}"},
                </c:forEach>
                    ];

                    // Pre-fill input field if a product type is already selected
                    if (selectedProductType) {
                        const selectedProduct = productTypeList.find(p => p.name === selectedProductType);
                        if (selectedProduct) {
                            productTypeInput.value = selectedProduct.name;
                            productTypeIdInput.value = selectedProduct.name;
                        }
                    }

                    function showSuggestions(value) {
                        autocompleteList.innerHTML = "";
                        selectedIndex = -1;

                        if (!value.trim())
                            return;

                        productTypeList.forEach((product, index) => {
                            if (product.name.toLowerCase().includes(value.toLowerCase())) {
                                const suggestionItem = document.createElement("div");
                                suggestionItem.textContent = product.name;
                                suggestionItem.setAttribute("data-index", index);
                                suggestionItem.classList.add("autocomplete-item");

                                suggestionItem.addEventListener("click", function () {
                                    selectItem(product.name);
                                });

                                autocompleteList.appendChild(suggestionItem);
                            }
                        });
                    }

                    function selectItem(value) {
                        productTypeInput.value = value;
                        productTypeIdInput.value = value;
                        autocompleteList.innerHTML = "";
                    }



                    productTypeInput.addEventListener("input", function () {
                        showSuggestions(this.value);

                        // ? Fix: If input is empty, clear hidden productTypeIdInput
                        if (!this.value.trim()) {
                            productTypeIdInput.value = "";
                        }
                    });

                    productTypeInput.addEventListener("keydown", function (e) {
                        const items = autocompleteList.querySelectorAll(".autocomplete-item");
                        if (items.length === 0)
                            return;

                        if (e.key === "ArrowDown") {
                            selectedIndex = (selectedIndex + 1) % items.length;
                        } else if (e.key === "ArrowUp") {
                            selectedIndex = (selectedIndex - 1 + items.length) % items.length;
                        } else if (e.key === "Enter") {
                            if (selectedIndex > -1) {
                                const selectedItem = items[selectedIndex];
                                selectItem(selectedItem.textContent);
                            }
                            e.preventDefault();
                        }

                        items.forEach((item, i) => {
                            item.classList.toggle("selected", i === selectedIndex);
                        });

                        if (selectedIndex >= 0 && selectedIndex < items.length) {
                            items[selectedIndex].scrollIntoView({block: "nearest", behavior: "smooth"});
                        }
                    });

                    document.addEventListener("click", function (e) {
                        if (!productTypeInput.contains(e.target) && !autocompleteList.contains(e.target)) {
                            autocompleteList.innerHTML = "";
                            selectedIndex = -1;
                        }
                    });
                });

            </script>

            <script>
                document.addEventListener("DOMContentLoaded", function () {
                    const categoryInput = document.getElementById("categoryInput");
                    const categoryIdInput = document.getElementById("categoryIdInput");
                    const autocompleteList = document.getElementById("category-autocomplete-list");
                    let selectedIndex = -1;

                    // Get the selected category from JSP
                    const selectedCategory = "${brand}";

                    // Convert JSP categoryList into JavaScript array
                    const categoryList = [
                <c:forEach items="${categoryList}" var="category">
                        {name: "${category.categoryName}"},
                </c:forEach>
                    ];

                    // Pre-fill input field if a category is already selected
                    if (selectedCategory) {
                        const selectedCat = categoryList.find(c => c.name === selectedCategory);
                        if (selectedCat) {
                            categoryInput.value = selectedCat.name;
                            categoryIdInput.value = selectedCat.name;
                        }
                    }

                    function showSuggestions(value) {
                        autocompleteList.innerHTML = "";
                        selectedIndex = -1;

                        if (!value.trim())
                            return;

                        categoryList.forEach((category, index) => {
                            if (category.name.toLowerCase().includes(value.toLowerCase())) {
                                const suggestionItem = document.createElement("div");
                                suggestionItem.textContent = category.name;
                                suggestionItem.setAttribute("data-index", index);
                                suggestionItem.classList.add("autocomplete-item");

                                suggestionItem.addEventListener("click", function () {
                                    categoryInput.value = category.name;
                                    categoryIdInput.value = category.name;
                                    autocompleteList.innerHTML = "";
                                });

                                autocompleteList.appendChild(suggestionItem);
                            }
                        });
                    }



                    categoryInput.addEventListener("input", function () {
                        showSuggestions(this.value);

                        if (!this.value.trim()) {
                            categoryIdInput.value = ""; // ? Agar manually delete kare toh bhi ID clear
                        }
                    });

                    categoryInput.addEventListener("keydown", function (e) {
                        const items = autocompleteList.querySelectorAll(".autocomplete-item");
                        if (items.length === 0)
                            return;

                        if (e.key === "ArrowDown") {
                            selectedIndex = (selectedIndex + 1) % items.length;
                        } else if (e.key === "ArrowUp") {
                            selectedIndex = (selectedIndex - 1 + items.length) % items.length;
                        } else if (e.key === "Enter") {
                            if (selectedIndex > -1) {
                                const selectedItem = items[selectedIndex];
                                categoryInput.value = selectedItem.textContent;
                                categoryIdInput.value = selectedItem.textContent;
                                autocompleteList.innerHTML = "";
                            }
                            e.preventDefault();
                        }

                        items.forEach((item, i) => {
                            item.classList.toggle("selected", i === selectedIndex);
                        });

                        // ? Improved Auto-scroll for Smooth Behavior
                        if (selectedIndex >= 0 && selectedIndex < items.length) {
                            const selectedItem = items[selectedIndex];
                            selectedItem.scrollIntoView({block: "center", behavior: "smooth"});
                        }
                    });

                    document.addEventListener("click", function (e) {
                        if (!categoryInput.contains(e.target) && !autocompleteList.contains(e.target)) {
                            autocompleteList.innerHTML = "";
                            selectedIndex = -1;
                        }
                    });
                });

            </script>

    </body>
</html>


