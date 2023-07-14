package com.rssl.phizic.business.resources.external.resolver;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eMakarov
 * @ created 08.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class DepositLinkResolver implements LinkResolver<DepositLink>
{
	private static DepositService depositService;
	private static final int SHIFT = 8;

	public List getLinks(CommonLogin login, String instanceName) throws BusinessException
	{
		return getLinks(login.getId(), instanceName);
	}

	public List getLinks(Long loginId, String instanceName) throws BusinessException
	{
		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		ActivePerson person = provider.getPersonData().getPerson();
		ClientService clientService = GateSingleton.getFactory().service(ClientService.class);

		if (depositService == null)
			depositService = GateSingleton.getFactory().service(DepositService.class);

		List<DepositLink> depositLink = new ArrayList<DepositLink>();
		try
		{
			Client client = null;
			if(person.getTrustingPersonId() != null)
	        {
		        PersonService personService = new PersonService();
				Person temp = personService.findById(person.getTrustingPersonId());
				client = clientService.getClientById(temp.getClientId());
	        }
			else
			{
				client = clientService.getClientById(person.getClientId());
			}
			List<? extends Deposit> deposits = depositService.getClientDeposits(client);
			int counter = 0;
			for (Deposit deposit : deposits)
			{
				DepositLink link = new DepositLink(deposit);
				//индивидуальный id link'а для каждого пользователя
				link.setId(loginId << SHIFT + counter++);
				link.setLoginId(loginId);
				depositLink.add(link);
			}
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException ex)
		{
			throw new BusinessException(ex);
		}

		return depositLink;
	}

	public List getChangedLinks(CommonLogin login, String instanceName) throws BusinessException
	{
		return getLinks(login,instanceName);
	}

	public List getInSystemLinks(CommonLogin login, String instanceName) throws BusinessException
	{
		return getLinks(login,instanceName);
	}
	public List getInMobileLinks(CommonLogin login, String instanceName) throws BusinessException
	{
		return getLinks(login,instanceName);
	}

	public List getInSocialLinks(CommonLogin login, String instanceName) throws BusinessException
	{
		return getLinks(login,instanceName);
	}

	public List getInATMLinks(CommonLogin login, String instanceName) throws BusinessException
	{
		return getLinks(login,instanceName);
	}

	public DepositLink findByExternalId(
			CommonLogin login,
			String externalId,
			String instanceName
	) throws BusinessException
	{
		throw new UnsupportedOperationException();
	}
}
