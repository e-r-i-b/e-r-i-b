package com.rssl.phizic.security.certification;

import com.rssl.phizic.auth.CheckLoginTest;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.security.crypto.Certificate;
import com.rssl.phizic.security.crypto.CryptoProvider;
import com.rssl.phizic.security.crypto.CryptoProviderHelper;
import com.rssl.phizic.security.test.SecurityTestBase;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 22.12.2006
 * @ $Author: mescheryakova $
 * @ $Revision: 22429 $
 */

@SuppressWarnings({"JavaDoc"})
public class CertificateOwnServiceTest extends SecurityTestBase
{
	private CertificateOwnService certificateOwnService;
	private CryptoProvider cryptoProvider;
	private String         testCertId1;
	private Certificate cert;

	protected void setUp() throws Exception
	{
		super.setUp();

		certificateOwnService = new CertificateOwnService();
		cryptoProvider = CryptoProviderHelper.getDefaultFactory().getProvider();
		testCertId1 = "?????";
		cert = cryptoProvider.findCertificate(testCertId1);

		CommonLogin login = certificateOwnService.findOwner(cert);

		if(login != null)
			certificateOwnService.remove(login, cert);

	}

	public void testAddRemoveAssignActivate() throws Exception
	{
		Login testLogin = CheckLoginTest.getTestLogin();

		CommonLogin owner1 = certificateOwnService.findOwner(cert);
		assertNull(owner1);

		//должно работать много раз
		certificateOwnService.add(testLogin, cert);
		certificateOwnService.add(testLogin, cert);

		CommonLogin owner2 = certificateOwnService.findOwner(cert);
		assertNotNull(owner2);

		Certificate active1 = certificateOwnService.findActive(testLogin);
		assertNull(active1);

		//аналогично
		certificateOwnService.setActive(testLogin, cert);
		certificateOwnService.setActive(testLogin, cert);

		Certificate active2 = certificateOwnService.findActive(testLogin);
		assertNotNull(active2);

		List<Certificate> certs = certificateOwnService.find(testLogin);

		assertNotNull(certs);
		assertTrue(certs.size() > 0);

		certificateOwnService.remove(testLogin, cert);
		certificateOwnService.remove(testLogin, cert);
	}

	protected void tearDown() throws Exception
	{
		certificateOwnService = null;
		cryptoProvider = null;
		testCertId1 = null;
		cert = null;
		super.tearDown();
	}

	/**
	 * Установить тестовому юзеру активный сертификат
	 * @param login логин
	 * @return установленный сертификат
	 * @throws Exception
	 */
	public static Certificate setActiveCertificate(CommonLogin login) throws Exception
	{
		CryptoProvider provider = CryptoProviderHelper.getDefaultFactory().getProvider();

		Certificate certificate = provider.findCertificate(login.getId() + "??????");
		CertificateOwnService service = new CertificateOwnService();

		service.add(login, certificate);
		service.setActive(login, certificate);

		return certificate;
	}
}