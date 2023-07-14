package com.rssl.phizicgate.rsretailV6r4.clients;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.Address;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.NullParameterType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;
import com.rssl.phizicgate.rsretailV6r4.messaging.RetailXMLHelper;
import com.rssl.phizgate.common.messaging.retail.RetailMessagingConfig;
import com.rssl.phizgate.common.messaging.retail.RetailMessagingConfigImpl;
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.util.XMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.ArrayList;
import javax.xml.transform.TransformerException;

/**
 * Created by IntelliJ IDEA.
 * User: Kidyaev
 * Date: 03.10.2005
 * Time: 18:01:00
 */
public class ClientServiceImpl extends AbstractService implements ClientService
{
	private static final int ADDR_TYPE_REG  = 1;
	private static final int ADDR_TYPE_FACT = 2;

	public ClientServiceImpl()
	{
		super(null);
	}

	public ClientServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Client getClientById(final String id) throws GateException,GateLogicException
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
			// передано числовое id для коотрого нет клиента в БД
			return null;
		}
		try
		{
		    return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<Client>()
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
			            client.setRealAddress(getClientAddress(client,"GetClientAddress", ADDR_TYPE_FACT));
			            client.setLegalAddress(getClientAddress(client,"GetClientAddress", ADDR_TYPE_REG ));
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

    public Client fillFullInfo(Client client) throws GateException, GateLogicException
    {
        if(client == null)
            throw new NullPointerException("Параметр client не может быть null");

        ClientImpl clientImpl = (ClientImpl)client;
        ClientImpl dbClient = (ClientImpl) getClientById(clientImpl.getId());

        if(dbClient == null)
            throw new GateException("Не найден клиент с ID=" + client.getId());

        clientImpl.update(dbClient);
        clientImpl.setPartial(false);

        return clientImpl;
    }

	public Client getClientByCardNumber(String rbTbBranchId, String cardNumber) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("Операция получения клиента по карте не поддерживается");
	}	

    public List<Client> getClientsByTemplate(final Client client, Office office, final int firstResult, final int maxResults) throws GateException, GateLogicException
    {
	    final String surNameLikePattern = prepareLikeParam(client.getSurName());
	    final String firstNameLikePattern = prepareLikeParam(client.getFirstName());
	    final String patrNameLikePattern = prepareLikeParam(client.getPatrName());
	    try
        {
            return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<List<Client>>()
            {
                public List<Client> run(Session session) throws Exception
                {
	                Long longId = null;
	                if (client.getId() != null && client.getId().length()>0)
	                {
		                try
		                {
			                longId = Long.decode(client.getId());
		                }
		                catch (NumberFormatException e)
		                {
			                // возвращаем null так как пользователь класса не знает ничего
			                // о внутреннем утройстве id, следовательно посупаем так же как и в случае если
			                // передано числовое id для коотрого нет клиента в БД
			                return null;
		                }
	                }
	                //noinspection unchecked
	                Query query = session
			                .getNamedQuery("GetLightClientByName")
	                        .setParameter("surNameLikePattern", surNameLikePattern)
			                .setParameter("firstNameLikePattern", firstNameLikePattern)
			                .setParameter("patrNameLikePattern", patrNameLikePattern);
	                if (longId == null)
	                {
		                query.setParameter("personId", NullParameterType.VALUE, NullParameterType.INSTANCE);
	                }
	                else
	                {
		                query.setParameter("personId", longId);
	                }

	                if(firstResult>0)
			                query.setFirstResult(firstResult-1);
					if (maxResults > 0)
						query.setMaxResults(maxResults);
					List<Client> clients = query.list();
					for (Client client : clients)
					{
						if (client != null)
						{
							((ClientImpl)client).setDocuments(getClientDocs(client));
							((ClientImpl)client).setRealAddress(getClientAddress(client,"GetClientAddress", ADDR_TYPE_FACT));
							((ClientImpl)client).setLegalAddress(getClientAddress(client,"GetClientAddress", ADDR_TYPE_REG ));
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
	    return StringHelper.isEmpty(str) ? "%" : "%"+str.toUpperCase()+"%";
    }

	private List<ClientDocument> getClientDocs(final Client client) throws GateException
	{
		try
		{
			return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<List<ClientDocument>>()
			{
				public List<ClientDocument> run(Session session) throws Exception
				{
					//noinspection unchecked
					Query query = session
							.getNamedQuery("GetClientDocuments")
							.setParameter("clientId", client.getId());
					List<ClientDocument> docs = query.list();
					if(getPensionNumber(client) != null)
						docs.add(getPensionNumber(client));
					return docs;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private ClientDocument getPensionNumber(final Client client) throws GateException
	{
		try
		{
			return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<ClientDocument>()
			{
				public ClientDocument run(Session session) throws Exception
				{
					//noinspection unchecked
					Query query = session
							.getNamedQuery("GetPensionNumber")
							.setParameter("clientId", client.getId());
					ClientDocument doc =  (ClientDocument)query.uniqueResult();
					if(doc.getDocNumber() == null)
						return null;
					return doc;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

	}

	private Address getClientAddress(final Client client, final String queryName, final int addressType) throws GateException
	{
		try
		{
			return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<Address>()
			{
				public Address run(Session session) throws Exception
				{
					//noinspection unchecked
					Query query = session
							.getNamedQuery(queryName)
							.setParameter("personId", client.getId())
							.setParameter("addrType", addressType);
					return (Address)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}

