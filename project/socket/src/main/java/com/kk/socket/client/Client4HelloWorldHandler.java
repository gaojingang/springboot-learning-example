package com.kk.socket.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;


public class Client4HelloWorldHandler extends ChannelInboundHandlerAdapter {
	
	
	public Client4HelloWorldHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {		
			ByteBuf readBuffer = (ByteBuf) msg;
			byte[] tempDatas = new byte[readBuffer.readableBytes()];
			readBuffer.readBytes(tempDatas);
			System.out.println("from server:" + new String(tempDatas,CharsetUtil.UTF_8));
		} finally {
			ReferenceCountUtil.release(msg);
		}
		
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("client exceptionCaught method run.");
		ctx.close();
	}
	
	
	
	
	
}
