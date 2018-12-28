package snownee.zentweaker.feature;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import snownee.kiwi.IModule;
import snownee.kiwi.Kiwi;
import snownee.kiwi.KiwiModule;
import snownee.kiwi.client.ModelUtil;
import snownee.zentweaker.ZenTweaker;
import snownee.zentweaker.item.ItemFlintAxe;
import snownee.zentweaker.item.ItemFlintPickaxe;
import snownee.zentweaker.item.ItemFlintShovel;

@KiwiModule(modid = ZenTweaker.MODID, name = "FlintTools", optional = true)
public class FlintTools implements IModule
{
    private ItemFlintAxe itemFlintAxe;
    private ItemFlintPickaxe itemFlintPickaxe;
    private ItemFlintShovel itemFlintShovel;

    public FlintTools()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void init()
    {
        OreDictionary.registerOre("stringAny", Items.STRING);
        if (Kiwi.isOptionalModuleLoaded(ZenTweaker.MODID, "DropFibreAndFlint"))
        {
            OreDictionary.registerOre("stringAny", DropFibreAndFlint.PLANT_STRING);
        }
    }

    @SubscribeEvent
    public void onItemRegister(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(itemFlintAxe = new ItemFlintAxe());
        event.getRegistry().register(itemFlintPickaxe = new ItemFlintPickaxe());
        event.getRegistry().register(itemFlintShovel = new ItemFlintShovel());
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onModelRegister(ModelRegistryEvent event)
    {
        ModelUtil.mapItemModel(itemFlintAxe);
        ModelUtil.mapItemModel(itemFlintPickaxe);
        ModelUtil.mapItemModel(itemFlintShovel);
    }
}
