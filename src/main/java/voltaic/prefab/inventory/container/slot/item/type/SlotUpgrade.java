package voltaic.prefab.inventory.container.slot.item.type;

import java.util.ArrayList;
import java.util.List;

import voltaic.prefab.inventory.container.slot.item.SlotGeneric;
import voltaic.prefab.inventory.container.slot.utils.IUpgradeSlot;
import voltaic.prefab.screen.component.types.ScreenComponentSlot.IconType;
import voltaic.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlotUpgrade extends SlotGeneric implements IUpgradeSlot {

	private final List<Item> items = new ArrayList<>();

	public SlotUpgrade(Container inventory, int index, int x, int y, Item... upgrades) {
		super(SlotType.NORMAL, IconType.UPGRADE_DARK, inventory, index, x, y);

		items.clear();
		for (Item upg : upgrades) {
			items.add(upg);
		}
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return items.contains(stack.getItem());
	}

	@Override
	public List<Item> getUpgrades() {
		return items;
	}

}
