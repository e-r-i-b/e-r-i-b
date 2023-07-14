package com.rssl.phizic.jcpcryptoplugin;

import com.rssl.phizic.security.crypto.CheckSignatureResult;
import com.rssl.phizic.security.crypto.Signature;
import com.rssl.phizic.utils.resources.ResourceHelper;
import junit.framework.TestCase;

/**
 * @author Erkin
 * @ created 21.12.2010
 * @ $Author$
 * @ $Revision$
 */
public class JCPCryptoProviderTest extends TestCase
{
	/**
	 * ��� ������� � �������������� �������
	 */
	private static final String DATA_FOR_SIGNATURE = "data-for-signature.xml";

	/**
	 * �������� ��� ��� ������������� ������ � DATA_FOR_SIGNATURE
	 * ������������ ���� ��� ���������, ���� ������ �������� �� ����� testMakeSignature
	 */
	private static Signature testSignature = Signature.fromBase64(
			"W/5I+87gBhXPeBIGtDCfx/RgypqK7+LathD4P1e3RCGhbRfhEmGQncX5mGimjJDhkT9Kr6n/mqe3wmihHh/qDw==");


	private JCPCryptoProvider provider;

	///////////////////////////////////////////////////////////////////////////

	protected void setUp() throws Exception
	{
		super.setUp();
		provider = new JCPCryptoProvider();
	}

	/**
	 * ���� �������� ���
	 * @throws Exception
	 */
	public void testMakeSignature() throws Exception
	{
		System.out.println("***** �������� ��� *****");

		String data = ResourceHelper.loadResourceAsString(DATA_FOR_SIGNATURE, "UTF8");
		System.out.println("������������� ������:\n" + data);

		testSignature = provider.makeSignature("uec", data);
		System.out.println("��� � base64:\n" + testSignature.toBase64());
	}

	/**
	 * ���� �������� ���
	 * @throws Exception
	 */
	public void testCheckSignature() throws Exception
	{
		System.out.println("***** �������� ��� *****");

		String data = ResourceHelper.loadResourceAsString(DATA_FOR_SIGNATURE, "UTF8");
		System.out.println("���������� ������:\n" + data);

		System.out.println("��� � base64:\n" + testSignature.toBase64());

		CheckSignatureResult result = provider.checkSignature("uec", data, testSignature);
		System.out.println("��������� ��������: " + result.isSuccessful());
		assertTrue(result.isSuccessful());
	}
}
