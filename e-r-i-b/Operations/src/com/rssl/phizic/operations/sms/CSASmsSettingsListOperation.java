package com.rssl.phizic.operations.sms;

import com.rssl.phizic.business.Constants;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 *
 * Операция редактирования смс ресурсов из ЦСА
 *
 * @author  Balovtsev
 * @version 05.04.13 12:21
 */
public class CSASmsSettingsListOperation extends OperationBase implements ListEntitiesOperation
{
	@Override
	protected String getInstanceName()
	{
		return Constants.DB_CSA;
	}
}
