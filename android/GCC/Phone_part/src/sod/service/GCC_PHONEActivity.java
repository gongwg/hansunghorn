package sod.service;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.Semaphore;

import sod.common.ActionEx;
import sod.common.Packet;
import sod.common.ReceiveHandler;
import sod.smartphone.AccessManager;
import sod.smartphone.ServerInfo;

import com.phonegap.*;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;

public class GCC_PHONEActivity extends DroidGap {
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Client_Initalize();
        
    }
    public void InitHTML()
    {
    	super.loadUrl("file:///android_asset/www/index.html");
    }
    public void Client_Initalize() {
  		ConnectionBean.client = new AccessManager();
  		//�� �κ��� TVServerLisrtActivity ���� �����˻� �� �ϱ� ������
  		 	//�ּ�ó��	
  		ConnectionBean.ServerInfomation = new ServerInfo();
  		ConnectionBean.ServerInfomation.EndPoint = new InetSocketAddress(
  				ConnectionBean.SERVERIP, ConnectionBean.SERVERPORT);
  		ConnectionBean.client.setReceiveHandler(new ReceiveHandler() {		// client ReceiveHandler// Ŭ���̾�Ʈ ���ú� �ڵ鷯

  			public void onReceive(Packet pkt) {
  				String temp=pkt.pop().toString();
  				if(pkt != null && temp.equals("down")){
  					Packet pt=new Packet();
  					pt.push("getCard");
  					ConnectionBean.client.send(pt);
  					StrictMode.enableDefaults();
  				}
  			else if (pkt != null && temp.equals("sendCard"))
  				{
  					for(int i=0;i<10;i++)
  					{
  						myPlugin.mMessage+=pkt.pop().toString()+",";
  					}
  					StrictMode.enableDefaults();
  			        InitHTML();
  				}
  				else{
  					while(pkt.getElementCount() > 0)			//packet element count // ��Ŷ ������Ʈ����
  					{
  						myPlugin.DataSign+=pkt.pop().toString();
  					}
  					StrictMode.enableDefaults();
  					myPlugin.waithandle.release();
  				}
  			}
  		});
  		StrictMode.enableDefaults();			// stack default // ���ÿ��� ����Ʈ �ʱ�ȭ
  		
  		
//  		ConnectionBean.client.setStartServiceDelegate(new ActionEx() {	// ���� �����ڵ鷯
//  			
//  			public void work(Object arg) {
////  				// TODO Auto-generated method stub
////  				//�ڵ鷯�� ������
////  				startHandler.sendMessage(Message.obtain());
//  			}
//  		});
//  		

  		ConnectionBean.client.connect(ConnectionBean.ServerInfomation);	// ������ ������ ����// connection Server
  		
  		
  	}
}