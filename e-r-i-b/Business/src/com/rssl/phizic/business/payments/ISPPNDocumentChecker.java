package com.rssl.phizic.business.payments;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.metadata.checkers.DocumentCheckerBase;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;

/**
 * @author Gainanov
 * @ created 05.02.2009
 * @ $Author$
 * @ $Revision$
 */
public class ISPPNDocumentChecker extends DocumentCheckerBase
{
	/**
	 * Проверить документ
	 * @param doc документ
	 * @return результат проверки
	 */
	public boolean check(BusinessDocument doc){
		if(!(doc instanceof GateExecutableDocument)){
			return false;
		}
		GateExecutableDocument payment = (GateExecutableDocument)doc;
		return payment.getType()== AccountPaymentSystemPayment.class;
	}
}
