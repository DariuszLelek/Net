package component.value.normalized;

public class Weight extends NormalizedValue {
    public Weight(){
    }

    private Weight(Weight wight){
        value = wight.value;
    }

    public Weight copy(){
        return new Weight(this);
    }
}
