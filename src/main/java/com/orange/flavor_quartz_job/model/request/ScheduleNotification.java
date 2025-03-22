package com.orange.flavor_quartz_job.model.request;

import lombok.Data;
import lombok.Setter;

import java.time.temporal.ChronoUnit;

@Data
public class ScheduleNotification {
    @Setter(lombok.AccessLevel.NONE)
    private int repeatCount;
    private int repeatIntervel;
    private ChronoUnit repeatIntervelUnit = ChronoUnit.DAYS;
    private int noOfDaysAfterToReminder;

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount - 1;
    }
}
