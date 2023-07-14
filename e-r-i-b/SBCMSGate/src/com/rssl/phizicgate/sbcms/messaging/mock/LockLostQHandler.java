package com.rssl.phizicgate.sbcms.messaging.mock;

import org.w3c.dom.Document;
import com.rssl.phizic.utils.xml.XmlHelper;

/**
 * @author Egorova
 * @ created 04.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class LockLostQHandler extends MockHandlerSupport
{
	protected Document makeMockRequest(Document message) throws Exception
	{
		return XmlHelper.loadDocumentFromResource("com/rssl/phizicgate/sbcms/messaging/mock/xml/WAY4_LOCK_OK.xml");
	}
}
