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
	        //��ѡ����ע�ᵽ���ӵ��Ŀͻ����ŵ�����ָ�����ŵ�keyֵ������ΪOP_READ��ͬʱΪ���ŵ�ָ�������ĸ���  
	        clntChan.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));  
	    }  

	@Override
	public void handleRead(SelectionKey key) throws IOException {
		SocketChannel sc = (SocketChannel) key.channel();  
        //��ȡ���ŵ��������ĸ���������Ϊ������  
        ByteBuffer buffer = (ByteBuffer) key.attachment();  

        int bytesRead = sc.read(buffer);  
        //���read������������-1��˵���ͻ��˹ر������ӣ���ô�ͻ����Ѿ����յ������Լ������ֽ�����ȵ����ݣ����԰�ȫ�عر�  
        if(bytesRead > 0){  

        	//����������ǰ��limit����Ϊposition=0�����ں����Ի������Ķ�ȡ����
        	buffer.flip();
        	
        	//���ݻ������ɶ��ֽ��������ֽ�����
			byte[] bytes = new byte[buffer.remaining()];
			//���������ɶ��ֽ����鸴�Ƶ��½���������
			buffer.get(bytes);
			String expression = new String(bytes,"UTF-8");
			System.out.println("�������յ���Ϣ��" + expression);

			
			//��������
			String result = null;
			try{
				//�Դ�����������ݽ��д���
				result = "Server calc:" +expression;
				
			}catch(Exception e){
				result = "�������" + e.getMessage();
			}
			//����Ӧ����Ϣ
			doWrite(sc,result);
        	
        	
        	//����������ܶ��������ݣ��򽫸��ŵ�����Ȥ�Ĳ�������ΪΪ�ɶ���д  
        	key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            
            
            
        }else if (bytesRead <0) {
        	System.out.println();
        	key.cancel();
        	sc.close();
        	
        }
        
     
	}

	private void doWrite(SocketChannel channel, String response) throws IOException {
			//����Ϣ����Ϊ�ֽ�����
			byte[] bytes = response.getBytes();
			//����������������ByteBuffer
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			//���ֽ����鸴�Ƶ�������
			writeBuffer.put(bytes);
			//flip����
			writeBuffer.flip();
			//���ͻ��������ֽ�����
			channel.write(writeBuffer);
			//****�˴���������д������Ĵ���

	}



	@Override
	public void handleWrite(SelectionKey key) throws IOException {
	    //��ȡ����ŵ������Ļ�������������֮ǰ��ȡ��������  
	    ByteBuffer buf = (ByteBuffer) key.attachment();  
	    //���û�������׼��������д���ŵ�  
	    buf.flip();   
	    SocketChannel clntChan = (SocketChannel) key.channel();  
	    //������д�뵽�ŵ���  
	    buf.clear();
	    buf.put("Hi,Client,haha!".getBytes());
	    buf.flip();
	    clntChan.write(buf);  
	    if (!buf.hasRemaining()){   
	    //����������е������Ѿ�ȫ��д�����ŵ����򽫸��ŵ�����Ȥ�Ĳ�������Ϊ�ɶ�  
	      key.interestOps(SelectionKey.OP_READ);  
	    }  
	    //Ϊ�������������ڳ��ռ�  
	    buf.compact();   
	}

}
