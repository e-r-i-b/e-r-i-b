package com.rssl.phizic.operations.locale.csa;

import com.rssl.phizic.operations.locale.RemoveLocaleOperation;

/**
 * Операция удаления локали в цса
 * @author koptyaev
 * @ created 29.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class RemoveCSALocaleOperation extends RemoveLocaleOperation
{
	private static final String CSA_INSTANCE_NAME = "CSA";

	@Override
	protected String getInstanceName()
	{
		return CSA_INSTANCE_NAME;
	}
}
