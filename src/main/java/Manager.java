import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "manager")
public class Manager {
    public int getManagerid() {
        return managerid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @DatabaseField(generatedId = true)
    private int managerid;
    @DatabaseField(foreign=true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "userid", canBeNull = false)
    private User user;
}
