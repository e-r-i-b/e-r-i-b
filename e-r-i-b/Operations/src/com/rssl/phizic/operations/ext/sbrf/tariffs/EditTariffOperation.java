package com.rssl.phizic.operations.ext.sbrf.tariffs;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.ext.sbrf.tariffs.Tariff;
import com.rssl.phizic.business.ext.sbrf.tariffs.TariffService;
import com.rssl.phizic.business.operations.restrictions.TariffRestriction;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * Операция редатирования тарифов
 * @author niculichev
 * @ created 18.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditTariffOperation extends OperationBase<TariffRestriction> implements EditEntityOperation<Tariff, TariffRestriction>
{
	private static final TariffService tariffService = new TariffService();
	private Tariff tariff;
	private boolean isNew;

	public void initialize(Long id) throws BusinessException
	{
		tariff = tariffService.findById(id);

		if(tariff == null)
			throw new ResourceNotFoundBusinessException("Тариф с идентификатором " + id + "не найден в системе", Tariff.class);

		if(!getRestriction().accept(tariff))
			throw new BusinessException("Невозможно отредактировать тариф с идентификатором " + id + " данной операцией");

		isNew = false;
	}

	public void initializeNew()
	{
		tariff = new Tariff();
		isNew = true;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		if(isNew && tariffService.findByTypeAndCur(tariff.getCurrencyCode(), tariff.getTransferType()) != null)
			throw new BusinessLogicException("Для выбранной валюты уже существует тариф. Пожалуйста, отредактируйте нужный тариф.");

		tariffService.addOrUpdate(tariff);
	}

	public Tariff getEntity() throws BusinessException, BusinessLogicException
	{
		return tariff;  
	}
}
