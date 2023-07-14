package com.rssl.phizic.utils;

import junit.framework.TestCase;
import org.apache.commons.codec.binary.Base64;

import java.util.Random;

/**
 * @author Krenev
 * @ created 03.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class EncodeHelperTest extends TestCase
{
	public void testEncode()
	{
/*
		for (int i = 0; i < 50; i++)
		{
			String original="1234686141249";
			byte[] encoded = EncodeHelper.encode(original.getBytes());
			byte[] decoded = EncodeHelper.decode(encoded);
			assertEquals(original,new String(decoded));
		}
*/

		for (int i = 0; i < 30; i++)
		{
			String original = rand(5);
			System.out.print(original + ": ");
			byte[] encoded = EncodeHelper.encode(original.getBytes());
			byte[] decoded = EncodeHelper.decode(encoded);
			assertEquals(original, new String(decoded));
		}
/*
		byte[] input = "12398514549".getBytes();
		byte[] bytes = EncodeHelper.encode(input);
		System.out.println(new String(bytes));

		input = "12sgfsa398514fgfd549".getBytes();
		bytes = EncodeHelper.encode(input);
		System.out.println(new String(bytes));
*/
	}

	public static String rand(int n)
	{
		String chars = "0123456789abcdefjhigklmnopqrstuvwxyzABCDEFJHIGKLMNOPQRSTUVWXYZ";

		Random rand = new Random();
		StringBuffer buffer = new StringBuffer(n);
		int lenght = chars.length();

		if (lenght == 0)
			throw new RuntimeException("Количество символов в переданной строке должно быть больше 0");

		char[] charsArray = new char[lenght];
		chars.getChars(0, lenght, charsArray, 0);

		for (int i = 0; i < n; i++)
		{
			int index = rand.nextInt(lenght);
			buffer.append(charsArray[index]);
		}
		return buffer.toString();
	}

	public void testFull()
	{

		test("qqq");
		test("dsgafdgdавыпавып325");
		test("+3246dsgafdgdавыпавып325");
		for (int i = 0; i < 30; i++)
		{
			test(rand(20));
		}
	}

	private static void test(String data)
	{
		System.out.println("original=" + data);
		String encoded = encodeData(data);
		System.out.println("encoded=" + encoded);
		String decoded = decodeData(encoded);
		System.out.println("decoded=" + decoded);
		assertEquals(data, decoded);
	}

	private static String encodeData(String data)
	{
		byte contralInfo = getContralInfo(data);
		//кодируем в base64 идентификатор
		byte[] encodedBase64 = Base64.encodeBase64(data.getBytes());
		System.out.println(">>>" + new String(encodedBase64));
		//вырезаем конечные "="
		int index = encodedBase64.length;
		String end = "";
		while (index > 0 && encodedBase64[index - 1] == '=')
		{
			end = (char) encodedBase64[index - 1] + end;
			index--;
		}
		//заполняем для кодирования
		byte[] forEncode = new byte[index + 1];
		forEncode[0] = contralInfo;
		System.arraycopy(encodedBase64, 0, forEncode, 1, index);
		//кодируем
		byte[] bytes = EncodeHelper.encode(forEncode);

		return new String(bytes) + end;
	}

	private static byte getContralInfo(String synchKey)
	{
		return 1;
	}

	private static String decodeData(String data)
	{
		//вырезаем конечные "=="
		String end = "";
		int i = data.indexOf('=');
		if (i >= 0)
		{
			end = data.substring(i);
			data = data.substring(0, i);
		}
		//декодируем
		byte[] bytes = EncodeHelper.decode(data.getBytes());
		System.out.println(">>>" + new String(bytes) + end);

		//вырезаем контрольную информацию
		byte contralInfo = bytes[0];
		//получаем строку для раскодирования из base64
		byte[] base64bytes = new byte[bytes.length - 1 + end.length()];
		System.arraycopy(bytes, 1, base64bytes, 0, bytes.length - 1);
		System.arraycopy(end.getBytes(), 0, base64bytes, bytes.length - 1, end.length());

		checkContralInfo(base64bytes, contralInfo);
		byte[] decodedBase64 = Base64.decodeBase64(base64bytes);
		//проверяем контрольную информацию
		return new String(decodedBase64);
	}

	private static void checkContralInfo(byte[] decodedBase64, byte contralInfo)
	{
		assertEquals(contralInfo, 1);
	}
}
