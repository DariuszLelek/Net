package component.value.normalized;

public class Weight extends NormalizedValue{
    public Weight(){
        value = MAX_VALUE;
    }

    @Override
    public String toString() {
        return "Weight{" +
                "value=" + value +
                '}';
    }
}
