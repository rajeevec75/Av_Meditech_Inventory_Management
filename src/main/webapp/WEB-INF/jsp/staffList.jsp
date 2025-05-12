
<%@page import="com.AvMeditechInventory.entities.PermissionGroups"%>
<%@page import="com.AvMeditechInventory.dtos.StaffDto"%>
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
                    display: flex!important;
                    justify-content: center !important;
                    margin-top: 10px !important;
                    position: absolute !important;
                    top: -5px !important;
                    right: 0 !important;
                }



                .edit-btn {
                    color: blue!important;
                    border: 1px solid #b991f5!important;
                    border-radius: 8px!important;
                }
                .staff-link {
                    display: inline-block;
                    max-width: 200px; /* Adjust as needed */
                    white-space: nowrap;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    vertical-align: bottom;
                }
                .edit-delete-action{
                    position: absolute;
                    top: 14px;
                    right: 8px;
                }

                .delete-btn {
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

            <!-- Header and Sidebar -->
            <jsp:include page="header.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>
            </div>
            <div class="page-wrapper">
                <div class="content">
                    <div class="page-header">
                        <div class="add-item d-flex">
                            <div class="page-title mob-view">
                                <h4>Staff Members List</h4>
                                <h6>Manage your Staff Members</h6>
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
                                <a data-bs-toggle="tooltip" data-bs-placement="top" title="Collapse" id="collapse-header"><i data-feather="chevron-up" class="feather-chevron-up"></i></a>
                            </li>
                        </ul>
                        <div class="page-btn mob-view">
                            <a href="${pageContext.request.contextPath}/staffCreate" class="btn btn-added"><i data-feather="plus-circle" class="me-2"></i>Add New Staff Members</a>
                        </div>	
                    </c:if>
                </div>

                <!-- /product list -->
                <div class="card table-list-card">
                    <div class="card-body">
                        <div class="table-responsive product-list">
                            <table class="table datanew1">
                                <thead>
                                    <tr>
                                        <th>First Name</th>
                                        <th>Last Name</th>
                                        <th>Email</th>
                                        <th>Mobile Number</th>
                                        <th>Permissions</th>
                                        <th class="no-sort">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%-- Display staff members --%>
                                    <%                List<StaffDto> staffList = (List<StaffDto>) request.getAttribute("staffList");
                                        if (staffList != null && !staffList.isEmpty()) {
                                            for (int i = 0; i < staffList.size(); i++) {
                                                StaffDto staff = staffList.get(i);
                                    %>
                                    <tr>
                                        <td><%= staff.getStaffFirstName()%></td>
                                        <td><%= staff.getStaffLastName()%></td>
                                        <td><a href="/staffUpdate?staffId=<%= staff.getStaffId()%>" class="staff-link"><%= staff.getStaffEmail()%></a></td>
                                        <td><%= staff.getStaffMobileNo() != null ? staff.getStaffMobileNo() : ""%></td>
                                        <td>
                                            <%
                                                List<PermissionGroups> permissions = staff.getPermissions();
                                                if (permissions != null && !permissions.isEmpty()) {
                                                    for (PermissionGroups permission : permissions) {
                                                        out.print(permission.getPermissionGroupsName() + ", ");
                                                    }
                                                } else {
                                                    out.print("No permissions");
                                                }
                                            %>
                                        </td>
                                        <td class="action-table-data">
                                            <div class="edit-delete-action">
                                                <a class="me-2 p-2" href="${pageContext.request.contextPath}/staffUpdate?staffId=<%= staff.getStaffId()%>">
                                                    <i data-feather="edit" class="feather-edit"></i>
                                                </a>
                                                <a class="confirm-text p-2" href="${pageContext.request.contextPath}/staffDelete?staffId=<%= staff.getStaffId()%>&email=<%= staff.getStaffEmail()%>">
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
                <!----------------add new section---------------------->
                <div class="card-container">
                    <% if (staffList != null) {
                            for (StaffDto staff : staffList) {%>
                    <div class="card-section">
                        <div class="card-content">
                            <h2>First Name: <%= staff.getStaffFirstName()%></h2>
                            <h2>Last Name: <%= staff.getStaffLastName()%></h2>
                            <h2>Email: <a href="/staffUpdate?staffId=<%= staff.getStaffId()%>" class="staff-link"><%= staff.getStaffEmail()%></a></h2>
                            <h2>Mobile No: <%= staff.getStaffMobileNo() != null ? staff.getStaffMobileNo() : ""%></h2>
                            <h2>Permissions: <td>
                                    <%
                                        List<PermissionGroups> permissions = staff.getPermissions();
                                        if (permissions != null && !permissions.isEmpty()) {
                                            for (PermissionGroups permission : permissions) {
                                                out.print(permission.getPermissionGroupsName() + ", ");
                                            }
                                        } else {
                                            out.print("No permissions");
                                        }
                                    %>
                                </td></h2>
                            <td class="action-table-data">
                                <div class="edit-delete-action">
                                    <a class="me-2 p-2 edit-btn" href="${pageContext.request.contextPath}/staffUpdate?staffId=<%= staff.getStaffId()%>">
                                        <i data-feather="edit" class="feather-edit"></i>
                                    </a>
                                    <a class="confirm-text p-2 delete-btn" href="${pageContext.request.contextPath}/staffDelete?staffId=<%= staff.getStaffId()%>">
                                        <i data-feather="trash-2" class="feather-trash-2"></i>
                                    </a>
                                </div>	
                            </td>
                        </div>
                    </div>
                    <%     }
                        }%>
                    <!-----------------end section-------------------->

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


    </body>
</html>