package com.rssl.phizicgate.rsV55.commission;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.SWIFTPayment;
import com.rssl.phizic.gate.payments.systems.SWIFTPaymentConditions;

/**
 * @author Krenev
 * @ created 22.06.2007
 * @ $Author$
 * @ $Revision$
 */

public class SWIFTPaymentCommissionCalculator extends DefaultPaymentCommissionCalculator
{
	private static final String PARAMETER_SUBOPERATION_TYPE_SPOT_NAME = "suboperation-type-spot";
	private static final String PARAMETER_SUBOPERATION_TYPE_TOM_NAME = "suboperation-type-tom";
	private static final String PARAMETER_SUBOPERATION_TYPE_TOD_NAME = "suboperation-type-tod";

	protected String getOperationSubspecies(GateDocument doc) throws GateException
	{
		SWIFTPayment payment = (SWIFTPayment) doc;
		if (payment.getConditions() == SWIFTPaymentConditions.tod)
			return getCurrencyTransferSuboperationTOD();
		if (payment.getConditions() == SWIFTPaymentConditions.tom)
			return getCurrencyTransferSuboperationTOM();
		if (payment.getConditions() == SWIFTPaymentConditions.spot)
			return getCurrencyTransferSuboperationSPOT();
		throw new GateException("не определены условия перевода!");
	}

	private String getCurrencyTransferSuboperationSPOT()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_SPOT_NAME);
	}

	private String getCurrencyTransferSuboperationTOM()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_TOM_NAME);
	}

	private String getCurrencyTransferSuboperationTOD()
	{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_TOD_NAME);
	}
}
