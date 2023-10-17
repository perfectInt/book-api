package io.sultanov.notificationservice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class Notification {
    private Integer id;
    private String message;
    private Timestamp dateOfNotification;
}
