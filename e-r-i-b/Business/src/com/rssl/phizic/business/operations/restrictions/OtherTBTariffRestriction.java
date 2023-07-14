package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.ext.sbrf.tariffs.Tariff;
import com.rssl.phizic.gate.commission.TransferType;
import com.rssl.phizic.business.BusinessException;

/**
 * Рестрикшн проверящий является ли тариф на перевод в другой ТБ
 * @author niculichev
 * @ created 19.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class OtherTBTariffRestriction implements TariffRestriction
{
	public boolean accept(Tariff tariff) throws BusinessException
	{
		return tariff.getTransferType() == TransferType.OTHER_TB_OWN_ACCOUNT
				|| tariff.getTransferType() == TransferType.OTHER_TB_ANOTHER_ACCOUNT;
	}
}
