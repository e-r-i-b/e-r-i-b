package com.rssl.phizic.business.dictionaries.pfp.risk;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import org.hibernate.criterion.Order;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author akrenev
 * @ created 15.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Сервис работы с риском
 */

public class RiskService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();

	/**
	 * @param instance имя инстанса модели БД
	 * @return список всех возможных рисков
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<Risk> getAll(String instance) throws BusinessException
	{
		return service.getAll(Risk.class, Order.asc("name"), instance);
	}

	/**
	 * @param id идентификатор риска
	 * @param instance имя инстанса модели БД
	 * @return риск
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public Risk getById(final Long id, String instance) throws BusinessException
	{
		return service.findById(Risk.class, id, instance);
	}

	/**
	 * добавить риск
	 * @param risk риск
	 * @param instance имя инстанса модели БД
	 * @return риск
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public Risk addOrUpdate(final Risk risk, String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			return service.addOrUpdate(risk, instance);
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Риск с таким названием уже существует.", e);
		}
	}

	/**
	 * удалить риск
	 * @param risk удаляемый риск
	 * @param instance имя инстанса модели БД
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void remove(final Risk risk, String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			service.remove(risk, instance);
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Невозможно удалить риск.", e);
		}
	}

}
