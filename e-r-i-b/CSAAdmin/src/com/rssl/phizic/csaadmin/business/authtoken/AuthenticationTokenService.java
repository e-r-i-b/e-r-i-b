package com.rssl.phizic.csaadmin.business.authtoken;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.SimpleService;
import com.rssl.phizic.csaadmin.config.CSAAdminConfig;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 27.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы с токеном аутентификации
 */
public class AuthenticationTokenService extends SimpleService<AuthenticationToken>
{
	@Override
	protected Class<AuthenticationToken> getEntityClass()
	{
		return AuthenticationToken.class;
	}

	/**
	 * Поиск активного токена аутентификации по переданному идентификатору
	 * @param id - идентификатор токена
	 * @return активный токен аутентификации или null, если токен закрыт или просрочен
	 * @throws AdminException
	 */
	public AuthenticationToken findActiveById(String id) throws AdminException
	{
		CSAAdminConfig config = ConfigFactory.getConfig(CSAAdminConfig.class);
		Calendar tokenDate = Calendar.getInstance();
		tokenDate.add(Calendar.SECOND,-config.getAuthTokenLifeTime());

		DetachedCriteria criteria = DetachedCriteria.forClass(AuthenticationToken.class);
		criteria.add(Expression.eq("id", id));
		criteria.add(Expression.eq("state", AuthenticationTokenState.ACTIVE));
		criteria.add(Expression.ge("creationDate",tokenDate));
		return findSingle(criteria);
	}

	/**
	 * Закрыть переданный токен
	 * @param authToken - токен
	 * @throws AdminException
	 */
	public void closeToken(AuthenticationToken authToken) throws AdminException
	{
		authToken.setState(AuthenticationTokenState.CLOSED);
		super.save(authToken);
	}
}
