package test.sod.common;

import java.io.FileNotFoundException;
import java.io.IOException;

import sod.common.Storage;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class StorageEXActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        EditText editText = (EditText)findViewById(R.id.editText);
        EditText editText2 = (EditText)findViewById(R.id.editText2);
        EditText editText3 = (EditText)findViewById(R.id.editText3);
        
        Storage storage = null;
        String [] list = null;
        String str = new String();
        try {
			storage = Storage.getStorage("ana");;
			editText2.setText(storage.getSODStoragePath());
			
			/*//getFileListTest
			list = storage.getFileList("*");;
			
			for(int i =0; i < list.length ; i++){
				str+= list[i];
			}
			
			editText3.setText(str);
			*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			editText.setText("IOException");
		} catch(IllegalArgumentException e){
			editText.setText("IllegalArgumentException");
		}
        
        try {
			storage.renameFile("b.txt", "iu.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			editText3.setText("FileNotFoundException");
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			editText3.setText("NullPointerException");
		}
        
        /*
        if(storage.checkIsFileExists("a.txt"))
        	editText3.setText("true");
        else
        	editText3.setText("false");
        */
        
        /* deleteFileTest...
        try {
			storage.deleteFile("a.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			editText3.setText("FileNotFoundException");
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			editText3.setText("NullPointerException");
		}
		*/
        
        /*// openFile Test...
        try {
			storage.openFile(null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			editText3.setText("FileNotFoundException");
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			editText3.setText("NullPointerException");
		}
       */
        
        
        
        /*
        try {
			editText3.setText(new String( String.valueOf(Storage.getStorageSize("ana")) ));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			editText3.setText("IOException");
		}
        */
        /*
        try {
			Storage.destroy("ana");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			editText2.setText("�������� ��");
		}
		*/
        
        /*
        try {
			Storage.clear("ana");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			editText3.setText("IOException");
		}
        */
    }
}