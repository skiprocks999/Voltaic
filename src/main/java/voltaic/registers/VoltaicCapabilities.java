package voltaic.registers;

import voltaic.api.radiation.util.IRadiationRecipient;
import net.neoforged.neoforge.capabilities.EntityCapability;
import org.jetbrains.annotations.Nullable;

import voltaic.Voltaic;
import voltaic.api.electricity.ICapabilityElectrodynamic;
import voltaic.api.gas.IGasHandler;
import voltaic.api.gas.IGasHandlerItem;
import voltaic.api.misc.ILocationStorage;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

public class VoltaicCapabilities {

    public static final double DEFAULT_VOLTAGE = 120.0;
    public static final String LOCATION_KEY = "location";

    public static final BlockCapability<ICapabilityElectrodynamic, @Nullable Direction> CAPABILITY_ELECTRODYNAMIC_BLOCK = BlockCapability.createSided(Voltaic.rl("electrodynamicblock"), ICapabilityElectrodynamic.class);

    public static final ItemCapability<ILocationStorage, Void> CAPABILITY_LOCATIONSTORAGE_ITEM = ItemCapability.createVoid(Voltaic.rl("locationstorageitem"), ILocationStorage.class);

    public static final ItemCapability<IGasHandlerItem, Void> CAPABILITY_GASHANDLER_ITEM = ItemCapability.createVoid(Voltaic.rl("gashandleritem"), IGasHandlerItem.class);
    public static final BlockCapability<IGasHandler, @Nullable Direction> CAPABILITY_GASHANDLER_BLOCK = BlockCapability.createSided(Voltaic.rl("gashandlerblock"), IGasHandler.class);

    public static final EntityCapability<IRadiationRecipient, Void> CAPABILITY_RADIATIONRECIPIENT = EntityCapability.createVoid(Voltaic.rl("radiationrecipient"), IRadiationRecipient.class);


}
