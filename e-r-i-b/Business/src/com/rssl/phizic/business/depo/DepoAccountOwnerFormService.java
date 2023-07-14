package com.rssl.phizic.business.depo;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.depo.DepoAccountOwnerForm;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author lukina
 * @ created 19.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoAccountOwnerFormService
{
	/**
	 * ��������� ������ ��������� �� ������ ����� ���� � id ������ �������
	 * @param accountNumber - ����� ����� ����
	 * @param loginId - id ������ �������
	 * @return ������ ���������
	 * @throws BusinessException
	 */
	public DepoAccountOwnerFormImpl getDepoAccountOwnerFormByAccNumberAndLoginId(final String accountNumber, final Long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<DepoAccountOwnerFormImpl>()
			{
				public DepoAccountOwnerFormImpl run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(DepoAccountOwnerFormService.class.getName() + ".findByAccNumberAndLoginId");
					query.setParameter("accountNumber", accountNumber);
					query.setParameter("loginId", loginId);
					return (DepoAccountOwnerFormImpl) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ ��������� �� ������ ����� ���� � id ������ �������
	 * @param accountNumber - ����� ����� ����
	 * @param loginId - id ������ �������
	 * @throws BusinessException
	 */
	public void deleteDepoAccountByExternalId(final String accountNumber, final Long loginId) throws BusinessException
	{
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = HibernateExecutor.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			Query query = session.getNamedQuery(DepoAccountOwnerFormService.class.getName() + ".deleteByAccNumberAndLoginId");
			query.setParameter("accountNumber", accountNumber);
			query.setParameter("loginId", loginId);
			query.executeUpdate();

			transaction.commit();
		}
		catch (Exception e)
		{
			if (transaction != null)
				transaction.rollback();

			throw new BusinessException(e);
		}
		finally
		{
			if (session != null)
			{
				session.close();
			}
		}
	}

	/**
	 * ����������� ������ ���������
	 * @param form ����� ������
	 * @throws BusinessException
	 */
	public void addOrUpdate(DepoAccountOwnerForm form) throws BusinessException
	{
		SimpleService service = new SimpleService();
		DepoAccountOwnerFormImpl newForm = new DepoAccountOwnerFormImpl(form);
		deleteDepoAccountByExternalId(form.getDepoAccountNumber(), form.getLoginId());
		service.add(newForm);
	}
}
