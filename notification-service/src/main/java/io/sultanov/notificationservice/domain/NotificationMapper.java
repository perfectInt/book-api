package io.sultanov.notificationservice.domain;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NotificationMapper {

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "message", column = "message"),
            @Result(property = "dateOfNotification", column = "date_of_notification")
    })
    @Select("""
            INSERT INTO notification (message, date_of_notification)
            VALUES (#{message}, #{dateOfNotification}) RETURNING *;
            """)
    Notification insertNotification(NotificationDto notificationDto) throws RuntimeException;
}
