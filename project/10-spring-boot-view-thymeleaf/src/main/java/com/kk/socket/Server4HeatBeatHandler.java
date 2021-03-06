package com.kk.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

/***
 * 
 * 
 * @author gaoji
 *
 *@Sharable 代表当前的handler 是一个可以分享的处理器，也就意味着，服务器注册此Handler之后，可以分享给多个客户端同时使用。
 *如果不使用此注解，则每次客户端请求的时候，必须为客户端创建一个新的Handler对象，就是就需要使用 new Server4HelloWorldHandler
 *	另外需要注意 ，避免出现 可写的实例变量。
 */

@Sharable
public class Server4HeatBeatHandler extends ChannelInboundHandlerAdapter{
	
	static String HEATBEAT_SUCCESS = "SERVER_HEATBEAT_SUCCESS";
	
	static ChannelGroup allFutures = new DefaultChannelGroup("allFutures", GlobalEventExecutor.INSTANCE);
	
	/****
	 * 业务处理逻辑
	 * 用于处理 读取数据请求逻辑。
	 * ctx 上下文对象，其中包含与客户端建立连接的所有资源 ，eg 对应 的channel  
	 * msg 读取到的数据，默认类型 byteBuf 类型，属于 netty包。是对 ByteBuffer 的封装。 不需要考虑复位问题。
	 * 
	 * 
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		String message = msg.toString();
		System.out.println("client receive protocol content:"+message);
		//消息格式化 ，消息头 （自定义协议），token校验，签名验证  
		
		if (null != allFutures) {
			allFutures.add(ctx.channel());
		}
		
		
		
		//判断消息是否为 心跳包，
		if ("Hi".startsWith(message)) {
			
			System.out.println("收到消息，通知客户端发送心跳包");
	
			//写操作自动释放缓存，避免内存泄漏
			ctx.writeAndFlush(Unpooled.copiedBuffer(HEATBEAT_SUCCESS.getBytes(CharsetUtil.UTF_8)));
			
		}else if ("ALL".startsWith(message)) {
			
			System.out.println("全局广播");
			String line = "====================Welcome Everyone=================";
				
			if (null != allFutures && !allFutures.isEmpty()) {
				//写操作自动释放缓存，避免内存泄漏
				allFutures.writeAndFlush(Unpooled.copiedBuffer(line.getBytes(CharsetUtil.UTF_8)));
			}

			
		} else {
			
			String line = "server message";
			
			//写操作自动释放缓存，避免内存泄漏
			ctx.writeAndFlush(Unpooled.copiedBuffer(line.getBytes(CharsetUtil.UTF_8)));
			
		}
		


		
	}
	
	/***
	 *  异常处理逻辑，当客户端异常退出时也会运行。
	 *  ChannelHandlerContext 关闭，也代表当前与客户端的连接被关闭，资源被释放。
	 * 
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("server exceptionCaught method run...");
		ctx.close();
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelRegistered:" + ctx.channel().toString());
		super.channelRegistered(ctx);
	}
	
	
}
