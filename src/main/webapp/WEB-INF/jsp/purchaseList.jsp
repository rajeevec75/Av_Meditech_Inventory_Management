<%@page import="com.AvMeditechInventory.dtos.ProductDto"%>
<%@page import="com.AvMeditechInventory.dtos.Response"%>
<%@page import="java.util.List"%>
<%@page import="com.AvMeditechInventory.dtos.CustomerAndSupplierDto"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.net.URLEncoder" %>
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

        <!-- Select2 CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/select2/css/select2.min.css">

        <!-- Summernote CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/summernote/summernote-bs4.min.css">

        <!-- Datatable CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dataTables.bootstrap5.min.css">

        <!-- Fontawesome CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome/css/all.min.css">

        <!-- Main CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
        <style>
            /* Default styles for desktop */
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
            .card {
                border: 1px solid #e0e0e0;
                border-radius: 8px;
                margin: 10px;
                padding: 10px;
            }
            #productList {
                max-height: 200px;
                overflow-y: auto;
                margin-top: 5px;
            }
            .product-item {
                padding: 8px;
                cursor: pointer;
            }
            .product-item:hover {
                background-color: #ddd;
            }
            #supplierList {
                max-height: 200px;
                overflow-y: auto;
                margin-top: 5px;
            }
            .supplier-item {
                padding: 8px;
                cursor: pointer;
            }
            .supplier-item:hover {
                background-color: #ddd;
            }
            .table-responsive {
                display: block;
                width: 100%;
                overflow-x: auto;
            }

            .table {
                width: 100%;
                margin-bottom: 1rem;
                background-color: transparent;
            }

            .table th, .table td {
                padding: 0.75rem;
                vertical-align: top;
                border-top: 1px solid #dee2e6;
            }

            .table .text-center {
                text-align: center;
            }
            #supplierList {
                max-height: 200px;
                overflow-y: auto;
            }
            .supplier-item {
                padding: 8px;
                cursor: pointer;
            }
            .supplier-item.active {
                background-color: #f1f1f1;
            }
            .supplier-item:hover {
                background-color: #ddd;
            }
            @media (min-width: 768px) {
                .card-container {
                    display: none;
                }
            }

            @media (max-width: 768px) {
                .card-container {
                    display: block;
                    /* Additional styles for mobile layout if needed */
                }
            }

            @media (max-width: 768px) {
                /* Mobile view styles */
                .table-list-card {
                    border: none;
                    margin: 0;
                    padding: 0;
                }
                .product-section{
                    display: none
                }
                .table-responsive {
                    display: none;
                    flex-direction: column;
                }

                .table {
                    display: none;
                }

                .card-section {
                    border: 1px solid blue;
                    border-radius: 8px;
                    margin: 10px 0;
                    padding: 10px;
                    background-color: #fff;
                }

                .card-section h2 {
                    font-size: 1.25rem;
                    margin-bottom: 10px;
                }

                .card-section p {
                    margin: 5px 0;
                }

                .action-buttons {
                    display: flex;
                    justify-content: flex-end;
                }

                .action-buttons a {
                    margin-left: 10px;
                    color: #007bff;
                }
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
                        <div class="page-header">
                            <div class="add-item d-flex">
                                <div class="page-title mob-view">
                                    <h4>Purchase List</h4>
                                    <h6>Manage your purchases</h6>
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
                            </div>
                        </div>
                        <c:if test="${autoLogin == null}">
                            <ul class="table-top-head mob-view">

                                <li>
                                    <a href="${pageContext.request.contextPath}/purchaseListExportToExcel?startDate=${URLEncoder.encode(startDate, 'UTF-8')}&endDate=${URLEncoder.encode(endDate, 'UTF-8')}&companyName=${URLEncoder.encode(companyName, 'UTF-8')}&productName=${URLEncoder.encode(productName, 'UTF-8')}"
                                       data-bs-toggle="tooltip" data-bs-placement="top" title="Export to Excel">
                                        <img src="${pageContext.request.contextPath}/image/icons/excel.svg" alt="Export to Excel">
                                    </a>
                                </li>


                                <li>
                                    <a data-bs-toggle="tooltip" data-bs-placement="top" title="Collapse" id="collapse-header"><i data-feather="chevron-up" class="feather-chevron-up"></i></a>
                                </li>
                            </ul>

                            <div class="page-btn mob-view">
                                <a href="${pageContext.request.contextPath}/purchaseProduct" class="btn btn-added">
                                    <i data-feather="plus-circle" class="me-2"></i>Add New Purchase
                                </a>
                            </div>




                        </c:if>




                    </div>

                    <!-- Purchase List Form and Table -->
                    <div class="card table-list-card">
                        <div class="card-body">
                            <div class="table-top">
                                <div class="row d-flex">
                                    <form name="purchaseForm" action="" method="post" id="purchaseForm">

                                        <div class="row">
                                            <div class="col-lg-3 col-md-6 col-sm-6 col-12">
                                                <div class="mb-3 add-product">
                                                    <label for="startDate" class="form-label">Start Date</label>
                                                    <input type="date" class="form-control" id="startDate" name="startDate" value="${startDate}" style="width: 200px;">
                                                </div>
                                            </div>
                                            <div class="col-lg-3 col-md-6 col-sm-6 col-12">
                                                <div class="mb-3 add-product">
                                                    <label for="endDate" class="form-label">End Date</label>
                                                    <input type="date" class="form-control" id="endDate" name="endDate" value="${endDate}" style="width: 200px;">
                                                </div>
                                            </div>
                                            <div class="col-lg-3 col-md-6 col-sm-6 col-12">
                                                <div class="mb-3 add-product">
                                                    <label for="companyName" class="form-label">Company Name</label>
                                                    <input type="text" class="form-control" id="supplierSearch" placeholder="Enter Company Name" onkeyup="handleSupplierKeyEvents(event)" onfocus="showSupplierList()" name="companyName" value="${companyName}" autocomplete="off">
                                                    <input type="hidden" name="userId" id="hiddenUserId"> <!-- Hidden input field for supplier ID -->

                                                    <div id="supplierList" style="display: none; border: 1px solid #ccc; position: absolute; background-color: white; z-index: 1000;">
                                                        <% List<CustomerAndSupplierDto> supplierList = (List<CustomerAndSupplierDto>) request.getAttribute("userDataList");
                                                            if (supplierList != null && !supplierList.isEmpty()) {
                                                                for (CustomerAndSupplierDto supplier : supplierList) {
                                                                    String supplierId = supplier.getId().replaceAll("\\s", ""); // Sanitize ID
%>
                                                        <div class="supplier-item" onclick="selectSupplier('<%= supplier.getCompanyName()%>', '<%= supplierId%>')">
                                                            <%= supplier.getCompanyName()%> (Code: <%= supplier.getId()%>)
                                                        </div>
                                                        <%
                                                                }
                                                            }
                                                        %>
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





                                        </div>
                                        <div class="row">
                                            <div class="col-lg-6 col-sm-6 col-12">
                                                <div class="mb-3 add-product">
                                                    <button type="button" onclick="getPurchaseData()" class="btn btn-submit sub-btn">Submit</button>
                                                </div>
                                            </div>
                                        </div>
                                    </form>

                                </div>
                            </div>
                            <div class="table-responsive">
                                <table id="dataTable" class="table datanew1">
                                    <thead>
                                        <tr>
                                            <th>Challan Number</th>
                                            <th>Company Name</th>
                                            <th>Purchase Date</th>
                                            <th>Reference No</th>
                                            <th>Remarks</th>
                                            <th>Quantity</th>
                                            <th class="no-sort">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="purchase" items="${purchaseList}">
                                            <tr>
                                                <td>${purchase.autoId}</td>
                                                <td>${purchase.companyName}</td>
                                                <td>${purchase.date}</td>
                                                <td>${purchase.referenceNumber}</td>
                                                <td>${purchase.remarks}</td>
                                                <td>${purchase.quantity}</td>
                                                <td class="action-table-data">
                                                    <div class="edit-delete-action">

                                                        <a class="me-2 p-2" href="${pageContext.request.contextPath}/getPurchaseById?purchaseId=${purchase.id}">
                                                            <i data-feather="edit" class="feather-edit"></i>
                                                        </a>
                                                        <a class="confirm-text p-2" href="${pageContext.request.contextPath}/deletePurchaseById?purchaseId=${purchase.id}">
                                                            <i data-feather="trash-2" class="feather-trash-2"></i>
                                                        </a>
                                                    </div>	
                                                </td>
                                            </tr>
                                        </c:forEach>

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
                                                <td> </td>
                                            </tr>
                                        </tbody>
                                    </c:if>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!--add mobile view screen design-->
                    <!--add stock mobile view-->
                    <!-- Mobile View Cards -->
                    <div class="card-container">
                        <c:forEach var="purchase" items="${purchaseList}">
                            <div class="card-section">
                                <h2><strong>Purchase Id:</strong> ${purchase.id}</h2>
                                <h2><strong>Company Name:</strong> ${purchase.companyName}</h2>
                                <h2><strong>Purchase Date:</strong> ${purchase.date}</h2>
                                <h2><strong>Reference No:</strong> ${purchase.referenceNumber}</h2>
                            </div>
                        </c:forEach>
                    </div>

                    <!--end mobile view-->



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

        <!-- Datetimepicker JS -->
        <script src="${pageContext.request.contextPath}/js/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker.min.js"></script>

        <!-- Summernote JS -->
        <script src="${pageContext.request.contextPath}/plugins/summernote/summernote-bs4.min.js"></script>

        <!-- Select2 JS -->
        <script src="${pageContext.request.contextPath}/plugins/select2/js/select2.min.js"></script>

        <!-- Sweetalert 2 -->
        <script src="${pageContext.request.contextPath}/plugins/sweetalert/sweetalert2.all.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/sweetalert/sweetalerts.min.js"></script>

        <!-- Custom JS -->
        <script src="assets/js/theme-script.js"></script>	
        <script src="${pageContext.request.contextPath}/js/script.js"></script>


        <!--        <script>
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
        
                </script>-->

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
            function fetchProductSuggestions() {
                var filter = document.getElementById("productName").value.trim();
                var suggestionList = [];
                var suggestionsDiv = document.getElementById("product-suggestions");

                $.ajax({
                    type: "GET",
                    url: "${pageContext.request.contextPath}/productList/json",
                    data: {
                        productName: filter
                    },
                    success: function (response) {
                        console.log("Response received:", response);
                        suggestionList = [];
                        for (var i = 0; i < response.length; i++) {
                            suggestionList.push(response[i].productName);
                        }

                        suggestionsDiv.innerHTML = "";
                        if (filter.length === 0) {
                            suggestionsDiv.style.display = "none";
                            return;
                        }

                        for (var i = 0; i < suggestionList.length; i++) {
                            var suggestionItem = document.createElement("div");
                            suggestionItem.textContent = suggestionList[i];
                            suggestionItem.className = "suggestion-item";
                            suggestionItem.addEventListener("click", function () {
                                document.getElementById("productName").value = this.textContent;
                                suggestionsDiv.style.display = "none";
                            });
                            suggestionsDiv.appendChild(suggestionItem);
                        }

                        suggestionsDiv.style.display = "block";
                    },
                    error: function (xhr, status, error) {
                        console.error("Error: " + error);
                        $('#barcodeDisplay').html("Error occurred while fetching data.");
                    }
                });
            }

            document.addEventListener("click", function (event) {
                var suggestionsDiv = document.getElementById("product-suggestions");
                if (event.target !== suggestionsDiv && !suggestionsDiv.contains(event.target)) {
                    suggestionsDiv.style.display = "none";
                }
            });
        </script>

        <script>
            function fetchCompanySuggestions() {
                var filter = document.getElementById("companyName").value.trim();
                var suggestionList = [];
                var suggestionsDiv = document.getElementById("company-suggestions");

                $.ajax({
                    type: "GET",
                    url: "${pageContext.request.contextPath}/companyNameList/json",
                    data: {
                        companyName: filter
                    },
                    success: function (response) {
                        console.log("Response received:", response);
                        suggestionList = [];
                        for (var i = 0; i < response.length; i++) {
                            suggestionList.push(response[i].companyName);
                        }

                        suggestionsDiv.innerHTML = "";
                        if (filter.length === 0) {
                            suggestionsDiv.style.display = "none";
                            return;
                        }

                        for (var i = 0; i < suggestionList.length; i++) {
                            var suggestionItem = document.createElement("div");
                            suggestionItem.textContent = suggestionList[i];
                            suggestionItem.className = "suggestion-item";
                            suggestionItem.addEventListener("click", function () {
                                document.getElementById("companyName").value = this.textContent;
                                suggestionsDiv.style.display = "none";
                            });
                            suggestionsDiv.appendChild(suggestionItem);
                        }

                        suggestionsDiv.style.display = "block";
                    },
                    error: function (xhr, status, error) {
                        console.error("Error: " + error);
                        $('#barcodeDisplay').html("Error occurred while fetching data.");
                    }
                });
            }

            document.addEventListener("click", function (event) {
                var suggestionsDiv = document.getElementById("company-suggestions");
                if (event.target !== suggestionsDiv && !suggestionsDiv.contains(event.target)) {
                    suggestionsDiv.style.display = "none";
                }
            });
        </script>


        <script>
            function filterSuppliers() {
                var input, filter, supplierList, supplierItems, supplierName, i;
                input = document.getElementById('supplierSearch');
                filter = input.value.toUpperCase();
                supplierList = document.getElementById('supplierList');
                supplierItems = supplierList.getElementsByClassName('supplier-item');

                // Show the supplier list if there is any input
                if (input.value) {
                    supplierList.style.display = "";
                } else {
                    supplierList.style.display = "none";
                }

                for (i = 0; i < supplierItems.length; i++) {
                    supplierName = supplierItems[i].innerText || supplierItems[i].textContent;
                    if (supplierName.toUpperCase().indexOf(filter) > -1) {
                        supplierItems[i].style.display = "";
                    } else {
                        supplierItems[i].style.display = "none";
                    }
                }
            }

            function getPurchaseData() {

                document.purchaseForm.action = "purchaseListByDateRangeAndFilters";
                document.purchaseForm.submit();
            }

            function selectSupplier(supplierName, supplierId) {
                console.log("supplierId: " + supplierId); // Debugging output
                var input = document.getElementById('supplierSearch');
                var hiddenInput = document.getElementById('hiddenUserId');
                input.value = supplierName;
                hiddenInput.value = supplierId;

                // Hide the supplier list once a selection is made
                document.getElementById('supplierList').style.display = "none";
            }

        </script>


        <script>
            function filterProducts() {
                var input, filter, productList, productItems, productName, i;
                input = document.getElementById('productName');
                filter = input.value.toUpperCase();
                productList = document.getElementById('productList');
                productItems = productList.getElementsByClassName('product-item');
                // Show the product list if there is any input
                if (input.value) {
                    productList.style.display = "";
                } else {
                    productList.style.display = "none";
                }
                for (i = 0; i < productItems.length; i++) {
                    productName = productItems[i].innerText || productItems[i].textContent;
                    if (productName.toUpperCase().indexOf(filter) > -1) {
                        productItems[i].style.display = "";
                    } else {
                        productItems[i].style.display = "none";
                    }
                }
            }

            function selectProduct(productName, productId) {
                console.log("productId: " + productId); // Debugging output
                var input = document.getElementById('productName');
                input.value = productName;
                // You might want to set a hidden input for productId similar to the customer ID if needed
                // var hiddenInput = document.getElementById('hiddenProductId');
                // hiddenInput.value = productId;
                // Hide the product list once a selection is made
                document.getElementById('productList').style.display = "none";
            }
        </script>

        <script>
            function filterSuppliers() {
                var input, filter, div, i;
                input = document.getElementById('supplierSearch');
                filter = input.value.toUpperCase();
                div = document.getElementById('supplierList');
                supplierItems = div.getElementsByClassName('supplier-item');

                // Display the supplier list if there's any input
                if (filter.length > 0) {
                    div.style.display = 'block';
                } else {
                    div.style.display = 'none';
                }

                // Loop through all supplier items, and hide those who don't match the search query
                for (i = 0; i < supplierItems.length; i++) {
                    txtValue = supplierItems[i].textContent || supplierItems[i].innerText;
                    if (txtValue.toUpperCase().indexOf(filter) > -1) {
                        supplierItems[i].style.display = "";
                    } else {
                        supplierItems[i].style.display = "none";
                    }
                }
            }

            function showSupplierList() {
                var div = document.getElementById('supplierList');
                var input = document.getElementById('supplierSearch');
                if (input.value.trim().length > 0) {
                    div.style.display = 'block';
                }
            }

            function selectSupplier(name, id) {
                document.getElementById('supplierSearch').value = name;
                document.getElementById('hiddenUserId').value = id;
                document.getElementById('supplierList').style.display = 'none';
            }

            document.addEventListener('click', function (event) {
                var isClickInside = document.getElementById('supplierSearch').contains(event.target) ||
                        document.getElementById('supplierList').contains(event.target);
                if (!isClickInside) {
                    document.getElementById('supplierList').style.display = 'none';
                }
            });
        </script>

        <script>
            function filterProducts() {
                var input, filter, div, i;
                input = document.getElementById('productName');
                filter = input.value.toUpperCase();
                div = document.getElementById('productList');
                productItems = div.getElementsByClassName('product-item');

                // Display the product list if there's any input
                if (filter.length > 0) {
                    div.style.display = 'block';
                } else {
                    div.style.display = 'none';
                }

                // Loop through all product items, and hide those who don't match the search query
                for (i = 0; i < productItems.length; i++) {
                    txtValue = productItems[i].textContent || productItems[i].innerText;
                    if (txtValue.toUpperCase().indexOf(filter) > -1) {
                        productItems[i].style.display = "";
                    } else {
                        productItems[i].style.display = "none";
                    }
                }
            }

            function showProductList() {
                var div = document.getElementById('productList');
                var input = document.getElementById('productName');
                if (input.value.trim().length > 0) {
                    div.style.display = 'block';
                }
            }

            function selectProduct(name, id) {
                document.getElementById('productName').value = name;
                document.getElementById('hiddenProductId').value = id; // Assuming you have a hidden input for the product ID
                document.getElementById('productList').style.display = 'none';
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
            $(".confirm-text").on("click", function (event) {
                event.preventDefault(); // Prevent the default action of the link
                var deleteUrl = $(this).attr("href"); // Get the delete URL from the link's href attribute
                var startDate = document.getElementById("startDate").value;
                var endDate = document.getElementById("endDate").value;
                var companyName = document.getElementById("supplierSearch").value;
                var productName = document.getElementById("productName").value;

                Swal.fire({
                    title: "Are you sure?",
                    text: "You won't be able to revert this!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#3085d6",
                    cancelButtonColor: "#d33",
                    confirmButtonText: "Yes, delete it!",
                    confirmButtonClass: "btn btn-primary",
                    cancelButtonClass: "btn btn-danger ml-1",
                    buttonsStyling: false,
                }).then(function (result) {
                    if (result.value) {
                        // If the user confirms deletion, redirect to the delete URL
                        window.location.href = deleteUrl + "&startDate=" + startDate + "&endDate=" + endDate +
                                "&companyName=" + companyName + "&productName=" + productName;
                    }
                });
            });
        </script>

        <script>
            let currentSupplierFocus = -1;

            function filterSuppliers() {
                const input = document.getElementById('supplierSearch');
                const filter = input.value.toUpperCase();
                const div = document.getElementById('supplierList');
                const supplierItems = div.getElementsByClassName('supplier-item');

                // Display the supplier list
                div.style.display = 'block';

                // Loop through all supplier items, and hide those who don't match the search query
                for (let i = 0; i < supplierItems.length; i++) {
                    const txtValue = supplierItems[i].textContent || supplierItems[i].innerText;
                    if (txtValue.toUpperCase().indexOf(filter) > -1) {
                        supplierItems[i].style.display = "";
                    } else {
                        supplierItems[i].style.display = "none";
                    }
                }
            }

            function showSupplierList() {
                document.getElementById('supplierList').style.display = 'block';
            }

            function hideSupplierList() {
                document.getElementById('supplierList').style.display = 'none';
            }

            function selectSupplier(name, id) {
                document.getElementById('supplierSearch').value = name;
                document.getElementById('hiddenUserId').value = id;
                hideSupplierList();
                currentSupplierFocus = -1; // Reset the focus index
            }

            function handleSupplierKeyEvents(event) {
                const supplierList = document.getElementById('supplierList');
                const items = Array.from(supplierList.getElementsByClassName('supplier-item')).filter(item => item.style.display !== 'none');
                const hiddenInput = document.getElementById('hiddenUserId');

                if (event.keyCode === 40) {
                    // Down arrow key
                    currentSupplierFocus++;
                    if (currentSupplierFocus >= items.length)
                        currentSupplierFocus = 0;
                    addActiveSupplier(items);
                    ensureItemVisible(items[currentSupplierFocus]);
                    if (items[currentSupplierFocus]) {
                        hiddenInput.value = items[currentSupplierFocus].getAttribute('data-id');
                    }
                } else if (event.keyCode === 38) {
                    // Up arrow key
                    currentSupplierFocus--;
                    if (currentSupplierFocus < 0)
                        currentSupplierFocus = items.length - 1;
                    addActiveSupplier(items);
                    ensureItemVisible(items[currentSupplierFocus]);
                    if (items[currentSupplierFocus]) {
                        hiddenInput.value = items[currentSupplierFocus].getAttribute('data-id');
                    }
                } else if (event.keyCode === 13) {
                    // Enter key
                    event.preventDefault(); // Prevent the default form submission behavior
                    if (currentSupplierFocus > -1) {
                        if (items)
                            items[currentSupplierFocus].click();
                    }
                } else {
                    filterSuppliers();
                }
            }

            function addActiveSupplier(items) {
                if (!items)
                    return false;
                removeActiveSupplier(items);
                if (currentSupplierFocus >= items.length)
                    currentSupplierFocus = 0;
                if (currentSupplierFocus < 0)
                    currentSupplierFocus = items.length - 1;
                items[currentSupplierFocus].classList.add('active');
            }

            function removeActiveSupplier(items) {
                for (let i = 0; i < items.length; i++) {
                    items[i].classList.remove('active');
                }
            }

            function ensureItemVisible(item) {
                const container = document.getElementById('supplierList');
                const containerRect = container.getBoundingClientRect();
                const itemRect = item.getBoundingClientRect();
                if (itemRect.bottom > containerRect.bottom) {
                    container.scrollTop += itemRect.bottom - containerRect.bottom;
                } else if (itemRect.top < containerRect.top) {
                    container.scrollTop -= containerRect.top - itemRect.top;
                }
            }

            document.addEventListener('click', function (event) {
                const supplierList = document.getElementById('supplierList');
                if (!supplierList.contains(event.target) && event.target !== document.getElementById('supplierSearch')) {
                    hideSupplierList();
                }
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
            function fetchCompanySuggestions() {
                var filter = document.getElementById("companyName").value.trim();
                var suggestionList = [];
                var suggestionsDiv = document.getElementById("company-suggestions");

                $.ajax({
                    type: "GET",
                    url: "${pageContext.request.contextPath}/companyNameList/json",
                    data: {
                        companyName: filter
                    },
                    success: function (response) {
                        console.log("Response received:", response);
                        suggestionList = [];
                        for (var i = 0; i < response.length; i++) {
                            suggestionList.push(response[i].companyName);
                        }

                        suggestionsDiv.innerHTML = "";
                        if (filter.length === 0) {
                            suggestionsDiv.style.display = "none";
                            return;
                        }

                        for (var i = 0; i < suggestionList.length; i++) {
                            var suggestionItem = document.createElement("div");
                            suggestionItem.textContent = suggestionList[i];
                            suggestionItem.className = "suggestion-item";
                            suggestionItem.addEventListener("click", function () {
                                document.getElementById("companyName").value = this.textContent;
                                suggestionsDiv.style.display = "none";
                            });
                            suggestionsDiv.appendChild(suggestionItem);
                        }

                        suggestionsDiv.style.display = "block";
                    },
                    error: function (xhr, status, error) {
                        console.error("Error: " + error);
                        $('#barcodeDisplay').html("Error occurred while fetching data.");
                    }
                });
            }

            document.addEventListener("click", function (event) {
                var suggestionsDiv = document.getElementById("company-suggestions");
                if (event.target !== suggestionsDiv && !suggestionsDiv.contains(event.target)) {
                    suggestionsDiv.style.display = "none";
                }
            });
        </script>


        <script>
            function filterSuppliers() {
                var input, filter, supplierList, supplierItems, supplierName, i;
                input = document.getElementById('supplierSearch');
                filter = input.value.toUpperCase();
                supplierList = document.getElementById('supplierList');
                supplierItems = supplierList.getElementsByClassName('supplier-item');

                // Show the supplier list if there is any input
                if (input.value) {
                    supplierList.style.display = "";
                } else {
                    supplierList.style.display = "none";
                }

                for (i = 0; i < supplierItems.length; i++) {
                    supplierName = supplierItems[i].innerText || supplierItems[i].textContent;
                    if (supplierName.toUpperCase().indexOf(filter) > -1) {
                        supplierItems[i].style.display = "";
                    } else {
                        supplierItems[i].style.display = "none";
                    }
                }
            }

            function getPurchaseData() {

                document.purchaseForm.action = "purchaseListByDateRangeAndFilters";
                document.purchaseForm.submit();
            }

            function selectSupplier(supplierName, supplierId) {
                console.log("supplierId: " + supplierId); // Debugging output
                var input = document.getElementById('supplierSearch');
                var hiddenInput = document.getElementById('hiddenUserId');
                input.value = supplierName;
                hiddenInput.value = supplierId;

                // Hide the supplier list once a selection is made
                document.getElementById('supplierList').style.display = "none";
            }

        </script>




</html>

