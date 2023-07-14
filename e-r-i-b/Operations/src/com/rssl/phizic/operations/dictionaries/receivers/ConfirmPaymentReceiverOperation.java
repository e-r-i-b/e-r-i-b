package com.rssl.phizic.operations.dictionaries.receivers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.MultiInstancePaymentReceiverService;
import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.dictionaries.ReceiverState;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.MultiInstancePersonService;
import com.rssl.phizic.business.persons.PersonOperationMode;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.person.PersonOperationBase;

/**
 * @author Krenev
 * @ created 15.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmPaymentReceiverOperation extends ConfirmableOperationBase
{
	private static final MultiInstancePaymentReceiverService paymentReceiverService = new MultiInstancePaymentReceiverService();
	private static final PersonService personService = new PersonService();
	private static final SimpleService simpleService = new SimpleService();
	private PaymentReceiverBase receiver;

	public void initialize(Long id) throws BusinessException
	{
		receiver = paymentReceiverService.findReceiver(id, null);
		if (receiver == null)
		{
			throw new BusinessException("ѕолучатель не найден (id=" + id + ")");
		}
		setStrategyType();
	}

	public PaymentReceiverBase getConfirmableObject()
	{
		return receiver;
	}

	@Transactional
	protected void saveConfirm() throws BusinessException
	{
		ActivePerson person = personService.findByLogin(receiver.getLogin());

		receiver.setState(ReceiverState.ACTIVE);
		paymentReceiverService.update(receiver,null);

		if(PersonOperationMode.shadow.equals(PersonOperationBase.getPersonMode(person.getId())))
		{//если есть shadow то правим и там
			simpleService.replicate(receiver, MultiInstancePersonService.PERSON_SHADOW_INSTANCE_NAME);
		}
	}
}
