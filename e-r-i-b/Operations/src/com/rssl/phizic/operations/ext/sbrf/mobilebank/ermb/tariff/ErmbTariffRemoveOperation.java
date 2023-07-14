package com.rssl.phizic.operations.ext.sbrf.mobilebank.ermb.tariff;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.DisconnectNotAvailibleErmbTariffException;
import com.rssl.phizic.business.ermb.ErmbTariff;
import com.rssl.phizic.business.ermb.ErmbTariffService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Moshenko
 * @ created 11.12.13
 * @ $Author$
 * @ $Revision$
 * Операция просмотра списка тарифов ЕРМБ
 */
public class ErmbTariffRemoveOperation extends OperationBase implements RemoveEntityOperation
{
	private static final ErmbTariffService tariffService = new ErmbTariffService();
	private ErmbTariff tariff;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		tariff = tariffService.findById(id);
	}

	public void remove() throws BusinessException,DisconnectNotAvailibleErmbTariffException, BusinessLogicException
	{
		String code = tariff.getCode();
		//не удаляем тарифы который используются в АС Филиал СЮ
		if ("full".equals(code)||"saving".equals(code))
			throw new BusinessLogicException("Данный тариф является системным его удаление не возможно ");
		tariffService.remove(tariff);
	}

	public Object getEntity()
	{
		return tariff;
	}
}
