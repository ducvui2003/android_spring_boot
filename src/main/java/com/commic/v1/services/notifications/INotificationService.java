package com.commic.v1.services.notifications;

import com.commic.v1.dto.responses.NotificationResponseDTO;
import com.commic.v1.entities.Notification;

import java.util.ArrayList;

public interface INotificationService {
    ArrayList<Notification> findAllByOrderByDateDesc();

}
