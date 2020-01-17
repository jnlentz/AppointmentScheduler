/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appointmentscheduler;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Time {
    public DateTimeFormatter df;
    public ZoneId localZone;
    private final ZoneId utcZone;
    
    public Time() {
        this.df = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
        this.localZone = ZoneId.systemDefault();
        this.utcZone = ZoneId.of("UTC");
    }
    
    public Timestamp toUtc(String date, String time) {
        String rawTime = date + " " + time;
        LocalDateTime ldt = LocalDateTime.parse(rawTime, df);
        ZonedDateTime zdt = ldt.atZone(localZone);
        ZonedDateTime utc = zdt.withZoneSameInstant(utcZone);
        ldt = utc.toLocalDateTime();
        Timestamp temp = Timestamp.valueOf(ldt);
        
        return temp;
    }
    
    public ZonedDateTime toLocal(Timestamp utc) {
        ZonedDateTime zUtc = utc.toLocalDateTime().atZone(ZoneId.of("UTC"));
	ZonedDateTime zLocal = zUtc.withZoneSameInstant(localZone);
        return zLocal;
    }
    
    public Timestamp now() {
        ZonedDateTime zdt = ZonedDateTime.now();
        ZonedDateTime utc = zdt.withZoneSameInstant(utcZone);
        LocalDateTime ldt = utc.toLocalDateTime();
        Timestamp temp = Timestamp.valueOf(ldt);
        return temp;
    } 
    
    public ObservableList<LocalTime> timeLists(int minutes) {
        ObservableList<LocalTime> temp = FXCollections.observableArrayList();
        LocalTime time = LocalTime.of(9, minutes);
        for (int i = 0; i < 16; i++) {
            temp.add(time);
            time = time.plusMinutes(30);
        }
        return temp;
    }
    
    public ObservableList<LocalTime> getStartTimes(LocalTime start, LocalTime stop) {
        ObservableList<LocalTime> temp = FXCollections.observableArrayList();
        LocalTime time = start;
        while(time.isBefore(stop)) {
            temp.add(time);
            time = time.plusMinutes(30);
        }
        return temp;
    }
    
    public ObservableList<LocalTime> getEndTimes(LocalTime start, LocalTime stop) {
        ObservableList<LocalTime> temp = FXCollections.observableArrayList();
        LocalTime time = start;
        while(time.isBefore(stop)) {
            time = time.plusMinutes(30);
            temp.add(time);
        }
        return temp;
    }
}
