import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "homeinsurance")
public class HomeInsurance {
    public int getHomeinsuranceid() {
        return homeinsuranceid;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    @DatabaseField(foreign=true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "insuranceid")
    private Insurance insurance;

    public int getHomevalue() {
        return homevalue;
    }

    public void setHomevalue(int homevalue) {
        this.homevalue = homevalue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @DatabaseField(generatedId = true)
    private int homeinsuranceid;
    @DatabaseField
    private int homevalue;
    @DatabaseField
    private String address;
}
