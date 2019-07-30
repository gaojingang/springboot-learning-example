package com.kk.nettytest.heatbeat;

import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Who;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.ScheduledFuture;

public class Client4HeatBeatHandler extends ChannelInboundHandlerAdapter {
	
	
	static String HEATBEAT_SUCCESS = "SERVER_HEATBEAT_SUCCESS";
	
	private ScheduledExecutorService executorServer = Executors.newScheduledThreadPool(1);
	private ScheduledFuture heatbeat;
	
	public Client4HeatBeatHandler() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//消息建立  ,发送token 或者标识，用来标记为正常的连接
		
		
		String line = "Hi";
		ctx.channel().writeAndFlush(Unpooled.copiedBuffer(line.getBytes(CharsetUtil.UTF_8)));
	
	}




	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			String message = msg.toString();
			System.out.println("client receive protocol content:" + message);
			
			
			if (HEATBEAT_SUCCESS.equals(msg)) {
				//处理心跳包
				executorServer.scheduleWithFixedDelay(new HeatbeatTask(ctx),0,30L,TimeUnit.SECONDS);
			} else {
				//处理正常的消息
				
				
			}
			
			



		} finally {
			ReferenceCountUtil.release(msg);
		}

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("client exceptionCaught method run.");
		cause.printStackTrace();
		if (null != heatbeat ) {
			heatbeat.cancel(true);
			heatbeat = null;
		}
		
		ctx.close();
	}


	
	
	public class HeatbeatTask implements Runnable{
		
		ChannelHandlerContext ctx;
		
		public HeatbeatTask() {
			// TODO Auto-generated constructor stub
		}
		
		public HeatbeatTask(ChannelHandlerContext ctx) {
			this.ctx = ctx;
		}
		
		public void run() {
			StringBuffer msg = new StringBuffer();
			
			msg.append("HeatbeatMsg").append("\n");
			
			Sigar sigar = new Sigar();
			
			try {
				Who[] whos = sigar.getWhoList();
				
				for (Who who : whos) {
					msg.append(who.toString()).append("\n");
				}

			} catch (SigarException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//写操作自动释放缓存，避免内存泄漏
			ctx.writeAndFlush(Unpooled.copiedBuffer(msg.toString().getBytes(CharsetUtil.UTF_8)));

		}
		
	}
	
	
}
