package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.dictionaries.MultiInstancePaymentReceiverService;
import com.rssl.phizic.operations.person.PersonOperationBase;
import com.rssl.phizic.operations.ListEntitiesOperation;

/**
 * @author Kidyaev
 * @ created 30.11.2005
 * @ $Author$
 * @ $Revision$
 */
public abstract class PaymentReceiverOperationBase extends PersonOperationBase  implements ListEntitiesOperation<UserRestriction>
{
	protected final static MultiInstancePaymentReceiverService service = new MultiInstancePaymentReceiverService();

	public void initialize(Long personId) throws BusinessException, BusinessLogicException
	{
		setPersonId(personId);
	}

	public Long getLoginId()
	{
		return getPerson().getLogin().getId();
	}
}
