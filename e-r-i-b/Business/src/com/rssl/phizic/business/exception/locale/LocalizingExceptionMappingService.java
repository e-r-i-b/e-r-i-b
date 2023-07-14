package com.rssl.phizic.business.exception.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * @author akrenev
 * @ created 01.11.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ � ���������������� ������������ �����������
 */

public class LocalizingExceptionMappingService
{
	private static final SimpleService service = new SimpleService();

	/**
	 * @return ��������
	 * @throws BusinessException
	 */
	public List<LocalizingExceptionMapping> getAll() throws BusinessException
	{
		return service.getAll(LocalizingExceptionMapping.class);
	}

	/**
	 * ��������� �������
	 * @param mapping �������
	 * @throws BusinessException
	 */
	public void save(LocalizingExceptionMapping mapping) throws BusinessException
	{
		service.addOrUpdate(mapping);
	}

	/**
	 * ������� ��� �������� ������� � minId
	 * @param minId ����������� �������������
	 * @throws BusinessException
	 */
	public void removeAll(final Long minId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Object>()
			{
				public Object run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.exception.locale.LocalizingExceptionMapping.delete");
					query.setParameter("id", minId);
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
