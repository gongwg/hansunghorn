package ana.phone;

import com.phonegap.*;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class AnAService extends DroidGap {
	Button TvSearch, ServiceLocation, InstallService;

	public void Initialize() {
		super.loadUrl("file:///android_asset/www/AnA.html");
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TvSearch = (Button) findViewById(R.id.TvSearch);
		ServiceLocation = (Button) findViewById(R.id.ServiceLocation);
		InstallService = (Button) findViewById(R.id.InstallService);

		TvSearch.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// new AccessManager().searchServer(); // TV ���� ã��
			}
		});
		ServiceLocation.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

			}
		});
		InstallService.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Initialize();
			}
		});

	}
}
