package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.payments.LoanOfferClaim;
import com.rssl.phizic.business.documents.payments.LoanProductClaim;

/**
 * @author gulov
 * @ created 29.07.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Проверка принадлежности документа к заявке на кредит
 */
public class LoanCondition extends LoanPaymentCondition
{
	protected boolean needUse(BusinessDocumentBase document)
	{
		return document instanceof LoanOfferClaim || document instanceof LoanProductClaim;
	}
}
