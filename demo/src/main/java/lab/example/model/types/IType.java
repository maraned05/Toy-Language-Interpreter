package lab.example.model.types;
import lab.example.model.values.*;

public interface IType {
    boolean equals(IType other);  
    IValue defaultValue();
}
