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

    </head>

    <body>

        <div id="global-loader">
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
                                <div class="page-title">
                                    <h4>Update Customer/Supplier</h4>
                                    <h6>Update Existing Customer/Supplier</h6>
                                <c:if test="${not empty errorMessage}">
                                    <div class="alert alert-danger" role="alert">
                                        ${errorMessage}
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <ul class="table-top-head">
                            <li>
                                <button class="add-button btn btn-secondary" onclick="redirectToAddressCreate()">Add Address</button>
                            </li>
                            <li>
                                <button class="add-button btn btn-secondary" onclick="redirectToAddressList()">Show Address</button>
                            </li>
                            <li>
                                <div class="page-btn">

                                    <a href="javascript:history.go(-1)" class="btn btn-secondary"><i data-feather="arrow-left" class="me-2"></i>Back</a>

                                </div>
                            </li>
                            <li>
                                <a data-bs-toggle="tooltip" data-bs-placement="top" title="Collapse" id="collapse-header"><i data-feather="chevron-up" class="feather-chevron-up"></i></a>
                            </li>
                        </ul>



                    </div>
                    <form action="processUpdate" method="post">
                        <div class="card">
                            <div class="card-body add-product pb-0">
                                <div class="accordion-card-one accordion" id="accordionExample">
                                    <!-- Basic Information Section -->
                                    <div class="accordion-item">
                                        <div class="accordion-header" id="headingBasic">
                                            <div class="accordion-button" data-bs-toggle="collapse" data-bs-target="#collapseBasic" aria-controls="collapseBasic">
                                                <div class="addproduct-icon">
                                                    <h5><i data-feather="info" class="add-info"></i><span>Basic Information - ${companyName} (Code: ${userCode})</span></h5>
                                                    <a href="javascript:void(0);"><i data-feather="chevron-down" class="chevron-down-add"></i></a>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="collapseBasic" class="accordion-collapse collapse show" aria-labelledby="headingBasic" data-bs-parent="#accordionExample">
                                            <div class="accordion-body">
                                                <div class="row">
                                                    <!-- Hidden input field for supplierId -->
                                                    <input type="hidden" id="customerId" name="id" value="${customerDto.id}">

                                                    <input type="hidden" id="supplierId" name="supplierId" value="${customerDto.id}">
                                                    <!-- Company Name -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Company Name<span style="color: red">*</span></label>
                                                            <input type="text" class="form-control" name="companyName" required value="${companyName}">
                                                        </div>
                                                    </div>
                                                    <!-- First Name -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">First Name</label>
                                                            <input type="text" class="form-control" name="firstName" value="${customerDto.firstName}">
                                                        </div>
                                                    </div>
                                                    <!-- Last Name -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Last Name</label>
                                                            <input type="text" class="form-control" name="lastName" value="${customerDto.lastName}">
                                                        </div>
                                                    </div>
                                                    <!-- Email -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Email</label>
                                                            <input type="text" class="form-control" name="email" value="${customerDto.email}">
                                                        </div>
                                                    </div>
                                                    <!-- Mobile Number -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Mobile Number<span style="color: red">*</span></label>
                                                            <input type="text" class="form-control" name="mobileNumber" required value="${customerDto.mobileNumber}">
                                                        </div>
                                                    </div>
                                                    <!-- Date Of Birth -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">User Code<span style="color: red">*</span></label>
                                                            <input type="text" class="form-control" name="userCode" required value="${userCode}">
                                                        </div>
                                                    </div>

                                                    <!-- Choose User Type -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Choose User Type<span style="color: red">*</span></label>
                                                            <select class="select" name="userType" required>
                                                                <option value="">Select UserType</option>
                                                                <option value="customer" ${userType == 'customer' ? 'selected' : ''}>Customer</option>
                                                                <option value="supplier" ${userType == 'supplier' ? 'selected' : ''}>Supplier</option>
                                                                <option value="both" ${userType == 'both' ? 'selected' : ''}>Both</option>
                                                            </select>
                                                        </div>
                                                    </div>

                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Customer/Supplier Id</label>
                                                            <input type="text" class="form-control" name="mobileNumber1" readonly value="${customerDto.supplierId}">
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Tax Information Section -->
                                    <div class="accordion-item">
                                        <div class="accordion-header" id="headingTax">
                                            <div class="accordion-button" data-bs-toggle="collapse" data-bs-target="#collapseTax" aria-controls="collapseTax">
                                                <div class="addproduct-icon">
                                                    <h5><i data-feather="info" class="add-info"></i><span>Tax Information</span></h5>
                                                    <a href="javascript:void(0);"><i data-feather="chevron-down" class="chevron-down-add"></i></a>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="collapseTax" class="accordion-collapse collapse show" aria-labelledby="headingTax" data-bs-parent="#accordionExample">
                                            <div class="accordion-body">
                                                <div class="row">
                                                    <!-- Pan Number -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Pan Number</label>
                                                            <input type="text" class="form-control" name="panNumber" value="${panNumber}">
                                                        </div>
                                                    </div>
                                                    <!-- GST Number -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">GST Number</label>
                                                            <input type="text" class="form-control" name="gstNumber" value="${gstNumber}">
                                                        </div>
                                                    </div>
                                                    <!-- TIN Number -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">TIN Number</label>
                                                            <input type="text" class="form-control" name="tinNumber" value="${tinNumber}">
                                                        </div>
                                                    </div>
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
                                <button type="submit" class="btn btn-submit">Update </button>
                            </div>
                        </div>
                    </form>


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
                                    function redirectToAddressCreate() {
                                        // Get the current URL parameters
                                        var queryParams = new URLSearchParams(window.location.search);

                                        // Retrieve the customerId and supplierId parameter values from the URL
                                        var customerId = queryParams.get('customerId');
                                        var supplierId = queryParams.get('supplierId');

                                        // Check if customerId is present and supplierId is null, redirect to customer addressCreate page
                                        if (customerId !== null && supplierId === null) {
                                            window.location.href = '${pageContext.request.contextPath}/addressCreate?customerId=' + encodeURIComponent(customerId);
                                        }
                                        // Check if supplierId is present and customerId is null, redirect to supplier addressCreate page
                                        else if (supplierId !== null && customerId === null) {
                                            window.location.href = '${pageContext.request.contextPath}/addressCreate?supplierId=' + encodeURIComponent(supplierId);
                                        }
                                        // Handle the case where neither or both IDs are present
                                        else {
                                            console.error("Both customerId and supplierId are either present or missing. Unable to determine redirection.");
                                            // Optionally, handle this case by redirecting to an error page or displaying an error message
                                            // window.location.href = '${pageContext.request.contextPath}/errorPage';
                                        }
                                    }


                                    function redirectToAddressList() {
                                        // Get the current URL parameters
                                        var queryParams = new URLSearchParams(window.location.search);

                                        // Retrieve the customerId and supplierId parameter values from the URL
                                        var customerId = queryParams.get('customerId');
                                        console.log("customerId" + customerId);
                                        var supplierId = queryParams.get('supplierId');
                                        console.log("supplierId" + supplierId);

                                        // Check if customerId is present and supplierId is null, redirect to customer details page
                                        if (customerId !== null && supplierId === null) {
                                            window.location.href = '${pageContext.request.contextPath}/getAddressListBy?customerId=' + encodeURIComponent(customerId);
                                        }
                                        // Check if supplierId is present and customerId is null, redirect to supplier details page
                                        else if (supplierId !== null && customerId === null) {
                                            window.location.href = '${pageContext.request.contextPath}/getAddressListBy?supplierId=' + encodeURIComponent(supplierId);
                                        }
                                        // Handle the case where neither or both IDs are present
                                        else {
                                            console.error("Both customerId and supplierId are either present or missing. Unable to determine redirection.");
                                            // Optionally, handle this case by redirecting to an error page or displaying an error message
                                            // window.location.href = '${pageContext.request.contextPath}/errorPage';
                                        }
                                    }

        </script>

    </body>

</html>