import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "accidentaldamageclaim")
public class AccidentalDamageInsuranceClaim {
    public int getAdclaimid() {
        return adclaimid;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public AccidentalDamageInsurance getAccidentaldamageinsurance() {
        return accidentaldamageinsurance;
    }

    public void setAccidentaldamageinsurance(AccidentalDamageInsurance accidentaldamageinsurance) {
        this.accidentaldamageinsurance = accidentaldamageinsurance;
    }

    @DatabaseField(generatedId = true)
    private int adclaimid;
    @DatabaseField(foreign=true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "claimid")
    private Claim claim;
    @DatabaseField(foreign=true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "accidentaldamageinsuranceid")
    private AccidentalDamageInsurance accidentaldamageinsurance;
}
