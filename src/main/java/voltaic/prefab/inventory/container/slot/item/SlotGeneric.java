package voltaic.prefab.inventory.container.slot.item;

import javax.annotation.Nullable;

import voltaic.api.screen.ITexture;
import voltaic.api.screen.component.ISlotTexture;
import voltaic.prefab.screen.component.types.ScreenComponentSlot.IconType;
import voltaic.prefab.screen.component.types.ScreenComponentSlot.SlotType;
import voltaic.prefab.screen.component.utils.SlotTextureProvider;
import voltaic.prefab.tile.components.type.ComponentInventory;
import voltaic.prefab.utilities.math.Color;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotGeneric extends Slot implements SlotTextureProvider {

	private final ISlotTexture slotType;
	private final ITexture iconType;

	private boolean active = true;

	@Nullable
	public Color ioColor = null; // null means there is no color for this slot in IO mode meaning it isn't mapped to a face!

	public SlotGeneric(ISlotTexture slotType, ITexture iconType, Container inventory, int index, int x, int y) {
		super(inventory, index, x, y);
		this.slotType = slotType;
		this.iconType = iconType;
	}

	public SlotGeneric(Container inventory, int index, int x, int y) {
		this(SlotType.NORMAL, IconType.NONE, inventory, index, x, y);
	}

	public SlotGeneric setIOColor(Color color) {
		this.ioColor = color;
		return this;
	}

	@Override
	public boolean mayPlace(ItemStack stack) {
		return stack != null && container.canPlaceItem(getSlotIndex(), stack);
	}

	@Override
	public ISlotTexture getSlotType() {
		return slotType;
	}

	@Override
	public ITexture getIconType() {
		return iconType;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public void setChanged() {
		if (container instanceof ComponentInventory inv) {
			inv.setChanged(index);
		} else {
			super.setChanged();
		}
	}

}