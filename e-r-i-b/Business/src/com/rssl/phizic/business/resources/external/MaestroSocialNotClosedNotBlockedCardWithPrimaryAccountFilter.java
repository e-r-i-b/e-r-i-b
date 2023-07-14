package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.utils.StringHelper;

/** Маэстро Социальная ((9, "I", 111738, "Maestro-Социальная" из запроса CardAcctDInqRq), кроме закрытых и заблокированных, c номером счета карты
 * @author sergunin
 * @ created 22.11.13
 * @ $Author$
 * @ $Revision$
 */

public class MaestroSocialNotClosedNotBlockedCardWithPrimaryAccountFilter extends MaestroSocialNotClosedNotBlockedCardFilter
{

	@Override public boolean evaluate(Object object)
	{
		if (!(object instanceof CardLink)) return false;

		boolean result = super.evaluate(object);
		CardLink cardlink = (CardLink) object;

		return result && StringHelper.isNotEmpty(cardlink.getCardPrimaryAccount());
	}

}
