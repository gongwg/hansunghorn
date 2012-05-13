package sod.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
public class StorageFile {
	File file;
	
	FileInputStream fileInputStream;
	FileOutputStream fileOutputStream;
	
	BufferedInputStream in;
	BufferedOutputStream out;
	
	
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
	static protected StorageFile createStorageFile(File mFile)throws IOException, NullPointerException{
		if(mFile == null)
			throw new NullPointerException();
		
		StorageFile storageFile = new StorageFile();
		
		storageFile.file = mFile;	
		storageFile.file.createNewFile();//
		
		storageFile.fileInputStream = new FileInputStream(storageFile.file);
		storageFile.fileOutputStream = new FileOutputStream(storageFile.file);
		
		storageFile.in = new BufferedInputStream(storageFile.fileInputStream);
		storageFile.out = new BufferedOutputStream(storageFile.fileOutputStream);
		
		return storageFile;
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
	static protected StorageFile getStorageFile(File mFile)throws IOException, NullPointerException{
		if(mFile == null)
			throw new NullPointerException();
		
		StorageFile storageFile = new StorageFile();
		
		storageFile.file = mFile;
		
		storageFile.fileInputStream = new FileInputStream(storageFile.file);
		storageFile.fileOutputStream = new FileOutputStream(storageFile.file);
		
		storageFile.in = new BufferedInputStream(storageFile.fileInputStream);
		storageFile.out = new BufferedOutputStream(storageFile.fileOutputStream);
		
		return storageFile;
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
	
	public void read(byte[] buf) throws IOException,  EOFException{
		int returnInt;
		returnInt = in.read(buf);
		
		if(returnInt == -1)
			throw new EOFException();
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
		int returnInt;
		returnInt = in.read(buf, index, length);
		
		if(returnInt == -1)
			throw new EOFException();
	}
	
	/**
	 * ���ϳ��� ���� �����Ͱ� ����ġ�� ���� offset��ŭ �̵��Ѵ�.
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
	public void flush() throws IOException{
		in.close();
		out.close();
		
		fileInputStream.close();
		fileOutputStream.close();
		
		fileInputStream = new FileInputStream(file);
		fileOutputStream = new FileOutputStream(file);
		
		in = new BufferedInputStream(fileInputStream);
		out = new BufferedOutputStream(fileOutputStream);
	}
	
	/**
	 * ������ �ݴ´�.
	 * @throws IOException 
	 * �̹� ���������� IOException �� ������.
	 */
	public void close() throws IOException{
		in.close();
		out.close();
		
		fileInputStream.close();
		fileOutputStream.close();
		
	}
}
