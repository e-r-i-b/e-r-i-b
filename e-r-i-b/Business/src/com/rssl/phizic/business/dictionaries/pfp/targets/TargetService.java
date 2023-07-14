package com.rssl.phizic.business.dictionaries.pfp.targets;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author akrenev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ � ������ ���
 */

public class TargetService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();
	private static final String PREFIX_QUERY = TargetService.class.getName() + ".";

	/**
	 * @return ������ ���� ��������� ����� ��� �������
	 * @throws BusinessException
	 */
	public List<Target> getAll() throws BusinessException
	{
		return service.getAll(Target.class, Order.asc("laterAll"), null);
	}

	/**
	 * @param id ������������� ����
	 * @return ����
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public Target getById(final Long id) throws BusinessException
	{
		return getById(id, null);
	}

	/**
	 * @param id ������������� ����
	 * @param instance ��� �������� ������ ��
	 * @return ����
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public Target getById(final Long id, String instance) throws BusinessException
	{
		return service.findById(Target.class, id, instance);
	}

	/**
	 * �������� ����
	 * @param target ����
	 * @param instance ��� �������� ������ ��
	 * @return ����
	 * @throws BusinessException
	 */
	public Target addOrUpdate(final Target target, String instance) throws BusinessException
	{
		return service.addOrUpdate(target, instance);
	}

	/**
	 * ������� ����
	 * @param target ��������� ����
	 * @param instance ��� �������� ������ ��
	 * @throws BusinessException
	 */
	public void remove(final Target target, final String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			service.removeWithImage(target, instance);
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("���������� ������� ����.", e);
		}
	}

	/**
	 * ������������� ������ ���� "���� ���������� ���� ����� ���������",
	 * ���� ���� ������� ����� � ������ ����, �� ������� ��� � ��
	 *
	 * @param id - ������������� ����
	 * @param instance ��� �������� ������ ��
	 * @throws BusinessException
	 */
	public void setAfterAll(final Long id, String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Target.class).add(Expression.eq("laterAll", true)).add(Expression.ne("id", id));
		Target target = service.findSingle(criteria, instance);
		if (target == null)
			return;

		target.setLaterAll(false);
		addOrUpdate(target, instance);
	}

	/**
	 * ����� � ����������� ���� � ��������� "���� ���������� ���� ����� ���������"
	 * @return ���� � ��������� "���� ���������� ���� ����� ���������"
	 * @throws BusinessException
	 */
	public Target findLaterAllTarget() throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Target>()
			{
				public Target run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(PREFIX_QUERY + "findLaterAllTarget");
					return (Target) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
