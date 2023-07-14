package com.rssl.phizic.operations.forms;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.documents.payments.RurPayment;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jatsky
 * @ created 06.10.14
 * @ $Author$
 * @ $Revision$
 */

public class ViewDocumentMobileOperation extends ViewDocumentOperation
{
	//Виды платежей, для которых разрешена печать чека
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
		printableFormNames.add(FormConstants.IMA_PAYMENT_FORM);
		printableFormNames.add(FormConstants.IMA_OPENING_CLAIM);
		printableFormNames.add(FormConstants.EXTERNAL_PROVIDER_PAYMENT_FORM);
		printableFormNames.add(FormConstants.AIRLINE_RESERVATION_PAYMENT_FORM);
		printableFormNames.add(FormConstants.CREATE_P2P_AUTO_TRANSFER_CLAIM_FORM);
		printableFormNames.add(FormConstants.PREAPPROVED_LOAN_CARD_CLAIM);
		printableFormNames.add(FormConstants.EXTENDED_LOAN_CLAIM);
	}

	/**
	 * Проверка разрешения печати чека для данной операции.
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
