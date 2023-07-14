package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.autosubscription.ListAutoSubscriptionClaimOperation;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * Список заявок клиента по функционалу автопереводов
 *
 * @author khudyakov
 * @ created 08.10.14
 * @ $Author$
 * @ $Revision$
 */
public class ListAutoTransfersClaimAction extends ListAutoSubscriptionClaimsActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListAutoSubscriptionClaimOperation operation = createOperation("ListAutoSubscriptionClaimOperation", "AutoTransfersManagement");
		operation.initialize();
		return operation;
	}
}
