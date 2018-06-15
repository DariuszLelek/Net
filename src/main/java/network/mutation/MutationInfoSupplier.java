package network.mutation;

import network.Network;

public interface MutationInfoSupplier {
    MutationInfo get(Network network);
}
