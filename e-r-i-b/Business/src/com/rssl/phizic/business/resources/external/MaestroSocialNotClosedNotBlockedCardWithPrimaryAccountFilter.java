package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.utils.StringHelper;

/** ������� ���������� ((9, "I", 111738, "Maestro-����������" �� ������� CardAcctDInqRq), ����� �������� � ���������������, c ������� ����� �����
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
