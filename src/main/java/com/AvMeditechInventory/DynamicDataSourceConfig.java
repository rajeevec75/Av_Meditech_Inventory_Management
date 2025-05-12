///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.AvMeditechInventory;
//
//import javax.sql.DataSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import javax.servlet.http.HttpServletRequest;
//
//@Configuration
//public class DynamicDataSourceConfig {
//
//    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceConfig.class);
//
//    @Bean
//    public DataSource getDataSource() {
//        HttpServletRequest request = getCurrentHttpRequest();
//        String dbName = determineDatabaseName(request);
//
//        logger.info("Using database: {}", dbName);
//
//        return DataSourceBuilder.create()
//                .driverClassName("org.postgresql.Driver")
//                .url("jdbc:postgresql://av-meditech.c9yiomscu0of.ap-south-1.rds.amazonaws.com:5432/" + dbName)
//                .username("postgres")
//                .password("jd8ueR45KJfsa_0a")
//                .build();
//    }
//
//    private HttpServletRequest getCurrentHttpRequest() {
//        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (attrs == null) {
//            logger.warn("No HTTP request available. Defaulting to fallback DataSource.");
//            return null; // or throw an exception if you want to fail the app startup
//        }
//        return attrs.getRequest();
//    }
//
//    private String determineDatabaseName(HttpServletRequest request) {
//        String domain = (request != null) ? request.getServerName() : "localhost"; // Get the domain
//        String dbName; // Initialize dbName
//
//        logger.info("Configuring data source for domain: {}", domain);
//
//        // Check if the domain is null or not
//        if (domain == null) {
//            logger.error("Domain is null. Using default database.");
//            dbName = "default_database"; // Specify a default database
//        } else {
//            // Check the domain and set the dbName accordingly
//            switch (domain) {
//                case "localhost":
//                    dbName = "lenseinventory";
//                    break;
//                case "sim.avmeditech.com":
//                    dbName = "avmeditech";
//                    break;
//                default:
//                    logger.error("Unknown domain: {}. Using default database.", domain);
//                    dbName = "default_database"; // Specify a default database or handle error
//                    break;
//            }
//        }
//
//        return dbName; // Return the determined database name
//    }
//
//}
