<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
        <meta name="description" content="POS - Bootstrap Admin Template">
        <meta name="keywords"
              content="admin, estimates, bootstrap, business, corporate, creative, invoice, html5, responsive, Projects">
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
                                    <h4>New Staff Members</h4>
                                    <h6>Create new Staff Members</h6>
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
                        <ul class="table-top-head">
                            <li>
                                <div class="page-btn">
                                    <a href="${pageContext.request.contextPath}/staffList" class="btn btn-secondary"><i data-feather="arrow-left" class="me-2"></i>Back to Staff Members</a>
                                </div>
                            </li>
                            <li>
                                <a data-bs-toggle="tooltip" data-bs-placement="top" title="Collapse" id="collapse-header"><i data-feather="chevron-up" class="feather-chevron-up"></i></a>
                            </li>
                        </ul>

                    </div>
                    <!-- /add -->
                    <form action="processStaffCreate" method="post">
                        <div class="card">
                            <div class="card-body add-product pb-0">
                                <div class="accordion-card-one accordion" id="accordionExample">
                                    <div class="accordion-item">
                                        <div class="accordion-header" id="headingOne">
                                            <div class="accordion-button" data-bs-toggle="collapse" data-bs-target="#collapseOne"  aria-controls="collapseOne">
                                                <div class="addproduct-icon">
                                                    <h5><i data-feather="info" class="add-info"></i><span>Staff Members Information</span></h5>
                                                    <a href="javascript:void(0);"><i data-feather="chevron-down" class="chevron-down-add"></i></a>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                                            <div class="accordion-body">
                                                <!-- Hidden input field for staffId -->
                                                <input type="hidden" id="staffId" name="staffId" >

                                                <div class="row">
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">First Name</label>
                                                            <input type="text" class="form-control" name="staffFirstName" required >
                                                        </div>
                                                    </div>

                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Last Name</label>
                                                            <input type="text" class="form-control" name="staffLastName" required >
                                                        </div>
                                                    </div>

                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Email</label>
                                                            <input type="text" class="form-control" name="staffEmail" required >
                                                        </div>
                                                    </div>

                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Mobile Number</label>
                                                            <input type="text" class="form-control" name="staffMobileNo" required >
                                                        </div>
                                                    </div>


                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Permissions</label>
                                                            <select class="select" name="addGroups" required multiple>
                                                                <option value="">Select Permissions</option>
                                                                <c:forEach var="group" items="${permissionGroups}">
                                                                    <option value="${group.permissionGroupsId}">${group.permissionGroupsName}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>

                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Stores</label>
                                                            <select class="select" name="stores" required multiple>
                                                                <option value="">Select Stores</option>
                                                                <c:forEach var="channel" items="${channelList}">
                                                                    <option value="${channel.channel_id}">${channel.channelName}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>





                                                </div>



                                                <div class="row">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                            </div>
                        </div>
                        <div class="col-lg-12">
                            <div class="btn-addproduct mb-4">
                                <button type="button" class="btn btn-cancel me-2">Cancel</button>
                                <button type="submit" class="btn btn-submit">Add Staff Members</button>
                            </div>
                        </div>
                    </form>
                    <!-- /add -->

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


    </body>

</html>