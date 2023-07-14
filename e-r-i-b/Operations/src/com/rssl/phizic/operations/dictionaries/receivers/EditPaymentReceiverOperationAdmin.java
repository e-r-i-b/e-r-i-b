package com.rssl.phizic.operations.dictionaries.receivers;

import com.rssl.phizic.business.BusinessException;

/**
 * @author Krenev
 * @ created 15.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class EditPaymentReceiverOperationAdmin extends EditPaymentReceiverOperationBase
{
	public void save(boolean autoActive) throws BusinessException
	{
		switchToShadow();
		saveInternal(true);//Всегда активен
	}
}
