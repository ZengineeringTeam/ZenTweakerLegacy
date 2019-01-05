package snownee.zentweaker.feature;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
import snownee.zentweaker.ModConfig;
import snownee.zentweaker.ZenTweaker;

@KiwiModule(modid = ZenTweaker.MODID, name = "SurfaceFossil", optional = true)
public class SurfaceFossil implements IModule
{
    private static final ResourceLocation STRUCTURE_SPINE_01 = new ResourceLocation("fossils/fossil_spine_01");
    private static final ResourceLocation STRUCTURE_SPINE_02 = new ResourceLocation("fossils/fossil_spine_02");
    private static final ResourceLocation STRUCTURE_SPINE_03 = new ResourceLocation("fossils/fossil_spine_03");
    private static final ResourceLocation STRUCTURE_SPINE_04 = new ResourceLocation("fossils/fossil_spine_04");
    private static final ResourceLocation STRUCTURE_SKULL_01 = new ResourceLocation("fossils/fossil_skull_01");
    private static final ResourceLocation STRUCTURE_SKULL_02 = new ResourceLocation("fossils/fossil_skull_02");
    private static final ResourceLocation STRUCTURE_SKULL_03 = new ResourceLocation("fossils/fossil_skull_03");
    private static final ResourceLocation STRUCTURE_SKULL_04 = new ResourceLocation("fossils/fossil_skull_04");
    private static final ResourceLocation[] FOSSILS = new ResourceLocation[] { STRUCTURE_SPINE_01, STRUCTURE_SPINE_02, STRUCTURE_SPINE_03, STRUCTURE_SPINE_04, STRUCTURE_SKULL_01, STRUCTURE_SKULL_02, STRUCTURE_SKULL_03, STRUCTURE_SKULL_04 };

    public SurfaceFossil()
    {
        MinecraftForge.TERRAIN_GEN_BUS.register(this);
    }

    @SubscribeEvent
    public void decorateEvent(Decorate event)
    {
        World world = event.getWorld();
        Random rand = event.getRand();
        if (world.provider.getDimension() != 0 || event.getType() != EventType.SAND_PASS2 || rand.nextInt(ModConfig.SurfaceFossil.Chance) > 0)
        {
            return;
        }

        BlockPos pos = event.getChunkPos().getBlock(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8);

        Biome biome = world.getBiome(pos);
        if (BiomeDictionary.getTypes(biome).stream().anyMatch(t -> t == Type.BEACH || t == Type.RIVER || t == Type.OCEAN))
        {
            return;
        }

        pos = world.getHeight(pos).down();
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof IFluidBlock || !biome.topBlock.equals(state))
        {
            return;
        }

        pos = pos.down(rand.nextInt(3));
        generateFossil(world, rand, pos);
    }

    private void generateFossil(World world, Random random, BlockPos pos)
    {
        System.out.println(pos);
        MinecraftServer minecraftserver = world.getMinecraftServer();
        Rotation[] arotation = Rotation.values();
        Rotation rotation = arotation[random.nextInt(arotation.length)];
        int i = random.nextInt(FOSSILS.length);
        TemplateManager templatemanager = world.getSaveHandler().getStructureTemplateManager();
        Template template = templatemanager.getTemplate(minecraftserver, FOSSILS[i]);
        ChunkPos chunkpos = new ChunkPos(pos);
        StructureBoundingBox structureboundingbox = new StructureBoundingBox(chunkpos.getXStart(), 0, chunkpos.getZStart(), chunkpos.getXEnd(), 256, chunkpos.getZEnd());
        PlacementSettings placementsettings = (new PlacementSettings()).setRotation(rotation).setBoundingBox(structureboundingbox).setRandom(random);
        BlockPos blockpos = template.transformedSize(rotation);

        BlockPos blockpos1 = template.getZeroPositionWithTransform(pos, Mirror.NONE, rotation);
        placementsettings.setIntegrity(1F);
        template.addBlocksToWorld(world, blockpos1, placementsettings, 20);
    }

}
