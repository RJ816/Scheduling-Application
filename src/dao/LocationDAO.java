package dao;

import helper.JDBC;
import model.Country;
import model.Division;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Data access object for division and country class.
 */
public class LocationDAO {
    private static HashMap<Integer, Division> divisions = new HashMap<>();
    private static HashMap<Integer, Country> countries = new HashMap<>();

    /**
     * Populate division database data into a hashmap.
     */
    public void setDivisionsHashMap(){
        int id;
        String name;
        int countryId;
        divisions.clear();
        String query = "SELECT Division_ID, Division, Country_ID FROM first_level_divisions;";
        try {
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt("Division_ID");
                name = rs.getString("Division");
                countryId = rs.getInt("Country_ID");
                Division division = new Division(id, name, countryId);
                divisions.put(id, division);
            }
        } catch (SQLException e) {
            System.out.println("LocationDAO SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Populate country database data into a hashmap.
     */
    public void setCountriesHashMap() {
        int id;
        String name;
        String query = "SELECT Country_ID, Country FROM countries;";
        countries.clear();
        try {
            Statement statement = JDBC.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                id = rs.getInt("Country_ID");
                name = rs.getString("Country");
                Country country = new Country(id, name);
                countries.put(id, country);;
            }
        } catch (SQLException e) {
            System.out.println("LocationDAO SQLException: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param id Division id.
     * @return Division name.
     */
    public String getDivisionName(Integer id) {
        return divisions.get(id).getName();
    }

    /**
     * @param id Country id.
     * @return Country name.
     */
    public String getCountryName(Integer id) {
        return countries.get(id).getName();
    }

    /** TODO Contains 1 method reference (lambda expression).
     * @return Array of country names.
     */
    public String[] getCountryNames() {
        return countries.values().stream().map(Country::getName).toArray(String[]::new);
    }

    /** TODO Contains 1 method reference (lambda expression).
     * @return Array of division names.
     */
    public String[] getDivisionNames() {
        return divisions.values().stream().map(Division::getName).toArray(String[]::new);
    }

    /** TODO Contains 1 method reference (lambda expression) and 1 lambda expression.
     * @param division Division name.
     * @return Division ID.
     */
    public Integer getDivisionId(String division) {
        Division[] divisionMatch =
                divisions.values().stream().filter(divObj -> divObj.getName().equals(division)).toArray(Division[]::new);
        Integer id = divisionMatch[0].getDivisionId(); //TODO rewrite.
        return id;
    }

    /** TODO Contains 1 method reference (lambda expression) and 1 lambda expression.
     * @param division Division name.
     * @return Country ID.
     */
    public Integer getCountryIdByDivisionName(String division) {
        Division[] divisionMatch =
                divisions.values().stream().filter(divObj -> divObj.getName().equals(division)).toArray(Division[]::new);
        Integer id = divisionMatch[0].getCountryId(); //TODO rewrite.
        return id;
    }

    /** TODO Contains 1 method reference (lambda expression) and 1 lambda expression.
     * @param country Country name.
     * @return Country ID.
     */
    public Integer getCountryIdByCountryName(String country) {
        Country[] countryMatch =
                countries.values().stream().filter(countObj -> countObj.getName().equals(country)).toArray(Country[]::new);
        Integer id = countryMatch[0].getId(); //TODO rewrite.
        return id;
    }

    /** TODO Contains 1 lambda expression.
     * @param id Country Id to filter by.
     * @return Divisions filtered by country.
     */
    public String[] getFilteredDivisions(Integer id) {
        String [] filteredByCountryId =
                divisions.values().stream().filter(divObj -> divObj.getCountryId() == id).map(Division::getName).toArray(String[]::new);
        return filteredByCountryId;
    }
}
