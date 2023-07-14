package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.business.BusinessException;

import javax.xml.transform.Source;

/**
 * @author Kidyaev
 * @ created 30.11.2006
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc"})
public class BankcellDictionariesSourceTest extends BusinessTestCaseBase
{
	public void testBankcellDictionariesSource() throws BusinessException
	{
		BankcellDictionariesSource source     = new BankcellDictionariesSource();
		Source                     entityList = source.getSource(null);
		assertNotNull(entityList);
	}
}
