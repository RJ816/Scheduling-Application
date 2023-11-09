package helper;

import dao.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Appointment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract class to manage time/location data.
 */
public abstract class Time {
    private static ZoneId localZoneId = ZoneId.systemDefault();
    private static ZoneId etZoneId = ZoneId.of("America/New_York");
    private static DateTimeFormatter utcFormatter =
            DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss").withZone(ZoneOffset.UTC);
    private static DateTimeFormatter localFormatter =
            DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:00").withResolverStyle(ResolverStyle.STRICT).withZone(localZoneId);
    private static Alert eAlert = new Alert(Alert.AlertType.ERROR);
    private static Alert iAlert = new Alert(Alert.AlertType.INFORMATION);

    /**
     * @return Local system timezone.
     */
    public static String getLocalZoneID() {
        return localZoneId.toString();
    }

    /**
     * @return Timestamp in UTC standard and database format.
     */
    public static String getTimestampUTC() {
        Instant instant = Instant.now();
        String timestamp = utcFormatter.format(instant);
        return timestamp;
    }

    /**
     * @param utc UTC datetime.
     * @return local datetime.
     */
    public static String convertUTCToLocal(String utc) {
        Instant instant = utcFormatter.parse(utc, Instant::from);
        ZonedDateTime localDateTime = instant.atZone(localZoneId);
        DateTimeFormatter localFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String local = localFormatter.format(localDateTime);
        return local;
    }

    /**
     * @param local Local datetime.
     * @return UTC datetime.
     */
    public static String convertLocalToUTC(String local) {
        ZonedDateTime localDateTime = ZonedDateTime.parse(local, localFormatter);
        Instant instant = localDateTime.toInstant();
        String utc = utcFormatter.format(instant);
        return utc;
    }

    /**
     * @return List of all times throughout a day in 15 minute intervals.
     */
    public static List<String> getTimeList() {
        List<String> timeList = new ArrayList<>();
        int startHour = 0;
        int startMinute = 0;
        int endHour = 23;
        int endMinute = 45;
        int intervalMinutes = 15;
        for (int hour = startHour; hour <= endHour; hour++) {
            for (int minute = startMinute; minute <= endMinute; minute += intervalMinutes) {
                String time = String.format("%02d:%02d", hour, minute);
                timeList.add(time);
            }
        }
        return timeList;
    }

    /**
     * @param date Local date.
     * @param time Local time.
     * @return Datetime in UTC format.
     */
    public static String combineDateTime(LocalDate date, String time) {
        LocalTime ltTime = LocalTime.parse(time);
        LocalDateTime datetime = LocalDateTime.of(date, ltTime);
        String datetimeFormatted = utcFormatter.format(datetime);
        return datetimeFormatted;
    }

    /**
     * @param startTime Start time.
     * @param endTime End time.
     */
    public static void isStartTimeAfterEndTime(String startTime, String endTime) throws Exception {
        try {
            LocalTime ltStart = LocalTime.parse(startTime);
            LocalTime ltEnd = LocalTime.parse(endTime);
            if (ltStart.isAfter(ltEnd) || ltStart.equals(ltEnd)) {
                throw new Exception();
            }
        } catch (Exception e) {
            eAlert.setContentText("Select an end time after the start time.");
            eAlert.showAndWait();
            throw e;
        }
    }

    /** TODO 1 lambda expression used.
     * @param appointments Appointments hashmap
     * @return Filtered list of appointments in the next 7 days.
     */
    public static ObservableList<Appointment> nextWeeksAppointments(HashMap<Integer, Appointment>  appointments) {
        LocalDate currentDate = LocalDate.now(localZoneId);
        LocalDate nextWeekDate = currentDate.plus(7, ChronoUnit.DAYS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
        ObservableList<Appointment> filteredAppointments = appointments.values().stream()
                .filter(appointment -> {
                    LocalDateTime appointmentStart = LocalDateTime.parse(appointment.getStartLocal(), formatter);
                    LocalDateTime appointmentEnd = LocalDateTime.parse(appointment.getEndLocal(), formatter);
                    LocalDate appointmentStartDate = appointmentStart.toLocalDate();
                    LocalDate appointmentEndDate = appointmentEnd.toLocalDate();
                    return !appointmentEndDate.isBefore(currentDate) && !appointmentStartDate.isAfter(nextWeekDate);
                })
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return filteredAppointments;
    }

    /**
     * @param appointments Appointments hashmap
     * @return Filtered list of appointments in the next 31 days.
     */
    public static ObservableList<Appointment> nextMonthsAppointments(HashMap<Integer, Appointment> appointments) {
        LocalDate currentDate = LocalDate.now(localZoneId);
        LocalDate nextMonthDate = currentDate.plus(31, ChronoUnit.DAYS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
        ObservableList<Appointment> filteredAppointments = appointments.values().stream()
                .filter(appointment -> {
                    LocalDateTime appointmentStart = LocalDateTime.parse(appointment.getStartLocal(), formatter);
                    LocalDateTime appointmentEnd = LocalDateTime.parse(appointment.getEndLocal(), formatter);
                    LocalDate appointmentStartDate = appointmentStart.toLocalDate();
                    LocalDate appointmentEndDate = appointmentEnd.toLocalDate();
                    return !appointmentEndDate.isBefore(currentDate) && !appointmentStartDate.isAfter(nextMonthDate);
                })
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return filteredAppointments;
    }

    /**
     * @param startDateTime Local start date time.
     * @param endDateTime Local end date time.
     */
    public static void checkET(String startDateTime, String endDateTime) throws Exception {
        try {
            ZonedDateTime localStart = ZonedDateTime.parse(startDateTime, localFormatter);
            ZonedDateTime localEnd = ZonedDateTime.parse(endDateTime, localFormatter);
            ZonedDateTime etStart = localStart.withZoneSameInstant(etZoneId);
            ZonedDateTime etEnd = localEnd.withZoneSameInstant(etZoneId);
            boolean weekendFlag = Time.isWeekend(etStart);
            LocalTime etBusinessHoursStart = LocalTime.of(8, 0);
            LocalTime etBusinessHoursEnd = LocalTime.of(22, 0);
            if (etStart.toLocalTime().isBefore(etBusinessHoursStart) ||
                    etEnd.toLocalTime().isAfter(etBusinessHoursEnd) ||
                    etStart.toLocalTime().equals(etBusinessHoursEnd) ||
                    etEnd.toLocalTime().equals(etBusinessHoursStart) || weekendFlag) {
                throw new Exception();
            }
        } catch (Exception e) {
            eAlert.setContentText("Selected appointment times fall outside of ET business hours (8AM-10PM) on weekdays.");
            eAlert.showAndWait();
            throw e;
        }
    }

    /**
     * @param date Date to check for day name.
     * @return True if saturday or sunday, false otherwise.
     */
    private static boolean isWeekend(final ZonedDateTime date) {
        return date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY;
    }

    /**
     * @param startDateTime UTC start datetime.
     * @param endDateTime UTC end datetime.
     * @param id Customer ID.
     */
    public static void checkAppointmentConflict(String startDateTime, String endDateTime, Integer id) throws Exception {
        try {
            ZonedDateTime newStart = ZonedDateTime.parse(startDateTime, utcFormatter);
            ZonedDateTime newEnd = ZonedDateTime.parse(endDateTime, utcFormatter);
            for (Appointment appointment : AppointmentDAO.getAppointments().values()) {
                if (id == appointment.getCustomerId()) {
                    ZonedDateTime existingStart = ZonedDateTime.parse(appointment.getStart(), utcFormatter);
                    ZonedDateTime existingEnd = ZonedDateTime.parse(appointment.getEnd(), utcFormatter);
                    if (newEnd.isAfter(existingStart) && newStart.isBefore(existingEnd)) {
                        throw new Exception("Appointment time conflict.");
                    }
                }
            }
        } catch (Exception e) {
            eAlert.setContentText("Appointment time conflict.");
            eAlert.showAndWait();
            throw e;
        }
    }

    /**
     * @param appointments Cached appointment data in program.
     */
    public static void checkUpcomingAppointments(HashMap<Integer, Appointment> appointments) {
        final long thresholdMinutes = 15;
        LocalDateTime currentLocalTime = LocalDateTime.now(localZoneId);
        boolean flag = true;
        for (Appointment appointment : appointments.values()) {
            String startLocal = appointment.getStartLocal();
            LocalDateTime appointmentStartTime = LocalDateTime.parse(startLocal, localFormatter);
            long minutesUntilAppointment = ChronoUnit.MINUTES.between(currentLocalTime, appointmentStartTime);
            if (minutesUntilAppointment >= 0 && minutesUntilAppointment <= thresholdMinutes) {
                iAlert.setTitle("Upcoming Appointment");
                iAlert.setHeaderText("You have an upcoming appointment!");
                iAlert.setContentText("Appointment ID: " + appointment.getAppointmentId() + "\n"
                        + "Start Time: " + startLocal + "\n"
                        + "Description: " + appointment.getDescription());
                iAlert.showAndWait();
                flag = false;
            }
        }
        if (flag) {
            iAlert.setContentText("No appointment in 15 minutes.");
            iAlert.show();
        }
    }

    /**
     * @param datetime String with a date time.
     * @return date only.
     */
    public static LocalDate getDate(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
        try {
            LocalDateTime dateTime = LocalDateTime.parse(datetime, formatter);
            LocalDate date = dateTime.toLocalDate();
            return date;
        } catch (DateTimeParseException e) {
            System.out.println("Time DateTimeParseException: " + e.getMessage());
        }
        return null;
    }

    /**
     * @param datetime A date time string.
     * @return Time only
     */
    public static LocalTime getTime(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
        try {
            LocalDateTime dateTime = LocalDateTime.parse(datetime, formatter);
            return dateTime.toLocalTime();
        } catch (DateTimeParseException e) {
            System.out.println("Time DateTimeParseException: " + e.getMessage());
            return null;
        }
    }

    /**
     * @param datetime local datetime.
     * @return month name.
     */
    public static String convertDateToMonth(String datetime) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("uuuu-MM-dd HH:mm:ss");
            Date date = formatter.parse(datetime);
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
            String month = monthFormat.format(date);
            return month;
        } catch (ParseException e) {
            System.out.println("Time ParseException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
