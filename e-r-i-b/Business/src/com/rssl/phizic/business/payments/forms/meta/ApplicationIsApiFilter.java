package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;

/**
 * Фильтр по API
 * @author Jatsky
 * @ created 26.09.13
 * @ $Author$
 * @ $Revision$
 */

public class ApplicationIsApiFilter extends HandlerFilterBase
{
	public boolean isEnabled(StateObject stateObject) throws DocumentException
	{
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		return applicationConfig.getApplicationInfo().isATM() || applicationConfig.getApplicationInfo().isMobileApi();
	}
}
