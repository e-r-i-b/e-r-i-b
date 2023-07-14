package com.rssl.phizic.web.logging;

import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.logging.Constants;

/**
 * @author Krenev
 * @ created 01.09.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditMessagesLoggingSettingsForm extends EditLoggingSettingsFormBase
{
	@Override
	protected String getLogPrefix()
	{
		return Constants.MESSAGE_LOG_PREFIX;
	}

	public void addAdditionalFields(FormBuilder fb)
	{
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("”ровень детализации протокола взаимодействи€ с RS-Retail");
		fieldBuilder.setName(getLogPrefix() + "level.retail");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());

		fb.addField(fieldBuilder.build());
	}
}
