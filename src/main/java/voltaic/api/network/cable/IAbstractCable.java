package voltaic.api.network.cable;

import voltaic.prefab.network.AbstractNetwork;
import voltaic.prefab.tile.types.GenericRefreshingConnectTile;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IAbstractCable<CONDUCTORTYPE, T extends AbstractNetwork<? extends GenericRefreshingConnectTile<?, ?, ?>, ?, ?, ?>> {

	void removeFromNetwork();

	T getNetwork();

	void createNetworkFromThis();

	void setNetwork(T aValueNetwork);

	BlockEntity[] getConectedRecievers();

	BlockEntity[] getConnectedCables();

	CONDUCTORTYPE getCableType();

	double getMaxTransfer();
}
