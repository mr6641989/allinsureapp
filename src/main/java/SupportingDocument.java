import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "supportingdocument")
public class SupportingDocument {
    public int getDocumentid() {
        return documentid;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public String getDocumentname() {
        return documentname;
    }

    public void setDocumentname(String documentname) {
        this.documentname = documentname;
    }

    @DatabaseField(generatedId = true)
    private int documentid;
    @DatabaseField(foreign=true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "claimid", canBeNull = true)
    private Claim claim;
    @DatabaseField
    private String documentname;

    public String getStoredfilename() {
        return storedfilename;
    }

    public void setStoredfilename(String storedfilename) {
        this.storedfilename = storedfilename;
    }

    @DatabaseField
    private String storedfilename;
}
