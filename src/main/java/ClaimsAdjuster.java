import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "claimsadjuster")
public class ClaimsAdjuster {
    @DatabaseField(generatedId = true)
    private int claimadjusterid;

    public int getClaimadjusterid() {
        return claimadjusterid;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @DatabaseField(foreign=true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "userid", canBeNull = false)
    private User user;
}
