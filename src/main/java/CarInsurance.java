import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "carinsurance")
public class CarInsurance {
    @DatabaseField(generatedId = true)
    private int carinsuranceid;
    @DatabaseField(foreign=true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "insuranceid")
    private Insurance insurance;
    @DatabaseField(canBeNull = false)
    private int carvalue;
    @DatabaseField(canBeNull = false)
    private String make;
    @DatabaseField(canBeNull = false)
    private String model;
    @DatabaseField(canBeNull = false)
    private int year;
    @DatabaseField(canBeNull = false, unique = true)
    private String registration;

    public int getCarinsuranceid() {
        return carinsuranceid;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public int getCarvalue() {
        return carvalue;
    }

    public void setCarvalue(int carvalue) {
        this.carvalue = carvalue;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public int getDriverage() {
        return driverage;
    }

    public void setDriverage(int driverage) {
        this.driverage = driverage;
    }

    @DatabaseField(canBeNull = false)
    private int driverage;
}
