package component.neuron;

import component.Connection;
import component.ConnectionWeight;
import component.value.NormalizedValue;
import exception.ValueNotInRangeException;
import org.junit.Assert;
import org.junit.Test;

public class NeuronTest {

    @Test
    public void getConnectionWeight_notEmpty(){
        Neuron neuron = new Neuron();

        Connection firstInputConnection = new Connection();
        Connection secondInputConnection = new Connection();

        neuron.addInputConnection(firstInputConnection);
        neuron.addInputConnection(secondInputConnection);

        Assert.assertNotEquals(ConnectionWeight.EMPTY, neuron.getConnectionWeight(firstInputConnection));
        Assert.assertEquals(firstInputConnection, neuron.getConnectionWeight(firstInputConnection).getConnection());

        Assert.assertNotEquals(ConnectionWeight.EMPTY, neuron.getConnectionWeight(secondInputConnection));
        Assert.assertEquals(secondInputConnection, neuron.getConnectionWeight(secondInputConnection).getConnection());
    }

    @Test
    public void fire() throws ValueNotInRangeException {
        Neuron neuron = new Neuron();

        Connection firstInputConnection = new Connection();
        Connection secondInputConnection = new Connection();

        neuron.addInputConnection(firstInputConnection);
        neuron.addInputConnection(secondInputConnection);

        double weight1 = 0.8d;
        double weight2 = 0.4d;
        double connectionValue1 = 0.2d;
        double connectionValue2 = 0.9d;
        double expected = (weight1 * connectionValue1 + weight2 * connectionValue2) / neuron.getInputConnections().size();

        neuron.getConnectionWeight(firstInputConnection).getWeight().set(weight1);
        neuron.getConnectionWeight(firstInputConnection).getConnection().setNormalized(connectionValue1);
        neuron.getConnectionWeight(secondInputConnection).getWeight().set(weight2);
        neuron.getConnectionWeight(secondInputConnection).getConnection().setNormalized(connectionValue2);

        neuron.fire();

        Assert.assertEquals(expected, neuron.getOutputConnection().getNormalizedValue().getNormalized(), 0.01d);
    }
}