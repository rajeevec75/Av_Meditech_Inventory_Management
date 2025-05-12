/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.util;

import com.AvMeditechInventory.dtos.Response;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.ErrorResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.results.SuccessResult;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Repository
public class ServiceDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Result updatePasswordWithStaff(String userEmail, String password, String mobileNumber) {
        try {
            String sqlQuery = "UPDATE account_user SET password = ?, mobile_no = ?  WHERE email = ?";

            // Updating the password in the database
            int rowsAffected = jdbcTemplate.update(sqlQuery, password, mobileNumber, userEmail);

            if (rowsAffected > 0) {
                // Password updated successfully
                return new SuccessResult("Password updated successfully for user with email: " + userEmail);
            } else {
                // No rows affected, possibly user not found
                return new ErrorResult("No user found with the specified email");
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorResult("Failed to update password: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorResult("Failed to update password: " + e.getMessage());
        }
    }

    public Result updateMobileNumberWithStaff(String userEmail, String mobileNumber) {
        try {
            String sqlQuery = "UPDATE account_user SET mobile_no = ? WHERE email = ?";

            // Updating the mobile number in the database
            int rowsAffected = jdbcTemplate.update(sqlQuery, mobileNumber, userEmail);

            if (rowsAffected > 0) {
                // Successfully updated
                return new SuccessResult("Mobile number updated successfully");
            } else {
                // No rows affected, possibly user not found
                return new ErrorResult("No user found with the specified email");
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorResult("Failed to update mobile number: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorResult("Failed to update mobile number: " + e.getMessage());
        }
    }

    public Result updateUserTypeAndCompanyName(String supplierEmail, String companyName, String userType) {
        try {
            String sqlQuery = "UPDATE account_user SET user_type = ?, company_name = ? WHERE email = ?";

            // Updating the user type and company name in the database
            int rowsAffected = jdbcTemplate.update(sqlQuery, userType, companyName, supplierEmail);

            if (rowsAffected > 0) {
                // Successfully updated user type and company name
                return new SuccessResult("User type and company name updated successfully for email: " + supplierEmail);
            } else {
                // No rows affected, possibly user not found
                return new ErrorResult("No user found with the email: " + supplierEmail);
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorResult("Failed to update user type and company name for email: " + supplierEmail + ". Error: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorResult("Failed to update user type and company name for email: " + supplierEmail + ". Error: " + e.getMessage());
        }
    }

    public Result updatePanNumberAndGstNumberAndTinNumberAndDealerTypeAndPlaceOfSupplyAndPartyType(String supplierEmail, String panNumber, String gstNumber, String tinNumber, String dealerType, String placeOfSupply, String partyType) {
        try {
            String sqlQuery = "UPDATE account_user SET pan_number = ?, gst_number = ?, tin_number = ?, dealer_type = ?, place_of_supply = ?, party_type = ? WHERE email = ?";

            // Updating the PAN number, GST number, dealer type, place of supply, and party type in the database
            int rowsAffected = jdbcTemplate.update(sqlQuery, panNumber, gstNumber, tinNumber, dealerType, placeOfSupply, partyType, supplierEmail);

            if (rowsAffected > 0) {
                // Successfully updated PAN number, GST number, dealer type, place of supply, and party type
                return new SuccessResult("PAN number, GST number, Tin Number, dealer type, place of supply, and party type updated successfully for email: " + supplierEmail);
            } else {
                // No rows affected, possibly user not found
                return new ErrorResult("No user found with the email: " + supplierEmail);
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorResult("Failed to update PAN number, GST number, dealer type, place of supply, and party type for email: " + supplierEmail + ". Error: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorResult("Failed to update PAN number, GST number, dealer type, place of supply, and party type for email: " + supplierEmail + ". Error: " + e.getMessage());
        }
    }

    public Result updateGenderAndDateOfBirthAndPanCardNumberAndGstNumberAndTinNumber(String customerEmail,
            String panCardNumber, String gstNumber, String tinNumber, String userCode) {
        try {
            String sqlQuery = "UPDATE account_user SET pan_number = ?, gst_number = ?, "
                    + "tin_number = ?, user_code = ? WHERE email = ?";

            // Updating gender, date of birth, PAN card number, GST number, and TIN number in the database
            int rowsAffected = jdbcTemplate.update(sqlQuery, panCardNumber, gstNumber, tinNumber, userCode, customerEmail);

            if (rowsAffected > 0) {
                // Successfully updated gender, date of birth, PAN card number, GST number, and TIN number
                return new SuccessResult("Gender, date of birth, PAN card number, GST number, and TIN number updated successfully for email: " + customerEmail);
            } else {
                // No rows affected, possibly user not found
                return new ErrorResult("No user found with the email: " + customerEmail);
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorResult("Failed to update gender, date of birth, PAN card number, GST number, and TIN number for email: " + customerEmail + ". Error: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorResult("Failed to update gender, date of birth, PAN card number, GST number, and TIN number for email: " + customerEmail + ". Error: " + e.getMessage());
        }

    }

    public DataResult<Map<String, Object>> getUserDataByEmail(String customerEmail) {
        try {
            String sqlQuery = "SELECT * FROM account_user WHERE email = ?";

            // Retrieve data from the database
            Map<String, Object> userData = jdbcTemplate.queryForMap(sqlQuery, customerEmail);

            // Check if data was found
            if (!userData.isEmpty()) {
                // Successfully retrieved user data
                return new SuccessDataResult<>(userData, "User data retrieved successfully");
            } else {
                // No user found with the given email
                return new ErrorDataResult<>("No user found with the email: " + customerEmail);
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorDataResult<>("Failed to retrieve user data for email: " + customerEmail + ". Error: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorDataResult<>("Failed to retrieve user data for email: " + customerEmail + ". Error: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> checkExistingUser(String customerMobile, String userCode) {
        try {
            String sqlQuery = "select * from account_user where mobile_no = ? or user_code = ?";

            // Retrieve data from the database
            List<Map<String, Object>> userData = jdbcTemplate.queryForList(sqlQuery, customerMobile, userCode);

            // Check if data was found
            if (!userData.isEmpty()) {
                // Successfully retrieved user data
                return new SuccessDataResult<>(userData, "User data retrieved successfully");
            } else {
                // No user found with the given email
                return new ErrorDataResult<>("No user found with the email: " + customerMobile + ", " + userCode);
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorDataResult<>("Failed to retrieve user data for email: " + customerMobile + ". Error: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorDataResult<>("Failed to retrieve user data for email: " + customerMobile + ". Error: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getALLUsers() {
        try {
            String sqlQuery = "SELECT * FROM account_user";

            // Retrieve data from the database
            List<Map<String, Object>> userList = jdbcTemplate.queryForList(sqlQuery);

            // Check if data was found
            if (!userList.isEmpty()) {
                // Successfully retrieved user data
                return new SuccessDataResult<>(userList, "User data retrieved successfully");
            } else {
                // No user data found
                return new ErrorDataResult<>("No user data found in the database");
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorDataResult<>("Failed to retrieve user data: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorDataResult<>("Failed to retrieve user data: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getSaleList() {
        try {
            String sqlQuery = "SELECT sale.*, account_user.first_name, account_user.last_name "
                    + "FROM sale "
                    + "INNER JOIN account_user ON sale.customer_id = account_user.id";

            // Retrieve data from the database
            List<Map<String, Object>> saleList = jdbcTemplate.queryForList(sqlQuery);

            // Check if data was found
            if (!saleList.isEmpty()) {
                // Successfully retrieved sale data
                return new SuccessDataResult<>(saleList, "Sale data retrieved successfully");
            } else {
                // No sale data found
                return new ErrorDataResult<>("No sale data found in the database");
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorDataResult<>("Failed to retrieve sale data: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorDataResult<>("Failed to retrieve sale data: " + e.getMessage());
        }
    }

    public DataResult<Integer> insert_purchase_master(Integer user_id, String purchase_status, BigDecimal sub_total_amount,
            BigDecimal gst_amount, BigDecimal discount_amount, BigDecimal shipping_amount,
            BigDecimal grand_total, String reference_no, Date purchase_date, int channelId, String remarks,
            int purchaseUserId) {
        try {
            String sqlInsert = "INSERT INTO purchase_master (user_id, purchase_status, sub_total_amount, gst_amount, "
                    + "discount_amount, shipping_amount, grand_total, reference_no, purchase_date, channel_id, remarks, purchase_user_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Use PreparedStatement to retrieve generated keys
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, user_id);
                ps.setString(2, purchase_status);
                ps.setBigDecimal(3, sub_total_amount);
                ps.setBigDecimal(4, gst_amount);
                ps.setBigDecimal(5, discount_amount);
                ps.setBigDecimal(6, shipping_amount);
                ps.setBigDecimal(7, grand_total);
                ps.setString(8, reference_no);
                ps.setDate(9, new java.sql.Date(purchase_date.getTime()));
                ps.setInt(10, channelId);
                ps.setString(11, remarks);
                ps.setInt(12, purchaseUserId);
                return ps;
            });

            if (rowsAffected > 0) {
                // Retrieve the generated key using currval
                Integer generatedId = jdbcTemplate.queryForObject("SELECT currval('purchase_master_purchase_id_seq')", Integer.class);
                return new SuccessDataResult<>(generatedId, "Purchase inserted successfully. ID: " + generatedId);
            } else {
                return new ErrorDataResult<>("Failed to insert purchase. No rows affected.");
            }
        } catch (DataAccessException e) {
            return new ErrorDataResult<>("Failed to insert purchase: " + e.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("Failed to insert purchase: " + e.getMessage());
        }
    }

    public DataResult<Integer> insert_purchase_master_items(Integer purchase_id, Integer product_id, Integer quantity,
            BigDecimal cost_price, BigDecimal discount, BigDecimal gst_percent, BigDecimal gst_amount, BigDecimal total_amount) {
        try {
            String sqlInsert = "INSERT INTO purchase_master_items (purchase_id, product_id, quantity, cost_price, discount, gst_percent, gst_amount, total_amount) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            // Use PreparedStatement to insert data and retrieve generated keys
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, purchase_id);
                ps.setInt(2, product_id);
                ps.setInt(3, quantity);
                ps.setBigDecimal(4, cost_price);
                ps.setBigDecimal(5, discount);
                ps.setBigDecimal(6, gst_percent);
                ps.setBigDecimal(7, gst_amount);
                ps.setBigDecimal(8, total_amount);
                return ps;
            });

            if (rowsAffected > 0) {
                // Retrieve the generated key using currval
                Integer generatedId = jdbcTemplate.queryForObject("SELECT currval('purchase_master_items_id_seq')", Integer.class);
                return new SuccessDataResult<>(generatedId, "Purchase inserted successfully. ID: " + generatedId);
            } else {
                return new ErrorDataResult<>("Failed to insert purchase master item. No rows affected.");
            }
        } catch (DataAccessException e) {
            return new ErrorDataResult<>("Failed to insert purchase master item: " + e.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("Failed to insert purchase master item: " + e.getMessage());
        }
    }

    public DataResult<Integer> insert_purchase_item_serial_master(Integer purchase_master_id, String status, String item_serial_number,
            String item_barcode, Date item_serial_expiry_date, Integer sale_id, String batchSerialNo, int channelId) {
        try {
            String sqlInsert = "INSERT INTO purchase_item_serial_master (purchase_master_id, status, item_serial_number, item_barcode, item_serial_expiry_date, sale_id, item_batch, channel_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            int rowsAffected = jdbcTemplate.update(sqlInsert, purchase_master_id, status, item_serial_number,
                    item_barcode, item_serial_expiry_date, sale_id, batchSerialNo, channelId);

            if (rowsAffected > 0) {
                // Retrieve the generated key using currval
                Integer generatedId = jdbcTemplate.queryForObject("SELECT currval('purchase_item_serial_master_purchase_item_serial_master_id_seq')", Integer.class);
                return new SuccessDataResult<>(generatedId, "Purchase item serial master inserted successfully. ID: " + generatedId);
            } else {
                return new ErrorDataResult<>("Failed to insert purchase item serial master. No rows affected.");
            }
        } catch (DataAccessException e) {
            // Specific exception handling for database access errors
            return new ErrorDataResult<>("Database error occurred while inserting purchase item serial master: " + e.getMessage());
        } catch (Exception e) {
            // General exception handling
            return new ErrorDataResult<>("Unexpected error occurred while inserting purchase item serial master: " + e.getMessage());
        }
    }

    public DataResult<Integer> insert_sale(Integer customerId, Date saleDate, BigDecimal subTotal, BigDecimal discount, BigDecimal gst,
            BigDecimal shipping, BigDecimal grandTotal, String reference, String status, int channelId,
            String remarks, int saleUserId, String saleType, int transferChannelId) {
        try {
            String sqlInsert = "INSERT INTO sale (customer_id, sale_date, sub_total, discount, gst, shipping, "
                    + "grand_total, reference, status, channel_id, remarks, sale_user_id, sale_type, transfer_channel_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Use PreparedStatement to retrieve generated keys
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, customerId);
                ps.setDate(2, new java.sql.Date(saleDate.getTime()));
                ps.setBigDecimal(3, subTotal);
                ps.setBigDecimal(4, discount);
                ps.setBigDecimal(5, gst);
                ps.setBigDecimal(6, shipping);
                ps.setBigDecimal(7, grandTotal);
                ps.setString(8, reference);
                ps.setString(9, status);
                ps.setInt(10, channelId);
                ps.setString(11, remarks);
                ps.setInt(12, saleUserId);
                ps.setString(13, saleType);
                ps.setInt(14, transferChannelId);
                return ps;
            });

            if (rowsAffected > 0) {
                // Retrieve the generated key using currval
                Integer generatedId = jdbcTemplate.queryForObject("SELECT currval('sale_sale_id_seq')", Integer.class);
                return new SuccessDataResult<>(generatedId, "Sale inserted successfully. ID: " + generatedId);
            } else {
                return new ErrorDataResult<>("Failed to insert sale. No rows affected.");
            }
        } catch (DataAccessException e) {
            return new ErrorDataResult<>("Failed to insert sale: " + e.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("Failed to insert sale: " + e.getMessage());
        }
    }

    public DataResult<Integer> insert_sale_items(Integer saleId, Integer productId, Integer purchaseSerialId) {
        try {
            String sqlInsert = "INSERT INTO sale_items (sale_id, product_id, purchase_item_serial_id) "
                    + "VALUES (?, ?, ?)";

            int rowsAffected = jdbcTemplate.update(sqlInsert, saleId, productId, purchaseSerialId);

            if (rowsAffected > 0) {
                // Retrieve the generated key using currval
                Integer generatedId = jdbcTemplate.queryForObject("SELECT currval('sale_items_sale_item_id_seq')", Integer.class);
                return new SuccessDataResult<>(generatedId, "Sale inserted successfully. ID: " + generatedId);
            } else {
                return new ErrorDataResult<>("Failed to insert sale item. No rows affected.");
            }
        } catch (DataAccessException e) {
            return new ErrorDataResult<>("Failed to insert sale item: " + e.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("Failed to insert sale item: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getUserDataByUserType(List<String> userTypes) {
        try {
            // Create a placeholder string for the user types
            String placeholders = String.join(",", Collections.nCopies(userTypes.size(), "?"));

            // SQL query with dynamic user types
            String sqlQuery = "SELECT * FROM account_user WHERE user_type IN (" + placeholders + ") AND is_staff = false";

            // Convert userTypes list to an array for the jdbcTemplate method
            Object[] params = userTypes.toArray();

            // Retrieve data from the database
            List<Map<String, Object>> userDataList = jdbcTemplate.queryForList(sqlQuery, params);

            // Check if data was found
            if (!userDataList.isEmpty()) {
                // User data retrieved successfully
                return new SuccessDataResult<>(userDataList, "User data retrieved successfully");
            } else {
                // No user found with the given user types
                return new ErrorDataResult<>("No user found with the specified user types");
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorDataResult<>("Failed to retrieve user data. Error: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorDataResult<>("Failed to retrieve user data. Error: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getProductList() {
        try {
            String sqlQuery = "SELECT * FROM product_product ORDER BY id ASC ";

            // Retrieve data from the database
            List<Map<String, Object>> productList = jdbcTemplate.queryForList(sqlQuery);

            // Check if data was found
            if (!productList.isEmpty()) {
                // Successfully retrieved product data
                return new SuccessDataResult<>(productList, "Product data retrieved successfully");
            } else {
                // No product data found
                return new ErrorDataResult<>("No product data found in the database");
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorDataResult<>("Failed to retrieve product data: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorDataResult<>("Failed to retrieve product data: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getProductDetails(int productId) {
        try {
            String sqlQuery = "SELECT pp.id, pp.name, pp.description, pp.slug, pp.sno_pattern, pp.tracking_serial_no, pp.is_batch, \n"
                    + "pp.is_product_service, ppv.sku FROM product_product pp, product_productvariant ppv where \n"
                    + "pp.id = ppv.product_id and pp.id = ?";

            // Retrieve data from the database
            List<Map<String, Object>> productList = jdbcTemplate.queryForList(sqlQuery, productId);

            // Check if data was found
            if (!productList.isEmpty()) {
                // Successfully retrieved product data
                return new SuccessDataResult<>(productList, "Product data retrieved successfully");
            } else {
                // No product data found
                return new ErrorDataResult<>("No product data found in the database");
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorDataResult<>("Failed to retrieve product data: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorDataResult<>("Failed to retrieve product data: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getCompanyAndEmailList() {
        try {
            String sqlQuery = "SELECT company_name, email, user_code FROM account_user ORDER BY id ASC";

            // Retrieve data from the database
            List<Map<String, Object>> companyList = jdbcTemplate.queryForList(sqlQuery);

            // Check if data was found
            if (!companyList.isEmpty()) {
                // Successfully retrieved company data
                return new SuccessDataResult<>(companyList, "Company data retrieved successfully");
            } else {
                // No company data found
                return new ErrorDataResult<>("No company data found in the database");
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorDataResult<>("Failed to retrieve company data: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorDataResult<>("Failed to retrieve company data: " + e.getMessage());
        }
    }

    public Result updateCompanyNameAndUserType(String email, String companyName, String userType) {
        try {
            String sqlQuery = "UPDATE account_user SET company_name = ?, user_type = ? WHERE email = ?";

            // Updating company name and user type in the database
            int rowsAffected = jdbcTemplate.update(sqlQuery, companyName, userType, email);

            if (rowsAffected > 0) {
                // Successfully updated
                return new SuccessResult("Company name and user type updated successfully for email: " + email);
            } else {
                // No rows affected, possibly user not found
                return new ErrorResult("No user found with the email: " + email);
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorResult("Failed to update company name and user type for email: " + email + ". Error: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorResult("Failed to update company name and user type for email: " + email + ". Error: " + e.getMessage());
        }
    }

    public DataResult<String> getCompanyNameByEmail(String email) {
        try {
            String sqlQuery = "SELECT company_name FROM account_user WHERE email = ?";
            String companyName = jdbcTemplate.queryForObject(sqlQuery, String.class, email);
            if (companyName != null) {
                return new SuccessDataResult<>(companyName, "Company name retrieved successfully");
            } else {
                // No company found for the email
                return new ErrorDataResult<>("No company found for email: " + email);
            }
        } catch (EmptyResultDataAccessException emptyResultException) {
            // No user found with the email
            return new ErrorDataResult<>("No user found with email: " + email);
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorDataResult<>("Failed to retrieve company name for email: " + email + ". Error: " + dataAccessException.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getStockReportProductWise(int channelId, Integer pageNumber, Integer pageSize,
            String productName, String productTypeName, String categoryName) {
        try {
            String baseQuery
                    = "SELECT "
                    + "  product_product.name AS productName, "
                    + "  product_producttype.name AS productType, "
                    + "  product_category.name AS categoryName, "
                    + "  COUNT(CASE "
                    + "    WHEN purchase_item_serial_master.sale_id IS NULL AND purchase_item_serial_master.channel_id = :channelId "
                    + "    THEN 1 END) AS quantity, "
                    + "  product_productvariant.sku AS sku "
                    + "FROM product_product "
                    + "JOIN product_producttype ON product_producttype.id = product_product.product_type_id "
                    + "JOIN product_category ON product_category.id = product_product.category_id "
                    + "JOIN product_productvariant ON product_productvariant.product_id = product_product.id "
                    + "LEFT JOIN purchase_master_items ON purchase_master_items.product_id = product_product.id "
                    + "LEFT JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_master_id = purchase_master_items.id "
                    + "LEFT JOIN purchase_master ON purchase_master.purchase_id = purchase_master_items.purchase_id ";

            String whereClause = "";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("channelId", channelId);

            if (productName != null && !productName.isEmpty()) {
                whereClause += (whereClause.isEmpty() ? "WHERE " : "AND ");
                whereClause += "product_product.name = :productName ";
                params.addValue("productName", productName);
            }

            if (productTypeName != null && !productTypeName.isEmpty()) {
                whereClause += (whereClause.isEmpty() ? "WHERE " : "AND ");
                whereClause += "product_producttype.name = :productTypeName ";
                params.addValue("productTypeName", productTypeName);
            }

            if (categoryName != null && !categoryName.isEmpty()) {
                whereClause += (whereClause.isEmpty() ? "WHERE " : "AND ");
                whereClause += "product_category.name = :categoryName ";
                params.addValue("categoryName", categoryName);
            }

            String groupAndOrder
                    = "GROUP BY product_product.name, product_producttype.name, product_category.name, product_productvariant.sku "
                    + "ORDER BY product_product.name ASC ";

            String pagination = "";
            if (pageNumber != null && pageSize != null && pageNumber > 0 && pageSize > 0) {
                int offset = (pageNumber - 1) * pageSize;
                pagination = "LIMIT :pageSize OFFSET :offset";
                params.addValue("pageSize", pageSize);
                params.addValue("offset", offset);
            }

            // Final query
            String finalQuery = baseQuery + whereClause + groupAndOrder + pagination;

            List<Map<String, Object>> stockReport = namedParameterJdbcTemplate.queryForList(finalQuery, params);

            if (!stockReport.isEmpty()) {
                return new SuccessDataResult<>(stockReport, "Stock report retrieved successfully. Found " + stockReport.size() + " records.");
            } else {
                return new ErrorDataResult<>("No stock report data found for the given criteria.");
            }

        } catch (DataAccessException dae) {
            return new ErrorDataResult<>("Failed to retrieve stock report due to a data access error: " + dae.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("Failed to retrieve stock report: " + e.getMessage());
        }
    }

    public int countTotalStockReportProductWiseByChannelId(int channelId) {
        String sql = "SELECT COUNT(DISTINCT product_product.id) "
                + "FROM product_product "
                + "JOIN product_producttype ON product_producttype.id = product_product.product_type_id "
                + "JOIN product_category ON product_category.id = product_product.category_id "
                + "JOIN purchase_master_items ON purchase_master_items.product_id = product_product.id "
                + "JOIN product_productvariant ON product_productvariant.product_id = product_product.id "
                + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_master_id = purchase_master_items.id "
                + "JOIN purchase_master ON purchase_master.purchase_id = purchase_master_items.purchase_id "
                + "WHERE purchase_item_serial_master.sale_id IS NULL AND purchase_master.channel_id = :channelId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("channelId", channelId);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public DataResult<List<Map<String, Object>>> getStockReportSerialNumberWise(int channelId, Integer pageNumber,
            Integer pageSize, String brand, String productType, Date startDate, Date endDate, String productName,
            String itemBarCode, Integer purchaseItemSerialMasterId) {
        try {
            StringBuilder sql = new StringBuilder("SELECT "
                    + "product_product.description_plaintext AS description, "
                    + "product_producttype.name AS productType, "
                    + "product_category.name AS categoryName, "
                    + "product_product.created_at AS createdAt, product_product.name AS product_name, "
                    + "1 AS quantity, "
                    + "product_productvariant.sku AS sku, "
                    + "purchase_master.purchase_id, purchase_master_items.id AS purchase_master_items_id, "
                    + "purchase_item_serial_master.purchase_item_serial_master_id, "
                    + "purchase_item_serial_master.item_serial_number, "
                    + "purchase_item_serial_master.item_serial_expiry_date, "
                    + "purchase_item_serial_master.item_barcode, purchase_item_serial_master.item_batch "
                    + "FROM purchase_master_items "
                    + "JOIN product_product ON purchase_master_items.product_id = product_product.id "
                    + "JOIN purchase_master ON purchase_master.purchase_id = purchase_master_items.purchase_id "
                    + "JOIN product_producttype ON product_producttype.id = product_product.product_type_id "
                    + "JOIN product_category ON product_category.id = product_product.category_id "
                    + "JOIN product_productvariant ON product_productvariant.product_id = product_product.id "
                    + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_master_id = purchase_master_items.id "
                    + "WHERE purchase_item_serial_master.sale_id IS NULL "
                    + "AND purchase_item_serial_master.channel_id = :channelId "
                    + "AND product_product.is_batch = 'false'");

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("channelId", channelId);

            if (productName != null && !productName.isEmpty()) {
                sql.append(" AND product_product.name LIKE :productName");
                params.addValue("productName", "%" + productName + "%");
            }

            if (itemBarCode != null && !itemBarCode.isEmpty()) {
                sql.append(" AND (purchase_item_serial_master.item_barcode = :itemBarCode OR purchase_item_serial_master.item_serial_number = :itemBarCode)");
                params.addValue("itemBarCode", itemBarCode);
            }

            // New condition for purchaseItemSerialMasterId
            if (purchaseItemSerialMasterId != null) {
                sql.append(" AND purchase_item_serial_master.purchase_item_serial_master_id = :purchaseItemSerialMasterId");
                params.addValue("purchaseItemSerialMasterId", purchaseItemSerialMasterId);
            }

            if (brand != null && !brand.isEmpty()) {
                sql.append(" AND product_category.name = :brandName");
                params.addValue("brandName", brand); // No wildcards needed
            }

            if (productType != null && !productType.isEmpty()) {
                sql.append(" AND product_producttype.name = :productType");
                params.addValue("productType", productType); // No wildcards needed
            }

            if (startDate != null && endDate != null) {
                if (startDate.after(endDate)) {
                    return new ErrorDataResult<>("Start date cannot be after end date.");
                }
                sql.append(" AND purchase_item_serial_master.item_serial_expiry_date BETWEEN :startDate AND :endDate");
                params.addValue("startDate", startDate);
                params.addValue("endDate", endDate);
            }

            sql.append(" ORDER BY purchase_item_serial_master.item_serial_expiry_date");

            if (pageNumber != null && pageSize != null) {
                int offset = (pageNumber - 1) * pageSize;
                sql.append(" LIMIT :pageSize OFFSET :offset");
                params.addValue("pageSize", pageSize);
                params.addValue("offset", offset);
            }

            List<Map<String, Object>> stockReport = namedParameterJdbcTemplate.queryForList(sql.toString(), params);

            if (!stockReport.isEmpty()) {
                return new SuccessDataResult<>(stockReport, "Stock report serial number wise retrieved successfully");
            } else {
                return new ErrorDataResult<>("No stock report serial number wise data found in the database");
            }
        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>("Failed to retrieve stock report serial number wise: " + dataAccessException.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("Failed to retrieve stock report serial number wise: " + e.getMessage());
        }
    }

    public int countTotalStockReportSerialNumberWiseByChannelId(int channelId, String brand, String productType, Date startDate, Date endDate, String productName) {
        String sql = "SELECT COUNT(*) "
                + "FROM purchase_master_items "
                + "JOIN product_product ON purchase_master_items.product_id = product_product.id "
                + "JOIN purchase_master ON purchase_master.purchase_id = purchase_master_items.purchase_id "
                + "JOIN product_producttype ON product_producttype.id = product_product.product_type_id "
                + "JOIN product_category ON product_category.id = product_product.category_id "
                + "JOIN product_productvariant ON product_productvariant.product_id = product_product.id "
                + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_master_id = purchase_master_items.id "
                + "WHERE purchase_item_serial_master.sale_id IS NULL AND purchase_master.channel_id = :channelId";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("channelId", channelId);

        if (productName != null && !productName.isEmpty()) {
            sql += " AND product_product.name LIKE :productName";
            params.addValue("productName", "%" + productName + "%");
        }

        if (brand != null && !brand.isEmpty()) {
            sql += " AND product_category.name LIKE :brandName";
            params.addValue("brandName", "%" + brand + "%");
        }

        if (productType != null && !productType.isEmpty()) {
            sql += " AND product_producttype.name LIKE :productType";
            params.addValue("productType", "%" + productType + "%");
        }

        // Conditionally add date filter
        if (startDate != null && endDate != null) {
            if (startDate.after(endDate)) {

            }
            sql += " AND purchase_item_serial_master.item_serial_expiry_date BETWEEN :startDate AND :endDate";
            params.addValue("startDate", startDate);
            params.addValue("endDate", endDate);
        }

        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public DataResult<Map<String, Object>> getBarcodeFromItemSerialNumber(String itemIdentifier, int channelId) {
        try {
            String sqlQuery = "SELECT 1 AS quantity, product_product.name, product_product.id, product_product.tracking_serial_no, product_product.is_batch, "
                    + "purchase_item_serial_master.item_barcode, purchase_item_serial_master.item_serial_number, "
                    + "purchase_item_serial_master.sale_id, purchase_item_serial_master.purchase_item_serial_master_id, purchase_master_items.discount, purchase_master_items.gst_percent, "
                    + "purchase_master_items.gst_amount, purchase_master_items.total_amount, purchase_master_items.cost_price, "
                    + "purchase_item_serial_master.item_serial_expiry_date "
                    + "FROM purchase_master_items "
                    + "JOIN product_product ON product_product.id = purchase_master_items.product_id "
                    + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_master_id = purchase_master_items.id "
                    + "WHERE (purchase_item_serial_master.item_barcode = ? OR purchase_item_serial_master.item_serial_number = ?) "
                    + "AND purchase_item_serial_master.sale_id IS NULL "
                    + "AND purchase_item_serial_master.channel_id = ? "
                    + "ORDER BY purchase_item_serial_master.item_serial_expiry_date ASC";

            // Execute the SQL query to retrieve item details
            List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sqlQuery, itemIdentifier, itemIdentifier, channelId);

            if (!queryForList.isEmpty()) {
                // Item found, return the details
                return new SuccessDataResult<>(queryForList.get(0), "Item details retrieved successfully");
            } else {
                // Item not found for the provided identifier
                return new ErrorDataResult<>(null, "No matching item found for the provided identifier");
            }
        } catch (DataAccessException e) {
            // Handle Spring DataAccessException separately
            return new ErrorDataResult<>("Error occurred while retrieving item details: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return new ErrorDataResult<>("Unexpected error occurred while retrieving item details: " + e.getMessage());
        }
    }

    public DataResult<Map<String, Object>> getBarcodeFromItemBatchSerialNumber(String itemBarCode, String itemBatch, int channelId) {
        try {
            String sqlQuery = "SELECT 1 AS quantity, product_product.name, product_product.id, product_product.tracking_serial_no, product_product.is_batch,\n"
                    + "purchase_item_serial_master.item_barcode, purchase_item_serial_master.item_serial_number,\n"
                    + "purchase_item_serial_master.sale_id, purchase_item_serial_master.purchase_item_serial_master_id, purchase_master_items.discount, purchase_master_items.gst_percent,\n"
                    + "purchase_master_items.gst_amount, purchase_master_items.total_amount, purchase_master_items.cost_price, "
                    + "purchase_item_serial_master.item_serial_expiry_date  \n"
                    + "FROM purchase_master_items\n"
                    + "JOIN product_product ON product_product.id = purchase_master_items.product_id\n"
                    + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_master_id = purchase_master_items.id\n"
                    + "WHERE purchase_item_serial_master.item_barcode = ? and purchase_item_serial_master.sale_id is null "
                    + "and purchase_item_serial_master.channel_id = ? and purchase_item_serial_master.item_batch = ?";

            // Execute the SQL query to retrieve the barcode
            List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sqlQuery, itemBarCode, channelId, itemBatch);

            if (!queryForList.isEmpty()) {
                // Barcode found, return it
                return new SuccessDataResult<>(queryForList.get(0), "Barcode retrieved successfully");
            } else {
                // Barcode not found for the provided serial number
                return new ErrorDataResult<>(null, "Barcode not found for the provided serial number");
            }
        } catch (DataAccessException e) {
            // Handle Spring DataAccessException separately
            return new ErrorDataResult<>("Error occurred while retrieving barcode: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return new ErrorDataResult<>("Unexpected error occurred while retrieving barcode: " + e.getMessage());
        }
    }

    public DataResult<Map<String, Object>> getPurchaseDataByProductId(Integer productId) {
        try {
            String sqlQuery = "SELECT  product_product.id,  product_product.name, product_product.tracking_serial_no, product_product.is_batch,   \n"
                    + "    purchase_item_serial_master.sale_id,  purchase_master_items.gst_percent,\n"
                    + "    purchase_master_items.cost_price, purchase_master_items.gst_amount \n"
                    + "FROM  product_product\n"
                    + "JOIN  purchase_master_items ON product_product.id = purchase_master_items.product_id\n"
                    + "JOIN  purchase_item_serial_master ON purchase_item_serial_master.purchase_master_id = purchase_master_items.id\n"
                    + "WHERE  product_product.id =  ?";

            // Execute the SQL query to retrieve product and purchase item details
            List<Map<String, Object>> queryResult = jdbcTemplate.queryForList(sqlQuery, productId);

            if (!queryResult.isEmpty()) {
                // Product and purchase item details found, return them
                return new SuccessDataResult<>(queryResult.get(0), "Product and purchase item details retrieved successfully.");
            } else {
                // No data found for the provided product ID
                return new ErrorDataResult<>(null, "No data found for the provided product ID: " + productId);
            }
        } catch (DataAccessException e) {
            // Handle Spring DataAccessException separately
            return new ErrorDataResult<>("A database error occurred while retrieving product and purchase item details: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return new ErrorDataResult<>("An unexpected error occurred while retrieving product and purchase item details: " + e.getMessage());
        }
    }

    public DataResult<Response> getProductById(Integer productId) {
        try {
            DataResult<Map<String, Object>> purchaseDataByProductId = getPurchaseDataByProductId(productId);
            if (!purchaseDataByProductId.isSuccess()) {
                return new ErrorDataResult<>("Failed to retrieve product");
            }
            Map<String, Object> data = purchaseDataByProductId.getData();

            Response response = new Response();
            // Set the fields of the Response object based on the retrieved data
            response.setProductId((Integer) data.get("id"));
            response.setProductName((String) data.get("name"));
            response.setGst((BigDecimal) data.get("gst_percent"));
            response.setCostPrice((BigDecimal) data.get("cost_price"));
            response.setTrackingSerialNo((boolean) data.get("tracking_serial_no"));
            response.setBatchSerialNo((boolean) data.get("is_batch"));

            return new SuccessDataResult<>(response, "Product retrieved successfully");
        } catch (Exception e) {
            return new ErrorDataResult<>("Unexpected error occurred while retrieving product : " + e.getMessage());
        }
    }

    public DataResult<Integer> getProductBySlug(String slug) {
        try {
            String sqlQuery = "SELECT id FROM product_product WHERE slug = ?";

            // Execute the SQL query to retrieve product ID by slug
            List<Integer> queryResult = jdbcTemplate.queryForList(sqlQuery, Integer.class, slug);

            if (!queryResult.isEmpty()) {
                // Product found, return its ID
                return new SuccessDataResult<>(queryResult.get(0), "Product ID retrieved successfully");
            } else {
                // No data found for the provided slug
                return new ErrorDataResult<>(null, "No product found for the provided slug: " + slug);
            }
        } catch (DataAccessException e) {
            // Handle Spring DataAccessException separately
            return new ErrorDataResult<>("Error occurred while retrieving product ID: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return new ErrorDataResult<>("Unexpected error occurred while retrieving product ID: " + e.getMessage());
        }
    }

    public DataResult<Integer> update_purchase_master(Integer purchase_id, BigDecimal sub_total_amount,
            BigDecimal gst_amount, BigDecimal discount_amount, BigDecimal shipping_amount, BigDecimal grand_total,
            int purSeqId) {
        try {
            String sqlUpdate = "UPDATE purchase_master SET sub_total_amount = ?, gst_amount = ?, discount_amount = ?, shipping_amount = ?, grand_total = ?, auto_purchase_id = ? "
                    + "WHERE purchase_id = ?";

            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlUpdate);
                ps.setBigDecimal(1, sub_total_amount);
                ps.setBigDecimal(2, gst_amount);
                ps.setBigDecimal(3, discount_amount);
                ps.setBigDecimal(4, shipping_amount);
                ps.setBigDecimal(5, grand_total);
                ps.setInt(6, purSeqId);
                ps.setInt(7, purchase_id);
                return ps;
            });

            if (rowsAffected > 0) {
                return new SuccessDataResult<>(purchase_id, "Purchase updated successfully. ID: " + purchase_id);
            } else {
                return new ErrorDataResult<>("Failed to update purchase. No rows affected.");
            }
        } catch (DataAccessException e) {
            return new ErrorDataResult<>("Failed to update purchase: " + e.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("Failed to update purchase: " + e.getMessage());
        }
    }

    public DataResult<Integer> update_sale(Integer sale_id, BigDecimal sub_total,
            BigDecimal gst, BigDecimal discount, BigDecimal shipping, BigDecimal grand_total, int saleSeqId) {
        try {
            String sqlUpdate = "UPDATE sale SET sub_total = ?, gst = ?, discount = ?, shipping = ?, grand_total = ?, auto_sale_id = ? WHERE sale_id = ?";

            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlUpdate);
                ps.setBigDecimal(1, sub_total);
                ps.setBigDecimal(2, gst);
                ps.setBigDecimal(3, discount);
                ps.setBigDecimal(4, shipping);
                ps.setBigDecimal(5, grand_total);
                ps.setInt(6, saleSeqId);
                ps.setInt(7, sale_id);
                return ps;
            });

            if (rowsAffected > 0) {
                return new SuccessDataResult<>(sale_id, "Sale updated successfully. ID: " + sale_id);
            } else {
                return new ErrorDataResult<>("Failed to update sale. No rows affected.");
            }
        } catch (DataAccessException e) {
            return new ErrorDataResult<>(e.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>(e.getMessage());
        }
    }

    public DataResult<Map<String, Object>> get_purchase_master_details_by_purchase_id(Integer purchaseId, int channelId) {
        try {
            // Updated SQL query with ORDER BY clause to sort by purchase_id in descending order
            String sqlQuery = "SELECT purchase_master.*, account_user.company_name FROM purchase_master "
                    + "JOIN account_user ON account_user.id = purchase_master.user_id "
                    + "WHERE purchase_id = ? AND channel_id = ? ORDER BY purchase_id DESC";

            // Execute the query and get the result
            Map<String, Object> purchaseDetails = jdbcTemplate.queryForMap(sqlQuery, purchaseId, channelId);

            // Check if the result is not null or empty
            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                return new SuccessDataResult<>(purchaseDetails, "Purchase details retrieved successfully");
            } else {
                return new SuccessDataResult<>(null, "No purchase details found for the given ID");
            }
        } catch (Exception e) {
            // Log the exception (use a proper logging framework in a real application)
            return new ErrorDataResult<>(null, "An error occurred while retrieving purchase details: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> get_purchase_master_items_details_by_purchase_id(Integer purchaseId) {
        try {
            String sqlQuery = "SELECT purchase_master_items.*, product_product.name, product_product.tracking_serial_no "
                    + "FROM purchase_master_items "
                    + "JOIN product_product ON product_product.id = purchase_master_items.product_id "
                    + "WHERE purchase_id = ? "
                    + "ORDER BY product_product.name ";

            List<Map<String, Object>> purchaseDetails = jdbcTemplate.queryForList(sqlQuery, purchaseId);

            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                return new SuccessDataResult<>(purchaseDetails, "Purchase details retrieved successfully");
            } else {
                return new SuccessDataResult<>(null, "No purchase details found for the given ID");
            }
        } catch (DataAccessException e) {
            // Log the exception (use a proper logging framework in a real application)
            return new ErrorDataResult<>(null, "An error occurred while retrieving purchase details: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> get_purchase_item_serial_master_details_by_purchase_master_id(Integer purchaseMasterId) {
        try {
            // Updated SQL query with ORDER BY clause to sort by purchase_master_id in descending order
            String sqlQuery = "SELECT * FROM purchase_item_serial_master WHERE purchase_master_id = ? ORDER BY purchase_master_id DESC";

            // Execute the query and get the result
            List<Map<String, Object>> purchaseDetails = jdbcTemplate.queryForList(sqlQuery, purchaseMasterId);

            // Check if the result is not null or empty
            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                return new SuccessDataResult<>(purchaseDetails, "Purchase item serial master details retrieved successfully");
            } else {
                return new SuccessDataResult<>(null, "No purchase item serial master details found for the given purchase master ID");
            }
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "Failed to retrieve purchase item serial master details: " + e.getMessage());
        }
    }

    public DataResult<Map<String, Object>> getSaleDetailsBySaleId(Integer saleId, int channelId) {
        try {
            // Use a parameterized query to avoid SQL injection
            String sqlQuery = "SELECT sale.*, account_user.company_name, account_user.last_name FROM sale "
                    + "JOIN account_user ON account_user.id = sale.customer_id WHERE sale.sale_id = ? and sale.channel_id = ?";

            // Execute the query with the provided saleId
            Map<String, Object> purchaseDetails = jdbcTemplate.queryForMap(sqlQuery, saleId, channelId);

            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                return new SuccessDataResult<>(purchaseDetails, "Purchase details retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No purchase details found for the given ID.");
            }
        } catch (EmptyResultDataAccessException e) {
            // Specific exception when no results are found
            return new SuccessDataResult<>(null, "No purchase details found for the given ID.");
        } catch (Exception e) {
            System.err.println("An error occurred while retrieving purchase details: " + e.getMessage());
            return new ErrorDataResult<>(null, "An error occurred while retrieving purchase details.");
        }
    }

    public DataResult<List<Map<String, Object>>> getSaleItemsDetailsBySaleId(Integer saleId) {
        try {
            // Use a parameterized query to avoid SQL injection
            String sqlQuery = "SELECT count(sale_items.sale_item_id) quantity, product_product.name, product_product.id, product_product.tracking_serial_no, "
                    + "product_product.is_batch, product_productvariant.sku, purchase_item_serial_master.item_batch "
                    + "FROM sale_items JOIN product_product ON product_product.id = "
                    + "sale_items.product_id JOIN product_productvariant ON product_product.id = product_productvariant.product_id "
                    + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_item_serial_master_id = sale_items.purchase_item_serial_id "
                    + "WHERE sale_items.sale_id = ? group by product_product.name, product_product.tracking_serial_no,  "
                    + "product_product.is_batch, product_productvariant.sku, product_product.id,  "
                    + "purchase_item_serial_master.item_batch "
                    + "ORDER BY product_product.name ";

            // Execute the query with the provided saleId
            List<Map<String, Object>> purchaseDetails = jdbcTemplate.queryForList(sqlQuery, saleId);

            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                return new SuccessDataResult<>(purchaseDetails, "Sale items details retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No Sale items details found for the given ID.");
            }
        } catch (EmptyResultDataAccessException e) {
            // Specific exception when no results are found
            return new SuccessDataResult<>(null, "No Sale items details found for the given ID.");
        } catch (Exception e) {
            System.err.println("An error occurred while retrieving Sale items details: " + e.getMessage());
            return new ErrorDataResult<>(null, "An error occurred while retrieving Sale items details.");
        }
    }

    public DataResult<List<Map<String, Object>>> getPurchaseItemSerialMasterDetailsBySaleItemsId(Integer saleItemsId) {
        try {
            String sqlQuery = "SELECT purchase_item_serial_master.*, sale_items.product_id FROM  purchase_item_serial_master\n"
                    + "JOIN sale_items ON sale_items.purchase_item_serial_id = purchase_item_serial_master.purchase_item_serial_master_id \n"
                    + "WHERE sale_items.sale_id = ?";
            List<Map<String, Object>> purchaseDetails = jdbcTemplate.queryForList(sqlQuery, saleItemsId);

            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                return new SuccessDataResult<>(purchaseDetails, "Purchase item serial master details retrieved successfully");
            } else {
                return new SuccessDataResult<>(purchaseDetails, "No purchase item serial master details found for the given purchase master ID");
            }
        } catch (Exception e) {
            // Log the exception (use a proper logging framework in a real application)
            e.printStackTrace();
            return new ErrorDataResult<>(null, "Failed to retrieve purchase item serial master details: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getPurchaseItemSerialMasterDetailsBySaleReturnId(Integer saleItemsId) {
        try {
            String sqlQuery = "SELECT purchase_item_serial_master.*, sale_return_items.product_id FROM  purchase_item_serial_master\n"
                    + "JOIN sale_return_items ON sale_return_items.purchase_item_serial_id = purchase_item_serial_master.purchase_item_serial_master_id \n"
                    + "WHERE sale_return_items.sale_id = ?";
            List<Map<String, Object>> purchaseDetails = jdbcTemplate.queryForList(sqlQuery, saleItemsId);

            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                return new SuccessDataResult<>(purchaseDetails, "Purchase item serial master details retrieved successfully");
            } else {
                return new SuccessDataResult<>(purchaseDetails, "No purchase item serial master details found for the given purchase master ID");
            }
        } catch (Exception e) {
            // Log the exception (use a proper logging framework in a real application)
            e.printStackTrace();
            return new ErrorDataResult<>(null, "Failed to retrieve purchase item serial master details: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> channel_channel_list() {
        try {
            // Construct the SQL query to fetch channel data
            String sqlQuery = "SELECT * FROM channel_channel";

            // Retrieve data from the database using the constructed query
            List<Map<String, Object>> channelDataList = jdbcTemplate.queryForList(sqlQuery);

            // Check if any channel data is retrieved
            if (channelDataList.isEmpty()) {
                // No channel data found
                return new ErrorDataResult<>("No channel data found.");
            } else {
                // Channel data successfully retrieved
                return new SuccessDataResult<>(channelDataList, "Successfully retrieved channel data.");
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorDataResult<>("Failed to retrieve channel data: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorDataResult<>("An unexpected error occurred: " + e.getMessage());
        }
    }

    public Result insertUserChannels(Integer user_id, Integer channel_id) {
        try {
            String sqlInsert = "INSERT INTO user_channels (user_id, channel_id) VALUES (?, ?)";

            int rowsAffected = jdbcTemplate.update(sqlInsert, user_id, channel_id);

            if (rowsAffected > 0) {
                return new SuccessResult("User channel inserted successfully");
            } else {
                return new ErrorResult("Failed to insert user channel. No rows affected.");
            }
        } catch (DataAccessException e) {
            return new ErrorResult("Failed to insert user channel: " + e.getMessage());
        } catch (Exception e) {
            return new ErrorResult("Failed to insert user channel: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getChannelListByUserId(Integer userId) {
        try {
            // Construct the SQL query to fetch channel data
            String sqlQuery = "SELECT  account_user.id AS user_id,  channel_channel.id AS channel_id, channel_channel.name \n"
                    + "FROM  user_channels\n"
                    + "JOIN  account_user ON account_user.id = user_channels.user_id\n"
                    + "JOIN  channel_channel ON channel_channel.id = user_channels.channel_id\n"
                    + "WHERE  user_channels.user_id = ?";

            // Retrieve data from the database using the constructed query
            List<Map<String, Object>> channelDataList = jdbcTemplate.queryForList(sqlQuery, userId);

            // Check if any channel data is retrieved
            if (channelDataList.isEmpty()) {
                // No channel data found
                return new ErrorDataResult<>("No channel data found for user ID: " + userId);
            } else {
                // Channel data successfully retrieved
                return new SuccessDataResult<>(channelDataList, "Successfully retrieved channel data for user ID: " + userId);
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorDataResult<>("Failed to retrieve channel data: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorDataResult<>("An unexpected error occurred: " + e.getMessage());
        }
    }

    public int deleteChannelForUser(Integer userId) {
        String sqlDelete = "DELETE FROM user_channels WHERE user_id = ?";
        return jdbcTemplate.update(sqlDelete, userId);
    }

    public DataResult<Integer> updateSaleIdForPurchaseItemSerialMaster(Integer saleId, int purchaseSerialId, int channelId) {
        try {
            // Update the sale_id in the purchase_item_serial_master table and return the updated id
            String sqlUpdate = "UPDATE purchase_item_serial_master SET sale_id = ? WHERE purchase_item_serial_master_id = ? and "
                    + "sale_id is null and channel_id = ?  RETURNING purchase_item_serial_master_id";
            Integer updatedId = jdbcTemplate.queryForObject(sqlUpdate, new Object[]{saleId, purchaseSerialId, channelId}, Integer.class);

            if (updatedId != null) {
                return new SuccessDataResult<>(updatedId, "Sale ID updated successfully for item barcode: " + purchaseSerialId);
            } else {
                return new ErrorDataResult<>("No update performed. The item barcode " + purchaseSerialId + " may not exist.");
            }
        } catch (DuplicateKeyException e) {
            return new ErrorDataResult<>("Sale ID " + saleId + " is already assigned to item barcode " + purchaseSerialId);
        } catch (DataAccessException e) {
            return new ErrorDataResult<>("Failed to update sale ID due to a database access issue: " + e.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("An unexpected error occurred while updating the sale ID: " + e.getMessage());
        }
    }

    public DataResult<Integer> updateChannelIdForPurchaseItemSerialMaster(Integer channelId, int purchaseSerialId, int channelId1) {
        try {
            // Update the sale_id in the purchase_item_serial_master table and return the updated id
            String sqlUpdate = "UPDATE purchase_item_serial_master SET channel_id = ? WHERE purchase_item_serial_master_id = ? and sale_id is null and channel_id = ? "
                    + "RETURNING purchase_item_serial_master_id";
            Integer updatedId = jdbcTemplate.queryForObject(sqlUpdate, new Object[]{channelId, purchaseSerialId, channelId1}, Integer.class);

            if (updatedId != null) {
                return new SuccessDataResult<>(updatedId, "Sale ID updated successfully for item barcode: " + purchaseSerialId);
            } else {
                return new ErrorDataResult<>("No update performed. The item barcode " + purchaseSerialId + " may not exist.");
            }
        } catch (DuplicateKeyException e) {
            return new ErrorDataResult<>("Sale ID " + channelId + " is already assigned to item barcode " + purchaseSerialId);
        } catch (DataAccessException e) {
            return new ErrorDataResult<>("Failed to update sale ID due to a database access issue: " + e.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("An unexpected error occurred while updating the sale ID: " + e.getMessage());
        }
    }

    public DataResult<Integer> updateSaleIdForPurchaseItemSerialMaster(Integer saleId, String itemBarCode, String batchSerialNo, int channelId) {
        try {
            // Update the sale_id in the purchase_item_serial_master table and return the updated id
            String sqlUpdate = "UPDATE purchase_item_serial_master SET sale_id = ? WHERE purchase_item_serial_master_id = ("
                    + "select purchase_item_serial_master_id from purchase_item_serial_master where item_barcode = ? and sale_id is null and item_batch = ? and channel_id = ? LIMIT 1) "
                    + "RETURNING purchase_item_serial_master_id";
            Integer updatedId = jdbcTemplate.queryForObject(sqlUpdate, new Object[]{saleId, itemBarCode, batchSerialNo, channelId}, Integer.class);

            if (updatedId != null) {
                return new SuccessDataResult<>(updatedId, "Sale ID updated successfully for item barcode: " + itemBarCode);
            } else {
                return new ErrorDataResult<>("No update performed. The item barcode " + itemBarCode + " may not exist.");
            }
        } catch (DuplicateKeyException e) {
            return new ErrorDataResult<>("Sale ID " + saleId + " is already assigned to item barcode " + itemBarCode);
        } catch (DataAccessException e) {
            return new ErrorDataResult<>("Failed to update sale ID due to a database access issue: " + e.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("An unexpected error occurred while updating the sale ID: " + e.getMessage());
        }
    }

    public DataResult<Integer> updateChannelIdForPurchaseItemSerialMaster(Integer channelId, String itemBarCode, String batchSerialNo, int channelId1) {
        try {
            // Update the sale_id in the purchase_item_serial_master table and return the updated id
            String sqlUpdate = "UPDATE purchase_item_serial_master SET channel_id = ? WHERE purchase_item_serial_master_id = ("
                    + "select purchase_item_serial_master_id from purchase_item_serial_master where item_barcode = ? and sale_id is null and item_batch = ? and channel_id = ? LIMIT 1) "
                    + "RETURNING purchase_item_serial_master_id";
            Integer updatedId = jdbcTemplate.queryForObject(sqlUpdate, new Object[]{channelId, itemBarCode, batchSerialNo, channelId1}, Integer.class);

            if (updatedId != null) {
                return new SuccessDataResult<>(updatedId, "Sale ID updated successfully for item barcode: " + itemBarCode);
            } else {
                return new ErrorDataResult<>("No update performed. The item barcode " + itemBarCode + " may not exist.");
            }
        } catch (DuplicateKeyException e) {
            return new ErrorDataResult<>("Sale ID " + channelId + " is already assigned to item barcode " + itemBarCode);
        } catch (DataAccessException e) {
            return new ErrorDataResult<>("Failed to update sale ID due to a database access issue: " + e.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("An unexpected error occurred while updating the sale ID: " + e.getMessage());
        }
    }

    public DataResult<BigDecimal> calculateTotalSaleAmount(Date fromDate, Date toDate, int channelId) {
        String sqlQuery = "SELECT SUM(grand_total) FROM sale WHERE sale_date BETWEEN ? AND ? and channel_id = ?";
        List<Object> queryParams = new ArrayList<>();
        queryParams.add(new java.sql.Date(fromDate.getTime()));
        queryParams.add(new java.sql.Date(toDate.getTime()));
        queryParams.add(channelId);

        try {
            // Query for the sum, which may return null if there are no rows
            BigDecimal totalSaleAmount = Optional.ofNullable(
                    jdbcTemplate.queryForObject(sqlQuery, queryParams.toArray(), BigDecimal.class)
            ).orElse(BigDecimal.ZERO);

            // Assuming SuccessDataResult is a custom class to wrap the response
            return new SuccessDataResult<>(totalSaleAmount, "Total sale amount calculated successfully.");
        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while calculating the total sale amount: " + e.getMessage());
        }
    }

    public DataResult<Integer> calculateUser(String userType, Date fromDate, Date toDate) {
        String sqlQuery = "SELECT COUNT(id) FROM account_user WHERE user_type = ? AND date_joined BETWEEN ? AND ?";
        List<Object> queryParams = new ArrayList<>();
        queryParams.add(userType);
        queryParams.add(new java.sql.Date(fromDate.getTime()));
        queryParams.add(new java.sql.Date(toDate.getTime()));

        try {
            // Query for the count, which may return null if there are no rows
            Long totalCustomerCount = Optional.ofNullable(
                    jdbcTemplate.queryForObject(sqlQuery, queryParams.toArray(), Long.class)
            ).orElse(0L);

            // Convert Long to Integer
            Integer totalCustomerCountInt = totalCustomerCount.intValue();

            // Assuming SuccessDataResult is a custom class to wrap the response
            return new SuccessDataResult<>(totalCustomerCountInt, "Total customer count calculated successfully.");
        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while calculating the total customer count: " + e.getMessage());
        }
    }

    public DataResult<Integer> calculateTotalPurchase(Date fromDate, Date toDate, int channelId) {
        String sqlQuery = "SELECT COUNT(purchase_id) FROM purchase_master WHERE purchase_date BETWEEN ? AND ? "
                + "and channel_id = ?";
        Object[] queryParams = new Object[]{new java.sql.Date(fromDate.getTime()),
            new java.sql.Date(toDate.getTime()), channelId};

        try {
            // Query for the count, which may return null if there are no rows
            Long totalPurchaseCount = Optional.ofNullable(
                    jdbcTemplate.queryForObject(sqlQuery, queryParams, Long.class)
            ).orElse(0L);

            // Convert Long to Integer
            Integer totalPurchaseCountInt = totalPurchaseCount.intValue();

            // Assuming SuccessDataResult is a custom class to wrap the response
            return new SuccessDataResult<>(totalPurchaseCountInt, "Total purchase count calculated successfully.");
        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while calculating the total purchase count: " + e.getMessage());
        }
    }

    public DataResult<Integer> calculateTotalSale(Date fromDate, Date toDate, int channelId) {
        String sqlQuery = "SELECT COUNT(sale_id) FROM sale WHERE sale_date BETWEEN ? AND ? and channel_id = ?";
        Object[] queryParams = new Object[]{new java.sql.Date(fromDate.getTime()),
            new java.sql.Date(toDate.getTime()), channelId};

        try {
            // Query for the count, which may return null if there are no rows
            Long totalSaleCount = Optional.ofNullable(
                    jdbcTemplate.queryForObject(sqlQuery, queryParams, Long.class)
            ).orElse(0L);

            // Convert Long to Integer
            Integer totalSaleCountInt = totalSaleCount.intValue();

            // Assuming SuccessDataResult is a custom class to wrap the response
            return new SuccessDataResult<>(totalSaleCountInt, "Total sale count calculated successfully.");
        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while calculating the total sale count: " + e.getMessage());
        }
    }

    public DataResult<BigDecimal> calculateTotalPurchaseAmount(Date fromDate, Date toDate, int channelId) {
        String sqlQuery = "SELECT SUM(grand_total) FROM purchase_master WHERE purchase_date BETWEEN ? AND ? "
                + "and channel_id = ?";
        Object[] queryParams = new Object[]{new java.sql.Date(fromDate.getTime()),
            new java.sql.Date(toDate.getTime()), channelId};

        try {
            // Query for the sum, which may return null if there are no rows
            BigDecimal totalPurchaseAmount = Optional.ofNullable(
                    queryParams.length > 0
                            ? jdbcTemplate.queryForObject(sqlQuery, queryParams, BigDecimal.class)
                            : jdbcTemplate.queryForObject(sqlQuery, BigDecimal.class)
            ).orElse(BigDecimal.ZERO);

            // Assuming DataResult is a custom class to wrap the response
            return new SuccessDataResult<>(totalPurchaseAmount, "Total purchase amount calculated successfully.");
        } catch (Exception e) {
            // Log the exception here if necessary
            return new ErrorDataResult<>("An error occurred while calculating the total purchase amount.");
        }

    }

    public DataResult<List<Map<String, Object>>> userChannelList(String userEmail) {
        try {
            String sqlQuery = "SELECT cc.* FROM user_channels uc, channel_channel cc, account_user au where "
                    + "cc.id = uc.channel_id and au.id = uc.user_id and au.email = ?";

            // Retrieve data from the database
            List<Map<String, Object>> userList = jdbcTemplate.queryForList(sqlQuery, userEmail);

            // Check if data was found
            if (!userList.isEmpty()) {
                // Successfully retrieved user data
                return new SuccessDataResult<>(userList, "User data retrieved successfully");
            } else {
                // No user data found
                return new ErrorDataResult<>("No user data found in the database");
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorDataResult<>("Failed to retrieve user data: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorDataResult<>("Failed to retrieve user data: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> channelList() {
        try {
            String sqlQuery = "SELECT * FROM channel_channel";

            // Retrieve data from the database
            List<Map<String, Object>> userList = jdbcTemplate.queryForList(sqlQuery);

            // Check if data was found
            if (!userList.isEmpty()) {
                // Successfully retrieved user data
                return new SuccessDataResult<>(userList, "User data retrieved successfully");
            } else {
                // No user data found
                return new ErrorDataResult<>("No user data found in the database");
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorDataResult<>("Failed to retrieve user data: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorDataResult<>("Failed to retrieve user data: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> purchaseMonthRecord(Date fromDate, Date toDate, int channelId) {
        try {
            String sqlQuery = "SELECT TO_CHAR(DATE_TRUNC('month', purchase_date), 'Mon') AS month1, "
                    + "SUM(grand_total) AS total_amount FROM purchase_master where purchase_date BETWEEN "
                    + "? AND ? and channel_id = ? GROUP BY month1 ORDER BY month1";

            // Retrieve data from the database
            List<Map<String, Object>> data = jdbcTemplate.queryForList(sqlQuery, new java.sql.Date(fromDate.getTime()),
                    new java.sql.Date(toDate.getTime()), channelId);

            // Check if data was found
            if (!data.isEmpty()) {
                // Successfully retrieved user data
                return new SuccessDataResult<>(data, "Purchase data retrieved successfully");
            } else {
                // No user data found
                return new ErrorDataResult<>("No purchase data found in the database");
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorDataResult<>("Failed to retrieve purchase data: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorDataResult<>("Failed to retrieve purchase data: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> saleMonthRecord(Date fromDate, Date toDate, int channelId) {
        try {
            String sqlQuery = "SELECT TO_CHAR(DATE_TRUNC('month', sale_date), 'Mon') AS month1, "
                    + "SUM(grand_total) AS total_amount FROM sale where sale_date BETWEEN "
                    + "? AND ? and channel_id = ? GROUP BY month1 ORDER BY month1";

            // Retrieve data from the database
            List<Map<String, Object>> data = jdbcTemplate.queryForList(sqlQuery, new java.sql.Date(fromDate.getTime()),
                    new java.sql.Date(toDate.getTime()), channelId);

            // Check if data was found
            if (!data.isEmpty()) {
                // Successfully retrieved user data
                return new SuccessDataResult<>(data, "Sale data retrieved successfully");
            } else {
                // No user data found
                return new ErrorDataResult<>("No sale data found in the database");
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorDataResult<>("Failed to retrieve sale data: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorDataResult<>("Failed to retrieve sale data: " + e.getMessage());
        }
    }

    public DataResult<Map<String, Object>> getStockFromBarcodeNumber(String itemIdentifier, int customerId, int channelId) {
        try {
            String sqlQuery = "SELECT 1 AS quantity, product_product.name, product_product.id, product_product.tracking_serial_no, product_product.is_batch, "
                    + "purchase_item_serial_master.item_barcode, purchase_item_serial_master.item_serial_number, "
                    + "purchase_item_serial_master.sale_id, purchase_item_serial_master.item_serial_expiry_date, "
                    + "purchase_item_serial_master.purchase_item_serial_master_id "
                    + "FROM sale_items "
                    + "JOIN product_product ON product_product.id = sale_items.product_id "
                    + "JOIN sale ON sale.sale_id = sale_items.sale_id "
                    + "JOIN purchase_item_serial_master ON purchase_item_serial_master.sale_id = sale_items.sale_item_id "
                    + "WHERE (purchase_item_serial_master.item_barcode = ? OR purchase_item_serial_master.item_serial_number = ?) "
                    + "AND purchase_item_serial_master.sale_id IS NOT NULL "
                    + "AND purchase_item_serial_master.channel_id = ? "
                    + "ORDER BY purchase_item_serial_master.item_serial_expiry_date ASC";

            // Execute the SQL query to retrieve the stock details
            List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sqlQuery, itemIdentifier, itemIdentifier, channelId);

            if (!queryForList.isEmpty()) {
                // Barcode or serial number found, return it
                return new SuccessDataResult<>(queryForList.get(0), "Stock details retrieved successfully");
            } else {
                // Barcode or serial number not found
                return new ErrorDataResult<>(null, "No matching stock found for the provided identifier");
            }
        } catch (DataAccessException e) {
            // Handle Spring DataAccessException separately
            return new ErrorDataResult<>("Error occurred while retrieving stock details: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return new ErrorDataResult<>("Unexpected error occurred while retrieving stock details: " + e.getMessage());
        }
    }

    public DataResult<Map<String, Object>> getStockFromBarcodeNumber(String itemBarCode, int customerId, String batchSerialNo) {
        try {
            String sqlQuery = "SELECT 1 AS quantity, product_product.name, product_product.id, product_product.tracking_serial_no, product_product.is_batch,\n"
                    + "purchase_item_serial_master.item_barcode, purchase_item_serial_master.item_serial_number,\n"
                    + "purchase_item_serial_master.sale_id, "
                    + "purchase_item_serial_master.item_serial_expiry_date, purchase_item_serial_master.purchase_item_serial_master_id  \n"
                    + "FROM sale_items\n"
                    + "JOIN product_product ON product_product.id = sale_items.product_id\n"
                    + "JOIN sale ON sale.sale_id = sale_items.sale_id \n"
                    + "JOIN purchase_item_serial_master ON purchase_item_serial_master.sale_id = sale_items.sale_item_id\n"
                    + "WHERE purchase_item_serial_master.item_barcode = ? and purchase_item_serial_master.item_batch = ? and purchase_item_serial_master.sale_id is not null ";

            // Execute the SQL query to retrieve the barcode
            List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sqlQuery, itemBarCode, batchSerialNo);

            if (!queryForList.isEmpty()) {
                // Barcode found, return it
                return new SuccessDataResult<>(queryForList.get(0), "Barcode retrieved successfully");
            } else {
                // Barcode not found for the provided serial number
                return new ErrorDataResult<>(null, "Barcode not found for the provided serial number");
            }
        } catch (DataAccessException e) {
            // Handle Spring DataAccessException separately
            return new ErrorDataResult<>("Error occurred while retrieving barcode: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return new ErrorDataResult<>("Unexpected error occurred while retrieving barcode: " + e.getMessage());
        }
    }

    public DataResult<Integer> insert_sale_return(Integer customerId, Date returnDate, BigDecimal subTotal, BigDecimal gst,
            BigDecimal grandTotal, String reference, int channelId, String remarks, int returnUserId) {
        try {
            String sqlInsert = "INSERT INTO sale_return (customer_id, return_date, amount, gst_amount, "
                    + "total_amount, reference_no, remarks, channel_id, return_user_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            // Use PreparedStatement to retrieve generated keys
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, customerId);
                ps.setDate(2, new java.sql.Date(returnDate.getTime()));
                ps.setBigDecimal(3, subTotal);
                ps.setBigDecimal(4, gst);
                ps.setBigDecimal(5, grandTotal);
                ps.setString(6, reference);
                ps.setString(7, remarks);
                ps.setInt(8, channelId);
                ps.setInt(9, returnUserId);
                return ps;
            });

            if (rowsAffected > 0) {
                // Retrieve the generated key using currval
                Integer generatedId = jdbcTemplate.queryForObject("SELECT currval('sale_return_id_seq')", Integer.class);
                return new SuccessDataResult<>(generatedId, "Sale inserted successfully. ID: " + generatedId);
            } else {
                return new ErrorDataResult<>("Failed to insert sale. No rows affected.");
            }
        } catch (DataAccessException e) {
            return new ErrorDataResult<>("Failed to insert sale");
        } catch (Exception e) {
            return new ErrorDataResult<>("Failed to insert sale");
        }
    }

    public DataResult<Integer> insert_sale_return_items(Integer saleReturnId, Integer productId, Integer saleId, Integer purchaseItemSerialId) {
        try {
            String sqlInsert = "INSERT INTO sale_return_items (sale_return_id, product_id, sale_id, purchase_item_serial_id) "
                    + "VALUES (?, ?, ?, ?)";

            int rowsAffected = jdbcTemplate.update(sqlInsert, saleReturnId, productId, saleId, purchaseItemSerialId);

            if (rowsAffected > 0) {
                // Retrieve the generated key using currval
                Integer generatedId = jdbcTemplate.queryForObject("SELECT currval('sale_return_item_id_seq')", Integer.class);
                return new SuccessDataResult<>(generatedId, "Sale inserted successfully. ID: " + generatedId);
            } else {
                return new ErrorDataResult<>("Failed to insert sale item. No rows affected.");
            }
        } catch (DataAccessException e) {
            return new ErrorDataResult<>("Failed to insert sale item: " + e.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("Failed to insert sale item: " + e.getMessage());
        }
    }

    public DataResult<Integer> updateSaleIdForPurchaseItemSerialMaster(int purchaseItemSerialId) {
        try {
            // Update the sale_id in the purchase_item_serial_master table and return the updated id
            String sqlUpdate = "UPDATE purchase_item_serial_master SET sale_id = null WHERE purchase_item_serial_master_id = ? RETURNING purchase_item_serial_master_id";
            Integer updatedId = jdbcTemplate.queryForObject(sqlUpdate, new Object[]{purchaseItemSerialId}, Integer.class);

            if (updatedId != null) {
                return new SuccessDataResult<>(updatedId, "Sale ID updated successfully for item barcode: " + purchaseItemSerialId);
            } else {
                return new ErrorDataResult<>("No update performed. The item barcode " + purchaseItemSerialId + " may not exist.");
            }
        } catch (DuplicateKeyException e) {
            return new ErrorDataResult<>("Sale ID is already assigned to item barcode " + purchaseItemSerialId);
        } catch (DataAccessException e) {
            return new ErrorDataResult<>("Failed to update sale ID due to a database access issue");
        } catch (Exception e) {
            return new ErrorDataResult<>("An unexpected error occurred while updating the sale ID");
        }
    }

    public DataResult<Integer> update_sale_return(Integer sale_id, BigDecimal sub_total,
            BigDecimal gst, BigDecimal grand_total, int saleReturnIdSeq) {
        try {
            String sqlUpdate = "UPDATE sale_return SET amount = ?, gst_amount = ?, total_amount = ?, "
                    + "auto_sale_return_id = ? WHERE id = ?";

            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlUpdate);
                ps.setBigDecimal(1, sub_total);
                ps.setBigDecimal(2, gst);
                ps.setBigDecimal(3, grand_total);
                ps.setInt(4, saleReturnIdSeq);
                ps.setInt(5, sale_id);
                return ps;
            });

            if (rowsAffected > 0) {
                return new SuccessDataResult<>(sale_id, "Sale updated successfully. ID: " + sale_id);
            } else {
                return new ErrorDataResult<>("Failed to update sale. No rows affected.");
            }
        } catch (DataAccessException e) {
            return new ErrorDataResult<>("Failed to update sale due to a database access issue: " + e.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("An unexpected error occurred while updating the sale: " + e.getMessage());
        }
    }

    public DataResult<Map<String, Object>> getSaleReturnDetailsBySaleId(Integer saleReturnId, int channelId) {
        try {
            // Use a parameterized query to avoid SQL injection
            String sqlQuery = "SELECT sale_return.*, account_user.company_name, account_user.last_name FROM sale_return "
                    + "JOIN account_user ON account_user.id = sale_return.customer_id WHERE sale_return.id = ? "
                    + "and sale_return.channel_id = ?";

            // Execute the query with the provided saleId
            Map<String, Object> purchaseDetails = jdbcTemplate.queryForMap(sqlQuery, saleReturnId, channelId);

            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                return new SuccessDataResult<>(purchaseDetails, "Purchase details retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No purchase details found for the given ID.");
            }
        } catch (EmptyResultDataAccessException e) {
            // Specific exception when no results are found
            return new SuccessDataResult<>(null, "No purchase details found for the given ID.");
        } catch (Exception e) {
            System.err.println("An error occurred while retrieving purchase details: " + e.getMessage());
            return new ErrorDataResult<>(null, "An error occurred while retrieving purchase details.");
        }
    }

    public DataResult<List<Map<String, Object>>> getPurchaseItemSerialMasterDetailsBySaleReturnItemsId(Integer saleItemsId) {
        try {
            String sqlQuery = "select pc.*, pp.name, pp.id from purchase_item_serial_master pc, sale_return_items si, product_product pp where \n"
                    + "pc.purchase_item_serial_master_id = si.purchase_item_serial_id and si.product_id = pp.id and \n"
                    + "si.sale_return_id = ? order by si.product_id desc";
            List<Map<String, Object>> purchaseDetails = jdbcTemplate.queryForList(sqlQuery, saleItemsId);

            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                return new SuccessDataResult<>(purchaseDetails, "Purchase item serial master details retrieved successfully");
            } else {
                return new SuccessDataResult<>(null, "No purchase item serial master details found for the given purchase master ID");
            }
        } catch (Exception e) {
            // Log the exception (use a proper logging framework in a real application)
            e.printStackTrace();
            return new ErrorDataResult<>(null, "Failed to retrieve purchase item serial master details: " + e.getMessage());
        }
    }

    public DataResult<Integer> insertStockMovmentHistory(Integer purchaseItemSerialId, Integer productId, String actionType, Date movementDate, Integer actionId) {
        try {
            String sqlInsert = "INSERT INTO stock_movement_history (purchase_item_serial_id, product_id, action_type, movement_date, action_id) VALUES (?, ?, ?, ?, ?)";

            // Updating the query with the correct parameters
            int rowsAffected = jdbcTemplate.update(sqlInsert, purchaseItemSerialId, productId, actionType, movementDate, actionId);

            if (rowsAffected > 0) {
                // Retrieve the generated key using currval
                Integer generatedId = jdbcTemplate.queryForObject("SELECT currval('stock_movement_id_seq')", Integer.class);
                return new SuccessDataResult<>(generatedId, "Stock movement inserted successfully. ID: " + generatedId);
            } else {
                return new ErrorDataResult<>("Failed to insert stock movement. No rows affected.");
            }
        } catch (DataAccessException e) {
            return new ErrorDataResult<>("Failed to insert stock movement");
        } catch (Exception e) {
            return new ErrorDataResult<>("Failed to insert stock movement");
        }
    }

    public DataResult<List<Map<String, Object>>> getInventoryTransactionReportByPurchase(Date startDate,
            Date endDate, String productName, String productTypeName, String categoryName, int channelId) {
        try {
            // Base query with company_name subquery
            StringBuilder sqlQuery = new StringBuilder(
                    "SELECT purchase_master.auto_purchase_id, purchase_master.purchase_id, purchase_master.purchase_date, purchase_master.reference_no, purchase_master.remarks, "
                    + "(SELECT account_user.company_name FROM account_user WHERE account_user.id = purchase_master.user_id) AS company_name "
                    + "FROM purchase_master "
                    + "WHERE purchase_master.channel_id = ? and purchase_master.purchase_id IN ("
                    + "    SELECT purchase_master_items.purchase_id "
                    + "    FROM purchase_master_items "
                    + "    WHERE purchase_master_items.id IN ("
                    + "        SELECT purchase_item_serial_master.purchase_master_id "
                    + "        FROM purchase_item_serial_master "
                    + "        WHERE purchase_item_serial_master.purchase_master_id IS NOT NULL "
                    + "    ) ");

            // Prepare parameters list
            List<Object> parameters = new ArrayList<>();
            parameters.add(channelId);

            // Optional filters for product, product type, and category
            if (productName != null && !productName.isEmpty()) {
                sqlQuery.append("AND purchase_master_items.product_id IN ("
                        + "    SELECT product_product.id "
                        + "    FROM product_product "
                        + "    WHERE product_product.name = ? "
                        + ") ");
                parameters.add(productName);
            }

            if (productTypeName != null && !productTypeName.isEmpty()) {
                sqlQuery.append("AND purchase_master_items.product_id IN ("
                        + "    SELECT product_product.id "
                        + "    FROM product_product "
                        + "    WHERE product_product.product_type_id = ("
                        + "        SELECT product_producttype.id "
                        + "        FROM product_producttype "
                        + "        WHERE product_producttype.name = ? "
                        + "    ) "
                        + ") ");
                parameters.add(productTypeName);
            }

            if (categoryName != null && !categoryName.isEmpty()) {
                sqlQuery.append("AND purchase_master_items.product_id IN ("
                        + "    SELECT product_product.id "
                        + "    FROM product_product "
                        + "    WHERE product_product.category_id = ("
                        + "        SELECT product_category.id "
                        + "        FROM product_category "
                        + "        WHERE product_category.name = ? "
                        + "    ) "
                        + ") ");
                parameters.add(categoryName);
            }

            // Date range condition
            if (startDate != null && endDate != null) {
                sqlQuery.append(") AND purchase_master.purchase_date BETWEEN ? AND ? ");
                parameters.add(new java.sql.Date(startDate.getTime())); // Convert to java.sql.Date
                parameters.add(new java.sql.Date(endDate.getTime())); // Convert to java.sql.Date
            } else {
                sqlQuery.append(") "); // Close the inner WHERE clause if no date range is provided
            }

            // Execute the query
            List<Map<String, Object>> purchaseTransactionReport = jdbcTemplate.queryForList(sqlQuery.toString(), parameters.toArray());

            // Return result
            if (purchaseTransactionReport != null && !purchaseTransactionReport.isEmpty()) {
                return new SuccessDataResult<>(purchaseTransactionReport, "Purchase transaction report retrieved successfully.");
            } else {
                return new SuccessDataResult<>(Collections.emptyList(), "No purchase transaction details found for the given filters and date range.");
            }
        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>(null, "Failed to retrieve purchase transaction report: " + dataAccessException.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getInventoryTransactionReportBySale(Date startDate, Date endDate,
            String productName, String productTypeName, String categoryName, String saleType, int channelId) {
        try {
            // Base query
            StringBuilder sqlQuery = new StringBuilder(
                    "SELECT sale.auto_sale_id, sale.sale_id, sale.sale_date, sale.reference, sale.remarks, cc.name, "
                    + "(SELECT account_user.company_name FROM account_user WHERE account_user.id = sale.customer_id) AS company_name "
                    + "FROM sale sale, channel_channel cc "
                    + "WHERE sale.sale_type = ? and sale.transfer_channel_id = cc.id and sale.channel_id = ? and sale.sale_id IN ("
                    + "    SELECT sale_items.sale_id "
                    + "    FROM sale_items where sale_items.sale_id is not null "
                    + "     ");

            // Prepare parameters list
            List<Object> parameters = new ArrayList<>();
            parameters.add(saleType);
            parameters.add(channelId);

            // Optional filters
            if (productName != null && !productName.isEmpty()) {
                sqlQuery.append(
                        "AND sale_items.product_id IN ("
                        + "    SELECT product_product.id "
                        + "    FROM product_product "
                        + "    WHERE product_product.name = ? "
                        + ") ");
                parameters.add(productName);
            }
            if (productTypeName != null && !productTypeName.isEmpty()) {
                sqlQuery.append(
                        "AND sale_items.product_id IN ("
                        + "    SELECT product_product.id "
                        + "    FROM product_product "
                        + "    WHERE product_product.product_type_id = ("
                        + "        SELECT product_producttype.id "
                        + "        FROM product_producttype "
                        + "        WHERE product_producttype.name = ? "
                        + "    ) "
                        + ") ");
                parameters.add(productTypeName);
            }
            if (categoryName != null && !categoryName.isEmpty()) {
                sqlQuery.append(
                        "AND sale_items.product_id IN ("
                        + "    SELECT product_product.id "
                        + "    FROM product_product "
                        + "    WHERE product_product.category_id = ("
                        + "        SELECT product_category.id "
                        + "        FROM product_category "
                        + "        WHERE product_category.name = ? "
                        + "    ) "
                        + ") ");
                parameters.add(categoryName);
            }

            // Date range condition
            if (startDate != null && endDate != null) {
                sqlQuery.append(") AND sale.sale_date BETWEEN ? AND ? ");
                parameters.add(new java.sql.Date(startDate.getTime())); // Convert to java.sql.Date
                parameters.add(new java.sql.Date(endDate.getTime())); // Convert to java.sql.Date
            }

            // Execute the query
            List<Map<String, Object>> salesTransactionReport = jdbcTemplate.queryForList(sqlQuery.toString(), parameters.toArray());

            // Return result
            if (salesTransactionReport != null && !salesTransactionReport.isEmpty()) {
                return new SuccessDataResult<>(salesTransactionReport, "Sales transaction report retrieved successfully.");
            } else {
                return new SuccessDataResult<>(Collections.emptyList(), "No sales transaction details found for the given filters and date range.");
            }
        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>(null, "Failed to retrieve sales transaction report: " + dataAccessException.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getInventoryTransactionReportBySale1(Date startDate, Date endDate,
            String productName, String productTypeName, String categoryName, String saleType, int channelId) {
        try {
            // Base query
            StringBuilder sqlQuery = new StringBuilder(
                    "SELECT sale.auto_sale_id, sale.sale_id, sale.sale_date, sale.reference, sale.remarks, cc.name, "
                    + "(SELECT account_user.company_name FROM account_user WHERE account_user.id = sale.customer_id) AS company_name "
                    + "FROM sale sale, channel_channel cc "
                    + "WHERE sale.sale_type = ? and sale.channel_id = cc.id and sale.transfer_channel_id = ? and sale.sale_id IN ("
                    + "    SELECT sale_items.sale_id "
                    + "    FROM sale_items "
                    + "     ");

            // Prepare parameters list
            List<Object> parameters = new ArrayList<>();
            parameters.add(saleType);
            parameters.add(channelId);

            // Optional filters
            if (productName != null && !productName.isEmpty()) {
                sqlQuery.append(
                        "AND sale_items.product_id IN ("
                        + "    SELECT product_product.id "
                        + "    FROM product_product "
                        + "    WHERE product_product.name = ? "
                        + ") ");
                parameters.add(productName);
            }
            if (productTypeName != null && !productTypeName.isEmpty()) {
                sqlQuery.append(
                        "AND sale_items.product_id IN ("
                        + "    SELECT product_product.id "
                        + "    FROM product_product "
                        + "    WHERE product_product.product_type_id = ("
                        + "        SELECT product_producttype.id "
                        + "        FROM product_producttype "
                        + "        WHERE product_producttype.name = ? "
                        + "    ) "
                        + ") ");
                parameters.add(productTypeName);
            }
            if (categoryName != null && !categoryName.isEmpty()) {
                sqlQuery.append(
                        "AND sale_items.product_id IN ("
                        + "    SELECT product_product.id "
                        + "    FROM product_product "
                        + "    WHERE product_product.category_id = ("
                        + "        SELECT product_category.id "
                        + "        FROM product_category "
                        + "        WHERE product_category.name = ? "
                        + "    ) "
                        + ") ");
                parameters.add(categoryName);
            }

            // Date range condition
            if (startDate != null && endDate != null) {
                sqlQuery.append(") AND sale.sale_date BETWEEN ? AND ? ");
                parameters.add(new java.sql.Date(startDate.getTime())); // Convert to java.sql.Date
                parameters.add(new java.sql.Date(endDate.getTime())); // Convert to java.sql.Date
            }

            // Execute the query
            List<Map<String, Object>> salesTransactionReport = jdbcTemplate.queryForList(sqlQuery.toString(), parameters.toArray());

            // Return result
            if (salesTransactionReport != null && !salesTransactionReport.isEmpty()) {
                return new SuccessDataResult<>(salesTransactionReport, "Sales transaction report retrieved successfully.");
            } else {
                return new SuccessDataResult<>(Collections.emptyList(), "No sales transaction details found for the given filters and date range.");
            }
        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>(null, "Failed to retrieve sales transaction report: " + dataAccessException.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getInventoryTransactionReportBySaleReturn(Date startDate,
            Date endDate, String productName, String productTypeName, String categoryName, int channelId) {
        try {
            // Base query
            StringBuilder sqlQuery = new StringBuilder(
                    "SELECT sale_return.auto_sale_return_id, sale_return.id, sale_return.return_date, sale_return.reference_no, sale_return.remarks, "
                    + "(SELECT account_user.company_name FROM account_user WHERE account_user.id = sale_return.customer_id) AS company_name "
                    + "FROM sale_return "
                    + "WHERE sale_return.channel_id = ? and sale_return.id IN ("
                    + "    SELECT sale_return_items.sale_return_id "
                    + "    FROM sale_return_items where sale_return_items.sale_return_id is not null ");

            // Prepare parameters list
            List<Object> parameters = new ArrayList<>();
            parameters.add(channelId);

            // Optional filters
            if (productName != null && !productName.isEmpty()) {
                sqlQuery.append(
                        "AND sale_return_items.product_id IN ("
                        + "    SELECT product_product.id "
                        + "    FROM product_product "
                        + "    WHERE product_product.name = ? "
                        + ") ");
                parameters.add(productName);
            }
            if (productTypeName != null && !productTypeName.isEmpty()) {
                sqlQuery.append(
                        "AND sale_return_items.product_id IN ("
                        + "    SELECT product_product.id "
                        + "    FROM product_product "
                        + "    WHERE product_product.product_type_id = ("
                        + "        SELECT product_producttype.id "
                        + "        FROM product_producttype "
                        + "        WHERE product_producttype.name = ? "
                        + "    ) "
                        + ") ");
                parameters.add(productTypeName);
            }
            if (categoryName != null && !categoryName.isEmpty()) {
                sqlQuery.append(
                        "AND sale_return_items.product_id IN ("
                        + "    SELECT product_product.id "
                        + "    FROM product_product "
                        + "    WHERE product_product.category_id = ("
                        + "        SELECT product_category.id "
                        + "        FROM product_category "
                        + "        WHERE product_category.name = ? "
                        + "    ) "
                        + ") ");
                parameters.add(categoryName);
            }

            // Date range condition
            if (startDate != null && endDate != null) {
                sqlQuery.append(") AND sale_return.return_date BETWEEN ? AND ? ");
                parameters.add(new java.sql.Date(startDate.getTime())); // Convert to java.sql.Date
                parameters.add(new java.sql.Date(endDate.getTime())); // Convert to java.sql.Date
            }

            // Execute the query
            List<Map<String, Object>> saleReturnTransactionReport = jdbcTemplate.queryForList(sqlQuery.toString(), parameters.toArray());

            // Return result
            if (saleReturnTransactionReport != null && !saleReturnTransactionReport.isEmpty()) {
                return new SuccessDataResult<>(saleReturnTransactionReport, "Sale return transaction report retrieved successfully.");
            } else {
                return new SuccessDataResult<>(Collections.emptyList(), "No sale return transaction details found for the given filters and date range.");
            }
        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>(null, "Failed to retrieve sale return transaction report: " + dataAccessException.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> checkBarcode(String barCode) {
        try {
            String sqlQuery = "SELECT * FROM purchase_item_serial_master WHERE item_barcode in (" + barCode + ")";
            List<Map<String, Object>> purchaseDetails = jdbcTemplate.queryForList(sqlQuery);

            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                return new SuccessDataResult<>(purchaseDetails, "Purchase item serial master details retrieved successfully");
            } else {
                return new SuccessDataResult<>(purchaseDetails, "No purchase item serial master details found for the given purchase master ID");
            }
        } catch (Exception e) {
            // Log the exception (use a proper logging framework in a real application)
            e.printStackTrace();
            return new ErrorDataResult<>(null, "Failed to retrieve purchase item serial master details: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getDeliveryChallanBySaleId(Integer saleId) {
        try {
            String sqlQuery = "SELECT sale.sale_id, product_product.name, product_product.description_plaintext, "
                    + "purchase_item_serial_master.item_barcode, purchase_item_serial_master.item_serial_number, "
                    + "purchase_item_serial_master.item_batch, purchase_item_serial_master.item_serial_expiry_date, "
                    + "product_productvariant.sku "
                    + "FROM sale "
                    + "JOIN sale_items ON sale_items.sale_id = sale.sale_id "
                    + "JOIN product_product ON product_product.id = sale_items.product_id "
                    + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_item_serial_master_id = sale_items.purchase_item_serial_id "
                    + "JOIN product_productvariant ON product_productvariant.product_id = product_product.id "
                    + "JOIN product_category ON product_category.id = product_product.category_id "
                    + "WHERE sale.sale_id = ? "
                    + "ORDER BY product_category.name, product_product.name, purchase_item_serial_master.item_barcode, purchase_item_serial_master.item_batch";

            // Execute the query with the provided saleId
            List<Map<String, Object>> inventoryTransactionReport = jdbcTemplate.queryForList(sqlQuery, saleId);

            if (inventoryTransactionReport != null && !inventoryTransactionReport.isEmpty()) {
                return new SuccessDataResult<>(inventoryTransactionReport, "Delivery challan retrieved successfully.");
            } else {
                return new SuccessDataResult<>(inventoryTransactionReport, "Delivery challan details found for the given sale ID.");
            }
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "An error occurred while retrieving the delivery challan: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getDeliveryChallanBySale1Id(Integer saleId) {
        try {
            String sqlQuery = "SELECT sale.sale_id, sale_items.quantity, product_product.name, product_product.description_plaintext, "
                    + "purchase_item_serial_master.item_barcode, purchase_item_serial_master.item_serial_number, purchase_item_serial_master.item_batch, "
                    + "purchase_item_serial_master.item_serial_expiry_date "
                    + "FROM sale "
                    + "JOIN sale_items ON sale_items.sale_id = sale.sale_id "
                    + "JOIN sale_return_items ON sale_return_items.sale_id = sale_items.sale_item_id "
                    + "JOIN product_product ON product_product.id = sale_items.product_id "
                    + "JOIN product_category ON product_category.id = product_product.category_id "
                    + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_item_serial_master_id = sale_return_items.purchase_item_serial_id "
                    + "WHERE sale.sale_id = ? order by product_category.name, product_product.name, purchase_item_serial_master.item_barcode";

            // Execute the query with the provided saleId
            List<Map<String, Object>> inventoryTransactionReport = jdbcTemplate.queryForList(sqlQuery, saleId);

            if (inventoryTransactionReport != null && !inventoryTransactionReport.isEmpty()) {
                return new SuccessDataResult<>(inventoryTransactionReport, "Delivery challan retrieved successfully.");
            } else {
                return new SuccessDataResult<>(inventoryTransactionReport, "Delivery challan details found for the given sale ID.");
            }
        } catch (Exception e) {

            return new ErrorDataResult<>(null, "An error occurred while retrieving the delivery challan: " + e.getMessage());
        }
    }

    public DataResult<Map<String, Object>> getUserDetailsBySaleId(Integer saleId) {
        try {
            // Use a parameterized query to avoid SQL injection
            String sqlQuery = "SELECT account_user.id, account_user.first_name, account_user.last_name, \n"
                    + "    account_user.email, account_user.mobile_no, account_user.company_name, \n"
                    + "    sale.sale_id, sale.sale_date, sale.sale_type, sale.remarks, sale.auto_sale_id, \n"
                    + "	(select CONCAT(ac.first_name, ' ', ac.last_name) from account_user ac where ac.id = sale.sale_user_id) cn\n"
                    + "FROM account_user \n"
                    + "JOIN sale ON sale.customer_id = account_user.id \n"
                    + "WHERE sale.sale_id = :saleId";

            // Create a map for the named parameter
            SqlParameterSource namedParameters = new MapSqlParameterSource("saleId", saleId);

            // Execute the query with the provided saleId
            Map<String, Object> userDetails = namedParameterJdbcTemplate.queryForMap(sqlQuery, namedParameters);

            if (userDetails != null && !userDetails.isEmpty()) {
                return new SuccessDataResult<>(userDetails, "User details retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No user details found for the given sale ID.");
            }
        } catch (EmptyResultDataAccessException e) {
            return new SuccessDataResult<>(null, "No user details found for the given sale ID.");
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "An error occurred while retrieving user details: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getCustomerNameListByCustomerName(List<String> userTypes, String customerName) {
        try {
            // Create a placeholder string for the user types
            String placeholders = String.join(",", Collections.nCopies(userTypes.size(), "?"));

            // SQL query with dynamic user types
            String sqlQuery = "SELECT * FROM account_user WHERE user_type IN (" + placeholders + ") "
                    + "AND is_staff = false AND (account_user.first_name LIKE ? OR account_user.last_name LIKE ?)";

            // Create parameter array for jdbcTemplate query
            List<Object> params = new ArrayList<>(userTypes);
            params.add("%" + customerName + "%"); // Append customerName with wildcard for LIKE query
            params.add("%" + customerName + "%"); // Append customerName with wildcard for LIKE query

            // Retrieve data from the database
            List<Map<String, Object>> userDataList = jdbcTemplate.queryForList(sqlQuery, params.toArray());

            // Check if data was found
            if (!userDataList.isEmpty()) {
                // User data retrieved successfully
                return new SuccessDataResult<>(userDataList, "Customer names retrieved successfully");
            } else {
                // No user found with the given user types and customer name
                return new ErrorDataResult<>("No customer names found matching the specified criteria");
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorDataResult<>("Failed to retrieve customer names. Error: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorDataResult<>("Failed to retrieve customer names. Error: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getCompanyNamesByUserTypesAndCompanyName(List<String> userTypes, String companyName) {
        try {
            // Create a placeholder string for the user types
            String placeholders = String.join(",", Collections.nCopies(userTypes.size(), "?"));

            // SQL query with dynamic user types
            String sqlQuery = "SELECT * FROM account_user WHERE user_type IN (" + placeholders + ") "
                    + "AND is_staff = false AND company_name LIKE ?";

            // Create parameter list for jdbcTemplate query
            List<Object> params = new ArrayList<>(userTypes);
            params.add("%" + companyName + "%"); // Append companyName with wildcard for LIKE query

            // Retrieve data from the database
            List<Map<String, Object>> userDataList = jdbcTemplate.queryForList(sqlQuery, params.toArray());

            // Check if data was found
            if (!userDataList.isEmpty()) {
                // User data retrieved successfully
                return new SuccessDataResult<>(userDataList, "Company names retrieved successfully");
            } else {
                // No user found with the given user types and company name
                return new ErrorDataResult<>("No company names found matching the specified criteria");
            }
        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            return new ErrorDataResult<>("Failed to retrieve company names. Database error: " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Other unexpected errors
            return new ErrorDataResult<>("Failed to retrieve company names. Unexpected error: " + e.getMessage());
        }
    }

    public DataResult<Integer> update_sale_data(Integer sale_id, String referenceNo, String remarks,
            Integer customerId, Date saleDate, int saleUserId) {
        try {
            String sqlUpdate = "UPDATE sale SET reference = ?, remarks = ?, customer_id = ?, "
                    + "sale_date = ?, sale_user_id = ? WHERE sale_id = ?";

            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlUpdate);
                ps.setString(1, referenceNo);
                ps.setString(2, remarks);
                ps.setInt(3, customerId);
                ps.setDate(4, new java.sql.Date(saleDate.getTime())); // Convert java.util.Date to java.sql.Date
                ps.setInt(5, saleUserId);
                ps.setInt(6, sale_id);
                return ps;
            });

            if (rowsAffected > 0) {
                return new SuccessDataResult<>(sale_id, "Sale updated successfully. ID: " + sale_id);
            } else {
                return new ErrorDataResult<>("Failed to update sale. No rows affected.");
            }
        } catch (DataAccessException e) {
            return new ErrorDataResult<>("Failed to update sale due to a database access issue.");
        } catch (Exception e) {
            return new ErrorDataResult<>("An unexpected error occurred while updating the sale.");
        }
    }

    public DataResult<Integer> updatePurchaseData(Integer purchaseId, String referenceNo, String remarks,
            Integer userId, Date purchaseDate, int purchaseUserId) {
        try {
            String sqlUpdate = "UPDATE purchase_master SET reference_no = ?, remarks = ?, user_id = ?, purchase_date = ?, purchase_user_id = ? WHERE purchase_id = ?";

            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlUpdate);
                ps.setString(1, referenceNo);
                ps.setString(2, remarks);
                ps.setInt(3, userId);
                ps.setDate(4, new java.sql.Date(purchaseDate.getTime()));
                ps.setInt(5, purchaseUserId);
                ps.setInt(6, purchaseId);
                return ps;
            });

            if (rowsAffected > 0) {
                return new SuccessDataResult<>(purchaseId, "Purchase updated successfully. ID: " + purchaseId);
            } else {
                return new ErrorDataResult<>("Failed to update purchase. No rows affected.");
            }
        } catch (Exception ex) {
            // Handle exceptions here, e.g., logging or throwing a custom exception
            return new ErrorDataResult<>("Failed to update purchase. Exception occurred.");
        }
    }

    public DataResult<Integer> updateSaleReturnData(Integer saleId, String referenceNo, String remarks,
            Integer customerId, Date returnDate, int returnUserId) {
        try {
            String sqlUpdate = "UPDATE sale_return SET reference_no = ?, remarks = ?, customer_id = ?, "
                    + "return_date = ?, return_user_id = ? WHERE id = ?";

            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlUpdate);
                ps.setString(1, referenceNo);
                ps.setString(2, remarks);
                ps.setInt(3, customerId);
                ps.setDate(4, new java.sql.Date(returnDate.getTime()));
                ps.setInt(5, returnUserId);
                ps.setInt(6, saleId);
                return ps;
            });

            if (rowsAffected > 0) {
                return new SuccessDataResult<>(saleId, "Sale return updated successfully. ID: " + saleId);
            } else {
                return new ErrorDataResult<>("Failed to update sale return. No rows affected.");
            }
        } catch (DataAccessException e) {
            return new ErrorDataResult<>("Failed to update sale return due to a database access issue: ");
        } catch (Exception e) {
            return new ErrorDataResult<>("An unexpected error occurred while updating the sale return: ");
        }
    }

    public DataResult<List<Map<String, Object>>> getSaleItemDetails(Integer saleId, Integer channelId) {
        try {
            // Use placeholders for parameters in the SQL query
            String sqlQuery = "SELECT count(sale_items.sale_item_id) quantity, "
                    + "       product_product.name AS product_name, sale.sale_date, "
                    + "       product_product.description_plaintext, channel_channel.name AS channel_name, "
                    + "       product_producttype.name AS product_type_name, product_category.name AS category_name "
                    + "FROM sale_items "
                    + "JOIN sale ON sale_items.sale_id = sale.sale_id "
                    + "JOIN product_product ON product_product.id = sale_items.product_id "
                    + "JOIN product_producttype ON product_product.product_type_id = product_producttype.id "
                    + "JOIN product_category ON product_category.id = product_product.category_id "
                    + "JOIN channel_channel ON channel_channel.id = sale.channel_id "
                    + "WHERE sale.sale_id = ? AND sale.channel_id = ? group by product_product.name, sale.sale_date, product_product.description_plaintext, channel_channel.name, \n"
                    + "product_producttype.name, product_category.name order by product_category.name, product_product.name";

            // Execute the query with the provided saleId and channelId
            List<Map<String, Object>> saleDetailsList = jdbcTemplate.queryForList(sqlQuery, saleId, channelId);

            if (saleDetailsList != null && !saleDetailsList.isEmpty()) {
                return new SuccessDataResult<>(saleDetailsList, "Sale details retrieved successfully.");
            } else {
                return new ErrorDataResult<>(null, "No sale details found for the given sale ID and channel ID.");
            }
        } catch (DataAccessException e) {
            return new ErrorDataResult<>(null, "An error occurred while accessing data.");
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "An error occurred while retrieving sale details.");
        }
    }

    public DataResult<Integer> getAutoPurchaseSeq() {
        try {
            String sqlQuery = "SELECT nextval('auto_purchase_id_seq')";

            // Execute the SQL query to retrieve product ID by slug
            List<Integer> queryResult = jdbcTemplate.queryForList(sqlQuery, Integer.class);

            if (!queryResult.isEmpty()) {
                // Product found, return its ID
                return new SuccessDataResult<>(queryResult.get(0), "Product ID retrieved successfully");
            } else {
                // No data found for the provided slug
                return new ErrorDataResult<>(null, "No sequence found: ");
            }
        } catch (DataAccessException e) {
            // Handle Spring DataAccessException separately
            return new ErrorDataResult<>("Error occurred while retrieving product ID: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return new ErrorDataResult<>("Unexpected error occurred while retrieving product ID: " + e.getMessage());
        }
    }

    public DataResult<Integer> getAutoPurchaseAvtSeq() {
        try {
            String sqlQuery = "SELECT nextval('auto_purchase_id_avt_seq')";

            // Execute the SQL query to retrieve product ID by slug
            List<Integer> queryResult = jdbcTemplate.queryForList(sqlQuery, Integer.class);

            if (!queryResult.isEmpty()) {
                // Product found, return its ID
                return new SuccessDataResult<>(queryResult.get(0), "Product ID retrieved successfully");
            } else {
                // No data found for the provided slug
                return new ErrorDataResult<>(null, "No sequence found: ");
            }
        } catch (DataAccessException e) {
            // Handle Spring DataAccessException separately
            return new ErrorDataResult<>("Error occurred while retrieving product ID: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return new ErrorDataResult<>("Unexpected error occurred while retrieving product ID: " + e.getMessage());
        }
    }

    public DataResult<Integer> getAutoPurchaseLhtSeq() {
        try {
            String sqlQuery = "SELECT nextval('auto_purchase_id_lht_seq')";

            // Execute the SQL query to retrieve product ID by slug
            List<Integer> queryResult = jdbcTemplate.queryForList(sqlQuery, Integer.class);

            if (!queryResult.isEmpty()) {
                // Product found, return its ID
                return new SuccessDataResult<>(queryResult.get(0), "Product ID retrieved successfully");
            } else {
                // No data found for the provided slug
                return new ErrorDataResult<>(null, "No sequence found: ");
            }
        } catch (DataAccessException e) {
            // Handle Spring DataAccessException separately
            return new ErrorDataResult<>("Error occurred while retrieving product ID: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return new ErrorDataResult<>("Unexpected error occurred while retrieving product ID: " + e.getMessage());
        }
    }

    public DataResult<Integer> getAutoPurchaseAmkSeq() {
        try {
            String sqlQuery = "SELECT nextval('auto_purchase_id_amk_seq')";

            // Execute the SQL query to retrieve product ID by slug
            List<Integer> queryResult = jdbcTemplate.queryForList(sqlQuery, Integer.class);

            if (!queryResult.isEmpty()) {
                // Product found, return its ID
                return new SuccessDataResult<>(queryResult.get(0), "Product ID retrieved successfully");
            } else {
                // No data found for the provided slug
                return new ErrorDataResult<>(null, "No sequence found: ");
            }
        } catch (DataAccessException e) {
            // Handle Spring DataAccessException separately
            return new ErrorDataResult<>("Error occurred while retrieving product ID: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return new ErrorDataResult<>("Unexpected error occurred while retrieving product ID: " + e.getMessage());
        }
    }

    public DataResult<Integer> getAutoSaleSeq() {
        try {
            String sqlQuery = "SELECT nextval('auto_sale_id_seq')";

            // Execute the SQL query to retrieve product ID by slug
            List<Integer> queryResult = jdbcTemplate.queryForList(sqlQuery, Integer.class);

            if (!queryResult.isEmpty()) {
                // Product found, return its ID
                return new SuccessDataResult<>(queryResult.get(0), "Product ID retrieved successfully");
            } else {
                // No data found for the provided slug
                return new ErrorDataResult<>(null, "No sequence found: ");
            }
        } catch (DataAccessException e) {
            // Handle Spring DataAccessException separately
            return new ErrorDataResult<>("Error occurred while retrieving product ID: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return new ErrorDataResult<>("Unexpected error occurred while retrieving product ID: " + e.getMessage());
        }
    }

    public DataResult<Integer> getAutoSaleAvtSeq() {
        try {
            String sqlQuery = "SELECT nextval('auto_sale_id_avt_seq')";

            // Execute the SQL query to retrieve product ID by slug
            List<Integer> queryResult = jdbcTemplate.queryForList(sqlQuery, Integer.class);

            if (!queryResult.isEmpty()) {
                // Product found, return its ID
                return new SuccessDataResult<>(queryResult.get(0), "Product ID retrieved successfully");
            } else {
                // No data found for the provided slug
                return new ErrorDataResult<>(null, "No sequence found: ");
            }
        } catch (DataAccessException e) {
            // Handle Spring DataAccessException separately
            return new ErrorDataResult<>("Error occurred while retrieving product ID: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return new ErrorDataResult<>("Unexpected error occurred while retrieving product ID: " + e.getMessage());
        }
    }

    public DataResult<Integer> getAutoSaleLhtSeq() {
        try {
            String sqlQuery = "SELECT nextval('auto_sale_id_lht_seq')";

            // Execute the SQL query to retrieve product ID by slug
            List<Integer> queryResult = jdbcTemplate.queryForList(sqlQuery, Integer.class);

            if (!queryResult.isEmpty()) {
                // Product found, return its ID
                return new SuccessDataResult<>(queryResult.get(0), "Product ID retrieved successfully");
            } else {
                // No data found for the provided slug
                return new ErrorDataResult<>(null, "No sequence found: ");
            }
        } catch (DataAccessException e) {
            // Handle Spring DataAccessException separately
            return new ErrorDataResult<>("Error occurred while retrieving product ID: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return new ErrorDataResult<>("Unexpected error occurred while retrieving product ID: " + e.getMessage());
        }
    }

    public DataResult<Integer> getAutoSaleAmkSeq() {
        try {
            String sqlQuery = "SELECT nextval('auto_sale_id_amk_seq')";

            // Execute the SQL query to retrieve product ID by slug
            List<Integer> queryResult = jdbcTemplate.queryForList(sqlQuery, Integer.class);

            if (!queryResult.isEmpty()) {
                // Product found, return its ID
                return new SuccessDataResult<>(queryResult.get(0), "Product ID retrieved successfully");
            } else {
                // No data found for the provided slug
                return new ErrorDataResult<>(null, "No sequence found: ");
            }
        } catch (DataAccessException e) {
            // Handle Spring DataAccessException separately
            return new ErrorDataResult<>("Error occurred while retrieving product ID: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return new ErrorDataResult<>("Unexpected error occurred while retrieving product ID: " + e.getMessage());
        }
    }

    public DataResult<Integer> getAutoSaleReturnSeq() {
        try {
            String sqlQuery = "SELECT nextval('auto_sale_return_id_seq')";

            // Execute the SQL query to retrieve product ID by slug
            List<Integer> queryResult = jdbcTemplate.queryForList(sqlQuery, Integer.class);

            if (!queryResult.isEmpty()) {
                // Product found, return its ID
                return new SuccessDataResult<>(queryResult.get(0), "Product ID retrieved successfully");
            } else {
                // No data found for the provided slug
                return new ErrorDataResult<>(null, "No sequence found: ");
            }
        } catch (DataAccessException e) {
            // Handle Spring DataAccessException separately
            return new ErrorDataResult<>("Error occurred while retrieving product ID: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return new ErrorDataResult<>("Unexpected error occurred while retrieving product ID: " + e.getMessage());
        }
    }

    public DataResult<Integer> getAutoSaleReturnAvtSeq() {
        try {
            String sqlQuery = "SELECT nextval('auto_sale_return_id_avt_seq')";

            // Execute the SQL query to retrieve product ID by slug
            List<Integer> queryResult = jdbcTemplate.queryForList(sqlQuery, Integer.class);

            if (!queryResult.isEmpty()) {
                // Product found, return its ID
                return new SuccessDataResult<>(queryResult.get(0), "Product ID retrieved successfully");
            } else {
                // No data found for the provided slug
                return new ErrorDataResult<>(null, "No sequence found: ");
            }
        } catch (DataAccessException e) {
            // Handle Spring DataAccessException separately
            return new ErrorDataResult<>("Error occurred while retrieving product ID: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return new ErrorDataResult<>("Unexpected error occurred while retrieving product ID: " + e.getMessage());
        }
    }

    public DataResult<Integer> getAutoSaleReturnLhtSeq() {
        try {
            String sqlQuery = "SELECT nextval('auto_sale_return_id_lht_seq')";

            // Execute the SQL query to retrieve product ID by slug
            List<Integer> queryResult = jdbcTemplate.queryForList(sqlQuery, Integer.class);

            if (!queryResult.isEmpty()) {
                // Product found, return its ID
                return new SuccessDataResult<>(queryResult.get(0), "Product ID retrieved successfully");
            } else {
                // No data found for the provided slug
                return new ErrorDataResult<>(null, "No sequence found: ");
            }
        } catch (DataAccessException e) {
            // Handle Spring DataAccessException separately
            return new ErrorDataResult<>("Error occurred while retrieving product ID: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return new ErrorDataResult<>("Unexpected error occurred while retrieving product ID: " + e.getMessage());
        }
    }

    public DataResult<Integer> getAutoSaleReturnAmkSeq() {
        try {
            String sqlQuery = "SELECT nextval('auto_sale_return_id_amk_seq')";

            // Execute the SQL query to retrieve product ID by slug
            List<Integer> queryResult = jdbcTemplate.queryForList(sqlQuery, Integer.class);

            if (!queryResult.isEmpty()) {
                // Product found, return its ID
                return new SuccessDataResult<>(queryResult.get(0), "Product ID retrieved successfully");
            } else {
                // No data found for the provided slug
                return new ErrorDataResult<>(null, "No sequence found: ");
            }
        } catch (DataAccessException e) {
            // Handle Spring DataAccessException separately
            return new ErrorDataResult<>("Error occurred while retrieving product ID: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            return new ErrorDataResult<>("Unexpected error occurred while retrieving product ID: " + e.getMessage());
        }
    }

    public DataResult<Map<String, Object>> getUserDetailsByPurchaseId(Integer purchaseId) {
        try {
            // Use a parameterized query to avoid SQL injection
            String sqlQuery = "SELECT account_user.id, account_user.first_name, account_user.last_name, \n"
                    + "    account_user.email, account_user.mobile_no, account_user.company_name, \n"
                    + "    purchase_master.purchase_id,  purchase_master.purchase_date, purchase_master.remarks, \n"
                    + "	purchase_master.auto_purchase_id, \n"
                    + "	(select CONCAT(ac.first_name, ' ', ac.last_name) from account_user ac where ac.id = purchase_master.purchase_user_id) cn \n"
                    + "FROM purchase_master \n"
                    + "JOIN account_user ON purchase_master.user_id = account_user.id \n"
                    + "WHERE purchase_master.purchase_id = ?";

            // Execute the query with the provided purchaseId
            Map<String, Object> userDetails = jdbcTemplate.queryForMap(sqlQuery, purchaseId);

            if (userDetails != null && !userDetails.isEmpty()) {
                return new SuccessDataResult<>(userDetails, "User details retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No user details found for the given purchase ID.");
            }
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "An error occurred while retrieving user details.");
        }
    }

    public DataResult<List<Map<String, Object>>> getDeliveryChallanByPurchaseId(Integer purchaseId) {
        try {
            // Adjusted SQL query to use the provided purchaseId parameter
            String sqlQuery = "SELECT purchase_master.purchase_id, product_product.name, purchase_item_serial_master.purchase_item_serial_master_id, product_productvariant.sku, \n"
                    + "product_product.description_plaintext AS product_description, purchase_item_serial_master.item_barcode,\n"
                    + "purchase_item_serial_master.item_serial_number, purchase_item_serial_master.item_serial_expiry_date, purchase_item_serial_master.item_batch\n"
                    + "FROM purchase_master\n"
                    + "JOIN purchase_master_items ON purchase_master.purchase_id = purchase_master_items.purchase_id\n"
                    + "JOIN product_product ON product_product.id = purchase_master_items.product_id\n"
                    + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_master_id = purchase_master_items.id\n"
                    + "JOIN product_productvariant ON product_productvariant.product_id = product_product.id "
                    + "JOIN product_category ON product_category.id = product_product.category_id \n"
                    + "WHERE purchase_master.purchase_id = ? order by product_category.name, product_product.name, purchase_item_serial_master.item_barcode";

            // Execute the query with the provided purchaseId
            List<Map<String, Object>> deliveryChallan = jdbcTemplate.queryForList(sqlQuery, purchaseId);

            if (deliveryChallan != null && !deliveryChallan.isEmpty()) {
                return new SuccessDataResult<>(deliveryChallan, "Delivery challan retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No delivery challan found for the given purchase ID.");
            }
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "Failed to retrieve the delivery challan: " + e.getMessage());
        }
    }

    public DataResult<Map<String, Object>> getUserDetailsBySaleReturnId(Integer saleReturnId) {
        try {
            // Use a parameterized query to avoid SQL injection
            String sqlQuery = "SELECT account_user.id, account_user.first_name, account_user.last_name,\n"
                    + "    account_user.email, account_user.mobile_no, account_user.company_name,\n"
                    + "    sale_return.id AS sale_return_id, sale_return.return_date, sale_return.remarks, \n"
                    + "	sale_return.auto_sale_return_id, \n"
                    + "	(select CONCAT(ac.first_name, ' ', ac.last_name) from account_user ac where ac.id = sale_return.return_user_id) cn\n"
                    + "FROM account_user\n"
                    + "JOIN sale_return ON sale_return.customer_id = account_user.id\n"
                    + "WHERE sale_return.id = ?";

            // Execute the query with the provided saleReturnId
            Map<String, Object> userDetails = jdbcTemplate.queryForMap(sqlQuery, saleReturnId);

            if (userDetails != null && !userDetails.isEmpty()) {
                return new SuccessDataResult<>(userDetails, "User details retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No user details found for the given sale return ID.");
            }
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "An error occurred while retrieving user details: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getDeliveryChallanBySaleReturnId(Integer saleReturnId) {
        try {
            // Adjusted SQL query to use the provided saleReturnId parameter
            String sqlQuery = "SELECT sr.id AS sale_return_id, sr.return_date AS sale_return_date, pp.name, pp.description_plaintext, product_productvariant.sku, "
                    + "purchase_item_serial_master.item_barcode, purchase_item_serial_master.item_serial_expiry_date, purchase_item_serial_master.purchase_item_serial_master_id, purchase_item_serial_master.item_batch, purchase_item_serial_master.item_serial_number "
                    + "FROM sale_return sr "
                    + "JOIN account_user au ON au.id = sr.customer_id "
                    + "JOIN sale_return_items sri ON sri.sale_return_id = sr.id "
                    + "JOIN product_product pp ON pp.id = sri.product_id "
                    + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_item_serial_master_id = sri.purchase_item_serial_id "
                    + "JOIN product_productvariant ON product_productvariant.product_id = pp.id "
                    + "JOIN product_category ON product_category.id = pp.category_id "
                    + "WHERE sri.sale_return_id = ? order by product_category.name, pp.name, purchase_item_serial_master.item_barcode"; // Use '?' as placeholder for saleReturnId

            // Execute the query with the provided saleReturnId
            List<Map<String, Object>> deliveryChallan = jdbcTemplate.queryForList(sqlQuery, saleReturnId);

            if (deliveryChallan != null && !deliveryChallan.isEmpty()) {
                return new SuccessDataResult<>(deliveryChallan, "Delivery challan retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No delivery challan found for the given sale return ID.");
            }
        } catch (EmptyResultDataAccessException e) {
            return new SuccessDataResult<>(null, "No delivery challan found for the given sale return ID.");
        } catch (Exception e) {
            String errorMessage = "Failed to retrieve the delivery challan due to an unexpected error: " + e.getMessage();
            return new ErrorDataResult<>(null, errorMessage);
        }
    }

    public DataResult<List<Map<String, Object>>> getPurchaseMasterItemDataByPurchaseId(int purchaseId) {
        try {
            String sqlQuery = "SELECT id FROM purchase_master_items WHERE purchase_id = ?";
            List<Map<String, Object>> purchaseDetails = jdbcTemplate.queryForList(sqlQuery, purchaseId);

            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                return new SuccessDataResult<>(purchaseDetails, "Purchase master item details retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No purchase master item details found for the given purchase ID.");
            }
        } catch (DataAccessException e) {
            return new ErrorDataResult<>(null, "Failed to retrieve purchase master item details: " + e.getMessage());
        }
    }

    public int updatePurchaseSale(int saleId) {
        return jdbcTemplate.update("update purchase_item_serial_master set sale_id = null where sale_id = ?", saleId);
    }

    public int updatePurchaseSaleReturn(int purchaseSerialId, int saleId) {
        return jdbcTemplate.update("update purchase_item_serial_master set sale_id = ? where purchase_item_serial_master_id = ?",
                saleId, purchaseSerialId);
    }

    public int deleteSaleReturnItems(int saleReturnId) {
        return jdbcTemplate.update("DELETE FROM sale_return_items where sale_return_id = ?", saleReturnId);
    }

    public int deleteSaleReturnMaster(int saleReturnId) {
        return jdbcTemplate.update("DELETE FROM sale_return where id = ?", saleReturnId);
    }

    public DataResult<List<Map<String, Object>>> get_item_return_data(int saleReturnId) {
        try {
            String sqlQuery = "select * from sale_return_items where sale_return_id = ?";
            List<Map<String, Object>> purchaseDetails = jdbcTemplate.queryForList(sqlQuery, saleReturnId);

            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                return new SuccessDataResult<>(purchaseDetails, "Purchase item serial master details retrieved successfully");
            } else {
                return new SuccessDataResult<>(null, "No purchase item serial master details found for the given purchase master ID");
            }
        } catch (Exception e) {
            // Log the exception (use a proper logging framework in a real application)
            e.printStackTrace();
            return new ErrorDataResult<>(null, "Failed to retrieve purchase item serial master details: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getDeliveryChallanQuantityBySaleId(Integer saleId) {
        try {
            String sqlQuery = "SELECT sale.sale_id, count(sale_items.sale_item_id) quantity, product_product.name, product_product.description_plaintext, product_productvariant.sku, product_category.name FROM sale \n"
                    + "JOIN sale_items ON sale_items.sale_id = sale.sale_id JOIN product_product ON product_product.id = \n"
                    + "sale_items.product_id JOIN product_productvariant ON product_productvariant.product_id = product_product.id JOIN product_category ON product_category.id = product_product.category_id \n"
                    + "WHERE sale.sale_id = ? group by product_category.name, product_product.name, product_product.description_plaintext, \n"
                    + "product_productvariant.sku, sale.sale_id ORDER BY product_category.name, product_product.name   ";  // Added ORDER BY clause

            // Execute the query with the provided saleId
            List<Map<String, Object>> inventoryTransactionReport = jdbcTemplate.queryForList(sqlQuery, saleId);

            if (inventoryTransactionReport != null && !inventoryTransactionReport.isEmpty()) {
                return new SuccessDataResult<>(inventoryTransactionReport, "Delivery challan retrieved successfully.");
            } else {
                return new SuccessDataResult<>(inventoryTransactionReport, "No delivery challan details found for the given sale ID.");
            }
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "An error occurred while retrieving the delivery challan: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getDeliveryChallanQuantityBySaleReturnId(Integer saleReturnId) {
        try {
            String sqlQuery = "SELECT  count(sale_return.id) quantity, 1 id, product_product.name, product_productvariant.sku\n"
                    + ", product_product.description_plaintext FROM  sale_return\n"
                    + " JOIN sale_return_items ON sale_return.id = sale_return_items.sale_return_id\n"
                    + " JOIN product_product ON product_product.id = sale_return_items.product_id \n"
                    + "JOIN product_productvariant ON product_productvariant.product_id = product_product.id "
                    + "JOIN product_category ON product_category.id = product_product.category_id \n"
                    + "WHERE sale_return_items.sale_return_id = ? group by product_category.name, sale_return_items.product_id, product_product.name\n"
                    + ", product_productvariant.sku, product_product.description_plaintext ORDER BY product_category.name, product_product.name";  // Added ORDER BY clause

            // Execute the query with the provided saleReturnId
            List<Map<String, Object>> deliveryChallanDetails = jdbcTemplate.queryForList(sqlQuery, saleReturnId);

            if (deliveryChallanDetails != null && !deliveryChallanDetails.isEmpty()) {
                return new SuccessDataResult<>(deliveryChallanDetails, "Delivery challan retrieved successfully.");
            } else {
                return new SuccessDataResult<>(deliveryChallanDetails, "No delivery challan details found for the given sale return ID.");
            }
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "An error occurred while retrieving the delivery challan: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getDeliveryChallanQuantityByPurchaseId(Integer purchaseId) {
        try {
            String sqlQuery = "SELECT purchase_master.purchase_id, purchase_master_items.quantity, "
                    + "    product_product.name, product_product.description_plaintext, product_productvariant.sku "
                    + "FROM  purchase_master "
                    + "JOIN  purchase_master_items ON purchase_master_items.purchase_id = purchase_master.purchase_id "
                    + "JOIN  product_product ON product_product.id = purchase_master_items.product_id "
                    + "JOIN product_productvariant ON product_productvariant.product_id = product_product.id "
                    + "JOIN product_category ON product_category.id = product_product.category_id "
                    + "WHERE  purchase_master.purchase_id = ? "
                    + "ORDER BY product_category.name, product_product.name";  // Added ORDER BY clause

            // Execute the query with the provided purchaseId
            List<Map<String, Object>> deliveryChallanDetails = jdbcTemplate.queryForList(sqlQuery, purchaseId);

            if (deliveryChallanDetails != null && !deliveryChallanDetails.isEmpty()) {
                return new SuccessDataResult<>(deliveryChallanDetails, "Delivery challan retrieved successfully.");
            } else {
                return new SuccessDataResult<>(deliveryChallanDetails, "No delivery challan details found for the given purchase ID.");
            }
        } catch (Exception e) {
            return new ErrorDataResult<>(null, "An error occurred while retrieving the delivery challan: " + e.getMessage());
        }
    }

    public List<Map<String, Object>> getBatchFromItemSerialNumber(String itemBarCode, int channelId) {
        String sqlQuery = "select count(purchase_item_serial_master_id) quantity, item_batch, item_serial_expiry_date from purchase_item_serial_master where \n"
                + "item_barcode = ? and sale_id is null and channel_id = ? group by item_batch, item_serial_expiry_date "
                + "order by item_serial_expiry_date asc";

        // Execute the SQL query to retrieve the barcode
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sqlQuery, itemBarCode, channelId);
        return queryForList;
    }

    public List<Map<String, Object>> getBatchFromItemSerialNumber1(String itemBarCode, int channelId) {
        String sqlQuery = "select count(purchase_item_serial_master_id) quantity, item_batch, item_serial_expiry_date from purchase_item_serial_master where \n"
                + "item_barcode = ? and sale_id is not null and channel_id = ? group by item_batch, item_serial_expiry_date \n"
                + "order by item_serial_expiry_date asc";

        // Execute the SQL query to retrieve the barcode
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sqlQuery, itemBarCode, channelId);
        return queryForList;
    }

    public int countNotTrackingProducts(String barcode) {
        String sql = "SELECT COUNT(DISTINCT purchase_item_serial_master_id) "
                + "FROM purchase_item_serial_master where item_barcode = :barcode and sale_id IS NULL";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("barcode", barcode);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public int countBatchProducts(String barcode, String batchNo) {
        String sql = "SELECT COUNT(DISTINCT purchase_item_serial_master_id) "
                + "FROM purchase_item_serial_master where item_barcode = :barcode and item_batch = :batchNo and sale_id IS NULL";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("barcode", barcode);
        params.addValue("batchNo", batchNo);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public int countNotTrackingReturnProducts(String barcode) {
        String sql = "SELECT COUNT(DISTINCT purchase_item_serial_master_id) "
                + "FROM purchase_item_serial_master where item_barcode = :barcode and sale_id is not null";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("barcode", barcode);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public int countBatchReturnProducts(String barcode, String batchNo) {
        String sql = "SELECT COUNT(DISTINCT purchase_item_serial_master_id) "
                + "FROM purchase_item_serial_master where item_barcode = :barcode and item_batch = :batchNo "
                + "and sale_id is not null";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("barcode", barcode);
        params.addValue("batchNo", batchNo);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public Result userChannelsDeletedByEmail(String email) {
        int userChannelsDeleted = jdbcTemplate.update(
                "DELETE FROM user_channels WHERE user_id IN (SELECT id FROM account_user WHERE email = ?)", email);

        if (userChannelsDeleted > 0) {
            return new SuccessResult("Deleted " + userChannelsDeleted + " user channel(s) for the email: " + email);
        } else {
            return new ErrorResult("No user channels found for the email: " + email);
        }
    }

    public DataResult<List<Map<String, Object>>> getServiceProductList(Date startDate, Date endDate, String customerName, String productName, String serialNumber, Integer pageNumber, Integer pageSize) {
        try {
            // Base SQL query with DISTINCT
            String sqlQuery = "SELECT purchase_item_serial_master.purchase_item_serial_master_id, sale.sale_date, sale.sale_id, sale.auto_sale_id, purchase_item_serial_master.item_serial_number, purchase_item_serial_master.item_serial_expiry_date, "
                    + "purchase_item_serial_master.item_batch, purchase_item_serial_master.warranty_valid_date, purchase_item_serial_master.amc_valid_date, account_user.company_name, product_product.name, "
                    + "product_product.is_batch, product_product.is_product_service "
                    + "FROM sale "
                    + "JOIN sale_items ON sale_items.sale_id = sale.sale_id "
                    + "JOIN purchase_item_serial_master ON purchase_item_serial_master.sale_id = sale_items.sale_item_id "
                    + "JOIN account_user ON account_user.id = sale.customer_id "
                    + "JOIN product_product ON product_product.id = sale_items.product_id "
                    + "WHERE sale.sale_date BETWEEN ? AND ? AND product_product.is_product_service = true ";

            List<Object> params = new ArrayList<>();
            params.add(startDate);
            params.add(endDate);

            // Conditionally add customer name filter
            if (customerName != null && !customerName.isEmpty()) {
                sqlQuery += " AND account_user.company_name = ?";
                params.add(customerName);
            }

            // Conditionally add product name filter
            if (productName != null && !productName.isEmpty()) {
                sqlQuery += " AND product_product.name = ?";
                params.add(productName);
            }

            // Conditionally add serial number filter
            if (serialNumber != null && !serialNumber.isEmpty()) {
                sqlQuery += " AND purchase_item_serial_master.item_serial_number = ?";
                params.add(serialNumber);
            }

            // Add the ORDER BY clause
            sqlQuery += " ORDER BY purchase_item_serial_master.purchase_item_serial_master_id DESC";

            // Check if pagination parameters are provided
            if (pageNumber != null && pageSize != null) {
                int offset = (pageNumber - 1) * pageSize;
                sqlQuery += " LIMIT ? OFFSET ?";
                params.add(pageSize);
                params.add(offset);
            }

            // Retrieve data from the database using JdbcTemplate
            List<Map<String, Object>> serviceProductList = jdbcTemplate.queryForList(sqlQuery, params.toArray());

            // Check if data was found
            if (!serviceProductList.isEmpty()) {
                return new SuccessDataResult<>(serviceProductList, "Successfully retrieved service product list.");
            } else {
                return new ErrorDataResult<>("No service product data found.");
            }

        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>("Failed to retrieve service product list: " + dataAccessException.getMessage());
        } catch (Exception exception) {
            return new ErrorDataResult<>("Failed to retrieve service product list: " + exception.getMessage());
        }
    }

    public Result updateWarrantyValidDate(String purchaseItemSerialMasterId, Date newWarrantyValidDate) {
        String sql = "UPDATE purchase_item_serial_master SET warranty_valid_date = ? WHERE purchase_item_serial_master_id = ?";

        int id = Integer.parseInt(purchaseItemSerialMasterId); // assumes valid numeric string

        int rowsAffected = jdbcTemplate.update(sql, new Object[]{newWarrantyValidDate, id});

        if (rowsAffected > 0) {
            return new SuccessResult("Warranty valid date updated successfully.");
        } else {
            return new ErrorResult("No records updated. Check if the item serial number exists.");
        }
    }

    public Result updateAmcValidDate(String purchaseItemSerialMasterId, Date newAmcValidDate) {
        String sql = "UPDATE purchase_item_serial_master SET amc_valid_date = ? WHERE purchase_item_serial_master_id = ?";

        int id = Integer.parseInt(purchaseItemSerialMasterId); // Assumes input is always valid

        int rowsAffected = jdbcTemplate.update(sql, new Object[]{newAmcValidDate, id});

        if (rowsAffected > 0) {
            return new SuccessResult("AMC valid date updated successfully.");
        } else {
            return new ErrorResult("No records updated. Check if the item serial number exists.");
        }
    }

    public Result insert_sale_service_master(Date serviceDate, String remarks, String solution, String attachment, Integer saleId) {
        try {
            String sqlInsert = "INSERT INTO sale_service_master (service_date, remarks, solution, attachment, sale_id) VALUES (?, ?, ?, ?, ?)";

            int rowsAffected = jdbcTemplate.update(sqlInsert, serviceDate, remarks, solution, attachment, saleId);

            if (rowsAffected > 0) {
                return new SuccessResult("Successfully inserted.");
            } else {
                return new ErrorResult("Insertion failed, no rows affected.");
            }
        } catch (DataAccessException e) {
            return new ErrorResult("Failed to insert record: " + e.getMessage());
        } catch (Exception e) {
            return new ErrorResult("Failed to insert record: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getServiceDetailsBySaleId(Integer saleId) {
        try {
            // SQL query to retrieve service details based on sale ID, ordered by service_date in descending order
            String sqlQuery = "SELECT sale_service_master.sale_service_id, sale_service_master.service_date, "
                    + "sale_service_master.remarks, sale_service_master.solution, sale_service_master.attachment, sale_service_master.sale_id "
                    + "FROM sale_service_master WHERE sale_service_master.sale_id = ? "
                    + "ORDER BY sale_service_master.service_date DESC";

            // Parameters for the query
            List<Object> params = new ArrayList<>();
            params.add(saleId);

            // Execute the query and fetch service details
            List<Map<String, Object>> serviceDetails = jdbcTemplate.queryForList(sqlQuery, params.toArray());

            // Check if data was found
            if (!serviceDetails.isEmpty()) {
                return new SuccessDataResult<>(serviceDetails, "Service details retrieved successfully.");
            } else {
                return new ErrorDataResult<>("No service details found for the given sale ID.");
            }

        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>("Database error: Unable to retrieve service details. " + dataAccessException.getMessage());
        } catch (Exception exception) {
            return new ErrorDataResult<>("An unexpected error occurred: " + exception.getMessage());
        }
    }

    // We are using this method in quickSearchWithSerialNumber
    public DataResult<List<Map<String, Object>>> retrieveSaleReturnByItemBarcode(String itemBarcode, int channelId) {
        try {
            String sqlQuery = "SELECT DISTINCT "
                    + "(SELECT account_user.company_name FROM account_user WHERE account_user.id = sale_return.customer_id) AS company_name, "
                    + "(SELECT CONCAT(account_user.first_name, ' ', account_user.last_name) FROM account_user WHERE account_user.id = sale_return.customer_id) AS full_name, "
                    + "(SELECT account_user.mobile_no FROM account_user WHERE account_user.id = sale_return.customer_id) AS mobile_no, "
                    + "product_product.name AS product_name, "
                    + "product_product.description_plaintext, "
                    + "product_producttype.name AS product_type_name, "
                    + "product_category.name AS category_name, "
                    + "purchase_item_serial_master.item_serial_number, "
                    + "purchase_item_serial_master.item_barcode, "
                    + "purchase_item_serial_master.item_batch, "
                    + "purchase_item_serial_master.item_serial_expiry_date, "
                    + "sale_return.auto_sale_return_id uniqueId, sale_return.return_date "
                    + "FROM sale_return "
                    + "JOIN sale_return_items ON sale_return_items.sale_return_id = sale_return.id "
                    + "JOIN product_product ON product_product.id = sale_return_items.product_id "
                    + "JOIN product_producttype ON product_producttype.id = product_product.product_type_id "
                    + "JOIN product_category ON product_category.id = product_product.category_id "
                    + "JOIN sale_items ON sale_items.sale_item_id = sale_return_items.sale_id "
                    + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_item_serial_master_id = sale_return_items.purchase_item_serial_id "
                    + "WHERE sale_return.channel_id = ? and (purchase_item_serial_master.item_barcode = ? OR purchase_item_serial_master.item_serial_number = ?)";

            List<Object> parameters = new ArrayList<>();
            parameters.add(channelId);

            if (itemBarcode != null && !itemBarcode.isEmpty()) {
                parameters.add(itemBarcode);
                parameters.add(itemBarcode);
            }

            List<Map<String, Object>> salesReturnDetails = jdbcTemplate.queryForList(sqlQuery, parameters.toArray());

            if (salesReturnDetails != null && !salesReturnDetails.isEmpty()) {
                return new SuccessDataResult<>(salesReturnDetails, "Sales return details retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No sales return details found for the given criteria.");
            }
        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>(null, "Failed to retrieve sales return details: " + dataAccessException.getMessage());
        }
    }

    // We are using this method in quickSearchWithSerialNumber
    public DataResult<List<Map<String, Object>>> retrievePurchaseByItemBarcode(String itemBarcode, int channelId) {
        String sqlQuery
                = "SELECT DISTINCT "
                + "    (SELECT account_user.company_name FROM account_user WHERE account_user.id = purchase_master.user_id) AS company_name, "
                + "    (SELECT CONCAT(account_user.first_name, ' ', account_user.last_name) FROM account_user WHERE account_user.id = purchase_master.user_id) AS full_name, "
                + "    (SELECT account_user.mobile_no FROM account_user WHERE account_user.id = purchase_master.user_id) AS mobile_no, "
                + "    product_product.name AS product_name, "
                + "    product_product.description_plaintext, "
                + "    purchase_item_serial_master.item_barcode, "
                + "    product_producttype.name AS product_type_name, "
                + "    product_category.name AS category_name, "
                + "    purchase_master_items.quantity, "
                + "    purchase_item_serial_master.item_serial_number, "
                + "    purchase_item_serial_master.item_batch, "
                + "    purchase_item_serial_master.item_serial_expiry_date, "
                + "    purchase_master.grand_total, "
                + "    purchase_master.auto_purchase_id AS uniqueId, purchase_master.purchase_date "
                + "FROM purchase_master "
                + "JOIN purchase_master_items ON purchase_master_items.purchase_id = purchase_master.purchase_id "
                + "JOIN product_product ON product_product.id = purchase_master_items.product_id "
                + "JOIN product_producttype ON product_producttype.id = product_product.product_type_id "
                + "JOIN product_category ON product_category.id = product_product.category_id "
                + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_master_id = purchase_master_items.id "
                + "WHERE purchase_master.channel_id = ? and (purchase_item_serial_master.item_barcode = ? OR purchase_item_serial_master.item_serial_number = ?)";

        List<Object> parameters = new ArrayList<>();
        parameters.add(channelId);

        if (itemBarcode != null && !itemBarcode.isEmpty()) {
            parameters.add(itemBarcode);
            parameters.add(itemBarcode);
        }

        try {
            List<Map<String, Object>> purchaseDetails = jdbcTemplate.queryForList(sqlQuery, parameters.toArray());

            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                return new SuccessDataResult<>(purchaseDetails, "Purchase details retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No purchase details found for the given criteria.");
            }
        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>(null, "Failed to retrieve purchase details: " + dataAccessException.getMessage());
        }
    }

    // We are using this method in quickSearchWithSerialNumber
    public DataResult<List<Map<String, Object>>> retrieveSaleByItemBarcode(String itemBarcode, int channelId) {
        String sqlQuery = "SELECT DISTINCT "
                + "(SELECT account_user.company_name FROM account_user WHERE account_user.id = sale.customer_id) AS company_name, "
                + "(SELECT CONCAT(account_user.first_name, ' ', account_user.last_name) FROM account_user WHERE account_user.id = sale.customer_id) AS full_name, "
                + "(SELECT account_user.mobile_no FROM account_user WHERE account_user.id = sale.customer_id) AS mobile_no, "
                + "product_product.name AS product_name, "
                + "product_product.description_plaintext, "
                + "product_producttype.name AS product_type_name, "
                + "product_category.name AS category_name, "
                + "sale.grand_total, sale.sale_date, "
                + "purchase_item_serial_master.item_serial_number, "
                + "purchase_item_serial_master.item_barcode, "
                + "purchase_item_serial_master.item_batch, "
                + "purchase_item_serial_master.item_serial_expiry_date, "
                + "sale.auto_sale_id AS uniqueId, sale.sale_date, sale.sale_type, sale.sale_id "
                + "FROM sale "
                + "JOIN sale_items ON sale_items.sale_id = sale.sale_id "
                + "JOIN product_product ON product_product.id = sale_items.product_id "
                + "JOIN product_producttype ON product_producttype.id = product_product.product_type_id "
                + "JOIN product_category ON product_category.id = product_product.category_id "
                + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_item_serial_master_id = sale_items.purchase_item_serial_id "
                + "WHERE (sale.channel_id = ? or sale.transfer_channel_id = ?) and (purchase_item_serial_master.item_barcode = ? OR purchase_item_serial_master.item_serial_number = ?)";

        List<Object> parameters = new ArrayList<>();
        parameters.add(channelId);
        parameters.add(channelId);

        if (itemBarcode != null && !itemBarcode.isEmpty()) {
            parameters.add(itemBarcode);
            parameters.add(itemBarcode);
        }

        try {
            List<Map<String, Object>> saleDetails = jdbcTemplate.queryForList(sqlQuery, parameters.toArray());

            if (saleDetails != null && !saleDetails.isEmpty()) {
                return new SuccessDataResult<>(saleDetails, "Sale details retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No sale details found for the given criteria.");
            }
        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>(null, "Failed to retrieve sale details: " + dataAccessException.getMessage());
        }
    }

    public String getImageNameBySaleServiceId(Integer saleServiceId) {
        String sql = "SELECT attachment FROM sale_service_master WHERE sale_service_id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{saleServiceId}, String.class);
        } catch (DataAccessException dataAccessException) {
            // Handle exception or log it
            throw new RuntimeException("Error retrieving image name for saleServiceId: " + saleServiceId, dataAccessException);
        }
    }

    public DataResult<List<Map<String, Object>>> fetchInventoryTransactionReportDetailsByPurchase(Integer purchaseId) {
        try {
            String sql = "SELECT purchase_master.purchase_id, purchase_master.auto_purchase_id, "
                    + "purchase_item_serial_master.item_serial_number, purchase_item_serial_master.item_batch, "
                    + "purchase_item_serial_master.item_serial_expiry_date, product_product.name AS product_name, "
                    + "product_product.is_batch, product_producttype.name AS product_type_name, "
                    + "product_category.name AS category_name, product_productvariant.sku, "
                    + "COUNT(purchase_master_items.quantity) AS quantity_count "
                    + "FROM purchase_master "
                    + "JOIN purchase_master_items ON purchase_master_items.purchase_id = purchase_master.purchase_id "
                    + "JOIN product_product ON product_product.id = purchase_master_items.product_id "
                    + "JOIN product_producttype ON product_producttype.id = product_product.product_type_id "
                    + "JOIN product_category ON product_category.id = product_product.category_id "
                    + "JOIN product_productvariant ON product_productvariant.product_id = product_product.id "
                    + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_master_id = purchase_master_items.id "
                    + "WHERE purchase_master.purchase_id = ? "
                    + "GROUP BY purchase_master.purchase_id, purchase_master.auto_purchase_id, purchase_item_serial_master.item_batch, "
                    + "purchase_item_serial_master.item_serial_number, purchase_item_serial_master.item_serial_expiry_date, "
                    + "product_product.name, product_producttype.name, product_product.is_batch, product_category.name, product_productvariant.sku;";

            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, purchaseId);

            if (resultList.isEmpty()) {
                return new ErrorDataResult<>("No data found for the provided purchase ID");
            } else {
                return new SuccessDataResult<>(resultList, "Data retrieved successfully");
            }
        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>("An error occurred while retrieving data: " + dataAccessException.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> fetchInventoryTransactionReportDetailsBySale(Integer saleId) {
        try {
            String sql = "SELECT sale.sale_id, sale.auto_sale_id, "
                    + "product_product.name AS product_name, product_product.is_batch, "
                    + "product_producttype.name AS product_type_name, product_category.name AS category_name, "
                    + "product_productvariant.sku, "
                    + "purchase_item_serial_master.item_batch, purchase_item_serial_master.item_serial_number, "
                    + "purchase_item_serial_master.item_serial_expiry_date, COUNT(sale_items.sale_item_id) AS quantity_count "
                    + "FROM sale "
                    + "JOIN sale_items ON sale_items.sale_id = sale.sale_id "
                    + "JOIN product_product ON product_product.id = sale_items.product_id "
                    + "JOIN product_producttype ON product_producttype.id = product_product.product_type_id "
                    + "JOIN product_category ON product_category.id = product_product.category_id "
                    + "JOIN product_productvariant ON product_productvariant.product_id = product_product.id "
                    + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_item_serial_master_id = sale_items.purchase_item_serial_id "
                    + "WHERE sale.sale_id = ? "
                    + "GROUP BY sale.sale_id, sale.auto_sale_id, product_product.name, product_product.is_batch, "
                    + "product_producttype.name, product_category.name, product_productvariant.sku, "
                    + "purchase_item_serial_master.item_batch, purchase_item_serial_master.item_serial_number, "
                    + "purchase_item_serial_master.item_serial_expiry_date";

            // Pass the saleId dynamically to the query
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql, saleId);

            if (resultList.isEmpty()) {
                return new ErrorDataResult<>("No data found for the provided sale ID");
            } else {
                return new SuccessDataResult<>(resultList, "Data retrieved successfully");
            }
        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>("An error occurred while retrieving data: " + dataAccessException.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> fetchInventoryTransactionDetailsBySaleReturnId(Integer saleReturnId) {
        try {
            // Parameterized query for sale_return_id
            String sqlQuery = "SELECT purchase_item_serial_master.item_serial_number, "
                    + "purchase_item_serial_master.item_serial_expiry_date, "
                    + "purchase_item_serial_master.item_batch, product_product.name AS product_name, product_product.id, "
                    + "product_product.is_batch, product_producttype.name AS product_type_name, "
                    + "product_category.name AS category_name, "
                    + "product_productvariant.sku, "
                    + "sale_return.auto_sale_return_id, sale_return.id, COUNT(sale_return_items.id) AS quantity_count "
                    + "FROM sale_return "
                    + "JOIN sale_return_items ON sale_return_items.sale_return_id = sale_return.id "
                    + "JOIN product_product ON sale_return_items.product_id = product_product.id "
                    + "JOIN product_producttype ON product_producttype.id = product_product.product_type_id "
                    + "JOIN product_category ON product_category.id = product_product.category_id "
                    + "JOIN product_productvariant ON product_productvariant.product_id = product_product.id "
                    + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_item_serial_master_id = sale_return_items.purchase_item_serial_id "
                    + "WHERE sale_return.id = ? "
                    + "GROUP BY purchase_item_serial_master.item_serial_number, purchase_item_serial_master.item_serial_expiry_date, "
                    + "purchase_item_serial_master.item_batch, product_product.name, product_product.id, product_product.is_batch, "
                    + "product_producttype.name, product_category.name, product_productvariant.sku, sale_return.auto_sale_return_id, sale_return.id";

            List<Map<String, Object>> saleReturnDetailsList = jdbcTemplate.queryForList(sqlQuery, saleReturnId);

            if (saleReturnDetailsList != null && !saleReturnDetailsList.isEmpty()) {
                return new SuccessDataResult<>(saleReturnDetailsList, "Sale return details retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No sale return details found for the given ID.");
            }
        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>(null, "An error occurred while retrieving sale return details: " + dataAccessException.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> fetchInventoryTransactionDetailsBySaleId(Integer saleReturnId) {
        try {
            String sqlQuery = "SELECT purchase_item_serial_master.item_serial_number, purchase_item_serial_master.item_serial_expiry_date, "
                    + "purchase_item_serial_master.item_batch, product_product.name AS product_name, product_product.id, "
                    + "product_product.is_batch, product_producttype.name AS product_type_name, product_category.name AS category_name, "
                    + "product_productvariant.sku, COUNT(sale_items.quantity) AS quantity_count, sale_return.auto_sale_return_id, sale_return.id "
                    + "FROM purchase_item_serial_master "
                    + "JOIN sale_return_items ON purchase_item_serial_master.purchase_item_serial_master_id = sale_return_items.purchase_item_serial_id "
                    + "JOIN product_product ON sale_return_items.product_id = product_product.id "
                    + "JOIN product_producttype ON product_producttype.id = product_product.product_type_id "
                    + "JOIN product_category ON product_category.id = product_product.category_id "
                    + "JOIN product_productvariant ON product_productvariant.product_id = product_product.id "
                    + "JOIN sale_items ON sale_items.sale_item_id = sale_return_items.sale_id "
                    + "JOIN sale_return ON sale_return.id = sale_return_items.sale_return_id "
                    + "WHERE sale_items.sale_id = ? "
                    + "GROUP BY purchase_item_serial_master.item_serial_number, purchase_item_serial_master.item_serial_expiry_date, "
                    + "purchase_item_serial_master.item_batch, product_product.name, product_product.id, product_product.is_batch, "
                    + "product_producttype.name, product_category.name, product_productvariant.sku, sale_return.auto_sale_return_id, sale_return.id";

            List<Map<String, Object>> saleReturnDetailsList = jdbcTemplate.queryForList(sqlQuery, saleReturnId);

            if (saleReturnDetailsList != null && !saleReturnDetailsList.isEmpty()) {
                return new SuccessDataResult<>(saleReturnDetailsList, "Sale return details retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No sale return details found for the given ID.");
            }
        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>(null, "An error occurred while retrieving sale return details.");
        }
    }

    public DataResult<List<Map<String, Object>>> fetchPurchaseList(
            Integer channelId, Integer pageNumber, Integer pageSize,
            Date startDate, Date endDate, String companyName, String productName
    ) {
        try {
            // Base query with company_name subquery and quantity sum
            StringBuilder sqlQuery = new StringBuilder(
                    "SELECT purchase_master.auto_purchase_id, purchase_master.purchase_id, "
                    + "purchase_master.purchase_date, purchase_master.reference_no, "
                    + "purchase_master.remarks, "
                    + "(SELECT account_user.company_name FROM account_user WHERE account_user.id = purchase_master.user_id) AS company_name, "
                    + "SUM(purchase_master_items.quantity) AS total_quantity "
                    + "FROM purchase_master "
                    + "JOIN purchase_master_items ON purchase_master.purchase_id = purchase_master_items.purchase_id "
                    + "WHERE purchase_master.purchase_id IS NOT NULL ");

            // Prepare parameters list
            List<Object> parameters = new ArrayList<>();

            // Optional filter for product name
            if (productName != null && !productName.isEmpty()) {
                sqlQuery.append(" AND purchase_master_items.product_id IN ("
                        + "    SELECT product_product.id "
                        + "    FROM product_product "
                        + "    WHERE product_product.name = ? "
                        + ") ");
                parameters.add(productName);
            }

            // Optional filter for company name
            if (companyName != null && !companyName.isEmpty()) {
                sqlQuery.append(" AND purchase_master.user_id IN ("
                        + "    SELECT account_user.id "
                        + "    FROM account_user "
                        + "    WHERE account_user.company_name = ? "
                        + ") ");
                parameters.add(companyName);
            }

            // Optional filter for channelId
            if (channelId != null) {
                sqlQuery.append(" AND purchase_master.channel_id = ? ");
                parameters.add(channelId);
            }

            // Date range condition
            if (startDate != null && endDate != null) {
                sqlQuery.append(" AND purchase_master.purchase_date BETWEEN ? AND ? ");
                parameters.add(new java.sql.Date(startDate.getTime()));
                parameters.add(new java.sql.Date(endDate.getTime()));
            }

            // Group by required fields
            sqlQuery.append(" GROUP BY purchase_master.auto_purchase_id, purchase_master.purchase_id, "
                    + "purchase_master.purchase_date, purchase_master.reference_no, purchase_master.remarks, "
                    + "purchase_master.user_id order by purchase_master.purchase_id desc");

            // Pagination logic
            if (pageNumber != null && pageSize != null) {
                sqlQuery.append(" LIMIT ? OFFSET ? ");
                parameters.add(pageSize);
                parameters.add(pageSize * (pageNumber - 1));
            }

            // Execute the query
            List<Map<String, Object>> purchaseTransactionReport = jdbcTemplate.queryForList(sqlQuery.toString(), parameters.toArray());

            // Return result
            if (purchaseTransactionReport != null && !purchaseTransactionReport.isEmpty()) {
                return new SuccessDataResult<>(purchaseTransactionReport, "Purchase transaction report retrieved successfully.");
            } else {
                return new SuccessDataResult<>(Collections.emptyList(), "No purchase transaction details found for the given filters and date range.");
            }
        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>(null, "Failed to retrieve purchase transaction report: " + dataAccessException.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> fetchSaleList(Integer channelId, Integer pageNumber, Integer pageSize,
            Date startDate, Date endDate, String companyName, String productName, String saleType, int channelId2) {
        try {
            // Base query with subqueries
            StringBuilder sqlQuery = new StringBuilder(
                    "SELECT sale.auto_sale_id, sale.sale_id, sale.sale_date, sale.reference, sale.remarks, sale.sale_type, "
                    + "(SELECT count(sale_items.sale_item_id) FROM sale_items WHERE sale_items.sale_id = sale.sale_id) AS total_quantity, "
                    + "(SELECT account_user.company_name FROM account_user WHERE account_user.id = sale.customer_id) AS company_name "
                    + "FROM sale "
                    + "WHERE sale.sale_id IN ("
                    + "    SELECT sale_items.sale_id FROM sale_items WHERE sale_items.product_id IN ("
                    + "        SELECT product_product.id FROM product_product"
            );

            // Prepare parameters list
            List<Object> parameters = new ArrayList<>();

            // Optional filter for product name
            if (productName != null && !productName.isEmpty()) {
                sqlQuery.append(" WHERE product_product.name = ? ");
                parameters.add(productName);
            }

            sqlQuery.append("    )"
                    + ")");

            // Optional filter for company name
            if (companyName != null && !companyName.isEmpty()) {
                sqlQuery.append(" AND sale.customer_id IN ("
                        + "    SELECT account_user.id FROM account_user WHERE account_user.company_name = ? "
                        + ")");
                parameters.add(companyName);
            }

            // Optional filter for channelId
            if (channelId != null) {
                sqlQuery.append(" AND sale.channel_id = ? ");
                parameters.add(channelId);
            }

            // Optional filter for channelId
            if ("Internal Transfer".equals(saleType) && channelId2 > 0) {
                sqlQuery.append(" AND sale.transfer_channel_id = ? ");
                parameters.add(channelId2);
            }

            // Optional filter for saleType
            if (!(saleType == null || saleType.equals(""))) {
                sqlQuery.append(" AND sale.sale_type = ? ");
                parameters.add(saleType);
            }

            // Date range condition
            if (startDate != null && endDate != null) {
                sqlQuery.append(" AND sale.sale_date BETWEEN ? AND ? ");
                parameters.add(new java.sql.Date(startDate.getTime()));
                parameters.add(new java.sql.Date(endDate.getTime()));
            }

            // Group by necessary fields
            sqlQuery.append(" GROUP BY sale.auto_sale_id, sale.sale_id, sale.sale_date, sale.reference, "
                    + "sale.remarks order by sale.sale_id desc");

            // Pagination logic
            if (pageNumber != null && pageSize != null) {
                sqlQuery.append(" LIMIT ? OFFSET ? ");
                parameters.add(pageSize);
                parameters.add(pageSize * (pageNumber - 1));
            }

            // Execute the query
            List<Map<String, Object>> salesTransactionReport = jdbcTemplate.queryForList(sqlQuery.toString(), parameters.toArray());

            // Return result
            if (salesTransactionReport != null && !salesTransactionReport.isEmpty()) {
                return new SuccessDataResult<>(salesTransactionReport, "Sales transaction report retrieved successfully.");
            } else {
                return new SuccessDataResult<>(Collections.emptyList(), "No sales transaction details found for the given filters and date range.");
            }
        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>(null, "Failed to retrieve sales transaction report: " + dataAccessException.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> fetchSaleReturnList(
            Integer channelId, Integer pageNumber, Integer pageSize,
            Date startDate, Date endDate, String companyName, String productName
    ) {
        try {
            // Base query with subqueries
            StringBuilder sqlQuery = new StringBuilder(
                    " SELECT sale_return.auto_sale_return_id,  sale_return.id,  sale_return.return_date, "
                    + " sale_return.reference_no,  sale_return.remarks, "
                    + "(SELECT count(sale_return_items.id) FROM sale_return_items WHERE sale_return_items.sale_return_id = sale_return.id) AS total_quantity, "
                    + "(SELECT account_user.company_name FROM account_user WHERE account_user.id = sale_return.customer_id) AS company_name "
                    + "FROM sale_return "
                    + "WHERE sale_return.id IN ("
                    + "    SELECT sale_return_items.sale_return_id FROM sale_return_items WHERE sale_return_items.product_id IN ("
                    + "        SELECT product_product.id FROM product_product"
            );

            // Prepare parameters list
            List<Object> parameters = new ArrayList<>();

            // Optional filter for product name
            if (productName != null && !productName.isEmpty()) {
                sqlQuery.append(" WHERE product_product.name = ? ");
                parameters.add(productName);
            }

            sqlQuery.append("    )"
                    + ")");

            // Optional filter for company name
            if (companyName != null && !companyName.isEmpty()) {
                sqlQuery.append(" AND sale_return.customer_id IN ("
                        + "    SELECT account_user.id FROM account_user WHERE account_user.company_name = ? "
                        + ")");
                parameters.add(companyName);
            }

            // Optional filter for channelId
            if (channelId != null) {
                sqlQuery.append(" AND sale_return.channel_id = ? ");
                parameters.add(channelId);
            }

            // Date range condition
            if (startDate != null && endDate != null) {
                sqlQuery.append(" AND sale_return.return_date BETWEEN ? AND ? ");
                parameters.add(new java.sql.Date(startDate.getTime()));
                parameters.add(new java.sql.Date(endDate.getTime()));
            }

            // Group by necessary fields
            sqlQuery.append(" GROUP BY sale_return.auto_sale_return_id, sale_return.id, sale_return.return_date, "
                    + "sale_return.reference_no, sale_return.remarks order by sale_return.id desc");

            // Pagination logic
            if (pageNumber != null && pageSize != null) {
                sqlQuery.append(" LIMIT ? OFFSET ? ");
                parameters.add(pageSize);
                parameters.add(pageSize * (pageNumber - 1));
            }

            // Execute the query
            List<Map<String, Object>> salesTransactionReport = jdbcTemplate.queryForList(sqlQuery.toString(), parameters.toArray());

            // Return result
            if (salesTransactionReport != null && !salesTransactionReport.isEmpty()) {
                return new SuccessDataResult<>(salesTransactionReport, "Sales transaction report retrieved successfully.");
            } else {
                return new SuccessDataResult<>(Collections.emptyList(), "No sales transaction details found for the given filters and date range.");
            }
        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>(null, "Failed to retrieve sales transaction report: " + dataAccessException.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getPurchaseReport(String productName, Date startDate,
            Date endDate, int channelId) {
        try {
            // Validate mandatory parameters
            if (productName == null || productName.isEmpty()) {
                return new ErrorDataResult<>(null, "Product name is required.");
            }
            if (startDate == null || endDate == null) {
                return new ErrorDataResult<>(null, "Start date and end date are required.");
            }

            String sqlQuery = "SELECT DISTINCT purchase_master.auto_purchase_id, purchase_master.purchase_id, "
                    + "purchase_master.purchase_date, purchase_master.reference_no, purchase_master.remarks, "
                    + "(SELECT account_user.company_name FROM account_user WHERE account_user.id = purchase_master.user_id) AS company_name, "
                    + "(SELECT CONCAT(account_user.first_name, ' ', account_user.last_name) FROM account_user WHERE account_user.id = purchase_master.user_id) AS full_name "
                    + "FROM purchase_master "
                    + "WHERE purchase_master.channel_id = ? and purchase_master.purchase_id IN ( "
                    + "    SELECT purchase_master_items.purchase_id "
                    + "    FROM purchase_master_items "
                    + "    WHERE purchase_master_items.product_id IN ( "
                    + "        SELECT product_product.id "
                    + "        FROM product_product "
                    + "        WHERE product_product.name = ? "
                    + "    ) "
                    + ") "
                    + "AND purchase_master.purchase_date BETWEEN ? AND ? "
                    + "ORDER BY purchase_master.purchase_date";

            List<Object> params = new ArrayList<>();
            params.add(channelId);
            params.add(productName);
            params.add(startDate);
            params.add(endDate);

            List<Map<String, Object>> purchaseReport = jdbcTemplate.queryForList(sqlQuery, params.toArray());

            if (purchaseReport.isEmpty()) {
                return new ErrorDataResult<>(null, "No purchase reports found for the given criteria.");
            }

            return new SuccessDataResult<>(purchaseReport, "Purchase report retrieved successfully.");
        } catch (DataAccessException e) {
            // Log the exception (use a proper logging framework in a real application)
            return new ErrorDataResult<>(null, "Failed to retrieve purchase report: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getSaleReport(String productName, Date startDate, Date endDate,
            int channelId, String saleType) {
        try {
            // Validate mandatory parameters
            if (productName == null || productName.isEmpty()) {
                return new ErrorDataResult<>(null, "Product name is required.");
            }
            if (startDate == null || endDate == null) {
                return new ErrorDataResult<>(null, "Start date and end date are required.");
            }

            // Construct the SQL query
            String sqlQuery = "SELECT DISTINCT sale.auto_sale_id, sale.sale_id, sale.sale_date, cc.name, "
                    + "sale.reference, sale.remarks, "
                    + "(SELECT account_user.company_name FROM account_user WHERE account_user.id = sale.customer_id) AS company_name, "
                    + "(SELECT CONCAT(account_user.first_name, ' ', account_user.last_name) FROM account_user WHERE account_user.id = sale.customer_id) AS full_name "
                    + "FROM sale sale, channel_channel cc "
                    + "WHERE sale.channel_id = ? and sale.transfer_channel_id = cc.id and sale.sale_type = ? and sale.sale_id IN ( "
                    + "    SELECT sale_items.sale_id "
                    + "    FROM sale_items "
                    + "    WHERE sale_items.product_id IN ( "
                    + "        SELECT product_product.id "
                    + "        FROM product_product "
                    + "        WHERE product_product.name = ? "
                    + "    ) "
                    + ") "
                    + "AND sale.sale_date BETWEEN ? AND ? "
                    + "ORDER BY sale.sale_date";

            // Prepare the parameters
            List<Object> params = new ArrayList<>();
            params.add(channelId);
            params.add(saleType);
            params.add(productName);
            params.add(startDate);
            params.add(endDate);

            // Execute the query
            List<Map<String, Object>> saleReport = jdbcTemplate.queryForList(sqlQuery, params.toArray());

            // Check if the result is empty
            if (saleReport.isEmpty()) {
                return new ErrorDataResult<>(null, "No sale reports found for the given criteria.");
            }

            return new SuccessDataResult<>(saleReport, "Sale report retrieved successfully.");
        } catch (DataAccessException e) {
            // Log the exception (use a proper logging framework in a real application)
            return new ErrorDataResult<>(null, "Failed to retrieve sale report: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getSaleReport1(String productName, Date startDate, Date endDate,
            int channelId, String saleType) {
        try {
            // Validate mandatory parameters
            if (productName == null || productName.isEmpty()) {
                return new ErrorDataResult<>(null, "Product name is required.");
            }
            if (startDate == null || endDate == null) {
                return new ErrorDataResult<>(null, "Start date and end date are required.");
            }

            // Construct the SQL query
            String sqlQuery = "SELECT DISTINCT sale.auto_sale_id, sale.sale_id, sale.sale_date, cc.name, "
                    + "sale.reference, sale.remarks, "
                    + "(SELECT account_user.company_name FROM account_user WHERE account_user.id = sale.customer_id) AS company_name, "
                    + "(SELECT CONCAT(account_user.first_name, ' ', account_user.last_name) FROM account_user WHERE account_user.id = sale.customer_id) AS full_name "
                    + "FROM sale sale, channel_channel cc "
                    + "WHERE sale.transfer_channel_id = ? and sale.channel_id = cc.id and sale.sale_type = ? and sale.sale_id IN ( "
                    + "    SELECT sale_items.sale_id "
                    + "    FROM sale_items "
                    + "    WHERE sale_items.product_id IN ( "
                    + "        SELECT product_product.id "
                    + "        FROM product_product "
                    + "        WHERE product_product.name = ? "
                    + "    ) "
                    + ") "
                    + "AND sale.sale_date BETWEEN ? AND ? "
                    + "ORDER BY sale.sale_date";

            // Prepare the parameters
            List<Object> params = new ArrayList<>();
            params.add(channelId);
            params.add(saleType);
            params.add(productName);
            params.add(startDate);
            params.add(endDate);

            // Execute the query
            List<Map<String, Object>> saleReport = jdbcTemplate.queryForList(sqlQuery, params.toArray());

            // Check if the result is empty
            if (saleReport.isEmpty()) {
                return new ErrorDataResult<>(null, "No sale reports found for the given criteria.");
            }

            return new SuccessDataResult<>(saleReport, "Sale report retrieved successfully.");
        } catch (DataAccessException e) {
            // Log the exception (use a proper logging framework in a real application)
            return new ErrorDataResult<>(null, "Failed to retrieve sale report: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getSaleReturnReport(String productName, Date startDate,
            Date endDate, int channelId) {
        try {
            // Validate mandatory parameters
            if (productName == null || productName.isEmpty()) {
                return new ErrorDataResult<>(null, "Product name is required.");
            }
            if (startDate == null || endDate == null) {
                return new ErrorDataResult<>(null, "Start date and end date are required.");
            }

            // Construct the SQL query as a String
            String sqlQuery = "SELECT DISTINCT sale_return.auto_sale_return_id, sale_return.id, sale_return.return_date, "
                    + "sale_return.reference_no, sale_return.remarks, "
                    + "(SELECT account_user.company_name FROM account_user WHERE account_user.id = sale_return.customer_id) AS company_name, "
                    + "(SELECT CONCAT(account_user.first_name, ' ', account_user.last_name) FROM account_user WHERE account_user.id = sale_return.customer_id) AS full_name "
                    + "FROM sale_return "
                    + "WHERE sale_return.channel_id = ? and sale_return.id IN ( "
                    + "    SELECT sale_return_items.sale_return_id "
                    + "    FROM sale_return_items "
                    + "    WHERE sale_return_items.product_id IN ( "
                    + "        SELECT product_product.id "
                    + "        FROM product_product "
                    + "        WHERE product_product.name = ? "
                    + "    ) "
                    + ") "
                    + "AND sale_return.return_date BETWEEN ? AND ? "
                    + "ORDER BY sale_return.return_date";

            // Prepare the parameters
            List<Object> params = new ArrayList<>();
            params.add(channelId);
            params.add(productName);
            params.add(startDate);
            params.add(endDate);

            // Execute the query
            List<Map<String, Object>> saleReturnReport = jdbcTemplate.queryForList(sqlQuery, params.toArray());

            // Check if the result is empty
            if (saleReturnReport.isEmpty()) {
                return new ErrorDataResult<>(null, "No sale return reports found for the given criteria.");
            }

            return new SuccessDataResult<>(saleReturnReport, "Sale return report retrieved successfully.");
        } catch (DataAccessException e) {
            // Log the exception (use a proper logging framework in a real application)
            return new ErrorDataResult<>(null, "Failed to retrieve sale return report: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> retrievePurchaseReportByUserType(Date startDate, Date endDate,
            String userType, String companyName, int channelId) {
        try {
            String sqlQuery = "SELECT purchase_master.auto_purchase_id, purchase_master.purchase_id, "
                    + "purchase_master.purchase_date, purchase_master.reference_no, purchase_master.remarks, "
                    + "(SELECT account_user.company_name FROM account_user WHERE account_user.id = purchase_master.user_id) AS company_name, "
                    + "(SELECT CONCAT(account_user.first_name, ' ', account_user.last_name) FROM account_user WHERE account_user.id = purchase_master.user_id) AS full_name "
                    + "FROM purchase_master "
                    + "WHERE purchase_master.channel_id = ? and purchase_master.user_id IN (SELECT account_user.id FROM account_user";

            List<Object> params = new ArrayList<>();
            params.add(channelId);
            boolean hasCondition = false;

            if (userType != null && !userType.isEmpty()) {
                sqlQuery += " WHERE account_user.user_type = ?";
                params.add(userType);
                hasCondition = true;
            }

            if (companyName != null && !companyName.isEmpty()) {
                sqlQuery += hasCondition ? " AND " : " WHERE ";
                sqlQuery += "account_user.company_name = ?";
                params.add(companyName);
            }

            sqlQuery += ")";

            if (startDate != null && endDate != null) {
                sqlQuery += " AND purchase_master.purchase_date BETWEEN ? AND ?";
                params.add(startDate);
                params.add(endDate);
            }

            // Append ORDER BY clause
            sqlQuery += " ORDER BY purchase_master.purchase_date";

            List<Map<String, Object>> purchaseReport = jdbcTemplate.queryForList(sqlQuery, params.toArray());

            return new SuccessDataResult<>(purchaseReport, "Purchase report retrieved successfully.");
        } catch (DataAccessException e) {
            // Log the exception (use a proper logging framework in a real application)
            return new ErrorDataResult<>(null, "Failed to retrieve purchase report: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> retrieveSaleReportByUserType(Date startDate, Date endDate,
            String userType, String companyName, int channelId) {
        try {
            StringBuilder sqlQuery = new StringBuilder("SELECT sale.auto_sale_id, sale.sale_id, sale.sale_date, "
                    + "sale.reference, sale.remarks, "
                    + "(SELECT account_user.company_name FROM account_user WHERE account_user.id = sale.customer_id) AS company_name, "
                    + "(SELECT CONCAT(account_user.first_name, ' ', account_user.last_name) FROM account_user WHERE account_user.id = sale.customer_id) AS full_name "
                    + "FROM sale "
                    + "WHERE sale.channel_id = ? and sale.sale_type = ? and sale.customer_id IN (SELECT account_user.id FROM account_user");

            List<Object> params = new ArrayList<>();
            params.add(channelId);
            params.add("Sale Create");
            boolean hasCondition = false;

            if (userType != null && !userType.isEmpty()) {
                sqlQuery.append(" WHERE account_user.user_type = ?");
                params.add(userType);
                hasCondition = true;
            }

            if (companyName != null && !companyName.isEmpty()) {
                sqlQuery.append(hasCondition ? " AND " : " WHERE ");
                sqlQuery.append("account_user.company_name = ?");
                params.add(companyName);
            }

            sqlQuery.append(")");

            if (startDate != null && endDate != null) {
                sqlQuery.append(" AND sale.sale_date BETWEEN ? AND ?");
                params.add(startDate);
                params.add(endDate);
            }

            // Append ORDER BY clause
            sqlQuery.append(" ORDER BY sale.sale_date");

            List<Map<String, Object>> saleReport = jdbcTemplate.queryForList(sqlQuery.toString(), params.toArray());

            if (saleReport.isEmpty()) {
                return new ErrorDataResult<>(null, "No sale reports found for the given criteria.");
            }

            return new SuccessDataResult<>(saleReport, "Sale report retrieved successfully.");
        } catch (DataAccessException e) {
            // Log the exception (use a proper logging framework in a real application)
            return new ErrorDataResult<>(null, "Failed to retrieve sale report: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> retrieveSaleReturnReportByUserType(Date startDate, Date endDate,
            String userType, String companyName, int channelId) {
        try {
            StringBuilder sqlQuery = new StringBuilder("SELECT sale_return.auto_sale_return_id, sale_return.id, "
                    + "sale_return.return_date, sale_return.reference_no, sale_return.remarks, "
                    + "(SELECT account_user.company_name FROM account_user WHERE account_user.id = sale_return.customer_id) AS company_name, "
                    + "(SELECT CONCAT(account_user.first_name, ' ', account_user.last_name) FROM account_user WHERE account_user.id = sale_return.customer_id) AS full_name "
                    + "FROM sale_return "
                    + "WHERE sale_return.channel_id = ? and sale_return.customer_id IN (SELECT account_user.id FROM account_user");

            List<Object> params = new ArrayList<>();
            params.add(channelId);
            boolean hasCondition = false;

            if (userType != null && !userType.isEmpty()) {
                sqlQuery.append(" WHERE account_user.user_type = ?");
                params.add(userType);
                hasCondition = true;
            }

            if (companyName != null && !companyName.isEmpty()) {
                sqlQuery.append(hasCondition ? " AND " : " WHERE ");
                sqlQuery.append("account_user.company_name = ?");
                params.add(companyName);
            }

            sqlQuery.append(")");

            if (startDate != null && endDate != null) {
                sqlQuery.append(" AND sale_return.return_date BETWEEN ? AND ?");
                params.add(startDate);
                params.add(endDate);
            }

            // Append ORDER BY clause
            sqlQuery.append(" ORDER BY sale_return.return_date");

            List<Map<String, Object>> saleReturnReport = jdbcTemplate.queryForList(sqlQuery.toString(), params.toArray());

            if (saleReturnReport.isEmpty()) {
                return new ErrorDataResult<>(null, "No sale return reports found for the given criteria.");
            }

            return new SuccessDataResult<>(saleReturnReport, "Sale return report retrieved successfully.");
        } catch (DataAccessException e) {
            // Log the exception (use a proper logging framework in a real application)
            return new ErrorDataResult<>(null, "Failed to retrieve sale return report: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getSaleReturnItemsDetailsBySaleId(Integer saleId) {
        try {
            // Use a parameterized query to avoid SQL injection
            String sqlQuery = "SELECT count(sale_return_items.id) quantity, product_product.name, product_product.id, product_product.tracking_serial_no,  "
                    + "product_product.is_batch, product_productvariant.sku, purchase_item_serial_master.item_batch  "
                    + "FROM sale_return_items JOIN product_product ON product_product.id =  "
                    + "sale_return_items.product_id JOIN product_productvariant ON product_product.id = product_productvariant.product_id  "
                    + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_item_serial_master_id = sale_return_items.purchase_item_serial_id  "
                    + "WHERE sale_return_items.sale_return_id = ? group by product_product.name, product_product.tracking_serial_no,  "
                    + "product_product.is_batch, product_productvariant.sku, product_product.id,  "
                    + "purchase_item_serial_master.item_batch "
                    + "ORDER BY product_product.name ";

            // Execute the query with the provided saleId
            List<Map<String, Object>> purchaseDetails = jdbcTemplate.queryForList(sqlQuery, saleId);

            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                return new SuccessDataResult<>(purchaseDetails, "Sale items details retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No Sale items details found for the given ID.");
            }
        } catch (EmptyResultDataAccessException e) {
            // Specific exception when no results are found
            return new SuccessDataResult<>(null, "No Sale items details found for the given ID.");
        } catch (Exception e) {
            System.err.println("An error occurred while retrieving Sale items details: " + e.getMessage());
            return new ErrorDataResult<>(null, "An error occurred while retrieving Sale items details.");
        }
    }

    public Result updateItemDetails(String itemBarCode, String serialNumber, Date expiryDate, Integer purchaseItemSerialMasterId) {
        try {
            String sqlQuery = "UPDATE purchase_item_serial_master SET "
                    + "item_barcode = ?, "
                    + "item_serial_number = ?, "
                    + "item_serial_expiry_date = ? "
                    + "WHERE purchase_item_serial_master_id = ?";

            int rowsAffected = jdbcTemplate.update(sqlQuery, itemBarCode, serialNumber, expiryDate, purchaseItemSerialMasterId);

            if (rowsAffected > 0) {
                return new SuccessResult("Item details updated successfully.");
            } else {
                return new ErrorResult("No item found with the specified ID.");
            }
        } catch (DataAccessException dataAccessException) {
            return new ErrorResult("Failed to update item details: " + dataAccessException.getMessage());
        } catch (Exception e) {
            return new ErrorResult("An unexpected error occurred: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> retrieveProductListByproductNameOrSku(String productNameOrSku) {
        try {
            String sqlQuery = "SELECT DISTINCT product_product.id, product_product.name AS product_name,  \n"
                    + "product_productvariant.sku, product_product.sno_pattern, product_product.tracking_serial_no, product_product.is_product_service, \n"
                    + "product_product.is_batch FROM product_product \n"
                    + "JOIN product_productvariant ON product_productvariant.id = product_product.default_variant_id  \n"
                    + "WHERE LOWER(product_product.name) LIKE ? OR LOWER(product_productvariant.sku) LIKE ? \n"
                    + "order by product_product.name";

            List<Map<String, Object>> result = jdbcTemplate.queryForList(sqlQuery,
                    "%" + productNameOrSku.toLowerCase() + "%", "%" + productNameOrSku.toLowerCase() + "%");

            if (!result.isEmpty()) {
                return new SuccessDataResult<>(result, "Item details retrieved successfully.");
            } else {
                return new ErrorDataResult<>("No items found matching the specified criteria.");
            }
        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>("Failed to retrieve item details: " + dataAccessException.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("An unexpected error occurred: " + e.getMessage());
        }
    }

    public DataResult<Map<String, Object>> retrieveProductServiceDetailsById(Integer saleServiceId) {
        try {
            // SQL query to retrieve product service details by saleServiceId
            String sqlQuery = "SELECT sale_service_id, service_date, remarks, solution, attachment, sale_id "
                    + "FROM sale_service_master WHERE sale_service_id = ?";

            // Execute the query using jdbcTemplate
            Map<String, Object> result = jdbcTemplate.queryForMap(sqlQuery, saleServiceId);

            // Check if the result is found
            if (result != null && !result.isEmpty()) {
                return new SuccessDataResult<>(result, "Product service details retrieved successfully.");
            } else {
                return new ErrorDataResult<>("No service details found for the given ID.");
            }

        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>("Failed to retrieve product service details: " + dataAccessException.getMessage());
        } catch (Exception exception) {
            return new ErrorDataResult<>("An unexpected error occurred while retrieving service details: " + exception.getMessage());
        }
    }

    public DataResult<Map<String, Object>> getUserAddressDetails(Integer userId) {
        try {
            // SQL query to retrieve user and address details
            String sqlQuery = "SELECT account_user.id, account_address.street_address_1, account_address.street_address_2, "
                    + "account_address.city, account_address.postal_code, account_address.country, account_address.country_area "
                    + "FROM account_user "
                    + "JOIN account_user_addresses ON account_user.id = account_user_addresses.user_id "
                    + "JOIN account_address ON account_user_addresses.address_id = account_address.id "
                    + "WHERE account_user.id = ? "
                    + "ORDER BY account_user.id DESC";

            // Execute the query and pass the userId as the parameter
            Map<String, Object> userAddressDetails = jdbcTemplate.queryForMap(sqlQuery, userId);

            // Check if user details were found
            if (userAddressDetails != null && !userAddressDetails.isEmpty()) {
                return new SuccessDataResult<>(userAddressDetails, "User address details retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No address details found for the specified user ID.");
            }
        } catch (EmptyResultDataAccessException e) {
            // Handle case where no results are found
            return new SuccessDataResult<>(null, "No address details found for the specified user ID.");
        } catch (DataAccessException e) {
            // Handle general database access errors
            return new ErrorDataResult<>(null, "An error occurred while retrieving user address details: " + e.getMessage());
        }
    }

    public boolean deletePurchaseMasterItems(int purchaseMasterId) {
        return jdbcTemplate.update("DELETE FROM purchase_master_items WHERE id = ?", purchaseMasterId) > 0;
    }

    public boolean deletePurchaseItemSerialMaster(int PurchaseItemSerialMasterId) {
        return jdbcTemplate.update("DELETE FROM purchase_item_serial_master WHERE purchase_item_serial_master_id = ?", PurchaseItemSerialMasterId) > 0;
    }

    public DataResult<List<Map<String, Object>>> getPurchaseMasterItemsDetailsByPurchaseId(Integer purchaseId) {
        try {
            // Correct SQL query with WHERE clause to filter by purchaseId
            String sqlQuery = "SELECT * FROM purchase_master_items WHERE purchase_id = ? ORDER BY purchase_id DESC";
            List<Map<String, Object>> purchaseDetails = jdbcTemplate.queryForList(sqlQuery, purchaseId);

            // Check if any details were found and return appropriate messages
            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                return new SuccessDataResult<>(purchaseDetails, "Purchase master item details retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No purchase master item details found for the given purchase ID.");
            }
        } catch (DataAccessException e) {
            // Return error message in case of an exception
            return new ErrorDataResult<>(null, "Failed to retrieve purchase master item details: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getPurchaseItemSerialMasterDetailsByPurchaseMasterId(Integer purchaseMasterId) {
        try {
            // Correct SQL query with WHERE clause to filter by purchaseMasterId
            String sqlQuery = "SELECT * FROM purchase_item_serial_master WHERE purchase_master_id = ? "
                    + "ORDER BY purchase_item_serial_master_id DESC";
            List<Map<String, Object>> purchaseDetails = jdbcTemplate.queryForList(sqlQuery, purchaseMasterId);

            // Check if any details were found and return appropriate messages
            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                return new SuccessDataResult<>(purchaseDetails, "Purchase item serial master details retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No purchase item serial master details found for the given purchase master ID.");
            }
        } catch (DataAccessException e) {
            // Return error message in case of an exception
            return new ErrorDataResult<>(null, "Failed to retrieve purchase item serial master details: " + e.getMessage());
        }
    }

    // delete sale query
    public DataResult<List<Map<String, Object>>> getSaleDetailsBySaleId(int saleId) {
        try {
            String sqlQuery = "SELECT * FROM sale WHERE sale_id = ?";
            List<Map<String, Object>> saleDetails = jdbcTemplate.queryForList(sqlQuery, saleId);

            if (saleDetails != null && !saleDetails.isEmpty()) {
                return new SuccessDataResult<>(saleDetails, "Sale details retrieved successfully.");
            } else {
                return new SuccessDataResult<>(null, "No sale details found for the given sale ID.");
            }
        } catch (DataAccessException ex) {
            return new ErrorDataResult<>(null, "Error retrieving sale details: " + ex.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> get_item_data(int saleId) {
        try {
            String sqlQuery = "select * from sale_items where sale_id = ?";
            List<Map<String, Object>> purchaseDetails = jdbcTemplate.queryForList(sqlQuery, saleId);

            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                return new SuccessDataResult<>(purchaseDetails, "Purchase item serial master details retrieved successfully");
            } else {
                return new SuccessDataResult<>(null, "No purchase item serial master details found for the given purchase master ID");
            }
        } catch (Exception e) {
            // Log the exception (use a proper logging framework in a real application)
            e.printStackTrace();
            return new ErrorDataResult<>(null, "Failed to retrieve purchase item serial master details: " + e.getMessage());
        }
    }

    public int deleteSaleMaster(int saleId) {
        return jdbcTemplate.update("DELETE FROM sale where sale_id = ?", saleId);
    }

    public int deleteSaleItems(int saleId) {
        return jdbcTemplate.update("DELETE FROM sale_items where sale_id = ?", saleId);
    }

    public DataResult<List<Map<String, Object>>> get_item_purchase_data(int purchaseId) {
        try {
            String sqlQuery = "select id from purchase_master_items where purchase_id = ?";
            List<Map<String, Object>> purchaseDetails = jdbcTemplate.queryForList(sqlQuery, purchaseId);

            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                return new SuccessDataResult<>(purchaseDetails, "Purchase item serial master details retrieved successfully");
            } else {
                return new SuccessDataResult<>(null, "No purchase item serial master details found for the given purchase master ID");
            }
        } catch (Exception e) {
            // Log the exception (use a proper logging framework in a real application)
            e.printStackTrace();
            return new ErrorDataResult<>(null, "Failed to retrieve purchase item serial master details: " + e.getMessage());
        }
    }

    public int deletePurchaseItemSerial(int purchaseId) {
        return jdbcTemplate.update("DELETE FROM purchase_item_serial_master WHERE purchase_master_id = ?", purchaseId);
    }

    public int deletePurchaseMaster(int purchaseId) {
        return jdbcTemplate.update("DELETE FROM purchase_master where purchase_id = ?", purchaseId);
    }

    public int deletePurchaseItems(int purchaseId) {
        return jdbcTemplate.update("DELETE FROM purchase_master_items where purchase_id = ?", purchaseId);
    }

    // check query
    public int checkSaleExistsByPurchaseId(Integer purchaseId) {
        try {
            // SQL query to join purchase_master, purchase_master_items, and purchase_item_serial_master
            String sqlQuery = "SELECT COUNT(*) FROM purchase_master pm "
                    + "JOIN purchase_master_items pmi ON pm.purchase_id = pmi.purchase_id "
                    + "JOIN purchase_item_serial_master pism ON pmi.id = pism.purchase_master_id "
                    + "WHERE pm.purchase_id = ? AND pism.sale_id IS NOT NULL";

            // Execute the query and get the count
            Integer count = jdbcTemplate.queryForObject(sqlQuery, new Object[]{purchaseId}, Integer.class);

            // Return the count
            return count != null ? count : 0; // Return 0 if count is null

        } catch (DataAccessException e) {
            return 0;  // Return 0 or some default value in case of an error
        }
    }

    public int checkSaleReturnExistsByPurchaseId(Integer purchaseId) {
        try {
            // SQL query to join purchase_master, purchase_master_items, and purchase_item_serial_master
            String sqlQuery = "SELECT COUNT(*) AS total_count "
                    + "FROM purchase_master pm "
                    + "JOIN purchase_master_items pmi ON pm.purchase_id = pmi.purchase_id "
                    + "JOIN purchase_item_serial_master pism ON pmi.id = pism.purchase_master_id "
                    + "JOIN sale_return_items ON sale_return_items.purchase_item_serial_id = pism.purchase_item_serial_master_id "
                    + "WHERE pm.purchase_id = ?;";

            // Execute the query and get the count
            Integer count = jdbcTemplate.queryForObject(sqlQuery, new Object[]{purchaseId}, Integer.class);

            // Return the count
            return count != null ? count : 0; // Return 0 if count is null

        } catch (DataAccessException e) {
            return 0;  // Return 0 or some default value in case of an error
        }
    }

    public int checkSaleReturnExistsBySaleId(Integer saleId) {
        try {
            // SQL query to count sale returns related to the given sale ID
            String sqlQuery = "SELECT COUNT(*) AS total_count "
                    + "FROM sale_return sr "
                    + "JOIN sale_return_items sri ON sri.sale_return_id = sr.id "
                    + "JOIN sale_items si ON si.sale_item_id = sri.sale_id "
                    + "JOIN purchase_item_serial_master pism ON pism.purchase_item_serial_master_id = sri.purchase_item_serial_id "
                    + "WHERE si.sale_id = ?";

            // Execute the query and get the count
            Integer count = jdbcTemplate.queryForObject(sqlQuery, new Object[]{saleId}, Integer.class);

            // Return the count
            return count != null ? count : 0; // Return 0 if count is null

        } catch (DataAccessException e) {
            // Return 0 in case of an exception
            return 0; // or handle error logging as needed
        }
    }

    public Result updateProductId(Integer purchaseItemSerialMasterId, String newProductName) {
        try {
            // Step 1: Retrieve the product_id from the product_product table based on the newProductName
            String productIdQuery = "SELECT id FROM product_product WHERE name = ?";
            Integer newProductId = jdbcTemplate.queryForObject(productIdQuery, new Object[]{newProductName}, Integer.class);

            if (newProductId == null) {
                return new ErrorResult("Error: No product found with the name: " + newProductName);
            }

            // Step 2: Retrieve the current quantity for the item in purchase_master_items
            String quantityQuery = "SELECT quantity, purchase_id FROM purchase_master_items "
                    + "WHERE purchase_master_items.id IN ( "
                    + "    SELECT purchase_master_id "
                    + "    FROM purchase_item_serial_master "
                    + "    WHERE purchase_item_serial_master.purchase_item_serial_master_id = ? "
                    + ")";
            Map<String, Object> result = jdbcTemplate.queryForMap(quantityQuery, purchaseItemSerialMasterId);
            Integer currentQuantity = (Integer) result.get("quantity");
            Integer purchaseIdFromDatabase = (Integer) result.get("purchase_id");

            if (currentQuantity == null || currentQuantity <= 0) {
                return new ErrorResult("Error: Invalid quantity for purchase item serial master ID: " + purchaseItemSerialMasterId);
            }

            // Step 3: Check if quantity is greater than 1, update quantity and add new product if needed
            if (currentQuantity > 1) {
                // Reduce the current quantity by 1
                String reduceQuantityQuery = "UPDATE purchase_master_items SET quantity = quantity - 1 "
                        + "FROM purchase_item_serial_master "
                        + "WHERE purchase_master_items.id = purchase_item_serial_master.purchase_master_id "
                        + "AND purchase_item_serial_master.purchase_item_serial_master_id = ?";
                int update = jdbcTemplate.update(reduceQuantityQuery, purchaseItemSerialMasterId);
                // Check if any rows were affected
                if (update == 0) {
                    return new ErrorResult("Error: No rows were updated. Failed to reduce quantity for purchase item serial master ID: " + purchaseItemSerialMasterId);
                }

                // Step 4: Check product_id and purchase_id, if the same then add 1 to quantity
                String checkProductAndPurchaseIdQuery = "SELECT product_id, purchase_id FROM purchase_master_items "
                        + "WHERE purchase_id = ? AND product_id = ?";

                List<Map<String, Object>> checkResults = jdbcTemplate.queryForList(checkProductAndPurchaseIdQuery, purchaseIdFromDatabase, newProductId);

                if (!checkResults.isEmpty()) {
                    // There is a matching product_id and purchase_id, proceed with the update
                    Map<String, Object> checkResult = checkResults.get(0);  // Get the first matching result

                    Integer existingProductId = (Integer) checkResult.get("product_id");
                    Integer existingPurchaseId = (Integer) checkResult.get("purchase_id");

                    if (existingProductId.equals(newProductId) && existingPurchaseId.equals(purchaseIdFromDatabase)) {
                        // Query to update quantity and return the id of the updated row
                        String updateQuantityQuery = "UPDATE purchase_master_items "
                                + "SET quantity = quantity + 1 "
                                + "WHERE purchase_id = ? AND product_id = ? "
                                + "RETURNING id";
                        // Execute the update query and get the returned id
                        Integer updatedId = jdbcTemplate.queryForObject(updateQuantityQuery, new Object[]{existingPurchaseId, newProductId}, Integer.class);

                        // Check if the id was retrieved and return appropriate message
                        if (updatedId != null) {
                            // Step 2: Update the purchase_master_id in purchase_item_serial_master using the updatedId
                            String updateIdQuery = "UPDATE purchase_item_serial_master "
                                    + "SET purchase_master_id = ? "
                                    + "WHERE purchase_item_serial_master_id = ?";

                            // Execute the update query for purchase_item_serial_master
                            int rowsUpdatedInSerialMaster = jdbcTemplate.update(updateIdQuery, updatedId, purchaseItemSerialMasterId);

                            // Check if the update was successful
                            if (rowsUpdatedInSerialMaster > 0) {
                                return new SuccessResult("Success: Quantity updated successfully, and purchase_master_id updated for the same product and purchase ID.");
                            } else {
                                return new ErrorResult("Error: Failed to update purchase_master_id for purchase_item_serial_master.");
                            }
                        } else {
                            return new ErrorResult("Error: Failed to update quantity for the same product and purchase ID.");
                        }
                    }
                }

                // Step 5: Insert new entry into purchase_master_items with quantity 1 and new product_id
                String insertNewItemQuery = "INSERT INTO purchase_master_items (purchase_id, product_id, quantity, cost_price, discount, gst_percent, gst_amount, total_amount) "
                        + "SELECT purchase_master.purchase_id, ?, ?, ?, ?, ?, ?, ? "
                        + "FROM purchase_item_serial_master "
                        + "JOIN purchase_master_items ON purchase_master_items.id = purchase_item_serial_master.purchase_master_id "
                        + "JOIN purchase_master ON purchase_master.purchase_id = purchase_master_items.purchase_id "
                        + "WHERE purchase_item_serial_master.purchase_item_serial_master_id = ?";
                int rowsAffected = jdbcTemplate.update(insertNewItemQuery, newProductId, 1, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, purchaseItemSerialMasterId);

                if (rowsAffected > 0) {
                    // Retrieve the last inserted ID
                    Integer newPurchaseMasterItemId = jdbcTemplate.queryForObject("SELECT currval('purchase_master_items_id_seq')", Integer.class);

                    // Step 6: Update the purchase_master_id in purchase_item_serial_master with the new purchase_master_items id
                    String updateSerialMasterQuery = "UPDATE purchase_item_serial_master SET purchase_master_id = ? WHERE purchase_item_serial_master_id = ?";
                    int update1 = jdbcTemplate.update(updateSerialMasterQuery, newPurchaseMasterItemId, purchaseItemSerialMasterId);

                    // Check if any rows were affected
                    if (update1 == 0) {
                        return new ErrorResult("Error: No rows were updated. Failed to update purchase_master_id for purchase item serial master ID: " + purchaseItemSerialMasterId);
                    }
                    return new SuccessResult("Success: Updated and a new item added successfully for purchase item serial master.");
                } else {
                    return new ErrorResult("Error: Failed to insert a new purchase master item. No rows were affected.");
                }
            }

            // If quantity is 1, just update the product_id in the existing row
            String updateProductQuery = "UPDATE purchase_master_items "
                    + "SET product_id = ? "
                    + "FROM purchase_item_serial_master "
                    + "WHERE purchase_master_items.id = purchase_item_serial_master.purchase_master_id "
                    + "AND purchase_item_serial_master.purchase_item_serial_master_id = ?";
            int update2 = jdbcTemplate.update(updateProductQuery, newProductId, purchaseItemSerialMasterId);
            // Check if any rows were affected
            if (update2 == 0) {
                return new ErrorResult("Error: No rows were updated. Failed to update purchase_master_id for product Id: ");
            }

            // Step 7: Return success message
            return new SuccessResult("Success: Updated product ID successfully for purchase item serial master.");

        } catch (DataAccessException e) {
            // Handle database access errors
            return new ErrorResult("Error: Database access issue - " + e.getMessage());
        } catch (Exception e) {
            // Handle any other unexpected errors
            return new ErrorResult("Error: Unexpected issue - " + e.getMessage());
        }
    }

    public int isSalePossible(int saleId) {
        int id = 0;
        try {
            String sqlQuery = "select sa.auto_sale_return_id total from sale_return_items sr, sale_items si, sale_return sa where \n"
                    + "si.sale_item_id = sr.sale_id and sa.id = sr.sale_return_id and si.sale_id = ? LIMIT 1";
            List<Map<String, Object>> purchaseDetails = jdbcTemplate.queryForList(sqlQuery, saleId);

            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                id = (int) purchaseDetails.get(0).get("total");
            }
        } catch (Exception e) {
            // Log the exception (use a proper logging framework in a real application)
            e.printStackTrace();
        }
        return id;
    }

    public int isSaleReturnPossible(int saleReturnId) {
        int id = 0;
        try {
            String sqlQuery = "select ps.sale_id total from sale_return_items sr, sale_return sa, purchase_item_serial_master ps where \n"
                    + "ps.sale_id is not null and ps.purchase_item_serial_master_id = sr.purchase_item_serial_id and \n"
                    + "sa.id = sr.sale_return_id and sa.id = ? LIMIT 1";
            List<Map<String, Object>> purchaseDetails = jdbcTemplate.queryForList(sqlQuery, saleReturnId);

            if (purchaseDetails != null && !purchaseDetails.isEmpty()) {
                id = (int) purchaseDetails.get(0).get("total");
            }
        } catch (Exception e) {
            // Log the exception (use a proper logging framework in a real application)
            e.printStackTrace();
        }
        return id;
    }

    public Optional<Integer> getChannelIdBySaleId(Integer saleId) {
        try {
            // Check if the sale exists
            String sqlQuery1 = "SELECT COUNT(*) FROM sale WHERE sale_id = :saleId";
            Integer saleCount = namedParameterJdbcTemplate.queryForObject(
                    sqlQuery1, new MapSqlParameterSource("saleId", saleId), Integer.class
            );

            if (saleCount == 0) {
                System.out.println("Sale ID " + saleId + " not found.");
                return Optional.empty();
            }

            // Get sale_item_id(s) associated with the sale_id
            String sqlQuery2 = "SELECT sale_item_id FROM sale_items WHERE sale_id = :saleId";
            List<Integer> saleItemIds = namedParameterJdbcTemplate.queryForList(
                    sqlQuery2, new MapSqlParameterSource("saleId", saleId), Integer.class
            );

            if (saleItemIds.isEmpty()) {
                System.out.println("No sale items found for Sale ID " + saleId);
                return Optional.empty();
            }

            // Get channel_id(s) for the associated sale_item_id(s)
            String sqlQuery3 = "SELECT purchase_item_serial_master.channel_id FROM purchase_item_serial_master "
                    + "JOIN sale_items ON sale_items.purchase_item_serial_id = purchase_item_serial_master.purchase_item_serial_master_id "
                    + "WHERE sale_items.sale_item_id IN (:saleItemIds)";

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("saleItemIds", saleItemIds);

            List<Integer> channelIds = namedParameterJdbcTemplate.queryForList(sqlQuery3, params, Integer.class);

            if (channelIds.isEmpty()) {
                System.out.println("No channels found for Sale ID " + saleId);
                return Optional.empty();
            }

            // Return the first channel ID if available
            return Optional.of(channelIds.get(0)); // Return the first channel ID

        } catch (DataAccessException e) {
            System.out.println("Error accessing database: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<String> getChannelNameByChannelId(Integer channelId) {
        try {
            // Construct the SQL query to fetch channel data
            String sqlQuery = "SELECT name FROM channel_channel WHERE channel_channel.id = ?";

            // Retrieve data from the database using the constructed query
            List<Map<String, Object>> channelDataList = jdbcTemplate.queryForList(sqlQuery, channelId);

            // Check if any data was returned
            if (channelDataList.isEmpty()) {
                System.out.println("No channel found with ID: " + channelId);
                return Optional.empty(); // Return empty if no channel found
            }

            // Extract the channel name from the result
            String channelName = (String) channelDataList.get(0).get("name");
            return Optional.of(channelName); // Wrap channel name in an Optional

        } catch (DataAccessException dataAccessException) {
            // Error occurred during database access
            System.out.println("Failed to retrieve channel data: " + dataAccessException.getMessage());
            return Optional.empty(); // Return empty in case of error
        } catch (Exception e) {
            // Other unexpected errors
            System.out.println("An unexpected error occurred: " + e.getMessage());
            return Optional.empty(); // Return empty in case of unexpected error
        }
    }

    public DataResult<List<Map<String, Object>>> getAccountsByUserType(List<String> userTypes) {
        try {
            // Validate input
            if (userTypes == null || userTypes.isEmpty()) {
                return new ErrorDataResult<>(null, "User types list is empty or null.");
            }

            // Dynamically construct the SQL query with placeholders
            String placeholders = String.join(",", Collections.nCopies(userTypes.size(), "?"));
            String sqlQuery = "SELECT * FROM account_user WHERE user_type IN (" + placeholders + ") ORDER BY id DESC";

            // Retrieve data from the database using the constructed query
            List<Map<String, Object>> accountDataList = jdbcTemplate.queryForList(sqlQuery, userTypes.toArray());

            // Check if any data was returned
            if (accountDataList.isEmpty()) {
                System.out.println("No accounts found for the given user types: " + userTypes);
                return new ErrorDataResult<>(null, "No accounts found for the specified user types.");
            }

            // Return the list of accounts
            return new SuccessDataResult<>(accountDataList, "Accounts retrieved successfully.");
        } catch (DataAccessException dataAccessException) {
            // Log and return database error
            return new ErrorDataResult<>(null, "Database error: Unable to fetch accounts. " + dataAccessException.getMessage());
        } catch (Exception e) {
            // Log and return unexpected error
            return new ErrorDataResult<>(null, "Unexpected error occurred while fetching accounts. " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> getBatchTrueProducts(int channelId, Integer pageNumber, Integer pageSize,
            String brand, String productType, Date startDate, Date endDate,
            String productName, String batchNumber, Integer purchaseItemSerialMasterId) {
        try {
            StringBuilder sql = new StringBuilder(
                    "SELECT "
                    + "product_product.name AS productName, "
                    + "product_producttype.name AS productType, "
                    + "product_category.name AS categoryName, "
                    + "COUNT(CASE "
                    + "WHEN purchase_item_serial_master.sale_id IS NULL "
                    + "AND purchase_item_serial_master.channel_id = :channelId "
                    + "THEN purchase_master_items.purchase_id "
                    + "END) AS quantity, "
                    + "product_productvariant.sku AS sku, "
                    + "purchase_item_serial_master.item_batch, "
                    + "purchase_item_serial_master.item_barcode, "
                    + "purchase_item_serial_master.item_serial_expiry_date "
                    + "FROM product_product "
                    + "JOIN product_producttype ON product_producttype.id = product_product.product_type_id "
                    + "JOIN product_category ON product_category.id = product_product.category_id "
                    + "JOIN product_productvariant ON product_productvariant.product_id = product_product.id "
                    + "LEFT JOIN purchase_master_items ON purchase_master_items.product_id = product_product.id "
                    + "LEFT JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_master_id = purchase_master_items.id "
                    + "LEFT JOIN purchase_master ON purchase_master.purchase_id = purchase_master_items.purchase_id "
                    + "WHERE product_product.is_batch = 'true' ");

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("channelId", channelId);

            if (productName != null && !productName.isEmpty()) {
                sql.append("AND product_product.name LIKE :productName ");
                params.addValue("productName", "%" + productName + "%");
            }

            if (batchNumber != null && !batchNumber.isEmpty()) {
                sql.append("AND purchase_item_serial_master.item_batch = :batchNumber ");
                params.addValue("batchNumber", batchNumber);
            }

            if (purchaseItemSerialMasterId != null) {
                sql.append("AND purchase_item_serial_master.purchase_item_serial_master_id = :purchaseItemSerialMasterId ");
                params.addValue("purchaseItemSerialMasterId", purchaseItemSerialMasterId);
            }

            if (brand != null && !brand.isEmpty()) {
                sql.append("AND product_category.name = :brandName ");
                params.addValue("brandName", brand);
            }

            if (productType != null && !productType.isEmpty()) {
                sql.append("AND product_producttype.name = :productType ");
                params.addValue("productType", productType);
            }

            if (startDate != null && endDate != null) {
                if (startDate.after(endDate)) {
                    return new ErrorDataResult<>("The start date cannot be after the end date.");
                }
                sql.append("AND purchase_item_serial_master.item_serial_expiry_date BETWEEN :startDate AND :endDate ");
                params.addValue("startDate", startDate);
                params.addValue("endDate", endDate);
            }

            sql.append(
                    "GROUP BY "
                    + "product_product.name, "
                    + "product_producttype.name, "
                    + "product_category.name, "
                    + "product_productvariant.sku, "
                    + "purchase_item_serial_master.item_batch, "
                    + "purchase_item_serial_master.item_barcode, "
                    + "purchase_item_serial_master.item_serial_expiry_date "
                    + "ORDER BY purchase_item_serial_master.item_serial_expiry_date DESC ");

            if (pageNumber != null && pageSize != null) {
                int offset = (pageNumber - 1) * pageSize;
                sql.append("LIMIT :pageSize OFFSET :offset ");
                params.addValue("pageSize", pageSize);
                params.addValue("offset", offset);
            }

            List<Map<String, Object>> stockReport = namedParameterJdbcTemplate.queryForList(sql.toString(), params);

            if (!stockReport.isEmpty()) {
                return new SuccessDataResult<>(stockReport, "Batch-specific product data retrieved successfully.");
            } else {
                return new ErrorDataResult<>("No batch-specific product data found.");
            }
        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>("Database access error while retrieving batch-specific product data: " + dataAccessException.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("An error occurred while retrieving batch-specific product data: " + e.getMessage());
        }
    }

    public DataResult<List<Map<String, Object>>> fetchBatchProductDetails(String brand, String productType, String batchNumber, String expiryDate, Integer channelId) {
        String sqlQuery = "SELECT product_product.name AS productName, "
                + "product_producttype.name AS productType, "
                + "product_category.name AS categoryName, "
                + "COUNT(purchase_master_items.purchase_id) AS quantity, "
                + "product_productvariant.sku AS sku, "
                + "purchase_item_serial_master.item_batch, "
                + "purchase_item_serial_master.item_barcode, "
                + "purchase_item_serial_master.item_serial_expiry_date "
                + "FROM product_product "
                + "JOIN product_producttype ON product_producttype.id = product_product.product_type_id "
                + "JOIN product_category ON product_category.id = product_product.category_id "
                + "JOIN purchase_master_items ON purchase_master_items.product_id = product_product.id "
                + "JOIN product_productvariant ON product_productvariant.product_id = product_product.id "
                + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_master_id = purchase_master_items.id "
                + "JOIN purchase_master ON purchase_master.purchase_id = purchase_master_items.purchase_id "
                + "WHERE purchase_item_serial_master.sale_id IS NULL "
                + "AND purchase_item_serial_master.channel_id = :channelId "
                + "AND product_product.is_batch = 'true'";

        // Dynamically append conditions to the query
        if (brand != null && !brand.isEmpty()) {
            sqlQuery += " AND product_category.name = :brand";
        }
        if (productType != null && !productType.isEmpty()) {
            sqlQuery += " AND product_producttype.name = :productType";
        }
        if (batchNumber != null && !batchNumber.isEmpty()) {
            sqlQuery += " AND purchase_item_serial_master.item_batch = :batchNumber";
        }
        if (expiryDate != null && !expiryDate.isEmpty()) {
            sqlQuery += " AND purchase_item_serial_master.item_serial_expiry_date = CAST(:expiryDate AS DATE)";
        }

        // Adding GROUP BY clause
        sqlQuery += " GROUP BY product_product.name, "
                + "product_producttype.name, "
                + "product_category.name, "
                + "product_productvariant.sku, "
                + "purchase_item_serial_master.item_batch, "
                + "purchase_item_serial_master.item_barcode, "
                + "purchase_item_serial_master.item_serial_expiry_date";

        // Prepare parameters
        Map<String, Object> params = new HashMap<>();
        params.put("channelId", channelId);
        if (brand != null && !brand.isEmpty()) {
            params.put("brand", brand);
        }
        if (productType != null && !productType.isEmpty()) {
            params.put("productType", productType);
        }
        if (batchNumber != null && !batchNumber.isEmpty()) {
            params.put("batchNumber", batchNumber);
        }
        if (expiryDate != null && !expiryDate.isEmpty()) {
            try {
                // Ensure expiryDate is in 'yyyy-MM-dd' format
                java.sql.Date sqlDate = java.sql.Date.valueOf(expiryDate);
                params.put("expiryDate", sqlDate);
            } catch (IllegalArgumentException e) {
                return new ErrorDataResult<>(null, "Invalid date format. Expected format: yyyy-MM-dd.");
            }
        }
        try {
            // Execute the query and fetch results using JdbcTemplate
            List<Map<String, Object>> stockReport = namedParameterJdbcTemplate.queryForList(sqlQuery, params);

            // Check if any data is returned
            if (stockReport.isEmpty()) {
                return new ErrorDataResult<>(null, "No product details found for the provided criteria.");
            }

            // Return the entire result list
            return new SuccessDataResult<>(stockReport);

        } catch (DataAccessException e) {
            // Handle database connection or query execution errors
            return new ErrorDataResult<>(null, "An error occurred while fetching product details. Database error: " + e.getMessage());
        } catch (Exception e) {
            // Handle unexpected errors
            return new ErrorDataResult<>(null, "An unexpected error occurred. " + e.getMessage());
        }
    }

    public Result updatePurchaseItemDetails(String oldBatchNumber, String newBatchNumber, String oldExpiryDate, String newExpiryDate) {
        try {
            // Define the date format (adjust format based on the expected input format)
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Convert String to Date (handle parsing errors)
            Date parsedOldExpiryDate = dateFormat.parse(oldExpiryDate);
            Date parsedNewExpiryDate = dateFormat.parse(newExpiryDate);

            // Convert Date to SQL Date for database compatibility
            java.sql.Date sqlOldExpiryDate = new java.sql.Date(parsedOldExpiryDate.getTime());
            java.sql.Date sqlNewExpiryDate = new java.sql.Date(parsedNewExpiryDate.getTime());

            // SQL query to update expiry date and batch number based on old values
            String sqlQuery = "UPDATE purchase_item_serial_master SET "
                    + "item_serial_expiry_date = ?, "
                    + "item_batch = ? "
                    + "WHERE item_batch = ? AND item_serial_expiry_date = ?";

            // Execute the update query with the provided parameters
            int rowsAffected = jdbcTemplate.update(sqlQuery, sqlNewExpiryDate, newBatchNumber, oldBatchNumber, sqlOldExpiryDate);

            if (rowsAffected > 0) {
                // If update was successful, return success message
                return new SuccessResult("Item details updated successfully for batch number: " + newBatchNumber);
            } else {
                // If no rows were affected, return error message
                return new ErrorResult("No item found with the specified batch number and expiry date: " + oldBatchNumber + ", " + oldExpiryDate);
            }
        } catch (Exception e) {
            // Handle any exceptions (parsing or database errors)
            return new ErrorResult("An error occurred while processing the dates: " + e.getMessage());
        }
    }

    public int getChannelCountByEmail(String userEmail) {
        try {
            String sqlQuery = "SELECT COUNT(user_channels.channel_id) AS channel_count "
                    + "FROM account_user "
                    + "LEFT JOIN user_channels ON account_user.id = user_channels.user_id "
                    + "LEFT JOIN channel_channel ON user_channels.channel_id = channel_channel.id "
                    + "WHERE account_user.email = ? "
                    + "GROUP BY account_user.id";

            // Retrieve the channel count from the database
            return jdbcTemplate.queryForObject(sqlQuery, new Object[]{userEmail}, Integer.class);
        } catch (EmptyResultDataAccessException emptyResultException) {
            // No data found, return 0
            return 0;
        } catch (Exception e) {
            // Log and return 0 in case of any other error
            e.printStackTrace();
            return 0;
        }
    }

    public DataResult<List<Map<String, Object>>> getCombinedStockReportProductWise(
            Integer pageNumber, Integer pageSize,
            String productName, String productTypeName,
            String categoryName, List<Integer> channelIds) {

        try {
            StringBuilder sql = new StringBuilder(
                    "SELECT "
                    + "product_product.name AS productName, "
                    + "product_producttype.name AS productType, "
                    + "product_category.name AS categoryName, "
                    + "product_productvariant.sku AS sku, "
                    + "COUNT(CASE "
                    + "WHEN purchase_item_serial_master.sale_id IS NULL "
                    + "AND purchase_master.channel_id IN (:channelIds) "
                    + "THEN purchase_master_items.purchase_id END) AS quantity "
                    + "FROM product_product "
                    + "JOIN product_producttype ON product_producttype.id = product_product.product_type_id "
                    + "JOIN product_category ON product_category.id = product_product.category_id "
                    + "JOIN product_productvariant ON product_productvariant.product_id = product_product.id "
                    + "LEFT JOIN purchase_master_items ON purchase_master_items.product_id = product_product.id "
                    + "LEFT JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_master_id = purchase_master_items.id "
                    + "LEFT JOIN purchase_master ON purchase_master.purchase_id = purchase_master_items.purchase_id "
            );

            MapSqlParameterSource params = new MapSqlParameterSource();
            List<String> conditions = new ArrayList<>();

            // Optional filters
            if (productName != null && !productName.isEmpty()) {
                conditions.add("product_product.name = :productName");
                params.addValue("productName", productName);
            }

            if (productTypeName != null && !productTypeName.isEmpty()) {
                conditions.add("product_producttype.name = :productTypeName");
                params.addValue("productTypeName", productTypeName);
            }

            if (categoryName != null && !categoryName.isEmpty()) {
                conditions.add("product_category.name = :categoryName");
                params.addValue("categoryName", categoryName);
            }

            // Add channelIds to parameters no matter what
            params.addValue("channelIds", channelIds != null ? channelIds : new ArrayList<>());

            // Append WHERE clause if conditions are present
            if (!conditions.isEmpty()) {
                sql.append("WHERE ").append(String.join(" AND ", conditions)).append(" ");
            }

            // Grouping and ordering
            sql.append("GROUP BY product_product.name, product_producttype.name, product_category.name, product_productvariant.sku ");
            sql.append("ORDER BY product_product.name ASC ");

            // Pagination
            if (pageNumber != null && pageSize != null && pageNumber > 0 && pageSize > 0) {
                int offset = (pageNumber - 1) * pageSize;
                sql.append("LIMIT :pageSize OFFSET :offset ");
                params.addValue("pageSize", pageSize);
                params.addValue("offset", offset);
            }

            List<Map<String, Object>> stockReport = namedParameterJdbcTemplate.queryForList(sql.toString(), params);

            if (!stockReport.isEmpty()) {
                return new SuccessDataResult<>(stockReport, "Stock report retrieved successfully. Found " + stockReport.size() + " records.");
            } else {
                return new ErrorDataResult<>("No stock report data found for the given criteria.");
            }

        } catch (DataAccessException dataAccessException) {
            return new ErrorDataResult<>("Failed to retrieve stock report due to a data access error: " + dataAccessException.getMessage());
        } catch (Exception exception) {
            return new ErrorDataResult<>("Failed to retrieve stock report: " + exception.getMessage());
        }
    }

    public int countTotalStockReportProductWiseByChannelId() {
        String sql = "SELECT COUNT(DISTINCT product_product.id) "
                + "FROM product_product "
                + "JOIN product_producttype ON product_producttype.id = product_product.product_type_id "
                + "JOIN product_category ON product_category.id = product_product.category_id "
                + "JOIN purchase_master_items ON purchase_master_items.product_id = product_product.id "
                + "JOIN product_productvariant ON product_productvariant.product_id = product_product.id "
                + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_master_id = purchase_master_items.id "
                + "JOIN purchase_master ON purchase_master.purchase_id = purchase_master_items.purchase_id "
                + "WHERE purchase_item_serial_master.sale_id IS NULL";

        MapSqlParameterSource params = new MapSqlParameterSource();

        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    public DataResult<Map<String, Object>> findBarcodeByProductAndChannelId(String productName, int channelId) {
        try {
            String sqlQuery = "SELECT 1 AS quantity, product_product.name, product_product.id, product_product.tracking_serial_no, product_product.is_batch, "
                    + "purchase_item_serial_master.item_barcode, purchase_item_serial_master.item_serial_number, "
                    + "purchase_item_serial_master.sale_id, purchase_item_serial_master.purchase_item_serial_master_id, "
                    + "purchase_master_items.discount, purchase_master_items.gst_percent, "
                    + "purchase_master_items.gst_amount, purchase_master_items.total_amount, purchase_master_items.cost_price, "
                    + "purchase_item_serial_master.item_serial_expiry_date "
                    + "FROM purchase_master_items "
                    + "JOIN product_product ON product_product.id = purchase_master_items.product_id "
                    + "JOIN purchase_item_serial_master ON purchase_item_serial_master.purchase_master_id = purchase_master_items.id "
                    + "WHERE product_product.name = ? "
                    + "AND purchase_item_serial_master.sale_id IS NULL "
                    + "AND purchase_item_serial_master.channel_id = ? "
                    + "ORDER BY purchase_item_serial_master.item_serial_expiry_date ASC "
                    + "LIMIT 1";

            // Execute the query by passing parameters for the placeholders
            List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sqlQuery, productName, channelId);

            if (!queryForList.isEmpty()) {
                return new SuccessDataResult<>(queryForList.get(0), "Barcode retrieved successfully");
            } else {
                return new ErrorDataResult<>(null, "No product found for the provided criteria");
            }
        } catch (DataAccessException e) {
            return new ErrorDataResult<>("Error occurred while retrieving barcode: " + e.getMessage());
        } catch (Exception e) {
            return new ErrorDataResult<>("Unexpected error occurred while retrieving barcode: " + e.getMessage());
        }
    }

}
