package sod.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.os.Environment;

/**
 * 
 * @author MB
 *
 */
public class Storage {
	
	final static String SOD = "/sod/";
	public final static int READ = 0;
	public final static int WRITE = 1;
	
	String sdPath;
	String sodRootPath;
	String sodStoragePath;
	
	String storageID;
	
	File directory;
	
	Storage(String mSdPath, String mStorageID){
		sdPath = mSdPath;
		sodRootPath = sdPath + SOD;
		
		File sod = new File (sodRootPath);
		
		if(!sod.exists())
			sod.mkdir();
		
		sodStoragePath = sodRootPath +mStorageID;
		
		directory = new File(sodStoragePath);
		
	}
	
	protected boolean isExist(){
		return directory.exists();
	}
	
	protected boolean createDirectory(){
		return directory.mkdir();
	}
	
	protected String [] getFileList(){
		return directory.list();
	}
	
	protected File getDirectory(){
		return directory;
	}
	
	public String getSODStoragePath(){
		return sodStoragePath;
	}
	/**
	 * ���ο� ����Ҹ� ������ �� ����ϴ� �޼ҵ��̴�. ������� ID�� �Ѱ��ָ� ����Ұ� �����ϴ����� Ȯ�� �� ��, ���� �� �ش� ����Ҹ� �����Ѵ�.
	 * storageID �� serviceName�̴�.
	 * @param storageID
	 * ����Ҹ� ���� �����Ҷ� ����� ������� ID�� �Ѱ��ش�. ������� ID�� �� ����Ҹ� ������ ���� ���ȴ�. ����� ID�� String ��ü���� �Ѵ�.
	 * @return
	 * ������ ����Ҹ� �Ѱ��ش�.
	 * @throws IOException
	 * ������� ID�� ������� ����Ұ� �̹� ����ų� ����Ҹ� �����ϴ� �� �������� ���, IOException�� ������.
	 */
	public static Storage create(String storageID) throws IOException, IllegalArgumentException{
		
		if(storageID == null)
			throw new IllegalArgumentException();
		
		String ext = Environment.getExternalStorageState();
		String mSdPath;
		
        if(ext.equals(Environment.MEDIA_MOUNTED)){
        	mSdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }else{
        	mSdPath = Environment.MEDIA_UNMOUNTED;
        }
        
        Storage storage = new Storage(mSdPath, storageID);
        
        //�ش� storageID�� �ش��ϴ� ���丮(�����)�� �̹� '�����ϸ�' ���ܸ� ������..
        if(storage.isExist())
        	throw new IOException();
        else{//�������� ������ �ش� ���丮(�����)�� �����Ѵ�.
        	
        	if( !storage.createDirectory() )
        		throw new IOException();
     
        }
       
        return storage;
	}
	
	/**
	 * �̹� �����Ǿ� �ִ� ����Ҹ� ����ϱ� ���� �����ö� ����ϴ� �޼ҵ��̴�.
	 * @param storageID 
	 * ���������� �ϴ� ������� ID�̴�.  ����� ID�� String ��ü���� �Ѵ�.
	 * @return
	 * ������ ������� ID�� �̿��Ͽ� ������ �����
	 * @throws  IOException
	 * �����������ϴ� ����Ұ� ���� ��,  IOException�� ������.
	 */
	static public Storage getStorage(String storageID) throws IOException{
		
		String ext = Environment.getExternalStorageState();
		String mSdPath;
		
        if(ext.equals(Environment.MEDIA_MOUNTED)){
        	mSdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }else{
        	mSdPath = Environment.MEDIA_UNMOUNTED;
        }
        
        Storage storage = new Storage(mSdPath, storageID);
        
        //�ش� storageID�� �ش��ϴ� ���丮(�����)��  '�������� ������' ���ܸ� ������..
        if(!storage.isExist()){
        	throw new IOException();
        }
       
        return storage;
	}
	
	/**
	 * �̹� ������ ����Ҹ� �ı��� �� ����ϴ� �޼ҵ��̴�. ����� ���� ������ �����ϴ� ��쿡�� ����� ���� ������ �����ϰ� �ش� ����Ҹ� �ı��Ѵ�.
	 * @param storageID 
	 * �ı��ϰ��� �ϴ� ������� ID�̴�.  ����� ID�� String ��ü���� �Ѵ�.
	 * @throws IOException
	 * ������� ID�� ���� ���� ������ �� �����Ƿ� IOException�� ������.
	 */
	static public void destroy(String storageID) throws IOException{
		
		Storage storage = getStorage(storageID);
		
		String [] fileNames = storage.getFileList();
		
		if(fileNames == null)
			throw new IOException();
		
		for(int i=0 ; i<fileNames.length; i++){
			File storageFile = new File(storage.getDirectory(), fileNames[i]);
			storageFile.delete();
		}
		
		storage.getDirectory().delete();

	}
	
	
	
	/**
	 * ������� ����� ��ȯ�ϴ� �޼ҵ��̴�. ����ڿ��� ��ġ�Ǿ� �ִ� ������� ũ�⸦ �˷��ֱ����� ����Ѵ�.
	 * @param storageID
	 * �뷮�� �˱����� ������� ID�̴�.  ����� ID�� String ��ü���� �Ѵ�.
	 * @return
	 * �뷮�� Byte ������ ����Ͽ� ���������� ��ȯ�Ѵ�.
	 */
	static public int getStorageSize(String storageID) throws IOException{
		
		Storage storage = getStorage(storageID);
		
		String [] fileNames = storage.getFileList();
		
		int returnStorageSize = 0;
		
		if(fileNames == null)
			throw new IOException();
		
		for(int i=0 ; i<fileNames.length; i++){
			File storageFile = new File(storage.getDirectory(), fileNames[i]);
			returnStorageSize += (int) storageFile.length();
		}
		
		return returnStorageSize;
	}
	
	/**
	 * �Ѱ��� ����� ID�� �ش��ϴ� ����� ���� ���ϵ��� ��� �����Ѵ�.
	 * @param storageID
	 * ������ �ϴ� ������� ID�̴�.  ����� ID�� String ��ü���� �Ѵ�.
	 * 
	 */
	static public void clear(String storageID) throws IOException{
		
		Storage storage = getStorage(storageID);
		String [] fileNames = storage.getFileList();
		
		if(fileNames == null)
			throw new IOException();
		
		for(int i=0 ; i<fileNames.length; i++){
			File storageFile = new File(storage.getDirectory(), fileNames[i]);
			storageFile.delete();
		}
		
	}
	
	/**
	 * �Ѱ��� filter�� ȭ���ڸ� ���� ������ ����� ���� ��ȯ�Ѵ�.
	 * @param filter
	 * ��⸦ ���ϴ� ������ Ȯ����
	 * @return
	 *  �Ѱ��� filter�� Ȯ���ڸ� ���� ������ ����Ʈ 
	 */
	public String [] getFileList(String filter) throws IllegalArgumentException{
		
		String [] returnStringList = null;
		
		if(filter.equals("*"))
			returnStringList = directory.list();
		//else ToDo...
		
		return returnStringList;
	}

	/**
	 * ����� ���� �̹� �����ϴ� ������ ����.
	 * @param filePath
	 * ������ �ϴ� ������ ��ġ�ϴ� ���
	 * @return
	 * ������ �ϴ� ����
	 * @throws FileNotFoundException
	 * �������ϴ� ������ ���� ��, FileNotFoundException�� ������.
	 * @throws NullPointerException
	 * fileName�� null�̷��� ���ϰ�ü ��ü ������ �����ϸ� NullPointerException�� ������.
	 * @throw IOException
	 * StorageFile ��ü ������ �����ϸ� IOException�� ������.
	 * @throw IllegalArgumentException
	 * mode�� READ(0)�� WRITE(1)������ ���� �߻��ϸ� IllegalArgumentException �� ������.
	 */
	public StorageFile openFile(String filePath, int mode) throws FileNotFoundException, NullPointerException, IOException, IllegalArgumentException{
		File file = new File(directory, filePath);
		
		//�������� ������ FileNotFoundException�� ������.
		if( !file.exists() )
			throw new FileNotFoundException();
		
		StorageFile returnStorageFile = null;
		switch(mode){
		case READ:
			returnStorageFile = StorageFileR.getStorageFile(file);
			break;
			
		case WRITE:
			returnStorageFile = StorageFileW.getStorageFile(file);
			break;
		default:
			throw new IllegalArgumentException();
		}
	
		return returnStorageFile;
	}
	
	
	/**
	 * ����� ���� ���ο� ������ �����.
	 * @param filePath
	 * ���ο� ������ �����ϱ� ���� ������ ���
	 * @return
	 * ���� ������ ����
	 * @throws IOException
	 * �̹� ������ ������ �����ϰų� ������ ����µ� ����(StrageFile ��ü ���� ����) ���� ��쿡 IOException�� ������. 
	 * @throws NullPointerException
	 * fileName�� null�̷��� ���ϰ�ü ��ü ������ �����ϸ� NullPointerException�� ������.
	 */
	public StorageFile createFile(String filePath) throws IOException, NullPointerException{
		
		File file = new File(directory, filePath);
		
		//�̹������ϸ� IOException�� ������.
		if( file.exists() )
			throw new IOException();
		
		StorageFile returnStorageFile = StorageFileW.createStorageFile(file);
	
		return returnStorageFile;
	}
	
	/**
	 * ����� ���� ������ �����Ѵ�.
	 * @param filePath
	 * �����Ϸ��� �ϴ� ������ ���
	 * @throws  FileNotFoundException
	 * �����Ϸ��� ������ �������� ������ FileNotFoundException�� ������.
	 * @throws NullPointerException
	 * fileName�� null�̷��� ���ϰ�ü ��ü ������ �����ϸ� NullPointerException�� ������.
	 */
	public void deleteFile(String filePath) throws FileNotFoundException, NullPointerException{
		
		File file = new File(directory, filePath);
		
		if( !file.exists() )
			throw new FileNotFoundException();
		
		file.delete();
	}
	
	/**
	 * �ش� ����� ������ �����ϴ��� Ȯ���Ѵ�. ��δ� /sod/StorageID �����θ� �˻��� �����ϴ�.
	 * @param filePath
	 * �����ϴ��� Ȯ���Ϸ��� �ϴ� ������ ���
	 * @return 
	 * ���縦 Ȯ���ϰ��� �ϴ� ������ ������ true, ������ false�� ��ȯ
	 */
	public boolean checkIsFileExists(String filePath){
		
		File file = new File(directory, filePath);
		
		return file.exists();
	}
	
	/**
	 * �̹� �����ϴ� ������ �̸��� �ٲ۴�.
	 * @param oldPath
	 * �̸��� �ٲٱ� ��, ������ ���
	 * @param newPath
	 * �̸��� �ٲ� ��, ������ ���
	 * @throws FileNotFoundException
	 * �̸��� �����Ϸ��� ������ ã�� �� ���� ��, FileNotFoundException�� ������.
	 * @throws NullPointerException
	 * Path�� null�̷��� ���ϰ�ü ��ü ������ �����ϸ� NullPointerException�� ������.
	 */
	public void renameFile(String oldPath, String newPath) throws FileNotFoundException, NullPointerException{
		File oldFile = new File(directory, oldPath);
		File newFile = new File(directory, newPath);
		
		if( !oldFile.exists() )
			new FileNotFoundException();
		
		oldFile.renameTo(newFile);
	}
	
}
