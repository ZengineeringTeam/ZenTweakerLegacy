package snownee.zentweaker.feature;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import snownee.kiwi.IModule;
import snownee.kiwi.KiwiModule;
import snownee.zentweaker.ModConfig;
import snownee.zentweaker.ZenTweaker;
import vazkii.patchouli.common.base.PatchouliSounds;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;
import vazkii.patchouli.common.network.NetworkHandler;
import vazkii.patchouli.common.network.message.MessageOpenBookGui;

@KiwiModule(modid = ZenTweaker.MODID, name = "QuestBookOpenPatchouli", optional = true, dependency = "patchouli")
public class QuestBookOpenPatchouli implements IModule
{
    private Item item;

    @Override
    public void init()
    {
        item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(ModConfig.QuestBookOpenPatchouli.QuestBook));
        if (item != null)
        {
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickItem event)
    {
        EntityPlayer player = event.getEntityPlayer();
        if (!player.isSneaking())
        {
            return;
        }

        ItemStack stack = event.getItemStack();
        if (stack.getItem() != item)
        {
            return;
        }
        event.setCanceled(true);

        Book book = BookRegistry.INSTANCE.books.get(new ResourceLocation(ModConfig.QuestBookOpenPatchouli.BookID));
        if (book == null)
        {
            event.setCancellationResult(EnumActionResult.FAIL);
            return;
        }

        if (player instanceof EntityPlayerMP)
        {
            NetworkHandler.INSTANCE.sendTo(new MessageOpenBookGui(book.resourceLoc.toString()), (EntityPlayerMP) player);
            SoundEvent sfx = PatchouliSounds.getSound(book.openSound, PatchouliSounds.book_open);
            event.getWorld().playSound(null, player.posX, player.posY, player.posZ, sfx, SoundCategory.PLAYERS, 1F, (float) (0.7 + Math.random() * 0.4));
        }

        event.setCancellationResult(EnumActionResult.SUCCESS);
        return;
    }

}
