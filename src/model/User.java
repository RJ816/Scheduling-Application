package model;

/**
 * Abstract class for user.
 */
public class User {
    private int id;
    private String name;

    /**
     * @param id User id.
     * @param name User name.
     */
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @param name User name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return User name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return User ID.
     */
    public int getId() {
        return id;
    }
}
