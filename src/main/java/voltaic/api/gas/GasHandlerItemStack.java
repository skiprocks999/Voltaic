package voltaic.api.gas;

import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import voltaic.registers.VoltaicCapabilities;

/**
 * Default implementation of IGasHandlerItem to be bound to an item's capability
 * 
 * @author skip999
 *
 */
public class GasHandlerItemStack implements IGasHandlerItem, ICapabilityProvider {

    public static final String GAS_NBT_KEY = "Gas";
    
    private final LazyOptional<IGasHandlerItem> lazyOptional = LazyOptional.of(() -> this);

    private Predicate<GasStack> isGasValid = gas -> true;
    protected ItemStack container;
    protected int capacity;
    protected int maxTemperature;
    protected int maxPressure;

    public GasHandlerItemStack(ItemStack container, int capacity, int maxTemperature, int maxPressure) {
        this.container = container;
        this.capacity = capacity;
        this.maxTemperature = maxTemperature;
        this.maxPressure = maxPressure;
    }

    public GasHandlerItemStack setPredicate(Predicate<GasStack> predicate) {
        isGasValid = predicate;
        return this;
    }
    
    @Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if(cap == VoltaicCapabilities.CAPABILITY_GASHANDLER_ITEM) {
			return lazyOptional.cast();
		}
		return LazyOptional.empty();
	}

    @Override
    public int getTanks() {
        return 1;
    }

    public void setGas(GasStack gas) {
    	
    	GasStack.CODEC.encode(gas, NbtOps.INSTANCE, NbtOps.INSTANCE.empty()).result().ifPresent(tag -> container.getOrCreateTag().put(GAS_NBT_KEY, tag));
    }

    @Override
    public GasStack getGasInTank(int tank) {

        return GasStack.CODEC.decode(NbtOps.INSTANCE, container.getOrCreateTag().get(GAS_NBT_KEY)).result().orElseGet(() -> Pair.of(GasStack.EMPTY, new CompoundTag())).getFirst();
    }

    @Override
    public int getTankCapacity(int tank) {
        return capacity;
    }

    @Override
    public int getTankMaxTemperature(int tank) {
        return maxTemperature;
    }

    @Override
    public int getTankMaxPressure(int tank) {
        return maxPressure;
    }

    @Override
    public boolean isGasValid(int tank, GasStack gas) {
        return isGasValid.test(gas);
    }

    @Override
    public int fill(GasStack resource, GasAction action) {
        if (resource.isEmpty()) {
            return 0;
        }

        if (!isGasValid(0, resource)) {
            return 0;
        }

        if (isEmpty()) {

            int accepted = Math.min(resource.getAmount(), capacity);

            if (action == GasAction.EXECUTE) {

                setGas(new GasStack(resource.getGas(), accepted, resource.getTemperature(), resource.getPressure()));

                if (resource.getTemperature() > maxTemperature) {

                    onOverheat();

                }

                if (resource.getPressure() > maxPressure) {

                    onOverpressure();

                }

            }

            return accepted;

        }
        GasStack gas = getGasInTank(0);

        if (!gas.isSameGas(resource)) {
            return 0;
        }

        int canTake = GasStack.getMaximumAcceptance(gas, resource, capacity);

        if (canTake == 0) {
            return 0;
        }

        if (action == GasAction.EXECUTE) {

            GasStack accepted = resource.copy();

            accepted.setAmount(canTake);

            GasStack equalized = GasStack.equalizePresrsureAndTemperature(gas, accepted);

            setGas(equalized);

            if (gas.getTemperature() > maxTemperature) {

                onOverheat();

            }

            if (gas.getPressure() > maxPressure) {

                onOverpressure();

            }

        }

        return canTake;
    }

    @Override
    public GasStack drain(GasStack resource, GasAction action) {

        GasStack gas = getGasInTank(0);

        if (resource.isEmpty() || !gas.isSameGas(resource) || !gas.isSamePressure(resource) || !gas.isSameTemperature(resource)) {
            return GasStack.EMPTY;
        }

        return drain(resource.getAmount(), action);

    }

    @Override
    public GasStack drain(int amount, GasAction action) {

        if (isEmpty() || amount == 0) {
            return GasStack.EMPTY;
        }

        GasStack gas = getGasInTank(0);

        int taken = Math.min(gas.getAmount(), amount);

        GasStack takenStack = new GasStack(gas.getGas(), taken, gas.getTemperature(), gas.getPressure());

        if (action == GasAction.EXECUTE) {

            gas.shrink(taken);

            if (gas.getAmount() == 0) {

                setContainerToEmpty();

            } else {

                setGas(gas);

            }
        }

        return takenStack;
    }

    @Override
    public int heat(int tank, int deltaTemperature, GasAction action) {

        GasStack gas = getGasInTank(0);

        if (gas.isAbsoluteZero() && deltaTemperature < 0) {
            return -1;
        }

        GasStack updated = gas.copy();

        updated.heat(deltaTemperature);

        if (updated.getAmount() > capacity) {
            return -1;
        }

        if (action == GasAction.EXECUTE) {

            setGas(updated);

            if (gas.getTemperature() > maxTemperature) {

                onOverheat();

            }

        }

        return capacity - updated.getAmount();
    }

    @Override
    public int bringPressureTo(int tank, int atm, GasAction action) {

        GasStack gas = getGasInTank(0);

        if (gas.isVacuum() && atm < GasStack.VACUUM) {
            return -1;
        }

        GasStack updated = gas.copy();

        updated.bringPressureTo(atm);

        if (updated.getAmount() > capacity) {

            return -1;

        }

        if (action == GasAction.EXECUTE) {

            setGas(updated);

            if (gas.getPressure() > maxPressure) {

                onOverpressure();

            }

        }

        return capacity - updated.getAmount();
    }

    @Override
    public ItemStack getContainer() {
        return container;
    }

    public void onOverheat() {
        container.shrink(1);
    }

    public void onOverpressure() {
        container.shrink(1);
    }

    public boolean isEmpty() {
        return getGasInTank(0).isEmpty();
    }

    public void setContainerToEmpty() {
        container.getOrCreateTag().remove(GAS_NBT_KEY);
    }

    /**
     * Destroys the container item when it's emptied.
     */
    public static class Consumable extends GasHandlerItemStack {

        public Consumable(ItemStack container, int capacity, int maxTemperature, int maxPressure) {
            super(container, capacity, maxTemperature, maxPressure);
        }

        @Override
        public void setContainerToEmpty() {
            super.setContainerToEmpty();
            container.shrink(1);
        }
    }

    /**
     * Swaps the container item for a different one when it's emptied.
     */
    public static class SwapEmpty extends GasHandlerItemStack {

        protected final ItemStack emptyContainer;

        public SwapEmpty(ItemStack container, ItemStack emptyContainer, int capacity, int maxTemperature, int maxPressure) {
            super(container, capacity, maxTemperature, maxPressure);
            this.emptyContainer = emptyContainer;
        }

        @Override
        public void setContainerToEmpty() {
            super.setContainerToEmpty();
            container = emptyContainer;
        }
    }

}
