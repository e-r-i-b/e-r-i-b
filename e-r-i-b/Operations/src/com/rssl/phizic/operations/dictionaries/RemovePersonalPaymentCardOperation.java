package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.resources.external.PaymentSystemIdLink;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author Pakhomova
 * @ created 12.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class RemovePersonalPaymentCardOperation extends PersonalPaymentCardOperationBase
	implements RemoveEntityOperation<PaymentSystemIdLink, UserRestriction>
{
	public void initialize(Long personId) throws BusinessException, BusinessLogicException
	{
		super.initialize(personId);
	}

	public PaymentSystemIdLink getEntity()
	{
		return getLink();
	}

	/**
	 * Удалить карту
	 * @throws BusinessException
	 */
	@Transactional
	public void remove() throws BusinessException
	{
		PaymentSystemIdLink link = getLink();
		if (link == null)
			throw new BusinessException("Не назначена карта персональных платежей");
		externalResourceService.removeLink(link, getInstanceName());
	}
}
