package com.rssl.phizicgate.rsV51.clients;

import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizicgate.rsV51.data.GateRSV51Executor;
import org.hibernate.Session;
import org.hibernate.Query;

import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Kidyaev
 * Date: 03.10.2005
 * Time: 18:01:00
 */
public class ClientServiceImpl extends AbstractService implements ClientService
{
	public ClientServiceImpl()
	{
		super(null);
	}

	public ClientServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Client getClientById(final String id) throws GateException
	{
		final Long longId;
		try
		{
			longId = Long.decode(id);
		}
		catch (NumberFormatException e)
		{
			// возвращаем null так как пользователь класса не знает ничего
			// о внутреннем утройстве id, следовательно посупаем так же как и в случае если
			// передано числовое id дл€ коотрого нет клиента в Ѕƒ
			return null;
		}
		try
		{
		    return GateRSV51Executor.getInstance().execute(new HibernateAction<Client>()
		    {
		        public Client run(Session session) throws Exception
		        {
			        Query namedQuery = session.getNamedQuery("GetFullClientInfo");
			        namedQuery.setParameter("personId", longId);

			        ClientImpl client = (ClientImpl) namedQuery.uniqueResult();
		            if(client != null)
		            {
		                client.setPartial(false);
			            client.setDocuments(getClientDocs(client));
		            }

			        return client;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new GateException(e);
		}
	}

	private List<ClientDocument> getClientDocs(final Client client) throws GateException
	{
		try
		{
			return GateRSV51Executor.getInstance().execute(new HibernateAction<List<ClientDocument>>()
			{
				public List<ClientDocument> run(Session session) throws Exception
				{
					//noinspection unchecked
					Query query = session
							.getNamedQuery("GetClientDocuments")
							.setParameter("personId", client.getId());
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

    public Client fillFullInfo(Client client) throws GateException
    {
        if(client == null)
            throw new NullPointerException("ѕараметр client не может быть null");

        ClientImpl clientImpl = (ClientImpl)client;
        ClientImpl dbClient = (ClientImpl) getClientById(clientImpl.getId());

        if(dbClient == null)
            throw new GateException("Ќе найден клиентс ID=" + client.getId());

        clientImpl.update(dbClient);
        clientImpl.setPartial(false);

        return clientImpl;
    }

	public Client getClientByCardNumber(String rbTbBranchId, String cardNumber) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("ќпераци€ получени€ клиента по карте не поддерживаетс€");
	}	

    public List<Client> getClientsByTemplate(Client client, Office office, final int firstResult, final int maxResults) throws GateException
    {
	    final String surNameLikePattern = prepareLikeParam(client.getSurName());
	    final String firstNameLikePattern = prepareLikeParam(client.getFirstName());
	    final String patrNameLikePattern = prepareLikeParam(client.getPatrName());

        try
        {
            return GateRSV51Executor.getInstance().execute(new HibernateAction<List<Client>>()
            {
                public List<Client> run(Session session) throws Exception
                {
	                //noinspection unchecked
	                Query query = session
			                .getNamedQuery("GetLightClientByName")
	                        .setParameter("surNameLikePattern", surNameLikePattern)
			                .setParameter("firstNameLikePattern", firstNameLikePattern)
			                .setParameter("patrNameLikePattern", patrNameLikePattern);
	                if(firstResult>0)
			                query.setFirstResult(firstResult-1);
					if (maxResults > 0)
						query.setMaxResults(maxResults);

	                // TODO переделать ƒокумент должен заполн€тьс€ в запросе
	                List<Client> clients = query.list();
					for (Client c : clients)
					{
						if (c != null)
						{
							((ClientImpl)c).setDocuments(getClientDocs(c));
						}
					}
	                return clients;
                }
            });
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
    }

    private String prepareLikeParam(String str)
    {
        String likePattern = "%";
	    return ( str == null || "".equals(str)  ?  likePattern  :   str + likePattern );
    }
}
