package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;

/**
 * @author Ismagilova
 * @ created 14.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class ApplicationIsATMFilter extends HandlerFilterBase
{
	public boolean isEnabled(StateObject stateObject) throws DocumentException
	{
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		return applicationConfig.getApplicationInfo().isATM();
	}
}
