package com.rssl.phizic.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;
import com.rssl.phizic.business.resources.external.PaymentSystemIdLink;

import java.util.List;

/**
 * @author Erkin
 * @ created 20.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class PaymentSystemIdLinkHelper
{
	private static final MultiInstanceExternalResourceService externalResourceService
			= new MultiInstanceExternalResourceService();

	/**
	 * Возвращает payment-id-link для заданного пользователя
	 * @param person - пользователь
	 * @param instance
	 * @return
	 * @throws BusinessException
	 */
	public static PaymentSystemIdLink getPersonPaymentSystemIdLink(Person person, String instance)
			throws BusinessException, BusinessLogicException
	{
		if (person == null)
			throw new NullPointerException("Argument 'person' cannot be null");

		List<PaymentSystemIdLink> list = externalResourceService.getLinks(
				person.getLogin(), PaymentSystemIdLink.class, instance);
		return list.size() > 0 ? list.get(0) : null;
	}
}
