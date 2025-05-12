/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service.implementation;

import com.AvMeditechInventory.entities.Channels;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.ErrorDataResult;
import com.AvMeditechInventory.results.ErrorResult;
import com.AvMeditechInventory.results.Result;
import com.AvMeditechInventory.results.SuccessDataResult;
import com.AvMeditechInventory.results.SuccessResult;
import com.AvMeditechInventory.service.ChannelsService;
import com.AvMeditechInventory.util.CommonUtil;
import com.AvMeditechInventory.util.ServiceDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Service
public class ChannelsServiceImpl implements ChannelsService {

    @Autowired
    private ServiceDao serviceDao;

    @Override
    public DataResult<List<Channels>> channelsListQuery(String authToken, HttpServletRequest request) {
        try {
            DataResult<List<Channels>> channelsListQuery = CommonUtil.channelsListQuery(authToken, request);
            if (!channelsListQuery.isSuccess()) {
                return new ErrorDataResult<>("Failed to reterive channels:" + channelsListQuery.getMessage());
            }
            return new SuccessDataResult<>(channelsListQuery.getData(), "Channels fetched successfully");
        } catch (Exception e) {

            return new ErrorDataResult<>("An unexpected error occurred while fetching channels: " + e.getMessage());
        }
    }

    @Override
    public DataResult<List<Channels>> channel_channel_list() {
        try {
            // Retrieve channel data from the service
            DataResult<List<Map<String, Object>>> channelDataResult = this.serviceDao.channel_channel_list();

            // Check if the channel data retrieval was successful
            if (channelDataResult.isSuccess()) {
                List<Map<String, Object>> data = channelDataResult.getData();
                List<Channels> channelList = new ArrayList<>();

                // Iterate over the data to create Channels objects and set the name attribute
                for (Map<String, Object> channelData : data) {
                    Channels channel = new Channels();
                    channel.setChannel_id((Integer) channelData.get("id"));
                    channel.setChannelName((String) channelData.get("name"));

                    channelList.add(channel);
                }

                // Return the successful result with the list of channels
                return new SuccessDataResult<>(channelList, channelDataResult.getMessage());
            } else {
                // Return an error result if the channel data retrieval failed
                return new ErrorDataResult<>(channelDataResult.getMessage());
            }
        } catch (Exception e) {
            // Handle any unexpected exceptions and return an error result
            return new ErrorDataResult<>("An unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public DataResult<List<Channels>> getChannelListByUserId(String email) {
        try {
            DataResult<Map<String, Object>> userDataByEmail = serviceDao.getUserDataByEmail(email);
            Map<String, Object> userData = userDataByEmail.getData();
            Integer user_id = (Integer) userData.get("id");
            // Fetch channel list by user ID
            DataResult<List<Map<String, Object>>> channelListByUserId = this.serviceDao.getChannelListByUserId(user_id);

            // Check if the result is successful
            if (!channelListByUserId.isSuccess()) {
                return new ErrorDataResult<>("Failed to retrieve channel data for user ID: " + user_id);
            }

            // Get the data from the result
            List<Map<String, Object>> data = channelListByUserId.getData();

            // Convert the data to List<Channels>
            List<Channels> channelsList = new ArrayList<>();
            for (Map<String, Object> entry : data) {
                Channels channel = new Channels();
                channel.setChannel_id((Integer) entry.get("channel_id"));
                channel.setChannelName((String) entry.get("name"));
                channelsList.add(channel);
            }

            // Return successful result
            return new SuccessDataResult<>(channelsList, "Successfully retrieved channel data for user ID: " + user_id);
        } catch (Exception e) {
            // Return error result with exception message
            return new ErrorDataResult<>("An unexpected error occurred: " + e.getMessage());
        }
    }

    @Override
    public Result updateChannelForUser(String email, String addStore) {
        try {
            // Retrieve user data by email
            DataResult<Map<String, Object>> userDataByEmail = this.serviceDao.getUserDataByEmail(email);
            if (!userDataByEmail.isSuccess()) {
                return new ErrorResult("Failed to retrieve user data by email: " + userDataByEmail.getMessage());
            }

            Map<String, Object> userData = userDataByEmail.getData();
            Integer userId = (Integer) userData.get("id");
            int deleteChannelForUser = this.serviceDao.deleteChannelForUser(userId);

            if (deleteChannelForUser < 0) {
                return new ErrorResult("Failed to delete the existing channel assignment for user ID: " + userId);
            }

            // Split the string into individual IDs based on comma delimiter
            String[] split = addStore.split(",");
            for (String channelId : split) {
                // Convert string ID to integer
                int channelIdInt = Integer.parseInt(channelId.trim()); // trim() to remove any leading/trailing spaces

                // Update user channels
                Result updateResult = this.serviceDao.insertUserChannels(userId, channelIdInt);
                if (!updateResult.isSuccess()) {
                    return new ErrorResult("Failed to update user channel for channel ID: " + channelIdInt + ". " + updateResult.getMessage());
                }
            }

            return new SuccessResult("Successfully updated channels for user: " + email);
        } catch (NumberFormatException e) {
            return new ErrorResult("Invalid channel ID format: " + e.getMessage());
        } catch (Exception e) {
            return new ErrorResult("An unexpected error occurred while updating channels: " + e.getMessage());
        }
    }

}
