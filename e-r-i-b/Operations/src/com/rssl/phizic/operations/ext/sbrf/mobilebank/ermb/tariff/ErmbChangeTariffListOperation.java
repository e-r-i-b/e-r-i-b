package com.rssl.phizic.operations.ext.sbrf.mobilebank.ermb.tariff;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbTariff;
import com.rssl.phizic.business.ermb.ErmbTariffService;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Moshenko
 * @ created 09.12.13
 * @ $Author$
 * @ $Revision$
 * Операция смены тарифа ЕРМБ у пользователей при удалении тарифа
 */
public class ErmbChangeTariffListOperation   extends OperationBase implements ListEntitiesOperation
{
	private static final ErmbTariffService tariffService = new ErmbTariffService();

	private ErmbTariff changeTariff;//тариф с которого отключаем

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		changeTariff = tariffService.findById(id);
	}

	/**
	 * @param fromTariffId Тариф id с которого меняем.
	 * @param toTariffId Тариф id на который меняем.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void changeTariff(Long fromTariffId,Long toTariffId) throws BusinessException, BusinessLogicException
	{
		tariffService.changeTariff(fromTariffId,toTariffId);
		changeTariff  = tariffService.findById(fromTariffId);
		tariffService.remove(changeTariff);
	}

	public ErmbTariff getChangeTariff()
	{
		return changeTariff;
	}
}
