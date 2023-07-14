package com.rssl.phizic.operations.dictionaries.receivers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.dictionaries.MultiInstancePaymentReceiverService;
import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;
import com.rssl.phizic.business.persons.PersonCreateConfig;
import com.rssl.phizic.business.persons.MultiInstancePersonService;
import com.rssl.phizic.business.persons.PersonOperationMode;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.person.PersonOperationBase;

/**
 * @author Krenev
 * @ created 15.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class EditPaymentReceiverOperationClient extends EditPaymentReceiverOperationBase
{
	private final static MultiInstancePaymentReceiverService service = new MultiInstancePaymentReceiverService();
	private static final MultiInstanceSimpleService simpleService = new MultiInstanceSimpleService();

	public void save(boolean autoActive) throws BusinessException
	{
		saveInternal(autoActive);
		if(PersonOperationMode.shadow.equals(PersonOperationBase.getPersonMode(getPersonId())))
		{//если требуется регистрации изменений, то клиент будет в shadow, если так, то надо получателя в тени тоже модифицировать.
			simpleService.replicate(getEntity(), MultiInstancePersonService.PERSON_SHADOW_INSTANCE_NAME);
		}
	}
	public String getInstanceName()
	{
		//клиент никогда не работает с теневой базой.
		return null;
	}
}
