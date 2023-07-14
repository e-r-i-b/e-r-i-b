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
 * ЭЦП - Электронно-цифовая Подпись
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
	 * Конструирует ЭЦП из бинарного представления
	 * @param bytes - ЭЦП в бинарном представлении
	 * @return ЭЦП
	 */
	public static Signature fromBinary(byte[] bytes)
	{
		if (bytes == null)
			throw new NullPointerException("Аргумент 'bytes' не может быть null");
		return new Signature(bytes);
	}

	/**
	 * Конструирует ЭЦП из BASE64
	 * @param base64 - ЭЦП в BASE64
	 * @return ЭЦП
	 */
	public static Signature fromBase64(String base64)
	{
		if (StringHelper.isEmpty(base64))
			throw new IllegalArgumentException("Аргумент 'base64' не может быть пустым");
		return new Signature(Base64.decodeBase64(base64.getBytes()));
	}

	/**
	 * Возвращает ЭЦП в бинарном представлении
	 * @return ЭЦП в бинарном представлении
	 */
	public byte[] getBytes()
	{
		return bytes.clone();
	}

	/**
	 * Возвращает ЭЦП в виде BASE64
	 * @return ЭЦП в BASE64
	 */
	public String toBase64()
	{
		return new String(Base64.encodeBase64(bytes));
	}
}
