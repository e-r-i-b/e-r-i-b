package com.rssl.phizic.dataaccess.query;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.Executor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.LikeStringType;
import com.rssl.phizic.dataaccess.hibernate.NullParameterType;
import com.rssl.phizic.dataaccess.query.template.SQLQueryTemplateSource;
import com.rssl.phizic.utils.ListTransformer;
import freemarker.template.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.engine.*;
import org.hibernate.exception.JDBCExceptionHelper;
import org.hibernate.exception.SQLExceptionConverter;
import org.hibernate.loader.custom.SQLQueryReturn;
import org.hibernate.loader.custom.SQLQueryScalarReturn;
import org.hibernate.type.Type;
import org.hibernate.type.TypeFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Krenev
 * @ created 10.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class ExecutorQuery extends QueryBase
{
	private static final Pattern BIND_PATTERN = Pattern.compile("(?<=:)[\\w\\d]+");
	private static final int    UNDEFINED               = -1;
	private static final String EXTRA_PARAMETERS_PREFIX = "extra_";
	private static final String LIKE_PARAMETERS_PREFIX = "like_";
	private Executor executor;
	private String             queryName;
	private Map<String,Object> extraParameters = new HashMap<String, Object>();
	private int                maxResults      = UNDEFINED;
	private int                firstResult     = UNDEFINED;
	private List<Filter>       filters         = new ArrayList<Filter>();
	private FilterRestriction  restriction     = NullFilterRestriction.INSTANCE;

	private List<OrderParameter> orderParameters = new ArrayList<OrderParameter>();

	private static final String RESULTSET = ".resultset";

	private ListTransformer listTransformer = null;

	public ExecutorQuery(Executor executor, String queryName)
	{
		this.executor = executor;
		this.setQueryName(queryName);
	}

	 /**
	 * Проверяет, есть ли запрос с таким названием
	 * @return true - такое название есть, false -  нет
	 */
	public boolean check() throws DataAccessException
	{
		try
	    {
	        return executor.execute(new HibernateAction<Boolean>()
	        {
	            public Boolean run(Session session) throws Exception
	            {
		            Template queryTemplate = loadTemplate(getQueryName());
		            if(queryTemplate != null)
			            return true;
	                org.hibernate.Query query = session.getNamedQuery(getQueryName());
		            return true;
	            }
	        });
	    }
		catch(Exception e)
		{
			return false;
		}
	}
	
	public Query setParameter(String name, Object value)
	{
	    extraParameters.put(name, value);
	    return this;
	}

	public Object getParameter(String name)
	{
		return extraParameters.get(name);
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

	public Query setParameterList(String name, Collection vals){
		if (vals != null && !vals.isEmpty())
		{
			extraParameters.put(name,vals);
		}
		return this;
	}

	public Query setOrderParameter(OrderParameter parameter)
	{
		orderParameters.add(parameter);
		return this;
	}

	public Query setOrderParameterList(List<OrderParameter> parameters)
	{
		orderParameters.addAll(parameters);
		return this;
	}

	public Query setParameterList(String name, Object[] vals){
		return setParameterList( name, Arrays.asList(vals) );
	}

	public Query setListParameters(String prefix, Object[] values, int count)
	{
		return setListParameters(prefix, (values == null) ? null : Arrays.asList(values), count);
	}

	public Query setListParameters(String prefix, List values, int count)
	{
		setParameter("empty_" + prefix, CollectionUtils.isEmpty(values));
		if (values == null)
		{
			values = Collections.EMPTY_LIST;
		}
		int valuesSize = values.size();
		for (int i = 0; i < count; i++)
		{
			setParameter(prefix + (i + 1), (i < valuesSize) ? values.get(i) : null);
		}
		return this;
	}

	public <Output, Input> void setListTransformer(ListTransformer<Output, Input> listTransformer)
	{
		this.listTransformer = listTransformer;
	}

	public Query setMaxResults(int val)
	{
	    maxResults = val;
	    return this;
	}

	private int getMaxResults()
	{
		return maxResults;
	}

	public Query setFirstResult(int val)
	{
	    firstResult = val;
	    return this;
	}

	private int getFirstResult()
	{
		return firstResult;
	}

	public void setFilterRestriction(FilterRestriction restriction)
	{
		this.restriction = restriction;
	}

	public <T> List<T> executeList() throws DataAccessException
	{
		return new QueryPaginalDataSource<T>(this);
	}

	public <T> List<T> executeListInternal() throws DataAccessException
	{
	    try
	    {
	        List list = executor.execute(new HibernateAction<List>()
	        {
	            public List run(Session session) throws Exception
	            {
	                enableFilters(session);
	                org.hibernate.Query query = createQuery(session);
	                setRestrictions(query);
	                return query.list();
	            }
	        });
		    return transformList(list);
	    }
	    catch (DataAccessException e)
	    {
	        throw e;
	    }
	    catch (Exception e)
	    {
	       throw new DataAccessException(e);
	    }
	}

	public int executeUpdate() throws DataAccessException
	{
		try
		{
			return executor.execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					if(!(session instanceof SessionImplementor))
							throw new DataAccessException("Неизвестная реализация интерфейса Session");
					SessionImplementor sessionImplementor = (SessionImplementor)session;
					//создаем стейтмент и заполняем его параметрами
					PreparedStatement preparedStatement = createPreparedStatement(
							session.connection(),
							getSqlText(sessionImplementor),
							sessionImplementor
					);
					//выполняем
					try
					{
						return preparedStatement.executeUpdate();
					}
					catch( SQLException e )
					{
						SQLExceptionConverter converter = sessionImplementor.getFactory().getSQLExceptionConverter();
						throw JDBCExceptionHelper.convert(converter, e, e.getMessage());
					}
					finally
					{
						preparedStatement.close();
					}
				}
			});
		}
		catch (DataAccessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new DataAccessException(e);
		}
	}

	private String getSqlText(SessionImplementor session) throws DataAccessException
	{
		Template template = loadTemplate(getQueryName());
		if(template != null)
		{
			fillBeanParameters();
			return transformQuery(template);
		}

		SessionFactoryImplementor factory = null;
		//достаем SessionFactory из сессии:
		factory = session.getFactory();
		if (factory == null)
			throw new DataAccessException("Ошибка при получении текста запроса " + queryName);
		//Дергаем оттуда напрямую текст запроса
		NamedSQLQueryDefinition nsqd = factory.getNamedSQLQuery(getQueryName());
		if(nsqd == null)
			throw new DataAccessException("Ошибка при получении текста запроса " + queryName);
		String sqltext = nsqd.getQueryString();
		if (sqltext == null)
			throw new DataAccessException("Ошибка при получении текста запроса " + queryName);
		return sqltext;
	}

	private PreparedStatement createPreparedStatement(Connection connection, String sqltext, SessionImplementor session)
			throws SQLException, DataAccessException
	{
		Matcher matcher = BIND_PATTERN.matcher(sqltext);
		List<TypedValue> valueList = new LinkedList<TypedValue>();
		//Разбор запроса и подготовка для инициализации
		while (matcher.find())
		{
			String bindName = matcher.group();
			TypedValue typedValue = null;
			if (bindName.startsWith(EXTRA_PARAMETERS_PREFIX))
				typedValue = getTypedValue(bindName);
			else
				typedValue = getTypedValueFromBean(bindName);
			if (typedValue.getValue() instanceof Collection)
			{   //если параметром у нас коллекция,
				//нам нужно переделать текст запроса
				//и обеспечить передачу всех элементов этой коллекции
				int size = ((Collection) typedValue.getValue()).size();
				StringBuilder stringBuilder = new StringBuilder();
				int i = 0;
				for (Object value : ((Collection) typedValue.getValue()))
				{
					stringBuilder.append('?');   //подготавливаем замену одной переменной на коллекцию
					if (++i != size)
						stringBuilder.append(',');

					Type type;
					if (value == null)
					{
						value = NullParameterType.VALUE;
						type = NullParameterType.INSTANCE;
					}
					else
						type = TypeFactory.heuristicType(value.getClass().getName());
					//сохраняем в список значения байнд-переменных:
					valueList.add(new TypedValue(type, value, EntityMode.POJO));
				}
				sqltext = sqltext.replaceFirst(":" + bindName, stringBuilder.toString());
			}
			else  {
				valueList.add(typedValue);
				sqltext = sqltext.replaceFirst(":"+bindName,"?");
			}
		}
		//создаие и заполнение стэйтмента
		PreparedStatement preparedStatement = connection.prepareStatement(sqltext);
		try
		{
			int index = 1;
			for (TypedValue typedValue : valueList)
				typedValue.getType().nullSafeSet(preparedStatement, typedValue.getValue(), index++, session);
		}
		catch (SQLException e)
		{
			preparedStatement.close();
			throw e;
		}
		catch (HibernateException e)
		{
			preparedStatement.close();
			throw e;
		}
		return preparedStatement;
	}

	protected TypedValue getTypedValueFromBean(String name) throws DataAccessException
	{
		throw new UnsupportedOperationException("Получение параметров из бина реализовано в BeanQuery");
	}

	private <T> List<T> transformList(List list) throws SystemException
	{
		if (CollectionUtils.isEmpty(list))
			return list;

		if (listTransformer == null)
			return list;

		List<T> transformedList = (List<T>) listTransformer.transform(list);
/*		if (CollectionUtils.size(transformedList) != list.size())
			throw new RuntimeException("Размер выходного списка должен быть равен размеру входного списка!");*/

		return transformedList;
	}

	private <T> T transformUnique(Object o) throws SystemException
	{
		List<T> transformedList = transformList(Collections.singletonList(o));
		return transformedList.iterator().next();
	}

	/**
	 * Выполнить запрос и вернуть итератор резутьтатов
	 *
	 * @return итератор результатов
	 */
	public <T> Iterator<T> executeIterator() throws DataAccessException
	{
		return new QueryIterator<T>(this);
	}

	public <T> T executeUnique() throws DataAccessException
	{
	    try
	    {
	        Object result = executor.execute(new HibernateAction<Object>()
	        {
	            public Object run(Session session) throws Exception
	            {
	                enableFilters(session);
		            org.hibernate.Query query = createQuery(session);
		            setRestrictions(query);
		            return query.uniqueResult();
	            }
	        });
		    return (T)transformUnique(result);
	    }
	    catch (DataAccessException e)
	    {
		    throw e;
	    }
	    catch (Exception e)
	    {
	        throw new DataAccessException(e);
	    }
	}

	private void enableFilters(Session session)
	{
		restriction.applyFilter(session);
		for (Filter filter : filters)
		{
			FilterImpl filterImpl = (FilterImpl) filter;
			org.hibernate.Filter hibFilter = session.enableFilter(filterImpl.getName());

			for (Map.Entry<String, Object> entry : filterImpl.getParameters().entrySet())
			{
				hibFilter.setParameter(entry.getKey(), entry.getValue());
			}
		}
	}

	private void setRestrictions(org.hibernate.Query query)
	{
	    if(maxResults != UNDEFINED)
	        query.setMaxResults(maxResults);

	    if(firstResult != UNDEFINED)
	        query.setFirstResult(firstResult);

		setPagingParameters(query);
	}

	private void setPagingParameters(org.hibernate.Query query)
	{
		String[] paramsArray = query.getNamedParameters();
		Set<String> params = new HashSet<String>(paramsArray.length);
		//noinspection ManualArrayToCollectionCopy
		for (String param : paramsArray)
			params.add(param);

		if (params.contains("maxRows"))
			query.setLong("maxRows", 1 + (maxResults != UNDEFINED ? maxResults : 100));

		if (params.contains("firstRow"))
			query.setLong("firstRow", (firstResult != UNDEFINED ? firstResult : 1));
	}

	protected org.hibernate.Query createQuery(Session session) throws DataAccessException
	{
		org.hibernate.Query query;
		//Ищем тело генерируемого запроса.
		Template template = loadTemplate(getQueryName());
		if(template != null)
		{
		    //для заполнения тегов данными из операции.
			fillBeanParameters();

			String transQueryString = transformQuery(template);
			query = session.createSQLQuery(transQueryString);

			((SQLQuery)query).setResultSetMapping(getQueryName() + RESULTSET);
		}
		else
			query = session.getNamedQuery(getQueryName());

		if(query == null)
			throw new DataAccessException("Запрос " + getQueryName() + " не найден");

		//модифицируем query, только если есть параметры сортировки и если она является SQLQuery
		if(!orderParameters.isEmpty() && (query instanceof SQLQuery))
		{
		    query = fillOrderParameters(session,query);
		}
	    fillParameters(query);

	    return query;
	}

	private String transformQuery(Template template) throws DataAccessException
	{
		StringWriter writer = new StringWriter();

		try
		{
			if(MapUtils.isNotEmpty(extraParameters))
				template.process(extraParameters, writer);
		}
		catch (TemplateException ex)
		{
			throw new DataAccessException(ex.getMessage(), ex);
		}
		catch (IOException ex)
		{
			throw new DataAccessException(ex.getMessage(), ex);
		}

		return writer.toString();
	}

	private Template loadTemplate(String queryName) throws DataAccessException
	{
		return SQLQueryTemplateSource.getTemplate(queryName,executor.getInstanceName());
	}

	/**
	 * получить все именнованные параметры запроса
	 * @return список имен параметров.
	 * @throws com.rssl.phizic.dataaccess.DataAccessException
	 */
	public List<String> getNamedParameters() throws DataAccessException
	{
	    try
	    {
	        return executor.execute(new HibernateAction<List<String>>()
	        {
	            public List<String> run(Session session) throws Exception
	            {
	                org.hibernate.Query query = session.getNamedQuery(getQueryName());
		            List<String> namedParametresFromQuery =Arrays.asList(query.getNamedParameters());
		            return namedParametresFromQuery;

	            }
	        });
	    }
	    catch (DataAccessException e)
	    {
	        throw e;
	    }
	    catch (Exception e)
	    {
	       throw new DataAccessException(e);
	    }
	}

	private org.hibernate.Query fillOrderParameters(Session session, org.hibernate.Query query) throws DataAccessException
	{
		SessionImplementor sessionImpl = (SessionImplementor) session;
		ResultSetMappingDefinition resultSetMapping = sessionImpl.getFactory().getResultSetMapping(getQueryName() + RESULTSET);
		for(OrderParameter parameter : orderParameters )
		{
			if(!checkOrderParameter(parameter,resultSetMapping))
				throw new DataAccessException("Параметр сортировки " + parameter.getValue() + " не специфицирован.");
		}

		StringBuilder queryBuilder = new StringBuilder(query.getQueryString());
		queryBuilder.append(" order by ");
		queryBuilder.append(StringUtils.join(orderParameters,","));

		SQLQuery orderedQuery = session.createSQLQuery(queryBuilder.toString());
		orderedQuery.setResultSetMapping(getQueryName() + RESULTSET);
		return orderedQuery;
	}

	private boolean checkOrderParameter(OrderParameter parameter, ResultSetMappingDefinition resultSetMapping)
	{
		//проверяем является ли параметр сортировки отдельным параметром ResultSet-a
		for(SQLQueryScalarReturn sqlQueryScalarReturn : resultSetMapping.getScalarQueryReturns())
			if(sqlQueryScalarReturn.getColumnAlias().equals(parameter.getValue()))
				return true;
		//затем пробегаем по alias-ам ResulSet-a
		for(SQLQueryReturn sqlQueryReturn : resultSetMapping.getEntityQueryReturns())
			//для каждого alias-a проходим по списку полей
			for(Iterator itr = sqlQueryReturn.getPropertyResultsMap().entrySet().iterator();itr.hasNext();)
			{
				Map.Entry entry = (Map.Entry)itr.next();
				for(String resultSetColumnValue: (String[])entry.getValue())
					if(parameter.getValue().equals(resultSetColumnValue))
						return true;
			}
		return false;
	}

	protected void fillParameters(org.hibernate.Query query) throws DataAccessException
	{
		String[] namedParameters = query.getNamedParameters();
		for (String name : namedParameters)
		{
			String nakedName = name;

			if (!nakedName.startsWith(EXTRA_PARAMETERS_PREFIX))
				continue;

			TypedValue typedValue = getTypedValue(nakedName);

			if (typedValue.getValue() instanceof Collection)
				query.setParameterList(name, (Collection) typedValue.getValue());
			else
               	query.setParameter(name, typedValue.getValue(), typedValue.getType());
		}
	}

	private TypedValue getTypedValue(String parameterName) throws DataAccessException
	{
		String nakedName = parameterName;

		if (!nakedName.startsWith(EXTRA_PARAMETERS_PREFIX))
			throw new DataAccessException("Parameter " + nakedName + " not starts with \"extra_\"");

		nakedName = nakedName.substring(EXTRA_PARAMETERS_PREFIX.length());

		boolean isLike = false;
		if (nakedName.startsWith(LIKE_PARAMETERS_PREFIX))
		{
			nakedName = nakedName.substring(LIKE_PARAMETERS_PREFIX.length());
			isLike = true;
		}

		if (!extraParameters.containsKey(nakedName))
			throw new DataAccessException("Parameter " + nakedName + " not specified");

		Object value = extraParameters.get(nakedName);
		Type type;

		if (value == null)
		{
			value = NullParameterType.VALUE;
			type = NullParameterType.INSTANCE;
		}
		else if (!isLike)
		{
			type = TypeFactory.heuristicType(value.getClass().getName());
		}
		else
		{
			type = LikeStringType.INSTANCE;
		}
		return new TypedValue(type, value, EntityMode.POJO);//POJO - default
	}

	public Filter enableFilter(String filterName)
	{
	    Filter filter = new FilterImpl(filterName);
	    filters.add(filter);
	    return filter;
	}

	public String getQueryName()
	{
		return queryName;
	}

	public void setQueryName(String queryName)
	{
		this.queryName = queryName;
	}

	/**
	 * Итератор, позволяет постранично читать данные.
	 * ОГРАНИЧЕНИЕ!!!: ПРИ ИЗМЕНЕНИЙ ЧИТАЕМЫХ ДАННЫХ РЕЗУЛЬТАТ НЕ ОПРЕДЕЛЕН!
	 */
	private class QueryIterator<E> implements Iterator<E>
	{
		private ExecutorQuery query;
		private int         firstResult;
		private int         pageSize = 1000;
		private Iterator<E> bufferIterator;
		private E           nextElement;
		private int         iteratedElements;
		private E current;

		private QueryIterator(ExecutorQuery query)
		{
			this.query = query;
		}

		private void shureInitialized()
		{
			if (bufferIterator == null)
			{
				readFirst();
			}
		}

		public boolean hasNext()
		{
			shureInitialized();
			return hasNextInternal();
		}

		private boolean hasNextInternal()
		{
			return nextElement != null && ((query.getMaxResults() == UNDEFINED) || (query.getMaxResults() > iteratedElements));
		}

		public E next()
		{
			shureInitialized();

			if (hasNextInternal())
			{
				current = nextElement;

				if (bufferIterator.hasNext())
				{
					nextElement = bufferIterator.next();
				}
				else
				{
					readNext();
				}

				iteratedElements++;

				try
				{
					return (E)query.transformUnique(current);
				}
				catch (SystemException e)
				{
					throw new RuntimeException(e);
				}
			}
			else
			{
				throw new NoSuchElementException();
			}
		}

		public void remove()
		{
			if (current == null){
				throw new IllegalStateException();
			}
			try
			{
				//noinspection InnerClassTooDeeplyNested
				executor.execute(new HibernateAction<Void>()
				{
					public Void run(Session session) throws Exception
					{
						session.delete(current);

						return null;
					}
				});
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
			firstResult--;
		}

		private void readNext()
		{
			firstResult += pageSize;
			executeQuery();
		}

		private void executeQuery()
		{
			try
			{
				//noinspection InnerClassTooDeeplyNested
				executor.execute(new HibernateAction<Void>()
				{
					public Void run(Session session) throws Exception
					{
						query.enableFilters(session);
						org.hibernate.Query hibernateQuery = query.createQuery(session);
						hibernateQuery.setFirstResult(firstResult);
						hibernateQuery.setMaxResults(pageSize);
						setPagingParameters(hibernateQuery);
						//noinspection unchecked
						bufferIterator = hibernateQuery.list().iterator();

						if (bufferIterator.hasNext())
						{
							nextElement = bufferIterator.next();
						}
						else
						{
							nextElement = null;
						}

						return null;
					}
				});
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}

		private void readFirst()
		{
			iteratedElements = 0;
			firstResult = query.getFirstResult() == UNDEFINED ? 0 : query.getFirstResult();
			executeQuery();
		}
	}
}
