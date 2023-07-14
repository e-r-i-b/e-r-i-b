package com.rssl.auth.csa.front.business.promo;

import com.rssl.phizic.business.promoters.PromoChannel;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.jdbc.JDBCAction;
import com.rssl.phizic.logging.source.JDBCActionExecutor;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import java.util.Calendar;
import java.util.Map;

/**
 * Сервис для работы со сменами промоутера
 * @ author: Gololobov
 * @ created: 21.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class PromoterAndPromoClientSessionService extends SimpleService
{
	private static final String CSA_INSTANCE_NAME = "CSA2";
	private volatile JDBCActionExecutor executor = null;
	private final Object lock = new Object();

	/**
	 * Получение открытой смены промоутера. Будет одна запись, т.к. каждый следующий вход промоутера закрывает
	 * предыдущую незавершенную смену.
	 * @param promoterData - данные смены промоутера
	 * @return PromoterSession - незавершенная смена промоутера
	 * @throws Exception
	 */
	public PromoterSession getPromoterOpenSessions(final Map<String, String> promoterData) throws Exception
	{
		return HibernateExecutor.getInstance(CSA_INSTANCE_NAME).execute(new HibernateAction<PromoterSession>()
		{
			public PromoterSession run(Session session)
			{
				Criteria criteria = session.createCriteria(PromoterSession.class);
				criteria.add(Expression.isNull("closeDate"));
				criteria.add(Expression.eq("channel", PromoChannel.valueOf(promoterData.get("channelId"))));
				criteria.add(Expression.eq("tb",promoterData.get("tb")));
				criteria.add(Expression.eq("osb",promoterData.get("osb")));
				String vsp = promoterData.get("vsp");
				if (StringHelper.isNotEmpty(vsp))
					criteria.add(Expression.eq("office",promoterData.get("vsp")));
				criteria.add(Expression.eq("promoter",promoterData.get("promoId")));
				criteria.setMaxResults(1);
				return (PromoterSession) criteria.uniqueResult();
			}
		});
	}

	/**
	 * Смена промоутера по ИД
	 * @param sessionId - ИД смены
	 * @return PromoterSession - смена
	 * @throws Exception
	 */
	public PromoterSession getPromoterOpenSessionsById(final Long sessionId) throws Exception
	{
		return HibernateExecutor.getInstance(CSA_INSTANCE_NAME).execute(new HibernateAction<PromoterSession>()
		{
			public PromoterSession run(Session session)
			{
				Criteria criteria = session.createCriteria(PromoterSession.class);
				criteria.add(Expression.isNull("closeDate"));
				criteria.add(Expression.eq("sessionId", sessionId));
				criteria.setMaxResults(1);
				return (PromoterSession) criteria.uniqueResult();
			}
		});
	}

	/**
	 * Закрытие всех открытых смен промоутера.
	 * @param promoterOpenedSession
	 */
	public void closePromoterOpenedSession(PromoterSession promoterOpenedSession) throws BusinessException
	{
		if (promoterOpenedSession != null)
		{
			if (promoterOpenedSession.getCloseDate() == null)
				promoterOpenedSession.setCloseDate(Calendar.getInstance());
			update(promoterOpenedSession, CSA_INSTANCE_NAME);
		}
	}

	/**
	 * Открытие новой смены промоутера
	 * @param newPromoterSession - новая смена
	 * @return PromoterSession
	 * @throws BusinessException
	 */
	public PromoterSession addPromoterSession(PromoterSession newPromoterSession) throws BusinessException
	{
		return add(newPromoterSession, CSA_INSTANCE_NAME);
	}

	protected JDBCActionExecutor getJDBCActionExecutor()
	{
		if (executor != null)
		{
			return executor;
		}
		synchronized (lock)
		{
			if (executor != null)
			{
				return executor;
			}
			executor = createExecutor();
		}
		return executor;
	}

	protected <T> T executeJDBCAction(JDBCAction<T> action) throws SystemException
	{
		return getJDBCActionExecutor().execute(action);
	}

	protected String getDataSourceName()
	{
		PropertyReader reader = ConfigFactory.getReaderByFileName("csa.properties");
		return reader.getProperty("datasource.name");
	}

	private JDBCActionExecutor createExecutor()
	{
		return new JDBCActionExecutor(getDataSourceName(), System.jdbc);
	}

	/**
	 * Логирование входа клиента промоутера
	 * @param connectorGuid - GUID коннектора клиента
	 * @param promoterSessionId - ID активной сессии промооутера
	 * @param operationOuid - OUID операции клиента 
	 * @return String - ID записи в логе
	 * @throws SystemException
	 */

	public String addPromoClientLog(String connectorGuid, String promoterSessionId, String operationOuid) throws SystemException
	{
		return executeJDBCAction(new PromoClientSessionLogAction(connectorGuid, promoterSessionId, operationOuid));
	}
}
