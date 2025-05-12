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

            <!-- Header and Sidebar -->
            <jsp:include page="header.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>

                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header">
                            <div class="add-item d-flex">
                                <div class="page-title mob-view">
                                    <h4>Day Book With Items</h4>
                                    <h6>Manage Your Day Book With Items</h6>
                                </div>
                            </div>
                            <ul class="table-top-head">

                                <li>
                                    <a href="${pageContext.request.contextPath}/dayBookWithItemsExportToExcel?startDate=${URLEncoder.encode(startDate, 'UTF-8')}&endDate=${URLEncoder.encode(endDate, 'UTF-8')}" 
                                   data-bs-toggle="tooltip" 
                                   data-bs-placement="top" 
                                   title="Export to Excel">
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
                                    <form action="getDayBookWithItems" method="post" class="row">
                                        <div class="col-lg-6 col-sm-6 col-12">
                                            <div class="mb-3 add-product">
                                                <label class="form-label">Start Date</label>
                                                <input type="date" class="form-control" id="startDate" name="startDate" value="${startDate}">
                                            </div>
                                        </div>
                                        <div class="col-lg-6 col-sm-6 col-12">
                                            <div class="mb-3 add-product">
                                                <label class="form-label">End Date</label>
                                                <input type="date" class="form-control" id="endDate" name="endDate" value="${endDate}">
                                            </div>
                                        </div>



                                        <div class="col-12">
                                            <div class="mb-3 add-product">
                                                <button type="submit" class="btn btn-submit sub-btn">Submit</button>
                                            </div>
                                        </div>
                                    </form>

                                </div>




                            </div>



                            <div class="table-responsive table-resp">
                                <table class="table  datanew1">
                                    <thead>
                                        <tr>
                                            <th>Voucher Number</th>
                                            <th>Voucher Date</th>
                                            <th>Company Name</th>
                                            <th>Remarks</th>
                                            <th class="text-center">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="sale" items="${saleList}">
                                            <tr>

                                                <td>${sale.autoId}</td>
                                                <td>${sale.date}</td>
                                                <td>${sale.fullName}</td>
                                                <td>${sale.remarks}</td>
                                                <td class="action-table-data">
                                                    <div class="edit-delete-action">
                                                        <a href="javascript:void(0);" class="me-2 p-2" onclick="openSaleItemModal(${sale.id})">
                                                            <i data-feather="edit" class="feather-edit"></i>
                                                        </a>
                                                    </div>
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
                                <c:forEach var="sale" items="${saleList}">
                                    <div class="card mb-3" >
                                        <div class="card-body text-dark">
                                            <h5 class="card-title">Sale Id: ${sale.id}</h5>
                                            <p class="card-title">Customer Name: ${sale.fullName}</p>
                                            <p class="card-title">Reference: ${sale.referenceNumber}</p>
                                            <p class="card-title">Remarks: ${sale.remarks}</p>
                                            <p class="card-title">Date: ${sale.date}</p>
                                            <p class="card-title">Amount: ${sale.amount}</p>
                                            <p class="card-title">Shipping: ${sale.shipping}</p>
                                            <p class="card-title">GST: ${sale.gst}</p>
                                            <p class="card-title">Grand Total: ${sale.totalAmount}</p>
                                            <div class="action-table-data">
                                                <a href="javascript:void(0);" class="me-2 p-2 edit-mob"  onclick="openSaleItemModal(${sale.id})">
                                                    <i data-feather="edit" class="feather-edit"></i>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                                <c:if test="${empty saleList}">
                                    <div class="card mb-3">
                                        <div class="card-body">
                                            <p class="card-text text-center">No sales found</p>
                                        </div>
                                    </div>
                                </c:if>
                            </div>

                        </div>
                    </div>

                </div>
            </div>
            <!-- Modal -->
            <div class="modal fade" id="edit-units" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog custom-width">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Day Book With Items</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <!--<th>Voucher Number</th>-->
                                            <th>Voucher Date</th>
                                            <th>Item Description</th>
                                            <th>Brand</th>
                                            <th>Company Name</th>
                                            <th>Quantity</th> 


                                        </tr>
                                    </thead>
                                    <tbody>
                                        <!-- Data will be populated here -->
                                    </tbody>
                                </table>
                            </div>
                        </div>

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
            function openSaleItemModal(saleId) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/dayBookWithItem", // URL to your endpoint
                    type: "GET", // HTTP method
                    data: {
                        saleId: saleId // Assuming saleId is a variable holding the ID you want to send
                    },
                    success: function (response) {
                        console.log("Response received:", response); // Log the entire response object for debugging

                        if (response && response.length > 0) {
                            populateModal(response); // Assuming response is an array of sale items
                            // Open the modal
                            var myModal = new bootstrap.Modal(document.getElementById('edit-units'));
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


            function populateModal(saleItems) {
                console.log("saleItems:", saleItems); // Check the structure of saleItems in the console
                const tbody = document.querySelector('#edit-units tbody');
                tbody.innerHTML = ''; // Clear any existing rows

                saleItems.forEach(item => {
                    console.log("item:", item); // Log the entire item object for debugging
                    console.log("item.customerId:", item.saleId); // Log customerId to verify its value

                    const row = document.createElement('tr');
                    row.innerHTML = '<td>' + item.saleDate + '</td>' +
                            '<td>' + item.productName + '</td>' +
                            '<td>' + item.productTypeName + '</td>' +
                            '<td>' + item.channelName + '</td>' +
                            '<td>' + item.quantity + '</td>';

                    tbody.appendChild(row);
                });
            }
        </script>





    </body>
</html>