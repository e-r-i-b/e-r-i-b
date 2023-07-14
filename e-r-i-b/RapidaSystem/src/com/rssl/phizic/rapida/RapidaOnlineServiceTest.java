package com.rssl.phizic.rapida;

import com.rssl.phizic.utils.test.RSSLTestCaseBase;
import com.rssl.phizic.gate.exceptions.GateException;
import org.w3c.dom.Document;

/**
 * @author Krenev
 * @ created 21.11.2007
 * @ $Author$
 * @ $Revision$
 */
public class RapidaOnlineServiceTest extends RSSLTestCaseBase
{
	public void testManual() throws GateException
	{
		RapidaService rapidaOnlineService = RapidaFactory.service(RapidaService.class);
		Document document = rapidaOnlineService.sendPayment("/test?function=check&PaymExtId=IKFL56x123a&PaymSubjTp=306&Amount=1234500&Params=11 1581315;53 154333;16 148;17 77&TermType=08&TermID=00001234");
		assertNotNull(document);
	}
}
