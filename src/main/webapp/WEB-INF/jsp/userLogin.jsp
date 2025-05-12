<%-- 
    Document   : userLogin
    Created on : Apr 23, 2024, 8:47:21 AM
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
        <title>Login - Av Meditech Inventory</title>

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/image/av-meditech-fevicon.png">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/bootstrap.min.css">

        <!-- Fontawesome CSS -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome/css/fontawesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome/css/all.min.css">

        <!-- Main CSS -->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">

    </head>
    <body class="account-page">

        <div id="global-loader" >
            <div class="whirly-loader"> </div>
        </div>
        <div class="main-wrapper">
            <div class="account-content">
                <div class="login-wrapper login-new">
                    <div class="container">
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

                        <div class="login-content user-login">
                            <img src="${pageContext.request.contextPath}/image/av-meditech-logo.png" alt="img" style="width: 30%; height: 180%;">

                            <form action="processUserLogin" method="post">
                                <div class="login-userset">
                                    <div class="login-userheading">

                                        <h3>Sign In</h3>
                                        <h4>Access the AvMeditech panel using your email and password.</h4>
                                    </div>
                                    <div class="form-login">
                                        <label class="form-label">Email Address</label>
                                        <div class="form-addons">
                                            <input type="text" class="form-control" name="userEmail">
                                            <img src="${pageContext.request.contextPath}/image/icons/mail.svg" alt="img">
                                        </div>
                                    </div>
                                    <div class="form-login">
                                        <label>Password</label>
                                        <div class="pass-group">
                                            <input type="password" class="pass-input" name="userPassword">
                                            <span class="fas toggle-password fa-eye-slash"></span>
                                        </div>
                                    </div>

                                    <div class="form-login">
                                        <button class="btn btn-login" type="submit">Sign In</button>
                                    </div>



                                </div>
                            </form>

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

        <!-- Bootstrap Core JS -->
        <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>

        <!-- Custom JS --><script src="/js/theme-script.js"></script>	
        <script src="${pageContext.request.contextPath}/js/script.js"></script>
    </body>
</html>
