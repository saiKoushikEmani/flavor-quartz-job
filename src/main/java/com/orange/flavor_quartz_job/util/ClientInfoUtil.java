package com.orange.flavor_quartz_job.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientInfoUtil {
    public static Date convertStringToDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Define format
        sdf.setLenient(false);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
