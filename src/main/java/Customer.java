import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import utils.Database;

import java.sql.SQLException;
import java.util.Date;

@DatabaseTable(tableName = "customer")
public class Customer {
    @DatabaseField(generatedId = true)
    private int customerid;
    @DatabaseField(foreign=true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "userid", canBeNull = false)
    private User user;
    @DatabaseField(canBeNull = false)
    private String address;

    public int getCustomerid() {
        return customerid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDatejoined() {
        return datejoined;
    }

    public void setDatejoined(Date datejoined) {
        this.datejoined = datejoined;
    }

    @DatabaseField(canBeNull = false, unique = true)
    private String phone;
    @DatabaseField(canBeNull = false, unique = true)
    private String email;
    @DatabaseField(canBeNull = false)
    private Date datejoined;

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    @DatabaseField(canBeNull = false)
    private Date dateofbirth;

    public boolean hasCarInsurance() throws SQLException {
        ConnectionSource connectionSource = Database.databaseConn();
        Dao<Insurance, Integer> insuranceDao = DaoManager.createDao(connectionSource, Insurance.class);
        if (insuranceDao.queryBuilder().where().eq("customerid", customerid).and().eq("insurancetype", Insurance.InsuranceType.Car).and().ge("enddate", new Date()).countOf() > 0) {
            return true;
        }
        return false;
    }

    public boolean hasAccidentalDamageInsurance() throws SQLException {
        ConnectionSource connectionSource = Database.databaseConn();
        Dao<Insurance, Integer> insuranceDao = DaoManager.createDao(connectionSource, Insurance.class);
        if(insuranceDao.queryBuilder().where().eq("customerid", customerid).and().eq("insurancetype", Insurance.InsuranceType.AccidentalDamage).and().ge("enddate", new Date()).countOf() > 0) {
            return true;
        }
        return false;
    }

    public boolean hasHomeInsurance() throws SQLException {
        ConnectionSource connectionSource = Database.databaseConn();
        Dao<Insurance, Integer> insuranceDao = DaoManager.createDao(connectionSource, Insurance.class);
        if (insuranceDao.queryBuilder().where().eq("customerid", customerid).and().eq("insurancetype", Insurance.InsuranceType.Home).and().ge("enddate", new Date()).countOf() > 0) {
            return true;
        }
        return false;
    }
}
