package com.commic.v1.services.notifications;

import com.commic.v1.entities.Notification;
import com.commic.v1.exception.AppException;
import com.commic.v1.exception.ErrorCode;
import com.commic.v1.repositories.INotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NotificationServiceImp implements INotificationService {
    @Autowired
    private INotificationRepository notificationRepository;
    @Override
    public ArrayList<Notification> findAllByOrderByDateDesc() {
        ArrayList<Notification> notifications = notificationRepository.findAllByOrderByDateDesc();
        if(notifications.isEmpty()) throw new AppException(ErrorCode.NOTIFICATION_EMPTY);
        return notifications;
    }

    @Override
    public Integer findAllCurrentDateNotification() {
        return notificationRepository.findAllCurrentDateNotification();
    }

}
