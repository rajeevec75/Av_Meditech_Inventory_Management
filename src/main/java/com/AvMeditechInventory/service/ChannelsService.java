/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.service;

import com.AvMeditechInventory.entities.Channels;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.results.Result;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
public interface ChannelsService {

    public DataResult<List<Channels>> channelsListQuery(String authToken, HttpServletRequest request);

    public DataResult<List<Channels>> channel_channel_list();

    public DataResult<List<Channels>> getChannelListByUserId(String email);

    public Result updateChannelForUser(String email, String addStore);

}
