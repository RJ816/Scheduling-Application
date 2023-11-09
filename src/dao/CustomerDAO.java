package dao;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CustomerDAO {
    private static Customer customer;
    private static HashMap<Integer, Customer> customers = new HashMap<>();
    private ArrayList<Integer> idList = new ArrayList<>();

    /** Add the same customer objects to the static hashmap.
     * @return Customers observable list.
     */
    public ObservableList<Customer> populateTable() {
        int id;
        String name;
        String address;
        String postalCode;
        String phone;
        String create;
        String createBy;
        String update;
        String updateBy;
        int divisionId;
        customers.clear();
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String query = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By, Division_ID FROM customers;";
        try {
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt("Customer_ID");
                name = rs.getString("Customer_Name");
                address = rs.getString("Address");
                postalCode = rs.getString("Postal_Code");
                phone = rs.getString("Phone");
                create = rs.getString("Create_Date");
                createBy = rs.getString("Created_By");
                update = rs.getString("Last_Update");
                updateBy = rs.getString("Last_Updated_By");
                divisionId = rs.getInt("Division_ID");
                Customer customer = new Customer(id, name, address, postalCode, phone, create, createBy, update,
                        updateBy, divisionId);
                customers.add(customer);
                this.customers.put(id, customer);
            }
        } catch (SQLException e) {
            System.out.println("CustomerDAO SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
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

    /**
     * @param customer Customer object with attributes to add to record.
     */
    public void addCustomerRecord (Customer customer) {
        //SQL injection risk.
        String query = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, " +
                "Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, customer.getId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPostalCode());
            ps.setString(5, customer.getPhone());
            ps.setString(6, customer.getCreate());
            ps.setString(7, customer.getCreateBy());
            ps.setString(8, customer.getUpdate());
            ps.setString(9, customer.getUpdateBy());
            ps.setInt(10, customer.getDivisionId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("CustomerDAO SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param customer Customer object with attributes to update a record.
     */
    public void updateCustomerRecord (Customer customer) {
        //SQL injection risk.
        String query = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Last_Update=?, " +
                "Last_Updated_By=?, Division_ID=? WHERE Customer_ID=?;";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getPostalCode());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getUpdate());
            ps.setString(6, customer.getUpdateBy());
            ps.setInt(7, customer.getDivisionId());
            ps.setInt(8, customer.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("CustomerDAO SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param customer Customer object to set static customer attribute for reference throughout the program.
     */
    public void setCustomer(Customer customer) {
        CustomerDAO.customer = customer;
    }

    /**
     * @return Customer dao static customer object.
     */
    public Customer getCustomer() {
        return customer;
    }

    /** TODO Contains 1 method reference (lambda expression).
     * @return Array of customer names.
     */
    public String[] getCustomerNames() {
        return customers.values().stream().map(Customer::getName).toArray(String[]::new);
    }

    /** TODO Contains 1 method reference (lambda expression) and 1 lambda expression.
     * @param name Customer name.
     * @return Customer ID.
     */
    public Integer getCustomerIdByCustomerName(String name) {
        Customer[] customerMatch =
                customers.values().stream().filter(cObj -> cObj.getName().equals(name)).toArray(Customer[]::new);
        Integer id = customerMatch[0].getId(); //TODO rewrite.
        return id;
    }

    /** TODO Contains 1 lambda expression.
     * @param id Customer ID.
     * @return Customer name.
     */
    public String getCustomerNameById(Integer id) {
        Customer[] customerMatch =
                customers.values().stream().filter(cObj -> cObj.getId() == (id)).toArray(Customer[]::new);
        String name = customerMatch[0].getName(); //TODO rewrite.
        return name;
    }

    /**
     * @param id Customer ID.
     */
    public void deleteCustomerRecord(Integer id) {
        //SQL injection risk.
        String query = "DELETE FROM customers WHERE Customer_ID=?;";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("CustomerDAO SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Customer Id list.
     */
    private ArrayList<Integer> getIdList(){
        int id;
        ArrayList<Integer> idList = new ArrayList<>();
        String query = "SELECT Customer_ID FROM customers;";
        try {
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt("Customer_ID");
                idList.add(id);
            }
        } catch (SQLException e) {
            System.out.println("CustomerDAO SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idList;
    }
}
