package com.rssl.phizic.web.security;

import com.rssl.phizic.auth.CheckLoginTest;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.CryptoConfirmRequest;
import com.rssl.phizic.auth.modes.CryptoConfirmStrategy;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.certification.CertificateOwnServiceTest;
import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * @author Evgrafov
 * @ created 04.01.2007
 * @ $Author: egorovaav $
 * @ $Revision: 35050 $
 */

@SuppressWarnings({"JavaDoc", "MagicNumber"})
public class CryptoConfirmStrategyTest extends BusinessTestCaseBase
{
	private Login testLogin;

	protected void setUp() throws Exception
	{
		super.setUp();

		testLogin = CheckLoginTest.getTestLogin();
		CertificateOwnServiceTest.setActiveCertificate(testLogin);
	}

	public void test() throws SecurityLogicException
	{
		InternalTransfer internalPayment = new InternalTransfer();

		internalPayment.setGround("Проверка");

		/*Metadata metadata = MetadataCache.getBasicMetadata("paymentsBundle", "InternalPayment");

		DocumentFieldValuesSource fieldValuesSource = new DocumentFieldValuesSource(metadata, internalPayment);
		FormDataConverter converter = new FormDataConverter(metadata.getForm(), fieldValuesSource);

		byte[] bytes = converter.toSignableForm();*/

		CryptoConfirmStrategy strategy = new CryptoConfirmStrategy();

		ConfirmRequest request = strategy.createRequest(testLogin, internalPayment, "132321324321",null);

		assertFalse(request.isError());
		assertNull(request.getErrorMessage());

		CryptoConfirmRequest cryptoConfirmRequest = (CryptoConfirmRequest) request;
		assertNotNull(cryptoConfirmRequest.getStringToSign());
	}
}