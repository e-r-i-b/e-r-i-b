package com.rssl.phizic.business.payments.forms;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.business.payments.ListsURIResolver;

import javax.xml.transform.TransformerException;
import javax.xml.transform.Source;

/**
 * @author Kidyaev
 * @ created 20.03.2006
 * @ $Author: Kidyaev $
 * @ $Revision: 977 $
 */
public class ListsURIResolverTest extends BusinessTestCaseBase
{
	public void testListsURIResolver() throws TransformerException
	{
		ListsURIResolver listsURIResolver = new ListsURIResolver();
		Source           source           = listsURIResolver.resolve("status-list.xml", "");
		assertNotNull(source);
	}
}
