package sod.common;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StorageFileW extends StorageFile{
	FileOutputStream fileOutputStream;
	BufferedOutputStream out;
	StringBuilder mBuf;
	
	
	/**
	 * 
	 * @param mFile
	 * @throws IOException
	 * ���� ������ �����ϸ� IOException�� ������.
	 * @throws NullPointerException
	 * ���ڷ� �Ѿ�� ���� Null�̸� NullPointerException�� ������.
	 */
	static public StorageFile createStorageFile(File mFile)throws IOException, NullPointerException{
		if(mFile == null)
			throw new NullPointerException();
		
		StorageFileW storageFileW = new StorageFileW();
		
		storageFileW.file = mFile;	
		storageFileW.file.createNewFile();//
		storageFileW.fileOutputStream = new FileOutputStream(storageFileW.file);
		storageFileW.out = new BufferedOutputStream(storageFileW.fileOutputStream);

		
		return storageFileW;
	}
	
	/**
	 * 
	 * @param mFile
	 * @return
	 * @throws IOException
	 * ���� ������ �����ϸ� IOException�� ������.
	 * @throws NullPointerException
	 * ���ڷ� �Ѿ�� ���� Null�̸� NullPointerException�� ������.
	 */
	static public StorageFile getStorageFile(File mFile)throws IOException, NullPointerException{
		if(mFile == null)
			throw new NullPointerException();
		
		StorageFileW storageFileW = new StorageFileW();
		storageFileW.file = mFile;
		storageFileW.fileOutputStream = new FileOutputStream(storageFileW.file);
		storageFileW.out = new BufferedOutputStream(storageFileW.fileOutputStream);
		
		
		return storageFileW;
	}
	
	public void write(byte [] buf) throws IOException{
		out.write(buf);
	}
	
	/**
	 * �ش� ���Ͽ� �����͸� ����.
	 * @param buf
	 * ���Ͽ� �������ϴ� ������ ����Ʈ �迭�� ����ȭ�� ��
	 * @param index
	 * ���ۿ����� �������ϴ� �κ��� ������ġ
	 * @param length
	 * �������ϴ� ����Ʈ �迭�� index������ ����
	 * @throws  IOException
	 * ���Ͽ� ���⸦ �����ϸ�  IOException�� ������.
	 */
	public void write(byte[] buf, int index, int length) throws IOException{
		
		out.write(buf, index, length);
		
	}
	
	/**
	 * ������ ������ ����� �� ����Ѵ�.
	 * @throws IOException 
	 * �̹� ���������� IOException �� ������.
	 */
	public void flush() throws IOException{

		out.close();
		fileOutputStream.close();
		
		fileOutputStream = new FileOutputStream(file);
		out = new BufferedOutputStream(fileOutputStream);
	}
	
	/**
	 * ������ �ݰ� �����Ѵ�.
	 * @throws IOException 
	 * �̹� ���������� IOException �� ������.
	 */
	public void close() throws IOException{
		out.close();
		fileOutputStream.close();
	}

}
