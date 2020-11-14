package Model.ADT;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class ADTDictionary<T_Key, T_Value> implements IADTDictionary<T_Key, T_Value>{
    Map<T_Key, T_Value> map = new HashMap<>();

    @Override
    public void put(T_Key key, T_Value value) {
        this.map.put(key, value);
    }

    @Override
    public boolean isKeyDefined(T_Key key) {
        return this.map.containsKey(key);
    }

    @Override
    public T_Value getValue(T_Key key) {
        return this.map.get(key);
    }

    @Override
    public T_Value remove(T_Key key) {
        return this.map.remove(key);
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (Map.Entry<T_Key, T_Value> entry : map.entrySet()) {
            stringJoiner.add(entry.getKey().toString() + " --> " + entry.getValue().toString() + "\n");
        }
        return stringJoiner.toString();
    }
}
