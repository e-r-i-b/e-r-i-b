package com.rssl.phizic.business.finances.processed;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.DateHelper;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 10.07.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������ � �������� ���������� ������, ����������� � ������� ������.
 */
public class ProcessedCardOperationClaimLoginService
{
	private static final String DELETE_OLD_QUERY_NAME = ProcessedCardOperationClaimLogin.class.getName() + ".deleteOld";
	private static final SimpleService simpleService = new SimpleService();


	/**
	 * �������� ������ � ���������� ������ ��������� ������.
	 * @param loginId - ������������� ������ ��������� ������
	 * @return ������ � ���������� ������
	 * @throws BusinessException
	 */
	public ProcessedCardOperationClaimLogin add(Long loginId) throws BusinessException
	{
		return simpleService.add(new ProcessedCardOperationClaimLogin(loginId));
	}

	/**
	 * ������� ������ � ���������� ��������� ������
	 * @param loginId ������������� ������ ��������� ������
	 * @throws BusinessException
	 */
	public void remove(Long loginId) throws BusinessException
	{
		simpleService.remove(new ProcessedCardOperationClaimLogin(loginId));
	}

	/**
	 * ������� ������ � ���������� ������� ������, ��� claimExecutionMaxTime
	 * @param claimExecutionMaxTime - ������������ ����� ���������� ������
	 * @throws BusinessException
	 */
	public void removeOld(final int claimExecutionMaxTime) throws BusinessException
	{
		final Calendar processingDate = DateHelper.addSeconds(Calendar.getInstance(), -claimExecutionMaxTime);
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
				{
					public Void run(Session session) throws Exception
					{
						Query query = session.getNamedQuery(DELETE_OLD_QUERY_NAME);
						query.setParameter("processingDate", processingDate);
						query.executeUpdate();
						return null;
					}
				});
		}
		catch (Exception e)
		{
			throw new BusinessException("�� ������� ������� ������ ������ �� ��������� ������",e);
		}
	}

}
