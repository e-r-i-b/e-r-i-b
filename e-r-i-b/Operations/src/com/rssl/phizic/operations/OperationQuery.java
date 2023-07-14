package com.rssl.phizic.operations;

import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.dataaccess.DataAccessException;

import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.business.hibernate.DataBaseTypeQueryHelper;

import java.util.List;

/**
 * ��������� ������� ��� ��������
 * @author Evgrafov
 * @ created 25.11.2005
 * @ $Author$
 * @ $Revision$
 */
class OperationQuery extends BeanQuery
{
	OperationQuery(Operation operation, String queryName)
	{
		super(operation, ClassHelper.getActualClass(operation).getName() + "." + queryName);
	}

	/**
	 * ����������� �������
	 * @param operation ������ �������� ��� �������������� ������
	 * @param queryName ��� �������
	 * @param instanceName ��� ���������� ��, � ������� ������ ���� �������� ������
	 */
	OperationQuery(Operation operation, String queryName, String instanceName)
	{
		super(operation, ClassHelper.getActualClass(operation).getName() + "." + queryName, instanceName);
	}

	public <T> T executeUnique() throws DataAccessException
	{
		if (!check())
		{
		   setQueryName(getQueryName() + "." + DataBaseTypeQueryHelper.getDBType());
		}
		return (T) super.executeUnique();
	}                                                                        

	public <T> List<T> executeListInternal() throws DataAccessException
	{
		if (!check())
		{
		   setQueryName(getQueryName() + "." + DataBaseTypeQueryHelper.getDBType());
		}
		return super.executeListInternal();
	}

}
