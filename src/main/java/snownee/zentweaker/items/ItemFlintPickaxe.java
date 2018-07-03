package snownee.zentweaker.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import snownee.zentweaker.ZenTweaker;

public class ItemFlintPickaxe extends ItemPickaxe
{
    public ItemFlintPickaxe()
    {
        super(Item.ToolMaterial.WOOD);
        setRegistryName(ZenTweaker.MODID, "flint_pickaxe");
        setUnlocalizedName(ZenTweaker.MODID + ".flint_pickaxe");
        setCreativeTab(CreativeTabs.TOOLS);
        setMaxDamage(10);
    }
}
