package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.MultiInstancePersonService;
import com.rssl.phizic.business.persons.PersonOperationMode;
import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.dictionaries.MultiInstancePaymentReceiverService;

/**
 * @author malafeevsky
 * @ created 30.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class RemovePaymentReceiverOperationClient extends RemovePaymentReceiverOperation
{
	private final static MultiInstancePaymentReceiverService service = new MultiInstancePaymentReceiverService();

	public String getInstanceName()
	{
		//клиент никогда не работает с теневой базой.
		return null;
	}

	@Transactional
	public void remove() throws BusinessException
	{
		if(PersonOperationMode.shadow.equals(getPersonMode(getPersonId())))
		{
			//если требуется регистрации изменений, то клиент будет в shadow, если так, то надо получателя оттуда тоже удалять.
			PaymentReceiverBase receiverShadow = service.findReceiver(getEntity().getId(), MultiInstancePersonService.PERSON_SHADOW_INSTANCE_NAME);
			if (receiverShadow != null)
			{
				service.remove(receiverShadow, MultiInstancePersonService.PERSON_SHADOW_INSTANCE_NAME);
			}
		}
		super.remove();
	}
}
