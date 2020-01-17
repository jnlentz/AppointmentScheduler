
package appointmentscheduler;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {    
    private Data db;
    public ResourceBundle rb;
    
    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private TextField usernameText;
    @FXML private TextField passwordText;
    @FXML private Button loginButton;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        this.rb = rb;
        this.db = new Data();
        try {
            db.loadData();
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AppointmentScheduler.getStage().setTitle(rb.getString("loginButton"));  
        this.usernameLabel.setText(rb.getString("usernameLabel"));
        this.passwordLabel.setText(rb.getString("passwordLabel"));
        this.loginButton.setText(rb.getString("loginButton"));
       
               
    }
    
    @FXML
    public void validate() throws SQLException, IOException {
        
        String userName = usernameText.getText();
        String password = passwordText.getText();
        
        for (User i : Data.userList) {
            if (i.getName().equalsIgnoreCase(userName)){
                if (i.getPassword().equals(password)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("mainScreenFXML.fxml"));
                    Parent scene = loader.load();
                    MainScreenController msControl = loader.getController();

                    Scene nScene = new Scene(scene);
                    Stage stage = AppointmentScheduler.stage;
                    stage.setTitle("Appointment Scheduler");
                    stage.setScene(nScene);
                    stage.show();
                    System.out.println("Login successful");
                    Data.user = i;
                    logUser(i.getName());
                    return;
                } else {
                    Alert alert1 = new Alert(AlertType.ERROR);
                    alert1.setTitle(rb.getString("warning"));
                    alert1.setHeaderText(rb.getString("passwordMiss"));
                    alert1.showAndWait();
                    return;
                }
            } 
        }       
        Alert alert2 = new Alert(AlertType.ERROR);
        alert2.setTitle(rb.getString("warning"));
        alert2.setHeaderText(rb.getString("usernameMiss"));
        alert2.showAndWait();            
    }
    
    public void logUser(String userName) throws IOException {
        String content = Data.clock.now().toString() + ": " + userName + " logged in \n";
        Files.write(Paths.get("./log.txt"), content.getBytes(), StandardOpenOption.APPEND);
    }
    
    @FXML
    public void closeWindow() {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.close();
    }
    
}
     
  