package com.rssl.phizic.operations.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;
import java.util.Map;

/**
 * @author koptyaev
 * @ created 12.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class ListLocaleOperation extends OperationBase implements ListEntitiesOperation
{
	private Map<String, Object> filterParams;

	/**
	 * Усановить параметры фильтрации
	 * @param filterParams  параметры фильтрации
	 */
	@SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
	public void setFilterParams(Map<String, Object> filterParams)
	{
		this.filterParams = filterParams;
	}

	/**
	 * Получить список локалей, соответствующих фильтру
	 * @return список локалей
	 * @throws BusinessException
	 */
	public List<ERIBLocale> getByFilter() throws BusinessException
	{
		Query query =  createQuery("getLocaleByName");
		for(String key:filterParams.keySet())
		{
			query.setParameter(key, filterParams.get(key));
		}
		try
		{
			return query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}
}
