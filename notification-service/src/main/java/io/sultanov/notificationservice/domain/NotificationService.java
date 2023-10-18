package io.sultanov.notificationservice.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationMapper notificationMapper;

    public Notification createNotification(NotificationDto notificationDto) {
        return notificationMapper.insertNotification(notificationDto);
    }

    @KafkaListener(topics = "author-notification-topic", groupId = "author-group")
    public void receiveMessage(String message) {
        System.out.println("Received new author from author-service:\n" + message);
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setMessage(message);
        createNotification(notificationDto);
    }
}
