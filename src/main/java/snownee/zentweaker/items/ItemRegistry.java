package snownee.zentweaker.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import snownee.zentweaker.ZenTweaker;
import snownee.zentweaker.utils.ModelUtil;

@EventBusSubscriber
public class ItemRegistry
{
    public static Item itemPlantFibre;
    public static Item itemPlantString;
    public static Item itemDepthMeter;

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(itemPlantFibre = new Item().setRegistryName(ZenTweaker.MODID, "plant_fibre").setUnlocalizedName(ZenTweaker.MODID
                + ".plant_fibre"));
        event.getRegistry().register(itemPlantString = new Item().setRegistryName(ZenTweaker.MODID, "plant_string").setUnlocalizedName(ZenTweaker.MODID
                + ".plant_string"));
        event.getRegistry().register(itemDepthMeter = new Item().setRegistryName(ZenTweaker.MODID, "depth_meter").setUnlocalizedName(ZenTweaker.MODID
                + ".depth_meter").setMaxStackSize(1).setCreativeTab(CreativeTabs.TOOLS));
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegister(@SuppressWarnings("unused") ModelRegistryEvent event)
    {
        ModelUtil.mapItemModel(itemPlantFibre);
        ModelUtil.mapItemModel(itemPlantString);
        ModelUtil.mapItemModel(itemDepthMeter);
    }
}
