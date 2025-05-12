/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import com.AvMeditechInventory.controller.UserLoginController;
import com.AvMeditechInventory.dtos.AddressDto;
import com.AvMeditechInventory.dtos.CustomerAndSupplierDto;
import com.AvMeditechInventory.dtos.ProductDto;
import com.AvMeditechInventory.dtos.StaffDto;
import com.AvMeditechInventory.entities.AccountUser;
import com.AvMeditechInventory.entities.Category;
import com.AvMeditechInventory.entities.Channels;
import com.AvMeditechInventory.entities.Permission;
import com.AvMeditechInventory.entities.PermissionGroups;
import com.AvMeditechInventory.entities.ProductType;
import com.AvMeditechInventory.entities.ProductVariant;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.ErrorResult;
import com.AvMeditechInventory.results.LoginError;
import com.AvMeditechInventory.results.LoginResponse;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.results.SuccessResult;
import com.AvMeditechInventory.results.User;
import com.AvMeditechInventory.service.StaffService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public class CommonUtil {

    // Helper method to validate mobile number format using regex
    public static boolean isValidMobileNumber(String mobileNumber) {
        // Adjust the regex pattern based on your specific mobile number format
        String regex = "^[0-9]{10}$"; // Assumes a 10-digit mobile number format
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mobileNumber);
        return matcher.matches();
    }

    // Helper method to validate email format using a simple regular expression
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    // Generate OTP
    public static String generateOTP() {
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < Constant.OTP_LENGTH; i++) {
            int randomIndex = Constant.secureRandom.nextInt(Constant.OTP_CHARACTERS.length());
            otp.append(Constant.OTP_CHARACTERS.charAt(randomIndex));
        }

        return otp.toString();
    }

    // verify user Email
    public static String verifyUserEmail(String accessToken) {
        String email = "";
        String query = "{me{id,email}}";
        JSONObject requestBody = new JSONObject();
        requestBody.put("query", query);

        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "JWT " + accessToken));

        String apiUrl = "";
        String apiResponse = null;
        try {
            apiResponse = HTTPUtil.executeUrl(new URL(apiUrl), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "Malformed URL Exception", ex);
            return email; // Returning empty email due to exception
        }

        JSONObject responseJson = new JSONObject(apiResponse);
        JSONObject data = responseJson.optJSONObject("data");

        if (data != null) {
            JSONObject me = data.optJSONObject("me");
            if (me != null && me.has("email")) {
                email = me.getString("email");
            } else {
                Logger.getLogger(CommonUtil.class.getName()).log(Level.WARNING, "User data or email not found in response.");
            }
        } else {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.WARNING, "No data found in response.");
        }
        return email;
    }

    // token generate
    public static DataResult<LoginResponse> tokenGenerateUserAndSendApiRequest(String userEmail, String userPassword,
            AccountUser user, HttpSession session, HttpServletRequest request) {
        // Create a response object to store the results
        LoginResponse lr = new LoginResponse();

        // Prepare headers for the HTTP request
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));

        // Define the API URL
        String url = checkDomainFromRequest(request);

        // Define the GraphQL mutation string
        String resData = "mutation {tokenCreate(email: \"" + userEmail + "\", password: \"" + userPassword + "\") {errors {field, message}, token, user {id, firstName, lastName, dateOfBirth, age, userType, permissionGroups {id, name, permissions {code, name}}}}}";

        // Create a JSON object to hold the request data
        JSONObject obje = new JSONObject();
        obje.put("query", resData);

        String apiResponse = "";
        try {
            // Execute the HTTP request
            apiResponse = HTTPUtil.executeUrl(new URL(url), obje.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            // Handle malformed URL exception
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "Malformed URL", ex);
            return new ErrorDataResult<>("Malformed URL");
        }

        // Parse the API response
        JSONObject resobj = new JSONObject(apiResponse);
        JSONObject tokenCreate = resobj.optJSONObject("data").optJSONObject("tokenCreate");
        if (tokenCreate == null) {
            return new ErrorDataResult<>("No tokenCreate data found in the response.");
        }

        // Check for errors in the response
        JSONArray errors = tokenCreate.optJSONArray("errors");
        if (errors != null && errors.length() > 0) {
            JSONObject error = errors.optJSONObject(0);
            if (error != null) {
                String field = error.optString("field");
                String message = error.optString("message");
                ArrayList<LoginError> errorList = new ArrayList<>();
                errorList.add(new LoginError(field, message));
                lr.setErrors(errorList);
            }
            lr.setApi_status(false);
            return new ErrorDataResult<>("API returned errors");
        }

        // Extract user data from the response
        JSONObject userData = tokenCreate.optJSONObject("user");
        if (userData == null) {
            return new ErrorDataResult<>("No user data found in the response.");
        }

        // Create and populate the user object
        User user1 = new User();
        user1.setFirstName(userData.optString("firstName"));
        user1.setLastName(userData.optString("lastName"));
        user1.setId(userData.optString("id"));
        user1.setEmail(userData.optString("email"));
        user1.setUserType(user.getUserType());

        // Extract and populate permissionGroups
        JSONArray permissionGroupsArray = userData.optJSONArray("permissionGroups");
        if (permissionGroupsArray != null) {
            List<PermissionGroups> permissionGroups = new ArrayList<>();
            for (int i = 0; i < permissionGroupsArray.length(); i++) {
                JSONObject groupObject = permissionGroupsArray.optJSONObject(i);
                PermissionGroups group = new PermissionGroups();
                group.setPermissionGroupsId(groupObject.optString("id"));
                group.setPermissionGroupsName(groupObject.optString("name"));

                // Extract and populate permissions
                JSONArray permissionsArray = groupObject.optJSONArray("permissions");
                if (permissionsArray != null) {
                    List<Permission> permissions = new ArrayList<>();
                    for (int j = 0; j < permissionsArray.length(); j++) {
                        JSONObject permissionObject = permissionsArray.optJSONObject(j);
                        Permission permission = new Permission();
                        permission.setPermissionCode(permissionObject.optString("code"));
                        permission.setPermissionName(permissionObject.optString("name"));
                        permissions.add(permission);
                    }
                    group.setPermissions(permissions);
                }
                permissionGroups.add(group);
            }
            user1.setPermissionGroups(permissionGroups);
        }

        // Set token and user details in the response object
        lr.setToken(tokenCreate.optString("token"));
        lr.setRefreshToken(tokenCreate.optString("refreshToken"));
        lr.setUser(user1);
        lr.setApi_status(true);

        // Store user information in the session
        session.setAttribute("token", lr.getToken());
        session.setAttribute("userEmail", userEmail);
        session.setAttribute("userName", user1.getFirstName() + " " + user1.getLastName());
        session.setAttribute("userId", user1.getId());
        session.setAttribute("userType", user1.getUserType());
        session.setAttribute("refreshToken", lr.getRefreshToken());

        return new SuccessDataResult<>(lr, "Successfully token generate.");
    }

    // change password mutation
    public static Result changePasswordMutation(String oldPassword, String newPassword, String authToken, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String resData;
        String apiResponse = "";
        String url = checkDomainFromRequest(request);
        JSONObject resobj;

        // Construct the GraphQL mutation
        resData = "mutation { passwordChange(oldPassword: \"" + oldPassword + "\", newPassword: \"" + newPassword
                + "\") { errors { message } user { email } } }";

        JSONObject obje = new JSONObject();
        obje.put("query", resData);

        try {
            // Execute the GraphQL mutation
            apiResponse = HTTPUtil.executeUrl(new URL(url), obje.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(UserLoginController.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorResult("Failed to change password: MalformedURLException");
        }

        // Parse the API response
        resobj = new JSONObject(apiResponse);
        JSONArray errors = resobj.optJSONObject("data").optJSONObject("passwordChange").optJSONArray("errors");
        if (errors == null || errors.length() == 0) {
            // Password change successful or no errors returned
            return new SuccessResult("Password changed successfully");
        } else {
            String errorMessage = errors.optJSONObject(0).optString("message");
            return new ErrorResult("Failed to change password: " + errorMessage);
        }
    }

    // staff create mutation
    public static Result staffCreateMutation(String firstName, String lastName, String email, String addGroups,
            String authToken, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "JWT " + authToken));

        String apiUrl = checkDomainFromRequest(request);

        // Split the groups from the input string and create a list
        String[] groupArray = addGroups.split(",");
        ArrayList<String> groupList = new ArrayList<>(Arrays.asList(groupArray));

        // Add group based on the specific condition
        if (!addGroups.equals("R3JvdXA6MQ==")) {
            //groupList.add("R3JvdXA6MTA="); // Add this group if the condition is met (not equal)
            System.out.println("groupList" + groupList);
        }

        // Create the groups string for the GraphQL mutation
        String groupsString = groupList.stream()
                .map(group -> "\"" + group.trim() + "\"")
                .collect(Collectors.joining(","));

        // Build the GraphQL mutation query string
        String query = new StringBuilder()
                .append("mutation { staffCreate(input: { email: \"")
                .append(email)
                .append("\", firstName: \"")
                .append(firstName)
                .append("\", lastName: \"")
                .append(lastName)
                .append("\", addGroups: [")
                .append(groupsString)
                .append("] }) { errors { message } user { email } } }")
                .toString();

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", query);

        try {
            // Make the HTTP POST request with the specified headers and body
            String apiResponse = HTTPUtil.executeUrl(new URL(apiUrl), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());
            JSONObject responseJson = new JSONObject(apiResponse);
            JSONObject data = responseJson.getJSONObject("data");

            if (data.has("staffCreate")) {
                JSONObject staffCreateData = data.getJSONObject("staffCreate");
                JSONArray errors = staffCreateData.getJSONArray("errors");

                if (errors.length() == 0) {
                    // Staff creation successful
                    return new SuccessResult("Staff created successfully with email: " + staffCreateData.getJSONObject("user").getString("email"));
                } else {
                    // Staff creation failed, process error messages
                    StringBuilder errorMessage = new StringBuilder("Failed to create staff: ");
                    for (int i = 0; i < errors.length(); i++) {
                        errorMessage.append(errors.getJSONObject(i).getString("message"));
                        if (i < errors.length() - 1) {
                            errorMessage.append(", ");
                        }
                    }
                    return new ErrorResult(errorMessage.toString());
                }
            } else {
                return new ErrorResult("Unexpected response from server: 'staffCreate' data missing.");
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(StaffService.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorResult("Failed to create staff: Invalid API URL.");
        } catch (Exception e) {
            Logger.getLogger(StaffService.class.getName()).log(Level.SEVERE, null, e);
            return new ErrorResult("Failed to create staff: " + e.getMessage());
        }
    }

    // staff update mutation
    public static Result staffUpdateMutation(String staffId, String firstName, String lastName,
            String email, String addGroups, String authToken, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "JWT " + authToken));

        String apiUrl = checkDomainFromRequest(request);
        String[] groupArray = addGroups.split(",");
        String query = "mutation { staffUpdate(id: \"" + staffId + "\", input: { email: \"" + email + "\", firstName: \"" + firstName + "\","
                + " lastName: \"" + lastName + "\", "
                + "addGroups: " + Arrays.stream(groupArray).map(group -> "\"" + group.trim() + "\"").collect(Collectors.joining(",", "[", "]")) + " }) { errors { message } user { id email firstName lastName } } }";

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", query);

        try {
            String apiResponse = HTTPUtil.executeUrl(new URL(apiUrl), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());
            JSONObject responseJson = new JSONObject(apiResponse);
            JSONObject data = responseJson.getJSONObject("data");
            if (data.has("staffUpdate")) {
                JSONObject staffUpdateData = data.getJSONObject("staffUpdate");
                JSONArray errors = staffUpdateData.getJSONArray("errors");
                if (errors.length() == 0) {
                    JSONObject user = staffUpdateData.getJSONObject("user");
                    String userId = user.getString("id");
                    String userFirstName = user.getString("firstName");
                    return new SuccessResult("Staff with ID: " + userId + " updated successfully. New first name: " + userFirstName);
                } else {
                    String errorMessage = errors.getJSONObject(0).getString("message");
                    return new ErrorResult("Failed to update staff: " + errorMessage);
                }
            } else {
                return new ErrorResult("Unexpected response from server");
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorResult("Failed to update staff: MalformedURLException");
        } catch (JSONException e) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, e);
            return new ErrorResult("Failed to update staff: " + e.getMessage());
        } catch (Exception e) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, e);
            return new ErrorResult("Failed to update staff: " + e.getMessage());
        }
    }

    // staff list query
    public static DataResult<List<StaffDto>> staffListQuery(Integer pageNumber, String authToken, String after,
            String isAsc, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = checkDomainFromRequest(request);
        String apiResponse = "";

        // Construct the GraphQL query with pagination and sorting
        String query;
        if (isAsc.equals("next")) {
            query = "query { staffUsers(first: " + pageNumber + ", after: \"" + after + "\", sortBy: {direction: ASC, field: FIRST_NAME}) { pageInfo { hasNextPage hasPreviousPage } edges { cursor node { id firstName lastName email mobileNo permissionGroups { name } } } } }";
        } else {
            query = "query { staffUsers(last: " + pageNumber + ", before: \"" + after + "\", sortBy: {direction: ASC, field: FIRST_NAME}) { pageInfo { hasNextPage hasPreviousPage } edges { cursor node { id firstName lastName email mobileNo permissionGroups { name } } } } }";
        }

        JSONObject obje = new JSONObject();
        obje.put("query", query);

        try {
            // Execute the GraphQL query
            apiResponse = HTTPUtil.executeUrl(new URL(url), obje.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject resobj = new JSONObject(apiResponse);
            JSONObject data = resobj.optJSONObject("data");
            if (data != null) {
                JSONObject staffUsers = data.optJSONObject("staffUsers");
                if (staffUsers != null) {
                    JSONArray edges = staffUsers.optJSONArray("edges");
                    if (edges != null && edges.length() > 0) {
                        // Extraction of staff users successful
                        List<StaffDto> staffList = new ArrayList<>();
                        for (int i = 0; i < edges.length(); i++) {
                            JSONObject edge = edges.getJSONObject(i);
                            JSONObject node = edge.getJSONObject("node");
                            String id = node.getString("id");
                            String firstName = node.getString("firstName");
                            String lastName = node.getString("lastName");
                            String email = node.getString("email");
                            String mobileNo = node.optString("mobileNo"); // handle null if needed

                            // Handle permissionGroups
                            JSONArray permissionGroups = node.optJSONArray("permissionGroups");
                            List<PermissionGroups> permissions = new ArrayList<>();
                            if (permissionGroups != null) {
                                for (int j = 0; j < permissionGroups.length(); j++) {
                                    JSONObject permissionGroup = permissionGroups.getJSONObject(j);
                                    String groupId = permissionGroup.optString("id");
                                    String groupName = permissionGroup.optString("name");
                                    permissions.add(new PermissionGroups(groupId, groupName));
                                }
                            }

                            // Create StaffDto object
                            StaffDto staffDto = new StaffDto(id, firstName, lastName, email, mobileNo, permissions);

                            // Set cursor, hasNextPage, and hasPreviousPage
                            staffDto.setCursor(edge.getString("cursor"));
                            staffDto.setHasNextPage(staffUsers.getJSONObject("pageInfo").getBoolean("hasNextPage"));
                            staffDto.setHasPreviousPage(staffUsers.getJSONObject("pageInfo").getBoolean("hasPreviousPage"));

                            staffList.add(staffDto);
                        }

                        // Return success result with staffList
                        return new SuccessDataResult<>(staffList, "Staff list fetched successfully");
                    }
                }
            }
            return new ErrorDataResult<>("Failed to fetch staff list: No data returned");
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch staff list: MalformedURLException");
        } catch (JSONException jSONException) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "JSON parsing error occurred", jSONException);
            return new ErrorDataResult<>("Failed to fetch staff list: JSON parsing error occurred" + jSONException.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch staff list: An unexpected error occurred");
        }
    }

    // To create a customer we will call accountRegister Mutation only.
    public static DataResult<String> accountRegisterMutation(String email, String password, String firstName,
            String lastName, String mobileNo, String companyName, String userCode, String userType, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));

        String resData;
        JSONObject obje;
        String apiResponse;
        String url = checkDomainFromRequest(request);
        JSONObject resobj;

        resData = "mutation { accountRegister(input: { email: \"" + email + "\", password: \"" + password + "\","
                + " firstName: \"" + firstName + "\", lastName: \"" + lastName + "\", "
                + "mobileNo: \"" + mobileNo + "\" , metadata: [{key: \"companyName\", value: \"" + companyName + "\"}, "
                + "{key: \"mobileNo\", value: \"" + mobileNo + "\"}, {key: \"userCode\", value: \"" + userCode + "\"}"
                + ", {key: \"userType\", value: \"" + userType + "\"}]}) { errors { message } user { id email } } }";
        obje = new JSONObject();
        obje.put("query", resData);

        try {
            apiResponse = HTTPUtil.executeUrl(new URL(url), obje.toString(), headers, Constant.HTTP_REQUEST.POST.name());

        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to create customer: MalformedURLException");
        }

        resobj = new JSONObject(apiResponse);
        JSONObject accountRegister = resobj.optJSONObject("data").optJSONObject("accountRegister");
        if (accountRegister != null) {
            JSONArray errors = accountRegister.optJSONArray("errors");
            if (errors == null || errors.length() == 0) {
                // Registration successful
                String customerId = accountRegister.optJSONObject("user").optString("id");

                return new SuccessDataResult<>(customerId, "Customer created successfully");
            } else {
                String errorMessage = errors.getJSONObject(0).getString("message");
                return new ErrorDataResult<>("Failed to create customer: " + errorMessage);
            }
        } else {
            return new ErrorDataResult<>("Failed to create customer: No response from server");
        }
    }

    // To update a customer we will call accountUpdate Mutation only.
    public static DataResult<?> customerUpdateMutation(String customerId, String firstName, String lastName,
            String email, String authToken, HttpServletRequest request) {

        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String apiResponse;
        String url = checkDomainFromRequest(request);
        JSONObject resobj;

        String mutationQuery = "mutation { customerUpdate(id: \"" + customerId + "\", input: {firstName: \"" + firstName + "\", "
                + "lastName: \"" + lastName + "\", email: \"" + email + "\"}) { errors { message } user { email firstName lastName } } }";

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", mutationQuery);

        try {
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult("Failed to update customer: MalformedURLException");
        }

        resobj = new JSONObject(apiResponse);
        JSONArray errors = resobj.getJSONObject("data").getJSONObject("customerUpdate").getJSONArray("errors");
        if (errors.length() == 0) {
            // Update successful
            return new SuccessDataResult<>("Customer updated successfully");
        } else {
            String errorMessage = errors.getJSONObject(0).getString("message");
            return new ErrorDataResult("Failed to update customer: " + errorMessage);
        }
    }

    // customer list query 
    public static DataResult<List<CustomerAndSupplierDto>> customerListQueryWithPaginated(Integer pageNumber,
            String authToken, String after, String isAsc, List<HashMap<String, Object>> data1, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = checkDomainFromRequest(request);
        String apiResponse;

        // Construct the GraphQL query
        String query;
        if (isAsc.equals("next")) {
            if (data1.size() > 0) {
                query = "query { customers(first: %d, after: \"%s\", sortBy: {direction: ASC, field: FIRST_NAME}, filter: {metadata: [";
                for (int i = 0; i < data1.size(); i++) {
                    HashMap<String, Object> map = data1.get(i);
                    query = query + "{ key: \"" + map.get("key") + "\", value: \"" + map.get("value") + "\" },";
                }
                query = query.substring(0, query.length() - 1);
                query = query + "]}) { pageInfo { hasNextPage hasPreviousPage } edges { cursor node { id firstName lastName email userType mobileNo addresses { id streetAddress1 streetAddress2 countryArea postalCode city } } } } }";
                query = String.format(query, pageNumber, after);
            } else {
                query = String.format("query { customers(first: %d, after: \"%s\", sortBy: {direction: ASC, field: FIRST_NAME}) { pageInfo { hasNextPage hasPreviousPage } edges { cursor node { id firstName lastName email userType mobileNo addresses { id streetAddress1 streetAddress2 countryArea postalCode city } } } } }", pageNumber, after);
            }
        } else {
            if (data1.size() > 0) {
                query = "query { customers(last: %d, before: \"%s\", sortBy: {direction: ASC, field: FIRST_NAME}, filter: {metadata: [";
                for (int i = 0; i < data1.size(); i++) {
                    HashMap<String, Object> map = data1.get(i);
                    query = query + "{ key: \"" + map.get("key") + "\", value: \"" + map.get("value") + "\" },";
                }
                query = query.substring(0, query.length() - 1);
                query = query + "]}) { pageInfo { hasNextPage hasPreviousPage } edges { cursor node { id firstName lastName email userType mobileNo addresses { id streetAddress1 streetAddress2 countryArea postalCode city } } } } }";
                query = String.format(query, pageNumber, after);
            } else {
                query = String.format("query { customers(last: %d, before: \"%s\", sortBy: {direction: ASC, field: FIRST_NAME}) { pageInfo { hasNextPage hasPreviousPage } edges { cursor node { id firstName lastName email userType mobileNo addresses { id streetAddress1 streetAddress2 countryArea postalCode city } } } } }", pageNumber, after);
            }
        }

        JSONObject obje = new JSONObject();
        obje.put("query", query);
        try {
            // Execute the GraphQL query
            apiResponse = HTTPUtil.executeUrl(new URL(url), obje.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject resobj = new JSONObject(apiResponse);
            JSONObject data = resobj.optJSONObject("data");
            if (data != null) {
                JSONObject customers = data.optJSONObject("customers");
                if (customers != null) {
                    JSONArray edges = customers.optJSONArray("edges");
                    if (edges != null && edges.length() > 0) {
                        // Extraction of customers successful
                        List<CustomerAndSupplierDto> customerList = new ArrayList<>();
                        for (int i = 0; i < edges.length(); i++) {
                            JSONObject edge = edges.getJSONObject(i);
                            JSONObject node = edge.getJSONObject("node");
                            String cursor = edge.getString("cursor");
                            String customerId = node.optString("id");
                            String firstName = node.optString("firstName");
                            String lastName = node.optString("lastName");
                            String email = node.optString("email");
                            String mobileNo = node.optString("mobileNo");
                            String userType = node.optString("userType");

                            // Extract addresses
                            JSONArray addresses = node.optJSONArray("addresses");
                            List<AddressDto> addressList = new ArrayList<>();
                            if (addresses != null && addresses.length() > 0) {
                                for (int j = 0; j < addresses.length(); j++) {
                                    JSONObject addressObject = addresses.getJSONObject(j);
                                    String addressId = addressObject.optString("id");
                                    String streetAddress1 = addressObject.optString("streetAddress1");
                                    String streetAddress2 = addressObject.optString("streetAddress2");
                                    String countryArea = addressObject.optString("countryArea");
                                    String postalCode = addressObject.optString("postalCode");
                                    String city = addressObject.optString("city");

                                    // Create AddressDto object
                                    AddressDto address = new AddressDto(customerId, addressId, streetAddress1, streetAddress2, countryArea, postalCode, city);
                                    addressList.add(address);
                                }
                            }

                            // Create CustomerAndSupplierDto object with addresses
                            CustomerAndSupplierDto customer = new CustomerAndSupplierDto(customerId, firstName, lastName, email, mobileNo, addressList);
                            customer.setDealerType(userType);
                            customer.setCursor(cursor);
                            customerList.add(customer);
                        }

                        if (!customerList.isEmpty()) {
                            // Set pagination info
                            JSONObject pageInfo = customers.getJSONObject("pageInfo");
                            boolean hasNextPage = pageInfo.getBoolean("hasNextPage");
                            boolean hasPreviousPage = pageInfo.getBoolean("hasPreviousPage");

                            // Update each customer with pagination info
                            for (CustomerAndSupplierDto customer : customerList) {
                                customer.setHasNextPage(hasNextPage);
                                customer.setHasPreviousPage(hasPreviousPage);
                            }

                            return new SuccessDataResult<>(customerList, "Customer list fetched successfully");
                        } else {
                            return new ErrorDataResult<>("Failed to fetch customer list: No data returned");
                        }
                    }
                }
            }
            return new ErrorDataResult<>("Failed to fetch customer list: No data returned");
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch customer list: Malformed URL");
        } catch (JSONException jSONException) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "JSON parsing error occurred", jSONException);
            return new ErrorDataResult<>("Failed to fetch customer list: JSON parsing error occurred - " + jSONException.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch customer list: An unexpected error occurred - " + ex.getMessage());
        }
    }

    // address create Mutation
    public static DataResult<?> addressCreateMutation(String id, String streetAddress1, String streetAddress2,
            String city, String country, String countryArea, String postalCode, String authToken, HttpServletRequest request) {

        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String resData = "mutation { addressCreate(input: { streetAddress1: \"" + streetAddress1 + "\", "
                + "streetAddress2: \"" + streetAddress2 + "\", "
                + "city: \"" + city + "\", "
                + "country: " + country + ", "
                + "countryArea: \"" + countryArea + "\", "
                + "postalCode: \"" + postalCode + "\" }, "
                + "userId: \"" + id + "\") { "
                + "user { id }, "
                + "errors { field message }, "
                + "address { id } } }";

        JSONObject obje = new JSONObject();
        obje.put("query", resData);

        String url = checkDomainFromRequest(request);
        String apiResponse = "";
        try {
            apiResponse = HTTPUtil.executeUrl(new URL(url), obje.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to create address: MalformedURLException");
        }

        JSONObject resobj = new JSONObject(apiResponse);
        JSONObject addressCreateResult = resobj.getJSONObject("data").getJSONObject("addressCreate");

        // Check for errors
        JSONArray errors = addressCreateResult.getJSONArray("errors");
        if (errors.length() > 0) {
            String errorMessage = errors.getJSONObject(0).getString("message");
            return new ErrorDataResult<>("Failed to create address: " + errorMessage);
        } else {
            // Address creation successful
            String addressId = addressCreateResult.getJSONObject("address").getString("id");
            return new SuccessDataResult<>(addressId, "Address created successfully");
        }
    }

    // address update mutation
    public static DataResult<?> addressUpdateMutation(String addressId, String streetAddress1, String streetAddress2,
            String city, String country, String countryArea, String postalCode, String authToken, HttpServletRequest request) {

        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String mutationQuery = "mutation { addressUpdate(id: \"" + addressId + "\", input: {"
                + "streetAddress1: \"" + streetAddress1 + "\", "
                + "streetAddress2: \"" + streetAddress2 + "\", "
                + "city: \"" + city + "\", "
                + "country: " + country + ", "
                + "countryArea: \"" + countryArea + "\", "
                + "postalCode: \"" + postalCode + "\" }) { "
                + "user { id }, "
                + "errors { field message }, "
                + "address { id } } }";

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", mutationQuery);

        String url = checkDomainFromRequest(request);
        String apiResponse = "";
        try {
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to update address: MalformedURLException");
        }

        JSONObject responseObject = new JSONObject(apiResponse);
        JSONObject addressUpdateResult = responseObject.getJSONObject("data").getJSONObject("addressUpdate");

        // Check for errors
        JSONArray errors = addressUpdateResult.getJSONArray("errors");
        if (errors.length() > 0) {
            String errorMessage = errors.getJSONObject(0).getString("message");
            return new ErrorDataResult<>("Failed to update address: " + errorMessage);
        } else {
            // Address update successful
            String updatedAddressId = addressUpdateResult.getJSONObject("address").getString("id");
            return new SuccessDataResult<>(updatedAddressId, "Address updated successfully");
        }
    }

    public static DataResult<CustomerAndSupplierDto> getUserById(String userId, String authToken, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = checkDomainFromRequest(request);
        String apiResponse = ""; // Assuming this will be filled with the actual API response

        // Construct the GraphQL query to fetch a user by ID
        String query = "query { user(id: \"" + userId + "\") { id firstName lastName email mobileNo } }";

        JSONObject obje = new JSONObject();
        obje.put("query", query);

        try {
            // Execute the GraphQL query
            apiResponse = HTTPUtil.executeUrl(new URL(url), obje.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject resobj = new JSONObject(apiResponse);
            JSONObject data = resobj.optJSONObject("data");
            if (data != null) {
                JSONObject user = data.optJSONObject("user");
                if (user != null) {
                    // Extraction of user successful
                    String customerId = user.optString("id");
                    String firstName = user.optString("firstName");
                    String lastName = user.optString("lastName");
                    String email = user.optString("email");
                    String mobileNo = user.optString("mobileNo");

                    // Create CustomerDto object
                    CustomerAndSupplierDto customer = new CustomerAndSupplierDto(customerId, firstName, lastName, email, mobileNo);
                    return new SuccessDataResult<>(customer, "Customer fetched successfully");
                }
            }
            // If data extraction fails, return appropriate error message
            return new ErrorDataResult<>("Failed to fetch customer: No data returned or data format is incorrect");
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "Malformed URL", ex);
            return new ErrorDataResult<>("Failed to fetch customer: Malformed URL - " + ex.getMessage());
        } catch (JSONException jSONException) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "JSON parsing error occurred", jSONException);
            return new ErrorDataResult<>("Failed to fetch customer: JSON parsing error occurred - " + jSONException.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "Unexpected error occurred", ex);
            return new ErrorDataResult<>("Failed to fetch customer: An unexpected error occurred - " + ex.getMessage());
        }
    }

    // get id by address
    public static DataResult<CustomerAndSupplierDto> getAddressById(String addressId, String authToken) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = "";
        String apiResponse = ""; // Assuming this will be filled with the actual API response

        // Construct the GraphQL query to fetch an address by ID
        String query = "query { address(id: \"" + addressId + "\") { id streetAddress1 streetAddress2 city countryArea cityArea postalCode  } }";

        JSONObject obje = new JSONObject();
        obje.put("query", query);

        try {
            // Execute the GraphQL query
            apiResponse = HTTPUtil.executeUrl(new URL(url), obje.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject resobj = new JSONObject(apiResponse);
            JSONObject data = resobj.optJSONObject("data");
            if (data != null) {
                JSONObject address = data.optJSONObject("address");
                if (address != null) {
                    // Extraction of address successful
                    String addressIdResult = address.optString("id");
                    String countryArea = address.optString("countryArea");
                    String streetAddress2 = address.optString("streetAddress2");
                    String streetAddress1 = address.optString("streetAddress1");
                    String city = address.optString("city");
                    String postalCode = address.optString("postalCode");

                    // Create AddressDto object
                    CustomerAndSupplierDto customerDto = new CustomerAndSupplierDto(addressIdResult, streetAddress1, streetAddress2, city, countryArea, postalCode);
                    return new SuccessDataResult<>(customerDto, "Address fetched successfully");
                }
            }
            // If data extraction fails, return appropriate error message
            return new ErrorDataResult<>("Failed to fetch address: No data returned or data format is incorrect");
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "Malformed URL", ex);
            return new ErrorDataResult<>("Failed to fetch address: Malformed URL - " + ex.getMessage());
        } catch (JSONException jSONException) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "JSON parsing error occurred", jSONException);
            return new ErrorDataResult<>("Failed to fetch address: JSON parsing error occurred - " + jSONException.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "Unexpected error occurred", ex);
            return new ErrorDataResult<>("Failed to fetch address: An unexpected error occurred - " + ex.getMessage());
        }
    }

    // Product Create mutation
    public static DataResult<String> productCreateMutation(ProductDto productDto, Integer gst, String snoPattern,
            String authToken, HttpServletRequest request) throws JsonProcessingException {

        String ITEM_DESCRIPTION = Constant.ITEM_DESCRIPTION;
        System.out.println("ITEM_DESCRIPTION" + ITEM_DESCRIPTION);
        ITEM_DESCRIPTION = ITEM_DESCRIPTION.replace("\"", "\\\"");
        String productDescription = productDto.getProductDescription();

        String modifiedDescription = ITEM_DESCRIPTION.replace("<description>", productDescription);

        // Extracting fields from ProductDto
        String productName = productDto.getProductName();

        String category = productDto.getCategory().getCategoryId();
        String productType = productDto.getProductType().getProductTypeId();
        boolean trackingSerialNo = productDto.isTrackingSerialNo();
        boolean batchSerialNo = productDto.isBatchSerialNo();
        boolean isProductService = productDto.isIsProductService();

        // Set up HTTP request headers
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken));

        // Constructing the GraphQL mutation
        String mutation = "mutation { productCreate(input: { "
                + "name: \"" + productName + "\", "
                + "description: \"" + modifiedDescription + "\", "
                + "trackingSerialNo: " + trackingSerialNo + ", "
                + "isProductService: " + isProductService + ", "
                + "isBatch: " + batchSerialNo + ", "
                + "category: \"" + category + "\", "
                + "productType: \"" + productType + "\", "
                + "gst:  " + gst + " , "
                + "snoPattern: \"" + snoPattern + "\", "
                + "attributes: [] }) { "
                + "product { id name productType { id name } } "
                + "errors { field message } } }";

        JSONObject obje = new JSONObject();
        obje.put("query", mutation);

        // Execute the GraphQL mutation
        String url = checkDomainFromRequest(request);
        String apiResponse = "";
        try {
            apiResponse = HTTPUtil.executeUrl(new URL(url), obje.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to create product: MalformedURLException");
        }

        // Parse the API response
        JSONObject responseObj = new JSONObject(apiResponse);
        JSONObject productCreateResult = responseObj.getJSONObject("data").getJSONObject("productCreate");

        // Check for errors
        JSONArray errors = productCreateResult.getJSONArray("errors");
        if (errors.length() > 0) {
            String errorMessage = errors.getJSONObject(0).getString("message");
            return new ErrorDataResult<>("Failed to create product: " + errorMessage);
        } else {
            // Product creation successful
            JSONObject product = productCreateResult.getJSONObject("product");
            String productId = product.getString("id");

            // You can handle the successful creation here as per your requirement
            return new SuccessDataResult<>(productId, "Product created successfully");
        }
    }

    //productChannelListingUpdateMutation
    public static DataResult<?> productChannelListingUpdateMutation(ProductDto productDto, String authToken,
            String productId, String[] hiddenInputContainerChannelIds, HttpServletRequest request) {

        // Iterate over each entry in hiddenInputContainerChannelIds
        for (String hiddenInputContainerChannelIdsData : hiddenInputContainerChannelIds) {
            String[] hiddenInput = hiddenInputContainerChannelIdsData.split("\\|");
            if (hiddenInput.length < 2) {
                return new ErrorDataResult<>("Invalid data format in hiddenInputContainerChannelIds.");
            }

            String channelId = hiddenInput[0];

            // Set up HTTP request headers
            ArrayList<BasicNameValuePair> headers = new ArrayList<>();
            headers.add(new BasicNameValuePair("Content-Type", "application/json"));
            headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken));

            // Construct the GraphQL mutation
            String mutation = "mutation { productChannelListingUpdate("
                    + "id: \"" + productId + "\", "
                    + "input: { "
                    + "updateChannels: [{ "
                    + "channelId: \"" + channelId + "\", }] "
                    + "}) { "
                    + "product { id } "
                    + "errors { field message code } "
                    + "} }";

            JSONObject requestPayload = new JSONObject();
            requestPayload.put("query", mutation);

            // Execute the GraphQL mutation
            String url = checkDomainFromRequest(request);
            String apiResponse = "";
            try {
                apiResponse = HTTPUtil.executeUrl(new URL(url), requestPayload.toString(), headers, Constant.HTTP_REQUEST.POST.name());
            } catch (MalformedURLException ex) {
                Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
                return new ErrorDataResult<>("Failed to update product channel listing: Invalid URL format.");
            } catch (IOException ex) {
                Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
                return new ErrorDataResult<>("Failed to update product channel listing: Communication error.");
            }

            // Parse the API response
            JSONObject responseObj = new JSONObject(apiResponse);
            if (!responseObj.has("data")) {
                return new ErrorDataResult<>("Failed to update product channel listing: No data returned from API.");
            }

            JSONObject channelUpdateResult = responseObj.getJSONObject("data").getJSONObject("productChannelListingUpdate");

            // Check for errors
            JSONArray errors = channelUpdateResult.getJSONArray("errors");
            if (errors.length() > 0) {
                StringBuilder errorMessages = new StringBuilder("Failed to update product channel listing: ");
                for (int i = 0; i < errors.length(); i++) {
                    JSONObject error = errors.getJSONObject(i);
                    errorMessages.append(error.getString("message"));
                    if (i < errors.length() - 1) {
                        errorMessages.append(", ");
                    }
                }
                return new ErrorDataResult<>(errorMessages.toString());
            } else {
                // Channel listing update successful
                JSONObject product = channelUpdateResult.getJSONObject("product");
                String productIdResult = product.getString("id");

            }
        }
        return new SuccessDataResult<>("All product channel listings processed successfully.");
    }

    // productVariantCreateMutation
    public static DataResult<String> productVariantCreateMutation(String sku, String productId, String authToken, HttpServletRequest request) {

        // Set up HTTP request headers
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken));

        // Constructing the GraphQL mutation
        String mutation = "mutation { productVariantCreate(input: { "
                + "product: \"" + productId + "\", "
                + "sku: \"" + sku + "\", "
                + "attributes: [], "
                + " }) { "
                + "productVariant { id name sku } "
                + "errors { field message code } } }";

        JSONObject obje = new JSONObject();
        obje.put("query", mutation);

        // Execute the GraphQL mutation
        String url = checkDomainFromRequest(request);
        String apiResponse = "";
        try {
            apiResponse = HTTPUtil.executeUrl(new URL(url), obje.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to create product variant: MalformedURLException");
        }

        // Parse the API response
        JSONObject responseObj = new JSONObject(apiResponse);
        JSONObject variantCreateResult = responseObj.getJSONObject("data").getJSONObject("productVariantCreate");

        // Check for errors
        JSONArray errors = variantCreateResult.getJSONArray("errors");
        if (errors.length() > 0) {
            String errorMessage = errors.getJSONObject(0).getString("message");
            return new ErrorDataResult<>("Failed to create product variant: " + errorMessage);
        } else {
            // Variant creation successful
            JSONObject variant = variantCreateResult.getJSONObject("productVariant");
            String variantId = variant.getString("id");

            // You can handle the successful creation here as per your requirement
            return new SuccessDataResult<>(variantId, "Product variant created successfully");
        }
    }

    // productVariantChannelListingUpdate mutation
    public static DataResult<?> productVariantChannelListingUpdateMutation(double price, double costPrice,
            String productVariantId, String[] hiddenInputContainerChannelIds, String authToken, HttpServletRequest request) {

        // Process each row in hiddenInputContainerChannelIds
        for (String hiddenInputContainerChannelIdsData : hiddenInputContainerChannelIds) {
            String[] hiddenInput = hiddenInputContainerChannelIdsData.split("\\|");
            if (hiddenInput.length < 2) {
                return new ErrorDataResult<>("Invalid data format in hiddenInputContainerChannelIds.");
            }

            String channelId = hiddenInput[0];

            // Set up HTTP request headers
            ArrayList<BasicNameValuePair> headers = new ArrayList<>();
            headers.add(new BasicNameValuePair("Content-Type", "application/json"));
            headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken));

            // Constructing the GraphQL mutation
            String mutation = "mutation { productVariantChannelListingUpdate("
                    + "id: \"" + productVariantId + "\", "
                    + "input: { "
                    + "channelId: \"" + channelId + "\", "
                    + "price: \"" + price + "\", "
                    + "costPrice: \"" + costPrice + "\" "
                    + "}) { "
                    + "variant { id } errors{ field message code } } }";

            JSONObject obje = new JSONObject();
            obje.put("query", mutation);

            // Execute the GraphQL mutation
            String url = checkDomainFromRequest(request);
            String apiResponse = "";
            try {
                apiResponse = HTTPUtil.executeUrl(new URL(url), obje.toString(), headers, Constant.HTTP_REQUEST.POST.name());
            } catch (MalformedURLException ex) {
                Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
                return new ErrorDataResult<>("Failed to update product variant channel listing: MalformedURLException");
            } catch (IOException ex) {
                Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
                return new ErrorDataResult<>("Failed to update product variant channel listing: IOException");
            }

            // Parse the API response
            JSONObject responseObj = new JSONObject(apiResponse);
            if (!responseObj.has("data") || !responseObj.getJSONObject("data").has("productVariantChannelListingUpdate")) {
                return new ErrorDataResult<>("Invalid response from the server.");
            }
            JSONObject updateVariantResult = responseObj.getJSONObject("data").getJSONObject("productVariantChannelListingUpdate");

            // Check for errors
            JSONArray errors = updateVariantResult.getJSONArray("errors");
            if (errors.length() > 0) {
                // Extract and log specific errors
                StringBuilder errorMessage = new StringBuilder("Failed to update product variant channel listing: ");
                for (int i = 0; i < errors.length(); i++) {
                    JSONObject error = errors.getJSONObject(i);
                    errorMessage.append("Field: ").append(error.getString("field"))
                            .append(", Message: ").append(error.getString("message"))
                            .append(", Code: ").append(error.getString("code")).append("; ");
                }
                Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, errorMessage.toString());
                return new ErrorDataResult<>(errorMessage.toString());
            }
        }

        // If all updates are successful
        return new SuccessDataResult<>("Product variant channel listing updated successfully for all channels.");
    }

// product list query
    public static DataResult<List<ProductDto>> productListQuery(Integer pageNumber, String authToken, String after,
            String isAsc, String brand, String productType, String productName, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = checkDomainFromRequest(request);
        String apiResponse;

        // Construct the GraphQL query
        String query;

        // Convert brand and productType to arrays if not null
        String brandFilter = brand != null && !brand.isEmpty() ? "\"" + brand + "\"" : null;
        String productTypeFilter = productType != null && !productType.isEmpty() ? "\"" + productType + "\"" : null;
        String productNameFilter = productName != null && !productName.isEmpty() ? productName : null;

        // Construct the filter part of the query
        String filterPart = "filter: {";

        if (productNameFilter != null) {
            filterPart += "search: \"" + productNameFilter + "\"";
        }

        if (brandFilter != null) {
            if (!filterPart.endsWith("{")) {
                filterPart += ", ";
            }
            filterPart += "categories: [" + brandFilter + "]";
        } else {
            if (!filterPart.endsWith("{")) {
                filterPart += ", ";
            }
            filterPart += "categories: []";
        }

        if (productTypeFilter != null) {
            if (!filterPart.endsWith("{")) {
                filterPart += ", ";
            }
            filterPart += "productTypes: [" + productTypeFilter + "]";
        } else {
            if (!filterPart.endsWith("{")) {
                filterPart += ", ";
            }
            filterPart += "productTypes: []";
        }

        filterPart += "}";

        // Construct the GraphQL query
        if (isAsc.equals("next")) {
            query = "query {\n"
                    + "  products(first: " + pageNumber + ", after: \"" + after + "\", sortBy: {direction: ASC, field: NAME}, " + filterPart + ") {\n"
                    + "    pageInfo {hasNextPage hasPreviousPage} edges {\n"
                    + "      cursor node { id name seoDescription gst snoPattern isProductService trackingSerialNo seoTitle description slug created updatedAt rating channel isAvailable\n"
                    + "        category { id name } variants { sku }\n"
                    + "        productType { id name }\n"
                    + "        channelListings { id pricing { priceRangeUndiscounted { start { gross { amount } } }}purchaseCost { start { amount  } } }  metadata { key value } } } } }";
        } else {
            query = "query {\n"
                    + "  products(last: " + pageNumber + ", before: \"" + after + "\", sortBy: {direction: ASC, field: NAME}, " + filterPart + ") {\n"
                    + "    pageInfo {hasNextPage hasPreviousPage} edges {\n"
                    + "      cursor node { id name seoDescription gst snoPattern isProductService trackingSerialNo seoTitle description slug created updatedAt rating channel isAvailable\n"
                    + "        category { id name } variants { sku }\n"
                    + "        productType { id name }\n"
                    + "        channelListings { id pricing { priceRangeUndiscounted { start { gross { amount } } }}purchaseCost { start { amount  } } }  metadata { key value } } } } }";
        }

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", query);

        try {
            // Execute the GraphQL query
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject responseObject = new JSONObject(apiResponse);
            JSONObject data = responseObject.optJSONObject("data");

            if (data != null) {
                JSONObject products = data.optJSONObject("products");
                if (products != null) {
                    JSONArray edges = products.optJSONArray("edges");
                    if (edges != null && edges.length() > 0) {
                        // Extraction of products successful
                        List<ProductDto> productList = new ArrayList<>();
                        for (int i = 0; i < edges.length(); i++) {
                            JSONObject node = edges.getJSONObject(i).getJSONObject("node");
                            String cursor = edges.getJSONObject(i).getString("cursor");
                            String productId = node.getString("id");
                            String name = node.getString("name");
                            String description = node.optString("description");
                            String gst = node.optString("gst");
                            String snoPattern = node.optString("snoPattern");
                            String productSku;
                            try {
                                productSku = node.getJSONArray("variants").getJSONObject(0).optString("sku");
                            } catch (JSONException e) {
                                // Handle the exception, e.g., by setting a default value for productSku
                                productSku = "Zero Variants";
                            }

                            boolean trackingSerialNo = node.getBoolean("trackingSerialNo");
                            boolean isProductService = node.getBoolean("isProductService");
                            // Additional parsing for category, product type, and channel listings
                            JSONObject categoryJson = node.optJSONObject("category");
                            String categoryId = categoryJson != null ? categoryJson.getString("id") : null;
                            String categoryName = categoryJson != null ? categoryJson.getString("name") : null;

                            JSONObject productTypeJson = node.optJSONObject("productType");
                            String productTypeId = productTypeJson != null ? productTypeJson.getString("id") : null;
                            String productTypeName = productTypeJson != null ? productTypeJson.getString("name") : null;

                            JSONArray channelListingsJson = node.optJSONArray("channelListings");
                            float grossamount = 0;
                            if (channelListingsJson != null) {
                                for (int j = 0; j < channelListingsJson.length(); j++) {
                                    JSONObject channelListing = channelListingsJson.getJSONObject(j);
                                    JSONObject pricing = channelListing.optJSONObject("pricing");
                                    if (pricing != null) {
                                        JSONObject priceRangeUndiscounted = pricing.optJSONObject("priceRangeUndiscounted");
                                        if (priceRangeUndiscounted != null) {
                                            JSONObject start = priceRangeUndiscounted.optJSONObject("start");
                                            if (start != null) {
                                                JSONObject gross = start.optJSONObject("gross");
                                                if (gross != null) {
                                                    grossamount = (float) gross.getDouble("amount");

                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            // Parse purchase cost
                            float purchaseAmount = 0;
                            if (channelListingsJson != null) {
                                for (int j = 0; j < channelListingsJson.length(); j++) {
                                    JSONObject channelListing = channelListingsJson.getJSONObject(j);
                                    JSONObject purchaseCost = channelListing.optJSONObject("purchaseCost");
                                    if (purchaseCost != null) {
                                        JSONObject start = purchaseCost.optJSONObject("start");
                                        if (start != null) {
                                            purchaseAmount = (float) start.getDouble("amount");
                                            break; // Assuming purchase cost is the same for all channel listings
                                        }
                                    }
                                }
                            }

                            // Create ProductDto object
                            ProductDto product = new ProductDto();
                            product.setCursor(cursor);
                            product.setProductId(productId);
                            product.setProductName(name);
                            product.setProductDescription(description);
                            product.setPrice(grossamount);
                            product.setCostPrice(purchaseAmount);
                            product.setGst(gst);
                            product.setSnoPattern(snoPattern);
                            product.setCategory(categoryId, categoryName);
                            product.setProductType(productTypeId, productTypeName);
                            product.setTrackingSerialNo(trackingSerialNo);
                            product.setIsProductService(isProductService);
                            product.setProductSku(productSku);
                            product.setHasNextPage(products.getJSONObject("pageInfo").getBoolean("hasNextPage"));
                            product.setHasPreviousPage(products.getJSONObject("pageInfo").getBoolean("hasPreviousPage"));

                            // Parse metadata
                            Map<String, Object> metadata = new HashMap<>();
                            JSONArray metadataArray = node.optJSONArray("metadata");
                            if (metadataArray != null) {
                                for (int k = 0; k < metadataArray.length(); k++) {
                                    JSONObject metadataObj = metadataArray.getJSONObject(k);
                                    String key = metadataObj.getString("key");
                                    String value = metadataObj.getString("value");
                                    metadata.put(key, value);
                                }
                            }
                            product.setMetadata(metadata);

                            productList.add(product);
                        }
                        return new SuccessDataResult<>(productList, "Product list fetched successfully");
                    }
                }
            }
            return new ErrorDataResult<>("Failed to fetch product list: No products found");
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch product list: An unexpected error occurred");
        }
    }

    public static DataResult<List<ProductDto>> getProductByProductName(String productName, String authToken,
            Integer pageNumber, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = checkDomainFromRequest(request);
        String apiResponse;

        // Construct the GraphQL query
        String query = "query {\n"
                + "  products(first: " + pageNumber + ", filter: { search: \"" + productName + "\"}) {\n"
                + "    edges {\n"
                + "      node { id name seoDescription gst snoPattern isProductService trackingSerialNo isBatch seoTitle description slug created updatedAt rating channel isAvailable\n"
                + "        category { id name } variants { sku }\n"
                + "        productType { id name }\n"
                + "        channelListings { id pricing { priceRangeUndiscounted { start { gross { amount } } }}purchaseCost { start { amount  } } }  metadata { key value } } } } }";
        JSONObject requestBody = new JSONObject();
        requestBody.put("query", query);

        try {
            // Execute the GraphQL query
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject responseObject = new JSONObject(apiResponse);
            JSONObject data = responseObject.optJSONObject("data");

            if (data != null) {
                JSONObject products = data.optJSONObject("products");
                if (products != null) {
                    JSONArray edges = products.optJSONArray("edges");
                    if (edges != null && edges.length() > 0) {
                        // Extraction of products successful
                        List<ProductDto> productList = new ArrayList<>();
                        for (int i = 0; i < edges.length(); i++) {
                            JSONObject node = edges.getJSONObject(i).getJSONObject("node");
                            String productId = node.getString("id");
                            String name = node.getString("name");
                            String description = node.optString("description");
                            String gst = node.optString("gst");
                            String snoPattern = node.optString("snoPattern");
                            String productSku = node.getJSONArray("variants").getJSONObject(0).getString("sku");
                            boolean trackingSerialNo = node.getBoolean("trackingSerialNo");
                            boolean batchSerialNo = node.getBoolean("isBatch");
                            boolean isProductService = node.getBoolean("isProductService");

                            // Additional parsing for category, product type, and channel listings
                            JSONObject categoryJson = node.optJSONObject("category");
                            String categoryId = categoryJson != null ? categoryJson.getString("id") : null;
                            String categoryName = categoryJson != null ? categoryJson.getString("name") : null;

                            JSONObject productTypeJson = node.optJSONObject("productType");
                            String productTypeId = productTypeJson != null ? productTypeJson.getString("id") : null;
                            String productTypeName = productTypeJson != null ? productTypeJson.getString("name") : null;

                            JSONArray channelListingsJson = node.optJSONArray("channelListings");
                            float grossamount = 0;
                            if (channelListingsJson != null) {
                                for (int j = 0; j < channelListingsJson.length(); j++) {
                                    JSONObject channelListing = channelListingsJson.getJSONObject(j);
                                    JSONObject pricing = channelListing.optJSONObject("pricing");
                                    if (pricing != null) {
                                        JSONObject priceRangeUndiscounted = pricing.optJSONObject("priceRangeUndiscounted");
                                        if (priceRangeUndiscounted != null) {
                                            JSONObject start = priceRangeUndiscounted.optJSONObject("start");
                                            if (start != null) {
                                                JSONObject gross = start.optJSONObject("gross");
                                                if (gross != null) {
                                                    grossamount = (float) gross.getDouble("amount");

                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            // Parse purchase cost
                            float purchaseAmount = 0;
                            if (channelListingsJson != null) {
                                for (int j = 0; j < channelListingsJson.length(); j++) {
                                    JSONObject channelListing = channelListingsJson.getJSONObject(j);
                                    JSONObject purchaseCost = channelListing.optJSONObject("purchaseCost");
                                    if (purchaseCost != null) {
                                        JSONObject start = purchaseCost.optJSONObject("start");
                                        if (start != null) {
                                            purchaseAmount = (float) start.getDouble("amount");
                                            break; // Assuming purchase cost is the same for all channel listings
                                        }
                                    }
                                }
                            }

                            // Create ProductDto object
                            ProductDto product = new ProductDto();
                            product.setProductId(productId);
                            product.setProductName(name);
                            product.setProductDescription(description);
                            product.setPrice(grossamount);
                            product.setCostPrice(purchaseAmount);
                            product.setGst(gst);
                            product.setSnoPattern(snoPattern);
                            product.setCategory(categoryId, categoryName);
                            product.setProductType(productTypeId, productTypeName);
                            product.setTrackingSerialNo(trackingSerialNo);
                            product.setBatchSerialNo(batchSerialNo);
                            product.setProductSku(productSku);
                            product.setIsProductService(isProductService);

                            // Parse metadata
                            Map<String, Object> metadata = new HashMap<>();
                            JSONArray metadataArray = node.optJSONArray("metadata");
                            if (metadataArray != null) {
                                for (int k = 0; k < metadataArray.length(); k++) {
                                    JSONObject metadataObj = metadataArray.getJSONObject(k);
                                    String key = metadataObj.getString("key");
                                    String value = metadataObj.getString("value");
                                    metadata.put(key, value);
                                }
                            }
                            product.setMetadata(metadata);

                            productList.add(product);
                        }
                        return new SuccessDataResult<>(productList, "Product list fetched successfully");
                    }
                }
            }
            return new ErrorDataResult<>("Failed to fetch product list: No products found");
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch product list: An unexpected error occurred");
        }
    }

    // product update mutation
    public static DataResult<String> productUpdateMutation(ProductDto productDto, Integer gst, String snoPattern,
            String authToken, HttpServletRequest request) {

        // Extracting fields from ProductDto
        String productId = productDto.getProductId();

        String productDescription = productDto.getProductDescription();

        String ITEM_DESCRIPTION = Constant.ITEM_DESCRIPTION;

        ITEM_DESCRIPTION = ITEM_DESCRIPTION.replace("\"", "\\\"");

        String modifiedDescription = ITEM_DESCRIPTION.replace("<description>", productDescription);

        String productName = productDto.getProductName();
        String category = productDto.getCategory().getCategoryId();
        boolean trackingSerialNo = productDto.isTrackingSerialNo();
        boolean batchSerialNo = productDto.isBatchSerialNo();
        boolean isProductService = productDto.isIsProductService();
        double weight = productDto.getWeight();
        int rating = productDto.getRating();

        // Set up HTTP request headers
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken));

        // Constructing the GraphQL mutation for product update
        String mutation = "mutation { productUpdate(id: \"" + productId + "\", input: { "
                + "name: \"" + productName + "\", "
                + "description: \"" + modifiedDescription + "\", "
                + "trackingSerialNo: " + trackingSerialNo + ", "
                + "isProductService: " + isProductService + ", "
                + "isBatch: " + batchSerialNo + ", "
                + "category: \"" + category + "\", "
                + "weight: \"" + weight + "\", "
                + "rating: " + rating + ", "
                + "gst: " + gst + ", "
                + "snoPattern: \"" + snoPattern + "\", "
                + "attributes: [] }) { "
                + "product { id name productType { id name } } "
                + "errors { field message } } }";

        JSONObject obje = new JSONObject();
        obje.put("query", mutation);

        // Execute the GraphQL mutation
        String url = checkDomainFromRequest(request);
        String apiResponse = "";
        try {
            apiResponse = HTTPUtil.executeUrl(new URL(url), obje.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to update product: MalformedURLException");
        }

        // Parse the API response
        JSONObject responseObj = new JSONObject(apiResponse);
        JSONObject productUpdateResult = responseObj.getJSONObject("data").getJSONObject("productUpdate");

        // Check for errors
        JSONArray errors = productUpdateResult.getJSONArray("errors");
        if (errors.length() > 0) {
            String errorMessage = errors.getJSONObject(0).getString("message");
            return new ErrorDataResult<>("Failed to update product: " + errorMessage);
        } else {
            // Product update successful
            JSONObject product = productUpdateResult.getJSONObject("product");
            String updatedProductId = product.getString("id");
            // You can handle the successful update here as per your requirement
            return new SuccessDataResult<>(updatedProductId, "Product updated successfully");
        }
    }

    public static DataResult<ProductDto> getProductById(String productId, String authToken, HttpServletRequest request) {

        // Set up HTTP request headers
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = checkDomainFromRequest(request);
        String apiResponse = "";

        // Construct the GraphQL query
        String graphqlQuery = "{ product(id: \"" + productId + "\") { id name  seoDescription seoTitle gst snoPattern trackingSerialNo isProductService isBatch description slug created updatedAt rating channel isAvailable category { id name } productType { id name } variants { id name sku } metadata { key value } channelListings { id pricing { priceRangeUndiscounted { start { gross { amount } } } } purchaseCost { start { amount } } } } }";

        JSONObject queryObj = new JSONObject();
        queryObj.put("query", graphqlQuery);

        try {
            // Execute the GraphQL query
            apiResponse = HTTPUtil.executeUrl(new URL(url), queryObj.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject resobj = new JSONObject(apiResponse);
            JSONObject data = resobj.optJSONObject("data");
            if (data != null) {
                JSONObject product = data.optJSONObject("product");
                if (product != null) {
                    // Extract other fields
                    String productIdResult = product.getString("id");
                    String name = product.getString("name");
                    String description = product.optString("description");
                    String gst = product.optString("gst");
                    String snoPattern = product.optString("snoPattern");
                    boolean trackingSerialNo = product.getBoolean("trackingSerialNo");
                    boolean isProductService = product.getBoolean("isProductService");
                    boolean batchSerialNo = product.getBoolean("isBatch");
                    String slug = product.optString("slug");

                    // Parse category and productType
                    JSONObject categoryJson = product.optJSONObject("category");
                    String categoryId = categoryJson != null ? categoryJson.getString("id") : null;
                    String categoryName = categoryJson != null ? categoryJson.getString("name") : null;

                    JSONObject productTypeJson = product.optJSONObject("productType");
                    String productTypeId = productTypeJson != null ? productTypeJson.getString("id") : null;
                    String productTypeName = productTypeJson != null ? productTypeJson.getString("name") : null;

                    // Parse variants
                    JSONArray variantsArray = product.getJSONArray("variants");
                    List<ProductVariant> variants = new ArrayList<>();
                    for (int i = 0; i < variantsArray.length(); i++) {
                        JSONObject variantObj = variantsArray.getJSONObject(i);
                        String variantId = variantObj.optString("id");
                        String variantName = variantObj.optString("name");
                        String variantSKU = variantObj.optString("sku");

                        // Create a VariantDto object with the extracted fields
                        ProductVariant variantDto = new ProductVariant(variantId, variantName, variantSKU);
                        variants.add(variantDto);
                    }

                    // Parse metadata
                    JSONArray metadataArray = product.optJSONArray("metadata");
                    Map<String, Object> metadata = new HashMap<>();
                    if (metadataArray != null) {
                        for (int i = 0; i < metadataArray.length(); i++) {
                            JSONObject metadataObj = metadataArray.getJSONObject(i);
                            String key = metadataObj.getString("key");
                            Object value = metadataObj.get("value");
                            metadata.put(key, value);
                        }
                    }

                    // Parse channel listings and pricing
                    JSONArray channelListingsJson = product.optJSONArray("channelListings");
                    float grossAmount = 0;
                    if (channelListingsJson != null) {
                        for (int j = 0; j < channelListingsJson.length(); j++) {
                            JSONObject channelListing = channelListingsJson.getJSONObject(j);
                            JSONObject pricing = channelListing.optJSONObject("pricing");
                            if (pricing != null) {
                                JSONObject priceRangeUndiscounted = pricing.optJSONObject("priceRangeUndiscounted");
                                if (priceRangeUndiscounted != null) {
                                    JSONObject start = priceRangeUndiscounted.optJSONObject("start");
                                    if (start != null) {
                                        JSONObject gross = start.optJSONObject("gross");
                                        if (gross != null) {
                                            grossAmount = (float) gross.getDouble("amount");

                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Parse purchase cost
                    float purchaseAmount = 0;
                    if (channelListingsJson != null) {
                        for (int j = 0; j < channelListingsJson.length(); j++) {
                            JSONObject channelListing = channelListingsJson.getJSONObject(j);
                            JSONObject purchaseCost = channelListing.optJSONObject("purchaseCost");
                            if (purchaseCost != null) {
                                JSONObject start = purchaseCost.optJSONObject("start");
                                if (start != null) {
                                    purchaseAmount = (float) start.getDouble("amount");
                                    break; // Assuming purchase cost is the same for all channel listings
                                }
                            }
                        }
                    }

                    // Create ProductDto object
                    ProductDto productDto = new ProductDto(productIdResult, name, description, grossAmount, purchaseAmount, gst, snoPattern);
                    productDto.setCategory(categoryId, categoryName);
                    productDto.setProductType(productTypeId, productTypeName);
                    productDto.setMetadata(metadata);
                    productDto.setProductVariant(variants);
                    productDto.setSlug(slug);
                    productDto.setTrackingSerialNo(trackingSerialNo);
                    productDto.setBatchSerialNo(batchSerialNo);
                    productDto.setIsProductService(isProductService);

                    // Return success result with ProductDto
                    return new SuccessDataResult<>(productDto, "Product retrieved successfully");
                } else {
                    // Product not found
                    return new ErrorDataResult<>("Product not found with ID: " + productId);
                }
            } else {
                // No data returned
                return new ErrorDataResult<>("Failed to fetch products: No data returned");
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch products: Malformed URL");
        } catch (JSONException jSONException) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "JSON parsing error occurred", jSONException);
            return new ErrorDataResult<>("Failed to fetch products: JSON parsing error occurred - " + jSONException.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch products: An unexpected error occurred - " + ex.getMessage());
        }
    }

    // get staff By Id
    public static DataResult<StaffDto> getStaffById(String staffId, String authToken, HttpServletRequest request) {

        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = checkDomainFromRequest(request);
        String apiResponse = "";

        // Construct the GraphQL query
        String query = "query { staffUsers(filter: { ids: \"" + staffId + "\" }, first: 1) { edges { node { id firstName lastName email mobileNo permissionGroups { id name } } } } }";
        JSONObject requestBody = new JSONObject();
        requestBody.put("query", query);

        try {
            // Execute the GraphQL query
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject responseObject = new JSONObject(apiResponse);
            JSONObject data = responseObject.optJSONObject("data");

            if (data != null) {
                JSONObject staffUsers = data.optJSONObject("staffUsers");
                if (staffUsers != null) {
                    JSONArray edges = staffUsers.optJSONArray("edges");
                    if (edges != null && edges.length() > 0) {
                        JSONObject node = edges.getJSONObject(0).optJSONObject("node");
                        String staffIdResult = node.optString("id");
                        String firstName = node.optString("firstName");
                        String lastName = node.optString("lastName");
                        String email = node.optString("email");
                        String mobileNo = node.optString("mobileNo");

                        // Extracting permissionGroups
                        JSONArray permissionGroups = node.optJSONArray("permissionGroups");
                        List<PermissionGroups> permissions = new ArrayList<>();
                        if (permissionGroups != null) {
                            for (int i = 0; i < permissionGroups.length(); i++) {
                                JSONObject permissionGroup = permissionGroups.getJSONObject(i);
                                String groupId = permissionGroup.optString("id");
                                String groupName = permissionGroup.optString("name");
                                permissions.add(new PermissionGroups(groupId, groupName));
                            }
                        }

                        return new SuccessDataResult<>(new StaffDto(staffIdResult, firstName, lastName, email, mobileNo, permissions),
                                "Staff fetched successfully");
                    } else {
                        return new ErrorDataResult<>("No staff found with the provided ID");
                    }
                }
            }
            return new ErrorDataResult<>("Failed to fetch staff: No data returned");
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch staff: Malformed URL");
        } catch (JSONException jSONException) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "JSON parsing error occurred", jSONException);
            return new ErrorDataResult<>("Failed to fetch staff: JSON parsing error occurred - " + jSONException.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch staff: An unexpected error occurred - " + ex.getMessage());
        }
    }

    public static DataResult<List<AddressDto>> getAddressesByCustomerId(String userId, String authToken,
            Integer pageNumber, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = checkDomainFromRequest(request);
        String apiResponse = "";

        // Construct the GraphQL query
        String query = "{ customers(first: " + pageNumber + ", filter: {ids: \"" + userId + "\"}) {"
                + "    edges {"
                + "        node {"
                + "            id"
                + "            firstName"
                + "            lastName"
                + "            email"
                + "            mobileNo"
                + "            addresses {"
                + "                id"
                + "                streetAddress1"
                + "                streetAddress2"
                + "                countryArea"
                + "                postalCode"
                + "                city"
                + "                cityArea"
                + "            }"
                + "        }"
                + "    }"
                + "}}";

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", query);

        try {
            // Execute the GraphQL query
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject responseObject = new JSONObject(apiResponse);
            JSONObject data = responseObject.optJSONObject("data");

            if (data != null) {
                JSONArray customers = data.optJSONObject("customers").optJSONArray("edges");
                if (customers != null && customers.length() > 0) {
                    JSONObject customerNode = customers.getJSONObject(0).optJSONObject("node"); // Assuming we're fetching a single customer by ID

                    // Extract customer details
                    String customerId = customerNode.getString("id");
                    String firstName = customerNode.optString("firstName");
                    String lastName = customerNode.optString("lastName");
                    String email = customerNode.optString("email");
                    String mobileNo = customerNode.optString("mobileNo");

                    // Parse addresses
                    JSONArray addresses = customerNode.optJSONArray("addresses");
                    List<AddressDto> addressList = new ArrayList<>();
                    if (addresses != null && addresses.length() > 0) {
                        for (int i = 0; i < addresses.length(); i++) {
                            JSONObject addressNode = addresses.getJSONObject(i);
                            String addressId = addressNode.optString("id");
                            String streetAddress1 = addressNode.optString("streetAddress1");
                            String streetAddress2 = addressNode.optString("streetAddress2");
                            String countryArea = addressNode.optString("countryArea");
                            String postalCode = addressNode.optString("postalCode");
                            String city = addressNode.optString("city");

                            // Create AddressDto object for each address
                            AddressDto addressDto = new AddressDto(customerId, addressId, streetAddress1, streetAddress2, countryArea, postalCode, city);
                            addressList.add(addressDto);
                        }
                    }

                    // Create CustomerDto object
                    CustomerAndSupplierDto customerDto = new CustomerAndSupplierDto(customerId, firstName, lastName, email, mobileNo, addressList);

                    return new SuccessDataResult<>(addressList, "Addresses fetched successfully");
                } else {
                    return new ErrorDataResult<>("No customer found with the provided ID");
                }
            }
            return new ErrorDataResult<>("Failed to fetch customer: No data returned");
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch customer: Malformed URL");
        } catch (JSONException jSONException) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "JSON parsing error occurred", jSONException);
            return new ErrorDataResult<>("Failed to fetch customer: JSON parsing error occurred - " + jSONException.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch customer: An unexpected error occurred - " + ex.getMessage());
        }
    }

    public static Result tokenVerify(String token, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));

        // Build the GraphQL query for token verification
        String tokenVerifyQuery = "mutation { tokenVerify(token: \"" + token + "\") { user { id firstName lastName email mobileNo } isValid payload errors { field message } } }";
        JSONObject verifyObj = new JSONObject();
        verifyObj.put("query", tokenVerifyQuery);

        String url = checkDomainFromRequest(request);
        String apiResponse = "";

        try {
            // Execute the GraphQL query to verify the token
            apiResponse = HTTPUtil.executeUrl(new URL(url), verifyObj.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "Malformed URL", ex);
            return new Result(false, "Malformed URL");
        }

        JSONObject resobj = new JSONObject(apiResponse);
        JSONObject tokenVerify = resobj.optJSONObject("data").optJSONObject("tokenVerify");

        if (tokenVerify == null) {
            return new ErrorResult("No token verification data found in the response.");
        }

        JSONArray errors = tokenVerify.optJSONArray("errors");
        if (errors != null && errors.length() > 0) {
            JSONObject error = errors.optJSONObject(0);
            if (error != null) {
                String field = error.optString("field");
                String message = error.optString("message");
                return new ErrorResult("Error: " + field + " - " + message);
            }
        }

        JSONObject userData = tokenVerify.optJSONObject("user");
        boolean isValid = tokenVerify.optBoolean("isValid");
        String payload = tokenVerify.optString("payload");

        if (userData == null) {
            return new ErrorResult("No user data found in the token verification response.");
        }

        User user = new User();
        user.setFirstName(userData.optString("firstName"));
        user.setLastName(userData.optString("lastName"));
        user.setId(userData.optString("id"));
        user.setEmail(userData.optString("email"));

        // You can handle the payload as needed in your application
        return new SuccessResult("Token verified successfully. User: " + user.toString() + ", isValid: " + isValid + ", Payload: " + payload);
    }

    public static Result tokenRefresh(String refreshToken, HttpSession session, HttpServletRequest request) {
        LoginResponse lr = new LoginResponse();
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));

        // Build the GraphQL query for token refresh
        String refreshTokenQuery = "mutation { tokenRefresh(refreshToken: \"" + refreshToken + "\") { token user { id firstName lastName email mobileNo } errors { field message } } }";
        JSONObject refreshObj = new JSONObject();
        refreshObj.put("query", refreshTokenQuery);

        String url = checkDomainFromRequest(request);
        String apiResponse = "";

        try {
            // Execute the GraphQL query to refresh the token
            apiResponse = HTTPUtil.executeUrl(new URL(url), refreshObj.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "Malformed URL", ex);
            return new Result(false, "Malformed URL");
        }

        JSONObject resobj = new JSONObject(apiResponse);
        JSONObject tokenRefresh = resobj.optJSONObject("data").optJSONObject("tokenRefresh");

        if (tokenRefresh == null) {
            return new ErrorResult("No token data found in the response.");
        }

        JSONArray errors = tokenRefresh.optJSONArray("errors");
        if (errors != null && errors.length() > 0) {
            JSONObject error = errors.optJSONObject(0);
            if (error != null) {
                String field = error.optString("field");
                String message = error.optString("message");
                return new ErrorResult("Error: " + field + " - " + message);
            }
        }

        JSONObject userData = tokenRefresh.optJSONObject("user");

        if (userData == null) {
            return new ErrorResult("No user data found in the response.");
        }

        User user = new User();
        user.setFirstName(userData.optString("firstName"));
        user.setLastName(userData.optString("lastName"));
        user.setId(userData.optString("id"));
        user.setEmail(userData.optString("email"));

        lr.setToken(tokenRefresh.optString("token"));
        lr.setRefreshToken(refreshToken); // Since it's a refresh token, it remains the same
        lr.setUser(user);
        lr.setApi_status(true);

        // Update the session with the new token
        session.setAttribute("token", tokenRefresh.optString("token"));

        return new SuccessResult("Token refreshed successfully.");
    }

    public static DataResult<String> addressDelete(String addressId, String authToken, HttpServletRequest request) {

        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = checkDomainFromRequest(request);
        String apiResponse = "";

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", "mutation { addressDelete(id: \"" + addressId + "\") { address { id } errors { field message } } }");

        try {
            // Execute the GraphQL mutation
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject responseObject = new JSONObject(apiResponse);
            JSONObject data = responseObject.optJSONObject("data");

            if (data != null) {
                JSONObject addressDelete = data.optJSONObject("addressDelete");
                if (addressDelete != null) {
                    JSONObject errors = addressDelete.optJSONObject("errors");
                    if (errors != null) {
                        // Handle errors
                        String field = errors.optString("field");
                        String message = errors.optString("message");
                        return new ErrorDataResult<>(message);
                    } else {
                        // Address deleted successfully
                        JSONObject address = addressDelete.optJSONObject("address");
                        String deletedAddressId = address.optString("id");
                        return new SuccessDataResult<>("Address with ID " + deletedAddressId + " deleted successfully");
                    }
                }
            }
            return new ErrorDataResult<>("Failed to delete address: No data returned");
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to delete address: Malformed URL");
        } catch (JSONException jSONException) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "JSON parsing error occurred", jSONException);
            return new ErrorDataResult<>("Failed to delete address: JSON parsing error occurred - " + jSONException.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to delete address: An unexpected error occurred - " + ex.getMessage());
        }
    }

    public static DataResult<String> staffDelete(String staffId, String authToken, HttpServletRequest request) {

        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = checkDomainFromRequest(request);
        String apiResponse = "";

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", "mutation { staffDelete(id: \"" + staffId + "\") { errors { field message } } }");

        try {
            // Execute the GraphQL mutation
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject responseObject = new JSONObject(apiResponse);
            JSONObject data = responseObject.optJSONObject("data");

            if (data != null) {
                JSONObject staffDelete = data.optJSONObject("staffDelete");
                if (staffDelete != null) {
                    JSONObject errors = staffDelete.optJSONObject("errors");
                    if (errors != null) {
                        // Handle errors
                        String field = errors.optString("field");
                        String message = errors.optString("message");
                        return new ErrorDataResult<>(message);
                    } else {
                        // Staff member deleted successfully
                        return new SuccessDataResult<>("Staff member with ID " + staffId + " deleted successfully");
                    }
                }
            }
            return new ErrorDataResult<>("Failed to delete staff member: No data returned");
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to delete staff member: Malformed URL");
        } catch (JSONException jSONException) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "JSON parsing error occurred", jSONException);
            return new ErrorDataResult<>("Failed to delete staff member: JSON parsing error occurred - " + jSONException.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to delete staff member: An unexpected error occurred - " + ex.getMessage());
        }
    }

    public static DataResult<String> productDelete(String productId, String authToken, HttpServletRequest request) {

        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = checkDomainFromRequest(request);
        String apiResponse = "";

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", "mutation { productDelete(id: \"" + productId + "\") { errors { field message } } }");

        try {
            // Execute the GraphQL mutation
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject responseObject = new JSONObject(apiResponse);
            JSONObject data = responseObject.optJSONObject("data");

            if (data != null) {
                JSONObject productDelete = data.optJSONObject("productDelete");
                if (productDelete != null) {
                    JSONObject errors = productDelete.optJSONObject("errors");
                    if (errors != null) {
                        // Handle errors
                        String field = errors.optString("field");
                        String message = errors.optString("message");
                        return new ErrorDataResult<>(message);
                    } else {
                        // Product deleted successfully
                        return new SuccessDataResult<>("Product with ID " + productId + " deleted successfully");
                    }
                }
            }
            return new ErrorDataResult<>("Failed to delete product: No data returned");
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to delete product: Malformed URL");
        } catch (JSONException jSONException) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "JSON parsing error occurred", jSONException);
            return new ErrorDataResult<>("Failed to delete product: JSON parsing error occurred - " + jSONException.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to delete product: An unexpected error occurred - " + ex.getMessage());
        }
    }

    public static DataResult<String> customerDelete(String customerId, String authToken, HttpServletRequest request) {

        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = checkDomainFromRequest(request);
        String apiResponse = "";

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", "mutation { customerDelete(id: \"" + customerId + "\") { errors { field message } } }");

        try {
            // Execute the GraphQL mutation
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject responseObject = new JSONObject(apiResponse);
            JSONObject data = responseObject.optJSONObject("data");

            if (data != null) {
                JSONObject customerDelete = data.optJSONObject("customerDelete");
                if (customerDelete != null) {
                    JSONObject errors = customerDelete.optJSONObject("errors");
                    if (errors != null) {
                        // Handle errors
                        String field = errors.optString("field");
                        String message = errors.optString("message");
                        return new ErrorDataResult<>(message);
                    } else {
                        // Customer deleted successfully
                        return new SuccessDataResult<>("Customer with ID " + customerId + " deleted successfully");
                    }
                }
            }
            return new ErrorDataResult<>("Failed to delete customer: No data returned");
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to delete customer: Malformed URL");
        } catch (JSONException jSONException) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "JSON parsing error occurred", jSONException);
            return new ErrorDataResult<>("Failed to delete customer: JSON parsing error occurred - " + jSONException.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to delete customer: An unexpected error occurred - " + ex.getMessage());
        }
    }

    // productType create Mutation
    public static DataResult<?> productTypeCreateMutation(String name, String authToken, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String mutationQuery = "mutation { productTypeCreate(input: { name: \"" + name + "\" }) { "
                + "errors { field message }"
                + " } }";

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", mutationQuery);

        String url = checkDomainFromRequest(request);
        String apiResponse = "";
        try {
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to create product type: MalformedURLException");
        }

        JSONObject responseObj = new JSONObject(apiResponse);
        JSONObject productTypeCreateResult = responseObj.getJSONObject("data").getJSONObject("productTypeCreate");

        // Check for errors
        JSONArray errors = productTypeCreateResult.getJSONArray("errors");
        if (errors.length() > 0) {
            JSONObject error = errors.getJSONObject(0);
            String errorMessage = error.getString("message");
            String errorField = error.getString("field");
            return new ErrorDataResult<>("Failed to create product type: " + errorMessage, errorField);
        } else {
            // Product type creation successful
            return new SuccessDataResult<>("Product type created successfully");
        }
    }

    // productType delete Mutation
    public static DataResult<?> productTypeDeleteMutation(String id, String authToken, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String mutationQuery = "mutation { productTypeDelete(id: \"" + id + "\") { "
                + "errors { field message }"
                + " } }";

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", mutationQuery);

        String url = checkDomainFromRequest(request);
        String apiResponse = "";
        try {
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to delete product type: MalformedURLException");
        }

        JSONObject responseObj = new JSONObject(apiResponse);
        JSONObject productTypeDeleteResult = responseObj.getJSONObject("data").getJSONObject("productTypeDelete");

        // Check for errors
        JSONArray errors = productTypeDeleteResult.getJSONArray("errors");
        if (errors.length() > 0) {
            JSONObject error = errors.getJSONObject(0);
            String errorMessage = error.getString("message");
            String errorField = error.getString("field");
            return new ErrorDataResult<>("Failed to delete product type: " + errorMessage, errorField);
        } else {
            // Product type deletion successful
            return new SuccessDataResult<>("Product type deleted successfully");
        }
    }

    public static DataResult<List<ProductType>> productTypeListQuery(Integer first, String authToken, String after,
            String isAsc, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = checkDomainFromRequest(request);
        String apiResponse = "";

        // Construct the GraphQL query
        String query;
        if (isAsc.equals("next")) {
            query = String.format("query { productTypes(first: %d, after: \"%s\", sortBy: {direction: ASC, field: NAME}) { pageInfo { hasNextPage hasPreviousPage } edges { cursor node { id name } } } }", first, after);
        } else {
            query = String.format("query { productTypes(last: %d, before: \"%s\", sortBy: {direction: ASC, field: NAME}) { pageInfo { hasNextPage hasPreviousPage } edges { cursor node { id name } } } }", first, after);
        }

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", query);

        try {
            // Execute the GraphQL query
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject responseObject = new JSONObject(apiResponse);
            JSONObject data = responseObject.optJSONObject("data");

            if (data != null) {
                JSONObject productTypes = data.optJSONObject("productTypes");
                if (productTypes != null) {
                    JSONArray edges = productTypes.optJSONArray("edges");
                    if (edges != null && edges.length() > 0) {
                        // Extraction of product types successful
                        List<ProductType> productTypeList = new ArrayList<>();
                        for (int i = 0; i < edges.length(); i++) {
                            JSONObject edge = edges.getJSONObject(i);
                            JSONObject node = edge.getJSONObject("node");
                            String productTypeId = node.getString("id");
                            String name = node.getString("name");
                            String cursor = edge.getString("cursor");

                            // Create ProductType object
                            ProductType productType = new ProductType();
                            productType.setProductTypeId(productTypeId);
                            productType.setProductTypeName(name);
                            productType.setCursor(cursor);

                            // Set pagination info
                            JSONObject pageInfo = productTypes.getJSONObject("pageInfo");
                            productType.setHasNextPage(pageInfo.getBoolean("hasNextPage"));
                            productType.setHasPreviousPage(pageInfo.getBoolean("hasPreviousPage"));

                            productTypeList.add(productType);
                        }

                        return new SuccessDataResult<>(productTypeList, "Product types fetched successfully");
                    }
                }
            }
            return new ErrorDataResult<>("Failed to fetch product types: No data returned");
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch product types: An unexpected error occurred - " + ex.getMessage());
        }
    }

    public static DataResult<?> productTypeUpdateMutation(String productTypeId, String productTypeName,
            String authToken, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        // Construct the GraphQL mutation
        String mutationQuery = "mutation { productTypeUpdate(id: \"" + productTypeId + "\", input: { name: \"" + productTypeName + "\" }) { "
                + "errors { field message }"
                + " } }";

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", mutationQuery);

        String url = checkDomainFromRequest(request);
        String apiResponse = "";
        try {
            // Execute the GraphQL mutation
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to update product type: MalformedURLException");
        }

        JSONObject responseObj = new JSONObject(apiResponse);
        JSONObject productTypeUpdateResult = responseObj.optJSONObject("data").optJSONObject("productTypeUpdate");

        if (productTypeUpdateResult != null) {
            // Check for errors
            JSONArray errors = productTypeUpdateResult.optJSONArray("errors");
            if (errors != null && errors.length() > 0) {
                JSONObject error = errors.optJSONObject(0);
                String errorMessage = error.optString("message", "Unknown error");
                String errorField = error.optString("field", "Unknown field");
                return new ErrorDataResult<>("Failed to update product type: " + errorMessage, errorField);
            } else {
                // Product type update successful
                return new SuccessDataResult<>("Product type updated successfully");
            }
        } else {
            // No data returned or unexpected response structure
            return new ErrorDataResult<>("Failed to update product type: No valid response received");
        }
    }

    // category create Mutation
    public static DataResult<String> categoryCreateMutation(String name, String authToken, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        // Construct the GraphQL mutation
        String mutationQuery = "mutation { categoryCreate(input: { name: \"" + name + "\" }) { "
                + "category { id name }"
                + "errors { field message }"
                + " } }";

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", mutationQuery);

        String url = checkDomainFromRequest(request);
        String apiResponse = "";
        try {
            // Execute the GraphQL mutation
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to create category: MalformedURLException");
        }

        JSONObject responseObj = new JSONObject(apiResponse);
        JSONObject categoryCreateResult = responseObj.optJSONObject("data").optJSONObject("categoryCreate");

        if (categoryCreateResult != null) {
            // Check for errors
            JSONArray errors = categoryCreateResult.optJSONArray("errors");
            if (errors != null && errors.length() > 0) {
                JSONObject error = errors.optJSONObject(0);
                String errorMessage = error.optString("message", "Unknown error");
                String errorField = error.optString("field", "Unknown field");
                return new ErrorDataResult<>("Failed to create category: " + errorMessage, errorField);
            } else {
                // Category creation successful, retrieve the id
                JSONObject category = categoryCreateResult.optJSONObject("category");
                if (category != null) {
                    String categoryId = category.optString("id");
                    return new SuccessDataResult<>(categoryId, "Category created successfully");
                } else {
                    return new ErrorDataResult<>("Failed to create category: No category data returned");
                }
            }
        } else {
            // No data returned
            return new ErrorDataResult<>("Failed to create category: No data returned");
        }
    }

    public static DataResult<?> categoryDeleteMutation(String id, String authToken, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        // Construct the GraphQL mutation
        String mutationQuery = "mutation { categoryDelete(id: \"" + id + "\") { "
                + "errors { field message }"
                + " } }";

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", mutationQuery);

        String url = checkDomainFromRequest(request);
        String apiResponse = "";
        try {
            // Execute the GraphQL mutation
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to delete category: MalformedURLException");
        }

        JSONObject responseObj = new JSONObject(apiResponse);
        JSONObject categoryDeleteResult = responseObj.optJSONObject("data").optJSONObject("categoryDelete");

        if (categoryDeleteResult != null) {
            // Check for errors
            JSONArray errors = categoryDeleteResult.optJSONArray("errors");
            if (errors != null && errors.length() > 0) {
                JSONObject error = errors.optJSONObject(0);
                String errorMessage = error.optString("message", "Unknown error");
                String errorField = error.optString("field", "Unknown field");
                return new ErrorDataResult<>("Failed to delete category: " + errorMessage, errorField);
            } else {
                // Category deletion successful
                return new SuccessDataResult<>("Category deleted successfully");
            }
        } else {
            // No data returned
            return new ErrorDataResult<>("Failed to delete category: No data returned");
        }
    }

    public static DataResult<List<Category>> categoryListQuery(Integer first, String authToken, String after,
            String isAsc, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = checkDomainFromRequest(request);
        String apiResponse = "";

        // Construct the GraphQL query for fetching categories with metadata
        String query;
        if (isAsc.equals("next")) {
            query = "query { categories(first: " + first + ", after: \"" + after + "\", sortBy: {direction: ASC, field: NAME}) { pageInfo { hasNextPage hasPreviousPage } edges { cursor node { id name metadata { key value } } } } }";
        } else {
            query = "query { categories(last: " + first + ", before: \"" + after + "\", sortBy: {direction: ASC, field: NAME}) { pageInfo { hasNextPage hasPreviousPage } edges { cursor node { id name metadata { key value } } } } }";
        }

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", query);

        try {
            // Execute the GraphQL query
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject responseObject = new JSONObject(apiResponse);
            JSONObject data = responseObject.optJSONObject("data");

            if (data != null) {
                JSONObject categories = data.optJSONObject("categories");
                if (categories != null) {
                    JSONArray edges = categories.optJSONArray("edges");
                    if (edges != null && edges.length() > 0) {
                        // Extraction of categories successful
                        List<Category> categoryList = new ArrayList<>();
                        for (int i = 0; i < edges.length(); i++) {
                            JSONObject edge = edges.getJSONObject(i);
                            JSONObject node = edge.getJSONObject("node");
                            String categoryId = node.getString("id");
                            String name = node.getString("name");
                            String cursor = edge.getString("cursor");

                            // Parse metadata
                            JSONArray metadataArray = node.optJSONArray("metadata");
                            Map<String, Object> metadata = new HashMap<>();
                            if (metadataArray != null) {
                                for (int j = 0; j < metadataArray.length(); j++) {
                                    JSONObject metadataItem = metadataArray.getJSONObject(j);
                                    String key = metadataItem.getString("key");
                                    Object value = metadataItem.get("value");
                                    metadata.put(key, value);
                                }
                            }

                            // Create Category object
                            Category category = new Category();
                            category.setCategoryId(categoryId);
                            category.setCategoryName(name);
                            category.setCursor(cursor);
                            category.setMetadata(metadata);

                            // Set pagination info
                            JSONObject pageInfo = categories.getJSONObject("pageInfo");
                            category.setHasNextPage(pageInfo.getBoolean("hasNextPage"));
                            category.setHasPreviousPage(pageInfo.getBoolean("hasPreviousPage"));

                            categoryList.add(category);
                        }
                        return new SuccessDataResult<>(categoryList, "Categories fetched successfully");
                    }
                }
            }
            return new ErrorDataResult<>("Failed to fetch categories: No data returned");
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch categories: An unexpected error occurred - " + ex.getMessage());
        }
    }

    public static DataResult<?> categoryUpdateMutation(String id, String categoryName, String authToken, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        // Construct the GraphQL mutation
        String mutationQuery = "mutation { categoryUpdate(id: \"" + id + "\", input: { name: \"" + categoryName + "\" }) { "
                + "errors { field message }"
                + " } }";

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", mutationQuery);

        String url = checkDomainFromRequest(request);
        String apiResponse = "";
        try {
            // Execute the GraphQL mutation
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to update category: MalformedURLException");
        }

        JSONObject responseObj = new JSONObject(apiResponse);
        JSONObject categoryUpdateResult = responseObj.optJSONObject("data").optJSONObject("categoryUpdate");

        if (categoryUpdateResult != null) {
            // Check for errors
            JSONArray errors = categoryUpdateResult.optJSONArray("errors");
            if (errors != null && errors.length() > 0) {
                JSONObject error = errors.optJSONObject(0);
                String errorMessage = error.optString("message", "Unknown error");
                String errorField = error.optString("field", "Unknown field");
                return new ErrorDataResult<>("Failed to update category: " + errorMessage, errorField);
            } else {
                // Category update successful
                return new SuccessDataResult<>("Category updated successfully");
            }
        } else {
            // No data returned or unexpected response structure
            return new ErrorDataResult<>("Failed to update category: No valid response received");
        }
    }

    public static DataResult<List<PermissionGroups>> permissionGroupsListQuery(Integer first, String authToken, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = checkDomainFromRequest(request);
        String apiResponse = "";

        // Construct the GraphQL query for fetching permission groups
        String query = String.format("query { permissionGroups(first: %d) { edges { node { id name } } } }", first);

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", query);

        try {
            // Execute the GraphQL query
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject responseObject = new JSONObject(apiResponse);
            JSONObject data = responseObject.optJSONObject("data");

            if (data != null) {
                JSONObject permissionGroups = data.optJSONObject("permissionGroups");
                if (permissionGroups != null) {
                    JSONArray edges = permissionGroups.optJSONArray("edges");
                    if (edges != null && edges.length() > 0) {
                        // Extraction of permission groups successful
                        List<PermissionGroups> permissionGroupsList = new ArrayList<>();
                        for (int i = 0; i < edges.length(); i++) {
                            JSONObject node = edges.getJSONObject(i).getJSONObject("node");
                            String groupId = node.getString("id");
                            String groupName = node.getString("name");

                            // Create PermissionGroups object
                            PermissionGroups permissionGroup = new PermissionGroups(groupId, groupName);
                            permissionGroupsList.add(permissionGroup);
                        }
                        return new SuccessDataResult<>(permissionGroupsList, "Permission groups fetched successfully");
                    }
                }
            }
            return new ErrorDataResult<>("Failed to fetch permission groups: No data returned");
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch permission groups: An unexpected error occurred - " + ex.getMessage());
        }
    }

    // Update MetaData Mutation
    public static DataResult<?> updateMetadataMutation(String id, Map<String, Object> metadata, String authToken, HttpServletRequest request) {

        // Set up HTTP request headers
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken));

        // Constructing the GraphQL mutation for product update
        StringBuilder mutationBuilder = new StringBuilder();
        mutationBuilder.append("mutation { updateMetadata(id: \"").append(id).append("\", input: [");
        for (Map.Entry<String, Object> entry : metadata.entrySet()) {
            mutationBuilder.append("{ key: \"").append(entry.getKey()).append("\", value: \"").append(entry.getValue()).append("\" },");
        }
        mutationBuilder.append("]) { errors { field message code } } }");

        JSONObject obje = new JSONObject();
        obje.put("query", mutationBuilder.toString());

        // Execute the GraphQL mutation
        String url = checkDomainFromRequest(request);
        String apiResponse = "";
        try {
            apiResponse = HTTPUtil.executeUrl(new URL(url), obje.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to update product: MalformedURLException");
        }

        // Parse the API response
        JSONObject responseObj = new JSONObject(apiResponse);
        JSONObject updateResult = responseObj.getJSONObject("data").getJSONObject("updateMetadata");

        // Check for errors
        JSONArray errors = updateResult.getJSONArray("errors");
        if (errors.length() > 0) {
            String errorMessage = errors.getJSONObject(0).getString("message");
            return new ErrorDataResult<>("Failed to update product: " + errorMessage);
        } else {
            // Product update successful
            // You can handle the successful update here as per your requirement
            return new SuccessDataResult<>("Metadata updated successfully");
        }
    }

    // product varient update mutation
    public static DataResult<String> productVariantUpdateMutation(String productVariantId, String sku,
            String authToken, HttpServletRequest request) {

        // Set up HTTP request headers
        List<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken));

        // Constructing the GraphQL mutation for product variant update
        String mutation = "mutation { productVariantUpdate(id: \"" + productVariantId + "\", input: { sku: \"" + sku + "\" }) { productVariant { id sku } errors { field message } } }";

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", mutation);

        // Execute the GraphQL mutation
        String url = checkDomainFromRequest(request);
        String apiResponse = "";
        try {
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), (ArrayList<BasicNameValuePair>) headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to update product variant: MalformedURLException");
        }

        // Parse the API response
        JSONObject responseObj = new JSONObject(apiResponse);
        JSONObject productVariantUpdateResult = responseObj.getJSONObject("data").getJSONObject("productVariantUpdate");

        // Check for errors
        JSONArray errors = productVariantUpdateResult.getJSONArray("errors");
        if (errors.length() > 0) {
            String errorMessage = errors.getJSONObject(0).getString("message");
            return new ErrorDataResult<>("Failed to update product variant: " + errorMessage);
        } else {
            // Product variant update successful
            JSONObject productVariant = productVariantUpdateResult.getJSONObject("productVariant");
            String updatedProductVariantId = productVariant.getString("id");
            // You can handle the successful update here as per your requirement
            return new SuccessDataResult<>(updatedProductVariantId, "Product variant updated successfully");
        }
    }

    public static HashMap<String, Object> splitExpSr(String barcode, String pattern) {

        HashMap<String, Object> map = new HashMap();
        String serialNo;
        String expiryDate = "";
        if (null == pattern || pattern.equals("")) {
            serialNo = barcode;
            expiryDate = "";
        } else {
            int index;
            if (pattern.contains("s*")) {
                index = pattern.indexOf("s*");
                serialNo = barcode.substring(index, barcode.length());
            } else {
                int count = 0;
                for (int i = 0; i < pattern.length(); i++) {
                    if (pattern.charAt(i) == 's') {
                        count++;
                    }
                }
                index = pattern.indexOf("s");
                serialNo = barcode.substring(index, index + count);
            }

            String ddPattern;
            String mmPattern = null;
            String yyPattern = null;
            String ddPatternValue;
            String mmPatternValue = null;
            String yyPatternValue = null;

            index = pattern.indexOf("d");
            ddPatternValue = barcode.substring(index, index + 2);
            ddPattern = pattern.substring(index, index + 2);

            if (pattern.contains("MMM")) {
                index = pattern.indexOf("M");
                mmPatternValue = barcode.substring(index, index + 3);
                mmPattern = pattern.substring(index, index + 3);
            } else if (pattern.contains("MM")) {
                index = pattern.indexOf("M");
                mmPatternValue = barcode.substring(index, index + 2);
                mmPattern = pattern.substring(index, index + 2);
            } else if (pattern.contains("mm")) {
                index = pattern.indexOf("m");
                mmPatternValue = barcode.substring(index, index + 2);
                mmPattern = "MM";
            }

            if (pattern.contains("yyyy")) {
                index = pattern.indexOf("y");
                yyPatternValue = barcode.substring(index, index + 4);
                yyPattern = pattern.substring(index, index + 4);
            } else if (pattern.contains("yy")) {
                index = pattern.indexOf("y");
                yyPatternValue = barcode.substring(index, index + 2);
                yyPattern = pattern.substring(index, index + 2);
            }

            try {
                String dateString = yyPatternValue + "-" + mmPatternValue + "-" + ddPatternValue; // Example string date
                SimpleDateFormat formatter = new SimpleDateFormat(yyPattern + "-" + mmPattern + "-" + ddPattern, Locale.ENGLISH);
                Date expiryDate1 = formatter.parse(dateString);
                formatter = new SimpleDateFormat("yyyy-MM-dd");
                expiryDate = formatter.format(expiryDate1);
            } catch (Exception e) {
            }
        }
        map.put("serialNo", serialNo);
        map.put("expiryDate", expiryDate);
        return map;
    }

    public static DataResult<List<Channels>> channelsListQuery(String authToken, HttpServletRequest request) {

        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken));

        String url = checkDomainFromRequest(request);
        String apiResponse = "";

        // Construct the GraphQL query for fetching channels
        String query = "query { channels { id name } }";

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", query);

        try {
            // Execute the GraphQL query
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject responseObject = new JSONObject(apiResponse);
            JSONObject data = responseObject.optJSONObject("data");

            if (data != null) {
                JSONArray channelsArray = data.optJSONArray("channels");
                if (channelsArray != null && channelsArray.length() > 0) {
                    // Extraction of channels successful
                    List<Channels> channelsList = new ArrayList<>();
                    for (int i = 0; i < channelsArray.length(); i++) {
                        JSONObject channelObject = channelsArray.getJSONObject(i);
                        String channelId = channelObject.getString("id");
                        String name = channelObject.getString("name");

                        // Create Channels object
                        Channels channel = new Channels();
                        channel.setChannelId(channelId);
                        channel.setChannelName(name);
                        channelsList.add(channel);
                    }
                    return new SuccessDataResult<>(channelsList, "Channels fetched successfully");
                }
            }
            return new ErrorDataResult<>("Failed to fetch channels: No data returned");
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch channels: An unexpected error occurred - " + ex.getMessage());
        }
    }

    public static DataResult<Category> searchCategoryByName(Integer last, String authToken, String searchFilter, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = checkDomainFromRequest(request);
        String apiResponse = "";

        // Construct the GraphQL query for fetching categories with a search filter
        String query = String.format("query { categories(filter: { search: \"%s\" }, last: %d) { edges { node { id name } } } }", searchFilter, last);

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", query);

        try {
            // Execute the GraphQL query
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject responseObject = new JSONObject(apiResponse);
            JSONObject data = responseObject.optJSONObject("data");

            if (data != null) {
                JSONObject categories = data.optJSONObject("categories");
                if (categories != null) {
                    JSONArray edges = categories.optJSONArray("edges");
                    if (edges != null && edges.length() > 0) {
                        // Extraction of categories successful
                        JSONObject node = edges.getJSONObject(0).getJSONObject("node");
                        String categoryId = node.getString("id");
                        String name = node.getString("name");

                        // Create Category object
                        Category category = new Category(categoryId, name);
                        return new SuccessDataResult<>(category, "Category fetched successfully");
                    }
                }
            }
            return new ErrorDataResult<>("Failed to fetch category: No data returned");
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch category: An unexpected error occurred - " + ex.getMessage());
        }
    }

    public static DataResult<ProductType> searchProductTypeByName(Integer last, String authToken, String searchFilter, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = checkDomainFromRequest(request);
        String apiResponse = "";

        // Construct the GraphQL query for fetching product types with a search filter
        String query = String.format("query { productTypes(filter: { search: \"%s\" }, last: %d) { edges { node { id name } } } }", searchFilter, last);

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", query);

        try {
            // Execute the GraphQL query
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject responseObject = new JSONObject(apiResponse);
            JSONObject data = responseObject.optJSONObject("data");

            if (data != null) {
                JSONObject productTypes = data.optJSONObject("productTypes");
                if (productTypes != null) {
                    JSONArray edges = productTypes.optJSONArray("edges");
                    if (edges != null && edges.length() > 0) {
                        // Extraction of product types successful
                        JSONObject node = edges.getJSONObject(0).getJSONObject("node");
                        String productTypeId = node.getString("id");
                        String name = node.getString("name");

                        // Create ProductType object
                        ProductType productType = new ProductType(productTypeId, name);
                        return new SuccessDataResult<>(productType, "Product type fetched successfully");
                    }
                }
            }
            return new ErrorDataResult<>("Failed to fetch product type: No data returned");
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch product type: An unexpected error occurred - " + ex.getMessage());
        }
    }

    public static Result autoLogin(String refreshToken, HttpSession session, HttpServletRequest request) {
        LoginResponse lr = new LoginResponse();
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));

        // Build the GraphQL query for token refresh
        String refreshTokenQuery = "mutation { tokenRefresh(refreshToken: \"" + refreshToken + "\") { token user { id firstName lastName email mobileNo dateOfBirth age userType permissionGroups {id, name, permissions {code, name}}} errors { field message } } }";
        JSONObject refreshObj = new JSONObject();
        refreshObj.put("query", refreshTokenQuery);

        String url = checkDomainFromRequest(request);
        String apiResponse = "";

        try {
            // Execute the GraphQL query to refresh the token
            apiResponse = HTTPUtil.executeUrl(new URL(url), refreshObj.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, "Malformed URL", ex);
            return new Result(false, "Malformed URL");
        }

        JSONObject resobj = new JSONObject(apiResponse);
        JSONObject tokenRefresh = resobj.optJSONObject("data").optJSONObject("tokenRefresh");

        if (tokenRefresh == null) {
            return new ErrorResult("No token data found in the response.");
        }

        JSONArray errors = tokenRefresh.optJSONArray("errors");
        if (errors != null && errors.length() > 0) {
            JSONObject error = errors.optJSONObject(0);
            if (error != null) {
                String field = error.optString("field");
                String message = error.optString("message");
                return new ErrorResult("Error: " + field + " - " + message);
            }
        }

        JSONObject userData = tokenRefresh.optJSONObject("user");

        if (userData == null) {
            return new ErrorResult("No user data found in the response.");
        }

        User user = new User();
        user.setFirstName(userData.optString("firstName"));
        user.setLastName(userData.optString("lastName"));
        user.setId(userData.optString("id"));
        user.setEmail(userData.optString("email"));
        user.setUserType(userData.optString("userType"));

        // Extract and populate permissionGroups
        JSONArray permissionGroupsArray = userData.optJSONArray("permissionGroups");
        if (permissionGroupsArray != null) {
            List<PermissionGroups> permissionGroups = new ArrayList<>();
            for (int i = 0; i < permissionGroupsArray.length(); i++) {
                JSONObject groupObject = permissionGroupsArray.optJSONObject(i);
                PermissionGroups group = new PermissionGroups();
                group.setPermissionGroupsId(groupObject.optString("id"));
                group.setPermissionGroupsName(groupObject.optString("name"));

                // Extract and populate permissions
                JSONArray permissionsArray = groupObject.optJSONArray("permissions");
                if (permissionsArray != null) {
                    List<Permission> permissions = new ArrayList<>();
                    for (int j = 0; j < permissionsArray.length(); j++) {
                        JSONObject permissionObject = permissionsArray.optJSONObject(j);
                        Permission permission = new Permission();
                        permission.setPermissionCode(permissionObject.optString("code"));
                        permission.setPermissionName(permissionObject.optString("name"));
                        permissions.add(permission);
                    }
                    group.setPermissions(permissions);
                }
                permissionGroups.add(group);
            }
            user.setPermissionGroups(permissionGroups);
        }

        lr.setToken(tokenRefresh.optString("token"));
        lr.setRefreshToken(refreshToken); // Since it's a refresh token, it remains the same
        lr.setUser(user);
        lr.setApi_status(true);

        // Update the session with the new token
        session.setAttribute("token", tokenRefresh.optString("token"));
        session.setAttribute("userEmail", user.getEmail());
        session.setAttribute("userName", user.getFirstName() + " " + user.getLastName());
        session.setAttribute("userId", user.getId());
        session.setAttribute("userType", user.getUserType());
        session.setAttribute("autoLogin", "1");
        session.setAttribute("refreshToken", lr.getRefreshToken());
        session.setAttribute("permissionGroups", user.getPermissionGroups());

        return new SuccessResult("Token refreshed successfully.");
    }

    // product list query
    public static DataResult<List<ProductDto>> importPurchaseProductListQuery(Integer pageNumber, String authToken,
            String after, String isAsc, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(new BasicNameValuePair("Content-Type", "application/json"));
        headers.add(new BasicNameValuePair("Authorization", "Bearer " + authToken)); // Include authentication token

        String url = checkDomainFromRequest(request);
        String apiResponse;

        // Construct the GraphQL query
        String query;
        if (isAsc.equals("next")) {
            query = "query {\n"
                    + "  products(first: " + pageNumber + ", after: \"" + after + "\", sortBy: {direction: ASC,field: NAME}) {\n"
                    + "    edges {\n"
                    + "      cursor node { id name seoDescription gst snoPattern trackingSerialNo seoTitle description slug created updatedAt rating channel isAvailable\n"
                    + "        category { id name } variants { sku }\n"
                    + "        productType { id name }\n"
                    + "        channelListings { id pricing { priceRangeUndiscounted { start { gross { amount } } }}purchaseCost { start { amount  } } }  metadata { key value } } } } }";
        } else {
            query = "query {\n"
                    + "  products(last: " + pageNumber + ", before: \"" + after + "\", sortBy: {direction: ASC,field: NAME}) {\n"
                    + "    edges {\n"
                    + "      cursor node { id name seoDescription gst snoPattern trackingSerialNo seoTitle description slug created updatedAt rating channel isAvailable\n"
                    + "        category { id name } variants { sku }\n"
                    + "        productType { id name }\n"
                    + "        channelListings { id pricing { priceRangeUndiscounted { start { gross { amount } } }}purchaseCost { start { amount  } } }  metadata { key value } } } } }";
        }

        JSONObject requestBody = new JSONObject();
        requestBody.put("query", query);

        try {
            // Execute the GraphQL query
            apiResponse = HTTPUtil.executeUrl(new URL(url), requestBody.toString(), headers, Constant.HTTP_REQUEST.POST.name());

            // Parse the API response
            JSONObject responseObject = new JSONObject(apiResponse);
            JSONObject data = responseObject.optJSONObject("data");

            if (data != null) {
                JSONObject products = data.optJSONObject("products");
                if (products != null) {
                    JSONArray edges = products.optJSONArray("edges");
                    if (edges != null && edges.length() > 0) {
                        // Extraction of products successful
                        List<ProductDto> productList = new ArrayList<>();
                        for (int i = 0; i < edges.length(); i++) {
                            JSONObject node = edges.getJSONObject(i).getJSONObject("node");
                            String cursor = edges.getJSONObject(i).getString("cursor");
                            String productId = node.getString("id");
                            String name = node.getString("name");
                            String description = node.optString("description");
                            String gst = node.optString("gst");
                            String snoPattern = node.optString("snoPattern");
                            String productSku;
                            try {
                                productSku = node.getJSONArray("variants").getJSONObject(0).optString("sku");
                            } catch (JSONException e) {
                                // Handle the exception, e.g., by setting a default value for productSku
                                productSku = "Zero Variants";
                            }

                            boolean trackingSerialNo = node.getBoolean("trackingSerialNo");

                            // Additional parsing for category, product type, and channel listings
                            JSONObject categoryJson = node.optJSONObject("category");
                            String categoryId = categoryJson != null ? categoryJson.getString("id") : null;
                            String categoryName = categoryJson != null ? categoryJson.getString("name") : null;

                            JSONObject productTypeJson = node.optJSONObject("productType");
                            String productTypeId = productTypeJson != null ? productTypeJson.getString("id") : null;
                            String productTypeName = productTypeJson != null ? productTypeJson.getString("name") : null;

                            JSONArray channelListingsJson = node.optJSONArray("channelListings");
                            float grossamount = 0;
                            if (channelListingsJson != null) {
                                for (int j = 0; j < channelListingsJson.length(); j++) {
                                    JSONObject channelListing = channelListingsJson.getJSONObject(j);
                                    JSONObject pricing = channelListing.optJSONObject("pricing");
                                    if (pricing != null) {
                                        JSONObject priceRangeUndiscounted = pricing.optJSONObject("priceRangeUndiscounted");
                                        if (priceRangeUndiscounted != null) {
                                            JSONObject start = priceRangeUndiscounted.optJSONObject("start");
                                            if (start != null) {
                                                JSONObject gross = start.optJSONObject("gross");
                                                if (gross != null) {
                                                    grossamount = (float) gross.getDouble("amount");

                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            // Parse purchase cost
                            float purchaseAmount = 0;
                            if (channelListingsJson != null) {
                                for (int j = 0; j < channelListingsJson.length(); j++) {
                                    JSONObject channelListing = channelListingsJson.getJSONObject(j);
                                    JSONObject purchaseCost = channelListing.optJSONObject("purchaseCost");
                                    if (purchaseCost != null) {
                                        JSONObject start = purchaseCost.optJSONObject("start");
                                        if (start != null) {
                                            purchaseAmount = (float) start.getDouble("amount");
                                            break; // Assuming purchase cost is the same for all channel listings
                                        }
                                    }
                                }
                            }

                            // Create ProductDto object
                            ProductDto product = new ProductDto();
                            product.setCursor(cursor);
                            product.setProductId(productId);
                            product.setProductName(name);
                            product.setProductDescription(description);
                            product.setPrice(grossamount);
                            product.setCostPrice(purchaseAmount);
                            product.setGst(gst);
                            product.setSnoPattern(snoPattern);
                            product.setCategory(categoryId, categoryName);
                            product.setProductType(productTypeId, productTypeName);
                            product.setTrackingSerialNo(trackingSerialNo);
                            product.setProductSku(productSku);

                            // Parse metadata
                            Map<String, Object> metadata = new HashMap<>();
                            JSONArray metadataArray = node.optJSONArray("metadata");
                            if (metadataArray != null) {
                                for (int k = 0; k < metadataArray.length(); k++) {
                                    JSONObject metadataObj = metadataArray.getJSONObject(k);
                                    String key = metadataObj.getString("key");
                                    String value = metadataObj.getString("value");
                                    metadata.put(key, value);
                                }
                            }
                            product.setMetadata(metadata);

                            productList.add(product);
                        }
                        return new SuccessDataResult<>(productList, "Product list fetched successfully");
                    }
                }
            }
            return new ErrorDataResult<>("Failed to fetch product list: No products found");
        } catch (Exception ex) {
            Logger.getLogger(CommonUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new ErrorDataResult<>("Failed to fetch product list: An unexpected error occurred");
        }
    }

    public static int updateMetadata(List<HashMap<String, Object>> data, String userId, String authToken, HttpServletRequest request) {
        ArrayList<BasicNameValuePair> headers = new ArrayList<>();
        headers.add(0, new BasicNameValuePair("Content-Type",
                "application/json"));
        headers.add(0, new BasicNameValuePair("Authorization",
                "Bearer " + authToken));
        JSONObject resobj;
        String resData;
        String apiResponse = null;
        JSONObject obje;
        String url = checkDomainFromRequest(request);
        resData = "mutation { updateMetadata(id: \"" + userId + "\", input: [";
        for (int i = 0; i < data.size(); i++) {
            HashMap<String, Object> map = data.get(i);
            resData = resData + "{ key: \"" + map.get("key") + "\", value: \"" + map.get("value") + "\" },";
        }
        resData = resData.substring(0, resData.length() - 1);
        resData = resData + "]) { errors { message } item { metadata { key value } } } }";

        obje = new JSONObject();
        obje.put("query", resData);
        try {
            apiResponse = HTTPUtil.executeUrl(new URL(url), obje.toString(), headers, Constant.HTTP_REQUEST.POST.name());
        } catch (MalformedURLException ex) {

        }
//        resobj = new JSONObject((String) apiResponse);
        try {
            System.out.print(apiResponse);
            resobj = new JSONObject((String) apiResponse);
            JSONArray errors = resobj.getJSONObject("data").getJSONObject("updateMetadata").getJSONArray("errors");

            if (errors.length() == 0) {
                return 0;
            } else {
                return errors.length();
            }
        } catch (Exception e) {
            return -1;
        }
    }

    public static boolean checkImageExt(String imageName) {

        //Check extension
        return imageName.toLowerCase().endsWith(".jpeg") || imageName.toLowerCase().endsWith(".jpg")
                || imageName.toLowerCase().endsWith(".png") || imageName.toLowerCase().endsWith(".pdf");
    }

    public static boolean writeImage(MultipartFile file, String requestUrl, String imageName) {

        Logger.getLogger(HTTPUtil.class.getName()).log(Level.SEVERE,
                requestUrl);
        boolean status = false;
        try {
            boolean flag = CommonUtil.checkImageExt(imageName);
            if (flag) {

                // Get the file and save it somewhere
                byte[] bytes = file.getBytes();
                Path path = Paths.get(requestUrl + imageName);
                Files.write(path, bytes);
                status = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(HTTPUtil.class.getName()).log(Level.SEVERE,
                    ex.getMessage());
        }
        Logger.getLogger(HTTPUtil.class.getName()).log(Level.SEVERE,
                "Image write");
        return status;
    }

    public static String checkDomainFromRequest(ServletRequest request) {
        return Constant.AV_MEDITECH_INVENTORY_API_URL_TEST;
    }

}
