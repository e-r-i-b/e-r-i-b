package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

import java.util.Map;

/**
 * Валидатор для проверки наличия поля "card" в зависимости от версии mAPI в модуле CSAMAPI
 * @author Pankin
 * @ created 09.06.14
 * @ $Author$
 * @ $Revision$
 */
public class MobileAPILoginValidator extends MultiFieldsValidatorBase
{
	public static final String VERSION_FIELD = "version";
	public static final String CARD_FIELD = "card";

	private static final String MESSAGE = "Укажите 4 последние цифры карты";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		if (!ConfigFactory.getConfig(MobileApiConfig.class).isMobileApiRegistrationCheckCardNum())
			return true;

		String version = (String) retrieveFieldValue(VERSION_FIELD, values);
		String card = (String) retrieveFieldValue(CARD_FIELD, values);

		if (StringHelper.isEmpty(card))
		{
			try
			{
				VersionNumber vNumber = VersionNumber.fromString(version);
				if (vNumber.ge(MobileAPIVersions.V8_00))
				{
					setMessage(MESSAGE);
					return false;
				}
			}
			catch (MalformedVersionFormatException e)
			{
				throw new TemporalDocumentException(e);
			}
		}

		return true;
	}
}
