package com.AvMeditechInventory.tally.xml.export;

import com.AvMeditechInventory.entities.InventoryTransactionReport;
import com.AvMeditechInventory.util.HTTPUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TallyXMLExporter {

    public File exportToXML(List<InventoryTransactionReport> transactionReports, String transactionName) {
        try {
            HTTPUtil.displayRequest("Starting XML export for transaction name: " + transactionName);

            // Step 1: Create a new Document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Step 2: Create root element
            Element rootElement = doc.createElement("ENVELOPE");
            doc.appendChild(rootElement);

            // Step 3: Add header
            Element header = doc.createElement("HEADER");
            rootElement.appendChild(header);

            Element tallyRequest = doc.createElement("TALLYREQUEST");
            tallyRequest.appendChild(doc.createTextNode("Import Data"));
            header.appendChild(tallyRequest);

            // Step 4: Add body with data
            Element body = doc.createElement("BODY");
            rootElement.appendChild(body);

            Element importData = doc.createElement("IMPORTDATA");
            body.appendChild(importData);

            Element requestDesc = doc.createElement("REQUESTDESC");
            importData.appendChild(requestDesc);

            Element reportName = doc.createElement("REPORTNAME");
            reportName.appendChild(doc.createTextNode("Transaction Report"));
            requestDesc.appendChild(reportName);

            // Step 5: Add transaction data dynamically
            Element requestData = doc.createElement("REQUESTDATA");
            importData.appendChild(requestData);

            for (InventoryTransactionReport report : transactionReports) {
                Element tallyMessage = doc.createElement("TALLYMESSAGE");
                requestData.appendChild(tallyMessage);

                Element ledger = doc.createElement("LEDGER");
                ledger.setAttribute("VOUCHER",
                        "purchase".equals(transactionName) ? "Purchase"
                        : "sale".equals(transactionName) ? "Sale"
                        : "sale return".equals(transactionName) ? "Sale Return"
                        : "transfer".equals(transactionName) ? "Internal Transfer In"
                        : "transfer1".equals(transactionName) ? "Internal Transfer Out" : "");

                ledger.setAttribute("Action", "Create");
                tallyMessage.appendChild(ledger);

                Element name = doc.createElement("NAME");
                name.appendChild(doc.createTextNode("LEDGER"));
                ledger.appendChild(name);

                // Add other elements
                Element voucherNo = doc.createElement("VOUCHERNO");
                voucherNo.appendChild(doc.createTextNode(report.getAutoId().toString()));
                ledger.appendChild(voucherNo);

                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
                Element date = doc.createElement("DATE");
                date.appendChild(doc.createTextNode(DATE_FORMAT.format(report.getVoucherDate())));
                ledger.appendChild(date);

                Element companyName = doc.createElement("COMPANYNAME");
                companyName.appendChild(doc.createTextNode(report.getCompanyName()));
                ledger.appendChild(companyName);

                Element reference = doc.createElement("REFERENCE");
                reference.appendChild(doc.createTextNode(report.getReferenceNumber()));
                ledger.appendChild(reference);

                Element remarks = doc.createElement("REMARKS");
                remarks.appendChild(doc.createTextNode(report.getRemarks()));
                ledger.appendChild(remarks);
            }

            // Step 6: Write the content to a temporary XML file with timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            // Use system's default temporary directory
            Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));

            // Ensure the directory exists
            if (!Files.exists(tempDir)) {
                IOException exception = new IOException("Temporary directory does not exist: " + tempDir);
                HTTPUtil.displayException(exception); // Log the exception
                throw exception; // Throw the exception
            }

            // Check if the directory is writable
            if (!Files.isWritable(tempDir)) {
                IOException exception = new IOException("Temporary directory is not writable: " + tempDir);
                HTTPUtil.displayException(exception); // Log the exception
                throw exception; // Throw the exception
            }

            // Create the temporary file in the correct directory
            Path tempFile = Files.createTempFile(tempDir, "TallyExport_" + timestamp, ".xml");

            // Check if the file is readable and writable
            File file = tempFile.toFile();

            // Check if the file is readable
            if (!file.canRead()) {
                HTTPUtil.displayException(new IOException("File is not readable: " + file.getAbsolutePath()));
                throw new IOException("File is not readable: " + file.getAbsolutePath());
            }

            // Check if the file is writable
            if (!file.canWrite()) {
                HTTPUtil.displayException(new IOException("File is not writable: " + file.getAbsolutePath()));
                throw new IOException("File is not writable: " + file.getAbsolutePath());
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);

            transformer.transform(source, result);

            HTTPUtil.displayResponse("XML file successfully created: " + tempFile.toAbsolutePath());

            return file;

        } catch (ParserConfigurationException e) {
            HTTPUtil.displayException(e);
            throw new RuntimeException("Error configuring the XML parser: " + e.getMessage(), e);
        } catch (TransformerException e) {
            HTTPUtil.displayException(e);
            throw new RuntimeException("Error while transforming the XML document: " + e.getMessage(), e);
        } catch (IOException e) {
            HTTPUtil.displayException(e);
            throw new RuntimeException("Error creating temporary file: " + e.getMessage(), e);
        } catch (Exception e) {
            HTTPUtil.displayException(e);
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage(), e);
        }
    }
}
