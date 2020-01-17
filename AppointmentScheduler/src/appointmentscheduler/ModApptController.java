package appointmentscheduler;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ModApptController implements Initializable {
    private Appointment appt;
    
    public ObservableList<Customer> cstrList = FXCollections.observableArrayList();
    public TableView<Customer> cstrTable;
    @FXML public TableColumn<Customer, Integer> id = new TableColumn("ID");
    @FXML public TableColumn<Customer, String> name = new TableColumn("Customer Name");
    @FXML public TextField titleText;
    @FXML public TextField typeText;
    @FXML public TextField descText;
    @FXML public TextField locText;
    @FXML public TextField dateText;
    @FXML public TextField startText;
    @FXML public TextField endText;
    @FXML public Button cancelButton;
    @FXML public Button saveButton;
    @FXML public DatePicker dateBox = new DatePicker();
    
    public ObservableList<LocalTime> startList;
    @FXML public ChoiceBox startBox;
    
    public ObservableList<LocalTime> endList;
    @FXML public ChoiceBox endBox;
    
    public ObservableList<String> contactList;
    @FXML public ChoiceBox contactBox;
    
    public ObservableList<String> locList;
    @FXML public ChoiceBox locBox;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       saveButton.setDisable(true);
       id.setCellValueFactory(new PropertyValueFactory<> ("ID"));
       name.setCellValueFactory(new PropertyValueFactory<> ("Name"));
       cstrTable.setItems(Data.cstrList);  
       
       startList = FXCollections.observableArrayList();
       startBox.setItems(startList);
       startBox.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) ->    {
           onTimeSelect((LocalTime)newV);
       }); 
    
       
       endList = FXCollections.observableArrayList();
       endBox.setItems(endList);
       
       contactList = Data.contacts();
       contactBox.setItems(contactList);
       
       locList = Data.cityNames();
       locBox.setItems(locList);
    } 
  
  

    public void saveAppt() throws SQLException {
        validateInput();
        appt.setCustomerID(cstrTable.getSelectionModel().getSelectedItem().getID());
        appt.setTitle(titleText.getText());
        appt.setType(typeText.getText());
        appt.setDescription(descText.getText());
        appt.setLocation(locBox.getValue().toString());
        appt.setContact(contactBox.getValue().toString());
        appt.setStartRaw(Data.clock.toUtc(dateBox.getValue().toString(), startBox.getValue().toString()));
        appt.setEndRaw(Data.clock.toUtc(dateBox.getValue().toString(), endBox.getValue().toString()));
        appt.setLastUpdate(Data.clock.now());
        appt.setLastUpdateBy(Data.user.getName());
        Data.updateAppt(appt);
    }
    
    @FXML
    public void saveButton() throws SQLException {   
        if (!validateInput()) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainScreenFXML.fxml"));
            Parent root = (Parent) loader.load();
            MainScreenController mainController = loader.getController();
            saveAppt();
            Data.refreshAppts();
            closeWindow();
          }  catch (IOException ex) {
                System.err.println(ex);
          }
    }
    
    @FXML
    public void tableSelected() {
        saveButton.setDisable(false);
    }
    
    @FXML
    public void closeWindow() throws SQLException {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void onDateSelect() {
        startList.clear();
        startList = Data.getStarts(dateBox.getValue());
        startBox.setItems(startList);
    }
    
    @FXML
    public void onTimeSelect (LocalTime start) {
        endList.clear();
        endList = Data.getEnds(dateBox.getValue(), (LocalTime)start);
        endBox.setItems(endList);
    }
    
    public boolean validateInput() {
        if (titleText.getLength() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter a title");
            alert.showAndWait();
            return false;
        }
        
        if (typeText.getLength() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter an appointment type");
            alert.showAndWait();
            return false;
        }
        
        if (descText.getLength() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please enter a description");
            alert.showAndWait();
            return false;
        }
        
        if (locBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please choose a location");
            alert.showAndWait();
            return false;
        }
        
        if (contactBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please choose a contact");
            alert.showAndWait();
            return false;
        }
        
        if (dateBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please choose a date");
            alert.showAndWait();
            return false;
        }
        
        return true;
    }    
    
    public void setInitData(Appointment appt) {
        this.appt = appt;
        titleText.setText(appt.getTitle());
        typeText.setText(appt.getType());
        descText.setText(appt.getDescription());
        locBox.setValue(appt.getLocation());
        contactBox.setValue(appt.getContact());
        dateBox.setValue(appt.getDate());
        onDateSelect();
    }
    
}
