package com.rssl.phizic.dataaccess.query.template;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.utils.StringHelper;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author mihaylov
 * @ created 20.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Загрузчик шаблонов генерируемых запросов на основе данных о ресалт сетах.
 */
public class SQLQueryTemplateLoader
{
	private static final String RESULTSET = ".resultset";
	private static final String TEMPLATE_FILE_EXTENSION = ".ftl";
	private static Configuration configuration;
	private static final Object lock = new Object();

	private Set<String> sqlResultSetNames;
	private String jndiName;

	/**
	 * Конструктор
	 * @param sqlResultSetNames - набор имен ресалт сетов
	 * @param jndiName - jndiName запускаемого сервиса Hibernate
	 */
	public SQLQueryTemplateLoader(Set<String> sqlResultSetNames, String jndiName)
	{
		this.sqlResultSetNames = new HashSet<String>(sqlResultSetNames);
		this.jndiName = jndiName;
	}

	/**
	 * Загрузить шаблоны генерируемых запросов
	 */
	public void load() throws SystemException
	{
		Map<String,Template> sqlQueryTemplateMap = new HashMap<String, Template>();
		for(String resultSetName: sqlResultSetNames)
		{
			String queryName = getTemplateQueryName(resultSetName);
			if(StringHelper.isEmpty(queryName))
				continue;
			Template template = loadTemplate(queryName);
			if(template != null)
				sqlQueryTemplateMap.put(queryName,template);
		}
		SQLQueryTemplateSource.addTemplateMap(sqlQueryTemplateMap,jndiName);
	}

	private Template loadTemplate(String queryName) throws SystemException
	{
		String tmp = StringUtils.replace(queryName, "Operation.", ".");
		String[] what = {"."};
		String[] to = {"/"};
		tmp = StringUtils.replaceEachRepeatedly(tmp,what, to);
		StringBuilder builder = new StringBuilder(tmp);
		builder.append(TEMPLATE_FILE_EXTENSION);

		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(builder.toString());
		if(inputStream == null)
		   return null;
		InputStreamReader tmlReader = new InputStreamReader(inputStream);

		try
		{
			return new Template("QueryTransformer", tmlReader, getConfiguration());
		}
		catch (IOException e)
		{
			throw new SystemException("Ошибка при загрузке шаблона для запроса " + queryName,e);
		}
		finally
		{
			if(tmlReader != null)
				try
				{
					tmlReader.close();
				}
				catch (IOException ignore)
				{
				}
		}
	}

	private String getTemplateQueryName(String queryResultSetName)
	{
		int index = queryResultSetName.lastIndexOf(RESULTSET);
		if(index < 0)
			return  null;
		return queryResultSetName.substring(0,index);
	}

	private Configuration getConfiguration()
	{
		if(configuration != null)
			return configuration;
		synchronized (lock)
		{
			if(configuration != null)
				return configuration;
			configuration = new Configuration();
			configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			configuration.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
			configuration.setDefaultEncoding("windows-1251");
			configuration.setOutputEncoding("windows-1251");
			configuration.setLocale(new Locale("ru", "RU"));
		}
		return configuration;
	}

}
