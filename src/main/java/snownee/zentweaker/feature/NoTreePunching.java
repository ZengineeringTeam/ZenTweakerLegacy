package snownee.zentweaker.feature;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
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

@KiwiModule(modid = ZenTweaker.MODID, name = "NoTreePunching", optional = true)
public class NoTreePunching implements IModule
{
    @ObjectHolder("mekanism:atomicdisassembler")
    private static final Item DISASSEMBLER = null;

    private static final int ORE_LOG_WOOD = OreDictionary.getOreID("logWood");

    private ItemFlintAxe itemFlintAxe;
    private ItemFlintPickaxe itemFlintPickaxe;
    private ItemFlintShovel itemFlintShovel;

    public NoTreePunching()
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

    @SubscribeEvent
    public void onPlayerBreaking(PlayerEvent.BreakSpeed event)
    {
        if (isLog(event.getState(), event.getEntityPlayer().world, event.getPos()) && !canPunchTree(event.getEntityPlayer()))
        {
            event.setNewSpeed(event.getOriginalSpeed() / 4);
        }
    }

    @SubscribeEvent
    public void onBlockDrops(BlockEvent.HarvestDropsEvent event)
    {
        if (event.getHarvester() == null || !isLog(event.getState(), event.getWorld(), event.getPos()))
        {
            return;
        }
        if (!canPunchTree(event.getHarvester()))
        {
            event.getDrops().clear();
        }
    }

    public static boolean canPunchTree(EntityPlayer player)
    {
        if (player == null)
        {
            return false;
        }
        ItemStack tool = player.getHeldItemMainhand();
        if (DISASSEMBLER != null && tool.getItem() == DISASSEMBLER)
        {
            return true;
        }
        return !tool.isEmpty() && tool.getItem().getToolClasses(tool).contains("axe");
    }

    public static boolean isLog(IBlockState state, World world, BlockPos pos)
    {
        NonNullList<ItemStack> drops = NonNullList.create();
        state.getBlock().getDrops(drops, world, pos, state, 0);
        if (drops.size() < 1)
        {
            return false;
        }
        ItemStack itemblock = drops.get(0);
        if (itemblock.isEmpty())
        {
            return false;
        }
        for (int id : OreDictionary.getOreIDs(itemblock))
        {
            if (ORE_LOG_WOOD == id)
            {
                return true;
            }
        }
        return false;
    }
}
