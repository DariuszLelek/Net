package component;

import component.value.TransputValue;

import java.util.ArrayList;

public class Transput {
    private final ArrayList<TransputValue> transputValues = new ArrayList<>();

    public ArrayList<TransputValue> getTransputValues() {
        return transputValues;
    }

    public int size(){
        return transputValues.size();
    }

    public void addTransputValue(TransputValue transputValue){
        transputValues.add(transputValue);
    }

    public void updateTransputValues(ArrayList<TransputValue> transputValues){
        ArrayList<TransputValue> collectionCopy = new ArrayList<>(transputValues);
        this.transputValues.clear();
        this.transputValues.addAll(collectionCopy);
    }

    public void clear(){
        transputValues.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transput transput = (Transput) o;

        return transputValues.equals(transput.transputValues);
    }

    @Override
    public int hashCode() {
        return transputValues.hashCode();
    }
}
