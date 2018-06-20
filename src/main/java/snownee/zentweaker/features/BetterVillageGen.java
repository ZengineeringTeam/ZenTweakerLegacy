package snownee.zentweaker.features;

import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import snownee.zentweaker.worldgen.ZenMapGenVillage;

public class BetterVillageGen
{
    @SubscribeEvent
    public void onGenerate(InitMapGenEvent event)
    {
        if (event.getType() == InitMapGenEvent.EventType.VILLAGE)
        {
            event.setNewGen(new ZenMapGenVillage());
        }
    }
}
