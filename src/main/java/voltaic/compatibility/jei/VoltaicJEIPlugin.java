package voltaic.compatibility.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.registration.IGuiHandlerRegistration;
import org.jetbrains.annotations.NotNull;

import voltaic.Voltaic;
import voltaic.api.gas.Gas;
import voltaic.api.gas.GasStack;
import voltaic.client.guidebook.ScreenGuidebook;
import voltaic.compatibility.jei.screenhandlers.ScreenHandlerGuidebook;
import voltaic.compatibility.jei.screenhandlers.ScreenHandlerMaterialScreen;
import voltaic.compatibility.jei.utils.ingredients.VoltaicJeiTypes;
import voltaic.compatibility.jei.utils.ingredients.IngredientHelperGasStack;
import voltaic.compatibility.jei.utils.ingredients.IngredientRendererGasStack;
import voltaic.prefab.screen.types.GenericMaterialScreen;
import voltaic.registers.VoltaicGases;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IExtraIngredientRegistration;
import mezz.jei.api.registration.IModIngredientRegistration;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

@JeiPlugin
public class VoltaicJEIPlugin implements IModPlugin {

    public static final ResourceLocation ID = Voltaic.rl( "jei");

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registration) {
        registration.register(VoltaicJeiTypes.GAS_STACK, new ArrayList<>(), new IngredientHelperGasStack(), IngredientRendererGasStack.LIST_RENDERER, GasStack.CODEC);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registry) {
        registry.addGenericGuiContainerHandler(ScreenGuidebook.class, new ScreenHandlerGuidebook());
        registry.addGenericGuiContainerHandler(GenericMaterialScreen.class, new ScreenHandlerMaterialScreen());
    }

    @Override
    public void registerExtraIngredients(IExtraIngredientRegistration registration) {

        List<GasStack> gases = new ArrayList<>();
        for(DeferredHolder<Gas, ? extends Gas> gas : VoltaicGases.GASES.getEntries()) {
            if(gas.get() == VoltaicGases.EMPTY.value()) {
                continue;
            }

            gases.add(new GasStack(gas.get(), 1000, Gas.ROOM_TEMPERATURE, Gas.PRESSURE_AT_SEA_LEVEL));
        }
        registration.addExtraIngredients(VoltaicJeiTypes.GAS_STACK, gases);
    }

}
