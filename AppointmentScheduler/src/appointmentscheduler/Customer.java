
package appointmentscheduler;

import java.sql.Timestamp;


public class Customer {
    private Address address;
    private int ID;
    private String Name;
    private int addressID;
    private Timestamp createDate;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;

    public Customer() {
    }

    public Customer(int customerID, String customerName, int addressID, String createdBy) {
        this.ID = customerID;
        this.Name = customerName;
        this.addressID = addressID;
        this.createdBy = createdBy;
        this.createDate = new Timestamp(createDate.getTime());
        this.lastUpdate = new Timestamp(lastUpdate.getTime());
        this.lastUpdateBy = createdBy;
    }

    public int getID() {
        return ID;
    }

    public void setID(int customerID) {
        this.ID = customerID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    
}
