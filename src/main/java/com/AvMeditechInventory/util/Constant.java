/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.util;

import java.security.SecureRandom;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public class Constant {

    // Length of the OTP
    public static final int OTP_LENGTH = 6;
    // Characters allowed in the OTP
    public static final String OTP_CHARACTERS = "0123456789";
    // SecureRandom instance for generating random numbers
    public static final SecureRandom secureRandom = new SecureRandom();
    public static final String EMAIL_SUBJECT = "OTP for  AvMeditechInventory ";
    public static final String EMAIL_BODY = "OTP here : ";
    public static final String CUSTOMER_ROLE = "Customer";

    public static final String AV_MEDITECH_INVENTORY_API_URL_TEST = "http://43.204.66.152:8001/graphql/";
    public static final String AV_MEDITECH_INVENTORY_API_URL_GR = "http://43.204.66.152:8000/graphql/";
    public static final String AV_MEDITECH_INVENTORY_API_URL_LH = "http://43.204.66.152:8003/graphql/";
    public static final String AV_MEDITECH_INVENTORY_API_URL_AVMPL = "http://43.204.66.152:8002/graphql/";
    public static final String AV_MEDITECH_INVENTORY_API_URL_AVMPLCO = "http://43.204.66.152:8004/graphql/";
    public static final String AV_MEDITECH_INVENTORY_PASSWORD = "testAdmin@9";
    public static final Integer AV_MEDITECH_INVENTORY_USER_ID = 1;
    public static final String AV_MEDITECH_INVENTORY_ENC_PASSWORD = "pbkdf2_sha256$260000$KoeCC9i5ieacHAo7Bf9PGz$WbNDGTC17eOhy5cO58c96ngSTEcT0ZSXZq4IyQntRsE=";
    public static final String AV_MEDITECH_INVENTORY_BETA_API_URL = "";
    public static final String AV_MEDITECH_INVENTORY_API_AUTH_TOKEN = "Bearer AiUtSO69RmuUql9DX7IEApzz5MlF2y";
    public static final String CUSTOMER = "customer";
    public static final String SUPPLIER = "supplier";
    public static final String BOTH = "both";
    public static final String ACTION_TYPE_PURCHASE = "purchase";
    public static final String ACTION_TYPE_SALE = "sale";
    public static final String ACTION_TYPE_SALE_RETURN = "sale return";
    public static final String ACTION_TYPE_TRANSFER = "transfer";
    public static final String ACTION_TYPE_TRANSFER_OUT = "transfer1";
    public static final String EMAIL_DOMAIN = "@avmeditech.com";

    public static final int ATTRIBUTE_LENGTH = 3;

    public static final String HSH_PASSWD_HSH_1 = "*0*eD6f*94_a6#8@6|13hd6b$@ae(_)57";

    public static enum HTTP_REQUEST {
        GET,
        PUT,
        POST,
        DELETE;
    }
    public static final String CHANNEL_ID = "Q2hhbm5lbDox";

    public static final String ITEM_DESCRIPTION = "{\"blocks\":[{\"data\":{\"text\":\"<description>\"},\"type\":\"paragraph\"}],\"version\":\"2.22.2\"}";

    public static final String AV_MEDITECH_ADDRESS = "GROUND FLOOR DSS 308, SECTOR 20 HUDA, KAITHAL-136027";
    public static final String LENSE_HOME_ADDRESS = "DSS 309 HUDA SECTOR 20, KAITHAL-136027";

    public static final String DOMAIN1 = "localhost";
    public static final String DOMAIN2 = "sim.avmeditech.com";
    public static final String DOMAIN3 = "lhsim.avmeditech.com";
    public static final String DOMAIN4 = "avmplsim.avmeditech.com";
    public static final String DOMAIN5 = "avmplcosim.avmeditech.com";
    public static final String DOMAIN6 = "test.avmeditech.com";

}
