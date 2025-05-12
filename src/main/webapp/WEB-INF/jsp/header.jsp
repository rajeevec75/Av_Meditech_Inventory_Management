<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.AvMeditechInventory.results.Result"%>
<%@page import="com.AvMeditechInventory.util.CommonUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession" %>

<%-- Token refresh logic --%>
<%
    String token = (String) session.getAttribute("token");
    String autoLogin = (String) session.getAttribute("autoLogin");
    Result resultTokenVerify = CommonUtil.tokenVerify(token, request);

    if (resultTokenVerify.isSuccess()) {
        // Token is valid, no need to refresh
    } else {
        String refreshToken = (String) session.getAttribute("refreshToken");
        Result resultRefreshToken = CommonUtil.tokenRefresh(refreshToken, session, request);
        // Check if token refresh is successful
        if (resultRefreshToken.isSuccess()) {
            // Token refresh success
            token = (String) session.getAttribute("token");
        } else {
            // Token refresh failed, handle error
        }
    }

    List<Map<String, Object>> channelList = (List<Map<String, Object>>) session.getAttribute("channelList");
    int selectedStoreId = (Integer) session.getAttribute("selectedStoreId");
    String selectedStoreName = (String) session.getAttribute("selectedStoreName");
    if (null == selectedStoreName) {
        selectedStoreName = "Select Store";
    }
%>
<style>
  
            @media screen and (max-width: 768px) {
  .content {
    margin-top: 90px!important;
  }
  .menu-sec{

     display: none;
  }
  @media (max-width: 991.98px) {
    .header .header-left {
        /* position: absolute; */
        /* width: 100%; */
    }
}
/*  .menu-sec2{
      margin-right: 42px!important
  }*/
 .main-sec3 {
         /*display: none;*/
                 position: absolute;

    }
 
.user-menu {
  display: flex;
  justify-content: center;
  left: -3rem;
   /*display: flex;*/
  /*justify-content: center;*/
  /*position: relative;*/
  /*top:-3rem;*/
  z-index: 99;
}

</style>
<c:if test="${autoLogin == null}">
    <!-- Header -->
    <div class="header">
        <!-- Logo -->
        <div class="header-left active">
            <a href="${pageContext.request.contextPath}/dashboard" class="logo logo-normal ">
                <img src="${pageContext.request.contextPath}/image/av-meditech-logo.png" alt="" style="width: 220px; height: auto;" class="menu-sec">
            </a>
            <a href="index.html" class="logo logo-white">
                <img src="${pageContext.request.contextPath}/image/logo-white.png" alt="">
            </a>
            <a href="index.html" class="logo logo-small">
                <img src="${pageContext.request.contextPath}/image/logo-small.png" alt="">
            </a>
            <a id="toggle_btn" href="javascript:void(0);">
                <i data-feather="chevrons-left" class="feather-16"></i>
            </a>
        </div>
        <!-- /Logo -->

        <a id="mobile_btn" class="mobile_btn" href="#sidebar">
            <span class="bar-icon">
                <span></span>
                <span></span>
                <span></span>
            </span>
        </a>

        <!-- Header Menu -->
        <ul class="nav menu-section  user-menu">
            <!-- Search -->
            <li class="nav-item nav-searchinputs">
                <div class="top-nav-search">
                    <a href="javascript:void(0);" class="responsive-search">
                        <i class="fa fa-search"></i>
                    </a>
                </div>
            </li>
            <!-- /Search -->

            <li class="nav-item dropdown has-arrow main-drop select-store-dropdown ">
                <a href="javascript:void(0);" class="dropdown-toggle nav-link select-store menu-sec2" data-bs-toggle="dropdown">
                    <span class="user-info" id="selected-store">
                        <span class="user-letter">
                            <img src="${pageContext.request.contextPath}/image/store/store-01.png" alt="Store Logo" class="img-fluid" id="selected-store-logo">
                        </span>
                        <span class="user-name" id="selected-store-name">
                            <%=selectedStoreName%>
                        </span>
                    </span>
                </a>
                <div class="dropdown-menu dropdown-menu-right menu-sec3">
                    <%
                        int channelId = 0;
                        String channelName = "Select Store";
                        try {
                            if (channelList != null && !channelList.isEmpty()) {
                                for (int i = 0; i < channelList.size(); i++) {
                                    Map<String, Object> channel = channelList.get(i);
                                    channelId = (int) channel.get("id");
                                    channelName = (String) channel.get("name");
                    %>
                    <a href="javascript:void(0);" class="dropdown-item store-option" data-store-id="<%=channelId%>" data-store-name="<%=channelName%>">
                        <img src="${pageContext.request.contextPath}/image/store/store-01.png" alt="Store Logo" class="img-fluid">
                        <%=channelName%>
                    </a>
                    <%
                                }
                            }
                        } catch (Exception e) {
                        }
                    %>
                </div>
            </li>
            <!-- Select Store -->

            <li class="nav-item dropdown has-arrow main-drop">
                <a href="javascript:void(0);" class="dropdown-toggle nav-link userset" data-bs-toggle="dropdown">
                    <span class="user-info main-sec3">
                        <span class="user-letter">
                            <img src="${pageContext.request.contextPath}/image/av-meditech-logo.png" alt="" class="img-fluid">
                        </span>
                        <span class="user-detail">
                            <span class="user-name"><%= session.getAttribute("userName")%></span>
                            <span class="user-role">Super Admin</span>
                        </span>
                    </span>
                </a>
                <div class="dropdown-menu menu-drop-user">
                    <div class="profilename">
                        <div class="profileset">
                            <span class="user-img"><img src="${pageContext.request.contextPath}/image/av-meditech-logo.png" alt="">
                                <span class="status online"></span></span>
                            <div class="profilesets">
                                <h6><%= session.getAttribute("userName")%></h6>
                                <h5>Super Admin</h5>
                            </div>
                        </div>
                        <hr class="m-0">
                        <a class="dropdown-item logout pb-0" href="${pageContext.request.contextPath}/userLogout"><img src="${pageContext.request.contextPath}/image/icons/log-out.svg" class="me-2" alt="img">Logout</a>
                        <a class="dropdown-item logout pb-0" href="${pageContext.request.contextPath}/userChangePassword"><img src="${pageContext.request.contextPath}/image/icons/log-out.svg" class="me-2" alt="img">Change Password</a>
                    </div>
                </div>
            </li>
        </ul>
        <!-- /Header Menu -->
    </div>
    <!-- /Header -->
</c:if>

<!-- Check if autoLogin is "1" -->
<c:if test="${autoLogin != null}">
    <!-- Additional content for autoLogin -->
</c:if>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function () {
        // Function to fetch the selected store from local storage
        function getSelectedStoreFromLocalStorage() {
            return localStorage.getItem('selectedStore');
        }

        // Function to update the selected store name in the UI
        function updateSelectedStoreUI(storeName) {
            if (storeName) {
                $('#selected-store-name').text(storeName);
            }
        }

        // Initial page load: Update selected store name from session or local storage
        let selectedStoreName = '<%= selectedStoreName%>';
        if (!selectedStoreName) {
            selectedStoreName = getSelectedStoreFromLocalStorage();
        }
        updateSelectedStoreUI(selectedStoreName);

        // Handle store option click
        $('.store-option').on('click', function () {
            const storeId = $(this).data('store-id');
            const storeName = $(this).data('store-name');

            // Update the selected store name in the UI
            updateSelectedStoreUI(storeName);

            // Update session via AJAX
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/updateStoreSelection",
                data: {
                    storeId: storeId,
                    storeName: storeName
                },
                success: function (response) {
                    console.log('Session updated with selected store ID:', storeId);
                    // Store the selected store in local storage
                    localStorage.setItem('selectedStore', storeName);
                    location.reload();
                },
                error: function (xhr, status, error) {
                    console.error('Failed to update session:', xhr.responseText);
                }
            });
        });
    });
</script>
