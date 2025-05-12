<%-- 
    Document   : stockBalanceReportSerialNumberOrExpiryDateWise
    Created on : May 6, 2024, 4:15:00 PM
    Author     : Rajeev kumar
--%>

<%@page import="com.AvMeditechInventory.dtos.ProductDto"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            .customer-item {
                padding: 8px;
                cursor: pointer;
            }

            .customer-item.active {
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
            .product-item:hover {
                background-color: #ddd;
            }
            /* Table styling for larger screens */
            .table-responsive {
                display: none;
            }

            /* Card container styling for mobile screens */
            .card-container {
                display: none;
            }

            .card-section {
                background: white;
                border: 1px solid #ccc;
                border-radius: 5px;
                padding: 15px;
                margin-bottom: 10px;
            }

            .card-section h2 {
                font-size: 16px;
                margin: 5px 0;
            }

            .card-section span {
                font-weight: normal;
            }

            @media (min-width: 768px) {
                /* Show table on larger screens */
                .table-responsive {
                    display: block;
                }

                /* Hide cards on larger screens */
                .card-container {
                    display: none;
                }
            }

            @media (max-width: 767px) {
                /* Show cards on mobile screens */
                .card-container {
                    display: block;
                }

                /* Hide table on mobile screens */
                .table-responsive {
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
            <!-- Header and Sidebar -->
            <jsp:include page="header.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>

                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header">
                            <div class="add-item d-flex">
                                <div class="page-title mob-view">
                                    <h4>Stock Report Serial Number Wise</h4>
                                    <h6>Manage your Stock Report Serial Number Wise</h6>
                                </div>							
                            </div>
                            <ul class="table-top-head mob-view">

                                <li>
                                    <a href="${pageContext.request.contextPath}/stockReportSerialNumberWiseExportToExcel?&brand=${selectedBrandId}&productType=${selectedProductTypeId}&startDate=${startDate}&endDate=${endDate}&productName=${productName}"
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
                                    <form name="saleForm" action="" method="get" class="row">
                                        <div class="row">
                                            <div class="col-lg-3 col-md-6 col-sm-12">
                                                <div class="mb-3 add-product">
                                                    <label for="startDate" class="form-label">Expiry Date Range</label>
                                                    <input type="date" class="form-control" id="startDate" name="startDate" value="${startDate}">
                                                </div>
                                            </div>
                                            <div class="col-lg-3 col-md-6 col-sm-12">
                                                <div class="mb-3 add-product">
                                                    <label for="endDate" class="form-label"></label>
                                                    <input type="date" class="form-control" id="endDate" name="endDate" value="${endDate}">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-lg-3 col-md-6 col-sm-12">
                                            <div class="mb-3 add-product">
                                                <label for="productName" class="form-label">Product Name</label>
                                                <input type="text" class="form-control" id="productName" name="productName" 
                                                       autocomplete="off" placeholder="Enter Product Name" value="${productName}">
                                                <div id="productList" class="product-list" style="display: none; border: 1px solid #ccc; position: absolute; background-color: white; z-index: 1000;">
                                                    <!-- Suggestions will be dynamically added here -->
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-lg-4 col-sm-6 col-12">
                                            <div class="mb-3 add-product">
                                                <label class="form-label">Brand</label>
                                                <select class="select" name="brand">
                                                    <option value="">Select Brand</option>
                                                    <c:forEach var="brand" items="${categoryList}">
                                                        <option value="${brand.categoryName}" ${brand.categoryName == selectedBrandId ? 'selected' : ''}>${brand.categoryName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-4 col-sm-6 col-12">
                                            <div class="mb-3 add-product">
                                                <label class="form-label">Product Type</label>
                                                <select class="select" name="productType">
                                                    <option value="">Select Product Type</option>
                                                    <c:forEach var="productType" items="${productTypeList}">
                                                        <option value="${productType.productTypeName}" ${productType.productTypeName == selectedProductTypeId ? 'selected' : ''}>${productType.productTypeName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="col-12">
                                            <div class="mb-3 add-product">
                                                <button type="button" onclick="getSaleData()" class="btn btn-submit sub-btn">Submit</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <!-- /Filter -->
                            <div class="card" id="filter_inputs">
                                <div class="card-body pb-0">
                                    <div class="row">
                                        <div class="col-lg-2 col-sm-6 col-12">
                                            <div class="input-blocks">
                                                <i data-feather="archive" class="info-img"></i>
                                                <select class="select">
                                                    <option>Choose Warehouse</option>
                                                    <option>Lobar Handy</option>
                                                    <option>Quaint Warehouse</option>
                                                    <option>Traditional Warehouse</option>
                                                    <option>Cool Warehouse</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-2 col-sm-6 col-12">
                                            <div class="input-blocks">
                                                <i data-feather="box" class="info-img"></i>
                                                <select class="select">
                                                    <option>Choose Product</option>
                                                    <option>Nike Jordan</option>
                                                    <option>Apple Series 5 Watch</option>
                                                    <option>Amazon Echo Dot</option>
                                                    <option>Lobar Handy</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-2 col-sm-6 col-12">
                                            <div class="input-blocks">
                                                <i data-feather="calendar" class="info-img"></i>
                                                <div class="input-groupicon">
                                                    <input type="text" class="datetimepicker" placeholder="Choose Date" >
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-2 col-sm-6 col-12">
                                            <div class="input-blocks">
                                                <i data-feather="user" class="info-img"></i>
                                                <select class="select">
                                                    <option>Choose Person</option>
                                                    <option>Steven</option>
                                                    <option>Gravely</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-4 col-sm-6 col-12 ms-auto">
                                            <div class="input-blocks">
                                                <a class="btn btn-filters ms-auto"> <i data-feather="search" class="feather-search"></i> Search </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- /Filter -->
                            <div class="table-responsive">
                                <table class="table datanew1">
                                    <thead>
                                        <tr>
                                            <th>Stock Number</th>
                                            <!--<th>Item Description</th>-->
                                            <th>Product Type</th>
                                            <th>Brand</th>
                                            <!--<th>Date</th>-->
                                            <th>Serial Number</th>
                                            <th>Expiry Date</th>
                                            <th>Bar Code</th>



                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:choose>
                                            <c:when test="${not empty stockReportSerialNumberWiseList}">
                                                <c:forEach var="userResponse" items="${stockReportSerialNumberWiseList}">
                                                    <tr>
                                                        <td>${userResponse.sku}</td>
                                                        <td>${userResponse.productTypeName}</td>
                                                        <td>${userResponse.categoryName}</td>
                                                        <td>${userResponse.serialNumber}</td>
                                                        <td>${userResponse.expiryDate}</td>
                                                        <td>${userResponse.itemBarCode}</td>
                                                    </tr>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <tr>
                                                    <td colspan="6">No stock report data available.</td>
                                                </tr>
                                            </c:otherwise>
                                        </c:choose>
                                    </tbody>



                                </table>

                                <div class="container mt-2 mb-4">
                                    <div class="d-flex justify-content-end">
                                        <div class="page-btn">
                                            <c:if test="${currentPage > 1}">
                                                <a href="${pageContext.request.contextPath}/getDetailstockReportSerialNumberWise?pageNumber=${currentPage - 1}&pageSize=${pageSize}&brand=${selectedBrandId}&productType=${selectedProductTypeId}&startDate=${startDate}&endDate=${endDate}&productName=${productName}" class="btn btn-secondary">Previous</a>
                                            </c:if>
                                        </div>&nbsp;&nbsp;
                                        <div class="page-btn">
                                            <c:if test="${currentPage < noOfPages}">
                                                <a href="${pageContext.request.contextPath}/getDetailstockReportSerialNumberWise?pageNumber=${currentPage + 1}&pageSize=${pageSize}&brand=${selectedBrandId}&productType=${selectedProductTypeId}&startDate=${startDate}&endDate=${endDate}&productName=${productName}" class="btn btn-secondary">Next</a>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>



                            </div>

                        </div>
                    </div>
                    <!-- /product list -->
                    <!-- Card container for mobile screens -->
                    <div class="card-container">
                        <c:if test="${not empty stockReportSerialNumberWiseList}">
                            <c:forEach var="userResponse" items="${stockReportSerialNumberWiseList}">
                                <div class="card-section">
                                    <h2>Stock Number: <span>${userResponse.sku}</span></h2>
                                    <h2>Product Type: <span>${userResponse.productTypeName}</span></h2>
                                    <h2>Brand: <span>${userResponse.categoryName}</span></h2>
                                    <h2>Serial Number: <span>${userResponse.serialNumber}</span></h2>
                                    <h2>Expiry Date: <span>${userResponse.expiryDate}</span></h2>
                                </div>
                            </c:forEach>
                        </c:if>
                    </div>
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
                                                            "searching": true, // Enable searching (default is true)
                                                            "ordering": true, // Enable ordering (default is true)
                                                            "info": false, // Hide information display
                                                            "responsive": true  // Enable responsive mode
                                                                    // Additional options and configurations can be added here
                                                        });
                                                    });

                                                    function getSaleData() {

                                                        document.saleForm.action = "getDetailstockReportSerialNumberWise";
                                                        document.saleForm.submit();
                                                    }

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
                url: "${pageContext.request.contextPath}/productList/json",
                data: {
                    productName: productName.toLowerCase()
                },
                success: function (response) {
                    console.log("Response received:", response);
                    var suggestionsHtml = "";
                    response.forEach(function (product) {
                        if (product.productName.toLowerCase().includes(productName.toLowerCase())) {
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





</body>
</html>