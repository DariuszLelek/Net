package network;

import component.value.AccessValue;

import java.util.ArrayList;

public class Access {
    private final ArrayList<AccessValue> accessValues = new ArrayList<>();

    public ArrayList<AccessValue> getAccessValues() {
        return accessValues;
    }

    public int size(){
        return accessValues.size();
    }

    public void addBorderValue(AccessValue accessValue){
        accessValues.add(accessValue);
    }
}
