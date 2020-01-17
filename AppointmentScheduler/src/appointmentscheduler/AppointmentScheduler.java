/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appointmentscheduler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppointmentScheduler extends Application {
    static Stage stage;
    Data db;
    @Override
    public void start(Stage stage) throws Exception {
        AppointmentScheduler.stage = stage;
        //Locale.setDefault(new Locale("rn", "RS"));
        ResourceBundle rb = ResourceBundle.getBundle("lib/rb");
        
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("loginFXML.fxml"));
            loader.setResources(rb);
            Parent root = loader.load();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
        }
        
        stage.setOnCloseRequest(event -> {
            System.out.println("Stage is closing");
            try {
                Data.close();
            } catch (SQLException ex) {
                Logger.getLogger(AppointmentScheduler.class.getName()).log(Level.SEVERE, null, ex);
            }
         });
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public static Stage getStage() {
        return stage;
    }
    
}
