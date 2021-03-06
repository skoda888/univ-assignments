package Model.Value;

import Model.Type.BoolType;
import Model.Type.IType;

public class BoolValue implements IValue{
    boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return Boolean.toString(this.value);
    }

    @Override
    public boolean equals(IValue other) {
        return other instanceof BoolValue;
    }

    public boolean getValue() {
        return this.value;
    }
}
