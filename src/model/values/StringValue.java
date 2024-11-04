package model.values;

import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue{
    private String val;

    public StringValue(String value) {
        this.val = value;
    }

    public String getValue() {
        return this.val;
    }

    public IType getType() {
        return new StringType();
    } 

    public boolean equals(IValue other) {
        return  other.getType() instanceof StringValue
                && this.getValue() == ((StringValue)other).getValue();
    }
}
