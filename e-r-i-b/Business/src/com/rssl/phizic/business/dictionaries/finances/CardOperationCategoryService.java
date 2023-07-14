package com.rssl.phizic.business.dictionaries.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.utils.BeanHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author Erkin
 * @ created 25.07.2011
 * @ $Author$
 * @ $Revision$
 * ������ ��� ������ � ����������� �������� �� ������
 */
public class CardOperationCategoryService
{
	protected static final String QUERY_PREFIX = "com.rssl.phizic.business.dictionaries.finances.";
	private static final SimpleService simpleService = new SimpleService();

    /**
     * ����� ��������� �� id
     * @param id ������������� ���������
     * @return ��������� �������� �� �����
     * @throws BusinessException
     */
    public CardOperationCategory findById(Long id) throws BusinessException
    {
	    return simpleService.findById(CardOperationCategory.class, id);
    }

	/**
     * ����� ��������� �� id � ������ �������
     * @param id ������������� ���������
	 * @param loginId - ������������� ������ �������
     * @return ��������� �������� �� �����
     * @throws BusinessException
     */
    public CardOperationCategory findById(final Long id, final Long loginId) throws BusinessException
    {
        DetachedCriteria criteria = DetachedCriteria.forClass(CardOperationCategory.class).
				add(Expression.eq("id",id)).
				add(Expression.eq("ownerId",loginId));
	    return simpleService.findSingle(criteria);
    }

	/**
	 * ����������/���������� ���������
	 * @param category - ���������
	 */
	public void addOrUpdate(CardOperationCategory category) throws BusinessException, ConstraintViolationException
	{
        simpleService.addOrUpdateWithConstraintViolationException(category);
	}

    /**
     * �������� ���������
     * @param category - ���������
     */
    public void delete(final CardOperationCategory category) throws BusinessException, ConstraintViolationException
    {
        try
        {
            HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
            {
                public Void run(Session session)
                {
                    session.delete(category);
                    session.flush();
                    return null;
                }
            });
        }
        catch(ConstraintViolationException e)
        {
            throw e;
        }
        catch (BusinessException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }

	/**
	 * ���������� ������ ��������� ��������, ��������� ������������
	 * @param loginId - ID ������ ������������
	 * @return ������ ��������� ��������
	 */
	public List<CardOperationCategory> getPersonAvailableCategories(final Long loginId) throws BusinessException
	{
		try
		{

			BeanQuery query = MultiLocaleQueryHelper.getQuery(QUERY_PREFIX + "getPersonAvailableCategories");
			query.setParameter("loginId", loginId);
			//noinspection unchecked
			return query.executeListInternal();

		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ���������� ��������� �������� ��������� �������������
	 * @param loginId - ������������� ������ ������������
	 * @return ������ ��������� ��������
	 */
	public Integer getPersonCategoriesSize(final Long loginId) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(CardOperationCategory.class).
				add(Expression.eq("ownerId",loginId)).
				setProjection(Projections.rowCount());
		return simpleService.findSingle(criteria);
	}

	/**
	 * ���������� �� ��������� � ����� ���������
	 * @param loginId - ������������� ������ ������������
	 * @param id - �� ������������� ���������
	 * @param name - �������� ���������
	 * @return true - ����������
	 */
	public boolean isExistCategory(final Long loginId, final Long id, final String name) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(CardOperationCategory.class).
				add(Expression.or(Expression.eq("ownerId",loginId), Expression.isNull("ownerId"))).
				add(Expression.eq("name", name)).
				setProjection(Projections.rowCount());
		if (id != null)
			criteria.add(Expression.ne("id", id));
		Integer size = simpleService.findSingle(criteria);
		return size > 0;
	}

	/**
	 * �������� ��������� �������� ��� ��������� ���������
	 * @param isIncome - �������� ��� ��������� ��������� ���������� ��������
	 * @return ���������
	 * @throws BusinessException
	 */
	public CardOperationCategory getDefaultCategory(boolean isIncome) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(CardOperationCategory.class).
				add(Expression.eq("isDefault", true)).
				add(Expression.eq("income",isIncome));
		return simpleService.findSingle(criteria);
	}

	/**
	 * ���������� ��������� ��� ��������, ������� �������� ���������� ����� ������ �������
	 * @param income - �������� ��� ��������� ��������
	 * @return ��������� ��� ��������, ������� �������� ���������� ����� ������ �������
	 * @throws BusinessException
	 */
	public CardOperationCategory findInternalCategory(boolean income) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(CardOperationCategory.class).
				add(Expression.eq("forInternalOperations", true)).
				add(Expression.eq("income", income));
		return simpleService.findSingle(criteria);
	}

	/**
	 * ���������� ������ ������, ������� ��� ������������ ��� ��������� ������� �������
	 * @param loginId - ����� �������
	 * @return ������ ������
	 * @throws BusinessException
	 */
	public List<String> getUsedColors(final Long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<String>>()
			{
				public List<String> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(QUERY_PREFIX + "getUsedColors");
					query.setLong("loginId", loginId);
					//noinspection unchecked
					return (List<String>)query.list();
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
