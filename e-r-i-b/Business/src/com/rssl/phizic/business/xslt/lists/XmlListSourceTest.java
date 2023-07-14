package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.test.BusinessTestCaseBase;

import javax.xml.transform.Source;
import java.util.HashMap;

/**
 * @author Kidyaev
 * @ created 20.03.2006
 * @ $Author: krenev $
 * @ $Revision: 6543 $
 */
public class XmlListSourceTest extends BusinessTestCaseBase
{
	private final String FILE_NAME = "com/rssl/phizic/business/payments/forms/status-list.xml";
	public void testXmlListSource() throws Exception
	{
		EntityListSource source = new XmlResourceSource(FILE_NAME);
		Source           list   = source.getSource(new HashMap<String, String>());
		assertNotNull(list);
	}
}
