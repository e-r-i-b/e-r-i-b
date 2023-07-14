package com.rssl.phizic.business.loanclaim.creditProduct.type;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.loanclaim.creditProduct.CreditProduct;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author Moshenko
 * @ created 26.12.2013
 * @ $Author$
 * @ $Revision$
 * ������ ��� ������ � ������ ��������� ���������.
 */
public class CreditProductTypeService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * @return ��� ���� ��������� ���������
	 * @throws BusinessException
	 */
	public List<CreditProductType> getAll() throws BusinessException
	{
		return simpleService.getAll(CreditProductType.class);
	}

	/**
	 * @param creditType ��� ���������� ��������
	 * @throws BusinessException
	 */
	public void addOrUpdate(CreditProductType creditType) throws BusinessException, CreditTypeCodeNotUnique
	{
		try
		{
		    simpleService.addOrUpdateWithConstraintViolationException(creditType);
		}
		catch (ConstraintViolationException e)
		{
			throw new CreditTypeCodeNotUnique("��������� ���� �������� ���� ���� �������� ��� ���� � �������. �������� ��� ���� ��������", e);
		}
	}


	/**
	 * @param creditType ��� ���������� ��������
	 * @throws BusinessException
	 * @throws CreditTypeCodeNotUnique
	 */
	public void update(CreditProductType creditType) throws BusinessException
	{
		simpleService.update(creditType);
	}

	/**
	 * ������� ��� ���������� �������� �� code
	 * @param code ��� ���� ��������
	 * @throws BusinessException
	 */
	public void removeByCode(final Long code) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(CreditProductType.class.getName() + ".removeById");
					query.setParameter("code", code);
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

	/**
	 * ������� ��� ���������� ��������
	 * @throws BusinessException
	 */
	public void remove(CreditProductType creditType) throws BusinessException
	{
		simpleService.remove(creditType);
	}

	/**
	 * ����� ��� ���������� �������� �� cod
	 * @param code ��� ���� ��������
	 * @throws BusinessException
	 */
	public CreditProductType findeByCode(final String code) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(CreditProductType.class);
		criteria.add(Expression.eq("code",code));
		return simpleService.findSingle(criteria);
	}

	/**
	 * @param creditProduct ��������� ��������
	 * @return ������ ����� ���������� ��� ��������
	 * @throws BusinessException
	 */
	public List<CreditSubProductType> getAllByCreditProduct(final CreditProduct creditProduct) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CreditSubProductType>>()
			{
				public List<CreditSubProductType> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(CreditSubProductType.class.getName() + ".getAllByCreditProduct");
					query.setParameter("creditProduct", creditProduct);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 *
	 * @param id id
	 * @return ��������� �������
	 * @throws BusinessException
	 */
	public CreditProductType findById(Long id) throws  BusinessException
	{
		return simpleService.findById(CreditProductType.class,id);
	}
}
