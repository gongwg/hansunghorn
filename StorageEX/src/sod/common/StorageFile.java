package sod.common;


import java.io.EOFException;
import java.io.File;
import java.io.IOException;


enum SeekOrigin
{
    Begin,
    Current,
    End,
}

/**
 * 
 * @author MB
 *
 */
public abstract class StorageFile {
	File file;
	
	
	StorageFile() {
		
		
	}
	

	
	/**
	 * 
	 * @param mFile
	 * @throws IOException
	 * ���� ������ �����ϸ� IOException�� ������.
	 * @throws NullPointerException
	 * ���ڷ� �Ѿ�� ���� Null�̸� NullPointerException�� ������.
	 */
	 static public StorageFile createStorageFile(File mFile)throws IOException, NullPointerException{
		return null;
		 
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
		return null;
	}
	
	

	/**
	 * ���ϳ��� ���� �����Ͱ� ����ġ�� ���� offset��ŭ �̵��Ѵ�.
	 * (���� �̱���)
	 * @param offset
	 * ���������͸� �̵��ϰ��� �ϴ� ��ġ
	 * @param seekorigin
	 * ������ �̵��ϱ� ���� ������������ ��ġ
	 * @throws EOFException
	 * ���� �����͸� �̵��ϴٰ� ������ ���� �����ϸ� EOFException�� ������.
	 */
	public void seek(int offset, SeekOrigin seekorigin) throws EOFException{
		
	}
	
	/**
	 * ������ ������������ ��ġ�� ��´�.
	 * (���� �̱���)
	 */
	public void getPosition(){
		
	}
	
	/**
	 * ������ ���̸� �����´�.
	 * ������ byte
	 */
	public int getLength(){
		return (int) file.length();
	}
	
	/**
	 * ������ ������ ����� �� ����Ѵ�.
	 * @throws IOException 
	 * �̹� ���������� IOException �� ������.
	 */
	public abstract void flush() throws IOException;
	
	/**
	 * ������ �ݰ� �����Ѵ�.
	 * @throws IOException 
	 * �̹� ���������� IOException �� ������.
	 */
	public abstract void close() throws IOException;
}
