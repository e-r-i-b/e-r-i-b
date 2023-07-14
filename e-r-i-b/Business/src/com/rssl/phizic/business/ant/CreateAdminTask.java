package com.rssl.phizic.business.ant;

import com.rssl.phizic.utils.test.SafeTaskBase;
import com.rssl.phizic.business.security.DefaultAdminCreator;

/**
 * @author Kosyakov
 * @ created 18.04.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1931 $
 */
public class CreateAdminTask extends SafeTaskBase
{
	public void safeExecute() throws Exception
	{
		new DefaultAdminCreator().create();
	}
}
