package com.rssl.phizic.operations.dictionaries.receivers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.PaymentReceiverPhiz;
import com.rssl.phizic.business.dictionaries.PaymentReceiverService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;

/**
 * @author Gainanov
 * @ created 28.07.2008
 * @ $Author$
 * @ $Revision$
 */

public class GetPaymentReceiverListOperationClient extends GetPaymentReceiverListOperation
{
	private final static ServiceProviderService serviceProviderService = new ServiceProviderService();
	private final static PaymentReceiverService paymentReceiverService = new PaymentReceiverService();

	public void initialize(Long personId) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException("В контексте клиента мспользовать метод void initialize()");
	}

	public void initialize() throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		//для представителя получатели того пользователя, которому он пренадлежит.
		ActivePerson activePerson = personData.getPerson();
		if (activePerson.getTrustingPersonId() != null)
		{
			super.initialize(activePerson.getTrustingPersonId());
		}
		else
		{
			super.initialize(activePerson.getId());
		}
	}

	public String getInstanceName()
	{
		//клиент никогда не работает с теневой базой.
		return null;
	}
}
