package com.rssl.phizic.business.claims.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.resources.external.CardLink;

import java.util.Map;

/**
 * @author Gulov
 * @ created 15.07.2010
 * @ $Authors$
 * @ $Revision$
 */
public class CardLinkStateEqualToOneFromParametersValidator extends EqualToOneFromParametersValidator
{
	private final static String FIELD_CARD_ID = "cardId";

	public CardLinkStateEqualToOneFromParametersValidator()
	{
		super();
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		CardLink cardLink = getCardLink(FIELD_CARD_ID, values);
		setBinding(FIELD_PARAMETER_NAME, FIELD_PARAMETER_NAME);
		values.put(FIELD_PARAMETER_NAME, cardLink.getCard().getCardState().toString());

		return super.validate(values);
	}
}
