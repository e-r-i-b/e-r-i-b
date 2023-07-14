package com.rssl.phizic.security.crypto;

import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.codec.binary.Base64;

/**
 * @author Erkin
 * @ created 22.12.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��� - ����������-������� �������
 */
public class Signature
{
	private final byte[] bytes;

	private Signature(byte[] bytes)
	{
		this.bytes = bytes.clone();
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ������������ ��� �� ��������� �������������
	 * @param bytes - ��� � �������� �������������
	 * @return ���
	 */
	public static Signature fromBinary(byte[] bytes)
	{
		if (bytes == null)
			throw new NullPointerException("�������� 'bytes' �� ����� ���� null");
		return new Signature(bytes);
	}

	/**
	 * ������������ ��� �� BASE64
	 * @param base64 - ��� � BASE64
	 * @return ���
	 */
	public static Signature fromBase64(String base64)
	{
		if (StringHelper.isEmpty(base64))
			throw new IllegalArgumentException("�������� 'base64' �� ����� ���� ������");
		return new Signature(Base64.decodeBase64(base64.getBytes()));
	}

	/**
	 * ���������� ��� � �������� �������������
	 * @return ��� � �������� �������������
	 */
	public byte[] getBytes()
	{
		return bytes.clone();
	}

	/**
	 * ���������� ��� � ���� BASE64
	 * @return ��� � BASE64
	 */
	public String toBase64()
	{
		return new String(Base64.encodeBase64(bytes));
	}
}
