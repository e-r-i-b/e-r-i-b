package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.CommissionCalculator;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Krenev
 * @ created 20.01.2010
 * @ $Author$
 * @ $Revision$
 */
public class ServiceProviderCommissionCalculator extends AbstractService implements CommissionCalculator
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	public ServiceProviderCommissionCalculator(GateFactory factory)
	{
		super(factory);
	}

	public void setParameters(Map<String, ?> params)
	{
	}

	/*
	Комиссия с платежа клиента рассчитывается следующим образом:
	с суммы платежа клиента рассчитывается комиссия, исходя из процентной ставки;
	если полученное значение меньше минимальной суммы комиссии,
	то в качестве комиссии за платеж клиента берется значение минимальной суммы комиссии,
	если же полученное значение больше значения максимальной суммы комиссии,
	то в качестве комиссии за платеж клиента берется значение максимальной суммы комиссии;
	иначе берется полученное значение, исходя из процентной ставки по комиссии
	*/
	public void calcCommission(GateDocument transfer) throws GateException, GateLogicException
	{
		Class<? extends GateDocument> type = transfer.getType();
		if (type != AccountPaymentSystemPayment.class && type!= CardPaymentSystemPayment.class)
		{
			throw new GateException("Ожидается AccountPaymentSystemPayment или CardPaymentSystemPayment");
		}
		AbstractPaymentSystemPayment payment = (AbstractPaymentSystemPayment) transfer;
		String pointCode = payment.getReceiverPointCode();
		try
		{
			ServiceProviderShort provider = serviceProviderService.findShortProviderBySynchKey(pointCode);
			BigDecimal comissionRate = provider.getComissionRate();
			Money amount = payment.getChargeOffAmount();
			Money commission = amount.multiply(comissionRate.doubleValue() / 100);
			Money minComissionAmount = new Money(provider.getMinComissionAmount(), amount.getCurrency());
			Money maxComissionAmount = new Money(provider.getMaxComissionAmount(), amount.getCurrency());
			if (commission.getDecimal().compareTo(minComissionAmount.getDecimal()) < 0)
			{
				commission = minComissionAmount;
			}
			else if (commission.getDecimal().compareTo(maxComissionAmount.getDecimal()) > 0)
			{
				commission = maxComissionAmount;
			}
			payment.setCommission(commission);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}
}
