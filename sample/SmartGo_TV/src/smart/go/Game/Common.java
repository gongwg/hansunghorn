package smart.go.Game;

import android.util.Log;


public class Common {
	public synchronized void WakeUp() {
		notifyAll();	
		Log.e("V", "���");
	}

	public synchronized void Wait(){
		try {
			Log.e("V", "���");
			wait();					
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
