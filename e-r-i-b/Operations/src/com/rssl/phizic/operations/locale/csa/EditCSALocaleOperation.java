package com.rssl.phizic.operations.locale.csa;

import com.rssl.phizic.operations.locale.EditLocaleOperation;

/**
 * Операция редактирования локали в цса
 * @author koptyaev
 * @ created 17.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditCSALocaleOperation extends EditLocaleOperation
{
	private static final String CSA_INSTANCE_NAME = "CSA";

	@Override
	protected String getInstanceName()
	{
		return CSA_INSTANCE_NAME;
	}
}
