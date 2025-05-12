/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.AvMeditechInventory.sms.service;

import com.AvMeditechInventory.results.ErrorResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.results.SuccessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Service
public class SmsService {

    @Autowired
    private JavaMailSender javaMailSender;

    // The email address of the sender.
    @Value("${spring.mail.username}")
    private String senderEmail;

    public Result sendEmailOTP(String toEmail, String emailSubject, String emailBody) {
        // Implement the logic to send an email OTP
        // Check if any of the parameters are empty or null
        if (toEmail == null || toEmail.isEmpty() || emailSubject == null || emailSubject.isEmpty() || emailBody == null || emailBody.isEmpty()) {
            // Return an error result indicating that one or more parameters are missing
            return new ErrorResult("Missing or empty email parameters. Please provide valid values.");
        }

        try {
            // Create a SimpleMailMessage and send it using the JavaMailSender.
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setFrom(senderEmail);
            simpleMailMessage.setTo(toEmail);
            simpleMailMessage.setSubject(emailSubject);
            simpleMailMessage.setText(emailBody);

            javaMailSender.send(simpleMailMessage);

            // If sending the email was successful, return a SuccessResult
            return new SuccessResult("OTP sent successfully to " + toEmail);
        } catch (MailException mailException) {
            // Handle the exception and provide an error message.
            return new ErrorResult("Failed to send OTP to " + toEmail + ". Please try again later.");
        }
    }

}
