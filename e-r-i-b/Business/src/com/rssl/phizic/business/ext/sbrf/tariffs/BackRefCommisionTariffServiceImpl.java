package com.rssl.phizic.business.ext.sbrf.tariffs;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.commission.TransferType;
import com.rssl.phizic.gate.commission.BackRefCommissionTariffService;
import com.rssl.phizic.gate.commission.CommissionTariff;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.impl.AbstractService;

/**
 * Реализация обратного сервиса для получения тарифа комиссии
 * @author niculichev
 * @ created 23.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class BackRefCommisionTariffServiceImpl extends AbstractService implements BackRefCommissionTariffService
{
	private static final TariffService tariffService = new TariffService();

	public BackRefCommisionTariffServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public CommissionTariff getTariff(String currencyCode, TransferType transferType) throws GateException
	{
		try
		{
			return tariffService.findByTypeAndCur(currencyCode, transferType);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}
}
