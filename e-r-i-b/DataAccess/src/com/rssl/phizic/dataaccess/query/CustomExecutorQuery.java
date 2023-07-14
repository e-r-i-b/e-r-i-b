package com.rssl.phizic.dataaccess.query;

import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.utils.ListTransformer;
import org.hibernate.NonUniqueResultException;

import java.util.*;

/**
 * @author mihaylov
 * @ created 19.05.14
 * @ $Author$
 * @ $Revision$
 *
 *  Запрос на получение данных для построения списка. Источником данных является CustomListExecutor executor.
 */
public class CustomExecutorQuery extends QueryBase
{
	private Map<String,Object> extraParameters = new HashMap<String, Object>();
	private List<OrderParameter> orderParameters = new ArrayList<OrderParameter>();
	private CustomListExecutor executor;
	private int size;
	private int offset;

	/**
	 * @param bean - бин, в котором могут содержаться дополнительные параметры для получения списка
	 * @param executor - класс выполняющий запрос на получение данных
	 */
	public CustomExecutorQuery(Object bean, CustomListExecutor executor)
	{
		setBean(bean);
		this.executor = executor;
	}

	public Query setParameter(String name, Object value)
	{
		extraParameters.put(name, value);
	    return this;
	}

	public Query setParameters(Map<String,Object> parameters)
	{
		if( parameters != null && parameters.size() > 0 )
	    {
		    for (Map.Entry<String,Object> entry : parameters.entrySet())
		    {
			    String name = entry.getKey();
			    Object value = entry.getValue();

			    setParameter(name, value);
		    }
	    }
	    return this;
	}

	public Object getParameter(String name)
	{
		return extraParameters.get(name);
	}

	public List<String> getNamedParameters() throws DataAccessException
	{
		throw new UnsupportedOperationException("Данный метод не реализован.");
	}

	public Query setParameterList(String name, Collection values)
	{
		extraParameters.put(name,values);
		return this;
	}

	public Query setParameterList(String name, Object[] values)
	{
		return setParameterList(name,Arrays.asList(values));
	}

	public Query setListParameters(String prefix, Object[] values, int count)
	{
		throw new UnsupportedOperationException("Данный метод не реализован.");
	}

	public Query setListParameters(String prefix, List values, int count)
	{
		throw new UnsupportedOperationException("Данный метод не реализован.");
	}

	public Query setMaxResults(int val)
	{
		this.size = val;
		return this;
	}

	public Query setFirstResult(int val)
	{
		this.offset = val;
		return this;
	}

	public <T> List<T> executeList() throws DataAccessException
	{
		return new QueryPaginalDataSource<T>(this);
	}

	public Query setOrderParameterList(List<OrderParameter> parameters)
	{
		this.orderParameters = new ArrayList<OrderParameter>(parameters);
		return this;
	}

	public <T> List<T> executeListInternal() throws DataAccessException
	{
		fillBeanParameters();
	    return executor.getList(extraParameters,size,offset,orderParameters);
	}

	public <T> Iterator<T> executeIterator() throws DataAccessException
	{
		List<T> list = executeListInternal();
		return list.iterator();
	}

	public <T> T executeUnique() throws DataAccessException
	{
		List<T> list = executeListInternal();
		int listSize = list.size();
		if (listSize == 0)
			return null;
		if(listSize == 1)
			return list.get(0);
		throw new NonUniqueResultException(listSize);
	}

	public int executeUpdate() throws DataAccessException
	{
		throw new UnsupportedOperationException("Данный метод не реализован.");
	}

	public Filter enableFilter(String filterName)
	{
		throw new UnsupportedOperationException("Данный метод не реализован.");
	}

	public void setFilterRestriction(FilterRestriction restriction)
	{
		throw new UnsupportedOperationException("Данный метод не реализован.");
	}

	public <Output, Input> void setListTransformer(ListTransformer<Output, Input> transformer)
	{
		throw new UnsupportedOperationException("Данный метод не реализован.");
	}
}
