package com.rssl.phizicgate.sbrf.utils;

import com.rssl.phizic.gate.claims.LossPassbookApplicationClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizicgate.sbrf.ws.listener.ConfirmOfflineRequestHandler;
import com.rssl.phizicgate.sbrf.ws.listener.handler.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gladishev
 * @ created 22.10.2013
 * @ $Author$
 * @ $Revision$
 */

public class DocumentOfflineHandlerHelper
{
	private static final Map< Class<? extends GateDocument>, ConfirmOfflineRequestHandler> successRequestHandlersByClass
			= new HashMap< Class<? extends GateDocument>, ConfirmOfflineRequestHandler>();

	static
	{
		ConfirmOfflineRequestHandler successJurPaymentHandler = new JurPaymentConfirmationOfflineHandler();
		RUSPaymentConfirmationOfflineHandler RUSPaymentConfirmationOfflineHandler = new RUSPaymentConfirmationOfflineHandler();

		successRequestHandlersByClass.put(ClientAccountsTransfer.class, new ClientAccountsTransferConfirmationOfflineHandler());
		successRequestHandlersByClass.put(AccountToCardTransfer.class, new AccountToCardTransferConfirmationOfflineHandler());
		successRequestHandlersByClass.put(LossPassbookApplicationClaim.class, new LossPassbookHandler());
		successRequestHandlersByClass.put(AccountJurTransfer.class, successJurPaymentHandler);
		successRequestHandlersByClass.put(AccountJurIntraBankTransfer.class, successJurPaymentHandler);
		successRequestHandlersByClass.put(AccountPaymentSystemPayment.class, new GorodPaymentConfirmationOfflineHandler());
		successRequestHandlersByClass.put(AccountRUSPayment.class, RUSPaymentConfirmationOfflineHandler);
		successRequestHandlersByClass.put(AccountIntraBankPayment.class, RUSPaymentConfirmationOfflineHandler);
		successRequestHandlersByClass.put(RUSTaxPayment.class, RUSPaymentConfirmationOfflineHandler);
		successRequestHandlersByClass.put(AccountJurTransfer.class, RUSPaymentConfirmationOfflineHandler);
		successRequestHandlersByClass.put(CardJurTransfer.class, RUSPaymentConfirmationOfflineHandler);
		successRequestHandlersByClass.put(CardRUSTaxPayment.class, RUSPaymentConfirmationOfflineHandler);
		successRequestHandlersByClass.put(AccountRUSTaxPayment.class, RUSPaymentConfirmationOfflineHandler);
	}

	/**
	 * ¬озвращает обработчик запроса на исполнение документа по классу
	 * @param documentClass - класс документа
	 * @return обработчик запроса
	 */
	public static ConfirmOfflineRequestHandler getSuccessOfflineRequestHandler(Class<? extends GateDocument> documentClass)
	{
		return successRequestHandlersByClass.get(documentClass);
	}
}
