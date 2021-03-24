import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import utils.AeSimpleSHA1;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@DatabaseTable(tableName = "user")
public class User {
    enum UserType {
        Customer("Customer"),
        ClaimAdjuster("Claim Adjuster"),
        Manager("Manager");

        private String value;
        public String getValue()
        {
            return this.value;
        }
        private UserType(String value)
        {
            this.value = value;
        }
    }

    @DatabaseField(generatedId = true)
    private int userid;

    public UserType getUsertype() {
        return usertype;
    }

    public void setUsertype(UserType usertype) {
        this.usertype = usertype;
    }

    @DatabaseField(canBeNull = false)
    private String fname;
    @DatabaseField(canBeNull = false)
    private String lname;
    @DatabaseField(canBeNull = false, unique = true)
    private String username;
    @DatabaseField(canBeNull = false, defaultValue = "Customer")
    private UserType usertype;

    public int getUserid() {
        return userid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFullName() {
        return this.fname + " " + this.lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isIsadmin() {
        return isadmin;
    }

    public void setIsadmin(boolean isadmin) {
        this.isadmin = isadmin;
    }

    public void setPassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.password = AeSimpleSHA1.SHA1(password);
    }

    @DatabaseField(canBeNull = false)
    private String password;
    @DatabaseField(canBeNull = false)
    private boolean isadmin;
}
