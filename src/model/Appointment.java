package model;

import dao.AppointmentDAO;
import helper.Time;

import java.util.Objects;

/**
 * Class to represent appointment objects.
 */
public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private String start;
    private String end;
    private String startLocal;
    private String endLocal;
    private String create;
    private String createBy;
    private String update;
    private String updateBy;
    private int customerId;
    private int userId;
    private int contactId;
    private AppointmentDAO dao = new AppointmentDAO();
    private String contactName;

    /**
     * @param appointmentId Appointment ID.
     * @param title Appointment title.
     * @param description Appointment description.
     * @param location Appointment location.
     * @param type Appointment type.
     * @param start Appointment start timestamp.
     * @param end Appointment end timestamp.
     * @param create Appointment record create timestamp.
     * @param createBy Appointment record creator.
     * @param update Appointment record update timestamp.
     * @param updateBy Appointment record updater.
     * @param customerId Appointment customer ID.
     * @param userId Appointment user ID.
     * @param contactId Appointment contact ID.
     */
    public Appointment(int appointmentId, String title, String description, String location, String type, String start,
                       String end, String create, String createBy, String update, String updateBy, int customerId,
                       int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.create = create;
        this.createBy = createBy;
        this.update = update;
        this.updateBy = updateBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = dao.getContactName(contactId);
    }

    /**
     * @return Appointment ID.
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * @return Appointment title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return Appointment Description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Appointment location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return Contact name.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @return Appointment type.
     */
    public String getType() {
        return type;
    }

    /**
     * @return Appointment local start datetime.
     */
    public String getStart() {
        return start;
    }

    /**
     * @return Appointment local end datetime.
     */
    public String getEnd() {
        return end;
    }

    /**
     * @return Customer Id.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @return User Id.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return Appointment create datetime.
     */
    public String getCreate() {
        return create;
    }

    /**
     * @return Appointment record creator.
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @return Appointment update datetime.
     */
    public String getUpdate() {
        return update;
    }

    /**
     * @return Appointment record updater.
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * @return Appointment contact ID.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * @param startUTC Start time in UTC.
     */
    public void setStartLocal(String startUTC) {
        this.startLocal = Time.convertUTCToLocal(startUTC);
    }

    /**
     * @param endUTC End time in UTC.
     */
    public void setEndLocal(String endUTC) {
        this.endLocal = Time.convertUTCToLocal(endUTC);
    }

    /**
     * @return Start time local.
     */
    public String getStartLocal() {
        return startLocal;
    }

    /**
     * @return End time local.
     */
    public String getEndLocal() {
        return endLocal;
    }

    /**
     * @param other Appointment object.
     * @return true if equals based on selective attributes, false if not.
     */
    public boolean equalsSelective(Appointment other) {
        return Objects.equals(this.title, other.title) &&
                Objects.equals(this.description, other.description) &&
                Objects.equals(this.location, other.location) &&
                Objects.equals(this.type, other.type) &&
                Objects.equals(this.start, other.start) &&
                Objects.equals(this.end, other.end) &&
                Objects.equals(this.customerId, other.customerId) &&
                Objects.equals(this.userId, other.userId) &&
                Objects.equals(this.contactId, other.contactId);
    }

    /**
     * @return Local month name.
     */
    public String getMonth() {
        return Time.convertDateToMonth(startLocal);
    }
}
