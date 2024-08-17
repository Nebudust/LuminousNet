package com.natukikana.luminousnet.server.aime;

import com.natukikana.luminousnet.utils.AimeUtils;
import com.natukikana.luminousnet.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable //标示一个Channel-Handler可以被多个Channel安全地共享
public class AimeServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 每个传入的消息都会调用该方法
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        byte[] data = ByteBufUtils.toBytes(AimeUtils.Decrypt(in));
        switch (data[4])
        {
            case 0x0B:
                // Maybe ping command
                pingHandler(ctx, data);
                break;

            case 0x66:
                // Goodbye command;
                break;
        }
    }

    /*
     *通知ChannelInboundHander最后一次对channelRead()的调用是当前批量读取中的最后一条消息
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        /**
         * 将目前暂存于ChannelOutboundBuffer中的消息
         *冲刷到远程节点，并且关闭该Channel
         */
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER);
    }


    /*
     * 在读取期间，有异常抛出时会调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 打印异常栈跟踪
        cause.printStackTrace();
        //关闭该channel
        ctx.close();
    }

    void pingHandler(ChannelHandlerContext ctx, byte[] src)
    {
        String srcStr = new String(src);
        String gameID = srcStr.substring(10, 14);
        String keychipID = srcStr.substring(20, 31);
        byte[] data = {0x3E, (byte) 0xA1, 0x21, 0x40, 0x0C, 0x00, 0x00, 0x02, 0x01, 0x00};
        byte[] sendData = new byte[512];
        System.arraycopy(data, 0, sendData, 0, data.length);
        System.arraycopy(gameID.getBytes(), 0, sendData, 10, 4);
        System.arraycopy(keychipID.getBytes(), 0, sendData, 20, 11);
        ctx.write(Unpooled.copiedBuffer(ByteBufUtils.toBytes(AimeUtils.Encrypt(sendData))));
    }

    void goodbyeHandler(ChannelHandlerContext ctx)
    {
        byte[] data = {0x3E, (byte) 0xA1, 0x21, 0x40, 0x66, 0x00, 0x20};
        byte[] sendData = new byte[32];
        System.arraycopy(data, 0, sendData, 0, data.length);
        ctx.write(Unpooled.copiedBuffer(ByteBufUtils.toBytes(AimeUtils.Encrypt(sendData))));
    }
}