package com.kk.client;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TCPEchoClientNonblocking {
	
	
	public static void main(String args[]) throws Exception{  
          
          
        String server = "localhost";   
        //�ڶ�������ΪҪ���͵�����˵��ַ���  
        byte[] argument = "hahaha".getBytes();  
        //����е���������������Ϊ�˿ںţ����û�У���˿ں���Ϊ7  
        int servPort = 9696;  
        //����һ���ŵ�������Ϊ������ģʽ  
        SocketChannel clntChan = SocketChannel.open();  
        clntChan.configureBlocking(false);  
        //�����˷�������  
        if (!clntChan.connect(new InetSocketAddress(server, servPort))){  
            //���ϵ���ѯ����״̬��ֱ���������  
            while (!clntChan.finishConnect()){  
                //�ڵȴ����ӵ�ʱ�������ִ�����������Գ�ַ��ӷ�����IO���첽����  
                //����Ϊ����ʾ�÷�����ʹ�ã�ֻ��һֱ��ӡ"."  
                System.out.print(".");    
            }  
        }  
        //Ϊ��������ӡ��"."������������������з�  
        System.out.print("\n");  
        //�ֱ�ʵ����������д�Ļ�����  
        ByteBuffer writeBuf = ByteBuffer.wrap(argument);  
        ByteBuffer readBuf = ByteBuffer.allocate(argument.length);  
        //���յ����ܵ��ֽ���  
        int totalBytesRcvd = 0;   
        //ÿһ�ε���read�����������յ����ֽ���  
        int bytesRcvd;   
        //ѭ��ִ�У�ֱ�����յ����ֽ����뷢�͵��ַ������ֽ������  
        while (totalBytesRcvd < argument.length){  
            //���������ͨ����д���ݵĻ������л���ʣ����ֽڣ������������д���ŵ�  
            if (writeBuf.hasRemaining()){  
                clntChan.write(writeBuf);  
            }  
            //���read�������յ�-1����������˹رգ��׳��쳣  
            if ((bytesRcvd = clntChan.read(readBuf)) == -1){  
                throw new SocketException("Connection closed prematurely");  
            }  
            //������յ������ֽ���  
            totalBytesRcvd += bytesRcvd;  
            //�ڵȴ�ͨ����ɵĹ����У��������ִ���������������ַ�����IO���첽����  
            //����Ϊ����ʾ�÷�����ʹ�ã�ͬ��ֻ��һֱ��ӡ"."  
            System.out.print(".");   
        }  
        //��ӡ�����յ�������  
        System.out.println("Received: " +  new String(readBuf.array(), 0, totalBytesRcvd));  
        //�ر��ŵ�  
        clntChan.close();  
    }  

}
