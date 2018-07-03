package snownee.zentweaker.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import snownee.zentweaker.ZenTweaker;

public class ItemFlintShovel extends ItemSpade
{
    public ItemFlintShovel()
    {
        super(Item.ToolMaterial.WOOD);
        setRegistryName(ZenTweaker.MODID, "flint_shovel");
        setUnlocalizedName(ZenTweaker.MODID + ".flint_shovel");
        setCreativeTab(CreativeTabs.TOOLS);
        setMaxDamage(10);
    }
}
