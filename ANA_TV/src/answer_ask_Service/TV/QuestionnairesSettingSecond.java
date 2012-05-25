package answer_ask_Service.TV;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

import sod.common.Packet;
import sod.common.Storage;
import sod.common.StorageFile;
import sod.smarttv.AccessManagerServer;
import sod.smarttv.ConnectHandler;
import sod.smarttv.DisconnectHandler;
import sod.smarttv.ServerConfig;
import sod.smarttv.ServerReceiveHandler;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;
import answer_ask_BeanTV.ConnectionBean;
import answer_ask_BeanTV.DataBean;
import answer_ask_BeanTV.LayoutComponentBean;

public class QuestionnairesSettingSecond extends Activity implements DataSturct {
	private ArrayList<String> list;
	private ArrayAdapter<String> adapter;
	private String[] items = { "Yes or No", "Good Nomal bad",
			"VeryGood Good Bad VeryBad", "VeryGood Good Nomal Bad VeryBad" };
	private int a = 0;
	private Storage storage;
	private StorageFile storageFile;
	int count=0;
	// final String[]
	private static String getLocalAddress() throws IOException { // �� ����̽� IP
		// �޾ƿ���
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
		}
		return "";
	}

	public void TVServerIni() {
		ConnectionBean.server = new AccessManagerServer();
		ConnectionBean.ServerConfig = new ServerConfig();
		ConnectionBean.ServerConfig.Timeout = 30000;
		ConnectionBean.ServerConfig.Port = ConnectionBean.SERVERPORT;
		ConnectionBean.ServerConfig.CheckingPeriod = 4000;
		ConnectionBean.ServerConfig.serviceName = "A&A_Service";

		ConnectionBean.server.setConnectHandler(new ConnectHandler() {

			@Override
			public void onConnect(int connid) {

				// TODO Auto-generated method stub
				// Toast.makeText(QuestionnairesSettingSecond.this, "��������",
				// Toast.LENGTH_LONG).show();
			}
		});
		ConnectionBean.server.setDisconnectHandler(new DisconnectHandler() {

			@Override
			public void onDisconnect(int connid) {
				// TODO Auto-generated method stub
				// Toast.makeText(QuestionnairesSettingSecond.this, "���� ����",
				// Toast.LENGTH_LONG).show();

			}
		});
		ConnectionBean.server.setReceiveHandler(new ServerReceiveHandler() {

			@Override
			public void onReceive(Packet pkt, int connid) {
				ConnectionBean.ClientId = connid;
				Packet packet;

				if (pkt != null) {
					while (pkt.getElementCount() > 0) {
						ConnectionBean.Message = pkt.pop().toString();
						if (ConnectionBean.Message.equals("download")) {
							packet = new Packet();
							for (int i = 0; i < DataSturct.vector.size(); i++) {
								packet.push(DataSturct.vector.elementAt(i));

							}
							ConnectionBean.server.send(packet,
									ConnectionBean.ClientId);
						}
						// else if(ConnectionBean.Message.equals("answerData"))
						// {
						else {
							DataBean.Message += ConnectionBean.Message;
						}
						// }

					}
					try{
						rearrange();
							
					}catch(Exception e){}// packet=null;
				}
				
			}
		});

	}
	public void Store() throws Exception
	{
			if(Storage.checkIsStorageExists("ana"))
				{
				storage=Storage.getStorage("ana");
				}
			else
			storage=Storage.createStorage("ana");

	}
	public void rearrange() throws Exception {
		Store();
		StorageFile storageFile = storage.openFile("data.txt", Storage.WRITE);
		String str="";
		for (int i = 0; i < DataSturct.vector.size(); i++) {
			String Temp1 = DataSturct.vector.get(i);
			String Temp2[] = Temp1.split(LayoutComponentBean.SEPARATOR);
			String Temp3[] = DataBean.Message
					.split(LayoutComponentBean.SEPARATOR);
			for (int j = 0, k = 0; j < Temp2.length; j++) {
				if (Temp2[j].equals("Answer")) {
					str=LayoutComponentBean.SEPARATOR+Temp2[j + 1]+LayoutComponentBean.SEPARATOR+Temp3[k + 1];
					storageFile.write(str.getBytes());
					storageFile.close();
					k++;
				} else if (Temp2[j].equals("Choice")) {
					str=Temp2[j+1];
					storageFile.write(str.getBytes());
					storageFile.close();
				}

			}
		}

	}

	public void ButtonEvent() {
		LayoutComponentBean.radiogroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						if (checkedId != -1) {

							RadioButton rb = (RadioButton) findViewById(checkedId);

							if (rb.getText().equals("������")) {
								Toast.makeText(
										QuestionnairesSettingSecond.this,
										rb.getText(), Toast.LENGTH_SHORT)
										.show();
								new AlertDialog.Builder(
										QuestionnairesSettingSecond.this)
										.setTitle("Select Question count")
										.setSingleChoiceItems(
												items,
												-1,
												new DialogInterface.OnClickListener() {

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {
														// TODO Auto-generated
														// method stub
														LayoutComponentBean.choice = which;

													}
												})
										.setNegativeButton("Ȯ��", null).show();
							} else {
								LayoutComponentBean.choice = -1;
							}
						}
					}
				});
		LayoutComponentBean.questionAdd
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						switch(count)
						{
						case 0:
							LayoutComponentBean.question.setText("���� ���� �Ա� �մϱ�?");
							break;
						case 1:
							LayoutComponentBean.question.setText("�����Բ��� �� ���� ���ּ̽��ϱ�?");
							break;
						case 2:
							LayoutComponentBean.question.setText("���� �ߴ� ����� ��� ���� ��ġ �մϱ�?");
							break;
						case 3:
							LayoutComponentBean.question.setText("���� �ϴ� ���� ����ģ���� �������� �ʾҽ��ϱ�?");
							break;
						default:
							LayoutComponentBean.question.setText("���踦 �ϴµ��� ������� �����ϱ�??");
							break;
						}
						vector.add(LayoutComponentBean.SEPARATOR + "Topic"
								+ LayoutComponentBean.SEPARATOR
								+ LayoutComponentBean.topic.getText()
								+ LayoutComponentBean.SEPARATOR + "Answer"
								+ LayoutComponentBean.SEPARATOR
								+ LayoutComponentBean.question.getText()
								+ LayoutComponentBean.SEPARATOR + "Choice"
								+ LayoutComponentBean.SEPARATOR
								+ LayoutComponentBean.choice);
						
						count++;
						list.add(""
								+ ++a
								+ ". "
								+ LayoutComponentBean.question.getText()
										.toString());
						LayoutComponentBean.topic.setEnabled(false);
						LayoutComponentBean.question.setText("");
						LayoutComponentBean.question.setFocusable(true);
						adapter.notifyDataSetChanged();
					}

				});
		LayoutComponentBean.listview
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String str = "����  = " + list.get(position);
						Toast.makeText(QuestionnairesSettingSecond.this, str,
								Toast.LENGTH_LONG).show();
					}

				});

		LayoutComponentBean.complete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				vector.add(LayoutComponentBean.SEPARATOR + "Topic"
						+ LayoutComponentBean.SEPARATOR
						+ LayoutComponentBean.topic.getText()
						+ LayoutComponentBean.SEPARATOR + "Answer"
						+ LayoutComponentBean.SEPARATOR
						+ LayoutComponentBean.question.getText()
						+ LayoutComponentBean.SEPARATOR + "Choice"
						+ LayoutComponentBean.SEPARATOR
						+ LayoutComponentBean.choice);
				vector.add(LayoutComponentBean.SEPARATOR + "xxxxx"
						+ LayoutComponentBean.SEPARATOR);

				if (LayoutComponentBean.ScreenCount == 0) {
					TVServerIni();
					ConnectionBean.server.start(ConnectionBean.ServerConfig);
					LayoutComponentBean.ScreenCount++;
				}

				LayoutComponentBean.statisticsGraph_btn.setEnabled(true);
				LayoutComponentBean.QuestionnaireImfo_btn.setEnabled(true);
				LayoutComponentBean.QuestionnaireInitial_btn.setText("������ �� ����");
				Intent intent = new Intent(QuestionnairesSettingSecond.this,
						AnA_BootMode.class);
				startActivity(intent);
			}

		});

	}

	public void InterfaceInital() {
		LayoutComponentBean.radiogroup = (RadioGroup) findViewById(R.id.radioGroup1);
		LayoutComponentBean.topic = (EditText) findViewById(R.id.topic);
		LayoutComponentBean.question = (EditText) findViewById(R.id.answer);
		LayoutComponentBean.Fileadd = (Button) findViewById(R.id.fileadd);
		LayoutComponentBean.questionAdd = (Button) findViewById(R.id.addAnswer);
		LayoutComponentBean.complete = (Button) findViewById(R.id.complete);
		LayoutComponentBean.long_answer = (RadioButton) findViewById(R.id.first);
		LayoutComponentBean.short_answer = (RadioButton) findViewById(R.id.second);
		LayoutComponentBean.listview = (ListView) findViewById(R.id.listview);
		
		
		/////////////���� ////////////////////////
		LayoutComponentBean.topic.setText("����");
		/////////////////////////////////////////
		list = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(QuestionnairesSettingSecond.this,
				R.layout.textstyle, list);
		LayoutComponentBean.listview.setAdapter(adapter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questionnaire2);
		InterfaceInital();
		ButtonEvent();

	}

}
