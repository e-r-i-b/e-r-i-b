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
 * ������ ��� ������ � ��������� ������������ ���������.
 */
public class MultiLocaleQueryHelper
{

	/**
	 * �������� ����� ��� �������� � ������ �������� �����
	 * @param operation - ��� ��������
	 * @param queryName - ��� ����� � ��������
	 * @param instanceName - ������� ��
	 * @return �����
	 */
	public static BeanQuery getOperationQuery(Operation operation, String queryName, String instanceName)
	{
		return getQuery(operation,ClassHelper.getActualClass(operation).getName() + "." + queryName,instanceName);
	}

	/**
	 * �������� ����� � ������ �������� �����
	 * @param queryName - ��� �����
	 * @return �����
	 */
	public static BeanQuery getQuery(String queryName)
	{
		return getQuery(new Object(),queryName,null);
	}

	/**
	 * �������� BeanQuery � ������ �������� �����
	 * @param bean - ���
	 * @param queryName - ��� �����
	 * @param instanceName ������� ��
	 * @return �����
	 */
	public static BeanQuery getQuery(Object bean, String queryName, String instanceName)
	{
		if(MultiLocaleContext.isDefaultLocale())
			return new BeanQuery(bean, queryName, instanceName);
		return new MultiLocaleBeanQuery(bean, queryName, instanceName, MultiLocaleContext.getLocaleId());
	}



}
