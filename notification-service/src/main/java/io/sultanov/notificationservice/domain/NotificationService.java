package io.sultanov.notificationservice.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationMapper notificationMapper;

    public Notification createNotification(NotificationDto notificationDto) {
        return notificationMapper.insertNotification(notificationDto);
    }
}
