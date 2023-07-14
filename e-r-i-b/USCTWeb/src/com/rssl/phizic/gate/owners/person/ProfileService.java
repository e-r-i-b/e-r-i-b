package com.rssl.phizic.gate.owners.person;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.owners.client.ClientHelper;
import com.rssl.phizic.gate.owners.client.ExtendedClient;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Query;
import org.hibernate.Session;

import static com.rssl.phizic.gate.hibernate.Constants.DB_INSTANCE_NAME;
import static com.rssl.phizic.logging.Constants.LOG_MODULE_CORE;


/**
 * Сервис для работы с профилями клиентов
 *
 * @author khudyakov
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 */
public class ProfileService
{
	private static final String QUERY_PREFIX_NAME = ProfileService.class.getName() + ".";

	private static final Log log = PhizICLogFactory.getLog(LOG_MODULE_CORE);
	private static final DatabaseServiceBase databaseServiceBase = new DatabaseServiceBase();
	private static final ProfileService INSTANCE = new ProfileService();


	public static ProfileService getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Найти персону клиента по установочным данным клиента
	 * @param client установочные данные клиента
	 * @return персона
	 */
	public Profile findByIdentificationData(final Client client) throws Exception
	{
		return HibernateExecutor.getInstance(DB_INSTANCE_NAME).execute(new HibernateAction<Profile>()
		{
			public Profile run(Session session) throws Exception
			{
				Query criteria = session.getNamedQuery(QUERY_PREFIX_NAME + "findByIdentificationData")
						.setParameter("surName",   client.getSurName())
						.setParameter("firstName", client.getFirstName())
						.setParameter("patrName",  client.getPatrName())
						.setParameter("birthDay",  client.getBirthDay())
						.setParameter("passport",  ClientHelper.getClientWayDocument(client).getDocSeries())
						.setParameter("tb",        StringHelper.addLeadingZeros(new ExtendedCodeGateImpl(client.getOffice().getCode()).getRegion(), 2));

				//noinspection unchecked
				return (Profile) criteria.uniqueResult();
			}}
		);
	}

	/**
	 * Вернуть текущий активный профиль клиента
	 * @param client клиент
	 * @return профиль
	 * @throws Exception
	 */
	public Profile getOriginalPerson(ExtendedClient client) throws Exception
	{
		try
		{
			return client.asCurrentPerson();
		}
		catch (ProfileNotFoundException e)
		{
			log.error("Профиль клиента не найден в БД, добавляем.", e);
			return databaseServiceBase.addOrUpdate(client.asAbstractPerson(), DB_INSTANCE_NAME);
		}
	}
}
