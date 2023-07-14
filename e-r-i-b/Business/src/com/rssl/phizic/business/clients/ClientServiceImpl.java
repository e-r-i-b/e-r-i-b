package com.rssl.phizic.business.clients;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;
import org.apache.commons.beanutils.BeanUtils;

import java.util.List;

/**
 * Заполняет поля internalOwnerId.
 * Получаем клиента, и, в случае если поле internalOwnerId не заполненно этот клиент ищется в нашей базе,
 * по идентификатору указывающему на внешнюю систему, затем значение internalOwnerId клиента полученного
 * из базы копируется клиенту полученнному из внешней системы.
 * @author Balovtsev
 * @created 21.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class ClientServiceImpl extends RoutableServiceBase implements ClientService
{
	private ClientService delegate;
	
	private static final PersonService personService = new PersonService();

	protected ClientServiceImpl(GateFactory factory)
	{
		super(factory);
		delegate = (ClientService) getDelegate(ClientService.class.getName() + ROUTABLE_DELEGATE_KEY);
	}

	/**
	 * @param id ID пользователя
	 * @return клиент с заполененным полем internalOwnerId
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public Client getClientById(String id) throws GateLogicException, GateException
	{
		Client delegatedClient = delegate.getClientById(id);
		return getSupplementedClient(delegatedClient);
	}

	/**
	 * @param  client описание, содержащее ограниченный надор данных
	 * @return клиент с заполененным полем internalOwnerId
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public Client fillFullInfo(Client client) throws GateLogicException, GateException
	{
		Client delegatedClient = delegate.fillFullInfo(client);
		return getSupplementedClient(delegatedClient);
	}

	/**
	 * @param client - клиент
	 * @param office - офис, в котором искать
	 * @param firstResult пропустить первые firstResult - 1 элементов выборки
	 * @param maxResults максимальное количество возвращаемых данных
	 * @return список клиентов с заполененным полем internalOwnerId
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public List<Client> getClientsByTemplate(Client client, Office office, final int firstResult, final int maxResults) throws GateLogicException, GateException
	{
		List<Client> delegatedClients = delegate.getClientsByTemplate(client, office, firstResult, maxResults);

		if(delegatedClients == null || delegatedClients.isEmpty() || delegatedClients.get(0).getInternalOwnerId() != null)
		{
			return delegatedClients;
		}

		for (Client delegatedClient : delegatedClients)
		{
			getSupplementedClient(delegatedClient);
		}
		return delegatedClients;
	}

	/**
	 * @param rbTbBranchId - идентификатор офиса, в котором заведена карта, по которой пользователь вошел в систему.
	 * @param cardNumber - Номер карты, по которой проводилась идентификация.
	 * @return клиент с заполененным полем internalOwnerId
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public Client getClientByCardNumber(String rbTbBranchId, String cardNumber) throws GateLogicException, GateException
	{   
		Client delegatedClient = delegate.getClientByCardNumber(rbTbBranchId, cardNumber);
		return getSupplementedClient(delegatedClient);
	}

	private Client getSupplementedClient(Client client) throws GateLogicException, GateException
	{
		if(client == null || client.getInternalOwnerId() != null)
		{
			return client;
		}

		try
		{
			ActivePerson person = personService.findByClientId( client.getId() );
			return copyInternalOwnerId(client, person);
		}
		catch (BusinessException be)
		{
			throw new GateException(be);
		}
	}

	private Client copyInternalOwnerId(Client clientToCopy, Person person) throws GateLogicException
	{
		if(clientToCopy == null || person == null)
		{
			return clientToCopy;
		}

		try
		{
			BeanUtils.copyProperty(clientToCopy, "internalOwnerId", person.getLogin().getId());
		}
		catch (Exception e)
		{
			throw new GateLogicException(e);
		}

		return clientToCopy;
	}
}
