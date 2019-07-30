package com.kk.socket.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

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
public class Server4HelloWorldHandler extends ChannelInboundHandlerAdapter{
	
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
		System.out.println("channelRead");
		//获取读取到的数据，
		ByteBuf readBuffer = (ByteBuf) msg;
		//创建一个字节数组，用于保存读取到的数据 
		byte[] tempDatas = new byte[readBuffer.readableBytes()];
		//将缓冲中的 数据读取到字节数组中。
		readBuffer.readBytes(tempDatas);
		
		String message = new String(tempDatas,CharsetUtil.UTF_8);
		System.out.println("from client:"+message);
		if ("exit".equals(message)) {
			
			System.out.println("disconnect " + ctx.channel().toString());
			ctx.close();
			return;
		}
		
		String line = "Server message to client.";
		//写操作自动释放缓存，避免内存泄漏
		ctx.writeAndFlush(Unpooled.copiedBuffer(line.getBytes(CharsetUtil.UTF_8)));
		//注意，如果只调用了 write 方法，不会刷新缓存，缓存中的数据不会发送到客户端，必须再次调用fulsh 方法才会将消息发送给客户端。
		//ctx.write();
		//ctx.flush();
		
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
