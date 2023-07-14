package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.autosubscription.ListAutoSubscriptionClaimOperation;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author: vagin
 * @ created: 17.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListAutoSubscriptionClaimAction extends ListAutoSubscriptionClaimsActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListAutoSubscriptionClaimOperation operation = createOperation("ListAutoSubscriptionClaimOperation", "AutoSubscriptionManagment");
		operation.initialize();
		return operation;
	}
}
