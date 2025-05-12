<%-- 
    Document   : monthlyTallyReport
    Created on : May 6, 2024, 11:34:40 AM
    Author     : Rajeev kumar
--%>
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
            /* Ensure the design only shows on mobile screens */
            /* Ensure the design only shows on mobile screens */
            @media (max-width: 768px) {
                .card-container {
                    display: flex;
                    flex-wrap: wrap;
                    justify-content: space-around;
                    background-color: white;
                    padding: 10px;
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
                    top: 52px;
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
                                <div class="page-title mob-view">
                                    <h4>Monthly Tally Report</h4>
                                    <h6>Manage your Monthly Tally Report</h6>
                                </div>							
                            </div>
                        <c:if test="${autoLogin == null}">
                            <ul class="table-top-head mob-view">

                                <li>
                                    <a data-bs-toggle="tooltip" data-bs-placement="top" title="Excel"><img src="${pageContext.request.contextPath}/image/icons/excel.svg" alt="img"></a>
                                </li>


                                <li>
                                    <a data-bs-toggle="tooltip" data-bs-placement="top" title="Collapse" id="collapse-header"><i data-feather="chevron-up" class="feather-chevron-up"></i></a>
                                </li>
                            </ul>
                            <div class="page-btn mob-view">
                                <a href="#" class="btn btn-added" data-bs-toggle="modal" data-bs-target="#add-units"><i data-feather="plus-circle" class="me-2"></i>Add New</a>
                            </div>
                        </c:if>
                    </div>
                    <!-- /product list -->
                    <div class="card table-list-card">
                        <div class="card-body">
                            <div class="table-top">
                                <div class="search-set">
                                    <div class="search-input">
                                        <a href="" class="btn btn-searchset"><i data-feather="search" class="feather-search"></i></a>
                                    </div>
                                </div>

                                <!--                                <div class="form-sort">
                                                                    <i data-feather="sliders" class="info-img"></i>
                                                                    <select class="select">
                                                                        <option>Sort by Date</option>
                                                                        <option>Newest</option>
                                                                        <option>Oldest</option>
                                                                    </select>
                                                                </div>-->
                            </div>
                            <!-- /Filter -->
                            <div class="card" id="filter_inputs">
                                <div class="card-body pb-0">
                                    <div class="row">
                                        <div class="col-lg-2 col-sm-6 col-12">
                                            <div class="input-blocks">
                                                <i data-feather="archive" class="info-img"></i>
                                                <select class="select">
                                                    <option>Choose Warehouse</option>
                                                    <option>Lobar Handy</option>
                                                    <option>Quaint Warehouse</option>
                                                    <option>Traditional Warehouse</option>
                                                    <option>Cool Warehouse</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-2 col-sm-6 col-12">
                                            <div class="input-blocks">
                                                <i data-feather="box" class="info-img"></i>
                                                <select class="select">
                                                    <option>Choose Product</option>
                                                    <option>Nike Jordan</option>
                                                    <option>Apple Series 5 Watch</option>
                                                    <option>Amazon Echo Dot</option>
                                                    <option>Lobar Handy</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-2 col-sm-6 col-12">
                                            <div class="input-blocks">
                                                <i data-feather="calendar" class="info-img"></i>
                                                <div class="input-groupicon">
                                                    <input type="text" class="datetimepicker" placeholder="Choose Date" >
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-2 col-sm-6 col-12">
                                            <div class="input-blocks">
                                                <i data-feather="user" class="info-img"></i>
                                                <select class="select">
                                                    <option>Choose Person</option>
                                                    <option>Steven</option>
                                                    <option>Gravely</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-lg-4 col-sm-6 col-12 ms-auto">
                                            <div class="input-blocks">
                                                <a class="btn btn-filters ms-auto"> <i data-feather="search" class="feather-search"></i> Search </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- /Filter -->
                            <div class="table-responsive">
                                <table class="table  datanew">
                                    <thead>
                                        <tr>
                                            <th class="no-sort">
                                                <label class="checkboxs">
                                                    <input type="checkbox" id="select-all">
                                                    <span class="checkmarks"></span>
                                                </label>
                                            </th>
                                            <th>Warehouse</th>
                                            <th>Shop</th>
                                            <th>Product</th>
                                            <th>Date</th>
                                            <th>Person</th>
                                            <th>Notes</th>
                                            <th class="no-sort">Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!-- /product list -->
                    <div class="card-container">
                        <div class="card-section">
                            <h2><strong>Warehouse: </strong></h2>
                            <p><strong>Shop: </strong></p>
                            <p><strong>Product: </strong></p>
                            <p><strong>Date: </strong></p>
                            <p><strong>Person: </strong></p>
                            <p><strong>Notes: </strong></p>
                            <div class="action-buttons">
                            </div>
                        </div>
                        <!-----------------end section-------------------->
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

        <!-- Datetimepicker JS -->
        <script src="${pageContext.request.contextPath}/js/moment.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap-datetimepicker.min.js"></script>

        <!-- Select2 JS -->
        <script src="${pageContext.request.contextPath}/plugins/select2/js/select2.min.js"></script>

        <!-- Sweetalert 2 -->
        <script src="${pageContext.request.contextPath}/plugins/sweetalert/sweetalert2.all.min.js"></script>
        <script src="${pageContext.request.contextPath}/plugins/sweetalert/sweetalerts.min.js"></script>

        <!-- Custom JS --><script src="${pageContext.request.contextPath}/js/theme-script.js"></script>	
        <script src="${pageContext.request.contextPath}/js/script.js"></script>


    </body>
</html>
