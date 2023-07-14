package com.rssl.phizgate.common.payments.offline;

import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * Сервис управления маршрутизацией исходящих запросов
 * @author Puzikov
 * @ created 10.10.14
 * @ $Author$
 * @ $Revision$
 */

public class OutgoingRequestService
{
	private static final String OFFLINE_DOC_DB_INSTANCE = "OfflineDoc";

	private final DatabaseServiceBase databaseService = new DatabaseServiceBase();

	/**
	 * Добавить информацию об исходящем запросе
	 * @param requestEntry - запись с данными исходящего запроса
	 */
	public void addRequestEntry(OutgoingRequestEntry requestEntry) throws Exception
	{
		databaseService.add(requestEntry, OFFLINE_DOC_DB_INSTANCE);
	}

	/**
	 * Найти по id
	 * @param rqUID id запроса (never null)
	 * @return исходящий запрос
	 */
	public OutgoingRequestEntry getById(String rqUID) throws Exception
	{
//		Опорный объект: PK_RQ_UID
//		Предикаты доступа: "RQ_UID"=:RQUID
//		Кардинальность: 1
		DetachedCriteria criteria = DetachedCriteria.forClass(OutgoingRequestEntry.class);
		criteria.add(Expression.eq("rqUID", rqUID));
		return databaseService.findSingle(criteria, null, OFFLINE_DOC_DB_INSTANCE);
	}

	/**
	 * Удалить по id
	 * @param rqUID id запроса
	 */
	public void deleteByRqId(final String rqUID) throws Exception
	{
		HibernateExecutor.getInstance(OFFLINE_DOC_DB_INSTANCE).execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				session.getNamedQuery("com.rssl.phizgate.common.payments.offline.OutgoingRequestEntry.delete")
						.setParameter("rqUID", rqUID)
						.executeUpdate();
				return null;
			}
		});
	}
}
