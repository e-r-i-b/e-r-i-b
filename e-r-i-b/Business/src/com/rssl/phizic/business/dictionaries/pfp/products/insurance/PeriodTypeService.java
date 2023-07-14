package com.rssl.phizic.business.dictionaries.pfp.products.insurance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author akrenev
 * @ created 16.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class PeriodTypeService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();

	/**
	 * @param id идентификатор типа периода
	 * @param instance имя инстанса модели БД
	 * @return тип периода
	 * @throws BusinessException
	 */
	public PeriodType getById(final Long id, String instance) throws BusinessException
	{
		return service.findById(PeriodType.class, id, instance);
	}

	/**
	 * @param ids идентификаторы
	 * @param instance имя инстанса модели БД
	 * @return список типов периодов по идентификаторам
	 * @throws BusinessException
	 */
	public List<PeriodType> getListByIds(Long[] ids, String instance) throws BusinessException
	{
		return service.getListByIds(PeriodType.class, ids, instance);
	}

	/**
	 * добавито тип периода
	 * @param type  тип периода
	 * @param instance имя инстанса модели БД
	 * @return тип периода
	 * @throws BusinessException
	 */
	public PeriodType addOrUpdate(final PeriodType type, String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			return service.addOrUpdate(type, instance);
		}
		catch (ConstraintViolationException cve)
		{
			throw new BusinessLogicException("Тип периода с таким названием уже существует. Пожалуйста, введите другое название.", cve);
		}
	}

	/**
	 * удалить тип периода
	 * @param type удаляемый тип периода
	 * @param instance имя инстанса модели БД
	 * @throws BusinessException
	 */
	public void remove(final PeriodType type, String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			service.remove(type, instance);
		}
		catch (ConstraintViolationException cve)
		{
			throw new BusinessLogicException("Невозможно удалить тип периода.", cve);
		}
	}
}
