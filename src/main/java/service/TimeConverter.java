package service;

import javax.ejb.Local;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimeConverter {
    public static String getUsersTimeOnSite(LocalDateTime registration){
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(registration,now);
        String result = "Вы на сайте уже: " + duration.toDays() + " дней, " +
                duration.toHours() % 24 + " часов, " + duration.toMinutes() % 60 + " минут";
        return result;
    }
}
