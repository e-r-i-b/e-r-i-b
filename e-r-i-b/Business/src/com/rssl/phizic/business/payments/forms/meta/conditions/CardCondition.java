package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.payments.LoanCardClaim;
import com.rssl.phizic.business.documents.payments.LoanCardProductClaim;
import com.rssl.phizic.business.documents.payments.LoanCardOfferClaim;

/**
 * @author gulov
 * @ created 29.07.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Проверка принадлежности класса к заявке на кредитную карту
 */
public class CardCondition extends LoanPaymentCondition
{
	protected boolean needUse(BusinessDocumentBase document)
	{
		return document instanceof LoanCardProductClaim || document instanceof LoanCardOfferClaim || document instanceof LoanCardClaim;
	}
}
