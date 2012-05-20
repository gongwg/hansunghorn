package sod.common;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class StorageFileR extends StorageFile {
	
	FileInputStream fileInputStream;
	
	BufferedInputStream in;
	
	/**
	 * @param mFile
	 * @throws IOException
	 * ���� ������ �����ϸ� IOException�� ������.
	 * @throws NullPointerException
	 * ���ڷ� �Ѿ�� ���� Null�̸� NullPointerException�� ������.
	 */
	static public StorageFile createStorageFile(File mFile)throws IOException, NullPointerException{
		if(mFile == null)
			throw new NullPointerException();
		
		StorageFileR storageFileR = new StorageFileR();
		
		storageFileR.file = mFile;	
		storageFileR.file.createNewFile();//
		
		storageFileR.fileInputStream = new FileInputStream(storageFileR.file);
		
		storageFileR.in = new BufferedInputStream(storageFileR.fileInputStream);
		
		return storageFileR;
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
		
		StorageFileR storageFileR = new StorageFileR();
		
		storageFileR.file = mFile;
		
		storageFileR.fileInputStream = new FileInputStream(storageFileR.file);
		
		
		storageFileR.in = new BufferedInputStream(storageFileR.fileInputStream);
		
		return storageFileR;
	}
	
	/**
	 * byte�迭�� ���Ͽ� �ִ� ��� �����͸� ��ƿ´�.
	 * 
	 * @param buf
	 * @throws IOException
	 * @throws EOFException
	 */
	public void read(byte[] buf) throws IOException,  EOFException{
		while(in.read(buf) != -1) {;}
		
	}
	
	/**
	 * �ش� ������ �����͸� �д´�.
	 * @param buf
	 * ���Ͽ��� ���� ������ ������� ����
	 * @param index
	 * ���Ͽ��� �����͸� ���� ��, ���ۿ��� ��� ������ ��ġ
	 * @param length
	 * ���۸� ����Ͽ� �о�� ����
	 * @throws  IOException
	 * �̹� close�� ����ưų� ���Ͽ� �б⸦ �����ϸ�  IOException�� ������.
	 * @throws EOFException
	 * ������ �дٰ� ������ ���� �����ϸ� EOFException�� ������.
	 */
	public void read(byte[] buf, int index, int length) throws IOException, EOFException{
		
		for(int i = 0 ; i<index ; i++){
			if (in.read() == -1)
				break;
		}
		
		int returnInt;	
		returnInt = in.read(buf, 0, length);
		
		
		if(returnInt == -1)
			throw new EOFException();
	}
	
	
	/**
	 * �̱���
	 */
	@Override
	public void getPosition() {
		// TODO Auto-generated method stub
	}


	/**
	 * �̱���
	 */
	@Override
	public void seek(int offset, SeekOrigin seekorigin) throws EOFException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * ������ ������ ����� �� ����Ѵ�.
	 * @throws IOException 
	 * �̹� ���������� IOException �� ������.
	 */
	public void flush() throws IOException{
		in.close();
		fileInputStream.close();

		fileInputStream = new FileInputStream(file);
		in = new BufferedInputStream(fileInputStream);
	}
	
	/**
	 * ������ �ݰ� �����Ѵ�.
	 * @throws IOException 
	 * �̹� ���������� IOException �� ������.
	 */
	public void close() throws IOException{
		in.close();
		fileInputStream.close();
		
	}

}
