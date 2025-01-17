package model.types;
import model.values.*;;

public interface IType {
    boolean equals(IType other);  
    IValue defaultValue();
}
