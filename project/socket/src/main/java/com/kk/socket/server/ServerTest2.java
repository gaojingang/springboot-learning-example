package com.kk.socket.server;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ServerTest2 {

	//监听线程组，监听客户连接请求
	private EventLoopGroup mAcceptGroup = null;
	//监听线程组，负责客户端与用户通讯
	private EventLoopGroup mClientGroup = null;
	//服务器 启动 配置相关信息
	private ServerBootstrap mBootstrap = null;
	

	 public ServerTest2() {
	     init();
	 }
	 
	 
	 
	 private void init() {
		// TODO Auto-generated method stub
			//初始化 线程组
			mAcceptGroup = new NioEventLoopGroup();
			//初始化 线程组，		
			mClientGroup = new NioEventLoopGroup();
			//初始化配置
			mBootstrap = new ServerBootstrap();
			//绑定线程组
			mBootstrap.group(mAcceptGroup,mClientGroup);
			//设置 NIO 模式，同步非阻塞
			mBootstrap.channel(NioServerSocketChannel.class);
			//设置缓存区
			mBootstrap.option(ChannelOption.SO_BACKLOG,1024);
			
			
			//SO_SNDBUF 发送缓冲区，SO_RCVBUF 接受缓冲区， SO_KEEPALIVE 开启心跳检测 (保证连接有效)
			mBootstrap.option(ChannelOption.SO_SNDBUF,16*1024);
			mBootstrap.option(ChannelOption.SO_RCVBUF,16*1024);
			mBootstrap.option(ChannelOption.SO_KEEPALIVE,true);
	}





	public void release() {
		this.mAcceptGroup.shutdownGracefully();
		this.mClientGroup.shutdownGracefully();
	}
	
	 
	 private ChannelFuture doAccept(int port ,final ChannelHandler... handlers) throws InterruptedException {
	          mBootstrap
	                 .childHandler(new ChannelInitializer() {
	                     @Override
	                     protected void initChannel(Channel ch) throws Exception {
	                         ch.pipeline().addLast(handlers);
	                     }
	                 });
	          ChannelFuture future = mBootstrap.bind(port).sync();
	          //ChannelFuture future2 = mBootstrap.bind(9690).sync();
	          //ChannelFuture future3 = mBootstrap.bind(9691).sync();
	          
	          
	          System.out.println("server started.");
	          //future.channel().closeFuture().sync();
	          //future2.channel().closeFuture().sync();
	          //future3.channel().closeFuture().sync();
	          
	          return future;

	 }
	

		public static void main(String[] args) {
			 int port = 9696;
			 ServerTest2 server = null;
			 ChannelFuture future = null;
			 
			 try {
				 server = new ServerTest2();
				 future = server.doAccept(port,new Server4HelloWorldHandler());
				 future.channel().closeFuture().sync();
		     } catch (InterruptedException e) {
		         // TODO Auto-generated catch block
		         e.printStackTrace();
		     } finally {
		    	if ( null != future) {
		    		try {
						future.channel().closeFuture().sync();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
		    	
		    	if (null != server) {
		    		server.release();
		    	}
		    	
			}
			 
		}
	 
}

