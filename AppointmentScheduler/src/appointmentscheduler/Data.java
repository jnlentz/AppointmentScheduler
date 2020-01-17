package appointmentscheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Data {
    public static Connection dbCon; 
    public static User user;
    public static Time clock;
    public static ObservableList<User> userList;
    public static ObservableList<Appointment> apptList;
    public static ObservableList<Customer> cstrList;
    public static ObservableList<Address> addressList;
    public static ObservableList<City> cityList;
    public static ObservableList<Country> countryList;
    private static final String dbURL = "jdbc:mysql://3.227.166.251/U05tLT";
    private static final String dbUser = "U05tLT";
    private static final String dbPassword = "53688604720";

    public Data() {
        userList = FXCollections.observableArrayList();
        apptList = FXCollections.observableArrayList();
        cstrList = FXCollections.observableArrayList();
        addressList = FXCollections.observableArrayList();
        cityList = FXCollections.observableArrayList();
        countryList = FXCollections.observableArrayList(); 
        clock = new Time();
    }
    
    public static void open() {
        try {
           Class.forName("com.mysql.jdbc.Driver");
           dbCon = DriverManager.getConnection(dbURL, dbUser, dbPassword);
        }
        catch(ClassNotFoundException ex) {
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public static void close() throws SQLException {
        dbCon.close();
    }
    
    public void loadData() throws SQLException {
        open();
        getUsers();
        getAppts();
        getAddresses();
        getCstrs();        
        getCities();
        getCountries();
    }
    
    public void getUsers() throws SQLException {
        ResultSet users = getData("SELECT * FROM user");
        while (users.next()) {
            User temp = new User();
            temp.setID(users.getInt("userId"));
            temp.setName(users.getString("userName"));
            temp.setPassword(users.getString("password"));
            temp.setCreateDate(users.getTimestamp("createDate"));
            temp.setCreatedBy(users.getString("createdBy"));
            temp.setLastUpdate(users.getTimestamp("lastUpdate"));
            temp.setLastUpdateBy(users.getString("lastUpdateBy"));
            userList.add(temp);
        }
    }
    
    public static void getAppts() throws SQLException {
        ResultSet appts = getData("SELECT * FROM appointment");
        while (appts.next()) {
            Appointment appt = new Appointment();
            appt.setAppointmentID(appts.getInt("appointmentId"));
            appt.setCustomerID(appts.getInt("customerId"));
            appt.setUserID(appts.getInt("userId"));
            appt.setTitle(appts.getString("title"));
            appt.setDescription(appts.getString("description"));
            appt.setLocation(appts.getString("location"));
            appt.setContact(appts.getString("contact"));
            appt.setType(appts.getString("type"));
            appt.setStartRaw(appts.getTimestamp("start"));
            appt.setEndRaw(appts.getTimestamp("end"));
            appt.setCreateDate(appts.getTimestamp("createDate"));
            appt.setCreatedBy(appts.getString("createdBy"));
            appt.setLastUpdate(appts.getTimestamp("lastUpdate"));
            appt.setLastUpdateBy(appts.getString("lastUpdateBy"));            
            apptList.add(appt);
        }
    }
    
    public static void getCstrs() throws SQLException {
        cstrList.removeAll(cstrList);
        ResultSet cstrs = getData("SELECT * FROM customer");
        while (cstrs.next()) {
            Customer temp = new Customer();
            temp.setID(cstrs.getInt("customerId"));
            temp.setName(cstrs.getString("customerName"));
            temp.setAddressID(cstrs.getInt("addressId"));
            temp.setCreateDate(cstrs.getTimestamp("createDate"));
            temp.setCreatedBy(cstrs.getString("createdBy"));
            temp.setLastUpdate(cstrs.getTimestamp("lastUpdate"));
            temp.setLastUpdateBy(cstrs.getString("lastUpdateBy"));
            cstrList.add(temp);
        }
        setCstrAddress();
    }
    
    public void getAddresses() throws SQLException {
        ResultSet addresses = getData("SELECT * FROM address");
        while (addresses.next()) {
            Address temp = new Address();
            temp.setAddressId(addresses.getInt("addressId"));
            temp.setAddress(addresses.getString("address"));
            temp.setCityId(addresses.getInt("cityId"));
            temp.setZip(addresses.getString("postalCode"));
            temp.setPhone(addresses.getString("phone"));
            temp.setCreateDate(addresses.getTimestamp("createDate"));
            temp.setCreatedBy(addresses.getString("createdBy"));
            temp.setLastUpdate(addresses.getTimestamp("lastUpdate"));
            temp.setLastUpdateBy(addresses.getString("lastUpdateBy"));
            addressList.add(temp);
        }        
    }
    
    public void getCities() throws SQLException {
        ResultSet cities = getData("SELECT * FROM city");
        while (cities.next()) {
            City temp = new City();
            temp.setCityId(cities.getInt("cityId"));
            temp.setName(cities.getString("city"));
            temp.setCountryId(cities.getInt("countryId"));
            temp.setCreateDate(cities.getTimestamp("createDate"));
            temp.setCreatedBy(cities.getString("createdBy"));
            temp.setLastUpdate(cities.getTimestamp("lastUpdate"));
            temp.setLastUpdateBy(cities.getString("lastUpdateBy"));
            cityList.add(temp);
        }
    }
    
    public void getCountries() throws SQLException {
        ResultSet countries = getData("SELECT * FROM country");
        while (countries.next()) {
            Country temp = new Country();
            temp.setCountryId(countries.getInt("countryId"));
            temp.setCountry(countries.getString("country"));
            temp.setCreateDate(countries.getTimestamp("createDate"));
            temp.setCreatedBy(countries.getString("createdBy"));
            temp.setLastUpdate(countries.getTimestamp("lastUpdate"));
            temp.setLastUpdateBy(countries.getString("lastUpdateBy")); 
            countryList.add(temp);
        }
    }
    
    public static ObservableList<Appointment> apptsByWeek(LocalDate startDate) {
        ObservableList<Appointment> temp = FXCollections.observableArrayList();
        // Used lambda as a simpler way of iterating through a list
        apptList.stream().filter((i) -> (i.getDate().isAfter(startDate))).filter((i) -> (i.getDate().isBefore(startDate.plusDays(6)))).forEach((i) -> {
            temp.add(i);
        });
        return temp;
    }
    
    public static ObservableList<Appointment> apptsByMonth(LocalDate startDate) {
        ObservableList<Appointment> temp = FXCollections.observableArrayList();
        // Used lambda as a simpler way of iterating through a list
        apptList.stream().filter((i) -> (i.getDate().getMonth().equals(startDate.getMonth()))).forEach((i) -> {
            temp.add(i);
        }); 
        return temp;
    }
    
    public static LocalTime apptWarning() {
        LocalTime now = LocalTime.now();
        for (Appointment i : apptList) {
            if (i.getStartTime().isAfter(now) && i.getStartTime().isBefore(now.plusMinutes(15))) {
                return i.getStartTime();
            }
        }
        return null;
    }
    
    public static ResultSet getData(String query) throws SQLException {
        Statement getData = dbCon.createStatement();
        ResultSet data = getData.executeQuery(query);
        return data;
    }
    
    public int getKey(String table) throws SQLException {
        Statement getID = dbCon.createStatement();
        String query = "SELECT MAX(" + table + "Id) FROM " + table;
        ResultSet temp = getID.executeQuery(query);
        int id = 0;
        while (temp.next()) {
            id = temp.getInt(1);
        }
        return id + 1;        
    }
    
    public static void delete(String table, String condition) throws SQLException {
        Statement delete = dbCon.createStatement();
        String thisPerson = "DELETE FROM " + table + " WHERE " + condition;
        System.out.println(thisPerson);
        delete.execute(thisPerson);
    }
        
    public static void insertAddress(Address address, String city) throws SQLException {
        String sql = "INSERT INTO address(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy)  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement insert = dbCon.prepareStatement(sql);    
        insert.setString(1, address.getAddress());
        insert.setString(2, " ");
        insert.setInt(3, getFk("city", city));
        insert.setString(4, address.getZip());
        insert.setString(5, address.getPhone());
        insert.setTimestamp(6, clock.now());
        insert.setString(7, user.getName());
        insert.setTimestamp(8, clock.now());
        insert.setString(9, user.getName());
        insert.execute();
    }
    
    public static void updateAddress(Address address, String city) throws SQLException {        
        String query = "UPDATE address SET address = ?, cityId = ?, postalCode = ?, phone = ?, lastUpdate = ?, lastUpdateBy = ? WHERE addressId = " + Integer.toString(address.getAddressId());
        System.out.println(query);
        PreparedStatement update = dbCon.prepareStatement(query);
        update.setString(1, address.getAddress());
        update.setInt(2, getFk("city", city));
        update.setString(3, address.getZip());
        update.setString(4, address.getPhone());
        update.setTimestamp(5, clock.now());
        update.setString(6, user.getName());  
        update.execute();
    }
    
    public static void insertCstr(Customer cstr) throws SQLException {
        String sql = "INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement insert = dbCon.prepareStatement(sql);
        insert.setString(1, cstr.getName());
        insert.setInt(2, cstr.getAddressID());
        insert.setInt(3, 1);
        insert.setTimestamp(4, clock.now());
        insert.setString(5, user.getName());
        insert.setTimestamp(6, clock.now());
        insert.setString(7, user.getName());
        insert.execute();
    }
    
    public static void updateCstr(Customer cstr) throws SQLException {
        String query = "UPDATE customer SET customerName = ?, addressId = ?, active = ?, createDate = ?, createdBy = ?, lastUpdate = ?, lastUpdateBy = ? WHERE customerId = " + Integer.toString(cstr.getID());
        System.out.println("Address ID: " + cstr.getAddressID());
        PreparedStatement update = dbCon.prepareStatement(query);
        update.setString(1, cstr.getName());
        update.setInt(2, cstr.getAddressID());
        update.setInt(3, 1);
        update.setTimestamp(4, cstr.getCreateDate());
        update.setString(5, cstr.getCreatedBy());
        update.setTimestamp(6, clock.now());
        update.setString(7, user.getName());
        update.execute();
    }
    
    public static void insertAppt(Appointment appt) throws SQLException {
        String query = "INSERT INTO appointment(customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy) Values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement saveData = Data.dbCon.prepareStatement(query);
        saveData.setInt(1, appt.getCustomerID());
        saveData.setInt(2, appt.getUserID());
        saveData.setString(3, appt.getTitle());
        saveData.setString(4, appt.getDescription());
        saveData.setString(5, appt.getLocation()); 
        saveData.setString(6, appt.getContact());
        saveData.setString(7, appt.getType());
        saveData.setString(8, " ");
        saveData.setTimestamp(9, appt.getStartRaw());
        saveData.setTimestamp(10, appt.getEndRaw());
        saveData.setTimestamp(11, clock.now());
        saveData.setString(12, user.getName());
        saveData.setTimestamp(13, clock.now());
        saveData.setString(14, user.getName());
        saveData.execute();
    }
    
    public static void updateAppt(Appointment appt) throws SQLException {
        String query = "UPDATE appointment SET customerId = ?, userId = ?, title = ?, description = ?, location = ?, contact = ?, type = ?, url = ?, start = ?, end = ?, createDate = ?, createdBy = ?, lastUpdate = ?, lastUpdateBy = ? WHERE appointmentId = " + Integer.toString(appt.getAppointmentID());
        PreparedStatement saveData = Data.dbCon.prepareStatement(query);
        saveData.setInt(1, appt.getCustomerID());
        saveData.setInt(2, appt.getUserID());
        saveData.setString(3, appt.getTitle());
        saveData.setString(4, appt.getDescription());
        saveData.setString(5, appt.getLocation()); 
        saveData.setString(6, appt.getContact());
        saveData.setString(7, appt.getType());
        saveData.setString(8, " ");
        saveData.setTimestamp(9, appt.getStartRaw());
        saveData.setTimestamp(10, appt.getEndRaw());
        saveData.setTimestamp(11, appt.getCreateDate());
        saveData.setString(12, appt.getCreatedBy());
        saveData.setTimestamp(13, appt.getLastUpdate());
        saveData.setString(14, appt.getLastUpdateBy());
        saveData.execute();
    }
    
    public static int getFk(String table, String value) throws SQLException {
        Statement getCityId = dbCon.createStatement();
        String query = "SELECT " + table + "Id FROM " + table + " WHERE " + table + " = " + "'" + value + "'";
        ResultSet temp = getCityId.executeQuery(query);
        int id = 0;
        while(temp.next()) {
            id = temp.getInt(1);
        }
        return id;               
    }
    
    public static void refreshCstrs() throws SQLException {
        cstrList.removeAll(cstrList);
        getCstrs();
    }
    
    public static void refreshAppts() throws SQLException {
        apptList.removeAll(apptList);
        getAppts();
    }
    
    public static ObservableList<String> cityNames() {
        ObservableList<String> temp = FXCollections.observableArrayList();
        // Used lambda as a simpler way of iterating through a list
        cityList.stream().forEach((i) -> {
            temp.add(i.getName());
        });
        return temp;
    }
    
    public static ObservableList<String> countryNames() {
        ObservableList<String> temp = FXCollections.observableArrayList();
        // Used lambda as a simpler way of iterating through a list
        countryList.stream().forEach((i) -> {
            temp.add(i.getCountry());
        });
        return temp;
    }
    
    public static ObservableList<String> contacts() {
        ObservableList<String> temp = FXCollections.observableArrayList();
        // Used lambda as a simpler way of iterating through a list
        userList.stream().forEach((i) -> {
            temp.add(i.getName());
        });
        return temp;
    }
   
   public static ObservableList<LocalTime> getStarts(LocalDate date) {
       ObservableList<LocalTime> start = clock.getStartTimes(LocalTime.of(9, 0), LocalTime.of(18, 0));
       // Used lambda as a simpler way of iterating through a list
       apptList.stream().filter((i) -> (i.getDate().equals(date))).map((i) -> clock.getStartTimes(i.getStartTime(), i.getEndTime())).forEach((temp) -> {
           start.removeAll(temp);
        });
       return start;
   }
   
   public static ObservableList<LocalTime> getEnds(LocalDate date, LocalTime time) {
       LocalTime end = LocalTime.of(18, 0);
       for (Appointment i : apptList) {
           if (i.getDate().equals(date)) {
               LocalTime stop = i.getStartTime();
               if (time.isBefore(stop)) {
                   end = stop;
                   break;
               }
           }           
       }
       
       ObservableList<LocalTime> endTimes = clock.getEndTimes(time, end);
       return endTimes;
   }
   
   public static int typesByMonth(int month) throws SQLException {
       String query = "SELECT COUNT(DISTINCT type) FROM appointment WHERE EXTRACT(MONTH from start) = " + month;
       ResultSet types = getData(query);
       int result = 0;
       while(types.next()) {
           result = types.getInt("COUNT(DISTINCT type)");
       }
       return result;
   }
   
   public static String apptsByConsultant(String userName) throws SQLException {
       String query = "SELECT * FROM appointment WHERE contact = " + "'" + userName + "'";
       ResultSet schedule = getData(query);
       String temp = "";
       while(schedule.next()) {
           Appointment appt = new Appointment();
           appt.setAppointmentID(schedule.getInt("appointmentId"));
           appt.setCustomerID(schedule.getInt("customerId"));
           appt.setUserID(schedule.getInt("userId"));
           appt.setTitle(schedule.getString("title"));
           appt.setDescription(schedule.getString("description"));
           appt.setLocation(schedule.getString("location"));
           appt.setContact(schedule.getString("contact"));
           appt.setType(schedule.getString("type"));
           appt.setStartRaw(schedule.getTimestamp("start"));
           appt.setEndRaw(schedule.getTimestamp("end"));
           appt.setCreateDate(schedule.getTimestamp("createDate"));
           appt.setCreatedBy(schedule.getString("createdBy"));        
           appt.setLastUpdate(schedule.getTimestamp("lastUpdate"));
           appt.setLastUpdateBy(schedule.getString("lastUpdateBy"));            
           temp += appt.stringify();
       }
       return temp;
   }
   
   public static String getDailyAppts() {
       String temp = "";
       // Used lambda as a simpler way of iterating through a list
       temp = apptList.stream().filter((i) -> (i.getDate().equals(LocalDate.now()))).map((i) -> i.stringify()).reduce(temp, String::concat);
       return temp;       
   }
   
   private static Address findAddress(int id) {
       for (Address i : addressList) {
           if (i.getAddressId() == id) {
               return i;
           }
       }
       return null;
   }
   
   private static void setCstrAddress() {
       cstrList.stream().forEach((i) -> {
           i.setAddress(findAddress(i.getAddressID()));
        });
   }
}
