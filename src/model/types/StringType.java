package model.types;

import model.values.IValue;
import model.values.StringValue;

public class StringType implements IType{
    public boolean equals(IType other) {
        return other instanceof StringType;
    }

    public IValue defaultValue() {
        return new StringValue("");
    }

    @Override
    public String toString() {
        return "String";
    }
}
