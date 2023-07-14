package com.rssl.phizic.business.ermb.profile.comparators;

import com.rssl.phizic.business.ermb.ErmbProfileImpl;

/**
 * Компаратор, проверяющий, изменился ли тариф ЕРМБ
 * @author Rtischeva
 * @ created 29.10.14
 * @ $Author$
 * @ $Revision$
 */
public class ErmbTariffComparator
{
	public int compare(ErmbProfileImpl oldProfile, ErmbProfileImpl newProfile)
	{
		if (oldProfile.getTarif() != null && newProfile.getTarif() != null
				&& !oldProfile.getTarif().getId().equals(newProfile.getTarif().getId()))
			return -1;

		return 0;
	}
}
