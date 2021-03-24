import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "carinsuranceclaim")
public class CarInsuranceClaim {
    enum TypeOfIncident {
        Theft("Theft"),
        Damage("Damage"),
        Injury("Injury");

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

    public int getCarinsuranceclaimid() {
        return carinsuranceclaimid;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public CarInsurance getCarinsurance() {
        return carinsurance;
    }

    public void setCarinsurance(CarInsurance carinsurance) {
        this.carinsurance = carinsurance;
    }

    public TypeOfIncident getTypeofincident() {
        return typeofincident;
    }

    public void setTypeofincident(TypeOfIncident typeofincident) {
        this.typeofincident = typeofincident;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    @DatabaseField(generatedId = true)
    private int carinsuranceclaimid;
    @DatabaseField(foreign=true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = false, columnName = "claimid")
    private Claim claim;
    @DatabaseField(foreign=true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = false, columnName = "carinsuranceid")
    private CarInsurance carinsurance;
    @DatabaseField(canBeNull = false)
    private TypeOfIncident typeofincident;
    @DatabaseField(canBeNull = false)
    private String location;
}
