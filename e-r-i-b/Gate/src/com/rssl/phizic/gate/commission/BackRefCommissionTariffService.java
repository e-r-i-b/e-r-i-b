package com.rssl.phizic.gate.commission;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Обратный сервис для получения тарифа коммисии
 * @author niculichev
 * @ created 23.04.2012
 * @ $Author$
 * @ $Revision$
 */
public interface BackRefCommissionTariffService extends Service
{
	/**
	 * Получения тарифа по коду валюты и типу перевода
	 * @param currencyCode код валюты
	 * @param transferType тип перевода
	 * @return тариф комиссии
	 * @throws GateException
	 */
	CommissionTariff getTariff(String currencyCode, TransferType transferType) throws GateException;
}
