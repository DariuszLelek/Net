package component.value.normalized;

public class Connection extends NormalizedValue {
    public Connection(){
    }

    private Connection(Connection connection){
        value = connection.value;
    }

    public Connection copy(){
        return new Connection(this);
    }
}
