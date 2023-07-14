package com.rssl.phizic.dataaccess.query;

import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.utils.ListTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Set;

/**
 * @author mihaylov
 * @ created 01.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Кверя для работы с многоязычными сущностями, учитывающая текущую локаль
 */
public class MultiLocaleBeanQuery extends BeanQuery
{
	private static final String MULTI_LOCALE_QUERY_SUFFIX = ".multilocale";
	private static final String LOCALE_FILTER_NAME = "localeIdFilter";
	private static final String LOCALE_ID_PARAMETER = "localeId";
	private static final ListTransformer listTransformer = new MultiLocaleResultTransformer();

	private String localeId;

	/**
	 * Конструктор
	 * @param bean - бин, в котором могут содержаться параметры квери
	 * @param queryName - название квери
	 * @param instanceName - инстанс БД
	 * @param localeId - идентификатор локали
	 */
	public MultiLocaleBeanQuery(Object bean, String queryName, String instanceName, String localeId)
	{

		super(bean, queryName + MULTI_LOCALE_QUERY_SUFFIX, instanceName);
		this.localeId = localeId;
	}

	@Override
	protected Query createQuery(Session session) throws DataAccessException
	{
		Set filterDefinitions = session.getSessionFactory().getDefinedFilterNames();
		if(CollectionUtils.isNotEmpty(filterDefinitions) && filterDefinitions.contains(LOCALE_FILTER_NAME))
			session.enableFilter(LOCALE_FILTER_NAME).setParameter(LOCALE_ID_PARAMETER, localeId);
		setParameter(LOCALE_ID_PARAMETER,localeId);
		setListTransformer(listTransformer);
		return super.createQuery(session);
	}

}
