package com.rssl.phizic.business.persons.clients;

import com.rssl.phizic.business.persons.MultiInstancePersonService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Query;

/**
 * @author: Pakhomova
 * @created: 06.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class AdditionalClientsService extends MultiInstancePersonService
{


	/**
	 * Создаем связь текущего клиента и дополнительный id клиентов.
	 * Для "кривых баз", где одному реальному клиенту соответсвует несколько клиентов в ритейле.
	 *
	 * @param client - текущий клиент
	 * @param addClientIds - дополнительный id, чьи ресурсы будут привязаны к текущему клиенту
	 */

	public void createClientIdsConnection(final Client client, final Set<String> addClientIds) throws GateException
	{
		if(client == null)
            throw new NullPointerException("Параметр client не может быть null");

		if (addClientIds==null || addClientIds.isEmpty())
			return;

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					AdditionalClientIds additionalClientId = findAdditionalsByClientId(client.getId());
					if (additionalClientId==null) additionalClientId = new AdditionalClientIds();

					additionalClientId.setClientId(client.getId());
					additionalClientId.clearOldAddClientIds();

					for (String addClientId : addClientIds)
					{
						if( !client.getId().equals(addClientId) )
						{
							AdditionalClientIds childAdditionalClientId = new AdditionalClientIds();
							childAdditionalClientId.setClientId(addClientId);
							additionalClientId.addAddClientIds(childAdditionalClientId);
						}
					}

					session.saveOrUpdate(additionalClientId);
					return null;
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
	/**
	 * Удаляем все дополнительные id'ки для клиента, при удалении клиента (даже при отправке его в архив)
	 * @param clientId - id "главного" клиента, удаляемого клиента
	 * @throws BusinessException
	 */
	public void cleanClientIdsConnection(final String clientId)  throws BusinessException
	{
		if(clientId == null)
           throw new NullPointerException("Параметр clientId не может быть null");
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					AdditionalClientIds additionalClientId = findAdditionalsByClientId(clientId);
					if (additionalClientId!=null)
						session.delete(additionalClientId);
					return null;
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получаем id основного клиента по дополнительному id
	 * @param clientId - дополнительный id клиента
	 * @return основного клиента, если нет null
	 * @throws BusinessException, BusinessLogicException
	 */
	public String getClientIdByAdditionalId(final String clientId) throws BusinessException, BusinessLogicException
	{
		AdditionalClientIds mainClientId = findMainIdByClientId(clientId);
		return  (mainClientId != null ? mainClientId.getClientId() : null);
	}

	/**
	 * Найти основого клиента (подключенного к ИКФЛ) по дополнительному id
	 * @param clientId дополнительный
	 * @return id к которому привязан clientId, если нет null
	 * @throws BusinessException, BusinessLogicException
	 */
	public AdditionalClientIds findMainIdByClientId(final String clientId) throws BusinessException
	{
		try
        {
            return HibernateExecutor.getInstance().execute(new HibernateAction<AdditionalClientIds>()
            {
                public AdditionalClientIds run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.business.persons.clients.findMainIdByClientId");
	                query.setParameter("clientId", clientId);
	                AdditionalClientIds additionalClientIds = (AdditionalClientIds)query.uniqueResult();
                    return additionalClientIds;
                }
            });
        }
        catch (Exception e)
        {
           throw new BusinessException(e);
        }
	}

	/**
	 * Найти все id, привязанные к clientId
	 * @param clientId подключенного клиента
	 * @return Все id, связанные с clientId, если нет null
	 * @throws BusinessException
	 */
	public AdditionalClientIds findAdditionalsByClientId(final String clientId) throws BusinessException
	{
		try
        {
            return HibernateExecutor.getInstance().execute(new HibernateAction<AdditionalClientIds>()
            {
                public AdditionalClientIds run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.business.persons.clients.findAdditionalsByClientId");
	                query.setParameter("clientId", clientId);
	                AdditionalClientIds additionalClientIds = (AdditionalClientIds)query.uniqueResult();
	                if (additionalClientIds!=null && !additionalClientIds.isMain())
	                    throw new GateException("Пользователь с id="+clientId+" привязан к другому пользователю.");
                    return additionalClientIds;
                }
            });
        }
        catch (Exception e)
        {
           throw new BusinessException(e);
        }
	}

}
