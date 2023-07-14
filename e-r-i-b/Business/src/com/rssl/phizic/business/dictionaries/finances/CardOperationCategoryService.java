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
 * —ервис дл€ работы с категори€ми операций по картам
 */
public class CardOperationCategoryService
{
	protected static final String QUERY_PREFIX = "com.rssl.phizic.business.dictionaries.finances.";
	private static final SimpleService simpleService = new SimpleService();

    /**
     * ѕоиск категории по id
     * @param id »дентификатор категории
     * @return  атегори€ операций по карте
     * @throws BusinessException
     */
    public CardOperationCategory findById(Long id) throws BusinessException
    {
	    return simpleService.findById(CardOperationCategory.class, id);
    }

	/**
     * ѕоиск категории по id и логину клиента
     * @param id идентификатор категории
	 * @param loginId - идентификатор логина клиента
     * @return  атегори€ операций по карте
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
	 * ƒобавление/сохранение категории
	 * @param category - категори€
	 */
	public void addOrUpdate(CardOperationCategory category) throws BusinessException, ConstraintViolationException
	{
        simpleService.addOrUpdateWithConstraintViolationException(category);
	}

    /**
     * ”даление категории
     * @param category - категори€
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
	 * ¬озвращает список категорий операций, доступных пользователю
	 * @param loginId - ID логина пользовател€
	 * @return список категорий операций
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
	 * ¬озвращает количество категорий операций созданных пользователем
	 * @param loginId - идентификатор логина пользовател€
	 * @return список категорий операций
	 */
	public Integer getPersonCategoriesSize(final Long loginId) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(CardOperationCategory.class).
				add(Expression.eq("ownerId",loginId)).
				setProjection(Projections.rowCount());
		return simpleService.findSingle(criteria);
	}

	/**
	 * —уществует ли категори€ с таким названием
	 * @param loginId - идентификатор логина пользовател€
	 * @param id - ид редактируемой категории
	 * @param name - название категории
	 * @return true - существует
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
	 * ѕолучить дефолтную доходную или расходную категорию
	 * @param isIncome - доходную или расходную категорию необходимо получить
	 * @return категори€
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
	 * ¬озвращает категорию дл€ операций, которые €вл€ютс€ переводами между своими картами
	 * @param income - доходна€ или расходна€ ктегори€
	 * @return категори€ дл€ операций, которые €вл€ютс€ переводами между своими картами
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
	 * ¬озвращает список цветов, которые уже используютс€ дл€ категорий данного клиента
	 * @param loginId - логин клиента
	 * @return список цветов
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
