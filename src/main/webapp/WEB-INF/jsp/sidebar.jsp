<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String autoLogin = (String) session.getAttribute("autoLogin");
%>



<script>
    // Print the autoLogin value to the browser console
    console.log("autoLogin: <%= autoLogin%>");

    // Listen for keydown events to detect specific key combinations
    document.addEventListener('keydown', function (event) {
        // Check for Alt + S
        if (event.altKey && event.key === 'a') {
            event.preventDefault(); // Prevent the default browser behavior (e.g., saving the page)
            window.location.href = '<%= request.getContextPath()%>/saleCreate'; // Redirect to saleCreate URL
        }

        // Check for Alt + P
        if (event.altKey && event.key === 'p') {
            event.preventDefault();
            window.location.href = '<%= request.getContextPath()%>/purchaseProduct';
        }

        // Check for Alt + R
        if (event.altKey && event.key === 'r') {
            event.preventDefault();
            window.location.href = '<%= request.getContextPath()%>/saleReturn';
        }

        // add
        // Check for Alt + T
        else if (event.altKey && event.key === 't') {
            event.preventDefault();
            window.location.href = '<%= request.getContextPath()%>/stockReportProductWise';
        }
        // Check for Alt + U
        else if (event.altKey && event.key === 'u') {
            event.preventDefault();
            window.location.href = '<%= request.getContextPath()%>/stockReportSerialNumberWise';
        }
        // Check for Alt + V
        else if (event.altKey && event.key === 'v') {
            event.preventDefault();
            window.location.href = '<%= request.getContextPath()%>/inventoryTransactionReport';
        }
        // Check for Alt + I
        else if (event.altKey && event.key === 'i') {
            event.preventDefault();
            window.location.href = '<%= request.getContextPath()%>/itemMovementReport';
        }
        // Check for Alt + X
        else if (event.altKey && event.key === 'x') {
            event.preventDefault();
            window.location.href = '<%= request.getContextPath()%>/partyReport';
        }
        // Check for Alt + Q
        else if (event.altKey && event.key === 'q') {
            event.preventDefault();
            window.location.href = '<%= request.getContextPath()%>/quickSearch';
        }
        // Check for Alt + D
        else if (event.altKey && event.key === 'd') {
            event.preventDefault();
            window.location.href = '<%= request.getContextPath()%>/dayBookWithItems';
        }


    });
</script>



<script>
    // Function to check if we are on a specific URL
    function isOnSaleCreate() {
        var currentUrl = window.location.href;

        // Define URL segments to check
        var saleListUrl = 'saleList';
        var saleCreateUrl = 'saleCreate';
        var saleReturnListUrl = 'saleReturnList'; // Define saleReturnListUrl

        // Check if current URL contains one of the required URL parts
        // Ensure that saleReturnList is NOT matched
        if ((currentUrl.includes(saleCreateUrl) || currentUrl.includes(saleListUrl)) &&
                !currentUrl.includes(saleReturnListUrl)) {
            return true;
        } else {
            return false;
        }
    }



    // To log the current URL
    console.log(window.location.href);

    // Example of how to use the function
    console.log(isOnSaleCreate());


    // Function to open the productList link or serviceProductList based on the URL condition
    function openUrlIfOnCheckUrl(name) {
        console.log('name' + name);
        var baseUrl = '${pageContext.request.contextPath}';

        if (isOnSaleCreate()) {

            console.log('isOnSaleCreate===' + isOnSaleCreate);

            if (name === 'productList') {
                window.location.href = baseUrl + '/productList';  // Redirect to productList
            } else if (name === 'serviceProductList') {
                window.location.href = baseUrl + '/serviceProductList';
            } else if (name === 'productTypeList') {
                window.location.href = baseUrl + '/productTypeList';
            } else if (name === 'categoryList') {
                window.location.href = baseUrl + '/categoryList';  // Redirect to categoryList
            } else if (name === 'saleList') {
                //window.open(baseUrl + '/saleList', '_blank');  // Open saleList in a new tab
                window.location.href = baseUrl + '/saleList';  // Redirect to saleList
            } else if (name === 'returnList') {
                window.open(baseUrl + '/saleReturnList', '_blank');  // Open saleReturnList in a new tab
            } else if (name === 'purchaseList') {
                window.location.href = baseUrl + '/purchaseList';  // Redirect to purchaseList
            } else if (name === 'customerList') {
                window.location.href = baseUrl + '/customerList';  // Redirect to customerList
            } else if (name === 'supplierList') {
                window.location.href = baseUrl + '/supplierList';  // Redirect to supplierList
            } else if (name === 'stockReportProductWise') {
                window.open(baseUrl + '/stockReportProductWise', '_blank');
            } else if (name === 'stockReportSerialNumberWise') {
                window.open(baseUrl + '/stockReportSerialNumberWise', '_blank');
            } else if (name === 'inventoryTransactionReport') {
                window.location.href = baseUrl + '/inventoryTransactionReport';  // Redirect to inventoryTransactionReport
            } else if (name === 'itemMovementReport') {
                window.location.href = baseUrl + '/itemMovementReport';  // Redirect to itemMovementReport
            } else if (name === 'partyReport') {
                window.location.href = baseUrl + '/partyReport';  // Redirect to partyReport
            } else if (name === 'quickSearch') {
                window.location.href = baseUrl + '/quickSearch';  // Redirect to quickSearch
            } else if (name === 'dayBookWithItems') {
                window.location.href = baseUrl + '/dayBookWithItems';  // Redirect to dayBookWithItems
            } else if (name === 'staffList') {
                window.location.href = baseUrl + '/staffList';  // Redirect to staffList
            } else if (name === 'batchWiseReport') {
                window.location.href = baseUrl + '/batchWiseReport';  // Redirect to batchWiseReport 
            }
            else if (name === 'combinedStockReport') {
                window.location.href = baseUrl + '/combinedStockReport';  // Redirect to combinedStockReport
            }
        } else {
            if (name === 'productList') {
                window.location.href = baseUrl + '/productList';  // Redirect to productList
            } else if (name === 'serviceProductList') {
                window.location.href = baseUrl + '/serviceProductList';  // Redirect to serviceProductList
            } else if (name === 'productTypeList') {
                window.location.href = baseUrl + '/productTypeList';  // Redirect to productTypeList
            } else if (name === 'categoryList') {
                window.location.href = baseUrl + '/categoryList';  // Redirect to categoryList
            } else if (name === 'saleList') {
                window.location.href = baseUrl + '/saleList';  // Redirect to saleList
            } else if (name === 'returnList') {
                window.location.href = baseUrl + '/saleReturnList';  // Redirect to saleReturnList
            } else if (name === 'purchaseList') {
                window.location.href = baseUrl + '/purchaseList';  // Redirect to purchaseList
            } else if (name === 'customerList') {
                window.location.href = baseUrl + '/customerList';  // Redirect to customerList
            } else if (name === 'supplierList') {
                window.location.href = baseUrl + '/supplierList';  // Redirect to supplierList
            } else if (name === 'stockReportProductWise') {
                window.location.href = baseUrl + '/stockReportProductWise';  // Redirect to stockReportProductWise
            } else if (name === 'stockReportSerialNumberWise') {
                window.location.href = baseUrl + '/stockReportSerialNumberWise';  // Redirect to stockReportSerialNumberWise
            } else if (name === 'inventoryTransactionReport') {
                window.location.href = baseUrl + '/inventoryTransactionReport';  // Redirect to inventoryTransactionReport
            } else if (name === 'itemMovementReport') {
                window.location.href = baseUrl + '/itemMovementReport';  // Redirect to itemMovementReport
            } else if (name === 'partyReport') {
                window.location.href = baseUrl + '/partyReport';  // Redirect to partyReport
            } else if (name === 'quickSearch') {
                window.location.href = baseUrl + '/quickSearch';  // Redirect to quickSearch
            } else if (name === 'dayBookWithItems') {
                window.location.href = baseUrl + '/dayBookWithItems';  // Redirect to dayBookWithItems
            } else if (name === 'staffList') {
                window.location.href = baseUrl + '/staffList';  // Redirect to staffList
            } else if (name === 'batchWiseReport') {
                window.location.href = baseUrl + '/batchWiseReport';  // Redirect to batchWiseReport
            } 
            else if (name === 'combinedStockReport') {
                window.location.href = baseUrl + '/combinedStockReport';  // Redirect to batchWiseReport
            } 
        }
    }
</script>


















<!-- Sidebar Start -->
<div class="sidebar" id="sidebar">
    <div class="sidebar-inner slimscroll">
        <div id="sidebar-menu" class="sidebar-menu">
            <ul class="main-menu">

                <!-- Check if autoLogin is not null -->
                <c:if test="${autoLogin == null}">

                    <!-- MASTERS Section -->
                    <c:forEach var="permissionGroup" items="${sessionScope.permissionGroups}">
                        <c:choose>
                            <c:when test="${permissionGroup.permissionGroupsName == 'Manage All' || permissionGroup.permissionGroupsName == 'Manage Products'}">
                                <li class="submenu-open">
                                    <span>MASTERS</span>
                                    <ul>
                                        <li>
                                            <a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('productList')">
                                                <i data-feather="box"></i><span>Products</span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('serviceProductList')"> 
                                                <i data-feather="box"></i><span>Product Service</span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('productTypeList')"> 
                                                <i data-feather="box"></i><span>Brand</span></a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('categoryList')"> 
                                                <i data-feather="box"></i><span>Product Type</span></a>
                                        </li>
                                    </ul>
                                </li>
                            </c:when>
                        </c:choose>
                    </c:forEach>

                    <!-- TRANSACTIONS Section -->
                    <c:set var="showSales" value="false" />
                    <c:set var="showPurchases" value="false" />
                    <c:set var="showCustomers" value="false" />
                    <c:set var="showSuppliers" value="false" />

                    <c:forEach var="permissionGroup" items="${sessionScope.permissionGroups}">
                        <c:choose>
                            <c:when test="${permissionGroup.permissionGroupsName == 'Manage All'}">
                                <c:set var="showSales" value="true" />
                                <c:set var="showPurchases" value="true" />
                                <c:set var="showCustomers" value="true" />
                                <c:set var="showSuppliers" value="true" />
                            </c:when>
                            <c:when test="${permissionGroup.permissionGroupsName == 'Manage Purchase'}">
                                <c:set var="showPurchases" value="true" />
                            </c:when>
                            <c:when test="${permissionGroup.permissionGroupsName == 'Manage Sales'}">
                                <c:set var="showSales" value="true" />
                            </c:when>
                            <c:when test="${permissionGroup.permissionGroupsName == 'Manage Customer & Supplier'}">
                                <c:set var="showCustomers" value="true" />
                                <c:set var="showSuppliers" value="true" />
                            </c:when>
                        </c:choose>
                    </c:forEach>

                    <c:if test="${showSales || showPurchases || showCustomers || showSuppliers}">
                        <li class="submenu-open">
                            <span>TRANSACTIONS</span>
                            <ul>
                                <c:if test="${showSales}">
                                    <li>
                                        <a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('saleList')"> 
                                            <i data-feather="box"></i><span>Sales</span></a> 
                                    </li>
                                    <li>
                                        <a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('returnList')">
                                            <i data-feather="box"></i><span>Sale Return</span></a>
                                    </li>
                                </c:if>
                                <c:if test="${showPurchases}"> 
                                    <li><a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('purchaseList')"><i data-feather="shopping-bag"></i><span>Purchases</span></a></li>
                                            </c:if>
                                            <c:if test="${showCustomers}"> 
                                    <li><a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('customerList')"><i data-feather="user"></i><span>Customers</span></a></li>
                                            </c:if>
                                            <c:if test="${showSuppliers}"> 
                                    <li><a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('supplierList')"><i data-feather="users"></i><span>Suppliers</span></a></li>
                                            </c:if>
                            </ul>
                        </li>
                    </c:if>

                    <!-- REPORT Section -->
                    <c:forEach var="permissionGroup" items="${sessionScope.permissionGroups}">
                        <c:choose>
                            <c:when test="${permissionGroup.permissionGroupsName == 'Manage All' || permissionGroup.permissionGroupsName == 'Manage Reports'}">
                                <li class="submenu-open"> 
                                    <span>REPORT</span> 
                                    <ul>
<!--                                        <li><a href="${pageContext.request.contextPath}/monthlyTallyReportList"><i data-feather="file-text"></i><span>Monthly Tally Report</span></a></li>-->
                                        <li><a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('stockReportProductWise')"><i data-feather="file-text"></i><span>Stock Report Product Wise</span></a></li>
                                        <li><a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('stockReportSerialNumberWise')"><i data-feather="file-text"></i><span>Stock Report Serial No Wise</span></a></li>
                                        <li><a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('inventoryTransactionReport')"><i data-feather="file-text"></i><span>Inventory Transaction Report</span></a></li>
<!--                                        <li><a href="${pageContext.request.contextPath}/inventoryTransactionBrandReport"><i data-feather="file-text"></i><span>Inventory Transaction Brand Report</span></a></li>-->
                                        <li><a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('itemMovementReport')"><i data-feather="file-text"></i><span>Item Movement Report</span></a></li>
                                        <li><a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('partyReport')"><i data-feather="file-text"></i><span>Party Report</span></a></li>
                                        <li><a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('quickSearch')"><i data-feather="file-text"></i><span>Quick Search </span></a></li>
                                        <li><a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('dayBookWithItems')"><i data-feather="file-text"></i><span>Day Book With Items</span></a></li>
                                        <li><a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('batchWiseReport')"><i data-feather="file-text"></i><span>Batch Wise Report</span></a></li>
                                        <c:choose>
                                            <c:when test="${channelList.size() == 4}">
                                                <li><a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('combinedStockReport')"><i data-feather="file-text"></i><span>Combined Stock Report</span></a></li>
                                            </c:when>
                                        </c:choose>
                                    </ul> 
                                </li>
                            </c:when>
                        </c:choose>
                    </c:forEach>

                    <!-- SETTING Section -->
                    <c:forEach var="permissionGroup" items="${sessionScope.permissionGroups}">
                        <c:choose>
                            <c:when test="${permissionGroup.permissionGroupsName == 'Manage All' || permissionGroup.permissionGroupsName == 'Manage Staff'}">
                                <li class="submenu-open">
                                    <span>SETTING</span> 
                                    <ul>
                                        <li><a href="javascript:void(0);" onclick="openUrlIfOnCheckUrl('staffList')"><i data-feather="users"></i><span>Staff Members</span></a></li>
                                    </ul>
                                </li>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                </c:if>

                <!-- Check if autoLogin is "1" -->
                <c:if test="${autoLogin != null}">
                    <!-- Additional content for autoLogin -->
                </c:if>
            </ul>
        </div>
    </div>
</div>
