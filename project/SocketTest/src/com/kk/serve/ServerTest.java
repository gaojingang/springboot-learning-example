package com.kk.serve;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class ServerTest {
	static int port = 9696;
	static int BUFSIZE = 1024;
	static int TIMEOUT = 3000;
			
	
	public static void main(String[] args) {
		
		try {
			Selector selector = Selector.open();
			ServerSocketChannel serverChannel = ServerSocketChannel.open();
			serverChannel.bind(new InetSocketAddress(port));
			serverChannel.configureBlocking(false);
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			 //创建一个实现了协议接口的对象  
	        TCPProtocol protocol = new EchoSelectorProtocol(BUFSIZE); 
			while (true) {
				if (selector.select(TIMEOUT) == 0) {
					System.out.println(".");
					continue;
				}
				
				Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
				SelectionKey key = null;
				while (keyIter.hasNext()) {
					key = keyIter.next();
					if (key.isValid()) {
						System.out.println("key="+key);
						if (key.isAcceptable()) {
							System.out.println("acceptable");

							
							protocol.handleAccept(key);
						}
						
						if (key.isReadable()) {
							System .out.println("read");
							
							protocol.handleRead(key);
						}
						
						if (key.isWritable()) {
							System.out.println("write");
							protocol.handleWrite(key);
						}

					}
					
					keyIter.remove();
					
				}
				
				
				
			}
			
			
			
			
			
			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
