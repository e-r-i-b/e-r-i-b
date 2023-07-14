package com.rssl.phizic.business;

import com.rssl.phizic.business.persons.clients.AdditionalClientIds;
import com.rssl.phizic.business.persons.clients.AdditionalClientsService;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizicgate.manager.services.persistent.clients.ClientServiceImpl;
import org.apache.commons.lang.StringUtils;

import java.util.Calendar;
import java.util.List;

/**
 * @author: Pakhomova
 * @created: 27.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class BusinessBankrollService extends AbstractService implements BankrollService
{
	private static final String ROUTE_NULL = "null|";

	protected static BankrollService delegate;
	private static AdditionalClientsService additionalClientsService = new AdditionalClientsService();
	

	public BusinessBankrollService(GateFactory factory)
	{
		super(factory);
		delegate = (BankrollService) getDelegate(BankrollService.class.getName() + DELEGATE_KEY);
	}

	public List<Account> getClientAccounts(Client client) throws GateException, GateLogicException
	{
		List<Account> accounts = delegate.getClientAccounts(client);

		try
		{
			AdditionalClientIds additionalClientIds = additionalClientsService.findAdditionalsByClientId(client.getId());

			ClientService clientService = GateSingleton.getFactory().service(ClientService.class);

			if (additionalClientIds != null)
			{
				for (AdditionalClientIds clientId: additionalClientIds.getAddClientIds())
				{
					Client cl = clientService.getClientById(clientId.getClientId());
					String uuid =  cl.getId();

					//признак того что клиент не найден.
					if (StringUtils.contains(uuid,ROUTE_NULL))
						continue;

					accounts.addAll(delegate.getClientAccounts(cl));
				}
			}
		}
		catch (BusinessException ex)
		{
			throw new GateException(ex);
		}
		catch (GateLogicException exx)
		{
			throw new GateException(exx);
		}
		return accounts;
	}

	public GroupResult<String, Account> getAccount(String... accountId)
	{
		return delegate.getAccount(accountId);
	}

	public AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateLogicException, GateException
	{
		return delegate.getAbstract(object, fromDate, toDate, withCardUseInfo);
	}

	public GroupResult<Object, AbstractBase> getAbstract(Long number, Object... object)
	{
		return delegate.getAbstract(number, object);
	}

	public List<Card> getClientCards(Client client) throws GateException, GateLogicException
	{
		List<Card> cards = delegate.getClientCards(client);

		try
		{
			AdditionalClientIds additionalClientIds = additionalClientsService.findAdditionalsByClientId(client.getId());

			ClientService clientService = GateSingleton.getFactory().service(ClientService.class);

			if (additionalClientIds != null)
			{
				for (AdditionalClientIds clientId: additionalClientIds.getAddClientIds())
				{
					Client cl = clientService.getClientById(clientId.getClientId());
					cards.addAll(delegate.getClientCards(cl));
				}
			}
		}
		catch (BusinessException ex)
		{
			throw new GateException(ex);
		}
		catch (GateLogicException exx)
		{
			throw new GateException(exx);
		}
		return cards;
	}

	public GroupResult<String, Card> getCard(String... cardId)
	{
		return delegate.getCard(cardId);
	}

	public GroupResult<Card, List<Card>> getAdditionalCards(Card... mainCard)
	{
		return delegate.getAdditionalCards(mainCard);
	}

	public GroupResult<Card, Account> getCardPrimaryAccount(Card... card)
	{
		return delegate.getCardPrimaryAccount(card);
	}

	public GroupResult<Card, Client> getOwnerInfo(Card... cards)
	{
		return delegate.getOwnerInfo(cards);
	}

	public GroupResult<Pair<String, Office>, Client> getOwnerInfoByCardNumber(Pair<String, Office> ... cardInfo)
	{
		return delegate.getOwnerInfoByCardNumber(cardInfo);
	}

	public GroupResult<Account, Client> getOwnerInfo(Account... account)
	{
		GroupResult<Account, Client> clients = delegate.getOwnerInfo(account);
		GroupResult<Account, Client> res = new GroupResult<Account, Client>();

		for (Account key: clients.getKeys())
		{
			try{
				res.putResult(key, getMainClient(GroupResultHelper.getResult(clients, key)));
			}
			catch (IKFLException e)
			{
				res.putException(key, e);
			}
		}
		return res;
	}

	/**
	 * по переданному клиенту возвращает главного, если таковой есть
	 * @param client - клиент, у которого ищем возможных "родителей"
	 * @return основного клиента (для тех "кривых" баз, где несколько клиентов составляют одного)
	 **/
	private Client getMainClient(Client client) throws GateException, GateLogicException
	{
		try
		{
			String ownerId = client.getId();
		    AdditionalClientIds mainClientId = additionalClientsService.findMainIdByClientId(ownerId);
			if ( mainClientId != null )
			{
				ClientService service = new ClientServiceImpl(this.getFactory());
				return service.getClientById(mainClientId.getClientId());
			}
			else
			{
				return client;
			}

		}
		catch (BusinessException ex)
		{
			throw new GateException(ex);
		}
	}

	public AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		return delegate.getAccountExtendedAbstract(account, fromDate, toDate);
	}

	public GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountInfo)
	{
		return delegate.getAccountByNumber(accountInfo);
	}
	public GroupResult<Pair<String,Office>,Card> getCardByNumber(Client client, Pair<String, Office>... cardInfo)
	{
		return delegate.getCardByNumber(client, cardInfo);
	}

	public AccountAbstract getAccHistoryFullExtract(Account account, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateException, GateLogicException
	{
		return delegate.getAccHistoryFullExtract(account, fromDate, toDate, withCardUseInfo);
	}
}
