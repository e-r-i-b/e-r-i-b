package com.rssl.phizic.web;

import com.rssl.phizic.config.PropertyReader;

/**
 * @author Kosyakov
 * @ created 20.11.2005
 * @ $Author$
 * @ $Revision$
 *
 * Конфиг страницприложения
 */

public class PropertiesWebPageConfig extends WebPageConfig
{
	public PropertiesWebPageConfig(PropertyReader reader)
	{
		super(reader);
	}

	public int getListLimit ()
    {
        return 20;
    }

	public void doRefresh(){}
}
