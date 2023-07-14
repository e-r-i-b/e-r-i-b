package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.common.forms.xslt.FilteredEntityListSource;
import com.rssl.common.forms.FormException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.Type;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author Evgrafov
 * @ created 30.01.2006
 * @ $Author: osminin $
 * @ $Revision: 9117 $
 */
class HqlEntityListSource implements FilteredEntityListSource
{
	/** @noinspection PackageVisibleInnerClass*/
	static class ReturnProperty implements Serializable
	{
		private String name;
		private String property;
		private long   index = -1;

		public long getIndex()
		{
			return index;
		}

		public void setIndex(long index)
		{
			this.index = index;
		}

		public String getName()
		{
			return name == null ? getProperty() : name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getProperty()
		{
			return property;
		}

		public void setProperty(String property)
		{
			this.property = property;
		}
	}

	private ReturnProperty[] returnProperties;
	private ReturnProperty   returnKey;
	private String           query;
	private Map<String,Type>              hibernateTypeMap;

	HqlEntityListSource(String query, ReturnProperty returnKey, ReturnProperty[] returnProperties, Map<String,Type> hibernateTypeMap)
	{
		this.query = query;
		this.returnKey = returnKey;
		this.returnProperties = returnProperties;
		this.hibernateTypeMap = hibernateTypeMap;
	}

	/**
	 * Строит xml представление выполнения запроса.
	 * Если возращаемое значение null, но над ним требуются доп. действия, то возвращется null
	 * Например:
	 * <hql-query>
	 *	<return property="chargeOffAmount.decimal"/>
	 * если chargeOffAmount==null, то в результате будет получен xml
	 * <chargeOffAmount.decimal></chargeOffAmount.decimal>
	 * @param params парамерты запроса
	 * @param selectedKeys идентификаторы выбранных сущностей,будут помеченны атрибутом selected
	 * @return возвращает Source для xml представления результата выполнение запроса
	 * @throws FormException ошибка при выполнении или разборе результатов выполнения запроса.
	 */
	public Source getList(Map<String, Object> params, String[] selectedKeys) throws FormException
	{
		try
		{
			List list = executeHqlQuery(params);

			FilteredEntityListBuilder builder = new FilteredEntityListBuilder();

			builder.openListDataTag();

			addFilterFields(builder, params);

			builder.openEntityListTag();

			for (Object aList : list)
			{
				addEntities(builder, aList, selectedKeys);
			}

			builder.closeEntityListTag();
			builder.closeListDataTag();

			return new StreamSource(new StringReader(builder.toString()));
		}
		catch (BusinessException e)
		{
			throw new FormException(e);
		}
	}

	/**
	 * @param params параметры отбора (фильтра). ключ - имя поля фильтра, значение - значение фильтра
	 * @return XML документ содержащий условия отбора
	 */
	public Source getFilter(Map<String, Object> params)
	{
		FilteredEntityListBuilder builder = new FilteredEntityListBuilder();

		builder.openListDataTag();

		addFilterFields(builder, params);

		builder.closeListDataTag();

		return new StreamSource(new StringReader(builder.toString()));
	}

	private void addFilterFields(FilteredEntityListBuilder builder, Map<String, Object> params)
	{
		builder.openFilterDataTag();

		for (Map.Entry<String, Object> entry : params.entrySet())
		{
			Object value = entry.getValue();
			String valueString = value == null ? "" : value.toString();
			builder.appentField(entry.getKey(), valueString);
		}

		builder.closeFilterDataTag();
	}

	private void addEntities(FilteredEntityListBuilder builder, Object bean, String[] selectedKeys) throws BusinessException
	{
		String keyValue = evaluateProperty(returnKey, bean);
		boolean selected = Arrays.binarySearch(selectedKeys, keyValue) > -1;

		builder.openEntityTag(keyValue, selected);

		for (ReturnProperty returnProperty : returnProperties)
		{
			addEntity(returnProperty, builder, bean);
		}

		builder.closeEntityTag();
	}

	private void addEntity(ReturnProperty returnProperty, FilteredEntityListBuilder builder, Object bean) throws BusinessException
	{
		String value = evaluateProperty(returnProperty, bean);
		builder.appentField(returnProperty.getName(), value);
	}

	private String evaluateProperty(ReturnProperty returnProperty, Object bean)
			throws BusinessException
	{
		Object value;
		String stringValue;
		try
		{
			value = PropertyUtils.getNestedProperty(bean, returnProperty.getProperty());
			stringValue = formatValue(value);
		}
		catch(NestedNullException e)
		{
			stringValue = "";
		}
		catch (IllegalAccessException e)
		{
			throw new BusinessException(e);
		}
		catch (InvocationTargetException e)
		{
			throw new BusinessException(e);
		}
		catch (NoSuchMethodException e)
		{
			throw new BusinessException(e);
		}
		return stringValue;
	}

	private String formatValue(Object value)
	{
		String stringValue;

		if(value == null)
			stringValue = "";
		else if(value instanceof Calendar)
		    stringValue = DateHelper.toISO8601DateFormat((Calendar)value);
		else if (value instanceof Date)
			stringValue = DateHelper.toISO8601DateFormat((Date) value);
		else
			stringValue = value.toString();

		return stringValue;
	}

	private List executeHqlQuery(final Map params) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<List>()
		    {
		        public List run(Session session) throws Exception
		        {
			        session.enableFilter("payments");
			        Query query = session.createQuery(HqlEntityListSource.this.query);


			        String[] namedParameters = query.getNamedParameters();

			        for (String name : namedParameters)
			        {
				        if (!params.containsKey(name))
					        throw new BusinessException("Parameter " + name + " not specified");

				        Object value = params.get(name);
				        if (value == null)
				        {
					        query.setParameter(name, value, hibernateTypeMap.get(name));
				        }
				        else
				        {
					        if(value instanceof String[])
					        {
						        String[] valueArray = (String[]) value;
						        if (valueArray.length!=0)
						        {
							        String sum = "";
									for (String str : valueArray)
									{
										sum += str;
									}
							        if (params.containsKey(name + "_list"))
							        {
										query.setParameter(name + "_list", sum);
										params.put(name + "_list", sum);
							        }
							        query.setParameterList(name, valueArray);
						        }
						        else
						        {
							        query.setParameter(name, value);
						        }
					        }
					        else
					        {
						        query.setParameter(name, value);
					        }
				        }
			        }

			        return query.list();
		        }
		    });
		}
		catch(BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}
}