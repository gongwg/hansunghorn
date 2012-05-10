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
public class StorageFile {
	File file;
	
	
	StorageFile(File mFile){
		file = mFile;
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
	 * ���Ͽ� �б⸦ �����ϸ�  IOException�� ������.
	 * @throws EOFException
	 * ������ �дٰ� ������ ���� �����ϸ� EOFException�� ������.
	 */
	public void read(byte[] buf, int index, int length) throws IOException, EOFException{
		
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
	 */
	public void getLength(){
		
	}
	
	/**
	 * ������ ������ ����� �� ����Ѵ�.
	 */
	public void flush(){
		
	}
	
	/**
	 * ������ �ݴ´�.
	 */
	public void close(){
		
	}
}
