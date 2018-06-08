package network.mutation;

import network.Network;

public interface NetworkMutationSupplier {
    NetworkMutationInfo get(Network network);
}
