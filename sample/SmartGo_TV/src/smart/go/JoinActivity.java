package smart.go;


import smart.go.R;
import smart.go.R.id;
import smart.go.R.layout;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class JoinActivity extends Activity {
	/** Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.join_us); 	

		ImageButton join_btn = (ImageButton)findViewById(R.id.join_btn);
		ImageButton cancel_btn = (ImageButton)findViewById(R.id.cancel_btn);
		
		join_btn.setOnClickListener(new ImageButton.OnClickListener() {

			public void onClick(View v) {
				EditText id = (EditText)findViewById(R.id.idEdit);
				EditText pw = (EditText)findViewById(R.id.pwEdit);
				EditText rpw = (EditText)findViewById(R.id.rpwEdit);
				EditText name = (EditText)findViewById(R.id.nameEdit);

				DBAdapter db = new DBAdapter(getApplicationContext());
				db.open();

				String sid = id.getText().toString();
				String spw = pw.getText().toString();
				String srpw = rpw.getText().toString();
				String sname = name.getText().toString();
		
				int yesno = db.getIDEntry(sid);
					
				// null�϶�,
				if(sid.equals("") || spw.equals("") || srpw.equals("") || sname.equals("")) {
					new AlertDialog.Builder(JoinActivity.this)
					.setTitle("���")
					.setMessage("��ĭ�� �Է��� �ּ���")
					.setPositiveButton("�ٽ�", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					})
					.show();
				} // pw�� rpw�� ��ġ���� ������ 
				else if(!spw.equals(srpw)) {
					new AlertDialog.Builder(JoinActivity.this)
					.setTitle("���")
					.setMessage("Confirm_PW�� �ٽ� �Է��� �ּ���")
					.setPositiveButton("�ٽ�", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					})
					.show();					
				}
				else if(yesno == 1) {
					new AlertDialog.Builder(JoinActivity.this)
					.setTitle("���")
					.setMessage("������� ID�Դϴ�.\n �ٽ� �Է��� �ּ���")
					.setPositiveButton("�ٽ�", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					})
					.show();
				}
				else {
					long num = db.insertEntry(sid, spw, srpw, sname);
					db.close();	
					finish();
				}
			}
		});

		cancel_btn.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
}
