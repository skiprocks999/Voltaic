package voltaic.compatibility.jei.utils.ingredients;

import voltaic.api.gas.Gas;
import voltaic.api.gas.GasStack;
import mezz.jei.api.ingredients.IIngredientTypeWithSubtypes;

public class VoltaicJeiTypes {

	public static final IIngredientTypeWithSubtypes<Gas, GasStack> GAS_STACK = new IIngredientTypeWithSubtypes<>() {

		@Override
		public Class<? extends GasStack> getIngredientClass() {
			return GasStack.class;
		}

		@Override
		public Class<? extends Gas> getIngredientBaseClass() {
			return Gas.class;
		}

		@Override
		public Gas getBase(GasStack ingredient) {
			return ingredient.getGas();
		}
	};

}
