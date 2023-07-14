package com.rssl.phizic.operations.forms;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.documents.payments.RurPayment;

import java.util.Set;
import java.util.HashSet;

/**
 * ѕечать чека операции
 * @author Jatsky
 * @ created 02.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class ViewDocumentATMOperation extends ViewDocumentOperation
{
	//¬иды платежей, дл€ которых разрешена печать чека
	private static final Set<String> printableFormNames = new HashSet<String>();

	static
	{
		printableFormNames.add(FormConstants.SERVICE_PAYMENT_FORM);
		printableFormNames.add(FormConstants.INTERNAL_PAYMENT_FORM);
		printableFormNames.add(FormConstants.JUR_PAYMENT_FORM);
		printableFormNames.add(FormConstants.RUR_PAYMENT_FORM);
		printableFormNames.add(FormConstants.ACCOUNT_OPENING_CLAIM_FORM);
	    printableFormNames.add(FormConstants.ACCOUNT_CLOSING_PAYMENT_FORM);
		printableFormNames.add(FormConstants.LOAN_CARD_PRODUCT_FORM);
		printableFormNames.add(FormConstants.LOAN_CARD_OFFER_FORM);
		printableFormNames.add(FormConstants.LOAN_PAYMENT_FORM);
        printableFormNames.add(FormConstants.LOAN_OFFER_FORM);
        printableFormNames.add(FormConstants.LOAN_PRODUCT_FORM);
	}

	/**
	 * ѕроверка разрешени€ печати чека дл€ данной операции.
	 * @return true - можно печатать
	 */
	@Override public boolean checkPrintCheckForOperation()
	{
		return printableFormNames.contains(document.getFormName()) && !isSubscription();
	}

	private boolean isSubscription() {
		return document instanceof RurPayment && document.isLongOffer();
	}
}
