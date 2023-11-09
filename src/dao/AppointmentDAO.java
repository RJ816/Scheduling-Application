package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Appointment;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Data access object for appointment class.
 */
public class AppointmentDAO {
    private static HashMap<Integer, Contact> contacts = new HashMap<>();
    private static HashMap<Integer, Appointment> appointments = new HashMap<>();
    private static Appointment appointment;
    private ArrayList<Integer> idList = new ArrayList<>();
    private Alert alert = new Alert(Alert.AlertType.ERROR);

    /** Set appointments hashmap.
     * @return Appointments observable list.
     */
    public ObservableList<Appointment> populateTable() {
        int appointmentId;
        String title;
        String description;
        String location;
        String type;
        String start;
        String end;
        String create;
        String createBy;
        String update;
        String updateBy;
        int customerId;
        int userId;
        int contactId;
        appointments.clear();
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, " +
                "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID FROM appointments;";
        try {
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                appointmentId = rs.getInt("Appointment_ID");
                title = rs.getString("Title");
                description = rs.getString("Description");
                location = rs.getString("Location");
                type = rs.getString("Type");
                start = rs.getString("Start");
                end = rs.getString("End");
                create = rs.getString("Create_Date");
                createBy = rs.getString("Created_By");
                update = rs.getString("Last_Update");
                updateBy = rs.getString("Last_Updated_By");
                customerId = rs.getInt("Customer_ID");
                userId = rs.getInt("User_ID");
                contactId = rs.getInt("Contact_ID");
                Appointment appointment = new Appointment(appointmentId, title, description, location, type, start,
                        end, create, createBy, update, updateBy, customerId, userId, contactId);
                appointment.setStartLocal(start);
                appointment.setEndLocal(end);
                appointments.add(appointment);
                this.appointments.put(appointmentId, appointment);
            }
        } catch (SQLException e) {
            System.out.println("AppointmentDAO SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

    /**
     * Populate contact database data into a hashmap.
     */
    public void setContactsHashMap() {
        int id;
        String name;
        String email;
        contacts.clear();
        String query = "SELECT Contact_ID, Contact_Name, Email FROM contacts;";
        try {
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt("Contact_ID");
                name = rs.getString("Contact_Name");
                email = rs.getString("Email");
                Contact contact = new Contact(id, name, email);
                contacts.put(id, contact);
            }
        } catch (SQLException e) {
            System.out.println("ContactDAO SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Populate appointment database data into a hashmap.
     */
    public void setAppointmentsHashMap() {
        int appointmentId;
        String title;
        String description;
        String location;
        String type;
        String start;
        String end;
        String create;
        String createBy;
        String update;
        String updateBy;
        int customerId;
        int userId;
        int contactId;
        appointments.clear();
        String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, " +
                "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID FROM appointments;";
        try {
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                appointmentId = rs.getInt("Appointment_ID");
                title = rs.getString("Title");
                description = rs.getString("Description");
                location = rs.getString("Location");
                type = rs.getString("Type");
                start = rs.getString("Start");
                end = rs.getString("End");
                create = rs.getString("Create_Date");
                createBy = rs.getString("Created_By");
                update = rs.getString("Last_Update");
                updateBy = rs.getString("Last_Updated_By");
                customerId = rs.getInt("Customer_ID");
                userId = rs.getInt("User_ID");
                contactId = rs.getInt("Contact_ID");
                Appointment appointment = new Appointment(appointmentId, title, description, location, type, start,
                        end, create, createBy, update, updateBy, customerId, userId, contactId);
                appointment.setStartLocal(start);
                appointment.setEndLocal(end);
                appointments.put(appointmentId, appointment);
            }
        } catch (SQLException e) {
            System.out.println("AppointmentDAO SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param id Contact ID.
     * @return Contact name.
     */
    public String getContactName(int id) {
        return contacts.get(id).getName();
    }

    /**
     * @return Unique customer id for add customer form.
     */
    public Integer getId() {
        idList = getIdList();
        Collections.sort(idList);
        int checkId = 1;
        for (int number : idList) {
            if (number == checkId) {
                checkId++;
            } else {
                break;
            }
        }
        return checkId;
    }

    /** TODO Contains 1 method reference (lambda expression).
     * @return Array of contact names.
     */
    public String[] getContactNames() {
        return contacts.values().stream().map(Contact::getName).toArray(String[]::new);
    }

    /** TODO Contains 1 method reference (lambda expression) and 1 lambda expression.
     * @param name Contact name.
     * @return Contact ID.
     */
    public Integer getContactIdByContactName(String name) {
        Contact[] contactMatch =
                contacts.values().stream().filter(cObj -> cObj.getName().equals(name)).toArray(Contact[]::new);
        Integer id = contactMatch[0].getId(); //TODO rewrite.
        return id;
    }

    /**
     * Add an Appointment record to the database.
     * @param appointment Appointment object with attributes to add to the record.
     */
    public void addAppointmentRecord(Appointment appointment) {
        // SQL injection risk.
        String query = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, " +
                "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES " +
                "(?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, appointment.getAppointmentId());
            ps.setString(2, appointment.getTitle());
            ps.setString(3, appointment.getDescription());
            ps.setString(4, appointment.getLocation());
            ps.setString(5, appointment.getType());
            ps.setString(6, appointment.getStart());
            ps.setString(7, appointment.getEnd());
            ps.setString(8, appointment.getCreate());
            ps.setString(9, appointment.getCreateBy());
            ps.setString(10, appointment.getUpdate());
            ps.setString(11, appointment.getUpdateBy());
            ps.setInt(12, appointment.getCustomerId());
            ps.setInt(13, appointment.getUserId());
            ps.setInt(14, appointment.getContactId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("AppointmentDAO SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param appointment Appointment object with attributes to update in the record.
     */
    public void updateAppointmentRecord(Appointment appointment) {
        // SQL injection risk.
        String query = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, " +
                "Last_Update=?, Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?;";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ps.setString(5, appointment.getStart());
            ps.setString(6, appointment.getEnd());
            ps.setString(7, appointment.getUpdate());
            ps.setString(8, appointment.getUpdateBy());
            ps.setInt(9, appointment.getCustomerId());
            ps.setInt(10, appointment.getUserId());
            ps.setInt(11, appointment.getContactId());
            ps.setInt(12, appointment.getAppointmentId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("AppointmentDAO SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param id Customer ID.
     * @return False if appointment record with customer ID exist, true if not.
     */
    public boolean checkAppointments(Integer id) {
        String query = "SELECT COUNT(*) FROM appointments WHERE Customer_ID = ?;";
        try {
            //SQL injection risk.
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            int rowCount = 0;
            if (rs.next()) {
                rowCount = rs.getInt(1);
                if (rowCount != 0) {
                    alert.setContentText("All customer appointments must be deleted first.");
                    alert.showAndWait();
                    return false;
                } else {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("AppointmentDAO SQLException:" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param id Appointment ID.
     */
    public void deleteAppointmentRecord(Integer id) {
        //SQL injection risk.
        String query = "DELETE FROM appointments WHERE Appointment_ID=?;";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("AppointmentDAO SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return appointments hashmap.
     */
    public static HashMap<Integer, Appointment> getAppointments() {
        return appointments;
    }

    /**
     * @param appointmentId Appointment Id.
     */
    public void deleteUpdateAppointment(int appointmentId) {
        appointments.remove(appointmentId);
    }

    /**
     * @param appointment Appointment object.
     */
    public void setAppointment(Appointment appointment) {
        AppointmentDAO.appointment = appointment;
    }

    /**
     * @return Static appointment object.
     */
    public static Appointment getAppointment() {
        return appointment;
    }

    /** TODO Contains 1 method reference (lambda expression).
     * @return Array of types.
     */
    public String[] getTypes() {
        return appointments.values().stream().map(Appointment::getType).toArray(String[]::new);
    }

    /** TODO Contains 2 method reference (lambda expression).
     * @return Array of months.
     */
    public String[] getMonths() {
        Set<String> uniqueMonths = new HashSet<>();
        appointments.values().stream().map(Appointment::getMonth).forEach(uniqueMonths::add);
        String[] uniqueMonthsArray = uniqueMonths.toArray(new String[0]);
        return uniqueMonthsArray;
    }

    /**
     * @param type Appointment type filter.
     * @param month Appointment month filter.
     * @return Sum of appointments after filters.
     */
    public int countAppointments(String type, String month) {
        int count = 0;
        // SQL injection risk.
        String query = "SELECT COUNT(*) FROM appointments WHERE Type = ? AND MONTHNAME(Start) = ?;";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setString(1, type);
            ps.setString(2, month);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("AppointmentDAO SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * @param name Contact name to filter by.
     * @return ObservableList of appointments for the contact.
     */
    public ObservableList<Appointment> getContactAppointments(String name) {
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
        Integer contactId = getContactIdByContactName(name);
        String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, " +
                "Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID " +
                "FROM appointments WHERE Contact_ID = ?;";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, contactId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                String start = rs.getString("Start");
                String end = rs.getString("End");
                String create = rs.getString("Create_Date");
                String createBy = rs.getString("Created_By");
                String update = rs.getString("Last_Update");
                String updateBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                Appointment appointment = new Appointment(appointmentId, title, description, location, type,
                        start, end, create, createBy, update, updateBy, customerId, userId, contactId);
                appointment.setStartLocal(start);
                appointment.setEndLocal(end);
                contactAppointments.add(appointment);
            }
        } catch (SQLException e) {
            System.out.println("AppointmentDAO SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contactAppointments;
    }

    /**
     * @return Array of unique appointment locations.
     */
    public String[] getLocations() {
        Set<String> uniqueLocations = new HashSet<>();
        for (Appointment appointment : appointments.values()) {
            uniqueLocations.add(appointment.getLocation());
        }
        return uniqueLocations.toArray(new String[0]);
    }

    /**
     * @param location Appointment location filter.
     * @return Sum of appointments after the location filter.
     */
    public int countLocations(String location) {
        int count = 0;
        // SQL injection risk.
        String query = "SELECT COUNT(*) FROM appointments WHERE Location = ?;";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setString(1, location);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("AppointmentDAO SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * @return Appointment Id list.
     */
    private ArrayList<Integer> getIdList(){
        int id;
        ArrayList<Integer> idList = new ArrayList<>();
        String query = "SELECT Appointment_ID FROM appointments;";
        try {
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt("Appointment_ID");
                idList.add(id);
            }
        } catch (SQLException e) {
            System.out.println("AppointmentDAO SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idList;
    }
}
