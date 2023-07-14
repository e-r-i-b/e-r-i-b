package com.rssl.phizic.operations.locale.csa;

import com.rssl.phizic.operations.locale.LoadEribMessagesOperation;



/**
 * Операция загрузки локалей в цса
 * @author koptyaev
 * @ created 24.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class LoadCSAEribMessagesOperation extends LoadEribMessagesOperation
{
	private static final String CSA_INSTANCE_NAME = "CSA";

	@Override
	protected String getInstanceName()
	{
		return CSA_INSTANCE_NAME;
	}
}
