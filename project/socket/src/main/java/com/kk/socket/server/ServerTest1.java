package com.kk.socket.server;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ServerTest1 {

	private final int port;

	 public ServerTest1(int port) {
	     this.port = port;
	 }
	 
	 public static void main(String[] args) {
		 int port = 9696;
		 try {
	         new ServerTest1(port).start();
	         
	     } catch (InterruptedException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	     }
		 
	}

	 
	 private void start() throws InterruptedException {
	      //final ServerHandler serverHandler = new ServerHandler();
		 final Server4HelloWorldHandler serverHandler =new Server4HelloWorldHandler();
	      EventLoopGroup group = new NioEventLoopGroup();
	      try {
	         ServerBootstrap bootstrap = new ServerBootstrap();
	          bootstrap
	                 .group(group)
	                 .channel(NioServerSocketChannel.class)
	                 //.localAddress(new InetSocketAddress( port))
	                 .childHandler(new ChannelInitializer() {
	                     @Override
	                     protected void initChannel(Channel ch) throws Exception {
	                         ch.pipeline().addLast(serverHandler);
	                     }
	                 });
	          ChannelFuture future = bootstrap.bind(port).sync();
	          ChannelFuture future2 = bootstrap.bind(9697).sync();
	          
	          
	          System.out.println("server started.");
	          future.channel().closeFuture().sync();
	          future2.channel().closeFuture().sync();
	          
	          
	          
	     } finally {
	         group.shutdownGracefully().sync();
	     }

	 }

}
