package com.rssl.phizic.business;

import com.rssl.phizic.service.StartupServiceDictionary;
import com.rssl.phizic.utils.test.JUnitConfigurator;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 16.09.2005
 * Time: 14:27:17
 * @noinspection OverlyCoupledClass
 */
public class JUnitConfiguratorImpl implements JUnitConfigurator
{
    public void configure()
    {
        try
        {
	        StartupServiceDictionary serviceStarter = new StartupServiceDictionary();
	        serviceStarter.startMBeans();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
