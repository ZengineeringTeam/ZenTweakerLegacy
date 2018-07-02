package snownee.zentweaker;

import org.apache.logging.log4j.Logger;

import net.minecraft.init.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import snownee.zentweaker.features.AutoStructureAlloySmelter;
import snownee.zentweaker.features.RegisterOres;
import snownee.zentweaker.potions.ZTPotion;
import snownee.zentweaker.worldgen.ZenStructureVillagePieces;

@Mod(modid = ZenTweaker.MODID, name = ZenTweaker.NAME, version = "@VERSION_INJECT@")
public class ZenTweaker
{
    public static final String MODID = "zentweaker";
    public static final String NAME = "ZenTweaker";

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        new RegisterOres();
        Items.WOODEN_SHOVEL.setMaxDamage(10).setNoRepair();
        Items.WOODEN_PICKAXE.setMaxDamage(5).setNoRepair();
        Items.WOODEN_AXE.setMaxDamage(5).setNoRepair().setHarvestLevel("axe", 1);
        ZenStructureVillagePieces.registerVillagePieces();
        ZTPotion.init();
        if (Loader.isModLoaded("immersiveengineering"))
        {
            MinecraftForge.EVENT_BUS.register(new AutoStructureAlloySmelter());
        }
    }
}
