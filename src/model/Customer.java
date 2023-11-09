package model;

import dao.LocationDAO;

import java.util.Objects;

/**
 * Class to represent customer objects.
 */
public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String create;
    private String createBy;
    private String update;
    private String updateBy;
    private int divisionId;
    private LocationDAO dao = new LocationDAO();
    private String division;
    private String country;

    /**
     * @param id Customer id attribute.
     * @param name Customer name attribute.
     * @param address Customer address attribute.
     * @param postalCode Customer postal code attribute.
     * @param phone Customer phone number attribute.
     * @param create Timestamp for when the customer record was created.
     * @param createBy User who created the customer record.
     * @param update Timestamp for when the customer record was updated.
     * @param updateBy User who updated the customer record.
     * @param divisionId Division ID attribute.
     */
    public Customer(int id, String name, String address, String postalCode, String phone, String create,
                    String createBy, String update, String updateBy, int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.create = create;
        this.createBy = createBy;
        this.update = update;
        this.updateBy = updateBy;
        this.divisionId = divisionId;
        division = dao.getDivisionName(divisionId);
        Integer countryId = dao.getCountryIdByDivisionName(division);
        country = dao.getCountryName(countryId);
    }

    /**
     * @return Customer id.
     */
    public int getId() {
        return id;
    }

    /**
     * @ return Customer name.
     */
    public String getName() {
        return name;
    }

    /**
     * @ return Customer address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * @ return Customer postal code.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @return Customer record create timestamp.
     */
    public String getCreate() {
        return create;
    }

    /**
     * @return Customer record creator.
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @return Customer record update timestamp.
     */
    public String getUpdate() {
        return update;
    }

    /**
     * @return Customer record updater.
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * @ return Customer phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @ return Customer Division Id.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * @return Division name.
     */
    public String getDivision() {
        return division;
    }

    /**
     * @return Country name.
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param other Customer object.
     * @return Compare two customer objects excluding the creator,updater, and datetime attributes.
     */
    public boolean equalsSelective(Customer other) {
        return Objects.equals(this.name, other.name) &&
                Objects.equals(this.address, other.address) &&
                Objects.equals(this.postalCode, other.postalCode) &&
                Objects.equals(this.phone, other.phone) &&
                this.divisionId == other.divisionId;
    }
}
