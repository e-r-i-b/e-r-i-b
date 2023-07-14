package com.rssl.phizic.business.personalOffer;

import java.util.Comparator;

/**
 * @author lukina
 * @ created 26.12.2013
 * @ $Author$
 * @ $Revision$
 */
public class PersonalOfferOrderedFieldComparator  implements Comparator<PersonalOfferOrderedField>
{
	public int compare(PersonalOfferOrderedField orderedField1, PersonalOfferOrderedField orderedField2)
	{
		if (orderedField1.getOrderIndex() < orderedField2.getOrderIndex())
			return -1;
		if (orderedField1.getOrderIndex() > orderedField2.getOrderIndex())
			return 1;
		return 0;
	}
}

