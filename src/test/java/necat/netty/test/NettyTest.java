package necat.netty.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import necat.netty.handler.EchoClientHandler;
import necat.netty.handler.EchoServerHandler;
import necat.test.base.BaseTest;

import org.junit.Test;

public class NettyTest extends BaseTest {

    @Test
    public void testBase() {
        try {
            int port = 33333;
            buildServer(port);
            buildClient(port);
            Thread.sleep(500000);
        } catch (Exception e) {
            System.out.println("caught an exception:" + e);
        }
    }

    public void buildServer(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);

        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                logger.info("server localhost: {}, remotehost: {}", ch.localAddress(), ch.remoteAddress());
                ch.pipeline().addLast(new EchoServerHandler());
            }
        });

        ChannelFuture future = bootstrap.bind(port).sync();
    }

    public void buildClient(int port) throws Exception {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        final Integer msgSize = 1024;
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                logger.info("client localhost:{}, remotehost: {}", ch.localAddress(), ch.remoteAddress());
                ch.pipeline().addLast(new EchoClientHandler(msgSize));
            }
        });
        
        ChannelFuture future = bootstrap.connect("127.0.0.1", port).sync();
        
    }
}
