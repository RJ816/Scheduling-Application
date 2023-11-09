package model;

/**
 * Class to represent Contact objects.
 */
public class Contact {
    private int id;
    private String name;
    private String email;

    /**
     * @param id Contact ID.
     * @param name Contact name.
     * @param email Contact email.
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * @return Contact name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Contact ID.
     */
    public int getId() {
        return id;
    }
}
