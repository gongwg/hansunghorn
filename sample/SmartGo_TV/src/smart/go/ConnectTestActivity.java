package smart.go;

import java.util.ArrayList;

import smart.go.Game.Client;
import smart.go.Game.Common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ConnectTestActivity extends Activity implements SensorEventListener {

	/** Called when the activity is first created. */

	ImageView[] imageView;
	// ������ ��������� ���� Ŭ���� ȣ��(��Ű���ȿ� ����)
	Client connect;
	Common c;
	//int check_sun = 0;
	// ����ν��� ���� �ʿ��� ����
	private long lastTime = 0;
	int lastPitch=0;

	int num = 0; // �� ��ȣ
	int imgId;
	int getNum = 0;

	public ArrayList<Integer> m_remoteList = new ArrayList<Integer>();
	// �������� ������ ��ȣ���� �����ϴ� �迭
	ArrayList<Integer> getCardNumber;

	// ��� ī��Ʈ
	private int motionCnt = 0;

	private int ShutdownCount;

	// � �а� ���õƴ��� Ȯ���ϴ� �迭
	private String str;

	private Gallery g;
	// �������� ����ϱ� ���� �̹��� �����
	private ImageAdapter imageAdapter;

	// �ؿ� ������ ��ü�� �����ֱ� ���� ���̾ƿ�(dotpannel)
	private LinearLayout layout;

	// �������� ������ ���ڷ� �̹����� ����� ���� ��Ƽ���� �迭
	private Integer[] mImageIds = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// Ÿ��Ʋ�� ���ֱ� ����
		setContentView(R.layout.gallerytest);

		connect = new Client();
		c = new Common();

		connect.init(c);
		c.Wait();


		// ������ ���� �޾ƿ� 1~48������ ���� 10���� ������
		// R.drawable.cardimage01(�ϱ�)�� �������� +getCardNumber[i]-1�� �Ͽ�
		// �ش��ϴ� ������ �� �̹��� ���̵� �����´�.(drawable������ ó���� 1�̰� �״��� �������� +1�̹Ƿ�

		// ����� if�� �ɾ �� �� �� �������ҵ�.
		imgId = R.drawable.cardimage01;
		int j = 0;

		m_remoteList = connect.getRemoteList();
		//check_sun = 1;
		mImageIds = new Integer[m_remoteList.size()];

		for(int i=0; i<m_remoteList.size(); i++) {
			mImageIds[i] = imgId + (m_remoteList.get(i) - 400);
		}
		// ����� ���
		imageAdapter = new ImageAdapter(this);

		// �������� �ٲ𶧸��� �����Ǿ� �ϹǷ� �����ʸ� ����Ѵ�.

		SensorManager sensorM = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		// ���� ������ ��ü(�����̺�Ʈ������ ���ø�����), ����Ÿ��, ���� �ΰ����� �Ŵ����� ����Ѵ�.
		sensorM.registerListener(
				this,// Activity�� ���� �����ʸ� ����

				sensorM.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_GAME);

		g = (Gallery) findViewById(R.id.gallery);
		g.setAdapter(imageAdapter);

		/*
		 * g.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		 * public void onItemClick(AdapterView parent, View v, int position,
		 * long id) {
		 * 
		 * } });
		 */

		// �̰� ���ִ°��� �𸣰��� ����Ʈ ������ٰ� �׸��а� ����.
		g.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				// selectGallery(position);
				num = position;

			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		layout = (LinearLayout) findViewById(R.id.dotPanel);

		// �г� ���̰� ��
		createDotPanel(this, layout, m_remoteList.size());

	}

	// �ؿ���
	private void createDotPanel(Context context, LinearLayout layout, int count) {

		imageView = new ImageView[count];

		for (int i = 0; i < count; i++) {

			imageView[i] = new ImageView(context);

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(3,
					0);
			// �̹��� �۾��� �������� �ʾƼ� �׳� �ϵ��ڵ����� ũ�� �������.
			params.width = 46;
			params.height = 65;

			/*
			 * �̰� ���� �ڵ��� params.width = LayoutParams.WRAP_CONTENT; params.height
			 * = LayoutParams.WRAP_CONTENT;
			 */
			params.topMargin = 2;
			params.leftMargin = 2;
			params.gravity = Gravity.CENTER;
			imageView[i].setId(i);

			imageView[i].setLayoutParams(params);
			imageView[i].setImageResource(R.drawable.cardimage01
					+ (m_remoteList.get(i) - 400));

			layout.addView(imageView[i]);
		}

		for (int i = 0; i < count; i++) {
			imageView[i].setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					switch (v.getId()) {
					case 0:
						g.setSelection(0);
						break;
					case 1:
						g.setSelection(1);
						break;
					case 2:
						g.setSelection(2);
						break;
					case 3:
						g.setSelection(3);
						break;
					case 4:
						g.setSelection(4);
						break;
					case 5:
						g.setSelection(5);
						break;
					case 6:
						g.setSelection(6);
						break;
					case 7:
						g.setSelection(7);
						break;
					case 8:
						g.setSelection(8);
						break;
					case 9:
						g.setSelection(9);
						break;
					default:
						break;

					}
					// Toast.makeText(ConnectTestActivity.this, imgId,
					// Toast.LENGTH_SHORT).show();

				}
			});
		}
	}

	// �� ������ �������� ����� �ٽ� ������Ʈ
	private void reCreateDotPanel(Context context, LinearLayout layout,
			int count, int position) {
		ImageView[] newImageView = new ImageView[count];
		int pos = position;
		for (int i = 0; i < count; i++) {
			if (pos != i) {
				newImageView[i] = imageView[i];
			} else {
				imageView[i + 1].setId(i);
				newImageView[i] = imageView[i + 1];

				pos++;
			}
		}
		imageView = new ImageView[count];
		imageView = newImageView;

		for (int i = 0; i < imageView.length; i++) {
			layout.addView(imageView[i]);
		}

	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}
	

	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		switch (event.sensor.getType()) {
		case Sensor.TYPE_ORIENTATION: // ���⼾�� ���� �ٲ������
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastTime > 400) {
				int pitch = (int) event.values[1];

				long gabOfPitch = (pitch - lastPitch);

				// TextView MyText = new TextView(this) ;

				lastTime = currentTime;
				Log.e("gab", ""+gabOfPitch);
				if (gabOfPitch > 60) {
					motionCnt++;
					lastPitch = pitch;					
					deleteGallery(num);
					connect.setMotion(num);
					sleep(500);
					c.WakeUp();
					c.Wait();
					Toast.makeText(ConnectTestActivity.this, "�и� �����ϼ̽��ϴ�!!!!!",
							Toast.LENGTH_SHORT).show();

				}
				lastPitch = pitch;
			}
			break;
		default:
			break;

		}

	}

	// �������� ����°�
	private void deleteGallery(int shake) {
		int pos = shake;

		mImageIds[shake] = 0;
		Integer[] newImageIds = new Integer[mImageIds.length - 1];

		for (int i = 0; i < mImageIds.length - 1; i++) {
			if (i == pos) {
				newImageIds[i] = mImageIds[i + 1];
				mImageIds[i + 1] = 0;
				pos++;
			} else {
				newImageIds[i] = mImageIds[i];

			}
		}
		mImageIds = new Integer[mImageIds.length - 1];
		mImageIds = newImageIds;
		layout.removeAllViews();

		reCreateDotPanel(this, layout, mImageIds.length, shake);

		// selectGallery(shake);
		// layout.removeViewAt(shake);

		// imageView[shake].setVisibility(View.INVISIBLE);
		imageAdapter.notifyDataSetChanged();

	}

	// �������� �̹��� �۾��� ���� �̹��� �����~
	public class ImageAdapter extends BaseAdapter {
		int mGalleryItemBackground;
		private Context mContext;
		private ImageView i;

		public ImageAdapter(Context c) {
			mContext = c;
			TypedArray a = c.obtainStyledAttributes(R.styleable.GalleryTheme);
			mGalleryItemBackground = a.getResourceId(
					R.styleable.GalleryTheme_android_galleryItemBackground, 0);
			a.recycle();
		}

		public int getCount() {
			return mImageIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			i = new ImageView(mContext);
			i.setImageResource(mImageIds[position]);
			i.setLayoutParams(new Gallery.LayoutParams(400, 670));
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			i.setBackgroundResource(mGalleryItemBackground);

			return i;
		}

	}

	DialogInterface.OnClickListener dlistener = new DialogInterface.OnClickListener() {

		public void onClick(DialogInterface dialog, int which) {

			if (which == Dialog.BUTTON_POSITIVE) {

				android.os.Process.killProcess(android.os.Process.myPid());
			}

		}

	};

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		AlertDialog alert = new AlertDialog.Builder(ConnectTestActivity.this)

		.setTitle("����Ȯ��").setMessage("���α׷��� ���� �Ͻðڽ��ϱ�?")
		.setPositiveButton("��", dlistener)
		.setNegativeButton("�ƴϿ�", dlistener).show();
	}
	
	public void sleep(int i){
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
