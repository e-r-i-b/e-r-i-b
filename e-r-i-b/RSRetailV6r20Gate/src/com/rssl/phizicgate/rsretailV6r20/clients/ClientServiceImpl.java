package com.rssl.phizicgate.rsretailV6r20.clients;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.Address;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.NullParameterType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.rsretailV6r20.data.RSRetailV6r20Executor;
import org.hibernate.Session;
import org.hibernate.Query;

import java.util.List;

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
			// передано числовое id для коотрого нет клиента в БД
			return null;
		}
		try
		{
		    return RSRetailV6r20Executor.getInstance().execute(new HibernateAction<Client>()
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

    public Client fillFullInfo(Client client) throws GateException
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

    public List<Client> getClientsByTemplate(final Client client, Office office, final int firstResult, final int maxResults) throws GateException
    {
	    final String surNameLikePattern = prepareLikeParam(client.getSurName());
	    final String firstNameLikePattern = prepareLikeParam(client.getFirstName());
	    final String patrNameLikePattern = prepareLikeParam(client.getPatrName());

	    final String branch = office.getCode().getId();

	    try
        {
            return RSRetailV6r20Executor.getInstance().execute(new HibernateAction<List<Client>>()
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
	                /*if (false)
	                {
	                EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
	                employeeData.getEmployee().getDepartmentId();
	                
		                Department department = getCurrentDepartment();
			            if (!department.getMain())

			            query.setParameter("branch", branch);
			        }*/
		            query.setParameter("branch", NullParameterType.VALUE, NullParameterType.INSTANCE);
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
			return RSRetailV6r20Executor.getInstance().execute(new HibernateAction<List<ClientDocument>>()
			{
				public List<ClientDocument> run(Session session) throws Exception
				{
					//noinspection unchecked
					Query query = session
							.getNamedQuery("GetClientDocuments")
							.setParameter("clientId", client.getId());
					return query.list();
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
			return RSRetailV6r20Executor.getInstance().execute(new HibernateAction<Address>()
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

