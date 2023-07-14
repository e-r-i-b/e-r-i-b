package com.rssl.phizic.web;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author Kosyakov
 * @ created 20.11.2005
 * @ $Author$
 * @ $Revision$
 */
public abstract class WebPageConfig extends Config
{
	protected WebPageConfig(PropertyReader reader)
	{
		super(reader);
	}

	public abstract int getListLimit ();
}
