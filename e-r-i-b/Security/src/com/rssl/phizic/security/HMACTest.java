package com.rssl.phizic.security;

import com.rssl.phizic.utils.StringUtils;
import junit.framework.TestCase;

import java.io.UnsupportedEncodingException;

/**
 * @author Evgrafov
 * @ created 13.02.2007
 * @ $Author: Roshka $
 * @ $Revision: 3960 $
 */

@SuppressWarnings({"JavaDoc"})
public class HMACTest extends TestCase
{
	private static final String PASSWORD      = "1234";
	private static final String PASSWORD_HASH = "0F037584C99E7FD4F4F8C59550F8F507";

	private static final String CLIENT_RND    = "4FD5CE7DF50F7B2E";
	private static final String SERVER_RND    = "283ED1D793525FCC";

	private static final String HMAC_MD5      = "CC31D62FFB9349ABAAE1A0D82F737CDA";

	public void testHash() throws Exception
	{
		byte[] digest = MD5Utils.hash(PASSWORD);

		assertNotNull(digest);
		assertEquals(PASSWORD_HASH, StringUtils.toHexString(digest));
	}

	public void testHmacMD5() throws Exception
	{
		byte[] hash      = MD5Utils.hash(PASSWORD);
		byte[] dataBytes = StringUtils.fromHexString(SERVER_RND + CLIENT_RND);
		byte[] digest    = MD5Utils.hmac(hash, dataBytes);

		String result = StringUtils.toHexString(digest);

		assertEquals(HMAC_MD5, result);
	}

	public void manual123()
	{
		hashS("123");
		hashS("test");
		hashS("проверка");
		hashH("310032003300");
		hashH("1234567890ABCDEF");
	}

	private void hashH(String hex)
	{
		byte[] bytes = StringUtils.fromHexString(hex);
		System.out.print("hash(0x" + hex + ")=");
		System.out.println("0x" + StringUtils.toHexString(MD5Utils.hash(bytes)));
	}

	private void hashS(String str)
	{
		System.out.print("hash(\"" + str + "\")=");
		System.out.println("0x" + StringUtils.toHexString(MD5Utils.hash(str)));
	}

	/**
	 * данные http://www.faqs.org/rfcs/rfc2202.html
	 */
	public void testRFC2202() throws UnsupportedEncodingException
	{
		assertEquals(
				"9294727a3638bb1c13f48ef8158bfc9d".toUpperCase(),
				StringUtils.toHexString(MD5Utils.hmac(
						StringUtils.fromHexString("0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b"),
						"Hi There".getBytes("ASCII"))));

		assertEquals(
				"750c783e6ab0b503eaa86e310a5db738".toUpperCase(),
				StringUtils.toHexString(MD5Utils.hmac(
						"Jefe".getBytes("ASCII"),
						"what do ya want for nothing?".getBytes("ASCII"))
				));

		assertEquals(
				"56be34521d144c88dbb8c733f0e8b3f6".toUpperCase(),
				StringUtils.toHexString(MD5Utils.hmac(
						StringUtils.fromHexString("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"),
						StringUtils.fromHexString(repeat("dd", 50)))
				));

		assertEquals(
				"697eaf0aca3a3aea3a75164746ffaa79".toUpperCase(),
				StringUtils.toHexString(MD5Utils.hmac(
						StringUtils.fromHexString("0102030405060708090a0b0c0d0e0f10111213141516171819"),
						StringUtils.fromHexString(repeat("cd", 50)))
				));

		assertEquals(
				"56461ef2342edc00f9bab995690efd4c".toUpperCase(),
				StringUtils.toHexString(MD5Utils.hmac(
						StringUtils.fromHexString("0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c0c"),
						"Test With Truncation".getBytes("ASCII"))
				));

		assertEquals(
				"6b1ab7fe4bd7bf8f0b62e6ce61b9d0cd".toUpperCase(),
				StringUtils.toHexString(MD5Utils.hmac(
						StringUtils.fromHexString(repeat("aa", 80)),
						"Test Using Larger Than Block-Size Key - Hash Key First".getBytes("ASCII"))
				));

		assertEquals(
				"6f630fad67cda0ee1fb1f562db3aa53e".toUpperCase(),
				StringUtils.toHexString(MD5Utils.hmac(
						StringUtils.fromHexString(repeat("aa", 80)),
						"Test Using Larger Than Block-Size Key and Larger Than One Block-Size Data".getBytes("ASCII"))
				));


	}

	private String repeat(String s, int n)
	{
		StringBuilder sb = new StringBuilder(s.length() * n);
		for(int i = 0; i < n; i++)
		{
			sb.append(s);
		}
		return sb.toString();
	}

	public void printGOSTHash(String hex)
	{
		System.out.print("hash(0x" + hex + ")=");
		System.out.println("0x" + StringUtils.toHexString(GOSTUtils.hash(hex)));
	}

	public void printGOSTHmac(String key, String text)
	{
		System.out.print("hmac(key: " + key + " data: " + text + ")=");
		System.out.println("0x" + StringUtils.toHexString(GOSTUtils.hmac(key.getBytes(), text.getBytes())));
	}

	public void manualTestHashGOST()
	{
		printGOSTHash("123");
		printGOSTHash("ZZZ");
		printGOSTHash("Я пришел к тебе с приветом рассказать, что солнце встало, что оно зеленым светом по листам затрепетало...");
	}

	public void manualTestHmacGOST()
	{
		printGOSTHmac("123", "123");
		printGOSTHmac("zzz", "zzz");
		printGOSTHmac("DBD290FC47405C3066ADAEBC55AF5ED5B990A0A34F6BB4B3F2B188A0325D578A",
				"Я пришел к тебе с приветом рассказать, что солнце встало, что оно зеленым светом по листам затрепетало...");
	}

	public void testHMAC() throws UnsupportedEncodingException
	{
		String hex = "Я пришел к тебе с приветом рассказать, что солнце встало, что оно зеленым светом по листам затрепетало...";
		byte[] bytes = hex.getBytes();
		assertEquals("DBD290FC47405C3066ADAEBC55AF5ED5B990A0A34F6BB4B3F2B188A0325D578A", StringUtils.toHexString(GOSTUtils.hash(bytes)));
		assertEquals("8F0CD5BA7564FD5842327C9EE02B5EC10F86EF77296F6808C76979E3B078C300", StringUtils.toHexString(GOSTUtils.hash(hex)));
		assertEquals("51A5FC2743EBB709957BB6552D9DD90DE504B0114E887AD4F0FE60AF55B43578", StringUtils.toHexString(GOSTUtils.hash("123")));

		byte[] key = StringUtils.fromHexString("DBD290FC47405C3066ADAEBC55AF5ED5B990A0A34F6BB4B3F2B188A0325D578A");
		assertEquals("F68C7D3A423E5597CEC4854161B18F45DE9A6B335BFA11ABE2C7C64B5F436DB0", StringUtils.toHexString(GOSTUtils.hmac(key, bytes)));
	}

	public void manualTestPINsHashes() throws UnsupportedEncodingException
	{
		assertEquals("FBA8E8846F5075BD6FBF7D2C12210A35F99DF12EC0702E26FBC4F017780633D0", StringUtils.toHexString(GOSTUtils.hash("8MJks6lVgU")));
		assertEquals("98550F597881E82F74203B4225E17AB2C54B8A1AD1682CFFD92901042AF56C53", StringUtils.toHexString(GOSTUtils.hash("fC3CvGq1CQ")));
	}

}
