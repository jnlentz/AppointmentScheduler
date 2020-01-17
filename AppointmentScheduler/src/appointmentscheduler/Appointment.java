
package appointmentscheduler;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author jnlen
 */
public class Appointment {
    public Time time;
    private Customer cstr;
    private int appointmentID;
    private int customerID;
    private int userID;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private Timestamp startRaw;
    private LocalDateTime start;
    private Timestamp endRaw;
    private LocalDateTime end;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;

    public Appointment() {
    }

    public Appointment(int appointmentID, int customerID, int userID, String title, String description, String location, String contact, String type, Timestamp start, Timestamp end, String createdBy) {
        this.time = new Time();
        this.appointmentID = appointmentID;
        this.customerID = customerID;
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startRaw = start;
        this.endRaw = end;
        this.createdBy = createdBy;
        this.createDate =  null;
        this.lastUpdate = null;
        this.lastUpdateBy = createdBy;
    }

    public Customer getCstr() {
        return cstr;
    }

    public void setCstr(Customer cstr) {
        this.cstr = cstr;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStart() {   
        this.time = new Time();
        this.start = this.time.toLocal(this.startRaw).toLocalDateTime();
        return this.start;
    }
    
    public Timestamp getStartRaw() {
        return this.startRaw;
    }

    public void setStartRaw(Timestamp start) {
        this.startRaw = start;
        this.start = Data.clock.toLocal(start).toLocalDateTime();
    }

    public LocalDateTime getEnd() {
        this.time = new Time();
        this.end = this.time.toLocal(this.endRaw).toLocalDateTime();
        return this.end;
    }
            
    public Timestamp getEndRaw() {
        return this.endRaw;
    }

    public void setEndRaw(Timestamp end) {
        this.endRaw = end;
        this.end = Data.clock.toLocal(end).toLocalDateTime();
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
    
    public LocalDate getDate() {
        return this.start.toLocalDate();
    }
    
    public LocalTime getStartTime() {
        LocalTime temp = this.start.toLocalTime();
        return temp;
    }
    
    public LocalTime getEndTime() {
        LocalTime temp = this.end.toLocalTime();
        return temp;
    }
    
    public String stringify() {
        String out = "Consultant: " + this.contact + "\nDate: " + this.getDate() + "\nFrom: " + this.getStartTime() + "\nTo: " + this.getEndTime() + "\n_______________________________________________\n";
        return out;        
    }
}
