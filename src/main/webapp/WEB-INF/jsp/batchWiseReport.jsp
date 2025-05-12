<%-- 
    Document   : batchWiseReport
    Created on : 27-Jan-2025, 4:33:27 PM
    Author     : RAJEEV KUMAR
--%>

<%@page import="com.AvMeditechInventory.dtos.ProductDto"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.net.URLEncoder" %>


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


            .suggestion-item {
                padding: 10px;
                border-bottom: 1px solid #ccc;
                cursor: pointer;
            }
            .suggestion-item:hover,
            .suggestion-item.active {
                background-color: #f0f0f0;
            }
            .suggestion-list {
                border: 1px solid #ccc;
                max-height: 200px;
                overflow-y: auto;
                display: none;
                position: absolute;
                background-color: white;
                z-index: 1000;
                width: 100%;
            }
            .autocomplete-list {
                position: absolute;
                width: 100%;
                max-height: 150px;
                overflow-y: auto;
                background: #fff;
                z-index: 1000;
            }
            .autocomplete-item {
                padding: 8px;
                cursor: pointer;
            }
            .autocomplete-item:hover, .autocomplete-item.selected {
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

                                <h4>Batch Wise Report</h4>
                                <h6>Manage your Batch Wise Report</h6>

                            </div>							
                        </div>
                        <ul class="table-top-head mob-view">

                            <li>
                                <a href="${pageContext.request.contextPath}/exportBatchReportToExcel?startDate=${URLEncoder.encode(startDate, 'UTF-8')}&endDate=${URLEncoder.encode(endDate, 'UTF-8')}&batchNumber=${URLEncoder.encode(batchNumber, 'UTF-8')}&productName=${URLEncoder.encode(productName, 'UTF-8')}&brand=${URLEncoder.encode(selectedBrandId, 'UTF-8')}&productType=${URLEncoder.encode(selectedProductTypeId, 'UTF-8')}"
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

                                            <div class="col-lg-4 col-md-6 col-sm-12">
                                                <div class="mb-3 add-product">
                                                    <label for="startDate" class="form-label">Expiry Date Range</label>
                                                    <input type="date" class="form-control" id="startDate" name="startDate" value="${startDate}">
                                                </div>
                                            </div>

                                            <div class="col-lg-4 col-md-6 col-sm-12">
                                                <div class="mb-3 add-product">
                                                    <label for="endDate" class="form-label"> </label>
                                                    <input type="date" class="form-control" id="endDate" name="endDate" value="${endDate}">
                                                </div>
                                            </div>



                                            <div class="col-lg-4 col-md-6 col-sm-12">
                                                <div class="mb-3 add-product">
                                                    <label for="batchNumber" class="form-label">Batch Number</label>
                                                    <input type="text" class="form-control"  name="batchNumber" 
                                                           autocomplete="off" placeholder="Enter Batch Number" value="${batchNumber}">
                                                </div>
                                            </div>
                                        </div>



                                        <div class="row">
                                            <div class="col-lg-4 col-md-6 col-sm-12">
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
                                                <div class="mb-3 add-product" style="position: relative;">
                                                    <label class="form-label">Product Type</label>
                                                    <input type="text" id="categoryInput" class="form-control" placeholder="Enter Product Type" autocomplete="off" required>
                                                    <input type="hidden" id="categoryHiddenInput" name="brand">
                                                    <div id="category-autocomplete-list" class="autocomplete-list"></div>
                                                </div>
                                            </div>

                                            <div class="col-lg-4 col-sm-6 col-12">
                                                <div class="mb-3 add-product" style="position: relative;">
                                                    <label class="form-label">Brand</label>
                                                    <input type="text" id="brandInput" class="form-control" placeholder="Enter Brand" autocomplete="off" required>
                                                    <input type="hidden" id="brandHiddenInput" name="productType">
                                                    <div id="brand-autocomplete-list" class="autocomplete-list"></div>
                                                </div>
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
                                            <th>Brand</th>
                                            <th>Product Type</th>
                                            <th>Batch Number</th>
                                            <th>Expiry Date</th>
                                            <th>Quantity</th>
                                            <th>Action</th>



                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:choose>
                                            <c:when test="${not empty stockReportList}">
                                                <c:forEach var="userResponse" items="${stockReportList}">
                                                    <tr>
                                                        <td>${userResponse.sku}</td>
                                                        <td>${userResponse.productTypeName}</td>
                                                        <td>${userResponse.categoryName}</td>
                                                        <td>${userResponse.batchNo}</td>
                                                        <td>${userResponse.expiryDate}</td>
                                                        <td>${userResponse.quantity}</td>
                                                        <td class="action-table-data">
                                                            <a class="me-2 p-2 btn btn-primary" data-bs-toggle="modal" data-bs-target="#view-warranty" 
                                                               onclick="fetchData(
                                                                               '${userResponse.categoryName}',
                                                                               '${userResponse.productTypeName}',
                                                                               '${userResponse.batchNo}',
                                                                               '${userResponse.expiryDate}'
                                                                               )">
                                                                Update
                                                            </a>
                                                        </td>



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
                                    <c:if test="${totalQuantity != 0}">
                                        <tbody>
                                            <tr>


                                                <td></td>
                                                <td></td>
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
                                                <a href="${pageContext.request.contextPath}/batchWiseReport?pageNumber=${currentPage - 1}&pageSize=${pageSize}&brand=${selectedBrandId}&productType=${selectedProductTypeId}&startDate=${startDate}&endDate=${endDate}&productName=${productName}" class="btn btn-secondary">Previous</a>
                                            </c:if>
                                        </div>&nbsp;&nbsp;
                                        <div class="page-btn">
                                            <c:if test="${currentPage < totalPages}">
                                                <a href="${pageContext.request.contextPath}/batchWiseReport?pageNumber=${currentPage + 1}&pageSize=${pageSize}&brand=${selectedBrandId}&productType=${selectedProductTypeId}&startDate=${startDate}&endDate=${endDate}&productName=${productName}" class="btn btn-secondary">Next</a>
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

    <!-- Modal -->
    <div class="modal fade" id="view-warranty" tabindex="-1" aria-labelledby="view-warrantyLabel" aria-hidden="false">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="page-wrapper-new p-0">
                    <div class="content">
                        <div class="modal-header border-0 custom-modal-header">
                            <div class="page-title">
                                <h4>Update Form</h4>
                            </div>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body custom-modal-body">
                            <form action="updateBatchProductDetails?startDate=${startDate}&endDate=${endDate}&batchNumber=${batchNumber}&productName=${productName}&brand=${selectedBrandId}&productType=${selectedProductTypeId}" method="post">
                                <div class="row">
                                    <div class="col-lg-12">
                                        <!-- Secondary Product Search -->
                                        <input type="hidden" name="oldExpiryDate" id="oldExpiryDate">
                                        <input type="hidden" name="oldBatchNumber" id="oldBatchNumber">

                                        <div class="input-blocks image-upload-down">
                                            <label for="itemSerialNumber">Batch Number</label>
                                            <input type="text" id="batchNo" name="newBatchNumber" class="form-control" required>
                                        </div>
                                        <div class="input-blocks image-upload-down">
                                            <label for="expiryDate">Expiry Date</label>
                                            <input type="date" id="expiryDate" name="newExpiryDate" class="form-control" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-12">
                                    <div class="modal-footer-btn">
                                        <button type="button" class="btn btn-cancel me-2" data-bs-dismiss="modal">Cancel</button>
                                        <button type="submit" class="btn btn-submit">Submit</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- /Import -->







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

                                                                   function getSaleData() {

                                                                       document.saleForm.action = "batchWiseReport";
                                                                       document.saleForm.submit();
                                                                   }

    </script>

    <script>
        function fetchData(brand, productType, batchNumber, expiryDate) {
            console.log("Fetching data with parameters:", {brand, productType, batchNumber, expiryDate});

            if (!brand || !productType || !batchNumber || !expiryDate) {
                alert("All fields are required. Please enter the missing details.");
                return;
            }

            const expiryDatePattern = /^\d{4}-\d{2}-\d{2}$/;
            if (!expiryDatePattern.test(expiryDate)) {
                alert("Please enter the expiry date in the format yyyy-mm-DD.");
                return;
            }

            $.ajax({
                url: '${pageContext.request.contextPath}/api/batch/wise/report/details',
                method: 'GET',
                data: {
                    brand: brand,
                    productType: productType,
                    batchNumber: batchNumber,
                    expiryDate: expiryDate,
                },
                success: function (response) {
                    console.log("Response received:", response);

                    if (response.batchNo) {
                        $('#batchNo').val(response.batchNo);
                    }
                    if (response.expiryDate) {
                        $('#expiryDate').val(response.expiryDate);
                    }
                    if (response.productName) {
                        $('#secondaryProductName').val(response.productName);
                    }
                    if (response.batchNo) {
                        $('#oldBatchNumber').val(response.batchNo);
                    }
                    if (response.expiryDate) {
                        $('#oldExpiryDate').val(response.expiryDate);
                    }
                },
                error: function (xhr) {
                    let errorMessage = xhr.status === 500 ? "Internal Server Error (500). Please contact support." : xhr.responseText || "Please try again later.";
                    alert(`An error occurred: ${errorMessage}`);
                    console.error("Error details:", xhr.responseText);
                }
            });
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







    <!-- Secondary product name search -->
    <script>
        var typingTimer;
        var doneTypingInterval = 300;
        var minInputLength = 1;
        var productFocus = -1;
        // Prevent form submission when pressing Enter key
        document.getElementById('secondaryProductName').addEventListener('keydown', function (event) {
            if (event.keyCode === 13) {
                event.preventDefault(); // Prevent form submission
            }
        });
        // Handle keyup event for typing and navigating suggestions
        document.getElementById('secondaryProductName').addEventListener('keyup', function (event) {
            clearTimeout(typingTimer);
            // Prevent the default form submission on Enter
            if (event.keyCode === 13) {
                event.preventDefault();
            }
            // Handle arrow keys and Enter key
            if (event.keyCode === 40 || event.keyCode === 38 || event.keyCode === 13) {
                handleSecondaryProductKeyEvents(event);
            } else {
                var inputValue = this.value.trim();
                if (inputValue.length >= minInputLength) {
                    typingTimer = setTimeout(function () {
                        fetchSecondaryProductList(inputValue);
                    }, doneTypingInterval);
                } else {
                    document.getElementById("secondaryProductList").style.display = "none";
                }
            }
        });
        // Fetch product list based on input
        function fetchSecondaryProductList(newProductName) {
            var productListDiv = document.getElementById("secondaryProductList");
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/productList1/json",
                data: {
                    productName: newProductName.toLowerCase()
                },
                success: function (response) {
                    console.log("Response received:", response);
                    var suggestionsHtml = "";
                    response.forEach(function (product) {
                        if (product.productName.toLowerCase().includes(newProductName.toLowerCase()) || product.productSku.toLowerCase().includes(newProductName.toLowerCase())) {
                            suggestionsHtml += '<div class="suggestion-item product-item" onclick="selectSecondaryProduct(\'' + product.productName + '\')">' +
                                    product.productName + '</div>';
                        }
                    });
                    productListDiv.innerHTML = suggestionsHtml;
                    productListDiv.style.display = suggestionsHtml ? "block" : "none";
                    productFocus = -1;
                },
                error: function (xhr, status, error) {
                    console.error("Error: " + error);
                    productListDiv.innerHTML = "Error occurred while fetching data.";
                    productListDiv.style.display = "block";
                }
            });
        }
        // Select product when clicked or Enter pressed
        function selectSecondaryProduct(newProductName) {
            document.getElementById("secondaryProductName").value = newProductName;
            document.getElementById("secondaryProductList").style.display = "none";
            productFocus = -1;
        }
        // Handle arrow key navigation and Enter selection
        function handleSecondaryProductKeyEvents(event) {
            let x = document.getElementById("secondaryProductList");
            if (!x)
                return false;
            let items = Array.from(x.getElementsByClassName("product-item")).filter(item => item.style.display !== 'none');
            if (event.keyCode == 40) {
                productFocus++;
                addProductActive(items);
                scrollProductIntoView(items);
            } else if (event.keyCode == 38) {
                productFocus--;
                addProductActive(items);
                scrollProductIntoView(items);
            } else if (event.keyCode == 13) {
                event.preventDefault(); // Prevent form submission
                if (productFocus > -1 && items.length) {
                    items[productFocus].click();
                }
            }
        }
        // Add active class to focused item
        function addProductActive(items) {
            if (!items || !items.length)
                return;
            removeProductActive(items);
            if (productFocus >= items.length)
                productFocus = 0;
            if (productFocus < 0)
                productFocus = items.length - 1;
            items[productFocus].classList.add("active");
        }
        // Remove active class from all items
        function removeProductActive(items) {
            items.forEach(item => item.classList.remove("active"));
        }
        // Scroll the focused item into view
        function scrollProductIntoView(items) {
            if (productFocus > -1 && items[productFocus]) {
                items[productFocus].scrollIntoView({behavior: 'smooth', block: 'nearest'});
            }
        }
        // Hide the suggestion list if clicked outside
        document.addEventListener('click', function (event) {
            var isClickInside = document.getElementById('secondaryProductName').contains(event.target) ||
                    document.getElementById('secondaryProductList').contains(event.target);
            if (!isClickInside) {
                document.getElementById('secondaryProductList').style.display = 'none';
            }
        });
    </script>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            function setupAutocomplete(inputField, hiddenField, autocompleteListId, dataList) {
                const autocompleteList = document.getElementById(autocompleteListId);
                let selectedIndex = -1;

                function showSuggestions(value) {
                    autocompleteList.innerHTML = "";
                    selectedIndex = -1;

                    if (!value.trim())
                        return;

                    dataList.forEach((item, index) => {
                        if (item.toLowerCase().includes(value.toLowerCase())) {
                            const suggestionItem = document.createElement("div");
                            suggestionItem.textContent = item;
                            suggestionItem.setAttribute("data-index", index);
                            suggestionItem.classList.add("autocomplete-item");

                            suggestionItem.addEventListener("click", function () {
                                inputField.value = item;
                                hiddenField.value = item;
                                autocompleteList.innerHTML = "";
                            });

                            autocompleteList.appendChild(suggestionItem);
                        }
                    });
                }

                inputField.addEventListener("input", function () {
                    showSuggestions(this.value);

                    if (!this.value.trim()) {
                        hiddenField.value = ""; // ? Agar manually delete kare toh bhi ID clear hoga
                    }
                });

                inputField.addEventListener("keydown", function (e) {
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
                            inputField.value = selectedItem.textContent;
                            hiddenField.value = selectedItem.textContent;
                            autocompleteList.innerHTML = "";
                        }
                        e.preventDefault();
                    }

                    // Remove previous selection
                    items.forEach((item, i) => {
                        item.classList.toggle("selected", i === selectedIndex);
                    });

                    // ? Fix: Ensure selected item is always visible
                    if (selectedIndex >= 0 && selectedIndex < items.length) {
                        items[selectedIndex].scrollIntoView({block: "nearest", behavior: "smooth"});
                    }
                });

                document.addEventListener("click", function (e) {
                    if (!inputField.contains(e.target) && !autocompleteList.contains(e.target)) {
                        autocompleteList.innerHTML = "";
                        selectedIndex = -1;
                    }
                });
            }

            // Convert JSP categoryList into JavaScript array
            const categoryList = [
        <c:forEach items="${categoryList}" var="brand">
                "${brand.categoryName}",
        </c:forEach>
            ];

            // Get selected value from JSP and pre-fill input field
            const selectedCategory = "${selectedBrandId}";

            if (selectedCategory) {
                document.getElementById("categoryInput").value = selectedCategory;
                document.getElementById("categoryHiddenInput").value = selectedCategory;
            }

            // Setup autocomplete
            setupAutocomplete(
                    document.getElementById("categoryInput"),
                    document.getElementById("categoryHiddenInput"),
                    "category-autocomplete-list",
                    categoryList
                    );
        });

    </script>

    <script>
        document.addEventListener("DOMContentLoaded", function () {
            function setupAutocomplete(inputField, hiddenField, autocompleteListId, dataList) {
                const autocompleteList = document.getElementById(autocompleteListId);
                let selectedIndex = -1;

                function showSuggestions(value) {
                    autocompleteList.innerHTML = "";
                    selectedIndex = -1;

                    if (!value.trim())
                        return;

                    dataList.forEach((item, index) => {
                        if (item.toLowerCase().includes(value.toLowerCase())) {
                            const suggestionItem = document.createElement("div");
                            suggestionItem.textContent = item;
                            suggestionItem.setAttribute("data-index", index);
                            suggestionItem.classList.add("autocomplete-item");

                            suggestionItem.addEventListener("click", function () {
                                inputField.value = item;
                                hiddenField.value = item;
                                autocompleteList.innerHTML = "";
                            });

                            autocompleteList.appendChild(suggestionItem);
                        }
                    });
                }

                inputField.addEventListener("input", function () {
                    showSuggestions(this.value);

                    if (!this.value.trim()) {
                        hiddenField.value = ""; // ? Agar manually delete kare toh bhi ID clear hoga
                    }
                });

                inputField.addEventListener("keydown", function (e) {
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
                            inputField.value = selectedItem.textContent;
                            hiddenField.value = selectedItem.textContent;
                            autocompleteList.innerHTML = "";
                        }
                        e.preventDefault();
                    }

                    // Remove previous selection
                    items.forEach((item, i) => {
                        item.classList.toggle("selected", i === selectedIndex);
                    });

                    // ? Fix: Ensure selected item is always visible
                    if (selectedIndex >= 0 && selectedIndex < items.length) {
                        items[selectedIndex].scrollIntoView({block: "nearest", behavior: "smooth"});
                    }
                });

                document.addEventListener("click", function (e) {
                    if (!inputField.contains(e.target) && !autocompleteList.contains(e.target)) {
                        autocompleteList.innerHTML = "";
                        selectedIndex = -1;
                    }
                });
            }

            // Convert JSP productTypeList into JavaScript array
            const productTypeList = [
        <c:forEach items="${productTypeList}" var="productType">
                "${productType.productTypeName}",
        </c:forEach>
            ];

            // Get selected value from JSP and pre-fill input field
            const selectedProductType = "${selectedProductTypeId}";

            if (selectedProductType) {
                document.getElementById("brandInput").value = selectedProductType;
                document.getElementById("brandHiddenInput").value = selectedProductType;
            }

            // Setup autocomplete
            setupAutocomplete(
                    document.getElementById("brandInput"),
                    document.getElementById("brandHiddenInput"),
                    "brand-autocomplete-list",
                    productTypeList
                    );
        });

    </script>


</body>
</html>
