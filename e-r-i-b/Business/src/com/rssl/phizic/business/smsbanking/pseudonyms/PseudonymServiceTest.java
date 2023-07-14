package com.rssl.phizic.business.smsbanking.pseudonyms;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.CheckBankLoginTest;
import com.rssl.phizic.business.resources.external.AccountLink;

import java.util.ArrayList;

/**
 * @author eMakarov
 * @ created 24.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class PseudonymServiceTest extends BusinessTestCaseBase
{
	PseudonymService pseudonymService = new PseudonymService();

	public void testGenerate() throws Exception
	{
		CommonLogin login = CheckBankLoginTest.getTestLogin();
		AccountLink link = new AccountLink();
		link.setExternalId("9876543210");
		link.setLoginId(login.getId());
		link.setPaymentAbility(Boolean.TRUE);
		link.setNumber("12304560789012304560");
		// у тестового клиента нет пседонимов, поэтому подаем пустой ArrayList
		Pseudonym pseudonym = pseudonymService.generate(login, link, new ArrayList<Pseudonym>());

		assertNotNull(pseudonym);
	}
}
