<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Delivery Challan</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #fffff;
                padding: 20px;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
                margin: 0;
            }
            .container {
                max-width: 1200px;
                margin: 0 auto;
                background-color: #fff;
                position: relative;
                padding: 20px 0; /* Adjust padding for spacing */
                flex: 1;
                display: flex;
                flex-direction: column;
            }
            .header-section {
                text-align: center;
                border-bottom: 2px solid #000;
                padding-bottom: 10px;
                margin-bottom: 0px; /* Adjusted for seamless border */
            }
            .logo {

                width: 130px;  /* Doubled from 80px to 160px */
                height: 130px; /* Maintain square aspect ratio */
                margin-right: 10px;
                margin-left: -10px;
                margin-top: -10px;
            }
            .store-info {
                font-weight: bold;
                margin-bottom: 10px;
            }
            .content-section {
                display: flex;
                justify-content: space-between;
                align-items: stretch;
                position: relative;
                flex: 1;
            }
            .left-section {
                width: 60%;
                padding: 10px;
            }
            .right-section {
                width: 59%;
                padding: 10px;
                margin-left: 4px;
            }

            .right-section .send-to {
                background-color: lightgrey;
                padding: 12px;
                margin-bottom: 10px;
                font-weight: bold;
                width: 104.50%;
                margin-left: -15.50px;
                margin-top: -10px;
                font-size: 40px;
            }
            .divider {
                position: absolute;
                top: 0;
                bottom: 0;
                left: 50%;
                width: 2px;
                background-color: #000;
            }
            .table-bordered {
                border: 2px solid black;
                border-collapse: collapse;
                margin: 0; /* Adjusted for seamless border */
                width: 100%;
                text-align: center;
            }
            .table-bordered th, .table-bordered td {
                border: 2px solid black;
                padding: 8px;
            }
            .table-bordered th {
                background-color: lightgray;
            }
            .footer-section {
                display: grid;
                grid-template-rows: auto auto; /* Two rows: Remarks and Columns */
                gap: 20px; /* Space between rows */
                margin-top: auto;
                padding-top: 10px;
                font-weight: 700;
                margin-left: 30px;
                font-weight: normal; /* Makes text not bold */
                color: #333333; /* Replace with your desired color */
            }

            .remarks-row {
                grid-column: 1 / -1; /* Full-width for Remarks */
                white-space: normal; /* Allow text wrapping */
                font-weight: normal; /* Makes text not bold */
                color: #333333; /* Replace with your desired color */
            }


            .columns-row {
                display: grid;
                grid-template-columns: 1fr 1fr; /* Two equal-width columns */
                gap: 40%; /* Space between columns */
            }

            .column {
                display: flex;
                flex-direction: column; /* Stack content within each column vertically */
            }

            .logo1 {

                width: 130px;  /* Doubled from 80px to 160px */
                height: 130px; /* Maintain square aspect ratio */
                margin-right: 10px;
                margin-left: -10px;
                margin-top: -10px;
            }
        </style>
    </head>
    <body>    
        <div class="container" id="pdfContent">

            <div style="border: 2px solid #000;">
                <div class="header-section">
                    <h3 style="font-size: 30px; font-weight: bold;margin-top: 20px;">${challan.toUpperCase()}</h3>
                </div>
                <div class="content-section">
                    <div class="left-section">
                        <div style="display: flex; align-items: center;">
                            <% String storeName = (String) session.getAttribute("selectedStoreName");
                                if (storeName != null && storeName.equals("AV MEDITECH PVT LTD KAITHAL")) {%>
                            <img src="<%= request.getContextPath()%>/image/logo.jpg" alt="Logo" class="logo">
                            <div>
                                <div style="font-weight: bold; font-size: 25px;">Av Meditech pvt ltd </div>
                                <div style="font-size: 20px;">DSS 308, Sector 20 huda, Kaithal 136027</div>
                                <!--<div style="font-size: 30px;">HUDA SECTOR 20 , KAITHAL 136027</div>-->
                            </div>
                            <% } else if (storeName != null && storeName.equals("LENSE HOME KAITHAL")) {%>
                            <img src="<%= request.getContextPath()%>/image/logowithoutname.jpg" alt="Logo" class="logo2" style="height:130px;width:130px;" //>

                            <div style="margin-left:20px">
                                <div style="font-weight: bold; font-size: 30px;">Lense Home</div>
                                <div style="font-size: 20px;">DSS 309 </div>
                                <div style="font-size: 20px;">Sector 20 huda, Kaithal 136027</div>
                                <!--<div style="font-size: 30px;">KAITHAL-136027</div>-->
                            </div>
                            <% } else if (storeName != null && storeName.equals("AV MEDITECH GURUGRAM")) {%>
                            <img src="<%= request.getContextPath()%>/image/logo.jpg" alt="Logo" class="logo1" style="height: 130px;width:130px">
                            <div>
                                <div style="font-weight: bold; font-size: 25px;">AV MEDITECH PVT. LTD., GGN</div>
                                <div style="font-size: 18px;">#715, 7TH FLOOR, B EMAAR DIGITAL GREEN SEC 61, GOLF COURSE EXT. ROAD, GURUGRAM-122011</div>
                                <!--                                <div style="font-size: 30px;"> EMAAR DIGITAL GREENS, SEC 61,</div>
                                                                <div style="font-size: 30px;">GOLF COURSE EXT.ROAD, GURUGRAM-122011</div>-->
                            </div>
                            <% } else if (storeName != null && storeName.equals("AV MEDITECH KAITHAL")) {%>
                            <img src="<%= request.getContextPath()%>/image/logo.jpg" alt="Logo" class="logo" style="height: 130px;width:130px">
                            <div>
                                <div style="font-weight: bold; font-size: 25px;">AV Meditech</div>
                                <div style="font-size: 20px;"> DSS 308, SECTOR 20, HUDA KAITHAL 136027</div>
                                <!--<div style="font-size: 30px;">HUDA SECTOR 20, KAITHAL-136027</div>-->
                            </div>
                            <% } %>
                        </div>

                        <% if (storeName != null && storeName.equals("LENSE HOME KAITHAL")) { %>
                        <div>
                            <div style="font-size: 20px;"><strong style="font-weight: bold;">MOB NO:</strong> 9728942623</div>
                            <div style="font-size: 20px;"><strong style="font-weight: bold;">E-Mail:</strong> accounts@lensehome.com</div>
                        </div>
                        <% } else if (storeName != null && storeName.equals("AV MEDITECH PVT LTD KAITHAL")) { %>
                        <div>
                            <div style="font-size: 20px;"><strong style="font-weight: bold;">MOB NO:</strong> 9728942623</div>
                            <div style="font-size: 20px;"><strong style="font-weight: bold;">E-Mail:</strong> accounts@avmeditech.com</div>
                        </div>
                        <% } else if (storeName != null && storeName.equals("AV MEDITECH GURUGRAM")) { %>
                        <div>
                            <div style="font-size: 20px;"><strong style="font-weight: bold;">MOB NO:</strong> 9953605079</div>
                            <div style="font-size: 20px;"><strong style="font-weight: bold;">E-Mail:</strong> accounts@avmeditech.com</div>
                        </div>
                        <% } else if (storeName != null && storeName.equals("AV MEDITECH KAITHAL")) { %>
                        <div>
                            <div style="font-size: 20px;"><strong style="font-weight: bold;">MOB NO:</strong> 9812063111</div>
                            <div style="font-size: 20px;"><strong style="font-weight: bold;">E-Mail:</strong> accounts@avmeditech.com</div>
                        </div>
                        <% }%>
                    </div>
                    <div class="divider"><svg height="100%" width="2px"><line x1="0" y1="0" x2="0" y2="100%" style="stroke:rgb(0,0,0);stroke-width:2" /></svg></div> <!-- Divider between AV MEDITECH and Send to sections -->
                    <div class="right-section">
                        <c:set var="saleDate" value="${response.saleDate}" />
                        <fmt:parseDate value="${saleDate}" pattern="yyyy-MM-dd" var="parsedDate" />
                        <fmt:formatDate value="${parsedDate}" pattern="dd-MM-yyyy" var="formattedDate" />

                        <div class="send-to" style="font-size:25px; display: flex; justify-content: space-between;">
                            <span> Ch No: ${response.seqId} </span>
                            <span style="margin-right: 20px;"> Date : ${formattedDate}</span>
                        </div>
    <!--                        <div style="margin-left:-8px; font-weight: bold; font-size: 20px;">Delivery Date: ${response.saleDate}</div> -->
                        <div style="margin-left:-8px; font-size: 20px; font-weight: bold;" >Send to: <span style="font-weight: bold;"></span></div>
                        <div style="margin-left:-8px; font-size: 20px; "><strong></strong> ${response.companyName}</div>
                                <c:if test="${response.saleType != 'Internal Transfer'}">
                            <div style="margin-left:-8px; font-size: 20px"><strong></strong> ${response.streetAddress1}, ${response.streetAddress2}, ${response.city}, ${response.postalCode},  ${response.countryArea}</div>
                                </c:if>

                    </div>
                </div>
                <table class="table table-bordered" style="font-size: 20px;">
                    <thead>
                        <tr class="table-header">
                            <th>Sr No</th>
                            <th>Product</th>
                            <th>Item Description</th>
                            <th>Quantity</th>

                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${responseList}" varStatus="loop">
                            <tr>
                                <td>${loop.index + 1}</td>
                                <td>${item.sku}</td>
                                <td>${item.productName}</td>
                                <td>${item.quantity}</td>

                            </tr>
                        </c:forEach>
                    </tbody>
                    <c:if test="${totalQuantity != 0}">
                        <tbody>
                            <tr>

                                <td></td>
                                <td></td>
                                <td>Total</td>
                                <td>${totalQuantity}</td>

                            </tr>
                        </tbody>
                    </c:if>
                </table>
            </div>
            <div class="footer-section" style="font-size: 25px;">
                <!-- Full-width row for Remarks -->
                <div class="row remarks-row">Remarks: ${response.remarks}</div>

                <!-- Two columns for Prepared By and Checked By -->
                <div class="row columns-row">
                    <div class="column">
                        <div style="font-size:25px">Prepared By: </div>
                        <div style="font-size:25px; position: relative; top: -50%; right: -50%">${response.userCompanyName.toUpperCase()}</div>
                    </div>
                    <div class="column">Checked By:</div>
                </div>
            </div>
        </div>

    </body>
    <script type="text/javascript">
        // Retrieve the store name from the JSP variable
        var storeName = "<%= storeName%>";
        // Print the store name to the JavaScript console
        console.log("Store Name: " + storeName);
    </script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.3.1/jspdf.umd.min.js"></script>
    <script>
        function downloadPDF() {
            const {jsPDF} = window.jspdf;

            // Create a new jsPDF instance with A5 page size
            const doc = new jsPDF({
                orientation: "portrait",
                unit: "mm",
                format: "a5", // Set to A5 size
            });

            // Set appropriate font size and scaling
            const fontSize = 12; // Adjust font size to fit A5 paper
            const scalingFactor = 0.8; // Adjust scaling to fit content

            // Set the font size
            doc.setFontSize(fontSize);

            // Adjust the scaling factor and margins for the content
            doc.html(document.querySelector('#pdfContent'), {

                callback: function (doc) {
                    doc.save('delivery_challan_a5.pdf');
                },
                x: 10, // Set left margin
                y: 10, // Set top margin
                width: 140, // Set content width to fit A5 dimensions (148 mm total width minus margins)
                windowWidth: 800, // Content window width for better scaling
                scale: scalingFactor, // Apply scaling to fit text and content
            });
        }
    </script>

    <script>
        function getBrowserName() {
            const userAgent = navigator.userAgent;
            if (userAgent.includes("Chrome"))
                return "Chrome";
            if (userAgent.includes("Firefox"))
                return "Firefox";
            return "Other";
        }

        function injectPrintStyles(cssRules) {
            const styleSheet = document.createElement("style");
            styleSheet.type = "text/css";
            styleSheet.textContent = cssRules;
            document.head.appendChild(styleSheet);
        }

        function injectPrintStylesForFirefox() {
            const firefoxCSS = `
        @media print {
            @page {
                size: A5;
                margin: 10mm;
            }
            body {
                position: relative;
                min-height: 100%;
                margin: 0;
                padding: 0;
                font-size: 12px;
                -webkit-print-color-adjust: exact;
                print-color-adjust: exact;
            }
            #pdfContent {
                padding-bottom: 40mm;
                width: 140mm;
                margin: 0 auto;
            }
        .table-bordered .table-header {
                color: black;
            }
            .footer-section {
                position: fixed;
                bottom: 10mm;
                left: 0;
                right: 0;
                background-color: white;
            }
            .right-section .send-to {
                color: black;
                background-color: lightgray;
                width: 105.50%;
            }
            .table-bordered th,
            .table-bordered td {
                border: 2px solid black !important;
                padding: 8px;
            }
            .table-bordered th {
                background-color: lightgray !important;
            }
            
            body.firefox-print {
                color: black;
            }
        }
    `;
            injectPrintStyles(firefoxCSS);
        }

        function injectPrintStylesForChrome() {
            const chromeCSS = `
        @media print {
            .table-bordered {
                border: 2px solid black;
                border-collapse: collapse;
                margin: 0;
                width: 100%;
                text-align: center;
            }
            .table-bordered th,
            .table-bordered td {
                border: 2px solid black !important;
                padding: 8px;
            }
            .table-bordered th {
                background-color: lightgray;
            }
            #pdfContent {
                width: 140mm;
            }
            .table-bordered .table-header {
                color: black;
            }
            .right-section .send-to {
                color: black;
                background-color: lightgray;
                width: 105.30%;
            }
            .table-header th {
                background-color: lightgray !important;
            }
        }
    `;
            injectPrintStyles(chromeCSS);
        }

        window.addEventListener("beforeprint", () => {
            const browser = getBrowserName();
            if (browser === "Chrome") {
                console.log("Preparing print layout for Chrome...");
                injectPrintStylesForChrome();
            } else if (browser === "Firefox") {
                console.log("Preparing print layout for Firefox...");
                document.body.classList.add("firefox-print");
                injectPrintStylesForFirefox();
            } else {
                console.log("Browser-specific print handling not available.");
            }
        });

        window.addEventListener("afterprint", () => {
            document.body.classList.remove("firefox-print");
            document.body.style.backgroundColor = ""; // Reset background after print
        });

    </script>

</html>
