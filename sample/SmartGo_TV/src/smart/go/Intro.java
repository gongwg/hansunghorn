package smart.go;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          

import smart.go.R;
import smart.go.R.layout;
import android.app.Activity;
import android.os.Bundle;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class Intro extends Activity {
	Handler mHandler;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// Ÿ��Ʋ�� ���ֱ� ����
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.intro);

		mHandler = new Handler();
		mHandler.postDelayed(mRunnable, 1000);// 4�� ��
	}

	Runnable mRunnable = new Runnable() {
		public void run() {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Intro.this, SmartGoActivity.class);
			startActivity(intent);
			finish();
			// ���̵� �� ���� ���̵��� ����
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);

		}
	};

	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		mHandler.removeCallbacks(mRunnable);
	}

}
