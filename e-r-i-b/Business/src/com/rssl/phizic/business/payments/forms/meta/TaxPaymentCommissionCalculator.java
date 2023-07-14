package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.kbk.KBK;
import com.rssl.phizic.business.dictionaries.kbk.KBKService;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.documents.CommissionCalculator;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.AccountRUSTaxPayment;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author gladishev
 * @ created 19.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class TaxPaymentCommissionCalculator implements CommissionCalculator
{
	private static final KBKService kbkService = new KBKService();

	public void setParameters(Map<String, ?> params)
	{
	}

	/*процентная ставка расчитывается из значений КБК*/
	public void calcCommission(GateDocument document) throws GateException, GateLogicException
	{
		if (document.getType() != AccountRUSTaxPayment.class)
		{
			throw new GateException("Ожидается AccountRUSTaxPayment");
		}
		AccountRUSTaxPayment payment = (AccountRUSTaxPayment) document;
		String kbkCode = payment.getTaxKBK();
		try
		{
			KBK kbk = kbkService.findByCode(kbkCode);
			if (kbk == null)
				return;

			BigDecimal comissionRate = kbk.getRate();
			Money amount = payment.getDestinationAmount();
			Money commission = amount.multiply(comissionRate.doubleValue() / 100);
			Money minComissionAmount = kbk.getMinCommission();
			Money maxComissionAmount = kbk.getMaxCommission();
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
