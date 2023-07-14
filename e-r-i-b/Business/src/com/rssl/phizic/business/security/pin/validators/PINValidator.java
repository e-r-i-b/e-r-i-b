package com.rssl.phizic.business.security.pin.validators;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.auth.pin.PINEnvelope;
import com.rssl.phizic.auth.pin.PINService;

/**
 * Проверка PIN конверта.
 *
 * @author Roshka
 * @ created 22.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class PINValidator extends FieldValidatorBase
{
	private static final PINService      pinService = new PINService();

	public boolean validate(String value) throws TemporalDocumentException
	{
		try
		{
			if (isValueEmpty(value))
				return true;

			PINEnvelope envelope = pinService.findEnvelope( value );
			if ( envelope == null )
				return false;

			if( ! envelope.getState().equals(PINEnvelope.STATE_UPLOADED) )
				return false;

			return true;

		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

}