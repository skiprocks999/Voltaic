package voltaic.common.settings;

import voltaic.api.configuration.BooleanValue;
import voltaic.api.configuration.Configuration;
import voltaic.api.configuration.DoubleValue;

@Configuration(name = "Voltaic")
public class VoltaicConstants {
	@BooleanValue(def = true)
	public static boolean DISPENSE_GUIDEBOOK = true;
	@DoubleValue(def = 100)
	public static double BACKROUND_RADIATION_DISSIPATION = 1;
	@DoubleValue(def = 300)
	public static double IODINE_RESISTANCE_THRESHHOLD = 300;
	@DoubleValue(def = 0.8)
	public static double IODINE_RAD_REDUCTION = 0.8;

}
