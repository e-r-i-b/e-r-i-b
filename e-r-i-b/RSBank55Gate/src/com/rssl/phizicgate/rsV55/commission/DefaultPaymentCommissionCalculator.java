package com.rssl.phizicgate.rsV55.commission;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import org.w3c.dom.Document;

import java.math.BigDecimal;

/**
 * @author Krenev
 * @ created 15.06.2007
 * @ $Author$
 * @ $Revision$
 */
public class DefaultPaymentCommissionCalculator extends AbstractCommissionCalculator
{
	private static final String PARAMETER_OPERATION_TYPE_NAME = "operation-type";
	private static final String PARAMETER_SUBOPERATION_TYPE_NAME = "suboperation-type";

	/**
	 * получить комиссию по документу
	 * @param document документ
	 */
	public void calcCommission(GateDocument document) throws GateException, GateLogicException
	{
		WebBankServiceFacade webBankServiceFacade = getWebBankServiceFacade();

		AbstractAccountTransfer payment = (AbstractAccountTransfer) document;
		
		Money amount = payment.getChargeOffAmount();

		GateMessage message = webBankServiceFacade.createRequest("commissionPay_q");
		message.addParameter("account", payment.getChargeOffAccount());
		message.addParameter("filial", getFnCash(payment));
		message.addParameter("date", getDate(payment));
		message.addParameter("sum", amount.getDecimal().toString());
		message.addParameter("currency", getCurrency(payment));
		message.addParameter("clientId", payment.getClientInfo().getExternalOwnerId());
		message.addParameter("operationType", getOperationType());
		message.addParameter("operationSubspecies", getOperationSubspecies(document));

		Document response = webBankServiceFacade.sendOnlineMessage(message, null);

		BigDecimal commission = getDocumentCommission(response);
		payment.setCommission(new Money(commission, amount.getCurrency()));
	}

	/**
	 * получить тип операции.
	 * @return тип операции.
	 */
	protected String getOperationType(){
		return (String) getParameter(PARAMETER_OPERATION_TYPE_NAME);
	}

	protected String getOperationSubspecies(GateDocument doc) throws GateException{
		return (String) getParameter(PARAMETER_SUBOPERATION_TYPE_NAME);
	}
}
