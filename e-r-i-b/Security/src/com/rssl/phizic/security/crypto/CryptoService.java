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
	 * Получение хеша массива байт
	 * @param m входной массив
	 * @return массив, содержащий хэш
	 */
	public byte[] hash(byte[] m);

	/**
	 * Получение хеша массива байт
	 * @param algorinm алгоритм
	 * @param m входной массив
	 * @return массив, содержащий хэш
	 */
	public byte[] hash(byte[] m, String algorinm);

	/**
	 * Получение хеша строки.
	 * Кодировка входной строки "UTF-16LE"
	 * @param m входной массив
	 * @return массив, содержащий хэш
	 */
	public String hash(String m);

	/**
	 * Вычисление HMAC
	 * @param key ключ
	 * @param data данные
	 * @param algorithm алгоритм
	 * @return ??? что это :)
	 */
	public byte[] hmac(byte[] key, byte[] data, String algorithm);

}
