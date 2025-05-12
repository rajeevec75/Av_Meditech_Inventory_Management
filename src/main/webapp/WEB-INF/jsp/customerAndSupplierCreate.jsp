<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
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

        <!-- jQuery UI for Autocomplete -->
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>

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
                                    <h4>New Customer/Supplier</h4>
                                    <h6>Create new customer/supplier</h6>
                                <c:if test="${not empty errorMessage}">
                                    <div class="alert alert-danger" role="alert">
                                        ${errorMessage}
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <ul class="table-top-head">
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
                    <form action="processCustomerAndSupplier" method="post">
                        <div class="card">
                            <div class="card-body add-product pb-0">
                                <div class="accordion-card-one accordion" id="accordionExample">
                                    <!-- Basic Information Section -->
                                    <div class="accordion-item">
                                        <div class="accordion-header" id="headingBasic">
                                            <div class="accordion-button" data-bs-toggle="collapse" data-bs-target="#collapseBasic" aria-controls="collapseBasic">
                                                <div class="addproduct-icon">
                                                    <h5><i data-feather="info" class="add-info"></i><span>Basic Information</span></h5>
                                                    <a href="javascript:void(0);"><i data-feather="chevron-down" class="chevron-down-add"></i></a>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="collapseBasic" class="accordion-collapse collapse show" aria-labelledby="headingBasic" data-bs-parent="#accordionExample">
                                            <div class="accordion-body">
                                                <div class="row">
                                                    <!-- Company Name -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Company Name<span style="color: red">*</span></label>
                                                            <input type="text" class="form-control" name="companyName" required>
                                                        </div>
                                                    </div>
                                                    <!-- First Name -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">First Name</label>
                                                            <input type="text" class="form-control" value="" name="firstName">
                                                        </div>
                                                    </div>
                                                    <!-- Last Name -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Last Name</label>
                                                            <input type="text" class="form-control" value="" name="lastName">
                                                        </div>
                                                    </div>
                                                    <!-- Email -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Email</label>
                                                            <input type="email" class="form-control" value="" name="email">
                                                        </div>
                                                    </div>
                                                    <!-- Mobile Number -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Mobile Number<span style="color: red">*</span></label>
                                                            <input type="text" class="form-control" name="mobileNumber" required>
                                                        </div>
                                                    </div>
                                                    <!-- Date Of Birth -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">User Code<span style="color: red">*</span></label>
                                                            <input type="text" class="form-control" name="userCode">
                                                        </div>
                                                    </div>

                                                    <!-- Choose User Type -->

                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">User Type<span style="color: red">*</span></label>
                                                            <select class="select" name="userType" required>
                                                                <option value="">Select UserType</option>
                                                                <option value="customer">Customer</option>
                                                                <option value="supplier">Supplier</option>
                                                                <option value="both">Both</option>
                                                            </select>
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Address Information Section -->
                                    <div class="accordion-item">
                                        <div class="accordion-header" id="headingAddress">
                                            <div class="accordion-button" data-bs-toggle="collapse" data-bs-target="#collapseAddress" aria-controls="collapseAddress">
                                                <div class="addproduct-icon">
                                                    <h5><i data-feather="info" class="add-info"></i><span>Address Information</span></h5>
                                                    <a href="javascript:void(0);"><i data-feather="chevron-down" class="chevron-down-add"></i></a>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="collapseAddress" class="accordion-collapse collapse show" aria-labelledby="headingAddress" data-bs-parent="#accordionExample">
                                            <div class="accordion-body">
                                                <div class="row">
                                                    <!-- Address 1 -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Address 1<span style="color: red">*</span></label>
                                                            <input type="text" class="form-control" name="streetAddress1" required>
                                                        </div>
                                                    </div>
                                                    <!-- Address 2 -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Address 2</label>
                                                            <input type="text" class="form-control" name="streetAddress2">
                                                        </div>
                                                    </div>
                                                    <!-- City -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">City<span style="color: red">*</span></label>
                                                            <input type="text" class="form-control" name="city" required>
                                                        </div>
                                                    </div>
                                                    <!-- Choose Country -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Country<span style="color: red">*</span></label>
                                                            <select class="select" name="country" required>
                                                                <option value="">Select Country</option>
                                                                <option value="IN">India</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <!-- State -->

                                                    <!-- Country Area -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3">
                                                            <label for="state-input" class="form-label">State <span style="color: red">*</span></label>
                                                            <input id="state-input" class="form-control" placeholder="Type to search..."  autocomplete="true" name="countryArea"/>
                                                        </div>
                                                    </div>

                                                    <!-- Postal Code -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Postal Code<span style="color: red">*</span></label>
                                                            <input type="text" class="form-control" name="postalCode" required>
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
                                                    <!-- PAN Number -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">PAN Number</label>
                                                            <input type="text" class="form-control" value="" name="panNumber">
                                                        </div>
                                                    </div>
                                                    <!-- GST Number -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">GST Number</label>
                                                            <input type="text" class="form-control" value="" name="gstNumber">
                                                        </div>
                                                    </div>
                                                    <!-- TIN Number -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">TIN Number</label>
                                                            <input type="text" class="form-control" value="" name="tinNumber">
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
                                <button type="submit" class="btn btn-submit">Add</button>
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



        <!-- Include jQuery and jQuery UI Scripts -->
        <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>

        <script>
            // List of Indian states for autocomplete
            const states = [

                "ANDHRA PRADESH", "ANDAMAN and NICOBAR ISLANDS", "Arunachal Pradesh", "Assam", "Bihar",
                "Chandigarh", "Chhattisgarh", "Dadra and Nagar Haveli", "Delhi", "Goa", "Gujarat",
                "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala",
                "Ladakh", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya",
                "Mizoram", "Nagaland", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim",
                "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"
            ];

            // Initialize jQuery UI autocomplete
            $(function () {
                console.log('9999999999999999999999');
                $("#state-input").autocomplete({
                    source: states
                });
            });
        </script>


    </body>

</html>