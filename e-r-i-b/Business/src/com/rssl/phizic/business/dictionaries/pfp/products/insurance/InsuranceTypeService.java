package com.rssl.phizic.business.dictionaries.pfp.products.insurance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import com.rssl.phizic.business.dictionaries.pfp.configure.PFPConfigHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.apache.commons.lang.BooleanUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * @author akrenev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class InsuranceTypeService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();
	private static final String MAX_INSURANCE_TYPES_KEY = "insurance.types.max";

	/**
	 * @param id идентификатор типа страховки
	 * @return тип периода
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public InsuranceType getById(final Long id) throws BusinessException
	{
		return getById(id, null);
	}

	/**
	 * @param id идентификатор типа страховки
	 * @param instance имя инстанса модели БД
	 * @return тип периода
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public InsuranceType getById(final Long id, String instance) throws BusinessException
	{
		return service.findById(InsuranceType.class, id, instance);
	}

	/**
	 * добавить тип страховки
	 * @param type  тип страховки
	 * @param instance имя инстанса модели БД
	 * @return тип периода
	 * @throws BusinessException
	 */
	public InsuranceType addOrUpdate(final InsuranceType type, String instance) throws BusinessException
	{
		return service.addOrUpdate(type, instance);
	}

	/**
	 * удалить тип страховки
	 * @param type удаляемый тип страховки
	 * @param instance имя инстанса модели БД
	 * @throws BusinessException
	 */
	public void remove(final InsuranceType type, final String instance) throws BusinessException
	{
		service.removeWithImage(type, instance);
	}

	/**
	 * @param type тип
	 * @param instance имя инстанса модели БД
	 * @return имеет ли тип дочерние типы
	 * @throws BusinessException
	 */
	public boolean hasChild(InsuranceType type, String instance) throws BusinessException
	{
		if (type == null)
			return false;

		return hasChild(type.getId(), instance);
	}


	/**
	 * @param insuranceTypeId идентификатор типа продукта
	 * @param instance имя инстанса модели БД
	 * @return имеет ли тип дочерние типы
	 * @throws BusinessException
	 */
	public boolean hasChild(final Long insuranceTypeId, String instance) throws BusinessException
	{
		if(insuranceTypeId == null)
			return false;
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session)
				{
					Query query = session.getNamedQuery(InsuranceType.class.getName() + ".hasChild");
					query.setParameter("typeId", insuranceTypeId);
					return BooleanUtils.toBooleanObject((Integer) query.uniqueResult());
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
	 * @param instance имя инстанса модели БД
	 * @return список всех топов страховых продуктов
	 * @throws BusinessException
	 */
	public List<InsuranceType> getAll(String instance) throws BusinessException
	{
		return service.getAll(InsuranceType.class, instance);
	}

	private Long getImageId(final Class clazz, final Long id) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Long>()
			{
				public Long run(Session session)
				{
					Query query = session.getNamedQuery(clazz.getName() + ".getImageId");
					query.setParameter("id", id);
					return (Long) query.uniqueResult();
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
	 * @param productId идентификатор
	 * @return идентификатор картинки для стахового продукта
	 */
	public Long getInsuranceProductImageId(Long productId) throws BusinessException
	{
		return getImageId(InsuranceProduct.class, productId);
	}

	/**
	 * @param typeId идентификатор
	 * @return идентификатор картинки для типа/вида стахового продукта
	 */
	public Long getInsuranceTypeImageId(Long typeId) throws BusinessException
	{
		return getImageId(InsuranceType.class, typeId);
	}

	/**
	 * @param instance имя инстанса модели БД
	 * @return количество типов периода
	 * @throws BusinessException
	 */
	public Long getCountInsuranceType(String instance) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instance).execute(new HibernateAction<Long>()
			{
				public Long run(Session session)
				{
					Query query = session.getNamedQuery(InsuranceType.class.getName() + ".countInsuranceType");
					return (Long) query.uniqueResult();
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
	 * Получить максимально допустимое количество типов страховых продуктов из ДБ или pfpDictionary.properties
	 * @return количество типов страховых продуктов
	 */
	public Long getMaxInsuranceTypes() throws BusinessException
	{
		try
		{
			String propertyValue = ConfigFactory.getConfig(PFPConfigHelper.class).getValue(MAX_INSURANCE_TYPES_KEY);
			return Long.valueOf(propertyValue);
		}
		catch (Exception e)
		{
			throw new BusinessException("Невозможно получить данные о максимальном колчиестве типов страховых продуктов.", e);
		}
	}
}
