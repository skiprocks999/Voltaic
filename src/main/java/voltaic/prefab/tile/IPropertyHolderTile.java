package voltaic.prefab.tile;

import voltaic.prefab.properties.PropertyManager;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IPropertyHolderTile {
	PropertyManager getPropertyManager();

	default BlockEntity getTile() {
		return (BlockEntity) this;
	}
}
