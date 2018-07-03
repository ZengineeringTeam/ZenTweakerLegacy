package snownee.zentweaker.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import snownee.zentweaker.ZenTweaker;

public final class ZTNetworkChannel
{
    public static final ZTNetworkChannel INSTANCE = new ZTNetworkChannel();

    private final Object2IntMap<Class<? extends ZTPacket>> mapping = new Object2IntArrayMap<>();
    private final Int2ObjectMap<Class<? extends ZTPacket>> mappingReverse = new Int2ObjectArrayMap<>();
    private int nextIndex = 0;
    private final FMLEventChannel channel;

    private ZTNetworkChannel()
    {
        (channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(ZenTweaker.MODID)).register(this);
    }

    @SubscribeEvent
    public void onServerPacketIncoming(FMLNetworkEvent.ServerCustomPacketEvent event)
    {
        handleOnServer(decodeData(event.getPacket().payload()), ((NetHandlerPlayServer) event.getHandler()).player);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientPacketIncoming(FMLNetworkEvent.ClientCustomPacketEvent event)
    {
        handleOnClient(decodeData(event.getPacket().payload()), Minecraft.getMinecraft().player);
    }

    public void sendToAll(ZTPacket packet)
    {
        channel.sendToAll(new FMLProxyPacket(new PacketBuffer(unpack(packet)), ZenTweaker.MODID));
    }

    public void sendToAllAround(ZTPacket packet, int dim, double x, double y, double z, double range)
    {
        channel.sendToAllAround(new FMLProxyPacket(new PacketBuffer(unpack(packet)), ZenTweaker.MODID),
                new NetworkRegistry.TargetPoint(dim, x, y, z, range));
    }

    public void sendToDimension(ZTPacket packet, int dim)
    {
        channel.sendToDimension(new FMLProxyPacket(new PacketBuffer(unpack(packet)), ZenTweaker.MODID), dim);
    }

    public void sendToPlayer(ZTPacket packet, EntityPlayerMP player)
    {
        channel.sendTo(new FMLProxyPacket(new PacketBuffer(unpack(packet)), ZenTweaker.MODID), player);
    }

    public void sendToServer(ZTPacket packet)
    {
        channel.sendToServer(new FMLProxyPacket(new PacketBuffer(unpack(packet)), ZenTweaker.MODID));
    }

    public void register(Class<? extends ZTPacket> klass)
    {
        mapping.put(klass, nextIndex);
        mappingReverse.put(nextIndex, klass);
        nextIndex++;
    }

    private int getPacketIndex(Class<? extends ZTPacket> klass)
    {
        return mapping.getInt(klass);
    }

    private ZTPacket getByIndex(int index)
    {
        try
        {
            return mappingReverse.get(index).newInstance();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private ZTPacket decodeData(ByteBuf buffer)
    {
        final int index = buffer.readInt();
        ZTPacket packet = this.getByIndex(index);
        if (packet == null)
        {
            ZenTweaker.logger.error("Receiving malformed packet");
            return null;
        }
        packet.readDataFrom(buffer);
        return packet;
    }

    @SideOnly(Side.CLIENT)
    private void handleOnClient(ZTPacket packet, EntityPlayerSP player)
    {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            try
            {
                packet.handleClient(player);
            }
            catch (Exception e)
            {
                ZenTweaker.logger.catching(e);
            }
        });
    }

    private void handleOnServer(ZTPacket packet, EntityPlayerMP player)
    {
        FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
            try
            {
                packet.handleServer(player);
            }
            catch (Exception e)
            {
                ZenTweaker.logger.catching(e);
            }
        });
    }

    private ByteBuf unpack(ZTPacket packet)
    {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(this.getPacketIndex(packet.getClass()));
        try
        {
            packet.writeDataTo(buffer);
        }
        catch (Exception e)
        {
            ZenTweaker.logger.catching(e);
        }
        return buffer;
    }
}
