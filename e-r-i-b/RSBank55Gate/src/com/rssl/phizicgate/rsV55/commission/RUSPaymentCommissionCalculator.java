package com.rssl.phizicgate.rsV55.commission;

import com.rssl.phizgate.common.payments.PaymentHelper;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AbstractJurTransfer;
import com.rssl.phizic.gate.payments.AbstractPhizTransfer;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;

/**
 * @author Krenev
 * @ created 22.06.2007
 * @ $Author$
 * @ $Revision$
 */
//перевод на счёт в другой банк
public class RUSPaymentCommissionCalculator extends DefaultPaymentCommissionCalculator
{
	private static final String PARAMETER_SUBOPERATION_TYPE_KORR = "suboperation-type-korr";
	private static final String PARAMETER_SUBOPERATION_TYPE_JUR = "suboperation-type-transfer-jur";
	private static final String PARAMETER_SUBOPERATION_TYPE_FIZ = "suboperation-type-transfer-fiz";
	private static final String PARAMETR_SUBOPERATION_TYPE_TAX = "suboperation-tax-type";

	protected String getOperationSubspecies(GateDocument doc) throws GateException
	{
		if(!(doc instanceof AbstractRUSPayment))
		{
			throw new GateException("Неверный тип платежа, должен быть - AbstractRUSPayment.");
		}
		AbstractRUSPayment rusPayment = (AbstractRUSPayment)doc;
		String res = "0";

		if (rusPayment instanceof AbstractJurTransfer)
		{
			res = getTransferSuboperationJUR();
		}
		else if (rusPayment instanceof AbstractPhizTransfer)
		{
		    res = getTransferSuboperationFIZ();
		}

		if (PaymentHelper.isCorrespondentBank(rusPayment))
			res = getTransferSuboperationKORR();

		return res;
	}
	private String getTransferSuboperationKORR()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_KORR);
	}
	private String getTransferSuboperationJUR()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_JUR);
	}
	private String getTransferSuboperationFIZ()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_FIZ);
	}
}
