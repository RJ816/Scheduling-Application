package model;

/**
 * Class to represent country objects.
 */
public class Country {
    private int id;
    private String country;

    /**
     * @param id Country id attribute.
     * @param country Country name attribute.
     */
    public Country(int id, String country) {
        this.id = id;
        this.country = country;
    }

    /**
     * @return Country name.
     */
    public String getName() {
        return country;
    }

    /**
     * @return Country Id.
     */
    public int getId() {
        return id;
    }
}
