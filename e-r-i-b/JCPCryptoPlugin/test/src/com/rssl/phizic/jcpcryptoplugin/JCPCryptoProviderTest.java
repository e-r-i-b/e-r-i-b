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
	 * Имя ресурса с подписываемыми данными
	 */
	private static final String DATA_FOR_SIGNATURE = "data-for-signature.xml";

	/**
	 * Значение ЭЦП для подписываемых данных в DATA_FOR_SIGNATURE
	 * Используется либо эта константа, либо берётся значение из теста testMakeSignature
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
	 * Тест создания ЭЦП
	 * @throws Exception
	 */
	public void testMakeSignature() throws Exception
	{
		System.out.println("***** Создание ЭЦП *****");

		String data = ResourceHelper.loadResourceAsString(DATA_FOR_SIGNATURE, "UTF8");
		System.out.println("Подписываемые данные:\n" + data);

		testSignature = provider.makeSignature("uec", data);
		System.out.println("ЭЦП в base64:\n" + testSignature.toBase64());
	}

	/**
	 * Тест проверки ЭЦП
	 * @throws Exception
	 */
	public void testCheckSignature() throws Exception
	{
		System.out.println("***** Проверка ЭЦП *****");

		String data = ResourceHelper.loadResourceAsString(DATA_FOR_SIGNATURE, "UTF8");
		System.out.println("Поверяемые данные:\n" + data);

		System.out.println("ЭЦП в base64:\n" + testSignature.toBase64());

		CheckSignatureResult result = provider.checkSignature("uec", data, testSignature);
		System.out.println("Результат проверки: " + result.isSuccessful());
		assertTrue(result.isSuccessful());
	}
}
