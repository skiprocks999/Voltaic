package voltaic.api.network.cable;

import voltaic.prefab.network.AbstractNetwork;
import voltaic.prefab.tile.types.GenericRefreshingConnectTile;
import net.minecraft.core.Direction;

public interface IRefreshableCable<CONDUCTORTYPE, T extends AbstractNetwork<? extends GenericRefreshingConnectTile<?, ?, ?>, ?, ?, ?>> extends IAbstractCable<CONDUCTORTYPE, T> {

	void updateNetwork(Direction...dirs);

	void destroyViolently();

}
