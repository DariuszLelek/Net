package component.value;

// TODO: get a better name for in/out value
public class AccessValue extends NumericValue<Double>{
    private final String name;

    public AccessValue(String name, Double min, Double max) {
        super(min, max);
        this.name  = name;
    }

    public String getName() {
        return name;
    }

    public void setFromNormalizedValue(NormalizedValue normalizedValue){
        value = (max - min) * normalizedValue.normalized - min;
    }

}
