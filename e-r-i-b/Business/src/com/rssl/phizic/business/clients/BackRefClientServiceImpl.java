package com.rssl.phizic.business.clients;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.BackRefClientService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 04.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class BackRefClientServiceImpl extends AbstractService implements BackRefClientService
{
	private static final String SEPARATOR = "|";
	private static final PersonService personService = new PersonService();
	private static final SimpleService simpleService = new SimpleService();

	public BackRefClientServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Client getClientById(Long id) throws GateLogicException,GateException
	{
		try
		{
			ActivePerson person = personService.findByLoginId(id);
			if (person == null)
				return null;
			return person.asClient();
		}
		catch(BusinessException ex)
		{
			throw new GateException(ex);
		}
	}

	@SuppressWarnings("MethodWithTooManyParameters")
	public Client getClientByFIOAndDoc(String firstName, String lastName, String middleName, String docSeries, String docNumber, Calendar birthDate, String tb) throws GateLogicException, GateException
	{
		try
		{
			Person person = personService.getByFIOAndDocUnique(lastName, firstName, middleName, docSeries, docNumber, birthDate, tb);
			if (person == null)
				return null;
			return new ClientImpl(new ActivePerson(person));
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

	public String getClientDepartmentCode(Long loginId) throws GateException, GateLogicException
	{
		try
		{
			Login login = simpleService.findById(Login.class, loginId);
			if (login == null)
				return null;
			return StringHelper.getEmptyIfNull(login.getLastLogonCardDepartment())
					+ SEPARATOR + StringHelper.getEmptyIfNull(login.getLastLogonCardTB())
					+ SEPARATOR + StringHelper.getEmptyIfNull(login.getLastLogonCardOSB())
					+ SEPARATOR + StringHelper.getEmptyIfNull(login.getLastLogonCardVSP());
		}
		catch (BusinessException e)
		{
			 throw new GateException(e);
		}
	}

	public String getClientCreationType(String clientId) throws GateLogicException, GateException
	{
		try
		{
			Person person = personService.findByClientId(clientId);
			if (person == null)
				return null;
			return person.getCreationType().toString();
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}
}
