import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "insurance")
public class Insurance {
    enum InsuranceType {
        Car("Car"),
        Home("Home"),
        AccidentalDamage("Accidental Damage");

        private String value;
        public String getValue()
        {
            return this.value;
        }
        private InsuranceType(String value)
        {
            this.value = value;
        }
    }

    @DatabaseField(generatedId = true)
    private int insuranceid;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @DatabaseField(canBeNull = false, foreign=true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "customerid")
    private Customer customer;
    @DatabaseField(canBeNull = false)
    private int coverageamount;
    @DatabaseField(canBeNull = false)
    private int premium;
    @DatabaseField(canBeNull = false)
    private int excess;

    public InsuranceType getInsurancetype() {
        return insurancetype;
    }

    public void setInsurancetype(InsuranceType insurancetype) {
        this.insurancetype = insurancetype;
    }

    @DatabaseField(canBeNull = false)
    private InsuranceType insurancetype;
    @DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, format = "yyyy-MM-dd")
    private Date startdate;
    @DatabaseField(canBeNull = false, dataType = DataType.DATE_STRING, format = "yyyy-MM-dd")
    private Date enddate;

    public int getInsuranceid() {
        return insuranceid;
    }

    public int getCoverageamount() {
        return coverageamount;
    }

    public void setCoverageamount(int coverageamount) {
        this.coverageamount = coverageamount;
    }

    public int getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
    }

    public int getExcess() {
        return excess;
    }

    public void setExcess(int excess) {
        this.excess = excess;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Date getLastclaimdate() {
        return lastclaimdate;
    }

    public void setLastclaimdate(Date lastclaimdate) {
        this.lastclaimdate = lastclaimdate;
    }

    @DatabaseField(canBeNull = true, dataType = DataType.DATE_STRING, format = "yyyy-MM-dd")
    private Date lastclaimdate;
}
