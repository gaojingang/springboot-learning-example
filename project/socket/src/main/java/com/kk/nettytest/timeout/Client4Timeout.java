package com.kk.nettytest.timeout;

import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.util.CharsetUtil;

public class Client4Timeout {
	
	//请求线程组和处理服务器端相应的
	private EventLoopGroup mGroup = null;
	//客户端启动相关配置信息
	private Bootstrap mBootstrap = null;
	
	private ChannelFuture mFuture = null;
	
	public Client4Timeout() {
		init();

	}
	
	
	
	private void init() {
		mGroup = new NioEventLoopGroup();
		mBootstrap = new Bootstrap();
		
		//绑定线程组
		mBootstrap.group(mGroup);
		//设置通讯模式为 NIO ，同步非阻塞。
		mBootstrap.channel(NioSocketChannel.class);		
	}

	private void release() {
		mGroup.shutdownGracefully();
	}
	
	private ChannelFuture doRequest(String host ,int port , final ChannelHandler... handlers ) throws InterruptedException {
		mBootstrap.handler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				
				ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
				//写操作，指定时间内，没有写的操作，自动断线
				ch.pipeline().addLast(new WriteTimeoutHandler(3));
				ch.pipeline().addLast(new Client4TimeoutHandler());
			}
		
		});
		ChannelFuture future = mBootstrap.connect(host, port).sync();
		return future;
	}
	
	public ChannelFuture getChannelFuture(String host,int port,ChannelFuture future) throws InterruptedException {
		if (null == future) {
			future = mBootstrap.connect(host, port).sync();
		}
		
		if (!future.channel().isActive()) {
			future = mBootstrap.connect(host, port).sync();
		}
		
		return future;
	}
	

	public static void main(String[] args) {
		Client4Timeout client = null;
		ChannelFuture future = null;
		
		String host = "localhost";
		int port = 9696;
		
		
		try {
			client = new Client4Timeout();
			//传递 服务器 端口  处理器
			future = client.doRequest(host, port,new Client4TimeoutHandler());
			
			for (int i = 0; i < 3; i++) {
				System.out.println("enter message send to server.(enter ‘exit' for close client)>");
				String line =  "kkk:" +i;
				line = Client4TimeoutHandler.ProtocolPaser.transforTo(line);
				future.channel().writeAndFlush(Unpooled.copiedBuffer(line.getBytes(CharsetUtil.UTF_8)));
				TimeUnit.SECONDS.sleep(2);
			}
			TimeUnit.SECONDS.sleep(5);			
			
			
			future = client.getChannelFuture(host,port,future);
			String reConnect =  "reConnect:";
			reConnect = Client4TimeoutHandler.ProtocolPaser.transforTo(reConnect);
			future.channel().writeAndFlush(Unpooled.copiedBuffer(reConnect.getBytes(CharsetUtil.UTF_8)));
			TimeUnit.SECONDS.sleep(2);
			
			reConnect =  "reConnect:222";
			reConnect = Client4TimeoutHandler.ProtocolPaser.transforTo(reConnect);
			future.channel().writeAndFlush(Unpooled.copiedBuffer(reConnect.getBytes(CharsetUtil.UTF_8)));
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			client.release();
		}
		
	}
	
	
	
	
}
