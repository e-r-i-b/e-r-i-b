package com.rssl.phizicgate.mock.ima;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.TransactionBase;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import com.rssl.phizic.gate.ima.IMAccountService;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizicgate.mock.clients.ClientImpl;
import com.rssl.phizicgate.mock.offices.OfficeImpl;

import java.util.*;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class MockImaService extends AbstractService implements IMAccountService
{
	private IMAccountGenerator accountGenerator = new IMAccountGenerator();
	private IMAccountAbstractGenerator abstractGenerator = new IMAccountAbstractGenerator();
	private IMATransactionGenerator transactionGenerator = new IMATransactionGenerator();

	private ExternalResourceService resourceService = new ExternalResourceService();

	protected MockImaService(GateFactory factory)
	{
		super(factory);
	}

	public List<IMAccount> getClientIMAccounts(Client client) throws GateException, GateLogicException
	{
		ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();

		try
		{
			List<IMAccountLink> links = resourceService.getInSystemLinks(person.getLogin(), IMAccountLink.class);

			List<IMAccount> result = new ArrayList<IMAccount>();

			int lenght = links == null ? 2 : links.size();

			if (links == null || links.size() < lenght)
			{
				for (int i = 0; i < lenght; i++)
				{
					resourceService.addIMAccountLink(person.getLogin(), accountGenerator.generate(), null);
				}
				return getClientIMAccounts(client);
			}

			Map<String, IMAccount> imAccounts = initiateIMAccounts(getIMAccountsExternalId(links));

			for (String externalId : imAccounts.keySet())
			{
				result.add(imAccounts.get(externalId));
			}
			return result;
		}
		catch (BusinessException be)
		{
			return new ArrayList<IMAccount>();
		}
		catch (BusinessLogicException e)
		{
			return new ArrayList<IMAccount>();
		}
	}

	public GroupResult<String, IMAccount> getIMAccount(String... externalIds)
	{
		GroupResult<String, IMAccount> result = new GroupResult<String, IMAccount>();

		Map<String, IMAccount> imAccounts = initiateIMAccounts(externalIds);

		if (imAccounts == null)
		{
			return result;
		}

		for (String external : externalIds)
		{
			result.putResult(external, imAccounts.get(external));
		}
		return result;
	}

	public GroupResult<IMAccount, Office> getLeadOffice(IMAccount... imAccounts)
	{
		GroupResult<IMAccount, Office> result = new GroupResult<IMAccount, Office>();

		Office office = initiateOffice();

		for (IMAccount imAccount : imAccounts)
		{
			result.putResult(imAccount, office);
		}
		return result;
	}

	public GroupResult<IMAccount, Client> getOwnerInfo(IMAccount... imAccounts)
	{
		GroupResult<IMAccount, Client> result = new GroupResult<IMAccount, Client>();

		PersonData data = PersonContext.getPersonDataProvider().getPersonData();

		Client client = initiateClient(data.getPerson());
		for (IMAccount imAccount : imAccounts)
		{
			result.putResult(imAccount, client);
		}
		return result;
	}

	private Client initiateClient(Person person)
	{
		ClientImpl client = new ClientImpl();
		client.setDisplayId(person.getDisplayClientId());
		client.setResident(person.getIsResident());
		client.setAgreementNumber(person.getAgreementNumber());
		client.setFirstName(person.getFirstName());
		client.setSurName(person.getSurName());
		client.setFullName(person.getFullName());
		return client;
	}

	private Office initiateOffice()
	{
		Office office = new OfficeImpl();

		Map<String, Object> codeFields = new HashMap();
		codeFields.put("region", "040");
		codeFields.put("branch", "1555");
		codeFields.put("office", "64");

		office.buildCode(codeFields);
		office.setAddress("„. ¬ÓÎÓ„‰‡ ÛÎ. ÀÂÌËÌ„‡‰ÒÍ‡ˇ 70");
		office.setName("÷≈Õ“–¿À‹ÕŒ≈ Œ—¡ π8641/0111");

		return office;
	}

	public IMAccountAbstract getAbstract(IMAccount imAccount, Calendar fromDate, Calendar toDate)
	{
		Map<IMAccount, IMAccountAbstract> result = initiateAbstract(fromDate, toDate, 12L, imAccount.getId());

		for (IMAccount key : result.keySet())
		{
			if (key.getId().equals(imAccount.getId()))
			{
				return result.get(key);
			}
		}
		return null;
	}

	public GroupResult<IMAccount, IMAccountAbstract> getAbstract(Long count, IMAccount... imAccounts)
	{
		GroupResult<IMAccount, IMAccountAbstract> result = new GroupResult<IMAccount, IMAccountAbstract>();

		Map<IMAccount, IMAccountAbstract> map = initiateAbstract(count, imAccounts);

		if (map == null)
		{
			return result;
		}

		for (IMAccount key : map.keySet())
		{
			result.putResult(key, map.get(key));
		}
		return result;
	}

	public GroupResult<String, IMAccount> getIMAccountByNumber(Client client, String... imAccountNumbers)
	{
		return null;
	}

	private Map<String, IMAccount> initiateIMAccounts(String... externalIds)
	{
		Map<String, IMAccount> result = new HashMap<String, IMAccount>();

		for (String external : externalIds)
		{
			accountGenerator.setId(external);
			result.put(external, accountGenerator.generate());
		}
		return result;
	}

	private Map<IMAccount, IMAccountAbstract> initiateAbstract(Calendar from, Calendar to, Long count, String... externalIds)
	{
		Map<IMAccount, IMAccountAbstract> result = new HashMap<IMAccount, IMAccountAbstract>();

		abstractGenerator.setToDate(to);
		abstractGenerator.setFromDate(from);

		for (String externalId : externalIds)
		{
			accountGenerator.setId(externalId);
			abstractGenerator.setTransactions(generateTransactions(count));

			result.put(accountGenerator.generate(), abstractGenerator.generate());
		}
		return result;
	}

	private Map<IMAccount, IMAccountAbstract> initiateAbstract(Long count, IMAccount... imAccounts)
	{
		String[] externalIds = getIMAccountsExternalId(imAccounts);

		if (externalIds == null)
		{
			return new HashMap<IMAccount, IMAccountAbstract>();
		}
		return initiateAbstract(null, null, count, externalIds);
	}

	private List<TransactionBase> generateTransactions(Long count)
	{
		List<TransactionBase> listOfTransactions = new ArrayList<TransactionBase>();

		for (int i = 0; i < count; i++)
		{
			listOfTransactions.add(transactionGenerator.generate());
		}
		return listOfTransactions;
	}

	private String[] getIMAccountsExternalId(IMAccount... imAccounts)
	{
		if (imAccounts == null)
		{
			return null;
		}

		String[] externalIds = new String[imAccounts.length];

		int index = 0;
		for (IMAccount imAccount : imAccounts)
		{
			externalIds[index++] = imAccount.getId();
		}
		return externalIds;
	}

	private String[] getIMAccountsExternalId(List<IMAccountLink> imAccounts)
	{
		IMAccountLink[] links = imAccounts.toArray(new IMAccountLink[imAccounts.size()]);
		IMAccount[] accounts = new IMAccount[links.length];

		int index = 0;
		for (IMAccountLink link : links)
		{
			accounts[index++] = link.getImAccount();
		}
		return getIMAccountsExternalId(accounts);
	}
}