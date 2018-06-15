package component.value.normalized.type;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public enum Type {
    NONE(false),
    WEIGHT(true),
    THRESHOLD(true),
    BIAS(true);

    private boolean mutable;
    public static int size = Arrays.stream(values()).map(Enum::toString).collect(Collectors.toList())
            .stream().max(Comparator.comparingInt(String::length)).orElse("").length();

    Type(boolean mutable) {
        this.mutable = mutable;
    }

    public boolean isMutable() {
        return mutable;
    }

    public static Type getRandomMutable(){
        List<Type> mutables = Arrays.stream(values()).filter(Type::isMutable).collect(Collectors.toList());
        return mutables.get(new Random().nextInt(mutables.size()));
    }
}
