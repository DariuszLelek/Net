package network;

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

    public void updateTransputValues(ArrayList<TransputValue> transputValue){
        this.transputValues.clear();
        this.transputValues.addAll(transputValues);
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


    protected Transput copy(){
        Transput copy = new Transput();
        this.transputValues.forEach(value -> copy.addTransputValue(new TransputValue(value)));
        return copy;
    }

    @Override
    public int hashCode() {
        return transputValues.hashCode();
    }
}
