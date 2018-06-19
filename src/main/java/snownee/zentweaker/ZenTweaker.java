package snownee.zentweaker;

import org.apache.logging.log4j.Logger;

import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import snownee.zentweaker.features.CompatVillageGen;
import snownee.zentweaker.worldgen.PieceVillageHouse;
import snownee.zentweaker.worldgen.StructureVillage;

@Mod(modid = ZenTweaker.MODID, name = ZenTweaker.NAME, version = ZenTweaker.VERSION)
public class ZenTweaker
{
    public static final String MODID = "zentweaker";
    public static final String NAME = "ZenTweaker";
    public static final String VERSION = "@VERSION_INJECT@";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        MapGenStructureIO.registerStructure(StructureVillage.Start.class, "ZenVillage");
        MapGenStructureIO.registerStructureComponent(PieceVillageHouse.class, "ZenViBH");
        MinecraftForge.TERRAIN_GEN_BUS.register(new CompatVillageGen());
    }
}
