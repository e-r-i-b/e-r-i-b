package com.rssl.phizic.business.ermb.migration.mbk;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

/**
 * @author Nady
 * @ created 02.07.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� ����������/��������� ����������� ��������� ������ ���
 * (�� ���������� ����������� ������ ��������� ���� � ������� ����)
 * ������ ���������� ����������� � ��� � ����� (�������� �� ����)
 */
public class MBKRegistrationResultDatabaseService
{
	private static final String PHIZ_PROXY_MBK_DB_INSTANCE = "PhizProxyMBK";

	private final MultiInstanceSimpleService simpleService = new MultiInstanceSimpleService();

	/**
	 * ��������� ��������� ��������� ������ ���
	 * @param result - ��������� ��������� ������ ��� (never null)
	 */
	public void addOrUpdate(final MBKRegistrationResult result) throws BusinessException
	{
		if (result == null)
			throw new IllegalArgumentException("�� ������ result");

		simpleService.addOrUpdate(result, PHIZ_PROXY_MBK_DB_INSTANCE);
	}

	/**
	 * ��������� ���������� ��������� ������ �� �� ���������������
	 * @param ids - �������������� ������ (never null can be empty)
	 * @return ������ � ������������ ��������� (never null can be empty)
	 */
	public List<MBKRegistrationResult> loadRegistrationsResults(List<Long> ids) throws BusinessException
	{
		if (ids.isEmpty())
			return Collections.emptyList();

		return simpleService.findByIds(MBKRegistrationResult.class, ids, PHIZ_PROXY_MBK_DB_INSTANCE);
	}

	/**
	 * ������� ���������� ��������� �� ��������� �������
	 * @param ids - �������������� ������ (never null can be empty)
	 */
	public void removeByIds(final List<Long> ids) throws BusinessException
	{
		if (ids.isEmpty())
			return;

		try
		{
			HibernateExecutor.getInstance(PHIZ_PROXY_MBK_DB_INSTANCE).execute(new HibernateAction<Void>()
			{
				public Void run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.ermb.migration.mbk.MBKRegistrationResult.removeByIds");
					query.setParameterList("ids", ids);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
