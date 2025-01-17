package model.types;

import model.values.IValue;
import model.values.RefValue;

public class RefType implements IType {
    private IType innerType;

    public RefType(IType inner) {
        this.innerType = inner;
    }    

    public IType getInnerType() {
        return this.innerType;
    }

    public boolean equals(IType other) {
        if (other instanceof RefType)
            return this.innerType.equals(((RefType)other).getInnerType());

        return false;
    }

    public IValue defaultValue() {
        return new RefValue(0, this.innerType);
    }

    @Override
    public String toString() {
        return "Ref(" + this.innerType.toString() + ")";
    }
}
