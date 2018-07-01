package snownee.zentweaker.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.util.FakePlayer;
import snownee.zentweaker.features.EmptyHandToDrink;

public class PlayerDrinkPacket implements ZTPacket
{
    public PlayerDrinkPacket()
    {
    }

    public PlayerDrinkPacket(EntityPlayer player)
    {
    }

    @Override
    public void writeDataTo(ByteBuf buffer)
    {
    }

    @Override
    public void readDataFrom(ByteBuf buffer)
    {
    }

    @Override
    public void handleClient(EntityPlayerSP player)
    {
    }

    @Override
    public void handleServer(EntityPlayerMP player)
    {
        if (player == null || player instanceof FakePlayer || player.isSneaking() || !player.onGround
                || player.isOnLadder() || !player.getHeldItemMainhand().isEmpty()
                || !player.getHeldItemOffhand().isEmpty())
        {
            return;
        }
        if (EmptyHandToDrink.isFacingToWater(player.world, player))
        {
            EmptyHandToDrink.doDrink(player.world, player);
        }
    }

}
