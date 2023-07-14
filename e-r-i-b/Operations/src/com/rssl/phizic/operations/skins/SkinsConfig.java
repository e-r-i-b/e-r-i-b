package com.rssl.phizic.operations.skins;

import com.rssl.phizic.config.*;

/**
 * @author Erkin
 * @ created 02.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class SkinsConfig extends Config
{
	private static final String ALLOW_CHANGE_ADMIN_SKIN = "com.rssl.iccs.skins.allow.change.admin.skin";

	public SkinsConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
	}

	boolean doesChangeAdminSkinAllowed()
	{
		return getBoolProperty(ALLOW_CHANGE_ADMIN_SKIN);
	}
}
