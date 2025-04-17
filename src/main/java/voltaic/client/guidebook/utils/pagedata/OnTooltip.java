package voltaic.client.guidebook.utils.pagedata;

import voltaic.client.guidebook.ScreenGuidebook;
import net.minecraft.client.gui.GuiGraphics;

public interface OnTooltip {

	public void onTooltip(GuiGraphics graphics, int xAxis, int yAxis, ScreenGuidebook screen);

}
