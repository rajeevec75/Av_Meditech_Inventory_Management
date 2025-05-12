<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.AvMeditechInventory.util.Constant"%>
<!DOCTYPE html>
<%
    String source = (String) request.getAttribute("source");
    String name = (String) request.getAttribute("name");
%>
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
                background: #fff;
                max-height: 150px;
                overflow-y: auto;
                width: 100%;
                z-index: 1000;
            }
            .autocomplete-list div {
                padding: 8px;
                cursor: pointer;
            }
            .autocomplete-list div:hover, .autocomplete-list .selected {
                background: #f0f0f0;
            }
        </style>




    </head>

    <body>

        <div id="global-loader">
            <div class="whirly-loader"></div>
        </div>

        <!-- Main Wrapper -->
        <div class="main-wrapper">
            <!-- Header and Sidebar -->
            <jsp:include page="header.jsp"></jsp:include>
            <jsp:include page="sidebar.jsp"></jsp:include>

                <div class="page-wrapper">
                    <div class="content">
                        <!-- Page Header -->
                        <div class="page-header">
                            <div class="add-item d-flex">
                                <div class="page-title">
                                    <h4>New Product</h4>
                                    <h6>Create new product</h6>
                                    <!-- Add this snippet in your JSP page where you want to display the error message -->
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

                                    <a href="javascript:history.go(-1)" class="btn btn-secondary"><i data-feather="arrow-left" class="me-2"></i>Back</a>

                                </div>
                            </li>
                            <li>
                                <a data-bs-toggle="tooltip" data-bs-placement="top" title="Collapse" id="collapse-header">
                                    <i data-feather="chevron-up" class="feather-chevron-up"></i>
                                </a>
                            </li>
                        </ul>
                    </div>
                    <!-- End Page Header -->

                    <!-- Form for Adding New Product -->
                    <form action="processProductCreate" method="post">
                        <input type="hidden" id="hiddenInputContainer" name="hiddenInputContainer" value="">
                        <div class="card">
                            <div class="card-body add-product pb-0">
                                <div class="accordion-card-one accordion" id="accordionExample">
                                    <div class="accordion-item">
                                        <div class="accordion-header" id="headingOne">
                                            <div class="accordion-button" data-bs-toggle="collapse" data-bs-target="#collapseOne"  aria-controls="collapseOne">
                                                <div class="addproduct-icon">
                                                    <h5><i data-feather="info" class="add-info"></i><span>Product Information</span></h5>
                                                    <a href="javascript:void(0);"><i data-feather="chevron-down" class="chevron-down-add"></i></a>
                                                </div>
                                            </div>
                                        </div>
                                        <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                                            <div class="accordion-body">
                                                <div class="row">
                                                    <!-- Product Name -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Product Name</label>
                                                            <input type="text" class="form-control" name="productName" value="${name}" required>
                                                            <input type="hidden" class="form-control" name="source" value="${source}">
                                                        </div>
                                                    </div>

                                                    <!-- Brand -->
                                                    <!-- Category Input -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product" style="position: relative;">
                                                            <label class="form-label">Product Type</label>
                                                            <input type="text" id="categoryInput" class="form-control" placeholder="Enter Product Type" required autocomplete="off">
                                                            <input type="hidden" id="categoryIdInput" name="category">
                                                            <div id="category-autocomplete-list" class="autocomplete-list"></div>
                                                        </div>
                                                    </div>

                                                    <!-- SNO Pattern Display -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="input-blocks add-product">
                                                            <label>SNO Pattern</label>
                                                            <input type="text" class="form-control" id="snoPattern" name="snoPattern" readonly="">
                                                        </div>
                                                    </div>



                                                    <!-- Product Type -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product" style="position: relative;">
                                                            <label class="form-label">Brand</label>
                                                            <input type="text" id="productTypeInput" class="form-control" placeholder="Enter Brand" required autocomplete="off">
                                                            <input type="hidden" id="productTypeIdInput" name="productType">
                                                            <div id="autocomplete-list" class="autocomplete-list"></div>
                                                        </div>
                                                    </div>



                                                    <!-- SKU/Product Code -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">SKU/Product Code:</label>
                                                            <input type="text" class="form-control" name="sku" required>
                                                        </div>
                                                    </div>



                                                    <!-- Tracking Serial Number -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Tracking Serial Number</label>
                                                            <select class="select" name="trackingSerialNo" id="trackingSerialNo" onchange="enableBatchId()" required>
                                                                <option value="">Select Tracking Serial Number</option>
                                                                <option value="true">YES</option>
                                                                <option value="false">NO</option>
                                                            </select>
                                                        </div>
                                                    </div>

                                                    <!-- Batch Serial Number -->
                                                    <div class="col-lg-4 col-sm-6 col-12" id="batchId" style="display: none;">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Batch Serial Number</label>
                                                            <select class="select" name="batchSerialNo">
                                                                <option value="true">YES</option>
                                                                <option selected value="false">NO</option>
                                                            </select>
                                                        </div>
                                                    </div>

                                                    <!-- Product Service -->
                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="mb-3 add-product">
                                                            <label class="form-label">Product Service</label>
                                                            <select class="select" name="isProductService" required>
                                                                <option value="true">True</option>
                                                                <option value="false" selected>False</option>
                                                            </select>
                                                        </div>
                                                    </div>



                                                    <!-- Description -->
                                                    <div class="col-lg-12">
                                                        <div class="input-blocks summer-description-box transfer mb-3">
                                                            <label>Description</label>
                                                            <textarea class="form-control h-100" rows="5" name="productDescription"></textarea>
                                                            <p class="mt-1">Maximum 60 Characters</p>
                                                        </div>
                                                    </div>



                                                    <div class="col-lg-4 col-sm-6 col-12">
                                                        <div class="input-blocks add-product">
                                                            <input type="button" class="btn btn-submit" value="Add Attribute" onclick="showAttribute()">
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="row" id="showAttribute">

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Buttons for Cancel and Save Product -->
                        <div class="col-lg-12">
                            <div class="btn-addproduct mb-4">
                                <button type="button" class="btn btn-cancel me-2">Cancel</button>
                                <button type="submit" class="btn btn-submit">Save Product</button>
                            </div>
                        </div>
                    </form>
                    <!-- End Form -->

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
                                                                var count = 1;
                                                                var attCount = ${Constant.ATTRIBUTE_LENGTH};
                                                                // Get the select element
                                                                const productTypeSelect = document.getElementById('productTypeSelect');
                                                                // Add event listener for change event
                                                                productTypeSelect.addEventListener('change', function () {
                                                                    // Get the selected option
                                                                    const selectedOption = productTypeSelect.options[productTypeSelect.selectedIndex];
                                                                    // Log the ID of the selected product type to the console
                                                                    console.log('Selected product type ID:', selectedOption.value);
                                                                });
                                                                function showAttribute() {
                                                                    if (count <= attCount) {
                                                                        count = count + 1;
                                                                        var attCode = '<div class="row">\n\
    <div class="col-lg-4 col-sm-6 col-12">\n\
<div class="mb-3 add-product dropdown-widget">\n\
<label class="form-label">Attribute</label>\n\
<div class="dropdown-textbox">\n\
<div class="d-flex">\n\
<select class="select" name="key" required>\n\
<option value="Power">Power</option>\n\
<option value="Weight">Weight</option>\n\
<option value="Size">Size</option>\n\
</select>\n\
</div>\n\
</div>\n\
</div>\n\
</div>\n\
<div class="col-lg-4 col-sm-6 col-12">\n\
<div class="input-blocks add-product input-widget">\n\
<label class="form-label">Value</label>\n\
                                                <input type="text" class="form-control" name="value"></div></div></div>';
                                                                        document.getElementById("showAttribute").innerHTML = document.getElementById("showAttribute").innerHTML + attCode;
                                                                    } else {
                                                                        alert("You have add maximum " + attCount + " attributes!");
                                                                        return;
                                                                    }
                                                                }
        </script>

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
        </script>

        <script>
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
        </script>


        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const categoryInput = document.getElementById("categoryInput");
                const categoryIdInput = document.getElementById("categoryIdInput");
                const snoPatternInput = document.getElementById("snoPattern");
                const autocompleteList = document.getElementById("category-autocomplete-list");
                let selectedIndex = -1; // Track selected item index

                // Convert JSP category list into JavaScript array
                const categoryList = [
            <c:forEach items="${categoryList}" var="category">
                    {name: "${category.categoryName}", id: "${category.categoryId}", serialPattern: "${category.metadata.serialNumberPattern}"},
            </c:forEach>
                ];

                function showSuggestions(value) {
                    autocompleteList.innerHTML = ""; // Clear old suggestions
                    selectedIndex = -1; // Reset selection

                    if (!value.trim())
                        return; // Stop if input is empty

                    categoryList.forEach((category, index) => {
                        if (category.name.toLowerCase().includes(value.toLowerCase())) {
                            const suggestionItem = document.createElement("div");
                            suggestionItem.textContent = category.name;
                            suggestionItem.setAttribute("data-index", index);
                            suggestionItem.classList.add("autocomplete-item");

                            // Click event for selecting an item
                            suggestionItem.addEventListener("click", function () {
                                selectCategory(category);
                            });

                            autocompleteList.appendChild(suggestionItem);
                        }
                    });
                }

                function selectCategory(category) {
                    categoryInput.value = category.name; // Set input value
                    categoryIdInput.value = category.id; // Store ID in hidden field
                    snoPatternInput.value = category.serialPattern; // Store Serial Number Pattern
                    autocompleteList.innerHTML = ""; // Hide suggestions
                }

                categoryInput.addEventListener("input", function () {
                    showSuggestions(this.value);
                });

                categoryInput.addEventListener("keydown", function (e) {
                    const items = autocompleteList.querySelectorAll(".autocomplete-item");
                    if (items.length === 0)
                        return;

                    if (e.key === "ArrowDown") {
                        selectedIndex = (selectedIndex + 1) % items.length;
                    } else if (e.key === "ArrowUp") {
                        selectedIndex = (selectedIndex - 1 + items.length) % items.length;
                    } else if (e.key === "Enter") {
                        if (selectedIndex > -1) {
                            const selectedItem = items[selectedIndex];
                            const selectedCategory = categoryList.find(c => c.name === selectedItem.textContent);
                            if (selectedCategory)
                                selectCategory(selectedCategory);
                        }
                        e.preventDefault();
                    } else if (e.key === "PageDown") {
                        selectedIndex = Math.min(selectedIndex + 5, items.length - 1);
                    } else if (e.key === "PageUp") {
                        selectedIndex = Math.max(selectedIndex - 5, 0);
                    }

                    // Highlight selected item
                    items.forEach((item, i) => {
                        item.classList.toggle("selected", i === selectedIndex);
                    });

                    // ? **Smooth scrolling for selected item**
                    if (selectedIndex >= 0 && selectedIndex < items.length) {
                        items[selectedIndex].scrollIntoView({block: "nearest", behavior: "smooth"});
                    }
                });

                document.addEventListener("click", function (e) {
                    if (!categoryInput.contains(e.target) && !autocompleteList.contains(e.target)) {
                        autocompleteList.innerHTML = "";
                        selectedIndex = -1;
                    }
                });
            });

        </script>

        <script>
            document.addEventListener("DOMContentLoaded", function () {
                const productTypeInput = document.getElementById("productTypeInput");
                const productTypeIdInput = document.getElementById("productTypeIdInput");
                const autocompleteList = document.getElementById("autocomplete-list");
                let selectedIndex = -1; // Track selected item index

                // Convert JSP product list into JavaScript array
                const productTypeList = [
            <c:forEach items="${productTypeList}" var="productType">
                    {name: "${productType.productTypeName}", id: "${productType.productTypeId}"},
            </c:forEach>
                ];

                // Function to show suggestions
                function showSuggestions(value) {
                    autocompleteList.innerHTML = ""; // Clear old suggestions
                    selectedIndex = -1; // Reset selection

                    if (!value.trim())
                        return; // Stop if input is empty

                    productTypeList.forEach((product, index) => {
                        if (product.name.toLowerCase().includes(value.toLowerCase())) {
                            const suggestionItem = document.createElement("div");
                            suggestionItem.textContent = product.name;
                            suggestionItem.setAttribute("data-index", index);
                            suggestionItem.classList.add("autocomplete-item");

                            // Click event for selecting an item
                            suggestionItem.addEventListener("click", function () {
                                selectProduct(product);
                            });

                            autocompleteList.appendChild(suggestionItem);
                        }
                    });
                }

                // Function to select a product
                function selectProduct(product) {
                    productTypeInput.value = product.name; // Set input value
                    productTypeIdInput.value = product.id; // Store ID in hidden field
                    autocompleteList.innerHTML = ""; // Hide suggestions
                }

                // Handle input event
                productTypeInput.addEventListener("input", function () {
                    showSuggestions(this.value);
                });


                // Handle keyboard navigation
                productTypeInput.addEventListener("keydown", function (e) {
                    const items = autocompleteList.querySelectorAll(".autocomplete-item");
                    if (items.length === 0)
                        return;

                    if (e.key === "ArrowDown") { // Move down
                        selectedIndex = (selectedIndex + 1) % items.length;
                    } else if (e.key === "ArrowUp") { // Move up
                        selectedIndex = (selectedIndex - 1 + items.length) % items.length;
                    } else if (e.key === "Enter") { // Select item
                        if (selectedIndex > -1) {
                            const selectedItem = items[selectedIndex];
                            const selectedProduct = productTypeList.find(p => p.name === selectedItem.textContent);
                            if (selectedProduct)
                                selectProduct(selectedProduct);
                        }
                        e.preventDefault(); // Prevent form submission
                    } else if (e.key === "PageDown") { // Jump forward
                        selectedIndex = Math.min(selectedIndex + 5, items.length - 1);
                    } else if (e.key === "PageUp") { // Jump backward
                        selectedIndex = Math.max(selectedIndex - 5, 0);
                    }

                    // Highlight selected item and scroll into view
                    items.forEach((item, i) => {
                        item.classList.toggle("selected", i === selectedIndex);
                    });

                    if (selectedIndex >= 0 && selectedIndex < items.length) {
                        items[selectedIndex].scrollIntoView({block: "nearest", behavior: "smooth"});
                    }
                });

                // Hide suggestions when clicking outside
                document.addEventListener("click", function (e) {
                    if (!productTypeInput.contains(e.target) && !autocompleteList.contains(e.target)) {
                        autocompleteList.innerHTML = "";
                        selectedIndex = -1;
                    }
                });
            });

        </script>









    </body>

</html>