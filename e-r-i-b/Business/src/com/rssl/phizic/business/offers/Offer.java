package com.rssl.phizic.business.offers;

import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * @author Rtischeva
 * @ created 19.01.15
 * @ $Author$
 * @ $Revision$
 */
public interface Offer
{
	OfferId getOfferId();

	String getFirstName();

	String getSurName();

	String getPatrName();

	Calendar getBirthDay();

	Money getMaxLimit();

	Long getTerbank();

	int getPriority();
}
