package com.rssl.phizic.business.addressBook.reports;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.NumericUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 19.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * Сервис записи сущности лога синхронизации адресной книги
 */

public class RequestsCountLogEntityService
{
	private static final String QUERY_PREFIX = RequestsCountLogEntity.class.getName();
	private static final SimpleService service = new SimpleService();

	/**
	 * добавление записи в лог
	 * @param entity сущность
	 * @return сущнось
	 * @throws BusinessException
	 */
	public RequestsCountLogEntity add(RequestsCountLogEntity entity) throws BusinessException
	{
		return service.add(entity);
	}

	/**
	 * Получить количество синхронизированных контактов за период с fromDate для клиента
	 * @param loginId - идентификатор логина клиента
	 * @param fromDate - дата, начиная с которой считаем синхронизации
	 * @return количество синхронизированных контактов
	 * @throws BusinessException
	 */
	public Long getContactCountByPeriodForLogin(final Long loginId, final Calendar fromDate) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Long>()
		    {
		        public Long run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery(QUERY_PREFIX + ".contactCountByPeriodForLogin");
			        query.setParameter("loginId", loginId);
			        query.setParameter("fromDate", fromDate.getTime());
			        return NumericUtil.getEmptyIfNull((Long)query.uniqueResult());
				}
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}

	/**
	 * Получить количество синхронизаций за период с fromDate для клиента
	 * @param loginId - идентификатор логина клиента
	 * @param fromDate - дата, начиная с которой считаем синхронизации
	 * @return количество синхронизированных контактов
	 * @throws BusinessException
	 */
	public Long getSynchronizationCountByPeriodForLogin(final Long loginId, final Calendar fromDate) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Long>()
		    {
		        public Long run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery(QUERY_PREFIX + ".synchronizationCountByPeriodForLogin");
			        query.setParameter("loginId", loginId);
			        query.setParameter("fromDate", fromDate.getTime());
			        return NumericUtil.getEmptyIfNull((Long)query.uniqueResult());
				}
		    });
		}
		catch (Exception e)
		{
		    throw new BusinessException(e);
		}
	}
}
