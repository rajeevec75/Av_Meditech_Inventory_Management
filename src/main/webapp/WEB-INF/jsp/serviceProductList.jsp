<%-- 
    Document   : serviceProductList
    Created on : Jul 30, 2024, 12:36:15 PM
    Author     : Rajeev kumar(QMM Technologies Private Limited)
--%>

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

            /* Custom CSS to make the modal full width */
            .modal-lg {
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

            <!-- Header and Sidebar -->
            <jsp:include page="header.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>

                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header">
                            <div class="add-item d-flex">
                                <div class="page-title mob-view">
                                    <h4>Service Product List</h4>
                                    <h6>Manage Your Service Product</h6>

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
                                    <a href="${pageContext.request.contextPath}/exportProductServiceToExcel?startDate=${URLEncoder.encode(startDate, 'UTF-8')}&endDate=${URLEncoder.encode(endDate, 'UTF-8')}&customerName=${URLEncoder.encode(customerName, 'UTF-8')}&productName=${URLEncoder.encode(productName, 'UTF-8')}&serialNumber=${URLEncoder.encode(serialNumber, 'UTF-8')}"
                                       data-bs-toggle="tooltip" data-bs-placement="top" title="Export to Excel">
                                        <img src="${pageContext.request.contextPath}/image/icons/excel.svg" alt="Export to Excel">
                                    </a>
                                </li>



                                <li>
                                    <a data-bs-toggle="tooltip" data-bs-placement="top" title="Collapse" id="collapse-header"><i data-feather="chevron-up" class="feather-chevron-up"></i></a>
                                </li>
                            </ul>

                        </c:if>
                    </div>


                    <!-- /product list -->
                    <div class="card table-list-card">
                        <div class="card-body">
                            <div class="table-top">
                                <div class="row">
                                    <form name="serviceProductForm" action="" method="get" class="row" id="serviceProductForm">
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
                                        <div class="col-lg-3 col-md-6 col-sm-12">
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

                                        <div class="col-lg-3 col-md-6 col-sm-12">
                                            <div class="mb-3 add-product">
                                                <label for="serialNumberInput" class="form-label">Serial Number</label>
                                                <input type="text" class="form-control" id="serialNumberInput" name="serialNumber" 
                                                       autocomplete="off" placeholder="Enter Serial Number" value="${serialNumber}">

                                            </div>
                                        </div>        





                                        <div class="col-12">
                                            <div class="mb-3 add-product">
                                                <button type="button" onclick="getserviceProductFormData()" class="btn btn-submit sub-btn">Submit</button>
                                            </div>
                                        </div>

                                    </form>
                                </div>
                            </div>
                            <div class="table-responsive table-container d-none d-md-block">

                                <table class="table datanew1">
                                    <thead>
                                        <tr>
                                            <th>Challan Number</th>
                                            <th>Date</th>
                                            <th>Party Name</th>
                                            <th>Item Description</th>
                                            <th>Serial Number</th>
                                            <th>Expiry Date</th>
                                            <th>Warranty Valid Date</th>
                                            <th>AMC Valid Date</th>

                                            <th class="text-center">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="sale" items="${serviceProductList}">
                                            <tr>
                                                <td>${sale.purchaseItemSerialMasterId}</td>
                                                <td>${sale.saleDateStr}</td>
                                                <td>${sale.companyName}</td>
                                                <td>${sale.productName}</td>
                                                <td>${sale.serialNumber}</td>
                                                <td>${sale.itemSerialExpiry}</td>
                                                <td>
                                                    <!-- Warranty Valid Date Modal Trigger -->
                                                    <div class="page-btn import">
                                                        <a class="btn btn-added color" data-bs-toggle="modal" data-bs-target="#view-warranty" 
                                                           data-serial-number="${sale.purchaseItemSerialMasterId}" 
                                                           data-warranty-date="${sale.warrantyValidDateStr}">
                                                            ${sale.warrantyValidDateStr}
                                                        </a>
                                                    </div>
                                                </td>
                                                <td>
                                                    <!-- AMC Valid Date Modal Trigger -->
                                                    <div class="page-btn import">
                                                        <a class="btn btn-added color" data-bs-toggle="modal" data-bs-target="#view-amc" 
                                                           data-serial-number="${sale.purchaseItemSerialMasterId}" 
                                                           data-amc-date="${sale.amcValidDateStr}">
                                                            ${sale.amcValidDateStr}
                                                        </a>
                                                    </div>

                                                </td>

                                                <td class="action-table-data">
                                                    <div class="edit-delete-action">
                                                        <!-- Button to perform service -->
                                                        <a class="me-2 p-2 btn btn-primary" data-bs-toggle="modal" 
                                                           data-bs-target="#performServiceModal" data-sale-id="${sale.purchaseItemSerialMasterId}">
                                                            Perform Service
                                                        </a>

                                                        <!-- Button to view performed service -->
                                                        <a class="p-2 btn btn-secondary openModalButton" 
                                                           data-sale-id="${sale.purchaseItemSerialMasterId}" 
                                                           data-serial-number="${sale.serialNumber}">
                                                            Service History
                                                        </a>



                                                    </div>
                                                </td>


                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>


                                <!-- Import Warranty Valid Date -->
                                <!-- Modal for Warranty Valid Date -->
                                <div class="modal fade" id="view-warranty" tabindex="-1" aria-labelledby="view-warrantyLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered">
                                        <div class="modal-content">
                                            <div class="page-wrapper-new p-0">
                                                <div class="content">
                                                    <div class="modal-header border-0 custom-modal-header">
                                                        <div class="page-title">
                                                            <h4>Add Warranty Valid Date</h4>
                                                        </div>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body custom-modal-body">
                                                        <form action="updateWarrantyValidDate?startDate=${startDate}&endDate=${endDate}&customerName=${customerName}&productName=${productName}&serialNumber=${serialNumber}" method="post">
                                                            <div class="row">
                                                                <div class="col-lg-12">
                                                                    <div class="input-blocks image-upload-down">
                                                                        <label for="newWarrantyValidDate">Warranty Valid Date</label>
                                                                        <input type="date" id="newWarrantyValidDate" name="newWarrantyValidDate" class="form-control">
                                                                        <input type="hidden" id="itemSerialNumber" name="itemSerialNumber">
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
                                <!-- /Import Warranty Valid Date -->

                                <!-- Import AMC Valid Date -->
                                <!-- Modal for AMC Valid Date -->
                                <div class="modal fade" id="view-amc" tabindex="-1" aria-labelledby="view-amcLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered">
                                        <div class="modal-content">
                                            <div class="page-wrapper-new p-0">
                                                <div class="content">
                                                    <div class="modal-header border-0 custom-modal-header">
                                                        <div class="page-title">
                                                            <h4>Add AMC Valid Date</h4>
                                                        </div>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body custom-modal-body">
                                                        <form action="updateAmcValidDate?startDate=${startDate}&endDate=${endDate}&customerName=${customerName}&productName=${productName}&serialNumber=${serialNumber}" method="post">
                                                            <div class="row">
                                                                <div class="col-lg-12">
                                                                    <div class="input-blocks image-upload-down">
                                                                        <label for="newAmcValidDate">AMC Valid Date</label>
                                                                        <input type="date" id="newAmcValidDate" name="newAmcValidDate" class="form-control">
                                                                        <input type="hidden" id="itemSerialNumberAmc" name="itemSerialNumberAmc">
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

                                <!-- /Import AMC Valid Date -->

                                <!-- Modal for Performing Service -->
                                <div class="modal fade" id="performServiceModal" tabindex="-1" aria-labelledby="performServiceModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="performServiceModalLabel">Perform Service</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="addPerformService?startDate=${startDate}&endDate=${endDate}&customerName=${customerName}&productName=${productName}&serialNumber=${serialNumber}" method="post" enctype="multipart/form-data">
                                                    <div class="mb-3">
                                                        <label for="serviceDate" class="form-label">Service Date</label>
                                                        <input type="date" class="form-control" id="serviceDate" name="serviceDate" required>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="problemRemarks" class="form-label">Problem Remarks</label>
                                                        <textarea class="form-control" id="problemRemarks" name="remarks" rows="3" required></textarea>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="solution" class="form-label">Solution</label>
                                                        <textarea class="form-control" id="solution" name="solution" rows="3" required></textarea>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="attachment" class="form-label">Attachment</label>
                                                        <input type="file" class="form-control" id="attachment" name="attachment">
                                                    </div>
                                                    <input type="hidden" id="saleId" name="saleId">
                                                    <button type="submit" class="btn btn-primary">Submit</button>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- /Modal for Performing Service -->

                                <!-- Performed Service Details Modal -->
                                <div class="modal fade" id="serviceModal" tabindex="-1" role="dialog" aria-labelledby="serviceModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-lg" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="serviceModalLabel">Service History</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <table class="table">
                                                    <thead>
                                                        <tr>


                                                            <th>Service Date</th>
                                                            <th>Remarks</th>
                                                            <th>Solution</th>
                                                            <th>Attachment</th>

                                                        </tr>
                                                    </thead>
                                                    <tbody id="serviceDetailsTableBody">
                                                        <!-- Rows will be dynamically inserted here -->
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- /Performed Service Details Modal -->




                            </div>
                            <!-- /Main Wrapper -->




                            <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
                            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
                            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

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

                                function getserviceProductFormData() {
                                    document.serviceProductForm.action = "serviceProductList";
                                    document.serviceProductForm.submit();
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

                            <!--                            <script>
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
                                        url: "${pageContext.request.contextPath}/products/isProductServiceTrue",
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
                                document.addEventListener('DOMContentLoaded', function () {
                                    const performServiceModal = document.getElementById('performServiceModal');
                                    performServiceModal.addEventListener('show.bs.modal', function (event) {
                                        const button = event.relatedTarget; // Button that triggered the modal
                                        const saleId = button.getAttribute('data-sale-id'); // Extract saleId from data-* attribute

                                        // Update the modal's input field
                                        const saleIdInput = performServiceModal.querySelector('#saleId');
                                        saleIdInput.value = saleId;
                                    });
                                });
                            </script>

                            <script>
                                $(document).ready(function () {
                                    $('#openModalButton').on('click', function (e) {
                                        e.preventDefault();
                                        var url = $(this).data('url');
                                        $.ajax({
                                            url: url,
                                            type: 'GET',
                                            success: function (data) {
                                                $('#serviceModal .modal-content').html(data);
                                                $('#serviceModal').modal('show');
                                            },
                                            error: function (xhr, status, error) {
                                                alert('An error occurred: ' + xhr.responseText);
                                            }
                                        });
                                    });
                                });
                            </script>

                            <script>
                                $(document).ready(function () {
                                    // Function to open and populate the product view modal
                                    function openProductViewModal() {
                                        // Get dynamic values from button attributes
//                                        var serialNumber = $(this).data('serial-number');
                                        var saleId = $(this).data('sale-id');

                                        $.ajax({
                                            url: `${pageContext.request.contextPath}/productServiceDetails`,
                                            type: 'GET',
                                            data: {
                                                saleId: saleId
                                            },
                                            success: function (response) {
                                                console.log("Response received:", response); // Log the entire response object for debugging

                                                if (response && Array.isArray(response) && response.length > 0) {
                                                    populateModal(response); // Populate modal with response data
                                                    // Open the modal
                                                    var myModal = new bootstrap.Modal(document.getElementById('serviceModal'));
                                                    myModal.show();
                                                } else {
                                                    alert('No sale item details found.');
                                                }
                                            },
                                            error: function (jqXHR, textStatus, errorThrown) {
                                                console.error('Error fetching sale item details:', errorThrown);
                                                alert('An error occurred while retrieving sale item details.');
                                            }
                                        });
                                    }

                                    // Attach click event to buttons with class 'openModalButton'
                                    $(document).on('click', '.openModalButton', openProductViewModal);

                                    // Function to populate modal with data
                                    function populateModal(data) {
                                        var tableBody = $('#serviceDetailsTableBody');
                                        tableBody.empty(); // Clear existing rows

                                        data.forEach(function (item) {

                                            const row = document.createElement('tr');

                                            // Log the value of attachment to the console
                                            console.log('Attachment value:', item.attachment);

                                            // Check if attachment is not null and not empty
                                            let attachmentCellContent = ''; // Default to '-'

                                            if (item.attachment !== null && item.attachment !== '') {
                                                attachmentCellContent =
                                                        '<a target="_blank" href="' + '${pageContext.request.contextPath}' + '/retrieveImage?saleServiceId=' + item.saleServiceId + '">' +
                                                        'View' +
                                                        '</a>';
                                            }

                                            // Directly assign the content to attachmentCell (no <td> tags)
                                            const attachmentCell = attachmentCellContent;




                                            // Log the attachmentCell for each row
                                            console.log('Attachment cell HTML:', attachmentCell);


                                            row.innerHTML =
                                                    '<td>' + item.serviceDate + '</td>' +
                                                    '<td style="text-wrap:wrap">' + item.remarks + '</td>' +
                                                    '<td style="text-wrap:wrap">' + item.solution + '</td>' +
                                                    '<td>' + attachmentCell + '</td>';

                                            tableBody.append(row);
                                        });
                                    }
                                });
                            </script>


                            <script>
                                document.addEventListener('DOMContentLoaded', function () {
                                    feather.replace();

                                    const modalElements = document.querySelectorAll('.modal');
                                    modalElements.forEach(modal => {
                                        modal.addEventListener('show.bs.modal', function (event) {
                                            const button = event.relatedTarget;
                                            const serialNumber = button.getAttribute('data-serial-number');
                                            let warrantyDate = button.getAttribute('data-warranty-date');
                                            console.log('warrantyDate======' + warrantyDate);

                                            const form = modal.querySelector('form');

                                            form.querySelector('input[name="itemSerialNumber"]').value = serialNumber;

                                            // Check if warrantyDate is "No Date" or null/undefined and set today's date in that case
                                            if (!warrantyDate || warrantyDate === "No Date") {
                                                const today = new Date().toISOString().split('T')[0];
                                                console.log('today=' + today);
                                                warrantyDate = today;
                                            }

                                            // Set the warranty date to the input field
                                            form.querySelector('input[name="newWarrantyValidDate"]').value = warrantyDate;
                                        });
                                    });
                                });
                            </script>



                            <script>
                                document.addEventListener('DOMContentLoaded', function () {
                                    const amcModalElements = document.querySelectorAll('.modal');
                                    amcModalElements.forEach(modal => {
                                        modal.addEventListener('show.bs.modal', function (event) {
                                            const button = event.relatedTarget;
                                            const serialNumber = button.getAttribute('data-serial-number');
                                            console.log('serialNumber=====' + serialNumber);
                                            let amcDate = button.getAttribute('data-amc-date');

                                            const form = modal.querySelector('form');

                                            form.querySelector('input[name="itemSerialNumberAmc"]').value = serialNumber;

                                            // Set today's date if no AMC date or "No Date" is provided
                                            const today = new Date().toISOString().split('T')[0];
                                            if (!amcDate || amcDate === "No Date") {
                                                amcDate = today;
                                            }

                                            // Set the AMC date in the input field
                                            form.querySelector('input[name="newAmcValidDate"]').value = amcDate;
                                        });
                                    });
                                });
                            </script>

                            <script>
                                document.addEventListener('DOMContentLoaded', function () {
                                    var today = new Date().toISOString().split('T')[0];
                                    document.getElementById('serviceDate').value = today;
                                });
                            </script>


                            </body>
                            </html>
