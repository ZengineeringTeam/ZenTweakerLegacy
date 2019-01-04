package snownee.zentweaker;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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
}
