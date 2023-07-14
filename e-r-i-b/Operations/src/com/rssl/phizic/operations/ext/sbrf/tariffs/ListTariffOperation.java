package com.rssl.phizic.operations.ext.sbrf.tariffs;

import com.rssl.phizic.business.ext.sbrf.tariffs.Tariff;
import com.rssl.phizic.business.ext.sbrf.tariffs.TariffService;
import com.rssl.phizic.gate.commission.TransferType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ListEntitiesOperation;

import java.util.List;

/**
 * Операция получения списка тарифов
 * @author niculichev
 * @ created 16.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListTariffOperation extends OperationBase implements ListEntitiesOperation
{
	private static final TariffService tariffService = new TariffService();

	public List<Tariff> getTransOtherTBOnOwnAccount() throws BusinessException
	{
		 return tariffService.getTariffs(TransferType.OTHER_TB_OWN_ACCOUNT);
	}

	public List<Tariff> getTransOtherTBOnAnotherAccount() throws BusinessException
	{
		return tariffService.getTariffs(TransferType.OTHER_TB_ANOTHER_ACCOUNT);
	}

	public List<Tariff> getTransOtherBank() throws BusinessException
	{
		return tariffService.getTariffs(TransferType.OTHER_BANK);
	}

}
