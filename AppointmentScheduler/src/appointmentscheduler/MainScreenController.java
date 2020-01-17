
package appointmentscheduler;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainScreenController implements Initializable {
    @FXML public TableView<Appointment> apptTable;
    @FXML public TableColumn<Appointment, String> title = new TableColumn("Title");
    @FXML public TableColumn<Appointment, String> location = new TableColumn("Location");
    @FXML public TableColumn<Appointment, String> type = new TableColumn("Type");
    @FXML public TableColumn<Appointment, String> desc = new TableColumn("Description");
    @FXML public TableColumn<Appointment, LocalDate> date = new TableColumn("Date");
    @FXML public TableColumn<Appointment, LocalTime> start = new TableColumn("Start");
    @FXML public TableColumn<Appointment, LocalTime> end = new TableColumn("End");
    
    @FXML public ToggleGroup filterGroup = new ToggleGroup();;
    @FXML public RadioButton weekButton;
    @FXML public RadioButton monthButton;    
    @FXML public DatePicker dateBox = new DatePicker();
    @FXML public Button modApptButton;
    @FXML public Button filterButton;
    @FXML public TextArea reportText;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        location.setCellValueFactory(new PropertyValueFactory<>("Location"));
        type.setCellValueFactory(new PropertyValueFactory<>("Type"));
        desc.setCellValueFactory(new PropertyValueFactory<>("Description"));
        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        start.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        end.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
        apptTable.setItems(Data.apptList);
        
        weekButton.setToggleGroup(filterGroup);
        monthButton.setToggleGroup(filterGroup);
        
        apptWarning();
    }
    
    @FXML 
    public void deleteButton() throws SQLException {
        int id = apptTable.getSelectionModel().getSelectedItem().getAppointmentID();
        Data.delete("appointment", "appointmentId = " + id);
        Data.refreshAppts();
    }
    
    @FXML
    public void newAppt() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addApptFXML.fxml"));
        Parent pScene = loader.load();        
        Scene scene = new Scene(pScene);
        Stage stage = new Stage();
        stage.setTitle("New Appointment");        
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void tableSelected() {
        modApptButton.setDisable(false);
    }
    
    @FXML
    public void dateSelected() {
        filterButton.setDisable(false);
    }
    
    @FXML
    public void modAppt() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModApptFXML.fxml"));
        Parent pScene = loader.load();
        ModApptController apptControl = loader.getController();
        apptControl.setInitData(apptTable.getSelectionModel().getSelectedItem());
        
        Scene scene = new Scene(pScene);
        Stage stage = new Stage();
        stage.setTitle("Modify Appointment");        
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    public void cstrManager() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerFXML.fxml"));
        Parent pScene = loader.load();
        CustomerController cstrControl = loader.getController();
        Scene scene = new Scene(pScene);
        Stage stage = new Stage();
        stage.setTitle("Customer Management");        
        stage.setScene(scene);
        cstrControl.loadTable();
        stage.show();
    }
    
    public void apptWarning() {
        if (Data.apptWarning() != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Appointment starting in " + Data.apptWarning() + " minutes.");
            alert.showAndWait();
        }
    }
    
    @FXML
    public void filterAppts() {        
        if (weekButton.isSelected()) {
            Data.apptsByWeek(dateBox.getValue());
            apptTable.setItems(Data.apptsByWeek(dateBox.getValue()));
        }
        if (monthButton.isSelected()) {
            apptTable.setItems(Data.apptsByMonth(dateBox.getValue()));
        }
    }
    
    @FXML
    public void resetTable() throws SQLException {
        Data.refreshAppts();
        apptTable.setItems(Data.apptList);
    }
    
    @FXML 
    public void typeCount() throws SQLException {
        List<Integer> types = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            int num = Data.typesByMonth(i);
            types.add(num);
        }
        reportText.setText("Number of distinct types of appointment by month");
        reportText.appendText("\n_______________________________________________\n");
        for (int i = 0; i < types.size(); i++) {
            reportText.appendText("\n");
            reportText.appendText(monthNames(i, types.get(i)));
        }
    }
    
    @FXML
    public void apptsByConsultant() throws SQLException {
        reportText.setText("Consultant Schedules \n");
        reportText.appendText("\n_______________________________________________\n");
        for (User i : Data.userList) {
            reportText.appendText(Data.apptsByConsultant(i.getName()));
        }
    }
    
    @FXML
    public void dailyAppts() {
        reportText.setText("All appointments for today\n");
        reportText.appendText("\n_______________________________________________\n");
        reportText.appendText(Data.getDailyAppts());
    }
    
    public String monthNames(int index, int count) {
        if (index == 0) {
            return "January: " + count;
        }
        if (index == 1) {
            return "Febuary: " + count;
        }
        if (index == 2) {
            return "March: " + count;
        }
        if (index == 3) {
            return "April: " + count;
        }
        if (index == 4) {
            return "May: " + count;
        }
        if (index == 5) {
            return "June: " + count;
        }
        if (index == 6) {
            return "July: " + count;
        }
        if (index == 7) {
            return "August: " + count;
        }
        if (index == 8) {
            return "September: " + count;
        }
        if (index == 9) {
            return "October: " + count;
        }
        if (index == 10) {
            return "November: " + count;
        }
        if (index == 11) {
            return "December: " + count;
        }
        return "No appointments booked";
    }
}
