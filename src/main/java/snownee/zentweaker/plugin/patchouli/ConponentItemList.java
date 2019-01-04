package snownee.zentweaker.plugin.patchouli;

import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.VariableHolder;

public class ConponentItemList implements ICustomComponent
{
    @VariableHolder
    public String[] items;

    int x, y;

    @Override
    public void build(int componentX, int componentY, int pageNum)
    {
        this.x = componentX;
        this.y = componentY;
    }

    @Override
    public void render(IComponentRenderContext context, float pticks, int mouseX, int mouseY)
    {
        context.getFont().drawString("???", x, y, 0);
    }

}
