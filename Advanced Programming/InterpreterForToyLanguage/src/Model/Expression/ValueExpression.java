package Model.Expression;

import Model.ADT.IADTDictionary;
import Model.ADT.IADTDictionaryForHeap;
import Model.Type.IType;
import Model.Value.IValue;

public class ValueExpression implements IExpression {
    IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }
    @Override
    public IValue evaluate(IADTDictionary<String, IValue> symbolTable, IADTDictionaryForHeap heap) throws Exception {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public IType checkTypes(IADTDictionary<String, IType> typeCheckerTable) throws Exception {
        return this.value.getType();
    }

}
