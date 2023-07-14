package com.rssl.phizic.business.ermb.registration;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.ermb.ErmbClientPhone;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.PhoneNumber;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Gulov
 * @ created 09.08.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сервис для редактирования новых регистраций клиентов в таблицу историй подключений.
 */
public class ErmbRegistrationEventService
{
	private static final SimpleService service = new SimpleService();

	/**
	 * Добавить список регистраций клиентов
	 * @param clientPhones список телефонов клиентов
	 * @throws BusinessException
	 */
	public void add(Set<ErmbClientPhone> clientPhones) throws BusinessException
	{
		if (CollectionUtils.isEmpty(clientPhones))
			return;
		List<ErmbRegistrationEvent> registrations = buildErmbRegistrations(clientPhones);
		service.addList(registrations);
	}

	private List<ErmbRegistrationEvent> buildErmbRegistrations(Set<ErmbClientPhone> clientPhones)
	{
		List<ErmbRegistrationEvent> registrations = new ArrayList<ErmbRegistrationEvent>();
		for (ErmbClientPhone phone : clientPhones)
			registrations.add(new ErmbRegistrationEvent(phone.getProfile().getId(), phone.getNumber(), phone.getProfile().getConnectionDate()));
		return registrations;
	}

	/**
	 * Найти все регистрации клиентов
	 * @param maxResults максимальное количество записей
	 * @return список регистраций
	 * @throws BusinessException
	 */
	public List<ErmbRegistrationEvent> find(final int maxResults) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ErmbRegistrationEvent>>()
			{
				public List<ErmbRegistrationEvent> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.registration.findAll");
					query.setMaxResults(maxResults);
					//noinspection unchecked
					return (List<ErmbRegistrationEvent>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка поиска списка новых клиентов ЕРМБ", e);
		}
	}

	/**
	 * Удалить записи по номерам телефонов
	 * @param phones список телефонов
	 * @throws BusinessException
	 */
	public void remove(final Set<PhoneNumber> phones) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.registration.delete");
					query.setParameterList("phones", phones);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
