package com.rssl.phizic.operations.log;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author eMakarov
 * @ created 01.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class GetLastUserLogonInfoOperation extends OperationBase
{

	protected String getInstanceName()
	{
		return Constants.DB_INSTANCE_NAME;
	}
}
