package com.rssl.phizic.business.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.type.LongType;

import java.util.*;

/**
 * @author akrenev
 * @ created 10.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class RiskProfileService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();

	/**
	 * @return новая сущность профиля
	 * @throws BusinessException
	 */
	public RiskProfile newRiskProfile() throws BusinessException
	{
		RiskProfile profile = new RiskProfile();
		profile.setSegment(SegmentCodeType.NOTEXISTS);
		profile.setProductsWeights(RiskProfileUtil.getNewProductWeightsMap());
		return profile;
	}

	/**
	 * найти профиль
	 * @param id идентификатор профиля
	 * @param instance имя инстанса модели БД
	 * @return профиль
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public RiskProfile getById(final Long id, String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(RiskProfile.class);
		criteria.add(Expression.and(Expression.eq("id", id), Expression.eq("deleted", false)));
		return service.findSingle(criteria, instance);
	}

	private void markDeleted(Session session, final RiskProfile profile)
	{
		Query query = session.getNamedQuery(RiskProfile.class.getName() + ".markDeleted");
		query.setLong("profileId", profile.getId());
		query.executeUpdate();
	}

	private boolean isMutable(Session session, final RiskProfile profile)
	{
		Query query = session.getNamedQuery(RiskProfile.class.getName() + ".isMutable");
		query.setLong("profileId", profile.getId());
		return (Boolean) query.uniqueResult();
	}

	/**
	 * сохранить профиль
	 * @param profile сохраняемый профиль
	 * @param instance имя инстанса модели БД
	 * @return профиль
	 * @throws BusinessException
	 */
	public RiskProfile addOrUpdate(final RiskProfile profile, final String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<RiskProfile>()
			{
				public RiskProfile run(Session session) throws Exception
				{
					RiskProfile savingRiskProfile = profile;
					//если вопрос привязан к кому нибудь, то делаем копию, а старый помечаем удаленным
					if (savingRiskProfile.getId() != null && MultiBlockModeDictionaryHelper.needExternalCheck() && !isMutable(session, savingRiskProfile))
					{
						savingRiskProfile = new RiskProfile(profile);
						markDeleted(session, profile);
					}
					service.addOrUpdate(savingRiskProfile, instance);
					return savingRiskProfile;
				}
			}
			);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Невозможно добавить риск-профиль.", e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * сохранить профиль
	 * @param profile сохраняемый профиль
	 * @return профиль
	 * @throws BusinessException
	 */
	public RiskProfile addOrUpdate(final RiskProfile profile) throws BusinessException, BusinessLogicException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<RiskProfile>()
			{
				public RiskProfile run(Session session) throws Exception
				{
					RiskProfile savingRiskProfile = profile;
					//если вопрос привязан к кому нибудь, то делаем копию, а старый помечаем удаленным
					if (savingRiskProfile.getId() != null && !isMutable(session, savingRiskProfile))
					{
						savingRiskProfile = new RiskProfile(profile);
						markDeleted(session, profile);
					}
					service.addOrUpdate(savingRiskProfile);
					return savingRiskProfile;
				}
			}
			);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Невозможно добавить риск-профиль.", e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * удалить профиль
	 * @param profile удаляемый профиль
	 * @param instance имя инстанса модели БД
	 * @throws BusinessException
	 */
	public void remove(final RiskProfile profile, final String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance(instance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					if (!MultiBlockModeDictionaryHelper.needExternalCheck() || isMutable(session, profile))
						service.remove(profile, instance);
					else
						markDeleted(session, profile);

					return null;
				}
			}
			);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Невозможно удалить вопрос.", e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * удалить профиль
	 * @param profile удаляемый профиль
	 * @throws BusinessException
	 */
	public void remove(final RiskProfile profile) throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					if (isMutable(session, profile))
						service.remove(profile);
					else
						markDeleted(session, profile);

					return null;
				}
			}
			);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Невозможно удалить вопрос.", e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Поиск риск профиля по весу ответов
	 * @param weight - вес
	 * @param segment сегмент, для которого добавляется риск-профиль
	 * @return риск профиль
	 * @throws BusinessException
	 * @throws BusinessLogicException - не удалось найти риск профиль для заданного веса
	 */
	public RiskProfile findRiskProfileForWeight(Integer weight, SegmentCodeType segment) throws BusinessException, BusinessLogicException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(RiskProfile.class);
		//TODO переделать вес в риск профиле на Integer
		Long weightLong = Long.valueOf(weight);
		criteria.add(Expression.eq("deleted", false));
		criteria.add(Expression.eq("segment", segment));
		criteria.add(Expression.or(
			Expression.isNull("maxWeight"),
			Expression.ge("maxWeight",weightLong))
		);
		criteria.add(Expression.or(
			Expression.isNull("minWeight"),
			Expression.le("minWeight",weightLong))
		);
		List<RiskProfile> riskList = service.find(criteria);
		if(CollectionUtils.isEmpty(riskList))
		{
			throw new BusinessException("Не удалось найти риск профиль для данных ответов. Риск профиль не будет присвоен клиенту. " +
					"Проверьте настройку справочника риск профилей для ПФП. Сумма ответов = " + weight);
		}
		if(riskList.size() > 1)
		{
			throw new BusinessException("Для заданных ответов нашлось более одного риск профиля. Риск профиль не будет присвоен клиенту." +
						"Проверьте настройку справочника риск профилей для ПФП. Сумма ответов = " + weight);
		}
		return riskList.get(0);
	}

	/**
	 * Возвращает список риск-профилей
	 * @return список
	 * @throws BusinessException
	 */
	public List<RiskProfile> getAllRiskProfiles() throws BusinessException
	{
		return service.getAll(RiskProfile.class);
	}

	/**
	 * @param id идентификатор текущего риск-профиля
	 * @param minWeight минимальный вес текущего риск профиля
	 * @param maxWeight максимальный вес текущего риск профиля
	 * @param segment сегмент, для которого добавляется риск-профиль
	 * @param instance имя инстанса модели БД
	 * @return есть ли пересечения в интервалах
	 */
	public boolean isExistCrossingRiskProfiles(final Long id, final Long minWeight, final Long maxWeight, final SegmentCodeType segment, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session)
				{
					Query query = session.getNamedQuery(RiskProfile.class.getName() + ".isExistCrossing");
					query.setParameter("id", id, new LongType());
					query.setParameter("segment",   segment.name());
					query.setParameter("minWeight", minWeight);
					query.setParameter("maxWeight", maxWeight, new LongType());
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
	 * @param segment сегмент для которого производится проверка
	 * @param instance имя инстанса модели БД
	 * @return есть ли дыры в интервалах
	 */
	public boolean isConcertedRiskProfiles(final SegmentCodeType segment, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session)
				{
					Query query = session.getNamedQuery(RiskProfile.class.getName() + ".isConcerted");
					query.setParameter("segment", segment.name());
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

	private List<String> getStringListFromEnums(List<? extends Enum> enums)
	{
		List<String> segments = new ArrayList<String>();
		for (Enum enumElement : enums)
			segments.add(enumElement.name());

		return segments;
	}

	private Boolean isExistProductWeight(Session session, ProductType type, List<SegmentCodeType> excludedTargetGroup)
	{
		String queryName = ".isExistProductWeight";
		if (CollectionUtils.isEmpty(excludedTargetGroup))
			queryName = ".isExistProductWeightForAllSegments";
		Query query = session.getNamedQuery(RiskProfile.class.getName() + queryName);
		query.setParameter("type", type.name());
		if (CollectionUtils.isNotEmpty(excludedTargetGroup))
			query.setParameterList("excludedSegments", getStringListFromEnums(excludedTargetGroup));
		return (Boolean) query.uniqueResult();
	}

	/**
	 * существуют ли риск-профили для всех сегментов, исключая заданные, с распределением веса (отличным от 0) для заданного типа продукта
	 * @param productTypesMap отношение типов продуктов к сегментам в которых не нужно искать
	 * @param instance имя инстанса модели БД
	 * @return true -- существуют риск-профили с заданным распределения веса
	 */
	public boolean isExistProductWeight(final Map<ProductType, List<SegmentCodeType>> productTypesMap, String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session)
				{
					for (Map.Entry<ProductType, List<SegmentCodeType>> entry : productTypesMap.entrySet())
					{
						if (isExistProductWeight(session, entry.getKey(), entry.getValue()))
							return true;
					}
					return false;
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private List<RiskProfile> getRiskProfilesForModification(Session session, ProductType type, List<SegmentCodeType> targetGroup)
	{
		Query query;
		if (CollectionUtils.isNotEmpty(targetGroup))
		{
			query = session.getNamedQuery(RiskProfile.class.getName() + ".getRiskProfilesForModification");
			query.setParameterList("segment", getStringListFromEnums(targetGroup));
		}
		else
		{
			query = session.getNamedQuery(RiskProfile.class.getName() + ".getRiskProfilesForDeleting");
		}
		query.setParameter("type", type.name());
		return query.list();
	}

	private List<RiskProfile> getRiskProfilesForModification(Session session, Map<ProductType, List<SegmentCodeType>> productTypesMap)
	{
		Set<RiskProfile> riskProfiles = new HashSet<RiskProfile>();
		for (Map.Entry<ProductType, List<SegmentCodeType>> entry : productTypesMap.entrySet())
			riskProfiles.addAll(getRiskProfilesForModification(session, entry.getKey(), entry.getValue()));
		return new ArrayList<RiskProfile>(riskProfiles);
	}



	/**
	 * Удаление распределений веса (равного 0) заданных типы продуктов для всех сегментов, кроме newTargetGroup
	 * +
	 * Добавление распределений веса (равного 0) заданного продукта для сегментов newTargetGroup
	 * @param productTypesMap отношение типов продуктов к сегментам в которых не нужно искать
	 * @param instance имя инстанса модели БД
	 */
	public void updateProductWeight(final Map<ProductType, List<SegmentCodeType>> productTypesMap, String instance) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instance).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					List<RiskProfile> changedRiskProfiles = getRiskProfilesForModification(session, productTypesMap);

					for (RiskProfile currentRiskProfile : changedRiskProfiles)
					{
						RiskProfile changedRiskProfile = currentRiskProfile;
						if (!isMutable(session, changedRiskProfile))
						{
							currentRiskProfile.setDeleted(true);
							changedRiskProfile = new RiskProfile(currentRiskProfile);
							session.saveOrUpdate(currentRiskProfile);
						}
						Map<ProductType, Long> weights = changedRiskProfile.getProductsWeights();
						for (Map.Entry<ProductType, List<SegmentCodeType>> entry : productTypesMap.entrySet())
						{
							if (entry.getValue().contains(changedRiskProfile.getSegment()))
							{
								Long oldValue = weights.get(entry.getKey());
								if (oldValue == null)
									weights.put(entry.getKey(), NumberUtils.LONG_ZERO);
							}
							else
								weights.remove(entry.getKey());
						}
						session.saveOrUpdate(changedRiskProfile);
					}
					return null;
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
