package com.rssl.phizic.business.connectUdbo;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;

/**
 * ������ ��� ������ � ��������� ��������� �� ����������� ����
 * User: miklyaev
 * @ created 11.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class UDBOClaimRulesService extends MultiInstanceUDBOClaimRulesService
{
	/**
	 * ��������� �������� �������
	 * @param claimRules - �������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void add(final UDBOClaimRules claimRules) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.add(claimRules);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� �������� �������
	 * @param claimRules - �������
	 * @throws BusinessException
	 */
	public void update(final UDBOClaimRules claimRules) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.update(claimRules);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� �������
	 * @param claimRules - �������
	 * @throws BusinessException
	 */
	public void remove(final UDBOClaimRules claimRules) throws BusinessException
	{
		remove(claimRules, null);
	}
}
