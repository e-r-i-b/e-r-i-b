package com.rssl.phizic.business.fund.initiator;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.fund.FundResponseState;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.gate.fund.Response;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author osminin
 * @ created 15.09.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������ �������� �� ���� ������� � ����� ����������
 */
public class FundInitiatorResponseService
{
	/**
	 * �������� ���������� ��������� ������� �� ���� �������
	 * @param requestId ������������� �������
	 * @return ������ �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public List<FundInitiatorResponse> getByRequestId(final Long requestId) throws BusinessException, BusinessLogicException
	{
		if (requestId == null)
		{
			throw new IllegalArgumentException("������������� ������� �� ����� ���� null.");
		}
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<FundInitiatorResponse>>()
			{
				public List<FundInitiatorResponse> run(Session session) throws Exception
				{
					return (List<FundInitiatorResponse>) session.getNamedQuery("com.rssl.phizic.business.fund.initiator.FundInitiatorResponse.getByRequestId")
							.setParameter("request_id", requestId)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ���������� � ���������� ��������� �������
	 * @param fundResponse ����� �� ������ ����� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void updateInfo(final Response fundResponse) throws BusinessException, BusinessLogicException
	{
		if (fundResponse == null)
		{
			throw new IllegalArgumentException("����� �� ������ ����� ������� �� ����� ���� null.");
		}
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.business.fund.initiator.FundInitiatorResponse.updateInfo");
					executorQuery.setParameter("external_id", fundResponse.getExternalId());
					executorQuery.setParameter("state", fundResponse.getState().name());
					executorQuery.setParameter("sum", fundResponse.getSum());
					executorQuery.setParameter("message", fundResponse.getMessage());
					executorQuery.executeUpdate();
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
	 * �������� ���������� � ���������� ��������� ������� � ������ ����������� �����
	 * @param fundResponse ����� �� ������ ����� �������
	 * @param requiredSum ����������� �����
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void updateInfoBySum(final Response fundResponse, final BigDecimal requiredSum) throws BusinessException, BusinessLogicException
	{
		if (fundResponse == null)
		{
			throw new IllegalArgumentException("����� �� ������ ����� ������� �� ����� ���� null.");
		}
		if (FundResponseState.SUCCESS != fundResponse.getState())
		{
			throw new IllegalArgumentException("��������� ��������� ������ ������ ���� � ������� SUCCESS.");
		}
		if (fundResponse.getSum() == null || fundResponse.getSum().compareTo(BigDecimal.ZERO) == 0)
		{
			throw new IllegalArgumentException("��������� ��������� ������ ������ ����� �����.");
		}
		if (requiredSum == null)
		{
			throw new IllegalArgumentException("����������� ����� �� ����� ���� null.");
		}
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					ExecutorQuery executorQuery = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.business.fund.initiator.FundInitiatorResponse.updateInfoBySum");
					executorQuery.setParameter("external_id", fundResponse.getExternalId());
					executorQuery.setParameter("calculated_sum", requiredSum.subtract(fundResponse.getSum()));
					executorQuery.setParameter("state", fundResponse.getState().name());
					executorQuery.setParameter("sum", fundResponse.getSum());
					executorQuery.setParameter("message", fundResponse.getMessage());
					executorQuery.executeUpdate();
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
	 * �������� ������� �������, ��� ����������� ����� �������
	 * @param requestId ������������� �������
	 * @throws BusinessException
	 */
	public void resetAccumulatedByRequestId(final Long requestId) throws BusinessException
	{
		if (requestId == null)
		{
			throw new IllegalArgumentException("������������� ������� �� ����� ���� null.");
		}
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Object>()
			{
				public Object run(Session session) throws Exception
				{
					session.getNamedQuery("com.rssl.phizic.business.fund.initiator.FundInitiatorResponse.resetAccumulatedByRequestId")
							.setParameter("request_id", requestId)
							.executeUpdate();
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
	 * ��������� ����� ����������� �����
	 * @param requestId ������������� ������� �� ���� �������
	 * @return ����� ����������� �����
	 * @throws BusinessException
	 */
	public BigDecimal getAccumulatedSum(final Long requestId) throws BusinessException
	{
		if (requestId == null)
		{
			throw new IllegalArgumentException("������������� ������� �� ���� ������� �� ����� ���� null.");
		}

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<BigDecimal>()
			{
				public BigDecimal run(Session session) throws Exception
				{
					return (BigDecimal) session.getNamedQuery("com.rssl.phizic.business.fund.initiator.FundInitiatorResponse.getAccumulatedSum")
							.setParameter("request_id", requestId)
							.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ��� ����������� �����
	 * @param firstName ��� �����������
	 * @param surName ������� �����������
	 * @param patrName �������� �����������
	 * @param externalId ������� ������������� ������
	 * @throws BusinessException
	 */
	public void updateFIO(final String firstName, final String surName, final String patrName, final String externalId) throws  BusinessException
	{
		if (StringHelper.isEmpty(surName))
		{
			throw new IllegalArgumentException("������� �� ����� ���� null.");
		}
		if (StringHelper.isEmpty(firstName))
		{
			throw new IllegalArgumentException("��� �� ����� ���� null.");
		}
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.getNamedQuery("com.rssl.phizic.business.fund.initiator.FundInitiatorResponse.updateFIO")
							.setParameter("first_name", firstName)
							.setParameter("sur_name", surName)
							.setParameter("patr_name", patrName)
							.setParameter("external_id", externalId)
							.executeUpdate();
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
