<%@page import="com.AvMeditechInventory.dtos.ProductDto"%>
<%@page import="java.util.List"%>
<%@page import="com.AvMeditechInventory.dtos.Response"%>
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
                                    <h4>Sales List</h4>
                                    <h6>Manage Your Sales</h6>
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


                                <!-- Export to Excel Link -->
                                <li>
                                    <a id="exportLink" href="#" 
                                       data-baseurl="${pageContext.request.contextPath}/saleListExportToExcel"
                                       data-startdate="${startDate}"
                                       data-enddate="${endDate}"
                                       data-productname="${productName}"
                                       data-customername="${customerName}"
                                       data-branchname="${channelId1}"
                                       title="Export to Excel" data-bs-toggle="tooltip" data-bs-placement="top">
                                        <img src="${pageContext.request.contextPath}/image/icons/excel.svg" alt="Export to Excel">
                                    </a>
                                </li>





                                <li>
                                    <a data-bs-toggle="tooltip" data-bs-placement="top" title="Collapse" id="collapse-header"><i data-feather="chevron-up" class="feather-chevron-up"></i></a>
                                </li>
                            </ul>
                            <div class="page-btn mob-view">
                                <a href="${pageContext.request.contextPath}/saleCreate" class="btn btn-added"><i data-feather="plus-circle" class="me-2"></i>Add New Sales</a>
                            </div>


                        </c:if>
                    </div>


                    <!-- /product list -->
                    <div class="card table-list-card">
                        <div class="card-body">
                            <div class="table-top">
                                <div class="row">
                                    <form name="saleForm" action="" method="post" class="row" id="saleForm">
                                        <div class="col-lg-3 col-md-6 col-sm-12">
                                            <div class="mb-3 add-product">
                                                <label for="startDate" class="form-label">Start Date</label>
                                                <input type="date" class="form-control" id="startDate" name="startDate" value="${startDate}">
                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-md-6 col-sm-12">
                                            <div class="mb-3 add-product">
                                                <label for="endDate" class="form-label">End Date</label>
                                                <input type="date" class="form-control" id="endDate" name="endDate" value="${endDate}">
                                            </div>
                                        </div>

                                        <div class="col-lg-3 col-md-6 col-12">
                                            <div class="mb-3 add-product">
                                                <label class="form-label" for="saleType">Sale Type</label>
                                                <select id="saleType" name="saleType" class="form-control" onchange="handleSaleTypeChange()">
                                                    <option value="" ${saleType == '' ? 'selected' : ''}>Sales & Transfers</option>
                                                    <option value="Sale Create" ${saleType == 'Sale Create' ? 'selected' : ''}>Sale to customer</option>
                                                    <option value="Internal Transfer" ${saleType == 'Internal Transfer' ? 'selected' : ''}>Internal Transfer</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="col-lg-3 col-md-6 col-sm-12" id="customer-dropdown">
                                            <div class="mb-3 add-product product-section">
                                                <label for="customerName" class="form-label">Customer Name</label>
                                                <input type="text" class="form-control" id="customerSearch" placeholder="Search Customer" onkeyup="handleKeyEvents(event)" name="customerName" value="${customerName}" autocomplete="off" onclick="showCustomerList()">
                                                <input type="hidden" name="customerId" id="hiddenUserId"> <!-- Hidden input field for customer ID -->
                                                <div id="customerList" style="display: none; border: 1px solid #ccc; position: absolute; background-color: white; z-index: 1000;">
                                                    <% List<Response> customerList = (List<Response>) request.getAttribute("customerNameList");
                                                        if (customerList != null && !customerList.isEmpty()) {
                                                            for (Response userResponse : customerList) {
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

                                        <!-- Check if saleType is 'Internal Transfer' initially when the page loads -->

                                        <!-- Channel Dropdown (Initially hidden using JavaScript toggle or JSTL check) -->
                                        <div id="channel-dropdown" class="col-lg-3 col-md-6 col-sm-12">
                                            <label for="channelId1" class="form-label">Branch Name</label>
                                            <select id="channelId1" name="channelId1" class="form-control">
                                                <option value="">Select Branch</option>
                                                <!-- Loop through channels using JSTL -->
                                                <c:forEach var="channel" items="${channels}">
                                                    <c:if test="${channel.channel_id != sessionScope.selectedStoreId}">
                                                        <option value="${channel.channel_id}" ${channel.channel_id == channelId1 ? 'selected' : ''}>
                                                            ${channel.channelName}
                                                        </option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
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





                                        <div class="col-12">
                                            <div class="mb-3 add-product">
                                                <button type="button" onclick="getSaleData()" class="btn btn-submit sub-btn">Submit</button>
                                            </div>
                                        </div>

                                    </form>
                                </div>
                            </div>
                            <div class="table-responsive table-container d-none d-md-block">
                                <table class="table  datanew1">
                                    <thead>
                                        <tr>

                                            <th>Challan No</th>
                                            <th>Name</th>
                                            <th>Remarks</th>
                                            <th>Date</th>
                                            <th>Quantity</th>
                                            <th class="text-center">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach var="sale" items="${saleList}">
                                            <tr>
                                                <td>${sale.autoId}</td>
                                                <td>${sale.fullName}</td>
                                                <td>${sale.remarks}</td>
                                                <td>${sale.date}</td>
                                                <td>${sale.quantity}</td>
                                                <td class="action-table-data">
                                                    <div class="edit-delete-action">

                                                        <a class="me-2 p-2" href="${pageContext.request.contextPath}/getSaleById?saleId=${sale.id}">
                                                            <i data-feather="edit" class="feather-edit"></i>
                                                        </a>
                                                        <c:choose>
                                                            <c:when test="${sale.saleType == 'Sale Create'}">
                                                                <a class="confirm-text p-2" href="${pageContext.request.contextPath}/deleteSaleById?saleId=${sale.id}">
                                                                    <i data-feather="trash-2" class="feather-trash-2"></i>
                                                                </a>
                                                            </c:when>
                                                        </c:choose>
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
                    <!-- /product list -->
                    <!--add stock mobile view-->
                    <div class="card-container d-md-none">
                        <c:forEach var="sale" items="${saleList}">
                            <div class="card">
                                <div class="card-body">
                                    <p><strong>Sale Id:</strong> ${sale.id}</p>
                                    <p><strong>Customer Name:</strong> ${sale.fullName}</p>
                                    <p><strong>Reference:</strong> ${sale.referenceNumber}</p>
                                    <p><strong>Date:</strong> ${sale.date}</p>
                                    <p><strong>Status:</strong> <span class="badge badge-bgsuccess">${sale.status}</span></p>
                                    <!--                                    <div class="text-center">
                                                                            <a class="action-set" href="javascript:void(0);" data-bs-toggle="dropdown" aria-expanded="true">
                                                                                <i class="fa fa-ellipsis-v" aria-hidden="true"></i>
                                                                            </a>
                                                                            <ul class="dropdown-menu">
                                                                                <li>
                                                                                    <a href="${pageContext.request.contextPath}/getSaleById?saleId=${sale.id}" class="dropdown-item"><i data-feather="eye" class="info-img"></i>Sale Detail</a>
                                                                                </li>
                                                                            </ul>
                                                                        </div>-->
                                </div>
                            </div>
                        </c:forEach>
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

        <!-- Bootstrap Tagsinput JS -->
        <script src="${pageContext.request.contextPath}/plugins/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>

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
            function fetchCustomerSuggestions() {
                var filter = document.getElementById("customerName").value.trim();
                var suggestionList = [];
                var suggestionsDiv = document.getElementById("customer-suggestions");

                $.ajax({
                    type: "GET",
                    url: "${pageContext.request.contextPath}/customerNameList/json",
                    data: {
                        customerName: filter
                    },
                    success: function (response) {
                        console.log("Response received:", response);
                        suggestionList = [];
                        for (var i = 0; i < response.length; i++) {
                            var fullName = response[i].firstName + " " + response[i].lastName;
                            suggestionList.push(fullName);
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
                                document.getElementById("customerName").value = this.textContent;
                                suggestionsDiv.style.display = "none";
                            });
                            suggestionsDiv.appendChild(suggestionItem);
                        }

                        suggestionsDiv.style.display = "block";
                    },
                    error: function (xhr, status, error) {
                        console.error("Error: " + error);
                        // Handle error display as needed
                        suggestionsDiv.innerHTML = "<p>Error occurred while fetching data.</p>";
                        suggestionsDiv.style.display = "block"; // Display error message
                    }
                });
            }

            document.addEventListener("click", function (event) {
                var suggestionsDiv = document.getElementById("customer-suggestions");
                if (event.target !== suggestionsDiv && !suggestionsDiv.contains(event.target)) {
                    suggestionsDiv.style.display = "none";
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
            $(".confirm-text").on("click", function (event) {
                event.preventDefault(); // Prevent the default action of the link
                var deleteUrl = $(this).attr("href"); // Get the delete URL from the link's href attribute
                var startDate = document.getElementById("startDate").value;
                var endDate = document.getElementById("endDate").value;
                var companyName = document.getElementById("customerSearch").value;
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
            // Function to handle saleType changes dynamically
            function handleSaleTypeChange() {
                var saleType = document.getElementById("saleType").value;
                console.log("saleType: " + saleType);

                if (saleType === "Internal Transfer") {
                    document.getElementById("channel-dropdown").style.display = "block";
                    document.getElementById("customer-dropdown").style.display = "none";
                } else if (saleType === "Sale Create") {
                    document.getElementById("channel-dropdown").style.display = "none";
                    document.getElementById("customer-dropdown").style.display = "block";
                } else {
                    document.getElementById("channel-dropdown").style.display = "none";
                    document.getElementById("customer-dropdown").style.display = "none";
                }

                // Call function to update the export URL
                handleURLSaleTypeChange();
            }

            // Function to update the export link dynamically
            function handleURLSaleTypeChange() {
                var saleType = document.getElementById("saleType").value; // Get selected sale type dynamically
                var exportLink = document.getElementById("exportLink");  // Get export link

                // Fetching encoded values
                var baseUrl = encodeURIComponent(exportLink.getAttribute("data-baseurl"));
                var startDate = encodeURIComponent(exportLink.getAttribute("data-startdate"));
                var endDate = encodeURIComponent(exportLink.getAttribute("data-enddate"));
                var productName = encodeURIComponent(exportLink.getAttribute("data-productname"));
                var customerName = encodeURIComponent(exportLink.getAttribute("data-customername"));
                var branchName = encodeURIComponent(exportLink.getAttribute("data-branchname"));

                var url = decodeURIComponent(baseUrl) + "?startDate=" + startDate +
                        "&endDate=" + endDate +
                        "&saleType=" + encodeURIComponent(saleType);

                if (saleType === "Sale Create") {
                    url += "&customerName=" + customerName + "&productName=" + productName;
                } else if (saleType === "Internal Transfer") {
                    url += "&channelId1=" + branchName + "&productName=" + productName;
                } else {
                    url += "&productName=" + productName;
                }

                console.log("Updated Export URL:", url);
                exportLink.href = url;
            }

            // Run function on page load
            window.onload = function () {
                handleSaleTypeChange();
            };

        </script>
    </body>
</html>