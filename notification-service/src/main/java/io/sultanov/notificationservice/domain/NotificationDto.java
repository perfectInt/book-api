package io.sultanov.notificationservice.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class NotificationDto {
    private String message;
    private Timestamp dateOfNotification = new Timestamp(System.currentTimeMillis());
}
