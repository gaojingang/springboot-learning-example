package com.kk.nettytest.timeout;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.CharsetUtil;

public class Server4Timeout {
	
	//监听线程组，监听客户连接请求
	private EventLoopGroup acceptGroup = null;
	//监听线程组，负责客户端与用户通讯
	private EventLoopGroup clientGroup = null;
	//服务器 启动 配置相关信息
	private ServerBootstrap bootstrap = null;
	
	public Server4Timeout() {
		init();
	}
	
	
	private void init() {
		//初始化 线程组 如果不传递参数，默认创建的是CPU核心数的线程。
		acceptGroup = new NioEventLoopGroup();
		//初始化 线程组，		
		clientGroup = new NioEventLoopGroup();
		//初始化配置
		bootstrap = new ServerBootstrap();
		//绑定线程组
		bootstrap.group(acceptGroup,clientGroup);
		//设置 NIO 模式，同步非阻塞
		bootstrap.channel(NioServerSocketChannel.class);
		//设置缓存区
		bootstrap.option(ChannelOption.SO_BACKLOG,1024);
		//SO_SNDBUF 发送缓冲区，SO_RCVBUF 接受缓冲区， SO_KEEPALIVE 开启心跳检测 (保证连接有效)
		bootstrap.option(ChannelOption.SO_SNDBUF,16*1024);
		bootstrap.option(ChannelOption.SO_RCVBUF,16*1024);
		bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
	}

	public ChannelFuture doAccept(int port  ) throws InterruptedException {
		
		System.out.println("doAccept");
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				System.out.println("initChannel");

				ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
				// 自动断开 定义多久没有读取时，自动断开 默认时秒 
				ch.pipeline().addLast(new ReadTimeoutHandler(3));
				ch.pipeline().addLast(new Server4TimeoutHandler());
			}
		});
		ChannelFuture future = bootstrap.bind(port).sync();
		System.out.println("bind port:" +port +",future:" + future);
		return future;
		
	}
	
	
	public void release() {
		this.acceptGroup.shutdownGracefully();
		this.clientGroup.shutdownGracefully();
	}
	
	public static void main(String[] args) {
		int port = 9696;
		ChannelFuture future = null;
		Server4Timeout server = null;
		
		try {

			server = new Server4Timeout();
			future = server.doAccept(port);
			System.out.println("server started.");
			
			//关闭连接回收资源 ！@@@@@ 是 closeFuture  不是close！！！！
			//future.channel().close().sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (null != future) {
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
