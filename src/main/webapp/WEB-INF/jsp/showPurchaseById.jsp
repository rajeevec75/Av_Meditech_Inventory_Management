<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.AvMeditechInventory.dtos.ProductDto"%>
<%@page import="com.AvMeditechInventory.dtos.CustomerAndSupplierDto"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
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

            .loader {
                display: none;
                border: 4px solid #F3F3F3;
                border-top: 4px solid #3498DB;
                border-radius: 50%;
                width: 30px;
                height: 30px;
                animation: spin 1s linear infinite;
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
            }
            @keyframes spin {
                0% { transform: rotate(0deg); }
                100% { transform: rotate(360deg); }
            }
            /* Disable button when loading */
            .disabled {
                display: none;
                pointer-events: none;
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
                                <div class="page-title">
                                    <h4>Purchase</h4>
                                    <h6>purchase Details</h6>
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
                        <ul class="table-top-head">
                            <li>

                                <div class="page-btn">

                                    <a href="javascript:history.go(-1)" class="btn btn-secondary"><i data-feather="arrow-left" class="me-2"></i>Back</a>

                                </div>

                            </li>
                            <li>
                                <div class="page-btn">
                                    <a target="_blank" href="${pageContext.request.contextPath}/purchaseDeliveryChallan?purchaseId=${purchase.purchaseMasters.purchaseId}" class="btn btn-secondary">
                                        Print Challan</a>
                                </div>
                            </li>
                            <li>
                                <div class="page-btn">
                                    <a target="_blank" href="${pageContext.request.contextPath}/purchaseDeliveryChallanQuantity?purchaseId=${purchase.purchaseMasters.purchaseId}" class="btn btn-secondary">
                                        Delivery Challan Quantity</a>
                                </div>
                            </li>
                            <li>
                                <a data-bs-toggle="tooltip" data-bs-placement="top" title="Collapse" id="collapse-header"><i data-feather="chevron-up" class="feather-chevron-up"></i></a>
                            </li>
                        </ul>

                    </div>


                    <!-- /add -->
                    <form name="purchase_form" action="" method="post" id="purchase_form">
                        <div class="row">


                            <div class="col-lg-3 col-md-6 col-sm-6 col-12">
                                <div class="mb-3 add-product">
                                    <label for="companyName" class="form-label">Company Name</label>
                                    <input type="text" class="form-control" id="supplierSearch" placeholder="Enter Company Name" 
                                           onkeyup="handleSupplierKeyEvents(event)" onfocus="showSupplierList()" 
                                           name="companyName" autocomplete="off" value="${purchase.purchaseMasters.companyName}">
                                    <input type="hidden" name="user_id" id="hiddenUserId" value="${purchase.purchaseMasters.userId}"> <!-- Hidden input field for supplier ID -->
                                    <input type="hidden" name="purchaseId" value="${purchase.purchaseMasters.purchaseId}" />
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

                            <!-- Date  -->

                            <div class="col-lg-4 col-sm-6 col-12">
                                <div class="mb-3 add-product">
                                    <label class="form-label">Date</label>
                                    <input type="date" class="form-control" value="${purchase.purchaseMasters.purchaseDate}"  name="purchase_date">
                                </div>
                            </div>

                            <div class="col-lg-4 col-sm-6 col-12">
                                <div class="input-blocks">
                                    <label>Reference No</label>
                                    <div class="input-groupicon calender-input">
                                        <!--<i data-feather="calendar" class="info-img"></i>-->
                                        <input type="text" name="referenceNo" value="${purchase.purchaseMasters.referenceNo}">
                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-4 col-sm-6 col-12">
                                <div class="input-blocks">
                                    <label>Remarks</label>
                                    <div class="input-groupicon calender-input">
                                        <!--<i data-feather="calendar" class="info-img"></i>-->
                                        <input type="text" name="remarks" value="${purchase.purchaseMasters.remarks}">
                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-4 col-sm-6 col-12">
                                <div class="input-blocks">
                                    <label>Purchase Id</label>
                                    <div class="input-groupicon calender-input">
                                        <!--<i data-feather="calendar" class="info-img"></i>-->
                                        <input type="text" readonly value="${purchase.purchaseMasters.purSeqId}">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="loader" id="loader"></div>
                            <div class="col-lg-12 text-end">
                                <!--<button type="button" class="btn btn-cancel add-cancel me-3" data-bs-dismiss="modal">Cancel</button>-->
                                <button type="button" id="sbmtBtn" onclick="get_purchase_form_data_submit()" class="btn btn-submit add-sale">Submit</button>
                            </div>
                        </div>
                    </form>
                    <div class="col-lg-12">
                        <div class="modal-body-table">
                            <div class="table-responsive">
                                <table class="table  datanew">
                                    <thead>
                                        <tr>
                                            <th>Product Name</th>
                                            <th>Qty</th>

                                        </tr>
                                    </thead>

                                    <tbody class="">
                                        <c:forEach var="item" items="${purchase.purchaseMasterItemses}">

                                            <tr>
                                                <c:choose>  
                                                    <c:when test="${item.trackingSerialNo}">  
                                                        <td class="p-3" onclick="popUpOpen('${item.productId}', '${item.productName}', this)">${item.productName}</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td class="p-3">${item.productName}</td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <td class="p-3">${item.quantity}</td>

                                            </tr>
                                        </c:forEach>




                                    </tbody>
                                </table>
                            </div>
                        </div>

                    </div>

                    <div class="row" style="display: none">
                        <div class="col-lg-6 ms-auto">
                            <div class="total-order w-100 max-widthauto m-auto mb-4">
                                <ul>
                                    <li>
                                        <h4>Sub-total</h4>
                                        <h5>Rs </h5><h5>${purchase.purchaseMasters.subTotalAmount}</h5>
                                    </li>
                                    <li>
                                        <h4>Order Tax (GST)</h4>
                                        <h5>Rs </h5><h5>${purchase.purchaseMasters.gstAmount}</h5>
                                    </li>
                                    <li>
                                        <h4>Discount</h4>
                                        <h5>Rs </h5><h5>${purchase.purchaseMasters.discountAmount}</h5>
                                    </li>
                                    <li>
                                        <h4>Shipping</h4>
                                        <h5>Rs </h5><h5>${purchase.purchaseMasters.shippingAmount}</h5>
                                    </li>

                                    <li>
                                        <h4>Grand Total</h4>
                                        <h5>Rs </h5><h5>${purchase.purchaseMasters.grandTotal}</h5>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>


                    <!--add popup -->
                    <div class="modal fade" id="add-sales-new">
                        <div class="modal-dialog add-centered">
                            <div class="modal-content">
                                <div class="page-wrapper p-0 m-0">
                                    <div class="content p-0">
                                        <div class="modal-header border-0 custom-modal-header">
                                            <div class="page-title">
                                                <h4 id="serialPattern">Pattern: </h4>
                                            </div>
                                            <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="card">
                                            <div class="card-body">
                                                <form id="serialNumberForm">
                                                    <div class="row">
                                                        <div class="col-lg-12 col-sm-6 col-12">
                                                            <div class="input-blocks">
                                                                <label for="productSerialNumber">Product Serial Number</label>
                                                                <div class="input-groupicon select-code">
                                                                    <input type="hidden" id="productSerialPattern" />
                                                                    <input type="hidden" id="productSerialId" />
                                                                    <input type="hidden" id="productSerialUp" />
                                                                    <input type="hidden" id="productSerialGst" />
                                                                    <input type="text" style="padding-left: 15px;" id="productSerialNumber" placeholder="Enter Product Serial Number">
                                                                    <div class="addonset">
                                                                        <img id="submitBtn" src="${pageContext.request.contextPath}/image/icons/qrcode-scan.svg" alt="Scan QR Code">
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-lg-12">
                                                        <div class="modal-body-table">
                                                            <div class="table-responsive">
                                                                <table class="table datanew1">
                                                                    <thead>
                                                                        <tr>
                                                                            <th>Serial Number</th>
                                                                            <th>Expiry Date</th>

                                                                        </tr>
                                                                    </thead>
                                                                    <tbody id="myTableBody"></tbody> <!-- Rows will be dynamically added here -->
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-lg-12 text-end">
                                                            <button type="button" onclick="deleteSerialAllData()" class="btn btn-cancel add-cancel me-3" data-bs-dismiss="modal">Ok</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /add popup -->



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

        <!-- Bootstrap Tagsinput JS -->
        <script src="${pageContext.request.contextPath}/plugins/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>

        <!-- Sweetalert 2 -->
        <script src="${pageContext.request.contextPath}/plugins/sweetalert/sweetalert2.all.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/sweetalert/sweetalerts.min.js"></script>

        <!-- Custom JS -->

        <script src="${pageContext.request.contextPath}/js/theme-script.js"></script>
        <script src="${pageContext.request.contextPath}/js/script.js"></script>





    </body>
    <script>
                                                                function popUpOpen(productId, productName) {
                                                                    console.log("Product ID:", productId);
                                                                    console.log("Product Name:", productName);

                                                                    // Clear existing rows in the table to avoid duplication
                                                                    $('.datanew1 tbody').empty();

                                                                    // Loop through the items and append rows if productId matches
        <c:forEach var="item" items="${purchase.purchaseItemSerialMasters}">
                                                                    var itemProductId = '${item.productId}';
                                                                    console.log("itemProductId" + itemProductId);
                                                                    var itemSerialNumber = '${item.itemSerialNumber}';
                                                                    var expiryDate = '${item.expiryDate}';
                                                                    // JavaScript conditional statement within JSP loop
                                                                    if (productId === itemProductId) {
                                                                        var newRow = '<tr>' +
                                                                                '<td><label>' + itemSerialNumber + '</label></td>' +
                                                                                '<td><label>' + expiryDate + '</label></td>' +
                                                                                '</tr>';
                                                                        $('.datanew1 tbody').append(newRow);
                                                                    }
        </c:forEach>

                                                                    // Show the modal
                                                                    const modal = new bootstrap.Modal(document.getElementById('add-sales-new'));
                                                                    modal.show();
                                                                }




                                                                document.getElementById('productSerialNumber').addEventListener('keypress', function (e) {
                                                                    if (e.key === 'Enter') {
                                                                        addSerialNumberRow();
                                                                        e.preventDefault(); // Prevent form submission on Enter key press
                                                                    }
                                                                });

                                                                function addSerialNumberRow() {
                                                                    const serialNumber = document.getElementById('productSerialNumber').value;
                                                                    const expiryDate = prompt("Enter expiry date (YYYY-MM-DD):"); // For simplicity, using prompt to get expiry date

                                                                    if (serialNumber && expiryDate) {
                                                                        const tableBody = document.getElementById('myTableBody');
                                                                        const row = document.createElement('tr');

                                                                        row.innerHTML = `<td>${serialNumber}</td>
                                                                                            <td>${expiryDate}</td>
                                                                                               <td><button type="button" onclick="removeSerialNumberRow(this)" class="btn btn-danger">Remove</button></td>
                                                                                              `;

                                                                        tableBody.appendChild(row);

                                                                        // Clear input field
                                                                        document.getElementById('productSerialNumber').value = '';
                                                                    }
                                                                }





    </script>

    <!-- comment -->
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

        // Show/hide loader and enable/disable button based on 'show' flag.
        function toggleLoader(show) {
            let loader = document.getElementById("loader");
            let sbmtBtn = document.getElementById("sbmtBtn");

            if (show) {
                sbmtBtn.classList.add("disabled");
                loader.style.display = "block"; // Show loader and disable button
            } else {
                sbmtBtn.classList.remove("disabled");
                loader.style.display = "none"; // Hide loader and enable button
            }
        }
        function get_purchase_form_data_submit() {
            toggleLoader(true);
            document.purchase_form.action = "updatePurchaseCreate";
            document.purchase_form.submit();
        }

    </script>

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
    </script>



</html>




