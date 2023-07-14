package com.rssl.phizic.operations.registration;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.registration.RegistrationFailureReason;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.Calendar;

/**
 * операция для сохранения причины отказа от самостоятельной регистрации
 * @author basharin
 * @ created 12.12.13
 * @ $Author$
 * @ $Revision$
 */

public class RegistrationFailureReasonOperation extends OperationBase implements EditEntityOperation
{
	private static final SimpleService simpleService = new SimpleService();
	private RegistrationFailureReason registrationFailureReason;

	public void initialize(String reason)
	{
		registrationFailureReason = new RegistrationFailureReason();
		registrationFailureReason.setReason(reason);
	}

	public void save() throws BusinessException
	{
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		registrationFailureReason.setLoginId(person.getLogin().getId());
		registrationFailureReason.setAdditionDate(Calendar.getInstance());

		simpleService.add(registrationFailureReason, com.rssl.phizic.logging.Constants.DB_INSTANCE_NAME);
	}

	public RegistrationFailureReason getEntity() throws BusinessException, BusinessLogicException
	{
		return registrationFailureReason;
	}
}
