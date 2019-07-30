package com.kk.serve;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class EchoSelectorProtocol implements TCPProtocol {

	private int bufSize ;
	
	public EchoSelectorProtocol(int bufSize) {
		// TODO Auto-generated constructor stub
		this.bufSize = bufSize;
	}
	
	
	
	@Override
	public void handleAccept(SelectionKey key) throws IOException {
	       	SocketChannel clntChan = ((ServerSocketChannel) key.channel()).accept();  
	        clntChan.configureBlocking(false);  
	        //将选择器注册到连接到的客户端信道，并指定该信道key值的属性为OP_READ，同时为该信道指定关联的附件  
	        clntChan.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));  
	    }  

	@Override
	public void handleRead(SelectionKey key) throws IOException {
		SocketChannel sc = (SocketChannel) key.channel();  
        //获取该信道所关联的附件，这里为缓冲区  
        ByteBuffer buffer = (ByteBuffer) key.attachment();  

        int bytesRead = sc.read(buffer);  
        //如果read（）方法返回-1，说明客户端关闭了连接，那么客户端已经接收到了与自己发送字节数相等的数据，可以安全地关闭  
        if(bytesRead > 0){  

        	//将缓冲区当前的limit设置为position=0，用于后续对缓冲区的读取操作
        	buffer.flip();
        	
        	//根据缓冲区可读字节数创建字节数组
			byte[] bytes = new byte[buffer.remaining()];
			//将缓冲区可读字节数组复制到新建的数组中
			buffer.get(bytes);
			String expression = new String(bytes,"UTF-8");
			System.out.println("服务器收到消息：" + expression);

			
			//处理数据
			String result = null;
			try{
				//对传输过来的数据进行处理
				result = "Server calc:" +expression;
				
			}catch(Exception e){
				result = "计算错误：" + e.getMessage();
			}
			//发送应答消息
			doWrite(sc,result);
        	
        	
        	//如果缓冲区总读入了数据，则将该信道感兴趣的操作设置为为可读可写  
        	key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            
            
            
        }else if (bytesRead <0) {
        	System.out.println();
        	key.cancel();
        	sc.close();
        	
        }
        
     
	}

	private void doWrite(SocketChannel channel, String response) throws IOException {
			//将消息编码为字节数组
			byte[] bytes = response.getBytes();
			//根据数组容量创建ByteBuffer
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			//将字节数组复制到缓冲区
			writeBuffer.put(bytes);
			//flip操作
			writeBuffer.flip();
			//发送缓冲区的字节数组
			channel.write(writeBuffer);
			//****此处不含处理“写半包”的代码

	}



	@Override
	public void handleWrite(SelectionKey key) throws IOException {
	    //获取与该信道关联的缓冲区，里面有之前读取到的数据  
	    ByteBuffer buf = (ByteBuffer) key.attachment();  
	    //重置缓冲区，准备将数据写入信道  
	    buf.flip();   
	    SocketChannel clntChan = (SocketChannel) key.channel();  
	    //将数据写入到信道中  
	    buf.clear();
	    buf.put("Hi,Client,haha!".getBytes());
	    buf.flip();
	    clntChan.write(buf);  
	    if (!buf.hasRemaining()){   
	    //如果缓冲区中的数据已经全部写入了信道，则将该信道感兴趣的操作设置为可读  
	      key.interestOps(SelectionKey.OP_READ);  
	    }  
	    //为读入更多的数据腾出空间  
	    buf.compact();   
	}

}
