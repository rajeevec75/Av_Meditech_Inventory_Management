<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.AvMeditechInventory.dtos.ProductDto"%>
<%@page import="com.AvMeditechInventory.entities.Category"%>
<%@page import="com.AvMeditechInventory.util.Constant"%>
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

        <style>
            .autocomplete-list {
                position: absolute;
                width: 100%;
                max-height: 150px;
                overflow-y: auto;
                background: #fff;
                z-index: 1000;
            }
            .autocomplete-item {
                padding: 8px;
                cursor: pointer;
            }
            .autocomplete-item:hover, .autocomplete-item.selected {
                background: #f0f0f0;
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
                <div class="page-wrapper">
                    <div class="content">
                        <div class="page-header">
                            <div class="add-item d-flex">
                                <div class="page-title">
                                    <h4>Edit Product</h4>
                                    <!-- Add this snippet in your JSP page where you want to display the error message -->
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
                    <!-- /add -->
                    <form action="processProductUpdate" method="post">
                        <input type="hidden" name="after" value="${after}" />
                        <input type="hidden" name="isAsc" value="${isAsc}" />
                        <input type="hidden" name="Urlbrand" value="${selectedBrandId}" />
                        <input type="hidden" name="UrlproductType" value="${selectedProductTypeId}" />
                        <input type="hidden" id="productId" name="productId" value="${product.productId}">
                        <input type="hidden" id="productVarientId" name="productVarientId" value="${productVariantId}">
                        <input type="hidden" id="hiddenInputContainer" name="hiddenInputContainer" value="">
                        <div class="card">
                            <div class="card-body add-product pb-0">
                                <div class="accordion-card-one accordion" id="accordionExample">
                                    <div class="accordion-item">
                                        <div class="accordion-header" id="headingOne">
                                            <div class="accordion-button" data-bs-toggle="collapse" data-bs-target="#collapseOne"  aria-controls="collapseOne">
                                                <div class="addproduct-icon">
                                                    <h5><i data-feather="info" class="add-info"></i><span>Update Product Information</span></h5>
                                                    <a href="javascript:void(0);"><i data-feather="chevron-down" class="chevron-down-add"></i></a>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                                            <div class="accordion-body">

                                                <div class="row">
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Product Name</label>
                                                            <input type="text" class="form-control" name="productName" required value="${product.productName}">
                                                        </div>
                                                    </div>



                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product" style="position: relative;">
                                                            <label class="form-label">Brand</label>
                                                            <input type="text" id="categoryInput" class="form-control" placeholder="Enter Brand" autocomplete="off" required readonly>
                                                            <input type="hidden" id="categoryIdInput" name="category">
                                                            <div id="category-autocomplete-list" class="autocomplete-list"></div>
                                                        </div>
                                                    </div>

                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="input-blocks add-product">
                                                            <label>SNO Pattern</label>
                                                            <input type="text" class="form-control" name="snoPattern" id="snoPattern" value="${product.snoPattern}">
                                                        </div>
                                                    </div>

                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product" style="position: relative;">
                                                            <label class="form-label">Product Type</label>
                                                            <!-- Autocomplete Input -->
                                                            <input type="text" id="productTypeInput" class="form-control" placeholder="Enter Product Type" autocomplete="off" required readonly>
                                                            <input type="hidden" id="productTypeIdInput" name="productType">
                                                            <div id="productType-autocomplete-list" class="autocomplete-list"></div>
                                                        </div>
                                                    </div>



                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">SKU/Product Code:</label>
                                                            <input type="text" class="form-control" name="sku" required value="${sku}">
                                                        </div>
                                                    </div>



                                                    <!-- Tracking Serial Number -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Tracking Serial Number</label>
                                                            <select class="select" name="trackingSerialNo" id="trackingSerialNo" onchange="enableBatchId()" required>
                                                                <option value="">Select Tracking Serial Number</option>
                                                                <option value="true" ${product.trackingSerialNo ? 'selected' : ''}>YES</option>
                                                                <option value="false" ${!product.trackingSerialNo ? 'selected' : ''}>NO</option>
                                                            </select>

                                                        </div>
                                                    </div>

                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Product Service</label>
                                                            <select class="select" name="isProductService" required>
                                                                <option value="true" ${product.isProductService == true ? 'selected' : ''}>True</option>
                                                                <option value="false" ${product.isProductService == false ? 'selected' : ''}>False</option>
                                                            </select>
                                                        </div>
                                                    </div>


                                                    <c:choose>
                                                        <c:when test="${permissionGroup.permissionGroupsName == 'Manage All'}">

                                                            <!-- Batch Serial Number -->
                                                            <!-- Batch Serial Number -->
                                                            <div class="col-lg-4 col-sm-6 col-12" id="batchId" style="display: none;">
                                                                <div class="mb-3 add-product">
                                                                    <label class="form-label">Batch Serial Number</label>
                                                                    <select class="select" name="batchSerialNo">
                                                                        <option value="true" ${product.batchSerialNo ? 'selected' : ''}>YES</option>
                                                                        <option value="false" ${!product.batchSerialNo ? 'selected' : ''}>NO</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>

                                                            <!-- Batch Serial Number -->
                                                            <div class="col-lg-4 col-sm-6 col-12" id="batchId" style="display: none;">
                                                                <div class="mb-3 add-product">
                                                                    <label class="form-label">Batch Serial Number</label>
                                                                    <select class="select" name="batchSerialNo">
                                                                        <option value="true" ${product.batchSerialNo ? 'selected' : ''}>YES</option>
                                                                        <option value="false" ${!product.batchSerialNo ? 'selected' : ''}>NO</option>
                                                                    </select>
                                                                </div>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>



                                                </div>



                                                <div class="row">
                                                    <!-- Editor -->
                                                    <div class="col-lg-12">
                                                        <div class="input-blocks summer-description-box transfer mb-3">
                                                            <label>Description</label>
                                                            <textarea class="form-control h-100" rows="5" name="productDescription">${text}</textarea>
                                                            <p class="mt-1">Maximum 60 Characters</p>
                                                        </div>
                                                    </div>

                                                    <!--                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                                                                            <div class="input-blocks add-product">
                                                                                                                <label>Price</label>
                                                                                                                <input type="text" class="form-control" value="${product.price}" name="price">
                                                                                                            </div>
                                                                                                        </div> 
                                                                                                        <div class="col-lg-4 col-sm-6 col-12">
                                                                                                            <div class="input-blocks add-product">
                                                                                                                <label>Cost Price</label>
                                                                                                                <input type="text" class="form-control" value="${product.costPrice}" name="costPrice">
                                                                                                            </div>
                                                                                                        </div> -->




                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="input-blocks add-product">
                                                            <input type="button" class="btn btn-submit" value="Add Attribute" onclick="showAttribute()">
                                                        </div>
                                                    </div>


                                                    <!-- /Editor -->
                                                </div>

                                                <%@ page import="java.util.Map" %>

                                                <%    // Retrieve the metadata map from the request attributes
                                                    Map<String, Object> metadata = (Map<String, Object>) request.getAttribute("metadata");
                                                %>

                                                <div class="row" id="showAttribute">
                                                    <!-- Iterate over metadata entries and display each key-value pair -->
                                                    <% for (Map.Entry<String, Object> entry : metadata.entrySet()) {%>
                                                    <!-- Attribute Dropdown Widget -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product dropdown-widget">
                                                            <label class="form-label">Attribute</label>
                                                            <div class="dropdown-textbox">
                                                                <div class="d-flex">
                                                                    <select class="select" name="key" required>
                                                                        <option value="Weight" <%= "Weight".equals(entry.getKey()) ? "selected" : ""%>>Weight</option>
                                                                        <option value="Size" <%= "Size".equals(entry.getKey()) ? "selected" : ""%>>Size</option>
                                                                        <option value="Power" <%= "Power".equals(entry.getKey()) ? "selected" : ""%>>Power</option>
                                                                    </select>

                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <!-- Value Input Widget -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="input-blocks add-product input-widget">
                                                            <label class="form-label">Value</label>
                                                            <input type="text" class="form-control" name="value" value="<%= entry.getValue()%>">
                                                        </div>
                                                    </div>

                                                    <!-- Close and open a new row for each pair -->
                                                    <%
                                                        if (metadata.entrySet().iterator().hasNext()) {
                                                    %>
                                                </div><div class="row">
                                                    <%
                                                        }
                                                    %>
                                                    <% }%>
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
                                <button type="submit" class="btn btn-submit">Update Product</button>
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



        <script>
                                                                $(document).ready(function () {
                                                                    $.ajax({
                                                                        type: "GET",
                                                                        url: "${pageContext.request.contextPath}/channelList/json",
                                                                        success: function (response) {
                                                                            // Check if the response is an array to avoid processing errors
                                                                            if (Array.isArray(response)) {
                                                                                var concatenatedValues = "";
                                                                                response.forEach(function (channel, index) {
                                                                                    // Log the channel details
                                                                                    console.log('Channel ID: ' + channel.channelId);
                                                                                    console.log('Channel Name: ' + channel.channelName);

                                                                                    // Concatenate channelId and channelName with a separator
                                                                                    concatenatedValues += channel.channelId + "|" + channel.channelName + ",";
                                                                                });
                                                                                // Remove the trailing comma
                                                                                concatenatedValues = concatenatedValues.slice(0, -1);

                                                                                // Set the concatenated values as the value of the hidden input
                                                                                $('#hiddenInputContainer').val(concatenatedValues);
                                                                            } else {
                                                                                console.error('Unexpected response format:', response);
                                                                            }
                                                                        },
                                                                        error: function (xhr, status, error) {
                                                                            console.error('Failed to fetch channels list:', xhr.responseText);
                                                                        }
                                                                    });
                                                                });

                                                                var count = ${metadataSize} + 1;
                                                                var attCount = ${Constant.ATTRIBUTE_LENGTH};

                                                                function showAttribute() {
                                                                    if (count <= attCount) {
                                                                        count = count + 1;
                                                                        var attCode = '<div class="row"><div class="col-lg-4 col-sm-6 col-12"><div class="mb-3 add-product dropdown-widget">\n\
                                                                        <label class="form-label">Attribute</label><div class="dropdown-textbox"><div class="d-flex"><select class="select" name="key" required>\n\
                                                                        <option value="Power">Power</option><option value="Weight">Weight</option><option value="Size">Size</option></select>\n\
                                                                        </div></div></div></div><div class="col-lg-4 col-sm-6 col-12"><div class="input-blocks add-product input-widget"><label class="form-label">Value</label>\n\
                                                                        <input type="text" class="form-control" name="value"></div></div></div>';
                                                                        document.getElementById("showAttribute").innerHTML = document.getElementById("showAttribute").innerHTML + attCode;
                                                                    } else {
                                                                        alert("You have add maximum " + attCount + " attributes!");
                                                                        return;
                                                                    }
                                                                }
        </script>

        <script>
            // Function to show or hide the Batch Serial Number dropdown based on Tracking Serial Number selection
            function enableBatchId() {
                var trackingSerialNo = document.getElementById("trackingSerialNo").value;
                var batchId = document.getElementById("batchId");

                // Show the batchId dropdown if "NO" is selected; hide it if "YES" is selected
                if (trackingSerialNo === "false") {
                    batchId.style.display = "block";
                } else {
                    batchId.style.display = "none";
                }
            }

            // Initialize the dropdown state on page load (for editing case)
            window.onload = function () {
                enableBatchId();
            };
        </script>


        <script>
            document.addEventListener("DOMContentLoaded", function () {
            const categoryInput = document.getElementById("categoryInput");
                    const categoryIdInput = document.getElementById("categoryIdInput");
                    const snoPatternInput = document.getElementById("snoPattern");
                    const autocompleteList = document.getElementById("category-autocomplete-list");
                    let selectedIndex = - 1;
                    // Get selected category details from JSP
                    const selectedCategoryId = "${product.category != null ? product.category.categoryId : ''}";
                    console.log('Selected Category ID:', selectedCategoryId);
                    // Convert JSP category list into JavaScript array
                    const categoryList = [
            <c:forEach items="${categoryList}" var="categoryItem">
                    {
                    id: "${categoryItem.categoryId}",
                            name: "${categoryItem.categoryName}",
                            serialPattern: "${categoryItem.metadata.serialNumberPattern}"
                    },
            </c:forEach>
                    ];
                    // Set selected category in the input field
                    const selectedCategory = categoryList.find(cat => cat.id === selectedCategoryId);
                    if (selectedCategory) {
            categoryInput.value = selectedCategory.name;
                    categoryIdInput.value = selectedCategory.id;
                    snoPatternInput.value = selectedCategory.serialPattern;
            }

            function showSuggestions(value) {
            autocompleteList.innerHTML = "";
                    selectedIndex = - 1;
                    if (!value.trim()) return;
                    categoryList.forEach((category, index) => {
                    if (category.name.toLowerCase().includes(value.toLowerCase())) {
                    const suggestionItem = document.createElement("div");
                            suggestionItem.textContent = category.name;
                            suggestionItem.setAttribute("data-index", index);
                            suggestionItem.classList.add("autocomplete-item");
                            suggestionItem.addEventListener("click", function () {
                            selectCategory(category);
                            });
                            autocompleteList.appendChild(suggestionItem);
                    }
                    });
            }

            function selectCategory(category) {
            categoryInput.value = category.name;
                    categoryIdInput.value = category.id;
                    snoPatternInput.value = category.serialPattern;
                    autocompleteList.innerHTML = "";
            }

            categoryInput.addEventListener("input", function () {
            showSuggestions(this.value);
            });
                    categoryInput.addEventListener("keydown", function (e) {
                    const items = autocompleteList.querySelectorAll(".autocomplete-item");
                            if (items.length === 0) return;
                            if (e.key === "ArrowDown") {
                    selectedIndex = (selectedIndex + 1) % items.length;
                    } else if (e.key === "ArrowUp") {
                    selectedIndex = (selectedIndex - 1 + items.length) % items.length;
                    } else if (e.key === "Enter") {
                    if (selectedIndex > - 1) {
                    const selectedItem = items[selectedIndex];
                            const selectedCategory = categoryList.find(cat => cat.name === selectedItem.textContent);
                            if (selectedCategory) selectCategory(selectedCategory);
                    }
                    e.preventDefault();
                    } else if (e.key === "PageDown") {
                    selectedIndex = Math.min(selectedIndex + 5, items.length - 1);
                    } else if (e.key === "PageUp") {
                    selectedIndex = Math.max(selectedIndex - 5, 0);
                    }

                    items.forEach((item, i) => {
                    item.classList.toggle("selected", i === selectedIndex);
                    });
                            if (selectedIndex >= 0 && selectedIndex < items.length) {
                    items[selectedIndex].scrollIntoView({ block: "nearest", behavior: "smooth" });
                    }
                    });
                    document.addEventListener("click", function (e) {
                    if (!categoryInput.contains(e.target) && !autocompleteList.contains(e.target)) {
                    autocompleteList.innerHTML = "";
                            selectedIndex = - 1;
                    }
                    });
            });
        </script>

        <script>
                    document.addEventListener("DOMContentLoaded", function () {
                    const productTypeInput = document.getElementById("productTypeInput");
                            const productTypeIdInput = document.getElementById("productTypeIdInput");
                            const autocompleteList = document.getElementById("productType-autocomplete-list");
                            let selectedIndex = - 1;
                            // Get selected product type details from JSP
                            const selectedProductTypeId = "${product.productType != null ? product.productType.productTypeId : ''}";
                            console.log('Selected Product Type ID:', selectedProductTypeId);
                            // Convert JSP product type list into JavaScript array
                            const productTypeList = [
            <c:forEach items="${productTypeList}" var="productType">
                            {name: "${productType.productTypeName}", id: "${productType.productTypeId}"},
            </c:forEach>
                            ];
                            // Find and set the selected product type in the input field
                            const selectedProductType = productTypeList.find(type => type.id === selectedProductTypeId);
                            if (selectedProductType) {
                    productTypeInput.value = selectedProductType.name;
                            productTypeIdInput.value = selectedProductType.id;
                    }

                    function showSuggestions(value) {
                    autocompleteList.innerHTML = "";
                            selectedIndex = - 1;
                            if (!value.trim()) return;
                            productTypeList.forEach((product, index) => {
                            if (product.name.toLowerCase().includes(value.toLowerCase())) {
                            const suggestionItem = document.createElement("div");
                                    suggestionItem.textContent = product.name;
                                    suggestionItem.setAttribute("data-index", index);
                                    suggestionItem.classList.add("autocomplete-item");
                                    suggestionItem.addEventListener("click", function () {
                                    selectProductType(product);
                                    });
                                    autocompleteList.appendChild(suggestionItem);
                            }
                            });
                    }

                    function selectProductType(product) {
                    productTypeInput.value = product.name;
                            productTypeIdInput.value = product.id;
                            autocompleteList.innerHTML = "";
                    }

                    productTypeInput.addEventListener("input", function () {
                    showSuggestions(this.value);
                    });
                            productTypeInput.addEventListener("keydown", function (e) {
                            const items = autocompleteList.querySelectorAll(".autocomplete-item");
                                    if (items.length === 0) return;
                                    if (e.key === "ArrowDown") {
                            selectedIndex = (selectedIndex + 1) % items.length;
                            } else if (e.key === "ArrowUp") {
                            selectedIndex = (selectedIndex - 1 + items.length) % items.length;
                            } else if (e.key === "Enter") {
                            if (selectedIndex > - 1) {
                            const selectedItem = items[selectedIndex];
                                    const selectedProduct = productTypeList.find(p => p.name === selectedItem.textContent);
                                    if (selectedProduct) selectProductType(selectedProduct);
                            }
                            e.preventDefault();
                            } else if (e.key === "PageDown") {
                            selectedIndex = Math.min(selectedIndex + 5, items.length - 1);
                            } else if (e.key === "PageUp") {
                            selectedIndex = Math.max(selectedIndex - 5, 0);
                            }

                            items.forEach((item, i) => {
                            item.classList.toggle("selected", i === selectedIndex);
                            });
                                    if (selectedIndex >= 0 && selectedIndex < items.length) {
                            items[selectedIndex].scrollIntoView({ block: "nearest", behavior: "smooth" });
                            }
                            });
                            document.addEventListener("click", function (e) {
                            if (!productTypeInput.contains(e.target) && !autocompleteList.contains(e.target)) {
                            autocompleteList.innerHTML = "";
                                    selectedIndex = - 1;
                            }
                            });
                            });

        </script>



    </body>

</html>