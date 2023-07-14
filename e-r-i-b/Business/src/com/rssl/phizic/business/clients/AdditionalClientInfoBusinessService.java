package com.rssl.phizic.business.clients;

import com.rssl.phizic.auth.modes.UserRegistrationMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.AdditionalClientInfoService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;

import java.util.Calendar;

/**
 * Сервис для получения дополнительной информации о клиентах
 * @author basharin
 * @ created 20.11.14
 * @ $Author$
 * @ $Revision$
 */

public class AdditionalClientInfoBusinessService  extends AbstractService implements AdditionalClientInfoService
{
	private static final PersonService personService = new PersonService();

	protected AdditionalClientInfoBusinessService(GateFactory factory)
	{
		super(factory);
	}

	@SuppressWarnings({"MethodWithTooManyParameters"})
	public UserRegistrationMode getUserRegistrationMode(String firstName, String lastName, String middleName, String docNumber, Calendar birthDate, String tb) throws GateException, GateLogicException
	{
		try
		{
			ActivePerson person = personService.getByFIOAndDocUnique(lastName, firstName, middleName, "", docNumber, birthDate, tb);
			if (person == null)
				return null;
			return person.getUserRegistrationMode();
		}
		catch(BusinessException ex)
		{
			throw new GateException(ex);
		}
		catch(BusinessLogicException ex)
		{
			throw new GateLogicException(ex);
		}
	}
}
