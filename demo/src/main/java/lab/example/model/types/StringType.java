package lab.example.model.types;

import lab.example.model.values.IValue;
import lab.example.model.values.StringValue;

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
