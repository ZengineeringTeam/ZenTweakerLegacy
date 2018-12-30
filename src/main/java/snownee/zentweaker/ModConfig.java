package snownee.zentweaker;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = ZenTweaker.MODID, name = ZenTweaker.MODID, category = "")
@Mod.EventBusSubscriber(modid = ZenTweaker.MODID)
public final class ModConfig
{
    private ModConfig()
    {
    }

    @Config.Name("Reduced Debug Info")
    public static final ReducedDebugInfo ReducedDebugInfo = new ReducedDebugInfo();

    public static final class ReducedDebugInfo
    {
        ReducedDebugInfo()
        {
        }

        @Config.RequiresMcRestart
        public String Barometer = "inspirations:barometer";

        @Config.RequiresMcRestart
        public String Compass = "minecraft:compass";

    }

    @SubscribeEvent
    public static void onConfigReload(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(ZenTweaker.MODID))
        {
            ConfigManager.sync(ZenTweaker.MODID, Config.Type.INSTANCE);
        }
    }
}
