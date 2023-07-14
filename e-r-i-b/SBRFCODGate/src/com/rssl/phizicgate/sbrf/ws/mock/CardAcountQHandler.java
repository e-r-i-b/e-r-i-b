package com.rssl.phizicgate.sbrf.ws.mock;

import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author Gainanov
 * @ created 19.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class CardAcountQHandler extends MockHandlerSupport
{
	private long counter = 0;

	protected Document makeMockRequest(Document message, String parentMessageId) throws Exception
	{
		counter++;
		if (counter % 10 == 5)
		{
			//�� ������ 10 ������ ������� ������.
			return XmlHelper.loadDocumentFromResource("cardAccount_error.xml");
		}
		return XmlHelper.loadDocumentFromResource("cardAccount.xml");
	}
}
