package lab.example.model.values;

import lab.example.model.types.*;

public interface IValue {
    IType getType();
    boolean equals(IValue other);
}
