package appointmentscheduler;

import java.sql.Timestamp;

public class Address {
    private int addressId;
    private String address;
    private int cityId;
    private String zip;
    private String phone;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBy;

    public Address() {
    }

    public Address(int addressId, String address, int cityId, String zip, String phone, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdateBy) {
        this.addressId = addressId;
        this.address = address;
        this.cityId = cityId;
        this.zip = zip;
        this.phone = phone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
    
    public String getCity() {
        if (this.cityId == 1) {
            return "New York";
        }
        
        if (this.cityId == 2) {
            return "Pheonix";
        }
        
        if (this.cityId == 3) {
            return "London";
        }
        
        return null;
    }
    
    public String getCountry() {
        if (this.cityId == 1) {
            return "US";
        }
        
        if (this.cityId == 2) {
            return "US";
        }
        
        if (this.cityId == 3) {
            return "UK";
        }
        
        return null;
    }
    
}
