package com.rssl.phizic.security.crypto;

/**
 * @author Krenev
 * @ created 17.07.2008
 * @ $Author$
 * @ $Revision$
 */
public interface CryptoService
{
	/**
	 * ��������� ���� ������� ����
	 * @param m ������� ������
	 * @return ������, ���������� ���
	 */
	public byte[] hash(byte[] m);

	/**
	 * ��������� ���� ������� ����
	 * @param algorinm ��������
	 * @param m ������� ������
	 * @return ������, ���������� ���
	 */
	public byte[] hash(byte[] m, String algorinm);

	/**
	 * ��������� ���� ������.
	 * ��������� ������� ������ "UTF-16LE"
	 * @param m ������� ������
	 * @return ������, ���������� ���
	 */
	public String hash(String m);

	/**
	 * ���������� HMAC
	 * @param key ����
	 * @param data ������
	 * @param algorithm ��������
	 * @return ??? ��� ��� :)
	 */
	public byte[] hmac(byte[] key, byte[] data, String algorithm);

}
