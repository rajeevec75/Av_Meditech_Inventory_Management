<%@page import="org.json.JSONArray"%>
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
        <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dataTables.bootstrap5.min.css">-->

        <!-- Fontawesome CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome/css/all.min.css">

        <!-- Main CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

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
                        <div class="page-header">
                            <div class="add-item d-flex">
                                <div class="page-title">
                                    <h4>Product List1</h4>
                                    <input type="hidden" name="after" value="" />
                                    <input type="hidden" name="isAsc" value="" />
                                    <h6>Manage your products</h6>
                                    <!-- Add this snippet in your JSP page where you want to display the error message -->
                                <c:if test="${not empty errorMessage}">
                                    <div class="alert alert-danger" role="alert">
                                        Following products could not be imported. Please check SKU duplication -<br>${errorMessage}
                                    </div>
                                </c:if>

                            </div>
                        </div>
                        <ul class="table-top-head">

                            <li>
                                <a data-bs-toggle="tooltip" data-bs-placement="top" title="Excel"><img src="${pageContext.request.contextPath}/image/icons/excel.svg" alt="img"></a>
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
                        <div class="page-btn import">
                            <a href="${pageContext.request.contextPath}/image/icons/product_sample.csv" target="_blank">
                                <i data-feather="download" class="me-2"></i>Download
                            </a>
                        </div>
                    </div>

                    <!-- /product list -->
                    <div class="card table-list-card">
                        <div class="card-body">
                            <div class="table-top">
                                <div class="search-set">
                                    <div class="search-input">
                                        <a href="javascript:void(0);" class="btn btn-searchset"><i data-feather="search" class="feather-search"></i></a>
                                    </div>
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
                                <table class="table datanew">
                                    <thead>
                                        <tr>
                                            <th>Sr No</th>
                                            <th>Product Name</th>
                                            <th>Brand Name</th>
                                            <th>Product Type</th>
                                            <th>SKU</th>
                                            <th class="no-sort">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%-- Loop through each product and display their details --%>
                                        <% int sequenceNumber = 1;
                                            List<ProductDto> productList = (List<ProductDto>) request.getAttribute("productList");

                                            if (productList != null) {
                                                for (ProductDto productDto : productList) {
                                        %>
                                        <tr>
                                            <td><%= sequenceNumber%></td>
                                            <td><%= productDto.getProductName()%></td>
                                            <td><%= productDto.getCategory().getCategoryName()%></td>
                                            <td><%= productDto.getProductType().getProductTypeName()%></td>
                                            <td><%= productDto.getProductSku()%></td>
                                            <td class="action-table-data">
                                                <div class="edit-delete-action">
                                                    <a class="me-2 p-2" href="${pageContext.request.contextPath}/productUpdate?productId=<%= productDto.getProductId()%>">
                                                        <i data-feather="edit" class="feather-edit"></i>
                                                    </a>
                                                    <a class="confirm-text p-2" href="${pageContext.request.contextPath}/productDelete?productId=<%= productDto.getProductId()%>">
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
                                    <div class="page-btn">
                                        <a href="${pageContext.request.contextPath}/productList?after=${firstCursor}&isAsc=prev" class="btn btn-secondary">Previous</a>
                                    </div>&nbsp;&nbsp;
                                    <div class="page-btn">
                                        <a href="${pageContext.request.contextPath}/productList?after=${lastCursor}" class="btn btn-secondary">Next</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /product list -->
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

    </body>
</html>
