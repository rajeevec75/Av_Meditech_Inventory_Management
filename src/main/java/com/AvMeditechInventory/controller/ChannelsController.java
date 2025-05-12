/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.AvMeditechInventory.controller;

import com.AvMeditechInventory.entities.Channels;
import com.AvMeditechInventory.results.DataResult;
import com.AvMeditechInventory.service.ChannelsService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Rajeev kumar(QMM Technologies Private Limited)
 */
@Controller
public class ChannelsController {

    @Autowired
    private ChannelsService channelsService;

    @GetMapping("/channelList/json")
    @ResponseBody
    public ResponseEntity<?> channelsList(HttpSession session, Model model, HttpServletRequest request) {
        try {
            String authToken = (String) session.getAttribute("token");
            DataResult<List<Channels>> channelListResult = this.channelsService.channelsListQuery(authToken, request);

            if (!channelListResult.isSuccess()) {
                return ResponseEntity.ok("Failed fetching the channels list." + channelListResult.getMessage());
            }

            List<Channels> channelList = channelListResult.getData();
            model.addAttribute("channelList", channelList);
            model.addAttribute("selectedStoreId", session.getAttribute("selectedStoreId"));
            model.addAttribute("selectedStoreName", session.getAttribute("selectedStoreName"));

            return ResponseEntity.ok(channelList);

        } catch (Exception e) {
            return ResponseEntity.ok("An error occurred while fetching the channels list." + e.getMessage());
        }
    }

    @PostMapping("/updateStoreSelection")
    @ResponseBody
    public ResponseEntity<?> updateStoreSelection(@RequestParam("storeId") int storeId, @RequestParam("storeName") String storeName, HttpSession session) {
        session.setAttribute("selectedStoreId", storeId);
        session.setAttribute("selectedStoreName", storeName);
        return ResponseEntity.ok("");
    }
}
