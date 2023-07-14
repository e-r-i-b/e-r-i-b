package com.rssl.phizgate.common.providers;

import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.Session;

import java.util.Iterator;

/**
 * ������ ��� ������ � ��������� ProviderProperties
 * @author gladishev
 * @ created 14.01.2015
 * @ $Author$
 * @ $Revision$
 */

public class ProviderPropertiesService
{
	private static final String CSA_ADMIN_DB_NAME = "CSAAdmin";
	private static final DatabaseServiceBase simpleService = new DatabaseServiceBase();

	/**
	 * ����� ������� ���������� �� id ����������
	 * @param providerId - ������������� ���������
	 * @return ������ �� ���������� ����������
	 */
	public ProviderPropertiesEntry findById(Long providerId) throws GateException
	{
		try
		{
			return simpleService.findById(ProviderPropertiesEntry.class, providerId, null, CSA_ADMIN_DB_NAME);
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

	/**
	 * ��������/�������� ������ ������� ����������
	 * @param entry - �������� ����������
	 */
	public ProviderPropertiesEntry addOrUpdate(ProviderPropertiesEntry entry) throws GateException
	{
		try
		{
			return simpleService.addOrUpdate(entry, CSA_ADMIN_DB_NAME);
		}
		catch (Exception ex)
		{
			throw new GateException(ex);
		}
	}

	/**
	 * ������������ �� ��� ��� ������� � �����������
	 * @param providerId - id ���������� � �� ��������
	 * @return - true - �������������� ����� ���,
	 * ����� ������� ���� �� ������ ���������� �� url, ��������� � ���������� ����������
	 */
	public boolean isUseESBProvider(final Long providerId) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance(CSA_ADMIN_DB_NAME).execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Iterator iterate = session.getNamedQuery("com.rssl.phizgate.common.providers.ProviderPropertiesEntry.isUseESBProvider")
							.setParameter("providerId", providerId)
							.iterate();
					return iterate.hasNext() && (Boolean) iterate.next();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
