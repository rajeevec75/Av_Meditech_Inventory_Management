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
            /* Custom CSS to make the modal full width */
            .custom-width {
                width: 100%;
                max-width: 80%;

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



                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header">
                            <div class="add-item d-flex">
                                <div class="page-title">
                                    <h4>Item Movement Report</h4>
                                    <h6>Manage your Item Movement Report</h6>
                                </div>
                            </div>
                            <ul class="table-top-head">
                                <li>
                                    <a href="${pageContext.request.contextPath}/itemMovementReportsExportToExcel?startDate=${URLEncoder.encode(startDate, 'UTF-8')}&endDate=${URLEncoder.encode(endDate, 'UTF-8')}&productName=${URLEncoder.encode(productName, 'UTF-8')}" data-bs-toggle="tooltip" data-bs-placement="top" title="Export to Excel">
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

                                <form id="myForm" name="myForm" method="post" action="" onkeypress="return disableEnterSubmit(event);">
                                    <div class="row">



                                        <div class="col-lg-4 col-md-6 col-sm-12">
                                            <div class="mb-3 add-product">
                                                <label for="startDate" class="form-label">Start Date</label>
                                                <input type="date" class="form-control" id="startDate" name="startDate" value="${startDate}">
                                            </div>
                                        </div>
                                        <div class="col-lg-4 col-md-6 col-sm-12">
                                            <div class="mb-3 add-product">
                                                <label for="endDate" class="form-label">End Date</label>
                                                <input type="date" class="form-control" id="endDate" name="endDate" value="${endDate}">
                                            </div>
                                        </div>
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
                                            <div class="mb-3 add-product">
                                                <button type="button" onclick="myFormSumbitButton()" class="btn btn-submit sub-btn">Submit</button>
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
                                            <th>Voucher Date</th>
                                            <th>Company Name</th>
                                            <th>Reference</th>
                                            <th>Remarks</th>
                                            <th>Action</th>

                                        </tr>
                                    </thead>

                                    <tbody>
                                        <c:forEach var="transaction" items="${itemMovementReportList}">
                                            <tr>
                                                <td>${transaction.autoId}</td>
                                                <td>${transaction.voucherType}</td>
                                                <td>${transaction.voucherDate}</td>
                                                <td>${transaction.companyName}</td>
                                                <td>${transaction.referenceNumber}</td>
                                                <td>${transaction.remarks}</td>
                                                <td>
                                                    <a class="p-2 btn btn-secondary openTransactionReportDetailsModalButton" 
                                                       data-purchase-id="${transaction.id}" 
                                                       data-transaction-name="${transaction.voucherType}" 
                                                       data-bs-toggle="modal">
                                                        View Report
                                                    </a>
                                                </td>

                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                </table>
                            </div>
                            <!-- Cards visible only on mobile screens -->
                            <div class="row mt-4 d-block d-lg-none" style="margin: 20px">
                                <div class="col-12">
                                    <div class="card-deck">
                                        <c:forEach var="transaction" items="${itemMovementReportList}">
                                            <div class="card mb-3" >
                                                <div class="card-body">
                                                    <h5 class="card-title">Voucher Type: ${transaction.voucherType}</h5>
                                                    <p class="card-text">Voucher Number: ${transaction.id}</p>
                                                    <p class="card-text">Voucher Date: ${transaction.voucherDate}</p>
                                                    <p class="card-text">Party Name: ${transaction.particulars}</p>
                                                    <p class="card-text">Product Type Name: ${transaction.productTypeName}</p>
                                                    <p class="card-text">Category Name: ${transaction.brandName}</p>
                                                    <p class="card-text">Quantity: ${transaction.quantity}</p>
                                                    <p class="card-text">Item Serial Number: ${transaction.itemSerialNumber}</p>
                                                    <p class="card-text">Item Serial Expiry Date: ${transaction.itemSerialExpiryDate}</p>
                                                </div>
                                            </div>
                                        </c:forEach>

                                    </div>
                                </div>
                            </div>



                            <!-- Modal -->
                            <div class="modal fade" id="transactionReportDetailsModal" tabindex="-1" role="dialog" aria-labelledby="transactionReportDetailsModalLabel" aria-hidden="true">
                                <div class="modal-dialog custom-width" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h5 class="modal-title" id="transactionReportDetailsModalLabel">Item Movement  Report Details</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="table-responsive">
                                                <table class="table">
                                                    <thead>
                                                        <tr>
                                                            <th>Serial Number</th>
                                                            <th>Item Description</th>
                                                            <th>Product Type</th>
                                                            <th>Brand</th>
                                                            <th>SKU</th>
                                                            <th>Serial Number</th>
                                                            <th>Expiry Date</th>
                                                            <th>Quantity</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="transactionReportDetailsTableBody">
                                                        <!-- Rows will be dynamically inserted here -->
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- / Modal -->
                        </div>
                    </div>
                    <!-- /product list -->
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
            $(document).ready(function () {

                // Function to open and populate the transaction report modal
                function openTransactionReportDetailsModal() {

                    var productName = '${productName}';
                    // Get dynamic values from button attributes
                    var transactionType = $(this).data('transaction-name');
                    var id = $(this).data('purchase-id');

                    $.ajax({
                        url: `${pageContext.request.contextPath}/fetchItemMovementReport`,
                        type: 'GET',
                        data: {
                            transactionType: transactionType,
                            id: id,
                            productName: productName
                        },
                        success: function (response) {
                            console.log("Response received:", response); // Log the entire response object for debugging

                            // Check if response is not null and is a non-empty array
                            if (response && Array.isArray(response) && response.length > 0) {
                                populateModal(response); // Populate modal with response data
                                // Open the modal
                                var myModal = new bootstrap.Modal(document.getElementById('transactionReportDetailsModal'));
                                myModal.show();
                            } else {
                                alert('No transaction report details found.');
                            }
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            console.error('Error fetching transaction report details:', errorThrown);
                            alert('An error occurred while retrieving transaction report details.');
                        }
                    });
                }

                // Attach click event to buttons with class 'openTransactionReportDetailsModalButton'
                $(document).on('click', '.openTransactionReportDetailsModalButton', openTransactionReportDetailsModal);

                // Function to populate modal with data
                function populateModal(data) {
                    var tableBody = $('#transactionReportDetailsTableBody');
                    tableBody.empty(); // Clear existing rows

                    let index = 1; // Initialize index
                    data.forEach(function (item) {
                        const row = document.createElement('tr');
                        row.innerHTML = '<td>' + (index++) + '</td>' + // Increment index
                                '<td>' + (item.productName || '') + '</td>' +
                                '<td>' + (item.categoryName || '') + '</td>' +
                                '<td>' + (item.productTypeName || '') + '</td>' +
                                '<td>' + (item.sku || '') + '</td>' +
                                '<td>' + (item.serialNumber || '') + '</td>' +
                                '<td>' + (item.itemSerialExpiry || '') + '</td>' +
                                '<td>' + (item.quantity || '') + '</td>';
                        tableBody.append(row);
                    });
                }
            });

        </script>

        <script>
            // Submit form with button click
            function myFormSumbitButton() {
                document.myForm.action = "itemMovementReportByProductNameAndDateRange";
                document.myForm.submit();
            }

            // Disable form submission on Enter key
            function disableEnterSubmit(event) {
                if (event.key === "Enter") {
                    event.preventDefault();
                    return false;
                }
            }
        </script>

    </body>
</html>