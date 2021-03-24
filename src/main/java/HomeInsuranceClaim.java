import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "homeinsuranceclaim")

public class HomeInsuranceClaim {
    enum TypeOfIncident {
        Fire("Fire"),
        Glass("Glass"),
        Theft("Theft");
        private String value;
        public String getValue()
        {
            return this.value;
        }
        private TypeOfIncident(String value)
        {
            this.value = value;
        }
    }
    @DatabaseField(generatedId = true)
    private int homeinsuranceclaimid;
    @DatabaseField(foreign=true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "claimid")
    private Claim claim;

    public int getHomeinsuranceclaimid() {
        return homeinsuranceclaimid;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public TypeOfIncident getTypeofincident() {
        return typeofincident;
    }

    public void setTypeofincident(TypeOfIncident typeofincident) {
        this.typeofincident = typeofincident;
    }

    @DatabaseField
    private TypeOfIncident typeofincident;

    public HomeInsurance getHomeInsurance() {
        return homeInsurance;
    }

    public void setHomeInsurance(HomeInsurance homeInsurance) {
        this.homeInsurance = homeInsurance;
    }

    @DatabaseField(foreign=true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = false, columnName = "homeinsuranceid")
    private HomeInsurance homeInsurance;
}
