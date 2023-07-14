package com.rssl.phizicgate.esberibgate.documents.commissionCalculators.PaymentSystemPayment;

import com.rssl.phizgate.common.payments.AbstractCommissionCalculator;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.BackRefReceiverInfoService;
import com.rssl.phizic.gate.payments.systems.recipients.BusinessRecipientInfo;
import com.rssl.phizic.utils.DateHelper;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author akrenev
 * @ created 16.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * калькулятор комиссии для платежа "Оплата товаров и услуг с карты."
 */

public class CardPaymentSystemPaymentCommissionCalculator extends AbstractCommissionCalculator
{
	private static final Pattern PATTERN = Pattern.compile("([^@]*)@(.*)");
	private static final int SERVICE_CODE_GROUP_INDEX = 1;
	private static final int POINT_CODE_GROUP_INDEX = 2;

	public void calcCommission(GateDocument transfer) throws GateException, GateLogicException
	{
		if (!(transfer instanceof AbstractPaymentSystemPayment))
			throw new GateException("Неверный тип платежа, ожидался наследник AbstractPaymentSystemPayment");

		AbstractPaymentSystemPayment payment = (AbstractPaymentSystemPayment) transfer;

		Calendar previousDay = DateHelper.getPreviousDay();

		//комиссия еще не посчитана или прошел день с момента создания платежа
		if (payment.getCommission() != null && transfer.getClientCreationDate().after(previousDay))
			return;
		
		String receiverPointCode = payment.getReceiverPointCode();

		Matcher matcher = PATTERN.matcher(receiverPointCode);
		if (!matcher.find())
			throw new GateException("Невозможно получить информацию по комиссии.");

		String serviceCode = matcher.group(SERVICE_CODE_GROUP_INDEX);
		String pointCode = matcher.group(POINT_CODE_GROUP_INDEX);

		BackRefReceiverInfoService infoService = GateSingleton.getFactory().service(BackRefReceiverInfoService.class);
		BusinessRecipientInfo info = infoService.getRecipientInfo(pointCode, serviceCode);

		BigDecimal commissionRate = info.getCommissionRate();
		Money amount = payment.getDestinationAmount();
		Money commission = amount.multiply(commissionRate.doubleValue() / 100);
		Money minCommissionAmount = new Money(info.getMinCommissionAmount(), amount.getCurrency());
		Money maxCommissionAmount = new Money(info.getMaxCommissionAmount(), amount.getCurrency());

		if (commission.compareTo(minCommissionAmount) < 0)
		{
			commission = minCommissionAmount;
		}
		else if (commission.compareTo(maxCommissionAmount) > 0)
		{
			commission = maxCommissionAmount;
		}
		payment.setCommission(commission);
	}
}
