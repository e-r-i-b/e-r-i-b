package com.rssl.phizic.business.dictionaries.pfp.period;

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
 * сервис работы с сущностью "горизонт инвестирования"
 */

public class InvestmentPeriodService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();

	/**
	 * @param instance имя инстанса модели БД
	 * @return список всех "горизонтов"
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<InvestmentPeriod> getAll(String instance) throws BusinessException
	{
		return service.getAll(InvestmentPeriod.class, Order.asc("period"), instance);
	}

	/**
	 * @param id идентификатор "горизонта"
	 * @param instance имя инстанса модели БД
	 * @return "горизонт инвестирования"
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public InvestmentPeriod getById(Long id, String instance) throws BusinessException
	{
		return service.findById(InvestmentPeriod.class, id, instance);
	}

	/**
	 * добавить "горизонт инвестирования"
	 * @param investmentPeriod "горизонт инвестирования"
	 * @param instance имя инстанса модели БД
	 * @return "горизонт инвестирования"
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public InvestmentPeriod addOrUpdate(InvestmentPeriod investmentPeriod, String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			return service.addOrUpdate(investmentPeriod, instance);
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Введенный горизонт инвестирования уже существует. Пожалуйста, укажите другой горизонт инвестирования", e);
		}
	}

	/**
	 * удалить "горизонт инвестирования"
	 * @param investmentPeriod удаляемый "горизонт инвестирования"
	 * @param instance имя инстанса модели БД
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void remove(InvestmentPeriod investmentPeriod, String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			service.remove(investmentPeriod, instance);
		}
		catch (ConstraintViolationException e)
		{
			throw new BusinessLogicException("Невозможно удалить гризонт инвестирования.", e);
		}
	}
}
