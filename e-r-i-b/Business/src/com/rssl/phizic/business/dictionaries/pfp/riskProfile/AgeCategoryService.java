package com.rssl.phizic.business.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.type.LongType;

import java.util.List;

/**
 * @author akrenev
 * @ created 30.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class AgeCategoryService
{
	private static final PFPDictionaryServiceBase simpleService = new PFPDictionaryServiceBase();

	/**
	 * @param id идентификатор возрастной категории
	 * @param instance им€ инстанса модели Ѕƒ
	 * @return возрастна€ категори€
	 * @throws BusinessException
	 */
	public AgeCategory getById(Long id, String instance) throws BusinessException
	{
		return simpleService.findById(AgeCategory.class, id, instance);
	}

	/**
	 * сохранить возрастную категорию
	 * @param category сохран€ема€ возрастна€ категори€
	 * @param instance им€ инстанса модели Ѕƒ
	 * @return возрастна€ категори€
	 * @throws BusinessException
	 */
	public AgeCategory addOrUpdate(AgeCategory category, String instance) throws BusinessException
	{
		return simpleService.addOrUpdate(category, instance);
	}

	/**
	 * удалить возрастную категорию
	 * @param category удал€ема€ возрастна€ категори€
	 * @param instance им€ инстанса модели Ѕƒ
	 * @throws BusinessException
	 */
	public void remove(AgeCategory category, String instance) throws BusinessException
	{
		simpleService.remove(category, instance);
	}

	/**
	 * @param age возраст
	 * @return вес возраста
	 * @throws BusinessException
	 */
	public Integer getWeightByAge(Integer age) throws BusinessException
	{
		//TODO переделать вес в настройке на Integer
		Long lAge = Long.valueOf(age);
		DetachedCriteria criteria = DetachedCriteria.forClass(AgeCategory.class).add(Expression.and(Expression.or(Expression.ge("maxAge", lAge), Expression.isNull("maxAge")), Expression.le("minAge", lAge)));
		List<AgeCategory> ageCategories = simpleService.find(criteria);
		if (CollectionUtils.isEmpty(ageCategories) || ageCategories.size() > 1)
		{
			throw new BusinessException(" орректировка риск профил€ клиента в зависимости от возраста не будет произведена. " +
					"«аданный возраст клиента не попадает ни под одну возрастную категорию(либо попадает в несколько) в справочнике возрастных групп дл€ ѕ‘ѕ. ѕроверьте справочник дл€ возраста = " + age);
		}
		//TODO переделать вес в настройке на Integer
		return ageCategories.get(0).getWeight().intValue();
	}

	/**
	 * @param id идентификатор текущей возрастной категории
	 * @param minAge минимальный возраст текущей возрастной категории
	 * @param maxAge максимальный возраст текущей возрастной категории
	 * @param instance им€ инстанса модели Ѕƒ
	 * @return есть ли пересечени€ в интервалах
	 */
	public boolean isExistCrossingAgeCategories(final Long id, final Long minAge, final Long maxAge, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session)
				{
					Query query = session.getNamedQuery(AgeCategory.class.getName() + ".isExistCrossing");
					query.setParameter("id", id, new LongType());
					query.setParameter("minAge", minAge);
					query.setParameter("maxAge", maxAge, new LongType());
					return (Boolean) query.uniqueResult();
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param instance им€ инстанса модели Ѕƒ
	 * @return есть ли дыры в интервалах
	 */
	public boolean isConcertedAgeCategories(String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session)
				{
					Query query = session.getNamedQuery(AgeCategory.class.getName() + ".isConcerted");
					return (Boolean) query.uniqueResult();
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
