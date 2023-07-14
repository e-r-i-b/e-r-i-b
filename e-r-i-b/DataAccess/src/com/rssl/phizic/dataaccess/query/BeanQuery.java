package com.rssl.phizic.dataaccess.query;

import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.NullParameterType;
import org.hibernate.EntityMode;
import org.hibernate.Query;
import org.hibernate.engine.TypedValue;
import org.hibernate.property.Getter;
import org.hibernate.type.TypeFactory;
import org.hibernate.util.ReflectHelper;

/**
 * @author Evgrafov
 * @ created 21.06.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1493 $
 */
//TODO по идее надо вседа явно указывать Executor 
public class BeanQuery extends ExecutorQuery
{

	public BeanQuery(String queryName)
	{
		this(new Object(), queryName);
	}

	public BeanQuery(Object bean, String queryName)
	{
		super(HibernateExecutor.getInstance(), queryName);
		setBean(bean);
	}

	/**
	 *  Конструктор для запроса
	 * @param bean бин с данными
	 * @param queryName имя запроса
	 * @param instanceName имя экземпляра бд
	 */
	public BeanQuery(Object bean, String queryName, String instanceName)
	{
		super(HibernateExecutor.getInstance(instanceName), queryName);
		setBean(bean);
	}

	protected void fillParameters(Query query) throws DataAccessException
	{
		query.setProperties(getBean());
		super.fillParameters(query);
	}

	protected TypedValue getTypedValueFromBean(String name) throws DataAccessException
	{
		Getter getter = ReflectHelper.getGetter(getBean().getClass(), name);
		if(getter == null)
			throw new DataAccessException("Не найден getter для " + name);
		Object value = getter.get(getBean());
		if (value == null)
			return new TypedValue(NullParameterType.INSTANCE, NullParameterType.VALUE, EntityMode.POJO);//def
		return new TypedValue(
				TypeFactory.heuristicType(value.getClass().getName()),
				value,
				EntityMode.POJO
		);//POJO - default
	}
}