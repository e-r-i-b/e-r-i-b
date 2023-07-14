package com.rssl.phizic.operations.person;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.dataaccess.common.counters.CounterService;
import com.rssl.phizic.dataaccess.common.counters.Counter;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Получение списка представителей <br/>
 * List<ActivePerson> empoweredPersons = operation.createQuery("empoweredPersons").executeList();
 * @author Roshka
 * @ created 13.04.2006
 * @ $Author$
 * @ $Revision$
 */

public class PrintPersonOperation extends PersonExtendedInfoOperationBase
{
	private static final CounterService counterService = new CounterService();
	
	private List<ActivePerson> empoweredPersons;

	//TODO: получение personId - криво!
	public ActivePerson getPerson ()
	{
		return super.getPerson();
	}

	public Map<Long, List<Service>> getEmpoweredPersonsServices () throws DataAccessException, BusinessException
	{
		List<ActivePerson> persons = getEmpoweredPersons();
		Map<Long, List<Service>> servicesMap = new HashMap<Long, List<Service>>();

		for (ActivePerson person : persons)
		{
			List<Service> services;
			AccessScheme scheme = schemeOwnService.findScheme(person.getLogin(), AccessType.simple,getInstanceName());
			if (scheme==null)
			{
				services = new ArrayList<Service>();
			}
			else
			{
				services = scheme.getServices();
			}

			servicesMap.put(person.getId(), services);
		}

		return servicesMap;
	}

	public Map<Long, List<AccountLink>> getEmpoweredPersonsAccounts () throws DataAccessException, BusinessException, BusinessLogicException
	{
		List<ActivePerson> persons = getEmpoweredPersons();
		Map<Long, List<AccountLink>> accountsMap = new HashMap<Long, List<AccountLink>>();

		for (ActivePerson person : persons)
		{
			List<AccountLink> list = loadAccountLinks(person);
			accountsMap.put(person.getId(), list);
		}

		return accountsMap;
	}

	public Map<Long, List<CardLink>> getEmpoweredPersonsCards() throws DataAccessException, BusinessException, BusinessLogicException
	{
		List<ActivePerson> persons = getEmpoweredPersons();
		Map<Long, List<CardLink>> accountsMap = new HashMap<Long, List<CardLink>>();

		for (ActivePerson person : persons)
		{
			List<CardLink> list = loadCardLinks(person);
			accountsMap.put(person.getId(), list);
		}

		return accountsMap;
	}

	public List<ActivePerson> getEmpoweredPersons () throws DataAccessException
	{
		if (empoweredPersons==null)
		{
			empoweredPersons = createQuery("empoweredPersons").executeList();
		}
		//noinspection ReturnOfCollectionOrArrayField
		return empoweredPersons;
	}

	public List<AccountLink> getDopAgreementAccountLinks () throws DataAccessException
	{
		Query query = createQuery("dopAgreementAccountLinks");
		query.setParameter("loginId", getPerson().getLogin().getId());
		return query.executeList();
	}

	//TODO: исправить в запросе BUG033234 
	public String getDopAgreementNumber() throws BusinessException
	{
		String agreementNumber = getPerson().getAgreementNumber();
		try
		{
			Long next = counterService.getNext(Counter.createSimpleCounter("DOP"+agreementNumber));
			return agreementNumber + StringHelper.appendLeadingZeros(next.toString(),2);

		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}