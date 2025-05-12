<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.AvMeditechInventory.dtos.ProductDto"%>
<%@page import="com.AvMeditechInventory.dtos.CustomerAndSupplierDto"%>
<%@page import="java.util.List"%>
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

        <style>
            #supplierList {
                max-height: 200px;
                overflow-y: auto;
            }
            .supplier-item {
                padding: 8px;
                cursor: pointer;
            }
            .supplier-item:hover, .supplier-item.active {
                background-color: #ddd;
            }
            .supplier-item:hover {
                background-color: #ddd;
            }
            .total-order ul {
                padding: 0;
                list-style: none;
                margin: 0;
            }
            .total-order li {
                border-bottom: 1px solid #ddd;
                padding-bottom: 10px;
                margin-bottom: 10px;
            }
            .total-order li:last-child {
                border-bottom: none;
                padding-bottom: 0;
                margin-bottom: 0;
            }
            .total-order h4, .total-order h5 {
                margin: 0;
            }
            .input-group {
                max-width: 100%;
            }
            .supplier-item {
                padding: 8px;
                cursor: pointer;
                border: 1px solid #ddd;
                background-color: #fff;
            }

            .supplier-item:hover {
                background-color: #f0f0f0;
            }

            .supplier-item:not(:last-child) {
                border-bottom: 1px solid #ddd;
            }

            #supplierList {
                border: 1px solid #ddd;
                max-height: 200px;
                overflow-y: auto;
                background-color: #fff;
                position: absolute;
                z-index: 1000;
                width: calc(100% - 2px); /* Full width of the input minus borders */
            }
            .suggestions-box {
                background: white;
                padding: 0px 16px;
                box-shadow: 0px 0px 2px lightgray;
                max-height: 150px;
                overflow-y: auto;
                position: absolute;
                z-index: 1000;
                width: 100%;
            }

            .suggestion-item {
                padding: 10px;
                cursor: pointer;
            }

            .suggestion-item:hover, .selected {
                background-color: #ddd;
                color: white;
            }

            .input-groupicon {
                position: relative;
            }

            .addonset {
                position: absolute;
                right: 10px;
                top: 50%;
                transform: translateY(-50%);
            }

            .input-groupicon input {
                width: 100%;
                padding-right: 40px;
            }
            .table-container {
                max-height:400px; /* Adjust the height as needed */
                overflow-y: auto;
                border: 1px solid #ddd;
            }
            .loader {
                display: none;
                border: 4px solid #F3F3F3;
                border-top: 4px solid #3498DB;
                border-radius: 50%;
                width: 30px;
                height: 30px;
                animation: spin 1s linear infinite;
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
            }
            @keyframes spin {
                0% { transform: rotate(0deg); }
                100% { transform: rotate(360deg); }
            }
            /* Disable button when loading */
            .disabled {
                display: none;
                pointer-events: none;
            }

        </style>

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
                <div class="page-wrapper ">
                    <div class="content">
                        <div class="page-header ">

                            <div class="add-item d-flex ">
                                <div class="page-title">
                                    <h4>New Purchase</h4>
                                    <h6>Add new purchase</h6>
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
                                <a data-bs-toggle="tooltip" data-bs-placement="top" title="Collapse" id="collapse-header"><i data-feather="chevron-up" class="feather-chevron-up"></i></a>
                            </li>
                            <li>
                                <div class="page-btn">
                                    <a href="${pageContext.request.contextPath}/purchaseList" class="btn btn-secondary"><i data-feather="arrow-left" class="me-2"></i>Back to Purchase</a>
                                </div>

                            </li>

                        </ul>
                        <div class="page-btn import">
                            <a href="#" class="btn btn-added color" data-bs-toggle="modal" data-bs-target="#view-notes">
                                <i data-feather="download" class="me-2"></i>Import Purchase
                            </a>
                        </div>
                        <div class="page-btn import">
                            <a href="${pageContext.request.contextPath}/image/icons/sample.csv" target="_blank">
                                <i data-feather="download" class="me-2"></i>Download
                            </a>
                        </div>

                    </div>


                    <!-- /add -->
                    <form autocomplete="off" name="purchaseForm" id="fileUploadForm" enctype="multipart/form-data" action="" method="post">
                        <div class="row">
                            <div class="col-lg-4 col-sm-6 col-12">
                                <div class="input-blocks">
                                    <label>Supplier Name</label>
                                    <div class="row">
                                        <div class="col-lg-10 col-sm-10 col-10">
                                            <input type="text" class="form-control" id="supplierSearch" placeholder="Search Supplier" onkeyup="handleSupplierKeyEvents(event)" onfocus="showSupplierList()" autocomplete="off">
                                            <input type="hidden" name="userId" id="hiddenUserId"> <!-- Hidden input field for supplier ID -->
                                            <!--<input type="hidden" name="purSeqId" value="${purSeqId}">-->
                                            <div id="supplierList" style="display: none; border: 1px solid #ccc; position: absolute; background-color: white; z-index: 1000; max-height: 200px; overflow-y: auto;">
                                                <% List<CustomerAndSupplierDto> supplierList = (List<CustomerAndSupplierDto>) request.getAttribute("userDataList");
                                                    if (supplierList != null && !supplierList.isEmpty()) {
                                                        for (CustomerAndSupplierDto supplier : supplierList) {
                                                            String supplierId = supplier.getId().replaceAll("\\s", ""); // Sanitize ID
                                                %>
                                                <div class="supplier-item" onclick="selectSupplier('<%= supplier.getCompanyName()%>', '<%= supplierId%>')" data-id="<%= supplierId%>">
                                                    <%= supplier.getCompanyName()%> (Code: <%= supplier.getUserCode()%>)
                                                </div>
                                                <%
                                                        }
                                                    }
                                                %>
                                            </div>
                                        </div>
                                        <div class="col-lg-2 col-sm-2 col-2 ps-0">
                                            <div class="add-icon">
                                                <a href="${pageContext.request.contextPath}/addUser" class="choose-add"><i data-feather="plus-circle" class="plus"></i></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>

                            <!-- Date  -->
                            <div class="col-lg-4 col-sm-6 col-12">
                                <div class="mb-3 add-product">
                                    <label class="form-label">Date</label>
                                    <input type="date" class="form-control" id="dateInput" name="dateOfBirth" onchange="displayFormattedDate()" required >
                                </div>
                            </div>

                            <input type="hidden" id="formattedDateOutput" name="formattedDate" value="">


                            <div class="col-lg-4 col-sm-6 col-12">
                                <div class="input-blocks">
                                    <label>Reference No</label>
                                    <div class="input-groupicon calender-input">
                                        <!--<i data-feather="calendar" class="info-img"></i>-->
                                        <input type="text" name="referenceNo">
                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-4 col-sm-6 col-12">
                                <div class="input-blocks">
                                    <label>Remarks</label>
                                    <div class="input-groupicon calender-input">
                                        <!--<i data-feather="calendar" class="info-img"></i>-->
                                        <input type="text" name="remarks">
                                    </div>
                                </div>
                            </div>

                            <div class="col-lg-12 col-sm-6 col-12">
                                <div class="input-blocks">
                                    <label for="productName" class="form-label">Product Name</label>

                                    <div class="input-groupicon select-code">
                                        <input type="text" id="myInput" style="padding-left: 16px"  oninput="showSuggestions()" onkeydown="handleKeyDown(event)">
                                        <!--onkeyup="showSuggestions()--> 
                                        <input type="hidden" name="purchaseData" id="purchaseData" />
                                        <input type="hidden" name="purchaseSerialData" id="purchaseSerialData" />
                                        <div class="addonset">
                                            <img src="${pageContext.request.contextPath}/image/icons/qrcode-scan.svg" alt="img">
                                        </div>
                                    </div>

                                    <div id="suggestions" class="suggestions-box" style="background: white;
                                         padding: 0px 16px;box-shadow: 0px 0px 2px lightgray;">

                                    </div>

                                    <div class="page-btn">
                                        <a onclick="productCreate()" class="btn btn-added"><i data-feather="plus-circle" class="me-2"></i>Add New Product</a>
                                    </div>
                                </div>
                                <script>
                                    let selectedIndex = -1;
                                    function showSuggestions() {
                                        const input = document.getElementById('myInput');
                                        const suggestionBox = document.getElementById('suggestions');
                                        const filter = input.value.toLowerCase();
                                        let suggestionsHTML = '';
                                        // Filter and display suggestions
                                        const filteredSuggestions = suggestions.filter(s => s.toLowerCase().includes(filter));
                                        filteredSuggestions.forEach((suggestion, index) => {
                                            suggestionsHTML += `<div class="suggestion-item" id="suggestion-${index}" onclick="selectSuggestion(${index})">${suggestion}</div>`;
                                        });
                                        suggestionBox.innerHTML = suggestionsHTML;
                                        selectedIndex = -1; // Reset the selection when new suggestions are shown
                                    }

                                    function handleKeyDown(event) {
                                        const suggestionItems = document.querySelectorAll('.suggestion-item');
                                        if (event.key === 'ArrowDown') {
                                            if (suggestionItems.length > 0) {
                                                selectedIndex = (selectedIndex + 1) % suggestionItems.length;
                                                highlightSuggestion(suggestionItems, selectedIndex);
                                                event.preventDefault();
                                            }
                                        } else if (event.key === 'ArrowUp') {
                                            if (suggestionItems.length > 0) {
                                                selectedIndex = (selectedIndex - 1 + suggestionItems.length) % suggestionItems.length;
                                                highlightSuggestion(suggestionItems, selectedIndex);
                                                event.preventDefault();
                                            }
                                        } else if (event.key === 'Enter' && selectedIndex >= 0) {
                                            console.log("Enter key pressed!");
                                            addNewRow(selectedIndex, suggestionDataReplica[selectedIndex]);
                                            //selectSuggestion(selectedIndex);
                                            const input = document.getElementById('myInput');
                                            const suggestionItems = document.querySelectorAll('.suggestion-item');
                                            input.value = suggestionItems[index].textContent;
                                            document.getElementById('suggestions').innerHTML = '';
                                            event.preventDefault();
                                        }
                                    }

                                    function highlightSuggestion(suggestionItems) {
                                        // Remove previous selection
                                        suggestionItems.forEach(item => item.classList.remove('selected'));
                                        // Highlight the current selection
                                        if (selectedIndex >= 0 && selectedIndex < suggestionItems.length) {
                                            suggestionItems[selectedIndex].classList.add('selected');
                                            suggestionItems[selectedIndex].scrollIntoView({behavior: 'smooth', block: 'nearest'});
                                        }
                                    }

                                    function selectSuggestion(index) {
                                        const input = document.getElementById('myInput');
                                        const suggestionItems = document.querySelectorAll('.suggestion-item');
                                        input.value = suggestionItems[index].textContent;
                                        document.getElementById('suggestions').innerHTML = ''; // Clear suggestions after selection
                                        selectedIndex = -1; // Reset the index
                                    }

                                    function productCreate() {
                                        alert('Create new product logic here.');
                                    }
                                </script>
                            </div>
                        </div>
                        <div class="col-lg-12">
                            <div class="alert alert-danger" id="errMessage" style="display: none" role="alert">

                            </div>
                            <div class="modal-body-table table-container">
                                <div class="table-responsive">
                                    <table class="table  datanew">
                                        <thead>
                                            <tr>
                                                <th>Product Name</th>
                                                <th>Quantity</th>
                                                <th>Expiry Date</th>
                                                <th>Batch Number</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>

                                        <tbody class="">
                                            <tr>
                                                <td class="p-3 one"></td>
                                                <td class="p-3 one"></td>
                                                <td class="p-3 one"></td>
                                                <td class="p-3 one"></td>
                                                <td class="p-3 one"></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>

                        </div>

                        <div class="row">
                            <div class="col-12">
                                <div class="total-order p-3 border rounded mb-4">
                                    <ul class="list-unstyled">
                                        <li class="d-flex justify-content-between align-items-center mb-3">
                                            <h4 class="mb-0">Quantity</h4>
                                            <h5 class="mb-0"><span id="totalQuantity">0</span></h5>
                                        </li>
                                        <!--                                        <li class="d-flex justify-content-between align-items-center mb-3">
                                                                                    <h4 class="mb-0">Order Tax (GST)</h4>
                                                                                    <h5 class="mb-0">Rs <span id="purchaseTax">0.00</span></h5>
                                                                                </li>
                                                                                <li class="d-flex justify-content-between align-items-center mb-3">
                                                                                    <h4 class="mb-0">Discount</h4>
                                                                                    <h5 class="mb-0">Rs <span id="purchaseDiscount">0.00</span></h5>
                                                                                </li>
                                                                                <li class="d-flex justify-content-between align-items-center mb-3">
                                                                                    <h4 class="mb-0">Shipping</h4>
                                                                                    <div class="input-group" style="max-width: 200px;">
                                                                                        <span class="input-group-text">Rs</span>
                                                                                        <input id="purchaseShipping" onchange="addShipping()" value="0" type="number" class="form-control text-end" name="shippingAmount" placeholder="0.00">
                                                                                    </div>
                                                                                </li>
                                                                                <li class="d-flex justify-content-between align-items-center mb-3">
                                                                                    <h4 class="mb-0">Grand Total</h4>
                                                                                    <h5 class="mb-0">Rs <span id="purchaseTotal">0.00</span></h5>
                                                                                </li>-->
                                    </ul>
                                </div>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="loader" id="loader"></div>
                            <div class="col-12 text-end" style="margin-bottom:30px">
                                <!--<button type="button" class="btn btn-secondary me-3" data-bs-dismiss="modal">Cancel</button>-->
                                <button type="button" id="sbmtBtn" onclick="addPurchaseData()" class="btn btn-primary">Submit</button>
                            </div>
                        </div>                

                        <!--add popup -->
                        <div class="modal fade" id="add-sales-new">
                            <div class="modal-dialog add-centered">
                                <div class="modal-content">
                                    <div class="page-wrapper p-0 m-0">
                                        <div class="content p-0">
                                            <div class="modal-header border-0 custom-modal-header">
                                                <div class="page-title">
                                                    <h4 id="serialPattern"> Pattern: </h4>
                                                </div>
                                                <button onclick="deleteSerialAllData()" type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="card">
                                                <div class="card-body">
                                                    <form action="" method="post">
                                                        <div class="row">

                                                            <div class="col-lg-12 col-sm-6 col-12">
                                                                <div class="input-blocks">
                                                                    <label for="productSerialNumber">Product Serial Number</label>
                                                                    <div class="input-groupicon select-code">
                                                                        <input type="hidden" id="productSerialPattern" />
                                                                        <input type="hidden" id="productSerialId" />
                                                                        <input type="hidden" id="productSerialUp" />
                                                                        <input type="hidden" id="productSerialGst" />
                                                                        <input type="text" style="padding-left: 15px;" id="productSerialNumber" placeholder="Enter Product Serial Number">
                                                                        <div class="addonset">
                                                                            <img id="submitBtn" src="${pageContext.request.contextPath}/image/icons/qrcode-scan.svg" alt="Scan QR Code">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-lg-12">
                                                            <div class="modal-body-table">
                                                                <div class="table-responsive">
                                                                    <table class="table  datanew1">
                                                                        <thead>
                                                                            <tr>

                                                                                <th>Serial Number</th>
                                                                                <th>Expiry Date</th>
                                                                                <!--<th>Action</th>-->
                                                                            </tr>

                                                                        </thead>
                                                                        <tbody id="myTableBody"></tbody> <!-- Rows will be dynamically added here -->
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="row">
                                                            <div class="col-lg-12 text-end">
                                                                <button type="button" onclick="deleteSerialAllData()" class="btn btn-cancel add-cancel me-3" data-bs-dismiss="modal">Ok</button>
                                                            </div>
                                                        </div>
                                                        <!--</form>-->
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- /add popup -->
                    </form>



                </div>

            </div>


            <!-- Import Purchase Modal -->
            <div class="modal fade" id="view-notes" tabindex="-1" aria-labelledby="importPurchaseLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="page-wrapper-new p-0">
                            <div class="content">
                                <div class="modal-header border-0 custom-modal-header">
                                    <div class="page-title">
                                        <h4 id="importPurchaseLabel">Import Purchase</h4>
                                    </div>
                                    <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body custom-modal-body">
                                    <form id="importForm" enctype="multipart/form-data" action="/your-endpoint" method="post">
                                        <div class="row">
                                            <div class="col-lg-12">
                                                <div class="input-blocks image-upload-down">
                                                    <label>Upload CSV File</label>
                                                    <div class="image-upload download">
                                                        <input type="file" name="file1" id="file1">
                                                        <div class="image-uploads">
                                                            <img src="${pageContext.request.contextPath}/image/download-img.png" alt="img">
                                                            <h4>Drag and drop a <span>file to upload</span></h4>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="modal-footer-btn" >
                                                <button type="button" class="btn btn-cancel me-2" data-bs-dismiss="modal">Cancel</button>
                                                <button type="button" class="btn btn-submit" onclick="getImportData(this);">Submit</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
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
<script>
                                                    document.addEventListener("DOMContentLoaded", function () {
                                                        var tableContainer = document.querySelector(".table-container");

                                                        if (tableContainer) {
                                                            tableContainer.scrollTop = tableContainer.scrollHeight;
                                                        }

                                                        // Scroll to bottom whenever new content is added
                                                        const observer = new MutationObserver(() => {
                                                            tableContainer.scrollTop = tableContainer.scrollHeight;
                                                        });

                                                        observer.observe(document.querySelector(".datanew tbody"), {
                                                            childList: true,
                                                            subtree: true,
                                                        });
                                                    });
</script>

<script>
    var purchaseDataArray = [];
    var purchaseDataSerialArray = [];
    var rowIndex = 0;
    var totalQuantity = 0;
    var suggestionDataReplica = [];
    function showSuggestions() {
        var input = document.getElementById("myInput");
        var filter = input.value.toLowerCase();
        var tableData = document.querySelectorAll(".one");
        var suggestionsDiv = document.getElementById("suggestions");
        suggestionsDiv.style.paddingBottom = "10px";
        var suggestionList = []; // Sample suggestion list, you can replace it with actual suggestions
        var suggestionList1 = [];
        $.ajax({
            type: "GET",
            url: "${pageContext.request.contextPath}/productList1/json",
            data: {
                productName: filter
            },
            success: function (response) {
                // Handle success response
                console.log("Response received:", response);
                for (var i = 0, max = response.length; i < max; i++) {
                    suggestionList.push(response[i].productName);
                    suggestionList1.push(response[i].productName + "|" + response[i].costPrice + "|" +
                            response[i].gst + "|" + response[i].snoPattern + "|" + response[i].productId +
                            "|" + response[i].trackingSerialNo + "|" + response[i].batchSerialNo);
                }
                suggestionsDiv.innerHTML = "";
                if (filter.length === 0) {
                    suggestionsDiv.style.display = "none";
                    return;
                }
                suggestionDataReplica = [...suggestionList1];
                suggestionList1.forEach(function (suggestion) {
                    if (suggestion.toLowerCase().indexOf(filter) > -1) {
                        var suggestionItem = document.createElement("div");
                        suggestionItem.className = "suggestion-item";
                        suggestionItem.style.cursor = "pointer";
                        suggestionItem.style.paddingTop = "5px";
                        suggestionItem.textContent = suggestion.split("|")[0];
                        suggestionItem.addEventListener("click", function () {
                            input.value = null;
                            //add table data in table
                            var newRow = '';
                            if (suggestion.split("|")[5] === 'true') {
                                newRow = '<tr id = "row' + rowIndex + '">' +
                                        '<td><a href="#" class="btn btn-added" onclick=showPopupData(\'' + suggestion.split("|")[3] + '\',\'' + suggestion.split("|")[4] + '\',\'' + suggestion.split("|")[1] + '\',\'' + rowIndex + '\') data-bs-toggle="modal" data-bs-target="#add-sales-new"><i data-feather="plus-circle" class="me-2"></i>' + suggestion.split("|")[0] + '</a></td>' +
                                        '<td><input style="width:100px;" id = "quantity' + rowIndex + '" onchange = "editDataRow(\'row' + rowIndex + '\',\'' + suggestion.split("|")[1] + '\',\'' + suggestion.split("|")[2] + '\')" type="number" value="' + 1 + '"/><input style="width:75px;" id = "batchExpiry' + rowIndex + '" type="hidden" value=""/><input style="width:75px;" id = "batchSerialNo' + rowIndex + '" type="hidden" value=""/></td>' +
                                        '<td></td>' + // Empty column 1
                                        '<td></td>' + // Empty column 2
                                        '<td class="action-table-data"><div class="edit-delete-action"><a class="confirm-text p-2" onclick = "deleteData(\'row' + rowIndex + '\',\'' + suggestion.split("|")[1] + '\',\'' + suggestion.split("|")[2] + '\')"><i data-feather="trash-2" class="feather-trash-2"></i></a></div></td>' +
                                        '</tr>';
                            } else {

                                if (suggestion.split("|")[6] === 'false') {
                                    newRow = '<tr id = "row' + rowIndex + '">' +
                                            '<td>' + suggestion.split("|")[0] + '</td>' +
                                            '<td><input style="width:100px;" id = "quantity' + rowIndex + '" onchange = "editDataRow(\'row' + rowIndex + '\',\'' + suggestion.split("|")[1] + '\',\'' + suggestion.split("|")[2] + '\')" type="number" value="' + 1 + '"/><input style="width:75px;" id = "batchExpiry' + rowIndex + '" type="hidden" value=""/><input style="width:75px;" id = "batchSerialNo' + rowIndex + '" type="hidden" value=""/></td>' +
                                            '<td></td>' + // Empty column 1
                                            '<td></td>' + // Empty column 2
                                            '<td class="action-table-data"><div class="edit-delete-action"><a class="confirm-text p-2" onclick = "deleteData(\'row' + rowIndex + '\',\'' + suggestion.split("|")[1] + '\',\'' + suggestion.split("|")[2] + '\')"><i data-feather="trash-2" class="feather-trash-2"></i></a></div></td>' +
                                            '</tr>';
                                } else {
                                    newRow = '<tr id="row' + rowIndex + '">' +
                                            '<td>' + suggestion.split("|")[0] + '</td>' +
                                            '<td>' +
                                            '<input style="width:100px;" id="quantity' + rowIndex + '" ' +
                                            'onchange="editDataRow(\'row' + rowIndex + '\',\'' + suggestion.split("|")[1] + '\',\'' + suggestion.split("|")[2] + '\')" ' +
                                            'type="number" value="1"/>' +
                                            '</td>' +
                                            '<td>' +
                                            '<input style="width:120px;" placeholder="Expiry Date" id="batchExpiry' + rowIndex + '" ' +
                                            'onchange="editDataRow(\'row' + rowIndex + '\',\'' + suggestion.split("|")[1] + '\',\'' + suggestion.split("|")[2] + '\')" ' +
                                            'type="date" value=""/>' +
                                            '</td>' +
                                            '<td>' +
                                            '<input style="width:200px;" placeholder="Batch Number" id="batchSerialNo' + rowIndex + '" ' +
                                            'onchange="editDataRow(\'row' + rowIndex + '\',\'' + suggestion.split("|")[1] + '\',\'' + suggestion.split("|")[2] + '\')" ' +
                                            'type="text" value=""/>' +
                                            '</td>' +
                                            '<td class="action-table-data">' +
                                            '<div class="edit-delete-action">' +
                                            '<a class="confirm-text p-2" ' +
                                            'onclick="deleteData(\'row' + rowIndex + '\',\'' + suggestion.split("|")[1] + '\',\'' + suggestion.split("|")[2] + '\')">' +
                                            '<i data-feather="trash-2" class="feather-trash-2"></i>' +
                                            '</a>' +
                                            '</div>' +
                                            '</td>' +
                                            '</tr>';
                                }



                            }
                            $('.datanew tbody').prepend(newRow);
                            suggestionsDiv.style.display = "none";
                            purchaseDataArray.push(suggestion.split("|")[4] + "|" + suggestion.split("|")[1] + "|1|0|0|0|0|" + rowIndex + "|" + suggestion.split("|")[5]);
                            document.getElementById("purchaseData").value = purchaseDataArray;
                            let qua = 0;
                            for (var i = 0; i < purchaseDataArray.length; i++) {
                                qua = qua + parseInt(purchaseDataArray[i].split("|")[2]);
                            }
                            document.getElementById("totalQuantity").innerHTML = qua;
                            rowIndex++;
                        });
                        suggestionsDiv.appendChild(suggestionItem);
                    }
                });
                suggestionsDiv.style.display = "block";
            },
            error: function (xhr, status, error) {
                // Handle error response
                console.error("Error: " + error);
                $('#barcodeDisplay').html("Error occurred while fetching data.");
            }
        });
    }
    document.addEventListener("click", function (event) {
        var suggestionsDiv = document.getElementById("suggestions");
        if (event.target !== suggestionsDiv && !suggestionsDiv.contains(event.target)) {
            suggestionsDiv.style.display = "none";
        }
    });
    function showPopupData(pattern, productId, unitPrice, gst) {
        for (var i = 0; i < purchaseDataSerialArray.length; i++) {
            var productId1 = purchaseDataSerialArray[i].split("|")[0];
            if (productId === productId1) {
                var newRow = '<tr id = "serial' + purchaseDataSerialArray[i].split("|")[2] + '">' +
                        '<td><label>' + purchaseDataSerialArray[i].split("|")[2] + '</label></td>' +
                        '<td><label>' + purchaseDataSerialArray[i].split("|")[3] + '</label></td>' +
//                                                                        '<td class="action-table-data"><div class="edit-delete-action"><a class="confirm-text p-2" onclick = "deleteSerialData(\'serial' + purchaseDataSerialArray[i].split("|")[2] + '\')"><i data-feather="trash-2" class="feather-trash-2"></i></a></div></td>' +
                        '</tr>';
                $('.datanew1 tbody').append(newRow);
            }
        }
        document.getElementById("serialPattern").innerHTML = "Pattern: " + pattern;
        document.getElementById("productSerialPattern").value = pattern;
        document.getElementById("productSerialId").value = productId;
        document.getElementById("productSerialUp").value = unitPrice;
        document.getElementById("productSerialGst").value = gst;
        let qua = 0;
        for (var i = 0; i < purchaseDataArray.length; i++) {
            qua = qua + parseInt(purchaseDataArray[i].split("|")[2]);
        }
        document.getElementById("totalQuantity").innerHTML = qua;
    }

    $(document).ready(function () {
        $('#productSerialNumber').keypress(function (event) {
            if (event.key === 'Enter') {
                event.preventDefault();
                calculateSerialNo();
            }
        });
        function calculateSerialNo() {
            var productSerialNumber = document.getElementById("productSerialNumber").value;
            var productSerialPattern = document.getElementById("productSerialPattern").value;
            var productSerialId = document.getElementById("productSerialId").value;
            var unitprice = document.getElementById("productSerialUp").value;
            var gst = document.getElementById("productSerialGst").value;
            document.getElementById("productSerialNumber").value = "";
            var suggestionsDiv = document.getElementById("suggestions");
            suggestionsDiv.style.paddingBottom = "10px";
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/calculateSerialNo",
                data: {
                    itemSerialNumber: productSerialNumber,
                    productSerialPattern: productSerialPattern
                },
                success: function (response) {
                    // Handle success response
                    console.log("Response received:", response);
                    var newRow = '<tr id = "serial' + response.serialNo + '">' +
                            '<td><label>' + response.serialNo + '</label></td>' +
                            '<td><label>' + response.expiryDate.substring(0, 10) + '</label></td>' +
//                                                                            '<td class="action-table-data"><div class="edit-delete-action"><a class="confirm-text p-2" onclick = "deleteSerialData(\'serial' + response.serialNo + '\')"><i data-feather="trash-2" class="feather-trash-2"></i></a></div></td>' +
                            '</tr>';
                    $('.datanew1 tbody').append(newRow);
                    purchaseDataSerialArray.push(productSerialId + "|" + productSerialNumber + "|" + response.serialNo + "|" + response.expiryDate.substring(0, 10) + "|" + productSerialPattern + "|" + gst);
                    document.getElementById("purchaseSerialData").value = purchaseDataSerialArray;
                    let count = 0;
                    for (var i = 0; i < purchaseDataSerialArray.length; i++) {
                        var productId = purchaseDataSerialArray[i].split("|")[0];
                        if (productId === productSerialId) {
                            count++;
                        }
                    }
                    document.getElementById("quantity" + gst).value = count;
                    let qua = 0;
                    for (var i = 0; i < purchaseDataArray.length; i++) {
                        qua = qua + parseInt(purchaseDataArray[i].split("|")[2]);
                    }
                    document.getElementById("totalQuantity").innerHTML = qua;
                    editDataRow('row' + gst, unitprice, gst);
                },
                error: function (xhr, status, error) {
                    // Handle error response
                    console.error("Error: " + error);
                    $('#barcodeDisplay').html("Error occurred while fetching data.");
                }
            });
        }
    });
    function deleteData(rowId, unitPrice, gst) {
        var row = document.getElementById(rowId);
        if (row) {
            row.parentNode.removeChild(row);
        }
        for (var i = 0; i < purchaseDataArray.length; i++) {
            var productId = purchaseDataArray[i].split("|")[7];
            if (productId === rowId.substring(3, rowId.length)) {
                purchaseDataArray.splice(i, 1);
                document.getElementById("purchaseData").value = purchaseDataArray;
            }
        }
        let qua = 0;
        for (var i = 0; i < purchaseDataArray.length; i++) {
            qua = qua + parseInt(purchaseDataArray[i].split("|")[2]);
        }
        document.getElementById("totalQuantity").innerHTML = qua;
    }

    function createRowWithEnterKeyPress(suggestion) {
        const input = document.getElementById('myInput');
        input.value = null;
        var newRow = '';
        var suggestionsDiv = document.getElementById("suggestions");

        const suggestionParts = suggestion.split("|");
        const productName = suggestionParts[0];
        const popupData1 = suggestionParts[1];
        const productCode = suggestionParts[2];
        const popupData3 = suggestionParts[3];
        const productId = suggestionParts[4];
        const hasPopup = suggestionParts[5];
        const hasBatchData = suggestionParts[6];

        if (suggestion.split("|")[5] === 'true') {
            newRow = '<tr id="row' + rowIndex + '">' +
                    '<td><a href="#" class="btn btn-added" onclick="showPopupData(\'' + suggestion.split("|")[3] + '\',\'' + suggestion.split("|")[4] + '\',\'' + suggestion.split("|")[1] + '\',\'' + rowIndex + '\')" data-bs-toggle="modal" data-bs-target="#add-sales-new"><i data-feather="plus-circle" class="me-2"></i>' + suggestion.split("|")[0] + '</a></td>' +
                    '<td><input style="width:100px;" id="quantity' + rowIndex + '" onchange="editDataRow(\'row' + rowIndex + '\',\'' + suggestion.split("|")[1] + '\',\'' + suggestion.split("|")[2] + '\')" type="number" value="1"/><input style="width:75px;" id="batchExpiry' + rowIndex + '" type="hidden" value=""/><input style="width:75px;" id="batchSerialNo' + rowIndex + '" type="hidden" value=""/></td>' +
                    '<td></td>' + // Empty column 1
                    '<td></td>' + // Empty column 2
                    '<td class="action-table-data"><div class="edit-delete-action"><a class="confirm-text p-2" onclick="deleteData(\'row' + rowIndex + '\',\'' + suggestion.split("|")[1] + '\',\'' + suggestion.split("|")[2] + '\')"><i data-feather="trash-2" class="feather-trash-2"></i></a></div></td>' +
                    '</tr>';
        } else {
            if (suggestion.split("|")[6] === 'false') {
                newRow = '<tr id="row' + rowIndex + '">' +
                        '<td>' + suggestion.split("|")[0] + '</td>' +
                        '<td><input style="width:100px;" id="quantity' + rowIndex + '" onchange="editDataRow(\'row' + rowIndex + '\',\'' + suggestion.split("|")[1] + '\',\'' + suggestion.split("|")[2] + '\')" type="number" value="1"/><input style="width:75px;" id="batchExpiry' + rowIndex + '" type="hidden" value=""/><input style="width:75px;" id="batchSerialNo' + rowIndex + '" type="hidden" value=""/></td>' +
                        '<td></td>' + // Empty column 1
                        '<td></td>' + // Empty column 2
                        '<td class="action-table-data"><div class="edit-delete-action"><a class="confirm-text p-2" onclick="deleteData(\'row' + rowIndex + '\',\'' + suggestion.split("|")[1] + '\',\'' + suggestion.split("|")[2] + '\')"><i data-feather="trash-2" class="feather-trash-2"></i></a></div></td>' +
                        '</tr>';
            } else {
                newRow = '<tr id="row' + rowIndex + '">' +
                        '<td>' + suggestion.split("|")[0] + '</td>' +
                        '<td>' +
                        '<input style="width:100px;" id="quantity' + rowIndex + '" onchange="editDataRow(\'row' + rowIndex + '\',\'' + suggestion.split("|")[1] + '\',\'' + suggestion.split("|")[2] + '\')" type="number" value="1"/>' +
                        '</td>' +
                        '<td>' +
                        '<input style="width:120px;" placeholder="Expiry Date" id="batchExpiry' + rowIndex + '" onchange="editDataRow(\'row' + rowIndex + '\',\'' + suggestion.split("|")[1] + '\',\'' + suggestion.split("|")[2] + '\')" type="date" value=""/>' +
                        '</td>' +
                        '<td>' +
                        '<input style="width:200px;" placeholder="Batch Number" id="batchSerialNo' + rowIndex + '" onchange="editDataRow(\'row' + rowIndex + '\',\'' + suggestion.split("|")[1] + '\',\'' + suggestion.split("|")[2] + '\')" type="text" value=""/>' +
                        '</td>' +
                        '<td class="action-table-data">' +
                        '<div class="edit-delete-action">' +
                        '<a class="confirm-text p-2" onclick="deleteData(\'row' + rowIndex + '\',\'' + suggestion.split("|")[1] + '\',\'' + suggestion.split("|")[2] + '\')">' +
                        '<i data-feather="trash-2" class="feather-trash-2"></i>' +
                        '</a>' +
                        '</div>' +
                        '</td>' +
                        '</tr>';
            }
        }
        $('.datanew tbody').prepend(newRow);
        suggestionsDiv.style.display = "none";
        purchaseDataArray.push(suggestion.split("|")[4] + "|" + suggestion.split("|")[1] + "|1|0|0|0|0|" + rowIndex + "|" + suggestion.split("|")[5]);
        document.getElementById("purchaseData").value = purchaseDataArray;
        let qua = 0;
        for (var i = 0; i < purchaseDataArray.length; i++) {
            qua = qua + parseInt(purchaseDataArray[i].split("|")[2]);
        }
        document.getElementById("totalQuantity").innerHTML = qua;
        rowIndex++;
    }

    function addNewRow(rowIndex, suggestion) {
        // Create a new row with the provided suggestion
        const newRow = createRowWithEnterKeyPress(suggestion);
        // Insert the new row at the top of the table
//                                                        document.querySelector('.datanew tbody').insertAdjacentHTML('afterbegin', newRow);
    }

    function editDataRow(rowId, unitPrice, gst) {
        // Get the <tr> element by its id
        var row = document.getElementById(rowId);
        var quantity = document.getElementById("quantity" + rowId.substring(3, rowId.length)).value;
        var batchSerialNo = document.getElementById("batchSerialNo" + rowId.substring(3, rowId.length)).value;
        var batchExpiry = document.getElementById("batchExpiry" + rowId.substring(3, rowId.length)).value;
        // Check if the row exists
        if (row) {
            // Modify the content of the first cell
            for (var i = 0; i < purchaseDataArray.length; i++) {
                var productId = purchaseDataArray[i].split("|")[7];
                var productId1 = purchaseDataArray[i].split("|")[0];
                var trackingSerialNo = purchaseDataArray[i].split("|")[8];
                if (productId === rowId.substring(3, rowId.length)) {
                    purchaseDataArray.splice(i, 1);
                    purchaseDataArray.push(productId1 + "|0|" + quantity + "|0|0|0|0|" + productId + "|" + trackingSerialNo + "|" + batchExpiry + "|" + batchSerialNo);
                    document.getElementById("purchaseData").value = purchaseDataArray;
                }
            }

            let qua = 0;
            for (var i = 0; i < purchaseDataArray.length; i++) {
                qua = qua + parseInt(purchaseDataArray[i].split("|")[2]);
            }
            document.getElementById("totalQuantity").innerHTML = qua;
            let count = 0;
            for (var i = 0; i < purchaseDataSerialArray.length; i++) {
                var productId = purchaseDataSerialArray[i].split("|")[5];
                if (productId === rowId.substring(3, rowId.length)) {
                    count++;
                    if (count > quantity) {
                        purchaseDataSerialArray.splice(i, 1);
                        document.getElementById("purchaseSerialData").value = purchaseDataSerialArray;
                    }
                }
            }
        } else {
            console.error("Row not found!");
        }
    }

    function addShipping() {

        document.getElementById("purchaseTotal").innerHTML = parseFloat(document.getElementById("purchaseSubtotal").innerHTML) + parseFloat(document.getElementById("purchaseTax").innerHTML) + parseFloat(document.getElementById("purchaseShipping").value) - parseFloat(document.getElementById("purchaseDiscount").innerHTML);
    }

    function deleteSerialData(rowId) {
        var row = document.getElementById(rowId);
        if (row) {
            row.parentNode.removeChild(row);
        }
        for (var i = 0; i < purchaseDataSerialArray.length; i++) {
            var serialNo = purchaseDataSerialArray[i].split("|")[2];
            if (serialNo === rowId.substring(6, rowId.length)) {
                purchaseDataSerialArray.splice(i, 1);
                document.getElementById("purchaseSerialData").value = purchaseDataSerialArray;
            }
        }
        let qua = 0;
        for (var i = 0; i < purchaseDataArray.length; i++) {
            qua = qua + parseInt(purchaseDataArray[i].split("|")[2]);
        }
        document.getElementById("totalQuantity").innerHTML = qua;
    }

    function deleteSerialAllData() {
        document.getElementById('myTableBody').innerHTML = '';
    }

    // Show/hide loader and enable/disable button based on 'show' flag.
    function toggleLoader(show) {
        let loader = document.getElementById("loader");
        let sbmtBtn = document.getElementById("sbmtBtn");

        if (show) {
            sbmtBtn.classList.add("disabled");
            loader.style.display = "block"; // Show loader and disable button
        } else {
            sbmtBtn.classList.remove("disabled");
            loader.style.display = "none"; // Hide loader and enable button
        }
    }

    function addPurchaseData() {
        var supplier = document.getElementById('supplierSearch').value;
        if (supplier === '') {
            alert("Please select supplier!");
            return false;
        }

        let quantity = 0;
        for (var i = 0; i < purchaseDataArray.length; i++) {
            var trackingNo = purchaseDataArray[i].split("|")[8];
            if (trackingNo === 'true') {
                quantity = quantity + parseFloat(purchaseDataArray[i].split("|")[2]);
            }
        }
//                                                        if (quantity !== purchaseDataSerialArray.length) {
//                                                            alert("Please enter serial number and expiry date!");
//                                                            return false;
//                                                        }

        toggleLoader(true);
        document.purchaseForm.action = "processPurchaseProductCreate";
        document.purchaseForm.submit();
    }





    function getImportData(button) {
        // Disable the button and change its text
        button.disabled = true;
        button.innerText = 'Uploaded';
        var formData = new FormData();
        var fileInput = document.getElementById('file1');
        var file1 = fileInput.files[0];
        formData.append("file1", file1);
        $.ajax({
            url: "${pageContext.request.contextPath}/importPurchaseProduct",
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            success: function (response) {
                for (var i = 0, max = response.productPurchase.length; i < max; i++) {
                    var newRow = '';
//                                                                    rowIndex = rowIndex + i + 1;
                    if (response.productPurchase[i].trackingSerialNo) {
                        newRow = '<tr id = "row' + rowIndex + '">' +
                                '<td><a href="#" class="btn btn-added" onclick=showPopupData(\'' + response.productPurchase[i].snoPattern + '\',\'' + response.productPurchase[i].productId + '\',\'' + response.productPurchase[i].costPrice + '\',\'' + rowIndex + '\') data-bs-toggle="modal" data-bs-target="#add-sales-new"><i data-feather="plus-circle" class="me-2"></i>' + response.productPurchase[i].productName + '</a></td>' +
                                '<td><input style="width:75px;" id = "quantity' + rowIndex + '" onchange = "editDataRow(\'row' + rowIndex + '\',\'' + response.productPurchase[i].costPrice + '\',\'' + response.productPurchase[i].gst + '\')" type="number" value="' + response.productPurchase[i].quantity + '"/></td>' +
                                '<td></td>' + // Empty column 1
                                '<td></td>' + // Empty column 2
                                '<td class="action-table-data"><div class="edit-delete-action"><a class="confirm-text p-2" onclick = "deleteData(\'row' + rowIndex + '\',\'' + response.productPurchase[i].costPrice + '\',\'' + response.productPurchase[i].gst + '\')"><i data-feather="trash-2" class="feather-trash-2"></i></a></div></td>' +
                                '</tr>';
                    } else {
                        newRow = '<tr id = "row' + rowIndex + '">' +
                                '<td>' + response.productPurchase[i].productName + '</td>' +
                                '<td><input style="width:75px;" id = "quantity' + rowIndex + '" onchange = "editDataRow(\'row' + rowIndex + '\',\'' + response.productPurchase[i].costPrice + '\',\'' + response.productPurchase[i].gst + '\')" type="number" value="' + response.productPurchase[i].quantity + '"/></td>' +
                                '<td class="action-table-data"><div class="edit-delete-action"><a class="confirm-text p-2" onclick = "deleteData(\'row' + rowIndex + '\',\'' + response.productPurchase[i].costPrice + '\',\'' + response.productPurchase[i].gst + '\')"><i data-feather="trash-2" class="feather-trash-2"></i></a></div></td>' +
                                '</tr>';
                    }
                    $('.datanew tbody').prepend(newRow);
                    purchaseDataArray.push(response.productPurchase[i].productId + "|" + response.productPurchase[i].costPrice + "|" + response.productPurchase[i].quantity + "|0|0|0|0|" + rowIndex + "|" + response.productPurchase[i].trackingSerialNo);
                    document.getElementById("purchaseData").value = purchaseDataArray;
                    rowIndex++;
                }
                button.disabled = false;
                button.innerText = 'Submit';
                if (response.serResp !== '') {
                    document.getElementById("errMessage").style.display = 'block';
                    document.getElementById("errMessage").innerHTML = 'These items are not import - ' + response.serResp;
                }
                purchaseDataSerialArray = response.serialPurchase;
                document.getElementById("purchaseSerialData").value = purchaseDataSerialArray;
                let qua = 0;
                for (var i = 0; i < purchaseDataArray.length; i++) {
                    qua = qua + parseInt(purchaseDataArray[i].split("|")[2]);
                }
                document.getElementById("totalQuantity").innerHTML = qua;
                // Close the modal
                const modal = document.getElementById('view-notes');
                const modalInstance = bootstrap.Modal.getInstance(modal);
                modalInstance.hide();
            },
            error: function (err) {
                alert("Error uploading file: " + err.responseText);
            }
        });
    }

    function productCreate() {

        var name = document.getElementById("myInput").value;
        var url = "${pageContext.request.contextPath}/productCreate?source=purchase&name=" + name;
        var newwindow = window.open(url, 'name', 'height=1000,width=1200');
        if (window.focus) {
            newwindow.focus();
        }
        return false;
    }
</script>



<script>
    function formatDate(date) {
        var day = ("0" + date.getDate()).slice(-2);
        var month = ("0" + (date.getMonth() + 1)).slice(-2);
        var year = date.getFullYear();
        return year + "-" + month + "-" + day;
    }

    function displayFormattedDate() {
        var inputDate = document.getElementById("dateInput").value;
        document.getElementById("formattedDateOutput").value = inputDate;
    }

    document.addEventListener("DOMContentLoaded", function () {
        var currentDate = formatDate(new Date());
        document.getElementById("dateInput").value = currentDate;
        document.getElementById("formattedDateOutput").value = currentDate;
    });
    document.getElementById("dateInput").addEventListener("change", displayFormattedDate);</script>
<script>
    let currentSupplierFocus = -1;
    let productFocus = -1;
    function filterSuppliers() {
        var input, filter, div, supplierItems, i, txtValue;
        input = document.getElementById('supplierSearch');
        filter = input.value.toUpperCase();
        div = document.getElementById('supplierList');
        supplierItems = div.getElementsByClassName('supplier-item');
        // Display the supplier list
        div.style.display = 'block';
        // Loop through all supplier items, and hide those who don't match the search query
        for (i = 0; i < supplierItems.length; i++) {
            txtValue = supplierItems[i].textContent || supplierItems[i].innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                supplierItems[i].style.display = "";
            } else {
                supplierItems[i].style.display = "none";
            }
        }
    }

    function showSupplierList() {
        var div = document.getElementById('supplierList');
        div.style.display = 'block';
    }

    function hideSupplierList() {
        var div = document.getElementById('supplierList');
        div.style.display = 'none';
    }

    function selectSupplier(name, id) {
        document.getElementById('supplierSearch').value = name;
        document.getElementById('hiddenUserId').value = id;
        hideSupplierList();
        currentSupplierFocus = -1; // Reset the focus index
    }

    function handleSupplierKeyEvents(event) {
        const supplierList = document.getElementById('supplierList');
        const items = Array.from(supplierList.getElementsByClassName('supplier-item')).filter(item => item.style.display !== 'none');
        var hiddenInput = document.getElementById('hiddenUserId');
        if (event.keyCode === 40) {
            // Down arrow key
            currentSupplierFocus++;
            if (currentSupplierFocus >= items.length)
                currentSupplierFocus = 0;
            addActiveSupplier(items);
            ensureItemVisible(items[currentSupplierFocus]);
            if (items[currentSupplierFocus]) {
                hiddenInput.value = items[currentSupplierFocus].getAttribute('data-id');
            }
        } else if (event.keyCode === 38) {
            // Up arrow key
            currentSupplierFocus--;
            if (currentSupplierFocus < 0)
                currentSupplierFocus = items.length - 1;
            addActiveSupplier(items);
            ensureItemVisible(items[currentSupplierFocus]);
            if (items[currentSupplierFocus]) {
                hiddenInput.value = items[currentSupplierFocus].getAttribute('data-id');
            }
        } else if (event.keyCode === 13) {
            // Enter key
            event.preventDefault(); // Prevent the default form submission behavior
            if (currentSupplierFocus > -1) {
                if (items)
                    items[currentSupplierFocus].click();
            }
            hideSupplierList();
        } else {
            filterSuppliers();
        }
    }

    function addActiveSupplier(items) {
        if (!items)
            return false;
        removeActiveSupplier(items);
        if (currentSupplierFocus >= items.length)
            currentSupplierFocus = 0;
        if (currentSupplierFocus < 0)
            currentSupplierFocus = items.length - 1;
        items[currentSupplierFocus].classList.add('active');
    }

    function removeActiveSupplier(items) {
        for (let i = 0; i < items.length; i++) {
            items[i].classList.remove('active');
        }
    }

    function ensureItemVisible(item) {
        const container = document.getElementById('supplierList');
        const containerRect = container.getBoundingClientRect();
        const itemRect = item.getBoundingClientRect();
        if (itemRect.bottom > containerRect.bottom) {
            container.scrollTop += itemRect.bottom - containerRect.bottom;
        } else if (itemRect.top < containerRect.top) {
            container.scrollTop -= containerRect.top - itemRect.top;
        }
    }

    function filterProducts() {
        var input, filter, div, productItems, i, txtValue;
        input = document.getElementById('productName');
        filter = input.value.toUpperCase();
        div = document.getElementById('productList');
        productItems = div.getElementsByClassName('product-item');
        // Display the product list
        div.style.display = 'block';
        // Loop through all product items, and hide those who don't match the search query
        for (i = 0; i < productItems.length; i++) {
            txtValue = productItems[i].textContent || productItems[i].innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                productItems[i].style.display = "";
            } else {
                productItems[i].style.display = "none";
            }
        }
    }

    function showProductList() {
        var div = document.getElementById('productList');
        div.style.display = 'block';
    }

    function hideProductList() {
        var div = document.getElementById('productList');
        div.style.display = 'none';
    }

    function selectProduct(name, id) {
        document.getElementById('productName').value = name;
        document.getElementById('hiddenProductId').value = id;
        hideProductList();
        productFocus = -1; // Reset the focus index
    }

    function handleProductKeyEvents(event) {
        const productList = document.getElementById('suggestions');
        const items = Array.from(productList.getElementsByClassName('suggestions-box')).filter(item => item.style.display !== 'none');
        var hiddenInput = document.getElementById('hiddenProductId');
        if (event.keyCode == 40) {
            // Down arrow key
            productFocus++;
            if (productFocus >= items.length)
                productFocus = 0;
            addProductActive(items);
            ensureProductVisible(items[productFocus]);
            if (items[productFocus]) {
                hiddenInput.value = items[productFocus].getAttribute('data-id');
            }
        } else if (event.keyCode == 38) {
            // Up arrow key
            productFocus--;
            if (productFocus < 0)
                productFocus = items.length - 1;
            addProductActive(items);
            ensureProductVisible(items[productFocus]);
            if (items[productFocus]) {
                hiddenInput.value = items[productFocus].getAttribute('data-id');
            }
        } else if (event.keyCode == 13) {
            // Enter key
            event.preventDefault(); // Prevent the default form submission behavior
            if (productFocus > -1) {
                if (items)
                    items[productFocus].click();
            }
            hideProductList();
        } else {
            filterProducts();
        }
    }



    function addProductActive(items) {
        if (!items)
            return false;
        removeProductActive(items);
        if (productFocus >= items.length)
            productFocus = 0;
        if (productFocus < 0)
            productFocus = items.length - 1;
        items[productFocus].classList.add('active');
    }

    function removeProductActive(items) {
        for (let i = 0; i < items.length; i++) {
            items[i].classList.remove('active');
        }
    }

    function ensureProductVisible(item) {
        const container = document.getElementById('productList');
        const containerRect = container.getBoundingClientRect();
        const itemRect = item.getBoundingClientRect();
        if (itemRect.bottom > containerRect.bottom) {
            container.scrollTop += itemRect.bottom - containerRect.bottom;
        } else if (itemRect.top < containerRect.top) {
            container.scrollTop -= containerRect.top - itemRect.top;
        }
    }

    document.addEventListener('click', function (event) {
        const supplierList = document.getElementById('supplierList');
        const productList = document.getElementById('productList');
        if (!supplierList.contains(event.target) && event.target !== document.getElementById('supplierSearch')) {
            hideSupplierList();
        }
        if (!productList.contains(event.target) && event.target !== document.getElementById('productName')) {
            hideProductList();
        }
    });</script>

<script>
    function filterSuppliers() {
        var input, filter, supplierList, supplierItems, supplierName, i;
        input = document.getElementById('supplierSearch');
        filter = input.value.toUpperCase();
        supplierList = document.getElementById('supplierList');
        supplierItems = supplierList.getElementsByClassName('supplier-item');
        // Show the supplier list if there is any input
        if (input.value) {
            supplierList.style.display = "";
        } else {
            supplierList.style.display = "none";
        }

        for (i = 0; i < supplierItems.length; i++) {
            supplierName = supplierItems[i].innerText || supplierItems[i].textContent;
            if (supplierName.toUpperCase().indexOf(filter) > -1) {
                supplierItems[i].style.display = "";
            } else {
                supplierItems[i].style.display = "none";
            }
        }
    }

    function selectSupplier(supplierName, supplierId) {
        console.log("supplierId: " + supplierId); // Debugging output
        var input = document.getElementById('supplierSearch');
        var hiddenInput = document.getElementById('hiddenUserId');
        input.value = supplierName;
        hiddenInput.value = supplierId;
        // Hide the supplier list once a selection is made
        document.getElementById('supplierList').style.display = "none";
    }

</script>





