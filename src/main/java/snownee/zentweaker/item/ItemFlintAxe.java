package snownee.zentweaker.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import snownee.zentweaker.ZenTweaker;

public class ItemFlintAxe extends ItemAxe
{
    public ItemFlintAxe()
    {
        super(Item.ToolMaterial.WOOD);
        setRegistryName(ZenTweaker.MODID, "flint_axe");
        setTranslationKey(ZenTweaker.MODID + ".flint_axe");
        setCreativeTab(CreativeTabs.TOOLS);
        setMaxDamage(10);
        setNoRepair();
    }
}
