package com.orange.flavor_quartz_job.model.request;

import lombok.Data;

import java.time.temporal.ChronoUnit;

@Data
public class ScheduleNotification {
    private int repeatCount;
    private int repeatIntervel;
    private ChronoUnit repeatIntervelUnit = ChronoUnit.DAYS;
    private int noOfDaysAfterToReminder;

}
