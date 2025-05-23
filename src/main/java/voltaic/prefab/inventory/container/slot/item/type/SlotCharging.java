package voltaic.prefab.inventory.container.slot.item.type;

import voltaic.api.item.IItemElectric;
import voltaic.prefab.inventory.container.slot.item.SlotGeneric;
import voltaic.prefab.screen.component.types.ScreenComponentSlot.IconType;
import voltaic.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class SlotCharging extends SlotGeneric {

	public SlotCharging(Container inventory, int index, int x, int y) {
		super(SlotType.NORMAL, IconType.ENERGY_DARK, inventory, index, x, y);
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		if (super.mayPlace(stack) && stack.getItem() instanceof IItemElectric) {
			return true;
		}
		return false;
	}

}
