package com.rssl.phizic.operations.ext.sbrf.mobilebank.ermb.tariff;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbTariff;
import com.rssl.phizic.business.ermb.ErmbTariffService;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;


/**
 * @author Moshenko
 * @ created 09.12.13
 * @ $Author$
 * @ $Revision$
 * Операция редактирования тарифов ЕРМБ
 */
public class ErmbTariffEditOperation extends OperationBase implements EditEntityOperation
{
	private static final ErmbTariffService tariffService = new ErmbTariffService();
	private static Currency currency ;

	private ErmbTariff tariff;

	public void initialize(Long tariffId) throws BusinessException
	{
		tariff = tariffService.findById(tariffId);
		try
		{
			currency =  GateSingleton.getFactory().service(CurrencyService.class).getNationalCurrency();
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	public void initializeNew() throws BusinessException
	{
		tariff = new ErmbTariff();
		try
		{
			currency =  GateSingleton.getFactory().service(CurrencyService.class).getNationalCurrency();
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		tariffService.addOrUpdate(tariff);
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return tariff;
	}

	public Currency getCurrency()
	{
		return currency;
	}
}
