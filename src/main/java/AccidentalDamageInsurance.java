import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "accidentaldamageinsurance")
public class AccidentalDamageInsurance {
    enum TypeOfItem {
        Electrical("Electrical"),
        WhiteGoods("White Goods"),
        Furniture("Furniture");

        private String value;
        public String getValue()
        {
            return this.value;
        }
        private TypeOfItem(String value)
        {
            this.value = value;
        }
    }
    public int getAccidentaldamageinsuranceid() {
        return accidentaldamageinsuranceid;
    }

    public String getNameofitem() {
        return nameofitem;
    }

    public void setNameofitem(String nameofitem) {
        this.nameofitem = nameofitem;
    }

    public TypeOfItem getTypeofitem() {
        return typeofitem;
    }

    public void setTypeofitem(TypeOfItem typeofitem) {
        this.typeofitem = typeofitem;
    }

    @DatabaseField(generatedId = true)
    private int accidentaldamageinsuranceid;
    @DatabaseField(canBeNull = false)
    private String nameofitem;
    @DatabaseField(canBeNull = false)
    private TypeOfItem typeofitem;

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    @DatabaseField(foreign=true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "insuranceid")
    private Insurance insurance;

    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    @DatabaseField(canBeNull = false)
    private String serialno;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @DatabaseField(canBeNull = false)
    private int value;

}
