<%@page import="com.AvMeditechInventory.entities.AccountUser"%>
<%@page import="com.AvMeditechInventory.dtos.Response"%>
<%@page import="java.util.List"%>
<%@ page import="java.net.URLEncoder" %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                                    <h4>Party Report</h4>
                                    <h6>Manage your Party Report</h6>
                                </div>
                            </div>
                            <ul class="table-top-head">

                                <li>
                                    <a href="${pageContext.request.contextPath}/partyReportExportToExcel?startDate=${URLEncoder.encode(startDate, 'UTF-8')}&endDate=${URLEncoder.encode(endDate, 'UTF-8')}&reportName=${URLEncoder.encode(reportName, 'UTF-8')}&companyName=${URLEncoder.encode(companyName, 'UTF-8')}"
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

                                
                                    <form id="myForm" name="myForm" method="post" action="" onkeypress="return disableEnterSubmit(event);">
                                        <div class="row">
                                            <div class="col-lg-4 col-sm-6 col-12">
                                                <div class="mb-3 add-product">
                                                    <label class="form-label">Start Date</label>
                                                    <input type="date" class="form-control" id="startDate" name="startDate" value="${startDate}">
                                                </div>
                                            </div>
                                            <div class="col-lg-4 col-sm-6 col-12">
                                                <div class="mb-3 add-product">
                                                    <label class="form-label">End Date</label>
                                                    <input type="date" class="form-control" id="endDate" name="endDate" value="${endDate}">
                                                </div>
                                            </div>
                                            <div class="col-lg-4 col-sm-6 col-12">
                                                <div class="mb-3 add-product">
                                                    <label class="form-label" for="reportName">Report Name</label>
                                                    <select id="transactionName" name="reportName" class="form-select">
                                                        <option value="" ${reportName == '' ? 'selected' : ''}>Please Select</option>
                                                        <option value="customer" ${reportName == 'customer' ? 'selected' : ''}>Customer</option>
                                                        <option value="supplier" ${reportName == 'supplier' ? 'selected' : ''}>Supplier</option>
                                                        <option value="both" ${reportName == 'both' ? 'selected' : ''}>Both</option>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="col-lg-4 col-md-6 col-sm-12">
                                                <div class="mb-3 add-product product-section">
                                                    <label for="customerName" class="form-label">Company Name</label>
                                                    <input type="text" class="form-control" id="customerSearch" placeholder="Search User Company Name" onkeyup="handleKeyEvents(event)" name="companyName" value="${companyName}" autocomplete="off" onclick="showCustomerList()">
                                                    <input type="hidden" name="customerId" id="hiddenUserId"> <!-- Hidden input field for customer ID -->
                                                    <div id="customerList" style="display: none; border: 1px solid #ccc; position: absolute; background-color: white; z-index: 1000;">
                                                        <% List<AccountUser> customerList = (List<AccountUser>) request.getAttribute("retrieveAllUsers");
                                                            if (customerList != null && !customerList.isEmpty()) {
                                                                for (AccountUser userResponse : customerList) {
                                                        %>
                                                        <div class="customer-item" onclick="selectCustomer('<%= userResponse.getCompanyName()%>', '<%= userResponse.getUserCode()%>')">
                                                            <%= userResponse.getCompanyName()%> (Code: <%= userResponse.getUserCode()%>)
                                                        </div>
                                                        <%
                                                                }
                                                            }
                                                        %>
                                                    </div>
                                                </div>
                                            </div>







                                        </div>
                                        <div class="col-lg-4 col-sm-6 col-12">
                                            <div class="mb-3 add-product">
                                                <button type="button" onclick="myFormSumbitButton()" class="btn btn-submit sub-btn">Submit</button>
                                            </div>
                                        </div>
                                    </form>


                            </div>
                            <!-- /Filter -->
                            <div class="card" id="filter_inputs">
                                <div class="card-body pb-0">
                                    <div class="row">
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="input-blocks">
                                                <i data-feather="zap" class="info-img"></i>
                                                <select class="select">
                                                    <option>Choose Category</option>
                                                    <option>Laptop</option>
                                                    <option>Electronics</option>
                                                    <option>Shoe</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="input-blocks">
                                                <i data-feather="calendar" class="info-img"></i>
                                                <div class="input-groupicon">
                                                    <input type="text" class="datetimepicker" placeholder="Choose Date" >
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12">
                                            <div class="input-blocks">
                                                <i data-feather="stop-circle" class="info-img"></i>
                                                <select class="select">
                                                    <option>Choose Status</option>
                                                    <option>Active</option>
                                                    <option>Inactive</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-sm-6 col-12 ms-auto">
                                            <div class="input-blocks">
                                                <a class="btn btn-filters ms-auto"> <i data-feather="search" class="feather-search"></i> Search </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- /Filter -->
                            <div class="table-responsive">
                                <table class="table  datanew1">
                                    <thead>
                                        <tr>
                                            <th>Voucher No</th>
                                            <th>Voucher Type</th>
                                            <th>Voucher Date</th>
                                            <th>Company Name</th>
                                            <th>Reference</th>
                                            <th>Remarks</th>
                                            <!--                                            <th>Customer Name</th>-->
                                            <th>Action</th>


                                        </tr>
                                    </thead> 
                                    <tbody>
                                        <c:forEach var="transaction" items="${partyReportList}">
                                            <tr>
                                                <td>${transaction.autoId}</td>
                                                <td>${transaction.voucherType}</td>
                                                <td>${transaction.voucherDate}</td>
                                                <td>${transaction.companyName}</td>
                                                <td>${transaction.referenceNumber}</td>
                                                <td>${transaction.remarks}</td>
<!--                                                <td>${transaction.particulars}</td>-->
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

                        </div>
                    </div>
                    <!-- /product list -->
                    <!-- Cards visible only on mobile screens -->
                    <div class="row mt-4 d-block d-lg-none">
                        <div class="col-12">
                            <div class="card-deck">
                                <c:forEach var="transaction" items="${partyReportList}">
                                    <div class="card mb-3 ">
                                        <div class="card-body text-dark">
                                            <h5 class="card-title">Voucher No: ${transaction.id}</h5>
                                            <p class="card-title">Voucher Date: ${transaction.voucherDate}</p>
                                            <p class="card-title">Particulars: ${transaction.particulars}</p>
                                            <p class="card-title">Product Type: ${transaction.productTypeName}</p>
                                            <p class="card-title">Brand: ${transaction.brandName}</p>
                                            <p class="card-title">Quantity: ${transaction.quantity}</p>
                                            <p class="card-title">Item Serial Number: ${transaction.itemSerialNumber}</p>
                                            <p class="card-title">Item Expiry Date: ${transaction.itemSerialExpiryDate}</p>
                                        </div>
                                    </div>
                                </c:forEach>
                                <c:if test="${empty partyReportList}">
                                    <div class="card mb-3">
                                        <div class="card-body">
                                            <p class="card-text text-center">No party report found</p>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>

                    <!-- Modal -->
                    <div class="modal fade" id="transactionReportDetailsModal" tabindex="-1" role="dialog" aria-labelledby="transactionReportDetailsModalLabel" aria-hidden="true">
                        <div class="modal-dialog custom-width" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="transactionReportDetailsModalLabel">Party  Report Details</h5>
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
            $(document).ready(function () {
                // Function to open and populate the transaction report modal
                function openTransactionReportDetailsModal() {
                    // Get dynamic values from button attributes
                    var userType = $(this).data('transaction-name');
                    var id = $(this).data('purchase-id');

                    $.ajax({
                        url: `${pageContext.request.contextPath}/partyReportDetails`,
                        type: 'GET',
                        data: {
                            userType: userType,
                            id: id
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
            function filterCustomers() {
                var input, filter, div, i;
                input = document.getElementById('customerSearch');
                filter = input.value.toUpperCase();
                div = document.getElementById('customerList');
                customerItems = div.getElementsByClassName('customer-item');

                // Display the customer list if there's any input
                if (filter.length > 0) {
                    div.style.display = 'block';
                } else {
                    div.style.display = 'none';
                }

                // Loop through all customer items, and hide those who don't match the search query
                for (i = 0; i < customerItems.length; i++) {
                    txtValue = customerItems[i].textContent || customerItems[i].innerText;
                    if (txtValue.toUpperCase().indexOf(filter) > -1) {
                        customerItems[i].style.display = "";
                    } else {
                        customerItems[i].style.display = "none";
                    }
                }
            }

            function showCustomerList() {
                var div = document.getElementById('customerList');
                div.style.display = 'block';
            }

            function selectCustomer(name, id) {
                document.getElementById('customerSearch').value = name;
                document.getElementById('hiddenUserId').value = id;
                document.getElementById('customerList').style.display = 'none';
            }

            document.addEventListener('click', function (event) {
                var isClickInside = document.getElementById('customerSearch').contains(event.target) ||
                        document.getElementById('customerList').contains(event.target);
                if (!isClickInside) {
                    document.getElementById('customerList').style.display = 'none';
                }
            });
        </script>




        <script>
            function filterCustomers() {
                var input, filter, customerList, customerItems, customerName, i;
                input = document.getElementById('customerSearch');
                filter = input.value.toUpperCase();
                customerList = document.getElementById('customerList');
                customerItems = customerList.getElementsByClassName('customer-item');
                // Show the customer list if there is any input
                if (input.value) {
                    customerList.style.display = "";
                } else {
                    customerList.style.display = "none";
                }
                for (i = 0; i < customerItems.length; i++) {
                    customerName = customerItems[i].innerText || customerItems[i].textContent;
                    if (customerName.toUpperCase().indexOf(filter) > -1) {
                        customerItems[i].style.display = "";
                    } else {
                        customerItems[i].style.display = "none";
                    }
                }
            }
            function selectCustomer(customerName, customerId) {
                console.log("customerId: " + customerId); // Debugging output
                var input = document.getElementById('customerSearch');
                var hiddenInput = document.getElementById('hiddenUserId');
                input.value = customerName;
                hiddenInput.value = customerId;
                // Hide the customer list once a selection is made
                document.getElementById('customerList').style.display = "none";
            }

            function getSaleData() {
                document.saleForm.action = "saleListByDateRangeAndFilters";
                document.saleForm.submit();
            }


        </script>


        <script>
            let currentFocus = -1;

            function showCustomerList() {
                document.getElementById("customerList").style.display = "block";
            }

            function handleKeyEvents(event) {
                let x = document.getElementById("customerList");
                if (!x)
                    return false;

                let items = x.getElementsByClassName("customer-item");
                let input = document.getElementById("customerSearch").value.toUpperCase();

                // Filter items
                let visibleItems = [];
                for (let i = 0; i < items.length; i++) {
                    let customerName = items[i].textContent || items[i].innerText;
                    if (customerName.toUpperCase().indexOf(input) > -1) {
                        items[i].style.display = "";
                        visibleItems.push(items[i]);
                    } else {
                        items[i].style.display = "none";
                    }
                }

                if (event.keyCode == 40) { // Down key
                    currentFocus++;
                    addActive(visibleItems);
                    scrollIntoView(visibleItems);
                } else if (event.keyCode == 38) { // Up key
                    currentFocus--;
                    addActive(visibleItems);
                    scrollIntoView(visibleItems);
                } else if (event.keyCode == 13) { // Enter key
                    event.preventDefault();
                    if (currentFocus > -1) {
                        if (visibleItems)
                            visibleItems[currentFocus].click();
                    }
                }
            }

            function addActive(items) {
                if (!items)
                    return false;
                removeActive(items);
                if (currentFocus >= items.length)
                    currentFocus = 0;
                if (currentFocus < 0)
                    currentFocus = items.length - 1;
                if (items.length > 0)
                    items[currentFocus].classList.add("active");
            }

            function removeActive(items) {
                for (let i = 0; i < items.length; i++) {
                    items[i].classList.remove("active");
                }
            }

            function selectCustomer(name, id) {
                document.getElementById("customerSearch").value = name;
                document.getElementById("hiddenUserId").value = id;
                document.getElementById("customerList").style.display = "none";
                currentFocus = -1;
            }

            function scrollIntoView(items) {
                if (currentFocus >= 0 && currentFocus < items.length) {
                    items[currentFocus].scrollIntoView({behavior: "smooth", block: "nearest"});
                }
            }

        </script>


        <script>
            // Submit form with button click
            function myFormSumbitButton() {
                document.myForm.action = "getPartyReportByDateRangeAndReportName";
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