package lab.example.model.values;

import lab.example.model.types.IType;
import lab.example.model.types.RefType;

public class RefValue implements IValue {
    private Integer address;
    IType locationType;

    public RefValue(int addr, IType location) {
        this.address = addr;
        this.locationType = location;
    }

    public int getAddress() {
        return this.address;
    }

    public IType getLocationType() {
        return this.locationType;
    }

    public IType getType() {
        return new RefType(this.locationType);
    }

    public boolean equals(IValue other) {
        return other.getType() instanceof RefType && this.getAddress() == ((RefValue)other).getAddress();
    }

    @Override
    public String toString() {
        return "(" + String.valueOf(this.address) + ", " + this.locationType.toString() + ")";  
    }
}
