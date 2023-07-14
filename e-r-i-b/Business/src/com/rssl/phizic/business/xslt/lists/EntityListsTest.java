package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.config.ConfigFactory;

import javax.xml.transform.Source;

/**
 * @author Evgrafov
 * @ created 03.11.2005
 * @ $Author: bogdanov $
 * @ $Revision$
 */

@SuppressWarnings({"JavaDoc"})
public class EntityListsTest extends BusinessTestCaseBase
{
	protected void setUp() throws Exception
	{
		super.setUp();
		initializeRsV51Gate();
	}

	public void testAllAccountsList() throws Exception
    {
        createTestClientContext();
	    EntityListsConfig config = ConfigFactory.getConfig(EntityListsConfig.class);
        AccountListSource listSource = new AccountListSource(config.getListDefinition("all-accounts.xml"));
        Source            source     = listSource.getSource(null);
        assertNotNull(source);

        clearTestClientContext();

        AccountListSource listSource1 = new AccountListSource(config.getListDefinition("active-accounts-info.xml"));
        Source            source1     = listSource1.getSource(null);
        assertNotNull(source1);
    }

    public void testLoadEntitiesDefinitions()
    {
        XmlEntitiesListsConfig conf = ConfigFactory.getConfig(XmlEntitiesListsConfig.class);

        EntityListDefinition entityListDefinition = conf.getListDefinition("accounts.xml");
        assertNotNull(entityListDefinition);

        EntityListDefinition entityListDefinition1 = conf.getListDefinition("banklist.xml");
        assertNotNull(entityListDefinition1);
    }

}
