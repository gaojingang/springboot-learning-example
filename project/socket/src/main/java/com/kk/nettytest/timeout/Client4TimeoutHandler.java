package com.kk.nettytest.timeout;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class Client4TimeoutHandler extends ChannelInboundHandlerAdapter {

	public Client4TimeoutHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			String message = msg.toString();
			System.out.println("client receive protocol content:" + message);

			message = ProtocolPaser.parser(message);
			if (null == message) {
				System.out.println("error request from server");
				return;
			}
			System.out.println("from server:" + message);

			// 使用了 StringCodec 之后就不能再对 msg进行对象转换了，默认就是string，否正会抛异常。
//			ByteBuf readBuffer = (ByteBuf) msg;
//			byte[] tempDatas = new byte[readBuffer.readableBytes()];
//			readBuffer.readBytes(tempDatas);
//			System.out.println("from server:" + new String(tempDatas,CharsetUtil.UTF_8));

		} finally {
			ReferenceCountUtil.release(msg);
		}

	}

	/**
	 *   当连接建立成功之后，触法一次。
	 *   当一次连接只触发一次，可以视为 init方法使用。
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("clent channel active");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.err.println("client exceptionCaught method run.");
		cause.printStackTrace();
		ctx.close();
	}

	static class ProtocolPaser {
		
		static String parser(String message) {
			String[] temp = message.split("HEADBODY");
			temp[0] =temp[0].substring(4);
			temp[1] =temp[1].substring(0,temp[1].length() -4);
			
			int length = Integer.parseInt(temp[0].substring(temp[0].indexOf(":")+1));
			if (temp[1].length() != length) {
				return null;
			}
			
			return temp[1];
			
			
		}
		
		static String transforTo(String message) {
			return  message = "HEADcontent-length:"+message.length()+"HEADBODY"+message+"BODY";
			
			
		}
	}
	
	
}
