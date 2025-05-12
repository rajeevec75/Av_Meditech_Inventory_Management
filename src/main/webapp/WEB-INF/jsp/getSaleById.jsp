<%-- 
    Document   : saleCreate
    Created on : May 17, 2024, 4:24:55 PM
    Author     : Rajeev kumar
--%>

<%@page import="java.util.List"%>
<%@page import="com.AvMeditechInventory.dtos.Response"%>
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
                                    <h2>Show Sale Details</h2>
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
                        <ul class="table-top-head d-flex justify-content-between align-items-center">
                            <li>
                                <div class="page-btn">

                                    <a href="javascript:history.go(-1)" class="btn btn-secondary"><i data-feather="arrow-left" class="me-2"></i>Back</a>

                                </div>

                            </li>
                            <li>
                                <div class="page-btn">
                                    <a target="_blank" href="${pageContext.request.contextPath}/createChallan?saleId=${response.sale.saleId}" class="btn btn-secondary">Print Challan</a>
                                </div>
                            </li>
                            <li>
                                <div class="page-btn">
                                    <a target="_blank" href="${pageContext.request.contextPath}/saleDeliveryChallanQuantity?saleId=${response.sale.saleId}" class="btn btn-secondary">Delivery Challan Quantity</a>
                                </div>
                            </li>

                        </ul>

                    </div>


                    <!-- /add -->
                    <form name="saleForm" action="" method="post" id="saleForm">
                        <div class="row">

                            <div class="col-lg-4 col-sm-6 col-12">
                                <div class="input-blocks">
                                    <label class="form-label">Sale Type</label>
                                    <div class="form-control">
                                        ${response.sale.saleType == 'Sale Create' ? 'Sale to Customer' : response.sale.saleType}
                                    </div>
                                </div>
                            </div>

                            <!-- Check if saleType is 'saleCreate' -->
                            <c:if test="${saleType == 'Sale Create'}">
                                <div class="col-lg-3 col-md-6 col-sm-12">
                                    <div class="mb-3 add-product product-section">
                                        <label for="customerName" class="form-label">Customer Name</label>
                                        <input type="hidden" name="saleId" value="${response.sale.saleId}" />
                                        <input type="text" value="${response.sale.name}" class="form-control" id="customerSearch" 
                                               placeholder="Search Customer" onkeyup="handleKeyEvents(event)" name="customerName" 
                                               autocomplete="off" onclick="showCustomerList()">
                                        <input type="hidden" name="customerId" id="hiddenUserId" value="${response.sale.customerId}">
                                        <div id="customerList" style="display: none; border: 1px solid #ccc; position: absolute; background-color: white; z-index: 1000;">
                                            <% List<Response> customerList = (List<Response>) request.getAttribute("customerNameList");
                                                if (customerList != null && !customerList.isEmpty()) {
                                                    for (Response userResponse : customerList) {
                                            %>
                                            <div class="customer-item" onclick="selectCustomer('<%= userResponse.getCompanyName()%>', '<%= userResponse.getId()%>')">
                                                <%= userResponse.getCompanyName()%> (Code: <%= userResponse.getUserCode()%>)
                                            </div>
                                            <% }
                                                }%>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <!-- Check if saleType is 'InternalTransfer' -->
                            <c:if test="${saleType == 'Internal Transfer'}">
                                <!-- Channel Dropdown (Visible only for InternalTransfer) -->
                                <div id="channel-dropdown" class="col-lg-4 col-sm-6 col-12">
                                    <label for="channelId1" class="form-label">Company Name:</label>
                                    <input type="hidden" name="saleId" value="${response.sale.saleId}" />
                                    <select id="channelId1" name="channelId1" class="form-control">
                                        <!-- Loop through channels using JSTL -->
                                        <c:forEach var="channel" items="${channels}">
                                            <option value="${channel.channel_id}" 
                                                    <c:if test="${channel.channel_id == channelId1FromDatabase}">selected</c:if>>
                                                ${channel.channelName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </c:if>

                            <script>
                                let currentIndex = -1;

                                function handleKeyEvents(event) {
                                    const customerListDiv = document.getElementById('customerList');
                                    const items = customerListDiv.querySelectorAll('.customer-item');

                                    // Show the customer list when the user starts typing
                                    if (event.target.value.length > 0) {
                                        customerListDiv.style.display = 'block';
                                    } else {
                                        customerListDiv.style.display = 'none';
                                    }

                                    if (event.key === 'ArrowDown') {
                                        // Move selection down
                                        currentIndex = (currentIndex + 1) % items.length;
                                        highlightItem(items);
                                    } else if (event.key === 'ArrowUp') {
                                        // Move selection up
                                        currentIndex = (currentIndex - 1 + items.length) % items.length;
                                        highlightItem(items);
                                    } else if (event.key === 'Enter') {
                                        // Select the highlighted item
                                        if (currentIndex >= 0) {
                                            items[currentIndex].click();
                                        }
                                    } else {
                                        // Filter items based on input
                                        filterCustomerList(event.target.value);
                                    }
                                }

                                function highlightItem(items) {
                                    // Remove highlight from all items
                                    items.forEach(item => {
                                        item.style.backgroundColor = '';
                                    });

                                    // Highlight the current item
                                    if (currentIndex >= 0) {
                                        items[currentIndex].style.backgroundColor = '#f0f0f0'; // Highlighted background color
                                        items[currentIndex].scrollIntoView({block: 'nearest'});
                                    }
                                }

                                function filterCustomerList(query) {
                                    const customerListDiv = document.getElementById('customerList');
                                    const items = customerListDiv.querySelectorAll('.customer-item');

                                    items.forEach(item => {
                                        if (item.textContent.toLowerCase().includes(query.toLowerCase())) {
                                            item.style.display = 'block';
                                        } else {
                                            item.style.display = 'none';
                                        }
                                    });
                                }

                                function showCustomerList() {
                                    document.getElementById('customerList').style.display = 'block';
                                }

                                function selectCustomer(name, id) {
                                    document.getElementById('customerSearch').value = name;
                                    document.getElementById('hiddenUserId').value = id;
                                    document.getElementById('customerList').style.display = 'none'; // Hide the list after selection
                                }
                            </script>



                            <!-- Date  -->
                            <div class="col-lg-4 col-sm-6 col-12">
                                <div class="mb-3 add-product">
                                    <label class="form-label">Date</label>
                                    <input type="date" class="form-control" value="${response.sale.saleDate}" name="sale_date">
                                </div>
                            </div>

                            <input type="hidden" class="form-control" name="saleType" value="${saleType}" >
                            <input type="hidden" class="form-control" name="referenceNo" value="${response.sale.referenceNo}" >

                            <div class="col-lg-4 col-sm-6 col-12">
                                <div class="input-blocks">
                                    <label>Remarks</label>
                                    <div class="input-groupicon calender-input">
                                        <input type="text" class="form-control" name="remarks" value="${response.sale.remarks}" >
                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-4 col-sm-6 col-12">
                                <div class="mb-3 add-product">
                                    <label class="form-label">Sale ID</label>
                                    <input type="text" class="form-control" readonly value="${response.sale.saleSeqId}" >
                                </div>
                            </div>

                            <c:if test="${saleType == 'Sale Create'}">
                                <div class="col-lg-12 col-sm-6 col-12">
                                    <div class="input-blocks">

                                        <label for="productSerialNumber">Product Serial Number</label>
                                        <div class="input-groupicon select-code">
                                            <input type="text" style="padding-left: 15px;" id="productSerialNumber" placeholder="Enter Product Serial Number">
                                            <div class="addonset">
                                                <img id="submitBtn" src="${pageContext.request.contextPath}/image/icons/qrcode-scan.svg" alt="Scan QR Code">
                                            </div>
                                            <div id="productList" class="product-list" 
                                                 style="display: none; border: 1px solid #ccc; position: absolute;
                                                 background-color: white; z-index: 1000;">
                                                <!-- Suggestions will be dynamically added here -->
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <div class="col-lg-12">
                                <div class="alert alert-danger" id="errMessage" style="display: none" role="alert">

                                </div>
                                <div class="modal-body-table">
                                    <div class="table-responsive">
                                        <table class="table  datanew1">
                                            <thead>
                                                <tr>

                                                    <th>Product</th>
                                                    <th>Qty</th>
                                                        <c:if test="${saleType == 'Sale Create'}">
                                                        <th>Action</th>
                                                        </c:if>
                                                </tr>

                                            </thead>
                                            <tbody class="">




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
                                                <h5>Rs </h5><h5 id="purchaseSubtotal">0.00</h5>
                                            </li>
                                            <li>
                                                <h4>Order Tax (GST)</h4>
                                                <h5>Rs </h5><h5 id="purchaseTax">0.00</h5>
                                            </li>
                                            <li>
                                                <h4>Discount</h4>
                                                <h5>Rs </h5><h5 id="purchaseDiscount">0.00</h5>
                                            </li>
                                            <li>
                                                <h4>Shipping</h4>
                                                <div class="input-group" style="left: 250px;">
                                                    <span class="input-group-text">Rs</span>
                                                    <input id="purchaseShipping" onchange="addShipping()" value ="0" type="number" class="form-control text-end" style="max-width: 100px;" name="shippingAmount" placeholder="0.00">
                                                </div>
                                            </li>
                                            <li>
                                                <h4>Grand Total</h4>
                                                <h5>Rs </h5><h5 id="purchaseTotal">0.00</h5>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="loader" id="loader"></div>
                                <div class="col-lg-12 text-end">
                                    <!--<button type="button" class="btn btn-cancel add-cancel me-3" data-bs-dismiss="modal">Cancel</button>-->
                                    <button type="button" id="sbmtBtn" onclick="addSaleData()" class="btn btn-submit add-sale">Submit</button>
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
                                                        <h4 id="serialPattern"> Serial Number </h4>
                                                    </div>
                                                    <button onclick="deleteSerialAllData()" type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>
                                                <div class="card">
                                                    <div class="card-body">
                                                        <form action="" method="post">
                                                            <div class="row">

                                                                <div class="col-lg-12 col-sm-6 col-12">
                                                                    <div class="input-blocks">
                                                                        <!--<label for="productSerialNumber">Product Serial Number</label>-->
                                                                        <div class="input-groupicon select-code">
                                                                            <input type="hidden" id="productSerialPattern" />
                                                                            <input type="hidden" id="productSerialId" />
                                                                            <input type="hidden" id="productSerialUp" />
                                                                            <input type="hidden" id="productSerialGst" />
                                                                            <input type="hidden" name="purchaseData" id="purchaseData" />
                                                                            <input type="hidden" name="purchaseSerialData" id="purchaseSerialData" />
                                                                            <!--                                                                        <input type="text" style="padding-left: 15px;" id="productSerialNumber" placeholder="Enter Product Serial Number">
                                                                                                                                                    <div class="addonset">
                                                                                                                                                        <img id="submitBtn" src="${pageContext.request.contextPath}/image/icons/qrcode-scan.svg" alt="Scan QR Code">
                                                                                                                                                    </div>-->
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-12">
                                                                <div class="modal-body-table">
                                                                    <div class="table-responsive">
                                                                        <table class="table  datanew2">
                                                                            <thead>
                                                                                <tr>

                                                                                    <th>Serial Number</th>
                                                                                    <th>Expiry Date</th>
                                                                                    <!--<th>Action</th>-->
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
                                                            <!--</form>-->
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- /add popup -->


                        </div>
                    </form>
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
                                                                        var purchaseDataArray = [];
                                                                        var purchaseDataSerialArray = [];
                                                                        var index = 0;
                                                                        function onWindowLoad() {
                <c:forEach var="item" items="${response.saleItemsMasters}">
                                                                            var newRow = '';
                                                                            if ('${item.trackingSerialNo}' === 'true') {
                                                                                newRow = '<tr id = "row' + '${item.productId}' + '">' +
                                                                                        '<td><a href="#" class="btn btn-added" onclick=showPopupData(\'' + '${item.productId}' + '\') data-bs-toggle="modal" data-bs-target="#add-sales-new">' + '${item.productName}' + '</a></td>' +
                                                                                        '<td><input id = "quantity' + '${item.productId}' + '" readonly style="width:75px;" type="number" value="' + '${item.quantity}' + '"/><input style="width:75px;" id = "batchSerialNo' + '${item.productId}' + '" type="hidden" value=""/></td>' +
                                                                                        '<td id = "deletetd" class="action-table-data"><div class="edit-delete-action">' +
                                                                                        '<a class="confirm-text p-2" onclick="deleteData(\'row' + '${item.productId}' + '\',\'0\')"><i data-feather="trash-2" class="feather-trash-2"></i></a>' +
                                                                                        '</div></td>' +
                                                                                        '</tr>';
                                                                                purchaseDataArray.push('${item.productId}' + "|" + '${item.quantity}' + "|0|0|0|0|0|a0|" + '${item.itemBarCode}' + "|" + '${item.isBatch}');
                                                                            } else {
                                                                                if ('${item.isBatch}') {
                                                                                    newRow = '<tr id = "row' + index + '">' +
                                                                                            '<td><a href="#" class="btn btn-added" onclick=showPopupData(\'' + '${item.productId}' + '\') data-bs-toggle="modal" data-bs-target="#add-sales-new">' + '${item.productName}' + '</a></td>' +
                                                                                            '<td><input readonly id = "quantity' + index + '" onchange = "editDataRow1(\'row' + index + '\',\'0\',\'\',\'0\',\'' + '${item.itemBarCode}' + '\')" style="width:100px;" type="number" value="' + '${item.quantity}' + '"/><br /><input type="hidden" id="batchSerialNo' + index + '" onchange = "editDataRow1(\'row' + index + '\',\'0\',\'\',\'0\',\'' + '${item.itemBarCode}' + '\')" value="' + '${item.itemBarCode}' + '" style="width:100px;">';

//                                                                                    for (var i = 0; i < myArray.length; i++) {
//                                                                                        newRow = newRow + '<option value="' + myArray[i] + '">' + myArray[i] + '</option>';
//                                                                                    }

                                                                                    newRow = newRow + '</input></td>' +
                                                                                            '<td id = "deletetd" class="action-table-data"><div class="edit-delete-action">' +
                                                                                            '<a class="confirm-text p-2" onclick="deleteData2(\'row' + index + '\',\'0\', \'' + '${item.productId}' + '\')"><i data-feather="trash-2" class="feather-trash-2"></i></a>' +
                                                                                            '</div></td>' +
                                                                                            '</tr>';
                                                                                } else {
                                                                                    newRow = '<tr id = "row' + index + '">' +
                                                                                            '<td>' + '${item.productName}' + '</td>' +
                                                                                            '<td><input readonly id = "quantity' + index + '" onchange = "editDataRow1(\'row' + index + '\',\'0\',\'\',\'0\',\'' + '${item.itemBarCode}' + '\')" style="width:75px;" type="number" value="' + '${item.quantity}' + '"/><input style="width:75px;" id = "batchSerialNo' + index + '" type="hidden" value=""/></td>' +
                                                                                            '<td id = "deletetd" class="action-table-data"><div class="edit-delete-action">' +
                                                                                            '<a class="confirm-text p-2" onclick="deleteData(\'row' + index + '\',\'0\')"><i data-feather="trash-2" class="feather-trash-2"></i></a>' +
                                                                                            '</div></td>' +
                                                                                            '</tr>';
                                                                                }
                                                                                purchaseDataArray.push('${item.productId}' + "|" + '${item.quantity}' + "|0|0|0|0|0|" + index + "|" + '${item.itemBarCode}' + "|" + '${item.itemSerialNo}');
                                                                                index = index + 1;
                                                                            }
                                                                            $('.datanew1 tbody').prepend(newRow);
                </c:forEach>
                <c:forEach var="item" items="${response.purchaseItemSerialMasters}">
                                                                            purchaseDataSerialArray.push('${item.productId}' + "|" + '${item.itemSerialNumber}' + "|" + '${item.itemSerialExpiryDate}' + "|" + '${item.itemBarcode}');
                </c:forEach>

                                                                            document.getElementById("purchaseData").value = purchaseDataArray;
                                                                            document.getElementById("purchaseSerialData").value = purchaseDataSerialArray;
                <c:if test="${saleType == 'Internal Transfer'}">
                                                                            document.getElementById("deletetd").style.display = 'none';
                </c:if>
                                                                        }

                                                                        // Attach the onWindowLoad function to the window's onload event
                                                                        window.onload = onWindowLoad;
                                                                        $(document).ready(function () {
                                                                            var addedSerialNumbers = []; // Array to store already added serial numbers
                                                                            var addedRowsData = []; // Array to store added rows data

                                                                            $('#submitBtn').click(function (e) {
                                                                                e.preventDefault();
                                                                                sendAjaxRequest();
                                                                            });

                                                                            $('#productSerialNumber').keypress(function (event) {
                                                                                if (event.key === 'Enter') {
                                                                                    event.preventDefault();
                                                                                    sendAjaxRequest();
                                                                                }
                                                                            });

                                                                            function sendAjaxRequest() {
                                                                                var productSerialNumber = $('#productSerialNumber').val();
                                                                                console.log("productSerialNumber" + productSerialNumber);

                                                                                $.ajax({
                                                                                    type: "GET",
                                                                                    url: "${pageContext.request.contextPath}/saleCreate/json",
                                                                                    data: {
                                                                                        itemSerialNumber: productSerialNumber
                                                                                    },
                                                                                    success: function (response) {
                                                                                        console.log("Response received:", response);
                                                                                        if (response.productName) {
                                                                                            document.getElementById("errMessage").style.display = 'none';
                                                                                            document.getElementById("errMessage").innerHTML = '';
                                                                                            // Add serial number to the list
                                                                                            addedSerialNumbers.push(productSerialNumber);
                                                                                            document.getElementById("productSerialNumber").value = null;

                                                                                            if (!(!response.trackingSerialNo && response.batchSerialNo)) {
                                                                                                // Check if the serial number is already added
                                                                                                for (var i = 0; i < purchaseDataArray.length; i++) {
                                                                                                    var serialNo = purchaseDataArray[i].split("|")[8];
                                                                                                    if (serialNo === productSerialNumber) {
                                                                                                        console.log("Serial number already added");
                                                                                                        return; // Exit function if serial number is already added
                                                                                                    }
                                                                                                }
                                                                                            }

                                                                                            // Construct a new row with the received data
                                                                                            var newRow = '';
                                                                                            var isEntryNew = true;
                                                                                            if (!(!response.trackingSerialNo && response.batchSerialNo)) {
                                                                                                for (var i = 0; i < purchaseDataArray.length; i++) {
                                                                                                    var productId1 = purchaseDataArray[i].split("|")[0];
                                                                                                    if (parseFloat(productId1) === parseFloat(response.productId)) {
                                                                                                        isEntryNew = false;
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                            if (isEntryNew) {
                                                                                                var batchNo = '';
                                                                                                if (response.trackingSerialNo) {
                                                                                                    newRow = '<tr id = "row' + response.productId + '">' +
                                                                                                            '<td><a href="#" class="btn btn-added" onclick=showPopupData(\'' + response.productId + '\') data-bs-toggle="modal" data-bs-target="#add-sales-new"><i data-feather="plus-circle" class="me-2"></i>' + response.productName + '</a></td>' +
                                                                                                            '<td><input id = "quantity' + response.productId + '" readonly style="width:75px;" type="number" value="' + response.quantity + '"/><input style="width:75px;" id = "batchSerialNo' + response.productId + '" type="hidden" value=""/></td>' +
                                                                                                            '<td class="action-table-data"><div class="edit-delete-action">' +
                                                                                                            '<a class="confirm-text p-2" target="_blank" var href="${pageContext.request.contextPath}/getDetailstockReportSerialNumberWise?productName=' + response.productName + '&pageNumber=${currentPage}&pageSize=${pageSize}&brand=${selectedBrandId}&productType=${selectedProductTypeId}&startDate=${startDate}&endDate=' + response.itemSerialExpiry + '"><i data-feather="edit" class="feather-edit"></i></a>' +
                                                                                                            '<a class="confirm-text p-2" onclick="deleteData(\'row' + response.productId + '\',\'' + response.gstPercent + '\')"><i data-feather="trash-2" class="feather-trash-2"></i></a>' +
                                                                                                            '</div></td>' +
                                                                                                            '</tr>';
                                                                                                    purchaseDataArray.push(response.productId + "|1|0|0|0|0|0|a0|" + response.itemBarCode + "|" + batchNo);
                                                                                                } else {
                                                                                                    if (response.batchSerialNo) {
                                                                                                        const myArray = response.batchNo.split("|");
                                                                                                        batchNo = myArray[0].split(",")[0];
                                                                                                        newRow = '<tr id = "roww' + index + '">' +
                                                                                                                '<td>' + response.productName + '</td>' +
                                                                                                                '<td><input id = "quantity' + index + '" onchange = "editDataRow2(\'roww' + index + '\',\'' + response.gstPercent + '\',\'' + response.serialNumber + '\',\'' + response.costPrice + '\',\'' + response.itemBarCode + '\')" style="width:100px;" type="number" value="' + response.quantity + '"/><br /><select id="batchSerialNo' + index + '" onchange = "editDataRow2(\'roww' + index + '\',\'' + response.gstPercent + '\',\'' + response.serialNumber + '\',\'' + response.costPrice + '\',\'' + response.itemBarCode + '\')" style="width:100px;">';

                                                                                                        for (var i = 0; i < myArray.length; i++) {
                                                                                                            newRow = newRow + '<option value="' + myArray[i].split(",")[0] + '">' + myArray[i] + '</option>';
                                                                                                        }

                                                                                                        newRow = newRow + '</select></td>' +
                                                                                                                '<td class="action-table-data"><div class="edit-delete-action">' +
                                                                                                                '<a class="confirm-text p-2" onclick="deleteData1(\'roww' + index + '\',\'' + response.gstPercent + '\')"><i data-feather="trash-2" class="feather-trash-2"></i></a>' +
                                                                                                                '</div></td>' +
                                                                                                                '</tr>';
                                                                                                    } else {
                                                                                                        newRow = '<tr id = "row' + index + '">' +
                                                                                                                '<td>' + response.productName + '</td>' +
                                                                                                                '<td><input id = "quantity' + index + '" onchange = "editDataRow1(\'row' + index + '\',\'' + response.gstPercent + '\',\'' + response.serialNumber + '\',\'' + response.costPrice + '\',\'' + response.itemBarCode + '\')" style="width:75px;" type="number" value="' + response.quantity + '"/><input style="width:75px;" id = "batchSerialNo' + index + '" type="hidden" value=""/></td>' +
                                                                                                                '<td class="action-table-data"><div class="edit-delete-action">' +
                                                                                                                '<a class="confirm-text p-2" onclick="deleteData1(\'row' + index + '\',\'' + response.gstPercent + '\')"><i data-feather="trash-2" class="feather-trash-2"></i></a>' +
                                                                                                                '</div></td>' +
                                                                                                                '</tr>';
                                                                                                    }
                                                                                                    purchaseDataArray.push(response.productId + "|1|0|0|0|0|0|" + index + "|" + response.itemBarCode + "|" + batchNo);
                                                                                                    index = index + 1;
                                                                                                }
                                                                                                $('.datanew1 tbody').prepend(newRow);
                                                                                                document.getElementById("purchaseData").value = purchaseDataArray;
                                                                                            } else {
                                                                                                var row = document.getElementById("row" + response.productId);
                                                                                                var quantity = parseFloat(document.getElementById("quantity" + response.productId).value) + 1;
                                                                                                if (response.trackingSerialNo) {
                                                                                                    row.cells[1].innerHTML = '<td><input id = "quantity' + response.productId + '" readonly style="width:75px;" type="number" value="' + quantity + '"/></td>';
                                                                                                } else {
//                                                                                                    if (response.batchSerialNo) {
//                                                                                                        row.cells[1].innerHTML = '<td><input id = "quantity' + response.productId + '" onchange = "editDataRow(\'row' + response.productId + '\',\'' + response.gstPercent + '\',\'' + response.serialNumber + '\',\'' + response.costPrice + '\',\'' + response.itemBarCode + '\')" style="width:100px;" type="number" value="' + response.quantity + '"/><br /><select id="batchSerialNo' + response.productId + '" onchange = "editDataRow(\'row' + response.productId + '\',\'' + response.gstPercent + '\',\'' + response.serialNumber + '\',\'' + response.costPrice + '\',\'' + response.itemBarCode + '\')" style="width:100px;">';
//                                                                                                        for (var i = 0; i < myArray.length; i++) {
//                                                                                                            newRow = newRow + '<option value="' + myArray[i] + '">' + myArray[i] + '</option>';
//                                                                                                        }
//                                                                                                        newRow = newRow + '</select></td>';
//                                                                                                    } else {
//                                                                                                        row.cells[1].innerHTML = '<td><input id = "quantity' + response.productId + '" onchange = "editDataRow(\'row' + response.productId + '\',\'' + response.gstPercent + '\',\'' + response.serialNumber + '\',\'' + response.costPrice + '\',\'' + response.itemBarCode + '\')" style="width:75px;" type="number" value="' + quantity + '"/></td>';
//                                                                                                    }
                                                                                                }
                                                                                                for (var i = 0; i < purchaseDataArray.length; i++) {
                                                                                                    var productId = purchaseDataArray[i].split("|")[0];
                                                                                                    if (parseFloat(productId) === parseFloat(response.productId)) {
                                                                                                        purchaseDataArray.splice(i, 1);
                                                                                                        purchaseDataArray.push(productId + "|" + quantity + "|0|0|0|0|0|a0|" + response.itemBarCode);
                                                                                                        document.getElementById("purchaseData").value = purchaseDataArray;
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                            var exp = '';
                                                                                            if (null !== response.itemSerialExpiry) {
                                                                                                exp = response.itemSerialExpiry;
                                                                                            }
                                                                                            purchaseDataSerialArray.push(response.productId + "|" + response.serialNumber + "|" + exp + "|" + productSerialNumber);
                                                                                            document.getElementById("purchaseSerialData").value = purchaseDataSerialArray;

                                                                                            // Construct the row data object
                                                                                            var rowData = {
                                                                                                productId: response.productId,
                                                                                                productName: response.productName,
                                                                                                quantity: response.quantity,
                                                                                                itemBarCode: response.itemBarCode,
                                                                                                amount: response.amount,
                                                                                                discount: response.discount,
                                                                                                gstPercent: response.gstPercent,
                                                                                                gst: response.gst,
                                                                                                costPrice: response.costPrice,
                                                                                                totalAmount: response.totalAmount
                                                                                            };

                                                                                            // Add row data to the list
                                                                                            addedRowsData.push(rowData);

                                                                                            // Update the hidden input field value
                                                                                            updateHiddenInputValue();
                                                                                        } else {
                                                                                            document.getElementById("errMessage").style.display = 'block';
                                                                                            document.getElementById("errMessage").innerHTML = 'You have entered wrong barcode!';
                                                                                        }
                                                                                    },
                                                                                    error: function (xhr, status, error) {
                                                                                        console.error("Error: " + error);
                                                                                        $('#barcodeDisplay').html("Error occurred while fetching data.");
                                                                                    }
                                                                                });
                                                                            }

                                                                            // Function to update the value of the hidden input field
                                                                            function updateHiddenInputValue() {
                                                                                $('#hiddenInput').val(JSON.stringify(addedRowsData));
                                                                            }
                                                                        });

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

                                                                        function addSaleData() {
                <c:if test="${saleType == 'Sale Create'}">
                                                                            var supplier = document.getElementById('customerSearch').value;
                                                                            if (supplier === '') {
                                                                                alert("Please select customer!");
                                                                                return false;
                                                                            }
                </c:if>
                                                                            // Start loader before sending request
                                                                            toggleLoader(true);
                                                                            document.saleForm.action = "updateSaleCreate";
                                                                            document.saleForm.submit();

//                                                                            var formData = new FormData();
//                                                                            var purchaseData = document.getElementById('purchaseData').value;
//                                                                            formData.append("purchaseData", purchaseData);
//
//                                                                            $.ajax({
//                                                                                url: "${pageContext.request.contextPath}/checkSaleCreate",
//                                                                                type: "POST",
//                                                                                data: formData,
//                                                                                contentType: false,
//                                                                                processData: false,
//                                                                                success: function (response) {
//                                                                                    var abc = response.split("|")[1];
//                                                                                    if (abc === 'false') {
//                                                                                        alert(response.split("|")[0]);
//                                                                                        return false;
//                                                                                    } else {
//                                                                                        document.saleForm.action = "updateSaleCreate";
//                                                                                        document.saleForm.submit();
//                                                                                    }
//                                                                                },
//                                                                                error: function (err) {
//                                                                                    alert("Error uploading file: " + err.responseText);
//                                                                                }
//                                                                            });
                                                                        }

                                                                        function deleteData(rowId, gst) {
                                                                            var row = document.getElementById(rowId);
                                                                            if (row) {
                                                                                row.parentNode.removeChild(row);
                                                                            }
                                                                            for (var i = 0; i < purchaseDataArray.length; i++) {
                                                                                var productId = purchaseDataArray[i].split("|")[0];
                                                                                if (productId === rowId.substring(3, rowId.length)) {
                                                                                    purchaseDataArray.splice(i, 1);
                                                                                    document.getElementById("purchaseData").value = purchaseDataArray;
                                                                                }
                                                                            }
                                                                        }

                                                                        function deleteData1(rowId, gst) {
                                                                            var row = document.getElementById(rowId);
                                                                            if (row) {
                                                                                row.parentNode.removeChild(row);
                                                                            }
                                                                            for (var i = 0; i < purchaseDataArray.length; i++) {
                                                                                var productId = purchaseDataArray[i].split("|")[7];
                                                                                if (productId === rowId.substring(4, rowId.length)) {
                                                                                    purchaseDataArray.splice(i, 1);
                                                                                    document.getElementById("purchaseData").value = purchaseDataArray;
                                                                                }
                                                                            }
                                                                        }

                                                                        function deleteData2(rowId, gst) {
                                                                            var row = document.getElementById(rowId);
                                                                            if (row) {
                                                                                row.parentNode.removeChild(row);
                                                                            }
                                                                            for (var i = 0; i < purchaseDataArray.length; i++) {
                                                                                var productId = purchaseDataArray[i].split("|")[7];
                                                                                if (productId === rowId.substring(3, rowId.length)) {
                                                                                    purchaseDataArray.splice(i, 1);
                                                                                    document.getElementById("purchaseData").value = purchaseDataArray;
                                                                                }
                                                                            }
                                                                        }




                                                                        function editDataRow(rowId, gst, serialNo, costPrice1, barCode) {
                                                                            // Get the <tr> element by its id
                                                                            var row = document.getElementById(rowId);
                                                                            var quantity = document.getElementById("quantity" + rowId.substring(3, rowId.length)).value;
                                                                            var batchSerialNo = document.getElementById("batchSerialNo" + rowId.substring(3, rowId.length)).value;
                                                                            // Check if the row exists
                                                                            if (row) {
                                                                                // Modify the content of the first cell
                                                                                for (var i = 0; i < purchaseDataArray.length; i++) {
                                                                                    var productId = purchaseDataArray[i].split("|")[0];
                                                                                    if (productId === rowId.substring(3, rowId.length)) {
                                                                                        purchaseDataArray.splice(i, 1);
                                                                                        purchaseDataArray.push(productId + "|" + quantity + "|0|0|0|0|0|a0|" + barCode + "|" + batchSerialNo);
                                                                                        document.getElementById("purchaseData").value = purchaseDataArray;
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                console.error("Row not found!");
                                                                            }
                                                                        }

                                                                        function editDataRow1(rowId, gst, serialNo, costPrice1, barCode) {
                                                                            // Get the <tr> element by its id
                                                                            var row = document.getElementById(rowId);
                                                                            var quantity = document.getElementById("quantity" + rowId.substring(3, rowId.length)).value;
                                                                            var batchSerialNo = document.getElementById("batchSerialNo" + rowId.substring(3, rowId.length)).value;
                                                                            // Check if the row exists
                                                                            if (row) {
                                                                                // Modify the content of the first cell
                                                                                for (var i = 0; i < purchaseDataArray.length; i++) {
                                                                                    var productId = purchaseDataArray[i].split("|")[7];
                                                                                    var productId1 = purchaseDataArray[i].split("|")[0];
                                                                                    if (productId === rowId.substring(3, rowId.length)) {
                                                                                        purchaseDataArray.splice(i, 1);
                                                                                        purchaseDataArray.push(productId1 + "|" + quantity + "|0|0|0|0|0|" + productId + "|" + barCode + "|" + batchSerialNo);
                                                                                        document.getElementById("purchaseData").value = purchaseDataArray;
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                console.error("Row not found!");
                                                                            }
                                                                        }

                                                                        function editDataRow2(rowId, gst, serialNo, costPrice1, barCode) {
                                                                            // Get the <tr> element by its id
                                                                            var row = document.getElementById(rowId);
                                                                            var quantity = document.getElementById("quantity" + rowId.substring(4, rowId.length)).value;
                                                                            var batchSerialNo = document.getElementById("batchSerialNo" + rowId.substring(4, rowId.length)).value;
                                                                            // Check if the row exists
                                                                            if (row) {
                                                                                // Modify the content of the first cell
                                                                                for (var i = 0; i < purchaseDataArray.length; i++) {
                                                                                    var productId = purchaseDataArray[i].split("|")[7];
                                                                                    var productId1 = purchaseDataArray[i].split("|")[0];
                                                                                    if (productId === rowId.substring(4, rowId.length)) {
                                                                                        purchaseDataArray.splice(i, 1);
                                                                                        purchaseDataArray.push(productId1 + "|" + quantity + "|0|0|0|0|0|" + productId + "|" + barCode + "|" + batchSerialNo);
                                                                                        document.getElementById("purchaseData").value = purchaseDataArray;
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                console.error("Row not found!");
                                                                            }
                                                                        }

                                                                        function addShipping() {

                                                                            document.getElementById("purchaseTotal").innerHTML = parseFloat(document.getElementById("purchaseSubtotal").innerHTML) + parseFloat(document.getElementById("purchaseTax").innerHTML) + parseFloat(document.getElementById("purchaseShipping").value) - parseFloat(document.getElementById("purchaseDiscount").innerHTML);
                                                                        }

                                                                        function showPopupData(productSerialId) {
                                                                            document.getElementById('myTableBody').innerHTML = '';
                                                                            for (var i = 0; i < purchaseDataSerialArray.length; i++) {
                                                                                var productId = purchaseDataSerialArray[i].split("|")[0];
                                                                                if (productId === productSerialId) {
                                                                                    var newRow = '<tr>' +
                                                                                            '<td><label>' + purchaseDataSerialArray[i].split("|")[1] + '</label></td>' +
                                                                                            '<td><label>' + purchaseDataSerialArray[i].split("|")[2] + '</label></td>' +
//                                                                                            '<td class="action-table-data"><div class="edit-delete-action"><a class="confirm-text p-2" onclick = "deleteSerialData(\'serial' + purchaseDataSerialArray[i].split("|")[1] + '\')"><i data-feather="trash-2" class="feather-trash-2"></i></a></div></td>' +
                                                                                            '</tr>';
                                                                                    $('.datanew2 tbody').append(newRow);
                                                                                }
                                                                            }
                                                                        }

                                                                        function deleteSerialData(rowId) {
                                                                            alert(rowId)
                                                                            var row = document.getElementById(rowId);
                                                                            if (row) {
                                                                                row.parentNode.removeChild(row);
                                                                                for (var i = 0; i < purchaseDataSerialArray.length; i++) {
                                                                                    var serialNo = purchaseDataSerialArray[i].split("|")[1];
                                                                                    if (serialNo === rowId.substring(6, rowId.length)) {
                                                                                        purchaseDataSerialArray.splice(i, 1);
                                                                                        document.getElementById("purchaseSerialData").value = purchaseDataSerialArray;
                                                                                    }
                                                                                }
                                                                            }
                                                                        }

                                                                        function getImportData() {
                                                                            var formData = new FormData();
                                                                            var fileInput = document.getElementById('file1');
                                                                            var file1 = fileInput.files[0];
                                                                            formData.append("file1", file1);

                                                                            $.ajax({
                                                                                url: "${pageContext.request.contextPath}/importSaleProduct",
                                                                                type: "POST",
                                                                                data: formData,
                                                                                contentType: false,
                                                                                processData: false,
                                                                                success: function (response) {
                                                                                    for (var i = 0, max = response.productPurchase.length; i < max; i++) {
                                                                                        var newRow = '';
                                                                                        if (response.productPurchase[i].trackingSerialNo) {
                                                                                            newRow = '<tr id = "row' + response.productPurchase[i].productId + '">' +
                                                                                                    '<td><a href="#" class="btn btn-added" onclick=showPopupData(\'' + response.productPurchase[i].snoPattern + '\',\'' + response.productPurchase[i].productId + '\',\'' + response.productPurchase[i].costPrice + '\',\'' + response.productPurchase[i].gst + '\') data-bs-toggle="modal" data-bs-target="#add-sales-new"><i data-feather="plus-circle" class="me-2"></i>' + response.productPurchase[i].productName + '</a></td>' +
                                                                                                    '<td><input style="width:75px;" id = "quantity' + response.productPurchase[i].productId + '" onchange = "editDataRow(\'row' + response.productPurchase[i].productId + '\',\'' + response.productPurchase[i].costPrice + '\',\'' + response.productPurchase[i].gst + '\')" type="number" value="' + response.productPurchase[i].quantity + '"/></td>' +
                                                                                                    '<td><input style="width:75px;" id = "costPrice' + response.productPurchase[i].productId + '" onchange = "editDataRow(\'row' + response.productPurchase[i].productId + '\',\'' + response.productPurchase[i].costPrice + '\',\'' + response.productPurchase[i].gst + '\')" type="number" value="' + response.productPurchase[i].costPrice + '"/></td>' +
                                                                                                    '<td><input id = "discount' + response.productPurchase[i].productId + '" onchange = "editDataRow(\'row' + response.productPurchase[i].productId + '\',\'' + response.productPurchase[i].costPrice + '\',\'' + response.productPurchase[i].gst + '\')" style="width:75px;" type="number" value="' + 0 + '"/></td>' +
                                                                                                    '<td><label>' + response.productPurchase[i].gst + '</label></td>' +
                                                                                                    '<td>' + parseFloat(response.productPurchase[i].quantity * (response.productPurchase[i].costPrice - 0) * response.productPurchase[i].gst / 100) + '</td>' +
                                                                                                    '<td>' + parseFloat((response.productPurchase[i].costPrice - 0) * response.productPurchase[i].quantity + (response.productPurchase[i].quantity * (response.productPurchase[i].costPrice - 0) * response.productPurchase[i].gst / 100)) + '</td>' +
                                                                                                    '<td class="action-table-data"><div class="edit-delete-action"><a class="confirm-text p-2" onclick = "deleteData(\'row' + response.productPurchase[i].productId + '\',\'' + response.productPurchase[i].costPrice + '\',\'' + response.productPurchase[i].gst + '\')"><i data-feather="trash-2" class="feather-trash-2"></i></a></div></td>' +
                                                                                                    '</tr>';
                                                                                        } else {
                                                                                            newRow = '<tr id = "row' + response.productPurchase[i].productId + '">' +
                                                                                                    '<td>' + response.productPurchase[i].productName + '</td>' +
                                                                                                    '<td><input style="width:75px;" id = "quantity' + response.productPurchase[i].productId + '" onchange = "editDataRow(\'row' + response.productPurchase[i].productId + '\',\'' + response.productPurchase[i].costPrice + '\',\'' + response.productPurchase[i].gst + '\')" type="number" value="' + response.productPurchase[i].quantity + '"/></td>' +
                                                                                                    '<td><input style="width:75px;" id = "costPrice' + response.productPurchase[i].productId + '" onchange = "editDataRow(\'row' + response.productPurchase[i].productId + '\',\'' + response.productPurchase[i].costPrice + '\',\'' + response.productPurchase[i].gst + '\')" type="number" value="' + response.productPurchase[i].costPrice + '"/></td>' +
                                                                                                    '<td><input id = "discount' + response.productPurchase[i].productId + '" onchange = "editDataRow(\'row' + response.productPurchase[i].productId + '\',\'' + response.productPurchase[i].costPrice + '\',\'' + response.productPurchase[i].gst + '\')" style="width:75px;" type="number" value="' + 0 + '"/></td>' +
                                                                                                    '<td><label>' + response.productPurchase[i].gst + '</label></td>' +
                                                                                                    '<td>' + parseFloat(response.productPurchase[i].quantity * (response.productPurchase[i].costPrice - 0) * response.productPurchase[i].gst / 100) + '</td>' +
                                                                                                    '<td>' + parseFloat((response.productPurchase[i].costPrice - 0) * response.productPurchase[i].quantity + (response.productPurchase[i].quantity * (response.productPurchase[i].costPrice - 0) * response.productPurchase[i].gst / 100)) + '</td>' +
                                                                                                    '<td class="action-table-data"><div class="edit-delete-action"><a class="confirm-text p-2" onclick = "deleteData(\'row' + response.productPurchase[i].productId + '\',\'' + response.productPurchase[i].costPrice + '\',\'' + response.productPurchase[i].gst + '\')"><i data-feather="trash-2" class="feather-trash-2"></i></a></div></td>' +
                                                                                                    '</tr>';
                                                                                        }
                                                                                        $('.datanew tbody').append(newRow);
                                                                                        var subTotal = parseFloat(response.productPurchase[i].quantity * (response.productPurchase[i].costPrice - 0));
                                                                                        var purchaseTax = parseFloat(response.productPurchase[i].quantity * (response.productPurchase[i].costPrice - 0) * response.productPurchase[i].gst / 100);
                                                                                        var totalAmt = parseFloat(subTotal + purchaseTax);
                                                                                        purchaseDataArray.push(response.productPurchase[i].productId + "|" + response.productPurchase[i].costPrice + "|" + response.productPurchase[i].quantity + "|0|" + subTotal + "|" + purchaseTax + "|0|" + totalAmt + "|" + response.productPurchase[i].trackingSerialNo);
                                                                                        document.getElementById("purchaseData").value = purchaseDataArray;
                                                                                    }
                                                                                    let subAmt = 0;
                                                                                    let taxAmt = 0;
                                                                                    let disAmt = 0;
                                                                                    let totAmt = 0;
                                                                                    for (var i = 0; i < purchaseDataArray.length; i++) {
                                                                                        subAmt = subAmt + parseFloat(purchaseDataArray[i].split("|")[4]);
                                                                                        taxAmt = taxAmt + parseFloat(purchaseDataArray[i].split("|")[5]);
                                                                                        disAmt = disAmt + parseFloat(purchaseDataArray[i].split("|")[6]);
                                                                                        totAmt = totAmt + parseFloat(purchaseDataArray[i].split("|")[7]);
                                                                                    }
                                                                                    document.getElementById("purchaseSubtotal").innerHTML = subAmt;
                                                                                    document.getElementById("purchaseTax").innerHTML = taxAmt;
                                                                                    document.getElementById("purchaseDiscount").innerHTML = disAmt;
                                                                                    document.getElementById("purchaseTotal").innerHTML = totAmt;
                                                                                    purchaseDataSerialArray = response.serialPurchase;
                                                                                },
                                                                                error: function (err) {
                                                                                    alert("Error uploading file: " + err.responseText);
                                                                                }
                                                                            });
                                                                        }
            </script>

            <script>
                function formatDate(date) {
                    var day = ("0" + date.getDate()).slice(-2);
                    var month = ("0" + (date.getMonth() + 1)).slice(-2);
                    var year = date.getFullYear();
                    return year + "-" + month + "-" + day;
                }

                function displayFormattedDate() {
                    var inputDate = document.getElementById("dateInput").value;
                    document.getElementById("formattedDateOutput").value = inputDate;
                }

                document.addEventListener("DOMContentLoaded", function () {
                    var currentDate = formatDate(new Date());
                    document.getElementById("dateInput").value = currentDate;
                    document.getElementById("formattedDateOutput").value = currentDate;
                });

                document.getElementById("dateInput").addEventListener("change", displayFormattedDate);
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
                    var input = document.getElementById('customerSearch');
                    if (input.value.trim().length > 0) {
                        div.style.display = 'block';
                    }
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
                let currentCustomerFocus = -1;

                function filterCustomers() {
                    let input, filter, div, customerItems, txtValue;
                    input = document.getElementById('customerSearch');
                    filter = input.value.toUpperCase();
                    div = document.getElementById('customerList');
                    customerItems = div.getElementsByClassName('customer-item');

                    // Display the customer list
                    div.style.display = 'block';

                    // Loop through all customer items, and hide those who don't match the search query
                    for (let i = 0; i < customerItems.length; i++) {
                        txtValue = customerItems[i].textContent || customerItems[i].innerText;
                        if (txtValue.toUpperCase().indexOf(filter) > -1) {
                            customerItems[i].style.display = "";
                        } else {
                            customerItems[i].style.display = "none";
                        }
                    }

                    // Reset customer focus after filtering
                    currentCustomerFocus = -1;
                }

                function showCustomerList() {
                    let div = document.getElementById('customerList');
                    div.style.display = 'block';
                }

                function hideCustomerList() {
                    let div = document.getElementById('customerList');
                    div.style.display = 'none';
                }

                function selectCustomer(name, id) {
                    document.getElementById('customerSearch').value = name;
                    document.getElementById('hiddenUserId').value = id;
                    hideCustomerList();
                }

                function handleCustomerKeyEvents(event) {
                    const customerList = document.getElementById('customerList');
                    const items = Array.from(customerList.getElementsByClassName('customer-item')).filter(item => item.style.display !== 'none');

                    if (event.keyCode == 40) {
                        // Down arrow key
                        currentCustomerFocus++;
                        if (currentCustomerFocus >= items.length)
                            currentCustomerFocus = 0;
                        addActiveCustomer(items);
                    } else if (event.keyCode == 38) {
                        // Up arrow key
                        currentCustomerFocus--;
                        if (currentCustomerFocus < 0)
                            currentCustomerFocus = items.length - 1;
                        addActiveCustomer(items);
                    } else if (event.keyCode == 13) {
                        // Enter key
                        event.preventDefault(); // Prevent the default form submission behavior
                        if (currentCustomerFocus > -1 && items.length) {
                            items[currentCustomerFocus].click();
                        }
                    } else {
                        filterCustomers();
                    }
                }

                function addActiveCustomer(items) {
                    if (!items || items.length === 0)
                        return false;
                    removeActiveCustomer(items);
                    if (currentCustomerFocus >= items.length)
                        currentCustomerFocus = 0;
                    if (currentCustomerFocus < 0)
                        currentCustomerFocus = items.length - 1;
                    items[currentCustomerFocus].classList.add('active');
                    scrollIntoViewIfNeeded(items[currentCustomerFocus]);
                }

                function removeActiveCustomer(items) {
                    items.forEach(item => item.classList.remove('active'));
                }

                function scrollIntoViewIfNeeded(element) {
                    const rect = element.getBoundingClientRect();
                    const parentRect = element.parentNode.getBoundingClientRect();

                    if (rect.top < parentRect.top) {
                        element.scrollIntoView({behavior: 'smooth', block: 'start'});
                    } else if (rect.bottom > parentRect.bottom) {
                        element.scrollIntoView({behavior: 'smooth', block: 'end'});
                    }
                }

                document.addEventListener('click', function (event) {
                    const customerList = document.getElementById('customerList');
                    if (!customerList.contains(event.target) && event.target !== document.getElementById('customerSearch')) {
                        hideCustomerList();
                    }
                });

            </script>


            <!--If we search sku then we will get product suggestions, this is the javascript code for that-->
            <script>
                var typingTimer; // Timer identifier
                var doneTypingInterval = 300; // Delay in milliseconds (adjust as needed)
                var minInputLength = 1; // Minimum input length to trigger Ajax call
                var productFocus = -1; // Index for the currently focused product in the suggestion list

                document.getElementById('productSerialNumber').addEventListener('keyup', function (event) {
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

                                suggestionsHtml += '<div class="suggestion-item product-item" onclick="selectProduct(\'' + product.productSku + '\')">' +
                                        product.productSku + '</div>';


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
                    console.log('select-product-name' + productName);
                    document.getElementById("productSerialNumber").value = productName;
                    document.getElementById("productList").style.display = "none";
                    productFocus = -1;
                }


                function handleProductKeyEvents(event) {
                    let x = document.getElementById("productList");
                    if (!x)
                        return false;

                    let items = Array.from(x.getElementsByClassName("product-item")).filter(item => item.style.display !== 'none');
                    if (event.keyCode === 40) { // Down key
                        productFocus++;
                        addProductActive(items);
                        scrollProductIntoView(items);
                    } else if (event.keyCode === 38) { // Up key
                        productFocus--;
                        addProductActive(items);
                        scrollProductIntoView(items);
                    } else if (event.keyCode === 13) { // Enter key
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
                function deleteRowData(rowId, gst, deleteProductId) {
                    const row = document.getElementById(rowId);
                    if (row) {
                        row.parentNode.removeChild(row);
                    }
                    for (let i = 0; i < purchaseDataSerialArray.length; i++) {
                        const productId = purchaseDataSerialArray[i].split("|")[0];
                        if (productId === deleteProductId) {
                            purchaseDataSerialArray.splice(i, 1);
                            document.getElementById("purchaseSerialData").value = purchaseDataSerialArray.join(",");
                            i--;
                        }
                    }
                    for (let i = 0; i < purchaseDataArray.length; i++) {
                        const productId = purchaseDataArray[i].split("|")[0];
                        if (productId === deleteProductId) {
                            purchaseDataArray.splice(i, 1);
                            document.getElementById("purchaseData").value = purchaseDataArray.join(",");
                            break;
                        }
                    }
                }
            </script>













    </body>
</html>
