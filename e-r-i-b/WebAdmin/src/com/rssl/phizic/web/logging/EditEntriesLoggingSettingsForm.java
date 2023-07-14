package com.rssl.phizic.web.logging;

import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.logging.Constants;

/**
 * @author komarov
 * @ created 12.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class EditEntriesLoggingSettingsForm extends EditLoggingSettingsFormBase
{
	@Override
	protected String getLogPrefix()
	{
		return Constants.ENTRIES_LOG_PREFIX;
	}

	public void addAdditionalFields(FormBuilder fb)
	{}
}
