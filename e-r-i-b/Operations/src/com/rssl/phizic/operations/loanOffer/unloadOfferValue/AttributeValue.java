package com.rssl.phizic.operations.loanOffer.unloadOfferValue;

import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author gulov
 * @ created 13.07.2011
 * @ $Authors$
 * @ $Revision$
 */
class AttributeValue extends LoanValue
{
	private String attributeTitle;

	AttributeValue(GateExecutableDocument loan, String attributeTitle, boolean mandatory, int commaCount)
	{
		super(loan, mandatory, commaCount);
		this.attributeTitle = attributeTitle;
	}

	AttributeValue(GateExecutableDocument loan, String attributeTitle)
	{
		super(loan, true, 1);
		this.attributeTitle = attributeTitle;
	}

	public Object getValue()
	{
		return replaceComma(loan.getAttribute(attributeTitle).getStringValue());
	}

	public boolean isEmpty()
	{
		ExtendedAttribute attribute = loan.getAttribute(attributeTitle);

		return attribute == null || attribute.getStringValue() == null || StringHelper.isEmpty(attribute.getStringValue());
	}

	public String getMessage()
	{
		return attributeTitle;
	}
}
