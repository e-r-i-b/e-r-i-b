package com.rssl.phizicgate.mdm.business;

import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;

/**
 * @author akrenev
 * @ created 17.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������� ������ ������ � �� ���
 */

public abstract class MDMDatabaseServiceBase
{
	protected static final DatabaseServiceBase databaseService = new DatabaseServiceBase();

	/**
	 * ��������� �������� � ����������
	 * @param action ��������
	 * @param <T> ��� ������������ ������
	 * @return ���������
	 * @throws Exception
	 */
	public static <T> T executeAtomic(HibernateAction<T> action) throws Exception
	{
		return HibernateExecutor.getInstance(getInstance()).execute(action);
	}

	protected static String getInstance()
	{
		return null;
	}
}
