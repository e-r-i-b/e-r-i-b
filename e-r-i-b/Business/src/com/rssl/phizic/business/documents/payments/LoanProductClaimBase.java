package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.gate.documents.GateDocument;

/**
 * Created by IntelliJ IDEA.
 * User: Moshenko
 * Date: 29.07.2011
 * Time: 18:28:17
 */
public abstract class LoanProductClaimBase extends LoanClaimBase
{
	private static final String TYPE_OF_CREDIT = "type-of-credit";  // тип кредита

	public String getTypeOfCredit()
	{
		return getNullSaveAttributeStringValue(TYPE_OF_CREDIT);
	}

	public Class<? extends GateDocument> getType()
	{
		return null;
	}
}
