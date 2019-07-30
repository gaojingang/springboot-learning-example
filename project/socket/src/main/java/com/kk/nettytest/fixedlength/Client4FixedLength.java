package com.kk.nettytest.fixedlength;

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
import io.netty.util.CharsetUtil;

public class Client4FixedLength {
	
	//请求线程组和处理服务器端相应的
	private EventLoopGroup mGroup = null;
	//客户端启动相关配置信息
	private Bootstrap mBootstrap = null;
	
	public Client4FixedLength() {
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
	
	private ChannelFuture doRequest(String host ,int port ) throws InterruptedException {
		mBootstrap.handler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelHandler[] handlers = new ChannelHandler[3];
				//定长包，定义每个包的长度
				handlers[0] = new FixedLengthFrameDecoder(3);
				handlers[1] = new StringDecoder(CharsetUtil.UTF_8);
				handlers[2] = new Client4FixedLengthHandler();
				
				ch.pipeline().addLast(handlers);
			}
		
		});
		ChannelFuture future = mBootstrap.connect(host, port).sync();
		return future;
	}


	public static void main(String[] args) {
		Client4FixedLength client = null;
		String host = "localhost";
		int port = 9696;
		
		
		try {
			client = new Client4FixedLength();
			//传递 服务器 端口  处理器
			ChannelFuture future = client.doRequest(host, port);
			
			Scanner s = null;
			while (true) {
				s = new Scanner(System.in);
				System.out.println("enter message send to server.(enter ‘exit' for close client)>");
				String line = s.nextLine() ;

				future.channel().writeAndFlush(Unpooled.copiedBuffer(line.getBytes(CharsetUtil.UTF_8)));
				TimeUnit.SECONDS.sleep(1);
			}
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
}
