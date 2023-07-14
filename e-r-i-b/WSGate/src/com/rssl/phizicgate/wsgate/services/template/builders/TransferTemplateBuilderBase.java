package com.rssl.phizicgate.wsgate.services.template.builders;

import com.rssl.phizic.business.documents.templates.impl.TransferTemplateBase;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.wsgate.services.template.generated.GateTemplate;

/**
 * Билдер шаблонов документов (поля по оплате)
 *
 * @author khudyakov
 * @ created 24.11.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class TransferTemplateBuilderBase<T extends TransferTemplateBase> extends TemplateBuilderBase<T>
{
	@Override
	protected void doBuild(T template, GateTemplate generated) throws GateException, GateLogicException
	{
		setBaseData(template, generated);

		setActivityInfo(template);
	}

	@Override
	protected void setBaseData(T template, GateTemplate generated) throws GateException, GateLogicException
	{
		super.setBaseData(template, generated);

		//счет/карта списания
		template.setChargeOffResource(getChargeOffResource(generated));

		//счет карта зачисления
		template.setDestinationResource(getDestinationResource(generated));

		//сумма в валюте счета списания
		template.setChargeOffAmount(toGateMoney(generated.getChargeOffAmount()));

		//сумма в валюте счета зачисления
		template.setDestinationAmount(toGateMoney(generated.getDestinationAmount()));

		//тип суммы
		String inputSumType = generated.getInputSumType();
		if (StringHelper.isNotEmpty(inputSumType))
		{
			template.setInputSumType(inputSumType);
		}

		//назначение платежа
		template.setGround(generated.getGround());
	}

	protected String getChargeOffResource(GateTemplate generated)
	{
		if (StringHelper.isNotEmpty(generated.getChargeOffAccount()))
		{
			return generated.getChargeOffAccount();
		}
		return generated.getChargeOffCard();
	}

	protected String getDestinationResource(GateTemplate generated)
	{
		if (StringHelper.isNotEmpty(generated.getReceiverAccount()))
		{
			return generated.getReceiverAccount();
		}
		return generated.getReceiverCard();
	}

	protected Money toGateMoney(com.rssl.phizicgate.wsgate.services.template.generated.Money generated) throws GateException
	{
		if (generated == null)
		{
			return null;
		}

		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		return new Money(generated.getDecimal(), currencyService.findByAlphabeticCode(generated.getCurrency().getCode()));
	}
}
