package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.AdditionalCardType;

import java.util.Map;

/** Валидатор, определяющий, не является ли карта дополнительной, выпущенной на имя третьего лица
 * @author Rtischeva
 * @created 06.09.13
 * @ $Author$
 * @ $Revision$
 */
public class CardNotAdditionalClientToOtherValidator extends AccountAndCardValidatorBase
{
	private static String FIELD_RESOURCE = "resource";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Object resource = retrieveFieldValue(FIELD_RESOURCE, values);

		if (!(resource instanceof CardLink))
			return true;
		CardLink cardLink = (CardLink) resource;

		return (cardLink.getAdditionalCardType() != AdditionalCardType.CLIENTTOOTHER);
	}
}
