package com.commic.v1.api;

import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.entities.Notification;
import com.commic.v1.services.notifications.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    @Autowired
    private INotificationService notificationService;
    @GetMapping("/all")
    public APIResponse<ArrayList<Notification>> getAllNotifications() {
        APIResponse<ArrayList<Notification>> apiResponse = new APIResponse<>();
        apiResponse.setCode(200);
        apiResponse.setMessage("Success");
        apiResponse.setResult(notificationService.findAllByOrderByDateDesc());
        return apiResponse;
    }

    @GetMapping("/count")
    public APIResponse<Integer> findAllCurrentDateNotification() {
        APIResponse<Integer> apiResponse = new APIResponse<>();
        apiResponse.setCode(200);
        apiResponse.setMessage("Success");
        apiResponse.setResult(notificationService.findAllCurrentDateNotification());
        return apiResponse;
    }
}
