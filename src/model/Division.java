package model;

/**
 * Class to represent first level division objects.
 */
public class Division {
    private int divisionId;
    private String division;
    private int countryId;
    /**
     * @param divisionId Division id attribute.
     * @param division Division name attribute.
     * @param countryId Country id attribute.
     */
    public Division(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }
    /**
     * @return Division name.
     */
    public String getName() {
        return division;
    }
    /**
     * @return Division ID.
     */
    public int getDivisionId() {
        return divisionId;
    }
    /**
     * @return Country ID.
     */
    public int getCountryId() {
        return countryId;
    }
}
