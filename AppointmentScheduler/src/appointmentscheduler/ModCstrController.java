/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appointmentscheduler;

import static appointmentscheduler.CustomerController.cstrForm;
import static appointmentscheduler.CustomerController.modCstr;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ModCstrController implements Initializable {
    public static Stage cstrForm;
    public Customer modCstr;
    public ObservableList<Customer> cstrList = FXCollections.observableArrayList();
    public TableView<Customer> cstrTable;
    @FXML public TableColumn<Customer, Integer> id = new TableColumn("ID");
    @FXML public TableColumn<Customer, String> name = new TableColumn("Customer Name");
    @FXML public TextField nameText;
    @FXML public TextField addressText;
    @FXML public TextField cityText;
    @FXML public TextField countryText;
    @FXML public TextField zipText;
    @FXML public TextField phoneText;
    @FXML public Button deleteButton;
    @FXML public Button updateButton;
    @FXML public Button cancelButton;
    @FXML public Label formLabel;
    @FXML public Label cstrName;    
    
    public ObservableList<String> cityList;
    @FXML public ChoiceBox cityBox;
    
    public ObservableList<String> countryList = FXCollections.observableArrayList();
    @FXML public ChoiceBox countryBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cityList = Data.cityNames();
        cityBox.setItems(cityList);
        
        countryList = Data.countryNames();
        countryBox.setItems(countryList);
    }    
    
    @FXML
    public void updateCstr() throws SQLException {
        if (!validateInput()) {
            return;
        }
        Customer temp = modCstr;
        Address address = saveAddress();
        address.setAddressId(modCstr.getAddressID());
        Data.updateAddress(address, cityBox.getValue().toString());
        temp.setName(nameText.getText());
        Data.updateCstr(temp);
        Data.getCstrs();      
        closeWindow();
    }
    
        public Address saveAddress() throws SQLException {
        Address temp = new Address();
        temp.setAddress(addressText.getText());
        temp.setZip(zipText.getText());
        temp.setPhone(phoneText.getText());
        return temp;
    }
    
    @FXML
    public void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
    public boolean validateInput() {
        if (nameText.getLength() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter a name");
            alert.showAndWait();
            return false;
        }
        
        if (addressText.getLength() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter an address");
            alert.showAndWait();
            return false;
        }
        
        if (zipText.getLength() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter a zip code");
            alert.showAndWait();
            return false;
        }
        
        if (phoneText.getLength() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter a phone number");
            alert.showAndWait();
            return false;
        }
        
        if (cityBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter a city");
            alert.showAndWait();
            return false;
        }
        
        if (countryBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter a country");
            alert.showAndWait();
            return false;
        }
        
        return true;
    }
    
    public void setInitData(Customer cstr) {
        this.modCstr = cstr;
        nameText.setText(modCstr.getName());
        addressText.setText(modCstr.getAddress().getAddress());        
        zipText.setText(modCstr.getAddress().getZip());
        phoneText.setText(modCstr.getAddress().getPhone());
        countryBox.setValue(modCstr.getAddress().getCountry());
        cityBox.setValue(modCstr.getAddress().getCity());
    }
    
}
