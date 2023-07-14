package com.rssl.phizic.csaadmin.business.session;

import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.SimpleService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author akrenev
 * @ created 30.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Сервис работы с сессиями
 */

public class SessionService extends SimpleService<Session>
{
	@Override
	protected Class<Session> getEntityClass()
	{
		return Session.class;
	}

	@Override
	protected String getIdFieldName()
	{
		return "sid";
	}

	/**
	 * Поиск активной сессии по идентификатору
	 * @param sessionId - идентификатор сессии
	 * @return сессия
	 * @throws AdminException
	 */
	public Session findActiveById(String sessionId) throws AdminException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Session.class);
		criteria.add(Expression.eq("state", SessionState.ACTIVE));
		criteria.add(Expression.eq("sid", sessionId));
		return findSingle(criteria);
	}
}
