a<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.AvMeditechInventory.dtos.ProductDto"%>
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

            .autocomplete-list {
                position: absolute;
                background: #fff;
                max-height: 150px;
                overflow-y: auto;
                width: 100%;
                z-index: 1000;
            }
            .autocomplete-list div {
                padding: 8px;
                cursor: pointer;
            }
            .autocomplete-list div:hover, .autocomplete-list .selected {
                background: #f0f0f0;
            }

        </style>
    </head>
    <body onload="closePopup()">

        <div id="global-loader" >
            <div class="whirly-loader"> </div>
        </div>
        <!-- Main Wrapper -->
        <div class="main-wrapper">
            <!-- Header and Sidebar -->
            <jsp:include page="header.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>
            </div>
            <form id="productlistForm" method="get">
                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header mob-view">
                            <div class="add-item d-flex ">
                                <div class="page-title mob-view">
                                    <h4>Product List</h4>
                                    <h6>Manage your products</h6>
                                    <!-- Add this snippet in your JSP page where you want to display the error message -->
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
                                    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
                                    <a href="${pageContext.request.contextPath}/productListExportToExcel?brand=${selectedBrandId}&productType=${selectedProductTypeId}" 
                                       data-bs-toggle="tooltip" data-bs-placement="top" title="Export to Excel">
                                        <img src="${pageContext.request.contextPath}/image/icons/excel.svg" alt="Export to Excel">
                                    </a>

                                </li>


                                <li>
                                    <a data-bs-toggle="tooltip" data-bs-placement="top" title="Collapse" id="collapse-header"><i data-feather="chevron-up" class="feather-chevron-up"></i></a>
                                </li>
                            </ul>

                            <div class="page-btn">
                                <a href="${pageContext.request.contextPath}/productCreate" class="btn btn-added"><i data-feather="plus-circle" class="me-2"></i>Add New Product</a>
                            </div>	
                            <div class="page-btn import">
                                <a href="#" class="btn btn-added color" data-bs-toggle="modal" data-bs-target="#view-notes">
                                    <i data-feather="download" class="me-2"></i>Import Product
                                </a>
                            </div>
                            <div class="page-btn">
                                <button type="button" onclick="deleteSelectedProducts()" class="btn btn-success">Delete Product</button>
                            </div>&nbsp;&nbsp;
                            <div class="page-btn import">
                                <a href="${pageContext.request.contextPath}/image/icons/product_sample.csv" target="_blank">
                                    <i data-feather="download" class="me-2"></i>Download
                                </a>
                            </div>

                        </c:if>
                    </div>
                    <!-- /product list -->
                    <div class="card table-list-card card-view-section">
                        <div class="card-body">
                            <div class="table-top">
                                <div class="row">
                                    <form action="/productListByFilters" method="post">


                                        <!-- First Row with 3 columns -->
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

                                        <div class="col-lg-4 col-md-6 col-sm-12">
                                            <div class="mb-3 add-product" style="position: relative;">
                                                <label class="form-label">Product Type</label>
                                                <input type="text" id="categoryInput" class="form-control" placeholder="Enter Product Type" autocomplete="off">
                                                <input type="hidden" id="categoryIdInput" name="brand">
                                                <div id="category-autocomplete-list" class="autocomplete-list"></div>
                                            </div>
                                        </div>


                                        <!-- Product Type -->

                                        <div class="col-lg-4 col-sm-6 col-12">
                                            <div class="mb-3 add-product" style="position: relative;">
                                                <label class="form-label">Brand</label>
                                                <input type="text" id="productTypeInput" class="form-control" placeholder="Enter Brand" autocomplete="off">
                                                <input type="hidden" id="productTypeIdInput" name="productType">
                                                <div id="autocomplete-list" class="autocomplete-list"></div>
                                            </div>
                                        </div>



                                        <!-- Separate Row for Submit Button -->
                                        <div class="row">
                                            <div class="col-lg-4 col-sm-6 col-12">
                                                <div class="mb-3 add-product">
                                                    <button type="submit" class="btn btn-submit">Submit</button>
                                                </div>
                                            </div>
                                        </div>


                                    </form>
                                </div>
                            </div>



                            <!-- /Filter -->
                            <div class="card mb-0" id="filter_inputs">
                                <div class="card-body pb-0">
                                    <div class="row">
                                        <div class="col-lg-12 col-sm-12">
                                            <div class="row">
                                                <div class="col-lg-2 col-sm-6 col-12">
                                                    <div class="input-blocks">
                                                        <i data-feather="box" class="info-img"></i>
                                                        <select class="select">
                                                            <option>Choose Product</option>
                                                            <option>Lenovo 3rd Generation</option>
                                                            <option>Nike Jordan</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-lg-2 col-sm-6 col-12">
                                                    <div class="input-blocks">
                                                        <i data-feather="stop-circle" class="info-img"></i>
                                                        <select class="select">
                                                            <option>Choose Category</option>
                                                            <option>Laptop</option>
                                                            <option>Shoe</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-lg-2 col-sm-6 col-12">
                                                    <div class="input-blocks">
                                                        <i data-feather="git-merge" class="info-img"></i>
                                                        <select class="select">
                                                            <option>Choose Sub Category</option>
                                                            <option>Computers</option>
                                                            <option>Fruits</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-lg-2 col-sm-6 col-12">
                                                    <div class="input-blocks">
                                                        <i data-feather="stop-circle" class="info-img"></i>
                                                        <select class="select">
                                                            <option>All Brand</option>
                                                            <option>Lenovo</option>
                                                            <option>Nike</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-lg-2 col-sm-6 col-12">
                                                    <div class="input-blocks">
                                                        <i class="fas fa-money-bill info-img"></i>
                                                        <select class="select">
                                                            <option>Price</option>
                                                            <option>$12500.00</option>
                                                            <option>$12500.00</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-lg-2 col-sm-6 col-12">
                                                    <div class="input-blocks">
                                                        <a class="btn btn-filters ms-auto"> <i data-feather="search" class="feather-search"></i> Search </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- /Filter -->
                            <div class="table-responsive product-list">
                                <table class="table datanew2">
                                    <thead>
                                        <tr>
                                            <!--<th>Sr No</th>-->
                                            <th class="no-sort">
                                                <label class="checkboxs">
                                                    <input type="checkbox" id="select-all">
                                                    <span class="checkmarks"></span>
                                                </label>
                                            </th>
                                            <th>Product Name</th>
                                            <th>Product Type</th>
                                            <th>Brand Name</th>
                                            <th>SKU</th>
                                            <th class="no-sort">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% int sequenceNumber = 1;
                                            List<ProductDto> productList = (List<ProductDto>) request.getAttribute("productList");

                                            if (productList != null) {
                                                for (ProductDto productDto : productList) {
                                        %>
                                        <tr>
                                            <td>
                                                <label class="checkboxs">
                                                    <input type="checkbox" name="selectedProducts" value="<%= productDto.getProductId()%>">
                                                    <span class="checkmarks"></span>
                                                </label>
                                            </td>
                                            <td><%= productDto.getProductName()%></td>
                                            <td><%= productDto.getCategory().getCategoryName()%></td>
                                            <td><%= productDto.getProductType().getProductTypeName()%></td>
                                            <td><%= productDto.getProductSku()%></td>
                                            <td class="action-table-data">
                                                <div class="edit-delete-action">
                                                    <a class="me-2 p-2" href="${pageContext.request.contextPath}/productUpdate?productId=<%= productDto.getProductId()%>&after=${currentCursor}&brand=${selectedBrandId}&productType=${selectedProductTypeId}">
                                                        <i data-feather="edit" class="feather-edit"></i>
                                                    </a>
                                                    <a class="confirm-text p-2" href="${pageContext.request.contextPath}/productDelete?productId=<%= productDto.getProductId()%>&after=${currentCursor}&brand=${selectedBrandId}&productType=${selectedProductTypeId}">
                                                        <i data-feather="trash-2" class="feather-trash-2"></i>
                                                    </a>
                                                </div>    
                                            </td>
                                        </tr>
                                        <%
                                                    sequenceNumber++;
                                                }
                                            }%>
                                    </tbody>
                                </table>

                            </div>



                            <!-- Add Previous and Next buttons here -->
                            <div class="container mt-2 mb-4">
                                <div class="d-flex justify-content-end">
                                    <c:if test="${hasPreviousPage}">
                                        <div class="page-btn">
                                            <a href="${pageContext.request.contextPath}/productList?after=${firstCursor}&isAsc=prev&brand=${selectedBrandId}&productType=${selectedProductTypeId}" class="btn btn-secondary">Previous</a>
                                        </div>&nbsp;&nbsp;
                                    </c:if>
                                    <c:if test="${hasNextPage}">
                                        <div class="page-btn">
                                            <a href="${pageContext.request.contextPath}/productList?after=${lastCursor}&brand=${selectedBrandId}&productType=${selectedProductTypeId}" class="btn btn-secondary">Next</a>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /product list -->
                    <!----------------add new section---------------------->
                    <div class="card-container">
                        <div class="row">
                            <form action="/productList" method="get">
                                <div class="col-lg-4 col-sm-6 col-12">
                                    <div class="mb-3 add-product">
                                        <label class="form-label">Brand</label>
                                        <select class="select" name="brand">
                                            <option value="">Select Brand</option>
                                            <c:forEach var="brand" items="${categoryList}">
                                                <option value="${brand.categoryId}" ${brand.categoryId == selectedBrandId ? 'selected' : ''}>${brand.categoryName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-4 col-sm-6 col-12">
                                    <div class="mb-3 add-product">
                                        <label class="form-label">Product Type</label>
                                        <select class="select" name="productType">
                                            <option value="">Select Product Type</option>
                                            <c:forEach var="productType" items="${productTypeList}">
                                                <option value="${productType.productTypeId}" ${productType.productTypeId == selectedProductTypeId ? 'selected' : ''}>${productType.productTypeName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-lg-4 col-sm-6 col-12 " style="margin-top: 28px">
                                    <div class="mb-3 add-product">
                                        <button type="submit" class="btn btn-submit" style="width:100%">Submit</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <% if (productList != null) {
                                for (ProductDto productDto : productList) {%>
                        <div class="card-section">
                            <h2><%= productDto.getProductName()%></h2>
                            <p><strong>Brand:</strong> <%= productDto.getCategory().getCategoryName()%></p>
                            <p><strong>Product Type:</strong> <%= productDto.getProductType().getProductTypeName()%></p>
                            <p><strong>SKU:</strong> <%= productDto.getProductSku()%></p>
                        </div>
                        <%     }
                            }%>
                        <!-----------------end section-------------------->
                    </div>



                </div>
            </div>
        </form>

        <!-- /Main Wrapper -->
        <!-- Import Product -->
        <div class="modal fade" id="view-notes">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="page-wrapper-new p-0">
                        <div class="content">
                            <div class="modal-header border-0 custom-modal-header">
                                <div class="page-title">
                                    <h4>Import Product</h4>
                                </div>
                                <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body custom-modal-body">
                                <form id="importForm" action="importProductFromCsv" method="post" enctype="multipart/form-data">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="input-blocks image-upload-down">
                                                <label>Upload CSV File</label>
                                                <div class="image-upload download">
                                                    <input type="file" name="importProductCsvFile">
                                                    <div class="image-uploads">
                                                        <img src="${pageContext.request.contextPath}/image/download-img.png" alt="img">
                                                        <h4>Drag and drop a <span>file to upload</span></h4>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="col-lg-12">
                                        <div class="modal-footer-btn">
                                            <button type="button" class="btn btn-cancel me-2" data-bs-dismiss="modal">Cancel</button>
                                            <!-- Submit button with an onclick event to disable it after the first click -->
                                            <button type="submit" class="btn btn-submit" onclick="disableButton()">Submit</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /Import Product -->

        <!-- jQuery -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>

        <!-- Feather Icon JS -->
        <script src="${pageContext.request.contextPath}/js/feather.min.js"></script>

        <!-- Slimscroll JS -->
        <script src="${pageContext.request.contextPath}/js/jquery.slimscroll.min.js"></script>

        <!-- DataTable JS -->
        <script src="${pageContext.request.contextPath}/js/jquery.dataTables.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/dataTables.bootstrap5.min.js"></script>
        <!--<script src="${pageContext.request.contextPath}/js/productList.js"></script>-->
        <script>
                                                $(document).ready(function () {
                                                    // Suppress DataTables warning alerts
                                                    $.fn.dataTable.ext.errMode = 'none';

                                                    // Check if the table is already initialized
                                                    if ($.fn.DataTable.isDataTable('.datanew')) {
                                                        // Destroy the existing instance
                                                        $('.datanew').DataTable().clear().destroy();
                                                    }

                                                    // Initialize DataTable
                                                    $('.datanew').DataTable({
                                                        "paging": false, // Disable pagination
                                                        "searching": true, // Enable searching (default is true)
                                                        "ordering": true, // Enable ordering (default is true)
                                                        "info": false, // Hide information display
                                                        "responsive": true  // Enable responsive mode
                                                                // Additional options and configurations can be added here
                                                    });
                                                });
        </script>


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

        <script>
                                                function disableButton() {
                                                    document.getElementById("importForm").addEventListener("submit", function () {
                                                        document.querySelector('.btn-submit').setAttribute('disabled', true);
                                                    });
                                                }

                                                function nextData() {
                                                    const lastCursor = "${lastCursor}";
                                                    document.getElementById("after").value = lastCursor;
                                                    document.getElementById("isAsc").value = "next";
                                                    document.productlistForm.action = "${pageContext.request.contextPath}/productList?after=" + lastCursor;
                                                    document.productlistForm.submit();
                                                }
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

            function closePopup() {
                var source = "${source}";
                if (source === 'purchase') {
                    window.close();
                }
            }
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
            function deleteSelectedProducts() {
                var selected = [];
                document.querySelectorAll('input[name="selectedProducts"]:checked').forEach((checkbox) => {
                    selected.push(checkbox.value);
                });

                if (selected.length > 0) {
                    Swal.fire({
                        title: "Are you sure?",
                        text: "You won't be able to revert this!",
                        icon: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#3085d6",
                        cancelButtonColor: "#d33",
                        confirmButtonText: "Yes, delete it!",
                        cancelButtonText: "Cancel"
                    }).then(function (result) {
                        if (result.isConfirmed) {
                            // Proceed with the deletion via AJAX
                            var productIds = selected.join(',');

                            $.ajax({
                                type: "POST",
                                url: "${pageContext.request.contextPath}/deleteProductsByProductIds",
                                data: {
                                    productIds: productIds
                                },
                                success: function (response) {
                                    if (response.success) {
                                        location.reload(); // Reload the page to reflect changes
                                    } else {
                                        alert('Failed to delete products: ' + response.message);
                                    }
                                },
                                error: function (error) {
                                    alert('An error occurred while deleting products: ' + error.responseText);
                                }
                            });
                        }
                    });
                } else {
                    alert('Please select at least one product to delete.');
                }
            }

            // JavaScript to handle "Select All" checkbox
            document.getElementById('select-all').addEventListener('change', function () {
                var checkboxes = document.querySelectorAll('input[name="selectedProducts"]');
                checkboxes.forEach((checkbox) => {
                    checkbox.checked = this.checked;
                });
            });
        </script>

        <script>
            document.getElementById('productlistForm').addEventListener('keydown', (event) => {
                if (event.key === 'Enter') {
                    event.preventDefault(); // Prevents form submission
                    console.log('Enter key pressed inside the form');
                }
            });
        </script>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const productTypeInput = document.getElementById("productTypeInput");
                const productTypeIdInput = document.getElementById("productTypeIdInput");
                const autocompleteList = document.getElementById("autocomplete-list");
                let selectedIndex = -1;

                // Get selected product details
                const selectedProductTypeId = "${selectedProductTypeId}";
                console.log('selectedProductTypeId:', selectedProductTypeId);

                // Convert JSP product list into JavaScript array
                const productTypeList = [
            <c:forEach items="${productTypeList}" var="productType">
                    {name: "${productType.productTypeName}", id: "${productType.productTypeId}"},
            </c:forEach>
                ];

                // Pre-fill input field if product type ID exists
                const selectedProduct = productTypeList.find(product => product.id === selectedProductTypeId);
                if (selectedProduct) {
                    productTypeInput.value = selectedProduct.name;
                    productTypeIdInput.value = selectedProduct.id;
                }

                function showSuggestions(value) {
                    autocompleteList.innerHTML = "";
                    selectedIndex = -1;

                    if (!value.trim()) {
                        productTypeIdInput.value = ""; // ? Fix: Clear ID when input is empty
                        return;
                    }

                    productTypeList.forEach((product, index) => {
                        if (product.name.toLowerCase().includes(value.toLowerCase())) {
                            const suggestionItem = document.createElement("div");
                            suggestionItem.textContent = product.name;
                            suggestionItem.setAttribute("data-index", index);
                            suggestionItem.classList.add("autocomplete-item");

                            suggestionItem.addEventListener("click", function () {
                                productTypeInput.value = product.name;
                                productTypeIdInput.value = product.id;
                                autocompleteList.innerHTML = "";
                            });

                            autocompleteList.appendChild(suggestionItem);
                        }
                    });
                }

                productTypeInput.addEventListener("input", function () {
                    showSuggestions(this.value);

                    // ? Fix: If input is empty, clear hidden productTypeIdInput
                    if (!this.value.trim()) {
                        productTypeIdInput.value = "";
                    }
                });

                productTypeInput.addEventListener("keydown", function (e) {
                    const items = autocompleteList.querySelectorAll(".autocomplete-item");
                    if (items.length === 0)
                        return;

                    if (e.key === "ArrowDown") {
                        selectedIndex = (selectedIndex + 1) % items.length;
                    } else if (e.key === "ArrowUp") {
                        selectedIndex = (selectedIndex - 1 + items.length) % items.length;
                    } else if (e.key === "Enter") {
                        if (selectedIndex > -1) {
                            const selectedItem = items[selectedIndex];
                            productTypeInput.value = selectedItem.textContent;
                            const selectedProduct = productTypeList.find(p => p.name === selectedItem.textContent);
                            if (selectedProduct) {
                                productTypeIdInput.value = selectedProduct.id;
                            }
                            autocompleteList.innerHTML = "";
                        }
                        e.preventDefault();
                    } else if (e.key === "PageDown") {
                        selectedIndex = Math.min(selectedIndex + 5, items.length - 1);
                    } else if (e.key === "PageUp") {
                        selectedIndex = Math.max(selectedIndex - 5, 0);
                    }

                    items.forEach((item, i) => {
                        item.classList.toggle("selected", i === selectedIndex);
                    });

                    if (selectedIndex >= 0 && selectedIndex < items.length) {
                        items[selectedIndex].scrollIntoView({block: "nearest", behavior: "smooth"});
                    }
                });

                document.addEventListener("click", function (e) {
                    if (!productTypeInput.contains(e.target) && !autocompleteList.contains(e.target)) {
                        autocompleteList.innerHTML = "";
                        selectedIndex = -1;
                    }
                });
            });

        </script>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const categoryInput = document.getElementById("categoryInput");
                const categoryIdInput = document.getElementById("categoryIdInput");
                const categoryAutocompleteList = document.getElementById("category-autocomplete-list");
                let selectedIndex = -1;

                // Get selected category details from JSP
                const selectedBrandId = "${selectedBrandId}";
                console.log('selectedBrandId:', selectedBrandId);

                // Convert JSP category list into JavaScript array
                const categoryList = [
            <c:forEach items="${categoryList}" var="brand">
                    {name: "${brand.categoryName}", id: "${brand.categoryId}"},
            </c:forEach>
                ];

                // Pre-fill input field if category ID exists
                const selectedCategory = categoryList.find(category => category.id === selectedBrandId);
                if (selectedCategory) {
                    categoryInput.value = selectedCategory.name;
                    categoryIdInput.value = selectedCategory.id;
                }

                function showCategorySuggestions(value) {
                    categoryAutocompleteList.innerHTML = "";
                    selectedIndex = -1;

                    if (!value.trim())
                        return;

                    categoryList.forEach((category, index) => {
                        if (category.name.toLowerCase().includes(value.toLowerCase())) {
                            const suggestionItem = document.createElement("div");
                            suggestionItem.textContent = category.name;
                            suggestionItem.setAttribute("data-index", index);
                            suggestionItem.classList.add("autocomplete-item");

                            suggestionItem.addEventListener("click", function () {
                                categoryInput.value = category.name;
                                categoryIdInput.value = category.id;
                                categoryAutocompleteList.innerHTML = "";
                            });

                            categoryAutocompleteList.appendChild(suggestionItem);
                        }
                    });
                }

                categoryInput.addEventListener("input", function () {
                    showCategorySuggestions(this.value);

                    // ? Fix: If input is empty, clear hidden categoryIdInput
                    if (!this.value.trim()) {
                        categoryIdInput.value = "";
                    }
                });


                categoryInput.addEventListener("keydown", function (e) {
                    const items = categoryAutocompleteList.querySelectorAll(".autocomplete-item");
                    if (items.length === 0)
                        return;

                    if (e.key === "ArrowDown") {
                        selectedIndex = (selectedIndex + 1) % items.length;
                    } else if (e.key === "ArrowUp") {
                        selectedIndex = (selectedIndex - 1 + items.length) % items.length;
                    } else if (e.key === "Enter") {
                        if (selectedIndex > -1) {
                            const selectedItem = items[selectedIndex];
                            categoryInput.value = selectedItem.textContent;
                            const selectedCategory = categoryList.find(c => c.name === selectedItem.textContent);
                            if (selectedCategory) {
                                categoryIdInput.value = selectedCategory.id;
                            }
                            categoryAutocompleteList.innerHTML = "";
                        }
                        e.preventDefault();
                    } else if (e.key === "PageDown") {
                        selectedIndex = Math.min(selectedIndex + 5, items.length - 1);
                    } else if (e.key === "PageUp") {
                        selectedIndex = Math.max(selectedIndex - 5, 0);
                    }

                    items.forEach((item, i) => {
                        item.classList.toggle("selected", i === selectedIndex);
                    });

                    // ? **Smooth Auto-scroll**: Ensures selected item is visible
                    if (selectedIndex >= 0 && selectedIndex < items.length) {
                        items[selectedIndex].scrollIntoView({block: "nearest", behavior: "smooth"});
                    }
                });

                document.addEventListener("click", function (e) {
                    if (!categoryInput.contains(e.target) && !categoryAutocompleteList.contains(e.target)) {
                        categoryAutocompleteList.innerHTML = "";
                        selectedIndex = -1;
                    }
                });
            });

        </script>


    </body>
</html>