package com.rssl.phizic.business.dictionaries.pfp.products.insurance;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author akrenev
 * @ created 03.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * Сервис работы со страхоывми компаниями
 */

public class InsuranceCompanyService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();

	/**
	 * @param id идентификатор страховой компании
	 * @param instance имя инстанса модели БД
	 * @return страховая компания
	 * @throws BusinessException
	 */
	public InsuranceCompany getById(Long id, String instance) throws BusinessException
	{
		return service.findById(InsuranceCompany.class, id, instance);
	}

	/**
	 * сохранить в БД страховую компанию
	 * @param company страховая компания
	 * @param instance имя инстанса модели БД
	 * @return страховая компания
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public InsuranceCompany addOrUpdate(InsuranceCompany company, String instance) throws BusinessException, BusinessLogicException
	{
		return service.addOrUpdate(company, instance);
	}

	/**
	 * удаление страховой компании
	 * @param company страховая компания
	 * @param instance имя инстанса модели БД
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void remove(InsuranceCompany company, String instance) throws BusinessException, BusinessLogicException
	{
		try
		{
			service.removeWithImage(company, instance);
		}
		catch (ConstraintViolationException cve)
		{
			throw new BusinessLogicException("Невозможно удалить страховую компанию.", cve);
		}
	}
}