<%@page import="com.AvMeditechInventory.dtos.CustomerAndSupplierDto"%>
<%@page import="java.util.List"%>
<%@page import="com.AvMeditechInventory.entities.AccountUser"%>
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

        <!-- Summernote CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/summernote/summernote-bs4.min.css">

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


            </div>
            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="add-item d-flex">
                            <div class="page-title mob-view">
                                <h4>Customer List</h4>
                                <h6>Manage your customers</h6>
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
                                <a href="${pageContext.request.contextPath}/customerListExportToExcel?mobileNo=${URLEncoder.encode(mobileNo, 'UTF-8')}&userCode=${URLEncoder.encode(userCode, 'UTF-8')}&companyName=${URLEncoder.encode(companyName, 'UTF-8')}" 
                                   data-bs-toggle="tooltip" data-bs-placement="top" title="Export to Excel">
                                    <img src="${pageContext.request.contextPath}/image/icons/excel.svg" alt="Export to Excel">
                                </a>
                            </li>


                            <li>
                                <a data-bs-toggle="tooltip" data-bs-placement="top" title="Collapse" id="collapse-header"><i data-feather="chevron-up" class="feather-chevron-up"></i></a>
                            </li>
                        </ul>
                        <div class="page-btn mob-view">
                            <a href="${pageContext.request.contextPath}/addUser" class="btn btn-added"><i data-feather="plus-circle" class="me-2"></i>Add New Customer</a>
                        </div>
                    </c:if>

                </div>

                <!-- /product list -->
                <div class="card table-list-card">
                    <div class="card-body">
                        <div class="table-top">
                            <div class="row">
                                <form class="row" name="customerForm" onsubmit="return false;">
                                    <!-- Row 1 -->
                                    <div class="col-lg-4 col-md-4 col-sm-4">
                                        <div class="mb-3 add-product">
                                            <label for="mobileNo" class="form-label">Mobile Number</label>
                                            <input type="text" class="form-control" id="mobileNo" name="mobileNo" value="${mobileNo}">
                                        </div>
                                    </div>
                                    <div class="col-lg-4 col-md-4 col-sm-4">
                                        <div class="mb-3 add-product">
                                            <label for="userCode" class="form-label">User Code</label>
                                            <input type="text" class="form-control" id="userCode" name="userCode" value="${userCode}">
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
                                    <div class="col-12">
                                        <div class="mb-3 add-product">
                                            <button type="submit" onclick="getCustomerData()" class="btn btn-submit sub-btn">Submit</button>
                                        </div>
                                    </div>
                                </form>
                            </div>


                        </div>
                    </div>



                    <div class="table-responsive product-list">
                        <table class="table datanew1">
                            <thead>
                                <tr>
                                    <th>Company Name</th>
                                    <th>Mobile No</th>
                                    <th>User Code</th>
                                    <th class="no-sort">Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%-- Loop through each customer and display their details --%>
                                <%                                            List<CustomerAndSupplierDto> customerList1 = (List<CustomerAndSupplierDto>) request.getAttribute("customerList");

                                    if (customerList1 != null && !customerList1.isEmpty()) {
                                        int sequence = 1; // Initialize sequence number
                                        for (CustomerAndSupplierDto customerDto : customerList1) {
                                %>
                                <tr>

                                    <td><%= customerDto.getCompanyName()%></td>
                                    <td><%= customerDto.getMobileNumber()%></td>
                                    <td><%= customerDto.getUserCode()%></td>
                                    <td class="action-table-data">
                                        <div class="edit-delete-action">

                                            <a class="me-2 p-2" href="${pageContext.request.contextPath}/updateUser?customerId=<%=  customerDto.getId()%>">
                                                <i data-feather="edit" class="feather-edit"></i>
                                            </a>
                                            <a class="confirm-text p-2" href="${pageContext.request.contextPath}/deleteUser?customerId=<%=  customerDto.getId()%>">
                                                <i data-feather="trash-2" class="feather-trash-2"></i>
                                            </a>
                                        </div>	
                                    </td>
                                </tr>
                                <%          }
                                    }%>	
                            </tbody>
                        </table>

                    </div>

                </div>
            </div>
            <!-- /product list -->
            <div class="card-container">
                <% if (customerList1 != null) {
                        for (CustomerAndSupplierDto customerDto : customerList1) {%>
                <div class="card-section">
                    <h2><strong>Company Name: </strong><%= customerDto.getCompanyName()%></h2>
                    <p><strong>Mobile No: </strong> <%= customerDto.getMobileNumber()%></p>
                    <p><strong>User Code: </strong> <%= customerDto.getUserCode()%></p>
                    <!--                   <div class="action-buttons">
                                           <a href="${pageContext.request.contextPath}/updateUser?customerId=<%=  customerDto.getId()%>" class="edit-btn">
                                               <i class="fa fa-edit"></i>
                                           </a>
                                           <a href="${pageContext.request.contextPath}/deleteUser?customerId=<%=  customerDto.getId()%>&after=${currentCursor}" class="delete-btn">
                                               <i class="fa fa-trash"></i>
                                           </a>
                                       </div>-->
                </div>
                <%     }
                    }%>
                <!-----------------end section-------------------->
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

    <!-- Summernote JS -->
    <script src="${pageContext.request.contextPath}/plugins/summernote/summernote-bs4.min.js"></script>

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

    <!--<script src="assets/js/theme-settings.js"></script>-->

    <script>
                                                $(".confirm-text").on("click", function (event) {
                                                    event.preventDefault(); // Prevent the default action of the link
                                                    var deleteUrl = $(this).attr("href"); // Get the delete URL from the link's href attribute

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
                                                            window.location.href = deleteUrl;
                                                        }
                                                    });
                                                });
    </script>


    <!------------------>
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
        // Custom logic for the submit button
        function getCustomerData() {
            console.log("Submit button clicked. Form submitted.");
            document.customerForm.action = "customerList";
            document.customerForm.submit(); // Submit the form programmatically
        }

        // Prevent form submission on Enter key
        document.customerForm.addEventListener("keydown", function (event) {
            if (event.key === "Enter") {
                event.preventDefault(); // Prevent default behavior (reload)
                console.log("Enter key pressed. URL not reloaded.");
            }
        });
    </script>




</body>
</html>