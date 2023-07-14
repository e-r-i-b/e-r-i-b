package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.Card;

import java.util.Map;

/**
 * @author Balovtsev
 * @created 03.03.2011
 * @ $Author$
 * @ $Revision$
 */

public abstract class VirtualCardTransferValidatorBase extends MultiFieldsValidatorBase
{
	protected static String FIELD_TO_RESOURCE   = "toResource";
	protected static String FIELD_FROM_RESOURCE = "fromResource";
	
	protected Card getCard(Object obj)
	{
		if(obj instanceof CardLink)
		{
			return ((CardLink)obj).getCard();
		}
		else
		{
			return null;
		}
	}

	public abstract boolean validate(Map values);
}
