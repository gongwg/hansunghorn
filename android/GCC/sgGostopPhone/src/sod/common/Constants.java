package sod.common;

import android.util.Log;

public class Constants {
	public static final boolean isDebug = true;
	
	public static final int Default_CheckingPeriod = 4000;
	public static final int Default_Timeout = 30000;
	
	public static final String Multicast_IP = "224.0.0.1";
	public static final int Multicast_Port = 4020;
	public static final int Multicast_Port_Response = 4021;
	
	
	//�ӽ�
	public static final int MAX_CONNECTION =10;
	public static final Logable logger = new Logable() {			

		public void log(Object arg) {
			Log.d("logger", arg.toString());
			
		}
	};
	
}
