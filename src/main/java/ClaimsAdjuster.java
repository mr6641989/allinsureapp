import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import spark.Request;
import spark.Response;
import spark.Route;
import utils.Paths;
import utils.Views;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.Map;

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
