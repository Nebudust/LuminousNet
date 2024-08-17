package com.natukikana.luminousnet.server.aime;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class AimeServer {
    private final int port;

    public AimeServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        final AimeServerHandler serverHandler = new AimeServerHandler();
        //1. 创建EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //2.创建Server-Bootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    //3.指定所使用的NIO传输Channel
                    .channel(NioServerSocketChannel.class)
                    //4.使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    //5.添加一个AimeServerHandler到子Channel的ChannelPipeline
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(serverHandler);
                        }
                    });
            //6. 异步绑定服务器，调用sync（）方法阻塞等待直到绑定完成
            ChannelFuture f = b.bind().sync();
            //7. 获取Channel的CloseFuture，并且阻塞当前线程直到它完成
            f.channel().closeFuture().sync();
        } finally {
            //8. 关闭EventLoopGroup,释放所有资源
            group.shutdownGracefully().sync();
        }
    }
}