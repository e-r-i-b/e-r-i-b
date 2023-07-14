package com.rssl.phizic.business.dictionaries.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.ips.CardOperationType;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Collection;
import java.util.List;

/**
 * @author Erkin
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 * ������ ��� ������ � ������ ��������
 */
public class CardOperationTypeService
{
	private static final String QUERY_PREFIX = CardOperationTypeService.class.getName() + ".";

	/**
	 * ������� ��� ��������� �������� �� ����
	 * @param code - ��� ���� ��������
	 * @return ��� ��������� ��������
	 */
	public CardOperationType findByCode(final Long code) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<CardOperationType>()
			{
				public CardOperationType run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "findOperationTypeByCode");
					query.setParameter("code", code);
					//noinspection unchecked
					return (CardOperationType)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������� ���� ��������� �������� �� �����
	 * @param operationTypeCodes - �������� ����� ����� ��������
	 * @return ������ ��������� �����
	 */
	public List<CardOperationType> findByCode(final Collection<Long> operationTypeCodes) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CardOperationType>>()
			{
				public List<CardOperationType> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "findOperationTypesByCode");
					query.setParameterList("codes", operationTypeCodes);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
