package io.github.biielkts.utils;

import java.util.concurrent.TimeUnit;

public class TimeFormatter {
    public static String format(long time) {
        if (time == 0) return "nunca";

        long day = TimeUnit.MILLISECONDS.toDays(time);
        long hours = TimeUnit.MILLISECONDS.toHours(time) - (day * 24);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - (hours * 60);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - (minutes * 60);
        long milliseconds = TimeUnit.MILLISECONDS.toMillis(time) - (seconds * 1000);

        StringBuilder sb = new StringBuilder();

        if (day > 0) {
            sb.append(day).append("d").append(" ");
        }
        if (hours > 0) {
            sb.append(hours).append("h").append(" ");
        }
        if (minutes > 0) {
            sb.append(minutes).append("m").append(" ");
        }
        if (seconds > 0) {
            sb.append(seconds).append("s").append(" ");
        }
        if (milliseconds > 0) {
            sb.append(milliseconds).append("ms");
        }

        String diff = sb.toString();

        return diff.isEmpty() ? "agora" : diff;
    }
}
