package model.values;

import model.types.*;

public interface IValue {
    IType getType();
    boolean equals(IValue other);
}
