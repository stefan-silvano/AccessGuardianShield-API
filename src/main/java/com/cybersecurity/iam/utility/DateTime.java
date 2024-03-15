package com.cybersecurity.iam.utility;

import com.cybersecurity.iam.exception.type.BadRequestException;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
    public static Timestamp toTimestamp(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateFormat.parse(dateString);
            return new Timestamp(date.getTime());
        } catch (ParseException exception) {
            throw new BadRequestException(exception.getMessage());
        }
    }

    public static String toString(Timestamp timestamp) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(timestamp);
    }

    public static Timestamp[] overleap(Timestamp start1, Timestamp end1, Timestamp start2, Timestamp end2) {
        Timestamp overlapStart = start1.before(start2) ? start2 : start1;
        Timestamp overlapEnd = end1.before(end2) ? end1 : end2;

        if (overlapStart.before(overlapEnd) || overlapStart.equals(overlapEnd)) {
            return new Timestamp[]{overlapStart, overlapEnd};
        }
        return null;
    }
}
