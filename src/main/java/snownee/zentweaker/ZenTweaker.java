package snownee.zentweaker;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import snownee.zentweaker.worldgen.ZenStructureVillagePieces;

@Mod(modid = ZenTweaker.MODID, name = ZenTweaker.NAME, version = "@VERSION_INJECT@")
public class ZenTweaker
{
    public static final String MODID = "zentweaker";
    public static final String NAME = "ZenTweaker";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        ZenStructureVillagePieces.registerVillagePieces();
    }
}
