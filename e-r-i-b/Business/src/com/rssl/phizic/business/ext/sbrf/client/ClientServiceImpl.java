package com.rssl.phizic.business.ext.sbrf.client;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 06.06.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * В этой версии бэк-офис не предоставляет информацию по клиенту.
 * Поэтому берем ее из бизнеса.
 */
public class ClientServiceImpl  extends AbstractService implements ClientService
{
	private static final PersonService personService = new PersonService();

	public ClientServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public Client getClientById(String id) throws GateLogicException, GateException
    {
	    try
	    {
	        ActivePerson person = personService.findByClientId(id);
		    return person.asClient();
	    }
	    catch(BusinessException ex)
	    {
		    throw new GateException(ex);
	    }

    }

	public Client fillFullInfo(Client client) throws GateLogicException, GateException
	{
		if(client == null)
			throw new NullPointerException("Параметр client не может быть null");

	    try
	    {
	        ActivePerson person = personService.findByClientId(client.getId());

		    if(person==null)
		        throw new GateException("Не найден клиентс ID=" + client.getId());

		    return person.asClient();
	    }
	    catch(BusinessException ex)
	    {
		    throw new GateException(ex);
	    }
	}

	public Client getClientByCardNumber(String rbTbBranchId, String cardNumber) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("Операция получения клиента по карте не поддерживается");
	}

   public List<Client> getClientsByTemplate(Client client, Office office,final int firstResult,final int maxResults) throws GateLogicException, GateException
   {
	   try
	   {
		   final String surName = client.getSurName();
		   final String firstName = client.getFirstName();
		   final String patrName = client.getPatrName();

			List<ActivePerson> persons =  HibernateExecutor.getInstance().execute(new HibernateAction<List<ActivePerson>>()
			{
				public List<ActivePerson> run(Session session) throws Exception
				{
					DetachedCriteria criteria = DetachedCriteria.forClass(ActivePerson.class)
								.add(Expression.eq("surName", surName))
								.add(Expression.eq("patrName", patrName))
								.add(Expression.eq("firstName", firstName));

					return criteria.getExecutableCriteria(session).setFirstResult(firstResult).setMaxResults(maxResults).list();
				}
			});

		   List<Client> clients = new ArrayList(persons.size());
		   for (ActivePerson person : persons)
		   {
			   clients.add(person.asClient());
		   }
		   return clients;
		}
		catch(Exception ex)
		{
			throw new GateException(ex);
		}
   }
}
