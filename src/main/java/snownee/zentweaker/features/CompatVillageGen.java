package snownee.zentweaker.features;

import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import snownee.zentweaker.worldgen.StructureVillage;

@EventBusSubscriber
public class CompatVillageGen
{
    @SubscribeEvent
    public void onGenerate(InitMapGenEvent event)
    {
        System.out.println("gggggggggggggggggggggg");
        if (event.getType() == InitMapGenEvent.EventType.VILLAGE)
        {
            System.out.println("gennnnnnnnnnnnnnnnnnnnn");
            event.setNewGen(new StructureVillage());
        }
    }
}
