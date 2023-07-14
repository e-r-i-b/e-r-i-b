package com.rssl.phizic.dataaccess.query.template;

import freemarker.template.Template;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 20.10.14
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��� ��������� �������� ������������ ��������
 */
public class SQLQueryTemplateSource
{
	private static final String DELIMITER = ":";
	private static final String DEFAULT_JNDI_NAME = "hibernate/session-factory/PhizIC";
	//������. ���������� ��-�� ���� ��� � HibernateExecutor-e ������ null � HibernateExecutorHelper ���������� main.
	//���� SessionFactory � HibernateExecutor �������� �� null
	private static final String DEFAULT_INSTANCE_NAME = "main";
	private static final Map<String,Template> templateMap = new HashMap<String, Template>();

	protected synchronized static void addTemplateMap(Map<String,Template> map, String jndiName)
	{
		for(Map.Entry<String,Template> entry: map.entrySet())
		{
			templateMap.put(getKey(entry.getKey(),jndiName),entry.getValue());
		}
	}

	/**
	 * �������� ������ ������������� �������
	 * @param queryName - �������� �������
	 * @param instanceName - ������� �� � ������� ���������� ��������� ������
	 * @return ������ ������������� �������
	 */
	public static Template getTemplate(String queryName, String instanceName)
	{
		String jndiName = DEFAULT_JNDI_NAME + getEmptyIfDefault(instanceName);
		return templateMap.get(getKey(queryName,jndiName));
	}

	private static String getEmptyIfDefault(String instanceName)
	{
		return DEFAULT_INSTANCE_NAME.equals(instanceName) ? "" : instanceName;
	}

	private static String getKey(String queryName, String jndiName)
	{
		return queryName + DELIMITER + jndiName;
	}
}
