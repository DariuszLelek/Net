package component.value;

import component.Transput;

import java.util.List;
import java.util.stream.IntStream;

public class TransputValueHelper {
    public static boolean sameTransputValuesDefinitions(Transput first, Transput second){
        return sameValuesDefinition(first.getTransputValues(), second.getTransputValues());
    }

    public static boolean sameValuesDefinition(List<TransputValue> first, List<TransputValue> second){
        return first.size() == second.size()
                && IntStream.range(0, first.size())
                        .mapToObj(i -> sameValueDefinition(first.get(i), second.get(i))).anyMatch(Boolean::booleanValue);
    }

    public static boolean sameValueDefinition(TransputValue first, TransputValue second){
        return stringMatch(first.getName(), second.getName())
                && first.getMin() == second.getMin() && first.getMax() == second.getMax();
    }

    private static boolean stringMatch(String first, String second){
        return first != null ? first.equals(second) : second == null;
    }
}
