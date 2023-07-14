package com.rssl.phizgate.common.payments;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.BackRefReceiverInfoService;
import com.rssl.phizic.gate.payments.systems.recipients.BusinessRecipientInfo;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Подсчитываем комиссию платежа, использовать в случаях, если внешняя система
 * не предоставляет комиссию, берем ее из нашей базы.
 *
 * @author khudyakov
 * @ created 21.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class DefaultBackRefCommissionCalculator extends AbstractCommissionCalculator
{
	public void calcCommission(GateDocument transfer) throws GateException, GateLogicException
	{
		if (!(transfer instanceof AbstractPaymentSystemPayment))
			throw new GateException("Неверный тип платежа, ожидался наследник AbstractPaymentSystemPayment");

		AbstractPaymentSystemPayment payment = (AbstractPaymentSystemPayment) transfer;

		Calendar currentDate = Calendar.getInstance();
		currentDate.add(Calendar.DAY_OF_MONTH, -1);

		//если комиссия еще не посчитана, или прошел день с момента создания платежа
		if (payment.getCommission() == null || currentDate.after(transfer.getClientCreationDate()))
		{
			BackRefReceiverInfoService infoService = GateSingleton.getFactory().service(BackRefReceiverInfoService.class);
			BusinessRecipientInfo info = infoService.getRecipientInfo(payment.getReceiverPointCode(), payment.getService().getCode());

			BigDecimal commissionRate = info.getCommissionRate();
			Money amount = payment.getDestinationAmount();
			Money commission = amount.multiply(commissionRate.doubleValue() / 100);
			Money minCommissionAmount = new Money(info.getMinCommissionAmount(), amount.getCurrency());
			Money maxCommissionAmount = new Money(info.getMaxCommissionAmount(), amount.getCurrency());

			if (commission.getDecimal().compareTo(minCommissionAmount.getDecimal()) < 0)
			{
				commission = minCommissionAmount;
			}
			else if (commission.getDecimal().compareTo(maxCommissionAmount.getDecimal()) > 0)
			{
				commission = maxCommissionAmount;
			}
			payment.setCommission(commission);
		}
	}
}
