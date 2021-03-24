import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "claim")
public class Claim {
    enum ClaimType {
        CarInsurance("Car Insurance"),
        HomeInsurance("Home Insurance"),
        AccidentalDamageInsurance("Accidental Damage Insurance");
        private String value;
        public String getValue()
        {
            return this.value;
        }
        private ClaimType(String value)
        {
            this.value = value;
        }
    }

    enum ClaimStatus {
        Pending("Pending"),
        Denied("Denied"),
        Finalised("Finalised");

        private String value;
        public String getValue()
        {
            return this.value;
        }
        private ClaimStatus(String value)
        {
            this.value = value;
        }
    }

    public ClaimType getClaimType() {
        return claimType;
    }

    public String getClaimTypeName() {
        return claimType.getValue();
    }

    public void setClaimType(ClaimType claimType) {
        this.claimType = claimType;
    }

    @DatabaseField(generatedId = true)
    private int claimid;
    @DatabaseField(foreign=true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "customerid", canBeNull = false)
    private Customer customer;
    @DatabaseField(foreign=true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "claimadjusterid", canBeNull = true)
    private ClaimsAdjuster claimadjuster;
    @DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, format = "yyyy-MM-dd")
    private Date date;
    @DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, format = "yyyy-MM-dd")
    private Date lastupdated;
    @DatabaseField(canBeNull = false)
    private ClaimStatus status;
    @DatabaseField
    private int amount;
    @DatabaseField(canBeNull = false)
    private ClaimType claimType;
    @DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, format = "yyyy-MM-dd")
    private Date dateofincident;
    @DatabaseField(canBeNull = false)
    private String timeofincident;

    public int getClaimid() {
        return claimid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ClaimsAdjuster getClaimadjuster() {
        return claimadjuster;
    }

    public void setClaimadjuster(ClaimsAdjuster claimadjuster) {
        this.claimadjuster = claimadjuster;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Date lastupdated) {
        this.lastupdated = lastupdated;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDateofincident() {
        return dateofincident;
    }

    public void setDateofincident(Date dateofincident) {
        this.dateofincident = dateofincident;
    }

    public String getTimeofincident() {
        return timeofincident;
    }

    public void setTimeofincident(String timeofincident) {
        this.timeofincident = timeofincident;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DatabaseField(canBeNull = false)
    private String description;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @DatabaseField(canBeNull = true)
    private String notes;
    public String getClaimAdjusterName() {
        if(claimadjuster != null) {
            return claimadjuster.getUser().getFullName();
        }
        return "";
    }
}
