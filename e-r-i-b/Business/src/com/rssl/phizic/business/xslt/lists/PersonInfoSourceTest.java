package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.test.annotation.IncludeTest;
import com.rssl.phizic.config.ConfigFactory;

import javax.xml.transform.Source;

/**
 * @author Omeliyanchuk
 * @ created 14.12.2006
 * @ $Author$
 * @ $Revision$
 */

@IncludeTest(configurations = "russlav")
public class PersonInfoSourceTest extends BusinessTestCaseBase
{
	public void testPersonInfoSource() throws Exception
	{
		createTestClientContext();
		EntityListsConfig config = ConfigFactory.getConfig(EntityListsConfig.class);
		PersonInfoSource source     = new PersonInfoSource(config.getListDefinition("currentPersonData.xml"));
		Source entityList = source.getSource(null);
		assertNotNull(entityList);
	}
}
