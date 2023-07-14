package com.rssl.phizic.business.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import org.hibernate.criterion.Expression;

/**
 * @author mihaylov
 * @ created 13.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Базовый сервис для работы с многоязычными сущностями
 */
public class MultiLocaleSimpleService extends MultiInstanceSimpleService
{

	@Override
	public <T> T findById(Class<T> clazz, Long id, String instanceName) throws BusinessException
	{
		try
		{
			return this.<T>findSingle(MultiLocaleDetachedCriteria.forClassInLocale(clazz,MultiLocaleContext.getLocaleId()).add(Expression.eq("id", id)), null, instanceName);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

}
