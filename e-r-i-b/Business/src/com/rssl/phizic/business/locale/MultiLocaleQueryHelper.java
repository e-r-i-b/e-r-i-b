package com.rssl.phizic.business.locale;

import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.dataaccess.query.MultiLocaleBeanQuery;
import com.rssl.phizic.utils.ClassHelper;

/**
 * @author mihaylov
 * @ created 07.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Хелпер для работы с запросами многоязычных сущностей.
 */
public class MultiLocaleQueryHelper
{

	/**
	 * Получить кверю для операции с учетом текущего языка
	 * @param operation - бин операции
	 * @param queryName - имя квери в операции
	 * @param instanceName - инстанс БД
	 * @return кверя
	 */
	public static BeanQuery getOperationQuery(Operation operation, String queryName, String instanceName)
	{
		return getQuery(operation,ClassHelper.getActualClass(operation).getName() + "." + queryName,instanceName);
	}

	/**
	 * Получить кверю с учетом текущего языка
	 * @param queryName - имя квери
	 * @return кверя
	 */
	public static BeanQuery getQuery(String queryName)
	{
		return getQuery(new Object(),queryName,null);
	}

	/**
	 * Получить BeanQuery с учетом текущего языка
	 * @param bean - бин
	 * @param queryName - имя квери
	 * @param instanceName инстанс БД
	 * @return кверя
	 */
	public static BeanQuery getQuery(Object bean, String queryName, String instanceName)
	{
		if(MultiLocaleContext.isDefaultLocale())
			return new BeanQuery(bean, queryName, instanceName);
		return new MultiLocaleBeanQuery(bean, queryName, instanceName, MultiLocaleContext.getLocaleId());
	}



}
