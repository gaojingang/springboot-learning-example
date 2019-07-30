package com.kk.nettytest.delimiter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;


public class Client4DeLimiterhHandler extends ChannelInboundHandlerAdapter {
	
	
	public Client4DeLimiterhHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {	
			
			
			String message = msg.toString();
			System.out.println("from server:" + message);
			
			
			//使用了 StringCodec 之后就不能再对 msg进行对象转换了，默认就是string，否正会抛异常。
//			ByteBuf readBuffer = (ByteBuf) msg;
//			byte[] tempDatas = new byte[readBuffer.readableBytes()];
//			readBuffer.readBytes(tempDatas);
//			System.out.println("from server:" + new String(tempDatas,CharsetUtil.UTF_8));
			
		} finally {
			ReferenceCountUtil.release(msg);
		}
		
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("client exceptionCaught method run.");
		cause.printStackTrace();
		ctx.close();
	}
	
	
	
	
	
}
