package com.rssl.phizic.operations.loanOffer;

import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.LoanCardClaim;
import com.rssl.phizic.business.loanCardOffer.LoanCardOffer;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CurrencyCreditProductCondition;

import java.util.Comparator;

/**
 * User: Moshenko
 * Date: 19.05.15
 * Time: 18:46
 */
public class BusinessDocumentExecutionDateComporator implements Comparator<GateExecutableDocument>
{
	public int compare(GateExecutableDocument o1, GateExecutableDocument o2)
	{
		BusinessDocumentBase documentBase1 = o1;
		BusinessDocumentBase documentBase2 = o2;
		return documentBase1.getExecutionDate().compareTo(documentBase2.getExecutionDate());
	}
}
