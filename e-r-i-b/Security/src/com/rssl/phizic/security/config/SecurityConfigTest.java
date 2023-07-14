package com.rssl.phizic.security.config;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.test.RSSLTestCaseBase;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 16.09.2005
 * Time: 15:31:08
 */
public class SecurityConfigTest extends RSSLTestCaseBase
{
    public void testSecurityConfiguration()
    {
        assertNotNull(ConfigFactory.getConfig(SecurityConfig.class).getPermissionProviderClassName());
    }
}
