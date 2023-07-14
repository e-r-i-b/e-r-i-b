package com.rssl.phizic.business.addressBook.reports;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;

/**
 * @author mihaylov
 * @ created 23.06.14
 * @ $Author$
 * @ $Revision$
 * ������ ��� ������ � ��������� �������� �������� �����.
 */
public class ContactsCountReportEntityService
{
	private static final String QUERY_PREFIX = ContactsCountReportEntity.class.getName();
	private static final SimpleService service = new SimpleService();

	/**
	 * �������� ��������
	 * @param entity ��������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void update(ContactsCountReportEntity entity) throws BusinessException
	{
		service.update(entity);
	}

	/**
	 * ������� ������ � ���������� ��������� �������, ���� � ��� � ��
	 * @param entity ��������
	 * @throws BusinessException
	 */
	public void createIfNotFound(ContactsCountReportEntity entity) throws BusinessException
	{
		try
		{
			ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), QUERY_PREFIX + ".createIfNotFound");
			executorQuery.setParameter("loginId",entity.getLogin());
			executorQuery.setParameter("name",entity.getName());
			executorQuery.setParameter("document",entity.getDocument());
			executorQuery.setParameter("birthday",entity.getBirthday());
			executorQuery.setParameter("tb",entity.getTb());
			executorQuery.executeUpdate();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}
}
