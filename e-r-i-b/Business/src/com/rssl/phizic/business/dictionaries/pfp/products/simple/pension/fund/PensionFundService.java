package com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.fund;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;

import java.util.List;

/**
 * @author akrenev
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Сервис работы с пенсионным фондом
 */

public class PensionFundService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();

	/**
	 * получить по id
	 * @param id идентификатор
	 * @param instance имя инстанса модели БД
	 * @return пенсионный фонд
	 * @throws BusinessException
	 */
	public PensionFund findById(Long id, String instance) throws BusinessException
	{
		return service.findById(PensionFund.class, id, instance);
	}

	/**
	 * @param instance имя инстанса модели БД
	 * @return список всех пенсионных фондов
	 * @throws BusinessException
	 */
	public List<PensionFund> getAll(String instance) throws BusinessException
	{
		return service.getAll(PensionFund.class, instance);
	}

	/**
	 * добавить или обновить
	 * @param fund пенсионный фонд
	 * @param instance имя инстанса модели БД
	 * @return сохраненный пенсионный фонд
	 * @throws BusinessException
	 */
	public PensionFund addOrUpdate(PensionFund fund, String instance) throws BusinessException
	{
		return service.addOrUpdate(fund, instance);
	}

	/**
	 * удалить
	 * @param fund пенсионный фонд
	 * @param instance имя инстанса модели БД
	 * @throws BusinessException
	 */
	public void remove(PensionFund fund, String instance) throws BusinessException
	{
		service.remove(fund, instance);
	}
}
