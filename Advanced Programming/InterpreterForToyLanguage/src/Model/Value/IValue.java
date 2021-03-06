package Model.Value;

import Model.Type.IType;

public interface IValue {
    IType getType();
    String toString();
    boolean equals(IValue other);
}
