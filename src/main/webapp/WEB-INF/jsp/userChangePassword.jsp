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

        <!-- Fontawesome CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome/css/all.min.css">

        <!-- Main CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

    </head>
    <body class="account-page">

        <div id="global-loader" >
            <div class="whirly-loader"> </div>
        </div>

        <!-- Main Wrapper -->
        <div class="main-wrapper">
            <div class="account-content">
                <div class="login-wrapper login-new">
                    <div class="login-content user-login">

                        <form action="processUserChangePassword" method="post">
                            <div class="login-userset">
                                <div class="login-userheading">
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
                                    <h3>Reset password?</h3>
                                    <h4>Enter New Password & Confirm Password to get inside</h4>
                                </div>
                                <div class="form-login">
                                    <label> Old Password</label>
                                    <div class="pass-group">
                                        <input type="password" class="pass-input" name="oldPassword" required>
                                        <span class="fas toggle-password fa-eye-slash"></span>
                                    </div>
                                </div>
                                <div class="form-login">
                                    <label>New Password</label>
                                    <div class="pass-group">
                                        <input type="password" class="pass-inputa" name="newPassword" required>
                                        <span class="fas toggle-passworda fa-eye-slash"></span>
                                    </div>
                                </div>
                                <div class="form-login">
                                    <label> New Confirm Passworrd</label>
                                    <div class="pass-group">
                                        <input type="password" class="pass-inputs" name="confirmPassword" required>
                                        <span class="fas toggle-passwords fa-eye-slash"></span>
                                    </div>
                                </div>
                                <div class="form-login">
                                    <button type="submit" class="btn btn-login">Change Password</button>
                                </div>
                                <div class="signinform text-center">
                                    <h4>Return to <a href="${pageContext.request.contextPath}/" class="hover-a"> login </a></h4>
                                </div>
                            </div>
                        </form>

                    </div>

                </div>
            </div>
        </div>
        <!-- /Main Wrapper -->


        <!-- jQuery -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>

        <!-- Feather Icon JS -->
        <script src="${pageContext.request.contextPath}/js/feather.min.js"></script>

        <!-- Bootstrap Core JS -->
        <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>

        <!-- Custom JS --><script src="assets/js/theme-script.js"></script>	
        <script src="${pageContext.request.contextPath}/js/script.js"></script>


    </body>
</html>