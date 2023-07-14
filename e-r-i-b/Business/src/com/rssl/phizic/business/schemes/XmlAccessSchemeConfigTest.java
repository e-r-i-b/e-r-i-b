package com.rssl.phizic.business.schemes;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * @author Evgrafov
 * @ created 24.11.2005
 * @ $Author$
 * @ $Revision$
 */

public class XmlAccessSchemeConfigTest extends BusinessTestCaseBase
{
    public void testXmlAccessSchemeConfig()
    {
        XmlAccessSchemesConfig xmlSchemesConfig = ConfigFactory.getConfig(XmlAccessSchemesConfig.class);
        assertNotNull(xmlSchemesConfig);
   }

}
