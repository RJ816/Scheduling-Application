package helper;

import dao.LocationDAO;
import javafx.scene.control.Alert;

import java.util.Arrays;

/**
 * Class to help format data.
 */
public class Format {
    private LocationDAO dao = new LocationDAO();

    /** TODO 2 method references (lambda expressions) used.
     * @param address Address format to be checked.
     */
    public void checkAddress(String address) {
        String [] countries = dao.getCountryNames();
        String [] divisions = dao.getDivisionNames();
        boolean cFlag = Arrays.stream(countries).anyMatch(address::contains);
        boolean dFlag = Arrays.stream(divisions).anyMatch(address::contains);
        if (cFlag || dFlag) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Do not include division/country in address.\n" +
                    "Use the following examples to format the address.\n" +
                    "U.S. address: 123 ABC Street, White Plains\n" +
                    "Canadian address: 123 ABC Street, Newmarket\n" +
                    "UK address: 123 ABC Street, Greenwich, London");
            alert.showAndWait();
        } else;
    }
}
