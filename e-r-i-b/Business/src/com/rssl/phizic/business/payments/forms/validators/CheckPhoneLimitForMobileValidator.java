package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ApplicationConfig;

import java.util.Map;

/**
 * @author bogdanov
 * @ created 24.11.14
 * @ $Author$
 * @ $Revision$
 */

public class CheckPhoneLimitForMobileValidator extends CheckPhoneLimitValidator
{
	@Override
	public boolean validate(Map values) throws TemporalDocumentException
	{
		if (!ApplicationInfo.isMobileApi(ApplicationInfo.getCurrentApplication()))
			return true;

		if (!super.validate(values))
			throw new RuntimeException(getMessage());
		return true;
	}
}
