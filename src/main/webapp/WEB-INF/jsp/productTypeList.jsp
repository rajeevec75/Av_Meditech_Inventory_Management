
<%@page import="com.AvMeditechInventory.entities.ProductType"%>
<%-- 
    Document   : productTypeList
    Created on : Apr 30, 2024, 6:20:03 PM
    Author     : Rajeev kumar
--%>


<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            /* Default to desktop view */
            .table-responsive {
                display: block;
            }

            .card-container {
                display: none;
            }

            /* Mobile view */
            @media (max-width: 768px) {
                .table-responsive {
                    display: none;
                }

                .card-container {
                    display: block;
                }

                .card-container .card-section {
                    background: white;
                    border: 1px solid #ccc;
                    padding: 15px;
                    margin-bottom: 10px;
                }

                .card-container .card-section h2 {
                    font-size: 18px;
                    margin-bottom: 10px;
                }
            }
            @media (max-width: 768px) {
                .card-container {
                    display: flex;
                    flex-wrap: wrap;
                    justify-content: space-around;
                    background-color: white;
                    padding: 10px;
                }
                .card{
                    border: none!important;
                }
                .card-container .card-section {
                    background: white;
                    border: 1px solid #007bff; /* Use your logo's blue color or grey */

                    padding: 15px;
                    margin-bottom: 10px;
                }
                .card-section {
                    background-color: white;
                    border: 1px solid #007bff; /* Use your logo's blue color or grey */
                    border-radius: 5px;
                    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                    width: 100%;
                    margin: 10px 0;
                    padding: 15px;
                    box-sizing: border-box;
                    position: relative;
                }

                .card-section h2 {
                    font-size: 18px;
                }

                .card-section p {
                    font-size: 17px;
                    margin: 5px 0;
                }

                .action-buttons {
                    display: flex;
                    justify-content: center;
                    margin-top: 10px;
                    position: absolute;
                    top: -5px;
                    right: 0;
                }

                .edit-btn, .delete-btn {
                    text-decoration: none;
                    color: white;
                    padding: 10px;
                    border-radius: 3px;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    width: 40px;
                    height: 40px;
                }

                .edit-btn {
                    background-color: #fff;
                    color: blue;
                    border: 1px solid #b991f5;
                    border-radius: 8px;
                }

                .delete-btn {
                    background-color: #fff;
                    color: red;
                    border: 1px solid #b991f5;
                    border-radius: 8px;
                }

                .edit-btn i, .delete-btn i {
                    font-size: 18px;
                }

                .action-buttons a {
                    margin: 0 5px;
                }
            }
            /* Default to desktop view */
            .table-responsive {
                display: block;
            }

            .card-container {
                display: none;
            }

            /* Mobile view */
            @media (max-width: 768px) {
                .table-responsive {
                    display: none;
                }

                .card-container {
                    display: block;
                }
                .card-view-section{
                    display: none;
                }
            }


        </style>
    </head>
    <body>

        <div id="global-loader">
            <div class="whirly-loader"></div>
        </div>

        <!-- Main Wrapper -->
        <div class="main-wrapper">

            <!-- Header and Sidebar -->
            <jsp:include page="header.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>

                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header ">
                            <div class="add-item d-flex">
                                <div class="page-title mob-view">
                                    <h2>Brand</h2>
                                    <h6>Manage your Brand</h6>
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
                        <ul class="table-top-head mob-view">


                            <li>
                                <a data-bs-toggle="tooltip" data-bs-placement="top" title="Collapse" id="collapse-header"><i data-feather="chevron-up" class="feather-chevron-up"></i></a>
                            </li>
                        </ul>
                        <div class="page-btn">
                            <a href="#" class="btn btn-added" data-bs-toggle="modal" data-bs-target="#add-category"><i data-feather="plus-circle" class="me-2"></i>Add New Brand</a>
                        </div>
                    </div>

                    <!-- Product type list -->
                    <div class="card table-list-card">
                        <div class="card-body">
                            <!-- Filter and search section omitted for brevity -->

                            <div class="table-responsive product-list">
                                <table class="table datanew1">
                                    <thead>
                                        <tr>
                                            <th>Brand Name</th>
                                            <th class="no-sort">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <!-- Display product types -->
                                        <% List<ProductType> productTypeList = (List<ProductType>) request.getAttribute("productTypeList");
                                            if (productTypeList != null && !productTypeList.isEmpty()) {
                                                for (ProductType productType : productTypeList) {
                                        %>
                                        <tr>
                                            <td><%= productType.getProductTypeName()%></td>
                                            <td class="action-table-data">
                                                <div class="edit-delete-action">
                                                    <!-- Edit product type button with data attribute -->
                                                    <a class="me-2 p-2 edit-product-type-btn" href="" data-bs-toggle="modal" data-bs-target="#edit-product-type" data-product-type-name="<%= productType.getProductTypeName()%>" data-product-type-id="<%= productType.getProductTypeId()%>">
                                                        <i data-feather="edit" class="feather-edit"></i>
                                                    </a>

                                                    <!-- Delete product type button link -->
                                                    <a class="confirm-text p-2" href="${pageContext.request.contextPath}/productTypeDelete?productTypeId=<%= productType.getProductTypeId()%>">
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

                            <!-- Add Previous and Next buttons here -->
                            <div class="container mt-2 mb-4">
                                <div class="d-flex justify-content-end">
                                    <c:if test="${hasPreviousPage}">
                                        <div class="page-btn">
                                            <a href="${pageContext.request.contextPath}/productTypeList?after=${firstCursor}&isAsc=prev" class="btn btn-secondary">Previous</a>
                                        </div>&nbsp;&nbsp;
                                    </c:if>
                                    <c:if test="${hasNextPage}">
                                        <div class="page-btn">
                                            <a href="${pageContext.request.contextPath}/productTypeList?after=${lastCursor}" class="btn btn-secondary">Next</a>
                                        </div>
                                    </c:if>
                                </div>
                            </div>

                            <!--add new section product type mobile screen-->
                            <div class="card-container mobile-view">
                                <% if (productTypeList != null && !productTypeList.isEmpty()) {
                                        for (ProductType productType : productTypeList) {%>
                                <div class="card-section">
                                    <h2>Product Type: <%= productType.getProductTypeName()%></h2>
                                    <div class="action-buttons">
                                        <a href="" class="edit-btn edit-product-type-btn" data-bs-toggle="modal" data-bs-target="#edit-product-type" data-product-type-name="<%= productType.getProductTypeName()%>" data-product-type-id="<%= productType.getProductTypeId()%>">
                                            <i class="fa fa-edit"></i>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/productTypeDelete?productTypeId=<%= productType.getProductTypeId()%>" class="delete-btn">
                                            <i class="fa fa-trash"></i>
                                        </a>
                                    </div>
                                </div>
                                <%    }
                                    }%>
                            </div>


                        </div>
                    </div>
                    <!-- /Product type list -->

                </div>
            </div>
        </div>
        <!-- /Main Wrapper -->

        <!-- Add Product Type -->
        <div class="modal fade" id="add-category">
            <div class="modal-dialog modal-dialog-centered custom-modal-two">
                <div class="modal-content">
                    <div class="page-wrapper-new p-0">
                        <div class="content">
                            <div class="modal-header border-0 custom-modal-header">
                                <div class="page-title">
                                    <h4>Create Brand</h4>
                                </div>
                                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body custom-modal-body">
                                <form action="processProductTypeCreate" method="post">
                                    <div class="mb-3">
                                        <label class="form-label">Brand</label>
                                        <input type="text" class="form-control" name="productTypeName" required>
                                    </div>


                                    <div class="modal-footer-btn">
                                        <button type="button" class="btn btn-cancel me-2" data-bs-dismiss="modal">Cancel</button>
                                        <button type="submit" class="btn btn-submit">Submit</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /Add Product Type -->


        <!-- Edit Product Type Modal -->
        <div class="modal fade" id="edit-product-type">
            <div class="modal-dialog modal-dialog-centered custom-modal-two">
                <div class="modal-content">
                    <div class="page-wrapper-new p-0">
                        <div class="content">
                            <div class="modal-header border-0 custom-modal-header">
                                <div class="page-title">
                                    <h4>Edit Brand</h4>
                                </div>
                                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body custom-modal-body">
                                <form action="processProductTypeUpdate" method="post">
                                    <div class="mb-3">
                                        <label class="form-label">Brand</label>
                                        <input type="hidden" id="edit-product-type-id" name="productTypeId">
                                        <input type="text" class="form-control" id="edit-product-type-name" name="productTypeName" required>
                                    </div>
                                    <div class="modal-footer-btn">
                                        <button type="button" class="btn btn-cancel me-2" data-bs-dismiss="modal">Cancel</button>
                                        <button type="submit" class="btn btn-submit">Submit</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /Edit Product Type Modal -->

        <!-- jQuery and other script imports omitted for brevity -->
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
            // Wait for the document to be fully loaded
            document.addEventListener("DOMContentLoaded", function () {
                // Find all edit product type buttons
                let editProductTypeButtons = document.querySelectorAll(".edit-product-type-btn");

                // Loop through each button
                editProductTypeButtons.forEach(function (button) {
                    // Add click event listener to each button
                    button.addEventListener("click", function () {
                        // Get the product type name and ID from data attributes
                        let productTypeName = button.getAttribute("data-product-type-name");
                        let productTypeId = button.getAttribute("data-product-type-id");

                        // Print productTypeName and productTypeId in the console
                        console.log("Product Type Name:", productTypeName);
                        console.log("Product Type ID:", productTypeId);

                        // Set the product type name and ID in the input fields of the edit modal
                        document.getElementById("edit-product-type-name").value = productTypeName;
                        document.getElementById("edit-product-type-id").value = productTypeId;
                    });
                });
            });
        </script>

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
                    "paging": false, // Disable pagination
                    "searching": false, // Enable searching (default is true)
                    "ordering": true, // Enable ordering (default is true)
                    "info": false, // Hide information display
                    "responsive": true  // Enable responsive mode
                            // Additional options and configurations can be added here
                });
            });
        </script>












    </body>
</html>









