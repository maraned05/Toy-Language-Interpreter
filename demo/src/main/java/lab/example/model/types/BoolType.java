package lab.example.model.types;

import lab.example.model.values.BoolValue;
import lab.example.model.values.IValue;

public class BoolType implements IType {
    public boolean equals(IType other) {
        return other instanceof BoolType;
    }

    @Override
    public String toString() {
        return "Boolean";
    }

    public IValue defaultValue() {
        return new BoolValue(false);
    }
}
