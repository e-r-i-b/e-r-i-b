package com.rssl.phizic.operations.autosubscription;

import com.rssl.phizic.operations.ListEntitiesOperation;

/**
 * @author: vagin
 * @ created: 17.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListAutoSubscriptionClaimOperation extends AutoSubscriptionOperationBase implements ListEntitiesOperation
{
	/**
	 * Получение id клиента
	 * @return id клиента
	 */
	public Long getLoginId()
	{
		return getPerson().getLogin().getId();
	}
}
